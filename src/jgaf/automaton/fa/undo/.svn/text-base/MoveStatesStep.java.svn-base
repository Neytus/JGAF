/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;


import java.util.Set;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class MoveStatesStep implements UndoRedoStep {

    private final Set<State> states;
    private final int offsetX;
    private final int offsetY;

    public MoveStatesStep(Set<State> states, int offsetX, int offsetY) {
        this.states = states;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void undo() {
        for (State state : states) {
            state.getVisualProperties().setXPos(state.getVisualProperties().getXPos() - offsetX);
            state.getVisualProperties().setYPos(state.getVisualProperties().getYPos() - offsetY);
        }
    }

    public void redo() {
        for (State state : states) {
            state.getVisualProperties().setXPos(state.getVisualProperties().getXPos() + offsetX);
            state.getVisualProperties().setYPos(state.getVisualProperties().getYPos() + offsetY);
        }
    }

    public String type() {
        return "MoveStatesStep";
    }
}
