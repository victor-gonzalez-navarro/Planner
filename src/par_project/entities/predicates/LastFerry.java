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
 * LastFerry Class is a Predicate used to identify the last car in a line of the Ferry.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class LastFerry extends Predicate {
    private Car x;
    
    public LastFerry(Car x) {
        this.predicateName = Constants.LAST_FERRY;
        this.x = x;
    }

    public LastFerry(Car x, ArrayList<String> available_cars) {
        this.predicateName = Constants.LAST_FERRY;
        this.x = x;
        this.available_cars = available_cars;
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
    
    @Override
    public void setCars (ArrayList<Car> listCars) {
        if (available_cars.contains(listCars.get(0).identifier)) {
            this.x.identifier = listCars.get(0).identifier;
            this.available_cars.remove(x.identifier);
        }
    }
}
