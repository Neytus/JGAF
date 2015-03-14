/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 *
 * @author hanis
 */
public class FSAutomatonEditorFace extends JPanel {

    private FSAutomatonToolBar toolbar;
    private FSAutomatonEditor editor;
    private JPanel centerPanel;
    private JScrollPane scroller;
    public FSAutomatonEditorFace(FSAutomatonEditor editor) {
        super(new BorderLayout());
        this.editor = editor;
        initComponents();
    }

    private void initComponents() {
        toolbar = new FSAutomatonToolBar(editor);
        add(toolbar, BorderLayout.NORTH);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(editor.getRepresenter(), BorderLayout.CENTER);
        scroller = new JScrollPane(centerPanel);
        add(scroller, BorderLayout.CENTER);
    }

    public void changeRepresenter() {
        centerPanel.removeAll();
        centerPanel.add(editor.getRepresenter(), BorderLayout.CENTER);
        repaintToolBar();
        revalidate();
    }

    public void repaintToolBar() {
        toolbar.repaintToolBar();
    }


    public Dimension getViewPanelSize() {
        //return centerPanel.getSize();
        return scroller.getSize();
    }

}
