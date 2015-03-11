/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jgaf.IA006.tools.FirstAndFollowI;
import jgaf.IA006.grammar.Symbol;

/**
 *
 * @author Empt
 */
public class SubLLCheckerRow 
{
    private Symbol A;
    private List<Symbol> alpha;
    private Set<List<Symbol>> fiLSet;
    
    private Map<Integer,Map<Symbol,Set<List<Symbol>>>> follows = new HashMap<>();

    public Symbol getA() {
        return A;
    }

    public void setA(Symbol A) {
        this.A = A;
    }

    public List<Symbol> getAlpha() {
        return alpha;
    }

    public void setAlpha(
                         List<Symbol> alpha) {
        this.alpha = alpha;
    }

    public Set<List<Symbol>> getFiLSet() {
        return fiLSet;
    }

    public void setFiLSet(
                          Set<List<Symbol>> fiLSet) {
        this.fiLSet = fiLSet;
    }

    public Map<Integer, Map<Symbol, Set<List<Symbol>>>> getFollows() {
        return follows;
    }

    public void setFollows(
                           Map<Integer, Map<Symbol, Set<List<Symbol>>>> follows) {
        this.follows = follows;
    }

    
    
    public void calc(Map<Symbol,Set<List<Symbol>>> firstSet,
                     FirstAndFollowI faf,int k,Set<List<Symbol>> L)
    {
        for(int i = 0; i < alpha.size();i++)
        {
            if(alpha.get(i).isNonterminal())
            {
//                Set<List<Symbol>> temp = new HashSet<>();
//                temp.add(alpha.subList(i, alpha.size()));
                Set<List<Symbol>> result = new HashSet<>();
               
                if(i == alpha.size()-1)
                {
                    result.addAll(L);
                }
                else
                {
                    List<Symbol> temp = alpha.subList(i+1, alpha.size());
                    
                    result.addAll(faf.concatenateSetsWithPrefix(faf.firstAlpha(temp, firstSet, k), L, k));
                }
                
                Map<Symbol,Set<List<Symbol>>> map = new HashMap<>();
                map.put(alpha.get(i), result);
                
                follows.put(follows.size()+1, map);
            }
        }
        
    }

    @Override
    public String toString() {
        return "SubLLCheckerRow{" + "A=" + A + ", alpha=" + alpha + ", fiLSet=" + fiLSet + ", follows=" + follows + '}';
    }
    
    
}
