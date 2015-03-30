/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.grammar;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class servers as Grammar representation.
 * @author Empt
 */
public class LLGrammar 
{
    private String grammarName; // defaultne sa gramatika vola G
    private Set<LLSymbol> terminals;
    private Set<LLSymbol> nonTerminals;
    private LLSymbol rootSymbol;
    private Map<LLSymbol, Set<List<LLSymbol>>> productionRules;

    public LLGrammar(String grammarName,
                   Set<LLSymbol> terminals,
                   Set<LLSymbol> nonTerminals,
                   LLSymbol rootSymbol,
                   Map<LLSymbol, Set<List<LLSymbol>>> productionRules) {
        this.grammarName = grammarName;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.rootSymbol = rootSymbol;
        this.productionRules = productionRules;
    }
    
    public LLGrammar()
    {
        this.grammarName = "G";
        this.terminals = new HashSet<>();
        this.nonTerminals = new HashSet<>();
        this.rootSymbol = null;
        this.productionRules = new LinkedHashMap<>();
    }

    public void setGrammarName(String grammarName) {
        this.grammarName = grammarName;
    }

    public void setTerminals(Set<LLSymbol> terminals) {
        this.terminals = terminals;
    }

    public void setNonTerminals(Set<LLSymbol> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public void setRootSymbol(LLSymbol rootSymbol) {
        this.rootSymbol = rootSymbol;
    }

    public void setProductionRules(Map<LLSymbol, Set<List<LLSymbol>>> productionRules) {
        this.productionRules = productionRules;
    }
    
   

    public String getGrammarName() {
        return grammarName;
    }

    public Set<LLSymbol> getTerminals() {
        return terminals;
    }

    public Set<LLSymbol> getNonTerminals() {
        return nonTerminals;
    }

    public LLSymbol getRootSymbol() {
        return rootSymbol;
    }

    public Map<LLSymbol, Set<List<LLSymbol>>> getProductionRules() {
        return productionRules;
    }
    
    
    
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(grammarName).append(" = (");
        sb.append("{");
        int i=0;
        for(LLSymbol s : nonTerminals)
        {
             sb.append(s);       
             if(i < nonTerminals.size()-1)
             {
                 sb.append(",");
             }
             i++;
        }
        sb.append("},");
        i=0;
        sb.append("{");
        for(LLSymbol s : terminals)
        {
             sb.append(s);       
             if(i < terminals.size()-1)
             {
                 sb.append(",");
             }
             i++;
        }
        i=0;
        sb.append("},P,").append(rootSymbol).append(")\n");
        sb.append("P={\n");
        
        for(LLSymbol s : productionRules.keySet())
        {
            sb.append("  ").append(s).append(" -> ");
            for(List<LLSymbol> l : productionRules.get(s))
            {
                for(LLSymbol ss : l)
                {
                    sb.append(ss);
                }
                i++;
                if(i < productionRules.get(s).size())
                {
                    sb.append(" | ");
                }                
            }
            sb.append(", \n");
            i = 0;
        }
        sb.append("}.");
        
        return sb.toString();
    }
    
}
