/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui;

import java.text.MessageFormat;
import jgaf.IA006.gui.sll.SLLChecker;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import jgaf.IA006.tools.FirstAndFollow;
import jgaf.IA006.tools.FirstAndFollowI;
import jgaf.IA006.grammar.Grammar;
import jgaf.IA006.grammar.Symbol;
import jgaf.IA006.gui.ll.LLChecker;
import jgaf.IA006.tools.GrammarLoader;

/**
 *
 * @author Empt
 */
public class GrammarLoaderFrame extends javax.swing.JPanel {

    private Grammar g;
    private int k;
    private java.util.Map<Symbol, java.util.Set<List<Symbol>>> fiSet;
    private java.util.Map<Symbol, java.util.Set<List<Symbol>>> foSet;
    private GrammarLoader gl;
    private String llCheck = "Check grammar for LL({0})";
    private String sllCheck = "Check grammar for SLL({0})";
    
    private class MyFIFWorker extends SwingWorker<Void, Void>
    {
        @Override
        protected Void doInBackground() throws Exception 
        {
            boolean error = false;
            
            try
            {
                g = gl.processFile();
            }
            catch(IllegalArgumentException iae)
            {
                error= true;
                JOptionPane.showMessageDialog(null, iae.getMessage());
            }
            
            if(!error)
            {
                FirstAndFollowI faf = new FirstAndFollow();

                FirstTableModel ftm = (FirstTableModel) jTable1.getModel();
                FollowTableModel fotm = (FollowTableModel) jTable2.getModel();
                try
                {
                    fiSet = faf.first(g, k);
                }
                catch(IllegalArgumentException iae)
                {
                    JOptionPane.showMessageDialog(null, iae.getMessage());
                    error=true;
                }
                
                if(!error)
                {
                    foSet = faf.follow(g, k);
                    ftm.setSets(fiSet);
                    fotm.setSets(foSet);

                    jButton1.setEnabled(true);
                    jButton3.setEnabled(true);
                    jButton5.setEnabled(true); 
                }                               
            }
            jTextArea1.setText(g.toString());

            
            
            return null;
        }
        
    }

    /**
     * Creates new form GrammarLoaderFrame
     */
    public GrammarLoaderFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jButton1.setText("Change k");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Load grammar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton3.setText("Check grammar for LL(k)");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Check grammar for SLL(k)");
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTable1.setModel(new FirstTableModel());
        jScrollPane3.setViewportView(jTable1);

        jTable2.setModel(new FollowTableModel());
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /*
     * public static void createErrorDialog(String errorMessage)
     {
     JOptionPane.showMessageDialog(null, errorMessage);
     }
     */
    @SuppressWarnings("unchecked")
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(this);
        if(jfc.getSelectedFile() != null)
        {
            k = changeK();
            setButtonLabels();
        
            gl = new GrammarLoader(jfc.getSelectedFile());

            MyFIFWorker worker = new MyFIFWorker();
            worker.execute();            
        }        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        k = changeK();
        setButtonLabels();
        MyFIFWorker worker = new MyFIFWorker();
        worker.execute();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

//        SLLPDA sllPDA = new SLLPDA(g, fiSet, foSet, k);
        SLLChecker sllchecker = new SLLChecker(null, true);
        sllchecker.setUp(g, k, fiSet, foSet);
//        sllPDA.setup();
//        sllchecker.addRows(sllPDA.getCheckrowz());
        
        sllchecker.setVisible(true);
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        LLChecker llCHecker = new LLChecker(null, true);
        llCHecker.setup(g, fiSet, k);
        llCHecker.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    private int changeK() 
    {
        return Integer.parseInt(JOptionPane.showInputDialog("Enter paramter k, that determines size of first, follow sets and look-ahead table"));
    }
    
    private void setButtonLabels()
    {
        jButton3.setText(MessageFormat.format(llCheck, k));
        jButton5.setText(MessageFormat.format(sllCheck, k));
    }
}
