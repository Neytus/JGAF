/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author hanis
 */
public class GrammarTableRenderer extends DefaultTableCellRenderer {

    private int x = -1;
    private int y = -1;
    private int maxLeft = 0;
    private int maxRight = 0;

    public GrammarTableRenderer() {
    }



    @Override
    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setHorizontalTextPosition(SwingConstants.CENTER);
        cell.setBackground(Color.WHITE);
        cell.setForeground(Color.BLACK);
        cell.setHorizontalTextPosition(SwingConstants.RIGHT);        
        if(column == GrammarTableModel.ARROW_INDEX) {
            cell.setForeground(Color.BLACK);
            cell.setBackground(Color.WHITE);
        } else {
            cell.setForeground(((ProductionRuleSide)value).getFgColor());
            cell.setBackground(((ProductionRuleSide)value).getBgColor());
        }
        

        if(column == 0) {
            cell.setHorizontalAlignment(SwingConstants.RIGHT);
        } else if(column == 1) {
            cell.setHorizontalAlignment(SwingConstants.CENTER);
        } else if(column == 2) {
            cell.setHorizontalAlignment(SwingConstants.LEFT);
        }
        if(row == y && column == x) {
            cell.setBackground(Color.red);
        }
        return cell;
    }

    public void setCell(int x, int y) {
        this.x = x;
        this.y = y;
    }



}
