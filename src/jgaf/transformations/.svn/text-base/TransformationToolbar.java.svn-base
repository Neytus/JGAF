/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.transformations;

import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import jgaf.automaton.fa.AutomatonTableEditor;
import jgaf.automaton.fa.EditorState;
import jgaf.automaton.fa.FSAutomatonEditor;
import jgaf.automaton.fa.StateDiagramEditor;
import jgaf.gui.TransformationPanel;

/**
 *
 * @author hanis
 */
public class TransformationToolbar extends JToolBar {



    private JButton previousButton;
    private JButton nextButton;
    private JButton playButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton endButton;

    private JTextField delayText;

    private static final int DEFAULT_DELAY = 200;


    private final TransformationPanel transformationPanel;

    public TransformationToolbar(TransformationPanel transformationPanel) {
        super();
        this.transformationPanel = transformationPanel;
        init();
    }


    private void init() {
        setRollover(true);
        setFloatable(false);
        setPreferredSize(new Dimension(28,28));



        previousButton = new JButton();
        previousButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/previous.png")));
        previousButton.setToolTipText("previous");
        previousButton.setFocusable(false);
        previousButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        previousButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transformationPanel.previous();
            }
        });
        add(previousButton);


        nextButton = new JButton();
        nextButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/next.png")));
        nextButton.setToolTipText("next");
        nextButton.setFocusable(false);
        nextButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nextButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transformationPanel.next();
            }
        });
        add(nextButton);

        addSeparator();



        playButton = new JButton();
        playButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/play.png")));
        playButton.setToolTipText("play");
        playButton.setFocusable(false);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handlePlay();
            }
        });
        add(playButton);


        pauseButton = new JButton();
        pauseButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/pause.png")));
        pauseButton.setToolTipText("pause");
        pauseButton.setFocusable(false);
        pauseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pauseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               handlePause();
            }
        });
        add(pauseButton);


        stopButton = new JButton();
        stopButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/stop.png")));
        stopButton.setToolTipText("stop");
        stopButton.setFocusable(false);
        stopButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stopButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleStop();
            }
        });
        add(stopButton);


        endButton = new JButton();
        endButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/end.png")));
        endButton.setToolTipText("end");
        endButton.setFocusable(false);
        endButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        endButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleEnd();
            }
        });
        add(endButton);

        addSeparator();



        add(new JLabel("delay: "));
        delayText = new JTextField(String.valueOf(DEFAULT_DELAY));
        delayText.setToolTipText("delay");
      //  delayText.setFocusable(false);
        add(delayText);
        add(new JLabel("ms"));


    }


    private int getDelay() {
        return Integer.valueOf(delayText.getText());
    }


    private void handlePlay() {
        try {
            int delay = getDelay();
            transformationPanel.play(delay);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(transformationPanel,
                    "Dealy has to be a positive integer.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void handleStop() {
        transformationPanel.stop();
    }

    private void handlePause() {
        transformationPanel.pause();
    }

    private void handleEnd() {
        transformationPanel.end();
    }



}


