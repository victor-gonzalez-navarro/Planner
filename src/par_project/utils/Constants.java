/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package par_project.utils;

import static par_project.CarTransportPlanning.INPUT_FILE_NAME;

/**
 * Constants Class centralises the constant Strings.
 *
 * @author Alejandro Ariza & Víctor González
 */

public class Constants {
    public final static String INPUT_PATH       = "src/par_project/resources/input/";
    //public final static String INPUT_FILE_NAME  = "example1.txt";
    //public final static String INPUT_FILE_NAME  = "example2.txt";
    //public final static String INPUT_FILE_NAME  = "our_example3.txt";
    //public final static String INPUT_FILE_NAME  = "our_example4.txt";
    //public final static String INPUT_FILE_NAME  = "our_example5.txt";
    //public final static String INPUT_FILE_NAME  = "our_example6.txt";
    //public final static String INPUT_FILE_NAME  = "our_example7.txt";

    public final static String OUTPUT_PATH       = "src/par_project/resources/output/";
    public final static String OUTPUT_FILE_NAME  = "out_" + INPUT_FILE_NAME;
    
    public final static String INIT_STATE       = "InitialState";
    public final static String TARGET_STATE     = "GoalState";
    public final static String CARS             = "Blocks";
    public final static String NUM_MAX_LINES    = "NumLines";
    public final static String NUM_MAX_CARS     = "MaxColumns";
    
    public final static String FIRST_DOCK       = "FirstDock";
    public final static String FIRST_FERRY      = "FirstFerry";
    public final static String LAST_DOCK        = "LastDock";
    public final static String LAST_FERRY       = "LastFerry";
    public final static String NEXT_TO_DOCK     = "NextToDock";
    public final static String NEXT_TO_FERRY    = "NextToFerry";
    public final static String NUM_LINES_EMPTY  = "NumLinesEmpty";
    public final static String FREE_LINE        = "FreeLine";
    
    public final static String PICK_LEAVE_FERRY     = "PickLeaveFerry";
    public final static String PICK_STACK_FERRY     = "PickStackFerry";
    public final static String UNSTACK_LEAVE_DOCK   = "UnstackLeaveDock";
    public final static String UNSTACK_LEAVE_FERRY  = "UnstackLeaveFerry";
    public final static String UNSTACK_STACK_DOCK   = "UnstackStackDock";
    public final static String UNSTACK_STACK_FERRY  = "UnstackStackFerry";

    public final static String X_IDENTIFIER = "XXX";
    public final static String Y_IDENTIFIER = "YYY";
    public final static String Z_IDENTIFIER = "ZZZ";

    public final static String DOCK     = "DOCK";
    public final static String FERRY    = "FERRY";
}
