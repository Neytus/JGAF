/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import javax.swing.table.AbstractTableModel;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

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
        return automaton.getStates().size();
    }

    public int getColumnCount() {
        return automaton.getAlphabet().size() + 1;
    }

    public Object getValueAt(int row, int col) {
        State state = automaton.getStates().toArray(new State[0])[row];
        if(col == 0) {;
            if(state.isInitial()) {
                return "-> " + state;
            } else if(state.isAccepting()) {
                return "<- " + state;
            } else {
                return state;
            }
        }
        String[] alphabet = automaton.getAlphabet().toArray(new String[0]);
        for (Transition transition : automaton.getTransitions()) {
            if(transition.getFromState().equals(state) && transition.getLabels().contains(alphabet[col-1])) {
                return transition.getToState();
            }
        }
        return "";
    }


    @Override
    public String getColumnName(int col) {
        if(col == 0) {
            return "";
        }
        return automaton.getAlphabet().toArray(new String[0])[col-1];
    }


    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}