/*
 * TransToListModel.java
 *
 * Created on 21. březen 2008, 14:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;

/**
 *
 * @author hanis
 */

public class TransToListModel extends AbstractListModel implements ListModel {
     
    private Pair selection = null;
    private List<Pair> data;
    private TransitionFunction transitionFunction;
    
    public TransToListModel(TransitionFunction transitionFunction) {
        this.transitionFunction = transitionFunction;
        nullModel();
    }                
    
    public void nullModel() {
        this.data = new ArrayList<Pair>();
        refresh();
    }
    
    public void setTransTo(Ternary ternary) {      
        SortedSet<Pair> p = transitionFunction.getPairs(ternary);
        if(p == null) {
            this.data = new ArrayList();
        } else {
            this.data = new ArrayList(transitionFunction.getPairs(ternary));
        }
        refresh();
    }    
    
    public int getIndexOf(Pair pair) {
        return data.indexOf(pair);
    }
  
    public Object getElementAt(int index) {
        return data.get(index);     
    }

    public int getSize() {
        if(data == null) {
            return 0;
        }
        return data.size();
    }

    public void setSelectedItem(Object item) {
        selection = (Pair) item; 
        refresh();
    } 

    public Object getSelectedItem() {
        return selection;
    }

    public void refresh() {
        this.fireContentsChanged(this, 0, getSize());
    }
}
