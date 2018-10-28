/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.states;

import par_project.entities.predicates.Predicate;
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
