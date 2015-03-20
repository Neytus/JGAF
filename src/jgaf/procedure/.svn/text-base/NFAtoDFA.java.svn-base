/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.Color;
import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.automaton.layouts.GEM;

/**
 *
 * @author hanis
 */
public class NFAtoDFA extends DefaultProcedure {


    private Automaton input;
    private Automaton output;


    public NFAtoDFA() {
    }


    public void clearAutomatonHighlighting() {
        input.clearHighlighting();
        output.clearHighlighting();
    }


    public void startProcedure() {
   
        int x = 130;
        int y = 100;

        clearAutomatonHighlighting();
        logState("start");




     //   logState();
        Set<Set<State>> Q = new HashSet<Set<State>>();
        Set q = new HashSet<State>();
        q.add(input.getInitialState());
        Q.add(q);
        State stateInit = createState(q);
        stateInit.getVisualProperties().setCoordinates(new Point(x, y));
        output.addState(stateInit);
        output.setInitialState(stateInit);

        stateInit.getVisualProperties().setFillColor(Color.RED);
        logState("creating initial state " + stateInit.getName());
        clearAutomatonHighlighting();


        Set done = new HashSet<Set<State>>();
        while(!subtract(Q, done).isEmpty()) {
            //System.out.println("Q" + Q);
            Set M = (Set) subtract(Q, done).iterator().next();
            //System.out.println("m" + M);
            //System.out.println("done" + done);
            State stateM = output.getState(createState(M));
           // output.addState(stateM);

            //System.out.println("inter - M: " + M + " , acc: " + input.getAcceptingStates());
            if(!intersection(M, input.getAcceptingStates()).isEmpty()) {        
                output.addAcceptingState(stateM);
                stateM.getVisualProperties().setFillColor(Color.RED);
                logState("making state " + stateM.getName() + " accepting");
                clearAutomatonHighlighting();
            }

            for (String symbol : input.getAlphabet()) {
                Set<State> N = new HashSet<State>();
                for (Transition transition : input.getTransitions()) {
                    if(transition.getLabels().contains(symbol) && M.contains(transition.getFromState())) {
                        N.add(transition.getToState());
                    }
                }
                State stateN = createState(N);
               // stateN.getVisualProperties().setCoordinates(output.getStates().size()*100, output.getStates().size()*100);
                Q.add(N);                
                if(!output.containsState(stateN)) {
                    output.addState(stateN);
                    stateN.getVisualProperties().setFillColor(Color.RED);
                    x+=100;
                    if(x > 670) {
                        x = 140;
                        y += 140;
                    }
                    stateN.getVisualProperties().setCoordinates(x,y);
//                    GEM gem = new GEM(output);
                    //gem.arrangeAutomaton();
//                    gem.placeVertex(stateN);


                    logState("creating state " + stateInit.getName());
                    clearAutomatonHighlighting();
                }

                //  logState();
                Transition t = output.addTransition(stateM, stateN, symbol);
                t.getVisualProperties().setCurveFactor(1);
                t.getVisualProperties().setStrokeColor(Color.RED);

                logState("adding transition " + t.toString());
                clearAutomatonHighlighting();
                
            }
            done.add(M);
        }

        //GEM gem = new GEM(output);
        //gem.arrangeAutomaton();
        //gem.arrangeAutomatonForProcedures();

        logState("done");















//
//
//        output.addStates(input.getStates());
//        for (State state : input.getStates()) {
//            output.addState((State) state.clone());
//        }
//        logState("Copying states to the new automaton.");
//
//        for (State state : input.getStates()) {
//            for (String letter : input.getAlphabet()) {
//                for (State stateTo : input.getStatesFromUnderExtended(state, letter)) {
//                    Transition transition = output.addTransition(state, stateTo, letter);
//                    transition.getVisualProperties().setStrokeColor(Color.RED);
//                    logState("adding transition: " + state.getName() + " ---" + letter + "---> " + stateTo.getName());
//                    clearAutomatonHighlighting();
//                }
//            }
//        }
//
//        for (State state : input.getStatesFromUnderEpsilonExtended(input.getInitialState())) {
//            if(state.isAccepting()) {
//                output.getInitialState().setAccepting(true);
//                output.getInitialState().getVisualProperties().setFillColor(Color.RED);
//                logState("making initial state accepting because of epsilon path " + output.getInitialState() +
//                        " to " + state.getName());
//                clearAutomatonHighlighting();
//                break;
//            }
//        }
//        logState("done");

    }


    @Override
    public String checkInputRepresentation() {
        int type = input.getType();
        if(type == Automaton.UFA) {
            return "Input automaton is not correct.";
        } else if(type == Automaton.EFA) {
            return "Input automaton contains epsilon steps.";                        
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
        output = (Automaton) outputRepresentation;
    }


    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        input = (Automaton) inputRepresentations[0];
    }


    public Set subtract(Set a, Set b) {
        Set set = new HashSet(a);
        for (Object state : b) {
            set.remove(state);
        }
        return set;
    }

    public Set<State> intersection(Collection<State> a, Collection<State> b) {
        Set<State> Set = new HashSet<State>();
        for (State state: a) {
            if(b.contains(state)) {
                Set.add(state);
            }
        }
        return Set;
    }

    public void start() {
    }


    public State createState(Set<State> Set) {
        if(Set.isEmpty()) {
            return new State("{}");
        }
        String name = "{";
        for (State state : Set) {
            name += state.getName() + ",";
        }
        name = name.substring(0, name.lastIndexOf(",")) + "}";
        return new State(name);
    }

}
