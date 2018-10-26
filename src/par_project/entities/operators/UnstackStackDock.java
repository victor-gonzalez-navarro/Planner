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
        
        precs_l.add(new FirstDock(x));
        precs_l.add(new FirstDock(y));
        precs_l.add(new NextToDock(z,x));
        precs_l.add(new FreeLine(y));
        
        add_l.add(new FreeLine(z));
        add_l.add(new FreeLine(x));
        add_l.add(new FirstDock(z));
        add_l.add(new NextToDock(y,x));
        
        del_l.add(new FirstDock(y));
        del_l.add(new FreeLine(y));
        del_l.add(new NextToDock(z,x));
        del_l.add(new FreeLine(x));
    }
    
    public Car getThirdCar (){
        return y;
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + "," + z.identifier + "," + y.identifier + ")";
    }
}
