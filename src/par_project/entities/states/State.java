/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.states;

import par_project.entities.predicates.Predicate;
import java.util.ArrayList;
import java.util.List;

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
    
    public State copy (){
        State out = new State();
        for (Predicate pred : this.getPredicates()){
            out.addPredicate(pred);
        }
        return out;
    }
}
