/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 *
 * @author g
 */
public class AutoState extends LinkedHashSet<Item>{

    public AutoState() {
    }

    public AutoState(Collection<? extends Item> c) {
        super(c);
    }
    
    
    
    
    
    public boolean containsItem(Item i){
        for (Iterator it = this.iterator(); it.hasNext();) {
            Item item =(Item) it.next();
            if (i.getRule().equals(item.getRule()) && item.getPosition()==i.getPosition()) {
                return true;
            }
        } return false;
    }
    
    
    public Item getSimilarItem(Item i){
    for (Iterator it = this.iterator(); it.hasNext();) {
            Item item =(Item) it.next();
            if (i.getRule().equals(item.getRule()) && item.getPosition()==i.getPosition()) {
                return item;
            }
    
    }return null;}
    
    
    
    public boolean addItem(Item i){
        if (!containsItem(i)) {
            
            return add(i);
        }else { return getSimilarItem(i).getLocFo().addAll(i.getLocFo());        
        }
    }
    
    public void unifySimilar(){
          AutoState tempState= new AutoState(this);
         this.clear();
          for (Iterator<Item> it = tempState.iterator(); it.hasNext();) {
            Item item = it.next();
            this.addItem(item);
        }
    }
}
