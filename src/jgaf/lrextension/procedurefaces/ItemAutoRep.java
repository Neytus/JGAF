/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedurefaces;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import jgaf.lrextension.procedures.AutoState;
import jgaf.lrextension.procedures.Item;

/**
 *
 * @author g
 */
public class ItemAutoRep {

    private List<AutoState> iA;

    public ItemAutoRep(List<AutoState> iA) {
        this.iA = iA;
    }

    public ItemAutoRep() {
        iA = new LinkedList<AutoState>();
    }

    public List<AutoState> get() {
        return iA;
    }

    public Item getItemAt(int i) {
        int current = 0;
        for (Iterator<AutoState> it = iA.iterator(); it.hasNext();) {
            AutoState items = it.next();
            for (Item item : items) {
                if (current == i) {
                    return item;
                }
                current++;
            }
        }
        return null;
    }

    public int getStateAt(int i) {
        int current = 0;
        for (Iterator<AutoState> it = iA.iterator(); it.hasNext();) {
            LinkedHashSet<Item> items = it.next();
            for (Item item : items) {
                if (current == i) {
                    return iA.indexOf(items);
                }
                current++;
            }
        }
        return 0;
    }

    @Override
    public ItemAutoRep clone() {
        List<AutoState> resultSet;
        resultSet = new LinkedList<AutoState>();
        for (Iterator<AutoState> it = iA.iterator(); it.hasNext();) {
            AutoState state = it.next();
            AutoState stateset = new AutoState();
            resultSet.add(stateset);
            for (Iterator<Item> it1 = state.iterator(); it1.hasNext();) {
                Item item = it1.next();
                stateset.add(item.clone());
            }
        }
        return new ItemAutoRep(resultSet);
    }
}