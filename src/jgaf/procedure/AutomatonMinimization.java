/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jgaf.Constants.MathConstants;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.helpClasses.RomanNumeral;
import jgaf.helpClasses.StateColors;
/**
 *
 * @author hanis
 */
public class AutomatonMinimization extends DefaultProcedure {

    private Automaton automaton1;
    private Automaton automaton2;


    public AutomatonMinimization() {
        
    }


    public void clearHighlighting() {
        automaton1.clearHighlighting();
        automaton2.clearHighlighting();
    }
    public void startProcedure() {
        clearHighlighting();
        logState("start");
        //komplet celá tabulka vnitřni pole je vždy jeden blok
        List<List<State>> tableActual = new ArrayList<>();
        List<List<State>> tablePrevious = new ArrayList<>();
        //příznaky odkud kam, mnitřní pole bude mít vždy jen 2 prvky
        List<List<Integer>> marks = new ArrayList<>();
        
        
        List<State> states = new ArrayList<>();
        List<State> statesForTable = new ArrayList<>();
        states.addAll(automaton1.getStates());
        statesForTable.addAll(states);
        
        logState("Initializing");
        List<State> acceptStates = new ArrayList<>();
        List<State> otherStates = new ArrayList<>();
        for(State state : states){
            if(state.isAccepting()){
                acceptStates.add(state);
            }else{
                otherStates.add(state);
            }
        }
        tableActual.add(otherStates);
        tableActual.add(acceptStates);
        //změny from to
        for(List<State> oneBlock : tableActual){
            for(State state : oneBlock){
                
            }
        }
        //obarvíme stavy podle tabulky
        colorState(tableActual);
        logState("Relation "+MathConstants.IDENT+"0 is:");
        logState(showTable(tableActual));
        
        //teď budeme kontrolovat a měnit tabulku
        int i = 0;
        while(!tableActual.equals(tablePrevious)){
            logState("Relations are not equal yet");
            tablePrevious.clear();
            tablePrevious.addAll(tableActual);
            tableActual.clear();
            marks.clear();
            int blockCount = 0;
            for(List<State> block : tablePrevious){
                for(State state : block){

                    List<Integer> test = fromTo(state, tablePrevious);

                    if(marks.contains(test)){
                        int index = marks.indexOf(test)+blockCount;
                        tableActual.get(index).add(state);
                    }else{
                        List<State> newBlock = new ArrayList<>();
                        newBlock.add(state);
                        tableActual.add(newBlock);
                        marks.add(test);
                    }
                }
                blockCount += marks.size();
                marks.clear();
            }
            i++;
            colorState(tableActual);
            logState("Relation "+MathConstants.IDENT+i+" is:");
            logState(showTable(tableActual));
        }
        logState("Relation "+MathConstants.IDENT+i+" = "+MathConstants.IDENT+(i-1));
        logState("Show Reduct - new automat creating");
        //vytvářím stav
        int x = 0;
        int y = 50;
        for(int j = 0; j<tableActual.size();j++ ){
            RomanNumeral romanNumerals = new RomanNumeral(j+1);
            State state = new State(romanNumerals.getRomanNumber());
            x += 100;
            if (x > 500) {
                x = 100;
                y += 100;
            }
            state.getVisualProperties().setCoordinates(x, y);
            automaton2.addState(state);
            state.getVisualProperties().setFillColor(Color.GREEN);
            logState("adding new state");
            if(hasInitial(tableActual.get(j))) automaton2.setInitialState(state);
            if(hasAccepting(tableActual.get(j))) automaton2.addAcceptingState(state);
            automaton2.clearHighlighting();
        }
        
        logState("Create transitions");
        for(List<State> block : tableActual){
            Set<String> alphabet = automaton1.getAlphabet();
            for(State state : block){
                int index = tableActual.indexOf(block);
                RomanNumeral romanNumerals = new RomanNumeral(index+1); 
                State from = automaton2.getStateByName(romanNumerals.getRomanNumber());
                for(String literal : alphabet){
                    List<State> list = automaton1.getStatesFromUnder(state, literal);
                    State testedState = list.get(0);
                    for(List<State> testedBlock : tableActual){    
                        if(testedBlock.contains(testedState)){
                            index = tableActual.indexOf(testedBlock);
                            romanNumerals = new RomanNumeral(index+1);           
                            State to = automaton2.getStateByName(romanNumerals.getRomanNumber());
                            Transition transition = new Transition(from, to, literal);
                            transition.getVisualProperties().setStrokeColor(Color.RED);
                            transition.getVisualProperties().setFontColor(Color.GREEN);
                            if(transition.isReflexive()) {
                                transition.getVisualProperties().setCurveFactor(Math.PI/2);
                            } else {
                                transition.getVisualProperties().setCurveFactor(1);
                            }
                            automaton2.addTransition(transition);
                            logState("Add transition "+transition.toString());
                            automaton2.clearHighlighting();
                            break;
                        }
                    }
                }
            }
        }
        logState("Reduct M/"+MathConstants.IDENT+" is:");
        logState(showReduct());
        logState("done");
    }

    public boolean hasInitial(List<State> block){
        for(State state : block){
            if(state.isInitial()) return true;
        }
        return false;
    }
    
    public boolean hasAccepting(List<State> block){
        for(State state : block){
            if(state.isAccepting()) return true;
        }
        return false;
    }
    public List<Integer> fromTo(State state, List<List<State>> table){
        Set<String> alphabet = automaton1.getAlphabet();
        List<Integer> result = new ArrayList<Integer>() ;
        for(String literal : alphabet){
            //je DFA má pouze jeden prvek
            List<State> list = automaton1.getStatesFromUnder(state, literal);
            State testedState = list.get(0);

            for(List<State> testedBlock : table){
                if(testedBlock.contains(testedState)){
                    int index = table.indexOf(testedBlock);
                    result.add(index);
                }
            }
        }
        return result;
    }
    public static void colorState(List<List<State>> table){
        StateColors colors = new StateColors();
        int i=0;
        for(List<State> blocks : table){
            if(i >= colors.getColorsCount()){
                i=0;
            }
            for(State state : blocks){
                state.getVisualProperties().setFillColor(colors.getColor(i));
            }
            i++;
        }
    }
    
    public String showReduct(){
        List<State> states = automaton2.getStates();
        StringBuilder sb = new StringBuilder();
        Set<String> alphabet = automaton2.getAlphabet();
        int i=1;
        int spaces = 6;
        int whiteSymbols = spaces+automaton2.getLongestStateNameLength();
        sb.append(setSpaces(whiteSymbols)).append("|");
        for(String literal : alphabet){
            sb.append(setSpaces(spaces)).append(literal);
            sb.append(setSpaces(spaces-literal.length()+2)).append("|");
        }
        sb.append("\n");
        for(State state : states){
            String name = state.getName();
            sb.append(name).append(setSpaces(whiteSymbols-name.length())).append("|");
            for(String literal : alphabet){
                //je DFA má pouze jeden prvek
                List<State> list = automaton2.getStatesFromUnder(state, literal);
                State testedState = list.get(0);
                name = testedState.getName();
                sb.append(setSpaces(spaces));
                sb.append(name);
                sb.append(setSpaces(whiteSymbols-name.length())).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
    public String showTable(List<List<State>> table){
        StringBuilder sb = new StringBuilder();
        RomanNumeral romanNumerals = new RomanNumeral();
        Set<String> alphabet = automaton1.getAlphabet();
        int i=1;
        int spaces = 6;
        int whiteSymbols = spaces+automaton1.getLongestStateNameLength();
        sb.append(setSpaces(whiteSymbols)).append("|");
        for(String literal : alphabet){
            sb.append(setSpaces(spaces)).append(literal);
            sb.append(setSpaces(spaces-literal.length()+2)).append("|");
        }
        sb.append("\n");
        for(List<State> oneBlock : table){
            romanNumerals = new RomanNumeral(i);
            sb.append(romanNumerals.getRomanNumber()).append("\n");
            for(State state : oneBlock){
                String name = state.getName();
                sb.append(name).append(setSpaces(whiteSymbols-name.length()*2)).append("|");
                for(String literal : alphabet){
                    //je DFA má pouze jeden prvek
                    List<State> list = automaton1.getStatesFromUnder(state, literal);
                    State testedState = list.get(0);
                    
                    for(List<State> testedBlock : table){
                        if(testedBlock.contains(testedState)){
                            int index = table.indexOf(testedBlock);
                            romanNumerals = new RomanNumeral(index+1);
                            String roman = romanNumerals.getRomanNumber();
                            sb.append(setSpaces(spaces));
                            sb.append(roman);
                            sb.append(setSpaces(whiteSymbols-roman.length())).append("|");
                        }
                    }
                }
                sb.append("\n");
            }
            i++;
        }
        
        return sb.toString();
    }      
    
    public String setSpaces(int dec){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < dec; i++){
            sb.append(" ");
        }
        return sb.toString();
    }
    @Override
    public String checkInputRepresentation() {
        if(!automaton1.hasTotalTransitionFunction()){
            return "Automaton does not have total transition function";
        }
        switch(automaton1.getType()) {
            case Automaton.UFA: return "The automaton has no initial state.";
            case Automaton.EFA: return "The automaton is nondeterministic with epsilon steps, the transformation requires a deterministic automaton.";
            case Automaton.NFA: return "The automaton is nondeterministic, the transformation requires a deterministic automaton.";
            case Automaton.DFA: return Procedure.CHECK_OK;
            default: return Procedure.CHECK_NOT_OK;
        }
        
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
        automaton2 = (Automaton) outputRepresentation;
    }


    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        automaton1 = (Automaton) inputRepresentations[0];
    }
}
