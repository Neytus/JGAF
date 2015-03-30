/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.sll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingWorker;
import jgaf.IA006.tools.FirstAndFollow;
import jgaf.IA006.tools.FirstAndFollowI;
import jgaf.IA006.grammar.LLGrammar;
import jgaf.IA006.tools.Tools;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class SLLChecker extends javax.swing.JDialog {

    private Map<LLSymbol,Set<List<LLSymbol>>> foSet;
    private Map<LLSymbol,Set<List<LLSymbol>>> fiSet;
    private FirstAndFollowI faf = new FirstAndFollow();
    private Map<LLSymbol,List<SLLRow>> map = new HashMap<>();
    private boolean hasConflict = false;
    private Set<List<LLSymbol>> prefixes = new HashSet<>();
    private LLGrammar g;
    private int k;
    private SLLTable table;
    
    
    
    /**
     * Metoda generuje tabulku, prechadza pravidla a generuje pomocnu triedu SLLRow. nasledne sa vlozi do mapy
     * ako Netemrinal -> \alpha, \beta etc.
     */
    private void build()
    {      
        for(LLSymbol s : g.getProductionRules().keySet())
        {
            List<SLLRow> roaws = new ArrayList<>();
            
            for(List<LLSymbol> rSide : g.getProductionRules().get(s))
            {
                Set<List<LLSymbol>> temp1 = faf.concatenateSetsWithPrefix(faf.firstAlpha(rSide, fiSet, k), foSet.get(s), k);
                prefixes.addAll(temp1);
                SLLRow sRow = new SLLRow();
                sRow.setFifo(temp1);
                sRow.setRule(rSide);
                sRow.setS(s);

                roaws.add(sRow);
            }
            
            map.put(s, new ArrayList<>(roaws));
            roaws.clear();
        }
    }
    
    /**
     * Methoda generuje vizualnu tabulku z vysledku metody buildTable().
     */
    private void buildTable2()
    {
        table = new SLLTable(g.getNonTerminals().size(), prefixes.size());
        StringBuilder sb = new StringBuilder("<table><tr style=\"border-bottom-style: dashed; border-bottom-width: 1px;\"><td>A</td><td>&#8594;</td><td>&alpha;, &beta;</td><td>FI<sub>");
        sb.append(k);
        sb.append("</sub>(&alpha;) &#8745 FO<sub>")
                .append(k).append("</sub>(A)</td></tr>");
        Set<List<LLSymbol>> fifos = new HashSet<>();
        Set<List<LLSymbol>> is = new HashSet<>();
        for(LLSymbol q : map.keySet())
        {
            
            for(SLLRow raw : map.get(q))
            {
                sb.append("<tr>");
                is.addAll(Tools.intersection2(fifos, raw.getFifo()));
                fifos.addAll(raw.getFifo());
                sb.append("<td>").append(raw.getS()).append("</td>");
                sb.append("<td>&#8594;</td>");
                sb.append("<td>").append(Tools.buildWord(raw.getRule())).append("</td>");
                sb.append("<td style=\"border-left-style: dotted; border-left-width: 1px\">").append(Tools.buildWordsInSets(raw.getFifo())).append("</td>");
                sb.append("</tr>");
                
                for(List<LLSymbol> w : raw.getFifo())
                {
                    SLLState state = new SLLState();
                    state.setRule(raw.getRule());
                    state.settHead(raw.getS());
                    table.addValue(state, raw.getS(), w);
                }
            }
            sb.append("<tr style=\"border-top-style: solid; border-top-width: 1px; border-bottom-style: solid; border-bottom-width: 1px;\"><td></td><td></td><td></td><td").append(is.isEmpty() ? ">" : " style=\"background-color: #E41B17\">"+Tools.buildWordsInSets(is)).append("</td></tr>");
            if(!is.isEmpty())
            {
                hasConflict = true;
            }
            is.clear();
            fifos.clear();
            
            jEditorPane1.setText(sb.toString());           
        }
    }
    
    
    public void setUp(LLGrammar g, int k,Map<LLSymbol,Set<List<LLSymbol>>> fiSet, Map<LLSymbol,Set<List<LLSymbol>>> foSet)
    {
        this.g = g;
        this.k = k;
        this.fiSet = fiSet;
        this.foSet = foSet;
        jButton1.setText("Create SLL("+k+") analyser");
        MyWorker mw = new MyWorker();
        mw.execute();
        
    }
    
    private class MyWorker extends SwingWorker<Void, Void>
    {
        @Override
        protected Void doInBackground() throws Exception 
        {
            build();
            buildTable2();
            
            table.sortColumns();
            table.nicePrint();
            if(!hasConflict)
            {
                jButton1.setEnabled(true);
            }
            return null;
        }    
    }
    
    

    /**
     * Creates new form SLLChecker
     */
    public SLLChecker(java.awt.Frame parent,
                      boolean modal) {
        super(parent, modal);
        initComponents();
        
        jEditorPane1.setEditable(false);
        jEditorPane2.setEditable(false);
        
        String s = "<div align=\"justify\">If there is a red cell in table on the left side, it means that conflict has occurred. "
                + "Such event means that condition set by Definition 21 is not fulfilled. "
                + "For unprocessed input word and two different rules PDA is unable to decide which rule to use on "
                + "Stack rewriting</div>";
        
        jEditorPane2.setText(s);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane2 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jEditorPane1.setContentType("text/html"); // NOI18N
        jScrollPane2.setViewportView(jEditorPane1);

        jEditorPane2.setContentType("text/html"); // NOI18N
        jScrollPane3.setViewportView(jEditorPane2);

        jButton1.setText("Create SLL PDA");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SLLAnalyser slla = new SLLAnalyser(null, true);
        slla.setup(table, g);
        slla.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SLLChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SLLChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SLLChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SLLChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SLLChecker dialog = new SLLChecker(new javax.swing.JFrame(), true);
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
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    

}
