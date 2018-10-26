/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.operators;

import java.util.ArrayList;
import java.util.List;
import par_project.entities.items.Car;
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
    
    protected String operatorName;
    
    protected Car x;
    
    public Operator (Car x){
        precs_l = new ArrayList<>();
        add_l   = new ArrayList<>();
        add2_l  = new ArrayList<>();
        del_l   = new ArrayList<>();
        
        this.x = x;
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
    
    public Car getFirstCar(){
        return x;
    }
}
