/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author hanis
 */
public class PushdownAutomatonEditorToolbar extends JToolBar {


    private JButton modifyButton;
    private JButton writeButton;

    private JButton redoButton;
    private JButton undoButton;



    private PushdownAutomatonEditor editor;



    public PushdownAutomatonEditorToolbar(PushdownAutomatonEditor editor) {
        super();
        this.editor = editor;
        init();
    }



    private void init() {
        setRollover(true);
        setFloatable(false);


        modifyButton = new JButton();
        modifyButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/tool.png")));
        modifyButton.setToolTipText("Modify");
        modifyButton.setFocusable(false);
        modifyButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        modifyButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.modifyAutomaton();
            }
        });
        add(modifyButton);

        addSeparator();




        undoButton = new JButton();
        undoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/undo.png")));
        undoButton.setToolTipText("undo");
        undoButton.setFocusable(false);
        undoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.undo();
            }
        });
        add(undoButton);




        redoButton = new JButton();
        redoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/redo.png")));
        redoButton.setToolTipText("redo");
        redoButton.setFocusable(false);
        redoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        redoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.redo();
            }
        });
        add(redoButton);

        addSeparator();

        writeButton = new JButton();
        writeButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/description.png")));
        writeButton.setToolTipText("Automaton detail");
        writeButton.setFocusable(false);
        writeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        writeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        writeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.writeAutomaton();
            }
        });
        add(writeButton);


    }


}

