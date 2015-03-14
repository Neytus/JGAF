/*
 * DefaultValues.java
 *
 * Created on 1. prosinec 2007, 19:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

/**
 *
 * @author hanis
 */
public class DefaultValues {

    private static DefaultValues instance;
    
    private String epsilon = "ε";
    private String statesSet = "Q";
    private String stackAlphabet = "Γ";
    private String inputAlphabet = "Σ";
    private String finalStates = "F";
    private String transition = "δ";
    
    private String name = "M";
    private String description = "";
    private int delay = 300;
    private int accepting = 1;
    
    private DefaultValues() {        
    }
    
    public static DefaultValues getInstance() {
        if(instance == null) {
            instance = new DefaultValues();
        }
        return instance;
    }

    public String getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(String epsilon) {
        this.epsilon = epsilon;
    }

    public String getStatesSet() {
        return statesSet;
    }

    public void setStatesSet(String statesSet) {
        this.statesSet = statesSet;
    }

    public String getStackAlphabet() {
        return stackAlphabet;
    }

    public void setStackAlphabet(String stackAlphabet) {
        this.stackAlphabet = stackAlphabet;
    }

    public String getInputAlphabet() {
        return inputAlphabet;
    }

    public void setInputAlphabet(String inputAlphabet) {
        this.inputAlphabet = inputAlphabet;
    }

    public String getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(String finalStates) {
        this.finalStates = finalStates;
    }

    public String getTransition() {
        return transition;
    }

    public void setTransition(String transition) {
        this.transition = transition;
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

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getAccepting() {
        return accepting;
    }

    public void setAccepting(int accepting) {
        this.accepting = accepting;
    }
    
}
