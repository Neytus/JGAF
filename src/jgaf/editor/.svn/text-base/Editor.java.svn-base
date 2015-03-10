    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.editor;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jgaf.Representation;
import jgaf.environment.Environment;
import jgaf.exporter.GraphicsExporter;
import jgaf.exporter.SVGExporter;
import jgaf.exporter.TXTExporter;
import jgaf.exporter.TXTWriterException;
import jgaf.gui.EditorFrame;
import org.apache.batik.svggen.SVGGraphics2DIOException;

/**
 *
 * @author hanis
 */
public abstract class Editor {

    private boolean initiated = false;

    private EditorFrame editorFrame;

    private boolean editable = true;
    private String instaneceName = "untitled";

    private boolean changed = false;
    private boolean saved = false;

    private String id;
    private File file = null;


    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

        
    
    
    
    
    public abstract void repaint();

    public abstract void create();
    
    public abstract JPanel getFace();

    public abstract JPanel getRepresenter();

    public abstract void setData(Representation data);

    public abstract Representation getData();

    public abstract boolean open(File file);

    public abstract boolean save(File file);



    public boolean exportSVG(File file) {
        try {
            SVGExporter.exportComponentGraphics(getRepresenter(), file);
            return true;
        } catch (UnsupportedEncodingException ex) {
        } catch (SVGGraphics2DIOException ex) {
        } catch (FileNotFoundException ex) {
        }
        return false;
    }
    
    
    
    public boolean exportPNG(File file) {
        try {
            GraphicsExporter.saveComponentAsGraphics(getRepresenter(), file, "png");
            return true;
        } catch (IOException ex) {            
        }
        return false;
    }


    public boolean exportGIF(File file) {
        try {
            GraphicsExporter.saveComponentAsGraphics(getRepresenter(), file, "gif");
            return true;
        } catch (IOException ex) {
        }
        return false;
    }


    public boolean exportJPG(File file) {
        try {
            GraphicsExporter.saveComponentAsGraphics(getRepresenter(), file, "jpg");
            return true;
        } catch (IOException ex) {
        }
        return false;
    }


    public boolean exportTXT(File file) {
        try {
            TXTExporter.writeStringToFile(file, getData().toString());
            return true;
        } catch (TXTWriterException ex) {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }



    
    public final String getName() {
        return instaneceName;
    }

    public final void setName(String name) {
        this.instaneceName = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    



    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public boolean isInitiated() {
        return initiated;
    }
    
    public final String getId() {
        return id;
    }

    public final void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof Editor) {
           Editor editor = (Editor)obj;
           if(editor.getName().equals(getName())){
                return true;
           }
        }
        return false;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.instaneceName != null ? this.instaneceName.hashCode() : 0);
        return hash;
    }


    public EditorFrame getEditorFrame() {
        return editorFrame;
    }
    

    public void setEditorFrame(EditorFrame editorFrame) {
        this.editorFrame = editorFrame;
    }


    public final JFrame getFrame() {
         return Environment.getInstance().getFrame();
    }



}
