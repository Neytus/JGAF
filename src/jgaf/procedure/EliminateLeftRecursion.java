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

public class EliminateLeftRecursion extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private Symbol nonterminal;
    private String parameter2;
    private Integer typeI;
    private List<ProductionRuleSide> ordering;
    private FAalgorithms helpAlgs = new FAalgorithms();
    
    public EliminateLeftRecursion() {
    }


 public void startProcedure() {
        grammar2.clearHighlighting();
        grammar1.clearHighlighting();
        int type = (int) typeI;
        logState("start");
        

        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<>();

        rules.putAll(grammar1.getSameLeftSideMap());

        List<ProductionRuleSide> listOfNonterms = new ArrayList<>();
        
        //potřebujeme libovolne usporadani - pouzijeme dané, jen musíme prenést 
        //pravidla z mapy do pole, ve kterém můžeme prohledávat jednotlivé bunky
        if(ordering.get(0).getSymbols().get(0).getName().equals("def")){
            listOfNonterms.addAll(rules.keySet());
        }else{
            listOfNonterms.addAll(ordering);
        }
        
        if(!nonterminal.getName().equals("def")){
            logState("Setted nonterminal is "+nonterminal.getName());
        }
        logState("Production rules type is "+typeI);
        
        logState("Ordering is "+listOfNonterms.toString());
        
        Grammar helpGramm = new Grammar();
        for(int i = 0; i < listOfNonterms.size(); i++){
            List<ProductionRuleSide> allRules = new ArrayList<ProductionRuleSide>();
            helpGramm = (Grammar) grammar2.clone();
            //máme kvůli přidávání nových pravidel
            List<ProductionRuleSide> newListOfRules = 
                                        new ArrayList<ProductionRuleSide>();
            //potrebujeme pravidlo A_i pro test
            ProductionRuleSide leftSide = listOfNonterms.get(i);
            
            logState("A_i = "+leftSide.toString());
            if(i == 0){
                newListOfRules.addAll(rules.get(leftSide));
                allRules.addAll(newListOfRules);
                logState("Is the lowest nonterminal. Nothing to search");
            }

          List<ProductionRuleSide> rulesToRemove = new ArrayList<ProductionRuleSide>();
            for(int j = 0; j < i; j++){
                newListOfRules = new ArrayList<ProductionRuleSide>();
                //Symbol leftSideSymb = leftSide.getSymbols().get(0);
                
                //potrebujeme pravidlo A_j pro test
                
                ProductionRuleSide nonTermForTest = listOfNonterms.get(j);
                logState("A_j = "+nonTermForTest.toString());
                
                Symbol nonTermForTestSymb = nonTermForTest.getSymbols().get(0);
   
                
                List<ProductionRuleSide> rulesOfLeftSide = 
                                        new ArrayList<ProductionRuleSide>();
                
                List<ProductionRuleSide> rulesOfnonTermForTest = 
                                        new ArrayList<ProductionRuleSide>();
                
                rulesOfLeftSide.addAll(rules.get(leftSide));
                rulesOfLeftSide.addAll(allRules);
                
                rulesOfLeftSide.removeAll(rulesToRemove);
                System.out.println(rules.toString());
                rulesOfnonTermForTest.addAll(rules.get(nonTermForTest));
                rulesToRemove = new ArrayList<ProductionRuleSide>();
                //testujeme jednotlivá pravidla
                logState("Rules for testing are "+rulesOfLeftSide.toString());
                 List<ProductionRules> colored = 
                                grammar1.getProductionsOfGivenNonterminal(leftSide);
                for(ProductionRuleSide oneRule : rulesOfLeftSide){
                    grammar1.clearHighlighting();
                    List<Symbol> listRule = new ArrayList<Symbol>();
                    listRule.addAll(oneRule.getSymbols());
                    logState("Testing rule "+leftSide.toString()+" -> "+oneRule.toString());
                    //pokud se symbol na první pozici pravidla rovná 
                    //neterminalu na pozici j, tak vytvorime nova pravidla
                    for(ProductionRules ruleToColor : colored){
                        if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                            ruleToColor.setFgColor(Color.RED);
                        }
                    }
                
                    if(nonTermForTestSymb.equals(listRule.get(0))){
                        
                        System.out.println("Testing rule is " +listRule.toString());
                        logState("Rule has form A_i -> A_j"+MathConstants.ALPHA);


                        List<Symbol> leftListRule = new ArrayList<Symbol>();
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
                                            new ProductionRules(newL, 
                                        new ProductionRulesSide(newRuleS));
//                                
                                grammar2.addRule(newRule);
                                grammar2.setFgColorToProd(Color.RED, newRule);
                                logState("Add rule "+newRule.toString());
                                newListOfRules.add(newRuleS);
                                if(allRules.contains(oneRule)) allRules.remove(oneRule);
                                rulesToRemove.add(oneRule);
                            }
                            grammar2.clearHighlighting();
                            //System.out.println("nove pravidlo po spojení je"+newRule.toString());
                        }
                        
                        //logState("Nová pravidla jsou "+nonTermForTestSymb.getName()+" -> "+newListOfRules.toString());
                        //System.out.println("nová pravidla jsou "+newListOfRules.toString());
                    }else{
                        
                        logState("Rule doesn't has form A_i -> A_j"+MathConstants.ALPHA);
                        newListOfRules.add(oneRule);
                        continue;
                    } 
                    
                }
                
                
                allRules.addAll(newListOfRules);
            }
            //ted odstranime primou rekurzi pomocí funce directRecursionElimination
            //v první iteraci se nedostaneme do for cyklu a tedy nemáme žádná pravidla v
            //newListOfRules. Ve všech ostatních iteracích už se ta množina naplní
            //i v případě, že tam není žádná rekurze
            logState("Testing rules for direct left recursion");
            
            if(i != 0 && !allRules.isEmpty()){
                grammar1.removeRulesOfLeft(leftSide);
                for(ProductionRuleSide rule: allRules){
                    ProductionRules newRule = new ProductionRules(leftSide, new ProductionRulesSide(rule));
                    grammar1.addRule(newRule);
                }
            }
            
            grammar2.removeAllRules();
            
            if(!nonterminal.getName().equals("def") && 
                    leftSide.getSymbols().get(0).equals(nonterminal)){
                leftSide.clear();
                leftSide.addSymbol(nonterminal);
            }else{
                grammar2.addAllRules(helpGramm);
            }
            SortedSet<ProductionRuleSide> recursiveRules = 
                new TreeSet<ProductionRuleSide>();
            SortedSet<ProductionRuleSide> anotherRules = 
                new TreeSet<ProductionRuleSide>();

        //potřebujeme symbol na porovnávání s prvním pravidlem 
            Symbol leftSideSymb = leftSide.getSymbols().get(0);
            ProductionRules unit = new ProductionRules();
            boolean unitCheck = false;
        //procházím jednotlivá pravidla a rozděluji je na dva typy
             List<ProductionRules> colored = 
                                grammar1.getProductionsOfGivenNonterminal(leftSide);
             List<ProductionRules> colored2 = 
                                grammar2.getProductionsOfGivenNonterminal(leftSide);

             //.out.println("testovaná pravidla jsou "+testedRules.toString());
             //System.out.println("všechna pravidla jsou "+rules.toString());
            for(ProductionRuleSide oneRule : allRules){
                List<Symbol> listRule = new ArrayList<Symbol>();
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
                
                logState("Testing rule "+leftSideSymb.getName()+" -> "+oneRule.toString());
                ProductionRuleSide ruleWithoutRecursion = new ProductionRuleSide();
                //máme levou rekurzi
                if(listRule.get(0).equals(leftSideSymb)){
                    if(oneRule.size()!=1){
                        logState("Rule has direct left recursion");
                        ruleWithoutRecursion.setSymbolsFromProductionRuleSide(oneRule);
                        ruleWithoutRecursion.removeSymbolOnPosition(0);
                        recursiveRules.add(ruleWithoutRecursion);
                    }else{
                        unit = 
                            new ProductionRules(leftSide, new ProductionRulesSide(oneRule));
                        unitCheck = true;
                        logState("Rule has direct left recursion and it's unit");
                    }
                }else{
                    logState("Rule doesn't have direct left recursion");
                    anotherRules.add(oneRule);
                }
                grammar1.clearHighlighting();
                grammar2.clearHighlighting();
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
                    logState("Browsing rules with direct left recursion "
                            +"and adding new rules (viz Lemma 5.1(**))");
                    //teď pridame vsechna ostatni pravidla
                    for(ProductionRuleSide oneRule : recursiveRules){
                        logState("Deleting left recursion for "
                                +leftSide.toString()+" -> "+leftSide.toString()
                                                        +oneRule.toString());
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
                        logState("Add rules "+newRule.toString());
                        grammar2.clearHighlighting();
                    }
                    //přidáme pravidla do nové mapy
                    
                    
                    logState("Browsing rules without direct left recursion "
                            +"and adding new rules (viz Lemma 5.1 (**))");
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
                        ProductionRuleSide newL = new ProductionRuleSide(leftSide);
                        ProductionRules newRule = 
                               new ProductionRules(newL, 
                                new ProductionRulesSide(newRulesListAnotherRules));
                        newRule.setFgColor(Color.RED);
                        grammar2.addRule(newRule);
                        logState("Add rules "+newRule.toString());
                        grammar2.clearHighlighting();
                    }
                    //přidáme pravidla do nové gramatiky
                    
                    
                    
                }else if(type == 2){
                    logState("Browsing rules with direct left recursion "
                            +"and adding new rules (viz Lemma 5.1(***))");
                    for(ProductionRuleSide oneRule : recursiveRules){
                         logState("Deleting left recursion for "
                                +leftSide.toString()+" -> "+leftSide.toString()+oneRule.toString());
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
                        logState("Add rules "+newRule.toString());
                        grammar2.clearHighlighting();
                    }
                    //musíme přidat eps pravidlo
                    ProductionRuleSide eps = new ProductionRuleSide();
                    eps.addEpsilon("eps");
//                    newRulesListRecursiveRules.add(eps);

                    ProductionRuleSide newL = new ProductionRuleSide(newLeftNonterm);
                    ProductionRules newRule = 
                            new ProductionRules(newLeftNonterm, new ProductionRulesSide(eps));
                    newRule.setFgColor(Color.RED);
                    grammar2.addRule(newRule);
                    logState("Add rule "+newRule.toString());
                    grammar2.clearHighlighting();
                    logState("Browsing rules without direct left recursion "
                            +"and adding new rules (viz Lemma 5.1 (***))");
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
                        
                        newRule = 
                            new ProductionRules(leftSide, new ProductionRulesSide(newRulesListAnotherRules));
                        newRule.setFgColor(Color.RED);
                        grammar2.addRule(newRule);
                        logState("Add rules "+newRule.toString());
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
                        ProductionRuleSide newL = new ProductionRuleSide(leftSide);
                        ProductionRules newRule = 
                                new ProductionRules(newL, new ProductionRulesSide(oneRule));
                        //newRule.setFgColor(Color.RED);
                        grammar2.addRule(newRule);
                        grammar2.setFgColorToProd(Color.RED, newRule);
                        
                        logState("Add rules. "+newRule.toString());

                        grammar2.clearHighlighting();
                    }
                }else{
                    if(unitCheck){
                        logState("Nonterminal "+leftSide+" has direct left recursion only in unit rule");
                        logState("Rules are same without "+unit.toString());
                        for(ProductionRuleSide oneRule : anotherRules){
                            ProductionRuleSide newL = new ProductionRuleSide(leftSide);
                            ProductionRules newRule = 
                                new ProductionRules(newL, new ProductionRulesSide(oneRule));
                            grammar2.addRule(newRule);
                            newRule.setFgColor(Color.RED);
                            logState("Add rules "+newRule.toString());
                            grammar2.clearHighlighting();
                        }
                    }else{
                        logState("Grammar doesn't have direct left recursion");
                        logState("Rules are same");
                        for(ProductionRuleSide oneRule : anotherRules){
                            ProductionRuleSide newL = new ProductionRuleSide(leftSide);
                            ProductionRules newRule = 
                                new ProductionRules(newL, new ProductionRulesSide(oneRule));
                            grammar2.addRule(newRule);
                            newRule.setFgColor(Color.RED);
                            logState("Add rules "+newRule.toString());
                            grammar2.clearHighlighting();
                        }
                    }
                }
                
            }
            if(i<listOfNonterms.size()-1){
                grammar1.removeRulesOfLeft(leftSide);
                grammar1.addListOfRules(grammar2.getProductionsOfGivenNonterminal(leftSide));
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
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        if(nonterminal.getName().equals("def") && parameter2.equals("def") &&
          (ordering.size()==1 && ordering.get(0).getSymbols().get(0).getName().equals("def"))) {
            typeI = 1;
            return CHECK_OK;
        }
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = 
                                                grammar1.getSameLeftSideMap();
        List<Symbol> list = new ArrayList<>();
        
        try{
            typeI = Integer.parseInt(parameter2);
        }catch(NumberFormatException ex){
            return "Parameter2 is not integer "+ex;
        }
        if(typeI != 1){
            typeI = 2;
        }
        if(ordering.size()==1 && ordering.get(0).getSymbols().get(0).getName().equals("def")){
        }else{
            for(ProductionRuleSide oneSymbol : ordering) {
                if(!map.keySet().contains(oneSymbol)){
                    return "Nonterminal "+oneSymbol.toString()+
                            " in parameter 3 is not at the left side of any rule";
                }
            }
        
            if(ordering.size() != map.keySet().size()){
                return "Not well count of symbols in parameter 3";
            }
        }
        if(nonterminal.getName().equals("def") ) return CHECK_OK;
        list.add(nonterminal);
        ProductionRuleSide leftHandSide = new ProductionRuleSide(list);
        if(!map.keySet().contains(leftHandSide)){
            return "Nonterminal is not on the left side of any rule";
        }
        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        if ((inputParameters[0] == null || inputParameters[0].equals("")) &&
            (inputParameters[1] == null || inputParameters[1].equals("")) &&
            (inputParameters[2] == null || inputParameters[2].equals(""))) {
            nonterminal = new Symbol("def", 1);
            parameter2 = "def";
            
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
            parameter2 = inputParameters[1].trim();
        }
        if(inputParameters[2] != null){
            String parameter = inputParameters[2].trim();
            String[] parameters = parameter.split(",");
            for(int i=0; i<parameters.length;i++){
                String symb = parameters[i].trim();
                ProductionRuleSide newNonterminal = new ProductionRuleSide();
                newNonterminal.addNonterminal(symb);
                ordering.add(newNonterminal);
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
