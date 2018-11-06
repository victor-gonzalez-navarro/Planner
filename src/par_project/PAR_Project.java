/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import par_project.entities.FerryPlanner;
import par_project.entities.items.Car;
import par_project.entities.predicates.Predicate;
import par_project.entities.states.State;
import par_project.utils.Constants;
import static par_project.utils.Functions.readInput;

/**
 *
 * @author alarca_94
 */
public class PAR_Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        // TODO code application logic here
        long startTime = System.currentTimeMillis();
        Map<String, List<String>> statement = readInput(Constants.INPUT_PATH + Constants.INPUT_FILE_NAME);
        
        State initState = new State();
        int counter = 0;
        for (String value : statement.get(Constants.INIT_STATE)){
            if (!value.startsWith(Constants.NUM_LINES_EMPTY)){
                initState.addPredicate(Predicate.CreatePredicate(value));
            }
            if (value.startsWith(Constants.FIRST_DOCK)){
                counter++;
            }
        }
        
        State targetState = new State();
        for (String value : statement.get(Constants.TARGET_STATE)){
            targetState.addPredicate(Predicate.CreatePredicate(value));
        }
        
        ArrayList<Car> cars = new ArrayList<>();
        for (String value : statement.get(Constants.CARS)){
            cars.add(new Car(value));
        }
        
        int numMaxLines = Integer.parseInt(statement.get(Constants.NUM_MAX_LINES).get(0)) - counter;
        int numMaxCars = Integer.parseInt(statement.get(Constants.NUM_MAX_CARS).get(0));
        
        FerryPlanner planner = new FerryPlanner(initState, targetState, cars, numMaxLines, numMaxCars);
        
        planner.solveProblem();
        long finishTime = System.currentTimeMillis();
        System.out.println("Time of execution: " + String.valueOf((finishTime-startTime)/1000.0));
    }
    
}
