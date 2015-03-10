/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import jgaf.editor.EditorsHandler;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

/**
 *
 * @author hanis
 */
public class FSAutomatonTransformationToolbar extends JToolBar {

    private FSAutomatonEditor editor;
    private EditorsHandler editorsHandler;

    private JButton openButton;
    private JComboBox editorSwitchComboBox;


    public static final String STATE_DIAGRAM = "State diagram";
    public static final String STATE_TRANSITION_TABLE = "State transition table";



    public FSAutomatonTransformationToolbar(FSAutomatonEditor editor, EditorsHandler editorsHandler) {
        super();
        this.editor = editor;
        this.editorsHandler = editorsHandler;
        init();
    }


    private void init() {
        setRollover(true);
        setFloatable(false);
        setPreferredSize(new Dimension(28,28));


        openButton = new JButton();
        openButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/window-new.png")));
        openButton.setToolTipText("undo");
        openButton.setFocusable(false);
        openButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorsHandler.openEditor(editor);
            }
        });
        add(openButton);



        addSeparator();



        String[] editorTypes = {STATE_DIAGRAM, STATE_TRANSITION_TABLE};
        editorSwitchComboBox = new JComboBox(editorTypes);
        editorSwitchComboBox.setSelectedItem(STATE_DIAGRAM);
        editorSwitchComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String selectedItem = editorSwitchComboBox.getSelectedItem().toString();
                if(selectedItem.equals(STATE_DIAGRAM)) {
                    editor.setEditor(FSAutomatonEditor.STATE_DIAGRAM);
                } else if(selectedItem.equals(STATE_TRANSITION_TABLE)) {
                    editor.setEditor(FSAutomatonEditor.STATE_TRANSITION_TABLE);
                }
            }
        });

        add(editorSwitchComboBox);



    }



}


