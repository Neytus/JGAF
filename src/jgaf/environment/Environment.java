/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.environment;

import java.io.File;
import java.net.URISyntaxException;
import javax.swing.JFrame;
import jgaf.editor.EditorsHandler;
import jgaf.gui.MainFrame;
import jgaf.importer.XMLImporter;
import jgaf.procedure.ProcedureHandler;
import jgaf.register.EditorRegister;
import jgaf.register.ProcedureRegister;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class Environment {


    private MainFrame mainFrame;

    private static Environment INSTANCE = null;



    private static final String CONFIG_PATH = "config" + File.separator;

    private static final String PROCEDURE_REGISTER_PATH = CONFIG_PATH + File.separator + "register" + File.separator + "procedures.xml";
    private static final String EDITOR_REGISTER_PATH = CONFIG_PATH + File.separator + "register" + File.separator + "editors.xml";
    private static final String PROPERTIES_FILE_PATH = CONFIG_PATH + "config.properties";



    private String projectPath;


   // private ProcedureRegister procedureRegister;
  //  private EditorRegister editorRegister;
    private EditorsHandler editorHandler;
    private ProcedureHandler procedureHandler;


    private Environment(MainFrame mainFrame) throws DocumentException, URISyntaxException {
        this.mainFrame = mainFrame;
        setProjectPath();
        createEditorHandler();
        createProcedureHandler();
        //createProcedureRegister();
    }


    public JFrame getFrame() {
        return mainFrame;
    }

    public EditorsHandler getEditorHandler() {
        return editorHandler;
    }


    public static Environment getInstance() {
        return INSTANCE;
    }

    public static Environment createInstance(MainFrame mainFrame) throws DocumentException, URISyntaxException {
        if(INSTANCE == null) {
            INSTANCE = new Environment(mainFrame);
        }
        return INSTANCE;
    }


    private void setProjectPath() throws URISyntaxException {
        projectPath = new File(PropertiesHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        System.out.println("--" + projectPath + "--");
        if (projectPath.endsWith("build")) {
            projectPath = projectPath.substring(0, projectPath.length() - 5);
        } else {
            projectPath = projectPath + File.separator;
        }
    }

    private String getProjectPath() {
        return projectPath;
    }

    private String getProcedureRegisterPath() {
        //return getClass().getResource("/config/register/procedures.xml").getFile();
        //return "config/register/procedures.xml";
        return getProjectPath() + PROCEDURE_REGISTER_PATH;
    }

    private String getEditorRegisterPath() {
        //return getClass().getResource("/config/register/editors.xml").getFile();
        //return "config/register/editors.xml";
        return getProjectPath() + EDITOR_REGISTER_PATH;
    }


    public String getPropertiesPath() {
        //return getClass().getResource("/config/config.properties").getFile();
        //return "config/config.properties";
        return getProjectPath() + PROPERTIES_FILE_PATH;
    }


    private ProcedureRegister createProcedureRegister() throws DocumentException {
        ProcedureRegister procedureRegister = new ProcedureRegister();
        procedureRegister.setDescriptorList(XMLImporter.getProcedureDescriptors(new File(getProcedureRegisterPath())));
        return procedureRegister;
    }


    private void createEditorHandler() throws DocumentException {
        editorHandler = new EditorsHandler(mainFrame);
        EditorRegister editorRegister = createEditorRegister();
        editorHandler.setEditorRegister(editorRegister);
    }


    private void createProcedureHandler() throws DocumentException {
        procedureHandler = new ProcedureHandler(mainFrame);
        ProcedureRegister procedureRegister = createProcedureRegister();
        procedureHandler.setProcedureRegister(procedureRegister);
    }


    private EditorRegister createEditorRegister() throws DocumentException {
        EditorRegister editorRegister = new EditorRegister();
        editorRegister.setDescriptorList(XMLImporter.getEditorDescriptors(new File(getEditorRegisterPath())));
        return editorRegister;
    }


    public ProcedureHandler getProcedureHandler() {
        return procedureHandler;
    }


}
