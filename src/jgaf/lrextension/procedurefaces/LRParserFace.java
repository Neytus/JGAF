/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedurefaces;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 *
 * @author g
 */
public class LRParserFace extends JPanel {
//    private final JPanel autoPanel;
//    private final JPanel tablePanel;
//   private final JPanel parserPanel;
    
    
    
    
    
    public LRParserFace(JPanel autoPanel,TablePanel tablePanel, ParserPanel parserPanel) {
       super(new BorderLayout()); 
        
       
       
       
       
       
     final ParserPanel fParserPanel=parserPanel;  
     final TablePanel   fTablePanel=tablePanel;
       
      JTabbedPane tabbedPane = new JTabbedPane();
        
tabbedPane.addTab("Item automaton", autoPanel);
      
tabbedPane.addTab("Parser tables", fTablePanel);

tabbedPane.addTab("Parse run", parserPanel);
//tabbedPane.setEnabledAt(1, false);
add(tabbedPane);
tabbedPane.addChangeListener(new ChangeListener() {
// boolean firstTime=true;
            public void stateChanged(ChangeEvent e) {
                 JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                 if (sourceTabbedPane.getSelectedIndex()==2 && !fParserPanel.hasInput()) {
                    fParserPanel.parseInput();
                }
               
//                 if (firstTime && sourceTabbedPane.getSelectedIndex()==1 && fTablePanel.hasConflict() ) {
//                    firstTime=false;
//                    fTablePanel.showConflictD();
//                }
            }
        });

    }


    

}
