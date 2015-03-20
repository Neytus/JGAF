/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jgaf.Representation;
import jgaf.automaton.fa.undo.UndoRedoHandler;
import jgaf.automaton.pda.undo.AutomatonChangedStep;
import jgaf.editor.Editor;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class PushdownAutomatonEditor extends Editor {

    private PushdownAutomaton automaton;
    private PushdownAutomatonEditorFace face;
    private PushdownAutomatonRepresenter representer;

    private UndoRedoHandler undoHandler;


    public PushdownAutomatonEditor() {
    }


    public void writeAutomaton() {
        new DialogAutomatonPrintout(getFrame(), automaton.writeAutomataDetail());
    }

    public void modifyAutomaton() {
        new DialogModifyAutomata(getFrame(), true, this).setVisible(true);
        repaint();

    }

    public PushdownAutomaton getAutomaton() {
        return automaton;
    }


    public void changeAutomaton(PushdownAutomaton newAutomaton) {
        undoHandler.addStep(new AutomatonChangedStep(automaton, newAutomaton, this));
        this.automaton = newAutomaton;
        repaint();
    }

    @Override
    public void repaint() {
        this.representer.setAutomaton(automaton);
        getFace().repaint();
    }

    @Override
    public void create() {
        this.undoHandler = new UndoRedoHandler();
        this.automaton = new PushdownAutomaton();
//        this.grammarRepsresenter = new GrammarRepresenter(this);
        this.representer = new PushdownAutomatonRepresenter();
        this.face = new PushdownAutomatonEditorFace(this);
        setEditable(true);
    }

    @Override
    public JPanel getFace() {
        return face;
    }

    @Override
    public JPanel getRepresenter() {
        return representer;
    }

    
    public void redo() {
        undoHandler.redo();
        repaint();
    }

    public void undo() {
        undoHandler.undo();
        repaint();
    }


    @Override
    public boolean open(File file) {
        try {
            this.automaton = XMLDataManipulation.getInstance().getAutomata(file);
            repaint();
            return true;
        } catch (DocumentException ex) {
            Logger.getLogger(PushdownAutomatonEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }


    @Override
    public boolean save(File file) {
        try {
            XMLDataManipulation.getInstance().saveAutomaton(automaton, file);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(PushdownAutomatonEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }



    @Override
    public void setData(Representation data) {
        automaton = (PushdownAutomaton) data;
    }

    @Override
    public Representation getData() {
        return automaton;
    }

}
