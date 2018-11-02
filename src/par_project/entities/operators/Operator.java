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
import par_project.entities.predicates.*;
import par_project.entities.states.State;
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class Operator{
    protected List<Predicate> precs_l;
    protected List<Predicate> add_l;
    protected List<Predicate> del_l;
    protected ArrayList<String> available_cars;
    
    private static Random rnd;
    
    
    protected String operatorName;
    
    protected Car x;

    public Operator (Car x, ArrayList<Car> cars){
        precs_l = new ArrayList<>();
        add_l   = new ArrayList<>();
        del_l   = new ArrayList<>();

        available_cars = new ArrayList<>();
        for (Car car : cars){
            available_cars.add(car.identifier);
        }
        
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

    private static List<Operator> getPossibleOperators(ArrayList<Car> cars){
        List<Operator> possibleOperators;
        Car X = new Car(Constants.X_IDENTIFIER);
        Car Y = new Car(Constants.Y_IDENTIFIER);
        Car Z = new Car(Constants.Z_IDENTIFIER);

        possibleOperators = new ArrayList<>();
        possibleOperators.add(new PickLeaveFerry(X, cars));
        possibleOperators.add(new PickStackFerry(X, Z, cars));
        possibleOperators.add(new UnstackLeaveDock(X, Z, cars));
        possibleOperators.add(new UnstackLeaveFerry(X, Z, cars));
        possibleOperators.add(new UnstackStackDock(X, Z, Y, cars));
        possibleOperators.add(new UnstackStackFerry(X, Z, Y, cars));

        return possibleOperators;
    }
    
    public static Operator searchAddPredicate (Predicate in_pred, State curr_state, int numLinesEmpty, ArrayList<Car> cars){
        ArrayList<Operator> matched_ops = new ArrayList<>();
 
        for (Operator op : getPossibleOperators(cars)){
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
            if (in_pred instanceof FirstDock){
                if (numLinesEmpty > 0){
                    for (Operator op : matched_ops){
                        if (op instanceof UnstackLeaveDock){
                            return op;
                        }
                    }
                } else {
                    for (Operator op : matched_ops){
                        if (op instanceof UnstackStackDock){
                            return op;
                        }
                    }
                }
            } else if (in_pred instanceof FirstFerry){
                if (curr_state.carsBehind(in_pred.getCars().get(0), Constants.DOCK) > 0){
                    for (Operator op : matched_ops){
                        if (op instanceof UnstackLeaveFerry){
                            return op;
                        }
                    }
                } else {
                    for (Operator op : matched_ops){
                        if (op instanceof PickLeaveFerry){
                            return op;
                        }
                    }
                }
            } else if (in_pred instanceof NextToFerry){
                if (curr_state.carsBehind(in_pred.getCars().get(0), Constants.DOCK) > 0){
                    for (Operator op : matched_ops){
                        if (op instanceof UnstackStackFerry){
                            return op;
                        }
                    }
                } else {
                    for (Operator op : matched_ops){
                        if (op instanceof PickStackFerry){
                            return op;
                        }
                    }
                }
            }
            return matched_ops.get(rnd.nextInt(matched_ops.size()));
        }
        //for (int i = 0; i < matched_ops.size(); i++){}
        
        return null;
    }
}
