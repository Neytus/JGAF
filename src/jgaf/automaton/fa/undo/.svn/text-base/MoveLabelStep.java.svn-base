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
public class MoveLabelStep implements UndoRedoStep {

    private final CanvasLabel label;
    private final int offsetX;
    private final int offsetY;

    public MoveLabelStep(CanvasLabel label, int offsetX, int offsetY) {
        this.label = label;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void undo() {
        label.move(-offsetX, -offsetY);
    }

    public void redo() {
        label.move(offsetX, offsetY);
    }

    public String type() {
        return "MoveLabelStep";
    }
}
