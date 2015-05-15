/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author hanis
 */
public class GrammarEditorFace extends JPanel {
    
    private GrammarEditorToolbar toolbar;
    private GrammarEditor editor;
    private JPanel centerPanel;


    public GrammarEditorFace(GrammarEditor editor) {
        super(new BorderLayout());
        this.editor = editor;
        initComponents();
    }

    private void initComponents() {
        toolbar = new GrammarEditorToolbar(editor);
        add(toolbar, BorderLayout.NORTH);
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        JScrollPane scroller = new JScrollPane(centerPanel);
        add(scroller, BorderLayout.CENTER);
        centerPanel.add(editor.getGrammarRepsresenter());

    }

    public GrammarEditorToolbar getToolbar() {
        return toolbar;
    }

    public void initiate() {
    }

}
