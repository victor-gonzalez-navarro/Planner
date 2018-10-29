/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.predicates;

import par_project.utils.Constants;

/**
 *
 * @author alarca_94
 */
public class NumLinesEmpty extends Predicate {
    public int n;

    public NumLinesEmpty(int n) {
        this.predicateName = Constants.NUM_LINES_EMPTY;
        this.n = n;
    }
    
    @Override
    public String toString(){
        return predicateName + "(" + String.valueOf(n) + ")";
    }
}
