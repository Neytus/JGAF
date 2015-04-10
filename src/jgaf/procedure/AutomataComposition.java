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
public class AutomataComposition extends DefaultProcedure {

    private Automaton automaton1;
    private Automaton automaton2;

    private Automaton output;
    
    private String typeString;
    private int type;
    
    public static final int union = 0;
    public static final int intersection = 1;
    public static final int difference = 2;


    public AutomataComposition() {
    }


    private void clearAutomatonHighlighting() {
        automaton1.clearHighlighting();
        automaton2.clearHighlighting();
        output.clearHighlighting();
    }



    public void startProcedure() {
        clearAutomatonHighlighting();
        logState("start");
        if (!automaton1.hasTotalTransitionFunction()) {
            State t1 = automaton1.makeTransitionFunctionTotal();
            t1.getVisualProperties().setCoordinates(20, 20);
            t1.getVisualProperties().setFillColor(Color.DARK_GRAY);
            logState("making first automaton total");
            clearAutomatonHighlighting();
        }

        if (!automaton2.hasTotalTransitionFunction()) {
            State t2 = automaton2.makeTransitionFunctionTotal();
            t2.getVisualProperties().setCoordinates(20, 20);
            t2.getVisualProperties().setFillColor(Color.DARK_GRAY);
            logState("making second automaton total");
            clearAutomatonHighlighting();
        }

        int i = 0;

        int x = -40;
        int y = 80;


        for (State s1 : automaton1.getStates()) {
            for (State s2 : automaton2.getStates()) {
                String n = s1.getName() + "x" + s2.getName();
                x+=120;
                if(x > 600) {
                    x = 80;
                    y+=120;
                }
                State newState = new State(n, x, y);

                s1.getVisualProperties().setFillColor(Color.RED);
                s2.getVisualProperties().setFillColor(Color.RED);
                newState.getVisualProperties().setFillColor(Color.RED);
                output.addState(newState);
                logState("adding new state " + newState.getName());
                clearAutomatonHighlighting();

                if(s1.isInitial() && s2.isInitial()) {
                    s1.getVisualProperties().setFillColor(Color.ORANGE);
                    s2.getVisualProperties().setFillColor(Color.ORANGE);
                    newState.getVisualProperties().setFillColor(Color.ORANGE);
                    output.setInitialState(newState);
                    logState("making state " + newState.getName() + " initial");
                    clearAutomatonHighlighting();
                }


                if (type == union) {
                    if(s1.isAccepting() || s2.isAccepting()) {
                        s1.getVisualProperties().setFillColor(Color.GREEN);
                        s2.getVisualProperties().setFillColor(Color.GREEN);
                        newState.getVisualProperties().setFillColor(Color.GREEN);
                        newState.setAccepting(true);
                        logState("making state " + newState.getName() + " accepting");
                        clearAutomatonHighlighting();
                    }
                } else if (type == intersection) {
                    if(s1.isAccepting() && s2.isAccepting()) {
                        s1.getVisualProperties().setFillColor(Color.GREEN);
                        s2.getVisualProperties().setFillColor(Color.GREEN);
                        newState.getVisualProperties().setFillColor(Color.GREEN);
                        newState.setAccepting(true);
                        logState("making state " + newState.getName() + " accepting");
                        clearAutomatonHighlighting();
                    }
                } else if (type == difference) {
                    if(s1.isAccepting() && !s2.isAccepting()) {
                        s1.getVisualProperties().setFillColor(Color.GREEN);
                        s2.getVisualProperties().setFillColor(Color.GREEN);
                        newState.getVisualProperties().setFillColor(Color.GREEN);
                        newState.setAccepting(true);
                        logState("making state " + newState.getName() + " accepting");
                        clearAutomatonHighlighting();
                    }
                }
            }

        }


        for (State s1 : automaton1.getStates()) {
            for (State s2 : automaton2.getStates()) {
                for (String a : automaton1.getAlphabet()) {
                    State from = output.getStateByName(s1.getName() + "x" + s2.getName());
                    State state1 = automaton1.getStatesFromUnder(s1, a).get(0);
                    State state2 = automaton2.getStatesFromUnder(s2, a).get(0);
                    State to = output.getStateByName(state1.getName() + "x" + state2.getName());
                    Transition t = output.addTransition(from, to, a);

                    t.getVisualProperties().setStrokeColor(Color.RED);
                    logState("adding new transition " + t.toString());
                    clearAutomatonHighlighting();
                }

            }
        }


    }



    @Override
    public String checkInputRepresentation() {
        if(!automaton1.getAlphabet().equals(automaton2.getAlphabet())) {
            return "Automata doesn't have the same alphabet.";
        }
        if(automaton1.getType() != Automaton.DFA) {
            return "Fist automaton is not deterministic.";
        }
        if(automaton2.getType() != Automaton.DFA) {
            return "Second automaton is not deterministic.";
        }
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() { 
        if (typeString.equalsIgnoreCase("union")) {
            type = union;
            return CHECK_OK;
        }

        if (typeString.equalsIgnoreCase("intersection")) {
            type = intersection;
            return CHECK_OK;
        }

        if (typeString.equalsIgnoreCase("difference") || typeString.equalsIgnoreCase("slr(k)")) {
            type = difference;
            return CHECK_OK;
        }

        return "Wrong parameter: Type. \n Only UNION, INTERSECTION and DIFFERENCE are allowed.";
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        typeString = inputParameters[0];
    }


    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
        output = (Automaton) outputRepresentation;
    }



    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        automaton1 = (Automaton) inputRepresentations[0];
        automaton2 = (Automaton) inputRepresentations[1];
    }
}