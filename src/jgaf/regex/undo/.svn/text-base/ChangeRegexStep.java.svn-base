/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.regex.undo;


import jgaf.automaton.fa.undo.UndoRedoStep;
import jgaf.regex.RegularExpressionEditor;

/**
 *
 * @author hanis
 */
public class ChangeRegexStep implements UndoRedoStep {

    private RegularExpressionEditor editor;
    private String newRegex;
    private String oldRegex;

    public ChangeRegexStep(RegularExpressionEditor editor, String newRegex, String oldRegex) {
        this.editor = editor;
        this.newRegex = newRegex;
        this.oldRegex = oldRegex;
    }

    public void undo() {
        editor.setExpression(oldRegex, false);
    }

    public void redo() {
        editor.setExpression(newRegex, false);
    }

    public String type() {
        return "ChangeRegexStep";
    }



}
