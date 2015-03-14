/*
 * TransitionListModel.java
 *
 * Created on 28. říjen 2007, 13:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.SortedSet;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;

/**
 *
 * @author hanis
 */

public class TransitionListModel extends AbstractListModel implements ListModel {
     
    private SortedSet<Transition> transitions;
    private Transition selection;
    
    public TransitionListModel(PushdownAutomaton automata) {
        this.transitions = automata.getTransitionFunction().getTransitions();
    }    
  
    public Object getElementAt(int index) {
        return transitions.toArray()[index];     
    }

    public int getSize() {
        return transitions.size();
    }

    public void setSelectedItem(Object item) {
        selection = (Transition) item; 
        //refresh();
    } 

    public Object getSelectedItem() {
        return selection;
    }

    public void refresh() {
        this.fireContentsChanged(this, 0, getSize());
    }
}