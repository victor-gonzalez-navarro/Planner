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
    public String toString(){
        return operatorName + "(" + x.identifier + "," + z.identifier + "," + y.identifier + ")";
    }
}
