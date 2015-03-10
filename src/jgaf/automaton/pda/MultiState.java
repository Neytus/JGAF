/*
 * MultiState.java
 *
 * Created on 25. listopad 2007, 19:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalElement;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author hanis
 */
public class MultiState {
    
    private SortedSet<String> state;
    
    public MultiState() {
        this.state = new TreeSet<String>();            
    }
    
    public MultiState(String state) {
        this();
        addState(state);
    }

    public boolean contains(String state) {
        return this.state.contains(state);
    }
    
    public boolean intersectionSet(Set<String> set) {
        for (String oneState : set) {
            if(contains(oneState)) {
                return true;
            }
        }
        return false;
    }  
    
    public boolean equals(Object o) {
        if(!(o instanceof MultiState)) {
            return false;
        }
        MultiState obj = (MultiState) o;        
        return (obj.getState().equals(state));      
        //return (obj.getState().containsAll(state));      
    }
    
    public int hashCode() {
        return state.hashCode();
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        for (String oneState : state) {
            builder.append(oneState).append(",");
        }
        return (state.isEmpty() ? "{}" : builder.substring(0, builder.lastIndexOf(",")) + "}");
    }

    public Set<String> getState() {
        return state;
    }

    public void setState(Set<String> state) {
        this.state = (SortedSet)state;
    }
    
    public void addState(String state) {
        this.state.add(state);
    }
    
    public static void main(String[] args) {

//        Locale subak = Locale.getDefault();


    }
    

}