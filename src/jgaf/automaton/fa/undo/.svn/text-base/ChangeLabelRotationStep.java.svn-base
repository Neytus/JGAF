/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.fa.CanvasLabel;

/**
 *
 * @author hanis
 */
public class ChangeLabelRotationStep implements UndoRedoStep {

    private final CanvasLabel label;
    private final int oldRotation;
    private final int newRotation;


    public ChangeLabelRotationStep(CanvasLabel label, int oldRotation, int newRotation) {
        this.label = label;
        this.oldRotation = oldRotation;
        this.newRotation = newRotation;
    }

    public void undo() {
        label.setRotationFactor(oldRotation);
    }

    public void redo() {
        label.setRotationFactor(newRotation);
    }

    public String type() {
        return "ChangeLabelRotationStep";
    }
}
