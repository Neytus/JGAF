/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.undo;

import java.util.Stack;
import jgaf.automaton.Automaton;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class UndoRedoHandler {

    public static final int STACK_LENGTH = 500;

    private Stack<UndoRedoStep> undoStack;
    private Stack<UndoRedoStep> redoStack;

    public UndoRedoHandler() {
        undoStack = new Stack<UndoRedoStep>();
        redoStack = new Stack<UndoRedoStep>();
    }


    public void undo() {
        if(!undoStack.isEmpty()) {
            UndoRedoStep step = undoStack.pop();
            step.undo();
            redoStack.push(step);
            if(redoStack.size() > STACK_LENGTH) {
                redoStack.remove(0);
            }
        }
    }

    public void redo() {
        if(!redoStack.isEmpty()) {
            UndoRedoStep step = redoStack.pop();
            step.redo();
            undoStack.push(step);
            if(undoStack.size() > STACK_LENGTH) {
                undoStack.remove(0);
            }
        }
    }
    
    public void addStep(UndoRedoStep step) {
        undoStack.push(step);
        if (undoStack.size() > STACK_LENGTH) {
            undoStack.remove(0);
        }
        redoStack.clear();
    }




    public String writeUndoStack() {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (UndoRedoStep undoRedoStep : undoStack) {
            sb.append("------------").append(counter++).append("--------------\n");
            sb.append(undoRedoStep.type()).append("\n");
        }
        return sb.toString();
    }

    public String writeRedoStack() {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (UndoRedoStep undoRedoStep : redoStack) {
            sb.append("------------").append(counter++).append("--------------\n");
            sb.append(undoRedoStep.type()).append("\n");
        }
        return sb.toString();
    }

}
