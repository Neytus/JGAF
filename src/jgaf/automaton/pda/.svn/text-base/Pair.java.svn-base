/*
 * Pair.java
 *
 * Created on 27. srpen 2007, 20:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hanis
 */
public class Pair implements Comparable<Pair>, Cloneable {
    
    
    private String state;
    private List<String> stackSymbols;    
    private boolean epsilonStack;

    public Pair() {
    }

    public Pair(String state, List<String> stackSymbols) {
        this.state = state;
        this.stackSymbols = new ArrayList<String>(stackSymbols);
        setEpsilonStack(false);
    }

    public Pair(String state, String stackSymbol) {
        this.state = state;
        this.stackSymbols = new ArrayList<String>();
        this.stackSymbols.add(stackSymbol);
        setEpsilonStack(false);
    }    
    
    public Pair(String state) {
        this.state = state;
        this.stackSymbols = new ArrayList<String>();
        setEpsilonStack(true);
    }    
    
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getStackSymbols() {
        return stackSymbols;
    }

    public void setStackSymbols(ArrayList<String> stackSymbols) {
        this.stackSymbols = stackSymbols;
    }
    
    public String toString() {
        String pairs;;
        if(isEpsilonStack()) {
            pairs = String.valueOf(DefaultValues.getInstance().getEpsilon());
        } else {
           StringBuilder sb = new StringBuilder();
           for (String symbol : stackSymbols) {
               sb.append(symbol);
           }
           pairs = sb.toString();
        }
        
        return "(" + state + "," + pairs +  ")";
    }    
    
    public String writeStack() {
        if(!isEpsilonStack()) {
            StringBuilder sb = new StringBuilder();
            for (String symbol : stackSymbols) {
                if(symbol.length() > 1) {
                    sb.append(AutomataConstants.SEPARATOR).append(symbol).append(AutomataConstants.SEPARATOR);
                } else {
                    sb.append(symbol);
                }
            }
            return sb.toString();
        }
        return "";
    }
    
    public boolean equals (Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return this.state.equals(pair.getState()) && this.stackSymbols.equals(pair.getStackSymbols());
    }
    
   public int hashCode() {
        return 13 + 17 * state.hashCode() + 17 * stackSymbols.hashCode(); 
   }

    public int compareTo(Pair obj) {
        int comp = this.state.compareTo(obj.getState());
        if(comp == 0) {
            comp = this.stackSymbols.toString().compareTo(obj.getStackSymbols().toString());
        }
        return comp;
    }    

    public boolean isEpsilonStack() {
        return epsilonStack;
    }

    public void setEpsilonStack(boolean epsilonStack) {
        this.epsilonStack = epsilonStack;
    }

    @Override
    public Object clone() {
        if(isEpsilonStack()) {
            return new Pair(state);
        }
        List<String> list = new ArrayList<String>();
        for (String string : getStackSymbols()) {
            list.add(string);
        }
        return new Pair(state, list);
    }

    
    
    
}
