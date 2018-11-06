/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.utils;

import par_project.entities.predicates.Predicate;
import par_project.entities.operators.Operator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author alarca_94
 */
public class Functions {
    public static Map<String, List<String>> readInput (String pathToFile) {
        Map<String, List<String>> statement = new HashMap<String, List<String>>();
        
        try {
            FileReader in = new FileReader(pathToFile);
            BufferedReader br = new BufferedReader(in);
            String line = "";
            String[] parts = new String[2];
            String[] items;
            
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
}
