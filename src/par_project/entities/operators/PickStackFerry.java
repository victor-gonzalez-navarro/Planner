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

/**
 *
 * @author alarca_94
 */
public class PickStackFerry extends Operator{
    private Car z;
    
    public PickStackFerry (Car x, Car z){
        super(x);
        
        this.z = z;
        
        operatorName = Constants.PICK_STACK_FERRY;
        
        precs_l.add(new FirstDock(x));
        precs_l.add(new LastDock(x));
        precs_l.add(new LastFerry(z));
        
        add_l.add(new NextToFerry(x, z));
        add_l.add(new NumLinesEmpty(1));
        
        add2_l.add(new LastFerry(x));
        
        del_l.add(new FirstDock(x));
        del_l.add(new LastDock(x));
        del_l.add(new FreeLine(x));
    }

    @Override
    public void setXCar(Car x) {
        super.setXCar(x);
        
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof FirstDock || 
                    precs_l.get(i) instanceof LastDock){
                precs_l.get(i).setCar(x, 0);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof NextToFerry) {
                add_l.get(i).setCar(x, 0);
            }        
        }
        
        for (int i = 0; i < add2_l.size(); i++){
            add2_l.get(i).setCar(x, 0);
        }
        
        for (int i = 0; i < del_l.size(); i++){
            del_l.get(i).setCar(x, 0);
        }
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
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + "," + z.identifier + ")";
    }
}
