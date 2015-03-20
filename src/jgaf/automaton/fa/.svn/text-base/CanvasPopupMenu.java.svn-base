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
import javax.swing.JPopupMenu;

/**
 *
 * @author hanis
 */
public class CanvasPopupMenu extends JPopupMenu {

    private StateDiagramEditor editor;

    private JMenuItem selectAllButton;
    private JMenuItem zoomInButton;
    private JMenuItem zoomOutButton;
    private JMenuItem clearZoomButton;
    private JMenuItem undoButton;
    private JMenuItem redoButton;
    private JMenuItem showLabelsButton;
    private JMenuItem hideLabelsButton;
    private JMenuItem canvasPropertiesButton;

    public CanvasPopupMenu() {


        selectAllButton = new JMenuItem("Select All");
        selectAllButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/selectAll.png")));
        selectAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSelectAllAction(e);
            }
        });
        this.add(selectAllButton);


        addSeparator();

        zoomInButton = new JMenuItem("Zoom In");
        zoomInButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/zoomIn.png")));
        zoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleZoomInAction(e);
            }
        });
        this.add(zoomInButton);

        zoomOutButton = new JMenuItem("Zoom Out");
        zoomOutButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/zoomOut.png")));
        zoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleZoomOutAction(e);
            }
        });
        this.add(zoomOutButton);


        clearZoomButton = new JMenuItem("Clear Zoom");
        clearZoomButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/clearZoom.png")));
        clearZoomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleClearZoomAction(e);
            }
        });
        this.add(clearZoomButton);

        this.add(new Separator());


        undoButton = new JMenuItem("Undo");
        undoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/undo.png")));
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleUndoAction(e);
            }
        });
        this.add(undoButton);

        redoButton = new JMenuItem("Redo");
        redoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/redo.png")));
        redoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRedoAction(e);
            }
        });
        this.add(redoButton);
    

        addSeparator();



        showLabelsButton = new JMenuItem("Show labels");
        showLabelsButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/show.png")));
        showLabelsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.showLabels(true);
            }
        });
        this.add(showLabelsButton);

        hideLabelsButton = new JMenuItem("Hide labels");
        hideLabelsButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/show.png")));
        hideLabelsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editor.showLabels(false);
            }
        });
        this.add(hideLabelsButton);

//        addSeparator();
//
//
//        canvasPropertiesButton = new JMenuItem("Properties");
//        canvasPropertiesButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/popupmenus/properties.png")));
//        canvasPropertiesButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                handleCanvasPropertiesAction(e);
//            }
//        });
//        this.add(canvasPropertiesButton);
//
    }




    public void show(StateDiagramEditor editor, Component comp, Point at) {
        this.editor = editor;
        if(editor.areLabelsVisible()) {
            showLabelsButton.setVisible(false);
            hideLabelsButton.setVisible(true);
        } else {
            showLabelsButton.setVisible(true);
            hideLabelsButton.setVisible(false);
        }
        show(comp, at.x, at.y);
    }

    private void handleSelectAllAction(ActionEvent e) {
        editor.selectAll();
    }

    private void handleZoomInAction(ActionEvent e) {
        editor.zoomIn();
    }

    private void handleZoomOutAction(ActionEvent e) {
        editor.zoomOut();
    }

    private void handleClearZoomAction(ActionEvent e) {
        editor.clearZoom();
    }



    private void handleUndoAction(ActionEvent e) {
        editor.undo();
    }

    private void handleRedoAction(ActionEvent e) {
        editor.redo();
    }


    public void handleCanvasPropertiesAction(ActionEvent e) {
       // StatePropertiesDialog dialog = new StatePropertiesDialog(editor.getFrame(), editor, state);
       // dialog.setVisible(true);
    }




    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if(!b) {
            editor.setEditable(true);
        }
    }
}
