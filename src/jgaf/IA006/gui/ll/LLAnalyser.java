/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.EmptyStackException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import jgaf.IA006.grammar.LLGrammar;
import jgaf.IA006.grammar.LLSymbol;
import jgaf.IA006.tools.FirstAndFollow;
import jgaf.IA006.tools.GrammarFactory;

/**
 *
 * @author Empt
 */
public class LLAnalyser extends javax.swing.JDialog 
{
    private List<LLSymbol> inputWord;
    private LLPDA pda;
    private StringBuilder sb = new StringBuilder();
    private LLGrammar g;
    private LLTable table;
    
    
    private class MyWorker extends SwingWorker<Void, Void>
    {
        @Override
        protected Void doInBackground() throws Exception 
        {
            jEditorPane1.setText(table.htmlPrint());
            return null;
        }        
    }
    
    /**
     * External setter that has to be called after class has been initialized.
     * @param table already computed ll table
     * @param g input grammar
     */
    public void setup(LLTable table, LLGrammar g)
    {
        this.g = g;
        pda = new LLPDA(table, g,sb,getSTM());
        this.table = table;
        
        MyWorker mw = new MyWorker();
        mw.execute();
    }
    /**
     * Creates new form LLAnalyser
     */
    public LLAnalyser(java.awt.Frame parent,
                      boolean modal) {
        super(parent, modal);
        initComponents();
        jButton1.setEnabled(false);
        jButton2.setEnabled(false);
        jEditorPane2.setContentType("text/html");
        jEditorPane1.setContentType("text/html");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jEditorPane2 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(978, 700));

        jTable1.setModel(new StackTableModel());
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jTextPane1);

        jEditorPane1.setEditable(false);
        jScrollPane4.setViewportView(jEditorPane1);

        jButton1.setText("Calculate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Calculate step");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Choose word");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jEditorPane2.setEditable(false);
        jScrollPane5.setViewportView(jEditorPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        try
        {
            pda.run();
        }
        catch(IllegalArgumentException iae)
        {
            JOptionPane.showMessageDialog(rootPane, iae.getMessage());
            jButton2.setEnabled(false);
            jButton1.setEnabled(false);
        }
        
       
       jEditorPane2.setText(sb.toString());
       
       if(pda.getInputWord().isEmpty())
       {
           jButton2.setEnabled(false);
           jButton1.setEnabled(false);
           JOptionPane.showMessageDialog(rootPane, "Your word has been successfully parsed. Choose another one if you want to continue");
       }
       else
       {
           JOptionPane.showMessageDialog(rootPane, "Because stack is empty, and word has not been processed, it means that word cannot be generated by given grammar.");
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try
        {
            pda.doStep();
        }
        catch(EmptyStackException ese)
        {
            if(pda.getInputWord().isEmpty())
            {
                JOptionPane.showMessageDialog(rootPane, "Your word has been successfully parsed. Choose another one if you want to continue");
                pda.doFakeStep();
                jButton1.setEnabled(false);
                jButton3.setEnabled(true);
                jButton2.setEnabled(false);
            }
            else
            {
                
            }
        }
        catch(IllegalArgumentException iae)
        {
            JOptionPane.showMessageDialog(rootPane, iae.getMessage());
            jButton2.setEnabled(false);
            jButton1.setEnabled(false);
        }
        jEditorPane2.setText("");
        jEditorPane2.setText(sb.toString());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String paneText = jTextPane1.getText();
        paneText = paneText.replaceAll("\\s", "");
        boolean error = false;
        try
        {
            inputWord = GrammarFactory.tokenize(g.getTerminals(), g.getNonTerminals(), paneText, true);
        }
        catch(IllegalArgumentException iae)
        {
            error = true;
            JOptionPane.showMessageDialog(rootPane, "Input contains illegal characters. Please choose another word.");
        }
        
        try
        {
            FirstAndFollow.checkForNonTerminal(inputWord);
        }
        catch(IllegalArgumentException iae)
        {
            error = true;
            JOptionPane.showMessageDialog(rootPane, iae.getMessage());
        }
        
        if(!error)
        {
            sb.delete(0, sb.length());
            pda = new LLPDA(table, g, sb, getSTM());
            jEditorPane2.setText("");
            pda.setWord(inputWord);
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);            
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Motif".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LLAnalyser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LLAnalyser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LLAnalyser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LLAnalyser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LLAnalyser dialog = new LLAnalyser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

    private StackTableModel getSTM()
    {
        return (StackTableModel) jTable1.getModel();
    }

}
