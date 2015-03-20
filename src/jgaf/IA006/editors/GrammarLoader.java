/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.editors;

import java.io.File;
import javax.swing.JPanel;
import jgaf.IA006.gui.GrammarLoaderFrame;
import jgaf.Representation;

/**
 *
 * @author Empt
 */
public class GrammarLoader extends jgaf.editor.Editor
{
    private GrammarLoaderFrame glf = new GrammarLoaderFrame();

    @Override
    public void repaint() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void create() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JPanel getFace() {
        return glf;
    }

    @Override
    public JPanel getRepresenter() {
        return glf;
    }

    @Override
    public void setData(Representation data) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Representation getData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean open(File file) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }

    @Override
    public boolean save(File file) {
        return true;
    }
    
}
