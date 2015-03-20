/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import jgaf.Constants.MathConstants;

/**
 *
 * @author hanis
 */
public class Symbol implements Comparable<Symbol>, Cloneable {

    public static final int TERMINAL = 0;
    public static final int NONTERMINAL = 1;
    public static final int EPSILON = 2;
    //public static final String EPSYLON = "\u03b5";

    private int type;
    private String name;

    public Symbol(String name, int type) {
        this.type = type;
        this.name = name;
    }

    public Symbol() {
        this.type = EPSILON;
        this.name = MathConstants.EPSILON;
    }

    public int getType() {
        return type;
    }

    public boolean isTerminal() {
        return type == TERMINAL;
    }


    public boolean isNonterminal() {
        return type == NONTERMINAL;
    }

    public boolean isEpsilon() {
        return type == EPSILON;
    }


    @Override
    public String toString() {
        return name;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof Symbol) {
           Symbol state = (Symbol)obj;
           if(state.getName().equals(name)){
                return true;
           }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Symbol s) {
        return name.compareTo(s.getName());
    }


    @Override
    public Object clone() {
        Symbol obj = new Symbol(getName(), getType());
        return obj;
    }

    public void setType(int type) {
        this.type = type;
    }


}
