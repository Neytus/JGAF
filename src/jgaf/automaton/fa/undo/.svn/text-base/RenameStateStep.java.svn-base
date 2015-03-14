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
public class RenameStateStep implements UndoRedoStep {

    private final State state;
    private final String oldName;
    private final String newName;
    private final Automaton automaton;

    public RenameStateStep(Automaton automaton, State state, String oldName, String newName) {
        this.automaton = automaton;
        this.state = state;
        this.oldName = oldName;
        this.newName = newName;
    }

    public void undo() {
        automaton.renameState(state, oldName);
    }

    public void redo() {
        automaton.renameState(state, newName);
    }

    public String type() {
        return "RenameStateStep";
    }


}
