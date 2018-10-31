/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import par_project.entities.items.Car;
import par_project.entities.predicates.FirstDock;
import par_project.entities.predicates.NumLinesEmpty;
import par_project.entities.predicates.Predicate;
import par_project.entities.states.State;
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class Operator {
    protected List<Predicate> precs_l;
    protected List<Predicate> add_l;
    protected List<Predicate> del_l;
    
    private static Random rnd;
    private static final List<Operator> POSSIBLE_OPERATORS;
    static {
        Car X = new Car(Constants.X_IDENTIFIER);
        Car Y = new Car(Constants.Y_IDENTIFIER);
        Car Z = new Car(Constants.Z_IDENTIFIER);
        
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
        del_l   = new ArrayList<>();
        
        this.x = x;
        
        rnd = new Random();
    }
    
    public List<Predicate> getPrecsList (){
        return precs_l;
    }
    
    public List<Predicate> getAddList (){
        return add_l;
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
    
    public static Operator searchAddPredicate (Predicate in_pred, State curr_state){
        ArrayList<Operator> matched_ops = new ArrayList<>();
 
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
                                case Constants.X_IDENTIFIER:
                                    op.setXCar(in_pred.getCars().get(i));
                                    break;
                                case Constants.Y_IDENTIFIER:
                                    op.setYCar(in_pred.getCars().get(i));
                                    break;
                                case Constants.Z_IDENTIFIER:
                                    op.setZCar(in_pred.getCars().get(i));
                                    break;
                                default:
                                    break;
                            }
                        }
                        //matched_ops.add(op);
                        return op;
                    }
                }
            }
        }
        
//        if (matched_ops.size() > 0){
//            return matched_ops.get(rnd.nextInt(matched_ops.size()));
//        }
        //for (int i = 0; i < matched_ops.size(); i++){}
        
        return null;
    }
}
