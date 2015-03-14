/*
 * AlgorithmsLibrary.java
 *
 * Created on 4. prosinec 2007, 12:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jgaf.automaton.pda.AutomataConstants;
import jgaf.automaton.pda.Configuration;
import jgaf.automaton.pda.L12N;
import jgaf.automaton.pda.MultiState;
import jgaf.automaton.pda.PAutomata;
import jgaf.automaton.pda.Pair;
import jgaf.automaton.pda.PushdownSystem;
import jgaf.automaton.pda.Transition;
import jgaf.automaton.pda.Triplet;
import jgaf.automaton.pda.WrongValuesException;

/**
 *
 * @author hanis
 */
public class AlgorithmsLibrary {
    
    private AlgorithmsLibrary() {
    }


    public static PAutomata preStar(PAutomata automataIn, PushdownSystem pushdownSystem) {
        PAutomata automataOut = new PAutomata();
        
        LinkedList<Triplet> trans = automataIn.convertToWithoutIntoP();//getTransitionRelation();
        Set<Transition> upDelta = new HashSet<Transition>();
        automataOut.setStates(automataIn.getStates());
        automataOut.setAlphabet(automataIn.getAlphabet());
        automataOut.setFinalStates(automataIn.getFinalStates());
        automataOut.setInitialStates(pushdownSystem.getStates());
        for (Transition transition : pushdownSystem.getTransitions()) {
            for (Pair pair : transition.getPairs()) {
                if(pair.isEpsilonStack() && automataOut.getInitialStates().contains(pair.getState()) 
                && automataOut.getInitialStates().contains(transition.getTernary().getState())) {
                    Triplet triplet = new Triplet();
                    triplet.setStateFrom(transition.getTernary().getState());
                    triplet.setStack(transition.getTernary().getStackSymbol());
                    triplet.setStateTo(pair.getState());
                    trans.add(triplet);
                }
            }            
        }
        while(!trans.isEmpty()) {
            Triplet t = trans.poll();
            if(!automataOut.constaintTransition(t)) {
                automataOut.addTransitionRelation(t);
                for (Transition transition : pushdownSystem.getTransitions()) {
                    if(automataOut.getInitialStates().contains(transition.getTernary().getState())) {
                        for (Pair pair : transition.getPairs()) {
                            if(pair.getStackSymbols().size() == 1 && pair.getStackSymbols().contains(t.getStack())
                            && t.getStateFrom().equals(pair.getState())) {
                                Triplet triplet = new Triplet();
                                triplet.setStateFrom(transition.getTernary().getState());
                                triplet.setStack(transition.getTernary().getStackSymbol());
                                triplet.setStateTo(t.getStateTo());
                                trans.add(triplet);
                            }
                        }
                    }
                }
                
                for (Transition transition : upDelta) {
                    if(automataOut.getInitialStates().contains(transition.getTernary().getState())) {
                        for (Pair pair : transition.getPairs()) {
                            if(pair.getStackSymbols().size() == 1 && pair.getStackSymbols().contains(t.getStack())
                            && t.getStateFrom().equals(pair.getState())) {
                                Triplet triplet = new Triplet();
                                triplet.setStateFrom(transition.getTernary().getState());
                                triplet.setStack(transition.getTernary().getStackSymbol());
                                triplet.setStateTo(t.getStateTo());
                                trans.add(triplet);
                            }
                        }
                    }
                }
                
                for (Transition transition : pushdownSystem.getTransitions()) {
                    if(automataOut.getInitialStates().contains(transition.getTernary().getState())) {
                        Transition newTransition = new Transition();
                        newTransition.setTernary(transition.getTernary());
                        for (Pair pair : transition.getPairs()) {
                            if(pair.getStackSymbols().size() == 2 && pair.getStackSymbols().get(0).equals(t.getStack())
                            && t.getStateFrom().equals(pair.getState())) {
                                Pair newPair = new Pair(t.getStateTo(), pair.getStackSymbols().get(1));
                                newTransition.addPair(newPair);
                                for (Triplet triplet : automataOut.getTransitionRelation()) {
                                    if(triplet.getStateFrom().equals(t.getStateTo()) &&
                                            triplet.getStack().equals(pair.getStackSymbols().get(1))) {
                                        Triplet newTriplet = new Triplet();
                                        newTriplet.setStateFrom(transition.getTernary().getState());
                                        newTriplet.setStack(transition.getTernary().getStackSymbol());
                                        newTriplet.setStateTo(triplet.getStateTo());
                                        trans.add(newTriplet);
                                    }
                                }
                            }
                        }
                        if(newTransition.getPairs().size() > 0) {
                            upDelta.add(newTransition);
                        }
                    }
                }
            }
        }
       // System.out.println("***********8");
       // System.out.println(automataOut);
       // System.out.println("*********************");
        return automataOut;
    }                                    
    
    
    
    public static PAutomata pre(PAutomata automataIn, PushdownSystem pushdownSystem) {
        PAutomata automataOut = new PAutomata();
        LinkedList<Triplet> deltaConvertedInputRelation = automataIn.convertToWithoutIntoP();
        automataOut.setTransitionRelation(deltaConvertedInputRelation);
        automataOut.setStates(automataIn.getStates());
        automataOut.setAlphabet(automataIn.getAlphabet());
        automataOut.setFinalStates(automataIn.getFinalStates());
        automataOut.setInitialStates(pushdownSystem.getStates());
        automataOut.setTransitionRelation(automataOut.transitionsWithoutFromP());
        
        Map<String, String> duplicatedStates = new HashMap<String, String>();
        for (Transition transition : pushdownSystem.getTransitions()) {
            for (Pair pair : transition.getPairs()) {
                if(pair.isEpsilonStack() && automataOut.getInitialStates().contains(pair.getState()) 
                && automataOut.getInitialStates().contains(transition.getTernary().getState())) {
                    if(!duplicatedStates.containsKey(pair.getState())) {
                        duplicatedStates.put(pair.getState(), automataOut.duplicateState(pair.getState(), deltaConvertedInputRelation));
                    }                    
                    Triplet triplet = new Triplet();
                    triplet.setStateFrom(transition.getTernary().getState());
                    triplet.setStack(transition.getTernary().getStackSymbol());
                    triplet.setStateTo(duplicatedStates.get(pair.getState()));
                    automataOut.addTransitionRelation(triplet);
                }
            }            
        }
        
        for (Transition transition : pushdownSystem.getTransitions()) {
            for (Pair pair : transition.getPairs()) {
                if(pair.getStackSymbols().size() == 1 && automataOut.getInitialStates().contains(pair.getState()) 
                && automataOut.getInitialStates().contains(transition.getTernary().getState())) {
                    for (Triplet triplet : deltaConvertedInputRelation) {
                        if(triplet.getStateFrom().equals(pair.getState()) && triplet.getStack().equals(pair.getStackSymbols().get(0))) {                            
                            Triplet newTriplet = new Triplet();
                            newTriplet.setStateFrom(transition.getTernary().getState());
                            newTriplet.setStack(transition.getTernary().getStackSymbol());
                            newTriplet.setStateTo(triplet.getStateTo());
                            automataOut.addTransitionRelation(newTriplet);;                            
                        }
                    }
                }
            }
        }
        
        for (Transition transition : pushdownSystem.getTransitions()) {
            for (Pair pair : transition.getPairs()) {
                if(pair.getStackSymbols().size() == 2 && automataOut.getInitialStates().contains(pair.getState()) 
                && automataOut.getInitialStates().contains(transition.getTernary().getState())) {
                    for (Triplet triplet : deltaConvertedInputRelation) {
                        if(triplet.getStateFrom().equals(pair.getState()) && triplet.getStack().equals(pair.getStackSymbols().get(0))) {                            
                            for (Triplet triplet2 : deltaConvertedInputRelation) {
                                if(triplet2.getStateFrom().equals(triplet.getStateTo()) && triplet2.getStack().equals(pair.getStackSymbols().get(1))) {
                                    Triplet newTriplet = new Triplet();
                                    newTriplet.setStateFrom(transition.getTernary().getState());
                                    newTriplet.setStack(transition.getTernary().getStackSymbol());
                                    newTriplet.setStateTo(triplet2.getStateTo());
                                    automataOut.addTransitionRelation(newTriplet);;
                                }
                            }
                        }
                    }
                }
            }
        }
        return automataOut;
    }

    
    private static Set<MultiState> differenceSet(Set<MultiState> m1, Set<MultiState> m2) {
        Set<MultiState> diff = new HashSet<MultiState>(m1);
        diff.removeAll(m2);
        return diff;
    }    
            
    
    public static PAutomata NFAtoDFA(PAutomata nfa, String stateInit) {
      //  Collections.shuffle(nfa.getTransitionRelation());
        Set<MultiState> newStates = new HashSet<MultiState>();
        MultiState initState = new MultiState();
        initState.addState(stateInit);
        newStates.add(initState);
        Set<Triplet> newTransitionRelation = new HashSet<Triplet>();
        Set<MultiState> newFinalStates = new HashSet<MultiState>();
        Set<MultiState> done = new HashSet<MultiState>();
        
        Set<MultiState> diffSet = differenceSet(newStates, done);
        while(!diffSet.isEmpty()) {
            MultiState stateM = diffSet.iterator().next();
            if(stateM.intersectionSet(nfa.getFinalStates())) {
                newFinalStates.add(stateM);
            }
            for(String symbol : nfa.getAlphabet()) {
                MultiState stateN = new MultiState();
                for (Triplet triplet : nfa.getTransitionRelation()) {
                    for (String stateP : stateM.getState()) {
                        if(triplet.getStateFrom().equals(stateP) && triplet.getStack().equals(symbol)) {
                            stateN.addState(triplet.getStateTo());
                        }
                    }
                }
                newStates.add(stateN);
                newTransitionRelation.add(new Triplet(stateM, symbol, stateN));
            }
            done.add(stateM);
            diffSet = differenceSet(newStates, done);
        }
        
        PAutomata dfa = new PAutomata();
        dfa.setAlphabet(nfa.getAlphabet());
        for (MultiState state : newStates) {
            dfa.addControlLocation(state.toString());
        }
        dfa.setInitialStates(nfa.getInitialStates());
        dfa.setDfaInitialState(initState.toString());     
        for (MultiState state : newFinalStates) {
            dfa.addFinalStates(state.toString());
        }
        for (Triplet triplet : newTransitionRelation) {
            dfa.addTransitionRelation(triplet.convertToNormal());
        }
        return dfa;
        
    }    
    
    

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////containsConfiguration///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //na vstup dostane konfiguraci a p-automat. vrati true pokud je dana konfigurace rozpoznavana p-automatem///
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean containsConfiguration(Configuration configuration, PAutomata pAutomata) {
        //pokud je konfigurace ve stavu ktery p-automata vubec neobsahje tak rovnou vrat false (tohle by ale nemelo nastat myslim :))
        if(!pAutomata.getInitialStates().contains(configuration.getCurrentState())) {
            return false;
        }
        //transformuje vstupni p-automat na deterministicky p-automat ... pri volani NFAtoDFA se pouzije itake
        //stav konfigurace pro urceni pocatecniho stavu p-automata
   
      //  System.out.println("*******************************************8");
     //   System.out.println(pAutomata);
      //  System.out.println("********************************************");
                
        PAutomata pDfa = AlgorithmsLibrary.NFAtoDFA(pAutomata, configuration.getCurrentState());
        

        String state = pDfa.getDfaInitialState();
        
        List list = configuration.getStack().AsList();

        for (String stack : (LinkedList<String>)list) {
            boolean contains = false;
            for (Triplet triplet : pDfa.getTransitionRelation()) {
                if(triplet.getStateFrom().equals(state) && triplet.getStack().equals(stack)) {
                    state = triplet.getStateTo();                   
                    contains = true;
                    break;
                }
            }
            if(!contains) {
                return false;
            }
        }
        if(!pDfa.getFinalStates().contains(state)) {
            for (Triplet triplet : pDfa.getTransitionRelation()) {
                if(triplet.getStateFrom().equals(state) && triplet.getStack().equals(PAutomata.EPSILON)) {
                    state = triplet.getStateTo();
                    break;
                }
            }
        }        
        return pDfa.getFinalStates().contains(state);                
    }        
    
    
    
    
    public static  List<String> createStack(String stringStack) throws WrongValuesException {
        List<String> list = new ArrayList<String>();
        String part = "";
        boolean more = false;
        for (int i = 0; i < stringStack.length(); i++) {
            if(stringStack.charAt(i) == AutomataConstants.SEPARATOR) {
                if(more) {
                    if(part.equals("")) {
                        throw new WrongValuesException(L12N.getValue("warn.parse.stack"));
                    }
                    list.add(part);
                } else {
                    part = "";
                }
                more = !more;                
            } else {
                if(more) {
                    part = part + stringStack.charAt(i);
                } else {
                    list.add(String.valueOf(stringStack.charAt(i)));
                }
            }
        }
        if(more) {
            throw new WrongValuesException(L12N.getValue("warn.parse.stack"));            
        }
        return list;
    }                    
}
