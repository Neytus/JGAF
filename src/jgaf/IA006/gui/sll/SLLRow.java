/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.sll;

import java.util.List;
import java.util.Set;
import jgaf.IA006.grammar.Symbol;

/**
 *
 * @author Empt
 */
public class SLLRow 
{
    private Symbol s;
    private List<Symbol> rule;
    private Set<List<Symbol>> fifo;

    public Symbol getS() {
        return s;
    }

    public void setS(Symbol s) {
        this.s = s;
    }

    public List<Symbol> getRule() {
        return rule;
    }

    public void setRule(
                        List<Symbol> rule) {
        this.rule = rule;
    }

    public Set<List<Symbol>> getFifo() {
        return fifo;
    }

    public void setFifo(
                        Set<List<Symbol>> fifo) {
        this.fifo = fifo;
    }
    
    
    
}
