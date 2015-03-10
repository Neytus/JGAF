/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author hanis
 */
public class OutputProcedureToolbar extends JToolBar {




    private final DefaultProcedureFace face;

    public OutputProcedureToolbar(DefaultProcedureFace face) {
        super();
        this.face = face;
        init();
    }


    private void init() {
        setRollover(true);
        setFloatable(false);
        setPreferredSize(new Dimension(28,28));

        JButton openButton = new JButton();
        openButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/open.png")));
        openButton.setToolTipText("Open in editor");
        openButton.setFocusable(false);
        openButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                face.previous();
            }
        });
        add(openButton);
    }

}
