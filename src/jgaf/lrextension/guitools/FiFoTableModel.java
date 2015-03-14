/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.table.AbstractTableModel;
import jgaf.lrextension.WString;
import jgaf.grammar.Symbol;

/**
 *
 * @author g
 */
public class FiFoTableModel extends AbstractTableModel {

    private List<Map<Symbol, Set<WString>>> dataMaps;
    private WString nontsSorted;

    public FiFoTableModel(WString nontsSorted,
                          List<Map<Symbol, Set<WString>>> dataMaps) {
        this.dataMaps = dataMaps;
        this.nontsSorted = nontsSorted;
    }

    public int getRowCount() {

        return dataMaps.size();
    }

    public int getColumnCount() {
        return nontsSorted.size()+1;
    }

    public Object getValueAt(int rowIndex,
                             int col) {
       if (col==0) return rowIndex;
        return dataMaps.get(rowIndex).get(nontsSorted.get(col-1));
    }

    @Override
    public String getColumnName(int col) {
        if (col==0)return "Iteration";
        return nontsSorted.get(col-1).toString();
    }
}
