/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.List;
import java.util.Set;
import jgaf.IA006.grammar.Symbol;

/**
 *
 * @author Empt
 */
public class LLCheckerRow 
{
    private Symbol key;
    private Set<List<Symbol>> L;
    List<SubLLCheckerRow> subRows;

    public Symbol getKey() {
        return key;
    }

    public void setKey(Symbol key) {
        this.key = key;
    }

    public Set<List<Symbol>> getL() {
        return L;
    }

    public void setL(
                     Set<List<Symbol>> L) {
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
