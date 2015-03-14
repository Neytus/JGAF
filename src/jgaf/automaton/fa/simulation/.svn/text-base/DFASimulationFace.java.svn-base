/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa.simulation;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import jgaf.automaton.fa.FSAutomatonEditor;
import jgaf.automaton.fa.StateDiagramEditor;

/**
 *
 * @author hanis
 */
public class DFASimulationFace extends JPanel {

   // private FSAutomatonEditor editor;
    private DFASimulation simulation;
    private DFASimulationToolbar toolbar;
    private DFASimulationControlPanel controlPanel;
    private JScrollPane scroller;

    public DFASimulationFace(DFASimulation simulation) {
        super(new BorderLayout());
        this.simulation = simulation;
        initComponents();
    }
    
    
    private void initComponents() {
        this.toolbar = new DFASimulationToolbar(simulation);
        this.controlPanel = new DFASimulationControlPanel();
        add(toolbar, BorderLayout.NORTH);


        JPanel dfa = simulation.getEditor().getRepresenter();
        scroller = new JScrollPane(dfa);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                   scroller, controlPanel);        
        splitPane.setResizeWeight(0.3);
        splitPane.setOneTouchExpandable(true);
        splitPane.setContinuousLayout(true);
        add(splitPane, BorderLayout.CENTER);
    }

    public void center() {
        double width = scroller.getSize().getWidth();
        double height = scroller.getSize().getHeight();
        ((StateDiagramEditor) simulation.getEditor().getEditor(FSAutomatonEditor.STATE_DIAGRAM)).centerAndScaleGraphics(true, width, height);
    }


    public DFASimulationControlPanel getControlPanel() {
        return controlPanel;
    }

    


}
