package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
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

public class TransformToCNF extends DefaultProcedure {

    private Grammar grammar1;
    private Grammar grammar2;
    private FAalgorithms helpAlgs = new FAalgorithms();
    private String leftSymbol;
    private String rightSymbol;
    private String leftTermSymbol;
    private String rightTermSymbol;
    private ProductionRuleSide nonterminal;
    private boolean def = false;

    public TransformToCNF() {
    }


 public void startProcedure() {
        grammar2.clearHighlighting();
        logState("start");
        

        
        List<ProductionRules> rules = new ArrayList<ProductionRules>();
        Set<Symbol> newNfromTerm = new HashSet<Symbol>();
        ProductionRules newRule = new ProductionRules();
        ProductionRuleSide newRuleHelp = new ProductionRuleSide();
        Symbol start = grammar1.getStartNonterminal();
        if(!nonterminal.getSymbols().get(0).getName().equals("def")){
            rules.addAll(grammar1.getProductionsOfGivenNonterminal(nonterminal));            
        }else{
            rules.addAll(grammar1.getProductionRules());
        }
        
        for (ProductionRules oneRule : rules) {
            
            ProductionRulesSide rightHandSide = new ProductionRulesSide();
            rightHandSide = oneRule.getRightHandSide();
            ProductionRuleSide leftHandSide = new ProductionRuleSide();
            leftHandSide = (ProductionRuleSide) oneRule.getLeftHandSide().clone();
            List<ProductionRuleSide> rightHandSideList = new ArrayList<ProductionRuleSide>();
            rightHandSideList.addAll(rightHandSide.getRules());
            List<ProductionRules> colored = 
                                grammar1.getProductionsOfGivenNonterminal(leftHandSide);
            for(ProductionRuleSide rightHandSideOneRule : rightHandSideList){
                grammar2.clearHighlighting();
                grammar1.clearHighlighting();
                logState("Testing rule "+leftHandSide.toString()+" -> "+rightHandSideOneRule.toString());
                List<Symbol> testedRule = new ArrayList<Symbol>();
                testedRule.addAll(rightHandSideOneRule.getSymbols()); 
                
                for(ProductionRules ruleToColor : colored){
                    if(ruleToColor.getRightHandSide().getRules().contains(rightHandSideOneRule)){
                        ruleToColor.setFgColor(Color.RED);
                    }
                }
                
                if(grammar1.isStartToEpsRule(leftHandSide, rightHandSideOneRule)){
                    logState("Rule has form "+leftHandSide.toString()+" -> "+rightHandSideOneRule.toString());
                    
                    ProductionRuleSide newL = new ProductionRuleSide(leftHandSide);
                    newRule = new ProductionRules(newL, new ProductionRulesSide(rightHandSideOneRule));
                    grammar2.addRule(newRule);
                    newRule.setFgColor(Color.RED);
                    logState("Add rule "+newRule.toString());
                    continue;
                }
                if(testedRule.size() == 1 && testedRule.get(0).isTerminal()){
                    logState("Rule has form "+leftHandSide.toString()+" -> "+rightHandSideOneRule.toString());
                    
                    ProductionRuleSide newL = new ProductionRuleSide(leftHandSide);
                    newRule = new ProductionRules(newL, new ProductionRulesSide(rightHandSideOneRule));
                    
                    grammar2.addRule(newRule);
                    newRule.setFgColor(Color.RED);
                    logState("Add rule "+newRule.toString());
                    continue;
                }
                if(testedRule.size() == 2){
                    if(testedRule.get(0).isNonterminal() && 
                            testedRule.get(1).isNonterminal()){
                        logState("Rule has form "+leftHandSide.toString()+" -> "+rightHandSideOneRule.toString());
                        ProductionRuleSide newL = new ProductionRuleSide(leftHandSide);
                        newRule = new ProductionRules(newL, new ProductionRulesSide(rightHandSideOneRule));
                        grammar2.addRule(newRule);
                        newRule.setFgColor(Color.RED);
                        logState("Add rule "+newRule.toString());
                    }else {
                        Symbol x1 = testedRule.get(0);
                        Symbol x2 = testedRule.get(1);

                        Symbol y1 = new Symbol();
                        Symbol y2 = new Symbol();
                        if(x1.isTerminal()){
                            //způsob vytvareni mame dany, takze i kdyz pravidlo nepridame
                            //rovnou, tak i pozdeji budou dana pravidla stejna
                            newNfromTerm.add(x1);
                            y1.setName(leftTermSymbol+x1+rightTermSymbol);
                            y1.setType(1);
                        }else{
                            y1 = x1;
                        } 
                        if(x2.isTerminal()){
                            newNfromTerm.add(x2);
                            y2.setName(leftTermSymbol+x2+rightTermSymbol);
                            y2.setType(1);
                        }else{
                            y2 = x2;
                        }
                        //zajistíme, že je pravidlo vždy prazdne
                        newRule = new ProductionRules();
                        newRuleHelp = new ProductionRuleSide();
                        newRule.setLeftHandSide(oneRule.getLeftHandSide());
                        newRuleHelp.addSymbol(y1);
                        newRuleHelp.addSymbol(y2);
                        newRule.addToRightHandSide(newRuleHelp);
                        grammar2.addRule(newRule);
                        newRule.setFgColor(Color.RED);
                        logState("Add rule "+newRule.toString());
                        
                    }
                    continue;
                }
                if(testedRule.size()>2){
                    List<Symbol> helpRule = new ArrayList<Symbol>();
                    helpRule.addAll(testedRule);

                    ProductionRuleSide leftSideSymb = new ProductionRuleSide();
                    leftSideSymb = oneRule.getLeftHandSide();

                    //když upravujeme pravidlo tak to musíme vždy udělat (n-1)x, 
                    //protoze na konci vzdy musi zbyt pravidlo delky 2;
                    for(int i=0; i<(testedRule.size()-1); i++){
                        Symbol x1 = helpRule.get(0);
                        Symbol y1 = new Symbol();
                        Symbol y2 = new Symbol();

                        //využije se pro pravidlo delky 2
                        Symbol x2 = helpRule.get(1);


                        if(x1.isTerminal()){
                            newNfromTerm.add(x1);
                            y1.setName(leftTermSymbol+x1+rightTermSymbol);
                            y1.setType(1);
                        }else{
                            y1 = x1;
                        } 
                        if(helpRule.size() == 2){
                            if(x2.isTerminal()){
                                newNfromTerm.add(x2);
                                y2.setName(leftTermSymbol+x2+rightTermSymbol);
                                y2.setType(1);
                            }else{
                                y2 = x2;
                            }
                            //zajistíme, že je pravidlo vždy prazdne
                            newRule = new ProductionRules();
                            newRuleHelp = new ProductionRuleSide();
                            newRule.setLeftHandSide(leftSideSymb);
                            newRuleHelp.addSymbol(y1);
                            newRuleHelp.addSymbol(y2);
                            newRule.addToRightHandSide(newRuleHelp);
                            grammar2.addRule(newRule);
                            newRule.setFgColor(Color.RED);
                            logState("Add rule"+newRule.toString());
                            grammar2.clearHighlighting();
                            break;
                        }
                        //vezmeme jen část od druheho symbolu do konce a s ni budeme
                        //pocitat v dalsi iteraci
                        helpRule.remove(0);
                        y2.setName(leftSymbol+helpAlgs.nameFromList(helpRule)+rightSymbol);
                        y2.setType(1);

                        newRule = new ProductionRules();
                        newRuleHelp = new ProductionRuleSide();
                        newRule.setLeftHandSide(leftSideSymb);
                        newRuleHelp.addSymbol(y1);
                        newRuleHelp.addSymbol(y2);
                        newRule.addToRightHandSide(newRuleHelp);
                        grammar2.addRule(newRule);
                        newRule.setFgColor(Color.RED);
                        logState("Add rule "+newRule.toString());
                        grammar2.clearHighlighting();

                        //změníme symbol pro levou stranu
                        leftSideSymb = new ProductionRuleSide();
                        leftSideSymb.addSymbol(y2);
                    }
                }
            }
        }
        //teď vytvořime nová pravidla pro všechny terminaly, které jsme upravovali
        logState("Creating rules from terminal symbols");
        for(Symbol term : newNfromTerm){
            Symbol nonterm = new Symbol(leftTermSymbol+term.getName()+rightTermSymbol,1);
            
            newRule = new ProductionRules();
            newRuleHelp = new ProductionRuleSide();
            newRule.addToLeftHandSide(nonterm);
            newRuleHelp.addSymbol(term);
            newRule.addToRightHandSide(newRuleHelp);
            grammar2.addRule(newRule);
            newRule.setFgColor(Color.RED);
            logState("Add rule "+newRule.toString());
            grammar2.clearHighlighting();
        }
        
        grammar2.setStartNonterminal(start);
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
            return "Grammar has "+MathConstants.EPSILON+"-productions.";
        }
        if(helpAlgs.isLanguageNotEmpty(grammar1) == null){
            return "Language created by this grammar is empty";
        }
        if(helpAlgs.eliminateUselessSymbols(grammar1) != null){
            return "Grammar has Useless productions.";
        }
        if(grammar1.hasUnitProduction()){
            return "Grammar has unit productions.";
        }
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        Map<ProductionRuleSide, List<ProductionRuleSide>> map = 
                                                grammar1.getSameLeftSideMap();
        if(leftSymbol == null || rightSymbol == null){
            return "Wrong creation of symbols in parameter 2";
        }
        
        if(leftTermSymbol == null || rightTermSymbol == null){
            return "Wrong creation of symbols in parameter 3";
        }
        if(nonterminal.getSymbols().get(0).getName().equals("def") ){
            return CHECK_OK;
        }
        
        if(map.keySet().contains(nonterminal)){
            return CHECK_OK;
        } else {
            return "Nonterminal is not on the left side of any rule";
        }
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        if ((inputParameters[0] == null || inputParameters[0].equals("")) &&
            (inputParameters[1] == null || inputParameters[1].equals("")) &&
            (inputParameters[2] == null || inputParameters[2].equals(""))) {
            nonterminal = new ProductionRuleSide();
            nonterminal.addNonterminal("def");
           
            leftSymbol = "<";
            rightSymbol = ">";
            
            leftTermSymbol = "<";
            rightTermSymbol = ">";
            
            def = true;
        } else {
        
        nonterminal = new ProductionRuleSide();
        if(inputParameters[0] != null){
            String parameter = inputParameters[0].trim();
            nonterminal.addNonterminal(parameter);
        }
        if(inputParameters[1] != null){
            String parameter = inputParameters[1].trim();
            if(parameter.equals("def")){
                leftSymbol = "<";
                rightSymbol = ">";
            }else if(parameter.length() == 2){
                leftSymbol = parameter.substring(0, 1);
                rightSymbol = parameter.substring(1);
            }
        }
        if(inputParameters[2] != null){
            String parameter = inputParameters[2].trim();
            if(parameter.equals("def")){
                leftTermSymbol = "<";
                rightTermSymbol = ">";
            }else if(parameter.length() == 1){
                System.out.println("symbol je "+parameter);
                leftTermSymbol = "";
                rightTermSymbol = parameter.substring(0);
            }else if(parameter.length() == 2){
                leftTermSymbol = parameter.substring(0, 1);
                rightTermSymbol = parameter.substring(1);
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
