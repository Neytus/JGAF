/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PDASimulatorPanel.java
 *
 * Created on Mar 27, 2011, 7:12:03 PM
 */

package jgaf.automaton.pda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import jgaf.environment.Environment;
import org.dom4j.DocumentException;


/**
 *
 * @author hanis
 */
public class PDASimulatorPanel extends javax.swing.JPanel {

    private static final int WORD = 0;
    private static final int CONF = 1;
    
    private static final int MANUAL = 0;
    private static final int RANDOM = 1;
    private static final int DETERMINISTIC = 2;
    private static final int ACCEPTING = 3;
    
    private PushdownAutomaton automaton;
    private MatrixData matrixData;
    private MatrixTableRender matrixRender;
    
 //   private StackListModel stackListModel; 
    private StackPanel stackPanel = null;
    
    private JLabel labCompltyWord;
    private JLabel labRestWord;
    private JLabel labCurrentChar;
    
    private Random random = null;
    
    private boolean AutomataChosen;
    
    private javax.swing.Timer timer;
    
    private JPanel panelButtons;
    
    private DefaultValues defaultValues;
    
    private boolean simulationInProgress = false;
    
    private DialogTerm progressDialog;

    private Dimension windowDim;
    
    private int simulationType;
    
    public static String ABSOLUT_PATH;

    private JFrame frame;


    public PDASimulatorPanel(JFrame frame, PushdownAutomaton inputAutomaton) {
        this.frame= frame;
        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration().getDevice().getDisplayMode();
        windowDim = new Dimension(displayMode.getWidth(), displayMode.getHeight());
        setPreferredSize(windowDim);

//        try {
//            defaultValues = XMLDataManipulation.getInstance().getDefaultValues();
//            //XMLDataManipulation.getInstance().getDefaultValues();
//        } catch (DocumentException ex) {
//            JOptionPane.showMessageDialog(this, L12N.getValue("warn.errror.op"), L12N.getValue("warn.error"), JOptionPane.ERROR_MESSAGE);
//        }
        try {
            XMLDataManipulation.getInstance().getDesign("config/applicationDesign.xml");//Environment.getInstance().getPDAPropertiesPath());
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(this, L12N.getValue("warn.errror.op"), L12N.getValue("warn.error"), JOptionPane.ERROR_MESSAGE);
        }
        initComponents();
        panelButtonsOut.setLayout(new BorderLayout());
        panelButtons = new JPanel(new GridLayout(0,1));
        panelButtonsOut.add(panelButtons, BorderLayout.NORTH);

        initMyComponents();
        progressDialog = new DialogTerm(frame);
        setDesign();
        setAutomataChosen(false);
        //ResourceBundle.getBundle("pdasimulator/localization", Locale.getDefault()).getString("menu.file"));

        txtDelay.setText(String.valueOf(DefaultValues.getInstance().getDelay()));


        //PushdownAutomaton newAutomata = /loadAutomata("/home/hanis/diplomka/podobne_projekty-netbeans/mojebakalarka/PDASimulator/Automata/bezanbncn.xml");
        if(inputAutomaton != null) {
            inputAutomaton.setStatus(PushdownAutomaton.SAVED);
            applyAutomata(inputAutomaton);
        }


        //jMenuItem1.setVisible(false);

    }


    public void setDesign() {
        DesignManipulation.getInstance().setDesign(this, true);
        panel.setBackground(DesignManipulation.getInstance().getStackPanelBack());
        panelButtonsOut.setBackground(DesignManipulation.getInstance().getTransPanelBack());
        if(stackPanel != null) {
            stackPanel.refreshMe();
        }
        repaitTransButtons(checkWhisperer.isSelected());
        tableMatrix.getTableHeader().setBackground(DesignManipulation.getInstance().getMatrixPanel());
        scrollMatrix.getViewport().setBackground(DesignManipulation.getInstance().getMatrixPanel());
        //tableMatrix.setGridColor(Color.BLACK);
        tableMatrix.setShowGrid(true);
        tableMatrix.setGridColor(Color.BLACK);

        
        labCompltyWord.setForeground(DesignManipulation.getInstance().getWordPre());
        labRestWord.setForeground(DesignManipulation.getInstance().getWordPost());
        labCurrentChar.setForeground(DesignManipulation.getInstance().getWordCurrent());
        panelWord.setBackground(DesignManipulation.getInstance().getWordPanel());
        labCurrentState.setForeground(DesignManipulation.getInstance().getStackStateFront());
        labCurrentState.setBackground(DesignManipulation.getInstance().getStackPanelBack());

    //    progressDialog.setDesign();
        //this.validateTree();
    }


    private boolean showSaveWarning() {
     //   System.out.println(isAutomataChosen() + "," + automata.getStatus());
        if(isAutomataChosen() && automaton.getStatus() != automaton.SAVED) {
            Object[] options = {L12N.getValue("warn.but.save"), L12N.getValue("warn.but.discar"), L12N.getValue("warn.but.storno")};
            String text = "";
            if(automaton.getStatus() == automaton.NEW) {
                text = L12N.getValue("warn.automataNotSaved");
            } else if(automaton.getStatus() == automaton.MODIFY) {
                text = L12N.getValue("warn.automataChanged");
            }
            int result = JOptionPane.showOptionDialog(this,
                    text, L12N.getValue("warn.warning"),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if(result == JOptionPane.YES_OPTION) {
                if(automaton.getStatus() == PushdownAutomaton.NEW) {
                    saveAsAutomata();
                } else if(automaton.getStatus() == PushdownAutomaton.MODIFY) {
                    saveAutomata();
                }
                return true;
            }
            if(result == JOptionPane.NO_OPTION) {
                return true;
            }
            if(result == JOptionPane.CANCEL_OPTION) {
                return false;
            }
            return false;
        }
        return true;
    }


    private void repaitTransButtons(boolean whisperer) {
        for (int i = 0; i < panelButtons.getComponentCount(); i++) {
            ((TransitionButton) panelButtons.getComponent(i)).paintMe(whisperer);
        }
    }

//    public void setDesign() {
//        DesignManipulation.getInstance().setDesign(this);
//        panel.setBackground(DesignManipulation.getInstance().getStackPanelBack());
//        panelButtonsOut.setBackground(DesignManipulation.getInstance().getTransPanelBack());
//        if(stackPanel != null) {
//            stackPanel.refreshMe();
//        }
//        repaitTransButtons(checkWhisperer.isSelected());
//        tableMatrix.getTableHeader().setBackground(DesignManipulation.getInstance().getMatrixPanel());
//        scrollMatrix.getViewport().setBackground(DesignManipulation.getInstance().getMatrixPanel());
//
//
//        labCompltyWord.setForeground(DesignManipulation.getInstance().getWordPre());
//        labRestWord.setForeground(DesignManipulation.getInstance().getWordPost());
//        labCurrentChar.setForeground(DesignManipulation.getInstance().getWordCurrent());
//        panelWord.setBackground(DesignManipulation.getInstance().getWordPanel());
//        labCurrentState.setForeground(DesignManipulation.getInstance().getStackStateFront());
//        labCurrentState.setBackground(DesignManipulation.getInstance().getStackPanelBack());
//
//        progressDialog.setDesign();
//        //this.validateTree();
//    }

    private int getRandom(int number) {
        if(random == null) {
            random = new Random();
        }
        return random.nextInt(number);
    }

    private void initMyComponents() {
        // panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.Y_AXIS));
        //panelButtons.setLayout(new GridLayout(0,1));
        panelWord.setLayout(new BoxLayout(panelWord, BoxLayout.X_AXIS));
        labCompltyWord = new JLabel();
        labCompltyWord.setFont(new Font("Dialog",Font.BOLD,18));
        labCompltyWord.setForeground(DesignManipulation.getInstance().getWordPre());
        labRestWord = new JLabel();
        labRestWord.setFont(new Font("Dialog",Font.BOLD,18));
        labRestWord.setForeground(DesignManipulation.getInstance().getWordPost());
        labCurrentChar = new JLabel();
        labCurrentChar.setFont(new Font("Dialog",Font.BOLD,18));
        labCurrentChar.setForeground(DesignManipulation.getInstance().getWordCurrent());

        panelWord.add(labCompltyWord);
        panelWord.add(labCurrentChar);
        panelWord.add(labRestWord);
        panel.setLayout(new BorderLayout());

    }


    private void saveAsAutomata() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(XMLDataManipulation.AUTOMATA_PATH));
        int returnVal = fileChooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                XMLDataManipulation.getInstance().saveAutomaton(automaton, new File(path));
                automaton.setStatus(PushdownAutomaton.SAVED);
                automaton.setPath(path);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, L12N.getValue("warn.io.error"), L12N.getValue("warn.error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveAutomata() {
        try {
            XMLDataManipulation.getInstance().saveAutomaton(automaton, new File(automaton.getPath()));
            automaton.setStatus(PushdownAutomaton.SAVED);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, L12N.getValue("warn.io.error"), L12N.getValue("warn.error"), JOptionPane.ERROR_MESSAGE);
        }
    }




//    private void setInputAs(int input) {
//        if(input == WORD) {
//            comboInitState.setEditable(false);
//            comboInitState.setSelectedItem(automata.getStartState());
//            txtStack.setEditable(false);
//            txtStack.setText(automata.getInitialStackSymbol());
//        } else {
//            comboInitState.setEditable(true);
//            comboInitState.setSelectedItem(comboInitState.getItemAt(0));
//            txtStack.setEditable(true);
//            txtStack.setText("");
//        }
//    }
//
//    private void initBottom() {
//        radioInputWord.setSelected(true);
//        txtWord.setText("");
//        comboInitState.setModel(new DefaultComboBoxModel(automata.getStates().toArray()));
//        startStopEnable(true);
//        setInputAs(WORD);
//    }


    private void enabledTransButt(boolean enable) {
        for (Component butt : panelButtons.getComponents()) {
            butt.setEnabled(enable);
        }
    }


//    private void enableTypePanel(boolean enable) {
////        for (Component comp : panelType.getComponents()) {
////            comp.setEnabled(enable);
////        }
////        radioTypeAccepting.setEnabled(enable);
////        radioTypeDeterministic.setEnabled(enable);
////        radioTypeManual.setEnabled(enable);
////        radioTypeRandom.setEnabled(enable);
//        txtDelay.setEditable(enable);
//    }


    private void nullInput() {
        this.txtInputAccepting.setText("-");
        this.txtInputStack.setText("-");
        this.txtInputState.setText("-");
        this.txtInputWord.setText("-");
    }

    private void setInputText(String state, String word, String stack, int acceptType) {
        if(acceptType == PushdownAutomaton.EMPTY) {
            this.txtInputAccepting.setText(L12N.getValue("freq.EmptyStackADV"));
        } else {
            this.txtInputAccepting.setText(L12N.getValue("freq.FinalStateADV"));
        }
        this.txtInputStack.setText(stack);
        this.txtInputState.setText(state);
        this.txtInputWord.setText(word);
    }


    private void nullComponents() {
        panelButtons.removeAll();
        panel.removeAll();
        panelButtons.repaint();
        matrixRender.getPlain();
        tableMatrix.repaint();
        this.labCompltyWord.setText("");
        this.labCurrentChar.setText("");
        this.labCurrentState.setText("");
        this.labRestWord.setText("");
        this.nullInput();
        butSetAgain.setEnabled(false);
        enableSimPanel(false);
        butPause.setEnabled(false);
    }

    private void loadMatrix() {
        if(automaton == null) {
            throw new NullPointerException();
        }
        matrixData = new MatrixData(automaton);
        tableMatrix.setModel(new MatrixTableModel(matrixData));
        matrixRender = new MatrixTableRender(matrixData);
        tableMatrix.setDefaultRenderer(Object.class, matrixRender);
        tableMatrix.getTableHeader().setDefaultRenderer(matrixRender);
        tableMatrix.setRowHeight(25);
        tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableMatrix.getColumn(tableMatrix.getColumnName(0)).setPreferredWidth(100);
        for (int i = 1; i < tableMatrix.getColumnCount(); i++) {
            tableMatrix.getColumn(tableMatrix.getColumnName(i)).setPreferredWidth(150);

        }
        //tableMatrix.getColumn(tableMatrix.getColumnName(1)).setPreferredWidth(20);
        //JLabel headerRenderer = new DefaultTableCellRenderer();
        //String columnName = tableMatrix.getModel().getColumnName(0);
        //headerRenderer.setText(columnName);
        //headerRenderer.set.setBackground(Color.GREEN);

        //TableColumnModel columnModel = tableMatrix.getColumnModel();
        //TableColumn column = columnModel.getColumn(0);
        //-0column.setHeaderRenderer((TableCellRenderer) headerRenderer);



       // stackListModel = new StackListModel();
       // listStack.setModel(stackListModel);
    }

    private void setLabelState() {
        this.labCurrentState.setText(automaton.getConfiguration().getCurrentState());
    }

//    private int getInput() {
//        if(radioInputWord.isSelected()) {
//            return Automata.WORD;
//        }
//        return Automata.CONF;
//    }


//    private void startSimulation(String word) throws WrongValuesException {
//        int delay = -1;
//        if(getSimulationType() != MANUAL) {
//            delay = getDelay();
//        }
//        automata.useWord(word, getAcceptType());
//        panel.removeAll();
//        stackPanel = new StackPanel(automata.getInitialStackSymbol());
//        panel.add(stackPanel, BorderLayout.SOUTH);
//        refreshComponents();
//        simulationType = MANUAL;
//       // if(getSimulationType() != MANUAL) {
//       //     simulateTransition(delay);
//       // }
//    }

    public void startSimulation(String state, String word, String stack, int acceptType) throws WrongValuesException {
        automaton.useConfiguration(state, word, stack, acceptType);
        setInputText(state, word, stack, acceptType);
        panel.removeAll();
        panel.validate();
        stackPanel = new StackPanel(automaton.getConfiguration());
        panel.add(stackPanel, BorderLayout.SOUTH);
        refreshComponents();
        simulationType = MANUAL;
        enableSimPanel(true);
        butPause.setEnabled(false);
        butSetInput.setEnabled(true);
        progressDialog.refreshMe();
        if(automaton.isUsed()) {
            butSetAgain.setEnabled(true);
        }
    }

    private void writeWord() {
        Dimension dim = jScrollPane4.getPreferredSize();
        labCompltyWord.setText(automaton.getConfiguration().getReadWord());
        labRestWord.setText(automaton.getConfiguration().getRestMinusCurent());
        labCurrentChar.setText(String.valueOf(automaton.getConfiguration().getCurrentChar()));

        panelWord.repaint();
        jScrollPane4.repaint();
        jScrollPane4.revalidate();
        //jScrollPane4.setPreferredSize(dim);
        //jScrollPane4.setMaximumSize(dim);

    }

    private void stopTimer() {
        if(timer != null) {
            //if(timer.isRunning()) {
                timer.stop();
            //}
            simulationType = MANUAL;
            enableSimPanel(true);
            butPause.setEnabled(false);
            enabledTransButt(true);
            butSetInput.setEnabled(true);
            butSetAgain.setEnabled(true);
            butUndo.setEnabled(true);
            butUndo1.setEnabled(true);
        }
    }

    private void showTransitions() {
        panelButtons.removeAll();
        panelButtons.repaint();
        Set<Transition> transitions = automaton.getMatchTransitions();
        if(!transitions.isEmpty()) {
            matrixRender.renderMatch(transitions);
           // ((MatrixHeaderRenderer) tableMatrix.getTableHeader().getDefaultRenderer()).renderMatch(transitions);
        }

        int transCount = 0;
        for (Transition transition : transitions) {
            for (Pair pair : transition.getPairs()) {
                transCount++;
                SimpleTransition simpleTransition = new SimpleTransition(transition.getTernary(), pair);
                TransitionButton button = new TransitionButton(simpleTransition, automaton.isPossible(simpleTransition), checkWhisperer.isSelected());
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        doTransition(((TransitionButton) e.getSource()).getTransition());
                    }
                });
                panelButtons.add(button);
            }
        }
        if(timer != null && timer.isRunning()) {
            enabledTransButt(false);
        } else {
            enabledTransButt(true);
        }
        tableMatrix.repaint();
        tableMatrix.getTableHeader().repaint(); ///////////!!!!!!!!!!!!!!!!!!!

        this.validateTree();
        //this.pack();

        if(transitions.isEmpty()) {
            stopTimer();
            simulationType = MANUAL;
            if(!automaton.isAccepting()) {
                new DialogAnswer(frame, false, automaton);
            } else {
                new DialogAnswer(frame, true, automaton);
            }
        } else if(automaton.isAccepting()) {
            simulationType = MANUAL;
            stopTimer();
            new DialogAnswer(frame, true, automaton);
        }

//        if(transCount == 1 && getSimulationType() == DETERMINISTIC) {
//            try {
//                simulateTransition(getDelay(), getSimulationType());
//            } catch (WrongValuesException ex) {
//                System.out.println("!ERR - spatny delay pri deterministic");
//            }
//        }

    }


    private void exportToTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(XMLDataManipulation.EXPORT_PATH));
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                File filePath = new File(path);
                BufferedWriter buffWriter = new BufferedWriter(new FileWriter(filePath));
                buffWriter.write(automaton.writeAutomataDetail());
                buffWriter.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, L12N.getValue("warn.io.error"), L12N.getValue("warn.error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void exportToHtml() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(XMLDataManipulation.EXPORT_PATH));
        int returnVal = fileChooser.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                XMLDataManipulation.getInstance().createHTML(automaton, path);
            } catch (AutomataException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void simulateTransition(int delay, int type) {
        if(timer == null || !timer.isRunning()) {
            simulationType = type;
            timer = new javax.swing.Timer(delay, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    simulation(getSimulationType());
                }
            });
            timer.start();
        }
    }


    private void enableSimPanel(boolean enable) {
        butSimAccepting.setEnabled(enable);
        butSimDeterministic.setEnabled(enable);
        butSimRandom.setEnabled(enable);
        txtDelay.setEditable(enable);
    }

    private void simulation(int type) {
        butSetInput.setEnabled(false);
        butSetAgain.setEnabled(false);
        butUndo.setEnabled(false);
        butUndo1.setEnabled(false);
        enableSimPanel(false);
        butPause.setEnabled(true);
        int transitionCount = panelButtons.getComponentCount();
        if(transitionCount != 0) {
            if(automaton.isAccepting()) {
                simulationType = MANUAL;
                stopTimer();
                new DialogAnswer(frame, true, automaton);
            } else {
                switch(type) {
                    case RANDOM : {
                        doTransition(((TransitionButton) panelButtons.getComponent(getRandom(transitionCount))).getTransition());
                    } break;
                    case DETERMINISTIC : {
                        if(transitionCount == 1) {
                            doTransition(((TransitionButton) panelButtons.getComponent(0)).getTransition());
                        } else {
                            stopTimer();
                        }
                    } break;
                    case ACCEPTING : {
                        SimpleTransition trans = null;
                        List<SimpleTransition> transOK = new ArrayList<SimpleTransition>();
                        for (int i = 0; i < transitionCount; i++) {
                            if(((TransitionButton) panelButtons.getComponent(i)).isPossible()) {
                                transOK.add(((TransitionButton) panelButtons.getComponent(i)).getTransition());
                            }
                        }
                        if(transOK.isEmpty()) {
                            stopTimer();
                            new DialogAnswer(frame, false, automaton);
                        } else {
                            doTransition(transOK.get(getRandom(transOK.size())));
                        }
                    }
                }
            }
        } else {
            stopTimer();
            if(!automaton.isAccepting()) {
                new DialogAnswer(frame, false, automaton);
            } else {
                new DialogAnswer(frame, true, automaton);
            }
        }
    }

    private void refreshComponents() {
        //stackPanel.refreshMe();
        stackPanel.repaint();//revalidate();
        panel.repaint();
        writeWord();
        setLabelState();
        progressDialog.refreshMe();
        showTransitions();
    }

    private void doTransition(SimpleTransition transition) {
        automaton.doTransition(transition);
        stackPanel.doTransition(transition);
        refreshComponents();
    }

    private void doUndo() {
        if(automaton.doUndo()) {
            stackPanel.doUndo(automaton.getLastUsedRedoUndo());
            refreshComponents();
            //terminal.addText("Used transition: " + transition);
            progressDialog.refreshMe();
        } else {
            JOptionPane.showMessageDialog(this, L12N.getValue("warn.undo.noStep"), L12N.getValue("warn.info"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void doRedo() {
        if(automaton.doRedo()) {
            stackPanel.doTransition(automaton.getLastUsedRedoUndo());
            refreshComponents();
            //terminal.addText("Used transition: " + transition);
            progressDialog.refreshMe();
        } else {
            JOptionPane.showMessageDialog(this, L12N.getValue("warn.redo.noStep"), L12N.getValue("warn.info"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private PushdownAutomaton loadAutomata() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(XMLDataManipulation.AUTOMATA_PATH));
        int returnVal = fileChooser.showOpenDialog(this);
        PushdownAutomaton retAutomata = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                retAutomata = XMLDataManipulation.getInstance().getAutomata(new File(path));
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(this, L12N.getValue("warn.format.file.automata"), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
            }
        }
        return retAutomata;
    }

    private PushdownAutomaton loadAutomata(String path) {
        PushdownAutomaton retAutomata = null;
        try {
            retAutomata = XMLDataManipulation.getInstance().getAutomata(new File(path));
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(this, L12N.getValue("warn.format.file.automata"), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
        return retAutomata;
    }


    public boolean isAutomataChosen() {
        return AutomataChosen;
    }

    public void setAutomataChosen(boolean automataChosen) {
        if(isAutomataChosen() != automataChosen || !automataChosen) {
         //   itemSave.setEnabled(automataChosen);
         //   itemSaveAs.setEnabled(automataChosen);
         //   itemEdit.setEnabled(automataChosen);
         //   itemExportTxt.setEnabled(automataChosen);
         //   itemExportHtlm.setEnabled(automataChosen);
            panel.setVisible(automataChosen);
            jScrollPane3.setVisible(automataChosen);
            jScrollPane4.setVisible(automataChosen);
            scrollMatrix.setVisible(automataChosen);
            scrollStack.setVisible(automataChosen);
            panelBottom.setVisible(automataChosen);
            labCurrentState.setVisible(automataChosen);
            //panelButtons.setVisible(automataChosen);
            panelWord.setVisible(automataChosen);
            butUndo.setVisible(automataChosen);
            butUndo1.setVisible(automataChosen);
        }
        this.AutomataChosen = automataChosen;
    }

    private int getDelay() throws WrongValuesException {
        String delayString = txtDelay.getText();
        int delay = -1;
        try {
            delay = Integer.parseInt(delayString);
        } catch (NumberFormatException ex) {
            throw new WrongValuesException(L12N.getValue("warn.format.delay"));
        }
        if(delay < 50 || delay > 50000) {
            throw new WrongValuesException(L12N.getValue("warn.format.delay"));
        }
        return delay;
    }

    private int getSimulationType() {
        return simulationType;
    }

    private void applyAutomata(PushdownAutomaton appAutomata) {
        this.automaton = appAutomata;
        progressDialog.setAutomata(appAutomata);
        setAutomataChosen(true);
        loadMatrix();
        nullInput();
        nullComponents();
    }

    public void hideProgressDialog() {
        progressDialog.setVisible(false);
    }




    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollMatrix = new javax.swing.JScrollPane();
        tableMatrix = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelWord = new javax.swing.JPanel();
        butUndo1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelButtonsOut = new javax.swing.JPanel();
        butUndo = new javax.swing.JButton();
        scrollStack = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        panelBottom = new javax.swing.JPanel();
        panelProcess = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtInputState = new javax.swing.JLabel();
        txtInputWord = new javax.swing.JLabel();
        txtInputStack = new javax.swing.JLabel();
        panelType = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtDelay = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        butSimRandom = new javax.swing.JButton();
        butSimDeterministic = new javax.swing.JButton();
        butSimAccepting = new javax.swing.JButton();
        butPause = new javax.swing.JButton();
        butStart2 = new javax.swing.JButton();
        checkWhisperer = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        butProgress = new javax.swing.JButton();
        txtInputAccepting = new javax.swing.JLabel();
        butSetInput = new javax.swing.JButton();
        butSetAgain = new javax.swing.JButton();
        labCurrentState = new javax.swing.JLabel();

        scrollMatrix.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tableMatrix.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableMatrix.setFocusable(false);
        tableMatrix.setRequestFocusEnabled(false);
        tableMatrix.setRowSelectionAllowed(false);
        scrollMatrix.setViewportView(tableMatrix);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelWordLayout = new javax.swing.GroupLayout(panelWord);
        panelWord.setLayout(panelWordLayout);
        panelWordLayout.setHorizontalGroup(
            panelWordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1227, Short.MAX_VALUE)
        );
        panelWordLayout.setVerticalGroup(
            panelWordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(panelWord);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pdasimulator/localization"); // NOI18N
        butUndo1.setText(bundle.getString("main.but.redo")); // NOI18N
        butUndo1.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butUndo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUndo1ActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelButtonsOutLayout = new javax.swing.GroupLayout(panelButtonsOut);
        panelButtonsOut.setLayout(panelButtonsOutLayout);
        panelButtonsOutLayout.setHorizontalGroup(
            panelButtonsOutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 202, Short.MAX_VALUE)
        );
        panelButtonsOutLayout.setVerticalGroup(
            panelButtonsOutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 639, Short.MAX_VALUE)
        );

        jScrollPane3.setViewportView(panelButtonsOut);

        butUndo.setText(bundle.getString("main.but.undo")); // NOI18N
        butUndo.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUndoActionPerformed(evt);
            }
        });

        scrollStack.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );

        scrollStack.setViewportView(panel);

        panelBottom.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panelProcess.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED), bundle.getString("main.panelTitle.inputConfiguration"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel1.setText(bundle.getString("main.lab.inputWord")); // NOI18N

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel7.setText(bundle.getString("main.lab.state")); // NOI18N

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel8.setText(bundle.getString("main.lab.stack")); // NOI18N

        txtInputState.setText(".");
        txtInputState.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtInputWord.setText(".");
        txtInputWord.setAutoscrolls(true);
        txtInputWord.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        txtInputStack.setText(".");
        txtInputStack.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout panelProcessLayout = new javax.swing.GroupLayout(panelProcess);
        panelProcess.setLayout(panelProcessLayout);
        panelProcessLayout.setHorizontalGroup(
            panelProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProcessLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtInputStack, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInputWord, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtInputState, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
        panelProcessLayout.setVerticalGroup(
            panelProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProcessLayout.createSequentialGroup()
                .addGroup(panelProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtInputState))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtInputWord))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtInputStack))
                .addContainerGap())
        );

        panelType.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), bundle.getString("main.panelTitle.automaticSimulation"))); // NOI18N

        jLabel3.setText(bundle.getString("main.lab.delay")); // NOI18N

        jLabel4.setText(bundle.getString("main.lab.ms")); // NOI18N

        butSimRandom.setText(bundle.getString("main.but.random")); // NOI18N
        butSimRandom.setActionCommand(bundle.getString("main.but.random")); // NOI18N
        butSimRandom.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butSimRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSimRandomActionPerformed(evt);
            }
        });

        butSimDeterministic.setText(bundle.getString("main.but.deterministic")); // NOI18N
        butSimDeterministic.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butSimDeterministic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSimDeterministicActionPerformed(evt);
            }
        });

        butSimAccepting.setText(bundle.getString("main.but.accepting")); // NOI18N
        butSimAccepting.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butSimAccepting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSimAcceptingActionPerformed(evt);
            }
        });

        butPause.setText(bundle.getString("main.but.pause")); // NOI18N
        butPause.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPauseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTypeLayout = new javax.swing.GroupLayout(panelType);
        panelType.setLayout(panelTypeLayout);
        panelTypeLayout.setHorizontalGroup(
            panelTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(butSimAccepting, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(butSimDeterministic, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(butSimRandom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addGroup(panelTypeLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDelay, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addComponent(butPause, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelTypeLayout.setVerticalGroup(
            panelTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTypeLayout.createSequentialGroup()
                .addComponent(butSimRandom)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butSimDeterministic)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butSimAccepting)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDelay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butPause)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        butStart2.setText(bundle.getString("main.but.info")); // NOI18N
        butStart2.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butStart2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStart2ActionPerformed(evt);
            }
        });

        checkWhisperer.setSelected(true);
        checkWhisperer.setText(bundle.getString("main.check.whisperer")); // NOI18N
        checkWhisperer.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        checkWhisperer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkWhispererActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel11.setText(bundle.getString("main.lab.accepting")); // NOI18N

        butProgress.setText(bundle.getString("main.but.progress")); // NOI18N
        butProgress.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butProgress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProgressActionPerformed(evt);
            }
        });

        txtInputAccepting.setText(".");

        butSetInput.setText(bundle.getString("main.but.setStart")); // NOI18N
        butSetInput.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butSetInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSetInputActionPerformed(evt);
            }
        });

        butSetAgain.setText(bundle.getString("main.but.againStart")); // NOI18N
        butSetAgain.setMargin(new java.awt.Insets(1, 4, 1, 4));
        butSetAgain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSetAgainActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBottomLayout = new javax.swing.GroupLayout(panelBottom);
        panelBottom.setLayout(panelBottomLayout);
        panelBottomLayout.setHorizontalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBottomLayout.createSequentialGroup()
                        .addComponent(butSetInput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butSetAgain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(checkWhisperer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butProgress)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butStart2))
                    .addComponent(panelProcess, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGroup(panelBottomLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtInputAccepting, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBottomLayout.setVerticalGroup(
            panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBottomLayout.createSequentialGroup()
                .addGroup(panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelBottomLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtInputAccepting))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addGroup(panelBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(butSetInput)
                            .addComponent(butSetAgain)
                            .addComponent(butStart2)
                            .addComponent(butProgress)
                            .addComponent(checkWhisperer, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))))
                .addContainerGap())
        );

        labCurrentState.setFont(new java.awt.Font("Dialog", 1, 36));
        labCurrentState.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labCurrentState.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        labCurrentState.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labCurrentState.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(butUndo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(butUndo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE))
                            .addComponent(panelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(scrollMatrix, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labCurrentState, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scrollStack, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labCurrentState, scrollStack});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollStack, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labCurrentState, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollMatrix, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(butUndo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(butUndo1))
                            .addComponent(jScrollPane4, 0, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, 0, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void butUndo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUndo1ActionPerformed
        doRedo();
}//GEN-LAST:event_butUndo1ActionPerformed

    private void butUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUndoActionPerformed
        doUndo();
}//GEN-LAST:event_butUndoActionPerformed

    private void butSimRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSimRandomActionPerformed
        try {
            simulateTransition(getDelay(), RANDOM);
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_butSimRandomActionPerformed

    private void butSimDeterministicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSimDeterministicActionPerformed
        try {
            simulateTransition(getDelay(), DETERMINISTIC);
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_butSimDeterministicActionPerformed

    private void butSimAcceptingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSimAcceptingActionPerformed
        try {
            simulateTransition(getDelay(), ACCEPTING);
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
}//GEN-LAST:event_butSimAcceptingActionPerformed

    private void butPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPauseActionPerformed
        stopTimer();
}//GEN-LAST:event_butPauseActionPerformed

    private void butStart2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStart2ActionPerformed
        new DialogInfo(frame, automaton);
}//GEN-LAST:event_butStart2ActionPerformed

    private void checkWhispererActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkWhispererActionPerformed
        repaitTransButtons(checkWhisperer.isSelected());
}//GEN-LAST:event_checkWhispererActionPerformed

    private void butProgressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProgressActionPerformed
        progressDialog.setVisible(true);
}//GEN-LAST:event_butProgressActionPerformed

    private void butSetInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSetInputActionPerformed
        new InputDialog(frame, this, this.automaton);
}//GEN-LAST:event_butSetInputActionPerformed

    private void butSetAgainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSetAgainActionPerformed
        if(automaton.isUsed()) {
            try {
                startSimulation(automaton.getInitState(), automaton.getConfiguration().getFullWord(), automaton.getInitStack().writeStack(), automaton.getAcceptBy());
            } catch (WrongValuesException ex) {
                System.out.println("errrrrrr");
            }
        }
}//GEN-LAST:event_butSetAgainActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butPause;
    private javax.swing.JButton butProgress;
    private javax.swing.JButton butSetAgain;
    private javax.swing.JButton butSetInput;
    private javax.swing.JButton butSimAccepting;
    private javax.swing.JButton butSimDeterministic;
    private javax.swing.JButton butSimRandom;
    private javax.swing.JButton butStart2;
    private javax.swing.JButton butUndo;
    private javax.swing.JButton butUndo1;
    private javax.swing.JCheckBox checkWhisperer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labCurrentState;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelBottom;
    private javax.swing.JPanel panelButtonsOut;
    private javax.swing.JPanel panelProcess;
    private javax.swing.JPanel panelType;
    private javax.swing.JPanel panelWord;
    private javax.swing.JScrollPane scrollMatrix;
    private javax.swing.JScrollPane scrollStack;
    private javax.swing.JTable tableMatrix;
    private javax.swing.JTextField txtDelay;
    private javax.swing.JLabel txtInputAccepting;
    private javax.swing.JLabel txtInputStack;
    private javax.swing.JLabel txtInputState;
    private javax.swing.JLabel txtInputWord;
    // End of variables declaration//GEN-END:variables

}
