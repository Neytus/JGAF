/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.transformations;

import java.awt.Color;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class MyFirstTransformation extends Transformation {


    public MyFirstTransformation(Automaton input, Automaton output) {
        super(input, output);
    }


    public void start() {
        for (State state : input.getStates()) {
            output.addState((State) state.clone());
            state.getVisualProperties().setFillColor(Color.green);
            logState();
        }
    }


    @Override
    public String getName() {
        return "Transformation example";
    }




}
