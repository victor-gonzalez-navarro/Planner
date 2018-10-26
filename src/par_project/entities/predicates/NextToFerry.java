/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.predicates;

import par_project.entities.items.Car;
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class NextToFerry extends Predicate {
    private Car car1;
    private Car car2;

    public NextToFerry(Car car, Car car0) {
        this.predicateName = Constants.NEXT_TO_FERRY;
        this.car1 = car1;
        this.car2 = car2;
    }
    
}
