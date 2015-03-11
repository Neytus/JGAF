/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.Nonterminal;
import jgaf.grammar.ProductionRule;
import jgaf.grammar.Symbol;
import jgaf.lrextension.WString;

/**
 *
 * @author g
 */
public class CFGUtils {

    /**
     *
     * @param grammar
     * @return
     */
    

    /**
     *
     * @param grammar
     * @param nont
     * @return
     */
    public static List<ProductionRule> getNonterminalRules(Grammar grammar,
                                                           Symbol nont) {
        if (!nont.isNonterminal()) {
            throw new IllegalArgumentException(nont + " is not nonterminal");
        }
        if (!grammar.isContextFreeE()) {
            throw new IllegalArgumentException("Grammar is not context-free");
        }
        List<ProductionRule> ret =
                new ArrayList<ProductionRule>();
        for (ProductionRule production : grammar.getProductionRules()) {
            if (production.getLeftHandSide().getFirst().equals(nont)) {
                ret.add(production);
            }
        }
        return ret;
    }

    /**
     *
     * @param grammar
     * @return
     */
    public static Map<Symbol, Set<WString>> getGrammarMap(Grammar grammar) {
        if (!grammar.isContextFreeE()) {
            throw new IllegalArgumentException("Grammar is not context-free");
        }
        Map<Symbol, Set<WString>> map =
                new LinkedHashMap<Symbol, Set<WString>>();
        for (ProductionRule production : grammar.getProductionRules()) {
            WString rightSymbols=new WString(production.getRightHandSide().getSymbols());
            if (map.containsKey(production.getLeftHandSide().getFirst())) {
                map.get(production.getLeftHandSide().getFirst()).add(rightSymbols);
            } else {
                Set<WString> rightSideList = new HashSet< WString>();
                rightSideList.add(rightSymbols);
                map.put(production.getLeftHandSide().getFirst(), rightSideList);
            }
        }
        return map;
    }

    /**
     *
     * @param grammar
     * @return
     */
    public static boolean isAugmented(Grammar grammar) {
        if (!grammar.hasStartNonterminal()) {
            return false;
        }
        int i = 0;
        Symbol startN = grammar.getStartNonterminal();
        for (ProductionRule rule : grammar.getProductionRules()) {
            if (rule.getLeftHandSide().containsSymbol(startN)) {
                if (!(rule.getLeftHandSide().size() == 1)) {
                    return false;
                }
                i++;
                if (rule.getRightHandSide().size()!=1) return false;
                if (!rule.getRightHandSide().getFirst().isNonterminal()) return false;
            }
            if (rule.getRightHandSide().containsSymbol(startN)) {
                return false;
            }
            if (!(i == 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param grammar
     */
    public static void augment(Grammar grammar) {
        if (grammar.hasStartNonterminal()) {

            Symbol newStart = new Symbol("S'", Symbol.NONTERMINAL);
            WString rightSymbols = new WString();
            rightSymbols.add(grammar.getStartNonterminal());
            WString leftSymbols = new WString();
            leftSymbols.add(newStart);
            ProductionRule newStartRule = new ProductionRule(leftSymbols, rightSymbols);
            grammar.addRule(0, newStartRule);
            grammar.setStartNonterminal(newStart);
        }
    }

    /**
     *
     * @param grammar
     * @return
     */
    public static ProductionRule getFirstRule(Grammar grammar) {
        if (!isAugmented(grammar)) {
            throw new IllegalArgumentException("Grammar contains more rules with starting nonterminal");
        }
        List<ProductionRule> resList = new ArrayList<ProductionRule>();
        for (ProductionRule rule : grammar.getProductionRules()) {
            if (rule.getLeftHandSide().containsSymbol(grammar.getStartNonterminal())) {
                resList.add(rule);
            }
        }
        return resList.get(0);
    }

    /**
     *
     * @param grammar
     * @param rightSymbol
     * @return
     */
    public static List<ProductionRuleSide> findLeftSides(Grammar grammar,
                                              Symbol rightSymbol) {
        if (!grammar.isContextFreeE()) {
            throw new IllegalArgumentException("Grammar is not context-free");
        }
        ArrayList<ProductionRuleSide> foundedLeftSides = new ArrayList<ProductionRuleSide>();
        for (ProductionRule rule : grammar.getProductionRules()) {
            if (rule.getRightHandSide().containsSymbol(rightSymbol)) {
                foundedLeftSides.add(rule.getLeftHandSide());
            }
        }
        return foundedLeftSides;
    }

    /**
     *
     * @param grammar
     * @param symbol
     * @return
     */
    public static Set<Symbol> getReachableFrom(Grammar grammar,
                                               Symbol symbol) {
        if (!grammar.isContextFreeE()) {
            throw new IllegalArgumentException("Grammar is not context-free");
        }
        Set<Symbol> reachableSymbols = new HashSet<Symbol>();
        for (ProductionRule rule : grammar.getProductionRules()) {
            if (rule.getLeftHandSide().getFirst().equals(symbol)) {
                reachableSymbols.addAll(rule.getRightHandSide().getNonterminals());
            }
        }
        return reachableSymbols;
    }

    
    /**
     *
     * @param g
     * @return
     */
    public static  boolean hasUnreachablesOrUnusables(Grammar g){
    Grammar tmpG= (Grammar) g.clone();
    deleteUnusable(tmpG);
    if(!tmpG.isContextFreeE()||(!getGrammarMap(tmpG).equals(getGrammarMap(g)))) return true;
    deleteUnreachable(tmpG);
    return (!getGrammarMap(tmpG).equals(getGrammarMap(g)));
    }
    
    /**
     *
     * @param g
     */
    public static void deleteUnreachable(Grammar g) {
        if (!g.isContextFreeE()) {
            throw new IllegalArgumentException("Grammar is not context-free");
        }

        g.removeEmptyRules();
        Set<Symbol> reachNon = new HashSet<Symbol>();
        boolean change = true;
        Set<Symbol> toAdd = new HashSet<Symbol>();

        reachNon.add(g.getStartNonterminal());
        while (change) {
            for (Symbol s : reachNon) {
                toAdd.addAll(getReachableFrom(g, s));
            }
            change = reachNon.addAll(toAdd);
        }

        Set<Symbol> unreachable = new HashSet<Symbol>();
        for (Symbol nont : g.getNonterminals()) {
            if (!reachNon.contains(nont)) {
                unreachable.add(nont);
            }
        }

        Set<ProductionRule> rulesToRomeve = new HashSet<ProductionRule>();
        for (ProductionRule rule : g.getProductionRules()) {

            for (Symbol non : unreachable) {
                if (rule.containsSymbol(non)) {
                    rulesToRomeve.add(rule);
                }
            }
        }
        g.getProductionRules().removeAll(rulesToRomeve);
    }

    /**
     *
     * @param g
     */
    public static void deleteUnusable(Grammar g) {
        if (!g.isContextFreeE()) {
            throw new IllegalArgumentException("Grammar is not context-free");
        }
        g.removeEmptyRules();
        Set<Symbol> usable = new HashSet<Symbol>();
        boolean change = true;
        while (change) {
            change = false;
            for (ProductionRule rule : g.getProductionRules()) {
                if (usable.containsAll(rule.getRightHandSide().getNonterminals())) {
                    if (usable.add(rule.getLeftHandSide().getFirst())) {
                        change = true;
                    }
                }
            }
        }
        Set<Symbol> unusable = new HashSet<Symbol>();
        for (Symbol nont : g.getNonterminals()) {
            if (!usable.contains(nont)) {
                unusable.add(nont);
            }
        }

        if (!usable.contains(g.getStartNonterminal())) {
            g.setStartNonterminal(null);
        }

        Set<ProductionRule> rulesToRomeve = new HashSet<ProductionRule>();
        for (ProductionRule rule : g.getProductionRules()) {
            for (Symbol non : rule.getNonterminals()) {
                if (unusable.contains(non)) {
                    rulesToRomeve.add(rule);
                }
            }

        }

        g.getProductionRules().removeAll(rulesToRomeve);
    }
}
