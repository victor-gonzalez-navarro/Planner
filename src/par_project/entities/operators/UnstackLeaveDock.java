/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.operators;

import par_project.entities.items.Car;
import par_project.entities.predicates.FirstDock;
import par_project.entities.predicates.FreeLine;
import par_project.entities.predicates.NextToDock;
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
        
        precs_l.add(new FirstDock(x));
        precs_l.add(new NumLinesEmpty(2));
        precs_l.add(new NextToDock(z,x));
        
        add_l.add(new FirstDock(z));
        add_l.add(new NumLinesEmpty(-1));
        add_l.add(new FreeLine(x));
        add_l.add(new FreeLine(z));
        
        del_l.add(new NextToDock(z,x));
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + "," + z.identifier + ")";
    }
}
