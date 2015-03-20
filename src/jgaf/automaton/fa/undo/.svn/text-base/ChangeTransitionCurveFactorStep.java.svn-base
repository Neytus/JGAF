/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.Transition;
import jgaf.automaton.fa.undo.UndoRedoStep;

/**
 *
 * @author hanis
 */
public class ChangeTransitionCurveFactorStep implements UndoRedoStep {

    private final Transition transition;
    private final double newFactor;
    private final double oldFactor;

    public ChangeTransitionCurveFactorStep (Transition transition, double oldFactor, double newFactor) {
        this.transition = transition;
        this.oldFactor = oldFactor;
        this.newFactor = newFactor;
    }

    public void undo() {
        transition.getVisualProperties().setCurveFactor(oldFactor);
    }

    public void redo() {
        transition.getVisualProperties().setCurveFactor(newFactor);
    }

    public String type() {
        return "ChangeTransitionCurveFactorStep";
    }


}
