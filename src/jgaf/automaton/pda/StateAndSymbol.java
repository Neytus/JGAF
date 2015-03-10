/*
 * StateAndSymbol.java
 *
 * Created on 27. srpen 2007, 23:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

/**
 *
 * @author hanis
 */
public class StateAndSymbol implements Comparable<StateAndSymbol> {
    
    private String state;
    private boolean epsilon;
    
    private char symbol;
    public StateAndSymbol() {
    }
    
    public StateAndSymbol(String actualState, char inputSymbol) {
        this.state = actualState;
        this.symbol = inputSymbol;
        setEpsilon(false);
    }

    public StateAndSymbol(String actualState) {
        this.state = actualState;
        setEpsilon(true);
    }    
    
    public String getState() {
        return state;
    }
    
    public void setState(String currentState) {
        this.state = currentState;
    }
    
    public char getSymbol() {
        return symbol;
    }
    
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
    
    public String toString() {
        return "(" + getState() + "," + (isEpsilon() ? DefaultValues.getInstance().getEpsilon() : getSymbol()) + ")";
    }
        
    
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof StateAndSymbol)) {
            return false;
        }
        StateAndSymbol sas = (StateAndSymbol) obj;
        return this.state.equals(sas.getState())
                && ((this.isEpsilon() && sas.isEpsilon()) || (!this.isEpsilon() && !sas.isEpsilon() && this.symbol == sas.symbol));
    }
    
    public int hashCode() {
        return 17 + 13 * state.hashCode() + (isEpsilon() ? 0 : 19 * symbol);
    }
    
    public int compareTo(StateAndSymbol obj) {
        int comp = this.state.compareTo(obj.getState());
        if(comp == 0) {
            comp = (isEpsilon() ? 0 : this.symbol) - (obj.isEpsilon() ? 0 : obj.getSymbol());
        }
        return comp;
    }
        
    
    public boolean isEpsilon() {
        return epsilon;
    }

    public void setEpsilon(boolean epsilon) {
        this.epsilon = epsilon;
    }
    
    
}
