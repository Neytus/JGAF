/*
 * DesignMatrixRender.java
 *
 * Created on 14. únor 2008, 13:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author hanis
 */
public class DesignMatrixRender extends DefaultTableCellRenderer {
    
    public DesignMatrixRender() {
    }        
        
    
    public void ref() {
        this.repaint();
    }
    
    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        DesignManipulation design = DesignManipulation.getInstance();
        
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setHorizontalTextPosition(SwingConstants.CENTER);        
        cell.setBackground(design.getMatrixTransToNokBack());
        cell.setForeground(design.getMatrixTransToNokFront());   
        if(row == -1 && column != 0) {
             cell.setBackground(design.getMatrixStackNokBack());
             cell.setForeground(design.getMatrixStackNokFront());             
             cell.setBorder(new EtchedBorder());
        } else if(row == -1 && column == 0) {
             cell.setBackground(design.getMatrixCornerBack());
             cell.setForeground(design.getMatrixCornerBack());             
        } if(row != -1 && column == 0) {
             cell.setBackground(design.getMatrixTransFromNokBack());
             cell.setForeground(design.getMatrixTransFromNokFront());             
        }                
        
        if(row == 1 && column == 0) {
             cell.setBackground(design.getMatrixTransFromOkBack());
             cell.setForeground(design.getMatrixTransFromOkFront());             
        } else if(row == -1 && column == 2) {
             cell.setBackground(design.getMatrixStackOkBack());
             cell.setForeground(design.getMatrixStackOkFront());             
        } else if(row == 1 && column == 2) {
            cell.setBackground(design.getMatrixTransToOkBack());           
            cell.setForeground(design.getMatrixTransToOkFront());           
        } 
        return cell;
    }
    

}
