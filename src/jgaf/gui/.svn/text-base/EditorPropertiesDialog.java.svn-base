/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditorPropertiesDialog.java
 *
 * Created on Mar 6, 2011, 5:20:18 PM
 */

package jgaf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.automaton.fa.CanvasLabel;
import jgaf.automaton.fa.InitialArrow;
import jgaf.automaton.fa.StateDiagramEditor;
import jgaf.automaton.fa.StrokeStyle;
import jgaf.environment.PropertiesHandler;
import say.swing.JFontChooser;

/**
 *
 * @author hanis
 */
public class EditorPropertiesDialog extends javax.swing.JDialog {


    private StateDiagramEditor editor;
    private PropertiesHandler properties = PropertiesHandler.getInstance();
    private final JFontChooser fontChooser = new JFontChooser();


    public EditorPropertiesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    private void init() {
        setTitle("State Diagram Editor Setting");
        initiatePaintingArea();
        setTabCaptions();
        initVisualizationStateTab();
        initVisualizationInitialArrowTab();
        initVisualizationTransitionTab();
        initVisualizationLabelTab();
        initVisualizationCanvasTab();
        centerDialog();
        setVisible(true);
    }



    private void centerDialog() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }



    private void initiatePaintingArea() {
        Automaton a = new Automaton();
        State s1 = new State("p", 150, 80);        
        State s2 = new State("q", 400, 130);
        State s3 = new State("r", 650, 80);
        
        a.addState(s2);
        a.addState(s1);
        a.addState(s3);
        Transition t1 = new Transition(s1, s2, "a");
        Transition t2 = new Transition(s2, s2, "b");
        Transition t3 = new Transition(s2, s3, "a,b");
        t2.getVisualProperties().setCurveFactor(3*Math.PI/2);
        a.addTransition(t2);
        a.addTransition(t1);
        a.addTransition(t3);
        a.setInitialState(s1);
        a.addAcceptingState(s2);

        CanvasLabel label1 = new CanvasLabel("caption");
        label1.move(30, 160);
        this.editor = new StateDiagramEditor(a, label1);

        paintingArea.setLayout(new BorderLayout());
        paintingArea.setPreferredSize(new Dimension(300,200));
        AutomatonPreviewCanvas canvas = new AutomatonPreviewCanvas(editor);
        paintingArea.add(canvas, BorderLayout.CENTER);
    }


    private void setTabCaptions() {
        TabPaneMain.setTitleAt(0, "Visualization");
        //tabPaneVisualization.setMaximumSize(new Dimension(500,400));
        tabPaneVisualization.setTitleAt(0, "States");
        tabPaneVisualization.setTitleAt(1, "Initial Arrow");
        tabPaneVisualization.setTitleAt(2, "Transitions");
        tabPaneVisualization.setTitleAt(3, "Labels");
        tabPaneVisualization.setTitleAt(4, "Canvas");
        
    }


    private void initVisualizationStateTab() {
        StateDiameterSpinner.setModel(new SpinnerNumberModel(properties.getAutomatonStateDiameter(), 20, 100, 2));
        stateStrokeStyleCombo.setModel(new DefaultComboBoxModel(StrokeStyle.STYLES));
        stateStrokeStyleCombo.setSelectedItem(properties.getAutomatonStateStrokeStyle().getTypeString());
        stateStrokeWidthSpinner.setModel(new SpinnerNumberModel(properties.getAutomatonStateStrokeWidth(), 0, 5, 0.5));
        stateStrokeColorButton.setBackground(properties.getAutomatonStateStrokeColor());
        stateFontColorButton.setBackground(properties.getAutomatonStateFontColor());
        stateFillColorButton.setBackground(properties.getAutomatonStateFillColor());
        stateMouseOverFillColorButton.setBackground(properties.getAutomatonStateMouseOverFillColor());
        stateMouseOverFontColorButton.setBackground(properties.getAutomatonStateMouseOverFontColor());
        stateMouseOverStrokeColorButton.setBackground(properties.getAutomatonStateMouseOverStrokeColor());
        stateSelectedFillColorButton.setBackground(properties.getAutomatonStateSelectedFillColor());
        stateSelectedFontColorButton.setBackground(properties.getAutomatonStateSelectedFontColor());
        stateSelectedStrokeColorButton.setBackground(properties.getAutomatonStateSelectedStrokeColor());        
    }


    private void initVisualizationInitialArrowTab() {
        initArrowStrokeColorButton.setBackground(properties.getAutomatonInitArrowColor());
        initArrowStrokeStyleCombo.setModel(new DefaultComboBoxModel(StrokeStyle.STYLES));
        initArrowStrokeStyleCombo.setSelectedItem(properties.getAutomatonInitArrowStrokeStyle().getTypeString());
        initArrowStrokeWidthSpinner.setModel(new SpinnerNumberModel(properties.getAutomatonInitArrowStrokeWidth(), 0, 5, 0.5));
       
        initArrowOrientationCombo.setModel(new DefaultComboBoxModel(InitialArrow.BASIC_ORIENTATIONS));
        initArrowLengthSpinner.setModel(new SpinnerNumberModel(properties.getAutomatonInitArrowLength(), 5, 50, 1));

        initArrowMouseOverColorButton.setBackground(properties.getAutomatonInitArrowColorMouseOver());
        initArrowSelectedColorButton.setBackground(properties.getAutomatonInitArrowColorSelected());

    }

    private void initVisualizationTransitionTab() {
        transitionStrokeStyleCombo.setModel(new DefaultComboBoxModel(StrokeStyle.STYLES));
        transitionStrokeStyleCombo.setSelectedItem(properties.getAutomatonTransitionStrokeStyle().getTypeString());
        transitionStrokeWidthSpinner.setModel(new SpinnerNumberModel(properties.getAutomatonTransitionStrokeWidth(), 0, 5, 0.5));
        transitionStrokeColorButton.setBackground(properties.getAutomatonTransitionStrokeColor());
        transitionFontColorButton.setBackground(properties.getAutomatonTransitionFontColor());
        transitionBackColorButton.setBackground(properties.getAutomatonTransitionBackColor());
        
        transitionMouseOverBackColorButton.setBackground(properties.getAutomatonTransitionMouseOverBackColor());
        transitionMouseOverFontColorButton.setBackground(properties.getAutomatonTransitionMouseOverFontColor());
        transitionMouseOverStrokeColorButton.setBackground(properties.getAutomatonTransitionMouseOverStrokeColor());
        transitionSelectedBackColorButton.setBackground(properties.getAutomatonTransitionSelectedBackColor());
        transitionSelectedFontColorButton.setBackground(properties.getAutomatonTransitionSelectedFontColor());
        transitionSelectedStrokeColorButton.setBackground(properties.getAutomatonTransitionSelectedStrokeColor());
    }


    private void initVisualizationLabelTab() {
        labelFontColorButton.setBackground(properties.getAutomatonLabelFrontColor());
        labelBackgroundColorButton.setBackground(properties.getAutomatonLabelBackColor());
        labelBackgroundTransparencySpinner.setModel(new SpinnerNumberModel(properties.getAutomatonLabelBackTransparency(), 0, 1, 0.1));
        boolean backgroundEnabled = properties.isAutomatonLabelBackEnabled();
        labelBackgroundEnabledCheckBox.setSelected(backgroundEnabled);
        labelBackgroundColorButton.setEnabled(backgroundEnabled);
        labelBackgroundTransparencySpinner.setEnabled(backgroundEnabled);
        labelMouseOverBackColorButton.setBackground(properties.getAutomatonLabelMouseOverBackColor());
        labelMouseOverFontColorButton.setBackground(properties.getAutomatonLabelMouseOverFrontColor());
        labelSelectedBackColorButton.setBackground(properties.getAutomatonLabelSelectedBackColor());
        labelSelectedFontColorButton.setBackground(properties.getAutomatonLabelSelectedFrontColor());
    }


    private void initVisualizationCanvasTab() {
        CanvasBackgroundColorButton.setBackground(properties.getAutomatonCanvasBackground());
        AntialiasingShapeEnabledCheckBox.setSelected(properties.isAutomatonCanvasAntialiasingShape());
        AntialiasingTextEnabledCheckBox.setSelected(properties.isAutomatonCanvasAntialiasingText());
    }





    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabPaneMain = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        tabPaneVisualization = new javax.swing.JTabbedPane();
        tabStatePanel = new javax.swing.JPanel();
        panelStateBasic = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        stateFillColorButton = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        StateDiameterSpinner = new javax.swing.JSpinner();
        panelStateFont = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        stateFontButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        stateFontColorButton = new javax.swing.JButton();
        panelStateStroke = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        stateStrokeWidthSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        stateStrokeColorButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        stateStrokeStyleCombo = new javax.swing.JComboBox();
        panelStateMouseOver = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        stateMouseOverFontColorButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        stateMouseOverFillColorButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        stateMouseOverStrokeColorButton = new javax.swing.JButton();
        panelStateSelected = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        stateSelectedFontColorButton = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        stateSelectedFillColorButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        stateSelectedStrokeColorButton = new javax.swing.JButton();
        tabInitialArrowPanel = new javax.swing.JPanel();
        panelInitArrowStroke = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        initArrowStrokeWidthSpinner = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        initArrowStrokeColorButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        initArrowStrokeStyleCombo = new javax.swing.JComboBox();
        panelInitArrowStroke1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        initArrowLengthSpinner = new javax.swing.JSpinner();
        jLabel18 = new javax.swing.JLabel();
        initArrowOrientationCombo = new javax.swing.JComboBox();
        panelInitArrowMouseOver = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        initArrowMouseOverColorButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelInitArrowSelected = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        initArrowSelectedColorButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        tabTransitionPanel = new javax.swing.JPanel();
        panelTransitionBasic = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        transitionFontButton = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        transitionFontColorButton = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        transitionBackColorButton = new javax.swing.JButton();
        panelTransitionStroke = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        transitionStrokeWidthSpinner = new javax.swing.JSpinner();
        jLabel21 = new javax.swing.JLabel();
        transitionStrokeColorButton = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        transitionStrokeStyleCombo = new javax.swing.JComboBox();
        panelTransitionMouseOver = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        transitionMouseOverFontColorButton = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        transitionMouseOverBackColorButton = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        transitionMouseOverStrokeColorButton = new javax.swing.JButton();
        panelTransitionSelected = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        transitionSelectedFontColorButton = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        transitionSelectedBackColorButton = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        transitionSelectedStrokeColorButton = new javax.swing.JButton();
        tabLabelPanel = new javax.swing.JPanel();
        panelTransitionFont = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        labelFontButton = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        labelFontColorButton = new javax.swing.JButton();
        panelLabelBackground = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        labelBackgroundEnabledCheckBox = new javax.swing.JCheckBox();
        jLabel35 = new javax.swing.JLabel();
        labelBackgroundTransparencySpinner = new javax.swing.JSpinner();
        jLabel38 = new javax.swing.JLabel();
        labelBackgroundColorButton = new javax.swing.JButton();
        panelTransitionMouseOver1 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        labelMouseOverFontColorButton = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        labelMouseOverBackColorButton = new javax.swing.JButton();
        panelTransitionSelected1 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        labelSelectedFontColorButton = new javax.swing.JButton();
        jLabel40 = new javax.swing.JLabel();
        labelSelectedBackColorButton = new javax.swing.JButton();
        tabCanvasPanel = new javax.swing.JPanel();
        panelCanvasAntialiasing = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        AntialiasingShapeEnabledCheckBox = new javax.swing.JCheckBox();
        jLabel43 = new javax.swing.JLabel();
        AntialiasingTextEnabledCheckBox = new javax.swing.JCheckBox();
        panelCanvasBackground = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        CanvasBackgroundColorButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        paintingArea = new javax.swing.JPanel();
        buttonApply = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tabStatePanel.setLayout(new java.awt.GridLayout(2, 3));

        panelStateBasic.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic"));
        panelStateBasic.setLayout(new java.awt.GridLayout(3, 2));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Color:");
        panelStateBasic.add(jLabel6);

        stateFillColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateFillColorButtonActionPerformed(evt);
            }
        });
        panelStateBasic.add(stateFillColorButton);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("Diameter:");
        panelStateBasic.add(jLabel34);

        StateDiameterSpinner.setOpaque(false);
        StateDiameterSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                StateDiameterSpinnerStateChanged(evt);
            }
        });
        panelStateBasic.add(StateDiameterSpinner);

        tabStatePanel.add(panelStateBasic);

        panelStateFont.setBorder(javax.swing.BorderFactory.createTitledBorder("Font"));
        panelStateFont.setLayout(new java.awt.GridLayout(3, 2));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Font:");
        panelStateFont.add(jLabel4);

        stateFontButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateFontButtonActionPerformed(evt);
            }
        });
        panelStateFont.add(stateFontButton);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Color:");
        panelStateFont.add(jLabel5);

        stateFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateFontColorButtonActionPerformed(evt);
            }
        });
        panelStateFont.add(stateFontColorButton);

        tabStatePanel.add(panelStateFont);

        panelStateStroke.setBorder(javax.swing.BorderFactory.createTitledBorder("Stroke"));
        panelStateStroke.setLayout(new java.awt.GridLayout(3, 2));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Width:");
        panelStateStroke.add(jLabel1);

        stateStrokeWidthSpinner.setOpaque(false);
        stateStrokeWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                stateStrokeWidthSpinnerStateChanged(evt);
            }
        });
        panelStateStroke.add(stateStrokeWidthSpinner);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Color:");
        panelStateStroke.add(jLabel2);

        stateStrokeColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateStrokeColorButtonActionPerformed(evt);
            }
        });
        panelStateStroke.add(stateStrokeColorButton);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Style:");
        panelStateStroke.add(jLabel3);

        stateStrokeStyleCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        stateStrokeStyleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateStrokeStyleComboActionPerformed(evt);
            }
        });
        panelStateStroke.add(stateStrokeStyleCombo);

        tabStatePanel.add(panelStateStroke);

        panelStateMouseOver.setBorder(javax.swing.BorderFactory.createTitledBorder("Mouse Over"));
        panelStateMouseOver.setLayout(new java.awt.GridLayout(3, 2));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Font Color:");
        panelStateMouseOver.add(jLabel8);

        stateMouseOverFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateMouseOverFontColorButtonActionPerformed(evt);
            }
        });
        panelStateMouseOver.add(stateMouseOverFontColorButton);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Background Color:");
        panelStateMouseOver.add(jLabel9);

        stateMouseOverFillColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateMouseOverFillColorButtonActionPerformed(evt);
            }
        });
        panelStateMouseOver.add(stateMouseOverFillColorButton);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Stroke Color:");
        panelStateMouseOver.add(jLabel7);

        stateMouseOverStrokeColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateMouseOverStrokeColorButtonActionPerformed(evt);
            }
        });
        panelStateMouseOver.add(stateMouseOverStrokeColorButton);

        tabStatePanel.add(panelStateMouseOver);

        panelStateSelected.setBorder(javax.swing.BorderFactory.createTitledBorder("State Selected"));
        panelStateSelected.setLayout(new java.awt.GridLayout(3, 2));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Font Color:");
        panelStateSelected.add(jLabel11);

        stateSelectedFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateSelectedFontColorButtonActionPerformed(evt);
            }
        });
        panelStateSelected.add(stateSelectedFontColorButton);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Background Color:");
        panelStateSelected.add(jLabel12);

        stateSelectedFillColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateSelectedFillColorButtonActionPerformed(evt);
            }
        });
        panelStateSelected.add(stateSelectedFillColorButton);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Stroke Color:");
        panelStateSelected.add(jLabel10);

        stateSelectedStrokeColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateSelectedStrokeColorButtonActionPerformed(evt);
            }
        });
        panelStateSelected.add(stateSelectedStrokeColorButton);

        tabStatePanel.add(panelStateSelected);

        tabPaneVisualization.addTab("tab1", tabStatePanel);

        tabInitialArrowPanel.setLayout(new java.awt.GridLayout(2, 2));

        panelInitArrowStroke.setBorder(javax.swing.BorderFactory.createTitledBorder("Stroke"));
        panelInitArrowStroke.setLayout(new java.awt.GridLayout(3, 2));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Width:");
        panelInitArrowStroke.add(jLabel13);

        initArrowStrokeWidthSpinner.setOpaque(false);
        initArrowStrokeWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                initArrowStrokeWidthSpinnerStateChanged(evt);
            }
        });
        initArrowStrokeWidthSpinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                initArrowStrokeWidthSpinnerAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        panelInitArrowStroke.add(initArrowStrokeWidthSpinner);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Color:");
        panelInitArrowStroke.add(jLabel14);

        initArrowStrokeColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initArrowStrokeColorButtonActionPerformed(evt);
            }
        });
        panelInitArrowStroke.add(initArrowStrokeColorButton);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Style:");
        panelInitArrowStroke.add(jLabel15);

        initArrowStrokeStyleCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        initArrowStrokeStyleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initArrowStrokeStyleComboActionPerformed(evt);
            }
        });
        panelInitArrowStroke.add(initArrowStrokeStyleCombo);

        tabInitialArrowPanel.add(panelInitArrowStroke);

        panelInitArrowStroke1.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic"));
        panelInitArrowStroke1.setLayout(new java.awt.GridLayout(3, 2));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Length:");
        panelInitArrowStroke1.add(jLabel16);

        initArrowLengthSpinner.setOpaque(false);
        initArrowLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                initArrowLengthSpinnerStateChanged(evt);
            }
        });
        initArrowLengthSpinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                initArrowLengthSpinnerAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        panelInitArrowStroke1.add(initArrowLengthSpinner);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Orientation:");
        panelInitArrowStroke1.add(jLabel18);

        initArrowOrientationCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        initArrowOrientationCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initArrowOrientationComboActionPerformed(evt);
            }
        });
        panelInitArrowStroke1.add(initArrowOrientationCombo);

        tabInitialArrowPanel.add(panelInitArrowStroke1);

        panelInitArrowMouseOver.setBorder(javax.swing.BorderFactory.createTitledBorder("Mouse Over"));
        panelInitArrowMouseOver.setLayout(new java.awt.GridLayout(3, 2));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Color:");
        panelInitArrowMouseOver.add(jLabel19);

        initArrowMouseOverColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initArrowMouseOverColorButtonActionPerformed(evt);
            }
        });
        panelInitArrowMouseOver.add(initArrowMouseOverColorButton);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        panelInitArrowMouseOver.add(jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        panelInitArrowMouseOver.add(jPanel3);

        tabInitialArrowPanel.add(panelInitArrowMouseOver);

        panelInitArrowSelected.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected"));
        panelInitArrowSelected.setLayout(new java.awt.GridLayout(3, 2));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Color:");
        panelInitArrowSelected.add(jLabel20);

        initArrowSelectedColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initArrowSelectedColorButtonActionPerformed(evt);
            }
        });
        panelInitArrowSelected.add(initArrowSelectedColorButton);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        panelInitArrowSelected.add(jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        panelInitArrowSelected.add(jPanel5);

        tabInitialArrowPanel.add(panelInitArrowSelected);

        tabPaneVisualization.addTab("tab2", tabInitialArrowPanel);

        tabTransitionPanel.setLayout(new java.awt.GridLayout(2, 2));

        panelTransitionBasic.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic"));
        panelTransitionBasic.setLayout(new java.awt.GridLayout(3, 2));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Font:");
        panelTransitionBasic.add(jLabel23);

        transitionFontButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionFontButtonActionPerformed(evt);
            }
        });
        panelTransitionBasic.add(transitionFontButton);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Font Color:");
        panelTransitionBasic.add(jLabel24);

        transitionFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionFontColorButtonActionPerformed(evt);
            }
        });
        panelTransitionBasic.add(transitionFontColorButton);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("Background Color:");
        panelTransitionBasic.add(jLabel25);

        transitionBackColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionBackColorButtonActionPerformed(evt);
            }
        });
        panelTransitionBasic.add(transitionBackColorButton);

        tabTransitionPanel.add(panelTransitionBasic);

        panelTransitionStroke.setBorder(javax.swing.BorderFactory.createTitledBorder("Stroke"));
        panelTransitionStroke.setLayout(new java.awt.GridLayout(3, 2));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Width:");
        panelTransitionStroke.add(jLabel17);

        transitionStrokeWidthSpinner.setOpaque(false);
        transitionStrokeWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                transitionStrokeWidthSpinnerStateChanged(evt);
            }
        });
        panelTransitionStroke.add(transitionStrokeWidthSpinner);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Color:");
        panelTransitionStroke.add(jLabel21);

        transitionStrokeColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionStrokeColorButtonActionPerformed(evt);
            }
        });
        panelTransitionStroke.add(transitionStrokeColorButton);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Style:");
        panelTransitionStroke.add(jLabel22);

        transitionStrokeStyleCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        transitionStrokeStyleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionStrokeStyleComboActionPerformed(evt);
            }
        });
        panelTransitionStroke.add(transitionStrokeStyleCombo);

        tabTransitionPanel.add(panelTransitionStroke);

        panelTransitionMouseOver.setBorder(javax.swing.BorderFactory.createTitledBorder("Mouse Over"));
        panelTransitionMouseOver.setLayout(new java.awt.GridLayout(3, 2));

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Font Color:");
        panelTransitionMouseOver.add(jLabel27);

        transitionMouseOverFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionMouseOverFontColorButtonActionPerformed(evt);
            }
        });
        panelTransitionMouseOver.add(transitionMouseOverFontColorButton);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Background Color:");
        panelTransitionMouseOver.add(jLabel28);

        transitionMouseOverBackColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionMouseOverBackColorButtonActionPerformed(evt);
            }
        });
        panelTransitionMouseOver.add(transitionMouseOverBackColorButton);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("Stroke Color:");
        panelTransitionMouseOver.add(jLabel26);

        transitionMouseOverStrokeColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionMouseOverStrokeColorButtonActionPerformed(evt);
            }
        });
        panelTransitionMouseOver.add(transitionMouseOverStrokeColorButton);

        tabTransitionPanel.add(panelTransitionMouseOver);

        panelTransitionSelected.setBorder(javax.swing.BorderFactory.createTitledBorder("State Selected"));
        panelTransitionSelected.setLayout(new java.awt.GridLayout(3, 2));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("Font Color:");
        panelTransitionSelected.add(jLabel30);

        transitionSelectedFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionSelectedFontColorButtonActionPerformed(evt);
            }
        });
        panelTransitionSelected.add(transitionSelectedFontColorButton);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Background Color:");
        panelTransitionSelected.add(jLabel31);

        transitionSelectedBackColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionSelectedBackColorButtonActionPerformed(evt);
            }
        });
        panelTransitionSelected.add(transitionSelectedBackColorButton);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Stroke Color:");
        panelTransitionSelected.add(jLabel29);

        transitionSelectedStrokeColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transitionSelectedStrokeColorButtonActionPerformed(evt);
            }
        });
        panelTransitionSelected.add(transitionSelectedStrokeColorButton);

        tabTransitionPanel.add(panelTransitionSelected);

        tabPaneVisualization.addTab("tab3", tabTransitionPanel);

        tabLabelPanel.setLayout(new java.awt.GridLayout(2, 2));

        panelTransitionFont.setBorder(javax.swing.BorderFactory.createTitledBorder("Font"));
        panelTransitionFont.setLayout(new java.awt.GridLayout(3, 2));

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("Font:");
        panelTransitionFont.add(jLabel32);

        labelFontButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelFontButtonActionPerformed(evt);
            }
        });
        panelTransitionFont.add(labelFontButton);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel33.setText("Color:");
        panelTransitionFont.add(jLabel33);

        labelFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelFontColorButtonActionPerformed(evt);
            }
        });
        panelTransitionFont.add(labelFontColorButton);

        tabLabelPanel.add(panelTransitionFont);

        panelLabelBackground.setBorder(javax.swing.BorderFactory.createTitledBorder("Background"));
        panelLabelBackground.setLayout(new java.awt.GridLayout(3, 2));

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Enabled:");
        panelLabelBackground.add(jLabel41);

        labelBackgroundEnabledCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelBackgroundEnabledCheckBoxActionPerformed(evt);
            }
        });
        panelLabelBackground.add(labelBackgroundEnabledCheckBox);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("Transparency");
        panelLabelBackground.add(jLabel35);

        labelBackgroundTransparencySpinner.setOpaque(false);
        labelBackgroundTransparencySpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                labelBackgroundTransparencySpinnerStateChanged(evt);
            }
        });
        panelLabelBackground.add(labelBackgroundTransparencySpinner);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("Color:");
        panelLabelBackground.add(jLabel38);

        labelBackgroundColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelBackgroundColorButtonActionPerformed(evt);
            }
        });
        panelLabelBackground.add(labelBackgroundColorButton);

        tabLabelPanel.add(panelLabelBackground);

        panelTransitionMouseOver1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mouse Over"));
        panelTransitionMouseOver1.setLayout(new java.awt.GridLayout(3, 2));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("Font Color:");
        panelTransitionMouseOver1.add(jLabel36);

        labelMouseOverFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelMouseOverFontColorButtonActionPerformed(evt);
            }
        });
        panelTransitionMouseOver1.add(labelMouseOverFontColorButton);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Background Color:");
        panelTransitionMouseOver1.add(jLabel37);

        labelMouseOverBackColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelMouseOverBackColorButtonActionPerformed(evt);
            }
        });
        panelTransitionMouseOver1.add(labelMouseOverBackColorButton);

        tabLabelPanel.add(panelTransitionMouseOver1);

        panelTransitionSelected1.setBorder(javax.swing.BorderFactory.createTitledBorder("State Selected"));
        panelTransitionSelected1.setLayout(new java.awt.GridLayout(3, 2));

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel39.setText("Font Color:");
        panelTransitionSelected1.add(jLabel39);

        labelSelectedFontColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelSelectedFontColorButtonActionPerformed(evt);
            }
        });
        panelTransitionSelected1.add(labelSelectedFontColorButton);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("Background Color:");
        panelTransitionSelected1.add(jLabel40);

        labelSelectedBackColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelSelectedBackColorButtonActionPerformed(evt);
            }
        });
        panelTransitionSelected1.add(labelSelectedBackColorButton);

        tabLabelPanel.add(panelTransitionSelected1);

        tabPaneVisualization.addTab("tab4", tabLabelPanel);

        tabCanvasPanel.setLayout(new java.awt.GridLayout(2, 2));

        panelCanvasAntialiasing.setBorder(javax.swing.BorderFactory.createTitledBorder("Antialiasing"));
        panelCanvasAntialiasing.setLayout(new java.awt.GridLayout(3, 2));

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Geometry:");
        panelCanvasAntialiasing.add(jLabel42);

        AntialiasingShapeEnabledCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AntialiasingShapeEnabledCheckBoxActionPerformed(evt);
            }
        });
        panelCanvasAntialiasing.add(AntialiasingShapeEnabledCheckBox);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Text:");
        panelCanvasAntialiasing.add(jLabel43);

        AntialiasingTextEnabledCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AntialiasingTextEnabledCheckBoxActionPerformed(evt);
            }
        });
        panelCanvasAntialiasing.add(AntialiasingTextEnabledCheckBox);

        tabCanvasPanel.add(panelCanvasAntialiasing);

        panelCanvasBackground.setBorder(javax.swing.BorderFactory.createTitledBorder("Background"));
        panelCanvasBackground.setLayout(new java.awt.GridLayout(3, 2));

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel47.setText("Color:");
        panelCanvasBackground.add(jLabel47);

        CanvasBackgroundColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CanvasBackgroundColorButtonActionPerformed(evt);
            }
        });
        panelCanvasBackground.add(CanvasBackgroundColorButton);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        panelCanvasBackground.add(jPanel8);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        panelCanvasBackground.add(jPanel9);

        tabCanvasPanel.add(panelCanvasBackground);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );

        tabCanvasPanel.add(jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );

        tabCanvasPanel.add(jPanel7);

        tabPaneVisualization.addTab("tab5", tabCanvasPanel);

        paintingArea.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));

        javax.swing.GroupLayout paintingAreaLayout = new javax.swing.GroupLayout(paintingArea);
        paintingArea.setLayout(paintingAreaLayout);
        paintingAreaLayout.setHorizontalGroup(
            paintingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 834, Short.MAX_VALUE)
        );
        paintingAreaLayout.setVerticalGroup(
            paintingAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPaneVisualization, javax.swing.GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
            .addComponent(paintingArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(tabPaneVisualization, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paintingArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabPaneMain.addTab("tab1", jPanel2);

        buttonApply.setText("Apply");
        buttonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonApplyActionPerformed(evt);
            }
        });

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonApply)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancel)
                .addContainerGap(730, Short.MAX_VALUE))
            .addComponent(TabPaneMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(TabPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonApply)
                    .addComponent(buttonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        properties.restoreProperties();
        dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void labelBackgroundColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelBackgroundColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Background color", properties.getAutomatonLabelBackColor());
        if (color != null) {
            properties.setAutomatonLabelBackColor(color);
            labelBackgroundColorButton.setBackground(color);
            for (CanvasLabel label : editor.getLabels()) {
                label.setBackgroundColor(color);
            }
            paintingArea.repaint();
        }
}//GEN-LAST:event_labelBackgroundColorButtonActionPerformed

    private void labelBackgroundEnabledCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelBackgroundEnabledCheckBoxActionPerformed
        boolean enabled = labelBackgroundEnabledCheckBox.isSelected();
        labelBackgroundColorButton.setEnabled(enabled);
        labelBackgroundTransparencySpinner.setEnabled(enabled);
        properties.setAutomatonLabelBackEnabled(enabled);
        paintingArea.repaint();
}//GEN-LAST:event_labelBackgroundEnabledCheckBoxActionPerformed

    private void labelBackgroundTransparencySpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_labelBackgroundTransparencySpinnerStateChanged
        double transparency = (Double) labelBackgroundTransparencySpinner.getValue();
        properties.setAutomatonLabelBackTransparency(transparency);
        paintingArea.repaint();
}//GEN-LAST:event_labelBackgroundTransparencySpinnerStateChanged

    private void labelSelectedBackColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelSelectedBackColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Label Selected Background Color", properties.getAutomatonLabelSelectedBackColor());
        if (color != null) {
            properties.setAutomatonLabelSelectedBackColor(color);
            labelSelectedBackColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_labelSelectedBackColorButtonActionPerformed

    private void labelSelectedFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelSelectedFontColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Label Selected Font Color", properties.getAutomatonLabelSelectedFrontColor());
        if (color != null) {
            properties.setAutomatonLabelSelectedFrontColor(color);
            labelSelectedFontColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_labelSelectedFontColorButtonActionPerformed

    private void labelMouseOverBackColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelMouseOverBackColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Mouse Over Background Color", properties.getAutomatonLabelMouseOverBackColor());
        if (color != null) {
            properties.setAutomatonLabelMouseOverBackColor(color);
            labelMouseOverBackColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_labelMouseOverBackColorButtonActionPerformed

    private void labelMouseOverFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelMouseOverFontColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Mouse Over Font Color", properties.getAutomatonLabelMouseOverFrontColor());
        if (color != null) {
            properties.setAutomatonLabelMouseOverFrontColor(color);
            labelMouseOverFontColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_labelMouseOverFontColorButtonActionPerformed

    private void labelFontButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelFontButtonActionPerformed
        int result = fontChooser.showDialog(this);
        if (result == JFontChooser.OK_OPTION) {
            Font font = fontChooser.getSelectedFont();
            properties.setAutomatonLabelFont(font);
            for (CanvasLabel label : editor.getLabels()) {
                label.setFont(font);
            }
            paintingArea.repaint();
        }
    }//GEN-LAST:event_labelFontButtonActionPerformed

    private void labelFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelFontColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Font color", properties.getAutomatonLabelFrontColor());
        if (color != null) {
            properties.setAutomatonLabelFrontColor(color);
            labelFontColorButton.setBackground(color);
            for (CanvasLabel label : editor.getLabels()) {
                label.setForegroundColor(color);
            }
            paintingArea.repaint();
        }
}//GEN-LAST:event_labelFontColorButtonActionPerformed

    private void transitionSelectedStrokeColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionSelectedStrokeColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Transition Selected Stroke Color", properties.getAutomatonTransitionSelectedStrokeColor());
        if (color != null) {
            properties.setAutomatonTransitionSelectedStrokeColor(color);
            transitionSelectedStrokeColorButton.setBackground(color);
            paintingArea.repaint();
        }
    }//GEN-LAST:event_transitionSelectedStrokeColorButtonActionPerformed

    private void transitionSelectedBackColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionSelectedBackColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Transition Selected Background Color", properties.getAutomatonTransitionSelectedBackColor());
        if (color != null) {
            properties.setAutomatonTransitionSelectedBackColor(color);
            transitionSelectedBackColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_transitionSelectedBackColorButtonActionPerformed

    private void transitionSelectedFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionSelectedFontColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Transition Selected Font Color", properties.getAutomatonTransitionSelectedFontColor());
        if (color != null) {
            properties.setAutomatonTransitionSelectedFontColor(color);
            transitionSelectedFontColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_transitionSelectedFontColorButtonActionPerformed

    private void transitionMouseOverStrokeColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionMouseOverStrokeColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Mouse Over Stroke Color", properties.getAutomatonTransitionMouseOverStrokeColor());
        if (color != null) {
            properties.setAutomatonTransitionMouseOverStrokeColor(color);
            transitionMouseOverStrokeColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_transitionMouseOverStrokeColorButtonActionPerformed

    private void transitionMouseOverBackColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionMouseOverBackColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Mouse Over Background Color", properties.getAutomatonTransitionMouseOverBackColor());
        if (color != null) {
            properties.setAutomatonTransitionMouseOverBackColor(color);
            transitionMouseOverBackColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_transitionMouseOverBackColorButtonActionPerformed

    private void transitionMouseOverFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionMouseOverFontColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Mouse Over Font Color", properties.getAutomatonTransitionMouseOverFontColor());
        if (color != null) {
            properties.setAutomatonTransitionMouseOverFontColor(color);
            transitionMouseOverFontColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_transitionMouseOverFontColorButtonActionPerformed

    private void transitionStrokeStyleComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionStrokeStyleComboActionPerformed
        properties.setAutomatonTransitionStrokeStyle(new StrokeStyle((String) transitionStrokeStyleCombo.getSelectedItem()));
        paintingArea.repaint();
}//GEN-LAST:event_transitionStrokeStyleComboActionPerformed

    private void transitionStrokeColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionStrokeColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Stroke color", properties.getAutomatonTransitionStrokeColor());
        if (color != null) {
            properties.setAutomatonTransitionStrokeColor(color);
            transitionStrokeColorButton.setBackground(color);
            for (Transition transition : editor.getAutomaton().getTransitions()) {
                transition.getVisualProperties().setStrokeColor(color);
            }
            paintingArea.repaint();
        }
}//GEN-LAST:event_transitionStrokeColorButtonActionPerformed

    private void transitionStrokeWidthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_transitionStrokeWidthSpinnerStateChanged
        Double width = (Double) transitionStrokeWidthSpinner.getValue();
        properties.setAutomatonTransitionStrokeWidth(width);
        paintingArea.repaint();
    }//GEN-LAST:event_transitionStrokeWidthSpinnerStateChanged

    private void transitionFontButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionFontButtonActionPerformed
        int result = fontChooser.showDialog(this);
        if (result == JFontChooser.OK_OPTION) {
            Font font = fontChooser.getSelectedFont();
            properties.setAutomatonTransitionFont(font);
            paintingArea.repaint();
        }
}//GEN-LAST:event_transitionFontButtonActionPerformed

    private void transitionBackColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionBackColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Background color", properties.getAutomatonTransitionBackColor());
        if (color != null) {
            properties.setAutomatonTransitionBackColor(color);
            transitionBackColorButton.setBackground(color);
            paintingArea.repaint();
        }
    }//GEN-LAST:event_transitionBackColorButtonActionPerformed

    private void transitionFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transitionFontColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Font color", properties.getAutomatonTransitionFontColor());
        if (color != null) {
            properties.setAutomatonTransitionFontColor(color);
            transitionFontColorButton.setBackground(color);
            for (Transition transition : editor.getAutomaton().getTransitions()) {
                transition.getVisualProperties().setFontColor(color);
            }
            paintingArea.repaint();
        }
}//GEN-LAST:event_transitionFontColorButtonActionPerformed

    private void initArrowSelectedColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initArrowSelectedColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Initial Arrow Selected Color", properties.getAutomatonInitArrowColorSelected());
        if (color != null) {
            properties.setAutomatonInitArrowColorSelected(color);
            initArrowSelectedColorButton.setBackground(color);
            paintingArea.repaint();
        }
}//GEN-LAST:event_initArrowSelectedColorButtonActionPerformed

        private void initArrowMouseOverColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initArrowMouseOverColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Initial Arrow Mouse Over Color", properties.getAutomatonInitArrowColorMouseOver());
            if (color != null) {
                properties.setAutomatonInitArrowColorMouseOver(color);
                initArrowMouseOverColorButton.setBackground(color);
                paintingArea.repaint();
            }
}//GEN-LAST:event_initArrowMouseOverColorButtonActionPerformed

        private void initArrowOrientationComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initArrowOrientationComboActionPerformed
            String orientation = ((String) initArrowOrientationCombo.getSelectedItem());
            double angle = 0;
            if(orientation.equals(InitialArrow.RIGHT)) {
                angle = Math.PI;
            } else if (orientation.equals(InitialArrow.TOP)) {
                angle = Math.PI/2;
            } else if (orientation.equals(InitialArrow.BOTTOM)) {
                angle = 3*Math.PI/2;
            }
            properties.setAutomatonInitArrowOrientation(angle);
            editor.getInitArrow().setOrientation(angle);
            paintingArea.repaint();
}//GEN-LAST:event_initArrowOrientationComboActionPerformed

        private void initArrowLengthSpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_initArrowLengthSpinnerAncestorAdded
            // TODO add your handling code here:
}//GEN-LAST:event_initArrowLengthSpinnerAncestorAdded

        private void initArrowLengthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_initArrowLengthSpinnerStateChanged
            int length = (Integer) initArrowLengthSpinner.getValue();
            properties.setAutomatonInitArrowLength(length);
            editor.getInitArrow().setLength(length);
            paintingArea.repaint();
}//GEN-LAST:event_initArrowLengthSpinnerStateChanged

        private void initArrowStrokeStyleComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initArrowStrokeStyleComboActionPerformed
            StrokeStyle strokeStyle = new StrokeStyle((String) initArrowStrokeStyleCombo.getSelectedItem());
            properties.setAutomatonInitArrowStrokeStyle(strokeStyle);
            editor.getInitArrow().setStrokeStyle(strokeStyle);
            paintingArea.repaint();
}//GEN-LAST:event_initArrowStrokeStyleComboActionPerformed

        private void initArrowStrokeColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initArrowStrokeColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Initial Arrow color", properties.getAutomatonInitArrowColor());
            if (color != null) {
                properties.setAutomatonInitArrowColor(color);
                initArrowStrokeColorButton.setBackground(color);
                editor.getInitArrow().setColor(color);
                paintingArea.repaint();
            }
}//GEN-LAST:event_initArrowStrokeColorButtonActionPerformed

        private void initArrowStrokeWidthSpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_initArrowStrokeWidthSpinnerAncestorAdded

}//GEN-LAST:event_initArrowStrokeWidthSpinnerAncestorAdded

        private void initArrowStrokeWidthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_initArrowStrokeWidthSpinnerStateChanged
            Double width = (Double) initArrowStrokeWidthSpinner.getValue();
            properties.setAutomatonInitArrowStrokeWidth(width);
            editor.getInitArrow().setStrokeWidth(width);
            paintingArea.repaint();
}//GEN-LAST:event_initArrowStrokeWidthSpinnerStateChanged

        private void stateSelectedStrokeColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateSelectedStrokeColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "State Selecetd Stroke Color", properties.getAutomatonStateSelectedStrokeColor());
            if (color != null) {
                properties.setAutomatonStateSelectedStrokeColor(color);
                stateSelectedStrokeColorButton.setBackground(color);
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateSelectedStrokeColorButtonActionPerformed

        private void stateSelectedFillColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateSelectedFillColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "State Selecetd Fill Color", properties.getAutomatonStateSelectedFillColor());
            if (color != null) {
                properties.setAutomatonStateSelectedFillColor(color);
                stateSelectedFillColorButton.setBackground(color);
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateSelectedFillColorButtonActionPerformed

        private void stateSelectedFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateSelectedFontColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "State Selecetd Font Color", properties.getAutomatonStateSelectedFontColor());
            if (color != null) {
                properties.setAutomatonStateSelectedFontColor(color);
                stateSelectedFontColorButton.setBackground(color);
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateSelectedFontColorButtonActionPerformed

        private void stateMouseOverStrokeColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateMouseOverStrokeColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Mouse Over Stroke Color", properties.getAutomatonStateMouseOverStrokeColor());
            if (color != null) {
                properties.setAutomatonStateMouseOverStrokeColor(color);
                stateMouseOverStrokeColorButton.setBackground(color);
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateMouseOverStrokeColorButtonActionPerformed

        private void stateMouseOverFillColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateMouseOverFillColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Mouse Over Fill Color", properties.getAutomatonStateMouseOverFillColor());
            if (color != null) {
                properties.setAutomatonStateMouseOverFillColor(color);
                stateMouseOverFillColorButton.setBackground(color);
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateMouseOverFillColorButtonActionPerformed

        private void stateMouseOverFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateMouseOverFontColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Mouse Over Font Color", properties.getAutomatonStateMouseOverFontColor());
            if (color != null) {
                properties.setAutomatonStateMouseOverFontColor(color);
                stateMouseOverFontColorButton.setBackground(color);
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateMouseOverFontColorButtonActionPerformed

        private void stateStrokeStyleComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateStrokeStyleComboActionPerformed
            properties.setAutomatonStateStrokeStyle(new StrokeStyle((String) stateStrokeStyleCombo.getSelectedItem()));
            paintingArea.repaint();
}//GEN-LAST:event_stateStrokeStyleComboActionPerformed

        private void stateStrokeColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateStrokeColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Stroke color", properties.getAutomatonStateStrokeColor());
            if (color != null) {
                properties.setAutomatonStateStrokeColor(color);
                stateStrokeColorButton.setBackground(color);
                for (State state : editor.getAutomaton().getStates()) {
                    state.getVisualProperties().setStrokeColor(color);
                }
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateStrokeColorButtonActionPerformed

        private void stateStrokeWidthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_stateStrokeWidthSpinnerStateChanged
            Double width = (Double) stateStrokeWidthSpinner.getValue();
            properties.setAutomatonStateStrokeWidth(width);
            paintingArea.repaint();
}//GEN-LAST:event_stateStrokeWidthSpinnerStateChanged

        private void stateFontButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateFontButtonActionPerformed
            int result = fontChooser.showDialog(this);
            if (result == JFontChooser.OK_OPTION) {
                Font font = fontChooser.getSelectedFont();
                properties.setAutomatonStateFont(font);
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateFontButtonActionPerformed

        private void stateFillColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateFillColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Fill color", properties.getAutomatonStateFillColor());
            if (color != null) {
                properties.setAutomatonStateFillColor(color);
                stateFillColorButton.setBackground(color);
                for (State state : editor.getAutomaton().getStates()) {
                    state.getVisualProperties().setFillColor(color);
                }
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateFillColorButtonActionPerformed

        private void stateFontColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateFontColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Font color", properties.getAutomatonStateFontColor());
            if (color != null) {
                properties.setAutomatonStateFontColor(color);
                stateFontColorButton.setBackground(color);
                for (State state : editor.getAutomaton().getStates()) {
                    state.getVisualProperties().setFontColor(color);
                }
                paintingArea.repaint();
            }
}//GEN-LAST:event_stateFontColorButtonActionPerformed

        private void StateDiameterSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_StateDiameterSpinnerStateChanged
            int diameter = (Integer) StateDiameterSpinner.getValue();
            properties.setAutomatonStateDiameter(diameter);
            paintingArea.repaint();
        }//GEN-LAST:event_StateDiameterSpinnerStateChanged

        private void AntialiasingShapeEnabledCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AntialiasingShapeEnabledCheckBoxActionPerformed
            properties.setAutomatonCanvasAntialiasingShape(AntialiasingShapeEnabledCheckBox.isSelected());
            paintingArea.repaint();
        }//GEN-LAST:event_AntialiasingShapeEnabledCheckBoxActionPerformed

        private void CanvasBackgroundColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CanvasBackgroundColorButtonActionPerformed
            Color color = JColorChooser.showDialog(this, "Canvas Color", properties.getAutomatonCanvasBackground());
            if (color != null) {
                properties.setAutomatonCanvasBackground(color);
                CanvasBackgroundColorButton.setBackground(color);
                paintingArea.repaint();
            }
        }//GEN-LAST:event_CanvasBackgroundColorButtonActionPerformed

        private void AntialiasingTextEnabledCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AntialiasingTextEnabledCheckBoxActionPerformed
            properties.setAutomatonCanvasAntialiasingText(AntialiasingTextEnabledCheckBox.isSelected());
            paintingArea.repaint();
        }//GEN-LAST:event_AntialiasingTextEnabledCheckBoxActionPerformed

        private void buttonApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonApplyActionPerformed
            properties.storeProperties();
            dispose();
        }//GEN-LAST:event_buttonApplyActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditorPropertiesDialog dialog = new EditorPropertiesDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox AntialiasingShapeEnabledCheckBox;
    private javax.swing.JCheckBox AntialiasingTextEnabledCheckBox;
    private javax.swing.JButton CanvasBackgroundColorButton;
    private javax.swing.JSpinner StateDiameterSpinner;
    private javax.swing.JTabbedPane TabPaneMain;
    private javax.swing.JButton buttonApply;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JSpinner initArrowLengthSpinner;
    private javax.swing.JButton initArrowMouseOverColorButton;
    private javax.swing.JComboBox initArrowOrientationCombo;
    private javax.swing.JButton initArrowSelectedColorButton;
    private javax.swing.JButton initArrowStrokeColorButton;
    private javax.swing.JComboBox initArrowStrokeStyleCombo;
    private javax.swing.JSpinner initArrowStrokeWidthSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JButton labelBackgroundColorButton;
    private javax.swing.JCheckBox labelBackgroundEnabledCheckBox;
    private javax.swing.JSpinner labelBackgroundTransparencySpinner;
    private javax.swing.JButton labelFontButton;
    private javax.swing.JButton labelFontColorButton;
    private javax.swing.JButton labelMouseOverBackColorButton;
    private javax.swing.JButton labelMouseOverFontColorButton;
    private javax.swing.JButton labelSelectedBackColorButton;
    private javax.swing.JButton labelSelectedFontColorButton;
    private javax.swing.JPanel paintingArea;
    private javax.swing.JPanel panelCanvasAntialiasing;
    private javax.swing.JPanel panelCanvasBackground;
    private javax.swing.JPanel panelInitArrowMouseOver;
    private javax.swing.JPanel panelInitArrowSelected;
    private javax.swing.JPanel panelInitArrowStroke;
    private javax.swing.JPanel panelInitArrowStroke1;
    private javax.swing.JPanel panelLabelBackground;
    private javax.swing.JPanel panelStateBasic;
    private javax.swing.JPanel panelStateFont;
    private javax.swing.JPanel panelStateMouseOver;
    private javax.swing.JPanel panelStateSelected;
    private javax.swing.JPanel panelStateStroke;
    private javax.swing.JPanel panelTransitionBasic;
    private javax.swing.JPanel panelTransitionFont;
    private javax.swing.JPanel panelTransitionMouseOver;
    private javax.swing.JPanel panelTransitionMouseOver1;
    private javax.swing.JPanel panelTransitionSelected;
    private javax.swing.JPanel panelTransitionSelected1;
    private javax.swing.JPanel panelTransitionStroke;
    private javax.swing.JButton stateFillColorButton;
    private javax.swing.JButton stateFontButton;
    private javax.swing.JButton stateFontColorButton;
    private javax.swing.JButton stateMouseOverFillColorButton;
    private javax.swing.JButton stateMouseOverFontColorButton;
    private javax.swing.JButton stateMouseOverStrokeColorButton;
    private javax.swing.JButton stateSelectedFillColorButton;
    private javax.swing.JButton stateSelectedFontColorButton;
    private javax.swing.JButton stateSelectedStrokeColorButton;
    private javax.swing.JButton stateStrokeColorButton;
    private javax.swing.JComboBox stateStrokeStyleCombo;
    private javax.swing.JSpinner stateStrokeWidthSpinner;
    private javax.swing.JPanel tabCanvasPanel;
    private javax.swing.JPanel tabInitialArrowPanel;
    private javax.swing.JPanel tabLabelPanel;
    private javax.swing.JTabbedPane tabPaneVisualization;
    private javax.swing.JPanel tabStatePanel;
    private javax.swing.JPanel tabTransitionPanel;
    private javax.swing.JButton transitionBackColorButton;
    private javax.swing.JButton transitionFontButton;
    private javax.swing.JButton transitionFontColorButton;
    private javax.swing.JButton transitionMouseOverBackColorButton;
    private javax.swing.JButton transitionMouseOverFontColorButton;
    private javax.swing.JButton transitionMouseOverStrokeColorButton;
    private javax.swing.JButton transitionSelectedBackColorButton;
    private javax.swing.JButton transitionSelectedFontColorButton;
    private javax.swing.JButton transitionSelectedStrokeColorButton;
    private javax.swing.JButton transitionStrokeColorButton;
    private javax.swing.JComboBox transitionStrokeStyleCombo;
    private javax.swing.JSpinner transitionStrokeWidthSpinner;
    // End of variables declaration//GEN-END:variables

}
