/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.Color;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.Transition;
import jgaf.grammar.Grammar;
import jgaf.grammar.Nonterminal;
import jgaf.grammar.ProductionRule;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.Terminal;

/**
 *
 * @author hanis
 */
public class FAtoRegularGrammar extends DefaultProcedure {

    private Grammar grammar;
    private Automaton automaton;


    public FAtoRegularGrammar() {
    }


    public void clearHighlighting() {
        grammar.clearHighlighting();
        automaton.clearHighlighting();
    }
    public void startProcedure() {
        clearHighlighting();
        logState("start");

        grammar.setStartNonterminal(new Nonterminal(automaton.getInitialState().getName()));
        for (Transition transition : automaton.getTransitions()) {
            transition.getVisualProperties().setStrokeColor(Color.RED);
            transition.getFromState().getVisualProperties().setFillColor(Color.GREEN);
            transition.getToState().getVisualProperties().setFillColor(Color.GREEN);
            for (String label : transition.getLabels()) {
                ProductionRules production = new ProductionRules(
                        new Nonterminal(transition.getFromState().getName()),
                        new Terminal(label),
                        new Nonterminal(transition.getToState().getName()));
                grammar.addRule(production);
                transition.getVisualProperties().setStrokeColor(Color.RED);
                transition.getFromState().getVisualProperties().setFillColor(Color.ORANGE);
                transition.getToState().getVisualProperties().setFillColor(Color.ORANGE);
                
                production.setFgColor(Color.RED);
                logState("adding rule " + production.toString());
                clearHighlighting();
            }
            if (transition.getToState().isAccepting()) {
                transition.getToState().getVisualProperties().setFillColor(Color.ORANGE);
                for (String label : transition.getLabels()) {
                    ProductionRules production = new ProductionRules(
                            new Nonterminal(transition.getFromState().getName()),
                            new Terminal(label));
                    grammar.addRule(production);

                transition.getVisualProperties().setStrokeColor(Color.RED);
                transition.getFromState().getVisualProperties().setFillColor(Color.ORANGE);
                transition.getToState().getVisualProperties().setFillColor(Color.BLUE);

                production.setFgColor(Color.RED);
                logState("adding rule " + production.toString());
                clearHighlighting();
                }
            }            
        }
        if (automaton.getInitialState().isAccepting()) {
            ProductionRules production = new ProductionRules(
                    new Nonterminal(automaton.getInitialState().getName()));
            grammar.addRule(production);
            production.setFgColor(Color.RED);
            automaton.getInitialState().getVisualProperties().setFillColor(Color.GREEN);
            logState("adding rule " + production.toString());
            clearHighlighting();
        }

        logState("done");
    }
    
    @Override
    public String checkInputRepresentation() {        
        int type = automaton.getType();
        if(type == Automaton.UFA) {
            return "Input automaton is not correct.";
        } else if(type == Automaton.EFA) {
            return "Input automaton contains epsilon steps.";
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
        grammar = (Grammar) outputRepresentation;
    }


    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        automaton = (Automaton) inputRepresentations[0];
    }
}
