/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import jgaf.lrextension.guitools.AutoTableRenderer;
import jgaf.lrextension.guitools.AutoTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import jgaf.lrextension.FiFoUtils;
import jgaf.lrextension.guitools.CgramTableModel;
import jgaf.lrextension.guitools.CustomProcedureToolbar;
import jgaf.lrextension.guitools.SteppedProcedure;

import jgaf.grammar.Grammar;
import jgaf.lrextension.procedures.ItemAuto;
import jgaf.lrextension.procedurefaces.ItemAutoRep;
import jgaf.procedure.DefaultProcedureLogger;


/**
 *
 * @author g
 */
public class ItemAutoPanel extends JPanel implements SteppedProcedure{
    private CustomProcedureToolbar toolbar;

    //private LRParserProcedure proc;

    private int step = 0;
    private Grammar gram;
    private List<String> logSequence;
    private List<ItemAutoRep> sequence;
    
    
   
    private ItemAuto itemAuto;
    //private 
    private JTable autoTable;
    private DefaultProcedureLogger logger;
    
    public ItemAutoPanel(ItemAuto itemAuto) {
    super(new BorderLayout());
        this.itemAuto=itemAuto;
    
    this.gram=itemAuto.getGrammar();
    
    
    
    
    
     drawComponents();
     //first();
    }

 

    

   

   



    private void drawComponents() {
        logger = new DefaultProcedureLogger();                
        JScrollPane scroller = new JScrollPane(logger);
        
        
        
        
        
       
         
         sequence=itemAuto.getSequence();
         logSequence=itemAuto.getLogSequence();
         
         TableModel pdaModel;
         pdaModel=new AutoTableModel(sequence.get(step));
         
         autoTable = new JTable(pdaModel);
         autoTable.setDefaultRenderer(Object.class,new AutoTableRenderer());
         JTableHeader header = autoTable.getTableHeader();
         
         header.setBackground(Color.yellow);
         header.setReorderingAllowed(false);
         JScrollPane tablePane = new JScrollPane(autoTable);
  
        
         JTable gTable = new JTable(new CgramTableModel(gram));
         
         gTable.setFont(new Font("Serif", Font.PLAIN, 15));
         gTable.setRowHeight(20);
         JScrollPane gTablePane = new JScrollPane(gTable);
         
         JSplitPane SubPane= new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                   gTablePane, scroller);
         
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   SubPane,tablePane);
        mainSplitPane.setResizeWeight(0.4);
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setContinuousLayout(true);
        add(mainSplitPane, BorderLayout.CENTER);
        //procedureLogger.setLog(composetOldLogText(step),logSequence.get(step));
    
    
   
    
         
         
         
         
      
       
  
      
     
      
     
        
  
  
         
         
         toolbar = new CustomProcedureToolbar(sequence.size(),this);
    
     add(toolbar, BorderLayout.NORTH);
         
         
         
//    newpaneltest();
    }

    
    
    
   
   
    
    
    

//    private void newpaneltest(){
//    JPanel testPanel=new JPanel(new BorderLayout());
//    CustomProcedureToolbar toolbar2 = new CustomProcedureToolbar(this);
//     toolbar2.showOpenButton(true);
//     toolbar2.addSeparator();
//            
//
//
//        
//     
//     testPanel.add(toolbar2, BorderLayout.NORTH);
//    
//     ParserTablesCalc bla=new ParserTablesCalc(pda.getSequence().get(pda.getSequence().size()-1), gram);
//    bla.calc();
//    ActionTableCalc bla2=new ActionTableCalc(pda.getSequence().get(pda.getSequence().size()-1), gram, k);
//    bla2.calc();
//    
//    GotoTableModel gtm = new GotoTableModel(bla);
//         JTable gotable = new JTable(gtm);
//         JTableHeader goheader = gotable.getTableHeader();
//         gotable.setShowGrid(true);
//    JScrollPane gopane = new JScrollPane(gotable);
//    final ActionTableModel atm2 = new ActionTableModel(bla2);
//    final ActionTableModel atm = new ActionTableModel(bla2);
//         final MinSizedTable table2 = new MinSizedTable(atm);
//         
//         JTableHeader header3 = table2.getTableHeader();
//         
//         
//         table2.setShowGrid(true);
//         table2.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
//         
//  
//      JScrollPane pane2 = new JScrollPane(table2);
//    
//         
//         JSplitPane tabSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
//                                   gopane,pane2);
//    
//    testPanel.add(tabSplitPane);
//    JFrame testframe =new JFrame();    
//    testframe.add(testPanel);
//    
//    
//    JButton sortDescendingButton = new JButton();
//        sortDescendingButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/grammar/sortDescending.png")));
//        sortDescendingButton.setToolTipText("sort descending");
//        sortDescendingButton.setFocusable(false);
//        sortDescendingButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        sortDescendingButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
//        sortDescendingButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//               if (table2.getModel()== atm){
//               table2.setModel(atm2);
//               }else table2.setModel(atm);
//               table2.
//            }
//
//           
//        });
//        toolbar2.add(sortDescendingButton);
//    
//    
//    
//    
//    
//    testframe.setVisible(true);
//    
//    
//    
//    }
//
    

    public void toStep(int step) {
        autoTable.setModel(new AutoTableModel(sequence.get(step)));    
    logger.setLog(composetOldLogText(step),logSequence.get(step));
        autoTable.repaint();
    }

    private String composetOldLogText(int step) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < step; i++) {
            sb.append(logSequence.get(i)).append("\n");
        }
        return sb.toString();
    }

}
