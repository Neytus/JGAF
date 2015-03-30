/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author g
 */
public class ParseStepsTable extends JTable{

    public ParseStepsTable() {        
        super();
    }

    public ParseStepsTable(ParseTableModel dm) {
        super(dm);
        
    }

    public void initalize(){
    setDefaultRenderer(Object.class, new ParseTableRenderer());
    setRowHeight(22);
    setShowGrid(true);   
    }
    
    
}
