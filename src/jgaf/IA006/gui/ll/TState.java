/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.List;
import java.util.Map;
import jgaf.IA006.grammar.Symbol;

/**
 *
 * @author Empt
 */
public class TState 
{
    Symbol tHead;
    List<Symbol> rule;
    List<Symbol> originalRule;
    Symbol leftSide;

    public Symbol getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(Symbol leftSide) {
        this.leftSide = leftSide;
    }

    public List<Symbol> getOriginalRule() {
        return originalRule;
    }

    public void setOriginalRule(
                                List<Symbol> originalRule) {
        this.originalRule = originalRule;
    }
    
    

    public TState(Symbol tHead,
                  List<Symbol> rule) {
        this.tHead = tHead;
        this.rule = rule;
    }
    

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
    
    public String toString()
    {
        return rule.toString();
    }
    
    
    
}
