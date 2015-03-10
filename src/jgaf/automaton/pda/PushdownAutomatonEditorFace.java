/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author hanis
 */
public class PushdownAutomatonEditorFace extends JPanel {

    
    private PushdownAutomatonEditor editor;
    private PushdownAutomatonEditorToolbar toolbar;

    private JPanel centerPanel;



    public PushdownAutomatonEditorFace(PushdownAutomatonEditor editor) {
        super(new BorderLayout());
        this.editor = editor;
        initComponents();
    }

    private void initComponents() {
        toolbar = new PushdownAutomatonEditorToolbar(editor);
        add(toolbar, BorderLayout.NORTH);
//        centerPanel = new JPanel();
//        centerPanel.setBackground(Color.WHITE);
       // JScrollPane scroller = new JScrollPane(centerPanel);
        //add(scroller, BorderLayout.CENTER);
        //add(centerPanel, BorderLayout.CENTER);
        add(editor.getRepresenter(), BorderLayout.CENTER);
//        panel = new JPanel();
//        panel.setBackground(Color.GRAY);
//        centerPanel.add(panel);
//        //table = new JTable();
    }

}
