/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.grammar;

import java.util.Objects;
import jgaf.Constants.MathConstants;

/**
 * This class serves as Symbol representation. By symbol we mean Terminal,
 * NonTerminal, Epsilon or Special version of T Symbol for LL(k) grammar.
 * @author Empt
 */
public class LLSymbol 
{
    private String value;
    private SymbolType type;

    public LLSymbol(String value,
                  SymbolType type) {
        this.value = value;
        this.type = type;
    }

    public LLSymbol(SymbolType type) 
    {
        if(type == SymbolType.EPSILON)
        {
            this.type = type;
            value = MathConstants.EPSILON;
        }        
    }
    
    public boolean isTerminal()
    {
        return type == SymbolType.TERMINAL;
    }
    
    public boolean isEpsilon()
    {
        return type == SymbolType.EPSILON;
    }
    
    public boolean isNonterminal()
    {
        return type == SymbolType.NONTERMINAL;
    }
    
    public String getValue()
    {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
   
    public String debugString()
    {
        return "Symbol{value="+getValue()+", type="+type+"}";
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.value);
        hash = 23 * hash + (this.type != null ? this.type.hashCode() : 0);
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
        final LLSymbol other = (LLSymbol) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
}
