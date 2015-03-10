/*
 * DialogModifyTransitions.java
 *
 * Created on 21. březen 2008, 13:41
 */

package jgaf.automaton.pda;

import jgaf.automaton.pda.tools.AlgorithmsLibrary;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 *
 * @author  hanis
 */
public class DialogModifyTransitions extends javax.swing.JDialog {
    
    private static final int LEFT = 0;
    private static final int PART = 1;
    private static final int RIGHT = 2;
    
    private DialogModifyAutomata parent;
    private PushdownAutomaton automaton;
    private TransFromListModel transFromListModel;
    private TransToListModel transToListModel;
    
    private Ternary selectedTernary = null;
    private Pair selectedPair = null;
    
    private int mode;
    
    public DialogModifyTransitions(java.awt.Dialog parent, PushdownAutomaton automaton) {
        super(parent, true);
        this.parent = (DialogModifyAutomata) parent;
        this.automaton = automaton;
        
        initComponents();
        hideAll();
        jLabel6.setText(") " + '\u042D' + " (");
        setLists();
        
        setLocationRelativeTo(parent);
     //   DesignManipulation.getInstance().setDesign(this.getContentPane());
        setColorForLists();
       // backToSelectMode();
        this.setVisible(true);
        
    }
    
    private void setColorForLists() {
        listTransFrom.setBackground(DesignManipulation.getInstance().getListBackNok());
        listTransFrom.setForeground(DesignManipulation.getInstance().getListFrontNok());
        listTransFrom.setSelectionBackground(DesignManipulation.getInstance().getListBackOk());
        listTransFrom.setSelectionForeground(DesignManipulation.getInstance().getListFrontOk());
        
        listTransTo.setBackground(DesignManipulation.getInstance().getListBackNok());
        listTransTo.setForeground(DesignManipulation.getInstance().getListFrontNok());
        listTransTo.setSelectionBackground(DesignManipulation.getInstance().getListBackOk());
        listTransTo.setSelectionForeground(DesignManipulation.getInstance().getListFrontOk());        
    }
    
    private void setLists() {
        transFromListModel = new TransFromListModel(automaton.getTransitionFunction());
        listTransFrom.setModel(transFromListModel);
        
        listTransFrom.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTransFrom.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                doSelectionFrom(listTransFrom.getSelectedIndex());
            }
        });
        
        transToListModel = new TransToListModel(automaton.getTransitionFunction());
        listTransTo.setModel(transToListModel);
        
        listTransTo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTransTo.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                doSelectionTo(listTransTo.getSelectedIndex());
            }
        });
        
    }
    
    private void doSelectionFrom(int row) {
        if(row == -1 || row > transFromListModel.getSize() - 1) {
            listTransFrom.setSelectedIndex(-1);
            butModifyLeft.setEnabled(false);
            butRemoveWhole.setEnabled(false);
            selectedTernary = null;
            transToListModel.nullModel();
            doSelectionTo(-1);
        } else {
            listTransFrom.setSelectedIndex(row);
            selectedTernary = (Ternary) listTransFrom.getSelectedValue();
            transToListModel.setTransTo(selectedTernary);
            doSelectionTo(0);
            butModifyLeft.setEnabled(true);
            butRemoveWhole.setEnabled(true);
        }
    }
    
    
    private void doSelectionTo(int row) {
        listTransTo.setSelectedIndex(row);
        if(row == -1) {
            selectedPair = null;
            enableRight(false);
        } else {
            selectedPair = (Pair) listTransTo.getSelectedValue();
            enableRight(true);
        }
    }
    
    
    
    private void enableRight(boolean enable) {
        butModifyPart.setEnabled(enable);
        butModifyRight.setEnabled(enable);
        butRemovePart.setEnabled(enable);
    }
    
    private void hideAll() {
        butModifyLeft.setEnabled(false);
        butModifyPart.setEnabled(false);
        butModifyRight.setEnabled(false);
        butRemovePart.setEnabled(false);
        butRemoveWhole.setEnabled(false);
        butStorno.setVisible(false);
        butOk.setVisible(false);
        panelTrans.setVisible(false);
    }
    
    private void setModifyMode(int mode) {
        listTransFrom.setEnabled(false);
        listTransTo.setEnabled(false);
        butModifyLeft.setEnabled(false);
        butModifyPart.setEnabled(false);
        butModifyRight.setEnabled(false);
        butRemovePart.setEnabled(false);
        butRemoveWhole.setEnabled(false);
        butOk.setVisible(true);
        butStorno.setVisible(true);
        panelTrans.setVisible(true);
        butBack.setEnabled(false);
        txtTStateFrom.setText(selectedTernary.getState());
        if(!selectedTernary.isEpsilon()) {
            txtTInput.setText(String.valueOf(selectedTernary.getSymbol()));
        }
        txtTStackFrom.setText(selectedTernary.getStackSymbol());
        txtTStateTo.setText(selectedPair.getState());
        if(!selectedPair.isEpsilonStack()) {
            txtTStackTo.setText(selectedPair.writeStack());
        }
        
        this.mode = mode;
        
        txtTStateFrom.setEditable(true);
        txtTInput.setEditable(true);
        txtTStackFrom.setEditable(true);
        txtTStateTo.setEditable(true);
        txtTStackTo.setEditable(true);
        
        switch(mode) {
            case LEFT : {
                txtTStateTo.setEditable(false);
                txtTStackTo.setEditable(false);
                txtTStateTo.setText(selectedPair.getState());
                txtTStackTo.setText(selectedPair.writeStack());
            } break;
            case RIGHT : {
                txtTStateFrom.setEditable(false);
                txtTInput.setEditable(false);
                txtTStackFrom.setEditable(false);
            } break;
        }
    }
    
    private void backToSelectMode() {
        listTransFrom.setEnabled(true);
        listTransTo.setEnabled(true);
        
        butOk.setVisible(false);
        butStorno.setVisible(false);
        panelTrans.setVisible(false);
        
        butModifyLeft.setEnabled(true);
        butModifyPart.setEnabled(true);
        butModifyRight.setEnabled(true);
        butRemovePart.setEnabled(true);
        butRemoveWhole.setEnabled(true);
        
        butBack.setEnabled(true);
    }
    
    
    private void removeTransition(int mode) throws WrongValuesException {
        if(mode == LEFT) {
            automaton.getTransitionFunction().removeTransition(selectedTernary);
        } else if(mode == RIGHT) {
            automaton.getTransitionFunction().removeTransition(selectedTernary, selectedPair);
        }
    }
    
    private Ternary getTernary() throws WrongValuesException {
        String stateFrom = txtTStateFrom.getText();
        String symbol = txtTInput.getText();
        String stackFrom = txtTStackFrom.getText();
        
        if(stateFrom == null || stateFrom.equals("")) {
            throw new WrongValuesException(L12N.getValue("warn.modify.miss.state.left"));
        }
        if(stackFrom == null || stackFrom.equals("")) {
            throw new WrongValuesException(L12N.getValue("warn.modify.miss.stack.left"));
        }        
        if(symbol.length() > 1) {
            throw new WrongValuesException(L12N.getValue("warn.modify.length.inputSymbol"));
        }                       
        Ternary ternary;
        if(symbol.equals("")) {
            ternary = new Ternary(stateFrom, stackFrom);
        } else {
            ternary = new Ternary(stateFrom, symbol.charAt(0), stackFrom);
        }
        return ternary;
    }
    
    
    private Pair getPair() throws WrongValuesException {
        String stateTo = txtTStateTo.getText();
        String stackTo = txtTStackTo.getText();
        if(stateTo == null || stateTo.equals("")) {
            throw new WrongValuesException(L12N.getValue("warn.modify.miss.state.right"));
        }
        Pair pair;
        if(stackTo == null || stackTo.equals("")) {
            pair = new Pair(stateTo);
        } else {
            List<String> listStack = AlgorithmsLibrary.createStack(stackTo);
            pair = new Pair(stateTo, listStack);
        }
        return pair;
    }
    
    
    
    private void modifyTransition() throws WrongValuesException {
        switch (mode) {
            case RIGHT : automaton.getTransitionFunction().modifyTransition(selectedTernary, selectedPair, getPair()); break;
            case LEFT : automaton.getTransitionFunction().modifyTransition(selectedTernary, getTernary()); break;
            case PART : automaton.getTransitionFunction().modifyTransition(selectedTernary, getTernary(), selectedPair, getPair()); break;
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        butBack = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listTransFrom = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        listTransTo = new javax.swing.JList();
        butRemovePart = new javax.swing.JButton();
        butRemoveWhole = new javax.swing.JButton();
        butModifyLeft = new javax.swing.JButton();
        butModifyRight = new javax.swing.JButton();
        butModifyPart = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panelTrans = new javax.swing.JPanel();
        txtTStateFrom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTInput = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTStackFrom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTStateTo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTStackTo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        butOk = new javax.swing.JButton();
        butStorno = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.trans.title"));
        butBack.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("freq.ok"));
        butBack.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBackActionPerformed(evt);
            }
        });

        listTransFrom.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(listTransFrom);

        listTransTo.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(listTransTo);

        butRemovePart.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.but.removeRight"));
        butRemovePart.setEnabled(false);
        butRemovePart.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butRemovePart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRemovePartActionPerformed(evt);
            }
        });

        butRemoveWhole.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.but.removeLeft"));
        butRemoveWhole.setEnabled(false);
        butRemoveWhole.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butRemoveWhole.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRemoveWholeActionPerformed(evt);
            }
        });

        butModifyLeft.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.but.changeLeft"));
        butModifyLeft.setEnabled(false);
        butModifyLeft.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModifyLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyLeftActionPerformed(evt);
            }
        });

        butModifyRight.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.but.changeRight"));
        butModifyRight.setEnabled(false);
        butModifyRight.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModifyRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyRightActionPerformed(evt);
            }
        });

        butModifyPart.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.but.changeWhole"));
        butModifyPart.setEnabled(false);
        butModifyPart.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModifyPart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyPartActionPerformed(evt);
            }
        });

        panelTrans.setBorder(javax.swing.BorderFactory.createTitledBorder(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.panel.transitionFunction")));

        jLabel3.setText(",");

        jLabel4.setText("(");

        jLabel5.setText(",");

        jLabel6.setText(") -> (");

        jLabel7.setText(",");

        txtTStackTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTStackToActionPerformed(evt);
            }
        });

        jLabel8.setText(")");

        org.jdesktop.layout.GroupLayout panelTransLayout = new org.jdesktop.layout.GroupLayout(panelTrans);
        panelTrans.setLayout(panelTransLayout);
        panelTransLayout.setHorizontalGroup(
            panelTransLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTransLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jLabel4)
                .add(4, 4, 4)
                .add(txtTStateFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(4, 4, 4)
                .add(jLabel3)
                .add(3, 3, 3)
                .add(txtTInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(4, 4, 4)
                .add(jLabel5)
                .add(4, 4, 4)
                .add(txtTStackFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(3, 3, 3)
                .add(jLabel6)
                .add(4, 4, 4)
                .add(txtTStateTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(3, 3, 3)
                .add(jLabel7)
                .add(3, 3, 3)
                .add(txtTStackTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(2, 2, 2)
                .add(jLabel8))
        );
        panelTransLayout.setVerticalGroup(
            panelTransLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelTransLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelTransLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtTInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(txtTStackFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6)
                    .add(txtTStateTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7)
                    .add(jLabel8)
                    .add(txtTStackTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(txtTStateFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        butOk.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("freq.ok"));
        butOk.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butOkActionPerformed(evt);
            }
        });

        butStorno.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("freq.storno"));
        butStorno.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butStorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStornoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(butOk)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butStorno))
                    .add(panelTrans, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(panelTrans, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(17, 17, 17)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(butOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(butStorno, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 168, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, butModifyPart, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2Layout.createSequentialGroup()
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(butRemoveWhole, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(butModifyLeft, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(butRemovePart, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(butModifyRight))))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {butModifyLeft, butModifyRight, butRemovePart, butRemoveWhole, jScrollPane1, jScrollPane2}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jScrollPane2)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(butRemoveWhole)
                    .add(butRemovePart, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(butModifyRight)
                    .add(butModifyLeft))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(butModifyPart)
                .add(15, 15, 15)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(butBack)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(butBack, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(16, 16, 16))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void butStornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStornoActionPerformed
        backToSelectMode();
    }//GEN-LAST:event_butStornoActionPerformed
    
    private void butOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOkActionPerformed
        try {
            modifyTransition();
            transFromListModel.refresh();
            selectedTernary = getTernary();
            listTransFrom.setSelectedIndex(transFromListModel.getIndexOf(selectedTernary));
            transToListModel.setTransTo(selectedTernary);
            selectedPair = getPair();            
            listTransTo.setSelectedIndex(transToListModel.getIndexOf(selectedPair));
            backToSelectMode();
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_butOkActionPerformed
    
    private void butBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBackActionPerformed
        this.dispose();
        parent.refreshDetail();        
    }//GEN-LAST:event_butBackActionPerformed
    
    private void butModifyPartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyPartActionPerformed
        setModifyMode(PART);
    }//GEN-LAST:event_butModifyPartActionPerformed
    
    private void txtTStackToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTStackToActionPerformed
        
    }//GEN-LAST:event_txtTStackToActionPerformed
    
    private void butModifyRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyRightActionPerformed
        setModifyMode(RIGHT);
    }//GEN-LAST:event_butModifyRightActionPerformed
    
    private void butModifyLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyLeftActionPerformed
        setModifyMode(LEFT);
    }//GEN-LAST:event_butModifyLeftActionPerformed
    
    private void butRemovePartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRemovePartActionPerformed
        try {
            removeTransition(RIGHT);
            transToListModel.setTransTo(selectedTernary);
            if(transToListModel.getSize() > 0) {
                doSelectionTo(0);
            } else {
                transFromListModel.refresh();
                doSelectionFrom(0);
            }
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_butRemovePartActionPerformed
    
    private void butRemoveWholeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRemoveWholeActionPerformed
        try {
            removeTransition(LEFT);
            transFromListModel.refresh();
            doSelectionTo(-1);
            doSelectionFrom(0);
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_butRemoveWholeActionPerformed
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBack;
    private javax.swing.JButton butModifyLeft;
    private javax.swing.JButton butModifyPart;
    private javax.swing.JButton butModifyRight;
    private javax.swing.JButton butOk;
    private javax.swing.JButton butRemovePart;
    private javax.swing.JButton butRemoveWhole;
    private javax.swing.JButton butStorno;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listTransFrom;
    private javax.swing.JList listTransTo;
    private javax.swing.JPanel panelTrans;
    private javax.swing.JTextField txtTInput;
    private javax.swing.JTextField txtTStackFrom;
    private javax.swing.JTextField txtTStackTo;
    private javax.swing.JTextField txtTStateFrom;
    private javax.swing.JTextField txtTStateTo;
    // End of variables declaration//GEN-END:variables
    
}
