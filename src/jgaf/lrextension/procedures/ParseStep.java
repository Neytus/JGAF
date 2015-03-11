/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import java.util.ArrayList;
import java.util.List;
import jgaf.lrextension.FiFoUtils;
import jgaf.lrextension.WString;
import jgaf.grammar.ProductionRule;
import jgaf.grammar.Symbol;

/**
 *
 * @author g
 */
public class ParseStep {
    private List<Integer> auto;
    private List<Symbol> stack;
    private WString input;
    private List<ProductionRule> rules;
    private PAction nextAction;
    private int k;
    public enum PAction {SHIFT, REDUCE, CONFLICT, ERRORR, ACCEPT ,SHIFTERR, MOREINPUTERR}
    

    public ParseStep() {
    }
    
    public ParseStep(List<Integer> auto,
                     List<Symbol> stack,
                     WString input,List<ProductionRule> rules,int k) {
        this.auto = auto;
        this.stack = stack;
        this.input = input;
        this.rules=rules;
        this.k=k;
    }

    public ParseStep(WString input,int k) {
        auto = new ArrayList<Integer>();
        auto.add(0);
        stack = new ArrayList<Symbol>();
        this.input = input;
        this.k=k;
        rules = new ArrayList<ProductionRule>();
    }
    
//    public ParseStep createShiftedStep(int stateToShift){
//     List<Integer> newAuto=new ArrayList<Integer>(auto);
//     newAuto.add(stateToShift);
//     WString newInput=new WString(input);
//     List<Symbol> newStack=new ArrayList<Symbol>(stack);
//     newStack.add( newInput.get(0));
//     newInput.remove(0);
//        return new ParseStep(newAuto,newStack,newInput,new ArrayList<ProductionRule>(rules));
//    }
    
//    public ParseStep createReducedStep(ProductionRule rule){
//    int ruleLenght=rule.getRightHandSide().size();
//    List<Integer> newAuto=new ArrayList<Integer>(auto.subList(0, auto.size() -ruleLenght));
//    WString newInput=new WString(input);
//    List<Symbol> newStack=new ArrayList<Symbol>(stack.subList(0, stack.size() -ruleLenght));
//    
//    newStack.add(rule.getLeftHandSide().getFirst());
//     List<ProductionRule> newRules=new ArrayList<ProductionRule>(rules);
//     newRules.add(rule);
//    return new ParseStep(newAuto, newStack, newInput, newRules);
//    }

    

    public int getState(){
    return auto.get(auto.size()-1);
    }
    
    public WString getKtoRead(){
        if (k==0) { 
            return new WString("eps.");  //epsilon            
        }
        if (input.size()==0) return new FiFoUtils().createEpsilonStr();
        
        if (k> input.size()) {
            return new WString(input);
        }   
    return new WString(input.subList(0, k));
    }

    public Symbol getFirstToRead(){
    if (input.size()==0) {
            return new Symbol();
        }
    return input.get(0);

}
    
    
    public Symbol getTopStackSymbol(){
            if (stack.isEmpty()) return new Symbol("$", Symbol.TERMINAL);
        return stack.get(stack.size()-1);
                    }

    @Override
    public String toString() {
        return "ParseStep{" + "auto=" + auto + ", stack=" + stack + ", input=" + input + ", rules=" + rules + '}';
    }
    
    
    
    public List<Integer> getAuto() {
        return new ArrayList<Integer>(auto);
    }

    public List<Symbol> getStack() {
        return new ArrayList<Symbol>(stack);
    }

    public WString getInput() {
        return new WString(input);
    }

    public List<ProductionRule> getRules() {
        return new ArrayList<ProductionRule>(rules);
    }

    public PAction getNextAction() {
        return nextAction;
    }

    public void setNextAction(PAction nextAction) {
        this.nextAction = nextAction;
    }

    
    public int getPreviouseState(){
        return getAuto().get(getAuto().size()-2);
    }
    
}




