package par_project.utils;

import par_project.entities.operators.Operator;
import par_project.entities.states.State;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static void writeOutput(String pathToFile, List<Operator> stepsToGoal, int[] n_ops_states//,
                                   //ArrayList<State> curr_state, String fail_reason
                                   ) {
        FileWriter fw;
        StringBuilder sb = new StringBuilder();
        try {
            fw = new FileWriter(new File(pathToFile));

            sb.append(String.valueOf(n_ops_states[0]));
            sb.append(System.lineSeparator());
            sb.append(String.valueOf(n_ops_states[1]));
            sb.append(System.lineSeparator());
            for (Operator op : stepsToGoal){
                sb.append(op.toString() + ", ");
            }
            sb.setLength(sb.length()-2);

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
    public static int argMax(ArrayList<Integer> ints){
        int maxValue = -1;
        int idx = 0;
        for (int i = 0; i < ints.size(); i++) {
            if (ints.get(i) > maxValue) {
                maxValue = ints.get(i);
                idx = i;
            }
        }
        return idx;
    }
}
