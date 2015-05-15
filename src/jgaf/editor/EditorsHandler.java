/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import jgaf.JgafFileException;
import jgaf.gui.MainFrame;
import jgaf.importer.XMLImporter;
import jgaf.environment.PropertiesHandler;
import jgaf.gui.EditorFrame;
import jgaf.gui.FrameTool;
import jgaf.register.EditorRegister;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class EditorsHandler {
  

    private List<Editor> editors;
    private Editor currentEditor;
    private MainFrame mainFrame;  
    private int untitledNumber = 1;
    public static final String DEFAULT_NAME = "untitled";
    private EditorRegister editorRegister;

    public EditorsHandler(MainFrame mainFrame) {
        this.editors = new ArrayList<>();
        this.mainFrame = mainFrame;
    }

    
    public void nullCurrentEditor() {
        this.currentEditor = null;
    }


    public void setEditorRegister(EditorRegister editorRegister) {
        this.editorRegister = editorRegister;
    }

    public EditorRegister getEditorRegister() {
        return editorRegister;
    }


    public void addEditor(Editor editor) {
        editors.add(editor);
    }

    public void removeEditor(Editor editor) {
        editors.remove(editor);
    }

    public void putEditorToFront(Editor editor) {
        if (editors.contains(editor)) {
            editor.getEditorFrame().toFront();
            try {
                editor.getEditorFrame().setSelected(true);
                editor.getEditorFrame().setIcon(false);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(EditorsHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            setCurrentEditor(editor);
        }
    }


    public void showEditorWindow(Editor editor) {
        setCurrentEditor(editor);
        EditorFrame frame = new EditorFrame(editor);
        editor.setEditorFrame(frame);
        JDesktopPane desktop = mainFrame.getDesktop();
        frame.setBounds(0, 0, desktop.getWidth(), desktop.getHeight());
        desktop.add(frame, 2);
        desktop.setBackground(Color.DARK_GRAY);
        frame.setVisible(true);
        frame.add(editor.getFace(), BorderLayout.CENTER);

        editor.getFace().repaint();

    }


    public List<Editor> getEditors() {
        return editors;
    }

    public List<JInternalFrame> getEditorFrames(List<Editor> editorList) {
        List<JInternalFrame> frameList = new ArrayList<>();
        for (Editor editor : editorList) {
            frameList.add(editor.getEditorFrame());
        }
        return frameList;
    }

    public void showAllEditors() {
        FrameTool.organizeFrames(getEditorFrames(getEditors()), mainFrame.getDesktop());
    }

    public void minimizeAllEditors() {
        FrameTool.minimizeFrames(getEditorFrames(getEditors()), mainFrame.getDesktop());
    }

    public void closeAllEditors() {
        for (Editor editor : editors) {
            editor.getEditorFrame().dispose();
        }
        editors.clear();
        nullCurrentEditor();
    }




    public void setCurrentEditor(Editor editor) {
        currentEditor = editor;
    }

    public Editor createEditor(String id) {         
        EditorDescriptor descriptor = null;
        try {
            descriptor = getEditorRegister().getDescriptorById(id);            
        } catch (NoSuchEditorException ex) {
            System.out.println("no such editor");
        }
        return createEditor(descriptor);        
    }


    public Editor createEditor(EditorDescriptor descriptor) {
        Editor editor = null;
        try {
            try {
                editor = (Editor) Class.forName(descriptor.getClassPath()).newInstance();
            } catch (InstantiationException ex) {
                System.out.println("1");
            } catch (IllegalAccessException ex) {
                System.out.println("2");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("3");
        }
        editor.setId(descriptor.getId());
        editor.create();
        return editor;
    }


    public Editor newEditor(EditorDescriptor descriptor) {
        Editor editor = createEditor(descriptor);
        editor.setName(DEFAULT_NAME + getNextUntitledNumber());    
        addEditor(editor);
        showEditorWindow(editor);
        return editor;
    }


    public Editor newEditor(Editor editor) {
        editor.setName(DEFAULT_NAME + getNextUntitledNumber());
        addEditor(editor);
        showEditorWindow(editor);
        return editor;
    }



    public List<Editor> getEditorsWithId(String id) {
        List<Editor> suitableEditors = new ArrayList<>();
        for (Editor editor : editors) {
            if(editor.getId().equals(id)) {
                suitableEditors.add(editor);
            }
        }
        return suitableEditors;
    }


    public boolean isEditorShown() {
        return !editors.isEmpty() && currentEditor != null;
    }

    public Editor getCurrentEditor() {
        return currentEditor;
    }

    public boolean save() {
        if (isEditorShown()) {
            if(currentEditor.isSaved()) {
                if (currentEditor.save(currentEditor.getFile())) {
                    currentEditor.setChanged(false);
                    return true;
                }
            } else {
                return saveAs();
            }
        }
        return false;
    }

    private File getChosenFile(String title, String extension) {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(PropertiesHandler.getInstance().getFileLastPath()));
        fc.setDialogTitle(title);
        fc.setApproveButtonText(title);
        int returnVal = fc.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            file = attachExtension(file, extension);
            PropertiesHandler.getInstance().setFileLastPath(file.getAbsolutePath());
            if (file.exists()) {
                String message = "A file named \"" + file.getName() + "\" already exists.  Do you want to replace it?";
                int answer = JOptionPane.showConfirmDialog(mainFrame,
                        message, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (answer == JOptionPane.NO_OPTION) {
                    return null;
                }
            }
            return file;
        }
        return null;
    }

    public boolean saveAs() {
        if (isEditorShown()) {
            File file = getChosenFile("Save", "jgaf");
            if (file != null) {
                if (currentEditor.save(file)) {                   
                    currentEditor.setName(getFreeName(file.getName()));
                    currentEditor.setFile(file);
                    currentEditor.setSaved(true);
                    currentEditor.setChanged(false);
                    currentEditor.getEditorFrame().setTitle(currentEditor.getName());
                    PropertiesHandler.getInstance().addRecentFile(file);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean exportSVG() {
        if (isEditorShown()) {
            File file = getChosenFile("SVG Export", "svg");
            if (file != null) {
                if (currentEditor.exportSVG(file)) {
                    return true;
                }
            }
        }
        return false;
    }

    private File attachExtension(File file, String extension) {
        if(!file.getName().endsWith("." + extension)) {
            File dest = new File(file.getAbsolutePath() + "." + extension);
            return dest;
        }
        return file;
    }

    public boolean exportPNG() {
        if (isEditorShown()) {
            File file = getChosenFile("PNG Export", "png");
            if (file != null) {
                if (currentEditor.exportPNG(file)) {                    
                    return true;
                }
            }
        }
        return false;
    }


    public boolean exportGIF() {
        if (isEditorShown()) {
            File file = getChosenFile("GIF Export", "gif");
            if (file != null) {
                if (currentEditor.exportGIF(attachExtension(file, "gif"))) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean exportTXT() {
        if (isEditorShown()) {
            File file = getChosenFile("TXT Export", "txt");
            if (file != null) {
                if (currentEditor.exportTXT(attachExtension(file, "txt"))) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean exportJPG() {
        if (isEditorShown()) {
            File file = getChosenFile("JPG Export", "jpg");
            if (file != null) {
                if (currentEditor.exportJPG(attachExtension(file, "jpg"))) {
                    return true;
                }
            }
        }
        return false;
    }




    public void openEditor(Editor editor) {
        addEditor(editor);
        showEditorWindow(editor);        
    }

    //if the internal frame is closing
    public void closingEditor(Editor editor) {
        removeEditor(editor);
    }



    public boolean open() throws DocumentException, JgafFileException, NoSuchEditorException {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(PropertiesHandler.getInstance().getFileLastPath()));
        fc.setDialogTitle("Open");
        int returnVal = fc.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(open(file)) {
                PropertiesHandler.getInstance().setFileLastPath(file.getAbsolutePath());
                return true;
            }
        }
        return false;
    }

    public boolean open(File file) throws DocumentException, JgafFileException, NoSuchEditorException {
        String id = XMLImporter.getRepresentationId(file);
        EditorDescriptor descriptor = getEditorRegister().getDescriptorById(id);
        Editor editor = createEditor(descriptor);
        if (editor.open(file)) {
            editor.setName(getFreeName(file.getName()));
            editor.setFile(file);
            editor.setSaved(true);
            editor.setChanged(false);
            PropertiesHandler.getInstance().addRecentFile(file);
            openEditor(editor);
            return true;
        }
        return false;
    }

    private String getFreeName(String name) {
        return getFreeName(name, 1);
    }

    private String getFreeName(String name, int num) {
        String suffix = "";
        if (num > 1) {
            suffix = "(" + num + ")";
        }
        for (Editor editor : editors) {
            if(editor.getName().equals(name + suffix)) {
                return getFreeName(name, num + 1);
            }
        }
        return name + suffix;
    }

    private int getNextUntitledNumber() {
        return untitledNumber++;
    }


    public boolean isCurrentEditor(Editor editor) {
        return editor != null && isEditorShown() && editor.equals(currentEditor);
    }
    
    public boolean allSaved() {
        for (Editor editor : getEditors()) {
            if (editor.isChanged()) {
                return false;
            }
        }      
        return true;
    }
}
