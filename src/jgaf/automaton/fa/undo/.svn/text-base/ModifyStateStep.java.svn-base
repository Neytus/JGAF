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
public class ModifyStateStep implements UndoRedoStep {

    private final Automaton automaton;
    private final State lastInitialState;
    private final State state;
    private final State oldState;
    private final State newState;

    public ModifyStateStep(Automaton automaton, State state, State oldState, State newState, State lastInitialState) {
        this.automaton = automaton;
        this.oldState = oldState;
        this.newState = newState;
        this.state = state;
        this.lastInitialState = lastInitialState;
    }

    public void undo() {
        automaton.modifyState(state, oldState);
        if(lastInitialState != null) {
            automaton.setInitialState(lastInitialState);
        }
    }

    public void redo() {
        automaton.modifyState(state, newState);
    }

    public String type() {
        return "ModifyStateStep";
    }



}
