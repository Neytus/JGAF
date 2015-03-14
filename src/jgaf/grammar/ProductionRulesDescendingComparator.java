/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.util.Comparator;

/**
 *
 * @author hanis
 */
public class ProductionRulesDescendingComparator implements Comparator<ProductionRules>{

    public int compare(ProductionRules o1, ProductionRules o2) {
        return o2.compareTo(o1);
    }
}
