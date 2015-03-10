/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton;

import java.io.Serializable;
import jgaf.automaton.visual.StateVisualProperties;

/**
 *
 * @author hanis
 */
public class State implements Serializable, Cloneable {

    //public static final String EPSILON = "\u03b5";

    private final long serialVersionUID = 10000000000001004L;

    private String name;

    private boolean accepting;
    private boolean initial;

    private StateVisualProperties visualProperties;


    public State(String name, int xPos, int yPos) {
        this.name = name;
        this.visualProperties = new StateVisualProperties();
        this.visualProperties.setCoordinates(xPos, yPos);
    }

    public State(String name) {
        this(name, 0, 0);
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccepting() {
        return accepting;
    }

    public void setAccepting(boolean accepting) {
        this.accepting = accepting;
    }

    public boolean isInitial() {
        return initial;
    }

    void setInitial(boolean initial) {
        this.initial = initial;
    }



    public StateVisualProperties getVisualProperties() {
        return visualProperties;
    }

    public void setVisualProperties(StateVisualProperties visualProperties) {
        this.visualProperties = visualProperties;
    }



    @Override
    public Object clone() {
        State obj = new State(getName());
        obj.setAccepting(accepting);
        obj.setInitial(initial);
        obj.setVisualProperties((StateVisualProperties) visualProperties.clone());
        return obj;
    }



    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof State) {
           State state = (State)obj;
           if(state.getName().equals(name)){
                return true;
           }
        }
        return false;
    }


    public boolean equalsUpToInitial(State state) {
        if(state == null) {
            return false;
        }
        if(state.getName().equals(name)
                && state.isAccepting() == isAccepting()
                && state.getVisualProperties().equals(getVisualProperties())) {
            return true;
        }
        return false;

    }


    public void modify(State state) {
        setAccepting(state.isAccepting());
        setInitial(state.isInitial());
        setName(state.getName());
        getVisualProperties().modify(state.getVisualProperties());
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }



}
