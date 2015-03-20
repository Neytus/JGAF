/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.List;
import java.util.Map;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class TState 
{
    LLSymbol tHead;
    List<LLSymbol> rule;
    List<LLSymbol> originalRule;
    LLSymbol leftSide;

    public LLSymbol getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(LLSymbol leftSide) {
        this.leftSide = leftSide;
    }

    public List<LLSymbol> getOriginalRule() {
        return originalRule;
    }

    public void setOriginalRule(
                                List<LLSymbol> originalRule) {
        this.originalRule = originalRule;
    }
    
    

    public TState(LLSymbol tHead,
                  List<LLSymbol> rule) {
        this.tHead = tHead;
        this.rule = rule;
    }
    

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
    
    public String toString()
    {
        return rule.toString();
    }
    
    
    
}
