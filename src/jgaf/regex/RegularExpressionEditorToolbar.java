/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.regex;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;



/**
 *
 * @author hanis
 */
public class RegularExpressionEditorToolbar extends JToolBar {

    private JButton redoButton;
    private JButton undoButton;

    private JButton allBracketButton;
    private JButton conciseBracketButton;



    private RegularExpressionEditor editor;



    public RegularExpressionEditorToolbar(RegularExpressionEditor editor) {
        super();
        this.editor = editor;
        init();
    }



    private void init() {
        setRollover(true);
        setFloatable(false);

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



        allBracketButton = new JButton();
        allBracketButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/bracket_both.png")));
        allBracketButton.setToolTipText("Fill in all parentheses");
        allBracketButton.setFocusable(false);
        allBracketButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        allBracketButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        allBracketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.setFullyEncapsulatedForm();
            }
        });
        add(allBracketButton);






        conciseBracketButton = new JButton();
        conciseBracketButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/bracket_no.png")));
        conciseBracketButton.setToolTipText("Only necesarry parentheses");
        conciseBracketButton.setFocusable(false);
        conciseBracketButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        conciseBracketButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        conciseBracketButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.setConciseForm();
            }
        });
        add(conciseBracketButton);








    }


}



