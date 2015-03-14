/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.gui;

import javax.swing.JInternalFrame;
import jgaf.environment.Environment;
import jgaf.procedure.Procedure;

/**
 *
 * @author hanis
 */
public class ProcedureFrame extends JInternalFrame {

    private Procedure procedure;

    public ProcedureFrame(Procedure procedure) {
        super(procedure.getNameID(), true, true, true, true);
        this.procedure = procedure;
        initiateListeners();
    }


    private void initiateListeners() {
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeactivated(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

    }

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
        activateProcedure();

    }

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
        closingProcedure();
    }

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
        deactivateProcedure();
    }





    private void closingProcedure() {
        Environment.getInstance().getProcedureHandler().closingProcedure(procedure);
    }

    private void activateProcedure() {
        Environment.getInstance().getProcedureHandler().setCurrentProcedure(procedure);
    }

    private void deactivateProcedure() {
        Environment.getInstance().getProcedureHandler().setCurrentProcedure(null);
    }

}
