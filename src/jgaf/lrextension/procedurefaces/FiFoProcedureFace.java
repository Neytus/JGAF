/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedurefaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import jgaf.Constants.MathConstants;
import jgaf.grammar.DescriptionDiaog;
import jgaf.lrextension.guitools.FiFoEquationsDialog;
import jgaf.lrextension.FiFoUtils;
import jgaf.lrextension.StringOutputUtils;
import jgaf.lrextension.guitools.CgramTableModel;
import jgaf.lrextension.guitools.CustomProcedureToolbar;
import jgaf.lrextension.guitools.FiFoTableModel;
import jgaf.lrextension.guitools.PeachTableRenderer;
import jgaf.lrextension.guitools.GUIPrep;
import jgaf.lrextension.guitools.MinSizedTable;
import jgaf.lrextension.guitools.SteppedProcedure;
import jgaf.lrextension.WString;
import jgaf.lrextension.guitools.GramTable;
import jgaf.lrextension.procedures.FiFoProcedure;
import jgaf.procedure.DefaultProcedureLogger;

/**
 *
 * @author g
 */
public class FiFoProcedureFace extends JPanel implements SteppedProcedure {

    private DefaultProcedureLogger logger;
    private JTable gTable;
    private JTable fiTable;
    private JTable foTable;
    private FiFoProcedure cProc;
    private WString nons;
    
    private List<String> logSequence;
   
    public FiFoProcedureFace(FiFoProcedure cProc){
        super(new BorderLayout());
        this.cProc = cProc;
        nons = cProc.getNonts();
        logSequence=cProc.getMsgSeq();
        
        
        setLayout(new BorderLayout());
        createToolbar(cProc.getMsgSeq().size());

        logger = new DefaultProcedureLogger();
        JScrollPane scroller = new JScrollPane(logger);

      //  gTable = new MinSizedTable();
        
        gTable = new GramTable(cProc.getG());
        fiTable = new MinSizedTable();
        foTable = new MinSizedTable();

        JTableHeader gheader = gTable.getTableHeader();

        gheader.setBackground(Color.yellow);
        gheader.setReorderingAllowed(false);
        JScrollPane gPane = new JScrollPane(gTable);

        JSplitPane lPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                          gPane, scroller);

        JSplitPane rPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                          GUIPrep.prepareTablePanel(fiTable, "FIRST table"), GUIPrep.prepareTablePanel(foTable, "FOLLOW table"));

        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                                  lPane, rPane);

        mainSplitPane.setResizeWeight(0.4);
        mainSplitPane.setOneTouchExpandable(true);
        rPane.setResizeWeight(0.5);
        rPane.setOneTouchExpandable(true);
        lPane.setResizeWeight(0.5);
        add(mainSplitPane, BorderLayout.CENTER);

        fiTable.setDefaultRenderer(Object.class, new PeachTableRenderer());
        fiTable.setShowHorizontalLines(true);
        foTable.setDefaultRenderer(Object.class, new PeachTableRenderer());
        foTable.setShowHorizontalLines(true);

        //CgramTableModel mg = new CgramTableModel(cProc.getG());
        //gTable.setModel(mg);
        toStep(0);

    }

 private String composetOldLogText(int step) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < step; i++) {
            sb.append(logSequence.get(i)).append("\n");
        }
        return sb.toString();
    }
 
   public void toStep(int step) {
        FiFoTableModel fi = new FiFoTableModel(nons, cProc.getFiSeq().get(step));
        fiTable.setModel(fi);
        FiFoTableModel fo = new FiFoTableModel(nons, cProc.getFoSeq().get(step));
        foTable.setModel(fo);

        logger.setLog(composetOldLogText(step),logSequence.get(step));
        
    }

    private void createToolbar(int steps) {
            final CustomProcedureToolbar toolbar;
                toolbar=new CustomProcedureToolbar(steps, this);
                JButton equalsButton = new JButton();
                equalsButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/equals.png")));
                equalsButton.setToolTipText("view equations");
                equalsButton.setFocusable(false);
                equalsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                equalsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                equalsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FiFoEquationsDialog fifoD= new FiFoEquationsDialog(cProc.getFrame(), cProc.getFiEqString(), cProc.getFoEqString());
                fifoD.setVisible(true);
            }
        });
                
                
                toolbar.add(equalsButton);
                
            add(toolbar,BorderLayout.NORTH);
            equalsButton.setEnabled(true);
    }
            
}
