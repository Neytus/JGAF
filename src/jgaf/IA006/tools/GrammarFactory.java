/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jgaf.Constants.MathConstants;
import jgaf.IA006.grammar.LLEpsilon;
import jgaf.IA006.grammar.LLGrammar;
import jgaf.IA006.grammar.NonTerminal;
import jgaf.IA006.grammar.LLSymbol;
import jgaf.IA006.grammar.LLTerminal;

/**
 *
 * @author Empt
 */
public class GrammarFactory 
{

    
    public static LLGrammar generateFromString(String s)
    {
        System.out.println(s);
        LLGrammar g = new LLGrammar();
        Set<LLSymbol> nonTerminals = new HashSet<>();
        Set<LLSymbol> terminals = new HashSet<>();
        Map<LLSymbol,Set<List<LLSymbol>>> productionRules = new HashMap<>();
        
        
        String pattern = "(?<=\\{)(.*?)(?=\\})"; // vrati cokolvek v { }
        
        
        Pattern p = Pattern.compile(pattern);
        
        Matcher m = p.matcher(s);
        int i = 0;
        while(m.find())
        {
            switch(i)
            {   // prvy prechod root
                case 0:
                    LLSymbol root = new NonTerminal(extractFromApostrophe(m.group()));
                    g.setRootSymbol(root);
                    i++;
                    break;
                case 1:     
                    // druhy prechod neterminaly nesmu byt praznde
                    String[] nonTerminalsTemp = m.group().split(",");
                    for(int j = 0; j < nonTerminalsTemp.length; j++)
                    {
                        nonTerminals.add(new NonTerminal(extractFromApostrophe(nonTerminalsTemp[j])));
                    }
                    g.setNonTerminals(nonTerminals);
                    i++;
                    break;
                case 2:  
                    // treti prechod 
                    String[] terminalsTemp = m.group().split(",");
                    // sigma moze byt prazdna  aby mohla existovat 
                    // G=({S},\emptyset,P,S) kde P= { (S,ε) }
                    if(Tools.stringIsEmpty(terminalsTemp[0]))
                    {
                        
                    }
                    else
                    {
                        for(int j = 0; j < terminalsTemp.length ; j++)
                        {
                            terminals.add(new LLTerminal(extractFromApostrophe(terminalsTemp[j])));
                        }                        
                    }
                    
                    g.setTerminals(terminals);
                    i++;
                    break;
                case 3:    
                    // stvrty prechod su pravidla
                    String[] rulez = m.group().split(",");  // rozdeli pravidla podla ,
                    for(int j = 0 ; j < rulez.length ; j++)
                    {
                        //String line = rulez[j];
                        String[] split = rulez[j].replace(" ", "").split("\\:",2); //rozdeli na pravu a lavu stranu a odstrani medzery
                        if(split.length == 0 || Tools.stringIsEmpty(split[0]))
                        {
                            throw new IllegalArgumentException("ERROR: Given grammar has no rules choose another one");
                        }
                        LLSymbol key = new NonTerminal(split[0]);
                        if(!nonTerminals.contains(key))
                        {
                            throw new IllegalArgumentException("ERROR: left side of rule contains unknown NonTerminal symbol - ["+key+"]");
                        }
                        else
                        {   // uz sme nieco pridali pre danu lavu stranu pravidla
                            if(productionRules.containsKey(key))
                            {
                                Set<List<LLSymbol>> temp = new HashSet<>();
                                temp.addAll(productionRules.get(key));
                                temp.add(tokenize(terminals, nonTerminals, split[1],true));
                                // vlozime
                                productionRules.put(key, temp);
                            }
                            else
                            {
                                // este sme nepridali
                                Set<List<LLSymbol>> temp = new HashSet<>();
                                temp.add(tokenize(terminals, nonTerminals, split[1],true));
                                // vlozime
                                productionRules.put(key, temp);                                
                            }
                        }
                    }
                    i++;
                    break;
            }            
        }
        g.setProductionRules(productionRules);
        
        return g;
    }
    
    /**
     * Method returns substring written in apostrophes. Implementation returns only first occurence. So
     * if we call method with input 'aa''bb' the output will be aa
     * @param s from which we want to obtain substring written in pair of apostrophes
     * @return substring written in apostrophes
     */
    private static String extractFromApostrophe(String s)
    {
        String pattern1 = "(?<=\\')(.*?)(?=\\')"; // vrati cokolvek v ' '
        Pattern p = Pattern.compile(pattern1);
        Matcher m = p.matcher(s);
        m.find();
        try
        {
            return m.group();
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException(e.getMessage()+ " when extracting symbol out of apostrophes. Is grammar in correct form ?");
        }
    }
    
    
    /**
     * Method serves as 'lexer'.
     * @param terminals set of terminal symbols
     * @param nonterminals set of nonterminals symbol
     * @param rightSide right side of rule
     * @param sort sort terminals and nonterminals
     * @return right side of rule in form of symbol representation
     */
    public static List<LLSymbol> tokenize(Set<LLSymbol> terminals, Set<LLSymbol> nonterminals, String rightSide,boolean sort)
    {
        // zlucime terminaly a neterminaly aby netrebalo 2x for cyklus
        List<LLSymbol> possibilities = new ArrayList<>();
        possibilities.addAll(terminals);
        possibilities.addAll(nonterminals);
        if(sort)
        {   //zoradi moznosti od najdlhsieho po najkratsie
            //dovodom je ze algoritmus matchuje text na zaklade zhody prefixu
            //problem vsak moze nastat napriklad pri pravidlach ako je 
            // Bool -> false,
            // Letter-> f | a | b | l | c | s | e
            // ak by sme nematchovali najdlhsi prefix 
            // tak by algoritmus mohol vyhodnotit false ako
            // postupnost Terminalov reprezentujucich pismeno
            // T(f)T(a)T(l)T(s)T(e) miesto
            // T(false)
            Collections.sort(possibilities, comparator);
        }
        
        
        //System.out.println(possibilities);
        List<LLSymbol> rs = new ArrayList<>(); // result
        String remainder = rightSide;
        boolean flag = false;
        boolean done = false;
        while(!done)
        {   // iterujeme od najldhsieho po najkratsi
            for(LLSymbol s : possibilities)
            {   // retazec zacina T / NT.
                // z definicie je zarucene ze je to bude T alebo NT, pretoze T \cap NT = \emptyset
                if(remainder.isEmpty())
                {   // nech sa zbytocn neiteruje ked sme precitali
                    break;
                }
                if(remainder.startsWith(s.getValue()))
                {
                    rs.add(s);
                    remainder = remainder.substring(s.getValue().length()); // odstranime prefix ktory sme uz pouzili                    
                    flag = true;
                }                
            }
            // mohol to byt este epsilon
            if(!flag && rightSide.equals(MathConstants.EPSILON))
            {
                LLSymbol e = new LLEpsilon();
                rs.add(e);
                flag = true;
                // pretoze moze byt iba A-> \eps, pravidlo typu A-> a\eps nema zmysel !
                remainder = "";
            }
            // presli sme cez vsetyk symboly, ale ziaden sa nematchol
            // takze pravidlo obsahuje nejaky retazec, ktory nie je 
            // podmnozinou terminalov ani neterminalov
            if(!flag)
            {
                throw new IllegalArgumentException("ERROR: no match for input "+remainder);
            }
            if(remainder.isEmpty())
            {
                done = true;
            }
            flag = false;
        }
        
        
        return rs;
    }
    
    /**
     * Comparator for symbols by their length
     */
    private static Comparator<LLSymbol> comparator = new Comparator<LLSymbol>() 
    {
        @Override
        public int compare(LLSymbol o1,LLSymbol o2) 
        {
            return o2.getValue().length()-o1.getValue().length();
        }
    };
    
    
    
    public static LLGrammar generateGrammarFromFileLines(List<String> list)
    {
        StringBuilder sb = new StringBuilder();
        for(String s : list)
        {
            sb.append(s); // nevkladat novy riadok lebo nepojdu RE
        }
        
        return generateFromString(sb.toString());
    }
}
