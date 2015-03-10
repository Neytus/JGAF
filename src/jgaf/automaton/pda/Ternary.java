package jgaf.automaton.pda;
/*
 * Ternary.java
 *
 * Created on 27. srpen 2007, 17:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author hanis
 */
public class Ternary implements Comparable<Ternary>, Cloneable {
    
    private String state;
    private char symbol;
    private String stackSymbol;
    private boolean epsilon;
    
    public Ternary() {
    }
    
    public Ternary(String actualState, char inputSymbol, String topOfStack) {
        this.state = actualState;
        this.symbol = inputSymbol;
        this.stackSymbol = topOfStack;
        setEpsilon(false);
    }  
    
    public Ternary(String actualState, String topOfStack) {
        this.state = actualState;
        this.stackSymbol = topOfStack;
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
    
    public String getStackSymbol() {
        return stackSymbol;
    }
    
    public void setStackSymbol(String stackSymbol) {
        this.stackSymbol = stackSymbol;
    }
    
    
    public StateAndSymbol getStateAndSymbol() {
        if(isEpsilon()) {
            return new StateAndSymbol(state);        
        } else {
            return new StateAndSymbol(state, symbol);        
        }
    }
    
    public boolean hasStackSymbol(String symbol) {
        return this.stackSymbol == symbol;
    }
    
    
    public String toString() {
        return "(" + state + "," + (isEpsilon() ? DefaultValues.getInstance().getEpsilon() : symbol) + "," + stackSymbol +  ")";
    }

    public boolean equalsWithEpsilon(Ternary ternary) {
        if(this.state.equals(ternary.getState()) && this.stackSymbol.equals(ternary.getStackSymbol())) {
            if(!this.epsilon) {
                return this.symbol == ternary.getSymbol();
            } else {
                return true;
            }
        }
        return false;
    }
    
    
    
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Ternary)) {
            return false;
        }
        Ternary ternary = (Ternary) obj;
        return this.state.equals(ternary.state)
        && ((this.isEpsilon() && ternary.isEpsilon()) || (!this.isEpsilon() && !ternary.isEpsilon() && this.symbol == ternary.symbol))
        && this.stackSymbol.equals(ternary.getStackSymbol());
    }
    
    public int hashCode() {
        return 17 + 13 * state.hashCode() + (isEpsilon() ? 0 : 17 * symbol) + 19 * stackSymbol.hashCode();
    }
    
    public int compareTo(Ternary obj) {
        int comp = this.state.compareTo(obj.getState());
        if(comp == 0) {
            comp = (isEpsilon() ? 0 : this.symbol) - (obj.isEpsilon() ? 0 : obj.getSymbol());
            if(comp == 0) {
               comp = this.stackSymbol.compareTo(obj.getStackSymbol());
            }
        }
        return comp;
    }

    public boolean isEpsilon() {
        return epsilon;
    }

    public void setEpsilon(boolean epsilon) {
        this.epsilon = epsilon;
    }




    @Override
    public Object clone() {
        if(isEpsilon()) {
            return new Ternary(state, stackSymbol);
        }
        return new Ternary(state, symbol, stackSymbol);
    }
    
    
}
