/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InputChooserPanel.java
 *
 * Created on Apr 26, 2011, 12:06:01 AM
 */

package jgaf.procedure;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jgaf.JgafFileException;
import jgaf.editor.Editor;
import jgaf.editor.EditorDescriptor;
import jgaf.editor.EditorsHandler;
import jgaf.editor.NoSuchEditorException;
import jgaf.environment.Environment;
import jgaf.environment.PropertiesHandler;
import jgaf.importer.XMLImporter;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class InputRepresentationChooserPanel extends javax.swing.JPanel {

    private ProcedureRepresentation representation;
    private ChooserComboboxModel comboModel;


    private Editor editorFromFile = null;


    private EditorDescriptor editorDescriptor;


    private static final int FROM_FILE = 0;
    private static final int FROM_EDITORS = 1;
    private int fromWhat = FROM_FILE;

    public InputRepresentationChooserPanel(ProcedureRepresentation representation) throws NoSuchEditorException {
        initComponents();
        this.representation = representation;
        initMyComponents();
        centerDialog();
    }

    private void initMyComponents() throws NoSuchEditorException {
        descriptionTxt.setText(representation.getDescription());
        textFilePath.setText("");
        String id = representation.getId();
        EditorsHandler editorHandler = Environment.getInstance().getEditorHandler();
        editorDescriptor = editorHandler.getEditorRegister().getDescriptorById(id);
        editorFromFile = editorHandler.createEditor(editorDescriptor);
        List<Editor> appropriateEditors = Environment.getInstance().getEditorHandler().getEditorsWithId(id);
        if(appropriateEditors.isEmpty()) {            
            comboEditors.addItem("There is no appropriate editor.");
            comboEditors.setEnabled(false);
            radioFromEditor.setEnabled(false);
            radioFromFile.setSelected(true);
            fromWhat = FROM_FILE;
        } else {
            comboModel = new ChooserComboboxModel(appropriateEditors);
            comboEditors.setModel(comboModel);
            comboEditors.setSelectedIndex(0);
            radioFromEditor.setSelected(true);
            fromWhat = FROM_EDITORS;
        }
        handleRadioChanged();
    }


    private void handleRadioChanged() {
        setRepresentaion();
        comboEditors.setEnabled(fromWhat == FROM_EDITORS);
        textFilePath.setEnabled(fromWhat == FROM_FILE);
        buttonLoad.setEnabled(fromWhat == FROM_FILE);
    }

    private void setRepresentaion() {
        if(fromWhat == FROM_EDITORS) {
            representation.setRepresentation(((Editor)comboModel.getSelectedItem()).getData().clone());
        } else {
            if(textFilePath.getText().equals("")) {
                representation.setRepresentation(null);
            } else {
                representation.setRepresentation(editorFromFile.getData());
            }
        }
    }

    private void centerDialog() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        comboEditors = new javax.swing.JComboBox();
        buttonLoad = new javax.swing.JButton();
        textFilePath = new javax.swing.JTextField();
        descriptionTxt = new javax.swing.JLabel();
        radioFromEditor = new javax.swing.JRadioButton();
        radioFromFile = new javax.swing.JRadioButton();

        comboEditors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEditorsActionPerformed(evt);
            }
        });

        buttonLoad.setText("Load");
        buttonLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoadActionPerformed(evt);
            }
        });

        textFilePath.setEditable(false);
        textFilePath.setText("jTextField1");

        descriptionTxt.setText("jLabel2");

        buttonGroup.add(radioFromEditor);
        radioFromEditor.setSelected(true);
        radioFromEditor.setText("Find opened");
        radioFromEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFromEditorActionPerformed(evt);
            }
        });

        buttonGroup.add(radioFromFile);
        radioFromFile.setText("From file");
        radioFromFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioFromFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioFromEditor)
                            .addComponent(radioFromFile))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonLoad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
                            .addComponent(comboEditors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {radioFromEditor, radioFromFile});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioFromEditor)
                    .addComponent(comboEditors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioFromFile)
                    .addComponent(buttonLoad)
                    .addComponent(textFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void radioFromFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFromFileActionPerformed
        fromWhat = FROM_FILE;
        handleRadioChanged();
    }//GEN-LAST:event_radioFromFileActionPerformed

    private void radioFromEditorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioFromEditorActionPerformed
        fromWhat = FROM_EDITORS;
        handleRadioChanged();
    }//GEN-LAST:event_radioFromEditorActionPerformed

    private void buttonLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoadActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(PropertiesHandler.getInstance().getFileLastPath()));
        fc.setDialogTitle("Open");
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                setEditorFromFile(file);
                setRepresentaion();
                PropertiesHandler.getInstance().setFileLastPath(file.getAbsolutePath());
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(this, "The file is not compatible with JGAF.", "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (JgafFileException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            } catch (WrongProcedureInputException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_buttonLoadActionPerformed

    private void comboEditorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEditorsActionPerformed
        setRepresentaion();
    }//GEN-LAST:event_comboEditorsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton buttonLoad;
    private javax.swing.JComboBox comboEditors;
    private javax.swing.JLabel descriptionTxt;
    private javax.swing.JRadioButton radioFromEditor;
    private javax.swing.JRadioButton radioFromFile;
    private javax.swing.JTextField textFilePath;
    // End of variables declaration//GEN-END:variables




//    public Object getChosenData() throws WrongProcedureInputException {
//        if(fromWhat == FROM_EDITORS) {
//            return comboModel.getSelectedItem();
//        } else {
//            String file = textFilePath.getText();
//            if(file.equals("")) {
//                throw new WrongProcedureInputException("No input selected.");
//            } else {
//                return editorFromFile.getData();
//            }
//        }
//    }


    private void setEditorFromFile(File file) throws DocumentException, JgafFileException, WrongProcedureInputException {
        String id = XMLImporter.getRepresentationId(file);
        if(!representation.getId().equals(id)) {
            throw new WrongProcedureInputException(file.getName() + " doesn't contain an appropriate representation.");
        }
        if (editorFromFile.open(file)) {
            textFilePath.setText(file.getAbsolutePath());
        } else {
            throw new WrongProcedureInputException("The file is not compatible with JGAF.");
        }
    }

    



}
