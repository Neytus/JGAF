/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.fa.InitialArrow;
import jgaf.automaton.fa.StateDiagramEditor;

/**
 *
 * @author hanis
 */

public class ChangeInitialArrowOrientationStep implements UndoRedoStep {

    private final InitialArrow initialArrow;
    private final double oldAngle;
    private final double newAngle;

    public ChangeInitialArrowOrientationStep(InitialArrow initialArrow, double oldAngle, double newAngle) {
        this.initialArrow = initialArrow;
        this.oldAngle = oldAngle;
        this.newAngle = newAngle;
    }

    public void undo() {
        initialArrow.setOrientation(oldAngle);
    }

    public void redo() {
        initialArrow.setOrientation(newAngle);
    }

    public String type() {
        return "ChangeInitialArrowOrientationStep";
    }
}
