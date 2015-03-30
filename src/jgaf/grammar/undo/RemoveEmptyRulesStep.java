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
public class RemoveEmptyRulesStep implements UndoRedoStep {


    private Grammar grammar;
    private int number;

    public RemoveEmptyRulesStep(Grammar grammar, int number) {
        this.grammar = grammar;
        this.number = number;
    }

    public void undo() {
        for (int i = 0; i < number; i++) {
            grammar.addRule(new ProductionRules());
        }
    }

    public void redo() {
        grammar.removeEmptyRules();
    }

    public String type() {
        return "RemoveEmptyRulesStep";
    }
}
