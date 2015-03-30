/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.List;
import java.util.Set;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class LLCheckerRow 
{
    private LLSymbol key;
    private Set<List<LLSymbol>> L;
    List<SubLLCheckerRow> subRows;

    public LLSymbol getKey() {
        return key;
    }

    public void setKey(LLSymbol key) {
        this.key = key;
    }

    public Set<List<LLSymbol>> getL() {
        return L;
    }

    public void setL(
                     Set<List<LLSymbol>> L) {
        this.L = L;
    }

    public List<SubLLCheckerRow> getSubRows() {
        return subRows;
    }

    public void setSubRows(
                           List<SubLLCheckerRow> subRows) {
        this.subRows = subRows;
    }

    @Override
    public String toString() {
        return "LLCheckerRow{" + "key=" + key + ", L=" + L + '}';
    }
}
