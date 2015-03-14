/*
 * MatrixTableModel.java
 *
 * Created on 27. srpen 2007, 20:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hanis
 */

public class MatrixTableModel extends AbstractTableModel {

    
    private SortedMap<StateAndSymbol, Map<String, Transition>> data;
    private List<String> stackSymbols;
    private List<StateAndSymbol> stateAndSymbols; 
    private Set<String> finalStates;
    
    public MatrixTableModel(MatrixData matrixData) {
        stackSymbols = matrixData.getStackSymbols();
        data = matrixData.getMatrixData();
        stateAndSymbols = matrixData.getStateAndSymbols();    
        finalStates = matrixData.getFinalStates();
    }
    
    public int getRowCount() {
        return data.size();
    }
    
    public int getColumnCount() {
        return stackSymbols.size() + 1;
    }
    
    public String getColumnName(int col) {
        if(col == 0) {
            return "/";
        } else {
            return stackSymbols.get(col - 1).toString();
        }
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0) {
            StateAndSymbol sas = stateAndSymbols.get(rowIndex);            
//            if(finalStates.contains(sas.getState())) {
//                return "-> " + sas;
//            }
            return sas;
        } else {
            Map<String, Transition> row = data.get(stateAndSymbols.get(rowIndex));
            if(row.containsKey(stackSymbols.get(columnIndex - 1))) {
                return row.get(stackSymbols.get(columnIndex - 1)).writePairs();
            } else {
                return "-"; 
            }
        }
    }
//    
//    public Class getColumnClass(int columnIndex) {
//        Class dataType = Object.class;
//        switch(columnIndex) {
//            case 0: dataType = Integer.class; break; 
//            case 1: dataType = Customer.class; break; 
//            case 2: dataType = String.class; break; 
//            case 3: dataType = String.class; break; 
//            case 4: dataType = Integer.class; break;             
//            default:dataType = Object.class; break; 
//        }
//        return dataType;
//    }
//    
    
    

    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }      
}