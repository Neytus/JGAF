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
public class ModifyLabelStep implements UndoRedoStep {

    private final CanvasLabel label;
    private final CanvasLabel oldLabel;
    private final CanvasLabel newLabel;

    public ModifyLabelStep(CanvasLabel label, CanvasLabel oldLabel, CanvasLabel newLabel) {
        this.label = label;
        this.oldLabel = oldLabel;
        this.newLabel = newLabel;
    }

    public void undo() {
        label.modify(oldLabel);
    }

    public void redo() {
        label.modify(newLabel);
    }

    public String type() {
        return "ModifyLabelStep";
    }

}
