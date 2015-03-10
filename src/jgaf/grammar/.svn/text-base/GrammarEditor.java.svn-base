        /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.grammar;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import jgaf.Representation;
import jgaf.editor.Editor;
import jgaf.automaton.fa.undo.UndoRedoHandler;
import jgaf.exporter.XMLExporter;
import jgaf.grammar.undo.ChangeGrammar;
import jgaf.importer.XMLImporter;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class GrammarEditor extends Editor {

    private Grammar grammar;
    private GrammarEditorFace face;
    private GrammarRepresenter grammarRepsresenter;

    private UndoRedoHandler undoHandler;


    public GrammarEditor() {
    }

    public void create() {
        this.undoHandler = new UndoRedoHandler();

        //this.grammar = createExample();//new Grammar();
        this.grammar = new Grammar();
        grammar.setStartNonterminal(new Nonterminal("S"));
        this.grammarRepsresenter = new GrammarRepresenter(this);
        this.face = new GrammarEditorFace(this);
        setEditable(true);
        repaint();
    }



    public boolean open() {
        return true;
    }



    @Override
    public boolean save(File file) {
        try {
            XMLExporter.saveGrammar(grammar, file);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(GrammarEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }




    @Override
    public boolean open(File file) {
        try {
            setGrammar(XMLImporter.getGrammar(file));
            return true;
        } catch (DocumentException ex) {
            Logger.getLogger(GrammarEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }



    public void undo() {
        undoHandler.undo();
        repaint();
    }

    public void redo() {
        undoHandler.redo();
        repaint();
    }



    public void repaintTable() {
        grammarRepsresenter.repaintTable();        
    }

    public JPanel getFace() {
        return face;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
        repaint();
    }



    public GrammarRepresenter getGrammarRepsresenter() {
        return grammarRepsresenter;
    }

    public void setGrammarRepsresenter(GrammarRepresenter grammarRepsresenter) {
        this.grammarRepsresenter = grammarRepsresenter;
    }



    public void changeRuleSide(ProductionRuleSide ruleSide, String newRuleSideString) {
        Grammar oldGrammar = (Grammar) grammar.clone();
        ProductionRuleSide oldRuleSide = (ProductionRuleSide) ruleSide.clone();
        ruleSide.setSymbolsFromString(newRuleSideString);
        ProductionRuleSide newRuleSide = (ProductionRuleSide) ruleSide.clone();
        if(!oldRuleSide.equals(newRuleSide)) {
            //undoHandler.addStep(new ChangeRuleSideStep(ruleSide, oldRuleSide, newRuleSide));
            undoHandler.addStep(new ChangeGrammar(oldGrammar, grammar, this));
            repaint();
        }
    }


    public void setRightSideToEpsilon(ProductionRuleSide ruleSide) {
        Grammar oldGrammar = (Grammar) grammar.clone();
        if(!ruleSide.isEpsilon()) {
            ruleSide.clear();
            ruleSide.addSymbol(new Symbol());
            //undoHandler.addStep(new ChangeRuleSideStep(ruleSide, oldRuleSide, newRuleSide));
            undoHandler.addStep(new ChangeGrammar(oldGrammar, grammar, this));
            repaint();
        }
    }



    

    public void clearRuleSide(ProductionRuleSide ruleSide) {
        changeRuleSide(ruleSide, "");
    }



    public void addEmptyProductionRule() {
        Grammar oldGrammar = (Grammar) grammar.clone();
        undoHandler.addStep(new ChangeGrammar(oldGrammar, grammar, this));
        //undoHandler.addStep(new AddEmptyRuleStep(grammar));
        grammar.addRule(new ProductionRule());
        repaint();
    }


    public void removeRule(ProductionRule rule) {
        Grammar oldGrammar = (Grammar) grammar.clone();
        int index = grammar.removeRule(rule);
        if (index != -1) {
            //undoHandler.addStep(new RemoveRuleStep(grammar, rule, index));
            undoHandler.addStep(new ChangeGrammar(oldGrammar, grammar, this));
            repaint();
        }
    }


    public void removeEmptyRules() {
        Grammar oldGrammar = (Grammar) grammar.clone();
        int number = grammar.removeEmptyRules();
        if(number > 0) {
            //undoHandler.addStep(new RemoveEmptyRulesStep(grammar, number));
            undoHandler.addStep(new ChangeGrammar(oldGrammar, grammar, this));
            repaint();
        }        
    }




    public void sortProductionRules() {
        grammar.sortProductionRules();
        repaint();
    }

    public void sortProductionRulesDesc() {
        grammar.sortProductionRulesDecs();
        repaint();
    }


    public void handleKeyPressed(java.awt.event.KeyEvent evt) {
        int key = evt.getKeyCode();
        if (key == KeyEvent.VK_DELETE) {

        }
        if (key == KeyEvent.VK_CONTEXT_MENU) {

        }
    }



    @Override
    public void setData(Representation data) {
        Grammar g = (Grammar) data;
        this.grammar = g;
    }

    @Override
    public Representation getData() {
        return grammar;
    }

    @Override
    public void repaint() {
        repaintTable();
        face.getToolbar().repaintCombo();
        face.repaint();
    }

    @Override
    public JPanel getRepresenter() {
        return grammarRepsresenter;
    }








}
