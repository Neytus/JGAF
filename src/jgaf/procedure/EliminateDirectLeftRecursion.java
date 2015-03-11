package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


public class EliminateDirectLeftRecursion extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private Symbol nonterminal;
    private String parameter2;
    private Integer typeI;
    private FAalgorithms helpAlgs = new FAalgorithms();

    public EliminateDirectLeftRecursion() {
    }


 public void startProcedure() {
        int type = (int) typeI;
        grammar2.clearHighlighting();
        grammar1.clearHighlighting();
        logState("start");

        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules = grammar1.getSameLeftSideMap();
        
        if(!nonterminal.getName().equals("def")){
            logState("Setted nonterminal is "+nonterminal.getName());
        }
        logState("Production rules type is "+typeI);
        for(Map.Entry<ProductionRuleSide, List<ProductionRuleSide>> mapEntry : rules.entrySet()){
            ProductionRuleSide leftSideNonterm = new ProductionRuleSide();
            SortedSet<ProductionRuleSide> recursiveRules = 
                new TreeSet<ProductionRuleSide>();
            SortedSet<ProductionRuleSide> anotherRules = 
                new TreeSet<ProductionRuleSide>();
            if(!nonterminal.getName().equals("def")){
                leftSideNonterm.addSymbol(nonterminal);
            }else{
                leftSideNonterm = mapEntry.getKey();
            }
            
        //potřebujeme symbol na porovnávání s prvním pravidlem 
            Symbol leftSideSymb = leftSideNonterm.getSymbols().get(0);
            ProductionRules unit = new ProductionRules();
            boolean unitCheck = false;
        //procházím jednotlivá pravidla a rozděluji je na dva typy
             List<ProductionRules> colored = 
                                grammar1.getProductionsOfGivenNonterminal(leftSideNonterm);
             List<ProductionRuleSide> testedRules = rules.get(leftSideNonterm);

            for(ProductionRuleSide oneRule : testedRules){
                List<Symbol> listRule = new ArrayList<Symbol>();
                listRule.addAll(oneRule.getSymbols());
                
                for(ProductionRules ruleToColor : colored){
                    if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                        ruleToColor.setFgColor(Color.RED);
                    }
                }
                
                logState("Tested rule "+leftSideSymb.getName()+" -> "+oneRule.toString());
                ProductionRuleSide ruleWithoutRecursion = new ProductionRuleSide();
                //máme levou rekurzi
                if(listRule.get(0).equals(leftSideSymb)){
                    if(oneRule.size()!=1){
                        logState("Production has direct left recursion");
                        ruleWithoutRecursion.setSymbolsFromProductionRuleSide(oneRule);
                        ruleWithoutRecursion.removeSymbolOnPosition(0);
                        recursiveRules.add(ruleWithoutRecursion);
                    }else{
                        unit = 
                            new ProductionRules(leftSideNonterm, new ProductionRulesSide(oneRule));
                        unitCheck = true;
                        logState("Production has direct left recursion and is Unit");
                    }
                }else{
                    logState("Production without direct left recursion");
                    anotherRules.add(oneRule);
                }
                grammar1.clearHighlighting();
            }

            //teď budeme přidávat pravidla - nejdříve vezmeme ty, u kterých zůstane 
            //na levé straně původní neterminál
            Symbol newLeftSideSymb = new Symbol(leftSideSymb.getName()+"'",1);
            ProductionRuleSide newLeftNonterm = new ProductionRuleSide();
            newLeftNonterm.addSymbol(newLeftSideSymb);

            //pomocné pravidlo pro změny v pravidlech
            ProductionRuleSide newRuleS = new ProductionRuleSide();
            if(!recursiveRules.isEmpty()){
                if(type == 1){
                    //při průchodu můžu bez problému pravidla vkládat přímo do gramatiky
                    //ještě uvidím, co pro mě pak bude lepší
                    logState("Browsing rules with direct left recursion and"
                                    +" adding new rules (viz Lemma 5.1(**))");
                    //teď pridame vsechna ostatni pravidla
                    for(ProductionRuleSide oneRule : recursiveRules){
                        logState("Deleting left recursion for rule "
                                +leftSideNonterm.toString()+" -> "+leftSideNonterm.toString()+oneRule.toString());
                        List<ProductionRuleSide> newRulesListRecursiveRules = 
                            new ArrayList<ProductionRuleSide>();
                        newRulesListRecursiveRules.add(oneRule);
                        newRuleS = new ProductionRuleSide();
                        newRuleS.setSymbolsFromProductionRuleSide(oneRule);
                        //upravíme pravidlo a přidáme na jeho konec nový neterminál A'


                        newRuleS.addSymbol(newLeftSideSymb);
                        newRulesListRecursiveRules.add(newRuleS);
                        ProductionRuleSide newL = new ProductionRuleSide(newLeftNonterm);
                        ProductionRules newRule = 
                            new ProductionRules(newL, 
                                new ProductionRulesSide(newRulesListRecursiveRules));
                        newRule.setFgColor(Color.RED);
                        grammar2.addRule(newRule);
                        logState("Added rules "+newRule.toString());
                        grammar2.clearHighlighting();
                    }
                    //přidáme pravidla do nové mapy
                    
                    
                    logState("Browsing rules without direct left recursion and"
                            +" adding new rules (viz Lemma 5.1 (**))");
                    for(ProductionRuleSide oneRule : anotherRules){
                        List<ProductionRuleSide> newRulesListAnotherRules = 
                            new ArrayList<ProductionRuleSide>();
                        newRulesListAnotherRules.add(oneRule);
                        newRuleS = new ProductionRuleSide();
                        newRuleS.setSymbolsFromProductionRuleSide(oneRule);
                        //upravíme pravidlo a přidáme na jeho konec nový neterminál A'
                        //epsilon pravidlo může byt jen v teto mnozine (k eps nic nepřidavam)
                        if(!oneRule.isEpsilon()){                         
                            newRuleS.addSymbol(newLeftSideSymb);
                            newRulesListAnotherRules.add(newRuleS);
                        }
                        ProductionRuleSide newL = new ProductionRuleSide(leftSideNonterm);
                        ProductionRules newRule = 
                               new ProductionRules(newL, 
                                new ProductionRulesSide(newRulesListAnotherRules));
                        newRule.setFgColor(Color.RED);
                        grammar2.addRule(newRule);
                        logState("Added rules "+newRule.toString());
                        grammar2.clearHighlighting();
                    }
                    //přidáme pravidla do nové gramatiky
                    
                    
                    
                }else if(type == 2){
                    logState("Browsing rules without direct left recursion and"
                            +" adding new rules (viz Lemma 5.1 (***))");
                    for(ProductionRuleSide oneRule : recursiveRules){
                        logState("Deleting left recursion for rule "
                                +leftSideNonterm.toString()+" -> "+leftSideNonterm.toString()+oneRule.toString());
                        List<ProductionRuleSide> newRulesListRecursiveRules = 
                            new ArrayList<ProductionRuleSide>();
                        newRuleS = new ProductionRuleSide();
                        newRuleS.setSymbolsFromProductionRuleSide(oneRule);
                        newRuleS.addSymbol(newLeftSideSymb);
                        newRulesListRecursiveRules.add(newRuleS);
                        
                        ProductionRuleSide newL = new ProductionRuleSide(newLeftNonterm);
                        ProductionRules newRule = 
                            new ProductionRules(newL, 
                                new ProductionRulesSide(newRulesListRecursiveRules));
                        newRule.setFgColor(Color.RED);
                        grammar2.addRule(newRule);
                        logState("Added rules "+newRule.toString());
                        grammar2.clearHighlighting();
                    }
                    //musíme přidat eps pravidlo
                    ProductionRuleSide eps = new ProductionRuleSide();
                    eps.addEpsilon("eps");
//                    newRulesListRecursiveRules.add(eps);
                    //přidáme pravidla do nové mapy
                    ProductionRuleSide newL = new ProductionRuleSide(newLeftNonterm);
                    ProductionRules newRule = 
                            new ProductionRules(newL, new ProductionRulesSide(eps));
                    newRule.setFgColor(Color.RED);
                    grammar2.addRule(newRule);
                    logState("Added rules "+newRule.toString());
                    grammar2.clearHighlighting();
                    
                    logState("Browsing rules without direct left recursion and"
                            +" adding new rules (viz Lemma 5.1 (***))");
                    for(ProductionRuleSide oneRule : anotherRules){
                        List<ProductionRuleSide> newRulesListAnotherRules = 
                            new ArrayList<ProductionRuleSide>();
                        newRuleS = new ProductionRuleSide();
                        newRuleS.setSymbolsFromProductionRuleSide(oneRule);
                        //epsilon pravidlo může byt jen v teto mnozine
                        if(!oneRule.isEpsilon()){
                            newRuleS.addSymbol(newLeftSideSymb); 
                        }
                        newRulesListAnotherRules.add(newRuleS);
                        
                        newL = new ProductionRuleSide(leftSideNonterm);
                        newRule = 
                            new ProductionRules(newL, new ProductionRulesSide(newRulesListAnotherRules));
                        newRule.setFgColor(Color.RED);
                        grammar2.addRule(newRule);
                        logState("Added rules "+newRule.toString());
                        grammar2.clearHighlighting();
                    } 
                }
            }else{
                //nejsou žádná rekurzivní pravidla - přidám původní
                if(!nonterminal.getName().equals("def")){
                    if(unitCheck){
                        logState("Nonterminal "+nonterminal.getName()
                                +" has direct left recursion only in unit rule");
                        logState("Add rules without "+unit.toString());
                        
                    }else{
                        logState("Nonterminal "+nonterminal.getName()
                                +" doesn't have direct left recursion");
                    }
                    for(ProductionRuleSide oneRule : anotherRules){
                        ProductionRuleSide newL = new ProductionRuleSide(leftSideNonterm);
                        ProductionRules newRule = 
                                new ProductionRules(newL, 
                                        new ProductionRulesSide(oneRule));
                        //newRule.setFgColor(Color.RED);
                        
                        grammar2.addRule(newRule);
                        grammar2.setFgColorToProd(Color.RED, newRule);
                        
                        logState("Add rules "+newRule.toString());

                        grammar2.clearHighlighting();
                    }
                }else{
                    if(unitCheck){
                        logState("Nonterminal "+leftSideNonterm
                                +" has direct left recursion only in unit rule");
                        
                        logState("Rules are same without "+unit.toString());
                        for(ProductionRuleSide oneRule : anotherRules){
                            ProductionRuleSide newL = new ProductionRuleSide(leftSideNonterm);
                            ProductionRules newRule = 
                                new ProductionRules(newL, 
                                    new ProductionRulesSide(oneRule));
                            grammar2.addRule(newRule);
                        }
                    }else{
                        logState("Grammar doesn't have direct left recursion");
                        logState("Rules are same");
                        for(ProductionRuleSide oneRule : anotherRules){
                            ProductionRuleSide newL = new ProductionRuleSide(leftSideNonterm);
                            ProductionRules newRule = 
                                new ProductionRules(newL, new ProductionRulesSide(oneRule));
                            grammar2.addRule(newRule);
                        }
                    }
                }
                grammar2.setStartNonterminal(grammar1.getStartNonterminal());
            }
            if(!nonterminal.getName().equals("def")){
                break;
            }
        }
        logState("\ndone");
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
        if(helpAlgs.isLanguageNotEmpty(grammar1) == null){
            return "Language created by this grammar is empty";
        }
        if(helpAlgs.eliminateUselessSymbols(grammar1) != null){
            return "Grammar has Useless productions.";
        }
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = 
                                                grammar1.getSameLeftSideMap();
        List<Symbol> list = new ArrayList<Symbol>();
        boolean check = true;
        try{
            typeI = Integer.parseInt(parameter2);
        }catch(NumberFormatException ex){
            return "Parameter 2 is not integer";
        }
        if(typeI != 1){
            typeI = 2;
        }
        if(nonterminal.getName().equals("def") ) return CHECK_OK;
        list.add(nonterminal);
        ProductionRuleSide leftHandSide = new ProductionRuleSide(list);
        if(!map.keySet().contains(leftHandSide)){
            return "Parameter 1 is not def and or nonterminal is "
                    +"not on left side of any rule";
        }
        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        if(inputParameters[0] != null){
            String parameter = inputParameters[0].trim();
            nonterminal = new Symbol(parameter, 1);
        }
        if(inputParameters[1] != null){
            parameter2 = inputParameters[1].trim();
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
