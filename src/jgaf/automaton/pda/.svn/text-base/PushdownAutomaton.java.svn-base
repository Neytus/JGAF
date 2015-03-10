/*
 * Automata.java
 *
 * Created on 27. srpen 2007, 15:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import jgaf.automaton.pda.tools.AlgorithmsLibrary;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jgaf.Representation;




public class PushdownAutomaton implements Representation {
    
    
    public static final int EMPTY = 0;
    public static final int FINAL = 1;
    public static final int WORD = 0;
    public static final int CONF = 1;
    
        
    public static final int NEW = 0;
    public static final int MODIFY = 1;
    public static final int SAVED = 2;
        
    
    private String path;
    
    private String name;
    private String description = "";
    
    private Set<String> states; //Q
    private Set<Character> inputAlphabet; //E
    private Set<String> stackAlphabet; //T
    private TransitionFunction transitionFunction; // (Q x E U {eps} x T) -> (Q x T*)
      
    private String startState; //q0
    private String initialStackSymbol; //Z0   

    private SimpleTransition lastUsedRedoUndo = null;

    private Set<String> finalStates; //F

    private String word;
    
    private Stack stack;
    
    private Configuration configuration;
    
    private List<SimpleTransition> undoStack;
    private int pointerToUndo = -1;
    
    
    private List<PAutomata> pAutomataList;

    private int acceptBy;
    private int input;

    private Report report = null;
    
    private Map<Character, PushdownSystem> pdss = null;
    private PushdownSystem pdsEpsilon = null;
    
    
    private boolean used = false;
    private String initState;
    private Stack initStack;
    
    
    private int status;
    
    
    private boolean possible;
    
    //private String initW;    
    /** Creates a new instance of Automata */
    public PushdownAutomaton() {
        this.states = new HashSet<String>();
        this.inputAlphabet = new HashSet<Character>();
        this.stackAlphabet = new HashSet<String>();
        this.finalStates = new HashSet<String>();
        this.transitionFunction = new TransitionFunction();
        this.undoStack = new ArrayList<SimpleTransition>();
    }
    
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public Report getReport() {
        return report;
    }
    
  
    private void setPAutomataList() {
        pAutomataList = new ArrayList<PAutomata>();
        PAutomata pa = PAutomata.finishAcceptablePAutomata(this);
        pa = AlgorithmsLibrary.preStar(pa, getPushdownSystemforEps());
        pAutomataList.add(pa);        
        for (int i = word.length(); i > 0; i--) {
            char letter = word.charAt(i - 1);
            PushdownSystem pds = getPushdownSystem(letter);
            PAutomata pAutomata = AlgorithmsLibrary.pre(pAutomataList.get(pAutomataList.size() - 1), pds);
            pAutomata = AlgorithmsLibrary.preStar(pAutomata, getPushdownSystemforEps());
            pAutomataList.add(pAutomata);
        }               
    }    
    
    public void sss() {
        Collections.shuffle(pAutomataList.get(pAutomataList.size() - 1 - configuration.getReadWord().length()).getTransitionRelation());
    }
    
    public void setPossible(boolean possible) {
        this.possible = possible;
    }
    
    public boolean isPosible() {
        return possible;
    }
    
    public boolean isPossible(SimpleTransition simpleTransition) {
        configuration.doTransition(simpleTransition);
        boolean is = AlgorithmsLibrary.containsConfiguration(configuration, pAutomataList.get(pAutomataList.size() - 1 - configuration.getReadWord().length()));
        configuration.doUndo(simpleTransition);
        if(is) {
           setPossible(true);
           //System.out.println("yes");
        }
        return is;
    }
    
    
    public void useWord(String word, int acceptBy) {
        setInput(WORD);
        setAcceptBy(acceptBy);
        this.setWord(word);
        this.setStack(new Stack());
        getStack().push(initialStackSymbol);
        setConfiguration(new Configuration());
        configuration.setCurrentState(startState);
        configuration.setWord(this.getWord());
        configuration.setStack(getStack());
        undoStack.clear();
        pointerToUndo = -1;
        setPAutomataList();
        report = new Report(configuration);
    } 
    
    
    public void useConfiguration(String state, String word, String stackString, int acceptBy) throws WrongValuesException {
        /////////// chech values////////////
        for (int i = 0; i < word.length(); i++) {
            if(!getInputAlphabet().contains(word.charAt(i))) {
                throw new WrongValuesException(L12N.getValue("warn.input.unknown.word"));
            }
        }                       
        
        List<String> stackList = AlgorithmsLibrary.createStack(stackString);
        if(!getStackAlphabet().containsAll(stackList)) {
            throw new WrongValuesException(L12N.getValue("warn.input.unknown.stack"));
        }         
        ////////////////////////////////////////// 
        setPossible(false);
        setInput(CONF);
        setAcceptBy(acceptBy);
        this.setWord(word);
        this.setStack(new Stack());
        this.setInitStack(new Stack());
        this.setInitState(state);                
        
        getStack().push(stackList);
        getInitStack().push(stackList);            
            
        setConfiguration(new Configuration());
        configuration.setCurrentState(state);
        configuration.setWord(this.getWord());
        configuration.setStack(getStack());
        undoStack.clear();
        pointerToUndo = -1;
        setPAutomataList();
        report = new Report(configuration);
        setUsed(true);
    }     
    
    public boolean doUndo() {
        if(pointerToUndo >= 0) {
            configuration.doUndo(undoStack.get(pointerToUndo));
            report.add(configuration, Report.UNDO);
            setLastUsedRedoUndo(undoStack.get(pointerToUndo));
            pointerToUndo--;
            return true;
        }
        return false;
    }
 
    
    public void doTransition(SimpleTransition transition) {
        if(pointerToUndo < (undoStack.size() -1)) {
            this.undoStack = undoStack.subList(0, pointerToUndo + 1);
        }
        undoStack.add(transition);
        pointerToUndo++;
        configuration.doTransition(transition);
        report.add(configuration, Report.TRANS);
    }    
    
    public boolean doRedo() {
        if(pointerToUndo < (undoStack.size() - 1)) {
            configuration.doTransition(undoStack.get(pointerToUndo + 1));
            report.add(configuration, Report.REDO);
            setLastUsedRedoUndo(undoStack.get(pointerToUndo + 1));
            pointerToUndo++;
            return true;
        }
        return false;
    }

    
    public boolean isAccepting() {
        if(configuration.isWordRead()){
            if(getAcceptBy() == FINAL && isInFinalState()) {
                return true;
            } else if(getAcceptBy() == EMPTY && configuration.getStack().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    public Set<Transition> getMatchTransitions() {
        return transitionFunction.getMatchTransitions(getConfiguration()); 
    }
    
    
    public void setPDSSystems() {
        this.pdss = new HashMap<Character, PushdownSystem>();
        this.pdsEpsilon = new PushdownSystem();
        pdsEpsilon.setAlphabet(getStackAlphabet());
        pdsEpsilon.setStates(getStates());
        
        for (Character lett : getInputAlphabet()) {
            PushdownSystem pds = new PushdownSystem();
            pds.setAlphabet(getStackAlphabet());
            pds.setStates(getStates());
            pdss.put(lett, pds);
        }
        int uniqueId = 0;
        
        for (Transition transition : getTransitionFunction().getTransitions()) {
            Transition trans = new Transition();
            trans.setTernary(transition.getTernary());
            for (Pair pair : transition.getPairs()) {
                int length = pair.getStackSymbols().size();                
                if(length > 2) {
                    String uniqueState = pair.getState();
                    for (int i = 0; i < length - 1; i++) {
                        Transition transNew;
                        if(i == 0) {
                            transNew = trans;
                        } else {
                            transNew = new Transition();
                            Ternary ternary = new Ternary(uniqueState, pair.getStackSymbols().get(length - i - 1));
                            transNew.setTernary(ternary);
                        }
                        if(i == length - 2) {
                            uniqueState = pair.getState();
                        } else {
                            uniqueState = "@" + uniqueId++;
                        }
                        List<String> stackNew = new ArrayList<String>();
                        stackNew.add(pair.getStackSymbols().get(length - i - 2));
                        stackNew.add(pair.getStackSymbols().get(length - i - 1));
                        Pair pairNew = new Pair(uniqueState, stackNew);
                        transNew.addPair(pairNew);   
                        if(i > 0) {
                            if(transNew.isEpsilon()) {
                                pdsEpsilon.addTransition(transNew);
                            } else {
                                pdss.get(transNew.getTernary().getSymbol()).addTransition(transNew);
                            }
                        }
                    }
                } else {
                    trans.addPair(pair);
                }
            }
            if(trans.isEpsilon()) {
                pdsEpsilon.addTransition(trans);
            } else {
                pdss.get(trans.getTernary().getSymbol()).addTransition(trans);
            }            
        }    
        
        
        for (int i = 0; i < uniqueId; i++) {
            String state = "@" + i;
            pdsEpsilon.addState(state);
            for (PushdownSystem push : pdss.values()) {
                push.addState(state);
            }
        }
    ////    System.out.println(pdss);
    //    System.out.println("EEEPP");
    //    System.out.println(pdsEpsilon);
    }        
    
    
    
    public PushdownSystem getPushdownSystem(char letter) {
        if(pdss == null) {
            setPDSSystems();
        }
        return pdss.get(letter);
    } 
        
    public PushdownSystem getPushdownSystemforEps() {
        if(pdsEpsilon == null) {
            setPDSSystems();
        }
        return pdsEpsilon;
    } 
    

    public boolean isInFinalState() {
        for (String state : finalStates) {
            if(configuration.getCurrentState().equals(state)) {
                return true;
            }
        }
        return false;
    }
        
    public String getName() {
        if(name == null) {
            return "";
        }        
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if(description == null) {
            return "";
        }                
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getStates() {
        return states;
    }

    public void addStates(String state) {
        this.states.add(state);
    }

    public Set<Character> getInputAlphabet() {
        return inputAlphabet;
    }

    public void addInputAlphabet(Character inputAlphabet) {
        this.inputAlphabet.add(inputAlphabet);
    }

    public Set<String> getStackAlphabet() {
        return stackAlphabet;
    }

    public void addStackAlphabet(String symbol) {
        this.stackAlphabet.add(symbol);
    }

    public TransitionFunction getTransitionFunction() {
        return transitionFunction;
    }

    public void setTransitionFunction(TransitionFunction transitionFunction) {
        this.transitionFunction = transitionFunction;
    }

    /**
     * start state according to definition
     */
    public String getStartState() {
        if(startState == null) {
            return "";
        }
        return startState;
    }

    public void setStartState(String startState) {
        this.startState = startState;
    }

    public String getInitialStackSymbol() {
        if(initialStackSymbol == null) {
            return "";
        }        
        return initialStackSymbol;
    }
    
    public String getInitialStackSymbolFormat() {
        if(initialStackSymbol == null) {
            return "";
        }        
        if(initialStackSymbol.length() > 1) {
            return AutomataConstants.SEPARATOR + initialStackSymbol + AutomataConstants.SEPARATOR;
        }
        return initialStackSymbol;
    }    

    public void setInitialStackSymbol(String initialStackSymbol) {
        this.initialStackSymbol = initialStackSymbol;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void addFinalStates(String finalState) {
        this.finalStates.add(finalState);
    }
    
    public String toString() {
        return getDescription() + "\n" + getName() + " = (" + writeStates() + ", " + writeInputAlphabet() + ", " + writeStackAlphabet() +
                ", " + AutomataConstants.DELTA_LOW + ", " + startState + ", " + initialStackSymbol + ", " + writeFinalStates() +
                ")\n" + transitionFunction;
    }
    
    public String writeStates() {
        StringBuilder stateSet = new StringBuilder("{");
        for (String state : states) {
            stateSet.append(state);
            stateSet.append(", ");
        }
        return (!states.isEmpty() ? stateSet.substring(0, stateSet.length() - 2) + "}" : stateSet + "}");
    }
    
    public String writeFinalStates() {
        StringBuilder stateSet = new StringBuilder("{");
        for (String state : finalStates) {
            stateSet.append(state);
            stateSet.append(", ");
        }
        return (!finalStates.isEmpty() ? stateSet.substring(0, stateSet.length() - 2) + "}" : stateSet + "}");
    }
     
    public String writeInputAlphabet() {
        StringBuilder symbolSet = new StringBuilder("{");
        for (Character symbol : inputAlphabet) {
            symbolSet.append(symbol);
            symbolSet.append(", ");
        }
        return (!inputAlphabet.isEmpty() ? symbolSet.substring(0, symbolSet.length() - 2) + "}" : symbolSet + "}");
    }    
    
    public String writeStackAlphabet() {
        StringBuilder symbolSet = new StringBuilder("{");
        for (String symbol : stackAlphabet) {
            symbolSet.append(symbol);
            symbolSet.append(", ");
        }
        return (!stackAlphabet.isEmpty() ? symbolSet.substring(0, symbolSet.length() - 2) + "}" : symbolSet + "}");
    }        
  
    
    public String definitionSeven() {
        return getName() + " = (" + DefaultValues.getInstance().getStatesSet() + ", " + DefaultValues.getInstance().getInputAlphabet() 
        + ", " + DefaultValues.getInstance().getStackAlphabet() + ", " + DefaultValues.getInstance().getTransition()  
        + ", " + getStartState() + ", " + getInitialStackSymbol() + ", " +  DefaultValues.getInstance().getFinalStates() + ")";
    }
    
    public String writeAutomataDetail() {
        StringBuilder automata = new StringBuilder();
        automata.append(L12N.getValue("dialog.automataModify.label.description")).append(" ").append(getDescription()).append("\n\n");
        automata.append(definitionSeven()).append("\n\n");
        automata.append(DefaultValues.getInstance().getStatesSet()).append(" = ").append(writeStates()).append("\n");
        automata.append(DefaultValues.getInstance().getInputAlphabet()).append(" = ").append(writeInputAlphabet()).append("\n");
        automata.append(DefaultValues.getInstance().getStackAlphabet()).append(" = ").append(writeStackAlphabet()).append("\n");
        automata.append(DefaultValues.getInstance().getFinalStates()).append(" = ").append(writeFinalStates()).append("\n\n");
        automata.append(transitionFunction);
        return automata.toString();
    }
    
    public void generateAllFromTransition() {
        for (Transition transition : transitionFunction.getTransitions()) {
            Ternary ternary = transition.getTernary();
            states.add(ternary.getState());
            if(!ternary.isEpsilon()) {
                inputAlphabet.add(ternary.getSymbol());
            }
            stackAlphabet.add(ternary.getStackSymbol());
            for (Pair pair : transition.getPairs()) {
                states.add(pair.getState());
                if(!pair.isEpsilonStack()) {
                    for (String stackSymbol : pair.getStackSymbols()) {
                        stackAlphabet.add(stackSymbol);
                    }
                }
                
            }
        }
    }

    private void validateTransition() throws AutomataException {
        for (Transition transition : transitionFunction.getTransitions()) {
            Ternary ternary = transition.getTernary();
            if(!states.contains(ternary.getState())) {
                throw new AutomataException(L12N.getValue("warn.modify.unknown.state.left1") + " " + ternary.getState() + 
                        L12N.getValue("warn.modify.unknown.state.2"));
            }
            if(!(ternary.isEpsilon()|| inputAlphabet.contains(ternary.getSymbol()))) {
                throw new AutomataException(L12N.getValue("warn.modify.unknown.symbol.left1") + " " + ternary.getSymbol() + 
                        L12N.getValue("warn.modify.unknown.symbol.left2"));                
            }
            if(!stackAlphabet.contains(ternary.getStackSymbol())) {
                throw new AutomataException(L12N.getValue("warn.modify.unknown.stack.left1") + " " + ternary.getStackSymbol() +
                        L12N.getValue("warn.modify.unknown.stack.2"));
            }                  
            for (Pair pair : transition.getPairs()) {
                if(!states.contains(pair.getState())) {
                    throw new AutomataException(L12N.getValue("warn.modify.unknown.state.right1") + " " + pair.getState() + 
                            L12N.getValue("warn.modify.unknown.state.2"));
                }
                if(!stackAlphabet.containsAll(pair.getStackSymbols())) {
                    throw new AutomataException(L12N.getValue("warn.modify.unknown.stack.right1") +
                            L12N.getValue("warn.modify.unknown.stack.2"));
                }
            }
        }   
    }
    
    public void validateAutomata() throws AutomataException {
        if(!states.contains(startState)) {
            throw new AutomataException(L12N.getValue("warn.modify.unknown.initState"));
        }
        if(!stackAlphabet.contains(initialStackSymbol)) {
            throw new AutomataException(L12N.getValue("warn.modify.unknown.initStack"));
        }
        validateTransition();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
    
    public void removeState(String state, boolean global) throws WrongValuesException {
        if(!states.remove(state)) {
            throw new WrongValuesException("Stav " + state + " neni prvkem monoziny stavu");
        }
        if(global) {
            transitionFunction.removeState(state);
            finalStates.remove(state);            
            if(startState.equals(state)) {
                startState = null;
            }
        }        
    }

    public void ModifyState(String oldState, String newState, boolean global) throws WrongValuesException {
        removeState(oldState, false);
        addStates(newState);
        if(global) {
            
            if(getStartState().equals(oldState)) {
                setStartState(newState);
            }
            
            transitionFunction.renameState(oldState, newState);
            if(finalStates.remove(oldState)) {
                finalStates.add(newState);
            }
            if(startState.equals(oldState)) {
                startState = newState;
            }
        }
    }        
    
    
    public void removeStackSymbol(String stack, boolean global) throws WrongValuesException {
        if(!stackAlphabet.remove(stack)) {
            throw new WrongValuesException("Zasobnikovy symbol " + stack + " neni prvkem zasobnikove abecedy");
        }
        if(global) {
            transitionFunction.removeStack(stack);
        }        
    }

    public void ModifyStackSymbol(String oldStack, String newStack, boolean global) throws WrongValuesException {
        removeStackSymbol(oldStack, false);
        addStackAlphabet(newStack);
        if(global) {
            if(getInitialStackSymbol().equals(oldStack)) {
                setInitialStackSymbol(newStack);
            }
            transitionFunction.renameStack(oldStack, newStack);
        }
    }
        
    
    public void removeFinalState(String state) throws WrongValuesException {
        if(!finalStates.remove(state)) {
            throw new WrongValuesException("stav " + state + " neni prvkem konecnych stavu");
        }
    }

    public void ModifyFinalState(String oldState, String newState) throws WrongValuesException {
        removeFinalState(oldState);
        addFinalStates(newState);
    }    
    
    public void removeSymbol(char symbol, boolean global) throws WrongValuesException {
        if(!inputAlphabet.remove(symbol)) {
            throw new WrongValuesException("Symbol vstupni abecedy " + symbol + " neni prvkem vstupni abecedy");
        }
        if(global) {
            transitionFunction.removeSymbol(symbol);
        }        
    }

    public void ModifySymbol(char
        oldSymbol, char newSymbol, boolean global) throws WrongValuesException {
        removeSymbol(oldSymbol, false);
        addInputAlphabet(newSymbol);
        if(global) {
            transitionFunction.renameSymbol(oldSymbol, newSymbol);
        }
    }    

    public SimpleTransition getLastUsedRedoUndo() {
        return lastUsedRedoUndo;
    }

    public void setLastUsedRedoUndo(SimpleTransition lastUsedRedoUndo) {
        this.lastUsedRedoUndo = lastUsedRedoUndo;
    }

    public int getAcceptBy() {
        return acceptBy;
    }

    public void setAcceptBy(int acceptBy) {
        this.acceptBy = acceptBy;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getInitState() {
        return initState;
    }

    public void setInitState(String initState) {
        this.initState = initState;
    }

    public Stack getInitStack() {
        return initStack;
    }

    public void setInitStack(Stack initStack) {
        this.initStack = initStack;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    @Override
    public Representation clone() {
        PushdownAutomaton automatonObj = new PushdownAutomaton();

        automatonObj.setName(name);
        automatonObj.setDescription(description);
        for (String string : getStates()) {
            automatonObj.addStates(string);
        }
        for (String string : getStackAlphabet()) {
            automatonObj.addStackAlphabet(string);
        }
        for (char s : getInputAlphabet()) {
            automatonObj.addInputAlphabet(s);
        }
        automatonObj.setTransitionFunction((TransitionFunction) transitionFunction.clone());

        automatonObj.setStartState(startState);
        automatonObj.setInitialStackSymbol(initialStackSymbol);

        for (String string : getFinalStates()) {
            automatonObj.addFinalStates(string);
        }
        return automatonObj;
    }

    
    
    
    
}
