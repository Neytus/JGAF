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
    private ProductionRuleSide leftRuleSide;
    private ProductionRulesSide ruleSide;
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

        deleteButton = new JMenuItem("Clear");
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteStateAction(e);
            }
        });
        this.add(deleteButton);

    }

    public void show(ProductionRulesSide ruleSide, Component comp, Point at, boolean right) {
        epsilonButton.setVisible(right);
        this.ruleSide = ruleSide;
        show(comp, at.x, at.y);
    }
    public void show(ProductionRuleSide ruleSide, Component comp, Point at, boolean right) {
        epsilonButton.setVisible(right);
        this.leftRuleSide = ruleSide;
        show(comp, at.x, at.y);
    }

    public void handleChangeAction(ActionEvent e) {
    }

    public void handleDeleteStateAction(ActionEvent e) {
        
        if(ruleSide != null){
            editor.clearRuleSide(ruleSide); 
        }
        if(leftRuleSide != null){
             editor.clearRuleSide(leftRuleSide);             
        }
           
    }

    public void handleEpsilonAction(ActionEvent e) {
        editor.setRightSideToEpsilon(ruleSide);
    }




}
