/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import javax.swing.JTable;

/**
 *
 * @author g
 */
public class AutoTable extends JTable{
    private AutoTableModel atm;
    
    
    public AutoTable(AutoTableModel atm) {
       //super();
       this.atm=atm;
        
       
        
        

    }
    
    private void init(){
        setModel(atm);
        
        

    


       
    
    }
}
