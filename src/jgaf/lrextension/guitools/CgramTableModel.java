/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import javax.swing.table.AbstractTableModel;
import jgaf.grammar.Grammar;

/**
 *
 * @author g
 */
public class CgramTableModel extends AbstractTableModel{
    private Grammar gram;
     
    public CgramTableModel(Grammar gram) {
        this.gram = gram;
    }

    public int getRowCount() {
        return gram.getProductionCount();
    }

    public int getColumnCount() {
        return 1;
    }

    public Object getValueAt(int r,
                             int columnIndex) {
        
                
            return gram.getProductionRules().get(r).getLeftHandSide()+
                 " -> "+
                    gram.getProductionRules().get(r).getRightHandSide();
            
        }
    
    
    public String getColumnName(int column) {
    return "Grammar";
}
}
