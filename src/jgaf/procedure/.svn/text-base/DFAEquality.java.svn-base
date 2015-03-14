/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class DFAEquality  extends DefaultProcedure {

    private Automaton dfa1;
    private Automaton dfa2;


    public DFAEquality() {
    }


    private void clearAutomatonHighlighting() {
        dfa1.clearHighlighting();
        dfa2.clearHighlighting();
    }



    public void startProcedure() {
        clearAutomatonHighlighting();
        logState("start");

        clearAutomatonHighlighting();
        logState("start");
        if (!dfa1.hasTotalTransitionFunction()) {
            State t1 = dfa1.makeTransitionFunctionTotal();
            t1.getVisualProperties().setCoordinates(20, 20);
            t1.getVisualProperties().setFillColor(Color.DARK_GRAY);
            logState("making first automaton total");
            clearAutomatonHighlighting();
        }

        if (!dfa2.hasTotalTransitionFunction()) {
            State t2 = dfa2.makeTransitionFunctionTotal();
            t2.getVisualProperties().setCoordinates(20, 20);
            t2.getVisualProperties().setFillColor(Color.DARK_GRAY);
            logState("making second automaton total");
            clearAutomatonHighlighting();
        }

        int i = 0;

        Set<StateCuple> cupleSet = new HashSet<StateCuple>();
        StateCuple cuple = new StateCuple(dfa1.getInitialState(), dfa2.getInitialState());
        cupleSet.add(cuple);
        logState("adding " + cuple.toString());
        int k = 0;
        while (true) {
            k++;
            if(k > 9) {
                break;
            }
            Set<StateCuple> newCupleSet = new HashSet<StateCuple>();
            boolean someNew = false;
            for (StateCuple stateCuple : cupleSet) {
                for (String string : dfa1.getAlphabet()) {
                    State s1 = dfa1.getStatesFromUnder(stateCuple.getS1(), string).get(0);
                    State s2 = dfa2.getStatesFromUnder(stateCuple.getS2(), string).get(0);
                    StateCuple tmpCuple = new StateCuple(s1, s2);
                    if(!cupleSet.contains(tmpCuple) && !newCupleSet.contains(tmpCuple)) {
                        s1.getVisualProperties().setFillColor(Color.RED);
                        s2.getVisualProperties().setFillColor(Color.RED);
                        newCupleSet.add(tmpCuple);
                        if(s1.isAccepting() != s2.isAccepting()) {
                            logState("automata recognize the same language - violation: " + tmpCuple.toString());
                            return;
                        }
                        logState("adding " + tmpCuple.toString());
                        someNew = true;
                        clearAutomatonHighlighting();
                    }
                }
            }
            if(!someNew) {
                break;
            } 
            for (StateCuple stateCuple : newCupleSet) {
                cupleSet.add(stateCuple);
            }


        }
        logState("automata recognize the same language");

    }

    @Override
    public String checkInputRepresentation() {
        if(!dfa1.getAlphabet().equals(dfa2.getAlphabet())) {
            return "Automata doesn't have the same alphabet.";
        }
        if(dfa1.getType() != Automaton.DFA) {
            return "Fist automaton is not deterministic";
        }
        if(dfa2.getType() != Automaton.DFA) {
            return "Second automaton is not deterministic";
        }
        return CHECK_OK;
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
    }


    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        dfa1 = (Automaton) inputRepresentations[0];
        dfa2 = (Automaton) inputRepresentations[1];
    }
}
