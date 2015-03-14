/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import jgaf.lrextension.procedures.AutoState;
import jgaf.lrextension.procedures.Item;
import jgaf.lrextension.procedurefaces.ItemAutoRep;

/**
 *
 * @author g
 */
public class AutoTableModel extends AbstractTableModel {

    public static final int STATE = 0;
    public static final int RULE = 1;
    public static final int INC_SYMBOL = 2;
    public static final int TO_STATE = 3;
    public static final int LOC_FO = 4;
    private static final int COLUMN_COUNT = 5;
    private ItemAutoRep itemAutoRep;
    private List<AutoState> iA;
    //Names of the columns 
    public String[] colNames = {"State",
        "Item",
        "Next Symbol",
        "To State",
        "Local follow"};
    // Types of the columns. 
    
    @Override
    public String getColumnName(int col) {        
        return colNames[col];       
    }    
    
    public AutoTableModel(ItemAutoRep itemAutoRep) {
        this.itemAutoRep = itemAutoRep;
        iA = itemAutoRep.get();
    }
    
    public int getRowCount() {
        int count = 0;
        if (!iA.isEmpty()) {
            for (Iterator<AutoState> it = iA.iterator(); it.hasNext();) {
                LinkedHashSet<Item> items = it.next();
                if (!items.isEmpty()) {
                    count = count + items.size();
                }
                
            }
        }
        return count;
    }
    
    public int getColumnCount() {
        return COLUMN_COUNT;
    }
    
    public Object getValueAt(int rowIndex,
                             int columnIndex) {
        switch (columnIndex) {
            case 0:
                return itemAutoRep.getStateAt(rowIndex);
            case 1:
                return itemAutoRep.getItemAt(rowIndex).getRuleStr();
            case 2:
                return itemAutoRep.getItemAt(rowIndex).getNextSymbolStr();
            case 3:
                return itemAutoRep.getItemAt(rowIndex).getToItemSetStr();
            case 4:                
                return itemAutoRep.getItemAt(rowIndex).getLocFoStr();
            default:
                return new Object();
        }
    }
    
    public Color getColorAt(int rowIndex,
                             int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Color.BLACK;
            case 1:
                return itemAutoRep.getItemAt(rowIndex).getColor(0);
            case 2:
                return itemAutoRep.getItemAt(rowIndex).getColor(1);
            case 3:
                return itemAutoRep.getItemAt(rowIndex).getColor(2);
            case 4:                
                return itemAutoRep.getItemAt(rowIndex).getColor(3);
            default:
                return Color.BLACK;
        }
    }
}
