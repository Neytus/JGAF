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
        System.out.println("procedury "+procedureRegister.toString());
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


        //JInternalFrame frame = new JInternalFrame(procedure.getNameID(), true, true, true, true);
        //JInternalFrame frame = new JInternalFrame(procedure.getNameID(), true, true, true, true);
        ProcedureFrame frame = new ProcedureFrame(procedure);
        procedure.setProcedureFrame(frame);
        JDesktopPane desktop = mainFrame.getDesktop();
        frame.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
        desktop.add(frame, 2);
        desktop.setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
        frame.add(procedure.getFace(), BorderLayout.CENTER);

        procedure.getFace().repaint();
        //setEditorShown(true);
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



//    public List<ProcedureController> getProcedureControllers() {
//        return procedureControllers;
//    }

//    public void setCurrentEditor(Editor editor) {
//        currentEditor = editor;
//    }


    public Procedure createProcedure(ProcedureDescriptor descriptor) {
        Procedure procedure = null;
        System.out.println("descriptor je : "+descriptor.toString());
        try {
            try {
                //editor = (Editor) Class.forName("jgaf.automaton.fa.FSAutomatonEditor").newInstance();
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





//    public Editor newEditor(EditorDescriptor descriptor) {
//        Editor editor = createEditor(descriptor);
//        editor.setName(DEFAULT_NAME + getNextUntitledNumber());
//        addEditor(editor);
//        showEditorWindow(editor);
//        return editor;
//    }



//    public List<Editor> getEditorsWithId(String id) {
//        List<Editor> suitableEditors = new ArrayList<Editor>();
//        for (Editor editor : editors) {
//            if(editor.getId().equals(id)) {
//                suitableEditors.add(editor);
//            }
//        }
//        return suitableEditors;
//    }
//
//
//    public boolean isEditorShown() {
//        return currentEditor != null && editorShown;
//    }

//    public void setEditorShown(boolean isEditorShown) {
//        this.editorShown = isEditorShown;
//    }
//
//    public Editor getCurrentEditor() {
//        return currentEditor;
//    }

//    @Override
//    public void saveFile() {
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File(PropertiesHandler.getInstance().getFileLastPath()));
//        fc.setDialogType(JFileChooser.SAVE_DIALOG);
//        fc.setDialogTitle("Save Automaton");
//        int returnVal = fc.showOpenDialog(getMainFrame());
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            PropertiesHandler.getInstance().setFileLastPath(fc.getSelectedFile().getAbsolutePath());
//            PropertiesHandler.getInstance().addRecentFile(fc.getSelectedFile());
//            FileImporter.persistAutomaton(fc.getSelectedFile(), getAutomaton());
//        }
//    }


//    public boolean save() {
//        if (isEditorShown()) {
//            if(currentEditor.isSaved()) {
//                if (currentEditor.save(currentEditor.getFile())) {
//                    currentEditor.setChanged(false);
//                    return true;
//                }
//            } else {
//                return saveAs();
//            }
//        }
//        return false;
//    }
//
//
//    private File getChosenFile(String title, String extension) {
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File(PropertiesHandler.getInstance().getFileLastPath()));
//        fc.setDialogType(JFileChooser.SAVE_DIALOG);
//        fc.setDialogTitle(title);
//        int returnVal = fc.showOpenDialog(mainFrame);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            File file = fc.getSelectedFile();
//            file = attachExtension(file, extension);
//            PropertiesHandler.getInstance().setFileLastPath(file.getAbsolutePath());
//            if (file.exists()) {
//                String message = "A file named \"" + file.getName() + "\" already exists.  Do you want to replace it?";
//                int answer = JOptionPane.showConfirmDialog(mainFrame,
//                        message, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//                if (answer == JOptionPane.NO_OPTION) {
//                    return null;
//                }
//            }
//            return file;
//        }
//        return null;
//    }
//
//
//    public boolean saveAs() {
//        if (isEditorShown()) {
//            File file = getChosenFile("save", "jgaf");
//            if (file != null) {
//                if (currentEditor.save(file)) {
//                    currentEditor.setName(file.getName());
//                    currentEditor.setFile(file);
//                    currentEditor.setSaved(true);
//                    currentEditor.setChanged(false);
//                    mainFrame.addToTitle(currentEditor.getName());
//                    PropertiesHandler.getInstance().addRecentFile(file);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//
//    public boolean exportXML() {
//        if (isEditorShown()) {
//            File file = getChosenFile("XML Export", "xml");
//            if (file != null) {
//                if (currentEditor.exportXML(file)) {
//                    currentEditor.setName(file.getName());
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    public boolean exportSVG() {
//        if (isEditorShown()) {
//            File file = getChosenFile("SVG Export", "svg");
//            if (file != null) {
//                if (currentEditor.exportSVG(file)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private File attachExtension(File file, String extension) {
//        if(!file.getName().endsWith("." + extension)) {
//            File dest = new File(file.getAbsolutePath() + "." + extension);
//            return dest;
//        }
//        return file;
//    }
//
//    public boolean exportPNG() {
//        if (isEditorShown()) {
//            File file = getChosenFile("PNG Export", "png");
//            if (file != null) {
//                if (currentEditor.exportPNG(file)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//
//    public void openEditor(Editor editor) {
//        addEditor(editor);
//        showEditorWindow(editor);
//    }
//
//
//    public boolean open() throws DocumentException, JgafFileException, NoSuchEditorException {
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File(PropertiesHandler.getInstance().getFileLastPath()));
//        fc.setDialogTitle("Open");
//        int returnVal = fc.showOpenDialog(mainFrame);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            File file = fc.getSelectedFile();
//            if(open(file)) {
//                PropertiesHandler.getInstance().setFileLastPath(file.getAbsolutePath());
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean open(File file) throws DocumentException, JgafFileException, NoSuchEditorException {
//        String id = XMLImporter.getRepresentationId(file);
//        String editorClassName = getEditorRegister().getEditorClassPath(id);
//        EditorDescriptor descriptor = getEditorRegister().getDescriptorById(id);
//        Editor editor = createEditor(descriptor);
//        if (editor.open(file)) {
//            editor.setName(file.getName());
//            editor.setFile(file);
//            editor.setSaved(true);
//            editor.setChanged(false);
//            PropertiesHandler.getInstance().addRecentFile(file);
//            openEditor(editor);
//            return true;
//        }
//        return false;
//    }
//
//
//
//    public boolean importXML() throws DocumentException, JgafFileException, NoSuchEditorException {
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File(PropertiesHandler.getInstance().getFileLastPath()));
//        fc.setDialogTitle("XML Import");
//        int returnVal = fc.showOpenDialog(mainFrame);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//                File file = fc.getSelectedFile();
//                String id = XMLImporter.getRepresentationId(file);
//                EditorDescriptor descriptor = getEditorRegister().getDescriptorById(id);
//                Editor editor = createEditor(descriptor);
//                if (editor.importXML(file)) {
//                    PropertiesHandler.getInstance().setFileLastPath(file.getAbsolutePath());
//                    editor.setName(file.getName());
//                    editor.setSaved(false);
//                    editor.setChanged(false);
//                    openEditor(editor);
//                    return true;
//                }
//        }
//        return false;
//    }
//
//
//    private int getNextUntitledNumber() {
//        return untitledNumber++;
//    }
//
//
//    public boolean isCurrentEditor(Editor editor) {
//        return editor != null && isEditorShown() && editor.equals(currentEditor);
//    }
}
