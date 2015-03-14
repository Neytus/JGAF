/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Constants.MathConstants;
import jgaf.Representation;
//import jgaf.grammar.algorithms.FAalgorithms;

/**
 *
 * @author hanis
 */
public class Grammar implements Representation {
    
    //private List<Terminal> terminalSymbols;
    //private List<Nonterminal> nonterminalSymbols;
    
    /*
    private static final int INCORRECT = -1;
    private static final int TYPE0 = 00; //Recursively enumerable
    private static final int TYPE1 = 10; //Context-sensitive
    private static final int TYPE2 = 20; //Context-free
    private static final int TYPE3 = 30; //Regular   
    private static final int TYPE2E = 21; //Context-free epsilon rules
    private static final int TYPE3E = 31; //regular epsilon rules
    */
    
    public static final int INCORRECT = -1;
    public static final int TYPE0 = 0; //Recursively enumerable
    public static final int TYPE1 = 1; //Context-sensitive
    public static final int TYPE2 = 2; //Context-free
    public static final int TYPE3 = 3; //Regular
    
    private Symbol startNonterminal;
    private String name = "G";
    //private List<ProductionRule> productionRules;
    private List<ProductionRules> productionRulesType2;

    public Grammar() {
    //    this.terminalSymbols = new ArrayList<Terminal>();
   //   this.nonterminalSymbols = new ArrayList<Nonterminal>();
        //this.productionRules = new ArrayList<ProductionRule>();
        this.productionRulesType2 = new ArrayList<ProductionRules>();
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


//    public SortedSet<Symbol> getSymbols() {
//        SortedSet<Symbol> symbols = new TreeSet<Symbol>();
//        for (ProductionRule rule : productionRules) {
//            symbols.addAll(rule.getLeftHandSide().getSymbols());
//            symbols.addAll(rule.getRightHandSide().getSymbols());
//        }
//        return symbols;
//    }

    public SortedSet<Symbol> getSymbols() {
        SortedSet<Symbol> symbols = new TreeSet<Symbol>();
        for (ProductionRules rule : productionRulesType2) {
            symbols.addAll(rule.getLeftHandSide().getSymbols());
            ProductionRulesSide right = rule.getRightHandSide();
            List<ProductionRuleSide> rightList = right.getRules();
            for(ProductionRuleSide r : rightList)
            symbols.addAll(r.getSymbols());
        }
        return symbols;
    }
    
    public SortedSet<Symbol> getTerminals() {
        SortedSet<Symbol> symbols = new TreeSet<Symbol>();
        for (Symbol symbol: getSymbols()) {
            if(symbol.isTerminal() || symbol.isEpsilon()) {
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
    
//    public SortedSet<Symbol> getLeftHandSideSymbols() {
//        SortedSet<Symbol> symbols = new TreeSet<Symbol>();
//        for (ProductionRule rule : productionRules) {
//            symbols.addAll(rule.getLeftHandSide().getSymbols());
//        }
//        return symbols;
//    }
//
//    public SortedSet<Symbol> getLeftHandSideNonterminals(){
//        SortedSet<Symbol> symbols = new TreeSet<Symbol>();
//        for (Symbol symbol: getLeftHandSideSymbols()) {
//            if(symbol.isNonterminal()) {
//                symbols.add(symbol);
//            }
//        }
//        return symbols;
//    }

    public int getProductionCount() {
        return productionRulesType2.size();
    }

//    public List<ProductionRule> getProductionRules() {
//        return productionRules;
//    }
    public List<ProductionRules> getProductionRules() {
        return productionRulesType2;
    }
    
    public List<ProductionRules> getProductionRulesType2() {
        return productionRulesType2;
    }
    
    public List<ProductionRules> getProductionsOfGivenNonterminal(ProductionRuleSide nonterminal) {
        List<ProductionRules> returnList = new ArrayList<ProductionRules>();
        for(ProductionRules testedRules : productionRulesType2){
            if(testedRules.getLeftHandSide().equals(nonterminal)){
                returnList.add(testedRules);
            }
        }
        return returnList;
    }

//    public List<ProductionRules> getProductionRulesType2() {
//        return productionRulesType2;
//    }
//
//    public Grammar(Symbol startNonterminal,
//                   List<ProductionRule> productionRules) {
//        this.startNonterminal = startNonterminal;
//        this.productionRules = productionRules;
//        this.productionRulesType2 = new ArrayList<ProductionRules>();
//        for(ProductionRule rule : productionRules){
//            this.productionRulesType2.add(new ProductionRules(rule));
//        }
//    }
    
//    public Grammar(Symbol startNonterminal,
//                   List<ProductionRules> productionRulesType2) {
//        this.startNonterminal = startNonterminal;
//        this.productionRulesType2 = productionRulesType2;
//        this.productionRules = new ArrayList<ProductionRule>();
//        
//    }
    
//    public int removeEmptyRules() {
//        int i = 0;
//        int j = 0;
//        List<Integer> rulesToRomeve = new ArrayList<Integer>();
//        for (ProductionRule productionRule : productionRules) {
//            if(productionRule.equals(new ProductionRule())) {
//                j++;
//                rulesToRomeve.add(i);
//            }
//            i++;
//        }
//        for (int k = rulesToRomeve.size() - 1; k >=0 ; k--) {
//            productionRules.remove((int)rulesToRomeve.get(k));
//        }
//        return j;
////        Set set = new HashSet();
////        List newList = new ArrayList();
////        for (Iterator iter = productionRules.iterator();  iter.hasNext(); ) {
////            Object element = iter.next();
////            if (set.add(element))
////      newList.add(element);
////    }
////    arlList.clear();
////    arlList.addAll(newList);
////}



    public int removeEmptyRules() {
        int i = 0;
        int j = 0;
        List<Integer> rulesToRemove = new ArrayList<Integer>();
        for (ProductionRules productionRule : productionRulesType2) {
            //System.out.println("Testuji pravidlo:" +productionRule.toString());
            boolean a=productionRule.getLeftHandSide().isEmpty();
            boolean b=productionRule.getRightHandSide().isEmpty();
            //int size = productionRule.getRightHandSide().size();
            
//            System.out.println("a = "+a+" b = "+b+" size = "+size+ " !"+productionRule.getRightHandSide().getRules().get(0)+"! ");
            if(a && b) {
                //System.out.println("mažu");
                j++;
                //System.out.println("i = "+i);
                rulesToRemove.add(i);
            }
            i++;
        }
        for (int k = rulesToRemove.size() - 1; k >=0 ; k--) {
            productionRulesType2.remove((int)rulesToRemove.get(k));
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
    

//    public boolean removeLastEmptyRule() {
//        int i = -1;
//        for (int j = 0; j < productionRules.size(); j++) {
//            if(productionRules.get(j).isEmpty()) {
//                i = j;
//            }
//        }
//        if(i == -1) {
//            return false;
//        }
//        productionRules.remove(i);
//        return false;
//    }

    public boolean removeLastEmptyRule() {
        int i = -1;
        for (int j = 0; j < productionRulesType2.size(); j++) {
            if(productionRulesType2.get(j).isEmpty()) {
                i = j;
            }
        }
        if(i == -1) {
            return false;
        }
        productionRulesType2.remove(i);
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


//    public boolean addRule(ProductionRule rule) {
//        if(productionRules.contains(rule) && ! rule.equals(ProductionRule.EMPTY_RULE)) {
//           // System.out.println("contains " + rule);
//            return false;
//        }
//        productionRulesType2.add(new ProductionRules(rule));
//        productionRules.add(rule);
//        
//        return true;
//    }

//    public boolean addRule(int index, ProductionRule rule) {
//        if(productionRules.contains(rule)) {
//            return false;
//        }
//        productionRulesType2.add(index, new ProductionRules(rule));
//        productionRules.add(index, rule);
//        return true;
//    }

    public boolean addRule(ProductionRules rule){
        //System.out.println("p jsou = "+productionRulesType2.toString());
        if(productionRulesType2.contains(rule) && ! rule.equals(ProductionRules.EMPTY_RULE)) {
           // System.out.println("contains " + rule);
            return false;
        }
        //System.out.println("pravidla jsou = "+rule.toString());
        //System.out.println("p jsou = "+productionRulesType2.toString());
        productionRulesType2.add(rule);
        //System.out.println("pravidla jsou = "+rule.toString());
        
        //System.out.println("Vložil jsem pravidlo = "+productionRulesType2.toString());
        ProductionRuleSide leftSide = rule.getLeftHandSide();
        List<ProductionRuleSide> rightSide = new ArrayList<ProductionRuleSide>();
        rightSide.addAll(rule.getRightHandSide().getRules());
        //System.out.println(rightSide.toString());
        //System.out.println("praá strana= "+rightSide.toString());
//        for(ProductionRuleSide oneRule : rightSide){
//            ProductionRule newRule = new ProductionRule(leftSide, oneRule);
//            System.out.println("newRule = "+newRule.toString());
////            productionRules.add(newRule);
//        }
//        System.out.println("productionRules jsou = "+productionRules.toString());
        return true;
    }
    
    public boolean addAllRules(List<ProductionRules> rules){
        productionRulesType2 = rules;
        return true;
    }
    
    public boolean addListOfRules(List<ProductionRules> rules){
        productionRulesType2.addAll(rules);
        return true;
    }
    
    public boolean addAllRules(Grammar grammar){
        List<ProductionRules> rules = grammar.getProductionRulesType2();
        for(ProductionRules rule : rules){
            addRule(rule);
        }
        return true;
    }
    
    public boolean addRule(int index, ProductionRules rule){
        if(productionRulesType2.contains(rule)) {
           // System.out.println("contains " + rule);
            return false;
        }
        //System.out.println("index je ="+index);
        //System.out.println("původní pravidlo bylo: "+productionRulesType2.get(index).toString());
        productionRulesType2.add(index, rule);
        //System.out.println("původní pravidlo bylo: "+productionRulesType2.get(index).toString());
        ProductionRuleSide leftSide = rule.getLeftHandSide();
        List<ProductionRuleSide> rightSide = rule.getRightHandSide().getRules();
        //System.out.println("pravá strana= "+rightSide.toString());
        for(ProductionRuleSide oneRule : rightSide){
            ProductionRule newRule = new ProductionRule(leftSide, oneRule);
            //System.out.println("newRule = "+newRule.toString());
//            productionRules.add(newRule);
        }
        //System.out.println();
        return true;
    }
    
    
  
    public int removeRulesOfLeft(ProductionRuleSide leftHandSide){
        int index = 0;
        List<ProductionRules> rulesToRemove = new ArrayList<>();
        for(int i=0; i<productionRulesType2.size();i++){
            ProductionRules rule = productionRulesType2.get(i);
            if(rule.getLeftHandSide().equals(leftHandSide)){
                System.out.println("odstraňuji pravidlo "+rule.toString());
                rulesToRemove.add(rule);
                index++;
            }
        }
        System.out.println("pravidla k odstraneni "+rulesToRemove.toString());
        System.out.println(productionRulesType2.toString());
        productionRulesType2.removeAll(rulesToRemove);
        System.out.println("po odstraneni" +productionRulesType2.toString());
        if(index>0)return index;
        return -1;
    }
    
    //TODO upravit
    public int removeRule(ProductionRules rule) {
        if(productionRulesType2.contains(rule)) {
            int index = productionRulesType2.indexOf(rule);
            productionRulesType2.remove(rule);
            return index;
        }
        int size = productionRulesType2.size();
        for(int i=0; i<size;i++){
            if(productionRulesType2.get(i) != null){
                ProductionRules oneLine = productionRulesType2.get(i);
                int index = productionRulesType2.indexOf(i);
                if(oneLine.getLeftHandSide().equals(rule.getLeftHandSide())){
                    ProductionRulesSide rulesToRemove = rule.getRightHandSide();
                    ProductionRulesSide testedRules = oneLine.getRightHandSide();
                    List<ProductionRuleSide> newRules = new ArrayList<ProductionRuleSide>();
                    newRules.addAll(testedRules.getRules());
                    for(ProductionRuleSide oneRule : testedRules.getRules()){

                        for(ProductionRuleSide oneRuleToRemove : rulesToRemove.getRules()){
                           if(oneRule.equals(oneRuleToRemove)){
                               newRules.remove(oneRule);
                           }
                        }                    
                    }
                    ProductionRules newOne = 
                            new ProductionRules(rule.getLeftHandSide(), new ProductionRulesSide(newRules));
                    productionRulesType2.remove(oneLine);
                    productionRulesType2.add(newOne);
                    return index;
                }
            }
        }
        return -1;
    }
    
    public int removeAllRules(){
        int index = -1;
        List<ProductionRules> allRules = new ArrayList<ProductionRules>();
        allRules.addAll(productionRulesType2);
        for(ProductionRules rule : allRules){
            index+=removeRule(rule);
        }
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof Grammar) {
           Grammar gramm = (Grammar) obj;
           if(startNonterminal.equals(gramm.getStartNonterminal()) && 
                   productionRulesType2.equals(gramm.getProductionRulesType2()))
           {
                return true;
           }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.startNonterminal != null ? this.startNonterminal.hashCode() : 0);
        hash = 79 * hash + (this.productionRulesType2 != null ? this.productionRulesType2.hashCode() : 0);
        return hash;
    }
    

//    public Map<ProductionRuleSide, List<ProductionRuleSide>> getSameLeftSideMap() {
//        Map<ProductionRuleSide, List<ProductionRuleSide>> map =
//                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
//        for (ProductionRule production : productionRules) {            
//            if(map.containsKey(production.getLeftHandSide())) {
//                map.get(production.getLeftHandSide()).add(production.getRightHandSide());
//            } else {
//                List<ProductionRuleSide> rightSide = new ArrayList<ProductionRuleSide>();
//                rightSide.add(production.getRightHandSide());
//                map.put(production.getLeftHandSide(), rightSide);
//            }
//        }
//        return map;
//    }
    
//    public Map<ProductionRuleSide, List<ProductionRuleSide>> getSameLeftSideMap() {
//        Map<ProductionRuleSide, List<ProductionRuleSide>> map =
//                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
//        for (ProductionRules production : productionRulesType2) {            
//            if(map.containsKey(production.getLeftHandSide())) {
//                map.get(production.getLeftHandSide()).addAll(production.getRightHandSide().getRules());
//            } else {
//                List<ProductionRuleSide> rightSide = new ArrayList<ProductionRuleSide>();
//                rightSide.addAll(production.getRightHandSide().getRules());
//                map.put(production.getLeftHandSide(), rightSide);
//            }
//        }
//        return map;
//    }
    public Map<ProductionRuleSide, List<ProductionRuleSide>> getSameLeftSideMap() {
        Map<ProductionRuleSide, List<ProductionRuleSide>> map =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        for (ProductionRules production : productionRulesType2) {            
            if(map.containsKey(production.getLeftHandSide())) {
                map.get(production.getLeftHandSide()).addAll(production.getRightHandSide().getRules());
            } else {
                List<ProductionRuleSide> rightSide = new ArrayList<ProductionRuleSide>();
                rightSide.addAll(production.getRightHandSide().getRules());
                map.put(production.getLeftHandSide(), rightSide);
            }
        }
        return map;
    }
   
    public boolean hasLeftRecursion(){

        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();

        rules.putAll(getSameLeftSideMap());
        List<ProductionRuleSide> listOfNonterms = new ArrayList<ProductionRuleSide>();
        
        //potřebujeme libovolne usporadani - pouzijeme dané, jen musíme prenést 
        //pravidla z mapy do pole, ve kterém můžeme prohledávat jednotlivé bunky
        listOfNonterms.addAll(rules.keySet());
        for(int i = 0; i < listOfNonterms.size(); i++){
            List<ProductionRuleSide> allRules = new ArrayList<ProductionRuleSide>();
            List<ProductionRuleSide> newListOfRules = 
                                        new ArrayList<ProductionRuleSide>();
            //máme kvůli přidávání nových pravidel
            //potrebujeme pravidlo A_i pro test
            ProductionRuleSide leftSide = new ProductionRuleSide();
            leftSide = listOfNonterms.get(i);

            for(int j = 0; j < i; j++){
                newListOfRules = new ArrayList<ProductionRuleSide>();
                ProductionRuleSide nonTermForTest = listOfNonterms.get(j);
                
                Symbol nonTermForTestSymb = nonTermForTest.getSymbols().get(0);
                
                System.out.println("Kontrolované pravidlo = " 
                        +nonTermForTestSymb.toString()+"j = "+j);
                
                List<ProductionRuleSide> rulesOfLeftSide = 
                                        new ArrayList<ProductionRuleSide>();
                
                List<ProductionRuleSide> rulesOfnonTermForTest = 
                                        new ArrayList<ProductionRuleSide>();
                
                rulesOfLeftSide.addAll(rules.get(leftSide));
                rulesOfLeftSide.addAll(allRules);
                rulesOfnonTermForTest.addAll(rules.get(nonTermForTest));

                //testujeme jednotlivá pravidla
                for(ProductionRuleSide oneRule : rulesOfLeftSide){
                    List<Symbol> listRule = new ArrayList<Symbol>();
                    listRule.addAll(oneRule.getSymbols());
                    //pokud se symbol na první pozici pravidla rovná 
                    //neterminalu na pozici j, tak vytvorime nova pravidla
                    if(nonTermForTestSymb.equals(listRule.get(0))){
                        
                        System.out.println("Testované pravidlo je " +listRule.toString());

                        List<Symbol> leftListRule = new ArrayList<Symbol>();
                        leftListRule.addAll(listRule);
                        //potřebujeme \alfa část pravidla - první symbol vytváří rekurzi
                        leftListRule.remove(0);
                        //System.out.println("pravidlo pro spojení je zprava je"+leftListRule.toString());
                        for(ProductionRuleSide rule : rulesOfnonTermForTest){
                            ProductionRuleSide newRuleS = new ProductionRuleSide();
                            newRuleS.setSymbolsFromProductionRuleSide(rule);
                            if(!newRuleS.equals(oneRule)){
                                //System.out.println("pravidlo pro spojení je "+newRule.toString());
                                newRuleS.addSymbolsFromList(leftListRule);
                                newListOfRules.add(newRuleS);
                                if(allRules.contains(oneRule)) allRules.remove(oneRule);
                            }
                        }
                    }else{
                        newListOfRules.add(oneRule);
                        continue;
                    } 
                    
                }
                allRules.addAll(newListOfRules);
                System.out.println(" pravidla jsou "+ allRules.toString());
            }
            //pokud symbol má nějakou rekurzi tak se to projeví tady
            //potřebujeme symbol na porovnávání s prvním pravidlem 
            Symbol leftSideSymb = leftSide.getSymbols().get(0);
            for(ProductionRuleSide oneRule : allRules){
                List<Symbol> listRule = new ArrayList<Symbol>();
                listRule.addAll(oneRule.getSymbols());
                //máme levou rekurzi
                System.out.println("Pravidlo je "+listRule.toString()+" symbol je "+leftSideSymb.toString());
                if(listRule.get(0).equals(leftSideSymb)){
                    return true;
                }else{
                    
                }
            }
        }
        return false;
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
                "\n\n" + writeProductions();
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
            if(terminal.isEpsilon()) {
                sb.append(MathConstants.EPSILON).append(", ");
            }
            else{
                sb.append(terminal.getName()).append(", ");
            }
        }
        if(!getNonterminals().isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();        
    }


    public String writeShortProductions() {

        if(productionRulesType2.isEmpty()) {
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
    

    public String writeProductions() {
        if(productionRulesType2.isEmpty()) {
            return "P=" + MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder("P={\n");
        for (ProductionRules productionRule : productionRulesType2) { 
           
            sb.append(productionRule.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public void sortProductionRules() {
        Collections.sort(productionRulesType2, new ProductionRulesAscendingComparator());
    }

     public void sortProductionRulesDecs() {
        sortProductionRules();
        Collections.sort(productionRulesType2, new ProductionRulesDescendingComparator());
    }

    @Override
    public Representation clone() {
        Grammar g = new Grammar();
        g.setName(getName());
        if(hasStartNonterminal()) {
            g.setStartNonterminal((Symbol) getStartNonterminal().clone());
        }

        for (ProductionRules productionRule : productionRulesType2) {
            g.addRule((ProductionRules) productionRule.clone());
        }
        return g;
    }


    public boolean isCorrect() {
        if(!hasStartNonterminal()) {
            //System.out.println("nema startovní neterminal");
            return false;
        }
        if(getNonterminals().isEmpty()) {
            //System.out.println("nema žádne neterminály");
            return false;
        }
        for (ProductionRules productionRule : productionRulesType2) {
            boolean containsNonterminal = false;
            for (Symbol symbol : productionRule.getLeftHandSide().getSymbols()) {
                if(symbol.isNonterminal()) {
                    containsNonterminal = true;
                }
            }
            if(!containsNonterminal) {
                //System.out.println("Levá strana není neterminál. pravidlo = "+productionRule.toString());
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
    
    
    public boolean isContextFreeE(){
        return (getType() >= TYPE2);
    }   
    
    public int getType() {
        if(!isCorrect()) {
            return INCORRECT;
        }
        boolean regular = true;
        boolean cfg = true;
        boolean csg = true;
        for (ProductionRules rule : productionRulesType2) {
            int leftSize = rule.getLeftHandSide().size();
            
            //boolean singleLeftNonterminal = false;
            if(leftSize > 1 || !rule.getLeftHandSide().getSymbols().get(0).isNonterminal()) {
                regular = false;
                cfg = false;
            }
            ProductionRulesSide rightHandSide = rule.getRightHandSide();
            for(ProductionRuleSide oneRule : rightHandSide.getRules()){
                int rightSize = oneRule.size();
                if(rightSize == 0){
                    regular = false;
//                    cfg = false;
                }
                if(rightSize == 1 && oneRule.getSymbols().get(0).isEpsilon()) {
                    if(leftSize > 1 || !isStartNonterminal(rule.getLeftHandSide().getSymbols().get(0))) {
                        regular = false;
                        //cfg = false;
                        csg = false;
                    } else {
                        continue;
                    }
                }
            
                
                boolean rightRegular = false;
                if((rightSize == 1 && oneRule.getSymbols().get(0).isTerminal()) ||
                        (rightSize == 2 && oneRule.getSymbols().get(0).isTerminal() &&
                        oneRule.getSymbols().get(1).isNonterminal())) {
                    rightRegular = true;
                }
                if(!rightRegular) {
                    regular = false;
                }

                if(leftSize > rightSize) {
                    csg = false;
                }
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
    
    /*
    
    public boolean isRegular() {
        return getType() == TYPE3;
    }

    public boolean isContextFree() {
        return getType() == TYPE2 || getType() ==TYPE3;
    }

    public boolean isContextSensitive() {
        return (getType() == TYPE1 || getType()== TYPE2 || getType() == TYPE3);
    }

    public boolean isRecursivelyEnumerable() {
        return getType() >= TYPE0;
    }
    
    public boolean isContextFreeE(){
    return (getType() >= TYPE2);
    }
    
    public boolean isRegularE(){
    return (getType() >= TYPE3 );
    }
    
    //TODO
    public int getType() {
        if(!isCorrect()) {
            return INCORRECT;
        }
        boolean regular = true;
        boolean cfg = true;
        boolean csg = true;
        boolean regularE = true;
        boolean cfgE=true;
        
        for (ProductionRules rule : productionRulesType2) {
            boolean eRule=false;
            int leftSize = rule.getLeftHandSide().size();
            int rightSize = rule.getRightHandSide().size();
            //boolean singleLeftNonterminal = false;
            if(leftSize > 1 || !rule.getLeftHandSide().getSymbols().get(0).isNonterminal()) {
                regular = false;
                cfg = false;
                regularE=false;
                cfgE=false;
            }
            
            if(rightSize == 1 && rule.getRightHandSide().getSymbols().get(0).isEpsilon()) {
                if(leftSize > 1 || !isStartNonterminal(rule.getLeftHandSide().getSymbols().get(0))) {
                    regular = false;
                    cfg = false;
                    csg = false;
                } else {
                    
                }
                eRule=true;
            }
            
            boolean rightRegularOrE = false;
            if((rightSize == 1 && rule.getRightHandSide().getSymbols().get(0).isTerminal()) || eRule ||
                    (rightSize == 2 && rule.getRightHandSide().getSymbols().get(0).isTerminal() &&
                    rule.getRightHandSide().getSymbols().get(1).isNonterminal())) {
                rightRegularOrE = true;
                
            }
            if(!rightRegularOrE) {
                regular = false;
                regularE= false;
            }
            
            if(leftSize > rightSize) {
                csg = false;                
            }
        }      
        if(regular) {
            return TYPE3;
        }
        
        if (regularE) {
           return TYPE3E;
        }
        if(cfg) {
            return TYPE2;
        }
        if (cfgE){ 
            return TYPE2E;
                    }
        if(csg) {
            return TYPE1;
        }
        
        
        return TYPE0;
    }*/


    public boolean isStartToEpsRule(ProductionRuleSide leftHandSide, ProductionRuleSide rule) {
        if(!hasStartNonterminal()) {
            return false;
        }
        if(rule.size() > 1){
            System.out.println("Tohle nemůže být");
            return false;
        }
        if (leftHandSide.size() == 1
                && isStartNonterminal(leftHandSide.getSymbols().get(0))
                && rule.getSymbols().get(0).isEpsilon()) {
            return true;
        }
        return false;
    }

    public ProductionRules getStartToEpsRule() {
        for (ProductionRules productionRule : productionRulesType2) {
            ProductionRuleSide leftHandSide = productionRule.getLeftHandSide();
            ProductionRulesSide rightHandSide = productionRule.getRightHandSide();
            for(ProductionRuleSide oneRule : rightHandSide.getRules()){
                if(isStartToEpsRule(leftHandSide, oneRule)) {
                    return productionRule;
                }
            }
        }
        return null;
    }
    
    public boolean containsStartToEpsRule() {
        return getStartToEpsRule() != null;
    }


    public void clearHighlighting() {
        for (ProductionRules productionRule : productionRulesType2) {
            productionRule.setBgColor(Color.WHITE);
            productionRule.setFgColor(Color.BLACK);
        }
        
    }
    public void setFgColorToProd(Color color, ProductionRules rule) {
        for (ProductionRules productionRule : productionRulesType2) {
            if(productionRule.equals(rule)){
                productionRule.setFgColor(Color.RED);
            }
        } 
    }

    public boolean hasEpsProductions(){
        if(containsStartToEpsRule()){
            for(ProductionRules oneLine : productionRulesType2){
                if(oneLine.getRightHandSide().containsRuleWithSymbol(startNonterminal)){
                    return true;
                }
            }
        }
        for(ProductionRules oneLine : productionRulesType2){
            ProductionRuleSide start = new ProductionRuleSide();
            start.addSymbol(startNonterminal);
            if(oneLine.getRightHandSide().hasEpsProduction() && 
                    !oneLine.getLeftHandSide().equals(start)) 
                return true;
        }
        return false;
    }
    
    public boolean hasUnitProduction(){
        for(ProductionRules oneLine : productionRulesType2){
            for(ProductionRuleSide oneRule : oneLine.getRightHandSide().getRules()){
                if(oneRule.size()==1 && oneRule.getSymbols().get(0).isNonterminal()){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * JB
     * @return 
     */
    public boolean isNormalized(){
        for(ProductionRules oneLine : productionRulesType2) {
            if (oneLine.getRightHandSide().size() > 1) {
                System.out.println("nie je normalizovana");
                return false;
            }
        }
        System.out.println("JE normalizovana");
        return true;
    }
    
    /**
     * JB
     */
    public void normalize(){
        if(!isNormalized()){
            List<ProductionRules> newRules = new ArrayList<>();
            List<Integer> rulesToDelete = new ArrayList<>();
        
            for(ProductionRules oneLine : productionRulesType2) {
                if (oneLine.getRightHandSide().size() > 1) {
                    System.out.println("normalizuje sa" + productionRulesType2.indexOf(oneLine) + ". pravidlo");
                    
                    rulesToDelete.add(productionRulesType2.indexOf(oneLine));
                                       
                    ProductionRulesSide oneSet = new ProductionRulesSide(oneLine.getRightHandSide().getRules());
                    
                    for (ProductionRuleSide side: oneSet.getRules()) {
                        ProductionRulesSide addedRightSide = new ProductionRulesSide(side);
                        ProductionRuleSide addedLeftSide = new ProductionRuleSide(oneLine.getLeftHandSide());
                        newRules.add(new ProductionRules(addedLeftSide, addedRightSide));
                    }
                }
            }
            
            for(int i = productionRulesType2.size() - 1; i > -1; i--) {
                if(rulesToDelete.contains(i)) {
                    productionRulesType2.remove(i);
                }
            }
            
            for (ProductionRules newRule : newRules) {
                productionRulesType2.add(newRule);
            }
        }
    }
}