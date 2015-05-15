package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jgaf.Constants.MathConstants;
import jgaf.Representation;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;
import jgaf.grammar.algorithms.FAalgorithms;

public class TransformToGNF extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private String leftSymbol;
    private String rightSymbol;
    private Symbol nonterminal;
    private List<ProductionRuleSide> ordering;
    FAalgorithms helpAlgs = new FAalgorithms();
    

    public TransformToGNF() {
    }


 public void startProcedure() {
        grammar2.clearHighlighting();
        grammar1.clearHighlighting();
        logState("start");

        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<>();

        rules.putAll(grammar1.getSameLeftSideMap());

        List<ProductionRuleSide> listOfNonterms = new ArrayList<>();
        
        //potřebujeme libovolne usporadani - pouzijeme dané, jen musíme prenést 
        //pravidla z mapy do pole, ve kterém můžeme prohledávat jednotlivé bunky
        if(ordering.get(0).getSymbols().get(0).getName().equals("def")){
            listOfNonterms.addAll(helpAlgs.orderingForGNF(rules));
        }else{
            listOfNonterms.addAll(ordering);
        }
        
        if(!nonterminal.getName().equals("def")){
            logState("Setted nonterminal is "+nonterminal.getName());
        }
        
        logState("Ordering is "+listOfNonterms.toString());
        Grammar helpGramm = new Grammar();
        for(int i=listOfNonterms.size()-1; i>=0; i--){
            helpGramm = (Grammar) grammar2.clone();
        
            List<ProductionRuleSide> allRules = new ArrayList<>();
            
            //máme kvůli přidávání nových pravidel
            List<ProductionRuleSide> newListOfRules = new ArrayList<>();
            //potrebujeme pravidlo A_i pro test
            ProductionRuleSide leftSide = listOfNonterms.get(i);
            int ind = i;
            if(!nonterminal.getName().equals("def")){
                leftSide.clear();
                leftSide.addSymbol(nonterminal);
                ind = listOfNonterms.indexOf(leftSide);
            }
            logState("A_i = "+leftSide.toString());
            List<ProductionRuleSide> rulesToRemove = new ArrayList<>();
            
            if(ind == listOfNonterms.size()-1){
                newListOfRules.addAll(rules.get(leftSide));
                allRules.addAll(newListOfRules);
                logState("Nonterminal is the highest. First part of alg. doesn't run");
            }else{
                logState("Eliminate all rules with nonterminal on first position");

            }
            for(int j=ind+1; j<listOfNonterms.size();j++){               
                //potrebujeme pravidlo A_j pro test
                ProductionRuleSide nonTermForTest = listOfNonterms.get(j);
                logState("A_j = "+nonTermForTest.toString());
                
                Symbol nonTermForTestSymb = nonTermForTest.getSymbols().get(0);
                
                
                List<ProductionRuleSide> rulesOfLeftSide = new ArrayList<>();
                
                List<ProductionRuleSide> rulesOfnonTermForTest = new ArrayList<>();
                
                rulesOfLeftSide.addAll(rules.get(leftSide));
                rulesOfLeftSide.addAll(allRules);                
                rulesOfLeftSide.removeAll(rulesToRemove);
                
                rulesOfnonTermForTest.addAll(rules.get(nonTermForTest));
                
                rulesToRemove = new ArrayList<ProductionRuleSide>();
                //testujeme jednotlivá pravidla
                List<ProductionRules> colored = 
                                grammar1.getProductionsOfGivenNonterminal(leftSide);
                for(ProductionRuleSide oneRule : rulesOfLeftSide){
                    grammar1.clearHighlighting();
                    List<Symbol> listRule = new ArrayList<Symbol>();
                    listRule.addAll(oneRule.getSymbols());
                    logState("Testing rule is "+leftSide.toString()+" -> "+oneRule.toString());
                    //pokud se symbol na první pozici pravidla rovná 
                    //neterminalu na pozici j, tak vytvorime nova pravidla
                    for(ProductionRules ruleToColor : colored){
                        if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                            ruleToColor.setFgColor(Color.RED);
                        }
                    }
                    if(nonTermForTestSymb.equals(listRule.get(0))){
                        logState("Rule has form A_i -> A_j"+MathConstants.ALPHA);
                        List<Symbol> leftListRule = new ArrayList<>();
                        leftListRule.addAll(listRule);
                        //potřebujeme \alfa část pravidla - první symbol vytváří rekurzi
                        leftListRule.remove(0);
                        for(ProductionRuleSide rule : rulesOfnonTermForTest){
                            ProductionRuleSide newRuleS = new ProductionRuleSide();
                            newRuleS.setSymbolsFromProductionRuleSide(rule);
                            if(!newRuleS.equals(oneRule)){
                                newRuleS.addSymbolsFromList(leftListRule);
                                ProductionRuleSide newL = new ProductionRuleSide(leftSide);
                                ProductionRules newRule = 
                                            new ProductionRules(newL, new ProductionRulesSide(newRuleS));
//                                
                                grammar2.addRule(newRule);
                                grammar2.setFgColorToProd(Color.RED, newRule);
                                logState("Add rule "+newRule.toString());
                                newListOfRules.add(newRuleS);
                                if(allRules.contains(oneRule)) allRules.remove(oneRule);
                                rulesToRemove.add(oneRule);
                            }
                            grammar2.clearHighlighting();
                        }
                    }else{
                        logState("Rule hasn't form A_i -> A_j"+MathConstants.ALPHA);
                        newListOfRules.add(oneRule);
                        continue;
                    } 
                }
                 allRules.addAll(newListOfRules);    
            }
               
            logState("Second partt - adapt rules with terminals "+MathConstants.ALPHA);
            List<ProductionRules> colored = 
                                grammar1.getProductionsOfGivenNonterminal(leftSide);
            List<ProductionRules> colored2 = 
                                grammar2.getProductionsOfGivenNonterminal(leftSide);
            
            
            
            grammar2.removeAllRules();
            if(!nonterminal.getName().equals("def") && leftSide.getSymbols().get(0).equals(nonterminal)){
                leftSide.clear();
                leftSide.addSymbol(nonterminal);
            }else{
                grammar2.addAllRules(helpGramm);
            }
            
            for(ProductionRuleSide oneRule : allRules){
                Set<Symbol> newNfromTerm = new HashSet<>();
                List<Symbol> listRule = new ArrayList<>();
                listRule.addAll(oneRule.getSymbols());
                
                for(ProductionRules ruleToColor : colored){
                    if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                        ruleToColor.setFgColor(Color.RED);
                    }
                }
                for(ProductionRules ruleToColor : colored2){
                    if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                        ruleToColor.setFgColor(Color.RED);
                    }
                }
                
                logState("Testing rule "+leftSide.toString()+" -> "+oneRule.toString());
                List<Symbol> newRuleList = new ArrayList<Symbol>();
                //máme levou rekurzi
                int index = 0;
                for(Symbol testedSymbol : listRule){
                    if(testedSymbol.isTerminal() && index!=0){
                        Symbol newNonterm = new Symbol(leftSymbol+testedSymbol.getName()+rightSymbol,1);
                        newRuleList.add(newNonterm);
                        newNfromTerm.add(testedSymbol);
                    }else{
                        newRuleList.add(testedSymbol);
                    }
                    index++;
                }
                ProductionRuleSide newRuleS = new ProductionRuleSide();
                newRuleS.addSymbolsFromList(newRuleList);
                ProductionRuleSide newL = new ProductionRuleSide(leftSide);
                ProductionRules newRule = 
                                          new ProductionRules(newL, new ProductionRulesSide(newRuleS));
                              
                grammar2.addRule(newRule);
                grammar2.setFgColorToProd(Color.RED, newRule);
                logState("Add rule "+newRule.toString());
                
                grammar2.clearHighlighting();
                if(!newNfromTerm.isEmpty()){
                    logState("Adding new nonterminals from terminals");
                    for(Symbol symbol : newNfromTerm){
                        ProductionRuleSide newLeftSide = new ProductionRuleSide();
                        newLeftSide.addNonterminal(leftSymbol+symbol.getName()+rightSymbol);
                        newRuleS = new ProductionRuleSide();
                        newRuleS.addSymbol(symbol);
                        newL = new ProductionRuleSide(newLeftSide);
                        newRule = 
                               new ProductionRules(newL, new ProductionRulesSide(newRuleS));
        //                                
                        grammar2.addRule(newRule);
                        grammar2.setFgColorToProd(Color.RED, newRule);
                        logState("Add rule "+newRule.toString());
                        
                        grammar2.clearHighlighting();
                    }
                }
                grammar1.clearHighlighting();
                grammar2.clearHighlighting();
            }
            
            grammar2.setStartNonterminal(grammar1.getStartNonterminal());
            rules.remove(leftSide);
            List<ProductionRuleSide> helpRules = grammar2.getSameLeftSideMap().get(leftSide);
            rules.put(leftSide, helpRules);
            if(!nonterminal.getName().equals("def")  && leftSide.getSymbols().get(0).equals(nonterminal)){
                break;
            }
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
        if(helpAlgs.isLanguageNotEmpty(grammar1) == null){
            return "Language created by this grammar is empty";
        }
        if(helpAlgs.eliminateUselessSymbols(grammar1) != null){
            return "Grammar has Useless productions.";
        }
        if(grammar1.hasLeftRecursion()){
            return "Grammar has left recursion.";
        }
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = 
                                                grammar1.getSameLeftSideMap();
        
        if(leftSymbol == null || rightSymbol == null){
            return "Wrong creating symbols in parameter 2";
        }
        List<Symbol> list = new ArrayList<>();
        if(ordering.size()==1 && ordering.get(0).getSymbols().get(0).getName().equals("def")){
        }else{
            for(ProductionRuleSide oneSymbol : ordering) {
                if(!map.keySet().contains(oneSymbol)){
                    return "Nonterminal "+oneSymbol.toString()+
                            " in parameter 2 is not at the left side of any rule";
                }
            }
        
            if(ordering.size() != map.keySet().size()){
                return "Not well count of symbols in parameter 2";
            }
            if(!helpAlgs.isWellOrdered(ordering, map)){
                return "Symbols in parameter 2 are not well ordered";
            }
        }
        if(nonterminal.getName().equals("def") ) return CHECK_OK;
        list.add(nonterminal);
        ProductionRuleSide leftHandSide = new ProductionRuleSide(list);
        if(!map.keySet().contains(leftHandSide)){
            return "Nonterminal is not at the left side of any rule";
        }
        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        if ((inputParameters[0] == null || inputParameters[0].equals("")) &&
            (inputParameters[1] == null || inputParameters[1].equals("")) &&
            (inputParameters[2] == null || inputParameters[2].equals(""))) {
            nonterminal = new Symbol("def", 1);
           
            leftSymbol = "<";
            rightSymbol = ">";
            
            ordering = new ArrayList<>();
            ProductionRuleSide newNonterminal = new ProductionRuleSide();
            newNonterminal.addNonterminal("def");
            ordering.add(newNonterminal);
        } else {
        
        ordering = new ArrayList<>();
        if(inputParameters[0] != null){
            String parameter = inputParameters[0].trim();
            nonterminal = new Symbol(parameter, 1);
        }
        if(inputParameters[1] != null){
            String parameter = inputParameters[1].trim();
            if(parameter.equals("def")){
                leftSymbol = "<";
                rightSymbol = ">";
            }else if(parameter.length() == 1){
                System.out.println("symbol je "+parameter);
                leftSymbol = "";
                rightSymbol = parameter.substring(0);
            }else if(parameter.length() == 2){
                leftSymbol = parameter.substring(0, 1);
                rightSymbol = parameter.substring(1);
            }
        }
        if(inputParameters[2] != null){
            String parameter = inputParameters[2].trim();
            String[] parameters = parameter.split(",");
            System.out.println("parametry "+parameters.length);
            for(int i=0; i<parameters.length;i++){
                String symb = parameters[i].trim();
                System.out.println("symb "+symb.toString());
                ProductionRuleSide newNonterminal = new ProductionRuleSide();
                newNonterminal.addNonterminal(symb);
                System.out.println("newNonterm "+newNonterminal.toString());
                
                ordering.add(newNonterminal);
                System.out.println("orderign "+ordering.toString()+ " size "+ordering.size());
            }
        }
        
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
