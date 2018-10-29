/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.operators;

import par_project.entities.items.Car;
import par_project.entities.predicates.FirstDock;
import par_project.entities.predicates.FreeLine;
import par_project.entities.predicates.LastDock;
import par_project.entities.predicates.NextToDock;
import par_project.entities.predicates.NextToFerry;
import par_project.entities.predicates.NumLinesEmpty;
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class UnstackLeaveDock extends Operator{
    private Car z;
    
    public UnstackLeaveDock (Car x, Car z){
        super(x);
        
        this.z = z;
        
        operatorName = Constants.UNSTACK_LEAVE_DOCK;
        
        precs_l.add(new FirstDock(this.x));
        precs_l.add(new NumLinesEmpty(2));
        precs_l.add(new NextToDock(this.z,this.x));
        
        add_l.add(new FirstDock(this.z));
        add_l.add(new NumLinesEmpty(-1));
        add_l.add(new FreeLine(this.x));
        add_l.add(new FreeLine(this.z));
        add_l.add(new LastDock(this.x));
        
        del_l.add(new NextToDock(this.z,this.x));
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
            if (add_l.get(i) instanceof FreeLine && add_l.get(i).getXCar().equals(x)){
                add_l.get(i).setCar(x, 0);
            } else if (add_l.get(i) instanceof LastDock){
                add_l.get(i).setCar(x, 0);
            }
        }
        
        for (int i = 0; i < del_l.size(); i++){
            del_l.get(i).setCar(x, 1);
        }
        
        this.x = x;
    }

    @Override
    public void setZCar(Car z) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof NextToDock){
                precs_l.get(i).setCar(z, 0);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof FirstDock){
                add_l.get(i).setCar(z, 0);
            } else if (add_l.get(i) instanceof FreeLine) {
                if (add_l.get(i).getXCar().equals(z)){
                    add_l.get(i).setCar(z, 0);
                }
            }     
        }
        
        for (int i = 0; i < del_l.size(); i++){
            del_l.get(i).setCar(z, 0);
        }
        
        this.z = z;
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + "," + z.identifier + ")";
    }
}
