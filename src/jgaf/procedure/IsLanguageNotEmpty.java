package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Representation;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;
import jgaf.grammar.algorithms.FAalgorithms;
import jgaf.Constants.MathConstants;

public class IsLanguageNotEmpty extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private Symbol nonterminal;

    public IsLanguageNotEmpty() {
    }

 /**
     * Metoda odstranuje nepouzitelne symboly podle <b>algoritmu 3.3</b>, ktery
     * je popsan v textu prace v <b>kapitole 2.1.2</b>
     * @param gramm je vstupni gramatika, kterou chceme redukovat
     * @return TRUE, pokud byla provedena nejaka uprava gramatiky, FALSE jinak
     * @throws IllegalArgumentException v pripade chybne gramatiky
     */
 public void startProcedure() {
        grammar2.clearHighlighting();
        grammar1.clearHighlighting();
        
        logState("start");
        FAalgorithms helpAlgs = new FAalgorithms();
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules.putAll(grammar1.getSameLeftSideMap());
        Symbol start = grammar1.getStartNonterminal();
        ProductionRuleSide startProductionRule = new ProductionRuleSide();
        if(nonterminal.equals(start)){
            startProductionRule.addSymbol(start);
        } else {
            startProductionRule.addSymbol(nonterminal);
        }

        SortedSet<ProductionRuleSide> set_previous = new TreeSet<ProductionRuleSide>();
        SortedSet<ProductionRuleSide> set_actual = new TreeSet<ProductionRuleSide>();
        
        SortedSet<Symbol> terminals = new TreeSet<Symbol>();
        SortedSet<ProductionRuleSide> nonterminals = new TreeSet<ProductionRuleSide>();

        terminals.addAll(grammar1.getTerminals());
        nonterminals.addAll(rules.keySet());

        boolean control = false;
        int i=0;
        logState("N_"+i+" = "+MathConstants.EMPTY_SET);
        do{
            i++;
            set_previous.clear();
            set_previous.addAll(set_actual);
            
            for(ProductionRuleSide leftRuleSide : nonterminals){
                logState("\nTesting rules of nonterminal "
                        +leftRuleSide.toString());
                SortedSet<ProductionRuleSide> setOfRules =
                      new TreeSet<ProductionRuleSide>();
               
                //ošetření, že neterminál není na levé straně, žádného pravidla
                //nemělo by nastat - beru množinu klíčů mapy
                if (rules.get(leftRuleSide) == null) {
                    continue;
                }
                setOfRules.addAll(rules.get(leftRuleSide));
                List<ProductionRules> colored = grammar1.getProductionsOfGivenNonterminal(leftRuleSide);
                
               for (ProductionRuleSide oneRule : setOfRules) {
                        
                        for(ProductionRules ruleToColor : colored){
                            if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                                ruleToColor.setFgColor(Color.RED);
                            }
                        }
                        logState("Testing rule "+leftRuleSide+" -> "+oneRule.toString());
                        for (Symbol s : oneRule.getSymbols()) {
                            //System.out.println("Symbol testovaný je :"+s.toString());
                            ProductionRuleSide testedSymbol = new ProductionRuleSide();
                            testedSymbol.addSymbol(s);
                            //sigma na hvezdicku obsahuje  i epsilon
                            if (terminals.contains(s) || set_previous.contains(testedSymbol)
                                    || s.isEpsilon()) {
                                control = true;
                            }else {
                                control = false;
                                
                                break;
                            }
                        }
                        if(control){
                            logState("Rule fulfil conditions." 
                                    +" add left nonterminal to N_"
                                    +i+"\nContinue with next nonterminal");
                            //vlozime symbol do mnoziny a vymazeme ho
                            //z aktualnich neterminalu
                            set_actual.add(leftRuleSide);
                            clearHighlighting();
//                            for(ProductionRules oneToColor : colored){
//                                oneToColor.setFgColor(Color.BLACK);
//                            }
                            break;
                        }else{
                            logState("Rule doesn't fulfil conditions");
                            clearHighlighting();
                        }

               }
               
            }
        logState("N_"+i+" = {"+set_actual.toString()+"}");
        }while(!set_actual.equals(set_previous));
        logState("N_"+(i-1)+" = N_"+i);
        if(set_actual.contains(startProductionRule)){
            if(nonterminal.equals(start)){
                logState("language is not empty ->"+
                        "create new grammar");
            } else {
                logState("From setted nonterminal can be created terminal string");
            }
            SortedSet<Symbol> nonterminals_e = new TreeSet<Symbol>();
            SortedSet<Symbol> nonterminalsOld = new TreeSet<Symbol>();
            //naplním novou množinu neterminálů z aktuální množiny levých pravidel
            //levá pravidla jsou jen 1 neterminál protože je to CFG
            for(ProductionRuleSide ruleSide : set_actual){
                nonterminals_e.add(ruleSide.getSymbols().get(0));
            }
            nonterminalsOld = grammar1.getNonterminals();
//            boolean equal = true;
//            for(ProductionRuleSide ruleSide : nonterminals){
//                
//                if(!nonterminals_e.contains(ruleSide.getSymbols().get(0)))
//                    equal = false;
//            }
            if(nonterminal.equals(start)){
                if(!nonterminalsOld.equals(nonterminals_e)){
                    logState("SetN: "+nonterminalsOld.toString() 
                            +" is not equal to N_e: " +nonterminals_e);

                    for(ProductionRuleSide leftRuleSide : set_actual){
                        if(rules.containsKey(leftRuleSide)){
                            List<ProductionRuleSide> oneSetOfRules =
                                rules.get(leftRuleSide);

                            for (ProductionRuleSide field : oneSetOfRules) {
                                control = true;
                                for (Symbol s : field.getSymbols()) {
                                    //nesmime zapomenout ze pravidla jsou tvorena
                                    //jak terminaly, tak neterminaly, tak i EPSILONEM
                                    if (s.isTerminal() || nonterminals_e.contains(s) ||
                                            s.isEpsilon()) {
                                        continue;
                                    }else{
                                        control = false;
                                        break;
                                    }
                                }
                                if(control == true){
                                    ProductionRulesSide rightHandSide = 
                                            new ProductionRulesSide(field);
                                    ProductionRuleSide newL = new ProductionRuleSide(leftRuleSide);
                                    ProductionRules newRule = 
                                            new ProductionRules(newL, rightHandSide);
                                    grammar2.addRule(newRule);
                                    newRule.setFgColor(Color.RED);
                                    logState("Add rule "+newRule.toString());
                                }
                                grammar2.clearHighlighting();
                            }
                        }
                    }
    //                grammar2.setStartNonterminal(start);
                }else{

                    logState("Set N:"+nonterminals.toString() 
                            +" is equal to N_e:" +nonterminals_e);
                    grammar2.addAllRules(grammar1);
                    logState("Grammar is same as previous");
                }
                grammar2.setStartNonterminal(start);
            } else {
                logState("Rules thatlead to creating terminal string from "
                            +nonterminal.getName()+" are:");
                
                SortedSet<ProductionRuleSide> keys_previous = new TreeSet<ProductionRuleSide>();
                SortedSet<ProductionRuleSide> keys_actual = new TreeSet<ProductionRuleSide>();
                SortedSet<ProductionRuleSide> done = new TreeSet<ProductionRuleSide>();
                
                keys_actual.add(startProductionRule);
                
                while(!keys_previous.equals(keys_actual)){
                    keys_previous.clear();
                    keys_previous.addAll(keys_actual);
                    for (ProductionRuleSide oneRule : keys_actual) {
                        if(rules.containsKey(oneRule) && !done.contains(oneRule)){
                            List<ProductionRuleSide> oneSetOfRules =
                                rules.get(oneRule);

                            for (ProductionRuleSide field : oneSetOfRules) {
                                control = true;
                                for (Symbol s : field.getSymbols()) {
                                    //nesmime zapomenout ze pravidla jsou tvorena
                                    //jak terminaly, tak neterminaly, tak i EPSILONEM
                                    if (s.isTerminal() || s.isEpsilon()) {
                                        continue;
                                    }else if(nonterminals_e.contains(s)){
                                        ProductionRuleSide helpS
                                                = new ProductionRuleSide();
                                        helpS.addSymbol(s);
                                        keys_actual.add(helpS);
                                    }else{
                                        control = false;
                                        break;
                                    }
                                }
                                if(control == true){
                                    ProductionRulesSide rightHandSide = 
                                            new ProductionRulesSide(field);
                                    ProductionRuleSide newL = new ProductionRuleSide(oneRule);
                                    ProductionRules newRule = 
                                            new ProductionRules(newL, rightHandSide);
                                    grammar2.addRule(newRule);
                                    newRule.setFgColor(Color.RED);
                                    logState("Add rule "+newRule.toString());
                                }
                                grammar2.clearHighlighting();
                            }
                        }
                        done.add(oneRule);
                    }
                }
                grammar2.setStartNonterminal(nonterminal);
            }
            
        } else{
            if(nonterminal.equals(start)){
                logState("Start nonterminal "+start.toString()+" isn't in N"+MathConstants.SUB_E);
                logState("Language is empty");
            } else {
                logState("From setted nonterminal "+nonterminal.getName()+" can't be created terminal string");
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
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = 
                                                grammar1.getSameLeftSideMap();
        List<Symbol> list = new ArrayList<Symbol>();
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
    public void clearHighlighting() {
        grammar1.clearHighlighting();
        grammar2.clearHighlighting();
    }
}
