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
import jgaf.lrextension.StringOutputUtils;

/**
 *
 * @author g
 */
public class ParseTableRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
            
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBackground(new Color(204, 204, 204));

        if (column == 0) {
            c.setBackground(new Color(251, 229, 194));
        }
        return c;


    }
}
