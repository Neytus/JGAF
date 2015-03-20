/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.Automaton;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class RemoveTransitionStep implements UndoRedoStep {

    private Automaton automaton;
    private Transition transition;

    public RemoveTransitionStep (Automaton automaton, Transition transition) {
        this.automaton = automaton;
        this.transition = transition;
    }

    public void undo() {
        automaton.addTransition(transition);
    }

    public void redo() {
        automaton.removeTransition(transition);
    }

    public String type() {
        return "RemoveTransitionStep";
    }



}
