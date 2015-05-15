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
    private ProductionRules rule;

    private JMenuItem deleteButton;


    public RulePopupMenu(GrammarEditor editor) {

        this.editor = editor;

        deleteButton = new JMenuItem("Delete");
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleDeleteAction(e);
            }
        });
        this.add(deleteButton);

    }


    public void show(ProductionRules rule, Component comp, Point at) {
        this.rule = rule;
        show(comp, at.x, at.y);
    }


    public void handleDeleteAction(ActionEvent e) {
        editor.removeRule(rule);
    }
}
