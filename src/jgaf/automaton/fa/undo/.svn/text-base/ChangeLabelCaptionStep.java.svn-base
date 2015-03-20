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
public class ChangeLabelCaptionStep implements UndoRedoStep {

    private final CanvasLabel label;
    private final String oldCaption;
    private final String newCaption;


    public ChangeLabelCaptionStep(CanvasLabel label, String oldCaption, String newCaption) {
        this.label = label;
        this.oldCaption = oldCaption;
        this.newCaption = newCaption;
    }

    public void undo() {
        label.setCaption(oldCaption);
    }

    public void redo() {
        label.setCaption(newCaption);
    }

    public String type() {
        return "ChangeLabelCaptionStep";
    }

}
