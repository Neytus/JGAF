/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.fa.CanvasLabel;
import jgaf.automaton.fa.StateDiagramEditor;

/**
 *
 * @author hanis
 */
public class addLabelStep implements UndoRedoStep {

    private final StateDiagramEditor editor;
    private final CanvasLabel label;

    public addLabelStep(StateDiagramEditor editor, CanvasLabel label) {
        this.editor = editor;
        this.label = label;
    }

    public void undo() {
        editor.removeLabel(label);
    }

    public void redo() {
        editor.addLabel(label);
    }

    public String type() {
        return "AddLabelStep";
    }

}
