/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.Timer;

/**
 *
 * @author g
 */
public class CustomProcedureToolbar extends JToolBar {

    private JButton previousButton;
    private JButton nextButton;
    private JButton playButton;
    private JButton stopButton;
    private JButton pauseButton;
    private JButton endButton;
    private int nOfSteps;
    private JTextField delayText;
    private static final int DEFAULT_DELAY = 200;
    private final SteppedProcedure stepper;
    private int step = 0;
    private Timer timer;

    public CustomProcedureToolbar(int nOfSteps,
                                  SteppedProcedure stepper) {
        this.nOfSteps = nOfSteps;
        this.stepper = stepper;
        init();
    }

    public CustomProcedureToolbar(int nOfSteps,
                                  SteppedProcedure stepper,
                                  int orientation) {
        super(orientation);
        this.nOfSteps = nOfSteps;
        this.stepper = stepper;
        init();
    }

    public CustomProcedureToolbar(SteppedProcedure stepper) {
        this.nOfSteps = 0;
        this.stepper = stepper;
        init();
    }

    private void init() {
        setRollover(true);
        setFloatable(false);
        setPreferredSize(new Dimension(28, 28));



        previousButton = new JButton();
        previousButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/previous.png")));
        previousButton.setToolTipText("previous");
        previousButton.setFocusable(false);
        previousButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        previousButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previous();
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
                next();
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
                pause();
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
                stop();
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
                end();
            }
        });
        add(endButton);

        addSeparator();



        add(new JLabel("delay: "));
        delayText = new JTextField(String.valueOf(DEFAULT_DELAY));
        delayText.setPreferredSize(new Dimension(100, (int) delayText.getPreferredSize().getHeight()));
        delayText.setToolTipText("delay");
        add(delayText);
        add(new JLabel("ms"));
        addSeparator();
    }

    private int getDelay() {
        return Integer.valueOf(delayText.getText());
    }

    private void handlePlay() {
        try {
            int delay = getDelay();
            play(delay);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Dealy has to be a positive integer.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void setNumberOfSteps(int numberOfSteps) {
        pause();
        this.nOfSteps = numberOfSteps;
        step = 0;
        stepper.toStep(step);
    }

    public void next() {
        pause();
        if (isNext()) {
            step++;
            stepper.toStep(step);
        }
    }

    public void previous() {
        pause();
        if (isPrevious()) {
            step--;
            stepper.toStep(step);
        }
    }

    public void play(int delay) {
        if (!isNext()) {
            step = 0;
            stepper.toStep(step);
        }
        if (timer == null || !timer.isRunning()) {
            timer = new javax.swing.Timer(delay, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (isNext()) {
                        step++;
                        stepper.toStep(step);
                    }
                }
            });
            timer.start();
        }
    }

    public void pause() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    public void stop() {
        pause();
        step = 0;
        stepper.toStep(step);
    }

    public void end() {
        pause();
        step = nOfSteps - 1;
        stepper.toStep(step);
    }

    public boolean isNext() {
        return step < nOfSteps - 1;
    }

    public boolean isPrevious() {
        return step > 0;
    }
}
