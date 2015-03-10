/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.Automaton;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class ChangeInitialStateStep implements UndoRedoStep {

    private final Automaton automaton;    
    private final State state;
    private final boolean initial;

    public ChangeInitialStateStep(Automaton automaton, State state, boolean initial) {
        this.automaton = automaton;                        
        this.state = state;
        this.initial = initial;
    }

    public void undo() {
        if(initial) {
            automaton.removeInitialState();
        } else {
            automaton.setInitialState(state);
        }
    }

    public void redo() {
        if(initial) {
            automaton.setInitialState(state);
        } else {
            automaton.removeInitialState();
        }
    }

    public String type() {
        return "ChangeInitialStateStep";
    }
}
