/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

/**
 *
 * @author hanis
 */
public class GrammarEditorToolbar extends JToolBar {


    private JButton addRowButton;
    private JButton removeEmptyRows;

    private JButton redoButton;
    private JButton undoButton;

    private JButton sortAscendingButton;
    private JButton sortDescendingButton;


    private JButton descriptionButton;
    private JButton typeButton;

    private JComboBox startCombo;


    private GrammarEditor editor;



    public GrammarEditorToolbar(GrammarEditor editor) {
        super();
        this.editor = editor;
        init();
    }



    private void init() {
        setRollover(true);        
        setFloatable(false);


        addRowButton = new JButton();
        addRowButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/addRow.png")));
        addRowButton.setToolTipText("Add empty rule");
        addRowButton.setFocusable(false);
        addRowButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addRowButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.addEmptyProductionRule();
            }
        });
        add(addRowButton);




        removeEmptyRows = new JButton();
        removeEmptyRows.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/grammar/clear.png")));
        removeEmptyRows.setToolTipText("Remove empty rules");
        removeEmptyRows.setFocusable(false);
        removeEmptyRows.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        removeEmptyRows.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        removeEmptyRows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.removeEmptyRules();
            }
        });
        add(removeEmptyRows);


        addSeparator();

        


        undoButton = new JButton();
        undoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/undo.png")));
        undoButton.setToolTipText("undo");
        undoButton.setFocusable(false);
        undoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.undo();
            }
        });
        add(undoButton);




        redoButton = new JButton();
        redoButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/redo.png")));
        redoButton.setToolTipText("redo");
        redoButton.setFocusable(false);
        redoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        redoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.redo();
            }
        });
        add(redoButton);

        addSeparator();





        sortAscendingButton = new JButton();
        sortAscendingButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/grammar/sortAscending.png")));
        sortAscendingButton.setToolTipText("sort ascending");
        sortAscendingButton.setFocusable(false);
        sortAscendingButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sortAscendingButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sortAscendingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.sortProductionRules();
            }
        });
        add(sortAscendingButton);


        sortDescendingButton = new JButton();
        sortDescendingButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/grammar/sortDescending.png")));
        sortDescendingButton.setToolTipText("sort descending");
        sortDescendingButton.setFocusable(false);
        sortDescendingButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sortDescendingButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sortDescendingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editor.sortProductionRulesDesc();
            }
        });
        add(sortDescendingButton);


        addSeparator();


        descriptionButton = new JButton();
        descriptionButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/24x24/description.png")));
        descriptionButton.setToolTipText("Grammar detail");
        descriptionButton.setFocusable(false);
        descriptionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        descriptionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        descriptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new DescriptionDiaog(editor.getFrame(), 
                        editor.getGrammar().writeGrammar(),
                        editor.getGrammar().writeGrammarShort());
            }
        });
        add(descriptionButton);


        typeButton = new JButton();
        typeButton.setIcon(new ImageIcon(getClass().getResource("/jgaf/icons/toolbar/grammar/question.png")));
        typeButton.setToolTipText("Type of grammar");
        typeButton.setFocusable(false);
        typeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        typeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        typeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int type = editor.getGrammar().getType();
                String answer = editor.getGrammar().getName() + " is ";
                switch(type) {
                    case -1: answer += "not well-formed."; break;
                    case 0: answer += "recursively enumerable grammar (type 0)."; break;
                    case 10: answer += "context-sensitive grammar (type 1)."; break;
                    case 20: answer += "context-free grammar (type 2)."; break;
                    case 30: answer += "regular grammar (type 3)."; break;
                    case 21: answer += "context-free grammar with epsilon rules (but type 4 by traditional definition)."; break;
                    case 31: answer += "regular grammar with epsilon rules (but type 4 by traditional definition)."; break;
                }
                JOptionPane.showMessageDialog(editor.getFace(), answer, " Grammar type",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
        add(typeButton);




        addSeparator();
        
        add(new JLabel("S="));
        startCombo = new JComboBox();
                startCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                Symbol s = (Symbol) startCombo.getSelectedItem();
                editor.getGrammar().setStartNonterminal(s);
            }
        });
        add(startCombo);







//
//
//        JButton b = new JButton("info");
//        b.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                System.out.println("------------------------------------");
//                System.out.println(editor.getGrammar().getType());
//                System.out.println(editor.getGrammar().writeGrammar());
//            }
//        });
//        add(b);
//
//
//        JButton c = new JButton("jpg");
//        c.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                try {
//                    JPEGExporter.saveComponentAsJPEG(editor.getRepresenter(), "/home/hanis/Desktop/aaaaa.jpg");
//                    JPEGExporter.saveComponentAsPNG(editor.getRepresenter(), "/home/hanis/Desktop/bbbbb");
//                } catch (FileNotFoundException ex) {
//                    Logger.getLogger(GrammarEditorToolbar.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IOException ex) {
//                    Logger.getLogger(GrammarEditorToolbar.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        add(c);

    }



    public void repaintCombo() {
        Object[] nonterminals = editor.getGrammar().getNonterminals().toArray();
        Symbol startNon = editor.getGrammar().getStartNonterminal();
        startCombo.setModel(new DefaultComboBoxModel(nonterminals));
        if(startNon != null) {
            startCombo.setSelectedItem(startNon);
        } else {
            //startCombo.addItem("-");
            startCombo.setSelectedItem(null);
        }

    }


}

