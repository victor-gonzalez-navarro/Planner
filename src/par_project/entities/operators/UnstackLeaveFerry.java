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
import par_project.entities.predicates.LastFerry;
import par_project.entities.predicates.NextToDock;
import par_project.entities.predicates.NumLinesEmpty;
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class UnstackLeaveFerry extends Operator{
    private Car z;
    
    public UnstackLeaveFerry (Car x, Car z){
        super(x);
        
        this.z = z;
        
        operatorName = Constants.UNSTACK_LEAVE_FERRY;
        
        precs_l.add(new FirstDock(x));
        precs_l.add(new NextToDock(z,x));
        
        add_l.add(new FirstFerry(x));
        add_l.add(new FreeLine(z));
        add_l.add(new FirstDock(z));
        
        add2_l.add(new LastFerry(x));
        
        del_l.add(new FirstDock(x));
        del_l.add(new NextToDock(z,x));
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
            if (add_l.get(i) instanceof FirstFerry){
                add_l.get(i).setCar(x, 0);
            }      
        }
        
        for (int i = 0; i < add2_l.size(); i++){
            add2_l.get(i).setCar(x, 0);   
        }
        
        for (int i = 0; i < del_l.size(); i++){
            if (del_l.get(i) instanceof FirstDock){
                del_l.get(i).setCar(x, 0);
            } else {
                del_l.get(i).setCar(x, 1);
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
        return operatorName + "(" + x.identifier + "," + z.identifier + ")";
    }
}
