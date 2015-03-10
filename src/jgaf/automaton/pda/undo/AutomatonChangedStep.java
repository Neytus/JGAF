/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.pda.undo;

import jgaf.automaton.fa.undo.UndoRedoStep;
import jgaf.automaton.pda.PushdownAutomaton;
import jgaf.automaton.pda.PushdownAutomatonEditor;

/**
 *
 * @author hanis
 */
public class AutomatonChangedStep implements UndoRedoStep {

    private final PushdownAutomaton oldAutomaton;
    private final PushdownAutomaton newAutomaton;
    private final PushdownAutomatonEditor editor;

    public AutomatonChangedStep(PushdownAutomaton oldAutomaton,
                                PushdownAutomaton newAutomaton,
                                PushdownAutomatonEditor editor) {
        this.oldAutomaton = oldAutomaton;
        this.newAutomaton = newAutomaton;
        this.editor = editor;
    }

    public void undo() {
        editor.setData(oldAutomaton);
    }

    public void redo() {
        editor.setData(newAutomaton);
    }

    public String type() {
        return "PDA:AutomatonChangedStep";
    }



}
