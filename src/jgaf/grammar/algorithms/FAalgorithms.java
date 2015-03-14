/**
 * Trida, ktera upravuje gramatiky podle algoritmu popsanych v textu prace
 * @author Radek Gomola
 * @version 1.0
 */
package jgaf.grammar.algorithms;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Constants.MathConstants;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;
/**
 *
 * @author LordDrake
 */
public class FAalgorithms {

    public Grammar eliminateUnreachableSymbols(Grammar gramm) {
        Grammar newGramm = new Grammar();
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules.putAll(gramm.getSameLeftSideMap());
        Symbol start = gramm.getStartNonterminal();

        Set<Symbol> set_previous = new HashSet<Symbol>();
        Set<Symbol> set_actual = new HashSet<Symbol>();
        //zaciname v pocatecnim symbolu
        set_previous.add(start);
        //pocitame tak dlouho dokud si nejsou dve po sobe jdouci mnoziny rovny
        while (!set_previous.equals(set_actual)) {
            //podminka je splnena pouze v prvni iteraci cyklu
            if (set_actual.isEmpty()) {
                set_actual.add(start);
            }
            //naplnime mnozinu mnozinou spoctenou v predchozi iteraci
            set_previous.addAll(set_actual);
            //pocitame pro vsechny symboly z predesle iterace
            for (Symbol symb : set_previous) {
                List<ProductionRuleSide> setOfRules =
                        new ArrayList<ProductionRuleSide>();
                ProductionRuleSide helpLeftNonTerminal = 
                                            new ProductionRuleSide();
                helpLeftNonTerminal.addSymbol(symb);
                List<ProductionRuleSide> helpSetOfRules = 
                                            rules.get(helpLeftNonTerminal);
                if(helpSetOfRules != null){
                    setOfRules.addAll(helpSetOfRules);

                    for (ProductionRuleSide oneRule : setOfRules) {
                        List<Symbol> oneRuleSymbols = new ArrayList<Symbol>();
                        oneRuleSymbols.addAll(oneRule.getSymbols());
                        for (Symbol s : oneRuleSymbols) {
                            if (s.isNonterminal()) {
                                set_actual.add(s);
                            }
                        }
                    }
                }else{
                    break;
                }   
            }
        }
        //kontrola zda bylo provedeno nejake odstraneni
        Set<ProductionRuleSide> helpList = rules.keySet();
        Set<Symbol> helpSet = new HashSet<Symbol>();
        for(ProductionRuleSide nonterm : helpList){
            helpSet.add(nonterm.getSymbols().get(0));
        }
        if (set_actual.equals(helpSet)) {
            newGramm = gramm;
            return null;
        } else {
            //gramatika byla upravovana - nastavime do nove  gramatiky
            newGramm.setStartNonterminal(start);
            //ziskani novych pravidel z puvodnich pravidel
            for (Symbol s : set_actual) {
                ProductionRuleSide oneSymb = new ProductionRuleSide();
                
                oneSymb.addSymbol(s);
                
                if(rules.containsKey(oneSymb)){
                    List<ProductionRuleSide> oneSetOfRules =
                                    rules.get(oneSymb);
                    for (ProductionRuleSide field : oneSetOfRules) {
                        ProductionRules newRule = 
                                new ProductionRules(oneSymb, 
                                            new ProductionRulesSide(field));
                        newGramm.addRule(newRule);
                    }
                }else{
                    continue;
                }
            }
            return newGramm;
        }
    }

    public Grammar eliminateUselessSymbols(Grammar gramm) 
                                        throws IllegalArgumentException{
        Grammar newGramm = new Grammar();

        SortedSet<Symbol> nonTerminals = new TreeSet<Symbol>();
        nonTerminals.addAll(gramm.getNonterminals());
        
        SortedSet<Symbol> nonTerminals_e = new TreeSet<Symbol>();
        SortedSet<Symbol> nonTerminals1 = new TreeSet<Symbol>(); //pro prunik
        SortedSet<Symbol> terminals1 = new TreeSet<Symbol>();
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules1 =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                gramm.getSameLeftSideMap();

        //je jazyk prazdny? tzn. nevytvori se zadny retezec terminalnich symbolu
        //znamena ze jazyk neni neprazdny a nema tedy smysl dal pokracovat
        nonTerminals_e = isLanguageNotEmpty(gramm);
        if(nonTerminals_e == null){
            throw new IllegalArgumentException("Chyba: Jazyk vytvoreny touto "
                    + "gramatikou je prazdny");
        }
        if(!nonTerminals.equals(nonTerminals_e)){
            //udelame prunik N a N_e - vysledek zjistovani
            for(Symbol symb : nonTerminals_e){
                if(nonTerminals.contains(symb)){
                    nonTerminals1.add(symb);
                }
            }

            //P1 = P /prunik (N_e x (N_e /sjednoceno %sigma)*)
            
            boolean control = true;

            for(Symbol symb : nonTerminals1){
                ProductionRuleSide helpLeftNonTerminal = 
                                                    new ProductionRuleSide();
                helpLeftNonTerminal.addSymbol(symb);
                if(rules.containsKey(helpLeftNonTerminal)){
                    List<ProductionRuleSide> oneSetOfRules =
                        rules.get(helpLeftNonTerminal);
                    List<ProductionRuleSide> newSetofRules =
                        new ArrayList<ProductionRuleSide>();

                    for (ProductionRuleSide field : oneSetOfRules) {
                        for (Symbol s : field.getSymbols()) {
                            //nesmime zapomenout ze pravidla jsou tvorena
                            //jak terminaly, tak neterminaly, tak i EPSILONEM
                            if (s.isTerminal() || nonTerminals1.contains(s) ||
                                    s.isEpsilon()) {
                                continue;
                            }else{
                                control = false;
                                break;
                            }
                        }
                        if(control == true){
                            newSetofRules.add(field);
                        }
                        control = true;
                    }
                    rules1.put(helpLeftNonTerminal, newSetofRules);
                }
            }           
        }else {
            nonTerminals1.addAll(gramm.getNonterminals());
            rules1.putAll(rules);
        }
        
        Grammar gramm1 = new Grammar();
        gramm1.setStartNonterminal(gramm.getStartNonterminal());
            
            //ziskani novych pravidel z puvodnich pravidel
            for (Symbol s : nonTerminals1) {
                ProductionRuleSide oneSymb = new ProductionRuleSide();
                    oneSymb.addSymbol(s);
                for(ProductionRuleSide oneOfRules : rules1.get(oneSymb)){
                    ProductionRulesSide rightHandSide = 
                                    new ProductionRulesSide(oneOfRules);
                    ProductionRules newRule = 
                                    new ProductionRules(oneSymb, rightHandSide);
                    gramm1.addRule(newRule);
                }
            }

        newGramm = eliminateUnreachableSymbols(gramm1);
        //spustime alg 3.2
        return newGramm;
    }

    public SortedSet<Symbol> isLanguageNotEmpty(Grammar gramm) {
       
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules.putAll(gramm.getSameLeftSideMap());
        Symbol start = gramm.getStartNonterminal();
        ProductionRuleSide startProductionRule = new ProductionRuleSide();
        startProductionRule.addSymbol(start);

        SortedSet<ProductionRuleSide> set_previous = 
                                        new TreeSet<ProductionRuleSide>();
        SortedSet<ProductionRuleSide> set_actual = 
                                        new TreeSet<ProductionRuleSide>();
        
        SortedSet<Symbol> terminals = new TreeSet<Symbol>();
        SortedSet<ProductionRuleSide> nonterminals = 
                                        new TreeSet<ProductionRuleSide>();

        terminals.addAll(gramm.getTerminals());
        nonterminals.addAll(rules.keySet());

        boolean control = false;
        int i=0;
        System.out.println("N_"+i+MathConstants.EMPTY_SET);
        do{
            i++;
            set_previous.clear();
            set_previous.addAll(set_actual);
            
            for(ProductionRuleSide leftRuleSide : nonterminals){
                List<ProductionRuleSide> setOfRules =
                      new ArrayList<ProductionRuleSide>();
                
                //ošetření, že neterminál není na levé straně, žádného pravidla
                //nemělo by nastat - beru množinu klíčů mapy
                if (rules.get(leftRuleSide) == null) {
                    continue;
                }
                setOfRules.addAll(rules.get(leftRuleSide));
            
               for (ProductionRuleSide oneRule : setOfRules) {

                        for (Symbol s : oneRule.getSymbols()) {

                            ProductionRuleSide testedSymbol = 
                                                    new ProductionRuleSide();
                            testedSymbol.addSymbol(s);
                            //sigma na hvezdicku obsahuje  i epsilon
                            if (terminals.contains(s)  
                                    || set_previous.contains(testedSymbol)
                                    || s.isEpsilon()) {
                                control = true;
                            }else {
                                control = false;
                                break;
                            }
                        }
                        if(control){
                            //vlozime symbol do mnoziny a vymazeme ho
                            //z aktualnich neterminalu
                            set_actual.add(leftRuleSide);
                            break;
                        }
               }
            }

        }while(!set_actual.equals(set_previous));
            
        SortedSet<Symbol> result = new TreeSet<Symbol>();
        if(set_actual.contains(startProductionRule)){
            for(ProductionRuleSide rule : set_actual){
                result.add(rule.getSymbols().get(0));
            }
            return result; 
        } else{
            return null;
        }
    }    
    
    public Grammar eliminateEpsProductions(Grammar gramm) 
                                                throws IllegalArgumentException{
        Grammar newGramm = new Grammar();
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules.putAll(gramm.getSameLeftSideMap());
        Symbol start = gramm.getStartNonterminal();

        SortedSet<Symbol> set_previous = new TreeSet<Symbol>();
        SortedSet<Symbol> set_actual = new TreeSet<Symbol>();
        
        SortedSet<Symbol> terminals = new TreeSet<Symbol>();
        SortedSet<Symbol> nonTerminals = new TreeSet<Symbol>();

        terminals.addAll(gramm.getTerminals());
        nonTerminals.addAll(gramm.getNonterminals());
       
        //cyklus pri kterem porovnavam mnoziny
        do {
            set_previous.clear();
            set_previous.addAll(set_actual);

            for (Symbol symb : nonTerminals){
               List<ProductionRuleSide> setOfRules =
                        new ArrayList<ProductionRuleSide>();
               
               //budeme potřebovat, abychom zkontrolovali jestli 
               //zřetězene pravodlo neni eps
               boolean control = false;
               //osetreno, ze symbol v neterminalech nahodou neni na leve
                //strane zadneho pravidla 
               //muzeme si dovolit je to CFG - na levé straně musí být jen 
               //jeden neterminál
                ProductionRuleSide oneSymb = new ProductionRuleSide();
                oneSymb.addSymbol(symb);
                if (rules.get(oneSymb) == null) {
                    //nonTerminals.remove(symb);
                } else {
                    setOfRules.addAll(rules.get(oneSymb));
                    for (ProductionRuleSide oneRule : setOfRules) {
                        for (Symbol s : oneRule.getSymbols()) {
                            //pravidlo je epsilon, tak rovnou vložíme
                            //tohle se provede jen v první iteraci while cyklu
                            if (s.isEpsilon()){
                                control = true;
                                break;
                            } 
                            //pravidlo obsahuje neterminál z předchozí množiny
                            //tak pokračujeme v hledání (nesmí obsahovat terminál
                            else if (set_previous.contains(s)) {
                                control = true;
                            } else {
                                control = false;
                                break;
                            }
                        }
                        //když je false tak to znamena, že nějaky symbol v pravidle
                        //se neprepise na esp a tím pádem se pravidlo nemuze prepsat na eps
                        if(control){
                            //vlozime symbol do mnoziny a 
                            //dál už pravidla tohohle neterminálu prohledávat nemusíme
                            //stačí nalézt jedno pravidlo pomocí, ktereho se prepise na eps,
                            //abychom pridali neterminal do N_eps
                            set_actual.add(symb);
                            break;
                        }
                    }
                }
            }
        }while (!set_actual.equals(set_previous));
        
        //pravidla P' po vymazani eps kroků
        Map<ProductionRuleSide, List<ProductionRuleSide>> newRules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        
        for(Map.Entry<ProductionRuleSide, 
                List<ProductionRuleSide>> rulesOfOneN : rules.entrySet()){
            //musím řešit, že už klíč obsahuje, ať se nepřemažou předchozí pravidla
            List<ProductionRuleSide> newRulesOfOneN = 
                                        new ArrayList<ProductionRuleSide>();

            //procházíme všechna pravidla jednoho neterminálu vlevo, vytvarime 
            // nova pravidla pomoci eliminateEpsHelp a pak je spojujeme s 
            //jiz vzniklými pravidly tak aby nevznikaly duplicity
            for(ProductionRuleSide rule : rulesOfOneN.getValue()){
                List<ProductionRuleSide> oldRules = 
                                        new ArrayList<ProductionRuleSide>();
                oldRules.addAll(newRulesOfOneN);
                newRulesOfOneN.clear();
                
            newRulesOfOneN.addAll(concateWithoutDuplicity(oldRules,
                                            eliminateEpsHelp(rule,set_actual)));
            }
            newRules.put(rulesOfOneN.getKey(), newRulesOfOneN);        
        }
        
        //Kdyz N_eps obsahuje pocatecni neterminal, tak vytvorime nove pravidlo 
        // S'->S|eps
        ProductionRuleSide newStartN = new ProductionRuleSide();
        Symbol newStart = new Symbol(start+"'", 1);
        if(set_actual.contains(start)){
            List<ProductionRuleSide> newStartRules = 
                    new ArrayList<ProductionRuleSide>();
            ProductionRuleSide eps = new ProductionRuleSide();
            ProductionRuleSide oldStart = new ProductionRuleSide();
            eps.addEpsilon(MathConstants.EPSILON);
            oldStart.addSymbol(start);
            newStartRules.add(eps);
            newStartRules.add(oldStart);
            newStartN.addSymbol(newStart);
            newRules.put(newStartN, newStartRules);
        }
        //pokud se mapa pravidel liší od puvodni mapy, tak budeme vracet true
        //a vytvoříme novou gramatiku s novými pravidly
        //množina terminálů a neterminálů by měla být stejná
        if(!rules.equals(newRules)){
            newGramm = addRuleToGrammFromMap(newRules);

            if(!newStartN.isEmpty()){
                newGramm.setStartNonterminal(newStart);
            }else{
                newGramm.setStartNonterminal(start);
            }
            return newGramm;
        }else{
            // znamená,že nebylo provedeno žádné odstrtanění (newGramm = gramm
            return null;
        }
    }
    
    public List<ProductionRuleSide> eliminateEpsHelp(ProductionRuleSide rule, 
                                                     SortedSet<Symbol> nonTermEps){
    
        List<ProductionRuleSide> newRulesSet = new ArrayList<ProductionRuleSide>();
        
        if(rule.isEpsilon()) return newRulesSet;        
        else{
            List<Symbol> oneRule = new ArrayList<Symbol>();
            oneRule.addAll(rule.getSymbols());
            boolean check = false;
            for(int i=0; i<oneRule.size();i++){

                Symbol testedSymb = oneRule.get(i);

                //symbol není v mnozine N_eps a pravidlo jeste nebylo pridano
                if(!nonTermEps.contains(testedSymb) && !check){
                    newRulesSet.add(rule);
                    check = true;
                }else if(nonTermEps.contains(testedSymb)){
                    newRulesSet.add(rule);
                    ProductionRuleSide shortedRuleSide = new ProductionRuleSide();
                    List<Symbol> shortedRule = new ArrayList<Symbol>();
                    shortedRule.addAll(oneRule);
                    shortedRule.remove(i);
                    shortedRuleSide.setSymbols(shortedRule);
                    newRulesSet = concateWithoutDuplicity(newRulesSet, 
                                  eliminateEpsHelp(shortedRuleSide, nonTermEps));
                    
                }else{
                    //jedině nějaká chyba - ještě bude potřeba promyslet
                }
            }
            return newRulesSet;
        }
    }
    
    public List<ProductionRuleSide> concateWithoutDuplicity(
                                        List<ProductionRuleSide> firstList,
                                        List<ProductionRuleSide> secondList){
        
        List<ProductionRuleSide> returnList = 
                                            new ArrayList<ProductionRuleSide>();
        returnList.addAll(firstList);
        for(ProductionRuleSide oneRule : secondList){
            if(!returnList.contains(oneRule)) returnList.add(oneRule);
        }
        return returnList;
    }
    
    public Grammar eliminateUnitRules(Grammar gramm) {
        Grammar newGramm = new Grammar();

        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules.putAll(gramm.getSameLeftSideMap());
        SortedSet<Symbol> nonTerminals = new TreeSet<Symbol>();
        nonTerminals = gramm.getNonterminals();
        
        
        for(Symbol oneNonterm : nonTerminals){
            
            ProductionRuleSide oneNontermSide = new ProductionRuleSide();
            oneNontermSide.addSymbol(oneNonterm);
            
            Set<Symbol> set_previous = new HashSet<Symbol>();
            Set<Symbol> set_actual = new HashSet<Symbol>();
            set_previous.add(oneNonterm);
            
            
            while (!set_previous.equals(set_actual)) {
                //podminka je splnena pouze v prvni iteraci cyklu
                if (set_actual.isEmpty()) {
                    set_actual.add(oneNonterm);
                }
                //naplnime mnozinu mnozinou spoctenou v predchozi iteraci
                set_previous.addAll(set_actual);
                //transitivni uzaver
                for (Symbol symb : set_previous) {
                    List<ProductionRuleSide> setOfRules =
                        new ArrayList<ProductionRuleSide>();
                    ProductionRuleSide helpLeftNonTerminal = 
                            new ProductionRuleSide();
                    helpLeftNonTerminal.addSymbol(symb);
                    List<ProductionRuleSide> helpSetOfRules = 
                            rules.get(helpLeftNonTerminal);
                    
                    //zkontrolujeme zda neni mnozina pravidel pro dany neterminal prazdna
                    if(helpSetOfRules != null){
                        setOfRules.addAll(helpSetOfRules);   
                        //kontrolujeme pravidla a hledame jednoduche pravidlo
                        for (ProductionRuleSide oneRule : setOfRules) {
                            Symbol probabAddSymb = oneRule.getSymbols().get(0);
                            if(oneRule.size() == 1 && probabAddSymb.isNonterminal()) {
                                set_actual.add(probabAddSymb);  
                            }
                        }
                    }else{
                        continue;
                    }
                }
            }
            //napocitavame novou mnozinu pravidel ze set_actual pro puvodni
            //neterminal ozn. oneNonterm (oneNontermSide)

            for (Symbol s : set_actual) {
                    ProductionRuleSide oneSymb = new ProductionRuleSide();
                    oneSymb.addSymbol(s);
                    List<ProductionRuleSide> helpRules = rules.get(oneSymb);
                    if(helpRules != null){
                        for(ProductionRuleSide oneOfRules : helpRules){
                            if(oneOfRules.size() != 1 
                               ||!oneOfRules.getSymbols().get(0).isNonterminal()) {
                            ProductionRules newRule = 
                                    new ProductionRules(oneNontermSide, 
                                           new ProductionRulesSide(oneOfRules));
                            newGramm.addRule(newRule);
                            }
                        }
                    }else{
                        continue;
                    }
            }
        }
        newGramm.setStartNonterminal(gramm.getStartNonterminal());
       
        if(!gramm.equals(newGramm)){
            return newGramm;
        }else{
            //gramatiky jsou stejné
            return null;
        }
        
    }
    
    public Grammar transformToCNF(Grammar gramm){
        Grammar newGramm = new Grammar();
        
        List<ProductionRules> rules = new ArrayList<ProductionRules>();
        SortedSet<ProductionRules> newRules = new TreeSet<ProductionRules>();
        SortedSet<Symbol> newNfromTerm = new TreeSet<Symbol>();
        ProductionRules newRule = new ProductionRules();
        ProductionRuleSide newRuleHelp = new ProductionRuleSide();
        
        rules.addAll(gramm.getProductionRules());
        Symbol start = gramm.getStartNonterminal();

        for (ProductionRules oneRule : rules) {
            
            ProductionRulesSide rightHandSide = new ProductionRulesSide();
            rightHandSide = oneRule.getRightHandSide();

            List<ProductionRuleSide> rightHandSideList = 
                                            new ArrayList<ProductionRuleSide>();
            rightHandSideList.addAll(rightHandSide.getRules());
            for(ProductionRuleSide rightHandSideOneRule : rightHandSideList){
                List<Symbol> testedRule = new ArrayList<Symbol>();
                testedRule.addAll(rightHandSideOneRule.getSymbols()); 

                if(gramm.isStartToEpsRule(oneRule.getLeftHandSide(),    
                                                         rightHandSideOneRule)){
                    newRules.add(oneRule);
                    continue;
                }
                if(testedRule.size() == 1 && testedRule.get(0).isTerminal()){
                    newRules.add(oneRule);
                    continue;
                }
                if(testedRule.size() == 2){
                    if(testedRule.get(0).isNonterminal() && 
                            testedRule.get(1).isNonterminal()){
                        newRules.add(oneRule);
                    }else {
                        Symbol x1 = testedRule.get(0);
                        Symbol x2 = testedRule.get(1);

                        Symbol y1 = new Symbol();
                        Symbol y2 = new Symbol();
                        if(x1.isTerminal()){
                            //způsob vytvareni mame dany, takze i kdyz pravidlo nepridame
                            //rovnou, tak i pozdeji budou dana pravidla stejna
                            newNfromTerm.add(x1);
                            y1.setName("<"+x1+">");
                            y1.setType(1);
                        }else{
                            y1 = x1;
                        } 
                        if(x2.isTerminal()){
                            newNfromTerm.add(x2);
                            y2.setName("<"+x2+">");
                            y2.setType(1);
                        }else{
                            y2 = x2;
                        }
                        //zajistíme, že je pravidlo vždy prazdne
                        newRule = new ProductionRules();
                        newRuleHelp = new ProductionRuleSide();
                        newRule.setLeftHandSide(oneRule.getLeftHandSide());
                        newRuleHelp.addSymbol(y1);
                        newRuleHelp.addSymbol(y2);
                        newRule.addToRightHandSide(newRuleHelp);
                        newRules.add(newRule);
                    }
                    continue;
                }
                if(testedRule.size()>2){
                    List<Symbol> helpRule = new ArrayList<Symbol>();
                    helpRule.addAll(testedRule);

                    ProductionRuleSide leftSideSymb = new ProductionRuleSide();
                    leftSideSymb = oneRule.getLeftHandSide();

                    //když upravujeme pravidlo tak to musíme vždy udělat (n-1)x, 
                    //protoze na konci vzdy musi zbyt pravidlo delky 2;
                    for(int i=0; i<(testedRule.size()-1); i++){
                        Symbol x1 = helpRule.get(0);
                        Symbol y1 = new Symbol();
                        Symbol y2 = new Symbol();

                        //využije se pro pravidlo delky 2
                        Symbol x2 = helpRule.get(1);


                        if(x1.isTerminal()){
                            newNfromTerm.add(x1);
                            y1.setName("<"+x1+">");
                            y1.setType(1);
                        }else{
                            y1 = x1;
                        } 
                        if(helpRule.size() == 2){
                            if(x2.isTerminal()){
                                newNfromTerm.add(x2);
                                y2.setName("<"+x2+">");
                                y2.setType(1);
                            }else{
                                y2 = x2;
                            }
                            //zajistíme, že je pravidlo vždy prazdne
                            newRule = new ProductionRules();
                            newRuleHelp = new ProductionRuleSide();
                            newRule.setLeftHandSide(leftSideSymb);
                            newRuleHelp.addSymbol(y1);
                            newRuleHelp.addSymbol(y2);
                            newRule.addToRightHandSide(newRuleHelp);
                            newRules.add(newRule);
                            break;
                        }
                        //vezmeme jen část od druheho symbolu do konce a s ni budeme
                        //pocitat v dalsi iteraci
                        helpRule.remove(0);
                        y2.setName("<"+nameFromList(helpRule)+">");
                        y2.setType(1);

                        newRule = new ProductionRules();
                        newRuleHelp = new ProductionRuleSide();
                        newRule.setLeftHandSide(leftSideSymb);
                        newRuleHelp.addSymbol(y1);
                        newRuleHelp.addSymbol(y2);
                        newRule.addToRightHandSide(newRuleHelp);

                        newRules.add(newRule);

                        //změníme symbol pro levou stranu
                        leftSideSymb = new ProductionRuleSide();
                        leftSideSymb.addSymbol(y2);
                    }
                }
            }
        }
        //teď vytvořime nová pravidla pro všechny terminaly, které jsme upravovali
        for(Symbol term : newNfromTerm){
            Symbol nonterm = new Symbol("<"+term.getName()+">",1);
            
            newRule = new ProductionRules();
            newRuleHelp = new ProductionRuleSide();
            newRule.addToLeftHandSide(nonterm);
            newRuleHelp.addSymbol(term);
            newRule.addToRightHandSide(newRuleHelp);
            
            newRules.add(newRule);
        }
        
        List<ProductionRules> newRulesList = new ArrayList<ProductionRules>();
        newRulesList.addAll(setToList(newRules));
        
        if(!rules.equals(newRules)){
            for(ProductionRules rule : newRules){
                newGramm.addRule(rule);
            }
            newGramm.setStartNonterminal(start);
            return newGramm;
        }else{
            //gramatika je stejná )newGramm = gramm);
            return null;
        }
    }
 
    public Grammar directLeftRecursionElimination(Grammar gramm, int type){
        Grammar newGramm = new Grammar();
        
        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        rules.putAll(gramm.getSameLeftSideMap());
        Symbol start = gramm.getStartNonterminal();
        Map<ProductionRuleSide, List<ProductionRuleSide>> newRules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        
        //cyklus na projití a úpravu všech pravidel
        for(Map.Entry<ProductionRuleSide, List<ProductionRuleSide>> 
                                                setOfRules : rules.entrySet()){
            newRules.putAll(directLeftRecursionElimination(
                            setOfRules.getKey(), setOfRules.getValue(), type));
        }
        if(!rules.equals(newRules)){
            newGramm = addRuleToGrammFromMap(newRules);
            newGramm.setStartNonterminal(start);
            return newGramm;
        }else {
            //neproběhla žadná změnanewGramm = gramm;
            return null;
        }
        
    }
   
    public Map<ProductionRuleSide, List<ProductionRuleSide>>
             directLeftRecursionElimination(ProductionRuleSide leftSide,
                                   List<ProductionRuleSide> rules, int type){
        //kontrola zda je type 1 nebo 2        
        Map<ProductionRuleSide, List<ProductionRuleSide>> newRules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
            
        boolean change = false;
        SortedSet<ProductionRuleSide> recursiveRules = 
                new TreeSet<ProductionRuleSide>();
        SortedSet<ProductionRuleSide> anotherRules = 
                new TreeSet<ProductionRuleSide>();


        //potřebujeme symbol na porovnávání s prvním pravidlem 
        ProductionRuleSide leftSideNonterm = new ProductionRuleSide();
        leftSideNonterm.setSymbolsFromProductionRuleSide(leftSide);
        Symbol leftSideSymb = leftSideNonterm.getSymbols().get(0);

        //procházím jednotlivá pravidla a rozděluji je na dva typy
        for(ProductionRuleSide oneRule : rules){
            List<Symbol> listRule = new ArrayList<Symbol>();
            listRule.addAll(oneRule.getSymbols());

            ProductionRuleSide ruleWithoutRecursion = new ProductionRuleSide();
            //máme levou rekurzi
            if(listRule.get(0).equals(leftSideSymb)){
                ruleWithoutRecursion.setSymbolsFromProductionRuleSide(oneRule);
                ruleWithoutRecursion.removeSymbolOnPosition(0);
                recursiveRules.add(ruleWithoutRecursion);
                change = true;
            }else{
                anotherRules.add(oneRule);
            }
        }

        //teď budeme přidávat pravidla - nejdříve vezmeme ty, u kterých zůstane 
        //na levé straně původní neterminál
        Symbol newLeftSideSymb = new Symbol(leftSideSymb.getName()+"'",1);
        ProductionRuleSide newLeftNonterm = new ProductionRuleSide();
        newLeftNonterm.addSymbol(newLeftSideSymb);

        List<ProductionRuleSide> newRulesListAnotherRules = 
                new ArrayList<ProductionRuleSide>();
        List<ProductionRuleSide> newRulesListRecursiveRules = 
                new ArrayList<ProductionRuleSide>();

        //pomocné pravidlo pro změny v pravidlech
        ProductionRuleSide newRule = new ProductionRuleSide();
        if(change){
            if(type == 1){
                //při průchodu můžu bez problému pravidla vkládat přímo do gramatiky
                //ještě uvidím, co pro mě pak bude lepší
                for(ProductionRuleSide oneRule : anotherRules){
                    newRulesListAnotherRules.add(oneRule);
                    newRule = new ProductionRuleSide();
                    newRule.setSymbolsFromProductionRuleSide(oneRule);
                    //upravíme pravidlo a přidáme na jeho konec nový neterminál A'
                    //epsilon pravidlo může byt jen v teto mnozine (k eps nic nepřidavam)
                    if(!oneRule.isEpsilon()){                         
                        newRule.addSymbol(newLeftSideSymb);
                        newRulesListAnotherRules.add(newRule);
                    }
                }
                //přidáme pravidla do nové mapy
                newRules.put(leftSideNonterm, newRulesListAnotherRules);

                //přidáme tato pravidla
                //teď pridame vsechna ostatni pravidla
                for(ProductionRuleSide oneRule : recursiveRules){
                    newRulesListRecursiveRules.add(oneRule);
                    newRule = new ProductionRuleSide();
                    newRule.setSymbolsFromProductionRuleSide(oneRule);
                    //upravíme pravidlo a přidáme na jeho konec nový neterminál A'


                    newRule.addSymbol(newLeftSideSymb);
                    newRulesListRecursiveRules.add(newRule);
                }
                //přidáme pravidla do nové mapy
                newRules.put(newLeftNonterm, newRulesListRecursiveRules);
            }else if(type == 2){
                for(ProductionRuleSide oneRule : anotherRules){
                    newRule = new ProductionRuleSide();
                    newRule.setSymbolsFromProductionRuleSide(oneRule);
                    //epsilon pravidlo může byt jen v teto mnozine
                    if(!oneRule.isEpsilon()){
                        newRule.addSymbol(newLeftSideSymb); 
                    }
                    newRulesListAnotherRules.add(newRule);
                }
                newRules.put(leftSideNonterm, newRulesListAnotherRules);

                for(ProductionRuleSide oneRule : recursiveRules){
                    newRule = new ProductionRuleSide();
                    newRule.setSymbolsFromProductionRuleSide(oneRule);
                    newRule.addSymbol(newLeftSideSymb);
                    newRulesListRecursiveRules.add(newRule);
                }
                //musíme přidat eps pravidlo
                ProductionRuleSide eps = new ProductionRuleSide();
                eps.addEpsilon("eps");
                newRulesListRecursiveRules.add(eps);
                //přidáme pravidla do nové mapy
                newRules.put(newLeftNonterm, newRulesListRecursiveRules);
            }
        }else{
            newRules.put(leftSideNonterm, rules);
        }
        return newRules;        
    }
    
    public Grammar leftRecursionElimination(Grammar gramm, int type){
        Grammar newGramm = new Grammar();

        Map<ProductionRuleSide, List<ProductionRuleSide>> rules =
                new HashMap<ProductionRuleSide, List<ProductionRuleSide>>();
        
        rules.putAll(gramm.getSameLeftSideMap());

        List<ProductionRuleSide> listOfNonterms = 
                                            new ArrayList<ProductionRuleSide>();
        
        //potřebujeme libovolne usporadani - pouzijeme dané, jen musíme prenést 
        //pravidla z mapy do pole, ve kterém můžeme prohledávat jednotlivé bunky
        listOfNonterms.addAll(rules.keySet());

        Grammar helpGramm = new Grammar();
        for(int i = 0; i < listOfNonterms.size(); i++){
            List<ProductionRuleSide> allRules = 
                                            new ArrayList<ProductionRuleSide>();
            helpGramm = (Grammar) newGramm.clone();
            //máme kvůli přidávání nových pravidel
            List<ProductionRuleSide> newListOfRules = 
                                        new ArrayList<ProductionRuleSide>();
            //potrebujeme pravidlo A_i pro test
            ProductionRuleSide leftSide = new ProductionRuleSide();
            leftSide = listOfNonterms.get(i);

            if(i == 0){
                newListOfRules.addAll(rules.get(leftSide));
                allRules.addAll(newListOfRules); 
            }

          List<ProductionRuleSide> rulesToRemove = 
                                            new ArrayList<ProductionRuleSide>();
            for(int j = 0; j < i; j++){
                newListOfRules = new ArrayList<ProductionRuleSide>();               
                //potrebujeme pravidlo A_j pro test
                
                ProductionRuleSide nonTermForTest = listOfNonterms.get(j);
                
                Symbol nonTermForTestSymb = nonTermForTest.getSymbols().get(0);

                List<ProductionRuleSide> rulesOfLeftSide = 
                                        new ArrayList<ProductionRuleSide>();
                
                List<ProductionRuleSide> rulesOfnonTermForTest = 
                                        new ArrayList<ProductionRuleSide>();
                
                rulesOfLeftSide.addAll(rules.get(leftSide));
                rulesOfLeftSide.addAll(allRules);
                rulesOfLeftSide.removeAll(rulesToRemove);
                rulesOfnonTermForTest.addAll(rules.get(nonTermForTest));
                rulesToRemove = new ArrayList<ProductionRuleSide>();
                //testujeme jednotlivá pravidla
                for(ProductionRuleSide oneRule : rulesOfLeftSide){
                    List<Symbol> listRule = new ArrayList<Symbol>();
                    listRule.addAll(oneRule.getSymbols());
                    //pokud se symbol na první pozici pravidla rovná 
                    //neterminalu na pozici j, tak vytvorime nova pravidla
                    if(nonTermForTestSymb.equals(listRule.get(0))){

                        List<Symbol> leftListRule = new ArrayList<Symbol>();
                        leftListRule.addAll(listRule);
                        //potřebujeme \alfa část pravidla - první symbol vytváří rekurzi
                        leftListRule.remove(0);
                            
                        for(ProductionRuleSide rule : rulesOfnonTermForTest){
                            ProductionRuleSide newRuleS = new ProductionRuleSide();
                            newRuleS.setSymbolsFromProductionRuleSide(rule);
                            if(!newRuleS.equals(oneRule)){
                                newRuleS.addSymbolsFromList(leftListRule);
                                ProductionRules newRule = 
                                    new ProductionRules(leftSide, 
                                        new ProductionRulesSide(newRuleS));
//                                
                                newGramm.addRule(newRule);
                                newListOfRules.add(newRuleS);
                                if(allRules.contains(oneRule)) allRules.remove(oneRule);
                                rulesToRemove.add(oneRule);
                            }
                            
                        }
                    }else{
                        newListOfRules.add(oneRule);
                        continue;
                    } 
                    
                }

                allRules.addAll(newListOfRules);
            }
            //ted odstranime primou rekurzi pomocí funce directRecursionElimination
            //v první iteraci se nedostaneme do for cyklu a tedy nemáme žádná pravidla v
            //newListOfRules. Ve všech ostatních iteracích už se ta množina naplní
            //i v případě, že tam není žádná rekurze
            
            //ProductionRuleSide leftSide = new ProductionRuleSide();
            SortedSet<ProductionRuleSide> recursiveRules = 
                new TreeSet<ProductionRuleSide>();
            SortedSet<ProductionRuleSide> anotherRules = 
                new TreeSet<ProductionRuleSide>();

        //potřebujeme symbol na porovnávání s prvním pravidlem 
            Symbol leftSideSymb = leftSide.getSymbols().get(0);
            ProductionRules unit = new ProductionRules();
            boolean unitCheck = false;
        //procházím jednotlivá pravidla a rozděluji je na dva typy
             List<ProductionRuleSide> testedRules = newListOfRules;

            for(ProductionRuleSide oneRule : allRules){
                List<Symbol> listRule = new ArrayList<Symbol>();
                listRule.addAll(oneRule.getSymbols());
                
                ProductionRuleSide ruleWithoutRecursion = 
                                                    new ProductionRuleSide();
                //máme levou rekurzi
                if(listRule.get(0).equals(leftSideSymb)){
                    if(oneRule.size()!=1){
                        ruleWithoutRecursion.setSymbolsFromProductionRuleSide(oneRule);
                        ruleWithoutRecursion.removeSymbolOnPosition(0);
                        recursiveRules.add(ruleWithoutRecursion);
                    }else{
                        unit = 
                            new ProductionRules(leftSide, 
                                new ProductionRulesSide(oneRule));
                        unitCheck = true;
                    }
                }else{
                    anotherRules.add(oneRule);
                }
            }
            newGramm.removeAllRules();
            newGramm.addAllRules(helpGramm);
            
            
            //teď budeme přidávat pravidla - nejdříve vezmeme ty, u kterých zůstane 
            //na levé straně původní neterminál
            Symbol newLeftSideSymb = new Symbol(leftSideSymb.getName()+"'",1);
            ProductionRuleSide newLeftNonterm = new ProductionRuleSide();
            newLeftNonterm.addSymbol(newLeftSideSymb);

            //pomocné pravidlo pro změny v pravidlech
            ProductionRuleSide newRuleS = new ProductionRuleSide();
            if(!recursiveRules.isEmpty()){
                if(type == 1){
                    //při průchodu můžu bez problému pravidla vkládat přímo do gramatiky
                    //ještě uvidím, co pro mě pak bude lepší
                    //teď pridame vsechna ostatni pravidla
                    for(ProductionRuleSide oneRule : recursiveRules){
                        List<ProductionRuleSide> newRulesListRecursiveRules = 
                            new ArrayList<ProductionRuleSide>();
                        newRulesListRecursiveRules.add(oneRule);
                        newRuleS = new ProductionRuleSide();
                        newRuleS.setSymbolsFromProductionRuleSide(oneRule);
                        //upravíme pravidlo a přidáme na jeho konec nový neterminál A'


                        newRuleS.addSymbol(newLeftSideSymb);
                        newRulesListRecursiveRules.add(newRuleS);
                        ProductionRules newRule = 
                            new ProductionRules(newLeftNonterm, 
                              new ProductionRulesSide(newRulesListRecursiveRules));
                        newGramm.addRule(newRule);
                    }
                    //přidáme pravidla do nové mapy
                    
                    for(ProductionRuleSide oneRule : anotherRules){
                        List<ProductionRuleSide> newRulesListAnotherRules = 
                            new ArrayList<ProductionRuleSide>();
                        newRulesListAnotherRules.add(oneRule);
                        newRuleS = new ProductionRuleSide();
                        newRuleS.setSymbolsFromProductionRuleSide(oneRule);
                        //upravíme pravidlo a přidáme na jeho konec nový neterminál A'
                        //epsilon pravidlo může byt jen v teto mnozine (k eps nic nepřidavam)
                        if(!oneRule.isEpsilon()){                         
                            newRuleS.addSymbol(newLeftSideSymb);
                            newRulesListAnotherRules.add(newRuleS);
                        }
                        ProductionRules newRule = 
                               new ProductionRules(leftSide, 
                                new ProductionRulesSide(newRulesListAnotherRules));
                        newGramm.addRule(newRule);
                    }
                    //přidáme pravidla do nové gramatiky
                    
                    
                    
                }else if(type == 2){
                    for(ProductionRuleSide oneRule : recursiveRules){
                        List<ProductionRuleSide> newRulesListRecursiveRules = 
                            new ArrayList<ProductionRuleSide>();
                        newRuleS = new ProductionRuleSide();
                        newRuleS.setSymbolsFromProductionRuleSide(oneRule);
                        newRuleS.addSymbol(newLeftSideSymb);
                        newRulesListRecursiveRules.add(newRuleS);
                        
                        ProductionRules newRule = 
                            new ProductionRules(newLeftNonterm, 
                                new ProductionRulesSide(newRulesListRecursiveRules));
                        newGramm.addRule(newRule);
                    }
                    //musíme přidat eps pravidlo
                    ProductionRuleSide eps = new ProductionRuleSide();
                    eps.addEpsilon("eps");
                    //přidáme pravidla do nové mapy
                    ProductionRules newRule = 
                            new ProductionRules(newLeftNonterm, 
                                            new ProductionRulesSide(eps));
                    newGramm.addRule(newRule);

                    for(ProductionRuleSide oneRule : anotherRules){
                        List<ProductionRuleSide> newRulesListAnotherRules = 
                            new ArrayList<ProductionRuleSide>();
                        newRuleS = new ProductionRuleSide();
                        newRuleS.setSymbolsFromProductionRuleSide(oneRule);
                        //epsilon pravidlo může byt jen v teto mnozine
                        if(!oneRule.isEpsilon()){
                            newRuleS.addSymbol(newLeftSideSymb); 
                        }
                        newRulesListAnotherRules.add(newRuleS);
                        
                        newRule = 
                            new ProductionRules(leftSide, 
                                new ProductionRulesSide(newRulesListAnotherRules));
                        newGramm.addRule(newRule);
                    } 
                }
            }else{
                //nejsou žádná rekurzivní pravidla - přidám původní

                if(unitCheck){
                    for(ProductionRuleSide oneRule : anotherRules){
                        ProductionRules newRule = 
                            new ProductionRules(leftSide, new ProductionRulesSide(oneRule));
                        newGramm.addRule(newRule);
                    }
                }else{
                    for(ProductionRuleSide oneRule : anotherRules){
                        ProductionRules newRule = 
                            new ProductionRules(leftSide, new ProductionRulesSide(oneRule));
                        newGramm.addRule(newRule);
                        newRule.setFgColor(Color.RED);
                    }
                }

            }
            newGramm.setStartNonterminal(gramm.getStartNonterminal());
            rules.remove(leftSide);
            List<ProductionRuleSide> helpRules = newGramm.getSameLeftSideMap().get(leftSide);
            rules.put(leftSide, helpRules);
        }  
        return newGramm;
    }

    //pomocna metoda pro vytvoreni pravidel pri odstranovani leve rekurze
    public List<ProductionRuleSide> makeRulesForLeftRecursion(
                                  ProductionRuleSide leftSideRule, 
                                  List<ProductionRuleSide> testedNontermRules){
        
        List<ProductionRuleSide> newRules = new ArrayList<ProductionRuleSide>();
        
                List<Symbol> leftListRule = new ArrayList<Symbol>();
        leftListRule.addAll(leftSideRule.getSymbols());
        //potřebujeme \alfa část pravidla - první symbol vytváří rekurzi
        leftListRule.remove(0);
        for(ProductionRuleSide rule : testedNontermRules){
            ProductionRuleSide newRule = new ProductionRuleSide();
            newRule.setSymbolsFromProductionRuleSide(rule);

            newRule.addSymbolsFromList(leftListRule);

            newRules.add(newRule);
        }
        return newRules;
    }
    
    public String nameFromList(List<Symbol> list){
        String name = "";
        for(Symbol symb : list){
            name += symb.getName();
        }
        return name;
    }
    
    public List<ProductionRules> setToList(Set<ProductionRules> set){
        List<ProductionRules> list = new ArrayList<ProductionRules>();
        for(ProductionRules rule : set){
            list.add(rule);
        }
        return list;
    }
    
    public List<Symbol> setToListSymbols(SortedSet<Symbol> set){
        List<Symbol> list = new ArrayList<Symbol>();
        for(Symbol symbol : set){
            list.add(symbol);
        }
        return list;
    }
    
    //usporada neterminály podle pravidla A->B\alfa => A<B
    //vrátí množinu usporadanych neterminalu
    public List<ProductionRuleSide> orderingForGNF(
                        Map<ProductionRuleSide,List<ProductionRuleSide>> rules){
        
        List<ProductionRuleSide> complete = 
                                            new ArrayList<ProductionRuleSide>();
        List<ProductionRuleSide> leftHandSideNonterms = 
                                            new ArrayList<ProductionRuleSide>();
        Set<ProductionRuleSide> keySet = rules.keySet();
        
        //naplníme si první pole
        for(ProductionRuleSide oneNonterm : keySet){
            leftHandSideNonterms.add(oneNonterm);
        }
        
        //množina aktuálně vytvářenych neterminálů zbylých pro testování
        List<ProductionRuleSide> actualTestedNonterms = 
                                            new ArrayList<ProductionRuleSide>();
        //mnozina zbylych neterminálů pro testování z předchozí iterace
        List<ProductionRuleSide> previousNonterms = 
                                            new ArrayList<ProductionRuleSide>();
        
        actualTestedNonterms.addAll(leftHandSideNonterms);
        //pomocna mnozina pro predusporadani
        List<ProductionRuleSide> orderedNonTerms = 
                                            new ArrayList<ProductionRuleSide>();

        while(complete.size()<leftHandSideNonterms.size()){
            
            //znamena, že už nemáme co porovnávat, ale ještě jsme nepřidali všechno do complete
            //uz můžeme skončit
            if(actualTestedNonterms.isEmpty() && !orderedNonTerms.isEmpty()){
                complete.addAll(orderedNonTerms);
                break;
            }

            if(actualTestedNonterms.equals(previousNonterms) || 
                                                   previousNonterms.isEmpty()){
          
                //pokud jsou množiny stejné, tak to znamena, ze uz neni co srovnávat
                //pokud je prázdná, tak to znamená, že je to první iterace
                //kdyby byla prázdná z poslední iterace, tak uz neprobehne while cyklus
                complete.addAll(orderedNonTerms);
                orderedNonTerms = new ArrayList<ProductionRuleSide>();
                orderedNonTerms.add(actualTestedNonterms.get(0));
            }
            previousNonterms = new ArrayList<ProductionRuleSide>();
            previousNonterms.addAll(actualTestedNonterms);
            actualTestedNonterms = new ArrayList<ProductionRuleSide>();
            
            for(int i = 0; i < previousNonterms.size(); i++){
                //první symbol pro testování
                ProductionRuleSide y = new ProductionRuleSide();
                y = previousNonterms.get(i);
                boolean inserted = false;
                if(!orderedNonTerms.contains(y)){
                    
                    //pomocna proměnná pro kontrolu jestli bylo y už vložené a když ne,
                    //tak ho vložíme do množiny na testování v další iteraci while cyklu
                    
                    boolean test = false;
                    //bereme postupně všechny symboly z už predusporadane mnoziny
                    for(int j=0; j<orderedNonTerms.size(); j++){
                        ProductionRuleSide x = new ProductionRuleSide();
                        x = orderedNonTerms.get(j);
                        

                        //kontrola jestli je y<x
                        test = compareForGNForder(x, rules.get(y));
                        //y<x, našli jsme dál už hledat nemusíme
                        
                        if(test) {
                            orderedNonTerms.add(j,y);
                            inserted = true;
                            break;
                        }else{
                            //testujeme zda y>x, tady můsime pak projít zbytek pole
                            //pomuzeme si tim, že rekneme, že inserted je true - 
                            //tzn musi byt vlozeno po konci cyklu
                            test = compareForGNForder(y, rules.get(x));
                            if(test) inserted = true;
                            //pokud jsou nějake dva prvky neporovnatelné, tak prostě pokračuji dal
                            //s tím, že inserted je zatím false

                            //jsme na posledním prvku a z predchozích iterací víme, 
                            //že se musí vložit, ale ještě jsme nevlozili
                            if(j==(orderedNonTerms.size()-1) && inserted){
                                orderedNonTerms.add(y);
                                break;
                            }
                        }

                    }
                    //znamena, ze jsme y nikam nevlozili, ani na konec
                    if(!inserted){
                        actualTestedNonterms.add(y);
                    }

                }else if(previousNonterms.size()==1){
                    //znamena, že jsme u posledního prvku, který jen přidáme
                    complete.add(y);
                }
            }
        }
        return complete;
    }
    
    //test pro porovnání dvou prvků, zda jsou pod sebou
    //return True když x<y, false jinak
    public boolean compareForGNForder(ProductionRuleSide y,
                                                List<ProductionRuleSide> xRules){
        Symbol testedSymb = y.getSymbols().get(0);
        
        for(ProductionRuleSide oneRule : xRules){
            List<Symbol> listRule = new ArrayList<Symbol>();
            listRule.addAll(oneRule.getSymbols());
            //pokud je na prvním místě hledaný neterminál, tak vrátí true 
            //jinak projde celým cyklem a frátí false
            if(testedSymb.equals(listRule.get(0))) return true;
        }
        return false;
    }
    
    //test pro seřazenou množinu, zda je správně seřazená
    //X<Y ?
    public boolean isWellOrdered(List<ProductionRuleSide> orderedList, 
                                 Map<ProductionRuleSide,List<ProductionRuleSide>> rules){
        
        for(int i = 0; i < orderedList.size(); i++){
            ProductionRuleSide symbolX = orderedList.get(i);
            List<ProductionRuleSide> rulesX = rules.get(symbolX);
            
            for(int j=i+1; j<orderedList.size(); j++){
                ProductionRuleSide symbolY = orderedList.get(j);
                List<ProductionRuleSide> rulesY = rules.get(symbolY);
                
                //is X<Y?
                if(compareForGNForder(symbolY, rulesX)){
                    continue;
                }else{
                    //is Y<X?
                    if(compareForGNForder(symbolX, rulesY)){
                        return false;
                    }else{
                        continue;
                    }
                }
            }
        }

        return true;
    }
    //nevytvoří korektní gramatiku, ale pouze tam naháže pravidla
    //nasledně je potřeba nastavit počíteční symbol
    public Grammar addRuleToGrammFromMap(
            Map<ProductionRuleSide, List<ProductionRuleSide>> rules){
        Grammar gramm = new Grammar();
        for (Map.Entry<ProductionRuleSide, 
                    List<ProductionRuleSide>> oneSet : rules.entrySet()) {
            for(ProductionRuleSide oneNewRule : oneSet.getValue()) {
                if(oneNewRule != null){
                    ProductionRules newRule = 
                                new ProductionRules(oneSet.getKey(), 
                                        new ProductionRulesSide(oneNewRule));
                    gramm.addRule(newRule);
                }
            }
        }
        return gramm;
    }
    
    //používáme pro CFG - víme, že levá strana má jen jeden neterminál
    public Set<Symbol> prodRulesToSymbols(Set<ProductionRuleSide> set){
        Set<Symbol> set2 = new HashSet<Symbol>();
        for(ProductionRuleSide rule : set) {
          set2.add(rule.getSymbols().get(0));  
        }
        return set2;
    }
}


