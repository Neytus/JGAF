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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import jgaf.Constants.MathConstants;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class TransitionPopupMenu extends JPopupMenu {

    private StateDiagramEditor editor;
    private Transition transition;

    private JMenuItem deleteTransitionButton;
    private JMenuItem addTransitionLabelButton;
    private JMenuItem addEpsilonTransitionButton;
    private JMenuItem changeTransitionLabelsButton;
    private JMenuItem transitionPropertiesButton;
    private JMenuItem makeTransitionStraightButton;

    public TransitionPopupMenu() {

        addTransitionLabelButton = new JMenuItem("Add Label");
        addTransitionLabelButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/add.png")));
        addTransitionLabelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddTransitionLabelAction(e);
            }
        });
        this.add(addTransitionLabelButton);


        addEpsilonTransitionButton = new JMenuItem("Add " + MathConstants.EPSILON + " transition");
        addEpsilonTransitionButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/epsilon.png")));
        addEpsilonTransitionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAddEpsTransitionAction(e);
            }
        });
        this.add(addEpsilonTransitionButton);



        changeTransitionLabelsButton = new JMenuItem("Change Labels");
        changeTransitionLabelsButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/change.png")));
        changeTransitionLabelsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleChangeTransitionLabelsAction(e);
            }
        });
        this.add(changeTransitionLabelsButton);


        deleteTransitionButton = new JMenuItem("Delete transition");
        deleteTransitionButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        deleteTransitionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteTransitionAction(e);
            }
        });
        this.add(deleteTransitionButton);

        makeTransitionStraightButton = new JMenuItem("Make Straight");
        makeTransitionStraightButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/makeStraight.png")));
        makeTransitionStraightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleMakeTransitionStraightAction(e);
            }
        });
        this.add(makeTransitionStraightButton);

        addSeparator();


        transitionPropertiesButton = new JMenuItem("Properties");
        transitionPropertiesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/properties.png")));
        transitionPropertiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleTransitionPropertiesAction(e);
            }
        });
        this.add(transitionPropertiesButton);



    }

    public void show(StateDiagramEditor editor, Component comp, Point at) {
        this.editor = editor;
        this.transition = editor.getCurrentSelectedTransition();
        if(transition.isReflexive() || transition.getVisualProperties().getCurveFactor() == 0) {
            makeTransitionStraightButton.setEnabled(false);
        } else {
            makeTransitionStraightButton.setEnabled(true);
        }
        show(comp, at.x, at.y);
    }


    public void handleAddTransitionLabelAction(ActionEvent e) {
        String newLabel = (String) JOptionPane.showInputDialog(this,
                    "Input a new label for the transition.", "Add Transition Label",
                    JOptionPane.PLAIN_MESSAGE, null, null, "");
        if(newLabel != null && !newLabel.equals("")) {
            editor.addTransitionLabel(transition, newLabel);
        }
    }


    private void handleAddEpsTransitionAction(ActionEvent e) {
        editor.addEpsilonTransition(transition);
    }

    private void handleChangeTransitionLabelsAction(ActionEvent e) {
        String labels = (String) JOptionPane.showInputDialog(this,
                    "Change the list of transition labels", "Change Transition Labels",
                    JOptionPane.PLAIN_MESSAGE, null, null, transition.writeLabels());
        if(labels != null && !labels.equals("")) {
            editor.changeTransitionLabels(transition, Tools.getSortedSetFromString(labels));
        }
    }


    public void handleDeleteTransitionAction(ActionEvent e) {
        editor.removeTransition(transition);
    }


    public void handleMakeTransitionStraightAction(ActionEvent e) {
        editor.changeTransitionCurveFactor(transition, 0);
    }

    public void handleTransitionPropertiesAction(ActionEvent e) {
        TransitionPropertiesDialog dialog = new TransitionPropertiesDialog(editor.getMainFrame(), editor, transition);
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
