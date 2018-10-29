/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.predicates;

import java.util.ArrayList;
import par_project.entities.items.Car;
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class NextToFerry extends Predicate {
    private Car x;
    private Car z;

    public NextToFerry(Car z, Car x) {
        this.predicateName = Constants.NEXT_TO_FERRY;
        this.z = z;
        this.x = x;
    }
    
    @Override
    public boolean isInstantiated() {
        return (x.isInstantiated() && z.isInstantiated());
    }
    
    @Override
    public String toString(){
        return predicateName + "(" + z.identifier + "," + x.identifier + ")";
    }
    
    @Override
    public Car getXCar (){
        return x;
    }
    
    @Override
    public void setCar (Car car, int idx){
        if (idx == 0){
            this.z = car;
        } else {
            this.x = car;
        }
    }
    
    @Override
    public void setCars (ArrayList<Car> listcars) {
        this.z.identifier = listcars.get(0).identifier;
        this.x.identifier = listcars.get(1).identifier;
    }
}
