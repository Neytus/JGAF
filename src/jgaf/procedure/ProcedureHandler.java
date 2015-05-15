/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import jgaf.Representation;
import jgaf.environment.Environment;
import jgaf.gui.FrameTool;
import jgaf.gui.MainFrame;
import jgaf.gui.ProcedureFrame;
import jgaf.register.ProcedureRegister;


/**
 *
 * @author hanis
 */
public class ProcedureHandler {

    private MainFrame mainFrame;

    private List<Procedure> procedures;
    private ProcedureRegister procedureRegister;

    private Procedure currentProcedure;

    public ProcedureHandler(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        procedures = new ArrayList<Procedure>();
    }


    public void setProcedureRegister(ProcedureRegister editorRegister) {
        this.procedureRegister = editorRegister;
    }

    public ProcedureRegister getProcedureRegister() {
        return procedureRegister;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public boolean isCurrentProcedure(Procedure procedure) {
        return procedure != null && isProcedureShown() && procedure.equals(currentProcedure);
    }

   public void putProcedureToFront(Procedure procedure) {
        if (procedures.contains(procedure)) {
            procedure.getProcedureFrame().toFront();
            try {
                procedure.getProcedureFrame().setSelected(true);
                procedure.getProcedureFrame().setIcon(false);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ProcedureHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            setCurrentProcedure(currentProcedure);
        }
    }



    public void showProcedureWindow(Procedure procedure) {
        procedure.setNameID(getFreeName(procedure.getTitle()));

        procedures.add(procedure);
        setCurrentProcedure(procedure);

        ProcedureFrame frame = new ProcedureFrame(procedure);
        procedure.setProcedureFrame(frame);
        JDesktopPane desktop = mainFrame.getDesktop();
        frame.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
        desktop.add(frame, 2);
        desktop.setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
        frame.add(procedure.getFace(), BorderLayout.CENTER);

        procedure.getFace().repaint();
    }

    private String getFreeName(String name) {
        return getFreeName(name, 1);
    }

    private String getFreeName(String name, int num) {
        String suffix = "";
        if (num > 1) {
            suffix = "(" + num + ")";
        }
        for (Procedure procedure : procedures) {
            if(procedure.getNameID().equals(name + suffix)) {
                return getFreeName(name, num + 1);
            }
        }
        return name + suffix;
    }


    public boolean isProcedureShown() {
        return currentProcedure != null;
    }


    public List<JInternalFrame> getProcedureFrames(List<Procedure> procedureList) {
        List<JInternalFrame> frameList = new ArrayList<JInternalFrame>();
        for (Procedure procedure : procedureList) {
            frameList.add(procedure.getProcedureFrame());
        }
        return frameList;
    }



    public void showAllProcedures() {
        FrameTool.organizeFrames(getProcedureFrames(procedures), mainFrame.getDesktop());
    }

    public void minimizeAllProcedures() {
        FrameTool.minimizeFrames(getProcedureFrames(procedures), mainFrame.getDesktop());
    }

    public void closeAllProcedures() {
        for (Procedure procedure : procedures) {
            procedure.getProcedureFrame().dispose();
        }
        currentProcedure = null;
        procedures.clear();
    }

    public Procedure createProcedure(ProcedureDescriptor descriptor) {
        Procedure procedure = null;
        try {
            try {
                procedure = (Procedure) Class.forName(descriptor.getClassPath()).newInstance();
            } catch (InstantiationException ex) {
                System.out.println("1");
            } catch (IllegalAccessException ex) {
                System.out.println("2");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("3");
        }
        procedure.setProcedureDescriptor(descriptor);
        return procedure;
    }

    public void createAndShowNewProcedure(ProcedureDescriptor descriptor) throws WrongProcedureInputException {
        Procedure procedure = createProcedure(descriptor);

        Representation[] inputRepresentations = descriptor.getInputRepresentationArray();
        String[] parameters = descriptor.getParametersArray();
        Representation outputRepresentation = null;
        if (procedure.hasOutput()) {
            outputRepresentation =
                    Environment.getInstance().getEditorHandler().createEditor(descriptor.getOutputRepresentation().getId()).getData();
            descriptor.getOutputRepresentation().setRepresentation(outputRepresentation);
        }

        procedure.assignInputRepresentation(inputRepresentations);
        procedure.assignInputParameters(parameters);
        if(procedure.hasOutput()) {
            procedure.assignOutputRepresentation(outputRepresentation);
        }



        String paramtersCheck = procedure.checkInputParameters();
        if(!Procedure.CHECK_OK.equals(paramtersCheck)) {
            if(paramtersCheck == null || paramtersCheck.equals(Procedure.CHECK_NOT_OK)) {
                throw new WrongProcedureInputException("Wrong parameters.");
            } else {
                throw new WrongProcedureInputException(paramtersCheck);
            }
        }

        String representationCheck = procedure.checkInputRepresentation();
        if(!Procedure.CHECK_OK.equals(representationCheck)) {
            if(representationCheck == null || representationCheck.equals(Procedure.CHECK_NOT_OK)) {
                throw new WrongProcedureInputException("Wrong input representations.");
            } else {
                throw new WrongProcedureInputException(representationCheck);
            }
        }

        procedure.create();

        showProcedureWindow(procedure);
    }

    public void closingProcedure(Procedure procedure) {
        procedures.remove(procedure);
    }

    public Procedure getCurrentProcedure() {
        return currentProcedure;
    }

    public void setCurrentProcedure(Procedure currentProcedure) {
        this.currentProcedure = currentProcedure;
    }
}
