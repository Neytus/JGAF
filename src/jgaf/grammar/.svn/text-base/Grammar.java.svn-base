/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Constants.MathConstants;
import jgaf.Representation;

/**
 *
 * @author hanis
 */
public class Grammar implements Representation {
    
    //private List<Terminal> terminalSymbols;
    //private List<Nonterminal> nonterminalSymbols;
    
    public static final int INCORRECT = -1;
    public static final int TYPE0 = 0; //Recursively enumerable
    public static final int TYPE1 = 1; //Context-sensitive
    public static final int TYPE2 = 2; //Context-free
    public static final int TYPE3 = 3; //Regular
    


    private Symbol startNonterminal;
    private String name = "G";
    private List<ProductionRule> productionRules;

    public Grammar() {
    //    this.terminalSymbols = new ArrayList<Terminal>();
   //   this.nonterminalSymbols = new ArrayList<Nonterminal>();
        this.productionRules = new ArrayList<ProductionRule>();
    }





//    public boolean addTerminal(String terminal) {
//        return addTermainal(new Terminal(terminal));
//    }
//
//
//    public boolean addTermainal(Terminal terminal) {
//        if(terminal == null) {
//            throw new NullPointerException();
//        }
//        if(terminalSymbols.contains(terminal)) {
//            return false;
//        }
//        terminalSymbols.add(terminal);
//        return true;
//    }
//
//
//    public boolean addNonterminal(String nonterminal) {
//        return addNontermainal(new Nonterminal(nonterminal));
//    }
//
//
//    public boolean addNontermainal(Nonterminal nonterminal) {
//        if(nonterminal == null) {
//            throw new NullPointerException();
//        }
//        if(nonterminalSymbols.contains(nonterminal)) {
//            return false;
//        }
//        nonterminalSymbols.add(nonterminal);
//        return true;
//    }


    public SortedSet<Symbol> getSymbols() {
        SortedSet<Symbol> symbols = new TreeSet<Symbol>();
        for (ProductionRule rule : productionRules) {
            symbols.addAll(rule.getLeftHandSide().getSymbols());
            symbols.addAll(rule.getRightHandSide().getSymbols());
        }
        return symbols;
    }


    public SortedSet<Symbol> getTerminals() {
        SortedSet<Symbol> symbols = new TreeSet<Symbol>();
        for (Symbol symbol: getSymbols()) {
            if(symbol.isTerminal()) {
                symbols.add(symbol);
            }
        }
        return symbols;
    }

    public SortedSet<Symbol> getNonterminals() {
        SortedSet<Symbol> symbols = new TreeSet<Symbol>();
        for (Symbol symbol: getSymbols()) {
            if(symbol.isNonterminal()) {
                symbols.add(symbol);
            }
        }
        return symbols;
    }


    public int getProductionCount() {
        return productionRules.size();
    }

    public List<ProductionRule> getProductionRules() {
        return productionRules;
    }

    public int removeEmptyRules() {
        int i = 0;
        int j = 0;
        List<Integer> rulesToRomeve = new ArrayList<Integer>();
        for (ProductionRule productionRule : productionRules) {
            if(productionRule.equals(new ProductionRule())) {
                j++;
                rulesToRomeve.add(i);
            }
            i++;
        }
        for (int k = rulesToRomeve.size() - 1; k >=0 ; k--) {
            productionRules.remove((int)rulesToRomeve.get(k));
        }
        return j;
//        Set set = new HashSet();
//        List newList = new ArrayList();
//        for (Iterator iter = productionRules.iterator();  iter.hasNext(); ) {
//            Object element = iter.next();
//            if (set.add(element))
//      newList.add(element);
//    }
//    arlList.clear();
//    arlList.addAll(newList);
//}



    }

    public boolean removeLastEmptyRule() {
        int i = -1;
        for (int j = 0; j < productionRules.size(); j++) {
            if(productionRules.get(j).isEmpty()) {
                i = j;
            }
        }
        if(i == -1) {
            return false;
        }
        productionRules.remove(i);
        return false;
    }


    
    public Symbol getStartNonterminal() {        
        if(startNonterminal != null && getNonterminals().contains(startNonterminal)) {
            return startNonterminal;
        }
        return null;
    }

    public boolean hasStartNonterminal() {
        return getStartNonterminal() != null;
    }

    public void setStartNonterminal(Symbol startNonterminal) {
        this.startNonterminal = startNonterminal;
    }

    public boolean isStartNonterminal(Symbol startNonterminal) {
        if(!hasStartNonterminal()) {
            return false;
        }
        return getStartNonterminal().equals(startNonterminal);
    }


    public boolean addRule(ProductionRule rule) {
        if(productionRules.contains(rule) && ! rule.equals(ProductionRule.EMPTY_RULE)) {
            System.out.println("contains " + rule);
            return false;
        }
        productionRules.add(rule);
        return true;
    }

    public boolean addRule(int index, ProductionRule rule) {
        if(productionRules.contains(rule)) {
            return false;
        }
        productionRules.add(index, rule);
        return true;
    }


    public int removeRule(ProductionRule rule) {
        if(productionRules.contains(rule)) {
            int index = productionRules.indexOf(rule);
            productionRules.remove(rule);
            return index;
        }
        return -1;
    }


    public Map<ProductionRuleSide, List<ProductionRuleSide>> getSameLeftSideMap() {
        Map<ProductionRuleSide, List<ProductionRuleSide>> map =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        for (ProductionRule production : productionRules) {            
            if(map.containsKey(production.getLeftHandSide())) {
                map.get(production.getLeftHandSide()).add(production.getRightHandSide());
            } else {
                List<ProductionRuleSide> rightSide = new ArrayList<ProductionRuleSide>();
                rightSide.add(production.getRightHandSide());
                map.put(production.getLeftHandSide(), rightSide);
            }
        }
        return map;
    }


    @Override
    public String toString() {
        return writeGrammar();
//        StringBuilder sb = new StringBuilder();
//        sb.append(getSymbols()).append("\n");
//        sb.append(getNonterminals()).append("\n");
//        sb.append(getTerminals()).append("\n");
//        for (ProductionRule production : productionRules) {
//            sb.append(production.toString()).append("\n");
//        }
//        return sb.toString();
    }

    public String writeGrammar() {
        return name + " = (" + writeNonterminalSet() + ", " + writeTerminalSet() + ", P, "
                + (hasStartNonterminal() ? getStartNonterminal().getName() : "-") + ")" +
                "\n\n" + writeProdictions();
    }

    public String writeGrammarShort() {
        return name + " = (" + writeNonterminalSet() + ", " + writeTerminalSet() + ", P, "
                + (hasStartNonterminal() ? getStartNonterminal().getName() : "-") + ")" +
                "\n\n" + writeShortProductions();
    }


    public String writeNonterminalSet() {
        if(getNonterminals().isEmpty()) {
            return MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Symbol nonterminal: getNonterminals()) {
            sb.append(nonterminal.getName()).append(", ");
        }
        if(!getNonterminals().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    public String writeTerminalSet() {
        if (getTerminals().isEmpty()) {
            return MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Symbol terminal: getTerminals()) {
            sb.append(terminal.getName()).append(", ");
        }
        if(!getNonterminals().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();        
    }


    public String writeShortProductions() {

        if(productionRules.isEmpty()) {
            return "P=" + MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder("P={\n");
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = getSameLeftSideMap();
        for (ProductionRuleSide leftSide : getSameLeftSideMap().keySet()) {
            sb.append("   ").append(leftSide.toString()).append(" -> ");
            for (ProductionRuleSide rightSide : map.get(leftSide)) {
                sb.append(rightSide.toString()).append(" | ");
            }
            sb.delete(sb.length() - 3, sb.length()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }


//    public String writeShortProductions() {
//        StringBuilder sb = new StringBuilder();
//         Map<ProductionRuleSide, List<ProductionRuleSide>> map = getSameLeftSideMap();
//        for (ProductionRuleSide leftSide : getSameLeftSideMap().keySet()) {
//            for (Symbol symbol : leftSide.getSymbols()) {
//                sb.append(leftSide.symbol.toString());
//            }
//            sb.append(" -> ");
//            for (ProductionRuleSide rightSide : map.get(leftSide)) {
//                for (Symbol symbol : rightSide.getSymbols()) {
//                    sb.append(symbol.toString());
//                }
//                sb.append(" | ");
//            }
//            sb.delete(sb.length() - 3, sb.length()).append("\n");
//        }
//        return sb.toString();
//    }



    public String writeProdictions() {
        if(productionRules.isEmpty()) {
            return "P=" + MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder("P={\n");
        for (ProductionRule productionRule : productionRules) {            
            sb.append("  ").append(productionRule.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        Grammar g = new Grammar();
        ProductionRule r1 = new ProductionRule();
        r1.addToLeftHandSide(new Nonterminal("S"));
        r1.addToRightHandSide(new Nonterminal("a"));
        r1.addToRightHandSide(new Nonterminal("A"));
        r1.addToRightHandSide(new Nonterminal("a"));

        ProductionRule r2 = new ProductionRule();
        r2.addToLeftHandSide(new Nonterminal("S"));
        r2.addToRightHandSide(new Nonterminal("B"));


        ProductionRule r3 = new ProductionRule();
        r3.addToLeftHandSide(new Nonterminal("G"));
        r3.addToLeftHandSide(new Nonterminal("d"));
        r3.addToRightHandSide(new Nonterminal("h"));
        r3.addToRightHandSide(new Nonterminal("j"));

        g.addRule(r3);
        g.addRule(r2);
        g.addRule(r1);

        System.out.println(g);
        System.out.println(g.writeShortProductions());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sortProductionRules() {
        Collections.sort(productionRules, new ProductionRuleAscendingComparator());
    }

    public void sortProductionRulesDecs() {
        sortProductionRules();
        Collections.sort(productionRules, new ProductionRuleDescendingComparator());
    }

    @Override
    public Representation clone() {
        Grammar g = new Grammar();
        g.setName(getName());
        if(hasStartNonterminal()) {
            g.setStartNonterminal((Symbol) getStartNonterminal().clone());
        }
        for (ProductionRule productionRule : productionRules) {
            g.addRule((ProductionRule) productionRule.clone());
        }
        return g;
    }


    public boolean isCorrect() {
        if(!hasStartNonterminal()) {
            return false;
        }
        if(getNonterminals().isEmpty()) {
            return false;
        }
        for (ProductionRule productionRule : productionRules) {
            boolean containsNonterminal = false;
            for (Symbol symbol : productionRule.getLeftHandSide().getSymbols()) {
                if(symbol.isNonterminal()) {
                    containsNonterminal = true;
                }
            }
            if(!containsNonterminal) {
                return false;
            }
        }
        return true;
    }

    public boolean isRegular() {
        return getType() == TYPE3;
    }

    public boolean isContextFree() {
        return getType() >= TYPE2;
    }

    public boolean isContextSensitive() {
        return getType() >= TYPE1;
    }

    public boolean isRecursivelyEnumerable() {
        return getType() >= TYPE0;
    }
    
    public int getType() {
        if(!isCorrect()) {
            return INCORRECT;
        }
        boolean regular = true;
        boolean cfg = true;
        boolean csg = true;
        for (ProductionRule rule : productionRules) {
            int leftSize = rule.getLeftHandSide().size();
            int rightSize = rule.getRightHandSide().size();
            //boolean singleLeftNonterminal = false;
            if(leftSize > 1 || !rule.getLeftHandSide().getSymbols().get(0).isNonterminal()) {
                regular = false;
                cfg = false;
            }
            
            if(rightSize == 1 && rule.getRightHandSide().getSymbols().get(0).isEpsilon()) {
                if(leftSize > 1 || !isStartNonterminal(rule.getLeftHandSide().getSymbols().get(0))) {
                    regular = false;
                    cfg = false;
                    csg = false;
                } else {
                    continue;
                }
            }
            
            boolean rightRegular = false;
            if((rightSize == 1 && rule.getRightHandSide().getSymbols().get(0).isTerminal()) ||
                    (rightSize == 2 && rule.getRightHandSide().getSymbols().get(0).isTerminal() &&
                    rule.getRightHandSide().getSymbols().get(1).isNonterminal())) {
                rightRegular = true;
            }
            if(!rightRegular) {
                regular = false;
            }
            
            if(leftSize > rightSize) {
                csg = false;
            }
        }      
        if(regular) {
            return TYPE3;
        }
        if(cfg) {
            return TYPE2;
        }
        if(csg) {
            return TYPE1;
        }
        return TYPE0;
    }


    public boolean isStartToEpsRule(ProductionRule rule) {
        if(!hasStartNonterminal()) {
            return false;
        }
        if (rule.getLeftHandSide().size() == 1
                && isStartNonterminal(rule.getLeftHandSide().getSymbols().get(0))
                && rule.getRightHandSide().isEpsilon()) {
            return true;
        }
        return false;
    }


    public ProductionRule getStartToEpsRule() {
        for (ProductionRule productionRule : productionRules) {
            if(isStartToEpsRule(productionRule)) {
                return productionRule;
            }
        }
        return null;
    }

    public boolean containsStartToEpsRule() {
        return getStartToEpsRule() != null;
    }


    public void clearHighlighting() {
        for (ProductionRule productionRule : productionRules) {
            productionRule.setBgColor(Color.WHITE);
            productionRule.setFgColor(Color.BLACK);
        }
    }


}
