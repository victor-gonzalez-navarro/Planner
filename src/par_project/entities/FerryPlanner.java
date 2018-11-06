package par_project.entities;

import par_project.entities.states.State;

import java.util.*;
import java.util.List;

import par_project.entities.items.Car;
import par_project.entities.operators.Operator;
import par_project.entities.operators.UnstackStackDock;
import par_project.entities.predicates.FirstFerry;
import par_project.entities.predicates.FreeLine;
import par_project.entities.predicates.LastFerry;
import par_project.entities.predicates.NextToFerry;
import par_project.entities.predicates.NumLinesEmpty;
import par_project.entities.predicates.Predicate;
import par_project.utils.Constants;
import par_project.utils.Functions;

/**
 * FerryPlanner Class contains the general logic to solve the planing problem according to a goal stack approach.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class FerryPlanner {    
    Deque<Object> stack = new ArrayDeque<>();    // Stack of Operators, ArrayLists of Predicates and Predicates
    State init_state, curr_state , target_state;
    List<Operator> stepsToGoal;                  // ArrayList for storing Operators necessary to reach the goal
    public ArrayList<Car> cars;
    public int numMaxCars;                       // Maximum number of Cars per line in the Dock
    public int numLinesEmpty;                    // Keep track of the number of empty lines.
                                                 // The predicate NumLinesEmpty will only serve to update this parameter
    
    public FerryPlanner (State init_state, State target_state, 
            ArrayList<Car> cars, int numLinesEmpty, int numMaxCars){
        this.init_state = init_state;
        this.target_state = target_state;
        stepsToGoal = new ArrayList<>();
        this.cars = cars;
        this.numLinesEmpty = numLinesEmpty;
        this.numMaxCars = numMaxCars;
    }
    
    public void solveProblem (){
        // Current State starts at the initial State
        curr_state = init_state.copy();

        // First, add the Goal State Predicates to the Stack
        stack.add(target_state.getPredicates());

        // Add each Predicate from the Goal State properly Sorted
        for (Predicate pred : sortPredicates(target_state.getPredicates())){
            stack.add(pred);
        }
        
        boolean finished = false;

        Predicate pred;
        
        int cars_behind_y = 0;

        // While there are still elements in the stack and no impossible State has been reached
        while(!finished) {

            // Case 1: Last Element of the Stack is an Operator
            if (stack.getLast() instanceof Operator) {
                Operator op = (Operator) stack.getLast();

                // Count the number of cars behing the car Y where car X is going to be Stacked in front
                if (op instanceof UnstackStackDock){
                    cars_behind_y = curr_state.carsBehind(((UnstackStackDock) op).getThirdCar(), Constants.DOCK);
                }

                // Add Predicates from the Operator's Add List to the current State
                for (Predicate p : op.getAddList()) {
                    // If Predicate is NumLinesEmpty, update the global Variable
                    if (p instanceof NumLinesEmpty){
                        numLinesEmpty += ((NumLinesEmpty) p).n;
                    } else if (!curr_state.contains(p)){
                        if (op instanceof UnstackStackDock){
                            if (p instanceof FreeLine &&
                                    (p.getCars().get(0).identifier.equals(op.getFirstCar().identifier))){
                                // Only add FreeLine(X) if the number of cars behind Y is lower that the maximum number
                                // of cars per line minus 2 (Y car and the new X car)
                                if (cars_behind_y < (numMaxCars - 2)){
                                    curr_state.addPredicate(p);
                                }
                            } else {
                                curr_state.addPredicate(p);
                            }
                        } else {
                            curr_state.addPredicate(p);
                        }
                    }
                }

                // Delete Predicates from the Operator's Del List from the current State
                for (Predicate p : op.getDelList()) {
                    // If Predicate is NumLinesEmpty, update the global Variable
                    if (p instanceof NumLinesEmpty){
                        numLinesEmpty += ((NumLinesEmpty) p).n;
                    } else if (curr_state.contains(p)){
                        if (op instanceof UnstackStackDock){
                            if (p instanceof FreeLine &&
                                    (p.getCars().get(0).identifier.equals(op.getFirstCar().identifier))){
                                // Only Delete FreeLine(X) if the number of cars behind Y is equal to the maximum number
                                // of cars per line minus 2
                                if (cars_behind_y == (numMaxCars-2)){
                                    curr_state.delPredicate(p);
                                }
                            } else {
                                curr_state.delPredicate(p);
                            }
                        } else {
                            curr_state.delPredicate(p);
                        }
                    }
                }

                // Delete the Operator from the Stack and Add it to the solution
                stack.removeLast();
                stepsToGoal.add(op);
                
            // Case 2: Last Element of the Stack is an ArrayList of Predicates
            } else if (stack.getLast() instanceof ArrayList){
                boolean found = true;

                // Check that Current State still contains the Instantiated Predicates
                for (Predicate p : (ArrayList<Predicate>) stack.getLast()) {
                    if (!(p instanceof NumLinesEmpty) && !curr_state.contains(p)){
                        stack.add(p);
                        found = false;
                    }
                }

                // If all Predicates are still satisfied in the Current State, delete the ArrayList
                if (found){
                    stack.removeLast();
                }
                
            // Case 3 & 4
            } else if (stack.getLast() instanceof Predicate) {
                pred = (Predicate) stack.getLast();
                // Case 3: Last Element of the Stack is a Predicate that is not instantiated
                if (!pred.isInstantiated()){
                    Boolean endFor = false;
                    int num = 0;
                    while (num < curr_state.getPredicates().size() && !endFor) {
                        Predicate p = curr_state.getPredicates().get(num);

                        // Both predicates are instances of the same type
                        if (pred.getClass().equals(p.getClass())) {
                            ArrayList<Car> ccars_currentstate = p.getCars();
                            ArrayList<Car> ccars_stack = pred.getCars();
                            Boolean equality = true;
                            Boolean enterWhile = true;
                            int index = 0;

                            // Compare Instantiated cars from both predicates to find a match
                            while (index < ccars_currentstate.size() && enterWhile) {
                                Car c_cstate = ccars_currentstate.get(index);
                                Car c_stack = ccars_stack.get(index);
                                if (c_stack.isInstantiated()) {
                                    if (!((c_cstate.identifier).equals(c_stack.identifier))){
                                        equality = false;
                                        enterWhile = false;
                                    }
                                }
                                index++;
                            }

                            // If a predicate in the current state satisfies the condition, instantiate the current
                            // predicate in the stack and propagate it
                            if (equality && pred.areCarsAvailable(ccars_currentstate)) {
                                pred.setCars(ccars_currentstate);
                                endFor = true;
                            }
                        }
                        num++;
                    }

                    if (!pred.isInstantiated()){
                        finished = true;
                        System.out.println("No predicate in the current state has been found to satisfy the condition");
                    }
                }
                // Case 4: Last Element of the Stack is a Predicate that is instantiated
                else {
                    // If predicate is NumLinesEmpty, check that there are empty lines in the current state
                    if (pred instanceof NumLinesEmpty && numLinesEmpty > 0){
                        stack.removeLast();
                    } else if (pred instanceof NumLinesEmpty && numLinesEmpty == 0){
                        // Search for an operator that contains the current predicate in its Add list
                        Operator op = Operator.searchAddPredicate(pred, curr_state, numLinesEmpty, cars);

                        if (op == null){
                            System.out.println("Finished in an impossible state");
                            finished = true;
                        } else {
                            // Add the Operator to the Stack
                            stack.add(op);

                            // Add the Preconditions List of the operator
                            stack.add(op.getPrecsList());

                            // Add each Predicate of the Preconditions List
                            op.getPrecsList().forEach((p) -> {
                                stack.add(p);
                            });
                        }
                    } else {
                        // If the Predicate is not satisfied in the current State yet...
                        if (!curr_state.contains(pred)){
                            // Search for an operator that contains the current predicate in its Add list
                            Operator op = Operator.searchAddPredicate(pred, curr_state, numLinesEmpty, cars);

                            if (op == null){
                                System.out.println("Finished in an impossible state");
                                finished = true;
                            } else {
                                // Add the Operator to the Stack
                                stack.add(op);

                                // Add the Preconditions List of the operator
                                stack.add(op.getPrecsList());

                                // Add each Predicate of the Preconditions List
                                op.getPrecsList().forEach((p) -> {
                                    stack.add(p);
                                });
                            }

                        } else {
                            stack.removeLast();
                        }
                    }
                }
                
            }

            // Check if the Planner has reached the target State
            if (stack.isEmpty()){
                if (curr_state.getPredicates().size() != target_state.getPredicates().size()){
                    System.out.println("Finished but current state and target state does not contain the same number of elements");
                } else {
                    System.out.println("Successfully finished");
                }
                finished = true;
            }
        }

        // Write the output file
        Functions.writeOutput(Constants.OUTPUT_PATH + Constants.OUTPUT_FILE_NAME, stepsToGoal,
                new int[]{stepsToGoal.size(),stepsToGoal.size()});
    }
    
    public ArrayList<Predicate> sortPredicates(ArrayList<Predicate> preds){
        ArrayList<Predicate> sorted = new ArrayList<>();

        Map<String, Integer> carsDepth = new HashMap<>();
        ArrayList<Integer> curr_layer_depths = new ArrayList<>();
        ArrayList<Predicate> curr_layer_preds = new ArrayList<>();
        ArrayList<Car> future_layer_cars = new ArrayList<>();
        ArrayList<Car> curr_layer_cars = new ArrayList<>();
        int maximum_idx;

        for (Car car : cars){
            carsDepth.put(car.identifier, curr_state.carsInFrontOf(car, Constants.DOCK));
        }

        int maxFerryDepth = -1;
        int currFerryDepth = 0;
        for (Predicate pred : preds){
            if (pred instanceof LastFerry){
                curr_layer_preds.add(pred);
                curr_layer_depths.add(carsDepth.get(pred.getCarIDs()));
                curr_layer_cars.add(pred.getCars().get(0));
                currFerryDepth = target_state.carsInFrontOf(pred.getCars().get(0), Constants.FERRY);
                if (currFerryDepth > maxFerryDepth){
                    maxFerryDepth = currFerryDepth;
                }
            }
        }

        for (int i = 0; i < curr_layer_preds.size(); i++){
            maximum_idx = Functions.argMax(curr_layer_depths);
            sorted.add(curr_layer_preds.get(maximum_idx));
            curr_layer_depths.set(maximum_idx, -1);
        }

        // NextToFerry
        for (int i = 0; i < maxFerryDepth; i++) {
            curr_layer_preds.removeAll(curr_layer_preds);
            curr_layer_depths.removeAll(curr_layer_depths);

            for (Predicate pred : preds) {
                if (pred instanceof NextToFerry){
                    for (Car car : curr_layer_cars){
                        if (car.identifier.equals(pred.getCars().get(0).identifier)){
                            curr_layer_preds.add(pred);
                            curr_layer_depths.add(carsDepth.get(pred.getCars().get(0).identifier));
                            future_layer_cars.add(pred.getCars().get(1));
                            break;
                        }
                    }
                }
            }

            for (int j = 0; j < curr_layer_preds.size(); j++) {
                maximum_idx = Functions.argMax(curr_layer_depths);
                sorted.add(curr_layer_preds.get(maximum_idx));
                curr_layer_depths.set(maximum_idx, -1);
            }

            curr_layer_cars = new ArrayList<>(future_layer_cars);
            future_layer_cars.removeAll(future_layer_cars);
        }

        // First Ferry

        curr_layer_preds.removeAll(curr_layer_preds);
        curr_layer_depths.removeAll(curr_layer_depths);

        for (Predicate pred : preds){
            if (pred instanceof FirstFerry){
                curr_layer_preds.add(pred);
                curr_layer_depths.add(carsDepth.get(pred.getCarIDs()));
            }
        }

        for (int i = 0; i < curr_layer_preds.size(); i++){
            maximum_idx = Functions.argMax(curr_layer_depths);
            sorted.add(curr_layer_preds.get(maximum_idx));
            curr_layer_depths.set(maximum_idx, -1);
        }
        
        return sorted;
    }
    
}
