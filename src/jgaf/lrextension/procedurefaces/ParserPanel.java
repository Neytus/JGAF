/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedurefaces;

import jgaf.lrextension.guitools.GotoTableModel;
import jgaf.lrextension.guitools.ActionTableModel;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import jgaf.Constants.MathConstants;
import jgaf.grammar.Grammar;
import jgaf.lrextension.WString;
import jgaf.lrextension.guitools.ActionTable;
import jgaf.lrextension.guitools.CustomProcedureToolbar;
import jgaf.lrextension.guitools.GUIPrep;
import jgaf.lrextension.guitools.GotoTable;
import jgaf.lrextension.guitools.PeachTableRenderer;
import jgaf.lrextension.guitools.SteppedProcedure;
import com.google.common.collect.Lists;
import java.util.Iterator;
import jgaf.grammar.Symbol;
import jgaf.lrextension.procedures.ParseStep;
import jgaf.lrextension.guitools.ParseStepsTable;
import jgaf.lrextension.guitools.ParseTableModel;
import jgaf.lrextension.procedures.ParserSimCalc;
import jgaf.lrextension.procedures.ParserTablesCalc;
/**
 *
 * @author g
 */
public class ParserPanel extends JPanel implements SteppedProcedure {

    private int step;
    private CustomProcedureToolbar toolbar;
    private WString inputWord;
    private ParserSimCalc psc;
    private Grammar g;
    private ParserTablesCalc ptc;
    private ParseStepsTable parseTable;
    private GotoTable gotoTable;
    private ActionTable actionTable;
    private JTextPane messagePane;

    public ParserPanel(ParserTablesCalc ptc,
                       ParserSimCalc psc) {
        super(new BorderLayout());
        this.ptc = ptc;
        this.psc = psc;
        //this.step = step;
        draw();
        // parseInput();
    }

    public void toStep(int step) {
        actionTable.decolor();
        gotoTable.decolor();
        
        if (inputWord == null) {
            parseInput();
        }
        StringBuilder sb = new StringBuilder("Parsing word " + inputWordString() + ", calcuation of next parsing step:\n");

        List<ParseStep> parseStepSubList = psc.getSimSteps().subList(0, step);
        parseTable.setModel(new ParseTableModel(parseStepSubList));

        if (parseStepSubList.isEmpty()) {
            sb.append("\tparsing will start in state 0");
        } else {
            ParseStep currentLastStep = parseStepSubList.get(step - 1);
            actionTable.color(currentLastStep.getKtoRead(), currentLastStep.getState());
            sb.append("\tAction table:\tLooking up \"");
            sb.append(currentLastStep.getKtoRead());
            sb.append("\" in state ");
            sb.append(currentLastStep.getState()+"\n");
            ParseStep futureStep;
            switch (currentLastStep.getNextAction()) {
                case REDUCE:
                    futureStep = psc.getSimSteps().get(step);

                    gotoTable.color(futureStep.getTopStackSymbol(), futureStep.getPreviouseState());
                    
                    sb.append("\t\tnext action is REDUCE\n");
                    
                    sb.append("\tGoto table: \tafter reduction in step ");
                    sb.append(futureStep.getPreviouseState());        
                    sb.append(" with top stack symbol \"") ;
                    sb.append(futureStep.getTopStackSymbol());
                    sb.append("\"\r\t\tnext state will be: " + futureStep.getState());
                    break;
                case SHIFT:
                    futureStep = psc.getSimSteps().get(step);
                    gotoTable.color(currentLastStep.getFirstToRead(), currentLastStep.getState());
                    sb.append("\t\tnext action is SHIFT\n");
                    sb.append("\tGoto table: \tafter shift in step ");
                    sb.append(currentLastStep.getState());
                    sb.append(" with top stack symbol \"") ;
                    sb.append(currentLastStep.getFirstToRead());
                    sb.append("\"\r\t\tnext state will be: " + futureStep.getState());
                    break;
                case ACCEPT:
                    sb.append("\t\tnext action is ACCEPT because this is the first rule of augmented grammar \n");
                    sb.append("\t\toutput sequence of trasformation rules: "+ Lists.reverse(currentLastStep.getRules()));
                    break;
                case ERRORR:
                    sb.append("\t\tnext action is ERROR\n");
                    sb.append("\t\t"+inputWordString() + " is not in lenguague generated by this grammar");
                    break;
                case CONFLICT:
                    sb.append("\t\tnext action is CONFLICT\n");
                    break;
                case SHIFTERR:
                    gotoTable.color(currentLastStep.getFirstToRead(), currentLastStep.getState());
                    sb.append("\t\tnext action is SHIFT\n");
                    sb.append("\tGoto table: \tafter shift in step ");
                    sb.append(currentLastStep.getState());
                    sb.append(" with top stack symbol \"") ;
                    sb.append(currentLastStep.getFirstToRead());
                    sb.append("\"\r\t\tnext state will be: not found in goto table.\n");
                    sb.append("\t\t"+inputWordString() + " is not in lenguague generated by this grammar");
                    break;
                    case MOREINPUTERR:
                    
                    sb.append("\t\tnext action is accept, ");
                    sb.append("but there is still unread part of the input\n");
                    
                    
                    sb.append("\t\t"+inputWordString() + " is not in lenguague generated by this grammar");
                    break;
            }
            
        }


        messagePane.setText(sb.toString());
    }

    private void draw() {
        toolbar = createToolbar();

        parseTable = new ParseStepsTable();
        parseTable.initalize();
        messagePane = new JTextPane();

        JScrollPane mScrollPane = new JScrollPane(messagePane);

        JPanel parseTablePanel = GUIPrep.prepareTablePanel(parseTable, "Parsing process");
        JSplitPane gotoActionPane = prepareGotoActionSplitPane();
        JSplitPane rSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                               parseTablePanel, mScrollPane);
        
        rSplitPane.setResizeWeight(0.5);

        JSplitPane centralSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                                     gotoActionPane, rSplitPane);
        centralSplitPane.setResizeWeight(0.7);
        add(centralSplitPane, BorderLayout.CENTER);









    }

    private CustomProcedureToolbar createToolbar() {
        final CustomProcedureToolbar toolbar;
        toolbar = new CustomProcedureToolbar(this);
        JButton inputWordButton = new JButton();
        inputWordButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/reload.png")));
        inputWordButton.setToolTipText("change word");
        inputWordButton.setFocusable(false);
        
        inputWordButton.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        inputWordButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        inputWordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parseInput();
            }
        });


        toolbar.add(inputWordButton);

        add(toolbar, BorderLayout.NORTH);
        inputWordButton.setEnabled(true);
        return toolbar;
    }

    private String showImputDialog() {
        return (String) JOptionPane.showInputDialog(
                toolbar,
                "Please input word to be analyzed: \n leave blank for empty word",
                "Input",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");

    }

    protected void parseInput() {
        String dialogWord = showImputDialog();
        if (dialogWord == null) {
            return;
        }
        if (dialogWord.equalsIgnoreCase("eps.")) {
            dialogWord = "";
        }
        
        WString wInputWord=new WString(dialogWord);
        if (wInputWord.hasNonterminal()){
        JOptionPane.showMessageDialog(toolbar,
    "Input word can't have nonterminals.",
    "wrong input",
    JOptionPane.ERROR_MESSAGE);
        return;
        
        }
        
        inputWord = wInputWord;
        
        
        psc.calc(wInputWord);
        
        //parseTable.setModel(new ParseTableModel(psc.getSimSteps()));

        toolbar.setNumberOfSteps(psc.getSimSteps().size() + 1);

    }

    public Boolean hasInput() {
        return !(inputWord == null);
    }

    private JSplitPane prepareGotoActionSplitPane() {
        GotoTableModel gtm = new GotoTableModel(ptc);
        gotoTable = new GotoTable(gtm);
        gotoTable.setShowGrid(true);

        ActionTableModel atm = new ActionTableModel(ptc);
        actionTable = new ActionTable(atm);
        actionTable.setShowGrid(true);


        actionTable.setDefaultRenderer(Object.class, new PeachTableRenderer());
        gotoTable.setDefaultRenderer(Object.class, new PeachTableRenderer());
        JPanel goPanel = GUIPrep.prepareTablePanel(gotoTable, "Goto Table");
        JPanel actionPanel = GUIPrep.prepareTablePanel(actionTable, "Action Table");





        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                              goPanel, actionPanel);
        splitPane.setResizeWeight(0.5);

        return splitPane;

    }
    
    
    private String inputWordString(){
        if (inputWord.isEmpty()) return MathConstants.EPSILON;
            return "\""+inputWord+"\"";
            
        }
    
}
