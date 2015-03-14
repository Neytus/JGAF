/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.List;
import java.util.Set;
import jgaf.IA006.grammar.Symbol;
import jgaf.IA006.grammar.SymbolType;

/**
 *
 * @author Empt
 */
public class TSymbol extends Symbol
{
    private int stateID;
    private Symbol nonTerminal;
    private Set<List<Symbol>> follow;

    public TSymbol(int stateID,
                   Symbol nonTerminal,
                   Set<List<Symbol>> follow) {
        super("T", SymbolType.NONTERMINAL);
        this.stateID = stateID;
        this.nonTerminal = nonTerminal;
        this.follow = follow;
    }

    public int getStateID() {
        return stateID;
    }

    public Symbol getNonTerminal() {
        return nonTerminal;
    }

    public Set<List<Symbol>> getFollow() {
        return follow;
    }

    public TSymbol(String value, SymbolType type) {
        super(value, type);
    }
    
    public TSymbol(int stateID)
    {        
        this("T",SymbolType.TERMINAL);
        this.stateID = stateID;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.stateID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TSymbol other = (TSymbol) obj;
        if (this.stateID != other.stateID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString()+"_"+stateID;
    }
}
