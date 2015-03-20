/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Constants.MathConstants;
import jgaf.automaton.visual.TransitionVisualProperties;

/**
 *
 * @author hanis
 */
public class Transition implements Serializable, Cloneable {

    private final long serialVersionUID = 10000000000001003L;
    
//    public static final String EPSILON = "\u03b5";
    
    

    private State from;
    private State to;
    private SortedSet<String> labels;

    private TransitionVisualProperties visualProperties;

    public Transition(State from, State to) {
        init(from, to);
        labels = new TreeSet<String>();
    }

    public Transition(State from, State to, SortedSet<String> labels) {
        init(from, to);
        this.labels = labels;
    }

    public Transition(State from, State to, String label) {
        init(from, to);
        labels = new TreeSet<String>();
        labels.add(label);
    }
    


    private void init(State from, State to) {
        this.from = from;
        this.to = to;
        this.visualProperties = new TransitionVisualProperties();
    }


//    public void remove() {
//        getFromState().getAutomaton().removeTransition(this);
//    }

    public boolean addLabel(String label) {
        return labels.add(label);
    }

    public boolean removeLabel(String label) {
        return labels.remove(label);
    }

    public void addLabels(Set<String> labels) {
        for (String label : labels) {
            addLabel(label);
        }
    }

    public boolean containsLabel(String label) {
        return labels.contains(label);
    }


    public boolean isReflexive() {
        return getFromState().equals(getToState());
    }


    @Override
    public String toString() {
        return getFromState() + " -" + writeLabels() + "-> " + getToState();
    }
    
    public String writeLabels() {
        if(getLabels().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String label : getLabels()) {
            sb.append(label).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }


    public State getFromState() {
        return from;
    }

    public void setFromState(State from) {
        this.from = from;
    }

    public State getToState() {
        return to;
    }

    public void setToState(State to) {
        this.to = to;
    }



    public SortedSet<String> getLabels() {
        return labels;
    }

    public boolean setLabels(SortedSet<String> labels) {
        if(getLabels().equals(labels)) {
            return false;
        }
        this.labels = labels;
        return true;
    }


    public TransitionVisualProperties getVisualProperties() {
        return visualProperties;
    }

    public void setVisualProperties(TransitionVisualProperties visualProperties) {
        this.visualProperties = visualProperties;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if (obj instanceof Transition) {
            Transition t = (Transition) obj;
            if (t.getFromState().equals(this.getFromState()) && t.getToState().equals(this.getToState())) {
                return true;
            }
        }
        return false;
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.from != null ? this.from.hashCode() : 0);
        hash = 37 * hash + (this.to != null ? this.to.hashCode() : 0);
        return hash;
    }



    public boolean containsEpsilonStep() {
        for (String step : labels) {
            if(MathConstants.EPSILON.equals(step)) {
                return true;
            }
        }
        return false;
    }




}
