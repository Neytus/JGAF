/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedurefaces;

import jgaf.lrextension.guitools.GotoTableModel;
import jgaf.lrextension.guitools.AutoTableRenderer;
import jgaf.lrextension.guitools.AutoTableModel;
import jgaf.lrextension.guitools.ActionTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.JTableHeader;
import jgaf.Constants.MathConstants;
import jgaf.lrextension.guitools.CustomProcedureToolbar;

import jgaf.grammar.Grammar;
import jgaf.lrextension.guitools.ActionTable;
import jgaf.lrextension.guitools.DynamicallyColorableTable;
import jgaf.lrextension.guitools.PeachTableRenderer;
import jgaf.lrextension.guitools.GUIPrep;
import jgaf.lrextension.guitools.GotoTable;
import jgaf.lrextension.guitools.MinSizedTable;
import jgaf.lrextension.guitools.SteppedProcedure;
import jgaf.lrextension.procedures.ParserTablesCalc;
import jgaf.procedure.DefaultProcedureLogger;

/**
 *
 * @author g
 */
public class TablePanel extends JPanel {
   
       
    private ParserTablesCalc ptc;
    private JTable autoTable;
    private GotoTable gotoTable;
    private ActionTable actionTable;
    
    
    public TablePanel(ParserTablesCalc ptc  ) {
        super(new BorderLayout());
        this.ptc = ptc;
        
        
         
        draw();
    }

    private void draw() {
       JToolBar infoToolBar=new JToolBar();
       infoToolBar.setRollover(true);
       infoToolBar.setFloatable(false);
       infoToolBar.setPreferredSize(new Dimension(28, 28));
       add(infoToolBar, BorderLayout.NORTH);
    
       JButton infoButton = new JButton();
        infoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/info.png")));
        infoButton.setToolTipText("Table creation info");
        infoButton.setFocusable(false);
        
        infoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showInfoD();
            }
        });
        infoToolBar.add(infoButton);
        
        
    autoTable = new JTable(new AutoTableModel(ptc.getIaRep()));
         autoTable.setDefaultRenderer(Object.class,new AutoTableRenderer());
         JTableHeader header = autoTable.getTableHeader();
         
         header.setBackground(Color.yellow);
         header.setReorderingAllowed(false);
         JScrollPane itemAutoPane = new JScrollPane(autoTable);
    
         
         
         
         
         
        
        JSplitPane rSplitPane = prepareGotoActionSplitPane();
        
         JSplitPane centralSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   itemAutoPane,rSplitPane);
         centralSplitPane.setResizeWeight(0.4);
         add(centralSplitPane,BorderLayout.CENTER);
      
    
    
    
    
         
          
        
    
    }

private  JSplitPane prepareGotoActionSplitPane(){
    GotoTableModel gtm = new GotoTableModel(ptc);
         gotoTable = new GotoTable(gtm);
        gotoTable.setShowGrid(true);
        
         ActionTableModel atm = new ActionTableModel(ptc);
        actionTable=new ActionTable(atm);
        actionTable.setShowGrid(true);
        
        
        actionTable.setDefaultRenderer(Object.class,new PeachTableRenderer());
        gotoTable.setDefaultRenderer(Object.class,new PeachTableRenderer());
        JPanel goPanel =GUIPrep.prepareTablePanel(gotoTable, "Goto Table");
        JPanel actionPanel =GUIPrep.prepareTablePanel(actionTable, "Action Table");
        
        
        
    
         
         JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                   goPanel,actionPanel);
    splitPane.setResizeWeight(0.5);
    
    return splitPane;
    
    }

    public void showInfoD(){
    
        JOptionPane.showMessageDialog(null, "For each state in item automata a row in both tables is created . \n"
            + "Creation of Goto table consists simply of adding value \"To State\" to column with corresponding \"Next Symbol\".\n"
            + "\n" + "Action table:\n"
            + "For all incompete items A -> "+MathConstants.ALPHA+"."+MathConstants.BETA+"  is calculated: FI("+MathConstants.BETA+") "+MathConstants.O_PLUS+" local follow\n"
            + "SHIFT is then added to all collumns marked with one of the string from the result.\n"
            + "For complete items reduce action is added to collumns marked by one of the strings from their local follow",
            "Goto/Action tables calculation info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public boolean hasConflict(){
        return ptc.hasConflict();
    }
    
    public void showConflictD(){
    
        JOptionPane.showMessageDialog(null, "There is a state with conflicting items.\nConflict is Marked red in action table.",
            "Conflict", JOptionPane.WARNING_MESSAGE);
    }
}
