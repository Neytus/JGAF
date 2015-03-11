/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jgaf.IA006.grammar.Epsilon;
import jgaf.IA006.grammar.Grammar;
import jgaf.IA006.grammar.Symbol;

/**
 *
 * @author Empt
 */
public class FirstAndFollow implements FirstAndFollowI
{

    @Override
    public List<Symbol> kLengthPrefix(List<Symbol> word,int k) throws IllegalArgumentException
    {
        if(k < 0)
        {
            throw new IllegalArgumentException();
        }
        if(Tools.isEmpty(word))
        {
            throw new IllegalArgumentException();
        }
        
        if(k == 0)
        {
            checkForNonTerminal(word);
            Symbol s = new Epsilon();
            return Arrays.asList(s);
        }
        
        List<Symbol> result = new ArrayList<>();
        
        if(isMadeOutOfEpsilons(word))
        {
            Symbol s = new Epsilon();
            return Arrays.asList(s);
        }
        else
        {
            checkForNonTerminal(word);
            
            
            for(Symbol s : word)
            {
                if(result.size() == k)
                {
                    break;
                }
                
                if(s.isTerminal())
                {
                    result.add(s);
                }
                // else is epsilon, but condition body would be empty, since we are ommiting
                // such occurences, also non terminals dont have to be checked due to 
                // checking b4 iteration
            }
        }
        return result;
    }

    @Override
    public List<Symbol> concatenateWordWithPrefix(List<Symbol> word1, List<Symbol> word2, int k) 
    {
        if(word1 == null || word1.isEmpty() || word2 == null || word2.isEmpty())
        {
            return new ArrayList<>();
        }
         
        if(k < 0 )
        {
            throw new IllegalArgumentException("CHYBA k je mensie nez 0");
        }
        if(k == 0)
        {
            checkForNonTerminal(word1);
            checkForNonTerminal(word2);
            Symbol s = new Epsilon();
            return Arrays.asList(s);
        }
        
        word1 = kLengthPrefix(word1, k);
        word2 = kLengthPrefix(word2, k);
        
        List<Symbol> result = new ArrayList<>(word1);
        result.addAll(word2);
        
        result = kLengthPrefix(result, k);
        
        return result;
    }

    @Override
    public Set<List<Symbol>> concatenateSetsWithPrefix(Set<List<Symbol>> set1, Set<List<Symbol>> set2, int k) throws IllegalArgumentException
    {
        if(k < 0)
        {
            throw new IllegalArgumentException("ERROR: k is lower than 0");
        }
        if(Tools.isEmpty(set1) || Tools.isEmpty(set2))
        {
            return new HashSet<>();
        }
        Set<List<Symbol>> result = new HashSet<>();
        
        
        for(List<Symbol> word1 : set1)
        {
            for(List<Symbol> word2 : set2)
            {
                result.add(concatenateWordWithPrefix(word1, word2, k));
            }
        }
        
        return result;
    }
    
    /**
     * Method checks if word contains nonterminal. After first NonTerminal Symbol is found exception with position is thrown.
     * @param list word to be checked
     */
    public static void checkForNonTerminal(List<Symbol> list)
    {
        int i = 0;
        for(Symbol s : list)
        {
            i++;
            if(s.isNonterminal())
            {
                throw new IllegalArgumentException("ERROR: Given input contains NonTerminal ["+s+"] at position ("+i+")");
            }            
        }
    }
    
    /**
     * Method checks whether input word is word.
     * @param list to be checked
     * @return false if input word contains any NonTerminal symbol, true otherwise.
     */
    private boolean isWord(List<Symbol> list)
    {
        try
        {
           checkForNonTerminal(list);
           return true;
        }
        catch(IllegalArgumentException iae)
        {
            return false;
        }
    }
    
    /**
     * Method checks if input word is made out of epsilon(s)
     * @param word to be checked
     * @return true if word is made out of epsilon(s), false otherwise
     */
    private boolean isMadeOutOfEpsilons(List<Symbol> word)
    {
        checkForNonTerminal(word);
        for(Symbol s : word)
        {
            if(!s.isEpsilon())
            {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public Map<Symbol, Set<List<Symbol>>> first(Grammar g,int k) throws IllegalArgumentException 
    {
        if(g == null)
        {
            throw new IllegalArgumentException("ERROR: given grammar is null");
        }
        if(k < 0)
        {
            throw new IllegalArgumentException("ERROR: k is lower than 0");
        }
        
        Map<Symbol,Set<List<Symbol>>> current = new HashMap<>();
        Map<Symbol,Set<List<Symbol>>> previous = new HashMap<>();
        
        //pozreme sa ci gramatika nieco generuje
        // teda pre kazde pravidlo A-> PQRSTW, sa pozrieme ci P-W su neterminaly, alebo epsilon,
        // a nasledne zredukujeme na dlzku k, presne podla rovnice napr pre
        // A-> bCd je 
        // FI3(A) = FI3(b) +_3 FI3(C) +_3 FI3(d)
        // FI3(A) = {b} +_3 FI3(C) +_3 {d}
        for(Symbol key : g.getProductionRules().keySet())
        {
            Set<List<Symbol>> temp = new HashSet<>();
            for(List<Symbol> rRside : g.getProductionRules().get(key))
            {
                if(isWord(rRside))
                {
                    temp.add(kLengthPrefix(rRside, k));
                }
            }
            current.put(key, temp);
        }
        
        // nemalo by nastat ale co keby
        if(current.isEmpty() || !current.keySet().contains(g.getRootSymbol()))
        {
            throw new IllegalArgumentException("ERROR: Grammar produces no language (empty one)");
        }
        
        boolean hasSomething = false;
        // urcite nastane a to vtedy ked neexistuje terminujuce kombinacia
        for(Symbol lSide : current.keySet())
        {
            if(!current.get(lSide).isEmpty())
            {
                hasSomething = true;
            }
        }
        
        if(!hasSomething)
        {
            throw new IllegalArgumentException("ERROR: Grammar produces no language (empty one)");
        }
        
        while(!current.equals(previous))
        {
            // epicka prasacina, ale priradenie cez = by iba prehodilo ukazatel
            // a je to imho lepsie nez to manualne kopirovat formi
            //previous = (Map<Symbol,Set<List<Symbol>>>) ((HashMap<Symbol,Set<List<Symbol>>>) current).clone();
            previous = new HashMap<>(current);
            
            for(Symbol key : g.getProductionRules().keySet())
            {
                Set<List<Symbol>> temp = new HashSet<>();
                for(List<Symbol> rSide : g.getProductionRules().get(key))
                {
                    Set<List<Symbol>> temp2 = new HashSet<>(); // spracovana cast
                    Symbol eps = new Epsilon();
                    temp2.add(Arrays.asList(eps));
                    
                    for(Symbol s : rSide)
                    {
                        temp2 = firstKHelp(s, temp2, previous, k);
                    }
                    
                    temp.addAll(temp2);
                }
                current.put(key, temp);
            }
        }
        
        return previous;
    }
    
    /**
     * Help method for first calculation
     * @param toAdd symbol we are adding to output
     * @param done part of word we have already processed
     * @param previous FIRST set from previous calculation
     * @param k size of lookahead set
     * @return List of words as result of current process
     */
    private Set<List<Symbol>> firstKHelp(Symbol toAdd,Set<List<Symbol>> done, Map<Symbol,Set<List<Symbol>>> previous, int k)
    {
        Set<List<Symbol>> result = new HashSet<>();
        if(toAdd.isEpsilon() || toAdd.isTerminal())
        {
            result.add(new ArrayList<>(Arrays.asList(toAdd)));
            return concatenateSetsWithPrefix(done, result, k);
        }
        else 
        {
            return concatenateSetsWithPrefix(done, previous.get(toAdd), k);
        }
    }

    @Override
    public Set<List<Symbol>> first(List<Symbol> input,Grammar g,int k) throws IllegalArgumentException 
    {
        if(input == null)
        {
            throw new IllegalArgumentException("Error: given input is null");
        }
        if(input.isEmpty())
        {
            throw new IllegalArgumentException("Error: given input word is empty");
        }
        if(g == null)
        {
            throw new IllegalArgumentException("Error: given input grammar is null");
        }
        if(k < 0)
        {
            throw new IllegalArgumentException("ERROR: given k is less than 0");
        }
       Map<Symbol,Set<List<Symbol>>> fiSet = first(g, k);
       Set<List<Symbol>> result = new HashSet<>();
       Symbol eps = new Epsilon();
       
       result.add(Arrays.asList(eps));
       
       for(Symbol s : input)
       {
           if(s.isNonterminal())
           {
               result = concatenateSetsWithPrefix(result, fiSet.get(s), k);
           }
           else
           {
               Set<List<Symbol>> temp = new HashSet<>();
               temp.add(Arrays.asList(s));
               result = concatenateSetsWithPrefix(result, temp, k);
           }
       }
       
       
       
       
       return result;
    }

    @Override
    public Map<Symbol, Set<List<Symbol>>> follow(Grammar g,int k) throws IllegalArgumentException 
    {
        if(g == null)
        {
            throw new IllegalArgumentException("Error: given grammar is null");
        }
        if(k < 0)
        {
            throw new IllegalArgumentException("Error: given k is less than 0");
        }
        //mapy pouzite pri induktivnim napocitavani
        Map<Symbol, List<Set<List<Symbol>>>> previous = new LinkedHashMap<>();
        Map<Symbol, List<Set<List<Symbol>>>> actualMap = new LinkedHashMap<>();

        //nacteme si z gramatiky pravidla a pocatecni symbol a vsechny neterminaly
        Map<Symbol, Set<List<Symbol>>> rules = g.getProductionRules();
        Symbol start = g.getRootSymbol();
        Set<Symbol> nonTerminals = rules.keySet();

        //inicializace - pocatecni naplneni mapy
        Set<List<Symbol>> helpSetEmpty = new HashSet<>();
        List<Symbol> arrEps = new ArrayList<>();
        Symbol eps = new Epsilon();
        Set<List<Symbol>> helpSetEps = new HashSet<>();
        
        arrEps.add(eps);
        helpSetEps.add(arrEps);

        for (Symbol actNonTerm : nonTerminals) 
        {
            List<Set<List<Symbol>>> helpArr1 = new ArrayList<>();
            if (actNonTerm.equals(start)) 
            {
                for (int i = 0; i <= k; i++) 
                {
                    helpArr1.add(helpSetEps);
                }
            } 
            else 
            {
                helpArr1.add(helpSetEps);
                for (int j = 0; j < k; j++) 
                {
                    helpArr1.add(helpSetEmpty);
                }
            }
            previous.put(actNonTerm, helpArr1);
        }

        //vypocet probiha tak dlouho, dokud si nejsou dve po sobe jdouci mapy rovny
        while (!previous.equals(actualMap)) 
        {
            //podminka je splnena pouze v prvni iteraci cyklu
            if (actualMap.isEmpty()) 
            {
                actualMap.putAll(previous);
            }

            //naplneni mnoziny mnouzinou spoctenou v predchozi iteraci
            previous.putAll(actualMap);
            //pocitame tak dlouho, dokud nedojdeme k mnozine FO_k
            for (int i = 1; i <= k; i++) {
                //mnozinu napocitavame pro vsechny neterminaly
                for (Symbol actNonTerm : nonTerminals) 
                {
                    //pomoci metody findRulesForFollow najdeme pravidla, ktera
                    //obsahuji dany neterminal na prave strane pravidla.
                    Map<Symbol, Set<List<Symbol>>> rulesWhereIsActNonTerm =  findRulesForFollow(actNonTerm, rules);
                    //nemusim se bat ze by tam neexistovalo zadne pravidlo,
                    //protoze je to redukovana gramatika
                    //v nasledujicim cyklu pocitame pro postupne pro vsechny
                    //nalezene pravidla
                    for (Map.Entry<Symbol, Set<List<Symbol>>> actCouple : rulesWhereIsActNonTerm.entrySet()) 
                    {
                        Set<List<Symbol>> actSetOfRules = actCouple.getValue();
                        //prochazim vsechna pravidla
                        for (List<Symbol> actRule : actSetOfRules) 
                        {
                            //musim pocitat pro vsechny vyskyty neterminalu
                            //v pravidle
                            for (int j = 0; j < actRule.size(); j++) 
                            {
                                //najdu vyskyt
                                if (actNonTerm.equals(actRule.get(j))) 
                                {
                                    //retezec za vyskytem je prazdny nebo
                                    //se v jednom kroce prepise na prazdny
                                    List<Set<List<Symbol>>> helpArrSets =new ArrayList<>();
                                    helpArrSets.addAll(actualMap.get(actNonTerm));
                                    //zkontrolujeme zda se neprepise na eps
                                    if (nullable(j, actRule, g)) 
                                    {
                                        Set<List<Symbol>> followA = new HashSet<>();
                                        followA.addAll(actualMap.get(actNonTerm).get(i));
                                        Set<List<Symbol>> followB =new HashSet<>();
                                        followB.addAll(actualMap.get(actCouple.getKey()).get(i));

                                        followA.addAll(followB);
                                        helpArrSets.set(i, followA);
                                    } 
                                    //retezec neni prazdny ani se
                                    //na prazdny neprepise
                                    else 
                                    {
                                        Set<List<Symbol>> followA =  new HashSet<>();
                                        followA.addAll((actualMap.get(actNonTerm)).get(i));
                                        Set<List<Symbol>> followB = new HashSet<>();
                                        followB.addAll((actualMap.get(actCouple.getKey())).get(i - 1));
                                        
                                        List<Symbol> helpArr = new ArrayList<>();

                                        helpArr.addAll(actRule.subList(j + 1, actRule.size()));
                                        //Set<List<Symbol>> firstBeta =first_k(helpArr, g, i);
                                        Set<List<Symbol>> firstBeta = first(helpArr, g, i);
                                        //odstranime z mnoziny pravidlo epsilon
                                        firstBeta.remove(arrEps);

                                        //Set<ArrayList<Symbol>> concat = concatenatingSets_k(firstBeta, followB, i);

                                        Set<List<Symbol>> concat = concatenateSetsWithPrefix(firstBeta, followB, i);
                                        
                                        Set<List<Symbol>> pomocnaMnozina = new HashSet<>();
                                        //naplnime jednu mnozinu
                                        pomocnaMnozina.addAll(followA);
                                        pomocnaMnozina.addAll(concat);
                                        //polozime danou mnozinu na spravne misto
                                        helpArrSets.set(i, pomocnaMnozina);
                                    }
                                    //naplnime mapu
                                    actualMap.put(actNonTerm, helpArrSets);
                                }
                            }
                        }
                    }
                }
            }
        }
       
        Map<Symbol,Set<List<Symbol>>> result = new HashMap<>();
        
        for(Symbol s : g.getNonTerminals())
        {
            result.put(s, actualMap.get(s).get(k));
        }
        
        return result;
    }

     /**
     * Pomocná metoda pro výpočet funkce FOLLOW, ktera hleda vsechna pravidla,
     * ve kterych se dany neterminal vyskytuje na prave strane pravidla
     * @param symb neterminal, pro ktery hledame pravidla
     * @param rules pravidla ktera prohledavame
     * @return mapu pravidel, ve kterych je obsazeny vstupni neterminal
     * @throws IllegalArgumentException pri spatnych vstupnich datech
     */
    public Map<Symbol, Set<List<Symbol>>> findRulesForFollow(
            Symbol symb, Map<Symbol, Set<List<Symbol>>> rules) {

        //kontroly vstupnich dat
        if(symb == null){
            throw new IllegalArgumentException("Chyba: pri vypoctu FOLLOW: "
                    + "Symbol je null");
        }
        if (!symb.isNonterminal()) {
            throw new IllegalArgumentException("Chyba: pri vypoctu FOLLOW: "
                    + "Symbol neni neterminal");
        }
        if(rules == null){
            throw new IllegalArgumentException("Chyba: pri vypoctu FOLLOW: "
                    + "Pravidla jsou null");
        }

        Set<Symbol> nonTerminals = rules.keySet();
        Map<Symbol, Set<List<Symbol>>> helpMap = new LinkedHashMap<>();

        //postupne prochazime vsechny neterminaly
        for (Symbol actNonTerm : nonTerminals) 
        {
            Set<List<Symbol>> actualSet =new  HashSet<>();
            actualSet.addAll(rules.get(actNonTerm));
            Set<List<Symbol>> helpSet =new HashSet<>();

            //prochazime jednotliva pravidla a zjistujeme zda v nich je hledany symbol
            for (List<Symbol> actRule : actualSet) 
            {
                if (actRule.contains(symb)) 
                {
                    helpSet.add(actRule);
                }
            }
            if(helpMap.containsKey(actNonTerm))
            {
                Set<List<Symbol>> previousSet = helpMap.get(symb);
                helpSet.addAll(previousSet);
            }
            if(!helpSet.isEmpty())
            {
                helpMap.put(actNonTerm, helpSet);
            }
        }
        return helpMap;
    }
    
    public boolean nullable(int i, List<Symbol> rule, Grammar gramm) {
        if (i >= (rule.size() - 1)) {
            return true;
        }

        Symbol eps = new Epsilon();
        List<Symbol> helpArrEps = new ArrayList<>();
        helpArrEps.add(eps);

        List<Symbol> helpArr = new ArrayList<>();

        helpArr.addAll(rule.subList(i + 1, rule.size()));

        Set<List<Symbol>> first_1 = first(helpArr, gramm, 1);
        
        if (first_1.contains(helpArrEps)) 
        {
            return true;
        }
        return false;
    }

    @Override
    public Set<List<Symbol>> firstAlpha(
                                   List<Symbol> rule,
                                   Map<Symbol,Set<List<Symbol>>> firstSet,
                                   int k) {
        Set<List<Symbol>> result = new HashSet<>();
        
        
        if(rule.size() == 1 && rule.get(0).isEpsilon())
        {
            Symbol eps = new Epsilon();
            result.add(Arrays.asList(eps));
            return result;
        }
        
        if(rule.get(0).isTerminal())
        {
            result.add(Arrays.asList(rule.get(0)));
        }
        else
        {
            result.addAll(firstSet.get(rule.get(0)));
        }
        
        for(int i = 1; i < rule.size(); i++)
        {
            Set<List<Symbol>> temp = new HashSet<>(result);
            result.clear();
            if(rule.get(i).isTerminal())
            {
                Set<List<Symbol>> ttemp = new HashSet<>();
                ttemp.add(Arrays.asList(rule.get(i)));
                result.addAll(concatenateSetsWithPrefix(temp, ttemp, k));
            }
            else
            {
                result.addAll(concatenateSetsWithPrefix(temp, firstSet.get(rule.get(i)), k));
            }
        }
        
        
        return result;
    }

}
