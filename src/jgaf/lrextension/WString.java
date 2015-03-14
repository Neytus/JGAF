/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import jgaf.grammar.Symbol;

/**
 *
 * @author g
 */
public class WString extends ArrayList<Symbol> {

    public WString(int initialCapacity) {
        super(initialCapacity);
    }

    public WString() {
    }

    public WString(Collection<? extends Symbol> c) {
        super(c);
    }

    public WString(String s) {
        //duplicade code from ProductionRuleSide - refactor

        s = s.trim();
        if (s.equalsIgnoreCase("eps.")) {
            add(new Symbol());
            return;
        }

        for (int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            if (letter.equals(letter.toUpperCase())) {
                add(new Symbol(letter, Symbol.NONTERMINAL));
            } else {
                add(new Symbol(letter, Symbol.TERMINAL));
            }
        }


    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<Symbol> it = this.iterator(); it.hasNext();) {
            Symbol symbol = it.next();
            sb.append(symbol);
        }
        return sb.toString();
    }

    public boolean hasNonterminal() {

        StringBuilder sb = new StringBuilder();
        for (Iterator<Symbol> it = this.iterator(); it.hasNext();) {
            Symbol symbol = it.next();
            if (symbol.isNonterminal()) {
                return true;
            }
        }
        return false;
    }
}
