/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hanis
 */
public class MultiStep implements UndoRedoStep{
    private List<UndoRedoStep> stepList = new ArrayList<UndoRedoStep>();

    public MultiStep() {
    }

    public void addStep(UndoRedoStep step) {
        stepList.add(step);
    }


    public boolean isEmpty() {
        return stepList.isEmpty();
    }

    public void undo() {
        if (!stepList.isEmpty()) {
            for (int i = stepList.size() - 1; i >= 0; i--) {
                stepList.get(i).undo();
            }
        }
    }

    public void redo() {
        if (!stepList.isEmpty()) {
            for (int i = 0; i < stepList.size(); i++) {
                stepList.get(i).redo();
            }
        }
    }

    public String type() {
        return "MultiStep";
    }

}
