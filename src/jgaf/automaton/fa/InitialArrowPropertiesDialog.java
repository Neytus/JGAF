/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InitialArrowPropertiesDialog.java
 *
 * Created on Mar 15, 2011, 12:08:46 AM
 */

package jgaf.automaton.fa;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JColorChooser;
import javax.swing.SpinnerNumberModel;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class InitialArrowPropertiesDialog extends javax.swing.JDialog {

    private StateDiagramEditor editor;

    /** Creates new form InitialArrowPropertiesDialog */
    public InitialArrowPropertiesDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }


    public InitialArrowPropertiesDialog(java.awt.Frame parent, StateDiagramEditor editor) {
        this(parent, true);
        this.editor = editor;
        init();
    }

    private void centerDialog() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    private void init() {
        setTitle(jgaf.l18n.Resource.getValue("editor.fa.dialogs.title.initialArrow"));

        InitialArrow initialArrow = editor.getInitArrow();
        initArrowStrokeColorButton.setBackground(initialArrow.getColor());
        initArrowStrokeStyleCombo.setModel(new DefaultComboBoxModel(StrokeStyle.STYLES));
        initArrowStrokeStyleCombo.setSelectedItem(initialArrow.getStrokeStyle().getTypeString());
        initArrowStrokeWidthSpinner.setModel(new SpinnerNumberModel(initialArrow.getStrokeWidth(), 0, 5, 0.5));
        initArrowLengthSpinner.setModel(new SpinnerNumberModel(initialArrow.getLength(), 5, 50, 1));
        centerDialog();
    }

    private Color getChosenColor() {
        return initArrowStrokeColorButton.getBackground();
    }




    private void applyChanges() {
        InitialArrow newInitialArrow = new InitialArrow();
        newInitialArrow.setColor(getChosenColor());

        StrokeStyle strokeStyle = new StrokeStyle((String) initArrowStrokeStyleCombo.getSelectedItem());
        newInitialArrow.setStrokeStyle(strokeStyle);

        double width = (Double) initArrowStrokeWidthSpinner.getValue();
        newInitialArrow.setStrokeWidth(width);

        int length = (Integer) initArrowLengthSpinner.getValue();
        newInitialArrow.setLength(length);

        editor.modifyInitialArrow(newInitialArrow);
    }




    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelInitArrowStroke = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        initArrowStrokeWidthSpinner = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        initArrowStrokeColorButton = new javax.swing.JButton();
        initArrowStrokeStyleCombo = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        initArrowLengthSpinner = new javax.swing.JSpinner();
        buttonOk = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelInitArrowStroke.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText(jgaf.l18n.Resource.getValue("editor.fa.dialogs.width") + ":");

        initArrowStrokeWidthSpinner.setOpaque(false);
        initArrowStrokeWidthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                initArrowStrokeWidthSpinnerStateChanged(evt);
            }
        });
        initArrowStrokeWidthSpinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                initArrowStrokeWidthSpinnerAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText(jgaf.l18n.Resource.getValue("editor.fa.dialogs.color") + ":");

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText(jgaf.l18n.Resource.getValue("editor.fa.dialogs.style") + ":");

        initArrowStrokeColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initArrowStrokeColorButtonActionPerformed(evt);
            }
        });

        initArrowStrokeStyleCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        initArrowStrokeStyleCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initArrowStrokeStyleComboActionPerformed(evt);
            }
        });

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText(jgaf.l18n.Resource.getValue("editor.fa.dialogs.length") + ":");

        initArrowLengthSpinner.setOpaque(false);
        initArrowLengthSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                initArrowLengthSpinnerStateChanged(evt);
            }
        });
        initArrowLengthSpinner.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                initArrowLengthSpinnerAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout panelInitArrowStrokeLayout = new javax.swing.GroupLayout(panelInitArrowStroke);
        panelInitArrowStroke.setLayout(panelInitArrowStrokeLayout);
        panelInitArrowStrokeLayout.setHorizontalGroup(
            panelInitArrowStrokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInitArrowStrokeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInitArrowStrokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInitArrowStrokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(initArrowStrokeWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(initArrowStrokeColorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(initArrowStrokeStyleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(initArrowLengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelInitArrowStrokeLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel13, jLabel14, jLabel15, jLabel16});

        panelInitArrowStrokeLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {initArrowLengthSpinner, initArrowStrokeColorButton, initArrowStrokeStyleCombo, initArrowStrokeWidthSpinner});

        panelInitArrowStrokeLayout.setVerticalGroup(
            panelInitArrowStrokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInitArrowStrokeLayout.createSequentialGroup()
                .addGroup(panelInitArrowStrokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(initArrowStrokeWidthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInitArrowStrokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(initArrowStrokeColorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInitArrowStrokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(initArrowStrokeStyleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInitArrowStrokeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(initArrowLengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addContainerGap())
        );

        buttonOk.setText(jgaf.l18n.Resource.getValue("buttons.ok") + ":");
        buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOkActionPerformed(evt);
            }
        });

        buttonCancel.setText(jgaf.l18n.Resource.getValue("buttons.cancel") + ":");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelInitArrowStroke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCancel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInitArrowStroke, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonOk)
                    .addComponent(buttonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initArrowStrokeWidthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_initArrowStrokeWidthSpinnerStateChanged

}//GEN-LAST:event_initArrowStrokeWidthSpinnerStateChanged

    private void initArrowStrokeWidthSpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_initArrowStrokeWidthSpinnerAncestorAdded

}//GEN-LAST:event_initArrowStrokeWidthSpinnerAncestorAdded

    private void initArrowStrokeColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initArrowStrokeColorButtonActionPerformed
        Color color = JColorChooser.showDialog(this, "Initial Arrow Color", initArrowStrokeColorButton.getBackground());
        if (color != null) {            
            initArrowStrokeColorButton.setBackground(color);            
        }
}//GEN-LAST:event_initArrowStrokeColorButtonActionPerformed

    private void initArrowStrokeStyleComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initArrowStrokeStyleComboActionPerformed

}//GEN-LAST:event_initArrowStrokeStyleComboActionPerformed

    private void initArrowLengthSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_initArrowLengthSpinnerStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_initArrowLengthSpinnerStateChanged

    private void initArrowLengthSpinnerAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_initArrowLengthSpinnerAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_initArrowLengthSpinnerAncestorAdded

    private void buttonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOkActionPerformed
        applyChanges();
        dispose();
}//GEN-LAST:event_buttonOkActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dispose();
}//GEN-LAST:event_buttonCancelActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InitialArrowPropertiesDialog dialog = new InitialArrowPropertiesDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton buttonOk;
    private javax.swing.JSpinner initArrowLengthSpinner;
    private javax.swing.JButton initArrowStrokeColorButton;
    private javax.swing.JComboBox initArrowStrokeStyleCombo;
    private javax.swing.JSpinner initArrowStrokeWidthSpinner;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JPanel panelInitArrowStroke;
    // End of variables declaration//GEN-END:variables

}