/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import jgaf.grammar.ProductionRule;

/**
 *
 * @author g
 */
public class SameActionItems {

    private List<Item> items;
    
    public static final int REDUCE = 0;
    public static final int SHIFT = 1;
    public static final int RR_CNFLCT = 2;
    public static final int SR_CNFLCT = 3;
    public static final int BOTH_CNFLCT = 4;
    public static final int ERROR=5;
    public SameActionItems(ArrayList<Item> items) {
        this.items = items;

    }

    public SameActionItems() {
        items = new ArrayList<Item>();
    }

    public SameActionItems(Item item) {
        items = new ArrayList<Item>();
        items.add(item);
    }

    public boolean add(Item item) {
        items.add(item);
        return !isConflict();
    }

    public int getAction() {





        boolean rr = false;
        boolean sr = false;
        boolean reduce = false;

        for (Item item : items) {
            for (Item item1 : items) {
                if (item.isShiftable() && !item1.isShiftable()) {
                    sr = true;
                    item.color(Color.red);
                    item1.color(Color.red);
                }
                if (!item.isShiftable() && !item1.isShiftable() && !item.getRule().equals(item1.getRule())) {
                    rr = true;
                    item.color(Color.red);
                    item1.color(Color.red);
                }
                if (!item.isShiftable()) {
                    reduce = true;
                }
            }
        }
        if (rr && sr) {
            return BOTH_CNFLCT;
        }
        if (rr) {
            return RR_CNFLCT;
        }
        if (sr) {
            return SR_CNFLCT;
        }
        if (reduce) {
            return REDUCE;
        }
        return SHIFT;
    }

    public boolean isConflict(){
        int action=getAction();
        if (action== RR_CNFLCT || action== SR_CNFLCT || action == BOTH_CNFLCT){
        return true;
        }
        return false;
    }
    @Override
    public String toString() {
        switch (getAction()) {
            case REDUCE:
                return "r(" +items.get(0).getRule()+ ")";
                
            case SHIFT:
                return "shift";
            case RR_CNFLCT:
                return "r/r CONF";
            case SR_CNFLCT:
                return "s/r CONF";
            case BOTH_CNFLCT:
                return "s/r/s CONF";
            default:
                return "shift";
        }
    }
    
    public ProductionRule reduceBy(){
    return items.get(0).getRule();
    
    }
}