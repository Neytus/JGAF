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
public class ProductionRulesAscendingComparator implements Comparator<ProductionRules>{

    public int compare(ProductionRules o1, ProductionRules o2) {
        return o1.compareTo(o2);
    }
}
