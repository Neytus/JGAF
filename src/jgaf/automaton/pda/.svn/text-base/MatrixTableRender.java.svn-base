/*
 * MatrixTableRender.java
 *
 * Created on 31. říjen 2007, 20:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Color;
import java.awt.Component;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;
import javax.swing.plaf.basic.BasicBorders.MarginBorder;
import javax.swing.plaf.metal.MetalBorders.PaletteBorder;
import javax.swing.plaf.metal.MetalBorders.TextFieldBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author hanis
 */

public class MatrixTableRender extends DefaultTableCellRenderer {

    private Color stackOkBack = new Color(255,0,0);
    private Color stackOkFront = new Color(255,255,255);
    
    private Color stackNokBack = new Color(0,0,200);
    private Color stackNokFront = new Color(255,255,255);

    private Color cornerBack = new Color(0,0,225);
    
    private Color TransFromOkBack = new Color(0,255,0);
    private Color TransFromOkFront = new Color(255,255,255);
    
    private Color TransFromNokBack = new Color(0,0,100);
    private Color TransFromNokFront = new Color(255,255,255);

    private Color TransToOkBack = new Color(0,255,0);
    private Color TransToOkFront = new Color(255,0,0);
    private Color TransToNokBack = new Color(255,255,225);
    private Color TransToNokFront = new Color(0,0,0);
    
    
    private MatrixData matrixData;
    private Set<Integer> matchRows;
    private int matchColumn;
    
    public MatrixTableRender(MatrixData matrixData) {
        this.matrixData = matrixData;
        this.matchRows = new HashSet<Integer>();
        matchColumn = -1;        
    }        
    
    public void getPlain() {
        this.matrixData = matrixData;
        this.matchRows = new HashSet<Integer>();
        matchColumn = -1;        
    }
    
    public void renderMatch(Set<Transition> transitions) {
        matchColumn = -1;
        matchRows.clear();
        for (Transition transition : transitions) {
            Ternary ternary = transition.getTernary();                     
            matchRows.add(matrixData.getStateAndSymbols().indexOf(ternary.getStateAndSymbol()));
            matchColumn = matrixData.getStackSymbols().indexOf((String)ternary.getStackSymbol()) + 1;
        }
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
        if(matchRows.contains(row) && column == 0) {
             cell.setBackground(design.getMatrixTransFromOkBack());
             cell.setForeground(design.getMatrixTransFromOkFront());             
        } else if(row == -1 && column == matchColumn) {
             cell.setBackground(design.getMatrixStackOkBack());
             cell.setForeground(design.getMatrixStackOkFront());             
        } else if(matchRows.contains(row) && column == matchColumn) {
            cell.setBackground(design.getMatrixTransToOkBack());           
            cell.setForeground(design.getMatrixTransToOkFront());           
        }
        return cell;
    }
    

}
