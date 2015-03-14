/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import jgaf.lrextension.procedures.ParserTablesCalc;

/**
 *
 * @author g
 */
public class GUIPrep {
    
    public static JPanel prepareTablePanel(JTable table,String tableTitle){
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        JScrollPane pane =new JScrollPane( table);
        JPanel panel=new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                                                            tableTitle,
                                                            TitledBorder.LEFT,
                                                            TitledBorder.TOP));
        panel.add(pane);
        return panel;
    }
    
   
}
