package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Constants.MathConstants;
import jgaf.Representation;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;
import jgaf.grammar.algorithms.FAalgorithms;

public class EliminateUnitRules extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private Symbol nonterminal;

    public EliminateUnitRules() {
    }


 public void startProcedure() {
        grammar2.clearHighlighting();
        grammar1.clearHighlighting();
        logState("start");
        FAalgorithms helpAlgs = new FAalgorithms();

        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules.putAll(grammar1.getSameLeftSideMap());
        SortedSet<Symbol> nonTerminals = new TreeSet<Symbol>();
        nonTerminals = grammar1.getNonterminals();
        
        
        for(Symbol oneNonterm : nonTerminals){
            
            ProductionRuleSide oneNontermSide = new ProductionRuleSide();
            
            Set<Symbol> set_previous = new HashSet<Symbol>();
            Set<Symbol> set_actual = new HashSet<Symbol>();
            if(!nonterminal.getName().equals("def")){
                logState("Setted nonterminal is "+nonterminal.getName());
                oneNonterm = nonterminal;
                
            }
                oneNontermSide.addSymbol(oneNonterm);
            set_actual.add(oneNonterm);
            
            logState("Testing rules for nonterminal "+oneNonterm.toString());
            logState("N_0 = "+set_actual.toString());
            int i=0;
            do{
                i++;
                //naplnime mnozinu mnozinou spoctenou v predchozi iteraci
                set_previous.addAll(set_actual);
                //transitivni uzaver
                logState("\niteration i = "+i);
                for (Symbol symb : set_previous) {
                    List<ProductionRuleSide> setOfRules =
                        new ArrayList<ProductionRuleSide>();
                    ProductionRuleSide helpLeftNonTerminal = 
                            new ProductionRuleSide();
                    helpLeftNonTerminal.addSymbol(symb);
                    List<ProductionRuleSide> helpSetOfRules = 
                            rules.get(helpLeftNonTerminal);
                    
                    List<ProductionRules> colored = 
                                grammar1.getProductionsOfGivenNonterminal(helpLeftNonTerminal);
                    //zkontrolujeme zda neni mnozina pravidel pro dany neterminal prazdna
                    if(helpSetOfRules != null){
                        setOfRules.addAll(helpSetOfRules);   
                        //kontrolujeme pravidla a hledame jednoduche pravidlo
                        for (ProductionRuleSide oneRule : setOfRules) {
                            
                            for(ProductionRules ruleToColor : colored){
                                if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                                    ruleToColor.setFgColor(Color.RED);
                                }
                            }
                            logState("Testing rule "+helpLeftNonTerminal.toString()
                                    +" -> "+oneRule.toString());
                            Symbol probabAddSymb = oneRule.getSymbols().get(0);
                            if(oneRule.size() == 1 && probabAddSymb.isNonterminal()) {
                                
//                                System.out.println("pridava se symbol = "+symb.toString());
                                set_actual.add(probabAddSymb);  
                                logState("Rule is unit. Add to N_"
                                        +i+" = "+set_actual.toString());
                            }else {
                                logState("Rule's not unit. Don't add.");
                            }
                            grammar1.clearHighlighting();
                        }
                    }else{
                        continue;
                    }
                    
                }
                
            }while (!set_previous.equals(set_actual));
            logState("N_"+i+" = N_"+(i-1)+" = "+set_actual.toString());
            logState("N_"+oneNonterm.getName()+" = N_"+i+" = "+set_actual.toString());
            
            logState("\nCreating new rules for "+oneNonterm.getName());
            //napocitavame novou mnozinu pravidel ze set_actual pro puvodni
            //neterminal ozn. oneNonterm (oneNontermSide)
            //System.out.println(set_actual.toString());
            for (Symbol s : set_actual) {
                    
                    ProductionRuleSide oneSymb = new ProductionRuleSide();
                    oneSymb.addSymbol(s);
                    List<ProductionRuleSide> helpRules = rules.get(oneSymb);
                    if(helpRules != null){
                        for(ProductionRuleSide oneOfRules : helpRules){
                            if(oneOfRules.size() != 1 ||
                                        !oneOfRules.getSymbols().get(0).isNonterminal()) {
                                ProductionRuleSide newL = new ProductionRuleSide(oneNontermSide);
                                ProductionRules newRule = 
                                        new ProductionRules(newL, 
                                        new ProductionRulesSide(oneOfRules));
                                newRule.setFgColor(Color.RED);
                                grammar2.addRule(newRule);
                                logState("Add rule "+newRule.toString());
                            }
                            grammar2.clearHighlighting();
                        }
                    }else{
                        continue;
                    }
            }
            if(!nonterminal.getName().equals("def")) break;
        }
        if(!nonterminal.getName().equals("def")){
            grammar2.setStartNonterminal(nonterminal);
        }else{
            grammar2.setStartNonterminal(grammar1.getStartNonterminal());
        }

        logState("done");
    }
 
    @Override
    public String checkInputRepresentation() {
        if(!grammar1.isCorrect()){
           return "Grammar is not correct."; 
        }
        if(!grammar1.isContextFree()) {
            return "Grammar is not context free.";
        }
        if(grammar1.hasEpsProductions()){
            return "Grammar contains "+MathConstants.EPSILON+"-production";
        }
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = 
                                                grammar1.getSameLeftSideMap();
        List<Symbol> list = new ArrayList<Symbol>();
        if(nonterminal.getName().equals("def")) return CHECK_OK;
        list.add(nonterminal);
        ProductionRuleSide leftHandSide = new ProductionRuleSide(list);
        if(map.keySet().contains(leftHandSide)){
            return CHECK_OK;
        } else {
            return "Parameter is not def and or nonterminal is not on left side of any rule";
        }
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        if(inputParameters[0] != null){
            String parameter = inputParameters[0].trim();
            nonterminal = new Symbol(parameter, 1);
        }
    }
    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
       grammar2 = (Grammar) outputRepresentation;
    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        grammar1 = (Grammar) inputRepresentations[0];
    }
}
