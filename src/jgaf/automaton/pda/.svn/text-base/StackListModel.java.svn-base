/*
 * StackListModel.java
 *
 * Created on 3. listopad 2007, 18:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import javax.swing.AbstractListModel;
import javax.swing.ListModel;

/**
 *
 * @author hanis
 */

public class StackListModel extends AbstractListModel implements ListModel {
     

    private Stack stack;
    
    public StackListModel() {
        stack = new Stack();
    }    
  
    public Object getElementAt(int index) {
        return stack.get(index);     
    }

    public int getSize() {
        return stack.length();
    }
    
    public void setStack(Stack stack) {
        this.stack = stack;
        refresh();
    } 
//
//    public void setSelectedItem(Object item) {
//        selection = (Transition) item; 
//        //refresh();
//    } 
//
//    public Object getSelectedItem() {
//        return selection;
//    }

    public void refresh() {
        this.fireContentsChanged(this, 0, getSize());
    }
}