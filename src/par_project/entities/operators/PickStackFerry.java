/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.operators;

import par_project.entities.items.Car;
import par_project.entities.predicates.FirstDock;
import par_project.entities.predicates.FirstFerry;
import par_project.entities.predicates.FreeLine;
import par_project.entities.predicates.LastDock;
import par_project.entities.predicates.LastFerry;
import par_project.entities.predicates.NextToFerry;
import par_project.entities.predicates.NumLinesEmpty;
import par_project.utils.Constants;

import java.util.ArrayList;

/**
 * PickStackFerry Class is an Operator that picks Car X from the Dock and puts it behind Car Z in the Ferry.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class PickStackFerry extends Operator{
    private Car z;
    
    public PickStackFerry (Car x, Car z, ArrayList<Car> cars){
        super(x, cars);
        
        this.z = z;
        
        operatorName = Constants.PICK_STACK_FERRY;

        this.available_cars.remove(x.identifier);
        this.available_cars.remove(z.identifier);

        precs_l.add(new LastDock(this.x, this.available_cars));
        precs_l.add(new FirstDock(this.x, this.available_cars));
        precs_l.add(new LastFerry(this.z, this.available_cars));
        
        add_l.add(new NextToFerry(this.x, this.z, this.available_cars));
        add_l.add(new NumLinesEmpty(1));
        add_l.add(new LastFerry(this.x, this.available_cars));
        
        del_l.add(new FirstDock(this.x, this.available_cars));
        del_l.add(new LastDock(this.x, this.available_cars));
        del_l.add(new FreeLine(this.x, this.available_cars));
        del_l.add(new LastFerry(this.z, this.available_cars));
    }

    @Override
    public void setXCar(Car x) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof FirstDock || 
                    precs_l.get(i) instanceof LastDock){
                precs_l.get(i).setCar(x, 0);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            add_l.get(i).setCar(x, 0); 
        }
        
        for (int i = 0; i < del_l.size(); i++){
            if (!(del_l.get(i) instanceof LastFerry)) {
                del_l.get(i).setCar(x, 0);
            }
        }
        
        this.x = x;
        this.available_cars.remove(x.identifier);
    }

    @Override
    public void setZCar(Car z) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof LastFerry){
                precs_l.get(i).setCar(z, 0);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof NextToFerry) {
                add_l.get(i).setCar(z, 1);
            }        
        }

        for (int i = 0; i < del_l.size(); i++){
            if (del_l.get(i) instanceof LastFerry) {
                del_l.get(i).setCar(z, 0);
            }
        }
        
        this.z = z;
        this.available_cars.remove(z.identifier);
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + "," + z.identifier + ")";
    }
}
