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
 * FreeLine Class is a Predicate used to identify a car that has a free spot in front.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class FreeLine extends Predicate {
    private Car x;

    public FreeLine(Car x) {
        this.predicateName = Constants.FREE_LINE;
        this.x = x;
    }

    public FreeLine(Car x, ArrayList<String> available_cars) {
        this.predicateName = Constants.FREE_LINE;
        this.x = x;
        this.available_cars = available_cars;
    }
    
    public Car getCar (){
        return x;
    }
    
    @Override
    public Car getXCar (){
        return x;
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
    public void setCar (Car car, int idx){
        this.x = car;
    }
    
    @Override
    public void setCars (ArrayList<Car> listCars) {
        if (available_cars.contains(listCars.get(0).identifier)) {
            this.x.identifier = listCars.get(0).identifier;
            this.available_cars.remove(x.identifier);
        }
    }
}
