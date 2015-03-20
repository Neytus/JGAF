/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar.undo;

import jgaf.automaton.fa.undo.UndoRedoStep;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRules;

/**
 *
 * @author hanis
 */
public class RemoveRuleStep implements UndoRedoStep {

    private int index;
    private ProductionRules rule;
    private Grammar grammar;

    public RemoveRuleStep(Grammar grammar, ProductionRules rule, int index) {
        this.index = index;
        this.rule = rule;
        this.grammar = grammar;
    }

    public void undo() {
        grammar.addRule(index, rule);
    }

    public void redo() {
        grammar.removeRule(rule);
    }

    public String type() {
        return "RemoveRuleStep";
    }
}
