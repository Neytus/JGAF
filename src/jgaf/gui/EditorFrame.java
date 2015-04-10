/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.gui;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
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
        setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
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
        if (editor.isChanged()) {
            String message = "This editor has unsaved content. Do you want to close it anyways?";
                    int answer = JOptionPane.showConfirmDialog(this,
                        message, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (answer == JOptionPane.YES_OPTION) {
                        closingEditor();
                        dispose();
                    }
                    if (answer == JOptionPane.NO_OPTION) {
                        return;
                    }
        } else {
            closingEditor();
            dispose();
        }
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
