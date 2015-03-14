/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.pda.simulation;

import javax.swing.JPanel;
import jgaf.Representation;
import jgaf.automaton.pda.PDASimulatorPanel;
import jgaf.automaton.pda.PushdownAutomaton;
import jgaf.procedure.Procedure;

/**
 *
 * @author hanis
 */
public class PDASimulation extends Procedure {

    private PushdownAutomaton automaton;
    private PDASimulatorPanel face;

    @Override
    public String checkInputRepresentation() {
        return Procedure.CHECK_OK;
    }

    @Override
    public String checkInputParameters() {
        return Procedure.CHECK_OK;
    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentation) {
        this.automaton = (PushdownAutomaton) inputRepresentation[0];
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
    }

    @Override
    public JPanel getFace() {
        return face;
    }

    @Override
    public void create() {
        face = new PDASimulatorPanel(getFrame(), automaton);
    }

}
