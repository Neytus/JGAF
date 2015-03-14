/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.automaton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Constants.MathConstants;
import jgaf.Representation;
import jgaf.automaton.layouts.GEM;
import jgaf.automaton.visual.TransitionVisualProperties;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class Automaton implements Serializable, Representation {

private final long serialVersionUID = 10000000000001005L;

    public static final int DFA = 0; //deterministic
    public static final int NFA = 1; //nondeterministic
    public static final int EFA = 2; //nondeterministic with epsilon steps
    public static final int UFA = 3; //undefined ...undefined initial state or epty set of states



    private String name = "M";    
    private String description = "";

 //   private List<String> alphabet;
    private List<State> states;
    private List<Transition> transitions;


    public Automaton() {
  //      alphabet = new ArrayList<String>();
        states = new ArrayList<State>();
        transitions = new ArrayList<Transition>();
       // finalStates = new ArrayList<State>();
    }


    public void modifyState(State oldState, State newState, boolean initial) {
        if(oldState == null || newState == null) {
            throw new NullPointerException();
        }
        if(!containsState(oldState)) {
            throw new IllegalArgumentException();
        }
        if(initial) {
            removeInitialState();
        }
        newState.setInitial(initial);
        oldState.modify(newState);
    }



    public void modifyState(State oldState, State newState) {
        modifyState(oldState, newState, newState.isInitial());
    }


    public State getState(State state) {
        return getStateByName(state.getName());
    }

    public List<State> getStates() {
        return states;
    }

    public State getStateByName(String name) {
        for (State state : states) {
            if(state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }


    public List<State> getStatesFromUnder(State state, String label) {
        List list = new ArrayList<State>();
        for (Transition transition : getOutgoingTransitions(state)) {
            if(transition.containsLabel(label)) {
                list.add(transition.getToState());
            }
        }
        return list;
    }


    public List<Transition> getTransitionFromUnder(State state, String label) {
        List<Transition> list = new ArrayList<Transition>();
        for (Transition transition : getOutgoingTransitions(state)) {
            if(transition.containsLabel(label)) {
                list.add(transition);
            }
        }
        return list;
    }

    public boolean containsTransitionFromUnder(State state, String label) {
        return !getTransitionFromUnder(state, label).isEmpty();
    }


public boolean hasTotalTransitionFunction() {
        for (State state : states) {
            for (String string : getAlphabet()) {
                if(!containsTransitionFromUnder(state, string)) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean addStates(Collection<State> states) {
        boolean anyChange = false;
        for (State state : states) {
            if(addState(state)) {
                anyChange = true;
            }
        }
        return false;
    }

    public boolean addState(State state) {        
        if(!states.contains(state)) {
            states.add(state);
            return true;
        }
        return false;
    }

    public boolean renameState(State s, String newName) {
        State state = getState(s);
        if(state == null) {
            throw new IllegalArgumentException();
        }
        if(getStateByName(newName) == null) {
            state.setName(newName);
            return true;
        }
        return false;
    }

    public boolean removeStates(Collection<State> statesToDelete) {
        boolean anyChange = false;
        for (State state : statesToDelete) {
            if(removeState(state)) {
                anyChange = true;
            }
        }
        return anyChange;
    }

    public boolean removeState(State state) {
        if (states.contains(state)) {
            removeTransitionsIncidentToState(state);
            states.remove(state);
            return true;
        }
        return false;
    }

    public boolean containsState(State state) {
        return states.contains(state);
    }
   
    
    public boolean hasInitialState() {
        return getInitialState() == null ? false : true;
    }


    public State getInitialState() {
        for (State state : states) {
            if(state.isInitial()) {
                return state;
            }
        }
        return null;
    }


    public boolean setInitialState(State state) {
        if(state == null) {
            throw new NullPointerException();
        }
        if(!states.contains(state)) {
            throw new IllegalArgumentException();
        }
        State oldInitial = removeInitialState();
        state.setInitial(true);
        if(oldInitial == null || !oldInitial.equals(state)) {
            return true;
        }
        return false;
    }


    public State removeInitialState() {
        for (State state : states) {
            if(state.isInitial()) {
                state.setInitial(false);
                return state;
            }
        }
        return null;
    }



    public List<State> getAcceptingStates() {
        List finalStates = new ArrayList<State>();
        for (State state : states) {
            if(state.isAccepting()) {
                finalStates.add(state);
            }
        }
        return finalStates;
    }

    public boolean hasAnyAcceptingState() {
        return !getAcceptingStates().isEmpty();
    }

    public boolean addAcceptingState(State s) {
        State state = getState(s);
        if(state == null) {
            throw new NullPointerException();
        }
        if(!states.contains(state)) {
            throw new IllegalArgumentException();
        }
        if(!state.isAccepting()) {
            state.setAccepting(true);
            return true;
        }
        return false;
    }

    public boolean addAcceptingStates(Collection<State> acceptingStates) {
        boolean anyChange = false;
        for (State state : acceptingStates) {
            if(addAcceptingState(state)) {
                anyChange = true;
            }
        }
        return anyChange;
    }


    public boolean removeAcceptingState(State state) {
        if(state == null) {
            throw new NullPointerException();
        }
        if(!states.contains(state)) {
            throw new IllegalArgumentException();
        }
        if(state.isAccepting()) {
            state.setAccepting(false);
            return true;
        }
        return false;
    }

    public boolean removeAcceptingStates(Collection<State> acceptingStates) {
        boolean anyChange = false;
        for (State state : acceptingStates) {
            if(removeAcceptingState(state)) {
                anyChange = true;
            }
        }
        return anyChange;
    }


    public void swapAcceptingState(State state) {
        if(state == null) {
            throw new NullPointerException();
        }
        if(!states.contains(state)) {
            throw new IllegalArgumentException();
        }
        state.setAccepting(!state.isAccepting());
    }


    
    public boolean swapAcceptingStates(Collection<State> acceptingStates) {
        if(acceptingStates.isEmpty()) {
            return false;
        }
        for (State state : acceptingStates) {
            swapAcceptingState(state);
        }
        return true;
    }


    public boolean isAcceptingState(State state) {
        if(state == null) {
            throw new NullPointerException();
        }
        if(!states.contains(state)) {
            throw new IllegalArgumentException();
        }
        return state.isAccepting() ? true : false;
    }




    public List<Transition> getTransitions() {
        return transitions;
    }


    public Transition addTransition(State from, State to, SortedSet<String> labels) {
        Transition transition = new Transition(from, to, labels);
        addTransition(transition);
        return transition;
    }

    public Transition addTransition(State from, State to, String label) {
        Transition transition = new Transition(getState(from), getState(to), label);
        addTransition(transition);
        return transition;
    }

    public void addTransition(Transition transition) {
        if(transitions.contains(transition)) {
            getTransitionFromTo(transition.getFromState(), transition.getToState()).addLabels(transition.getLabels());
            getTransitionFromTo(transition.getFromState(), transition.getToState()).setVisualProperties(transition.getVisualProperties());
        } else {
            transitions.add(transition);
        }
      //  alphabet.addAll(transition.getLabels());
    }

    public void addTransitions(Set<Transition> transitions) {
        for (Transition transition : transitions) {
            addTransition(transition);
        }
    }

    public boolean removeTransition(Transition transition) {
        if(transitions.remove(transition)) {
            return true;
        }
        return false;
    }

    public boolean removeTransitions(Collection<Transition> transitions) {
        boolean anyChange = false;
        for (Transition transition : transitions) {
            if(removeTransition(transition)) {
                anyChange = true;
            }
        }
        return anyChange;
    }

    public Transition getTransitionFromTo(State from, State to) {
        for (Transition t : transitions) {
            if (t.getFromState().equals(from) && t.getToState().equals(to)) {
                return t;
            }
        }
        return null;
    }


    public List<Transition> getTransitionFrom(State from) {
        List<Transition> list = new ArrayList<Transition>();
        for (Transition t : transitions) {
            if (t.getFromState().equals(from)) {
                list.add(t);
            }
        }
        return list;
    }
    
    public List<Transition> getTransitionTo(State to) {
        List<Transition> list = new ArrayList<Transition>();
        for (Transition t : transitions) {
            if (t.getToState().equals(to)) {
                list.add(t);
            }
        }
        return list;
    }

    public List<Transition> getIncomingTransitions(State state) {
        List<Transition> incomingTransitions = new ArrayList<Transition>();
        for (Transition transition : transitions) {
            if(transition.getToState().equals(state)) {
                incomingTransitions.add(transition);
            }
        }
        return incomingTransitions;
    }

    public List<Transition> getOutgoingTransitions(State state) {
        List<Transition> outgoingTransitions = new ArrayList<Transition>();
        for (Transition transition : transitions) {
            if(transition.getFromState().equals(state)) {
                outgoingTransitions.add(transition);
            }
        }
        return outgoingTransitions;
    }


    /**TODO
        causes ConcurrentModificationException
    */

    public void removeTransitionsIncidentToState(State state) {
        for (Transition transition : getIncomingTransitions(state)) {
            removeTransition(transition);
        }
        for (Transition transition : getOutgoingTransitions(state)) {
            removeTransition(transition);
        }
    }

    public boolean containsTransition(Transition transition) {
        return transitions.contains(transition);
    }


    public boolean containsTransition(State stateFrom, State stateTo) {
        return getTransitionFromTo(stateFrom, stateTo) != null;
    }



    public String writeAlphabet() {
        Set<String> alphabet = getAlphabet();
        if(alphabet.isEmpty()) {
            return MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder("{");
        for (String symbol : alphabet) {
            sb.append(symbol).append(",");
        }
        return sb.substring(0, sb.length() - 1) + "}";
    }


    public String writeStates() {
        if(states.isEmpty()) {
            return MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder("{");
        for (State state : states) {
            sb.append(state.getName()).append(",");
        }
        return sb.substring(0, sb.length() - 1) + "}";
    }

    public String writeFinalStates() {
        if(!hasAnyAcceptingState()) {
            return MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder("{");
        for (State state : getAcceptingStates()) {
            sb.append(state.getName()).append(",");
        }
        return sb.substring(0, sb.length() - 1) + "}";
    }

    public String writeDefinition() {
        StringBuilder sb = new StringBuilder(getName());
        sb.append(" = (").append(writeStates()).append(", ")
                .append(writeAlphabet()).append(", δ, ")
                .append(hasInitialState() ? getInitialState() : "-").append(", ")
                .append(writeFinalStates()).append(")");
        return sb.toString();

    }

    public String writeTransitions() {
        StringBuilder sb = new StringBuilder();
        for (Transition transition : transitions) {
            for (String label : transition.getLabels()) {
                sb.append("δ(")
                        .append(transition.getFromState().getName())
                        .append(",")
                        .append(label)
                        .append(")")
                        .append(" = ")
                        .append(transition.getToState().getName())
                        .append("\n");
            }
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return writeDefinition() + "\n\n" + writeTransitions();
    }




    public String writeStateCoordinates() {
        StringBuilder sb = new StringBuilder();
        for (State state : states) {
            sb.append(state.getName()).append(":")
                    .append(state.getVisualProperties().getXPos()).append(",")
                    .append(state.getVisualProperties().getYPos()).append("\n");
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<String> getAlphabet() {
        Set alphabetSet = new HashSet<String>();
        for (Transition transition : transitions) {
            alphabetSet.addAll(transition.getLabels());
        }
        alphabetSet.remove(MathConstants.EPSILON);
        return alphabetSet;
    }

//    public void setAlphabet(List<String> alphabet) {
//        this.alphabet = alphabet;
//    }


    public int getStateDegree(State state) {
        return getOutgoingTransitions(state).size() + getIncomingTransitions(state).size();
    }
    
    public boolean isBidirectionalTransition(Transition transition) {
        return getReverseDirectionTransition(transition) != null;
    }

    public Transition getReverseDirectionTransition(Transition transition) {
        return getTransitionFromTo(transition.getToState(), transition.getFromState());
    }

    public int getLongestStateNameLength(){
        int longest = 0;
        for(State state : states){
            if(longest < state.getName().length()){
                longest = state.getName().length();
            }
        }
        return longest;
    }
    
    @Override
    public Representation clone() {
        Automaton automatonObj = new Automaton();
        automatonObj.setName(name);
        automatonObj.setDescription(description);
        for (State state : states) {
            automatonObj.addState((State) state.clone());
        }
        for (Transition transition : transitions) {
            Transition transitionObj = new Transition(
                    automatonObj.getStateByName(transition.getFromState().getName()),
                    automatonObj.getStateByName(transition.getToState().getName()));
            transitionObj.setVisualProperties((TransitionVisualProperties) transition.getVisualProperties().clone());
            SortedSet<String> labelsObj = new TreeSet<String>();
            for (String label : transition.getLabels()) {
                labelsObj.add(label);
            }
            transitionObj.setLabels(labelsObj);
            automatonObj.addTransition(transitionObj);
        }            
        return automatonObj;
    }


    public List<Transition> getTransitionsWithEpsilonStep() {
        List<Transition> list = new ArrayList<Transition>();
        for (Transition transition : getTransitions()) {
            if(transition.containsEpsilonStep()) {
                list.add(transition);
            }
        }
        return list;
    }

    public boolean containsEpsilonSteps() {
        return !getTransitionsWithEpsilonStep().isEmpty();
    }


    public boolean containsNonDeterminism() {
        for (State state : getStates()) {
            SortedSet<String> set = new TreeSet<String>();
            int steps = 0;
            for (Transition transition : getTransitionFrom(state)) {
                steps += transition.getLabels().size();
                set.addAll(transition.getLabels());
                if (steps > set.size()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getType() {
        if(!hasInitialState() || getStates().isEmpty()) {
            return Automaton.UFA;
        }
        if(containsEpsilonSteps()) {
            return Automaton.EFA;
        }
        if(containsNonDeterminism()) {
            return Automaton.NFA;
        }
        return Automaton.DFA;
    }


    public boolean isStringOverAlphabet(String string) {
        Set<String> allowedSymbols = getAlphabet();
        for (int i = 0; i < string.length(); i++) {
            String symbol = String.valueOf(string.charAt(i));
            if(!allowedSymbols.contains(symbol)) {
                return false;
            }
        }
        return true;
    }


    public void layout() {
        GEM gem = new GEM(this);
        gem.arrangeAutomatonForProcedures();
    }



    public void clearHighlighting() {
        for (State state : getStates()) {
            state.getVisualProperties().setFillColor(PropertiesHandler.getInstance().getAutomatonStateFillColor());
            state.getVisualProperties().setFontColor(PropertiesHandler.getInstance().getAutomatonStateFontColor());
            state.getVisualProperties().setStrokeColor(PropertiesHandler.getInstance().getAutomatonStateStrokeColor());

        }
        for (Transition transition : getTransitions()) {
            transition.getVisualProperties().setStrokeColor(PropertiesHandler.getInstance().getAutomatonTransitionStrokeColor());
            transition.getVisualProperties().setFontColor(PropertiesHandler.getInstance().getAutomatonTransitionFontColor());
        }
    }

    public Set<State> getStatesFromUnderExtended(State state, String label) {
        Set<State> set = new HashSet<State>();
        for (Transition transition : getOutgoingTransitions(state)) {
            if(transition.containsLabel(label)) {
                set.add(transition.getToState());
                for (State s : getStatesFromUnderEpsilonExtended(transition.getToState())) {
                    set.add(s);
                }
            }
            if(transition.containsEpsilonStep()) {                
                Set<State> list2 = getStatesFromUnderExtended(transition.getToState(), label);
                for (State state2 : list2) {
                    set.add(state2);
                }
            }
        }
        return set;
    }

    public Set<State> getStatesFromUnderEpsilonExtended(State state) {
        return getStatesFromUnderExtended(state, MathConstants.EPSILON);
    }

    public State makeTransitionFunctionTotal() {
        List<Transition> transitionList = new ArrayList<Transition>();
        State newState = new State("XX");
        addState(newState);
        for (State state : getStates()) {
            for (String string : getAlphabet()) {
                if (!containsTransitionFromUnder(state, string)) {
                    transitionList.add(new Transition(state, newState, string));
                }
            }
        }
        for (Transition transition : transitionList) {
            addTransition(transition);
        }
        for (String s : getAlphabet()) {
            addTransition(newState, newState, s);
        }
        return newState;

    }




}
