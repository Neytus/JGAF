/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar.undo;

import jgaf.automaton.fa.undo.UndoRedoStep;
import jgaf.grammar.Grammar;
import jgaf.grammar.GrammarEditor;

/**
 *
 * @author hanis
 */
public class ChangeGrammar implements UndoRedoStep {

    private Grammar oldGrammar;
    private Grammar newGrammar;    

    private GrammarEditor editor;

    public ChangeGrammar(Grammar oldGrammar, Grammar newGrammar, GrammarEditor editor) {
        this.oldGrammar = oldGrammar;
        this.newGrammar = (Grammar)newGrammar.clone();
        this.editor = editor;
    }

    public void undo() {
        editor.setGrammar(oldGrammar);
    }

    public void redo() {
        editor.setGrammar(newGrammar);
    }

    public String type() {
        return "ChangeGrammar";
    }
}
