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
import jgaf.IA006.grammar.LLEpsilon;
import jgaf.IA006.grammar.LLGrammar;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class FirstAndFollow implements FirstAndFollowI
{

    @Override
    public List<LLSymbol> kLengthPrefix(List<LLSymbol> word,int k) throws IllegalArgumentException
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
            LLSymbol s = new LLEpsilon();
            return Arrays.asList(s);
        }
        
        List<LLSymbol> result = new ArrayList<>();
        
        if(isMadeOutOfEpsilons(word))
        {
            LLSymbol s = new LLEpsilon();
            return Arrays.asList(s);
        }
        else
        {
            checkForNonTerminal(word);
            
            
            for(LLSymbol s : word)
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
    public List<LLSymbol> concatenateWordWithPrefix(List<LLSymbol> word1, List<LLSymbol> word2, int k) 
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
            LLSymbol s = new LLEpsilon();
            return Arrays.asList(s);
        }
        
        word1 = kLengthPrefix(word1, k);
        word2 = kLengthPrefix(word2, k);
        
        List<LLSymbol> result = new ArrayList<>(word1);
        result.addAll(word2);
        
        result = kLengthPrefix(result, k);
        
        return result;
    }

    @Override
    public Set<List<LLSymbol>> concatenateSetsWithPrefix(Set<List<LLSymbol>> set1, Set<List<LLSymbol>> set2, int k) throws IllegalArgumentException
    {
        if(k < 0)
        {
            throw new IllegalArgumentException("ERROR: k is lower than 0");
        }
        if(Tools.isEmpty(set1) || Tools.isEmpty(set2))
        {
            return new HashSet<>();
        }
        Set<List<LLSymbol>> result = new HashSet<>();
        
        
        for(List<LLSymbol> word1 : set1)
        {
            for(List<LLSymbol> word2 : set2)
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
    public static void checkForNonTerminal(List<LLSymbol> list)
    {
        int i = 0;
        for(LLSymbol s : list)
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
    private boolean isWord(List<LLSymbol> list)
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
    private boolean isMadeOutOfEpsilons(List<LLSymbol> word)
    {
        checkForNonTerminal(word);
        for(LLSymbol s : word)
        {
            if(!s.isEpsilon())
            {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public Map<LLSymbol, Set<List<LLSymbol>>> first(LLGrammar g,int k) throws IllegalArgumentException 
    {
        if(g == null)
        {
            throw new IllegalArgumentException("ERROR: given grammar is null");
        }
        if(k < 0)
        {
            throw new IllegalArgumentException("ERROR: k is lower than 0");
        }
        
        Map<LLSymbol,Set<List<LLSymbol>>> current = new HashMap<>();
        Map<LLSymbol,Set<List<LLSymbol>>> previous = new HashMap<>();
        
        //pozreme sa ci gramatika nieco generuje
        // teda pre kazde pravidlo A-> PQRSTW, sa pozrieme ci P-W su neterminaly, alebo epsilon,
        // a nasledne zredukujeme na dlzku k, presne podla rovnice napr pre
        // A-> bCd je 
        // FI3(A) = FI3(b) +_3 FI3(C) +_3 FI3(d)
        // FI3(A) = {b} +_3 FI3(C) +_3 {d}
        for(LLSymbol key : g.getProductionRules().keySet())
        {
            Set<List<LLSymbol>> temp = new HashSet<>();
            for(List<LLSymbol> rRside : g.getProductionRules().get(key))
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
        for(LLSymbol lSide : current.keySet())
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
            
            for(LLSymbol key : g.getProductionRules().keySet())
            {
                Set<List<LLSymbol>> temp = new HashSet<>();
                for(List<LLSymbol> rSide : g.getProductionRules().get(key))
                {
                    Set<List<LLSymbol>> temp2 = new HashSet<>(); // spracovana cast
                    LLSymbol eps = new LLEpsilon();
                    temp2.add(Arrays.asList(eps));
                    
                    for(LLSymbol s : rSide)
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
    private Set<List<LLSymbol>> firstKHelp(LLSymbol toAdd,Set<List<LLSymbol>> done, Map<LLSymbol,Set<List<LLSymbol>>> previous, int k)
    {
        Set<List<LLSymbol>> result = new HashSet<>();
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
    public Set<List<LLSymbol>> first(List<LLSymbol> input,LLGrammar g,int k) throws IllegalArgumentException 
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
       Map<LLSymbol,Set<List<LLSymbol>>> fiSet = first(g, k);
       Set<List<LLSymbol>> result = new HashSet<>();
       LLSymbol eps = new LLEpsilon();
       
       result.add(Arrays.asList(eps));
       
       for(LLSymbol s : input)
       {
           if(s.isNonterminal())
           {
               result = concatenateSetsWithPrefix(result, fiSet.get(s), k);
           }
           else
           {
               Set<List<LLSymbol>> temp = new HashSet<>();
               temp.add(Arrays.asList(s));
               result = concatenateSetsWithPrefix(result, temp, k);
           }
       }
       
       
       
       
       return result;
    }

    @Override
    public Map<LLSymbol, Set<List<LLSymbol>>> follow(LLGrammar g,int k) throws IllegalArgumentException 
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
        Map<LLSymbol, List<Set<List<LLSymbol>>>> previous = new LinkedHashMap<>();
        Map<LLSymbol, List<Set<List<LLSymbol>>>> actualMap = new LinkedHashMap<>();

        //nacteme si z gramatiky pravidla a pocatecni symbol a vsechny neterminaly
        Map<LLSymbol, Set<List<LLSymbol>>> rules = g.getProductionRules();
        LLSymbol start = g.getRootSymbol();
        Set<LLSymbol> nonTerminals = rules.keySet();

        //inicializace - pocatecni naplneni mapy
        Set<List<LLSymbol>> helpSetEmpty = new HashSet<>();
        List<LLSymbol> arrEps = new ArrayList<>();
        LLSymbol eps = new LLEpsilon();
        Set<List<LLSymbol>> helpSetEps = new HashSet<>();
        
        arrEps.add(eps);
        helpSetEps.add(arrEps);

        for (LLSymbol actNonTerm : nonTerminals) 
        {
            List<Set<List<LLSymbol>>> helpArr1 = new ArrayList<>();
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
                for (LLSymbol actNonTerm : nonTerminals) 
                {
                    //pomoci metody findRulesForFollow najdeme pravidla, ktera
                    //obsahuji dany neterminal na prave strane pravidla.
                    Map<LLSymbol, Set<List<LLSymbol>>> rulesWhereIsActNonTerm =  findRulesForFollow(actNonTerm, rules);
                    //nemusim se bat ze by tam neexistovalo zadne pravidlo,
                    //protoze je to redukovana gramatika
                    //v nasledujicim cyklu pocitame pro postupne pro vsechny
                    //nalezene pravidla
                    for (Map.Entry<LLSymbol, Set<List<LLSymbol>>> actCouple : rulesWhereIsActNonTerm.entrySet()) 
                    {
                        Set<List<LLSymbol>> actSetOfRules = actCouple.getValue();
                        //prochazim vsechna pravidla
                        for (List<LLSymbol> actRule : actSetOfRules) 
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
                                    List<Set<List<LLSymbol>>> helpArrSets =new ArrayList<>();
                                    helpArrSets.addAll(actualMap.get(actNonTerm));
                                    //zkontrolujeme zda se neprepise na eps
                                    if (nullable(j, actRule, g)) 
                                    {
                                        Set<List<LLSymbol>> followA = new HashSet<>();
                                        followA.addAll(actualMap.get(actNonTerm).get(i));
                                        Set<List<LLSymbol>> followB =new HashSet<>();
                                        followB.addAll(actualMap.get(actCouple.getKey()).get(i));

                                        followA.addAll(followB);
                                        helpArrSets.set(i, followA);
                                    } 
                                    //retezec neni prazdny ani se
                                    //na prazdny neprepise
                                    else 
                                    {
                                        Set<List<LLSymbol>> followA =  new HashSet<>();
                                        followA.addAll((actualMap.get(actNonTerm)).get(i));
                                        Set<List<LLSymbol>> followB = new HashSet<>();
                                        followB.addAll((actualMap.get(actCouple.getKey())).get(i - 1));
                                        
                                        List<LLSymbol> helpArr = new ArrayList<>();

                                        helpArr.addAll(actRule.subList(j + 1, actRule.size()));
                                        //Set<List<Symbol>> firstBeta =first_k(helpArr, g, i);
                                        Set<List<LLSymbol>> firstBeta = first(helpArr, g, i);
                                        //odstranime z mnoziny pravidlo epsilon
                                        firstBeta.remove(arrEps);

                                        //Set<ArrayList<Symbol>> concat = concatenatingSets_k(firstBeta, followB, i);

                                        Set<List<LLSymbol>> concat = concatenateSetsWithPrefix(firstBeta, followB, i);
                                        
                                        Set<List<LLSymbol>> pomocnaMnozina = new HashSet<>();
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
       
        Map<LLSymbol,Set<List<LLSymbol>>> result = new HashMap<>();
        
        for(LLSymbol s : g.getNonTerminals())
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
    public Map<LLSymbol, Set<List<LLSymbol>>> findRulesForFollow(
            LLSymbol symb, Map<LLSymbol, Set<List<LLSymbol>>> rules) {

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

        Set<LLSymbol> nonTerminals = rules.keySet();
        Map<LLSymbol, Set<List<LLSymbol>>> helpMap = new LinkedHashMap<>();

        //postupne prochazime vsechny neterminaly
        for (LLSymbol actNonTerm : nonTerminals) 
        {
            Set<List<LLSymbol>> actualSet =new  HashSet<>();
            actualSet.addAll(rules.get(actNonTerm));
            Set<List<LLSymbol>> helpSet =new HashSet<>();

            //prochazime jednotliva pravidla a zjistujeme zda v nich je hledany symbol
            for (List<LLSymbol> actRule : actualSet) 
            {
                if (actRule.contains(symb)) 
                {
                    helpSet.add(actRule);
                }
            }
            if(helpMap.containsKey(actNonTerm))
            {
                Set<List<LLSymbol>> previousSet = helpMap.get(symb);
                helpSet.addAll(previousSet);
            }
            if(!helpSet.isEmpty())
            {
                helpMap.put(actNonTerm, helpSet);
            }
        }
        return helpMap;
    }
    
    public boolean nullable(int i, List<LLSymbol> rule, LLGrammar gramm) {
        if (i >= (rule.size() - 1)) {
            return true;
        }

        LLSymbol eps = new LLEpsilon();
        List<LLSymbol> helpArrEps = new ArrayList<>();
        helpArrEps.add(eps);

        List<LLSymbol> helpArr = new ArrayList<>();

        helpArr.addAll(rule.subList(i + 1, rule.size()));

        Set<List<LLSymbol>> first_1 = first(helpArr, gramm, 1);
        
        if (first_1.contains(helpArrEps)) 
        {
            return true;
        }
        return false;
    }

    @Override
    public Set<List<LLSymbol>> firstAlpha(
                                   List<LLSymbol> rule,
                                   Map<LLSymbol,Set<List<LLSymbol>>> firstSet,
                                   int k) {
        Set<List<LLSymbol>> result = new HashSet<>();
        
        
        if(rule.size() == 1 && rule.get(0).isEpsilon())
        {
            LLSymbol eps = new LLEpsilon();
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
            Set<List<LLSymbol>> temp = new HashSet<>(result);
            result.clear();
            if(rule.get(i).isTerminal())
            {
                Set<List<LLSymbol>> ttemp = new HashSet<>();
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
