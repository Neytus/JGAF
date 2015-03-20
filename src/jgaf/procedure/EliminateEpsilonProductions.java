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
import jgaf.grammar.ProductionRuleSideAscendingComparator;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;
import jgaf.grammar.algorithms.FAalgorithms;

public class EliminateEpsilonProductions extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private Symbol parameter1;
//    private String parameter2;

    public EliminateEpsilonProductions() {
    }

 /**
     * Metoda odstranuje epsilon pravidla podle <b>algoritmu 3.4/b>
     * @param gramm je vstupni gramatika, kterou chceme redukovat
     * @return TRUE, pokud byla provedena nejaka uprava gramatiky, FALSE jinak
     * @throws IllegalArgumentException v pripade chybne gramatiky
     */
    public void startProcedure() {
        
        grammar2.clearHighlighting();
        grammar1.clearHighlighting();
        long begin, end;
        begin = System.currentTimeMillis();
        logState("start");
        FAalgorithms helpAlgs = new FAalgorithms();
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules.putAll(grammar1.getSameLeftSideMap());
        Symbol start = grammar1.getStartNonterminal();

        SortedSet<Symbol> set_previous = new TreeSet<Symbol>();
        SortedSet<Symbol> set_actual = new TreeSet<Symbol>();
        
        SortedSet<Symbol> terminals = new TreeSet<Symbol>();
        SortedSet<Symbol> nonTerminals = new TreeSet<Symbol>();

        terminals.addAll(grammar1.getTerminals());
        nonTerminals.addAll(grammar1.getNonterminals());
       
        //cyklus pri kterem porovnavam mnoziny
        do {
            set_previous.clear();
            set_previous.addAll(set_actual);
            if(set_actual.isEmpty()){
                logState("Set N_"+MathConstants.EPSILON+" = "+MathConstants.EMPTY_SET);
            }else{
                logState("Set N_"+MathConstants.EPSILON+" = {"+set_previous.toString()+"}");
            }
            for (Symbol symb : nonTerminals){
               List<ProductionRuleSide> setOfRules =
                        new ArrayList<ProductionRuleSide>();
               
               //budeme potřebovat, abychom zkontrolovali jestli 
               //zřetězene pravidlo neni eps
               boolean control = false;
               boolean eps = false;
               //osetreno, ze symbol v neterminalech nahodou neni na leve
                //strane zadneho pravidla 
               //muzeme si dovolit je to CFG - na levé straně musí být jen 
               //jeden neterminál
                ProductionRuleSide oneSymb = new ProductionRuleSide();
                oneSymb.addSymbol(symb);
                if (rules.get(oneSymb) == null) {
                    //nonTerminals.remove(symb);
                } else {
                    List<ProductionRules> colored = grammar1.getProductionsOfGivenNonterminal(oneSymb);
                    setOfRules.addAll(rules.get(oneSymb));
                    for (ProductionRuleSide oneRule : setOfRules) {
                        //obarvení
                        for(ProductionRules ruleToColor : colored){
                            if(ruleToColor.getRightHandSide().getRules().contains(oneRule)){
                                ruleToColor.setFgColor(Color.RED);
                            }
                        }
                        logState("Tested rule is "+oneSymb.toString()+
                            " -> "+oneRule.toString());
                        
                        for (Symbol s : oneRule.getSymbols()) {
                            //pravidlo je epsilon, tak rovnou vložíme
                            //tohle se provede jen v první iteraci while cyklu
                            if (s.isEpsilon()){
                                eps = true;
                                break;
                            } 
                            //pravidlo obsahuje neterminál z předchozí množiny
                            //tak pokračujeme v hledání (nesmí obsahovat terminál
                            else if (set_previous.contains(s)) {
                                logState("Symbol "+s.getName()+" is in N_"+MathConstants.EPSILON);
                               
                                control = true;
                            } else {
                                logState("Symbol X_i = "+s.getName()+" isn't in N_"+MathConstants.EPSILON);
//                                logState("Neterminál "+symb.getName()
//                                        +" se pomoci pravidla "+oneRule.toString()
//                                        +" nepřepíše na "+MathConstants.EPSILON);
                                control = false;
                                break;
                            }
                        }
                        //když je false tak to znamena, že nějaky symbol v pravidle
                        //se neprepise na eps a tím pádem se pravidlo nemuze prepsat na eps
                        if(eps){
                            logState("Rule has form "+symb.getName()+" -> "
                                    +MathConstants.EPSILON);
                        }
                        if(control){
                            logState("Nonterminaů "+symb.getName()
                                    +" assign to "+MathConstants.EPSILON+" in final count of steps");
                        }
                        if(control || eps){
                            logState("Nonterminal "+symb.getName()
                                    + " was added to N_"+MathConstants.EPSILON);
                            //vlozime symbol do mnoziny a 
                            //dál už pravidla tohohle neterminálu prohledávat nemusíme
                            //stačí nalézt jedno pravidlo pomocí, ktereho se prepise na eps,
                            //abychom pridali neterminal do N_eps
                            set_actual.add(symb);
                            break;
                        }
                    }
                    grammar1.clearHighlighting();
                }
            }
        }while (!set_actual.equals(set_previous));
        logState("Set N_"+MathConstants.EPSILON+" = "+set_actual.toString()
                + " is same as previous set N_"+MathConstants.EPSILON
                +" = "+set_previous.toString());
//        logState("Ukončuji cyklus pro vytvoření množiny N_"+MathConstants.EPSILON);
        //pravidla P' po vymazani eps kroků
        Map<ProductionRuleSide, List<ProductionRuleSide>> newRules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        
        List<ProductionRules> allRules = grammar1.getProductionRulesType2();
//        for(Map.Entry<ProductionRuleSide, 
//                List<ProductionRuleSide>> rulesOfOneN : rules.entrySet()){
        
        logState("\nStart cycle to creating rules");
        for(ProductionRules oneLine : allRules){
            //musím řešit, že už klíč obsahuje, ať se nepřemažou předchozí pravidla
//            List<ProductionRuleSide> newRulesOfOneN = 
//                                        new ArrayList<ProductionRuleSide>();
//            List<ProductionRuleSide> oldRules = 
//                                        new ArrayList<ProductionRuleSide>();
            //procházíme všechna pravidla jednoho neterminálu vlevo, vytvarime 
            // nova pravidla pomoci eliminateEpsHelp a pak je spojujeme s 
            //jiz vzniklými pravidly tak aby nevznikaly duplicity
            
            //ProductionRuleSide key = rulesOfOneN.getKey();
            oneLine.setFgColor(Color.RED);
            ProductionRuleSide left = oneLine.getLeftHandSide();
            ProductionRulesSide right = oneLine.getRightHandSide();
            
//            if(parameter2.equals("quick")){
//                List<ProductionRuleSide> newRulesOfOneN = 
//                                            new ArrayList<ProductionRuleSide>();
//    //            List<ProductionRuleSide> oldRules = 
//    //                                        new ArrayList<ProductionRuleSide>();
//                //procházíme všechna pravidla jednoho neterminálu vlevo, vytvarime 
//                // nova pravidla pomoci eliminateEpsHelp a pak je spojujeme s 
//                //jiz vzniklými pravidly tak aby nevznikaly duplicity
//                for(ProductionRuleSide rule : right.getRules()){
//                    List<ProductionRuleSide> oldRules = 
//                                            new ArrayList<ProductionRuleSide>();
//                    oldRules.addAll(newRulesOfOneN);
//                    newRulesOfOneN.clear();
//                    //System.out.println("Old = "+oldRules.toString()+", new = "+newRulesOfOneN.toString());
//                    newRulesOfOneN.addAll(helpAlgs.concateWithoutDuplicity(oldRules,
//                                                    helpAlgs.eliminateEpsHelp(rule,set_actual)));
//                }
//                //System.out.println("Nová pravidla od "+rulesOfOneN.getKey().toString()
//                  //      +" jsou: "+newRulesOfOneN);
//                newRules.put(left, newRulesOfOneN);
//            }
//            
//            
//            
//            else{
    //            for(ProductionRuleSide rule : rulesOfOneN.getValue()){
                for(ProductionRuleSide rule : right.getRules()){
    //                List<ProductionRuleSide> oldRules = 
    //                                        new ArrayList<ProductionRuleSide>();
    //                oldRules.addAll(newRulesOfOneN);
    //                newRulesOfOneN.clear();
    //                System.out.println("Old = "+oldRules.toString()+", new = "+newRulesOfOneN.toString());
    //                
    //                
    //                SortedSet<ProductionRuleSide> newRulesSet = new TreeSet<ProductionRuleSide>();

                    SortedSet<Symbol> nonTermEps = new TreeSet<Symbol>();
                    nonTermEps.addAll(set_actual);
                    List<ProductionRuleSide> setRulesPrevious = 
                            new ArrayList<ProductionRuleSide>();
                    List<ProductionRuleSide> setRulesActual = new ArrayList<ProductionRuleSide>();
                            //new TreeSet<ProductionRuleSide>(new ProductionRuleSideAscendingComparator());
                    List<ProductionRuleSide> newSetActual = new ArrayList<ProductionRuleSide>();
                            //new TreeSet<ProductionRuleSide>(new ProductionRuleSideAscendingComparator());

                    SortedSet<ProductionRuleSide> testedRules = 
                            new TreeSet<ProductionRuleSide>(new ProductionRuleSideAscendingComparator());
                    SortedSet<ProductionRuleSide> testedRulesHelp =
                            new TreeSet<ProductionRuleSide>(new ProductionRuleSideAscendingComparator());
                    testedRulesHelp.add(rule);
                    int j =0;
                    do{
                        j++;
                       // System.out.println("setPrevious = "+setRulesPrevious.toString()+"setAktual = "+setRulesActual.toString());
                        setRulesPrevious.clear();
                        setRulesPrevious.addAll(setRulesActual);
                        newSetActual.clear();

                        testedRules.clear();
                        testedRules.addAll(testedRulesHelp);
                        testedRulesHelp.clear();
                        for(ProductionRuleSide testedRule : testedRules){

                            logState("Testing rule "+left.toString()+" -> "
                                    +testedRule.toString());
                            if(testedRule.isEpsilon() || testedRule.isEmpty()) {
                                logState("Testing rule has form "+left.toString()
                                        +" -> "+MathConstants.EPSILON);
                                logState("Don't add rule. Continue.");
                                break;
                            }        
                            else{
                                List<Symbol> oneRule = new ArrayList<Symbol>();
                                oneRule.addAll(testedRule.getSymbols());
                                //System.out.println("*************************");
                                //System.out.println("Testuji pravidlo "+testedRule.toString());
                                //System.out.println("*************************");
                                //if(!setRulesActual.contains(testedRule)){
    //                            boolean pridano = false;
                                  newSetActual.add(new ProductionRuleSide(testedRule));
    //                            logState("tpridano :"+pridano);
                                //}
                                boolean check = false;
                                for(int i=0; i<oneRule.size();i++){
                    //                System.out.println("prochazim pravidlo "+oneRule.toString()+" jinak "+rule.toString());

                                    Symbol testedSymb = oneRule.get(i);
                                    logState("Testing rule "+left.toString()
                                            + " -> "+testedRule.toString()+" and symbol "
                                            +testedSymb.getName()+" at position "+i);
                    //                System.out.println("kontroluji symbol "+testedSymb.getName());
                                    //symbol není v mnozine N_eps a pravidlo jeste nebylo pridano
                                    if(!nonTermEps.contains(testedSymb) && !check){
                                        logState("Symbol "+testedSymb.getName()+" isn't in N_"
                                                +MathConstants.EPSILON);
                                        logState("Add rule "
                                                +left.toString()+" -> "+testedRule.toString());

                                        if(parameter1.getName().equals("def") || parameter1.equals(left.getSymbols().get(0))){
                                            ProductionRuleSide newL = new ProductionRuleSide(left);
                                            ProductionRules newRule = 
                                                        new ProductionRules(newL, 
                                                    new ProductionRulesSide(testedRule));
                                            newRule.setFgColor(Color.RED);
                                            grammar2.addRule(newRule);
                                        }

    //                                    logState("Přidáme pravidlo "+newRules.toString());

    //                                    logState("SetAktual = "+setRulesActual.toString());
                                        check = true;
                    //                    System.out.println("vlozeno "+rule.toString());
                                    }else if(nonTermEps.contains(testedSymb)){
                                        logState("Symbol "+testedSymb.getName()+" is in N_"
                                                +MathConstants.EPSILON);
                                        if(check){
                                            //logState("Pravidlo již bylo přidáno");
                                        }else{
                                            logState("Add rule "
                                                    +left.toString()+" -> "+testedRule.toString());
                                            //logState("SetAktual před = "+setRulesActual.toString());
                                            //logState("NewSetAktual před = "+newSetActual.toString());
                                            //logState("tested rule je:"+testedRule.toString());

    //                                        if(!setRulesActual.contains(testedRule)){
    //                                            pridano = setRulesActual.add(testedRule);
    //                                        }
                                            if(parameter1.getName().equals("def") || parameter1.equals(left.getSymbols().get(0))){
                                                ProductionRuleSide newL = new ProductionRuleSide(left);
                                                ProductionRules newRule = 
                                                            new ProductionRules(newL, 
                                                        new ProductionRulesSide(testedRule));
                                                newRule.setFgColor(Color.RED);
                                                grammar2.addRule(newRule);
                                            }
                                        }
    //                                    logState("SetAktual po= "+setRulesActual.toString());
                                        ProductionRuleSide shortedRuleSide = new ProductionRuleSide();
                                        List<Symbol> shortedRule = new ArrayList<Symbol>();
                                        shortedRule.addAll(oneRule);
                                        shortedRule.remove(i);

                                        shortedRuleSide.setSymbols(shortedRule);
                                        logState("Cut short rule and add to next testing "
                                                +left.toString()+" -> "+shortedRuleSide.toString());
                    //                    System.out.println("zmenšené pravidlo "+shortedRule.toString()+" jinak "+shortedRuleSide.toString());

                    //                    System.out.println("vlozeno "+shortedRuleSide.toString());
    //                                    setRulesActual.addAll(setRulesPrevious);
                                        if(!testedRulesHelp.contains(shortedRuleSide)){
                                            testedRulesHelp.add(shortedRuleSide);
                                        }
//                                        logState("testovací množina pravidel pro "
//                                                +"další iteraci je :"+testedRulesHelp.toString());
                                        check = true;

                                    }else{
                                        //jedině nějaká chyba - ještě bude potřeba promyslet
                                    }
                                    setRulesActual = helpAlgs.concateWithoutDuplicity(setRulesActual, newSetActual);
                                    //logState("SetAktual = "+setRulesActual.toString());
                                    //logState("NewSetAktual = "+newSetActual.toString());
                                }
                                grammar2.clearHighlighting();
                            }
                            
                        }
//                        logState("předchozí množina pravidel pro neterminál "+left.toString()
//                                +" je = "+setRulesPrevious.toString()
//                                + " aktuální množina pravidel pro neterminál "+left.toString()
//                                +" je "+setRulesActual.toString());
                    }while(!setRulesPrevious.equals(setRulesActual));

                    logState("Cycle ended \n");
                    //logState("předchozí množina je = "+setRulesPrevious.toString()+ " aktuální množina je "+setRulesActual.toString());

    //                newRulesOfOneN.addAll(helpAlgs.concateWithoutDuplicity(oldRules,
    //                                            helpAlgs.eliminateEpsHelp(rule,set_actual)));

                }
    //            System.out.println("Nová pravidla od "+rulesOfOneN.getKey().toString()
    //                    +" jsou: "+newRulesOfOneN);
    //            newRules.put(rulesOfOneN.getKey(), newRulesOfOneN); 
                
//            }
            grammar1.clearHighlighting();
            
        }
        //Kdyz N_eps obsahuje pocatecni neterminal, tak vytvorime nove pravidlo 
        // S'->S|eps
        ProductionRuleSide newStartN = new ProductionRuleSide();
        Symbol newStart = new Symbol(start+"'", 1);
        if(parameter1.getName().equals("def")){
            if(set_actual.contains(start)){
                logState("Set N_"+MathConstants.EPSILON+" contain start nonterminal "+start.getName());

                List<ProductionRuleSide> newStartRules = 
                        new ArrayList<ProductionRuleSide>();
                ProductionRuleSide eps = new ProductionRuleSide();
                ProductionRuleSide oldStart = new ProductionRuleSide();
                eps.addEpsilon(MathConstants.EPSILON);
                oldStart.addSymbol(start);
                newStartRules.add(eps);
                newStartRules.add(oldStart);
                newStartN.addSymbol(newStart);
    //            newRules.put(newStartN, newStartRules);
                ProductionRules newRule = 
                                        new ProductionRules(newStartN, new ProductionRulesSide(newStartRules));

                if(grammar1.getProductionCount()==1){
                    logState("Grammar has only one rule "+grammar1.getStartToEpsRule().toString());  
                    grammar2.addRule(grammar1.getStartToEpsRule());
                    logState("Add rule "+grammar1.getStartToEpsRule());  
                }else{
                    grammar2.addRule(newRule);
                    logState("Add rules "+newRule.toString());
                }

            }
        }
        //pokud se mapa pravidel liší od puvodni mapy, tak budeme vracet true
        //a vytvoříme novou gramatiku s novými pravidly
        //množina terminálů a neterminálů by měla být stejná
        //System.out.println("Došel jsem až sem");
        //System.out.println("pravidla jsou: " +rules.toString());
        // System.out.println("nová pravidla jsou: " +newRules.toString());
        if(!newStartN.isEmpty()){
            grammar2.setStartNonterminal(newStart);
        }else{
            grammar2.setStartNonterminal(start);
        }
//        boolean lol = grammar1.equals(grammar2);
//        logState("lol = "+lol);
//        if(parameter2.equals("quick")){
//            //grammar2 = helpAlgs.addRuleToGrammFromMap(newRules);
//            for (Map.Entry<ProductionRuleSide, 
//                    List<ProductionRuleSide>> oneSet : newRules.entrySet()) {
//                for(ProductionRuleSide oneNewRule : oneSet.getValue()) {
//                    if(oneNewRule != null){
//                        if(parameter1.getName().equals("def") || parameter1.equals(oneSet.getKey().getSymbols().get(0))){
//                            ProductionRules newRule = 
//                                        new ProductionRules(oneSet.getKey(), new ProductionRulesSide(oneNewRule));
//                            newRule.setFgColor(Color.RED);
//                            grammar2.addRule(newRule);
//                            logState("Přidáme pravidlo "+newRule.toString());
//                        }
//                    }
//                    grammar2.clearHighlighting();
//                }
//            }
//           // System.out.println("gramatika  je: " +grammar2.toString());
//        }
//        }else{
//            grammar2.addAllRules(grammar1);            
//            grammar2.setStartNonterminal(start);
//            logState("Gramatika je stejná jako předchozí");
//        }
//        end = System.currentTimeMillis();
//        logState("čas běhu = "+(end-begin) + " ms.");
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
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = 
                                                grammar1.getSameLeftSideMap();
        List<Symbol> list = new ArrayList<Symbol>();
        if(parameter1.getName().equals("def")) return CHECK_OK;
        list.add(parameter1);
        ProductionRuleSide leftHandSide = new ProductionRuleSide(list);
        if(map.keySet().contains(leftHandSide)){
            return CHECK_OK;
        } else {
            return "Parameter 1 is not 'def' and or nonterminal is not on left side of any rule";
        }
//        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        if(inputParameters[0] != null){
            String param = inputParameters[0].trim();
            parameter1 = new Symbol(param, 1);
        }
//        if(inputParameters[1] != null){
//            String param = inputParameters[1].trim();
//            if(param.equals("quick")){
//                parameter2 = param;
//            }else{
//                parameter2 = "classic";
//            }
//        }
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
