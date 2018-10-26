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

/**
 *
 * @author alarca_94
 */
public class UnstackLeaveDock extends Operator{
    
    public UnstackLeaveDock (Car x, Car z){
        super(x);
        
        precs_l.add(new FirstDock(x));
        precs_l.add(new NumLinesEmpty(2));
        precs_l.add(new NextToDock(z,x));
        
        add_l.add(new FirstDock(z));
        add_l.add(new NumLinesEmpty(-1));
        add_l.add(new FreeLine(x));
        add_l.add(new FreeLine(z));
        
        del_l.add(new NextToDock(z,x));
    }
}
