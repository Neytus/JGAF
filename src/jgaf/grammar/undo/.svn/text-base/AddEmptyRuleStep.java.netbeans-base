/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar.undo;

import jgaf.automaton.fa.undo.UndoRedoStep;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRule;

/**
 *
 * @author hanis
 */
public class AddEmptyRuleStep implements UndoRedoStep {


    private Grammar grammar;

    public AddEmptyRuleStep(Grammar grammar) {
        this.grammar = grammar;
    }

    public void undo() {
        grammar.removeLastEmptyRule();
    }

    public void redo() {
        grammar.addRule(new ProductionRule());
    }

    public String type() {
        return "AddEmptyStep";
    }
}
