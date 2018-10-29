/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.operators;

import java.util.ArrayList;
import java.util.List;
import par_project.entities.items.Car;
import par_project.entities.predicates.FirstDock;
import par_project.entities.predicates.NumLinesEmpty;
import par_project.entities.predicates.Predicate;

/**
 *
 * @author alarca_94
 */
public class Operator {
    protected List<Predicate> precs_l;
    protected List<Predicate> add_l;
    // Just for the cases where LastFerry must not be included in the operators search
    protected List<Predicate> add2_l;
    protected List<Predicate> del_l;
    
    private static final List<Operator> POSSIBLE_OPERATORS;
    static {
        Car X = new Car("X");
        Car Y = new Car("Y");
        Car Z = new Car("Z");
        
        POSSIBLE_OPERATORS = new ArrayList<>();
        POSSIBLE_OPERATORS.add(new PickLeaveFerry(X));
        POSSIBLE_OPERATORS.add(new PickStackFerry(X, Z));
        POSSIBLE_OPERATORS.add(new UnstackLeaveDock(X, Z));
        POSSIBLE_OPERATORS.add(new UnstackLeaveFerry(X, Z));
        POSSIBLE_OPERATORS.add(new UnstackStackDock(X, Z, Y));
        POSSIBLE_OPERATORS.add(new UnstackStackFerry(X, Z, Y));
    }
    
    
    protected String operatorName;
    
    protected Car x;
    
    public Operator (Car x){
        precs_l = new ArrayList<>();
        add_l   = new ArrayList<>();
        add2_l  = new ArrayList<>();
        del_l   = new ArrayList<>();
        
        this.x = x;
    }
    
    public List<Predicate> getPrecsList (){
        return precs_l;
    }
    
    public List<Predicate> getAddList (){
        return add_l;
    }
    
    public List<Predicate> getAdd2List (){
        return add2_l;
    }
    
    public List<Predicate> getDelList (){
        return del_l;
    }
    
    public Car getFirstCar (){
        return x;
    }
    
    public void setXCar (Car x){
    }
    
    public void setYCar (Car y){
    }
    
    public void setZCar (Car z){
    }
    
    public static Operator searchAddPredicate (Predicate in_pred){
        for (Operator op : POSSIBLE_OPERATORS){
            for (Predicate pred : op.getAddList()){
                if (pred.getClass().equals(in_pred.getClass())){
                    if (pred instanceof NumLinesEmpty){
                        if (((NumLinesEmpty)pred).n == 1){
                            return op;
                        }
                    } else {
                        for (int i=0; i < pred.getCars().size(); i++){
                            switch (pred.getCars().get(i).identifier) {
                                case "X":
                                    op.setXCar(in_pred.getCars().get(i));
                                    break;
                                case "Y":
                                    op.setYCar(in_pred.getCars().get(i));
                                    break;
                                case "Z":
                                    op.setZCar(in_pred.getCars().get(i));
                                    break;
                                default:
                                    break;
                            }
                        }
                        return op;
                    }
                }
            }
        }
        return null;
    }
}
