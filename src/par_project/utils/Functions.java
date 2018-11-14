package par_project.utils;

import par_project.entities.items.Car;
import par_project.entities.operators.Operator;
import par_project.entities.predicates.Predicate;
import par_project.entities.states.State;

import java.io.*;
import java.util.*;

/**
 * Functions Class contain several global functions for reading input files, write output files and calculate the
 * argument that contains the maximum value.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class Functions {

    /**
     * Function to read an input file and transform the statement into a map of item name, item value
     *
     * @param pathToFile
     * @return statement
     */
    public static Map<String, List<String>> readInput (String pathToFile) {
        Map<String, List<String>> statement = new HashMap<String, List<String>>();
        
        try {
            FileReader in = new FileReader(pathToFile);
            BufferedReader br = new BufferedReader(in);
            String line = "";
            String[] parts = new String[2];                 // Parts will contain pairs such as [NumLines, 3]
            String[] items;                                 // Items will contain the separated content of parts[1]
            
            while ((line = br.readLine()) != null){
                line = line.replace(";", "");
                parts = line.split("=");
                statement.put(parts[0], new ArrayList<String>());
                             
                if (parts[1].indexOf(".") != -1){
                    items = parts[1].split("\\.");
                    for (int i = 0; i < items.length; i++){
                        statement.get(parts[0]).add(items[i]);
                    }
                } else {
                    statement.get(parts[0]).add(parts[1]);
                }
            }
            
        } catch (IOException e){
            e.printStackTrace();
        }
        
        return statement;
    }

    /**
     * WriteOutput is responsible for writing the output file
     *
     * @param pathToFile
     * @param stepsToGoal
     * @param n_ops_states
     */
    public static void writeOutput(String pathToFile, List<Operator> stepsToGoal, int[] n_ops_states,
                                   State curr_state, String fail_reason) {
        FileWriter fw;
        StringBuilder sb = new StringBuilder();
        try {
            fw = new FileWriter(new File(pathToFile));

            sb.append("Number of Operators of the plan: ");
            sb.append(String.valueOf(n_ops_states[0]));
            sb.append(System.lineSeparator());
            sb.append("Number of States Generated to solve the problem: ");
            sb.append(String.valueOf(n_ops_states[1]));
            sb.append(System.lineSeparator());
            sb.append("Sequence of Operators: ");
            for (Operator op : stepsToGoal){
                sb.append(op.toString() + ", ");
            }
            if (stepsToGoal.size() > 0) {
                sb.setLength(sb.length() - 2);
            }

            if (!fail_reason.isEmpty()){
                sb.append(System.lineSeparator());
                sb.append("-----------------------------");
                sb.append(System.lineSeparator());
                sb.append("Current State: ");
                sb.append(curr_state.toString());
                sb.append(System.lineSeparator());
                sb.append("Reason of Failure: ");
                sb.append(fail_reason);
                sb.append(System.lineSeparator());
                sb.append("-----------------------------");
            }

            fw.write(sb.toString());
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ArgMax will return the index where the maximum value is located in the input array
     *
     * @param ints
     * @return idx
     */
    public static int argMax(ArrayList<Integer> ints, ArrayList<Car> cars, State curr_state){
        int maxValue = -1;
        int idx = 0;
        for (int i = 0; i < ints.size(); i++) {
            if (ints.get(i) > maxValue) {
                maxValue = ints.get(i);
                idx = i;
            } else if (ints.get(i) == maxValue){
                int cars_b1 = curr_state.carsBehind(cars.get(idx), Constants.DOCK);
                int cars_b2 = curr_state.carsBehind(cars.get(i), Constants.DOCK);
                if (cars_b1 < cars_b2){
                    maxValue = ints.get(i);
                    idx = i;
                }
            }
        }
        return idx;
    }

    /**
     * Drawing draws the Stack Elements in the console
     * @param stack
     */
    public static void drawing(Deque<Object> stack){
        System.out.println("\n\n");
        ArrayList list = new ArrayList(stack);
        for (int i = list.size()-1; i >= 0; i--){
            if (list.get(i) instanceof Predicate || list.get(i) instanceof Operator){
            System.out.println(list.get(i).toString());
            System.out.println("-----------------------------");
            } else {
                System.out.println("List of Predicates");
                System.out.println("-----------------------------");
            }
        }
    }

    /**
     * DifferenceCars return the car identifiers that are in cars1 but are not in cars2
     * @param cars1
     * @param cars2
     * @return
     */
    public static String differenceCars(ArrayList<Car> cars1, ArrayList<String> cars2){
        boolean found = false;
        StringBuilder sb = new StringBuilder();

        for (Car car1 : cars1){
            for (String car2 : cars2){
                if (car1.identifier.equals(car2)){
                    found = true;
                    break;
                }
            }
            if (!found) {
                sb.append(car1.identifier);
                sb.append(", ");
            }
        }

        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }

        return sb.toString();
    }
}
