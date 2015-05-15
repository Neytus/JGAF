package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jgaf.Representation;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;
import jgaf.grammar.algorithms.FAalgorithms;

public class EliminateUnreachableSymbols extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private Symbol nonterminal;
    private boolean def;
             
    
    
    public EliminateUnreachableSymbols() {
    }


    public void startProcedure() {
        grammar2.clearHighlighting();
        grammar1.clearHighlighting();
        logState("start");
        FAalgorithms helpAlgs = new FAalgorithms();
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<>();
        rules.putAll(grammar1.getSameLeftSideMap());
        //logState("vložili jsme všechna pravidla do množiny pravidel");
        Symbol start = grammar1.getStartNonterminal();

        Set<Symbol> set_previous = new HashSet<>();
        Set<Symbol> set_actual = new HashSet<>();
        //zaciname v pocatecnim symbolu
        if(nonterminal.equals(start)){
            set_previous.add(start);
        } else {
            logState("Setted nonterminal is "+nonterminal.toString()+" and it's not start nonterminal");
            logState("At first we find from which rules is reacheble in one step");
            for(Map.Entry<ProductionRuleSide, List<ProductionRuleSide>> oneEntry : rules.entrySet()){
                if(oneEntry.getKey() != null && oneEntry.getValue() != null && oneEntry != null){
                    ProductionRuleSide key = oneEntry.getKey();
                    List<ProductionRuleSide> values = oneEntry.getValue();
                    
                    List<ProductionRules> colored = grammar1.getProductionsOfGivenNonterminal(key);
                    for (ProductionRuleSide oneRule : values) {     
                        
                        for(ProductionRules ruleToColor : colored){
                            if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                                ruleToColor.setFgColor(Color.RED);
                            }
                        }
                        logState("Testing rule is "+key.toString()+
                            " -> "+oneRule.toString());
                        
                        List<Symbol> oneRuleSymbols = new ArrayList<Symbol>();
                        oneRuleSymbols.addAll(oneRule.getSymbols());
                        for (Symbol s : oneRuleSymbols) {
                            ProductionRuleSide helpContain = new ProductionRuleSide();
                            helpContain.addSymbol(nonterminal);
                            if (s.isNonterminal()){
                                if(!s.equals(nonterminal)){
                                }else{
                                    Symbol symbol = oneEntry.getKey().getSymbols().get(0);
                                    set_previous.add(symbol);
                                    logState("Add nonterminal "+symbol.getName()+
                                            ". Actual set is:"
                                            +set_previous.toString());
                                }
                            }
                        }
                        for(ProductionRules oneToColor : colored){
                            oneToColor.setFgColor(Color.BLACK);
                        }
                    }
                }
            }
        }
        if(set_previous.isEmpty()){
            logState("Nonterminal is not reacheable!");
        } else {
            //pocitame tak dlouho dokud si nejsou dve po sobe jdouci mnoziny rovny
            int i=0;
            while (!set_previous.equals(set_actual)) {
                //podminka je splnena pouze v prvni iteraci cyklu
                if (set_actual.isEmpty()) {
                    if(nonterminal.equals(start)){
                        set_actual.add(start);
                    } else {
                        set_actual.addAll(set_previous);
                    }
                }
                //naplnime mnozinu mnozinou spoctenou v predchozi iteraci
                set_previous.addAll(set_actual);
                logState("Set V_"+i+" is "+set_previous.toString());
                //pocitame pro vsechny symboly z predesle iterace
                for (Symbol symb : set_previous) {
                    logState("Testing symbol "+symb.getName()+"from V_"+i);
                    List<ProductionRuleSide> setOfRules =
                            new ArrayList<>();
                    ProductionRuleSide helpLeftNonTerminal = new ProductionRuleSide();
                    helpLeftNonTerminal.addSymbol(symb);

                    List<ProductionRuleSide> helpSetOfRules = new ArrayList<>();
                    helpSetOfRules.addAll(rules.get(helpLeftNonTerminal));
                    
                    if(helpSetOfRules != null){
                        setOfRules.addAll(helpSetOfRules);

                        List<ProductionRules> colored = 
                                grammar1.getProductionsOfGivenNonterminal(helpLeftNonTerminal);
                        for (ProductionRuleSide oneRule : setOfRules) {
                            for(ProductionRules ruleToColor : colored){
                                if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                                    ruleToColor.setFgColor(Color.RED);
                                }
                            }
                            logState("Testing rule "+helpLeftNonTerminal.toString()
                                    +" -> "+oneRule.toString());
                            List<Symbol> oneRuleSymbols = new ArrayList<>();
                            oneRuleSymbols.addAll(oneRule.getSymbols());
                            for (Symbol s : oneRuleSymbols) {
                                ProductionRuleSide helpContain = new ProductionRuleSide();
                                helpContain.addSymbol(s);
                                if (s.isNonterminal()){
                                    if(!rules.containsKey(helpContain)){
                                        logState("Testing nonterminal "+s.getName()
                                                +" is not at the left side of any rule."+
                                                "Don't adding to testing set");
                                    }else{
                                        set_actual.add(s);
                                        logState("Add nonterminal "+s.getName()+
                                                " to testing set. Actual set is:"
                                                +set_actual.toString());
                                    }
                                }
                            }
                            for(ProductionRules oneToColor : colored){
                                oneToColor.setFgColor(Color.BLACK);
                            }
                        }
                    }else{
                        break;
                    }   
                }
                i++;
            }
            logState("All reachable nonterminals were added");
            logState("Set is " + set_actual.toString()+ 
                    " previous set was "+rules.keySet().toString());
            //kontrola zda bylo provedeno nejake odstraneni
            if(set_actual.size() == 1 && set_actual.contains(nonterminal) && !nonterminal.equals(start)){
                logState("Set contain only input nonterminal");
                logState("Nonterminal is not reachable!");
            } else {
                
                Set<ProductionRuleSide> helpList = rules.keySet();

                //gramatika byla upravovana - nastavime do nove  gramatiky
                grammar2.setStartNonterminal(start);
                if(!nonterminal.equals(start)){
                    logState("nonterminal is reachable!");
                }
                //ziskani novych pravidel z puvodnich pravidel
                for (Symbol s : set_actual) {
                    ProductionRuleSide oneSymb = new ProductionRuleSide();
                    oneSymb.addSymbol(s);
                    if(rules.containsKey(oneSymb)){
                        List<ProductionRuleSide> oneSetOfRules =
                                        rules.get(oneSymb);
                        for (ProductionRuleSide field : oneSetOfRules) {
                            if(!nonterminal.equals(start)){
                                //jsou vypisována jen pravidla, pomocí níž se dá do neterminálu dostat 
                                //- není to komplet gramatika
                                for(Symbol tested : set_actual){
                                    if(field.getSymbols().contains(tested)){
                                        ProductionRuleSide newL = new ProductionRuleSide(oneSymb);
                                        ProductionRules newRule = 
                                            new ProductionRules(newL, new ProductionRulesSide(field));
                                        grammar2.addRule(newRule);
                                        logState("Add rule "+newRule.toString()); 
                                    }
                                }
                            }else{
                                ProductionRuleSide newL = new ProductionRuleSide(oneSymb);
                                ProductionRules newRule = 
                                    new ProductionRules(newL, new ProductionRulesSide(field));
                                grammar2.addRule(newRule);
                                logState("Add rule "+newRule.toString());
                            }
                        }
                    }else{
                        continue;
                    }
                }

                 if (set_actual.equals(helpAlgs.prodRulesToSymbols(helpList))) { 
                    grammar2.addAllRules(grammar1);
                    logState("Rules are same");
                }
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
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        if (def == true) {
            nonterminal = grammar1.getStartNonterminal();
            return CHECK_OK;
        }
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = 
                                                grammar1.getSameLeftSideMap();
        List<Symbol> list = new ArrayList<>();
        list.add(nonterminal);
        ProductionRuleSide leftHandSide = new ProductionRuleSide(list);
        if(map.keySet().contains(leftHandSide)){
            return CHECK_OK;
        } else {
            return "Nonterminal is not on left side of any rule";
        }
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        if (inputParameters[0] == null || inputParameters[0].equals("")) {
            def = true;
        }
        
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
