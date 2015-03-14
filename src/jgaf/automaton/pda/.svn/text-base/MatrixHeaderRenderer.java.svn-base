/*
 * MatrixHeaderRenderer.java
 *
 * Created on 18. listopad 2007, 19:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author hanis
 */

public class MatrixHeaderRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
    
    private List<String> stackSymbols;
    private String stackSymbol;
    
    public MatrixHeaderRenderer(MatrixData matrixData) {
        stackSymbols = matrixData.getStackSymbols();
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
       
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(stackSymbol != null && column < (stackSymbols.size() - 1) && stackSymbols.get(column) == stackSymbol) {
            cell.setBackground(Color.WHITE);
        } else {
            cell.setBackground(Color.GRAY);
        }
        cell.setBorder(new LineBorder(Color.BLACK,1));
        //cell.setHorizontalTextPosition(SwingConstants.CENTER);
        //cell.setForeground(Color.GREEN);
        cell.setHorizontalAlignment(SwingConstants.CENTER);
        return cell;        
    }
    
     public void renderMatch(Set<Transition>  transitions) {
         this.stackSymbol = null;
         for (Transition transition : transitions) {
             this.stackSymbol = transition.getTernary().getStackSymbol();
         }
     }
}