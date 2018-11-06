/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.predicates;

import par_project.utils.Constants;

/**
 * NumLinesEmpty Class is a special Predicate that is used to update the global variable based on its parameter.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class NumLinesEmpty extends Predicate {
    public int n;       // Possible Values: -1 [subtract 1], 1 [add 1], 2 [Check that numEmptyLines > 0]

    public NumLinesEmpty(int n) {
        this.predicateName = Constants.NUM_LINES_EMPTY;
        this.n = n;
    }
    
    @Override
    public String toString(){
        return predicateName + "(" + String.valueOf(n) + ")";
    }
}
