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
public class ProductionRuleSideAscendingComparator implements Comparator<ProductionRuleSide>{

    public int compare(ProductionRuleSide o1, ProductionRuleSide o2) {
        return o1.compareTo(o2);
    }
}
