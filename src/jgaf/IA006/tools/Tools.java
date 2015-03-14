/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import jgaf.IA006.grammar.Symbol;
import jgaf.IA006.gui.ll.TSymbol;

/**
 *
 * @author Empt
 */
public class Tools 
{
    /**
     * Checker if collection is empty or not
     * @param <T> Type of collection
     * @param collection to be checked
     * @return true if collection is null, or has no elements
     */
    public static <T> boolean isEmpty(Collection<T> collection)
    {
        return collection == null || collection.isEmpty();
    }
    
    /**
     * Method checks whether input string is empty or not
     * @param s input string to be checked
     * @return true if input string is null or does not contains any symbols, false otherwise
     */
    public static boolean stringIsEmpty(String s)
    {
        return s == null || s.isEmpty();
    } 
    
    
    @Deprecated
    public static <T> List<List<T>> intersection(Set<Set<List<T>>> setz)
    {
       List<List<T>> result = new ArrayList<>();
       List<Set<List<T>>> tempInput = new ArrayList<>(setz);
       Set<List<T>> temp = new HashSet<>(tempInput.get(0));

       for(int i = 1; i < tempInput.size(); i++)
       {
           for(List<T> tt : tempInput.get(i))
           {
               for(List<T> ttt : temp)
               {
                   if(ttt.equals(tt))
                   {
                       result.add(tt);
                   }
               }
           }
           temp.addAll(tempInput.get(i));
       }

       return result;
    }
    
    /**
     * Method calculates intersection for given sets of List with type T.
     * @param <T> Return and T type of result
     * @param s1 first set
     * @param s2 second set
     * @return intersection of set1 and set2
     */
    public static <T> Set<List<T>> intersection2(Set<List<T>> s1,Set<List<T>> s2)
    {
        Set<List<T>> result = new HashSet<>();
        
        for(List<T> word : s1)
        {
            if(s2.contains(word))
            {
                result.add(word);
            }
        }
        
        return result;
    }
    
    /**
     * Method builds word as single String from given list of symbols.
     * @param list from which we build word
     * @return empty string is list is null or does not contains any symbol, otherwise 
     * it returns String representation of list.
     */
    public static String buildWord(List<Symbol> list)
    {
        if(list == null)
        {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        for(Symbol s : list)
        {
            sb.append(s);
        }
        return sb.toString();
    }
    
    
    /**
     * Method build HTML output for given input word. It's usefull for LL(k) parsing table
     * where we need bottom index
     * @param list list of symbols from which we build output word
     * @return String representation of input word
     */
    public static String buildHTMLWordT(List<Symbol> list)
    {
        if(list == null)
        {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        for(Symbol s: list)
        {
            if(s instanceof TSymbol)
            {
                TSymbol tt = (TSymbol) s;
                sb.append(tt.getValue()).append("<sub>").append(tt.getStateID()).append("</sub>");
            }
            else
            {
                sb.append(s);
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Static comparator used for Column comparing. After words are build out of column (list of symbol)
     * we sort them by size. If two words have same size words are sorted by lexicographical order.
     * @return 
     */
    public static Comparator<List<Symbol>> getColumnComparator()
    {
        return new Comparator<List<Symbol>>() 
        {
            @Override
            public int compare(List<Symbol> o1,List<Symbol> o2) 
            {
                int result = o2.size() - o1.size();

                if(result == 0)
                {
                    return Tools.buildWord(o1).compareTo(Tools.buildWord(o2));
                }

                return result;
            }
        };
    }
    
    /**
     * Method builds word out of stack.
     * @param list
     * @return 
     */
    public static String buildWord(Stack<Symbol> list)
    {
        return buildWord(new ArrayList<>(list));
    }
    
    
    /**
     * Method builds word out of stack. Reversed is required because
     * order in stack is reversed.
     * @param list stack
     * @return String representation of stack
     */
    public static String buildWordReversed(Stack<Symbol> list)
    {
        StringBuilder sb = new StringBuilder();
        
         for(int i = list.size()-1; i >= 0 ; i--)
         {
             sb.append(list.get(i));
         }
         return sb.toString();
    }
    
    
    /**
     * Method builds multiple words in set.
     * @param set set of words
     * @return string representation of given set.
     */
    public static String buildWordsInSets(Set<List<Symbol>> set)
    {
        StringBuilder sb = new StringBuilder("{");
        for(List<Symbol> word : set)
        {
            sb.append(buildWord(word));
            sb.append(",");
        }
        sb.append("}");
        
        String output = sb.toString();
        
        return output.substring(0, output.lastIndexOf(","))+"}";
    }
    
}
