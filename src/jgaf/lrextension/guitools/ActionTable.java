/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import javax.swing.table.TableModel;
import jgaf.lrextension.WString;

/**
 *
 * @author g
 */
public class ActionTable extends DynamicallyColorableTable{

    public ActionTable(ActionTableModel dm) {
        super(dm);
    }
    
     public void color(WString w, int i){
        super.color( i,((ActionTableModel) getModel()).getColByName(w));
    }
}
