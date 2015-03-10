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
public class MoveGraphicsStep implements UndoRedoStep {
    private final int offsetX;
    private final int offsetY;
    private final StateDiagramEditor automatonEditor;

    public MoveGraphicsStep(StateDiagramEditor automatonEditor, int offsetX, int offsetY) {
        this.automatonEditor = automatonEditor;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void undo() {
        automatonEditor.moveGraphics(-offsetX, -offsetY);
    }

    public void redo() {
        automatonEditor.moveGraphics(offsetX, offsetY);
    }

    public String type() {
        return "MoveGraphicsStep";
    }

}
