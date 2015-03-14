package jgaf.procedure;

import java.awt.Color;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;

public class RegularGrammarToFA extends DefaultProcedure {

    private Grammar grammar;
    private Automaton automaton;

    public RegularGrammarToFA() {
    }
    
    public void startProcedure() {
        automaton.clearHighlighting();
         
        logState("start");
        int x = 40;
        int y = 160;
        for (Symbol nonterminal : grammar.getNonterminals()) {
            State state = new State(nonterminal.getName());
            x += 150;
            if (x > 600) {
                x = 150;
                y += 150;
            }
            state.getVisualProperties().setCoordinates(x, y);
            automaton.addState(state);
            state.getVisualProperties().setFillColor(Color.GREEN);
            logState("adding new state");
            automaton.clearHighlighting();
            if (grammar.isStartNonterminal(nonterminal)) {
                automaton.setInitialState(state);
                state.getVisualProperties().setFillColor(Color.ORANGE);
                logState("setting " + state.getName() + " as initial state");
                automaton.clearHighlighting();
                if (grammar.containsStartToEpsRule()) {
                    automaton.addAcceptingState(state);
                    state.getVisualProperties().setFillColor(Color.BLUE);
                    logState("setting " + state.getName() + " as accepting beacuse of the rule S -> eps");
                    automaton.clearHighlighting();
                }
            }
        }
        State newState = new State("$K$");
        automaton.addState(newState);
        newState.getVisualProperties().setFillColor(Color.GREEN);
        newState.getVisualProperties().setCoordinates(60,60);
        logState("adding special state $K$");
        automaton.clearHighlighting();
        newState.getVisualProperties().setFillColor(Color.ORANGE);
        automaton.addAcceptingState(newState);
        logState("setting $K$ as accepting");
        automaton.clearHighlighting();
        
        for (ProductionRules rule : grammar.getProductionRules()) {
            ProductionRuleSide leftHandSide = rule.getLeftHandSide();
            ProductionRulesSide rightHandSide = rule.getRightHandSide();
            for(ProductionRuleSide oneRule : rightHandSide.getRules()){
                if(grammar.isStartToEpsRule(leftHandSide, oneRule)) {
                    continue;
                }
                State from = automaton.getStateByName(leftHandSide.getSymbols().get(0).getName());
                String label = oneRule.getSymbols().get(0).getName();
                if(oneRule.size() == 2) {
                    rule.setFgColor(Color.RED);                
                    State to = automaton.getStateByName(oneRule.getSymbols().get(1).getName());
                    Transition transition = new Transition(from, to, label);
                    transition.getVisualProperties().setStrokeColor(Color.RED);
                    transition.getVisualProperties().setFontColor(Color.GREEN);
                    if(transition.isReflexive()) {
                        transition.getVisualProperties().setCurveFactor(Math.PI/2);
                    } else {
                        transition.getVisualProperties().setCurveFactor(1);
                    }
                    automaton.addTransition(transition);
                    logState(rule.getLeftHandSide().toString()+" -> "+oneRule.toString() + " -----> " + transition.toString());
                    rule.setFgColor(Color.BLACK);
                    automaton.clearHighlighting();
                } else {
                    rule.setFgColor(Color.RED);                
                    Transition transition = new Transition(from, newState, label);
                    transition.getVisualProperties().setStrokeColor(Color.RED);
                    transition.getVisualProperties().setFontColor(Color.GREEN);
                    automaton.addTransition(transition);
                    logState(rule.getLeftHandSide().toString()+" -> "+oneRule.toString() + " -----> " + transition.toString());
                    automaton.clearHighlighting();
                    rule.setFgColor(Color.BLACK);
                }
            }
        }
        logState("done");
    }
    
    @Override
    public String checkInputRepresentation() {
        if(!grammar.isRegular()) {
            return "Grammar is not regular.";
        }
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
       automaton = (Automaton) outputRepresentation;
    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        grammar = (Grammar) inputRepresentations[0];
    }
}
