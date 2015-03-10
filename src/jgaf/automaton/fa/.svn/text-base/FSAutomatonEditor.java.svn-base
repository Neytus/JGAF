/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Dimension;
import jgaf.Representation;
import jgaf.editor.Editor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.exporter.SVGExporter;
import jgaf.exporter.XMLExporter;
import jgaf.importer.XMLImporter;
import jgaf.environment.PropertiesHandler;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.dom4j.DocumentException;


/**
 *
 * @author hanis
 */
public class FSAutomatonEditor extends Editor {

    public static final int STATE_DIAGRAM = 0;
    public static final int STATE_TRANSITION_TABLE = 1;

    private StateDiagramEditor stateDiagramEditor;
    private AutomatonTableEditor transitionTableEditor;

    //private JFrame mainFrame;
    private int currentEditor;
    private Automaton automaton;


    private FSAutomatonEditorFace face;


    public FSAutomatonEditor() {
    }

    public void create() {        
        this.automaton = new Automaton();
        
        stateDiagramEditor = new StateDiagramEditor(this);
        transitionTableEditor = new AutomatonTableEditor(this);
        currentEditor = STATE_DIAGRAM;

        face = new FSAutomatonEditorFace(this);
    }

    public StateDiagramEditor getStateDiagramEditor() {
        return stateDiagramEditor;
    }

    public AutomatonTableEditor getTransitionTableEditor() {
        return transitionTableEditor;
    }

    public JFrame getMainFrame() {
        return getFrame();
    }



    public void setEditor(int editor) {
        if(currentEditor != editor) {
            currentEditor = editor;
            face.changeRepresenter();
            getEditor().repaint();
            //toolBar.repaintToolBar();
            //repaintToolBar();
           // getEditor().repaint();
        }
    }

    public FSAutomatonSubEditor getEditor() {
        if(currentEditor == STATE_DIAGRAM) {
            return stateDiagramEditor;
        } else {
            return transitionTableEditor;
        }
    }




    public int getEditorType() {
        return currentEditor;
    }

    public FSAutomatonSubEditor getEditor(int editorType) {
        if(editorType == STATE_DIAGRAM) {
            return stateDiagramEditor;
        } else {
            return transitionTableEditor;
        }
    }



    
//    public void paint(Graphics2D g2d) {
//        getEditor().paint(g2d);
//    }


    public void redo() {
        getEditor().redo();
    }


    public void undo() {
        getEditor().undo();
    }


    public void zoomIn() {
        getEditor().zoomIn();
    }


    public void zoomOut() {
        getEditor().zoomOut();
    }


    public void clearZoom() {
        getEditor().clearZoom();
    }




//    @Override
//    public boolean save(File file) {
//        return getEditor(STATE_DIAGRAM).save(file);
//    }
//
//    @Override
//    public boolean open(File file) {
//        if(!getEditor(STATE_DIAGRAM).open(file)) {
//            return false;
//        }
//        ((AutomatonTableEditor)getEditor(STATE_TRANSITION_TABLE)).setAutomaton(((StateDiagramEditor)getEditor(STATE_DIAGRAM)).getAutomaton());
//        return true;
//    }
//
//    @Override
//    public boolean exportXML(File file) {
//        return getEditor(STATE_DIAGRAM).exportXML(file);
//    }
//
//    @Override
//    public boolean importXML(File file) {
//        return getEditor(STATE_DIAGRAM).importXML(file);
//    }

    @Override
    public boolean exportSVG(File file) {
        Painter painter = null;
        if (getEditorType() == STATE_DIAGRAM) {
            painter = ((StateDiagramEditor) getEditor()).getAutomatonPainter();
        } else if (getEditorType() == STATE_TRANSITION_TABLE) {
            painter = ((AutomatonTableEditor) getEditor()).getPainter();
        }
        try {
            SVGExporter.exportAutomatonGraphics(painter, file);
            return true;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FSAutomatonEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SVGGraphics2DIOException ex) {
            Logger.getLogger(FSAutomatonEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FSAutomatonEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }






    @Override
    public boolean exportPNG(File file) {
        try {
            ImageIO.write(getEditor().getImage(), "png", file);
     //       JOptionPane.showMessageDialog(null, "File saved successfully!", "Image export", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException ex) {
     //       JOptionPane.showMessageDialog(null, "Error while saving file!", "Image export", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    @Override
    public boolean exportGIF(File file) {
        try {
            ImageIO.write(getEditor().getImage(), "gif", file);
     //       JOptionPane.showMessageDialog(null, "File saved successfully!", "Image export", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException ex) {
     //       JOptionPane.showMessageDialog(null, "Error while saving file!", "Image export", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }


    @Override
    public boolean exportJPG(File file) {
        try {
            ImageIO.write(getEditor().getImage(), "jpg", file);
     //       JOptionPane.showMessageDialog(null, "File saved successfully!", "Image export", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException ex) {
     //       JOptionPane.showMessageDialog(null, "Error while saving file!", "Image export", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }






    @Override
    public boolean save(File file) {
        return exportXML(file);
    }


    @Override
    public boolean open(File file) {
        return importXML(file);
    }


    
    public boolean exportXML(File file) {
        try {
            XMLExporter.saveAutomaton(getAutomaton(), stateDiagramEditor.getLabels(), file);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FSAutomatonEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    

    public boolean importXML(File file) {
        try {
            setAutomaton(XMLImporter.getAutomaton(file));
            stateDiagramEditor.setLabels(XMLImporter.getLabels(file));
            return true;
        } catch (DocumentException ex) {
            Logger.getLogger(FSAutomatonEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Automaton getAutomaton() {
        return automaton;
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    @Override
    public void setData(Representation data) {
        if(data != null) {
            setAutomaton((Automaton) data);
        }
    }

    @Override
    public Representation getData() {
        return getAutomaton();
    }



//
//    @Override
//    public Dimension getPreferredSize() {
//        return getEditor().getPreferredSize();
//    }

//    public Rectangle getBounds() {
//        return stateDiagramEditor.getBounds();
//    }

//    public void applyGEMLayout() {
//        stateDiagramEditor.applyGEMLayout();
//    }

    public void center() {
        stateDiagramEditor.centerAndScaleGraphics(true);
    }


    public Dimension getViewportSize() {
        return face.getViewPanelSize();
    }


    @Override
    public JPanel getFace() {
        return face;
    }


    @Override
    public JPanel getRepresenter() {
        return getEditor().getRepresenter();
    }


    public void clearHighlighting() {
        PropertiesHandler properties = PropertiesHandler.getInstance();
        for (State state : getAutomaton().getStates()) {
            state.getVisualProperties().setFillColor(properties.getAutomatonStateFillColor());
            state.getVisualProperties().setFontColor(properties.getAutomatonStateFontColor());
            state.getVisualProperties().setStrokeColor(properties.getAutomatonStateStrokeColor());
        }
        for (Transition transition : getAutomaton().getTransitions()) {
            transition.getVisualProperties().setFontColor(properties.getAutomatonTransitionFontColor());
            transition.getVisualProperties().setStrokeColor(properties.getAutomatonTransitionStrokeColor());
        }
    }

    @Override
    public void repaint() {
        getEditor().repaint();
    }



}
