/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class InaccessibleStatesElimination extends DefaultProcedure {

    private Automaton input;
    private Automaton output;


    public InaccessibleStatesElimination() {
    }


    public void clearAutomatonHighlighting() {
        input.clearHighlighting();
        output.clearHighlighting();
    }


    public void startProcedure() {
        clearAutomatonHighlighting();
        logState("start");




        State initialState = input.getInitialState();
        initialState.getVisualProperties().setFillColor(Color.RED);
        output.addState(initialState);
        logState("adding initial state");

        List<State> oldStates = new ArrayList<State>();
        List<State> newStates = new ArrayList<State>();
        newStates.add(initialState);
        int i = 1;
        do {
            clearAutomatonHighlighting();
            oldStates.addAll(newStates);
            newStates.clear();
            for (State state : oldStates) {
                state.getVisualProperties().setFillColor(Color.GREEN);
                for (Transition transition : input.getOutgoingTransitions(state)) {
                    transition.getVisualProperties().setStrokeColor(Color.RED);
                    State toState = transition.getToState();
                    if(!output.containsState(toState)) {
                        toState.getVisualProperties().setFillColor(Color.RED);
                        newStates.add(toState);
                        output.addState(toState);
                    }
                    output.addTransition(transition);
                }
            }
            oldStates.clear();
            logState("after " + i++ + ". iteration");
        } while(!newStates.isEmpty());
        clearAutomatonHighlighting();
        logState("done");

    }


    @Override
    public String checkInputRepresentation() {
        int type = input.getType();
        if(type != Automaton.UFA) {
            return CHECK_OK;
        }
        return CHECK_NOT_OK;
    }


    @Override
    public String checkInputParameters() {
        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {

    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
        output = (Automaton) outputRepresentation;
    }


    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        input = (Automaton) inputRepresentations[0];
    }
}
