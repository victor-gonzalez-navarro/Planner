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
public class FreeLine extends Predicate {
    private Car car;

    public FreeLine(Car car) {
        this.predicateName = Constants.FREE_LINE;
        this.car = car;
    }
    
    public Car getCar (){
        return car;
    }
    
    @Override
    public boolean isInstantiated() {
        return car.isInstantiated();
    }
    
    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(car);
        return cars;
    }
}
