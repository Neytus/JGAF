/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author hanis
 */
public class StateDiagramEditorFace extends JPanel {

    private FSAutomatonToolBar toolbar;
    private StateDiagramEditor editor;
    private JPanel centerPanel;

    public StateDiagramEditorFace(StateDiagramEditor editor) {
        super(new BorderLayout());
        this.editor = editor;
        initComponents();
    }

    private void initComponents() {
        //toolbar = new FSAutomatonToolBar(editor);
        add(toolbar, BorderLayout.NORTH);
        
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(editor.getRepresenter(), BorderLayout.CENTER);
        JScrollPane scroller = new JScrollPane(centerPanel);
        add(scroller, BorderLayout.CENTER);
      
       // add(editor.getRepresenter(), BorderLayout.CENTER);
//centerPanel.add(editor.getRepresenter());



//
//        toolbar = new StateDiagramToolBar(editor);
//        add(toolbar, BorderLayout.NORTH);
//        centerPanel = new JPanel();
//       // centerPanel.add(editor.getRepresenter());
//        JScrollPane scroller = new JScrollPane(editor.getRepresenter());
//        add(scroller, BorderLayout.CENTER)
//
//
    }
}
