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
 * NextToFerry Class is a Predicate used to identify adjacent cars in the Ferry.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class NextToFerry extends Predicate {
    private Car x;
    private Car z;

    public NextToFerry(Car z, Car x) {
        this.predicateName = Constants.NEXT_TO_FERRY;
        this.z = z;
        this.x = x;
    }

    public NextToFerry(Car z, Car x, ArrayList<String> available_cars) {
        this.predicateName = Constants.NEXT_TO_FERRY;
        this.z = z;
        this.x = x;
        this.available_cars = available_cars;
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
    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<Car>();
        cars.add(z);
        cars.add(x);
        return cars;
    }
    
    @Override
    public void setCar (Car car, int idx){
        if (idx == 0) {
            this.z = car;
        } else {
            this.x = car;
        }
    } 
   
    @Override
    public void setCars (ArrayList<Car> listCars) {
        if (available_cars.contains(listCars.get(0).identifier)) {
            this.z.identifier = listCars.get(0).identifier;
            this.available_cars.remove(z.identifier);
        }
        if (available_cars.contains(listCars.get(1).identifier)) {
            this.x.identifier = listCars.get(1).identifier;
            this.available_cars.remove(x.identifier);
        }
    }
}
