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
public class Operator implements Cloneable{
    protected List<Predicate> precs_l;
    protected List<Predicate> add_l;
    protected List<Predicate> del_l;
    
    private static Random rnd;
    
    
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

    private static List<Operator> getPossibleOperators(){
        List<Operator> possibleOperators;
        Car X = new Car(Constants.X_IDENTIFIER);
        Car Y = new Car(Constants.Y_IDENTIFIER);
        Car Z = new Car(Constants.Z_IDENTIFIER);

        possibleOperators = new ArrayList<>();
        possibleOperators.add(new PickLeaveFerry(X));
        possibleOperators.add(new PickStackFerry(X, Z));
        possibleOperators.add(new UnstackLeaveDock(X, Z));
        possibleOperators.add(new UnstackLeaveFerry(X, Z));
        possibleOperators.add(new UnstackStackDock(X, Z, Y));
        possibleOperators.add(new UnstackStackFerry(X, Z, Y));

        return possibleOperators;
    }
    
    public static Operator searchAddPredicate (Predicate in_pred, State curr_state){
        ArrayList<Operator> matched_ops = new ArrayList<>();
 
        for (Operator op : getPossibleOperators()){
            for (Predicate pred : op.getAddList()){
                if (pred.getClass().equals(in_pred.getClass())){
                    if (pred instanceof NumLinesEmpty){
                        if (((NumLinesEmpty)pred).n == 1){
                            return op;
                        }
                    } else {
                        for (int i=0; i < pred.getCars().size(); i++) {
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
                        //return op;
                        matched_ops.add(op);

                    }
                }
            }
        }
        
        if (matched_ops.size() > 0){
            return matched_ops.get(rnd.nextInt(matched_ops.size()));
        }
        //for (int i = 0; i < matched_ops.size(); i++){}
        
        return null;
    }
}
