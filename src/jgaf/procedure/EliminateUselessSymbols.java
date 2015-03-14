package jgaf.procedure;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Representation;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;
import jgaf.grammar.algorithms.FAalgorithms;
import jgaf.Constants.MathConstants;

public class EliminateUselessSymbols extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private String parameter;
//    private SubIndexHash a = new ModuloSubIndexHash("e",3);
    FAalgorithms helpAlgs = new FAalgorithms();

    public EliminateUselessSymbols() {
    }

    public void startProcedure() {
        
        grammar2.clearHighlighting();
        grammar1.clearHighlighting();
        logState("start");
        
        SortedSet<Symbol> setOfNonTerminals = new TreeSet<Symbol>();

        SortedSet<Symbol> nonterminals = new TreeSet<Symbol>();
        nonterminals.addAll(grammar1.getNonterminals());
        
        SortedSet<Symbol> nonterminals_e = new TreeSet<Symbol>();
        SortedSet<Symbol> nonterminals1 = new TreeSet<Symbol>(); //pro prunik      
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                grammar1.getSameLeftSideMap();
        Symbol start = grammar1.getStartNonterminal();
        Grammar gramm1 = new Grammar();
        if(parameter.equals("unusual")){
           logState("Start algorithm 3.2.");
           gramm1 = helpAlgs.eliminateUnreachableSymbols(grammar1);
           
           //System.out.println("****** \n gramatika po provedeni algoritmu 3.2"+gramm1.toString());
           if(gramm1 == null){
               logState("No elimination was made");
               logState("Grammar after alg 3.2 is same");
               grammar2.addAllRules(grammar1);
               
           }else{
               logState("Grammar after alg. 3.2 is not same");
               logState("Create new grammar");
               grammar2.addAllRules(gramm1);   
           }
           grammar2.setStartNonterminal(start);
           nonterminals = new TreeSet<Symbol>();
           nonterminals.addAll(grammar2.getNonterminals());
           logState("Start algorithm 3.1 with input G' and output N"+MathConstants.SUB_E);
           setOfNonTerminals = helpAlgs.isLanguageNotEmpty(grammar2);
           logState("After alg. 3.1 N_e = {"+setOfNonTerminals.toString()+"}");
        }
        //je jazyk prazdny? tzn. nevytvori se zadny retezec terminalnich symbolu
        //znamena ze jazyk neni neprazdny a nema tedy smysl dal pokracovat
        //kontrola na prázdnost jazyka
        //tady by to mělo být krok po kroku a nebo takhle?
        else if(parameter.equals("classic")){
            logState("Start algorithm 3.1 with input G and output N"+MathConstants.SUB_E);
            setOfNonTerminals = helpAlgs.isLanguageNotEmpty(grammar1);
            
        }
        //System.out.println("+++++++++ \n množina neterminálu"+setOfNonTerminals.toString());
        if(setOfNonTerminals == null || setOfNonTerminals.isEmpty()){
            logState("Language is empty");
        }else{
            logState("After alg 3.1 is N_e = {"+setOfNonTerminals.toString()+"}");
            logState("Language is not empty. Create G1 for algorithm 3.2.");
            logState("Let G"+MathConstants.SUB_ONE+"=(N"+MathConstants.INTERSECTION+
                    "N"+MathConstants.SUB_E+", "+MathConstants.SIGMA+", P"+
                    MathConstants.SUB_ONE+", "+start.getName()+") "+
                    ", kde P"+MathConstants.SUB_ONE+"=P"+MathConstants.INTERSECTION+
                    "(N"+MathConstants.SUB_E+MathConstants.MULTIPLIC+"N"+
                    MathConstants.SUB_E+MathConstants.UNION+MathConstants.SIGMA+
                    ")*) \n");
            
            nonterminals_e = setOfNonTerminals;
            if(!nonterminals.equals(nonterminals_e)){
                logState("Set of all nonterminals is N:"+nonterminals.toString() 
                        +" se nerovná množině N_e:" +nonterminals_e);
                logState("Creating intersection  N"+MathConstants.SUB_ONE+"=N"+MathConstants.INTERSECTION+
                        "N"+MathConstants.SUB_E);
                //udelame prunik N a N_e - vysledek zjistovani
                for(Symbol symb : nonterminals_e){
                    if(nonterminals.contains(symb)){
                        nonterminals1.add(symb);
                    }
                }
                logState("Set N"+MathConstants.SUB_ONE+" = {"+nonterminals1+"}");
                //P1 = P /prunik (N_e x (N_e /sjednoceno %sigma)*)

                logState("Createing intersection P"+MathConstants.SUB_ONE+"=P"
                        +MathConstants.INTERSECTION+"(N"
                        +MathConstants.SUB_E+MathConstants.MULTIPLIC+"N"
                        +MathConstants.SUB_E+MathConstants.UNION
                        +MathConstants.SIGMA+ ")*)");
                boolean control = true;

                grammar2.removeAllRules();
                for(Symbol symb : nonterminals1){
                    ProductionRuleSide helpLeftNonTerminal = new ProductionRuleSide();
                    helpLeftNonTerminal.addSymbol(symb);
                    if(rules.containsKey(helpLeftNonTerminal)){
                        List<ProductionRuleSide> oneSetOfRules =
                            rules.get(helpLeftNonTerminal);

                        for (ProductionRuleSide field : oneSetOfRules) {
                            for (Symbol s : field.getSymbols()) {
                                //nesmime zapomenout ze pravidla jsou tvorena
                                //jak terminaly, tak neterminaly, tak i EPSILONEM
                                if (s.isTerminal() || nonterminals1.contains(s) ||
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
                                ProductionRuleSide newL = new ProductionRuleSide(helpLeftNonTerminal);
                                ProductionRules newRule = 
                                        new ProductionRules(newL, rightHandSide);
                                grammar2.addRule(newRule);
                                grammar1.setFgColorToProd(Color.RED, newRule);
                                logState("přidáváme pravidlo "+newRule.toString());
                            }
                            control = true;
                        }
                    }
                }
            }else {
                logState("Set of previous nonterminal "+nonterminals.toString() 
                        +" is equivalent to new set N_e:" +nonterminals_e);
                grammar2.addAllRules(grammar1);
                logState("Grammar is same");
            }
           
            grammar2.setStartNonterminal(start);
            
            logState("\nStart algorithm 3.2");
            //spustime alg 3.2
           
           gramm1 = helpAlgs.eliminateUnreachableSymbols(grammar2);
           //System.out.println("****** \n"+gramm1.toString());
           if(gramm1 == null){
               logState("No elimination was made");
               logState("Grammar after alg. 3.2 is same as grammar after alg. 3.1.");
           }else{
               logState("Grammar after alg. 3.2 is different than grammar after alg. 3.1.");
               grammar2.removeAllRules();
               grammar2.addAllRules(gramm1); 
               logState("new Grammar was creating");
                             
           }
           
        }
        logState("\ndone");
    }
//    
    @Override
    public String checkInputRepresentation() {
        if(!grammar1.isCorrect()){
           return "Grammar is not correct."; 
        }
        if(!grammar1.isContextFree()) {
            return "Grammar is not context free.";
        }
        if(helpAlgs.isLanguageNotEmpty(grammar1) == null){
            return "Language L(G) is empty";
        }
        
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        if(inputParameters[0] != null){
            String param = inputParameters[0].trim();
            if(param.equals("unusual")) {
                parameter = param;
            }else{
                parameter = "classic";
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
