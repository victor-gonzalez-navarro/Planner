/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.states;

import par_project.entities.items.Car;
import par_project.entities.predicates.NextToDock;
import par_project.entities.predicates.NextToFerry;
import par_project.entities.predicates.Predicate;
import par_project.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

/**
 *
 * @author alarca_94
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
        this.predicates.remove(pred);
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
    
    public Predicate getPredicate (String predicateName, ArrayList<String> carIDs){
        for (String carID : carIDs){
            if (!getPredicate (predicateName, carID).equals(null)){
                return getPredicate (predicateName, carID);
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

    public int carsBehind(Car x, String dockOrFerry) {
        int cars_behind_y = 0;
        Car curr_car = new Car(x.identifier);

        if (dockOrFerry.equals(Constants.DOCK)) {
            for (Predicate p : this.predicates){
                if ((p instanceof NextToDock) &&
                        p.getCars().get(1).identifier.equals(curr_car.identifier)){
                    cars_behind_y++;
                    curr_car = p.getCars().get(0);
                }
            }
        } else if (dockOrFerry.equals(Constants.FERRY)){
            for (Predicate p : this.predicates){
                if ((p instanceof NextToFerry) &&
                        p.getCars().get(1).identifier.equals(curr_car.identifier)){
                    cars_behind_y++;
                    curr_car = p.getCars().get(0);
                }
            }
        }



        return cars_behind_y;
    }

    public int carsInFrontOf(Car x, String dockOrFerry) {
        int cars_in_front_y = 0;
        Car curr_car = new Car(x.identifier);
        if (dockOrFerry.equals(Constants.DOCK)) {
            for (Predicate p : this.predicates) {
                if ((p instanceof NextToDock) &&
                        p.getCars().get(0).identifier.equals(curr_car.identifier)) {
                    cars_in_front_y++;
                    curr_car = p.getCars().get(1);
                }
            }
        } else if (dockOrFerry.equals(Constants.FERRY)) {
            for (Predicate p : this.predicates) {
                if ((p instanceof NextToFerry) &&
                        p.getCars().get(0).identifier.equals(curr_car.identifier)) {
                    cars_in_front_y++;
                    curr_car = p.getCars().get(1);
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
