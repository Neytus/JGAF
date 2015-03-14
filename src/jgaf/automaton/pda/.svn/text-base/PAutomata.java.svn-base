/*
 * PAutomata.java
 *
 * Created on 20. listopad 2007, 10:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 *
 * @author hanis
 */
public class PAutomata {
    
    public static final String EPSILON = "$$eps";
    public static final String FINISH = "$$finish";
    
    private static int duplicateCout = 1;
    private Set<String> states; //Q
    private Set<String> initialStates; //P
    private Set<String> alphabet; //T
    private Set<String> finalStates; //F
    private LinkedList<Triplet> transitionRelation;
    private String dfaInitialState = "";
    
    public PAutomata() {
        this.states = new HashSet<String>();
        this.alphabet = new HashSet<String>();
        this.finalStates = new HashSet<String>();
        this.initialStates = new HashSet<String>();
        this.transitionRelation = new LinkedList<Triplet>();
    }

    private static int getDuplicateCount() {
        return duplicateCout++;
    }
    
    
    public String toString() {
        return "{" + states + "," + alphabet + ",d," + initialStates + "," + finalStates + "} - " + dfaInitialState +"\n" + transitionRelation; 
    }
    
    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }
    
    public void addControlLocation(String state) {
        this.getStates().add(state);
    }

    public Set<String> getInitialStates() {
        return initialStates;
    }

    public void setInitialStates(Set<String> initialStates) {
        this.initialStates = initialStates;
    }

    public void addInitialState(String initialState) {
        this.initialStates.add(initialState);
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void addAlphabet(String alphabet) {
        this.alphabet.add(alphabet);
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }

    public void addFinalStates(String finalState) {
        this.finalStates.add(finalState);
    }

    public LinkedList<Triplet> getTransitionRelation() {
        return transitionRelation;
    }

    
    public void setTransitionRelation(LinkedList<Triplet> transitionRelation) {
        this.transitionRelation = transitionRelation;
    }
    
    
    public void addTransitionRelation(Triplet triplet) {
        this.transitionRelation.add(triplet);
    }
        
    public boolean constaintTransition(Triplet triplet) {
        return this.transitionRelation.contains(triplet);
    }
    
    public LinkedList<Triplet> transitionsWithoutFromP() {
        LinkedList<Triplet> newTransitions = new LinkedList<Triplet>(); 
        for (Triplet triplet : transitionRelation) {
            if(!initialStates.contains(triplet.getStateFrom())) {
               newTransitions.add(triplet);
            }
        }
        return newTransitions;
    }    
    
    public LinkedList<Triplet> convertToWithoutIntoP() {              
        LinkedList<Triplet> newTransitions = new LinkedList<Triplet>();
        Set<String> duplicatedStates = new HashSet<String>();
        for (Triplet triplet : transitionRelation) {
            String stateInto = triplet.getStateTo();
            if(initialStates.contains(stateInto)) {
                if(!duplicatedStates.contains(stateInto)) {
                    duplicatedStates.add(stateInto);
                    String newState = PAutomata.getDuplicateCount() + "$" + stateInto;
                    addControlLocation(newState);
                    for (Triplet trip : transitionRelation) {
                        if(trip.getStateTo().equals(stateInto)) {
                            newTransitions.add(new Triplet(trip.getStateFrom(), trip.getStack(), newState));
                        }
                        if(trip.getStateFrom().equals(stateInto)) {
                            newTransitions.add(new Triplet(newState, trip.getStack(), trip.getStateTo()));
                        }
                    }
                }
            } else {
                newTransitions.add(triplet);
            }
        }
        return newTransitions;
    }

    

    public String duplicateState(String state, LinkedList<Triplet> rel) {
        String newState = PAutomata.getDuplicateCount() + "$" + state;
        states.add(newState);
        List<Triplet> newTriplets = new ArrayList<Triplet>();
        for (Triplet triplet : rel) {
            if(triplet.getStateFrom().equals(state)) {
                transitionRelation.add(new Triplet(newState, triplet.getStack(), triplet.getStateTo()));
            }
        }
        return newState;
    }

    public static boolean containsConfiguration(Configuration configuration, PAutomata pAutomata) {     
        String state = pAutomata.getInitialStates().iterator().next();
        List list = configuration.getStack().AsList();
        for (String stack : (LinkedList<String>)list) {
            boolean contains = false;
            for (Triplet triplet : pAutomata.getTransitionRelation()) {
                if(triplet.getStack().equals(stack) && triplet.getStateFrom().equals(state)) {
                    state = triplet.getStateTo();                   
                    contains = true;
                    break;
                }
            }
            if(!contains) {
                return false;
            }
        }
        return pAutomata.getFinalStates().contains(state);        
    }
    
    
    
    
    private Set<MultiState> differenceSet(Set<MultiState> m1, Set<MultiState> m2) {
        Set<MultiState> diff = new HashSet<MultiState>(m1);
        diff.removeAll(m2);
        return diff;
    }
    
    
    public PAutomata NFAtoDFA(PAutomata nfa) {
        Set<MultiState> newStates = new HashSet<MultiState>();
        MultiState initState = new MultiState();
        for (String state : nfa.getInitialStates()) {
            initState.addState(state);
        }
        newStates.add(initState);
        Set<Triplet> newTransitionRelation = new HashSet<Triplet>();
        Set<MultiState> newFinalStates = new HashSet<MultiState>();
        Set<MultiState> done = new HashSet<MultiState>();
        
        Set<MultiState> diffSet = differenceSet(newStates, done);
        while(!diffSet.isEmpty()) {
            MultiState stateM = diffSet.iterator().next();
            if(stateM.intersectionSet(nfa.getFinalStates())) {
                newFinalStates.add(stateM);
            }
            for(String symbol : nfa.getAlphabet()) {
                MultiState stateN = new MultiState();
                for (Triplet triplet : nfa.getTransitionRelation()) {
                    for (String stateP : stateM.getState()) {
                        if(triplet.getStateFrom().equals(stateP) && triplet.getStack().equals(symbol)) {
                            stateN.addState(triplet.getStateTo());
                        }
                    }
                }
                newStates.add(stateN);
                newTransitionRelation.add(new Triplet(stateM, symbol, stateN));
            }
            done.add(stateM);
            diffSet = differenceSet(newStates, done);
        }
        
        PAutomata dfa = new PAutomata();
        dfa.setAlphabet(nfa.getAlphabet());
        for (MultiState state : newStates) {
            dfa.addControlLocation(state.toString());
        }
        dfa.addInitialState(initState.toString());     
        for (MultiState state : newFinalStates) {
            dfa.addFinalStates(state.toString());
        }
        for (Triplet triplet : newTransitionRelation) {
            dfa.addTransitionRelation(triplet.convertToNormal());
        }
        return dfa;    
    }
    
    
    
    public static PAutomata finishAcceptablePAutomata(PushdownAutomaton automata) {
        PAutomata pAutomata = new PAutomata();       
        for (String st : automata.getStates()) {
            pAutomata.addControlLocation(st);
        }
        
        for (String st : automata.getStackAlphabet()) {
            pAutomata.addAlphabet(st);
        }
        pAutomata.addAlphabet(EPSILON);                
        pAutomata.addFinalStates(FINISH);
        LinkedList<Triplet> trans = new LinkedList<Triplet>();
        
        
        if(automata.getAcceptBy() == automata.FINAL) {
            for (String symbol : automata.getStackAlphabet()) {
                trans.add(new Triplet(FINISH, symbol, FINISH));
                for (String state : automata.getFinalStates()) {
                    pAutomata.addInitialState(state);
                    trans.add(new Triplet(state, FINISH));
                    trans.add(new Triplet(state, symbol, FINISH));
                }
            }
        } else if(automata.getAcceptBy() == automata.EMPTY) {
            for (String state : automata.getStates()) {
                pAutomata.addInitialState(state);
                trans.add(new Triplet(state, FINISH));
            }
        }
        
        pAutomata.setTransitionRelation(trans);
        return pAutomata;
    }
    

    public String getDfaInitialState() {
        return dfaInitialState;
    }

    public void setDfaInitialState(String dfaInitialState) {
        this.dfaInitialState = dfaInitialState;
    }
    

}
