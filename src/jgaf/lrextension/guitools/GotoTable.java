/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import javax.swing.table.TableModel;
import jgaf.grammar.Symbol;

/**
 *
 * @author g
 */
public class GotoTable extends DynamicallyColorableTable{

    public GotoTable(GotoTableModel dm) {
        super(dm);
    }
    
    public void color(Symbol s, int i){
        super.color( i,((GotoTableModel) getModel()).getColByName(s));
    }
}
