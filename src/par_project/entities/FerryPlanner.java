/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alarca_94
 */

package par_project.entities;

import par_project.entities.states.State;

import java.awt.*;
import java.util.*;
import java.util.List;

import par_project.entities.items.Car;
import par_project.entities.operators.Operator;
import par_project.entities.operators.UnstackStackDock;
import par_project.entities.predicates.FirstFerry;
import par_project.entities.predicates.FreeLine;
import par_project.entities.predicates.LastFerry;
import par_project.entities.predicates.NextToDock;
import par_project.entities.predicates.NextToFerry;
import par_project.entities.predicates.NumLinesEmpty;
import par_project.entities.predicates.Predicate;
import par_project.utils.Constants;
import par_project.utils.Functions;

public class FerryPlanner {    
    Deque<Object> stack = new ArrayDeque<>();
    State init_state, curr_state , target_state;
    List<Operator> stepsToGoal;
    public ArrayList<Car> cars;
    public int numLinesEmpty;   // Keep track of the number of empty lines.
                                // The predicate will only serve to update this parameter
    public int numMaxCars;
    
    public FerryPlanner (State init_state, State target_state, 
            ArrayList<Car> cars, int numLinesEmpty, int numMaxCars){
        this.init_state = init_state;
        this.target_state = target_state;
        stepsToGoal = new ArrayList<>();
        this.cars = cars;
        this.numLinesEmpty = numLinesEmpty;
        this.numMaxCars = numMaxCars;
    }
    
    public void solveProblem () throws CloneNotSupportedException{
        curr_state = init_state.copy();
        
        System.out.println(curr_state.toString());
        
        stack.add(target_state.getPredicates());
        
        for (Predicate pred : sortPredicates(target_state.getPredicates())){
            stack.add(pred);
        }
        System.out.println(sortPredicates(target_state.getPredicates()));
        
        boolean finished = false;
        Predicate pred;
        
        int cars_behind_y = 0;
                
        while(!finished) {
            //////////////////////////System.out.println(stack.size());
            // Case 1
            if (stack.getLast() instanceof Operator) {
                Operator op = (Operator) stack.getLast();
                if (op instanceof UnstackStackDock){
                    cars_behind_y = curr_state.carsBehind(((UnstackStackDock) op).getThirdCar(), Constants.DOCK);
                }
                
                for (Predicate p : op.getAddList()) {
                    if (p instanceof NumLinesEmpty){
                        numLinesEmpty += ((NumLinesEmpty) p).n;
                    } else if (!curr_state.contains(p)){
                        if (op instanceof UnstackStackDock){
                            if (p instanceof FreeLine &&
                                    (p.getCars().get(0).identifier.equals(op.getFirstCar().identifier))){
                                if (cars_behind_y < numMaxCars){
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
               
                for (Predicate p : op.getDelList()) {
                    if (p instanceof NumLinesEmpty){
                        numLinesEmpty += ((NumLinesEmpty) p).n;
                    } else if (curr_state.contains(p)){
                        if (op instanceof UnstackStackDock){
                            if (p instanceof FreeLine &&
                                    (p.getCars().get(0).identifier.equals(op.getFirstCar().identifier))){
                                if (cars_behind_y == numMaxCars){
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

                stack.removeLast();
                stepsToGoal.add(op);
                
            // Case 2
            } else if (stack.getLast() instanceof ArrayList){
                boolean found = true;
                for (Predicate p : (ArrayList<Predicate>) stack.getLast()) {
                    if (!(p instanceof NumLinesEmpty) && !curr_state.contains(p)){
                        stack.add(p);
                        found = false;
                    }
                }
                // Delete the ArrayList
                if (found){
                    stack.removeLast();
                }
                
            // Case 3 & 4
            } else if (stack.getLast() instanceof Predicate) {
                pred = (Predicate) stack.getLast();
                // Case 3
                if (!pred.isInstantiated()){
                    Boolean endFor = false;
                    int num = 0;
                    while (num < curr_state.getPredicates().size() && !endFor) {
                        Predicate p = curr_state.getPredicates().get(num);
                        if (pred.getClass().equals(p.getClass())) { // p and pred are the same predicate
                            ArrayList<Car> ccars_currentstate = p.getCars();
                            ArrayList<Car> ccars_stack = pred.getCars();
                            Boolean equality = true;
                            Boolean enterWhile = true;
                            int index = 0;
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
                            if (equality) {
                                pred.setCars(ccars_currentstate);
                                endFor = true;
                            }
                        }
                        num++;
                    }
                }
                // Case 4
                else {
                    if (pred instanceof NumLinesEmpty && numLinesEmpty > 0){
                        stack.removeLast();
                    } else if (pred instanceof NumLinesEmpty && numLinesEmpty == 0){
                        Operator op = Operator.searchAddPredicate(pred, curr_state, numLinesEmpty);
                        stack.add(op);
                        stack.add(op.getPrecsList());
                        op.getPrecsList().forEach((p) -> {
                            stack.add(p);
                        });
                    } else {
//                        boolean found = false;
//
//                        for (int i = 0; i < curr_state.getPredicates().size() && !found; i++){
//                            Predicate pred2 = curr_state.getPredicates().get(i);
//                            if (pred2.getClass().equals(pred.getClass()) &&
//                                pred2.getCarIDs().equals(pred.getCarIDs())){
//                                found = true;
//                            }
//                        }

                        if (!curr_state.contains(pred)){
                            Operator op = Operator.searchAddPredicate(pred, curr_state, numLinesEmpty);
                            stack.add(op);
                            stack.add(op.getPrecsList());
                            op.getPrecsList().forEach((p) -> {
                                stack.add(p);
                            });

                        } else {
                            stack.removeLast();
                        }
                    }
                }
                
            }
            
            if (stack.isEmpty()){
                if (curr_state.getPredicates().size() != target_state.getPredicates().size()){
                    System.out.println("Finished but current state and target state does not contain the same number of elements");
                }
                finished = true;
            }
        }
        System.out.println("Finiquitao");
        System.out.println(curr_state.toString());
    }
    
    public Predicate bestInstantiation (State curr_state, ArrayList<String> possibleCarIDs, Predicate p){
        Predicate out_pred = null;
        
        if (p.getXCar().identifier.equals(Constants.X_IDENTIFIER)) {
            out_pred = curr_state.getPredicate(p.getPredicateName(), possibleCarIDs);
        } else {
            out_pred = curr_state.getPredicate(p.getPredicateName(), p.getXCar().identifier);
        }
        
        return out_pred;
    }
    
    public ArrayList<Predicate> sortPredicates(ArrayList<Predicate> preds){
        ArrayList<Predicate> sorted = new ArrayList<>();

        ////////////////////////
//        Map<String, Integer> carsDepth = new HashMap<>();
//        String id = "";
//        String prev_car_id = "";
//        int depth = 0;
//        ArrayList<String> carsCurrentLayer = new ArrayList<>();
//
//        for (Predicate pred : this.target_state.getPredicates()){
//            if (pred instanceof FirstFerry){
//                id = pred.getCarIDs();
//                depth = curr_state.carsInFrontOf(pred.getCars().get(0), Constants.DOCK);
//                carsDepth.put(id, depth);
//                carsCurrentLayer.add(id);
//            }
//        }
//
//
//
//        for (Predicate pred : this.target_state.getPredicates()){
//            if (pred instanceof NextToFerry){
//                prev_car_id = pred.getCarIDs().substring(1,2);
//                if (carsCurrentLayer.contains(prev_car_id)) {
//                    id = pred.getCarIDs().substring(0, 1);
//                    depth = curr_state.carsInFrontOf(pred.getCars().get(0), Constants.DOCK) +
//                            carsDepth.get(prev_car_id);
//                    carsDepth.put(id, depth);
//                    carsCurrentLayer.add(id);
//                    carsCurrentLayer.remove(prev_car_id);
//                }
//            }
//        }

        ///////////////////////

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
                            curr_layer_depths.add(carsDepth.get(pred.getCarIDs().substring(0,1)));
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


//        for (Predicate pred : preds){
//            if (pred instanceof NextToFerry){
//                sorted.add(pred);
//            }
//        }
//        for (Predicate pred : preds){
//            if (pred instanceof FirstFerry){
//                carsDepth.put(pred.getCarIDs(), curr_state.carsInFrontOf(pred.getCars().get(0), Constants.DOCK));
//                //sorted.add(pred);
//            }
//        }

        ArrayList<Predicate> curr_preds = new ArrayList<>();
        
        return sorted;
    }
    
}
