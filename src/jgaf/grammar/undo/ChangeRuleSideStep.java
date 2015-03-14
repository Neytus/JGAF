/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar.undo;

import jgaf.automaton.fa.undo.UndoRedoStep;
import jgaf.grammar.ProductionRulesSide;

/**
 *
 * @author hanis
 */
public class ChangeRuleSideStep implements UndoRedoStep {

    private ProductionRulesSide oldRuleSide;
    private ProductionRulesSide newRuleSide;
    private ProductionRulesSide ruleSide;

    public ChangeRuleSideStep(ProductionRulesSide ruleSide,
            ProductionRulesSide oldRuleSide, ProductionRulesSide newRuleSide) {
        this.ruleSide = ruleSide;
        this.oldRuleSide = oldRuleSide;
        this.newRuleSide = newRuleSide;
    }

    public void undo() {
        ruleSide.setRules(oldRuleSide.getRules());
    }

    public void redo() {
        ruleSide.setRules(oldRuleSide.getRules());
    }

    public String type() {
        return "ChangeRuleSideStep";
    }
}
