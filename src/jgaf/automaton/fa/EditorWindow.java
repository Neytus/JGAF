/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import jgaf.editor.Editor;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author hanis
 */
public class EditorWindow extends JPanel {

    private JPanel mainPanel;
    private JPanel toolbarPanel;
    private JPanel statusPanel;


    public EditorWindow() {
        setLayout(new BorderLayout());
        init();
    }

    

    private void init() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setVisible(true);
        mainPanel.setBackground(Color.DARK_GRAY);
        //JScrollPane scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //add(scroller, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);



      //  toolbarPanel = new JPanel(new BorderLayout());

      //  add(toolbarPanel, BorderLayout.PAGE_START);
        
        
    }



    public void applyEditor(Editor editor) {
//        toolbarPanel.removeAll();
//        toolbarPanel.add(editor.getToolBar(), BorderLayout.CENTER);
        mainPanel.removeAll();
        mainPanel.add(editor.getFace(), BorderLayout.CENTER);
    }



}
