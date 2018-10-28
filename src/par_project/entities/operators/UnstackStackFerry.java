/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.operators;

import par_project.entities.items.Car;
import par_project.entities.predicates.FirstDock;
import par_project.entities.predicates.FreeLine;
import par_project.entities.predicates.LastFerry;
import par_project.entities.predicates.NextToDock;
import par_project.entities.predicates.NextToFerry;
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class UnstackStackFerry extends Operator{
    private Car z, y;
    
    public UnstackStackFerry (Car x, Car z, Car y){
        super(x);
        
        this.z = z;
        this.y = y;
        
        operatorName = Constants.UNSTACK_STACK_FERRY;
        
        precs_l.add(new FirstDock(x));
        precs_l.add(new LastFerry(y));
        precs_l.add(new NextToDock(z,x));
        
        add_l.add(new FreeLine(z));
        add_l.add(new LastFerry(x));
        add_l.add(new FirstDock(z));
        add_l.add(new NextToFerry(x,y));
        
        del_l.add(new FirstDock(x));
        del_l.add(new LastFerry(y));
        del_l.add(new NextToDock(z,x));
        del_l.add(new FreeLine(x));
    }

    @Override
    public void setXCar(Car x) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof FirstDock){
                precs_l.get(i).setCar(x, 0);
            } else if (precs_l.get(i) instanceof NextToDock){
                precs_l.get(i).setCar(x, 1);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof LastFerry || add_l.get(i) instanceof NextToFerry){
                add_l.get(i).setCar(x, 0);
            }
        }
        
        for (int i = 0; i < del_l.size(); i++){
            if (del_l.get(i) instanceof NextToDock){
                del_l.get(i).setCar(x, 1);
            } else if (del_l.get(i) instanceof FreeLine || del_l.get(i) instanceof FirstDock){
                del_l.get(i).setCar(x, 0);
            }
        }
    }

    @Override
    public void setYCar(Car y) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof LastFerry){
                precs_l.get(i).setCar(y, 0);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof NextToFerry){
                add_l.get(i).setCar(y, 1);
            }
        }
        
        for (int i = 0; i < del_l.size(); i++){
            if (del_l.get(i) instanceof LastFerry){
                del_l.get(i).setCar(y, 0);
            }
        }
    }

    @Override
    public void setZCar(Car z) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof NextToDock){
                precs_l.get(i).setCar(z, 0);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof FreeLine || add_l.get(i) instanceof FirstDock){
                add_l.get(i).setCar(z, 0);
            }
        }
        
        for (int i = 0; i < del_l.size(); i++){
            if (del_l.get(i) instanceof NextToDock){
                del_l.get(i).setCar(z, 0);
            }
        }
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + "," + z.identifier + "," + y.identifier + ")";
    }
}
