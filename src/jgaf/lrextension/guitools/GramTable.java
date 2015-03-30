/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import javax.swing.JTable;
import jgaf.grammar.Grammar;

/**
 *
 * @author g
 */



public class GramTable extends JTable{
   

   
    
    public GramTable(Grammar g) {
        super(new CgramTableModel(g));
        
    }

    
     
    
   
    
}
