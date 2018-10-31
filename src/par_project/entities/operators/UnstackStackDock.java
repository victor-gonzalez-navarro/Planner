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
import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class UnstackStackDock extends Operator{
    private Car z, y;
    
    public UnstackStackDock (Car x, Car z, Car y){
        super(x);
        
        operatorName = Constants.UNSTACK_STACK_DOCK;
        
        this.z = z;
        this.y = y;
        
        precs_l.add(new FirstDock(this.x));
        precs_l.add(new FirstDock(this.y));
        precs_l.add(new NextToDock(this.z,this.x));
        precs_l.add(new FreeLine(y));
        
        add_l.add(new FreeLine(this.z));
        add_l.add(new FreeLine(this.x));
        add_l.add(new FirstDock(this.z));
        add_l.add(new NextToDock(this.y,this.x));
        
        del_l.add(new FirstDock(this.y));
        del_l.add(new FreeLine(this.y));
        del_l.add(new NextToDock(this.z,this.x));
        del_l.add(new FreeLine(this.x));
    }
    
    public Car getThirdCar (){
        return y;
    }

    @Override
    public void setXCar(Car x) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof FirstDock){
                if (precs_l.get(i).getXCar().identifier.equals(Constants.X_IDENTIFIER)){
                    precs_l.get(i).setCar(x, 0);
                }
            } else if (precs_l.get(i) instanceof NextToDock){
                precs_l.get(i).setCar(x, 1);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof FreeLine){
                if (add_l.get(i).getXCar().identifier.equals(Constants.X_IDENTIFIER)){
                    add_l.get(i).setCar(x, 0);
                }
            } else if (add_l.get(i) instanceof NextToDock){
                add_l.get(i).setCar(x, 1);
            }
        }
        
        for (int i = 0; i < del_l.size(); i++){
            if (del_l.get(i) instanceof NextToDock){
                del_l.get(i).setCar(x, 1);
            } else if (del_l.get(i) instanceof FreeLine){
                if (del_l.get(i).getXCar().identifier.equals(Constants.X_IDENTIFIER)){
                    del_l.get(i).setCar(x, 0);
                }
            }
        }
        
        this.x = x;
    }

    @Override
    public void setYCar(Car y) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof FirstDock){
                if (precs_l.get(i).getXCar().identifier.equals(Constants.Y_IDENTIFIER)){
                    precs_l.get(i).setCar(y, 0);
                }
            } else if (precs_l.get(i) instanceof FreeLine){
                precs_l.get(i).setCar(y, 0);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof NextToDock){
                add_l.get(i).setCar(y, 0);
            }
        }
        
        for (int i = 0; i < del_l.size(); i++){
            if (del_l.get(i) instanceof FirstDock){
                del_l.get(i).setCar(y, 1);
            } else if (del_l.get(i) instanceof FreeLine){
                if (del_l.get(i).getXCar().identifier.equals(Constants.Y_IDENTIFIER)){
                    del_l.get(i).setCar(y, 0);
                }
            }
        }
        
        this.y = y;
    }

    @Override
    public void setZCar(Car z) {
        for (int i = 0; i < precs_l.size(); i++){
            if (precs_l.get(i) instanceof NextToDock){
                precs_l.get(i).setCar(z, 0);
            }
        }
        
        for (int i = 0; i < add_l.size(); i++){
            if (add_l.get(i) instanceof FreeLine){
                if (add_l.get(i).getXCar().identifier.equals(Constants.Z_IDENTIFIER)){
                    add_l.get(i).setCar(z, 0);
                }
            } else if (add_l.get(i) instanceof FirstDock){
                add_l.get(i).setCar(z, 0);
            }
        }
        
        for (int i = 0; i < del_l.size(); i++){
            if (del_l.get(i) instanceof NextToDock){
                del_l.get(i).setCar(z, 0);
            }
        }
        
        this.z = z;
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + "," + z.identifier + "," + y.identifier + ")";
    }
}
