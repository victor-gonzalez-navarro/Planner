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
public class NextToDock extends Predicate{
    private Car car1;
    private Car car2;

    public NextToDock(Car car1, Car car2) {
        this.predicateName = Constants.NEXT_TO_DOCK;
        this.car1 = car1;
        this.car2 = car2;
    }

    public Car firstCar(Car c) {
        if (car2.equals(c)){
            return car1;
        } else {
            return null;
        }
    }
    
    @Override
    public boolean isInstantiated() {
        return (car1.isInstantiated() && car2.isInstantiated());
    }
    
    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(car1);
        cars.add(car2);
        return cars;
    }
    
    @Override
    public String toString(){
        return predicateName + "(" + car1.identifier + "," + car2.identifier + ")";
    }
}
