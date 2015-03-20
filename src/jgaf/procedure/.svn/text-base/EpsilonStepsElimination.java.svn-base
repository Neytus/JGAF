/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.Color;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class EpsilonStepsElimination extends DefaultProcedure {

    private Automaton input;
    private Automaton output;


    public EpsilonStepsElimination() {
    }


    public void clearAutomatonHighlighting() {
        input.clearHighlighting();
        output.clearHighlighting();
    }


    public void startProcedure() {
        clearAutomatonHighlighting();
        logState("start");

        output.addStates(input.getStates());
        for (State state : input.getStates()) {
            output.addState((State) state.clone());
        }
        logState("Copying states to the new automaton.");

        for (State state : input.getStates()) {
            for (String letter : input.getAlphabet()) {
                for (State stateTo : input.getStatesFromUnderExtended(state, letter)) {                    
                    Transition transition = output.addTransition(state, stateTo, letter);
                    transition.getVisualProperties().setStrokeColor(Color.RED);
                    logState("adding transition: " + state.getName() + " ---" + letter + "---> " + stateTo.getName());
                    clearAutomatonHighlighting();
                }
            }
        }

        for (State state : input.getStatesFromUnderEpsilonExtended(input.getInitialState())) {
            if(state.isAccepting()) {
                output.getInitialState().setAccepting(true);
                output.getInitialState().getVisualProperties().setFillColor(Color.RED);
                logState("making initial state accepting because of epsilon path " + output.getInitialState() +
                        " to " + state.getName());
                clearAutomatonHighlighting();
                break;
            }
        }
        logState("done");

    }
    

    @Override
    public String checkInputRepresentation() {
        int type = input.getType();
        if(type != Automaton.UFA) {
            return CHECK_OK;
        }
        return "Automaton is not correct.";
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