/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.Symbol;
import jgaf.lrextension.WString;

/**
 *
 * @author g
 */
public class ParserSimCalc {

    private Map<Integer, HashMap<Symbol, Integer>> gotoRep;
    private WString word;
    private Grammar g;
    private Map<Integer, HashMap<WString, SameActionItems>> actionRep;
    private ArrayList<ParseStep> simSteps;
    private int k;

    public ParserSimCalc(ParserTablesCalc ptc,
                         Grammar g) {
        this.g = g;
        gotoRep = ptc.getGotoRep();
        actionRep = ptc.getActionRep();
        k = ptc.getK();

    }

    public void calc(WString word) {
        simSteps = new ArrayList<ParseStep>();
        ParseStep currentStep = new ParseStep(word,k);
        boolean error = false;
        boolean conflict = false;
        boolean done = false;
        while (!error && !conflict && !currentStep.getTopStackSymbol().equals(g.getStartNonterminal())) {
            simSteps.add(currentStep);
            int currntState = currentStep.getState();
            WString lookAhead = currentStep.getKtoRead();
            if (actionRep.get(currntState).containsKey(lookAhead)) {
                SameActionItems actionItems = actionRep.get(currntState).get(lookAhead);
                switch (actionItems.getAction()) {
                    case SameActionItems.REDUCE: {

                        if (actionItems.reduceBy().getLeftHandSide().containsSymbol(g.getStartNonterminal())){
                            if ( currentStep.getInput().isEmpty()) {
                            currentStep.setNextAction(ParseStep.PAction.ACCEPT);
                        } else {
                            currentStep.setNextAction(ParseStep.PAction.MOREINPUTERR); //lr(0)}
                            }
                        }else{
                            
                            currentStep.setNextAction(ParseStep.PAction.REDUCE);
                        }

                        currentStep = createReducedStep(currentStep, actionItems.reduceBy());
                        break;
                    }
                    case SameActionItems.SHIFT: {

                        if (gotoRep.get(currntState).containsKey(currentStep.getFirstToRead())) {
                            int toState = gotoRep.get(currntState).get(currentStep.getFirstToRead());
                            currentStep.setNextAction(ParseStep.PAction.SHIFT);
                            currentStep = createShiftedStep(currentStep, toState);

                        } else {
                            error = true;
                            currentStep.setNextAction(ParseStep.PAction.SHIFTERR); //LR0
                        }
                        break;
                    }
                    default: {
                        conflict = true;
                        currentStep.setNextAction(ParseStep.PAction.CONFLICT);
                    }
                }
            } else {
                error = true;
                currentStep.setNextAction(ParseStep.PAction.ERRORR);
            }
        }

    }

    @Override
    public String toString() {
        return "ParserSimCalc{" + "simulation=" + simSteps + '}';
    }

    public ParseStep createShiftedStep(ParseStep step,
                                       int stateToShift) {

        List<Integer> newAuto = step.getAuto();
        newAuto.add(stateToShift);
        WString newInput = step.getInput();
        List<Symbol> newStack = step.getStack();
        newStack.add(newInput.get(0));
        newInput.remove(0);
        return new ParseStep(newAuto, newStack, newInput, step.getRules(),k);
    }

    public ParseStep createReducedStep(ParseStep step,
                                       ProductionRules rule) {
        int ruleLenght = 0;
        if (!rule.getRightHandSide().getRules().get(0).isEpsilon()) {
            ruleLenght = rule.getRightHandSide().getRules().get(0).size();
        }

        List<Integer> newAuto = (step.getAuto().subList(0, step.getAuto().size() - ruleLenght));
        WString newInput = step.getInput();
        List<Symbol> newStack = new ArrayList<Symbol>(step.getStack().subList(0, step.getStack().size() - ruleLenght));

        Symbol topNewStack = rule.getLeftHandSide().getFirst();
        int newState = gotoFrom(newAuto.get(newAuto.size() - 1), topNewStack);
        
        newAuto.add(newState);


        newStack.add(topNewStack);
        List<ProductionRules> newRules = step.getRules();
        newRules.add(rule);
        return new ParseStep(newAuto, newStack, newInput, newRules,k);
    }

//    private int calcNextAction(ParseStep step) {
//        int state = step.getState();
//        WString nextKSymbols = step.getKtoRead(k);
//
//        if (actionRep.get(state).containsKey(nextKSymbols)){
//        //its not blank in the PAction table ==cant be error
//        SameActionItems actionItems = actionRep.get(state).get(nextKSymbols);
//                switch (actionItems.getAction()) {
//                    case SameActionItems.REDUCE: {}
//        }
//            
//        return actionRep.get(state).get(lookAhead).getAction();
//
//    }
    private int gotoFrom(int state,
                         Symbol symbol) {
        if (!gotoRep.containsKey(state) || !gotoRep.get(state).containsKey(symbol)) {
            return state;
        }

        return gotoRep.get(state).get(symbol);
    }

    public ArrayList<ParseStep> getSimSteps() {
        return simSteps;
    }
}