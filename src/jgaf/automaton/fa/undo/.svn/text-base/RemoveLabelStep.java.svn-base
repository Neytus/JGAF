/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.fa.StateDiagramEditor;
import jgaf.automaton.fa.CanvasLabel;

/**
 *
 * @author hanis
 */
public class RemoveLabelStep implements UndoRedoStep {

    private final StateDiagramEditor editor;
    private final CanvasLabel label;

    public RemoveLabelStep(StateDiagramEditor editor, CanvasLabel label) {
        this.editor = editor;
        this.label = label;
    }

    public void undo() {
        editor.addLabel(label);
    }

    public void redo() {
        editor.removeLabel(label);
    }

    public String type() {
        return "RemoveLabelStep";
    }

}
