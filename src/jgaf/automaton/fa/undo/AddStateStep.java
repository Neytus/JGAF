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
public class AddStateStep implements UndoRedoStep {

    private Automaton automaton;
    private State state;

    public AddStateStep(Automaton automaton, State state) {
        this.automaton = automaton;
        this.state = state;
    }

    public void undo() {
        automaton.removeState(state);
    }

    public void redo() {
        automaton.addState(state);
    }

    public String type() {
        return "AddStateStep";
    }
    


}
