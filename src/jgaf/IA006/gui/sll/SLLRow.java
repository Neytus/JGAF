/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.sll;

import java.util.List;
import java.util.Set;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class SLLRow 
{
    private LLSymbol s;
    private List<LLSymbol> rule;
    private Set<List<LLSymbol>> fifo;

    public LLSymbol getS() {
        return s;
    }

    public void setS(LLSymbol s) {
        this.s = s;
    }

    public List<LLSymbol> getRule() {
        return rule;
    }

    public void setRule(
                        List<LLSymbol> rule) {
        this.rule = rule;
    }

    public Set<List<LLSymbol>> getFifo() {
        return fifo;
    }

    public void setFifo(
                        Set<List<LLSymbol>> fifo) {
        this.fifo = fifo;
    }
    
    
    
}
