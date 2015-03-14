/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRule;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.Symbol;

/**
 *
 * @author g
 */
public class CFGUtils {

    /**
     *
     * @param grammar
     * @param nont
     * @return
     */
    public static List<ProductionRules> getNonterminalRules(Grammar grammar,
                                                           Symbol nont) {
        if (!nont.isNonterminal()) {
            throw new IllegalArgumentException(nont + " is not nonterminal");
        }
        if (!grammar.isContextFreeE()) {
            throw new IllegalArgumentException("Grammar is not context-free");
        }
        List<ProductionRules> ret =
                new ArrayList<>();
        for (ProductionRules production : grammar.getProductionRules()) {
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
        Map<Symbol, Set<WString>> map = new LinkedHashMap<>();
        for (ProductionRules production : grammar.getProductionRules()) {
            WString rightSymbols=new WString(production.getRightHandSide().getSymbols());
            if (map.containsKey(production.getLeftHandSide().getFirst())) {
                map.get(production.getLeftHandSide().getFirst()).add(rightSymbols);
            } else {
                Set<WString> rightSideList = new HashSet< >();
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
        for (ProductionRules rule : grammar.getProductionRules()) {
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
            grammar.addRule(0, new ProductionRules(newStartRule));
            grammar.setStartNonterminal(newStart);
        }
    }

    /**
     *
     * @param grammar
     * @return
     */
    public static ProductionRules getFirstRule(Grammar grammar) {
        if (!isAugmented(grammar)) {
            throw new IllegalArgumentException("Grammar contains more rules with starting nonterminal");
        }
        List<ProductionRules> resList = new ArrayList<>();
        for (ProductionRules rule : grammar.getProductionRules()) {
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
        ArrayList<ProductionRuleSide> foundedLeftSides = new ArrayList<>();
        for (ProductionRules rule : grammar.getProductionRules()) {
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
        Set<Symbol> reachableSymbols = new HashSet<>();
        for (ProductionRules rule : grammar.getProductionRules()) {
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
        Set<Symbol> reachNon = new HashSet<>();
        boolean change = true;
        Set<Symbol> toAdd = new HashSet<>();

        reachNon.add(g.getStartNonterminal());
        while (change) {
            for (Symbol s : reachNon) {
                toAdd.addAll(getReachableFrom(g, s));
            }
            change = reachNon.addAll(toAdd);
        }

        Set<Symbol> unreachable = new HashSet<>();
        for (Symbol nont : g.getNonterminals()) {
            if (!reachNon.contains(nont)) {
                unreachable.add(nont);
            }
        }

        Set<ProductionRules> rulesToRemove = new HashSet<>();
        for (ProductionRules rule : g.getProductionRules()) {

            for (Symbol non : unreachable) {
                if (rule.containsSymbol(non)) {
                    rulesToRemove.add(rule);
                }
            }
        }
        g.getProductionRules().removeAll(rulesToRemove);
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
        Set<Symbol> usable = new HashSet<>();
        boolean change = true;
        while (change) {
            change = false;
            for (ProductionRules rule : g.getProductionRules()) {
                if (usable.containsAll(rule.getRightHandSide().getNonterminals())) {
                    if (usable.add(rule.getLeftHandSide().getFirst())) {
                        change = true;
                    }
                }
            }
        }
        Set<Symbol> unusable = new HashSet<>();
        for (Symbol nont : g.getNonterminals()) {
            if (!usable.contains(nont)) {
                unusable.add(nont);
            }
        }

        if (!usable.contains(g.getStartNonterminal())) {
            g.setStartNonterminal(null);
        }

        Set<ProductionRules> rulesToRomeve = new HashSet<>();
        for (ProductionRules rule : g.getProductionRules()) {
            for (Symbol non : rule.getNonterminals()) {
                if (unusable.contains(non)) {
                    rulesToRomeve.add(rule);
                }
            }

        }

        g.getProductionRules().removeAll(rulesToRomeve);
    }
}
