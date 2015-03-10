/*
 * Transition.java
 *
 * Created on 27. srpen 2007, 15:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author hanis
 */
public class Transition implements Comparable<Transition>, Cloneable {

    private Ternary ternary;
    private SortedSet<Pair> pairs;
    
    public Transition() {
        pairs = new TreeSet<Pair>();
    }
        
    public boolean match(Ternary ternary) {
        return getTernary().equalsWithEpsilon(ternary);
    }

    public Ternary getTernary() {
        return ternary;
    }

    public void setTernary(Ternary ternary) {
        this.ternary = ternary;
    }
    
    public void setTernary(String actualState, char Symbol, String topOfStack) {            
        this.ternary = new Ternary(actualState, Symbol, topOfStack);
    }          
  
    public void setTernary(String actualState, String topOfStack) {            
        this.ternary = new Ternary(actualState, topOfStack);
    }                    

    public SortedSet<Pair> getPairs() {
        return pairs;
    }

    public void setPairs(SortedSet<Pair> pairs) {
        this.pairs = pairs;
    }

    public void addPair(Pair pair) {
        this.pairs.add(pair);
    }
    
     public void addPair(String state, List<String> stackSymbols) {
        this.pairs.add(new Pair(state, stackSymbols));
    }
    
     public void addPair(String state) {
        this.pairs.add(new Pair(state));
    }    

    public String getStackSymbol() {
        return ternary.getStackSymbol();
    }
    
    public String wrtePairs() {
        StringBuilder pairSet = new StringBuilder("{");
        for (Pair pair : pairs) {
            pairSet.append(pair);
            pairSet.append(",");
        }
        return (!pairs.isEmpty() ? pairSet.substring(0, pairSet.length() - 2) + "}" : pairSet + "}");            
    }
    
    public String writePairs() {
        StringBuilder write = new StringBuilder("{");
        for (Pair pair : pairs) {
            write.append(pair).append(",");
        }
        return write.substring(0, write.length() - 1) + "}";
    }
    
    public String toString() {
        return String.valueOf(DefaultValues.getInstance().getTransition()) + getTernary() + " = " + writePairs() ;
    }    
    

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof Transition)) {
            return false;
        }
        Transition transition = (Transition) obj;
        return this.getTernary().equals(transition.getTernary());    
    }

   public int hashCode() {
        return 13 + 17 * ternary.hashCode(); 
   }

    public int compareTo(Transition obj) {
        return this.ternary.compareTo(obj.getTernary());
    }

    public boolean isEpsilon() {
        return ternary.isEpsilon();
    }


    @Override
    public Object clone() {
        Transition obj = new Transition();
        obj.setTernary((Ternary) ternary.clone());
        for (Pair pair : pairs) {
            obj.addPair((Pair) pair.clone());
        }
        return obj;
    }


}
