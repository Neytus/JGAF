/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

//import com.rits.cloning.Cloner;
import jgaf.lrextension.procedurefaces.ItemAutoRep;
import java.awt.Color;
import java.util.*;
import jgaf.Constants.MathConstants;
import jgaf.lrextension.CFGUtils;
import jgaf.lrextension.FiFoUtils;
import jgaf.lrextension.WString;
import jgaf.automaton.fa.undo.ChangeAcceptingStatesStep;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRule;
import jgaf.grammar.Symbol;
import jgaf.lrextension.StringOutputUtils;

/**
 *
 * @author g
 */
public class ItemAuto {

    private Grammar grammar;
    private ItemAutoRep iA;
    private List<AutoState> iAwork;
    private Map<Symbol, Set<WString>> fiMap;
    private Map<Symbol, Set<WString>> foMap;
    private int k;
    private int lastInState;
    private List<ItemAutoRep> sequence;
    private List<String> logSequence;
    private int type;
    

    public ItemAuto(Grammar grammar,
                    int k,
                    int type) {
        this.grammar = grammar;
        fiMap = FiFoUtils.calcFiMap(grammar, k);
        foMap = FiFoUtils.calcFo(grammar, k);
        this.k = k;
        this.type = type;
        iA = new ItemAutoRep();
        iAwork = iA.get();
        sequence = new LinkedList<ItemAutoRep>();
        logSequence = new LinkedList<String>();
    }

    public int getK() {
        return k;
    }

    public Map<Symbol, Set<WString>> getFiMap() {
        return fiMap;
    }
    
    

    public List<AutoState> getResult() {
        return sequence.get(sequence.size()-1).get();
    }

    private void calcClosure(AutoState stateItems) {
        AutoState changeSet = new AutoState();
        logState("Checking if some items need closure");
        boolean closureAdded = false;
        boolean changed = true;
        while (changed) {
            changeSet.addAll(stateItems);
            changed=false;
            for (Item item : changeSet) {
                if (item.needsClosure()) {
                    closureAdded = true;
                    for (ProductionRule rule : CFGUtils.getNonterminalRules(grammar, item.atDot())) {

                        Item closingItem;

                        if (type == LRParserProcedure.SLR) {

                            closingItem = new Item(rule, 0, foMap.get(rule.getLeftHandSide().getFirst()));

                        } else {
                            closingItem = new Item(rule, 0, inhrtdLocFo(item, false));
                        }


                        if (stateItems.add(closingItem)) {
                            changed=true;
                            item.color(Color.GREEN);
                            closingItem.color(Color.RED);
                            closingItem.color(3, Color.WHITE);
                            logState("Adding closure for " + item.getRuleStr()
                                    + " with local follow " + item.getLocFoStr());
                            closingItem.decolor();
                            closingItem.color(3, Color.RED);
                            item.decolor();
                            inhrtdLocFo(item, true);
                            closingItem.decolor();
                        }
                    }
                }

            }


        }
        if (!closureAdded) {
            logState("..they don't");
        }
    }

    private Set<WString> inhrtdLocFo(Item item,
                                     boolean verbose) {

        if (k == 0) {
            return new HashSet<WString>();
        }


        Set<WString> fiToRead = FiFoUtils.fiFast(item.toRead(), k, fiMap);

        if (fiToRead.isEmpty()) {
            fiToRead = FiFoUtils.createEpsilonSet();
        }

        Set<WString> ret = FiFoUtils.concatKSet(k, fiToRead, item.getLocFo());
        if (verbose) {
            item.color(0, Color.BLUE);
            item.color(3, Color.BLUE);
            logState("  Computation of local follow for this item: " + setToString(fiToRead) + " " + MathConstants.O_PLUS + " " + item.getLocFoStr() + " = " + setToString(ret));
            item.decolor();
        }

        return ret;
    }

    public void calcAuto() {
        //Cloner c=new Cloner();
        iAwork.clear();
        // sequence.add(c.deepClone(iA)); //////////////////
        lastInState = 0;
        logState();
        AutoState firstState = new AutoState();
        iAwork.add(firstState);
        logState("Creating state 0:");
        Item fItem = firstItem();
        firstState.add(fItem);
        fItem.color(Color.RED);
        logState("First item created form first grammar rule with local follow " + fItem.getLocFoStr());
        fItem.decolor();
        calcClosure(firstState);
        firstState.unifySimilar();
        logState("Unifying same items with different local follow" );
        
        logState("For all items in state 0 calculating states to go to after one position shift.");
        calcToState(firstState);


        int lastFinishedState = 0;

        while (lastFinishedState != lastInState) {

            AutoState stateItems = new AutoState();
            iAwork.add(stateItems);
            logState("Creating state " + (lastFinishedState + 1) + ":");
            for (Iterator<AutoState> it = iAwork.iterator(); it.hasNext();) {
                AutoState state = it.next();

                for (Iterator<Item> it1 = state.iterator(); it1.hasNext();) {
                    Item item = it1.next();
                    if (lastFinishedState + 1 == item.getToItemSet()) {
                        Item newItem = new Item(item.getRule(),
                                                item.getPosition() + 1,
                                                item.getLocFo());
                        stateItems.add(newItem);
                        item.color(Color.GREEN);
                        newItem.color(Color.RED);
                        logState("Adding shifted " + item.getRuleStr() + " with the same local follow");
                        item.decolor();
                        newItem.decolor();
                    }
                }
            }
            calcClosure(stateItems);
            stateItems.unifySimilar();
            logState("Unifying same items with different local follow" );
            calctoStateFromOlders(stateItems);
            calcToState(stateItems);
            lastFinishedState++;



        }
        System.out.print("\n"+iAwork.size());
        if (type == LRParserProcedure.LALR) {
            lalrMerge();
        }
        logState("Marking possible conflicts.\nComputation complete.");
    }

    private Item firstItem() {
        if (k == 0) {
            return new Item(CFGUtils.getFirstRule(grammar), 0, new HashSet<WString>());
        } else {
            return new Item(CFGUtils.getFirstRule(grammar), 0, FiFoUtils.createEpsilonSet());
        }
    }

    private void calcToState(Set<Item> stateItems) {
        Set<Item> tmp = new LinkedHashSet<Item>();
        tmp.addAll(itemsToShift(stateItems));


        for (Iterator<Item> it = itemsToShift(stateItems).iterator(); it.hasNext();) {
            Item item = it.next();
            if (item.getToItemSet() == -1) {

                item.setToItemSet(lastInState + 1);

                for (Iterator<Item> it1 = tmp.iterator(); it1.hasNext();) {
                    Item item1 = it1.next();

                    if (item1.atDot().equals(item.atDot())) {
                        item1.setToItemSet(lastInState + 1);
                        if (!item1.equals(item)) {
                            item.color(2, Color.GREEN);
                            item1.color(2, Color.RED);
                            logState(item1.getRuleStr() + " with " + item1.getLocFoStr() + " goes "
                                    + "to same state as " + item.getRuleStr() + " with "
                                    + item.getLocFoStr() + " because they shift at the same "
                                    + "character (" + item.getNextSymbolStr() + ")");
                            item.decolor();
                            item1.decolor();
                        } else {
                            item1.color(2, Color.RED);
                            logState(item1.getRuleStr() + " with " + item1.getLocFoStr() + " goes to new state "
                                    + (lastInState + 1));
                            item1.decolor();
                        }
                    }
                }
                lastInState++;
            }
        }
    }

    private void calctoStateFromOlders(Set<Item> stateItems) {
        Set<Item> tmp = new LinkedHashSet<Item>();
        tmp.addAll(itemsToShift(stateItems));
        for (Iterator<Item> it = tmp.iterator(); it.hasNext();) {
            Item item = it.next();

            for (Iterator<AutoState> it2 = iAwork.iterator(); it2.hasNext();) {
                LinkedHashSet<Item> state = it2.next();
                for (Iterator<Item> it1 = itemsToShift(state).iterator(); it1.hasNext();) {
                    Item olderItem = it1.next();
                    if (item.toSameState(olderItem)) {
                        item.setToItemSet(olderItem.getToItemSet());
                    }
                }
            }
        }
    }

    private Set<Item> itemsToShift(Set<Item> items) {
        Set<Item> ret = new LinkedHashSet<Item>();
        for (Iterator<Item> it = items.iterator(); it.hasNext();) {
            Item item = it.next();
            if (item.isShiftable()) {
                ret.add(item);
            }

        }
        return ret;
    }

    public List<ItemAutoRep> getSequence() {
        return sequence;
    }

    public List<String> getLogSequence() {
        return logSequence;
    }

    private void logState(String msg) {
        sequence.add(iA.clone());
        logSequence.add(msg);
    }

    private void logState() {
        sequence.add(iA.clone());
        logSequence.add("");
    }

    private static String setToString(Set<WString> set) {
        if (set == null) {
            return ("");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        String prefix = "";
        for (List list : set) {
            sb.append(prefix);
            prefix = ",";

            sb.append(StringOutputUtils.listToString(list));


        }
        sb.append("}");
        return sb.toString();
    }

    private static String listToString(WString list) {
        if (list == null) {
            return ("");
        }
        StringBuilder sb = new StringBuilder();
        for (Object symbol : list) {
            sb.append(symbol);
        }
        return sb.toString();
    }

    public Grammar getGrammar() {
        return grammar;



    }

    private void lalrMerge() {
        logState("Merging states if item cores from one state are all contained in another state.");
        boolean changed = true;
        while (changed) {
            changed = false;
            List<AutoState> iATemp = new ArrayList<AutoState>();
            for (AutoState linkedHashSet : iAwork) {
                iATemp.add(new AutoState(linkedHashSet));
            }
            
            
            
            iAwork=iATemp;
            iA=new ItemAutoRep(iAwork);
            
            for (Iterator<AutoState> it1 = iAwork.iterator(); it1.hasNext();) {
                AutoState iSet = it1.next();
                boolean foundMatchingItem=false;
                for (Iterator<AutoState> it2 = iAwork.iterator(); it2.hasNext() && !foundMatchingItem;) {
                    AutoState iSet2 = it2.next();
                    
                    if (mergeStates(iSet2, iSet)) {
                        changed = true;
                        foundMatchingItem=true;
                        
                    }
            if (foundMatchingItem) {
               
                fixMerged(iAwork.indexOf(iSet2), iAwork.indexOf(iSet));
                it1.remove();
                
            }
                }

            }
            for (Iterator<AutoState> it = iAwork.iterator();
                    it.hasNext();) {
                AutoState itemSet = it.next();
                if (itemSet.isEmpty()) {
                    it.remove();
                }
            }
        }logState("Can't merge more states. Calculation finished.");

    }

    private boolean mergeStates(Set<Item> a,
                                Set<Item> b) {
        if (a.equals(b) || a.isEmpty() || b.isEmpty()) {
            return false;
        }
        if (!iAwork.contains(a)) return  false;
        
        boolean bIsSubsetA = true;
        int bStateIndex=iAwork.indexOf(b);
        int aStateIndex=iAwork.indexOf(a);
        
        if (a.size() < b.size() || bStateIndex < aStateIndex) {
            return false;
        }

        for (Item itemB : b) {
            boolean iFromAfoundInB = false;
            for (Item itemA : a) {
                if (itemA.getRule().equals(itemB.getRule()) && itemA.getPosition() == itemB.getPosition()) {
                    iFromAfoundInB = true;
                    
                   
                }
            }
            if (!iFromAfoundInB) {
                bIsSubsetA = false;
            }
        }


        if (bIsSubsetA ) {
             logState("All items from "+bStateIndex+" were found in "+aStateIndex+ ". Merging state "+bStateIndex+" into "+aStateIndex+"."); 
            for (Iterator<Item> it = b.iterator(); it.hasNext();) {
                    Item itemB = it.next();
            for (Item itemA : a) {
               
                    if (itemA.getRule().equals(itemB.getRule()) && itemA.getPosition() == itemB.getPosition()) {
                        itemA.getLocFo().addAll(itemB.getLocFo());
                        if (itemA.getToItemSet()==bStateIndex){
                        itemA.setToItemSet(itemB.getToItemSet());
                        }
                    }
                }it.remove();
               
            }
            b=new HashSet<Item>();
             return true;
        } else {
            return false;
        }
    }
    
    public  void fixMerged(int replace,int deleted){
        
            for (LinkedHashSet<Item> linkedHashSet : iAwork) {
                for (Item item : linkedHashSet) {
                    if(item.getToItemSet()==deleted) {
                        item.setToItemSet(replace);
                        
                    }
                     if(item.getToItemSet()>deleted) {
                        item.setToItemSet(item.getToItemSet()-1);
                        
                     }
                }
            }
        
    }
    
    public ItemAutoRep getItemAutoRep(){
        return sequence.get(sequence.size()-1);
    }
    
    
//    public void fixDeleted(int deleted){
//            
//            for (LinkedHashSet<Item> linkedHashSet : iAwork) {
//                for (Item item : linkedHashSet) {
//                    if(item.getToItemSet()>deleted) item.setToItemSet(item.getToItemSet()-1);
//                }
//            }
//}

    public int getType() {
        return type;
    }

}
//private void lalrMerge2() {
//
//        ListIterator<LinkedHashSet<Item>> reverseIt;
//
//        for (reverseIt = iAwork.listIterator(iAwork.size()); reverseIt.hasPrevious();) {
//            Set<Item> iSetBack = reverseIt.previous();
//            for (Iterator<Item> it = iSetBack.iterator(); it.hasNext();) {
//                Item itemBack = it.next();
//                boolean wasMerged = true;
//                for (Iterator<LinkedHashSet<Item>> it2 = iAwork.iterator(); it2.hasNext();) {
//                    LinkedHashSet<Item> iSetFornt = it2.next();
//                    if (iAwork.indexOf(iSetBack)!=iAwork.indexOf(iSetFornt)){
//                        
//                        for (Item i : iSetFornt) {
//
//                            if (i.getRule().equals(itemBack.getRule())
//                                    && i.getPosition() == itemBack.getPosition()) {
//
//                                i.getLocFo().addAll(itemBack.getLocFo());
//                            }
//                        }
//                    }
//                }
//                if (wasMerged) {
//                    it.remove();
//                }
//            }
//        }
//        for (Iterator<LinkedHashSet<Item>> it = iAwork.iterator(); it.hasNext();) {
//            LinkedHashSet<Item> itemSet = it.next();
//            if (itemSet.isEmpty()) {
//                it.remove();
//            }
//        }
//
//
//
//
//    }
//}