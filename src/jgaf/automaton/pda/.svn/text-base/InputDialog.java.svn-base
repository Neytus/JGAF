/*
 * InputDialog.java
 *
 * Created on 2. březen 2008, 13:33
 */

package jgaf.automaton.pda;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author  hanis
 */
public class InputDialog extends javax.swing.JDialog {
    
    private PDASimulatorPanel simulator;
    private PushdownAutomaton automata;
    
    public InputDialog(java.awt.Frame frame, PDASimulatorPanel simulator, PushdownAutomaton automata) {
        super(frame, true);
        this.simulator =  simulator;
        this.automata = automata;
        initComponents();
        comboState.setModel(new DefaultComboBoxModel(automata.getStates().toArray()));
               
        if(!automata.isUsed()) {
            this.butLastAll.setEnabled(false);
            this.butCurrentAll.setEnabled(false);         
        }
        
        setLocationRelativeTo(simulator);
        //DesignManipulation.getInstance().setDesign(this.getContentPane());
        
        comboState.setSelectedItem(automata.getStartState());
        txtWord.setText("");
        txtStack.setText(automata.getInitialStackSymbolFormat());
        if(DefaultValues.getInstance().getAccepting() == PushdownAutomaton.FINAL) {
            radionAcceptFInal.setSelected(true);
        } else {
            radioAcceptEmpty.setSelected(true);
        }
        if(automata.getFinalStates().isEmpty()) {
            radioAcceptEmpty.setSelected(true);
            radionAcceptFInal.setEnabled(false);
        }        
        setVisible(true);                 
    }

    private void processInput() throws WrongValuesException {
        String state = (String) comboState.getSelectedItem();
        String word = txtWord.getText();
        String stack = txtStack.getText();
        if(word == null) {
            word = "";
        }
        if(stack == null) {
            stack = "";
        }
        int acceptType = -1;
        if(radioAcceptEmpty.isSelected()) {
            acceptType = PushdownAutomaton.EMPTY;
        } else {
            acceptType = PushdownAutomaton.FINAL;
        }
        simulator.startSimulation(state, word, stack, acceptType);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comboState = new javax.swing.JComboBox();
        txtWord = new javax.swing.JTextField();
        txtStack = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        radionAcceptFInal = new javax.swing.JRadioButton();
        radioAcceptEmpty = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        butLastAll = new javax.swing.JButton();
        butCurrentAll = new javax.swing.JButton();
        butStop16 = new javax.swing.JButton();
        butStop17 = new javax.swing.JButton();
        butStop18 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pdasimulator/localization"); // NOI18N
        setTitle(bundle.getString("input.title.input")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("main.panelTitle.inputConfiguration"))); // NOI18N

        jLabel1.setText(bundle.getString("main.lab.state")); // NOI18N

        jLabel2.setText(bundle.getString("main.lab.inputWord")); // NOI18N

        jLabel3.setText(bundle.getString("main.lab.stack")); // NOI18N

        comboState.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel3)
                    .add(jLabel2)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txtStack, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(comboState, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(txtWord, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(comboState, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(txtWord, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(7, 7, 7)
                        .add(jLabel3))
                    .add(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtStack, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("input.panelTitle.accepting"))); // NOI18N

        buttonGroup1.add(radionAcceptFInal);
        radionAcceptFInal.setSelected(true);
        radionAcceptFInal.setText(bundle.getString("freq.FinalStateADV")); // NOI18N
        radionAcceptFInal.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        buttonGroup1.add(radioAcceptEmpty);
        radioAcceptEmpty.setText(bundle.getString("freq.EmptyStackADV")); // NOI18N
        radioAcceptEmpty.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(radionAcceptFInal)
                    .add(radioAcceptEmpty))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(radionAcceptFInal)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(radioAcceptEmpty)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("input.panelTitle.preset"))); // NOI18N

        butLastAll.setText(bundle.getString("input.but.previous")); // NOI18N
        butLastAll.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butLastAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLastAllActionPerformed(evt);
            }
        });

        butCurrentAll.setText(bundle.getString("input.but.current")); // NOI18N
        butCurrentAll.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butCurrentAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCurrentAllActionPerformed(evt);
            }
        });

        butStop16.setText(bundle.getString("input.but.initial")); // NOI18N
        butStop16.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butStop16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStop16ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(butLastAll)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(butCurrentAll)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(butStop16)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(butLastAll)
                    .add(butCurrentAll)
                    .add(butStop16))
                .addContainerGap())
        );

        butStop17.setText(bundle.getString("input.but.setup")); // NOI18N
        butStop17.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butStop17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStop17ActionPerformed(evt);
            }
        });

        butStop18.setText(bundle.getString("freq.storno")); // NOI18N
        butStop18.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butStop18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStop18ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(butStop17)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butStop18)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(butStop17)
                    .add(butStop18))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butStop18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStop18ActionPerformed
        this.dispose();
    }//GEN-LAST:event_butStop18ActionPerformed

    private void butStop17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStop17ActionPerformed
        try {
            processInput();
            this.dispose();
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.input.wrong"), JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_butStop17ActionPerformed

    private void butStop16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStop16ActionPerformed
        comboState.setSelectedItem(automata.getStartState());
        txtWord.setText("");
        txtStack.setText(automata.getInitialStackSymbolFormat());
        if(DefaultValues.getInstance().getAccepting() == PushdownAutomaton.FINAL) {
            radionAcceptFInal.setSelected(true);
        } else {
            radioAcceptEmpty.setSelected(true);
        }
        if(automata.getFinalStates().isEmpty()) {
            radioAcceptEmpty.setSelected(true);
            radionAcceptFInal.setEnabled(false);
        }
    }//GEN-LAST:event_butStop16ActionPerformed
 
    private void butCurrentAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCurrentAllActionPerformed
        comboState.setSelectedItem(automata.getConfiguration().getCurrentState());
        txtWord.setText(automata.getConfiguration().getRestWord());
        txtStack.setText(automata.getConfiguration().getStack().writeStack());
            if(automata.getAcceptBy() == automata.EMPTY) {
                radioAcceptEmpty.setSelected(true);
            } else {
                radionAcceptFInal.setSelected(true);
            }            
    }//GEN-LAST:event_butCurrentAllActionPerformed

    private void butLastAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLastAllActionPerformed
        if(automata.isUsed()) {
            comboState.setSelectedItem(automata.getInitState());
            txtWord.setText(automata.getConfiguration().getFullWord());
            txtStack.setText(automata.getInitStack().writeStack());
            if(automata.getAcceptBy() == automata.EMPTY) {
                radioAcceptEmpty.setSelected(true);
            } else {
                radionAcceptFInal.setSelected(true);
            }                   
        }
    }//GEN-LAST:event_butLastAllActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCurrentAll;
    private javax.swing.JButton butLastAll;
    private javax.swing.JButton butStop16;
    private javax.swing.JButton butStop17;
    private javax.swing.JButton butStop18;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox comboState;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton radioAcceptEmpty;
    private javax.swing.JRadioButton radionAcceptFInal;
    private javax.swing.JTextField txtStack;
    private javax.swing.JTextField txtWord;
    // End of variables declaration//GEN-END:variables
    
}
