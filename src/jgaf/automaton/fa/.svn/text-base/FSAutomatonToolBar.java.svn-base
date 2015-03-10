/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import jgaf.gui.EditorPropertiesDialog;

/**
 *
 * @author hanis
 */
public class FSAutomatonToolBar extends JToolBar {

    private JToggleButton pointerButton;
    private JToggleButton addStateButton;
    private JToggleButton addTransitionButton;
    private JToggleButton addLabelButton;
    private ButtonGroup toolsGroup = new ButtonGroup();


    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton clearZoomButton;
    private JComboBox zoomComboBox;

    private JComboBox editorSwitchComboBox;

    private JButton redoButton;
    private JButton undoButton;

   // private JCheckBoxMenuItem initialCheck;
   // private JCheckBox accptingCheck;


    private JButton adjustButton;
    private JButton gemButton;
    private JButton setupButton;
    private JButton descriptionButton;

    private JButton switchButton;
    private JButton switchButton2;
    //private JButton prop;


    private FSAutomatonEditor editor;
    private StateDiagramEditor stateDiagramEditor;
    private AutomatonTableEditor automatonTableEditor;
   // private AutomatonTableEditor automatonTableEditor;

    public static final String BEST_FIT_ZOOM = "Best Fit";
    public static final String STATE_DIAGRAM = "State diagram";
    public static final String STATE_TRANSITION_TABLE = "State transition table";


    public FSAutomatonToolBar(FSAutomatonEditor editor) {
        super();
        this.editor = editor;
        this.stateDiagramEditor = editor.getStateDiagramEditor();
        this.automatonTableEditor = editor.getTransitionTableEditor();
        init();
    }


    private void init() {
        setRollover(true);
        setFloatable(false);
        setPreferredSize(new Dimension(28,28));



        pointerButton = new JToggleButton();
        pointerButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/pointer.png")));
        pointerButton.setToolTipText("pointer");
        pointerButton.setFocusable(false);
        pointerButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pointerButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pointerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateDiagramEditor.setToolState(EditorState.hand);
            }
        });
        pointerButton.setSelected(true);
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
                stateDiagramEditor.setToolState(EditorState.addState);                
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
                stateDiagramEditor.setToolState(EditorState.addTransition);
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
                stateDiagramEditor.setToolState(EditorState.addLabel);
            }
        });
        toolsGroup.add(addLabelButton);
        add(addLabelButton);

//
//        selectionButton = new JToggleButton();
//        selectionButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/select.png")));
//        selectionButton.setToolTipText("selection");
//        selectionButton.setFocusable(false);
//        selectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        selectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        selectionButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                //editor.setSelection(!editor.isSelectionMode());
//            }
//        });
//        add(selectionButton);

        addSeparator();


        zoomInButton = new JButton();
        zoomInButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/zoomIn.png")));
        zoomInButton.setToolTipText("zoom in");
        zoomInButton.setFocusable(false);
        zoomInButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomInButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.getEditor().zoomIn();
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
                editor.getEditor().zoomOut();
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
                editor.getEditor().clearZoom();
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
                    stateDiagramEditor.centerAndScaleGraphics(true);
                } else {
                    String zoomFactor = selectedItem.substring(0, selectedItem.length() - 1);
                    stateDiagramEditor.setZoomInPercentage(Double.valueOf(zoomFactor));
                }
            }
        });
       // zoomComboBox.setSelectedItem("100%");
 //       add(zoomComboBox);

        addSeparator();


        undoButton = new JButton();
        undoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/undo.png")));
        undoButton.setToolTipText("undo");
        undoButton.setFocusable(false);
        undoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateDiagramEditor.undo();
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
                stateDiagramEditor.redo();
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
                editor.getEditor().center();
                //stateDiagramEditor.centerAndScaleGraphics(true);
            }
        });
        add(adjustButton);


        gemButton = new JButton();
        gemButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/gem.png")));
        gemButton.setToolTipText("Layout graph");
        gemButton.setFocusable(false);
        gemButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gemButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateDiagramEditor.applyGEMLayout();
            }
        });
        add(gemButton);

        addSeparator();


        setupButton = new JButton();
        setupButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/setup.png")));
        setupButton.setToolTipText("Coniguration");
        setupButton.setFocusable(false);
        setupButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setupButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        setupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditorPropertiesDialog editorPropertiesDialog = new EditorPropertiesDialog(editor.getMainFrame(), true);
            }
        });
        add(setupButton);


        descriptionButton = new JButton();
        descriptionButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/description.png")));
        descriptionButton.setToolTipText("Coniguration");
        descriptionButton.setFocusable(false);
        descriptionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        descriptionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        descriptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new AutomatonDetailDiaog(editor.getMainFrame(), editor.getAutomaton().toString());
            }
        });
        add(descriptionButton);



//        switchButton = new JButton();
//        switchButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/view-refresh.png")));
//        switchButton.setToolTipText("Switch editors");
//        switchButton.setFocusable(false);
//        switchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        switchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        switchButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                editor.getAutomaton().layout();
//                editor.repaint();
//            }
//        });
//        add(switchButton);

//
//
//        switchButton2 = new JButton();
//        switchButton2.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/view-refresh.png")));
//        switchButton2.setToolTipText("Switch editors2");
//        switchButton2.setFocusable(false);
//        switchButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        switchButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        switchButton2.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//
//
//
//
//
//                DFASimulation simulation = new DFASimulation();
//                String check = simulation.checkInput(editor.getAutomaton());
//                if (!check.equals(Procedure.CHECK_OK)) {
//                    if (check.equals(Procedure.CHECK_NOT_OK)) {
//                        check = "Input object dosn't have a correct format";
//                    }
//                    JOptionPane.showMessageDialog(editor.getMainFrame(), check, "Wrong input", JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//                JInternalFrame frame = new JInternalFrame("simulation", true, true, true, true);
//                JDesktopPane desktop = ((MainFrame) editor.getMainFrame()).getDesktop();
//                frame.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
//                desktop.add(frame, 2);
//                desktop.setBackground(Color.DARK_GRAY);
//                frame.setVisible(true);
//
//                simulation.setEditor(editor);
//                frame.add(simulation.getFace(), BorderLayout.CENTER);
//
//
//
//            }
//        });
//        add(switchButton2);




        String[] editorTypes = {STATE_DIAGRAM, STATE_TRANSITION_TABLE};
        editorSwitchComboBox = new JComboBox(editorTypes);
        editorSwitchComboBox.setSelectedItem(STATE_DIAGRAM);
        editorSwitchComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String selectedItem = editorSwitchComboBox.getSelectedItem().toString();
                if(selectedItem.equals(STATE_DIAGRAM)) {
                    editor.setEditor(FSAutomatonEditor.STATE_DIAGRAM);
                } else if(selectedItem.equals(STATE_TRANSITION_TABLE)) {
                    editor.setEditor(FSAutomatonEditor.STATE_TRANSITION_TABLE);
                }
            }
        });
       
        add(editorSwitchComboBox);



    }

    public void repaintToolBar() {
        boolean enabled = editor.getEditorType() == FSAutomatonEditor.STATE_DIAGRAM;
       // adjustButton.setEnabled(enabled);
        zoomComboBox.setEnabled(enabled);
        pointerButton.setEnabled(enabled);
        addLabelButton.setEnabled(enabled);
        addTransitionButton.setEnabled(enabled);
        addStateButton.setEnabled(enabled);
        redoButton.setEnabled(enabled);
        undoButton.setEnabled(enabled);

        gemButton.setEnabled(enabled);
        setupButton.setEnabled(enabled);
        //descriptionButton.setEnabled(enabled);


    }




}


