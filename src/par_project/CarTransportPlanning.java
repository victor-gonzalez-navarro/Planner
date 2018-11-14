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
 * CarTransportPlanning Class is the main application class that reads the file, initializes the general
 * variables and call the planner solver function.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class CarTransportPlanning {
    public final static String INPUT_FILE_NAME  = "Test3.txt";

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        Map<String, List<String>> statement = readInput(Constants.INPUT_PATH + INPUT_FILE_NAME);

        // Initialize the initial state by adding the initial predicates to it
        State initState = new State();
        int counter_firsts = 0;
        int counter_lasts = 0;
        for (String value : statement.get(Constants.INIT_STATE)){
            if (!value.startsWith(Constants.NUM_LINES_EMPTY)){
                initState.addPredicate(Predicate.CreatePredicate(value));
            }
            if (value.startsWith(Constants.FIRST_DOCK)){
                counter_firsts++;
            } else if (value.startsWith(Constants.LAST_DOCK)){
                counter_lasts++;
            }
        }

        if (counter_firsts == counter_lasts) {

            int numMaxLines = Integer.parseInt(statement.get(Constants.NUM_MAX_LINES).get(0));
            int numMaxCars = Integer.parseInt(statement.get(Constants.NUM_MAX_CARS).get(0));

            // The number of empty lines are equal to the maximum number of lines minus the counter of FirstDock in the
            // init_state
            int numLinesEmpty = numMaxLines - counter_firsts;

            counter_firsts = 0;
            counter_lasts = 0;

            // Initialize the target state by adding the final predicates to it
            State targetState = new State();
            for (String value : statement.get(Constants.TARGET_STATE)) {
                targetState.addPredicate(Predicate.CreatePredicate(value));
                if (value.startsWith(Constants.FIRST_FERRY)){
                    counter_firsts++;
                } else if (value.startsWith(Constants.LAST_FERRY)){
                    counter_lasts++;
                }
            }

            if (counter_firsts == counter_lasts) {

                // Initialize the available cars by adding the blocks from the input file to it
                ArrayList<Car> cars = new ArrayList<>();
                for (String value : statement.get(Constants.CARS)) {
                    cars.add(new Car(value));
                }

                FerryPlanner planner = new FerryPlanner(initState, targetState, cars, numLinesEmpty, numMaxCars);

                // Solve the planning problem and write the output file
                planner.solveProblem();
            } else {
                System.out.println("Error in the input file: Number of LastFerries (" + String.valueOf(counter_lasts) +
                        ") is different than the number of FirstFerries (" + String.valueOf(counter_firsts) + ")");
            }
        } else {
            System.out.println("Error in the input file: Number of LastDocks (" + String.valueOf(counter_lasts) +
                    ") is different than the number of FirstDocks (" + String.valueOf(counter_firsts) + ")");
        }

        long finishTime = System.currentTimeMillis();
        System.out.println("Execution time: " + String.valueOf((finishTime-startTime)/1000.0));
    }
    
}
