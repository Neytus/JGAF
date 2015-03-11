/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.sll;

import java.util.List;
import jgaf.IA006.grammar.Symbol;

/**
 *
 * @author Empt
 */
public class SLLState {
    Symbol tHead;
    List<Symbol> rule;

    public Symbol gettHead() {
        return tHead;
    }

    public void settHead(Symbol tHead) {
        this.tHead = tHead;
    }

    public List<Symbol> getRule() {
        return rule;
    }

    public void setRule(
                        List<Symbol> rule) {
        this.rule = rule;
    }
    
    
}
