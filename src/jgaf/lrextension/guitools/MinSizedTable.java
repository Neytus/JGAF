/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author g
 */
public class MinSizedTable extends  JTable{

    public MinSizedTable() {
    }

    public MinSizedTable(TableModel dm) {
        super(dm);
    }

    public MinSizedTable(TableModel dm,
                         TableColumnModel cm) {
        super(dm, cm);
    }

    public MinSizedTable(TableModel dm,
                         TableColumnModel cm,
                         ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    public MinSizedTable(int numRows,
                         int numColumns) {
        super(numRows, numColumns);
    }

    public MinSizedTable(Vector rowData,
                         Vector columnNames) {
        super(rowData, columnNames);
    }

    public MinSizedTable(Object[][] rowData,
                         Object[] columnNames) {
        super(rowData, columnNames);
    }
    
    
    @Override
    public boolean getScrollableTracksViewportWidth(){
    return getPreferredSize().width < getParent().getWidth();
    }
}
