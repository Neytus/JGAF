/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import jgaf.lrextension.procedurefaces.ItemAutoRep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jgaf.lrextension.WString;
import jgaf.lrextension.FiFoUtils;
import jgaf.grammar.Grammar;
import jgaf.grammar.Symbol;

/**
 *
 * @author g
 */
public class ParserTablesCalc {
    ///static??

    private ItemAutoRep iaRep;
    private Map<Integer, HashMap<Symbol, Integer>> gotoRep;
    private List<Symbol> gotoTColumns;
    private Map<Integer, HashMap<WString, SameActionItems>> actionRep;
    private int k;
    private List<WString> actionTColumns;
    private Map<Symbol,Set<WString>> fiMap;
    private  Grammar gram;
    private boolean  conflict;
    
    public ParserTablesCalc(ItemAuto itemAuto) {
        this.iaRep = new ItemAutoRep(itemAuto.getResult());
        this.gram= itemAuto.getGrammar();
        this.k=itemAuto.getK();
        this.fiMap=itemAuto.getFiMap();
        
    }

    public void calcGotoTable() {
        gotoTColumns = new ArrayList<Symbol>();
        for (Symbol symbol : gram.getSymbols()) {
            if (!symbol.isEpsilon()) {
                gotoTColumns.add(symbol);
            }
        }
        
        gotoRep = new HashMap<Integer, HashMap<Symbol, Integer>>();
        List<AutoState> iaList = iaRep.get();

        for (LinkedHashSet<Item> State : iaList) {
            HashMap<Symbol, Integer> tableLine = new HashMap<Symbol, Integer>();
            
            for (Item item : State) {
                if (item.isShiftable()) {
                    tableLine.put(item.atDot(), item.getToItemSet());
                }
            }
            gotoRep.put(iaList.indexOf(State), tableLine);
        }



        
    }

    private void noLookaheadActionTable(){
    WString epsilon=FiFoUtils.createEpsilonStr();  //new WString(); //
    actionTColumns =new ArrayList<WString>() ;   
    actionTColumns.add(epsilon);
    actionRep = new HashMap<Integer, HashMap<WString, SameActionItems>>();
    
    for (LinkedHashSet<Item> State : iaRep.get()) {
    HashMap<WString, SameActionItems> tableLine = new HashMap<WString, SameActionItems>();
    actionRep.put(iaRep.get().indexOf(State), tableLine);
    
    for (Item item : State) {
                if (!item.needsClosure()) {
                    if (tableLine.containsKey(epsilon)){
                           if (!tableLine.get(epsilon).add(item)){conflict=true;}
                            
                    }else{
                            tableLine.put(epsilon, new SameActionItems(item));
                            
                        }}
                }
    }
    }
    
    private void calcMoreKActionTable() {
        Set<WString> terStrgsShortSet = new HashSet<WString>();
        actionRep = new HashMap<Integer, HashMap<WString, SameActionItems>>();
        List<AutoState> iaList = iaRep.get();

        for (LinkedHashSet<Item> State : iaList) {
            HashMap<WString, SameActionItems> tableLine = new HashMap<WString, SameActionItems>();
            
            
            
            actionRep.put(iaList.indexOf(State), tableLine);
            for (Item item : State) {
                if (!item.needsClosure()) {
                    Set<WString> allItemOutcomes;
                    if (item.isShiftable()) {
                        Set<WString> fiset = FiFoUtils.fiFast(item.allToRead(), k,fiMap);
                        allItemOutcomes = FiFoUtils.concatKSet(k, fiset, item.getLocFo());
                    }else{allItemOutcomes=item.getLocFo();}
                        
                        for (WString terStrg : allItemOutcomes) {
                            if (tableLine.containsKey(terStrg)){
                                if (!tableLine.get(terStrg).add(item)) {conflict=true;}
                            }else{
                            tableLine.put(terStrg, new SameActionItems(item));
                            
                        }terStrgsShortSet.add(terStrg);}
                    }
                    }
                
    
        }actionTColumns=new ArrayList<WString>(terStrgsShortSet);
    }

    public Map<Integer, HashMap<Symbol, Integer>> getGotoRep() {
        return gotoRep;
    }

    public List<Symbol> getGotoTColumns() {
        return gotoTColumns;
    }

    public ItemAutoRep getIaRep() {
        return iaRep;
    }

    public List<WString> getActionTColumns() {
        return actionTColumns;
    }

    public Map<Integer, HashMap<WString, SameActionItems>> getActionRep() {
        return actionRep;
    }

    

    void calcActionTable() {
        if (k==0) {
            noLookaheadActionTable();
        }
        else {
            calcMoreKActionTable();
        }
    }

    public int getK() {
        return k;
    }

    public boolean hasConflict(){
        return conflict;
    }
}
