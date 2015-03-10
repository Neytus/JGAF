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

/**
 *
 * @author hanis
 */
public class RulePopupMenu extends JPopupMenu {

    private GrammarEditor editor;
    private ProductionRule rule;

   // private JMenuItem changeButton;
    private JMenuItem deleteButton;


    public RulePopupMenu(GrammarEditor editor) {

        this.editor = editor;

//        changeButton = new JMenuItem("Rename");
//        changeButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/rename.png")));
//        changeButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                handleChangeAction(e);
//            }
//        });
//        this.add(changeButton);


        deleteButton = new JMenuItem("Delete");
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteAction(e);
            }
        });
        this.add(deleteButton);

    }


    public void show(ProductionRule rule, Component comp, Point at) {
        this.rule = rule;
        show(comp, at.x, at.y);
    }


    public void handleDeleteAction(ActionEvent e) {
        editor.removeRule(rule);
    }
}
