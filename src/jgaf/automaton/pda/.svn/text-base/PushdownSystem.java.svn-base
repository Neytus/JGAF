/*
 * PushdownSystem.java
 *
 * Created on 4. prosinec 2007, 12:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author hanis
 */
public class PushdownSystem {
    
    private Set<String> states; 
    private Set<String> alphabet; 
    private Set<Transition> transitions;
    
    public PushdownSystem() {
        transitions = new HashSet<Transition>();
    }

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states) {
        this.states = new HashSet<String>(states);
    }

    public void addState(String states) {
        this.states.add(states);
    }
    
    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public Set<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(Set<Transition> transitions) {
        this.transitions = transitions;
    }

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }
        
    public String toString() {
        return "states: " + states + "\nalphabet: " + alphabet + "\n" + transitions;
    }
    
    
}
