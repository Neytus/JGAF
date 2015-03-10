/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import jgaf.automaton.fa.InitialArrow;

/**
 *
 * @author hanis
 */
public class ModifyInitialArrowStep implements UndoRedoStep {

    private final InitialArrow initialArrow;
    private final InitialArrow oldInitialArrow;
    private final InitialArrow newInitialArrow;

    public ModifyInitialArrowStep(InitialArrow initialArrow, InitialArrow oldInitialArrow, InitialArrow newInitialArrow) {
        this.initialArrow = initialArrow;
        this.oldInitialArrow = oldInitialArrow;
        this.newInitialArrow = newInitialArrow;
    }

    public void undo() {
        initialArrow.modify(oldInitialArrow);
    }

    public void redo() {
        initialArrow.modify(newInitialArrow);
    }

    public String type() {
        return "ModifyInitialArrowStep";
    }
}
