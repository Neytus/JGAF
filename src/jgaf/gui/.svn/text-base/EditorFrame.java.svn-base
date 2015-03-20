/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.gui;

import javax.swing.JInternalFrame;
import jgaf.editor.Editor;
import jgaf.environment.Environment;

/**
 *
 * @author hanis
 */
public class EditorFrame extends JInternalFrame{

    private Editor editor;

    public EditorFrame(Editor editor) {
        super(editor.getName(), true, true, true, true);
        this.editor = editor;
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
        activateEditor();
    }

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
        closingEditor();
    }

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
        deactivateProcedure();
    }


    private void closingEditor() {
        Environment.getInstance().getEditorHandler().closingEditor(editor);
    }

    private void activateEditor() {
        Environment.getInstance().getEditorHandler().setCurrentEditor(editor);
    }

    private void deactivateProcedure() {
        Environment.getInstance().getEditorHandler().nullCurrentEditor();
    }

}
