/*
 * DialogModifyAutomata.java
 *
 * Created on 10. říjen 2007, 16:39
 */

package jgaf.automaton.pda;

import jgaf.automaton.pda.tools.AlgorithmsLibrary;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author  hanis
 */
public class DialogModifyAutomata extends javax.swing.JDialog {    
    
    
    private PushdownAutomatonEditor editor;
    private PushdownAutomaton automaton;
    private Transition currentTransition;
    private boolean TransitionCreated;

    private final static int MODE_NEW = 0;
    private final static int MODE_MODIFY = 1;
    private int mode;
    
   // private PDASimulator pdaSimulator;
    
    public DialogModifyAutomata(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        //pdaSimulator = (PDASimulator) parent;
        initComponents();
        setLocationRelativeTo(parent);
        setMode(MODE_NEW);
        this.setTitle(L12N.getValue("dialog.automataModify.title.newAutomata"));
        this.automaton = new PushdownAutomaton();
        String name = DefaultValues.getInstance().getName();
        String desc = DefaultValues.getInstance().getDescription();
        this.automaton.setDescription(desc);
        this.automaton.setName(name);
        initForm();
        jLabel6.setText(") " + '\u042D' + " (");
        txtDescription.setText(desc);
        txtName.setText(name);        
        DesignManipulation.getInstance().setDesign(this.getContentPane());        
    }

    public DialogModifyAutomata(JFrame parent, boolean modal, PushdownAutomatonEditor editor) {
        super(parent, true);
        initComponents();    
        setLocationRelativeTo(parent);
        this.editor = editor;
        this.automaton = (PushdownAutomaton) editor.getAutomaton().clone();
        initForm();
        setMode(MODE_MODIFY);    
        loadSimpleValues();        
        jLabel6.setText(") " + '\u042D' + " (");
        this.setTitle(L12N.getValue("dialog.automataModify.title.modifyAutomata"));
    }            

    
    
    public void refreshDetail() {
        areaAutomataDetail.setText(automaton.writeAutomataDetail());
    }
    
    public void initForm() {
        String text = L12N.getValue("dialog.automataModify.button.generate") + 
                " " + DefaultValues.getInstance().getInputAlphabet() +
                ", " + DefaultValues.getInstance().getStatesSet() +
                ", " + DefaultValues.getInstance().getStackAlphabet() +
                " " + L12N.getValue("dialog.automataModify.button.generateFrom");
                
        butGenSymbols.setText(text);
        setTransitionCreated(false);
        nullFields();
        refreshDetail();
    }
    
    private void nullFields() {
        this.txtDescription.setText("");
        this.txtFinishState.setText("");
        this.txtInitialStack.setText("");
        this.txtInput.setText("");
        this.txtName.setText("");
        this.txtStack.setText("");
        this.txtStartState.setText("");
        this.txtState.setText("");
        nullTransitionField();
    }
    
    private void nullTransitionField() {
        txtTStateFrom.setText("");
        txtTInput.setText("");
        txtTStackFrom.setText("");  
        nullPairField();
    }
    
    private void nullPairField() {
        txtTStateTo.setText("");
        txtTStackTo.setText("");         
    }
    
    private void setSimpleValues() throws WrongValuesException {
        String name = txtName.getText();
        String description = txtDescription.getText();
        String initialStack = txtInitialStack.getText();
        String startState = txtStartState.getText();
        if(name == null || name.equals("")) {
            throw new WrongValuesException(L12N.getValue("warn.modify.miss.name"));
        }
        if(initialStack == null || initialStack.equals("warn.modify.miss.initState")) {
            throw new WrongValuesException(L12N.getValue(""));
        }
        if(startState == null ||startState.equals("warn.modify.miss.initStack")) {
            throw new WrongValuesException(L12N.getValue(""));
        }                         
        automaton.setName(name);
        automaton.setDescription(description);
        automaton.setStartState(startState);
        automaton.setInitialStackSymbol(initialStack);
    }
    
    private void loadSimpleValues() {
        txtName.setText(automaton.getName());
        txtDescription.setText(automaton.getDescription());
        txtInitialStack.setText(automaton.getInitialStackSymbol());
        txtStartState.setText(automaton.getStartState());
    }
    
    private Ternary getTernary() throws WrongValuesException {
        //take values from text fields
        String stateFrom = txtTStateFrom.getText();
        String symbol = txtTInput.getText();
        String stackFrom = txtTStackFrom.getText();        
        //values checking
        if(stateFrom == null || stateFrom.equals("")) {
            throw new WrongValuesException(L12N.getValue("warn.modify.miss.state.left"));
        }
        if(stackFrom == null || stackFrom.equals("")) {
            throw new WrongValuesException(L12N.getValue("warn.modify.miss.stack.left"));
        }        
        if(symbol.length() > 1) {
            throw new WrongValuesException(L12N.getValue("warn.modify.length.inputSymbol"));
        }
        Ternary ternary;
        if(symbol.equals("")) {
            ternary = new Ternary(stateFrom, stackFrom);
        } else {
            ternary = new Ternary(stateFrom, symbol.charAt(0), stackFrom);
        }        
        return ternary;
    }
    
    private void addTransition() throws WrongValuesException {
        Ternary ternary = getTernary();
        Transition transition = automaton.getTransitionFunction().getTransition(ternary);
        if(transition == null) {            
            transition = new Transition();
            transition.setTernary(ternary);
        }
        currentTransition = transition;
        addPair();
        automaton.getTransitionFunction().addTransition(transition);
    }
    
    private Pair getPair() throws WrongValuesException {
        String stateTo = txtTStateTo.getText();
        String stackTo = txtTStackTo.getText();
        //values checking
        if(stateTo == null || stateTo.equals("")) {
            throw new WrongValuesException(L12N.getValue("warn.modify.miss.state.right"));
        }
        Pair pair;
        if(stackTo == null || stackTo.equals("")) {
            pair = new Pair(stateTo);
        } else {
            List<String> listStack = AlgorithmsLibrary.createStack(stackTo);            
            pair = new Pair(stateTo, listStack);            
        }
        return pair;
    }
    
    private void addPair() throws WrongValuesException {
        currentTransition.addPair(getPair());
    }
    
    private void addState() {    
        automaton.addStates(txtState.getText());
        txtState.setText("");
        refreshDetail();
    }
    
     private void addFinalState() {    
        automaton.addFinalStates(txtFinishState.getText());
        txtFinishState.setText("");
        refreshDetail();  
    }
     
     private void addStackSymbol() {    
        automaton.addStackAlphabet(txtStack.getText());
        txtStack.setText("");
        refreshDetail(); 
    }
     
    private void addInputSymbol() {    
        automaton.addInputAlphabet(txtInput.getText().charAt(0));
        txtInput.setText("");
        refreshDetail(); 
    }     
    
    private void setSartSymbol() {
        automaton.setStartState(txtStartState.getText());
    }
    
    private void setInitialStack() {
        automaton.setInitialStackSymbol(txtInitialStack.getText());
    }
    
    private void enabledTernaryField(boolean enable) {
        txtTStateFrom.setEnabled(enable);
        txtTInput.setEnabled(enable);
        txtTStackFrom.setEnabled(enable);
    }
    
    public void setTxtInitialStack(String symbol) {
        txtInitialStack.setText(symbol);
    }
    
    public void setTxtStartState(String symbol) {
        txtStartState.setText(symbol);
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaAutomataDetail = new javax.swing.JTextArea();
        butRefreshDetail = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtState = new javax.swing.JTextField();
        butState = new javax.swing.JButton();
        txtInput = new javax.swing.JTextField();
        butInput = new javax.swing.JButton();
        txtStack = new javax.swing.JTextField();
        butStack = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtInitialStack = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtStartState = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtFinishState = new javax.swing.JTextField();
        butFinish = new javax.swing.JButton();
        panelModifyState = new javax.swing.JPanel();
        butModifyAlphabet = new javax.swing.JButton();
        butModifyStack = new javax.swing.JButton();
        butModifyState = new javax.swing.JButton();
        butModifyFinal = new javax.swing.JButton();
        butGenSymbols = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtTStateFrom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTInput = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTStackFrom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTStateTo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTStackTo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        butNewTrasition = new javax.swing.JButton();
        butAddTrasition = new javax.swing.JButton();
        butModifyTrans = new javax.swing.JButton();
        butBack = new javax.swing.JButton();
        butStorno = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Nov\u00fd automat");
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.panel.preview")));
        jScrollPane1.setViewport(null);
        areaAutomataDetail.setColumns(20);
        areaAutomataDetail.setEditable(false);
        areaAutomataDetail.setRows(5);
        areaAutomataDetail.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane1.setViewportView(areaAutomataDetail);

        butRefreshDetail.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.refreshPreview"));
        butRefreshDetail.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butRefreshDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRefreshDetailActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .add(butRefreshDetail))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(butRefreshDetail)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.panel.definition")));

        jLabel1.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.label.name"));

        jLabel2.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.label.description"));

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel2)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txtName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 247, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(txtName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(txtDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel11.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.label.states"));

        jLabel12.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.label.inputAlphabet"));

        jLabel13.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.label.stackAlphabet"));

        txtState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStateActionPerformed(evt);
            }
        });

        butState.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.add"));
        butState.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStateActionPerformed(evt);
            }
        });

        txtInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInputActionPerformed(evt);
            }
        });

        butInput.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.add"));
        butInput.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butInputActionPerformed(evt);
            }
        });

        txtStack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStackActionPerformed(evt);
            }
        });

        butStack.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.add"));
        butStack.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butStack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStackActionPerformed(evt);
            }
        });

        jLabel14.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.label.stackBottom"));

        jLabel9.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.label.initialState"));

        jLabel10.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.label.finishStates"));

        txtFinishState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFinishStateActionPerformed(evt);
            }
        });

        butFinish.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.add"));
        butFinish.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFinishActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout panelModifyStateLayout = new org.jdesktop.layout.GroupLayout(panelModifyState);
        panelModifyState.setLayout(panelModifyStateLayout);
        panelModifyStateLayout.setHorizontalGroup(
            panelModifyStateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 296, Short.MAX_VALUE)
        );
        panelModifyStateLayout.setVerticalGroup(
            panelModifyStateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        butModifyAlphabet.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.modify"));
        butModifyAlphabet.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModifyAlphabet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyAlphabetActionPerformed(evt);
            }
        });

        butModifyStack.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.modify"));
        butModifyStack.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModifyStack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyStackActionPerformed(evt);
            }
        });

        butModifyState.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.modify"));
        butModifyState.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModifyState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyStateActionPerformed(evt);
            }
        });

        butModifyFinal.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.modify"));
        butModifyFinal.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModifyFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyFinalActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(118, 118, 118)
                        .add(panelModifyState, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel13)
                            .add(jLabel10)
                            .add(jLabel9)
                            .add(jLabel14)
                            .add(jLabel11)
                            .add(jLabel12))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(txtFinishState)
                            .add(txtStartState)
                            .add(txtInitialStack)
                            .add(txtStack)
                            .add(txtInput)
                            .add(txtState, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(butState)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(butModifyState))
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(butInput)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(butModifyAlphabet))
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(butStack)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(butModifyStack))
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(butFinish)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(butModifyFinal)))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel11)
                    .add(butState)
                    .add(butModifyState)
                    .add(txtState, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(7, 7, 7)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel12)
                        .add(txtInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(butInput, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(butModifyAlphabet)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel13)
                    .add(butStack)
                    .add(butModifyStack)
                    .add(txtStack, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel14)
                    .add(txtInitialStack, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel9)
                    .add(txtStartState, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel10)
                    .add(txtFinishState, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(butFinish)
                    .add(butModifyFinal))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelModifyState, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        butGenSymbols.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.generate"));
        butGenSymbols.setActionCommand(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.generate"));
        butGenSymbols.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butGenSymbols.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGenSymbolsActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.panel.transitionFunction")));

        jLabel3.setText(",");

        jLabel4.setText("(");

        jLabel5.setText(",");

        jLabel6.setText(") -> (");

        jLabel7.setText(",");

        txtTStackTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTStackToActionPerformed(evt);
            }
        });

        jLabel8.setText(")");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .add(jLabel4)
                .add(4, 4, 4)
                .add(txtTStateFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(4, 4, 4)
                .add(jLabel3)
                .add(4, 4, 4)
                .add(txtTInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(4, 4, 4)
                .add(jLabel5)
                .add(4, 4, 4)
                .add(txtTStackFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 34, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(3, 3, 3)
                .add(jLabel6)
                .add(4, 4, 4)
                .add(txtTStateTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(3, 3, 3)
                .add(jLabel7)
                .add(3, 3, 3)
                .add(txtTStackTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(2, 2, 2)
                .add(jLabel8))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {txtTStackFrom, txtTStateFrom, txtTStateTo}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(txtTStateFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtTInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5)
                    .add(txtTStackFrom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(jLabel6)
                    .add(txtTStateTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel7)
                    .add(jLabel8)
                    .add(txtTStackTo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        butNewTrasition.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.newTransition"));
        butNewTrasition.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butNewTrasition.setNextFocusableComponent(txtTStateFrom);
        butNewTrasition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butNewTrasitionActionPerformed(evt);
            }
        });

        butAddTrasition.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.add"));
        butAddTrasition.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butAddTrasition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAddTrasitionActionPerformed(evt);
            }
        });

        butModifyTrans.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("dialog.automataModify.button.modify"));
        butModifyTrans.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butModifyTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butModifyTransActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(butAddTrasition)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butNewTrasition)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butModifyTrans)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(7, 7, 7)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(butAddTrasition)
                    .add(butNewTrasition)
                    .add(butModifyTrans))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(butGenSymbols)
                    .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3, 0, 376, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(butGenSymbols)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        butBack.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("freq.ok"));
        butBack.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBackActionPerformed(evt);
            }
        });

        butStorno.setText(java.util.ResourceBundle.getBundle("pdasimulator/localization").getString("freq.storno"));
        butStorno.setMargin(new java.awt.Insets(1, 5, 1, 5));
        butStorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butStornoActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(butBack)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(butStorno)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(butBack)
                    .add(butStorno))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butStornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStornoActionPerformed

        this.dispose();
    }//GEN-LAST:event_butStornoActionPerformed

    private void butModifyTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyTransActionPerformed
        new DialogModifyTransitions(this, automaton);
    }//GEN-LAST:event_butModifyTransActionPerformed

    private void butModifyFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyFinalActionPerformed
        new DialogModifyBasic(this, automaton, DialogModifyBasic.FINAL);
    }//GEN-LAST:event_butModifyFinalActionPerformed

    private void butModifyStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyStateActionPerformed
        new DialogModifyBasic(this, automaton, DialogModifyBasic.STATE);
    }//GEN-LAST:event_butModifyStateActionPerformed

    private void butModifyStackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyStackActionPerformed
        new DialogModifyBasic(this, automaton, DialogModifyBasic.STACK);
    }//GEN-LAST:event_butModifyStackActionPerformed

    private void butModifyAlphabetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butModifyAlphabetActionPerformed
        new DialogModifyBasic(this, automaton, DialogModifyBasic.ALPHABET);
    }//GEN-LAST:event_butModifyAlphabetActionPerformed
    
    private void txtTStackToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTStackToActionPerformed

    }//GEN-LAST:event_txtTStackToActionPerformed

    private void butGenSymbolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGenSymbolsActionPerformed
        automaton.generateAllFromTransition();
        refreshDetail();
    }//GEN-LAST:event_butGenSymbolsActionPerformed

    private void butRefreshDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRefreshDetailActionPerformed
        try {
            setSimpleValues();
            refreshDetail();
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_butRefreshDetailActionPerformed

    private void butBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBackActionPerformed
        try {
            setSimpleValues();
            automaton.validateAutomata();
            editor.changeAutomaton(automaton);
            this.dispose();
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);       
        } catch(AutomataException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_butBackActionPerformed

    private void txtStackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStackActionPerformed
        addStackSymbol();
    }//GEN-LAST:event_txtStackActionPerformed

    private void txtFinishStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFinishStateActionPerformed
        addFinalState();
    }//GEN-LAST:event_txtFinishStateActionPerformed

    private void txtInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInputActionPerformed
        addInputSymbol();
    }//GEN-LAST:event_txtInputActionPerformed

    private void txtStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStateActionPerformed
        addState();
    }//GEN-LAST:event_txtStateActionPerformed

    private void butAddTrasitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAddTrasitionActionPerformed
        try {
            if(!isTransitionCreated()) {                
                addTransition();
                setTransitionCreated(true);
            } else {
                addPair();
            }
            nullPairField();
            refreshDetail();
        } catch (WrongValuesException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), L12N.getValue("warn.warning"), JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_butAddTrasitionActionPerformed

    private void butNewTrasitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butNewTrasitionActionPerformed
        setTransitionCreated(false);
        nullTransitionField();
    }//GEN-LAST:event_butNewTrasitionActionPerformed

    private void butFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butFinishActionPerformed
        addFinalState();
    }//GEN-LAST:event_butFinishActionPerformed

    private void butStackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStackActionPerformed
        addStackSymbol();
    }//GEN-LAST:event_butStackActionPerformed

    private void butInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butInputActionPerformed
        addInputSymbol();
    }//GEN-LAST:event_butInputActionPerformed

    private void butStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butStateActionPerformed
        addState();
    }//GEN-LAST:event_butStateActionPerformed
     

    public boolean isTransitionCreated() {
        return TransitionCreated;
    }

    public void setTransitionCreated(boolean TransitionCreated) {
        this.TransitionCreated = TransitionCreated;
        enabledTernaryField(!TransitionCreated);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaAutomataDetail;
    private javax.swing.JButton butAddTrasition;
    private javax.swing.JButton butBack;
    private javax.swing.JButton butFinish;
    private javax.swing.JButton butGenSymbols;
    private javax.swing.JButton butInput;
    private javax.swing.JButton butModifyAlphabet;
    private javax.swing.JButton butModifyFinal;
    private javax.swing.JButton butModifyStack;
    private javax.swing.JButton butModifyState;
    private javax.swing.JButton butModifyTrans;
    private javax.swing.JButton butNewTrasition;
    private javax.swing.JButton butRefreshDetail;
    private javax.swing.JButton butStack;
    private javax.swing.JButton butState;
    private javax.swing.JButton butStorno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelModifyState;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtFinishState;
    private javax.swing.JTextField txtInitialStack;
    private javax.swing.JTextField txtInput;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtStack;
    private javax.swing.JTextField txtStartState;
    private javax.swing.JTextField txtState;
    private javax.swing.JTextField txtTInput;
    private javax.swing.JTextField txtTStackFrom;
    private javax.swing.JTextField txtTStackTo;
    private javax.swing.JTextField txtTStateFrom;
    private javax.swing.JTextField txtTStateTo;
    // End of variables declaration//GEN-END:variables
    
}
