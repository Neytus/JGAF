/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import jgaf.editor.Editor;
import jgaf.automaton.fa.FSAutomatonEditor;
import jgaf.automaton.fa.FSAutomatonToolBar;
import jgaf.automaton.fa.FSAutomatonTransformationToolbar;
import jgaf.transformations.Transformation;
import jgaf.transformations.TransformationToolbar;

/**
 *
 * @author hanis
 */
public class TransformationPanel extends JPanel {

    private JSplitPane splitPane;

    private TransformationToolbar toolbar;

    private Transformation transformation;
    private int step = 0;
    private Editor inputEditor;
    private Editor outputEditor;

    private Timer timer;

    public TransformationPanel(Transformation transformation, MainFrame frame) {
        super(new BorderLayout());
        this.transformation = transformation;
        transformation.start();
        inputEditor = new FSAutomatonEditor();
//        inputEditor.setEnvironment(frame);
//        inputEditor.initiate();
//        inputEditor.setData(transformation.getInitialInput());
//
//        outputEditor = new FSAutomatonEditor();
//        outputEditor.setEnvironment(frame);
//        outputEditor.initiate();
//        outputEditor.setData(transformation.getInitialOutput());
//
//
        JPanel inputPanel = new JPanel(new BorderLayout());
 //       inputPanel.add(inputEditor.getCanvas(), BorderLayout.CENTER);
//////////        inputPanel.add(new FSAutomatonTransformationToolbar((FSAutomatonEditor) inputEditor, frame.getEditorHandler()),
//////////                BorderLayout.PAGE_END);

        JPanel outputPanel = new JPanel(new BorderLayout());
   //     outputPanel.add(outputEditor.getCanvas(), BorderLayout.CENTER);
//////        outputPanel.add(new FSAutomatonTransformationToolbar((FSAutomatonEditor) outputEditor, frame.getEditorHandler()),
//////                BorderLayout.PAGE_END);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                   inputPanel, outputPanel);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);


        toolbar = new TransformationToolbar(this);
        add(splitPane, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        JButton buttonNext = new JButton("next");
        buttonNext.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                next();
            }
        });

        JButton buttonPrevious = new JButton("previous");
        buttonPrevious.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previous();
            }
        });
    }

    public void next() {
        if(isNext()) {
            step++;
            moveTo(step);
        }
    }


    public void previous() {
        if(isPrevious()) {
            step--;
            moveTo(step);
        }
    }

    public void first() {
        moveTo(0);
    }

    public void last() {
        moveTo(transformation.getNumberOfSteps() - 1);
    }


    public void moveTo(int step) {
        if(step >= 0 && step < transformation.getNumberOfSteps()) {
            this.step = step;
            inputEditor.setData(transformation.getInput(step));
            outputEditor.setData(transformation.getOutput(step));
           // outputEditor.center();
    //       inputEditor.repaint();
     //       outputEditor.repaint();
            if(!isNext()) {
                pause();
            }
        }
    }


    public boolean isNext() {
        return step < transformation.getNumberOfSteps() - 1;
    }

    public boolean isPrevious() {
        return step > 0;
    }

    public void play(int delay) {
        if(timer == null || !timer.isRunning()) {
            timer = new Timer(delay, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    next();
                }
            });
            timer.start();
        }
    }

    public void pause() {
        if(timer != null && timer.isRunning()) {
            timer.stop();
        }
    }


    public void stop() {
        pause();
        first();
    }

    public void end() {
        pause();
        last();
    }

}