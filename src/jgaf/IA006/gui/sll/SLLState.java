/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.sll;

import java.util.List;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class SLLState {
    LLSymbol tHead;
    List<LLSymbol> rule;

    public LLSymbol gettHead() {
        return tHead;
    }

    public void settHead(LLSymbol tHead) {
        this.tHead = tHead;
    }

    public List<LLSymbol> getRule() {
        return rule;
    }

    public void setRule(
                        List<LLSymbol> rule) {
        this.rule = rule;
    }
    
    
}
