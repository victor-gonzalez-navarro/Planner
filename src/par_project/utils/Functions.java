/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
