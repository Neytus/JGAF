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
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author hanis
 */
public class LabelPopupMenu extends JPopupMenu {

    private StateDiagramEditor editor;
    private CanvasLabel label;
    private JMenuItem changeNameButton;
    private JMenuItem deleteLabelButton;
    private JMenuItem labelPropertiesButton;

    
    private JMenu rotateMenu;
    private JMenuItem noRotation;
    private JMenuItem rotate90cw;
    private JMenuItem rotate90ccw;
    private JMenuItem rotate180;

    public LabelPopupMenu() {
        init();
    }

    private void init() {
        changeNameButton = new JMenuItem("Change caption");
        changeNameButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/rename.png")));
        changeNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleChangeNameAction(e);
            }
        });
        this.add(changeNameButton);


        deleteLabelButton = new JMenuItem("Delete");
        deleteLabelButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        deleteLabelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handleDeleteLabelAction(e);
            }
        });
        this.add(deleteLabelButton);





        rotateMenu = new JMenu("Rotate");
        this.add(rotateMenu);

        noRotation = new JMenuItem("No rotation");
        noRotation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.handleSettingLabelRotationFactor(label, 0);
            }
        });
        rotateMenu.add(noRotation);

        rotate90cw = new JMenuItem("90° clockwise");
       // rotate90cw.setIcoNewClassn(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        rotate90cw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.handleLabelRotation(label, 90);
            }
        });
        rotateMenu.add(rotate90cw);


        rotate90ccw = new JMenuItem("90° counter-clockwise");
       // rotate90cw.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        rotate90ccw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.handleLabelRotation(label, -90);
            }
        });
        rotateMenu.add(rotate90ccw);


        rotate180 = new JMenuItem("180°");
       // rotate90cw.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/delete.png")));
        rotate180.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.handleLabelRotation(label, 180);
            }
        });
        rotateMenu.add(rotate180);





        addSeparator();

        labelPropertiesButton = new JMenuItem("Properties");
        labelPropertiesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/properties.png")));
        labelPropertiesButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                handleLabelPropertiesAction(e);
            }
        });
        this.add(labelPropertiesButton);

    }

    public void show(StateDiagramEditor editor, Component comp, Point at) {
        this.editor = editor;
        this.label = editor.getCurrentLabelSelected();
        show(comp, at.x, at.y);
    }

    public void handleChangeNameAction(ActionEvent e) {
        String newCaption = (String) JOptionPane.showInputDialog(this,
                "Input a new caption.", "Change caption",
                JOptionPane.PLAIN_MESSAGE, null, null, label.getCaption());
        if (newCaption != null && !newCaption.equals("")) {
            editor.renameLabel(label, newCaption);
        }
    }

    public void handleDeleteLabelAction(ActionEvent e) {
        editor.handleRemoveLabel(label);
    }

    public void handleLabelPropertiesAction(ActionEvent e) {
        LabelPropertiesDialog dialog = new LabelPropertiesDialog(editor.getMainFrame(), editor, label);
        dialog.setVisible(true);
    }

//    @Override
//    public void setVisible(boolean b) {
//        super.setVisible(b);
//        if (!b) {
//            editor.setEditable(true);
//        }
//    }
}
