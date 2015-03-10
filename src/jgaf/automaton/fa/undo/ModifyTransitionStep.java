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
public class ModifyTransitionStep implements UndoRedoStep {

    private final Transition transition;
    private final Transition oldTransition;
    private final Transition newTransition;

    public ModifyTransitionStep(Transition transition, Transition oldTransition, Transition newTransition) {
        this.transition = transition;
        this.oldTransition = oldTransition;
        this.newTransition = newTransition;
    }

    public void undo() {
        transition.setLabels(oldTransition.getLabels());
        transition.getVisualProperties().modify(oldTransition.getVisualProperties());
    }

    public void redo() {
        transition.setLabels(newTransition.getLabels());
        transition.getVisualProperties().modify(newTransition.getVisualProperties());
    }

    public String type() {
        return "ModifyTransitionStep";
    }

}
