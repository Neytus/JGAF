/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.transformations;

import java.util.ArrayList;
import java.util.List;
import jgaf.automaton.Automaton;

/**
 *
 * @author hanis
 */
public abstract class Transformation {

    public final Automaton input;
    public final Automaton output;

    private final List<Automaton> inputSequence;
    private final List<Automaton> outputSequence;

    public Transformation(Automaton input, Automaton output) {
        this.input = input;
        this.output = output;
        this.inputSequence = new ArrayList<>();
        this.outputSequence = new ArrayList<>();
        logState();
    }

    public final void logState() {
        inputSequence.add((Automaton) input.clone());
        outputSequence.add((Automaton) output.clone());
    }

    public abstract void start();

    public final Automaton getInitialInput() {
        return inputSequence.get(0);
    }

    public final Automaton getInitialOutput() {
        return outputSequence.get(0);
    }

    public final Automaton getInput(int pointer) {
        return inputSequence.get(pointer);
    }

    public final Automaton getOutput(int pointer) {
        return outputSequence.get(pointer);
    }

    public final int getNumberOfSteps() {
        return inputSequence.size();
    }

    public abstract String getName();


}
