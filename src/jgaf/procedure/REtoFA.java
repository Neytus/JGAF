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


public class REtoFA extends DefaultProcedure {

    private RegularExpression expresion;
    private Automaton automaton;
    private String parameter;
    private List<List<RegularExpression>> input;
    private List<List<String>> inputTest;
//    private SubIndexHash a = new ModuloSubIndexHash("e",3);

    public REtoFA() {
    }

    public void startProcedure() {
        
        automaton.clearHighlighting();
        logState("start");
        List<Transition> allTransitions = new ArrayList<Transition>();
        
        List<RegularExpression> oldInput = new ArrayList<RegularExpression>();
        List<RegularExpression> newInput = new ArrayList<RegularExpression>();
        List<List<RegularExpression>> allPosibilities = new ArrayList<List<RegularExpression>>();
        
       
        
        //toto vytvoří iniciální stav 
        int x = 50;
        int y = 100;
        
        State stateInc = new State("p");
        stateInc.getVisualProperties().setCoordinates(x, y);
        automaton.addState(stateInc);
        stateInc.getVisualProperties().setFillColor(Color.GREEN);
        logState("adding new state");
        automaton.setInitialState(stateInc);
        logState("set p to inicial");
        automaton.clearHighlighting();
        State stateFin = new State("q");
        stateFin.getVisualProperties().setCoordinates(x+250, y);
        stateFin.getVisualProperties().setFillColor(Color.RED);
        automaton.addState(stateFin);
        stateFin.getVisualProperties().setFillColor(Color.GREEN);
        logState("adding new state");
        automaton.addAcceptingState(stateFin);
        logState("Add q to accepting states");
        automaton.clearHighlighting();
        String label = expresion.toString();
        
        Transition transitionInc = new Transition(stateInc, stateFin, label);
        transitionInc.getVisualProperties().setStrokeColor(Color.RED);
        transitionInc.getVisualProperties().setFontColor(Color.GREEN);
        if(transitionInc.isReflexive()) {
            transitionInc.getVisualProperties().setCurveFactor(Math.PI/2);
        } else {
            transitionInc.getVisualProperties().setCurveFactor(1);
        }
        automaton.addTransition(transitionInc);
        allTransitions.add(transitionInc);
        logState(expresion.toString() + " -----> " + transitionInc.toString());
        
        if(expresion.getType() != RegularExpresionType.ATOMIC && 
                expresion.getType() != RegularExpresionType.EPSILON){
            oldInput.add(expresion);
        }
        
        
        //TODO - tady porovnám jestli některá možnost se shoduje s tím co zadal uživatel
        //jinak beru default
        int i = 0;
        int q = 0;
        y=200;
        while(!oldInput.isEmpty()){            
            //System.out.println("Jsem v cyklu i = "+i);
            List<RegularExpression> testedLine = null;
            if(input != null && q<input.size()){
                testedLine = input.get(q);
                logState("Input  is "+testedLine.toString());
            }
            
            allPosibilities = allPosibilitiesFinder(oldInput);
            int indexOfPosib = 0;
//            System.out.println(allPosibilities.toString()+" obsahuje ? "+testedLine.toString());
//            System.out.println(allPosibilities.contains(testedLine));}
            if(testedLine != null){
                if(allPosibilities.contains(testedLine)){
                    
                    logState("This move is posible");
                    indexOfPosib = allPosibilities.indexOf(testedLine);
                }else{
                    logState("This move is not posible. Continue classicly");
                }       
            }
            logState("index ="+indexOfPosib);
            Operation operation = null;
            newInput.clear();
            System.out.println(allPosibilities.toString());
            newInput = allPosibilities.get(indexOfPosib);
            int index = changePosition(oldInput, newInput);

//            System.out.println("Old input = "+oldInput.toString());
//            System.out.println("new input = "+newInput.toString());
//            System.out.println("index = "+index);
            operation = oldInput.get(index).getOperator();
            
            RegularExpression left = newInput.get(index);
            RegularExpression right = null;
            if(operation.getType()!=2){
                right = newInput.get(index+1);
            }
            
            //System.out.println("Známenko je "+operation.toString()+ " index změny je ="+index);
            Transition transition = allTransitions.get(index);
            
            State leftState = transition.getFromState();
            State rightState= transition.getToState();
//            logState("From ="+leftState.toString()+ " to "+rightState.toString());
//            logState("Remove transition "+transition.toString());
            
            String oldRegex = oldInput.get(index).toString();
            //logState("přechod pro odstranění je "+transition.toString());
//            List<Transition> help = automaton.getTransitions();
//            if(help != null) logState(help.toString());
            State from = transition.getFromState();
            State to = transition.getToState();
            transition.getVisualProperties().setStrokeColor(Color.YELLOW);
            transition.getVisualProperties().setFontColor(Color.BLUE);
            logState("Remove transition "+transition.toString());
            Transition allT = automaton.getTransitionFromTo(from,to);
//            logState("transition je "+allT.toString());
//            logState("Label pro odstranění je "+oldRegex);
            allT.removeLabel(oldRegex);
           // logState("allT po odstranění "+allT.toString());
            
            automaton.removeTransition(transition);
            allTransitions.remove(index);
            if(!allT.getLabels().isEmpty()){
                automaton.addTransition(allT);
            }
            automaton.clearHighlighting();
            logState("Add new transitions");
            //TODO budu muset zase řešit všechny možnosti, ale to mě momentálně nezajíma
            //System.out.println("Levá strana je :"+left.toString()+ " pravá strana je "+right.toString());
           // System.out.println("Operace je "+operation.toString());
            if(operation.getType()==1){
//                System.out.println("Operace je typ 1");
//                System.out.println("Index = "+index);
                String label1 = left.toString();
                String label2 = right.toString();
                Transition transition1 = new Transition(leftState, rightState, label1);
                transition1.getVisualProperties().setStrokeColor(Color.RED);
                transition1.getVisualProperties().setFontColor(Color.GREEN);
                if(transition1.isReflexive()) {
                    transition1.getVisualProperties().setCurveFactor(Math.PI/2);
                } else {
                    transition1.getVisualProperties().setCurveFactor(1);
                }
                automaton.addTransition(transition1);
                logState(left.toString() + " -----> " + transition1.toString());

                Transition transition2 = new Transition(leftState, rightState, label2);
                transition1.getVisualProperties().setStrokeColor(Color.RED);
                transition1.getVisualProperties().setFontColor(Color.GREEN);
                if(transition2.isReflexive()) {
                    transition2.getVisualProperties().setCurveFactor(Math.PI/2);
                } else {
                    transition2.getVisualProperties().setCurveFactor(1);
                }
                automaton.addTransition(transition2);
                logState(right.toString() + " -----> " + transition2.toString());
                
                int leftIndex = index;
                int rightIndex = index+1;
               // System.out.println("index "+index+ " všechny přechody "+allTransitions.size());
                if(left.getType() == RegularExpresionType.ATOMIC || 
                        left.getType() == RegularExpresionType.EPSILON) {
                        newInput.remove(leftIndex);
                        rightIndex -= 1;
                }else{
                    if(index >= allTransitions.size()){
                        allTransitions.add(transition1);
                    }else{
                        allTransitions.add(leftIndex, transition1);
                    }
                }
                if(right.getType() == RegularExpresionType.ATOMIC || 
                        right.getType() == RegularExpresionType.EPSILON) {
                        newInput.remove(rightIndex);
                }else{
                    if(index >= allTransitions.size()){
                        allTransitions.add(transition2);
                    }else{
                        allTransitions.add(rightIndex, transition2);
                    }
                }
//                System.out.println("All trans "+allTransitions.toString()+ " leftIndex = " +leftIndex+" rightIndex="+rightIndex);
//                System.out.println("New input je "+newInput.toString());
                
                oldInput.clear();
                oldInput.addAll(newInput);
            }else if(operation.getType()==0){
                x += 100;
                if (x > 600) {
                    x = 150;
                    y += 150;
                }
//                System.out.println("Operace je typ 0");
//                System.out.println("Index = "+index);
                State state = new State("s"+i);
                state.getVisualProperties().setCoordinates(x, y);
                state.getVisualProperties().setFillColor(Color.RED);
                automaton.addState(state);
                state.getVisualProperties().setFillColor(Color.GREEN);
                logState("adding new state");
                String label1 = left.toString();
                String label2 = right.toString();
                Transition transition1 = new Transition(leftState, state, label1);
                transition1.getVisualProperties().setStrokeColor(Color.RED);
                transition1.getVisualProperties().setFontColor(Color.GREEN);
                if(transition1.isReflexive()) {
                    transition1.getVisualProperties().setCurveFactor(Math.PI/2);
                } else {
                    transition1.getVisualProperties().setCurveFactor(1);
                }
                automaton.addTransition(transition1);
                logState(left.toString() + " -----> " + transition1.toString());

                Transition transition2 = new Transition(state, rightState, label2);
                transition2.getVisualProperties().setStrokeColor(Color.RED);
                transition2.getVisualProperties().setFontColor(Color.GREEN);
                if(transition2.isReflexive()) {
                    transition2.getVisualProperties().setCurveFactor(Math.PI/2);
                } else {
                    transition2.getVisualProperties().setCurveFactor(1);
                }
                automaton.addTransition(transition2);
                logState(right.toString() + " -----> " + transition2.toString());
//                System.out.println("index =" +index+" pole má velikost "+allTransitions.size());
                
                int leftIndex = index;
                int rightIndex = index+1;
                if(left.getType() == RegularExpresionType.ATOMIC || 
                        left.getType() == RegularExpresionType.EPSILON) {
                        newInput.remove(leftIndex);
                        rightIndex -=1;
                }else{
                    if(index >= allTransitions.size()){
                        allTransitions.add(transition1);
                    }else{
                        allTransitions.add(leftIndex, transition1);
                    }
                }
                
                if(right.getType() == RegularExpresionType.ATOMIC || 
                        right.getType() == RegularExpresionType.EPSILON) {
                        newInput.remove(rightIndex);
                }else{
                    if(index >= allTransitions.size()){
                        allTransitions.add(transition2);
                    }else{
                        allTransitions.add(rightIndex, transition2);
                    }
                }
//                System.out.println("All trans "+allTransitions.toString()+ " leftIndex = " +leftIndex+" rightIndex="+rightIndex);
//                System.out.println("New input je "+newInput.toString());
                oldInput.clear();
                oldInput.addAll(newInput);
                i++;
            }else if(operation.getType()==2){
//                System.out.println("Operace je typ 2");
                x += 100;
                if (x > 600) {
                    x = 150;
                    y += 150;
                }
                State state = new State("s"+i);
                state.getVisualProperties().setCoordinates(x, y);
                state.getVisualProperties().setFillColor(Color.RED);
                automaton.addState(state);
                
                logState("adding new state");
                state.getVisualProperties().setFillColor(Color.GREEN);
                
                String label1 = left.toString();
                
                Transition transition1 = new Transition(leftState, state, MathConstants.EPSILON);
                transition1.getVisualProperties().setStrokeColor(Color.RED);
                transition1.getVisualProperties().setFontColor(Color.GREEN);
                if(transition1.isReflexive()) {
                    transition1.getVisualProperties().setCurveFactor(Math.PI/2);
                } else {
                    transition1.getVisualProperties().setCurveFactor(1);
                }
                automaton.addTransition(transition1);
                logState(transition1.toString());
                
                Transition transition2 = new Transition(state, rightState, MathConstants.EPSILON);
                transition2.getVisualProperties().setStrokeColor(Color.RED);
                transition2.getVisualProperties().setFontColor(Color.GREEN);
                if(transition2.isReflexive()) {
                    transition2.getVisualProperties().setCurveFactor(Math.PI/2);
                } else {
                    transition2.getVisualProperties().setCurveFactor(1);
                }
                automaton.addTransition(transition2);
                logState(transition2.toString());
                
                Transition transition3 = new Transition(state, state, label1);
                transition3.getVisualProperties().setStrokeColor(Color.RED);
                transition3.getVisualProperties().setFontColor(Color.GREEN);
                if(transition3.isReflexive()) {
                    transition3.getVisualProperties().setCurveFactor(Math.PI/2);
                } else {
                    transition3.getVisualProperties().setCurveFactor(1);
                }
                automaton.addTransition(transition3);
                logState(left.toString() + " -----> " + transition3.toString());
//                System.out.println("index =" +index+" pole má velikost "+allTransitions.size());
                if(left.getType() == RegularExpresionType.ATOMIC || 
                        left.getType() == RegularExpresionType.EPSILON) {
                        newInput.remove(index);
                }else{
                    if(index >= allTransitions.size()){
                        allTransitions.add(transition3);
                    }else{
                        allTransitions.add(index, transition3);
                    }
                }
                //System.out.println("All trans "+allTransitions.toString()+ " left = " +index);
                //System.out.println("New input je "+newInput.toString());
                oldInput.clear();
                oldInput.addAll(newInput);
                i++;
            }
            automaton.clearHighlighting();
            q++;
           // logState("všechny přechody jsou "+allTransitions.toString());
        }
        logState("\ndone");
    }

    public int changePosition(List<RegularExpression> expres1, List<RegularExpression> expres2){
        //procházím tak dlouho dokud nenarazím na změnu
        //expres1 bude logicky menší - je to starý vstup
        int index = -1;
        for(int i=0; i<expres1.size();i++ ){
            RegularExpression regex1 = expres1.get(i);
            RegularExpression regex2 = expres2.get(i);
            if(!regex1.equals(regex2)){
                index = i;
                break;
            }
        }
        return index;
    }
    
    public List<List<RegularExpression>> allPosibilitiesFinder(List<RegularExpression> expressions){
        List<List<RegularExpression>> allPosibilities = new ArrayList<List<RegularExpression>>();
        //System.out.println("Vstup je ="+expressions.toString());
        for(int i = 0; i<expressions.size(); i++){
            List<List<RegularExpression>> oneRegPosib = new ArrayList<List<RegularExpression>>();
            oneRegPosib.addAll(expressions.get(i).getAllPosibilities());
            //System.out.println("Všechny možnosti pro i="+i+" jsou: "+oneRegPosib.toString());
            for(List<RegularExpression> onePosib : oneRegPosib){
                List<RegularExpression> helpExpression = new ArrayList<RegularExpression>();
                helpExpression.addAll(expressions);
                helpExpression.remove(i);
                //je na konci, tak to rovnou vložím
                if(i == expressions.size()-1){
                    for(int j=0; j<onePosib.size(); j++){
                        RegularExpression reg = onePosib.get(j);
                        if(reg.getType() != RegularExpresionType.EMPTY && onePosib.get(j) != null ){
                            helpExpression.add(onePosib.get(j));
                        }
                    }
                }else{
                    int index = i;
                    for(int j=0; j<onePosib.size(); j++){
                        RegularExpression reg = onePosib.get(j);
                        if(reg.getType() != RegularExpresionType.EMPTY && onePosib.get(j) != null ){
                            helpExpression.add(index, onePosib.get(j));
                        }
                        index++;
                    }
                }
                //System.out.println("Jeden přidávaný řádek je : "+helpExpression.toString());
                allPosibilities.add(helpExpression);
            }
        }
        return allPosibilities;
    }
    
    @Override
    public String checkInputRepresentation() {
        if(input == null) return CHECK_OK;
        for(List<String> onePosib : inputTest){
            List<RegularExpression> onePosibility = new ArrayList<RegularExpression>();
            for(String reg : onePosib){
                RegularExpression regex = null;
                try {
                    regex = new RegularExpression(reg);
                    
                }catch (WrongExpressionException ex) {
                    return "Regular expression "+reg+" is wrong "+ex.toString();
                }
//                if(regex.getType() != RegularExpresionType.ATOMIC){
                    onePosibility.add(regex);
//                }
            }
            input.add(onePosibility);
        }
        return CHECK_OK;
    }


    @Override
    public String checkInputParameters() {
        return CHECK_OK;
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        inputTest = new ArrayList<List<String>>();
        input = new ArrayList<List<RegularExpression>>();
        
        if(inputParameters[0] != null){
            String param = inputParameters[0].trim();
            if(param.equals("def")){
                input = null;
                inputTest = null;
            }else{
                String[] posib = param.split("->");
                for(String onePart : posib){
                    List<String> oneLine = new ArrayList<String>();
                    String[] regexs = onePart.split("-");
                    for(String regex : regexs){
                        regex = regex.trim();
                        oneLine.add(regex);
                    }
                    inputTest.add(oneLine);
                }
            }
        }
    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
       automaton = (Automaton) outputRepresentation;
    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentations) {
        expresion = (RegularExpression) inputRepresentations[0];
    }
    
    
}
