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
    private Car y;
    
    public PickStackFerry (Car x, Car y){
        super(x);
        
        this.y = y;
        
        operatorName = Constants.PICK_STACK_FERRY;
        
        precs_l.add(new FirstDock(x));
        precs_l.add(new LastDock(x));
        precs_l.add(new LastFerry(y));
        
        add_l.add(new NextToFerry(x, y));
        add_l.add(new NumLinesEmpty(1));
        
        add2_l.add(new LastFerry(x));
        
        del_l.add(new FirstDock(x));
        del_l.add(new LastDock(x));
        del_l.add(new FreeLine(x));
    }
    
    @Override
    public String toString(){
        return operatorName + "(" + x.identifier + "," + y.identifier + ")";
    }
}
