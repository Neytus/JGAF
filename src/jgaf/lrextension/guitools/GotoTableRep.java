/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jgaf.grammar.Symbol;
import jgaf.lrextension.WString;

    
    
/**
 *
 * @author g
 */
public class GotoTableRep {
    private Map<Integer, HashMap<Symbol, Integer>> gotoRep;
    private List<Symbol> gotoTColumns;
    
    

    public GotoTableRep(Map<Integer, HashMap<Symbol, Integer>> gotoRep,
                        List<Symbol> gotoTColumns) {
        this.gotoRep = gotoRep;
        this.gotoTColumns = gotoTColumns;
    }

    public GotoTableRep(List<Symbol> gotoTColumns) {
        this.gotoTColumns = gotoTColumns;
    }

    
    public void insertTableRow(HashMap<Symbol, Integer> rowMap,int index ){
    gotoRep.put( index,rowMap);
    } 
    
    public boolean nextStateInTable(int currentState, Symbol nextSymbol){
        return gotoRep.get(currentState).containsKey(nextSymbol);
    }
    
    public int nextState(int currentState, Symbol nextSymbol){
    
    if(!nextStateInTable(currentState, nextSymbol)) return currentState;
    return gotoRep.get(currentState).get(nextSymbol);
    }
}
