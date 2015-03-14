/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import java.util.Map;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class LineUpStatesStep implements UndoRedoStep {

    public static final int VERTICALLY = 0;
    public static final int HORIZONTALLY = 1;

    private final Map<State, Integer> statesShiftMap;
    private final int direction;

    public LineUpStatesStep(Map<State, Integer> statesShiftMap, int direction) {
        this.statesShiftMap = statesShiftMap;
        this.direction = direction;
    }

    public void undo() {
        for (State state : statesShiftMap.keySet()) {
            int moveX = direction == VERTICALLY ? statesShiftMap.get(state) : 0;
            int moveY = direction == HORIZONTALLY ? statesShiftMap.get(state) : 0;
            state.getVisualProperties().move(-moveX, -moveY);
        }
    }

    public void redo() {
        for (State state : statesShiftMap.keySet()) {
            int moveX = direction == VERTICALLY ? statesShiftMap.get(state) : 0;
            int moveY = direction == HORIZONTALLY ? statesShiftMap.get(state) : 0;
            state.getVisualProperties().move(moveX, moveY);
        }
    }

    public String type() {
        return "LineUpStatesStep";
    }
}
