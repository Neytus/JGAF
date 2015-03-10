/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class MultipleStatesPopupMenu extends JPopupMenu {

    private StateDiagramEditor editor;
    private Set<State> states;
    private State clickedState;

    private JMenuItem allAcceptingStatesButton;
    private JMenuItem noneAcceptingStatesButton;
    private JMenuItem swapAcceptingStatesButton;

    private JMenu lineUpMenu;
    private JMenuItem lineUpVerticallyButton;
    private JMenuItem lineUpHorizontallyButton;


    private JMenuItem deleteStatesButton;

    public MultipleStatesPopupMenu() {

        allAcceptingStatesButton = new JMenuItem("All accepting");
        allAcceptingStatesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/allAccepting.png")));
        allAcceptingStatesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAllAcceptingAction(e);
            }
        });
        this.add(allAcceptingStatesButton);

        noneAcceptingStatesButton = new JMenuItem("None accepting");
        noneAcceptingStatesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/noneAccepting.png")));
        noneAcceptingStatesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleNoneAcceptingAction(e);
            }
        });
        this.add(noneAcceptingStatesButton);

        swapAcceptingStatesButton = new JMenuItem("Swap accepting");
        swapAcceptingStatesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/swapAccepting.png")));
        swapAcceptingStatesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSwapAcceptingAction(e);
            }
        });
        this.add(swapAcceptingStatesButton);



        addSeparator();



        lineUpMenu = new JMenu("Line Up");
        this.add(lineUpMenu);

        lineUpHorizontallyButton = new JMenuItem("Horizontally");
        lineUpHorizontallyButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/horizontal.png")));
        lineUpHorizontallyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.lineUpStatesHorizontally(states, clickedState);
            }
        });
        lineUpMenu.add(lineUpHorizontallyButton);


        lineUpVerticallyButton = new JMenuItem("Vertically");
        lineUpVerticallyButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/vertical.png")));
        lineUpVerticallyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.lineUpStatesVertically(states, clickedState);
            }
        });
        lineUpMenu.add(lineUpVerticallyButton);




        addSeparator();

        deleteStatesButton = new JMenuItem("Delete all");
        deleteStatesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        deleteStatesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteStatesAction(e);
            }
        });
        this.add(deleteStatesButton);

    }

    public void show(StateDiagramEditor editor, Component comp, Point at, State clickedState) {
        this.editor = editor;
        this.states = editor.getCurrentStatesSelected();
        this.clickedState = clickedState;
        show(comp, at.x, at.y);
    }


    private void handleAllAcceptingAction(ActionEvent e) {
        editor.changeAcceptingStates(states, true);
    }

    private void handleNoneAcceptingAction(ActionEvent e) {
        editor.changeAcceptingStates(states, false);
    }

    private void handleSwapAcceptingAction(ActionEvent e) {
        editor.swapAcceptingStates(states);
    }

    private void handleDeleteStatesAction(ActionEvent e) {
        editor.removeStates(states);
    }




    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(!b) {
            editor.setEditable(true);
        }
    }

}
