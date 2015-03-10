/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.algorithms;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class FAAlgorithms {


    public static void removeUnreachableStates(Automaton automaton) {
        if(!automaton.hasInitialState()) {
            return;
        }
        int i = 0;
        Set<State> reachableStates = new HashSet<State>();
        reachableStates.add(automaton.getInitialState());
        Set statesToAdd = new HashSet<State>();
        do {
            for (State state : reachableStates) {
                for (Transition transition : automaton.getTransitions()) {
                    if(transition.getFromState().equals(state)) {
                        statesToAdd.add(transition.getToState());
                    }
                }
            }
        } while(reachableStates.addAll(statesToAdd));
        Set<State> unreachableStates = new HashSet<State>();
        unreachableStates.addAll(automaton.getStates());
        unreachableStates.removeAll(reachableStates);
        for (State state : unreachableStates) {
            automaton.removeState(state);
        }
    }


}
