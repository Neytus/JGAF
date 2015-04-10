/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import jgaf.Representation;
import jgaf.editor.Editor;
import jgaf.editor.EditorsHandler;
import jgaf.editor.NoSuchEditorException;
import jgaf.environment.Environment;

/**
 *
 * @author hanis
 */
public final class DefaultProcedureFace extends JPanel {

   // private JSplitPane splitPane;

    private DefaultProcedureToolbar toolbar;

    private DefaultProcedure procedure;

    private int step = 0;

    private Timer timer;

    private List<Editor> inputEditors;
    private Editor outputEditor;

    private List<List<Representation>> inputSequence;
    private List<Representation> outputSequence;
    private List<String> logSequence;


    private DefaultProcedureLogger procedureLogger;



    public DefaultProcedureFace(DefaultProcedure procedure) {
        super(new BorderLayout());
        inputEditors = new ArrayList<>();
        assignProcedure(procedure);
        setEditors();
        
        toolbar = new DefaultProcedureToolbar(this);
        toolbar.showOpenButton(procedure.getDescriptor().hasOutput());
        add(toolbar, BorderLayout.NORTH);
        crateMainPanel();
        first();
    }


    private void assignProcedure(DefaultProcedure procedure) {
        this.procedure = procedure;
        procedure.startProcedure();
        inputSequence = procedure.getInputSequence();
        outputSequence = procedure.getOutputSequence();
        logSequence = procedure.getLogSequence();
    }

    private int getNumberOfSteps() {
        return inputSequence.size();
    }
    
    private void setEditors() {
        EditorsHandler editorHandler = Environment.getInstance().getEditorHandler();               
        for (ProcedureRepresentation representation : procedure.getDescriptor().getInputRepresentations()) {
            Editor editor = editorHandler.createEditor(representation.getId());
            editor.setEditable(false);
            inputEditors.add(editor);
        }
        if(procedure.hasOutput()) {
            outputEditor = editorHandler.createEditor(procedure.getDescriptor().getOutputRepresentation().getId());
            
            /**
             * JB 
             * setEditable - must be true 
             */
            outputEditor.setEditable(true);
        }
    }
    
    
    
    private void crateMainPanel() {
        procedureLogger = new DefaultProcedureLogger();                
        JScrollPane scroller = new JScrollPane(procedureLogger);
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                   createRepresentationsPanel(), scroller);
        mainSplitPane.setResizeWeight(0.4);
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setContinuousLayout(true);
        add(mainSplitPane, BorderLayout.CENTER);
    }

    private JComponent createInputRepresentationPanel() {
        switch (procedure.getDescriptor().getInputRepresentationsCardinality()) {
            case 1:
                return new JScrollPane(inputEditors.get(0).getRepresenter());
            case 2: {
                int split = procedure.hasOutput() ? JSplitPane.VERTICAL_SPLIT : JSplitPane.HORIZONTAL_SPLIT;
                JPanel inputPanel1 = inputEditors.get(0).getRepresenter();
                JPanel inputPanel2 = inputEditors.get(1).getRepresenter();
                return createSplitPane(inputPanel1, inputPanel2, true, true, split);
            }
            case 3: {
                JPanel inputPanel1 = inputEditors.get(0).getRepresenter();
                JPanel inputPanel2 = inputEditors.get(1).getRepresenter();
                JPanel inputPanel3 = inputEditors.get(2).getRepresenter();
                JComponent upper = createSplitPane(inputPanel1, inputPanel2, true, true, JSplitPane.HORIZONTAL_SPLIT);
                return createSplitPane(upper, inputPanel3, false, true, JSplitPane.VERTICAL_SPLIT);
            }
            case 4: {
                JPanel inputPanel1 = inputEditors.get(0).getRepresenter();
                JPanel inputPanel2 = inputEditors.get(1).getRepresenter();
                JPanel inputPanel3 = inputEditors.get(2).getRepresenter();
                JPanel inputPanel4 = inputEditors.get(3).getRepresenter();
                JComponent upper = createSplitPane(inputPanel1, inputPanel2, true, true, JSplitPane.HORIZONTAL_SPLIT);
                JComponent lower = createSplitPane(inputPanel3, inputPanel4, true, true, JSplitPane.HORIZONTAL_SPLIT);
                return createSplitPane(upper, lower, false, false, JSplitPane.VERTICAL_SPLIT);
            }
            default: return null;
        }
    }


    private JComponent createSplitPane(JComponent comp1,
                                       JComponent comp2,
                                       boolean scroll1,
                                       boolean scroll2,
                                       int splitType) {
        JComponent c1 = scroll1 ? new JScrollPane(comp1) : comp1;
        JComponent c2 = scroll2 ? new JScrollPane(comp2) : comp2;

        JSplitPane splitPane = new JSplitPane(splitType, c1, c2);

        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.1);
        return splitPane;
    }


    private JComponent createRepresentationsPanel() {
        JComponent input = createInputRepresentationPanel();
        if(procedure.hasOutput()) {
            JPanel outputPanel = outputEditor.getRepresenter();
            return createSplitPane(input, outputPanel, false, true, JSplitPane.HORIZONTAL_SPLIT);
        }
        return input;
    }


    public void moveTo(int step) {
        if(step >= 0 && step < getNumberOfSteps()) {
            this.step = step;
            
            for (int i = 0; i < procedure.getDescriptor().getInputRepresentationsCardinality(); i++) {               
                inputEditors.get(i).setData(inputSequence.get(step).get(i));
                inputEditors.get(i).repaint();                
            }
            if(procedure.hasOutput()) {
                outputEditor.setData(outputSequence.get(step));
                outputEditor.repaint();
                // outputEditor.center();
            }
            procedureLogger.setLog(composetOldLogText(step), logSequence.get(step));                        
            
            toolbar.hideProcedureChooser();
            if(!isNext()) {
                pause();
                toolbar.showProcedureChooser();
            }
        }
    }

    private String composetOldLogText(int step) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < step; i++) {
            sb.append(logSequence.get(i)).append("\n");
        }
        return sb.toString();
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
        moveTo(getNumberOfSteps() - 1);
    }



    public boolean isNext() {
        return step < getNumberOfSteps() - 1;
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


    public void openOutputInEditor() {
        Editor  editor = Environment.getInstance().getEditorHandler().createEditor(procedure.getDescriptor().getOutputRepresentation().getId());
        editor.setData(outputSequence.get(step));
        Environment.getInstance().getEditorHandler().newEditor(editor);
    }


    public ProcedureDescriptor[] getDescriptorArray() {
        List<ProcedureDescriptor> list = new ArrayList<>();
        for (ProcedureDescriptor procedureDescriptor : Environment.getInstance().getProcedureHandler().getProcedureRegister().getDescriptorList()) {
            if(procedureDescriptor.getParameters().isEmpty()
                    && procedureDescriptor.getInputRepresentationsCardinality() == 1
                    && procedureDescriptor.getInputRepresentations().get(0).getId().equals(this.procedure.getDescriptor().getOutputRepresentation().getId())) {
                list.add(procedureDescriptor);
            }
        }
        ProcedureDescriptor[] descriptorArray = new ProcedureDescriptor[list.size()];
        int i = 0;
        for (ProcedureDescriptor d : list) {
            descriptorArray[i++] = d;
        }
        return descriptorArray;
    }

    public void continueWithNewProcedure() {
        ProcedureDescriptor descriptor = (ProcedureDescriptor)JOptionPane.showInputDialog(
                    Environment.getInstance().getFrame(),
                    "Choose another procedure.",
                    "Procedures",
                    JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/transformation/open.png")),
                    getDescriptorArray(), null);

        /*
        System.out.println("CONTINUE with new procedure info: " + JOptionPane.PLAIN_MESSAGE + ", ");
        System.out.println("descriptor: " + getDescriptorArray());
        System.out.println("output sequence:" + outputSequence.size());
        System.out.println("output sequence:" + outputSequence.get(0));
        System.out.println("output sequence:" + outputSequence.get(outputSequence.size() - 1));
        */
                
        if (descriptor != null) {
        descriptor.getInputRepresentations().get(0).setRepresentation(outputSequence.get(outputSequence.size() - 1));
            try {
                ProcedureDialog procedureDialog = new ProcedureDialog(Environment.getInstance().getFrame(), descriptor, true);
            } catch (NoSuchEditorException ex) {
                Logger.getLogger(DefaultProcedureFace.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }






}
