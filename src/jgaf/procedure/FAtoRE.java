package jgaf.procedure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import jgaf.Constants.MathConstants;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.regex.Operation;
import jgaf.regex.RegularExpresionType;
import jgaf.regex.RegularExpression;
import jgaf.regex.WrongExpressionException;


public class FAtoRE extends DefaultProcedure {

    private RegularExpression expresion;
    private Automaton automaton;
    private List<State> input;
//    private SubIndexHash a = new ModuloSubIndexHash("e",3);

    public FAtoRE() {
    }

    public void startProcedure() {
        automaton.clearHighlighting();
        
        logState("start");

        List<State> allStates = new ArrayList<State>();
        allStates.addAll(automaton.getStates());
        
        State initialState = automaton.getInitialState();
        List<State> acceptingStates = automaton.getAcceptingStates();

        //toto vytvoří iniciální stav 
        int x = 50;
        int y = 250;
        
        State stateInc = new State("<x>");
        stateInc.getVisualProperties().setCoordinates(x, y);
        automaton.addState(stateInc);
        stateInc.getVisualProperties().setFillColor(Color.GREEN);
        logState("adding new state");
        automaton.setInitialState(stateInc);
        logState("set <x> to inicial");
        automaton.clearHighlighting();
        State stateFin = new State("<y>");
        stateFin.getVisualProperties().setCoordinates(x+350, y);
        stateFin.getVisualProperties().setFillColor(Color.RED);
        automaton.addState(stateFin);
        stateFin.getVisualProperties().setFillColor(Color.GREEN);
        logState("adding new state");
        automaton.addAcceptingState(stateFin);
        logState("Add <y> to accepting states");
        automaton.clearHighlighting();
        String label = MathConstants.EPSILON;
        
        Transition transitionInc = new Transition(stateInc, initialState, label);
        transitionInc.getVisualProperties().setStrokeColor(Color.RED);
        transitionInc.getVisualProperties().setFontColor(Color.GREEN);
        if(transitionInc.isReflexive()) {
            transitionInc.getVisualProperties().setCurveFactor(Math.PI/2);
        } else {
            transitionInc.getVisualProperties().setCurveFactor(1);
        }
        automaton.addTransition(transitionInc);
        logState("Add transition for initial state" + " -----> " + transitionInc.toString());
        automaton.clearHighlighting();
        
        for(State accept : acceptingStates){
            Transition transitionAcc = new Transition(accept, stateFin, label);
            accept.setAccepting(false);
            transitionAcc.getVisualProperties().setStrokeColor(Color.RED);
            transitionAcc.getVisualProperties().setFontColor(Color.GREEN);
            if(transitionAcc.isReflexive()) {
                transitionAcc.getVisualProperties().setCurveFactor(Math.PI/2);
            } else {
                transitionAcc.getVisualProperties().setCurveFactor(1);
            }
            automaton.addTransition(transitionAcc);
            logState("Add transition for accepting state" + " -----> " + transitionAcc.toString());
            automaton.clearHighlighting();
        }
        int i = 0;
        while(!allStates.isEmpty()){
            
            int indexOfPosib = 0;
            if(input != null && i<input.size()){
                indexOfPosib = allStates.indexOf(input.get(i));
            }
            State stateToRemove = allStates.get(indexOfPosib);
            logState("Delete state " +stateToRemove.toString());
            Transition sameState = automaton.getTransitionFromTo(stateToRemove, stateToRemove);
            List<Transition> toState = automaton.getTransitionTo(stateToRemove);
            //odstraníme hranu cyklickou hranu
            toState.remove(sameState);
            List<Transition> fromState = automaton.getTransitionFrom(stateToRemove);
            //odstraníme hranu cyklickou hranu
            fromState.remove(sameState);
            String sameStateLabels = null;
            RegularExpression newRegex = null;
            if(sameState != null){
                 sameStateLabels = concateLabels(sameState)+"*";
            }
            
            for(Transition to : toState){
               
                State leftState = to.getFromState();
                String toStateLabels = concateLabels(to);
                String leftPart = null;
                if(sameStateLabels!=null){
                    leftPart = toStateLabels+"."+sameStateLabels;
                }else{
                    leftPart = toStateLabels;
                }
                for(Transition from : fromState){
                    stateToRemove.getVisualProperties().setStrokeColor(Color.RED);
                    stateToRemove.getVisualProperties().setFontColor(Color.GREEN);
                    to.getVisualProperties().setStrokeColor(Color.RED);
                    to.getVisualProperties().setFontColor(Color.GREEN);
                    from.getVisualProperties().setStrokeColor(Color.RED);
                    from.getVisualProperties().setFontColor(Color.GREEN);
                    if (sameState != null){
                        sameState.getVisualProperties().setStrokeColor(Color.RED);
                        sameState.getVisualProperties().setFontColor(Color.GREEN);
                    }
                    State rightState = from.getToState();
                    String fromStateLabels = concateLabels(from);
                    String newLabel = leftPart+"."+fromStateLabels;
                    try {
                       newRegex = new RegularExpression(newLabel);
                    }  catch (WrongExpressionException ex) {
                       System.out.println("err" + ex.getMessage());
                    }
                    System.out.println(newLabel);
                    //přidáme nový stav
                    
                    Transition transition = new Transition(leftState, rightState, newRegex.toString());                    
                    transition.getVisualProperties().setStrokeColor(Color.YELLOW);
                    transition.getVisualProperties().setFontColor(Color.BLUE);
                    if(transition.isReflexive()) {
                        transition.getVisualProperties().setCurveFactor(Math.PI/2);
                    } else {
                        transition.getVisualProperties().setCurveFactor(1);
                    }
                    automaton.addTransition(transition);
                    logState("Add new transition" + " -----> " + transition.toString());
                    automaton.clearHighlighting();
                }
                
            }
            allStates.remove(stateToRemove);
            automaton.removeState(stateToRemove);
            automaton.clearHighlighting();
            i++;
        }
        logState("Adapt transition from initial to accepting state to concise form");
        Transition finalTrans = automaton.getTransitionFromTo(stateInc, stateFin);
        if(finalTrans != null) {

            String finalString = concateLabels(finalTrans);
            //může tam být jen jeden přechod původně
            RegularExpression newReg = null;
            try {
                expresion.setName(finalString);
                
               newReg = new RegularExpression(finalString);
               expresion = (RegularExpression) newReg.clone();
               logState("upraven expression "+expresion.toString());
               //expresion.set
               //System.out.println("výpis ? "+expresion.toString());
            }  catch (WrongExpressionException ex) {
               System.out.println("err" + ex.getMessage());
            }
            Transition transition = new Transition(stateInc, stateFin, newReg.writeConcise());                    
            transition.getVisualProperties().setStrokeColor(Color.YELLOW);
            transition.getVisualProperties().setFontColor(Color.BLUE);
            if(transition.isReflexive()) {
                transition.getVisualProperties().setCurveFactor(Math.PI/2);
            } else {
                transition.getVisualProperties().setCurveFactor(1);
            }
            automaton.addTransition(transition);
            logState("Add new transition" + " -----> " + transition.toString());
            automaton.clearHighlighting();
            logState("Regular expression is:");
            logState(expresion.writeConcise());
        }
        
        logState("\ndone");
    }
    
    public String concateLabels(Transition transition){
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int i = 0;
        for(String labels : transition.getLabels()){
           sb.append(labels);
           if(i!=transition.getLabels().size()-1){
               sb.append("+");
           }
           i++;
        }
        sb.append(")");
        return sb.toString();
    }
    
    
    @Override
    public String checkInputRepresentation() {
        if(input == null) return CHECK_OK;
        List<State> allStates = automaton.getStates();
        for(State state : input){
            if(!allStates.contains(state)){
                return "State "+state.toString()+" is not in Automaton";
            }
        }
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        input = new ArrayList<State>();
        
        if(inputParameters[0] != null){
            String param = inputParameters[0].trim();
            if(param.equals("def")){
                input = null;
            }else{
                String[] posib = param.split(",");
                for(String onePart : posib){
                    String name = onePart.trim();
                    State newState = new State(name);
                    input.add(newState);
                }
            }
        }
    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
       expresion = (RegularExpression) outputRepresentation;
    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        automaton = (Automaton) inputRepresentations[0];
    }
    
    
}
