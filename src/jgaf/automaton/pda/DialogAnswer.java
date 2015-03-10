/*
 * DialogAnswer.java
 *
 * Created on 14. únor 2008, 16:11
 */

package jgaf.automaton.pda;

/**
 *
 * @author  hanis
 */
public class DialogAnswer extends javax.swing.JDialog {
    
    
    /*
     *
     *automat (ne)akeptuje

vstupni konfigurace:
  (q,aaaaaaaaaa,Z)

koncova konfigurace:
  (f,e,F)

pocet kroku:

     */
    
    /** Creates new form DialogAnswer */
    public DialogAnswer(java.awt.Frame parent, boolean accept, PushdownAutomaton automata) {
        super(parent, true);
     //   ((PDASimulator) parent).hideProgressDialog();
        initComponents();
        this.setLocationRelativeTo(parent);
        
        
        
        ////////    L12N.getValue("")
        
        tabbed.setTitleAt(0, L12N.getValue("results.tab.answer"));
        tabbed.setTitleAt(1, L12N.getValue("results.tab.simulation"));
        tabbed.setTitleAt(2, L12N.getValue("results.tab.automata"));              
        
        areaAutomata.setText(automata.writeAutomataDetail());                
        labWord.setText(automata.getConfiguration().getFullWord());
        areaReport.setText(automata.getReport().createProcess()); 
        
        
        if(automata.getAcceptBy() == PushdownAutomaton.EMPTY) {
            labAccept.setText(L12N.getValue("freq.EmptyStackADV"));
        } else {
            labAccept.setText(L12N.getValue("freq.FinalStateADV"));
        }
        StringBuilder answer = new StringBuilder();
        if(accept) {
            answer.append(L12N.getValue("results.answer.ok"));
            labAnswer.setText(L12N.getValue("results.lab.ok"));                       
        } else {
            answer.append(L12N.getValue("results.answer.nok"));
            labAnswer.setText(L12N.getValue("results.lab.nok"));                        
        }

        answer.append("\n\n");     
        answer.append(L12N.getValue("results.answer.first"));
        answer.append(automata.getReport().getFirst()).append("\n\n");        
        answer.append(L12N.getValue("results.answer.last"));
        answer.append(automata.getReport().getLast()).append("\n\n");
        answer.append(L12N.getValue("results.answer.stepCount"));
        answer.append(automata.getReport().stepCount() + "");//.append("\n\n");
        
        areaAnswer.setText(answer.toString());
        
        
        //areaAnswer.setText(areaAnswer.getText() + "\n\n\n" + automata.getReport().getFirst() + "\n" + automata.getReport().getLast());
        
        
      //  DesignManipulation.getInstance().setDesign(this.getContentPane());
        setVisible(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        tabbed = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        areaAnswer = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaReport = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaAutomata = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        labAnswer = new javax.swing.JLabel();
        labWord = new javax.swing.JLabel();
        labAccept = new javax.swing.JLabel();
        butBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("results.title"));
        areaAnswer.setColumns(20);
        areaAnswer.setRows(5);
        areaAnswer.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane3.setViewportView(areaAnswer);

        tabbed.addTab("tab3", jScrollPane3);

        areaReport.setBackground(new java.awt.Color(0, 0, 0));
        areaReport.setColumns(20);
        areaReport.setForeground(new java.awt.Color(0, 255, 0));
        areaReport.setRows(5);
        areaReport.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane1.setViewportView(areaReport);

        tabbed.addTab("tab1", jScrollPane1);

        areaAutomata.setBackground(new java.awt.Color(0, 0, 0));
        areaAutomata.setColumns(20);
        areaAutomata.setForeground(new java.awt.Color(0, 255, 0));
        areaAutomata.setRows(5);
        areaAutomata.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane2.setViewportView(areaAutomata);

        tabbed.addTab("tab2", jScrollPane2);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel1.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("results.lab.result"));

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel2.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("results.lab.input"));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel3.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("results.lab.accepting"));

        labAnswer.setText("oo");

        labWord.setFont(new java.awt.Font("Dialog", 0, 12));
        labWord.setText("oo");

        labAccept.setFont(new java.awt.Font("Dialog", 0, 12));
        labAccept.setText("oo");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(labAnswer)
                    .add(labWord)
                    .add(labAccept))
                .addContainerGap(301, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(labAnswer))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(labWord))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(labAccept))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        butBack.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("freq.ok"));
        butBack.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBackActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, tabbed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(butBack))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabbed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(butBack)
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBackActionPerformed
        dispose();
    }//GEN-LAST:event_butBackActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaAnswer;
    private javax.swing.JTextArea areaAutomata;
    private javax.swing.JTextArea areaReport;
    private javax.swing.JButton butBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labAccept;
    private javax.swing.JLabel labAnswer;
    private javax.swing.JLabel labWord;
    private javax.swing.JTabbedPane tabbed;
    // End of variables declaration//GEN-END:variables
    
}