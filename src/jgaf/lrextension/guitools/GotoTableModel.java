/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;
import jgaf.grammar.Symbol;
import jgaf.lrextension.guitools.ColorableTableModel;
import jgaf.lrextension.procedures.ParserTablesCalc;

/**
 *
 * @author g
 */
public class GotoTableModel extends AbstractTableModel {
    private List<Symbol> symbols;
    private Map<Integer, HashMap<Symbol, Integer>> gotorep;
    
    public GotoTableModel(ParserTablesCalc calculator) {
       gotorep= calculator.getGotoRep();
       this.symbols=calculator.getGotoTColumns();
    }

    
    
    
    
    public int getRowCount() {
        return gotorep.size();
    }

    public int getColumnCount() {
        return symbols.size()+1;
    }

    public Object getValueAt(int rowIndex,
                             int columnIndex) {
        
         Map<Symbol,Integer> row =gotorep.get(rowIndex);
         if (columnIndex==0) return rowIndex;
         
         
         if (row.containsKey(symbols.get(columnIndex-1)))
             return row.get(symbols.get(columnIndex-1));
         return null;
    }
    
     @Override
    public String getColumnName(int col) {        
        if (col==0) return "State";
         return symbols.get(col-1).toString();        
    }    

    

    public int getColByName(Symbol s) {
         return symbols.indexOf(s)+1;
    
    
    }
}
