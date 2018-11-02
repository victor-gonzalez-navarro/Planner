/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.operators;

import java.util.ArrayList;
import par_project.entities.items.Car;
import par_project.entities.predicates.FirstDock;
import par_project.entities.predicates.FirstFerry;
import par_project.entities.predicates.FreeLine;
import par_project.entities.predicates.LastDock;
import par_project.entities.predicates.LastFerry;
import par_project.entities.predicates.NumLinesEmpty;
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class PickLeaveFerry extends Operator{
    
    public PickLeaveFerry (Car x, ArrayList<Car> cars){
        super(x, cars);
        
        operatorName = Constants.PICK_LEAVE_FERRY;

        this.available_cars.remove(x.identifier);

        precs_l.add(new LastDock(this.x, this.available_cars));
        precs_l.add(new FirstDock(this.x, this.available_cars));
        
        add_l.add(new FirstFerry(this.x, this.available_cars));
        add_l.add(new NumLinesEmpty(1));
        add_l.add(new LastFerry(this.x, this.available_cars));
        
        del_l.add(new FirstDock(this.x, this.available_cars));
        del_l.add(new LastDock(this.x, this.available_cars));
        del_l.add(new FreeLine(this.x, this.available_cars));
    }

    @Override
    public void setXCar(Car x) {
        for (int i = 0; i < precs_l.size(); i++){
            precs_l.get(i).setCar(x, 0);
        }
        
        for (int i = 0; i < add_l.size(); i++){
            add_l.get(i).setCar(x, 0);
        }
        
        for (int i = 0; i < del_l.size(); i++){
            del_l.get(i).setCar(x, 0);
        }
        
        this.x = x;
        this.available_cars.remove(x.identifier);
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + ")";
    }
}
