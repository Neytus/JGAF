/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.awt.Color;
import java.awt.Component;
import java.util.Collection;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import jgaf.lrextension.FiFoUtils;
import jgaf.lrextension.StringOutputUtils;
import jgaf.lrextension.procedures.SameActionItems;

/**
 *
 * @author g
 */
public class PeachTableRenderer extends DefaultTableCellRenderer {
    
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
       
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setForeground(Color.BLACK);
        c.setBackground(Color.WHITE);
        if (value instanceof Collection<?>) {
            setText(StringOutputUtils.setToString((Collection<?>) value));
        }
        if (value instanceof SameActionItems && ((SameActionItems) value).isConflict()){
            c.setBackground(Color.red);
            
        }
        if (column == 0) {
            c.setBackground(new Color(251, 229, 194));
        }

        if (table instanceof DynamicallyColorableTable){
        DynamicallyColorableTable dct=(DynamicallyColorableTable) table;
            
            if (dct.getColoredCol()==column && dct.getColoredRow()==row) {
                c.setBackground(Color.cyan);
                return c;
            }
        }

        return c;


    }
}
