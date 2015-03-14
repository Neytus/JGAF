/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.automaton.fa;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class StatePopupMenu extends JPopupMenu {

    private StateDiagramEditor editor;
    private State state;

    private JCheckBoxMenuItem acceptingCheckBox;
    private JCheckBoxMenuItem initialCheckBox;
    private JMenuItem changleNameButton;
    private JMenuItem deleteStateButton;
    private JMenuItem statePropertiesButton;



    public StatePopupMenu() {

        initialCheckBox = new JCheckBoxMenuItem("Initial");
        initialCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleInitialCheckBoxAction(e);
            }
        });
        this.add(initialCheckBox);

        acceptingCheckBox = new JCheckBoxMenuItem("Final");        
        acceptingCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAcceptingCheckBoxAction(e);
            }
        });
        this.add(acceptingCheckBox);


        addSeparator();

        
        changleNameButton = new JMenuItem("Rename");
        changleNameButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/rename.png")));
        changleNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleChangeNameAction(e);
            }
        });

        this.add(changleNameButton);


        deleteStateButton = new JMenuItem("Delete");
        deleteStateButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        deleteStateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteStateAction(e);
            }
        });
        this.add(deleteStateButton);

        addSeparator();


        statePropertiesButton = new JMenuItem("Properties");
        statePropertiesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/properties.png")));
        statePropertiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleStatePropertiesAction(e);
            }
        });
        this.add(statePropertiesButton);

    }

    public void show(StateDiagramEditor editor, Component comp, Point at) {
        this.editor = editor;
        this.state = editor.getSingleStateSelected();
        acceptingCheckBox.setSelected(state.isAccepting());
        initialCheckBox.setSelected(state.isInitial());
        //initialArrowOrientationMenu.setEnabled(state.isInitial());
        show(comp, at.x, at.y);
    }


    public void handleAcceptingCheckBoxAction(ActionEvent e) {
        editor.changeAcceptingState(state, acceptingCheckBox.isSelected());
    }

    public void handleInitialCheckBoxAction(ActionEvent e) {
        editor.changeInitialState(state, initialCheckBox.isSelected());
    }

    public void handleChangeNameAction(ActionEvent e) {
        String newName = (String) JOptionPane.showInputDialog(this,
                    "Input a new name for the state.", "Rename State",
                    JOptionPane.PLAIN_MESSAGE, null, null, state.getName());
        if(newName != null && !newName.equals("")) {
            editor.renameState(state, newName);
        }
    }

    public void handleDeleteStateAction(ActionEvent e) {
        editor.removeState(state);
    }

    public void handleStatePropertiesAction(ActionEvent e) {
        StatePropertiesDialog dialog = new StatePropertiesDialog(editor.getMainFrame(), editor, state);
        dialog.setVisible(true);
    }



    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(!b) {
            editor.setEditable(true);
        }
    }


}
