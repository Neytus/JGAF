/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.register;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaf.procedure.Procedure;
import jgaf.procedure.ProcedureDescriptor;

/**
 *
 * @author hanis
 */
public class ProcedureRegister {


    private List<ProcedureDescriptor> descriptorList;


    public ProcedureRegister() {

    }

    public void setDescriptorList(List<ProcedureDescriptor> descriptorList) {
        this.descriptorList = descriptorList;
    }


    private Procedure createProcedure(String className) {
        Procedure procedure = null;
        try {
            procedure = (Procedure) Class.forName(className).newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(ProcedureRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProcedureRegister.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProcedureRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        return procedure;
    }

    public List<ProcedureDescriptor> getDescriptorList() {
        return descriptorList;
    }

    public List<ProcedureDescriptor> getSimulations() {
        List<ProcedureDescriptor> simulations = new ArrayList<ProcedureDescriptor>();
        for (ProcedureDescriptor procedureDescriptor : descriptorList) {
            if(procedureDescriptor.getType().equals(ProcedureDescriptor.SIMULATION)) {
                simulations.add(procedureDescriptor);
            }
        }
        return simulations;
    }



    public List<ProcedureDescriptor> getTransformations() {
        List<ProcedureDescriptor> transformations = new ArrayList<ProcedureDescriptor>();
        for (ProcedureDescriptor procedureDescriptor : descriptorList) {
            if(procedureDescriptor.getType().equals(ProcedureDescriptor.TRANSFORMATION)) {
                transformations.add(procedureDescriptor);
            }
        }
        return transformations;
    }



    public List<ProcedureDescriptor> getAnalyses() {
        List<ProcedureDescriptor> analyses = new ArrayList<ProcedureDescriptor>();
        for (ProcedureDescriptor procedureDescriptor : descriptorList) {
            if(procedureDescriptor.getType().equals(ProcedureDescriptor.ANALYSIS)) {
                analyses.add(procedureDescriptor);
            }
        }
        return analyses;
    }













}
