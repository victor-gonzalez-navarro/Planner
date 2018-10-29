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
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            // Case 1
            if (stack.getLast() instanceof Operator) {
                Operator op = (Operator) stack.getLast();
                // Special case
                if (op instanceof UnstackStackDock) {
                    cars_behind_y = 0;
                    Car curr_car = ((UnstackStackDock)op).getThirdCar();
                    boolean counting = true;
                    while (counting){
                        counting = false;
                        // TODO: Save all NextTo For Efficiency
                        for (Predicate p : curr_state.getPredicates()){
                            if ((p instanceof NextToDock) && 
                                    !((NextToDock) p).previousCar(curr_car).equals(null)){
                                cars_behind_y++;
                                curr_car = ((NextToDock) p).previousCar(curr_car);
                                counting = true;
                            }
                        }
                    }
                }
                
                for (Predicate p : op.getAddList()) {
                    if (!curr_state.getPredicates().contains(p)){
                        if (op instanceof UnstackStackDock){
                            if (p instanceof FreeLine &&
                                    ((FreeLine)p).getCar().equals(((UnstackStackDock)op).getFirstCar())){
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
                
                for (Predicate p : op.getAdd2List()) {
                    if (!curr_state.getPredicates().contains(p)){
                        curr_state.addPredicate(p);
                    }
                }
               
                for (Predicate p : op.getDelList()) {
                    if (curr_state.getPredicates().contains(p)){
                        if (op instanceof UnstackStackDock){
                            if (p instanceof FreeLine &&
                                    ((FreeLine)p).getCar().equals(((UnstackStackDock)op).getFirstCar())){
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
                
                stepsToGoal.add(op);
                
            // Case 2
            } else if (stack.getLast() instanceof ArrayList){
                boolean found = true;
                for (Predicate p : (ArrayList<Predicate>) stack.getLast()) {
                    if (!curr_state.getPredicates().contains(p)){
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
                        Operator op = Operator.searchAddPredicate(pred);
                        stack.add(op);
                        stack.add(op.getPrecsList());
                        op.getPrecsList().forEach((p) -> {
                            stack.add(p);
                        });
                    } else {
                        boolean found = false;

                        for (int i = 0; i < curr_state.getPredicates().size() && !found; i++){
                            Predicate pred2 = curr_state.getPredicates().get(i);
                            if (pred2.getClass().equals(pred.getClass()) && 
                                pred2.getCarIDs().equals(pred.getCarIDs())){
                                found = true;
                            }
                        }

                        if (!found){
                            Operator op = Operator.searchAddPredicate(pred);
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
            
            if (stack.isEmpty()){ finished = true;}
        }
    }
    
    public Predicate bestInstantiation (State curr_state, ArrayList<String> possibleCarIDs, Predicate p){
        Predicate out_pred = null;
        
        if (p.getXCar().identifier.equals("X")) {
            out_pred = curr_state.getPredicate(p.getPredicateName(), possibleCarIDs);
        } else {
            out_pred = curr_state.getPredicate(p.getPredicateName(), p.getXCar().identifier);
        }
        
        return out_pred;
    }
    
    public ArrayList<Predicate> sortPredicates(ArrayList<Predicate> preds){
        ArrayList<Predicate> sorted = new ArrayList<>();
        
        for (Predicate pred : preds){
            if (pred instanceof LastFerry){
                sorted.add(pred);
            }
        }
        for (Predicate pred : preds){
            if (pred instanceof NextToFerry){
                sorted.add(pred);
            }
        }
        for (Predicate pred : preds){
            if (pred instanceof FirstFerry){
                sorted.add(pred);
            }
        }
        
        return sorted;
    }
    
}
