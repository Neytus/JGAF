/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import java.util.List;
import java.util.Set;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class SwapAcceptingStatesStep implements UndoRedoStep {

    private final Automaton automaton;
    private final List<State> states;

    public SwapAcceptingStatesStep(Automaton automaton, List<State> states) {
        this.automaton = automaton;
        this.states = states;
    }

    public void undo() {
        automaton.swapAcceptingStates(states);
    }

    public void redo() {
        automaton.swapAcceptingStates(states);
    }

    public String type() {
        return "SwapAcceptingStatesStep";
    }


}