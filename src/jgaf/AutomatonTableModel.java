/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf;

import javax.swing.table.AbstractTableModel;
import jgaf.automaton.Automaton;

/**
 *
 * @author hanis
 */
public class AutomatonTableModel extends AbstractTableModel {

    private Automaton automaton;

    public AutomatonTableModel(Automaton automaton) {
        this.automaton = automaton;
    }

    public int getRowCount() {
        return automaton.getStates().size() + 1;
    }

    public int getColumnCount() {
        return automaton.getAlphabet().size() + 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
    //    if()
        return automaton.getStates().get(rowIndex);
    }
    
}
