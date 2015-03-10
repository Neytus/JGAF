/*
 * L12N.java
 *
 * Created on 1. prosinec 2007, 18:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author hanis
 */
public class L12N {

    private L12N() {
    }
       
    public static String getValue(String key) {        
        return ResourceBundle.getBundle("pdasimulator/localization", Locale.getDefault()).getString(key);        
    }
    
}
