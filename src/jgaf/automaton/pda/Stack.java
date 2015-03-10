/*
 * Stack.java
 *
 * Created on 3. listopad 2007, 18:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author hanis
 */
public class Stack {
    
    private LinkedList<String> stack;
    
    public Stack() {
        this.stack = new LinkedList<String>();
    }
    
    public int length() {
        return stack.size();
    }
    
    public void push(String symbol) {
        stack.addFirst(symbol);       
    }
    
    public void push(List<String> symbols) {
        for (int i = symbols.size() - 1; i >= 0;  i--) {
            stack.addFirst(symbols.get(i));
        }
    }
    
    public String pop() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return stack.poll();
    }

    public String top() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return stack.peek();
    }
    
    public String bottom() {
        if(isEmpty()) {
            return null;
        }
        return stack.getLast();
    }    
    
    public List<String> AsList() {
        return stack;        
    }        
    
    public String get(int index) {
        return stack.get(index); 
    }
    
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    public String toString() {
        if(!isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length(); i++) {
                sb.append(get(i));
            }
            return sb.toString();
        }
        return "";
    }
    
    
    
    public String writeStack() {
        if(!isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length(); i++) {
                if(get(i).length() > 1) {
                    sb.append(AutomataConstants.SEPARATOR).append(get(i)).append(AutomataConstants.SEPARATOR);
                } else {
                    sb.append(get(i));
                }
            }
            return sb.toString();
        }
        return "";
    }
    
}
