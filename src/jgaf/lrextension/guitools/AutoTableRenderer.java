/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import jgaf.lrextension.guitools.AutoTableModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author g
 */
public class AutoTableRenderer extends DefaultTableCellRenderer{

     
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        AutoTableModel AtModel=  (AutoTableModel) table.getModel();
        setForeground(AtModel.getColorAt(row, column));
        table.getModel().getValueAt(row,column);
        
         
            if (value != null)
        
                if (column==0 && 
                        row>0 && 
                        table.getModel().getValueAt(row-1,column)==table.getModel().getValueAt(row,column)){
                setText("");
                }else{
                    setText(value.toString());
                    
                }
            if (AtModel.getColorAt(row, column)== Color.WHITE) setText("");
        if ((Integer) table.getModel().getValueAt(row,0) % 2==1)
        {cell.setBackground( new Color(235,235,235));}
        else{cell.setBackground(Color.WHITE);}
        
        return cell;
  }
       
    
    
}
