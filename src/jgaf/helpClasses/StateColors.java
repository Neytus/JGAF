/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.helpClasses;

import java.awt.Color;

/**
 *
 * @author LordDrake
 */
public class StateColors {
    
    final static Color[] colorsTable = {
        Color.BLUE, Color.CYAN, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
        Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW, 
        Color.BLUE.darker(), Color.CYAN.darker(), Color.GREEN.darker(), 
        Color.LIGHT_GRAY.darker(), Color.MAGENTA.darker(), Color.ORANGE.darker(), 
        Color.PINK.darker(), Color.RED.darker(), Color.YELLOW.darker()};        
        
    public StateColors(){    
    }
    
    public Color getColor(int dec){
        if(dec<colorsTable.length){
            return colorsTable[dec];
        } else{
            return null;
        }
    }
    
    public int getColorsCount(){
        return colorsTable.length;
    }
}
