/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.entities.items;

/**
 *
 * @author alarca_94
 */
public class Car {
    public String identifier;
    
    public Car (String name){
        identifier = name;
    }
    
    public boolean isInstantiated (){
        if (identifier.equals("X") || identifier.equals("Y") || identifier.equals("Z")) {
            return false;
        }
        return true;
    }
}
