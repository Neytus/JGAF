/*
 * DialogModifyBasic.java
 *
 * Created on 8. březen 2008, 13:07
 */

package jgaf.automaton.pda;

import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author  hanis
 */
public class DialogModifyBasic extends javax.swing.JDialog {
    

    public static final int STATE = 0;
    public static final int ALPHABET = 1;
    public static final int STACK = 2;
    public static final int FINAL = 3;    
    
    private DialogModifyAutomata parent;
    private PushdownAutomaton automaton;
    
    private int mode;
    
    public DialogModifyBasic(java.awt.Dialog parent, PushdownAutomaton automaton, int mode) {
        super(parent, true);
        this.parent = (DialogModifyAutomata) parent;
        this.automaton = automaton;
        initComponents();
        setMode(mode);
        setLocationRelativeTo(parent);
      //  DesignManipulation.getInstance().setDesign(this.getContentPane());
        try {
            showModify(false);
        } catch (WrongValuesException ex) {
            System.out.println("ERR22");
        }
        this.setVisible(true);
    }                    
    
    private void hideGlobal() {
        checkGlobal.setVisible(false);
        butHelp.setVisible(false);
    }
    
    private void showModify(boolean enable) throws WrongValuesException {
        if(enable) {
            txtSymbol.setText(getSelectedItem());
        } else {
            txtSymbol.setText("");
        }        
        txtSymbol.setVisible(enable);
        butOk.setVisible(enable);        
        butBack.setEnabled(!enable);
        butModify.setEnabled(!enable);
        butRemove.setEnabled(!enable);
        comboSymbols.setEnabled(!enable);        
    }
    

    public int getMode() {
        return mode;
    }
    
    private String getSelectedItem() throws WrongValuesException {
        String item = null;
        if(mode == ALPHABET) {
            item = String.valueOf((Character) comboSymbols.getSelectedItem());
        } else {
            item = (String) comboSymbols.getSelectedItem();
        }
        if(item == null) {
            throw new WrongValuesException(L12N.getValue("warn.modify.noSymbolSelected"));
        }
        return item;
    }    
    
    private void refreshCombo() {        
        switch (mode) {
            case STATE   : comboSymbols.setModel(new DefaultComboBoxModel(automaton.getStates().toArray())); break;
            case STACK   : comboSymbols.setModel(new DefaultComboBoxModel(automaton.getStackAlphabet().toArray())); break;
            case FINAL   : comboSymbols.setModel(new DefaultComboBoxModel(automaton.getFinalStates().toArray())); break;
            case ALPHABET: comboSymbols.setModel(new DefaultComboBoxModel(automaton.getInputAlphabet().toArray())); break;
        }       
    }
    
    public void setMode(int mode) {
        this.mode = mode;
        switch(getMode()) {
            case STATE    : setTitle(L12N.getValue("freq.modify") + " " + DefaultValues.getInstance().getStatesSet()); break;
            case STACK    : setTitle(L12N.getValue("freq.modify") + " " + DefaultValues.getInstance().getStackAlphabet()); break;
            case FINAL    : setTitle(L12N.getValue("freq.modify") + " " + DefaultValues.getInstance().getFinalStates()); break;
            case ALPHABET : setTitle(L12N.getValue("freq.modify") + " " + DefaultValues.getInstance().getInputAlphabet()); break;
        }        
        if(mode == FINAL) {
            hideGlobal();
        }
        refreshCombo();        
    }
    
    private void removeSymbol() throws WrongValuesException {
        switch(getMode()) {
            case STATE    : automaton.removeState((String) getSelectedItem(), checkGlobal.isSelected()); break;
            case STACK    : automaton.removeStackSymbol((String) getSelectedItem(), checkGlobal.isSelected()); break;
            case FINAL    : automaton.removeFinalState((String) getSelectedItem()); break;
            case ALPHABET : automaton.removeSymbol(getSelectedItem().charAt(0), checkGlobal.isSelected()); break;
        }
        refreshCombo();
    }
    
    
    private void modifySymbol() throws WrongValuesException {
        String symbol = txtSymbol.getText();
        if(symbol == null || symbol.equals("")) {
            String message = "";
            switch(getMode()) {
                case STATE    : message = L12N.getValue("warn.modify.miss.state"); break;
                case STACK    : message = L12N.getValue("warn.modify.miss.stack"); break;
                case FINAL    : message = L12N.getValue("warn.modify.miss.finalState"); break;
                case ALPHABET : message = L12N.getValue("warn.modify.miss.input"); break;
            }
            throw new WrongValuesException(message);
        }
        if(mode == ALPHABET && symbol.length() > 1) {
            throw new WrongValuesException(L12N.getValue("warn.modify.miss.input"));
        }

        switch(getMode()) {
            case STATE    : {
                if(checkGlobal.isSelected() && automaton.getStartState().equals(getSelectedItem())) {
                    parent.setTxtStartState(symbol);
                }                
                automaton.ModifyState(getSelectedItem(), symbol, checkGlobal.isSelected());
            } break;
            case STACK    : {                
                if(checkGlobal.isSelected() && automaton.getInitialStackSymbol().equals(getSelectedItem())) {
                    parent.setTxtInitialStack(symbol);
                }
                automaton.ModifyStackSymbol(getSelectedItem(), symbol, checkGlobal.isSelected());
            } break;
            case FINAL    : automaton.ModifyFinalState(getSelectedItem(), symbol); break;
            case ALPHABET : automaton.ModifySymbol(getSelectedItem().charAt(0), symbol.charAt(0), checkGlobal.isSelected()); break;
        }
        refreshCombo();
        showModify(false);
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        comboSymbols = new javax.swing.JComboBox();
        butOk = new javax.swing.JButton();
        butRemove = new javax.swing.JButton();
        butModify = new javax.swing.JButton();
        txtSymbol = new javax.swing.JTextField();
        checkGlobal = new javax.swing.JCheckBox();
        butBack = new javax.swing.JButton();
        butHelp = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        comboSymbols.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSymbolsActionPerformed(evt);
            }
        });

        butOk.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("freq.ok"));
        butOk.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butOkActionPerformed(evt);
            }
        });

        butRemove.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.remove"));
        butRemove.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRemoveActionPerformed(evt);
            }
        });

        butModify.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.modify"));
        butModify.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(comboSymbols, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butModify)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butRemove))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(txtSymbol, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butOk)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(comboSymbols, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(butModify)
                    .add(butRemove))
                .add(18, 18, 18)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtSymbol, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(butOk))
                .add(163, 163, 163))
        );

        checkGlobal.setSelected(true);
        checkGlobal.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.check.global"));
        checkGlobal.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        checkGlobal.setMargin(new java.awt.Insets(0, 0, 0, 0));

        butBack.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("freq.ok"));
        butBack.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBackActionPerformed(evt);
            }
        });

        butHelp.setText("?");
        butHelp.setMargin(new java.awt.Insets(0, 0, 0, 0));
        butHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butHelpActionPerformed(evt);
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
                        .add(checkGlobal)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butHelp))
                    .add(butBack))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(checkGlobal)
                    .add(butHelp))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 98, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(butBack)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butHelpActionPerformed
        new DialogHelp(this, L12N.getValue("warn.help.globalModifies"));        
    }//GEN-LAST:event_butHelpActionPerformed

    private void butBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBackActionPerformed
        this.dispose();
        parent.refreshDetail();
    }//GEN-LAST:event_butBackActionPerformed

    private void butModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyActionPerformed
        try {
            showModify(true);
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_butModifyActionPerformed

    private void butRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRemoveActionPerformed
        try {
            removeSymbol();
        } catch(WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_butRemoveActionPerformed

    private void butOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOkActionPerformed
        try {
            modifySymbol();
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_butOkActionPerformed

    private void comboSymbolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSymbolsActionPerformed

    }//GEN-LAST:event_comboSymbolsActionPerformed
    

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBack;
    private javax.swing.JButton butHelp;
    private javax.swing.JButton butModify;
    private javax.swing.JButton butOk;
    private javax.swing.JButton butRemove;
    private javax.swing.JCheckBox checkGlobal;
    private javax.swing.JComboBox comboSymbols;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtSymbol;
    // End of variables declaration//GEN-END:variables
    
}
