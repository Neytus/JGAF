/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.simulation;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import jgaf.Constants.MathConstants;
import jgaf.Representation;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.automaton.fa.FSAutomatonEditor;
import jgaf.automaton.fa.undo.UndoRedoHandler;
import jgaf.environment.Environment;
import jgaf.procedure.Procedure;

/**
 *
 * @author hanis
 */
public class DFASimulation extends Procedure {

    private DFASimulationFace face;
    private FSAutomatonEditor editor;


    private Automaton automaton;
    private UndoRedoHandler undoHandler;
    private String inputWord = "";
    private int wordPointer = 0;
    private State lastState = null;
    private State currentState;
    private Transition usedTransition = null;
    private List<Transition> sequence;

    private Timer timer;



    public DFASimulation() {

    }
    



    public Automaton getAutomaton() {
        return editor.getAutomaton();
    }

    public DFASimulationFace getFace() {
        return face;
    }

    public FSAutomatonEditor getEditor() {
        return editor;
    }


    public String getInputWord() {
        return inputWord;
    }


    public void reset() {
        setWordPointer(0);
        setCurrentState(editor.getAutomaton().getInitialState());
        setWord();
        highlightAutomaton();
        setOutput(getConfiguration());
        repaint();
    }

    public String getConfiguration() {
        return "("+ currentState + "," + 
                (getCurrentSymbol().equals("") ?  MathConstants.EPSILON : getCurrentSymbol() + getRestWord()) + ")";

    }


    public String getOutput() {
        return getFace().getControlPanel().getAreaTxt();
    }

    public void setOutput(String text) {
        getFace().getControlPanel().setAreaTxt(text);
    }

    public void addToOutput(String text) {
        getFace().getControlPanel().setAreaTxt(getFace().getControlPanel().getAreaTxt() + "\n" + text);
    }


    public String getCurrentSymbol() {
        return getSymbolAt(wordPointer);
    }


    public String getSymbolAt(int index) {
        if (index >= inputWord.length()) {
            return "";
        }
        return String.valueOf(inputWord.charAt(index));
    }

    public void setWord() {
        setWord(getReadWord(), getCurrentSymbol(), getRestWord());
    }

    public String getRestWord() {
        String rest = "";
        if(wordPointer < getInputWord().length()) {
            rest = inputWord.substring(wordPointer + 1);
        }
        return rest;
    }


    public String getReadWord() {
        return inputWord.substring(0, getWordPointer());
    }

    private void setWord(String read, String current, String rest) {
        getFace().getControlPanel().setWordReadTxt(read);
        getFace().getControlPanel().setWordCurrentTxt(current);
        getFace().getControlPanel().setWordRestTxt(rest);
    }

    public int getWordPointer() {
        return wordPointer;
    }

    public void setWordPointer(int wordPointer) {
        this.wordPointer = wordPointer;
    }


    public int getInputCardinality() {
        return 1;
    }

    

    public boolean hasBeenInputWordRead() {
        return "".equals(getRestWord());
    }

    private List<Transition> getTransitionSequence() {
        List<Transition> transitionSequence = new ArrayList<Transition>();
        int pointer = 0;
        Transition transition = null;
        while (true) {
            State state = getCurrentState();
            if(pointer > 0) {
                state = transition.getToState();
            }
            String symbol = getSymbolAt(pointer++);
            if(symbol.equals("")) {
                break;
            }
            List<Transition> list = getAutomaton().getTransitionFromUnder(state, symbol);
            if (list.isEmpty()) {
                break;
            }
            transition = list.get(0);
            transitionSequence.add(transition);
        }
        return transitionSequence;
    }

    public boolean next() {
        if(!hasNextSetp()) {
            pause();
            if(getRestWord().equals("") && getCurrentState().isAccepting()) {
                JOptionPane.showMessageDialog(face, "The automaton ACCEPTS the input word: " + inputWord, "answer", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(face, "The automaton does NOT ACCEPT the input word: " + inputWord, "answer", JOptionPane.INFORMATION_MESSAGE);
            }
            return false;
        }
        Transition transition = sequence.get(wordPointer);
        setCurrentState(transition.getToState());
        wordPointer++;
        setWord();
        addToOutput(" |- " + getConfiguration());
        editor.clearHighlighting();
        highlightAutomaton();
        repaint();
        return true;
    }

    public boolean previous() {
        if(wordPointer == 0) {
            return false;
        }
        wordPointer--;
        setCurrentState(sequence.get(wordPointer).getFromState());
        setWord();
        setOutput(getOutput().substring(0, getOutput().lastIndexOf("\n")));
   //     addToOutput("("+ currentState + "," +  getRestWord() + ")");
        editor.clearHighlighting();
        highlightAutomaton();
        repaint();
        return true;
    }


    public void play(int delay) {
        if(timer == null || !timer.isRunning()) {
            timer = new Timer(delay, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });
            timer.start();
        }
    }

    public void pause() {
        if(timer != null && timer.isRunning()) {
            timer.stop();
        }
    }


    public void stop() {
        pause();
        reset();
    }

    public void end() {
        pause();
        while(next()){
        }
    }





    private void highlightAutomaton() {
        editor.clearHighlighting();
        currentState.getVisualProperties().setFillColor(Color.GREEN);
        if(wordPointer > 0) {
            sequence.get(wordPointer - 1).getVisualProperties().setStrokeColor(Color.RED);
        }
    }


    public void repaint() {
        editor.getRepresenter().repaint();
        getFace().repaint();
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
        getFace().getControlPanel().setStateTxt(currentState.getName());
    }


    public boolean hasNextSetp() {
        return sequence.size() > wordPointer;
    }


    @Override
    public void assignInputParameters(String... inputParameters) {
        this.inputWord = inputParameters[0];
    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentation) {
        this.automaton = (Automaton) inputRepresentation[0];
    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {        
    }

    @Override
    public String checkInputRepresentation() {
        switch(automaton.getType()) {
            case Automaton.UFA: return "The automaton has no initial state.";
            case Automaton.EFA: return "The automaton is nondeterministic with epsilon steps, the simulation requires a deterministic automaton.";
            case Automaton.NFA: return "The automaton is nondeterministic, the simulation requires a deterministic automaton.";
            case Automaton.DFA: return Procedure.CHECK_OK;
            default: return Procedure.CHECK_NOT_OK;
        }
    }


    @Override
    public String checkInputParameters() {
        if (inputWord == null || inputWord.equals("")) {
            return "The input word is empty.";
        }
        
        if(automaton.isStringOverAlphabet(inputWord)) {
            return Procedure.CHECK_OK;    
        } else {
            return "The inuput word is not over the automaton alphabet.";
        }
    }

    @Override
    public void create() {
        FSAutomatonEditor faEditor = (FSAutomatonEditor) Environment.getInstance().getEditorHandler().createEditor("FA");
        faEditor.setData(automaton);
        this.editor = faEditor;        
        this.face = new DFASimulationFace(this);
        setCurrentState(editor.getAutomaton().getInitialState());
        reset();
        this.sequence = getTransitionSequence();
    }




}
