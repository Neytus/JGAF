/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Color;
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
public class PushdownAutomatonEditorRenderer  extends DefaultTableCellRenderer {


    private MatrixData matrixData;

    public PushdownAutomatonEditorRenderer(MatrixData matrixData) {
        this.matrixData = matrixData;
    }


    @Override
    public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        DesignManipulation design = DesignManipulation.getInstance();

        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        //cell.setBorder(new EmptyBorder(1,1,1,1));
        cell.setHorizontalAlignment(SwingConstants.CENTER);
        cell.setHorizontalTextPosition(SwingConstants.CENTER);
        cell.setBackground(design.getMatrixTransToNokBack());
        cell.setForeground(design.getMatrixTransToNokFront());
        if(row == -1 && column != 0) {
             cell.setBackground(new Color(240,240,240));
             cell.setForeground(Color.BLACK);
             cell.setBorder(new EtchedBorder());
        } else if(row == -1 && column == 0) {
             cell.setBackground(Color.WHITE);
             cell.setForeground(Color.WHITE);
        } if(row != -1 && column == 0) {
             cell.setBackground(new Color(240,240,240));
             cell.setForeground(Color.BLACK);
        }
//        if(matchRows.contains(row) && column == 0) {
//             cell.setBackground(design.getMatrixTransFromOkBack());
//             cell.setForeground(design.getMatrixTransFromOkFront());
//        } else if(row == -1 && column == matchColumn) {
//             cell.setBackground(design.getMatrixStackOkBack());
//             cell.setForeground(design.getMatrixStackOkFront());
//        } else if(matchRows.contains(row) && column == matchColumn) {
//            cell.setBackground(design.getMatrixTransToOkBack());
//            cell.setForeground(design.getMatrixTransToOkFront());
//        }
        return cell;
    }


}
