/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import jgaf.Representation;

/**
 *
 * @author hanis
 */
public abstract class DefaultProcedure extends Procedure {

    private List<List<Representation>> inputSequence;
    private List<Representation> outputSequence;
    private List<String> logSequence;


    private ProcedureDescriptor descriptor;

    private DefaultProcedureFace face;


    public DefaultProcedure() {
        this.inputSequence = new ArrayList<List<Representation>>();
        this.outputSequence = new ArrayList<Representation>();
        this.logSequence = new ArrayList<String>();
    }


    public abstract void startProcedure();


    public final void logState(String message) {
        List<Representation> newInputList = new ArrayList<Representation>();
        for (ProcedureRepresentation procedureRepresenation : getDescriptor().getInputRepresentations()) {
            newInputList.add(procedureRepresenation.getRepresentation().clone());
        }
        getInputSequence().add(newInputList);
        if(hasOutput()) {
            getOutputSequence().add(getDescriptor().getOutputRepresentation().getRepresentation().clone());
        }
        if(message == null) {
            message = "";
        }
        logSequence.add(message);
    }
    
    public final void logState() {
        logState("");
    }


    public void create() {
        face = new DefaultProcedureFace(this);
    }

    public JPanel getFace() {
        return face;
    }
    
    public List<List<Representation>> getInputSequence() {
        return inputSequence;
    }

    public List<Representation> getOutputSequence() {
        return outputSequence;
    }

    public List<String> getLogSequence() {
        return logSequence;
    }



}
