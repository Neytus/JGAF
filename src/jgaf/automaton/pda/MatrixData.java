/*
 * MatrixData.java
 *
 * Created on 31. říjen 2007, 20:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 *
 * @author hanis
 */
public class MatrixData {
    
 
    private SortedMap<StateAndSymbol, Map<String, Transition>> matrixData;
    private List<String> stackSymbols;
    private List<StateAndSymbol> stateAndSymbols;
    private Set<String> finalStates;
    
    public MatrixData(PushdownAutomaton automata) {
        matrixData = getDataForMatrix(automata.getTransitionFunction().getTransitions());
        stackSymbols = new ArrayList<String>(automata.getStackAlphabet());
        stateAndSymbols = new ArrayList<StateAndSymbol>(getMatrixData().keySet());     
        finalStates = automata.getFinalStates();
    }
    
    
    public SortedMap<StateAndSymbol, Map<String, Transition>> getDataForMatrix(SortedSet<Transition> transitions) {
        SortedMap<StateAndSymbol, Map<String, Transition>> matrix = new TreeMap<StateAndSymbol, Map<String, Transition>> ();
        for (Transition transition : transitions) {
            StateAndSymbol stateAndSymbol = transition.getTernary().getStateAndSymbol();
            if(matrix.containsKey(stateAndSymbol)) {
                matrix.get(stateAndSymbol).put(transition.getTernary().getStackSymbol(), transition);
            } else {
                Map<String, Transition> transitionMap = new HashMap<String, Transition>();
                transitionMap.put(transition.getTernary().getStackSymbol(), transition);
                matrix.put(stateAndSymbol, transitionMap);
            }
        }        
        return matrix;
    }       

    public SortedMap<StateAndSymbol, Map<String, Transition>> getMatrixData() {
        return matrixData;
    }

    public List<String> getStackSymbols() {
        return stackSymbols;
    }

    public List<StateAndSymbol> getStateAndSymbols() {
        return stateAndSymbols;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }
    
    
}
