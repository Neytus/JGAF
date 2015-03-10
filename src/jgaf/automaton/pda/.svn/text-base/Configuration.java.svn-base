/*
 * Configuration.java
 *
 * Created on 27. srpen 2007, 17:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.io.IOException;

/**
 *
 * @author hanis
 */
public class Configuration {
    

    private String currentState;
    private String word;
    private Stack stack;
    private int pointer;
            
    public Configuration() {
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getFullWord() {
        return word;
    }

    public void setWord(String word) {
        pointer = 0;
        this.word = word;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }
    
    public void truncateWord() {
        pointer++;
    }
    
    public void undoWord() {
        pointer--;
    }
    
    public boolean isWordRead() {
        return pointer == word.length();
    }
    
    public char getCurrentChar() { 
        if(!isWordRead()) {
            return this.word.charAt(pointer);
        } else {
            return ' ';
        }
    }
    
    public Ternary getTernary() {
        return new Ternary(getCurrentState(), getCurrentChar(), stack.top());
    }
    
    public String toString() {
        return "(" + getCurrentState() + ", " 
                + (getRestWord().length() < 1 ? DefaultValues.getInstance().getEpsilon() : getRestWord()) + ", "
                + (getStack().isEmpty() ? DefaultValues.getInstance().getEpsilon() : getStack()) + ")";
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }
    
    public String getRestWord() {
        return word.substring(pointer);
    }
    
    public String getRestMinusCurent() {
        if((pointer + 1) < word.length()) { 
            return word.substring(pointer + 1);
        } else {
            return "";
        }
    }
    
    public String getReadWord() {
        return word.substring(0, pointer);
    }     
    
    public void doTransition(SimpleTransition transition) {
        setCurrentState(transition.getPair().getState());
        if(!transition.getTernary().isEpsilon()) {
            truncateWord();
        }
        stack.pop();
        if(!transition.getPair().isEpsilonStack()) {
            stack.push(transition.getPair().getStackSymbols());
        }        
    }

    public void doUndo(SimpleTransition transition) {
        setCurrentState(transition.getTernary().getState());
        if(!transition.getTernary().isEpsilon()) {
            undoWord();
        }
        if(!transition.getPair().isEpsilonStack()) {
            for (int i = 0; i < transition.getPair().getStackSymbols().size(); i++) {
                stack.pop();
            }
        }    
        stack.push(transition.getTernary().getStackSymbol());                
    }        

}
