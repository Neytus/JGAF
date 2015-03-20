/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar.undo;

import jgaf.automaton.fa.undo.UndoRedoStep;
import jgaf.grammar.ProductionRuleSide;

/**
 *
 * @author hanis
 */
public class ChangeRuleSideStep implements UndoRedoStep {

    private ProductionRuleSide oldRuleSide;
    private ProductionRuleSide newRuleSide;
    private ProductionRuleSide ruleSide;

    public ChangeRuleSideStep(ProductionRuleSide ruleSide,
            ProductionRuleSide oldRuleSide, ProductionRuleSide newRuleSide) {
        this.ruleSide = ruleSide;
        this.oldRuleSide = oldRuleSide;
        this.newRuleSide = newRuleSide;
    }

    public void undo() {
        //System.out.println("undo: " + oldRuleSide.getSymbols());
        ruleSide.setSymbols(oldRuleSide.getSymbols());
    }

    public void redo() {
        ruleSide.setSymbols(newRuleSide.getSymbols());
    }

    public String type() {
        return "ChangeRuleSideStep";
    }
}
