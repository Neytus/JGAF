/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.automaton.fa.undo;

import java.util.List;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class ChangeAcceptingStatesStep implements UndoRedoStep {

    private final Automaton automaton;
    private final List<State> states;
    private final boolean accepting;

    public ChangeAcceptingStatesStep(Automaton automaton, List<State> states, boolean accepting) {
        this.automaton = automaton;
        this.states = states;
        this.accepting = accepting;
    }

    public void undo() {
        if(accepting) {
            automaton.removeAcceptingStates(states);
        } else {
            automaton.addAcceptingStates(states);
        }
    }

    public void redo() {
        if(accepting) {
            automaton.addAcceptingStates(states);
        } else {
            automaton.removeAcceptingStates(states);
        }
    }

    public String type() {
        return "ChangeAcceptingStatesStep";
    }
}
