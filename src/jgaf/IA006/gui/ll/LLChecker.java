/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.SwingWorker;
import jgaf.IA006.tools.FirstAndFollow;
import jgaf.IA006.tools.FirstAndFollowI;
import jgaf.IA006.tools.Tools;
import jgaf.IA006.grammar.LLEpsilon;
import jgaf.IA006.grammar.LLGrammar;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class LLChecker extends javax.swing.JDialog {

    private LLGrammar g;
    private List<LLCheckerRow> rows = new ArrayList<>();
    private Map<LLSymbol,Set<List<LLSymbol>>> fiSet;
    private FirstAndFollowI faf = new FirstAndFollow();
    private Map<Integer,Map<LLSymbol,Set<List<LLSymbol>>>> tStates = new HashMap<>();
    private int k;
    private StringBuilder sb = new StringBuilder();
    private boolean hasConflict = false;
    int max = 0;
    int rowsCount = 0;
    
    private Set<List<LLSymbol>> columnPrefixes = new HashSet<>();
    
    private class MyWorker extends SwingWorker<String, String>
    {
        @Override
        protected String doInBackground() throws Exception 
        {
            LLSymbol eps = new LLEpsilon();
            Set<List<LLSymbol>> start = new HashSet<>();
            start.add(Arrays.asList(eps));
            sb.append("<table>");
            sb.append("<tr><td>A</td><td>&#8594;</td><td>&alpha;</td><td style=\"padding-right: 5px; border-right-style: dotted; border-right-width: 1px\">FI<sub>")
                    .append(k).append("</sub>(&alpha;) +<sub>")
                    .append(k).append("</sub> L</td>");
            for(LLSymbol s : g.getNonTerminals())
            {
                sb.append("<td style=\"text-align: center\">").append(s).append("</td>");
            }
            sb.append("</tr>");
            createRow(g.getRootSymbol(), start);
            sb.append("</table>");

            jEditorPane1.setContentType("text/html");
            jEditorPane1.setText(sb.toString());
            
            
            if(!hasConflict)
            {
                jButton1.setEnabled(true);
            }
            
            return sb.toString();
        }

        
        private void createTableRow(LLSymbol s,Set<List<LLSymbol>> LF, LLCheckerRow llRow)
        {
            Set<List<LLSymbol>> intersectionz = new HashSet<>();
            Set<List<LLSymbol>> is = new HashSet<>();
            intersectionz.addAll(llRow.getSubRows().get(0).getFiLSet());
            sb.append("<tr style=\"border-bottom-style: solid; border-bottom-width: 1px\"><td colspan=\"3\">T<sub>")
                    .append(tStates.size()-1)
                    .append("</sub>(")
                    .append(s)
                    .append(",")
                    .append(Tools.buildWordsInSets(LF))
                    .append(")</td>")                    
                    .append("<td colspan=\"")
                    .append(g.getNonTerminals().size()+1)
                    .append("\"></td></tr>");
            int q = 0;
            for(SubLLCheckerRow srow : llRow.getSubRows())
            {
                if(q!=0)
                {
                    is.addAll(Tools.intersection2(intersectionz,srow.getFiLSet()));
                    intersectionz.addAll(srow.getFiLSet());
                    if(!is.isEmpty())
                    {
                        hasConflict = true;
                    }
                }
                sb.append("<tr style=\"border-bottom-style: dashed; border-bottom-width: 1px\"><td>");
                sb.append(srow.getA());
                sb.append("</td><td>&#8594;</td><td>")
                        .append(Tools.buildWord(srow.getAlpha()))
                        .append("</td><td>")
                        .append(Tools.buildWordsInSets(srow.getFiLSet()))
                        .append("</td>");
                columnPrefixes.addAll(srow.getFiLSet());
                List<LLSymbol> nonTerminalz = new ArrayList<>(g.getNonTerminals());
                for(int i = 0; i < g.getNonTerminals().size();i++)
                {
                    if(i == 0)
                    {
                        sb.append("<td style=\"border-left-style: dotted; border-left-width: 1px; border-right-style: solid; border-right-width: 1px;\">");
                    }
                    else
                    {
                        sb.append("<td style=\"border-right-style: solid; border-right-width: 1px;\">");
                    }
                    
                    boolean hasMore = false;
                    for(Integer ii : srow.getFollows().keySet())
                    {
                        Map<LLSymbol,Set<List<LLSymbol>>> temp = srow.getFollows().get(ii);
                        
                        if(temp.containsKey(nonTerminalz.get(i)))
                        { 
                            if(hasMore)
                            {
                                sb.append(",");
                            }
                            sb.append(Tools.buildWordsInSets(temp.get(nonTerminalz.get(i))));
                            
                            hasMore = true;
                        }
                    }
                    sb.append("</td>"); 
               }
               q++;
            }
            sb.append("</tr>");
            
            sb.append("<tr><td colspan=\"3\"></td>");
                    
            if(!is.isEmpty())
            {
                sb.append("<td style=\"background-color: #E41B17\">");
                sb.append(Tools.buildWordsInSets(is));
                sb.append("</td>");
            }
            else
            {
                sb.append("<td></td>");
            }
            sb.append("<td colspan=\"").append(g.getNonTerminals().size()).append("\"></td></tr>");
        }
    
        private void createRow(LLSymbol s, Set<List<LLSymbol>> LF)
        {   // prve volanie
            if(tStates.isEmpty())
            {
                Map<LLSymbol,Set<List<LLSymbol>>> temp = new HashMap<>();
                temp.put(s, LF);
                tStates.put(tStates.size(), temp);
            }
            else
            {   // prebehnem cez uz napocitane stavy
                // ak uz existuje zastavim metodu returnom ak nie tak pridam a rekurzivne na konci vytvorenia zavolam
                // v podstate je to DFS (prehladavanie do hlbky)
                for(Integer i : tStates.keySet())
                {
                    Map<LLSymbol,Set<List<LLSymbol>>> temp = tStates.get(i);
                    if(temp.containsKey(s))
                    {
                        if(temp.get(s).containsAll(LF) && temp.get(s).size() == LF.size())
                        {
                            return;
                        }
                    }
                }

                // ak prebehol for cyklus vyssie tak tento stav tam este nie je inac by metoda skoncila returnom
                Map<LLSymbol,Set<List<LLSymbol>>> temp = new HashMap<>();
                temp.put(s, LF);
                tStates.put(tStates.size(), temp);            
            }

            LLCheckerRow llRow = new LLCheckerRow();
            llRow.setKey(s);
            llRow.setL(LF);
            List<SubLLCheckerRow> subRows = new ArrayList<>();
            for(List<LLSymbol> rule : g.getProductionRules().get(s))
            {
                SubLLCheckerRow subllrow = new SubLLCheckerRow();
                subllrow.setA(s);
                subllrow.setAlpha(rule);
                
                Set<List<LLSymbol>> fiL = new HashSet<>();
                fiL.addAll(faf.concatenateSetsWithPrefix(faf.firstAlpha(rule, fiSet, k), LF, k));

                
                subllrow.setFiLSet(fiL);
                subllrow.calc(fiSet, faf, k, LF);    
                subRows.add(subllrow);
            }

            llRow.setSubRows(subRows);

            createTableRow(s, LF, llRow);

            for(SubLLCheckerRow srow : llRow.getSubRows())
            {
                for(Integer i : srow.getFollows().keySet())
                {
                    Map<LLSymbol,Set<List<LLSymbol>>> temp = srow.getFollows().get(i);
                    for(LLSymbol skey : temp.keySet())
                    {
                        createRow(skey,temp.get(skey));
                    }
                }
            }
            
            rows.add(llRow);
        }
    }   
    
    /**
     * External setter
     * @param g
     * @param fiSet
     * @param k 
     */
    public void setup(LLGrammar g, Map<LLSymbol,Set<List<LLSymbol>>> fiSet,int k)
    {
        this.g = g;
        this.fiSet = fiSet;
        this.k = k;
        MyWorker worker = new MyWorker();
        worker.execute();
    }
    
    /**
     * Creates new form LLChecker
     */
    public LLChecker(java.awt.Frame parent,
                     boolean modal) {
        super(parent, modal);
        initComponents();
        
        String s = "If there is a red cell in table on the left side, it means that FIRST-FIRST conflict has occurred. "
                + "Such event means that condition set by Definition 20 is not fulfilled. "
                + "For unprocessed input word and two different rules PDA is unable to decide which rule to use on "
                + "Stack rewriting";        

        jEditorPane2.setEditable(false);
        jEditorPane2.setContentType("text/html");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane2 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(978, 700));

        jEditorPane1.setEditable(false);
        jEditorPane1.setText("Calculation in progres...");
        jScrollPane1.setViewportView(jEditorPane1);

        jEditorPane2.setEditable(false);
        jScrollPane2.setViewportView(jEditorPane2);

        jButton1.setText("Create PDA");
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LLTable table = new LLTable(rows.size(), columnPrefixes.size());
        
        int stateID = 0;
        for(Integer i : tStates.keySet())
        {
            
            {
            for(LLSymbol symb : tempMap.keySet())
            {
                TSymbol ts = new TSymbol(stateID, symb, tempMap.get(symb));
                table.addToRows(ts);
                stateID++;
            }
        }
        
        // prebieham hlavny riadok
        for(LLCheckerRow row : rows)
        {   // prebehnem podriadky            
            for(SubLLCheckerRow subRow : row.getSubRows())
            {   // prejdem mozne prefixy
                for(List<LLSymbol> prefix : subRow.getFiLSet())
                {   // vytvorime nove pravidlo kde sa budu ukladat T symboly                   
                    List<LLSymbol> newRule = new ArrayList<>();
                    int position = 0;
                    for(LLSymbol s: subRow.getAlpha())
                    {   // pre dane prefixy ktore pravidlo
                        if(s.isEpsilon() || s.isTerminal())
                        {   // terminal ide priamo ~> terminal
                            newRule.add(s);
                        }
                        else
                        {   // neterminal treba nahradit
                            boolean added = false;
                            for(Integer ii : subRow.getFollows().keySet())
                            {   //este sme ziaden nepridali
                                if(!added)
                                {
                                    Map<LLSymbol,Set<List<LLSymbol>>> temp = subRow.getFollows().get(ii);
                                    // prejdeme cez mozne lokalne follow                           
                                    for(LLSymbol ss: temp.keySet())
                                    {
                                        if(ss.equals(s))
                                        {
                                            Set<List<LLSymbol>> set1 = new HashSet<>();
                                            List<LLSymbol> tempp = subRow.getAlpha().subList(position+1, subRow.getAlpha().size());                                              
                                            if(tempp.isEmpty())
                                            {   // neterminal na konci s epsilonom
                                                set1.addAll(row.getL());
                                            }
                                            else
                                            {
                                                set1.addAll(faf.firstAlpha(subRow.getAlpha().subList(position+1, subRow.getAlpha().size()), fiSet, k));                                                
                                            }
                                            
                                            if(set1.equals(temp.get(ss)))
                                            {
                                                added = true;
                                                // uz priamo nahrada
                                                newRule.add(table.getSymbolFor(s, temp.get(ss)));                                                
                                            }                                            
                                        }
                                    }                                    
                                }                                
                            }                       
                        }
                        position++;
                    }
                    
                    TState ts = new TState(table.getSymbolFor(subRow.getA(), row.getL()), newRule);
                    ts.setOriginalRule(subRow.getAlpha());
                    ts.setLeftSide(subRow.getA());
                    // vytvorime stav a vlozime do tabulky s tym ze do stavu ulozime povodne pravidlo
                    table.addValue(ts, table.getSymbolFor(subRow.getA(), row.getL()), prefix);
                }
            }
        }
        
        // zoradime stlpce
        table.sortColumns();
        
        LLAnalyser lla = new LLAnalyser(null, true);
        
        lla.setup(table, g);
        
        lla.setVisible(true);
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
            java.util.logging.Logger.getLogger(LLChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LLChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LLChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LLChecker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LLChecker dialog = new LLChecker(new javax.swing.JFrame(), true);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
