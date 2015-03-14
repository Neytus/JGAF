/*
 * TransFromListModel.java
 *
 * Created on 21. březen 2008, 14:07
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
    
public class TransFromListModel extends AbstractListModel implements ListModel {
     
    private Ternary selection = null;
    private TransitionFunction transitionFunction;
    private List<Ternary> data;
    
    public TransFromListModel(TransitionFunction transitionFunction) {
        this.transitionFunction = transitionFunction;
        setData();
    }    
      
    private void setData() {
        this.data = new ArrayList(transitionFunction.getTernaries());
    }
    
    public Object getElementAt(int index) {
        return data.get(index);     
    }

    public int getSize() {
        return data.size();
    }
    
    public int getIndexOf(Ternary ternary) {
        return data.indexOf(ternary);
    }    

    public void setSelectedItem(Object item) {
        selection = (Ternary) item; 
        refresh();
    } 

    public Object getSelectedItem() {
        return selection;
    }

    public void refresh() {
        setData();
        this.fireContentsChanged(this, 0, getSize());
    }
}
