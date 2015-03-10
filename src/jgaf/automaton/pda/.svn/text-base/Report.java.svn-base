/*
 * Report.java
 *
 * Created on 14. únor 2008, 15:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.LinkedList;

/**
 *
 * @author hanis
 */
public class Report {    

    public static final int UNDO = 0;
    public static final int REDO = 1;
    public static final int TRANS = 2;
    
   // private String automata;
    //private int start;
    private String startConf;
   // private StringBuilder process;
    
    private LinkedList<String> configurations;        
    
    public Report(Configuration configuration) {
        configurations = new LinkedList<String>();
        startConf = configuration.toString();
    }
            
    public void add(Configuration configuration, int type) {
        switch(type) {
            case TRANS : {
                configurations.add(configuration.toString());
            } break;
            case UNDO : {
                configurations.removeLast();
            } break;
            case REDO : {
                configurations.add(configuration.toString());
            } break;            
        }
    }
    
    
    
    
    /*
     *
     *
     *
     *
     *
     *(defun prumerPrvkuOb (L) (cond ((null L) 0)(t (/ soucetPrvkuOb(L) pocetPrvkuOb(L)))))
 */   
    
    
    
    
    
    
    
    
    
    public String createProcess() {
        StringBuilder process = new StringBuilder(startConf + "\n");
        for (String conf : configurations) {
            process.append(" |-- ").append(conf).append("\n");
        }
        return process.toString();
    }        
    
    
    public String getFirst() {
        return startConf;
    }

    
    public String getLast() {
        if(configurations.isEmpty()) {
            return startConf;
        }
        return configurations.getLast();
    }

    public int stepCount() {
        return configurations.size();
    }
    
    
}
