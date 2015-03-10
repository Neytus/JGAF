/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StatePropertiesDialog.java
 *
 * Created on Nov 28, 2010, 2:08:49 PM
 */
package jgaf.automaton.fa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class StatePropertiesDialog extends javax.swing.JDialog {

    private State state;
    private StateDiagramEditor editor;

    /** Creates new form StatePropertiesDialog */
    public StatePropertiesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public StatePropertiesDialog(java.awt.Frame parent, StateDiagramEditor editor, State state) {
        this(parent, true);
        this.editor = editor;
        this.state = state;
        init();
    }

    private void centerDialog() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    private void init() {
        setTitle("State Properties");
        textName.setText(state.getName());
        checkAccepting.setSelected(state.isAccepting());
        checkInitial.setSelected(state.isInitial());
        //textXPos.setText(String.valueOf(state.getVisualProperties().getXPos()));
        //textYPos.setText(String.valueOf(state.getVisualProperties().getYPos()));
        buttonFontColor.setBackground(state.getVisualProperties().getFontColor());
        buttonFillColor.setBackground(state.getVisualProperties().getFillColor());
        buttonStrokeColor.setBackground(state.getVisualProperties().getStrokeColor());
        centerDialog();
    }

    private Color getChosenFontColor() {
        return buttonFontColor.getBackground();
    }

    private Color getChosenFillColor() {
        return buttonFillColor.getBackground();
    }

    private Color getChosenStrokeColor() {
        return buttonStrokeColor.getBackground();
    }

    private void applyChanges() throws InputFormatException {

        String name = textName.getText();
        if(name.equals("")) {
            throw new InputFormatException("State name has to be a non-empty string.");
        }
        State newState = (State) state.clone();

        newState.getVisualProperties().setFillColor(getChosenFillColor());
        newState.getVisualProperties().setFontColor(getChosenFontColor());
        newState.getVisualProperties().setStrokeColor(getChosenStrokeColor());

//        try {
//            newState.getVisualProperties().setXPos(Integer.valueOf(textXPos.getText()));
//        } catch (NumberFormatException e) {
//            throw new InputFormatException("X coordinate has to be an integer.");
//        }
//        try {
//            newState.getVisualProperties().setYPos(Integer.valueOf(textYPos.getText()));
//        } catch (NumberFormatException e) {
//            throw new InputFormatException("Y coordinate has to be an integer.");
//        }
        newState.setAccepting(checkAccepting.isSelected());
        boolean initial = checkInitial.isSelected();
        //newState.set (checkInitial.isSelected());
        editor.modifyState(state, newState, initial);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        checkInitial = new javax.swing.JCheckBox();
        checkAccepting = new javax.swing.JCheckBox();
        buttonCancel = new javax.swing.JButton();
        buttonOk = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        buttonFontColor = new javax.swing.JButton();
        buttonFillColor = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        buttonStrokeColor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("State Properties");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic"));

        jLabel1.setText("Name:");

        jLabel2.setText("Type:");

        checkInitial.setText("Initial state");
        checkInitial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkInitialActionPerformed(evt);
            }
        });

        checkAccepting.setText("Accepting state");
        checkAccepting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAcceptingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkAccepting)
                    .addComponent(checkInitial)
                    .addComponent(textName, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(checkInitial))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkAccepting)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        buttonOk.setText("Ok");
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Colors"));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Foreground:");

        buttonFontColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFontColorActionPerformed(evt);
            }
        });

        buttonFillColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFillColorActionPerformed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Background:");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Stroke:");

        buttonStrokeColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStrokeColorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonFontColor, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(buttonFillColor, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(buttonStrokeColor, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel7, jLabel8, jLabel9});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonFillColor, buttonFontColor, buttonStrokeColor});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(buttonFontColor, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(buttonFillColor, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonStrokeColor, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(buttonOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCancel))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonOk)
                    .addComponent(buttonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkInitialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkInitialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkInitialActionPerformed

    private void checkAcceptingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAcceptingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkAcceptingActionPerformed

    private void buttonFontColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFontColorActionPerformed
        Color color = JColorChooser.showDialog(this, "Foreground color", state.getVisualProperties().getFontColor());
        if (color != null) {
            buttonFontColor.setBackground(color);
        }
    }//GEN-LAST:event_buttonFontColorActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        try {
            applyChanges();
            dispose();
        } catch (InputFormatException ex) {
             JOptionPane.showMessageDialog(this, ex.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_buttonOkActionPerformed

    private void buttonFillColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFillColorActionPerformed
        Color color = JColorChooser.showDialog(this, "Background color", state.getVisualProperties().getFillColor());
        if (color != null) {
            buttonFillColor.setBackground(color);
        }
    }//GEN-LAST:event_buttonFillColorActionPerformed

    private void buttonStrokeColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStrokeColorActionPerformed
        Color color = JColorChooser.showDialog(this, "Stroke color", state.getVisualProperties().getStrokeColor());
        if (color != null) {
            buttonStrokeColor.setBackground(color);
        }

    }//GEN-LAST:event_buttonStrokeColorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                StatePropertiesDialog dialog = new StatePropertiesDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonFillColor;
    private javax.swing.JButton buttonFontColor;
    private javax.swing.JButton buttonOk;
    private javax.swing.JButton buttonStrokeColor;
    private javax.swing.JCheckBox checkAccepting;
    private javax.swing.JCheckBox checkInitial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField textName;
    // End of variables declaration//GEN-END:variables
}
