/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class AddTransitionLabelStep implements UndoRedoStep {

    private final Transition transition;
    private final String label;

    public AddTransitionLabelStep (Transition transition, String label) {
        this.transition = transition;
        this.label = label;
    }

    public void undo() {
        transition.removeLabel(label);
    }

    public void redo() {
        transition.addLabel(label);
    }

    public String type() {
        return "AddTransitionLabelStep";
    }
}
