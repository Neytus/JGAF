/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import jgaf.lrextension.WString;
import jgaf.lrextension.procedures.ParserTablesCalc;
import jgaf.lrextension.procedures.ParserTablesCalc;
import jgaf.lrextension.procedures.SameActionItems;
import jgaf.lrextension.procedures.SameActionItems;

/**
 *
 * @author g
 */
public class ActionTableModel extends AbstractTableModel{
    private Map<Integer,HashMap<WString,SameActionItems>> actionRep;
    private List<WString> terStrgs;
    
    public ActionTableModel(ParserTablesCalc calculator) {
         actionRep=calculator.getActionRep();
         terStrgs=calculator.getActionTColumns();
    }
    
    public int getRowCount() {
        return actionRep.size();
    }

    public int getColumnCount() {
        return terStrgs.size()+1;
    }

    public Object getValueAt(int rowIndex,
                             int columnIndex) {
         Map<WString,SameActionItems> row =actionRep.get(rowIndex);
        if (columnIndex==0) return rowIndex;
         if (row.containsKey(terStrgs.get(columnIndex-1))){
             SameActionItems actionItems= row.get(terStrgs.get(columnIndex-1));
             
             return actionItems;
        }
         return null;
    }
    
    @Override
     public String getColumnName(int col) {        
        if (col==0) return "State"; 
        return terStrgs.get(col-1).toString();        
    }    
    
    public int getColByName(WString w) {
         return terStrgs.indexOf(w)+1;
    
    
    }
}