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
 * Predicate Class contains a predicate name, a list of available cars for Operator purposes, a general Generator method
 * as well as getters and setters for their children.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class Predicate {
    protected String predicateName;
    ArrayList<String> available_cars;

    /**
     * Alternative Generator depending on the String it receives
     * @param predicatePlusCars --> e.g. FirstDock(A)
     * @return
     */
    public static Predicate CreatePredicate(String predicatePlusCars){
        Predicate pred = null;
        int parenthesisIdx = predicatePlusCars.indexOf("(");
        String predicateName = predicatePlusCars.substring(0, parenthesisIdx);
        String cars = predicatePlusCars.substring(parenthesisIdx + 1, predicatePlusCars.length() - 1);
        
        if (predicateName.equals(Constants.FIRST_FERRY)){
            pred = new FirstFerry(new Car(cars));
        } else if (predicateName.equals(Constants.LAST_FERRY)){
            pred = new LastFerry(new Car(cars));
        } else if (predicateName.equals(Constants.FIRST_DOCK)){
            pred = new FirstDock(new Car(cars));
        } else if (predicateName.equals(Constants.LAST_DOCK)){
            pred = new LastDock(new Car(cars));
        } else if (predicateName.equals(Constants.NEXT_TO_DOCK)){
            String[] car = cars.split(",");
            pred = new NextToDock(new Car(car[0]), new Car(car[1]));
        } else if (predicateName.equals(Constants.NEXT_TO_FERRY)){
            String[] car = cars.split(",");
            pred = new NextToFerry(new Car(car[0]), new Car(car[1]));
        } else if (predicateName.equals(Constants.FREE_LINE)){
            pred = new FreeLine(new Car(cars));
        }
        
        return pred;
    }
    
    public String getPredicateName (){
        return predicateName;
    }

    /**
     * IsInstantiated method returns true if all its cars are instantiated
     * @return
     */
    public boolean isInstantiated (){
        return true;
    }
    
    public ArrayList<Car> getCars (){
        return null;
    }
    
    public Car getXCar (){
        return null;
    }
    
    public String getCarIDs (){
        StringBuilder out = new StringBuilder();
        for (Car car : this.getCars()){
            out.append(car.identifier);
        }
        return out.toString();
    }
    
    public void setCar (Car car, int idx){}
    
    public void setCars (ArrayList<Car> listCars) { }

    /**
     * AreCarsAvailable is useful to know which cars are available for instantiation
     * @param cars
     * @return available
     */
    public boolean areCarsAvailable(ArrayList<Car> cars) {
        boolean available = false;
        for (Car car : cars){
            if (available_cars.contains(car.identifier)){
                available = true;
            }
        }
        return available;
    }
}
