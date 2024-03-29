/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.items;

import par_project.utils.Constants;

/**
 * Car Class contains an identifier which allow to identify the Car and detect whether it is instantiated or not.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class Car {
    public String identifier;
    
    public Car (String name){
        identifier = name;
    }
    
    public boolean isInstantiated (){
        if (identifier.equals(Constants.X_IDENTIFIER) ||
                identifier.equals(Constants.Y_IDENTIFIER) ||
                identifier.equals(Constants.Z_IDENTIFIER)) {
            return false;
        }
        return true;
    }
}
