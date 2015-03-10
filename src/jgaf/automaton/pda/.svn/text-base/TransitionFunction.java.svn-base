/*
 * TransitionFunction.java
 *
 * Created on 27. srpen 2007, 15:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author hanis
 */
public class TransitionFunction implements Cloneable {
             
    
    SortedSet<Transition> transitions;
    
    
    public TransitionFunction() {
        transitions = new TreeSet<Transition>();
    }
    
    public SortedSet<Transition> getTransitions() {
        return transitions;
    }
    
    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }        
    
    
    public String toString() {
        StringBuilder transitionSet = new StringBuilder();
        for (Transition transition : transitions) {
            transitionSet.append(transition);
            transitionSet.append("\n");
        }
        return transitionSet.toString();
    }            
    

    public void renameState(String oldState, String newState) {
        for (Transition transition : transitions) {
            if(transition.getTernary().getState().equals(oldState)) {
                transition.getTernary().setState(newState);
            }
            for (Pair pair : transition.getPairs()) {
                if(pair.getState().equals(oldState)) {
                    pair.setState(newState);
                }
            }
        }
    }


    public void removeState(String state) {
        SortedSet<Transition> removingTransitions = new TreeSet<Transition>();
        for (Transition transition : transitions) {
            if(transition.getTernary().getState().equals(state)) {
                removingTransitions.add(transition);
                continue;
            }
            for (Pair pair : transition.getPairs()) {
                if(pair.getState().equals(state)) {
                    removingTransitions.add(transition);
                    break;
                }
            }
        }
        for (Transition transition : removingTransitions) {
            transitions.remove(transition);    
        }        
    }
    
    
    public void renameStack(String oldSymbol, String newSymbol) {
        for (Transition transition : transitions) {
            if(transition.getTernary().getStackSymbol().equals(oldSymbol)) {
                transition.getTernary().setStackSymbol(newSymbol);
            }
            for (Pair pair : transition.getPairs()) {
                for (int i = 0; i < pair.getStackSymbols().size(); i++) {
                    if(pair.getStackSymbols().get(i).equals(oldSymbol)) {
                        pair.getStackSymbols().set(i, newSymbol);
                    }
                }
            }
        }
    }
    
    
    public void removeStack(String stack) {
        SortedSet<Transition> removingTransitions = new TreeSet<Transition>();
        for (Transition transition : transitions) {
            if(transition.getTernary().getStackSymbol().equals(stack)) {
                removingTransitions.add(transition);
                continue;
            }
            boolean found = false;
            for (Pair pair : transition.getPairs()) {
                for (int i = 0; i < pair.getStackSymbols().size(); i++) {
                    if(pair.getStackSymbols().get(i).equals(stack)) {
                        found = true;
                        removingTransitions.add(transition);
                        break;
                    }
                }
                if(found) {
                    break;
                }
            }
        }
        for (Transition transition : removingTransitions) {
            transitions.remove(transition);    
        }        
    }    
    
    public void renameSymbol(char oldSymbol, char newSymbol) {
        for (Transition transition : transitions) {
            if(transition.getTernary().getSymbol() == oldSymbol) {
                transition.getTernary().setSymbol(newSymbol);
            }
        }
    }
    
    
    public void removeSymbol(char symbol) {
        SortedSet<Transition> removingTransitions = new TreeSet<Transition>();
        for (Transition transition : transitions) {
            if(transition.getTernary().getSymbol() == symbol) {
                removingTransitions.add(transition);
            }
        }
        for (Transition transition : removingTransitions) {
            transitions.remove(transition);    
        }        
    }    
    
    
    public Set<Transition> getMatchTransitions(Configuration configuration) {
        Set<Transition> matchTransition = new HashSet<Transition>();
        Ternary ternary;
        try {
            ternary = configuration.getTernary();
        } catch(NoSuchElementException ex) {
            return matchTransition;
        }
        for (Transition transition : this.transitions) {
            if(transition.match(ternary)) {
                matchTransition.add(transition);
            }
        }                
        return matchTransition;
    }
            
    public Set<Transition> getTransitionsUnder(char symbol) {
        Set<Transition> transitionUnder = new HashSet<Transition>();
        for (Transition transition : transitions) {
            if(!transition.isEpsilon() && transition.getTernary().getSymbol() == symbol) {
                transitionUnder.add(transition);
            }
        }
        return transitionUnder;
    }        
    
    public Set<Transition> getEpsilonTransitions() {
        Set<Transition> transitionUnder = new HashSet<Transition>();
        for (Transition transition : transitions) {
            if(transition.isEpsilon()) {
                transitionUnder.add(transition);
            }
        }
        return transitionUnder;
    } 

    public Set<Transition> getEpsilonTransitions(PAutomata pAutomata) {
        Set<Transition> transitionUnder = new HashSet<Transition>();
        for (Transition transition : transitions) {
            if(transition.isEpsilon() && pAutomata.getInitialStates().contains(transition.getTernary().getState())) {
                transitionUnder.add(transition);
            }
        }
        return transitionUnder;
    } 


    public boolean isEmpty() {
        return transitions.isEmpty();
    }
    
    public SortedSet<Ternary> getTernaries() {
        SortedSet<Ternary> ternaries = new TreeSet<Ternary>();
        for (Transition trans : transitions) {
            ternaries.add(trans.getTernary());
        }        
        return ternaries;
    }
    
    public SortedSet<Pair> getPairs(Ternary ternary) {
        for (Transition trans : transitions) {            
            if(trans.getTernary().equals(ternary)) {
                return trans.getPairs();
            }
        }
        return null;
    }
    
    public Transition getTransition(Ternary ternary) {
        for (Transition trans : transitions) {
            if(trans.getTernary().equals(ternary)) {
                return trans;
            }
        }
        return null;
    }
    
    
    public void removeTransition(Ternary ternary) {
        transitions.remove(getTransition(ternary));
    }
    
    public void removeTransition(Ternary ternary, Pair pair) {
        Transition trans = getTransition(ternary);
        trans.getPairs().remove(pair);
        if(trans.getPairs().isEmpty()) {
            transitions.remove(trans);
        }
    }
    
//    
//    public void modifyTransition(Ternary oldTernary,Ternary newTernary, Pair oldPair, Pair newPair, boolean global) {
//        if(global) {
//            Transition existTrans = getTransition(newTernary);
//            Transition trans = getTransition(oldTernary);
//            trans.getPairs().remove(oldPair);
//            trans.getPairs().add(newPair);        
//            if(existTrans == null) {
//                oldTernary.setState(newTernary.getState());
//                oldTernary.setStackSymbol(newTernary.getStackSymbol());
//                oldTernary.setEpsilon(newTernary.isEpsilon());
//                if(!newTernary.isEpsilon()) {
//                    oldTernary.setSymbol(newTernary.getSymbol());
//                }
//            } else {
//                for (Pair pair : trans.getPairs()) {
//                    existTrans.addPair(pair);
//                }
//                transitions.remove(trans);
//            }            
//        } else {
//            Transition existTrans = getTransition(newTernary);
//            Transition trans = getTransition(oldTernary);
//            if(existTrans == null) {
//                existTrans = new Transition();
//                existTrans.setTernary(newTernary);                
//                transitions.add(existTrans);
//            }
//            existTrans.addPair(newPair);
//            trans.getPairs().remove(oldPair);
//            if(trans.getPairs().isEmpty()) {
//                transitions.remove(trans);
//            }
//        }                
//    }


    //rename the part of transition
    public void modifyTransition(Ternary oldTernary,Ternary newTernary, Pair oldPair, Pair newPair) {
            Transition existTrans = getTransition(newTernary);
            Transition trans = getTransition(oldTernary);
            if(existTrans == null) {
                existTrans = new Transition();
                existTrans.setTernary(newTernary);                
                transitions.add(existTrans);
            }
            existTrans.addPair(newPair);
            trans.getPairs().remove(oldPair);
            if(trans.getPairs().isEmpty()) {
                transitions.remove(trans);
            }
                      
    }
    
    
    //rename left side of transition
    public void modifyTransition(Ternary oldTernary,Ternary newTernary) {
            Transition existTrans = getTransition(newTernary);
            Transition trans = getTransition(oldTernary);      
            if(existTrans == null) {
                oldTernary.setState(newTernary.getState());
                oldTernary.setStackSymbol(newTernary.getStackSymbol());
                oldTernary.setEpsilon(newTernary.isEpsilon());
                if(!newTernary.isEpsilon()) {
                    oldTernary.setSymbol(newTernary.getSymbol());
                }
            } else {
                for (Pair pair : trans.getPairs()) {
                    existTrans.addPair(pair);
                }
                transitions.remove(trans);
            }            
    }
            
    
    //rename right side of transition
    public void modifyTransition(Ternary ternary, Pair oldPair, Pair newPair) {
        Transition trans = getTransition(ternary);
        trans.addPair(newPair);
        trans.getPairs().remove(oldPair);
        if(trans.getPairs().isEmpty()) {
            transitions.remove(trans);
        }
    }
    


    @Override
    public Object clone() {
        TransitionFunction obj = new TransitionFunction();
        for (Transition transition : getTransitions()) {
            obj.addTransition((Transition) transition.clone());
        }
        return obj;
    }

    
    
}
