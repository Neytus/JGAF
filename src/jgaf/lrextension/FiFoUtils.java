/*
 * To change this template, choose Tools | Templates
 * and open the template e editor.
 */
package jgaf.lrextension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import jgaf.Constants.MathConstants;
import jgaf.lrextension.WString;
import jgaf.grammar.Grammar;
import jgaf.grammar.Symbol;
import jgaf.Constants.MathConstants;

/**
 *
 * @author gx
 */
public class FiFoUtils {

    public static Set<WString> fiFast(WString strg,
                                      int k,
                                      Map<Symbol, Set<WString>> fiMap) {

        Set<WString> ret = new HashSet<WString>();
        if (k == 0) {
            return ret;
        }
        if (strg.isEmpty()) {
            return ret;
        }

        boolean firstRun2 = true;
        for (Symbol symbol : strg) {
            Set<WString> symbolFi = new HashSet<WString>();
            if (symbol.isNonterminal()) {
                symbolFi = fiMap.get(symbol);
            } else {
                WString thisSymbol = new WString();
                thisSymbol.add(symbol);
                symbolFi.add(thisSymbol);
            }
            if (firstRun2) {
                ret = symbolFi;
            } else {
                ret = FiFoUtils.concatSet(ret, symbolFi);
                ret = FiFoUtils.ShorternSet(k, ret);
            }
            firstRun2 = false;
        }

        return ret;
    }

    public static Map<Symbol, Set<WString>> calcFiMap(Grammar grammar,
                                                      int k) {
        Map<Symbol, Set<WString>> fiMap = new HashMap<Symbol, Set<WString>>();
        if (k == 0) {
            return fiMap;
        }
        Map<Symbol, Set<WString>> previouseFiMap = new HashMap<Symbol, Set<WString>>();
        Map<Symbol, Set<WString>> gramMap = CFGUtils.getGrammarMap(grammar);

        for (Symbol nonT : gramMap.keySet()) {
            previouseFiMap.put(nonT, new HashSet<WString>());
        }

        while (!fiMap.equals(previouseFiMap)) {
            previouseFiMap.putAll(fiMap);

            for (Symbol nonT : gramMap.keySet()) {
                fiMap.put(nonT, new HashSet<WString>()); // changes reference - maintains ineqaulity
                for (WString rule : gramMap.get(nonT)) {
                    Set<WString> ruleFi = new HashSet<WString>();
                    boolean firstRun2 = true;
                    for (Symbol symbol : rule) {
                        Set<WString> symbolFi = new HashSet<WString>();
                        if (symbol.isNonterminal()) {
                            symbolFi = previouseFiMap.get(symbol);
                        } else {
                            WString thisSymbol = new WString();
                            thisSymbol.add(symbol);
                            symbolFi.add(thisSymbol);
                        }
                        if (firstRun2) {
                            ruleFi = symbolFi;
                        } else {
                            ruleFi = FiFoUtils.concatSet(ruleFi, symbolFi);
                            ruleFi = FiFoUtils.ShorternSet(k, ruleFi);
                        }
                        firstRun2 = false;
                    }
                    fiMap.get(nonT).addAll(ruleFi);
                }
            }
        }
        return fiMap;
    }

    public static Map<Symbol, Set<WString>> calcFo(Grammar grammar,
                                                   int k) {
        Map<Symbol, Set<WString>> foMap = new HashMap<Symbol, Set<WString>>();
        if (k == 0) {
            return foMap;
        }
        Map<Symbol, Set<WString>> fiMap = calcFiMap(grammar, k);

        Map<Symbol, Set<WString>> previouseFoMap = new HashMap<Symbol, Set<WString>>();
        Map<Symbol, Set<WString>> gramMap = CFGUtils.getGrammarMap(grammar);

        for (Symbol nonT : gramMap.keySet()) {
            if (nonT.equals(grammar.getStartNonterminal())) {
                previouseFoMap.put(nonT, FiFoUtils.createEpsilonSet());
            } else {
                previouseFoMap.put(nonT, new HashSet<WString>());
            }
        }

        while (!foMap.equals(previouseFoMap)) {

            previouseFoMap.putAll(foMap);

            for (Iterator<Symbol> it = gramMap.keySet().iterator(); it.hasNext();) {
                Symbol nonCalc = it.next();
                foMap.put(nonCalc, new HashSet<WString>());
                if (nonCalc.equals(grammar.getStartNonterminal())) {
                    foMap.get(nonCalc).addAll(FiFoUtils.createEpsilonSet());
                }
                for (Iterator<Symbol> it2 = gramMap.keySet().iterator(); it2.hasNext();) {
                    Symbol nonOther = it2.next();
                    for (WString rule : gramMap.get(nonOther)) {


                        WString subRule = rule;
                        while (subRule.contains(nonCalc)) {

                            Set<WString> subRuleFi;
                            subRule = new WString(subRule.subList(subRule.indexOf(nonCalc) + 1, subRule.size()));



                            subRuleFi = fiFast(subRule, k, fiMap);
                            if (subRuleFi.isEmpty()) {
                                subRuleFi.addAll(FiFoUtils.createEpsilonSet());
                            }
                            Set<WString> nonOtherOldFo = new HashSet<WString>();
                            nonOtherOldFo.addAll(previouseFoMap.get(nonOther));
                            subRuleFi = FiFoUtils.concatSet(subRuleFi, nonOtherOldFo);
                            subRuleFi = FiFoUtils.ShorternSet(k, subRuleFi);
                            foMap.get(nonCalc).addAll(subRuleFi);



                        }

                    }
                }
            }
        }
        return foMap;
    }

    public static Set<WString> concatSet(Set<WString> a,
                                         Set<WString> b) {
        Set<WString> tmp = new HashSet<WString>();
        Set<WString> ret = new HashSet<WString>();

        if (!(b.isEmpty() || a.isEmpty())) {
            for (WString strgFromA : a) {
                for (WString strgFromB : b) {
                    tmp.add(concat(strgFromA, strgFromB));
                }
            }
            ret.addAll(tmp);
        }

        return ret;
    }

    public static Set<WString> ShorternSet(int k,
                                           Set<WString> a) {
        Set<WString> tmp = new HashSet<WString>();
        Set<WString> ret = new HashSet<WString>();
        tmp.addAll(a);
        for (Iterator<WString> it = tmp.iterator(); it.hasNext();) {
            WString strg = it.next();
            if (strg.size() > k) {
                strg.subList(k, strg.size()).clear();
            }
        }
        ret.addAll(tmp);
        return ret;
    }

    public static WString concat(WString strgOne,
                                 WString strgTwo) {

        if (strgOne.get(0).isEpsilon()) {
            return new WString(strgTwo);
        }

        if (strgTwo.get(0).isEpsilon()) {
            return new WString(strgOne);
        }

        WString a = new WString(strgOne);
        a.addAll(strgTwo);

        return a;
    }

    public static Set<WString> createEpsilonSet() {

        Set<WString> retSet = new HashSet<WString>();
        retSet.add(createEpsilonStr());
        return retSet;
    }

    public static WString createEpsilonStr() {
        WString symbols = new WString();
        symbols.add(new Symbol());
        return symbols;
    }

    public static Set<WString> concatKSet(int k,
                                          Set<WString> a,
                                          Set<WString> b) {
        return ShorternSet(k, concatSet(a, b));
    }
}
