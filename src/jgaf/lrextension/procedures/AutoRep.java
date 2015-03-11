/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author g
 */
public class AutoRep {
    public List<AutoState> iA;
    public String log;
    public Set<Integer> red;
    public Set<Integer> blue;
    public Set<Integer> green;
    public AutoRep(){
        iA=new ArrayList<AutoState>();
        
    }
    
     
    
    public Item getItemAt(int i){
 int current=0;
        for (Iterator<AutoState> it = iA.iterator(); it.hasNext();) {
            LinkedHashSet<Item> items = it.next();
            for (Item item:items){
            if (current==i) return item;
            current++;
            }
        }
 return null;
 }
    
    public int getStateAt(int i){
 int current=0;
        for (Iterator<AutoState> it = iA.iterator(); it.hasNext();) {
            LinkedHashSet<Item> items = it.next();
            for (Item item:items){
            if (current==i) return iA.indexOf(items);
            current++;
            }
        }
 return 0;
 }

    public List<AutoState> getiA() {
        return iA;
    }
    
    @Override
    public  AutoRep clone(){
    AutoRep ar= new AutoRep();
    for  (Iterator<AutoState> it = iA.iterator(); it.hasNext();) {
            AutoState items = it.next();
             AutoState cItems= new AutoState();
            ar.iA.add(cItems );
            for (Iterator<Item> it2 = items.iterator(); it2.hasNext();) {
                Item item = it2.next();
                cItems.add(item.clone());
            }
    }
    return ar;
}
}