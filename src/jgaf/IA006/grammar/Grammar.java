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
public class Grammar 
{
    private String grammarName; // defaultne sa gramatika vola G
    private Set<Symbol> terminals;
    private Set<Symbol> nonTerminals;
    private Symbol rootSymbol;
    private Map<Symbol, Set<List<Symbol>>> productionRules;

    public Grammar(String grammarName,
                   Set<Symbol> terminals,
                   Set<Symbol> nonTerminals,
                   Symbol rootSymbol,
                   Map<Symbol, Set<List<Symbol>>> productionRules) {
        this.grammarName = grammarName;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.rootSymbol = rootSymbol;
        this.productionRules = productionRules;
    }
    
    public Grammar()
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

    public void setTerminals(Set<Symbol> terminals) {
        this.terminals = terminals;
    }

    public void setNonTerminals(Set<Symbol> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public void setRootSymbol(Symbol rootSymbol) {
        this.rootSymbol = rootSymbol;
    }

    public void setProductionRules(Map<Symbol, Set<List<Symbol>>> productionRules) {
        this.productionRules = productionRules;
    }
    
   

    public String getGrammarName() {
        return grammarName;
    }

    public Set<Symbol> getTerminals() {
        return terminals;
    }

    public Set<Symbol> getNonTerminals() {
        return nonTerminals;
    }

    public Symbol getRootSymbol() {
        return rootSymbol;
    }

    public Map<Symbol, Set<List<Symbol>>> getProductionRules() {
        return productionRules;
    }
    
    
    
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(grammarName).append(" = (");
        sb.append("{");
        int i=0;
        for(Symbol s : nonTerminals)
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
        for(Symbol s : terminals)
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
        
        for(Symbol s : productionRules.keySet())
        {
            sb.append("  ").append(s).append(" -> ");
            for(List<Symbol> l : productionRules.get(s))
            {
                for(Symbol ss : l)
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
