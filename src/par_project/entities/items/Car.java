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
    boolean instantiated;
    
    public Car (){
        instantiated = false;
    }
    
    public Car (String name){
        identifier = name;
        instantiated = true;
    }
    
    public boolean isInstantiated (){
        return instantiated;
    }
}
