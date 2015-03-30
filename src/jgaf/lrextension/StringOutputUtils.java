/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import jgaf.Constants.MathConstants;
import jgaf.grammar.Grammar;
import jgaf.grammar.Symbol;

/**
 *
 * @author g
 */
public class StringOutputUtils {

    public static String setToString(Collection<?> col) {
        if (col == null || col.isEmpty()) {
            return MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        String prefix = "";
        for (Object object : col) {
            sb.append(prefix);
            prefix = ",";
            if (object instanceof Collection<?>) {
                sb.append(listToString((Collection<Object>) object));
            } else {
                sb.append(object.toString());
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static String foEquationsString(Grammar gram,
                                           int k) {
        Map<Symbol, Set<String>> foStringMap = foEquationsStringMap(gram, k);
        StringBuilder sb = new StringBuilder();
        for (Entry<Symbol, Set<String>> entry : foStringMap.entrySet()) {
            Symbol leftNont = entry.getKey();
            Set<String> stringSet = entry.getValue();
            sb.append("\nFO" + kSub(k) + "(" + leftNont + ")= ");
            String prefix = "\t";
            if (stringSet==null) {
                sb.append(prefix + MathConstants.EMPTY_SET +"\n");
            }else{
            
            for (String str : stringSet) {
                sb.append( prefix+ str + "\n");
                prefix = "\t" + MathConstants.UNION+" ";
            }}
        }
        return sb.toString();
    }

    public static String listToString(Collection<Object> col) {
        if (col == null || col.isEmpty()) {
            return MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder();
        for (Object object : col) {
            sb.append(object.toString());
        }
        return sb.toString();
    }

    public static String superscript(String str) {
        str = str.replaceAll("0", "\u2070");
        str = str.replaceAll("1", "\u00b9");
        str = str.replaceAll("2", "\u00b2");
        str = str.replaceAll("3", "\u00b3");
        str = str.replaceAll("4", "\u2074");
        str = str.replaceAll("5", "\u2075");
        str = str.replaceAll("6", "\u2076");
        str = str.replaceAll("7", "\u2077");
        str = str.replaceAll("8", "\u2078");
        str = str.replaceAll("9", "\u2079");
        return str;
    }

    public static String kSub(int k) {
        return subscript(Integer.toString(k));
    }

    public static String subscript(String str) {
        str = str.replaceAll("0", "\u2080");
        str = str.replaceAll("1", "\u2081");
        str = str.replaceAll("2", "\u2082");
        str = str.replaceAll("3", "\u2083");
        str = str.replaceAll("4", "\u2084");
        str = str.replaceAll("5", "\u2085");
        str = str.replaceAll("6", "\u2086");
        str = str.replaceAll("7", "\u2087");
        str = str.replaceAll("8", "\u2088");
        str = str.replaceAll("9", "\u2089");
        return str;
    }

    public static String fiEquationsString(Grammar gram,
                                           int k) {
        Map<Symbol, Set<WString>> gramMap = CFGUtils.getGrammarMap(gram);
        StringBuilder sb = new StringBuilder();
        for (Entry<Symbol, Set<WString>> entry : gramMap.entrySet()) {
            Symbol leftNont = entry.getKey();
            Set<WString> ruleSet = entry.getValue();
            sb.append("\nFI" + kSub(k) + "(" + leftNont.toString() + ")= ");
            String prefix = "\t";
            for (WString rule : ruleSet) {
                sb.append( prefix + fiString(rule, k) + "\n");
                prefix = "\t" + MathConstants.UNION+" ";
            }
        }
        return sb.toString();
    }

    public static void putOrAdd(Map<Symbol, Set<String>> map,
                                Symbol nonT,
                                String string) {
        if (map.containsKey(nonT)) {
            map.get(nonT).add(string);
        } else {
            Set<String> newSet = new HashSet<String>();
            newSet.add(string);
            map.put(nonT, newSet);
        }
    }

    public static Map<Symbol, Set<String>> foEquationsStringMap(Grammar gram,
                                                                int k) {
        Map<Symbol, Set<WString>> gramMap = CFGUtils.getGrammarMap(gram);
        Map<Symbol, Set<String>> foStringMap = new HashMap<Symbol, Set<String>>();
        for (Entry<Symbol, Set<WString>> entry : gramMap.entrySet()) {
            Symbol nonT = entry.getKey();
            Set<WString> ruleSet = entry.getValue();
            String foSuffix;
            foSuffix = "FO" + kSub(k) + "(" + nonT + ")";
            for (WString rule : ruleSet) {
                for (int i = 0; i < rule.size(); i++) {
                    if (rule.get(i).isNonterminal()) {
                        WString subWString = new WString(rule.subList(i + 1, rule.size()));
                        String Oplus = " " + oPlusK(k) + " ";
                        if (subWString.isEmpty()) {
                            Oplus = "";
                        }
                        String foString = fiString(subWString, k) + Oplus + foSuffix;
                        putOrAdd(foStringMap, rule.get(i), foString);
                    }
                }
            }
        }
        putOrAdd(foStringMap, gram.getStartNonterminal(), "{"+MathConstants.EPSILON+"}");
        Map<Symbol, Set<String>> oFoStringMap = new LinkedHashMap<Symbol, Set<String>>();
        for (Entry<Symbol, Set<WString>> entry : gramMap.entrySet()) {
            Symbol symbol = entry.getKey();
            oFoStringMap.put(symbol, foStringMap.get(symbol));
        }
        return oFoStringMap;
    }

    public static String fiString(WString fiRule,
                                  int k) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (Symbol s : fiRule) {
            sb.append(prefix);
            prefix = " " + oPlusK(k) + " ";
            if (s.isNonterminal()) {
                sb.append("FI" + kSub(k) + "(" + s.toString() + ")");
            } else {
                sb.append("{" + s.toString() + "}");
            }
        }
        return sb.toString();
    }    
    
    public static String oPlusK(int k){
    return MathConstants.O_PLUS + kSub(k);
    }
}
