/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.transformations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class InaccessibleStatesElimination extends Transformation {


    public InaccessibleStatesElimination(Automaton input, Automaton output) {
        super(input, output);
    }



    private void clearHighlighting() {
        for (State state : input.getStates()) {
            state.getVisualProperties().setFillColor(Color.yellow);
        }
        for (State state : output.getStates()) {
            state.getVisualProperties().setFillColor(Color.orange);
        }

        for (Transition transition : input.getTransitions()) {
            transition.getVisualProperties().setStrokeColor(Color.black);
        }
    }


    public void start() {
        clearHighlighting();
        State initialState = input.getInitialState();
        initialState.getVisualProperties().setFillColor(Color.red);
        output.addState(initialState);
        logState();
        List<State> oldStates = new ArrayList<>();
        List<State> newStates = new ArrayList<>();
        newStates.add(initialState);
        do {
            clearHighlighting();
            oldStates.addAll(newStates);
            newStates.clear();
            for (State state : oldStates) {
                state.getVisualProperties().setFillColor(Color.green);
                for (Transition transition : input.getOutgoingTransitions(state)) {
                    transition.getVisualProperties().setStrokeColor(Color.red);
                    State toState = transition.getToState();
                    if(!output.containsState(toState)) {
                        toState.getVisualProperties().setFillColor(Color.red);
                        newStates.add(toState);
                        output.addState(toState);                        
                    }
                    output.addTransition(transition);
                }
            }
            oldStates.clear();
            logState();
        } while(!newStates.isEmpty());
        clearHighlighting();
        logState();
    }

    @Override
    public String getName() {
        return "Inaccessible States Elimination";
    }
}
