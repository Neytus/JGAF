/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import jgaf.Constants.MathConstants;

/**
 *
 * @author hanis
 */
public class RuleSidePopupMenu extends JPopupMenu {

    private GrammarEditor editor;
    private ProductionRuleSide ruleSide;

//    private JMenuItem changeButton;
    private JMenuItem deleteButton;
    private JMenuItem epsilonButton;


    public RuleSidePopupMenu(GrammarEditor editor) {
        this.editor = editor;

        epsilonButton = new JMenuItem("Set to " + MathConstants.EPSILON);
        epsilonButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/epsilon.png")));
        epsilonButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleEpsilonAction(e);
            }
        });
        this.add(epsilonButton);



//        changeButton = new JMenuItem("Rename");
//        changeButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/rename.png")));
//        changeButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                handleChangeAction(e);
//            }
//        });
//        this.add(changeButton);


        deleteButton = new JMenuItem("Clear");
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteStateAction(e);
            }
        });
        this.add(deleteButton);

    }


    public void show(ProductionRuleSide ruleSide, Component comp, Point at, boolean right) {
        epsilonButton.setVisible(right);
        this.ruleSide = ruleSide;
        show(comp, at.x, at.y);
    }


    public void handleChangeAction(ActionEvent e) {
      //  System.out.println("handling change action");
//        String newName = (String) JOptionPane.showInputDialog(this,
//                    "Input a new name for the state.", "Rename State",
//                    JOptionPane.PLAIN_MESSAGE, null, null, state.getName());
//        if(newName != null && !newName.equals("")) {
//            editor.renameState(state, newName);
//        }
    }

    public void handleDeleteStateAction(ActionEvent e) {
        editor.clearRuleSide(ruleSide);
    }

    public void handleEpsilonAction(ActionEvent e) {
        editor.setRightSideToEpsilon(ruleSide);
    }




}
