/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author hanis
 */
public class AutomatonTableToolBar extends JToolBar {


    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton clearZoomButton;

    private AutomatonTableEditor editor;


    public AutomatonTableToolBar(AutomatonTableEditor editor) {
        super();
        this.editor = editor;
        init();
    }


    private void init() {
        setRollover(true);
        setPreferredSize(new Dimension(28,28));





        zoomInButton = new JButton();
        zoomInButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/zoomIn.png")));
        zoomInButton.setToolTipText("zoom in");
        zoomInButton.setFocusable(false);
        zoomInButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomInButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.zoomIn();
            }
        });
        add(zoomInButton);


        zoomOutButton = new JButton();
        zoomOutButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/zoomOut.png")));
        zoomOutButton.setToolTipText("zoom out");
        zoomOutButton.setFocusable(false);
        zoomOutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomOutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.zoomOut();
            }
        });
        add(zoomOutButton);


        clearZoomButton = new JButton();
        clearZoomButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/clearZoom.png")));
        clearZoomButton.setToolTipText("clearZoom");
        clearZoomButton.setFocusable(false);
        clearZoomButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clearZoomButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clearZoomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.clearZoom();
            }
        });
        add(clearZoomButton);
    }

}
