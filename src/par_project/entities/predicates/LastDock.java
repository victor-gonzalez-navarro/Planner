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
public class LastDock extends Predicate {
    private Car x;
    
    public LastDock(Car x) {
        this.predicateName = Constants.LAST_DOCK;
        this.x = x;
    }
    
    @Override
    public boolean isInstantiated() {
        return x.isInstantiated();
    }
    
    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(x);
        return cars;
    }
    
    @Override
    public String toString(){
        return predicateName + "(" + x.identifier + ")";
    }
    
    @Override
    public Car getXCar (){
        return x;
    }
    
    @Override
    public void setCar (Car car, int idx){
        this.x = car;
    }
}
