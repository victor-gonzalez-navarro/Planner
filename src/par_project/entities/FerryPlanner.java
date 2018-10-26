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
import par_project.entities.predicates.FreeLine;
import par_project.entities.predicates.NextToDock;
import par_project.entities.predicates.Predicate;
import par_project.utils.Constants;

public class FerryPlanner {    
    Deque<Object> stack = new ArrayDeque<>();
    State init_state, curr_state , target_state;
    List<Operator> stepsToGoal;
    public int numLinesEmpty;
    public int numMaxCars;
    
    public FerryPlanner (State init_state, State target_state, int numLinesEmpty, int numMaxCars){
        this.init_state = init_state;
        this.target_state = target_state;
        stepsToGoal = new ArrayList<>();
        this.numLinesEmpty = numLinesEmpty;
        this.numMaxCars = numMaxCars;
    }
    
    public void solveProblem () throws CloneNotSupportedException{
        curr_state = init_state.copy();
        
        stack.add(target_state.getPredicates());
        
        for (Predicate pred : target_state.getPredicates()){
            stack.add(pred);
        }
        
        boolean finished = false;
        Predicate pred;
        
        int counter = 0;
        int cars_behind_y = 0;
        
        while(!finished && counter < 3) {
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
                            if (p instanceof NextToDock){
                                if (!((NextToDock) p).firstCar(curr_car).equals(null)){
                                    cars_behind_y++;
                                    curr_car = ((NextToDock) p).firstCar(curr_car);
                                    counting = true;
                                }
                            }
                        }
                    }
                }
                
                for (Predicate p : op.getAddList()) {
                    if (!curr_state.getPredicates().contains(p)){
                        if (op instanceof UnstackStackDock){
                            if (p instanceof FreeLine &&
                                    ((FreeLine)p).getCar() == ((UnstackStackDock)op).getFirstCar()){
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
                                    ((FreeLine)p).getCar() == ((UnstackStackDock)op).getFirstCar()){
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
                System.out.println("New Operator");
                
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
                    stack.pop();
                }
                
            // Case 3 & 4
            } else if (stack.getLast() instanceof Predicate) {
                pred = (Predicate) stack.getLast();
                // Case 3
                if (!pred.isInstantiated()){
                    if (curr_state.getPredicates().contains(pred)){
                        stack.pop();
                    } else {
                        stack.pop();
                    }
                }
                // Case 4
                else {
                    
                }
                
            }
            
            counter++;
        }
    }
    
    public Car bestInstantiation (State curr_state, ArrayList<Car> possibleCars, Predicate p){
        Car out_car = null;
        
        for (Predicate pred : curr_state.getPredicates()){
            if (pred.getClass().equals(p.getClass())){
                
            }
        }
        
        return out_car;
    }
}
