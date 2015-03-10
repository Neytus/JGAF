/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.fa.StateDiagramEditor;

/**
 *
 * @author hanis
 */
public class ZoomStep implements UndoRedoStep{
    private final StateDiagramEditor automatonEditor;
    private final double oldZoom;
    private final double newZoom;

    public ZoomStep(StateDiagramEditor automatonEditor, double oldZoom, double newZoom) {
        this.automatonEditor = automatonEditor;
        this.oldZoom = oldZoom;
        this.newZoom = newZoom;

    }

    public void undo() {
        automatonEditor.setZoomfactor(oldZoom);
        automatonEditor.centerZoom(newZoom);
    }

    public void redo() {
        automatonEditor.setZoomfactor(newZoom);
        automatonEditor.centerZoom(oldZoom);
    }

    public String type() {
        return "ZoomStep";
    }

}
