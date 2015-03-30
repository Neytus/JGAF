/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.awt.Color;
import java.util.Vector;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author g
 */
public class DynamicallyColorableTable extends MinSizedTable {

    private int coloredRow=-1;
    private int coloredCol=-1;
    
    
    public DynamicallyColorableTable() {
    }

    public DynamicallyColorableTable(TableModel dm) {
        super(dm);
    }

    public DynamicallyColorableTable(TableModel dm,
                                     TableColumnModel cm) {
        super(dm, cm);
    }

    public DynamicallyColorableTable(TableModel dm,
                                     TableColumnModel cm,
                                     ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    public DynamicallyColorableTable(int numRows,
                                     int numColumns) {
        super(numRows, numColumns);
    }

    public DynamicallyColorableTable(Vector rowData,
                                     Vector columnNames) {
        super(rowData, columnNames);
    }

    public DynamicallyColorableTable(Object[][] rowData,
                                     Object[] columnNames) {
        super(rowData, columnNames);
    }

    public int getColoredRow() {
        return coloredRow;
    }

    public int getColoredCol() {
        return coloredCol;
    }

    

    
    
    
    
    
    public void color(int row, int col){
       
        coloredCol=col;
       coloredRow=row;
       repaint();
    }
    public void decolor(){
       
        color(-1,-1);
       repaint();
    }
}
