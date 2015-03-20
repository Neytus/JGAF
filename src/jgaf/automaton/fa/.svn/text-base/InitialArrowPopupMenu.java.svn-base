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
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author hanis
 */
public class InitialArrowPopupMenu extends JPopupMenu {

    private StateDiagramEditor editor;

    private JMenu orientationMenu;
    private JMenuItem topOrientationButton;
    private JMenuItem bottomOrientationButton;
    private JMenuItem leftOrientationButton;
    private JMenuItem rightOrientationButton;

    private JMenuItem propertiesButton;

    public InitialArrowPopupMenu() {


        orientationMenu = new JMenu("Orientation");
        this.add(orientationMenu);

        leftOrientationButton = new JMenuItem(InitialArrow.LEFT);
        leftOrientationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.changeInitialArrowOrientation(0);
            }
        });
        orientationMenu.add(leftOrientationButton);

        rightOrientationButton = new JMenuItem(InitialArrow.RIGHT);
        rightOrientationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.changeInitialArrowOrientation(Math.PI);
            }
        });
        orientationMenu.add(rightOrientationButton);


        topOrientationButton = new JMenuItem(InitialArrow.TOP);
        topOrientationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.changeInitialArrowOrientation(Math.PI/2);
            }
        });
        orientationMenu.add(topOrientationButton);


        bottomOrientationButton = new JMenuItem(InitialArrow.BOTTOM);
        bottomOrientationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.changeInitialArrowOrientation(3*Math.PI/2);
            }
        });
        orientationMenu.add(bottomOrientationButton);


        addSeparator();


        propertiesButton = new JMenuItem("Properties");
        propertiesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/properties.png")));
        propertiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePropertiesAction(e);
            }
        });
        this.add(propertiesButton);


    }



    public void show(StateDiagramEditor editor, Component comp, Point at) {
        this.editor = editor;
        show(comp, at.x, at.y);
    }


    public void handlePropertiesAction(ActionEvent e) {
        InitialArrowPropertiesDialog dialog = new InitialArrowPropertiesDialog(editor.getMainFrame(), editor);
        dialog.setVisible(true);
    }


}
