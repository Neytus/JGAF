/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import java.util.SortedSet;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class ChangeTransitionLabelsStep implements UndoRedoStep {

    private final SortedSet<String> oldLabels;
    private final SortedSet<String> newLabels;
    private final Transition transition;

    public ChangeTransitionLabelsStep(Transition transition, SortedSet<String> oldLabels, SortedSet<String> newLabels) {
        this.transition = transition;
        this.oldLabels = oldLabels;
        this.newLabels = newLabels;
    }

    public void undo() {
        transition.setLabels(oldLabels);
    }

    public void redo() {
        transition.setLabels(newLabels);
    }

    public String type() {
        return "ChangeTransitionLabelsStep";
    }

}
