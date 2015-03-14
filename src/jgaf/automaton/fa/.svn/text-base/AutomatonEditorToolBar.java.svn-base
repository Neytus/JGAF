/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 *
 * @author hanis
 */
public class AutomatonEditorToolBar extends JToolBar {

    private JToggleButton pointerButton;
    private JToggleButton addStateButton;
    private JToggleButton addTransitionButton;
    private JToggleButton addLabelButton;
    private JToggleButton selectionButton;
    private ButtonGroup toolsGroup = new ButtonGroup();


    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton clearZoomButton;
    private JComboBox zoomComboBox;

    private JButton redoButton;
    private JButton undoButton;

   // private JCheckBoxMenuItem initialCheck;
   // private JCheckBox accptingCheck;


    private JButton adjustButton;


    private JButton switchButton;


    private StateDiagramEditor editor;

    public static final String BEST_FIT_ZOOM = "Best Fit";

    public AutomatonEditorToolBar(StateDiagramEditor editor) {
        super();
        this.editor = editor;
        init();
    }



    private void init() {
        setRollover(true);
        setPreferredSize(new Dimension(28,28));



        pointerButton = new JToggleButton();
        pointerButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/pointer.png")));
        pointerButton.setToolTipText("pointer");
        pointerButton.setFocusable(false);
        pointerButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pointerButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pointerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.setToolState(EditorState.hand);
                editor.removeAllSelections();
            }
        });
        toolsGroup.add(pointerButton);
        add(pointerButton);


        addStateButton = new JToggleButton();
        addStateButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/addState.png")));
        addStateButton.setToolTipText("add state");
        addStateButton.setFocusable(false);
        addStateButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addStateButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addStateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.setToolState(EditorState.addState);
                editor.removeAllSelections();
            }
        });
        toolsGroup.add(addStateButton);
        add(addStateButton);
      

        
        
        addTransitionButton = new JToggleButton();
        addTransitionButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/addTransition.png")));
        addTransitionButton.setToolTipText("add transition");
        addTransitionButton.setFocusable(false);
        addTransitionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addTransitionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addTransitionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.setToolState(EditorState.addTransition);
                editor.removeAllSelections();
            }
        });
        toolsGroup.add(addTransitionButton);
        add(addTransitionButton);


        addLabelButton = new JToggleButton();
        addLabelButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/addLabel.png")));
        addLabelButton.setToolTipText("add label");
        addLabelButton.setFocusable(false);
        addLabelButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addLabelButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addLabelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.setToolState(EditorState.addLabel);
                editor.removeAllSelections();
            }
        });
        toolsGroup.add(addLabelButton);
        add(addLabelButton);


        selectionButton = new JToggleButton();
        selectionButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/select.png")));
        selectionButton.setToolTipText("selection");
        selectionButton.setFocusable(false);
        selectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        selectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        selectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //editor.setSelection(!editor.isSelectionMode());
            }
        });
        add(selectionButton);

        addSeparator();


        zoomInButton = new JButton();
        zoomInButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/zoomIn.png")));
        zoomInButton.setToolTipText("zoom in");
        zoomInButton.setFocusable(false);
        zoomInButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomInButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.zoomIn();
            }
        });
        add(zoomInButton);


        zoomOutButton = new JButton();
        zoomOutButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/zoomOut.png")));
        zoomOutButton.setToolTipText("zoom out");
        zoomOutButton.setFocusable(false);
        zoomOutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomOutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.zoomOut();
            }
        });
        add(zoomOutButton);


        clearZoomButton = new JButton();
        clearZoomButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/clearZoom.png")));
        clearZoomButton.setToolTipText("clearZoom");
        clearZoomButton.setFocusable(false);
        clearZoomButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clearZoomButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clearZoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.clearZoom();
            }
        });
        add(clearZoomButton);

        
        String[] items = {BEST_FIT_ZOOM, "20%", "30%", "50%", "75%", "100%", "120%", "150%", "175%", "200%", "250%", "300%", "400%" };
        zoomComboBox = new JComboBox(items);
        zoomComboBox.setSelectedItem("100%");
        zoomComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String selectedItem = zoomComboBox.getSelectedItem().toString();
                if(selectedItem.equals(BEST_FIT_ZOOM)) {
                    editor.centerAndScaleGraphics(true);
                } else {
                    String zoomFactor = selectedItem.substring(0, selectedItem.length() - 1);
                    editor.setZoomInPercentage(Double.valueOf(zoomFactor));
                }
            }
        });
       // zoomComboBox.setSelectedItem("100%");
        add(zoomComboBox);

        addSeparator();


        undoButton = new JButton();
        undoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/undo.png")));
        undoButton.setToolTipText("undo");
        undoButton.setFocusable(false);
        undoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.undo();
            }
        });
        add(undoButton);


        redoButton = new JButton();
        redoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/redo.png")));
        redoButton.setToolTipText("redo");
        redoButton.setFocusable(false);
        redoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        redoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.redo();
            }
        });
        add(redoButton);


        addSeparator();





        adjustButton = new JButton();
        adjustButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/adjust.png")));
        adjustButton.setToolTipText("Adjust graphics");
        adjustButton.setFocusable(false);
        adjustButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        adjustButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        adjustButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.centerAndScaleGraphics(true);
            }
        });
        add(adjustButton);







        addSeparator();





        switchButton = new JButton();
        switchButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/view-refresh.png")));
        switchButton.setToolTipText("Switch editors");
        switchButton.setFocusable(false);
        switchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        switchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        switchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
       //        editor.s
            }
        });
        add(switchButton);














//
//
//
//        initialCheck = new JCheckBoxMenuItem();
//        //initialCheck.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/redo.png")));
//        initialCheck.setToolTipText("redo");
//        initialCheck.setText("initial");
//        initialCheck.setFocusable(false);
//        initialCheck.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        initialCheck.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        initialCheck.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                //editor.redo();
//            }
//        });
//        add(initialCheck);


    }


}
