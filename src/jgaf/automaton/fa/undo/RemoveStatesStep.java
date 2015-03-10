/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class RemoveStatesStep implements UndoRedoStep {

    private final Set<State> states;
    private final Set<Transition> transitions;
    private final Automaton automaton;

    public RemoveStatesStep(Set<State> states, Automaton automaton) {
        this.automaton = automaton;
        this.states = new HashSet<State>();
        this.transitions = new HashSet<Transition>();

        for (State state : states) {
            if(automaton.containsState(state)) {
                this.states.add(state);
                transitions.addAll(automaton.getIncomingTransitions(state));
                transitions.addAll(automaton.getOutgoingTransitions(state));
            }
        }
    }

    public void undo() {
        automaton.addStates(states);
        automaton.addTransitions(transitions);
    }

    public void redo() {
        automaton.removeStates(states);
        automaton.removeTransitions(transitions);
    }

    public String type() {
        return "RemoveStatesStep";
    }


}
