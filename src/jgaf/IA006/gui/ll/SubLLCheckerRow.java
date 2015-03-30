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
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class SubLLCheckerRow 
{
    private LLSymbol A;
    private List<LLSymbol> alpha;
    private Set<List<LLSymbol>> fiLSet;
    
    private Map<Integer,Map<LLSymbol,Set<List<LLSymbol>>>> follows = new HashMap<>();

    public LLSymbol getA() {
        return A;
    }

    public void setA(LLSymbol A) {
        this.A = A;
    }

    public List<LLSymbol> getAlpha() {
        return alpha;
    }

    public void setAlpha(
                         List<LLSymbol> alpha) {
        this.alpha = alpha;
    }

    public Set<List<LLSymbol>> getFiLSet() {
        return fiLSet;
    }

    public void setFiLSet(
                          Set<List<LLSymbol>> fiLSet) {
        this.fiLSet = fiLSet;
    }

    public Map<Integer, Map<LLSymbol, Set<List<LLSymbol>>>> getFollows() {
        return follows;
    }

    public void setFollows(
                           Map<Integer, Map<LLSymbol, Set<List<LLSymbol>>>> follows) {
        this.follows = follows;
    }

    
    
    public void calc(Map<LLSymbol,Set<List<LLSymbol>>> firstSet,
                     FirstAndFollowI faf,int k,Set<List<LLSymbol>> L)
    {
        for(int i = 0; i < alpha.size();i++)
        {
            if(alpha.get(i).isNonterminal())
            {
//                Set<List<Symbol>> temp = new HashSet<>();
//                temp.add(alpha.subList(i, alpha.size()));
                Set<List<LLSymbol>> result = new HashSet<>();
               
                if(i == alpha.size()-1)
                {
                    result.addAll(L);
                }
                else
                {
                    List<LLSymbol> temp = alpha.subList(i+1, alpha.size());
                    
                    result.addAll(faf.concatenateSetsWithPrefix(faf.firstAlpha(temp, firstSet, k), L, k));
                }
                
                Map<LLSymbol,Set<List<LLSymbol>>> map = new HashMap<>();
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
