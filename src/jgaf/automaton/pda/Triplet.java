/*
 * Triplet.java
 *
 * Created on 20. listopad 2007, 11:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

/**
 *
 * @author hanis
 */
public class Triplet {
    
    public static final int NORMAL = 0;
    public static final int EXTEND = 1;
    
    private String stateFrom;
    private String stateTo;
    private String stack;
    private MultiState extendStateTo;
    private MultiState extendStateFrom;
    
    private boolean epsilon = false;
    
    private int mode;
    
    public Triplet() {
        setMode(NORMAL);
    }
    
    public Triplet(String stateFrom, String stateTo) {
        this.stack = PAutomata.EPSILON;
        this.epsilon = true;
        this.stateFrom = stateFrom;
        this.stateTo = stateTo;
        setMode(NORMAL);
    }

    
    public Triplet(String stateFrom, String stack, String stateTo) {
        this.stack = stack;
        this.stateFrom = stateFrom;
        this.stateTo = stateTo;
        setMode(NORMAL);
    }
        
    public Triplet(MultiState extendStateFrom, String stack, MultiState extendStateTo) {
        this.stack = stack;
        this.extendStateFrom = extendStateFrom;
        this.extendStateTo = extendStateTo;
        setMode(EXTEND);
    }

    public Triplet convertToNormal() {
        setStateFrom(getExtendStateFrom().toString());
        setStateTo(getExtendStateTo().toString());
        setMode(NORMAL);
        return this;
    }
    
    public String getStateFrom() {
        return stateFrom;
    }

    public void setStateFrom(String stateFrom) {
        this.stateFrom = stateFrom;
    }

    public String getStateTo() {
        return stateTo;
    }

    public void setStateTo(String stateTo) {
        this.stateTo = stateTo;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
    
    public boolean equals(Object o) {
        if(!(o instanceof Triplet)) {
            return false;
        }
        Triplet obj = (Triplet) o;  
        if(mode == NORMAL) {
            return (obj.getStack().equals(stack) && obj.getStateFrom().equals(stateFrom) && obj.getStateTo().equals(stateTo)
                && obj.isEpsilon() == epsilon);
        } else {
            return (obj.getStack().equals(stack) && obj.getExtendStateFrom().equals(extendStateFrom) && obj.getExtendStateTo().equals(extendStateTo)
                && obj.isEpsilon() == epsilon);
        }
               
    }
    
    public int hashCode() {
        if(mode == NORMAL) {
            return 13 + stack.hashCode() * 17 + stateFrom.hashCode() * 13 + stateTo.hashCode() * 19;
        } else {
            return 13 + stack.hashCode() * 17 + extendStateFrom.hashCode() * 13 + extendStateTo.hashCode() * 19;
        }
    }
    
    public String toString() {
        if(mode == NORMAL) {
            return "(" + stateFrom + "," + stack + "," + stateTo + ")";
        } else {
            return "(" + extendStateFrom + "," + stack + "," + extendStateTo + ")";
        }
    }

    public MultiState getExtendStateTo() {
        return extendStateTo;
    }

    public void setExtendStateTo(MultiState extendStateTo) {
        this.extendStateTo = extendStateTo;
    }

    public MultiState getExtendStateFrom() {
        return extendStateFrom;
    }

    public void setExtendStateFrom(MultiState extendStateFrom) {
        this.extendStateFrom = extendStateFrom;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isEpsilon() {
        return epsilon;
    }

    public void setEpsilon(boolean epsilon) {
        this.epsilon = epsilon;
    }

}
