/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.regex;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import jgaf.Constants.MathConstants;
import jgaf.Representation;
import jgaf.editor.Editor;
import jgaf.automaton.fa.undo.UndoRedoHandler;
import jgaf.exporter.GraphicsExporter;
import jgaf.exporter.XMLExporter;
import jgaf.importer.XMLImporter;
import jgaf.regex.undo.ChangeRegexStep;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class RegularExpressionEditor extends Editor {

    private RegularExpression regularExpression;
    private RegularExpressionFace face;
    private UndoRedoHandler undoHandler;
    private String regexString;

    private static final String EPSILON_SUBSTITUTION = "eps";

    public RegularExpressionEditor() {
    }

    @Override
    public void create() {        
        this.face = new RegularExpressionFace(this);
        initiate(new RegularExpression(""));
        setEditable(true);           
    }


    public void initiate(RegularExpression regularExpression) {
        this.undoHandler = new UndoRedoHandler();
        this.regexString = "";
        setExpression(regularExpression);
        repaint();         
        setChanged(false);
    }

    @Override
    public JPanel getFace() {
        return face;
    }

    public void redo() {
        undoHandler.redo();
        repaint();
    }

    public void undo() {
        //System.out.println("undo");
        //System.out.println(undoHandler.writeUndoStack());
        undoHandler.undo();
        repaint();

    }



    @Override
    public boolean save(File file) {
        try {
            XMLExporter.saveRegularExpression(getRegex(),file);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(RegularExpressionEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }



    @Override
    public boolean open(File file) {
        try {
            initiate(XMLImporter.getRegularExpression(file));
            return true;
        } catch (DocumentException ex) {
            Logger.getLogger(RegularExpressionEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void repaint() {
        face.repaintMe();
    }


    public RegularExpression getRegex() {
        return regularExpression;
    }


    public void setRegex(RegularExpression regex) {
        this.regularExpression = regex;
    }


    public void setConciseForm() {
        if(hasWellFormedRegex()) {
            setExpression(getRegex().writeConcise());
        }
    }


    public void setFullyEncapsulatedForm() {
        if(hasWellFormedRegex()) {
            setExpression(getRegex().writeFullyEncapsuleted());
        }
    }




    public void setExpression(String re, boolean undo) {
        setChanged(true);
        if(regexString.equals(re) && undo) {
            return;
        }
        if(undo) {
     //       System.out.println("putting: " + regexString + ", " + re);
            undoHandler.addStep(new ChangeRegexStep(this, re, regexString));
        }
      //  System.out.println("old:" + regexString);
    //    System.out.println("new:" + re);
        regexString = re;
        try {
    //        System.out.println("valid");
            re = re.replaceAll(EPSILON_SUBSTITUTION, MathConstants.EPSILON);
            if(re.equals("")) {
                re = MathConstants.EMPTY_SET;
            }

            if(re.contains(MathConstants.EMPTY_SET) && re.length() > 1) {
                re = re.replaceAll(MathConstants.EMPTY_SET, "");
            }
            RegularExpression regex;
            if(re.equals(MathConstants.EMPTY_SET)) {
                regex = new RegularExpression("");
            } else {
                regex = new RegularExpression(re);
            }
            //face.addInfoText(regex.writeConcise() + "\n");
            face.writeInfo(regex.writeAlphabet());
            face.getRegexPane().setNormalText(re);
            setRegex(regex);
            //addInfoText(re + "-------------\n" + regex.geiveMePositionos() + "\n-------------\n");
        } catch (WrongExpressionException ex) {
     //       System.out.println("invalid");
            String[] split = ex.getMessage().split(", ");
            //face.addInfoText(ex.getMessage() + "\n");
            face.writeWarning("RE is not well-formed : " +
                    ex.getMessage().substring(0, ex.getMessage().indexOf(",")) + ".");
            //System.out.println("--" + split[1]+"--");
            int from = Integer.valueOf(split[1]);
            int to = Integer.valueOf(split[2]);
            face.getRegexPane().setHighlightedText(re, from, to);
            setRegex(null);
        }
    }


    public void setExpression(String re) {
        setExpression(re, true);
    }


    public void setExpression(RegularExpression regex) {
        setExpression(regex.writeAll(), false);
    }


    public boolean hasWellFormedRegex() {
        return this.regularExpression != null;
    }



    public JPanel getRepresenter() {
        return face.getRepresenter();
    }

    @Override
    public void setData(Representation data) {
        System.out.println("Data = "+data.toString());
        RegularExpression re = (RegularExpression) data;
        initiate(re);
    }

    @Override
    public Representation getData() {
        return regularExpression;
    }



    @Override
    public boolean exportPNG(File file) {
        try {
            GraphicsExporter.saveComponentAsGraphics(getRepresenter(), file, "png", true);
            return true;
        } catch (IOException ex) {
        }
        return false;
    }


    public boolean exportGIF(File file) {
        try {
            GraphicsExporter.saveComponentAsGraphics(getRepresenter(), file, "gif", true);
            return true;
        } catch (IOException ex) {
        }
        return false;
    }


    public boolean exportJPG(File file) {
        try {
            GraphicsExporter.saveComponentAsGraphics(getRepresenter(), file, "jpg", true);
            return true;
        } catch (IOException ex) {
        }
        return false;
    }

}
