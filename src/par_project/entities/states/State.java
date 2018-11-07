/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.states;

import par_project.entities.items.Car;
import par_project.entities.predicates.*;
import par_project.utils.Constants;

import java.util.ArrayList;
import java.lang.StringBuilder;

/**
 * State Class contains a list of Predicates and some methods useful to deal with it.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class State {
    private ArrayList<Predicate> predicates;
     
    public State(){
        predicates = new ArrayList<>();
    }
    
    public State(ArrayList<Predicate> predicates){
        this.predicates = predicates;
    }
    
    public void addPredicate (Predicate pred){
        this.predicates.add(pred);
    }
    
    public void delPredicate (Predicate pred){
        for (int i = 0; i < this.predicates.size(); i++){
            if(predicates.get(i).getClass().equals(pred.getClass()) &&
                    predicates.get(i).getCarIDs().equals(pred.getCarIDs())){
                this.predicates.remove(i);
                break;
            }
        }
    }
    
    public ArrayList<Predicate> getPredicates (){
        return predicates;
    }
    
    public Predicate getPredicate (String predicateName, String carID){
        for (Predicate pred : predicates){
            if (pred.getPredicateName().equals(predicateName) && 
                    pred.getXCar().identifier == carID) {
                return pred;
            }
        }
        return null;
    }
    
    public State copy (){
        State out = new State();
        for (Predicate pred : this.getPredicates()){
            out.addPredicate(pred);
        }
        return out;
    }

    /**
     * CarsBehind method counts the number of cars behind X by counting the number of consecutive adjacent cars
     * via NextToDock/Ferry
     * @param x
     * @param dockOrFerry --> Possible values: Constants.DOCK, Constants.FERRY
     * @return
     */
    public int carsBehind(Car x, String dockOrFerry) {
        int cars_behind_y = 0;
        Car curr_car = new Car(x.identifier);
        boolean keep = true;

        if (dockOrFerry.equals(Constants.DOCK)) {
            // First check if it is Last in its line
            if (this.contains(new LastDock(curr_car))) {
                keep = false;
            }
            while(keep) {
                for (Predicate p : this.predicates) {
                    // Comparing the Predicate instance with NextToDock class and Car identifier of the second car with
                    // last car stored
                    if ((p instanceof NextToDock) &&
                            p.getCars().get(1).identifier.equals(curr_car.identifier)) {
                        cars_behind_y++;
                        curr_car = p.getCars().get(0);
                        // If this State contains a LastDock of the Current Car, stop
                        if (this.contains(new LastDock(curr_car))) {
                            keep = false;
                        }
                        break;
                    }
                }
                // Given Y is not the Last in its Line, if no cars are behind Y, there is an error in the input file
                if (cars_behind_y == 0) {
                    return -1;
                }
            }
        } else if (dockOrFerry.equals(Constants.FERRY)) {
            if (this.contains(new LastFerry(curr_car))) {
                keep = false;
            }
            while (keep) {
                for (Predicate p : this.predicates) {
                    if ((p instanceof NextToFerry) &&
                            p.getCars().get(1).identifier.equals(curr_car.identifier)) {
                        cars_behind_y++;
                        curr_car = p.getCars().get(0);
                        if (this.contains(new LastFerry(curr_car))) {
                            keep = false;
                        }
                        break;
                    }
                }
                // Given Y is not the Last in its Line, if no cars are behind Y, there is an error in the input file
                if (cars_behind_y == 0) {
                    return -1;
                }
            }
        }



        return cars_behind_y;
    }

    /**
     * CarsInFrontOf method counts the number of cars in front of X by counting the number of consecutive adjacent cars
     * via NextToDock/Ferry
     * @param x
     * @param dockOrFerry --> Possible values: Constants.DOCK, Constants.FERRY
     * @return
     */
    public int carsInFrontOf(Car x, String dockOrFerry) {
        int cars_in_front_y = 0;
        Car curr_car = new Car(x.identifier);
        boolean keep = true;
        if (dockOrFerry.equals(Constants.DOCK)){
            // First check if it is First in its line
            if (this.contains(new FirstDock(curr_car))){
                keep = false;
            }
            while(keep){
                for (Predicate p : this.predicates) {
                    if ((p instanceof NextToDock) &&
                            p.getCars().get(0).identifier.equals(curr_car.identifier)) {
                        cars_in_front_y++;
                        curr_car = p.getCars().get(1);
                        if (this.contains(new FirstDock(curr_car))) {
                            keep = false;
                        }
                        break;
                    }
                }
                // Given Y is not the Last in its Line, if no cars are in front of Y, there is an error in the input file
                if (cars_in_front_y == 0) {
                    return -1;
                }
            }
        } else if (dockOrFerry.equals(Constants.FERRY)) {
            if (this.contains(new FirstFerry(curr_car))) {
                keep = false;
            }
            while(keep) {
                for (Predicate p : this.predicates) {
                    if (p instanceof NextToFerry &&
                            p.getCars().get(0).identifier.equals(curr_car.identifier)) {
                        cars_in_front_y++;
                        curr_car = p.getCars().get(1);
                        if (this.contains(new FirstFerry(curr_car))) {
                            keep = false;
                        }
                        break;
                    }
                }
                // Given Y is not the Last in its Line, if no cars are in front of Y, there is an error in the input file
                if (cars_in_front_y == 0) {
                    return -1;
                }
            }
        }
        return cars_in_front_y;
    }

    public boolean contains(Predicate pred){
        for (int i = 0; i < this.predicates.size(); i++){
            Predicate pred2 = this.predicates.get(i);
            if (pred2.getClass().equals(pred.getClass()) &&
                    pred2.getCarIDs().equals(pred.getCarIDs())){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString (){
        StringBuilder out = new StringBuilder();
        for (Predicate pred : this.getPredicates()){
            out.append(pred.toString())
               .append(".");
        }
        // Remove last dot
        out.setLength(out.length() - 1);
        
        return out.toString();
    }
}
