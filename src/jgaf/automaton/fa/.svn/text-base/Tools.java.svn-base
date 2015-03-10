/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class Tools {



    public static SortedSet<String> getSortedSetFromString(String labelsString) {
        labelsString = labelsString.trim();
        StringTokenizer st = new StringTokenizer(labelsString, ",");
        SortedSet<String> labels = new TreeSet<String>();
        while(st.hasMoreTokens()) {
            labels.add(st.nextToken());
        }
        return labels;
    }



    public static String getStringFromSortedSet(SortedSet<String> set) {
        if(set.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String label : set) {
            sb.append(label).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }



    public static HashSet<State> getStatesInRectangle(Automaton automaton, Rectangle rectangle) {
        HashSet<State> set = new HashSet<State>();
        for (State state : automaton.getStates()) {
            if(rectangle.contains(state.getVisualProperties().getPoint())) {                
                set.add(state);
            }
        }
        return set;
    }

}
