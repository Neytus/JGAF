/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import jgaf.IA006.grammar.Symbol;

/**
 * This class as Model for table, that shows calculated follow sets. 
 * @author Empt
 */
public class FollowTableModel extends AbstractTableModel 
{
    private Map<Symbol,Set<List<Symbol>>> foSets = new HashMap<>();
    
    public void setSets(Map<Symbol,Set<List<Symbol>>> sets)
    {
        this.foSets = sets;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return foSets.keySet().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex,
                             int columnIndex) {
        List<Symbol> keys = new ArrayList<>(foSets.keySet());
        switch(columnIndex)
        {
            case 0:
                return keys.get(rowIndex);
            case 1:                
                return buildWord(foSets.get(keys.get(rowIndex)));
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String getColumnName(int column) 
    {
        switch(column)
        {
            case 0: return "NonTerminal";
            case 1: return "Follow sets";
            default: throw new IllegalArgumentException("mehehe");
        }
    }
    
    
    
    private String buildWord(Set<List<Symbol>> list)
    {
        StringBuilder sb = new StringBuilder("{");
        int i =0;
        for(List<Symbol> l : list)
        {
            for(Symbol s: l)
            {
                sb.append(s.getValue());
            }
            if(i < list.size() -1)
            {
                sb.append(",");
            }
            i++;
        }
        sb.append("}");
        

        return sb.toString();
    }
    
}
