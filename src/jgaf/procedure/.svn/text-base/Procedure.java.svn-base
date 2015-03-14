/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import javax.swing.JFrame;
import javax.swing.JPanel;
import jgaf.Representation;
import jgaf.environment.Environment;
import jgaf.gui.ProcedureFrame;

/**
 *
 * @author hanis
 */
public abstract class Procedure {

    public static String CHECK_OK = "check_ok";
    public static String CHECK_NOT_OK = "check_not_ok";

    private ProcedureDescriptor descriptor;

    private String nameID;

    private ProcedureFrame procedureFrame;




    public abstract String checkInputRepresentation();

    public abstract String checkInputParameters();

    public abstract void assignInputRepresentation(Representation... inputRepresentation);

    public abstract void assignInputParameters(String... inputParameters);

    public abstract void assignOutputRepresentation(Representation outputRepresentation);

    public abstract JPanel getFace();

    public abstract void create();





    public void setProcedureDescriptor(ProcedureDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public String getTitle() {
        return descriptor.getName();
    }

    public String getDescription() {
        return descriptor.getDescription();
    }

    public ProcedureDescriptor getDescriptor() {
        return descriptor;
    }

    public boolean hasOutput() {
        return descriptor.getOutputRepresentation() != null;
    }

    public final JFrame getFrame() {
         return Environment.getInstance().getFrame();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof Procedure) {
           Procedure procedure = (Procedure)obj;
           if(procedure.getNameID().equals(getNameID())){
                return true;
           }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.getNameID() != null ? this.getNameID().hashCode() : 0);
        return hash;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public ProcedureFrame getProcedureFrame() {
        return procedureFrame;
    }

    public void setProcedureFrame(ProcedureFrame procedureFrame) {
        this.procedureFrame = procedureFrame;
    }

}
