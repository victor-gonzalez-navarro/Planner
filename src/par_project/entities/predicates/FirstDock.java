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
public class FirstDock extends Predicate {
    private Car car;

    public FirstDock(Car car) {
        this.predicateName = Constants.FIRST_DOCK;
        this.car = car;
    }

    @Override
    public boolean isInstantiated() {
        return car.isInstantiated();
    }
    
    public Car getFirstCar (){
        return car;
    }

    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(car);
        return cars;
    }
    
    @Override
    public String toString(){
        return predicateName + "(" + car.identifier + ")";
    }
}
