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
public class AddTransitionStep implements UndoRedoStep {

    private final Automaton automaton;
    private final Transition transition;

    public AddTransitionStep (Automaton automaton, Transition transition) {
        this.automaton = automaton;
        this.transition = transition;
    }

    public void undo() {
        automaton.removeTransition(transition);
    }

    public void redo() {
        automaton.addTransition(transition);
    }

    public String type() {
        return "AddTransitionStep";
    }

}
