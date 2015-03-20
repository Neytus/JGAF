/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.tools;

import java.util.List;
import java.util.Map;
import java.util.Set;
import jgaf.IA006.grammar.LLGrammar;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public interface FirstAndFollowI 
{
    /**
     * This method is implementation of function k : w (Definition 16 in thesis).
     * Because word consists of terminals, no nonterminal can occur in input otherwise 
     * exception is thrown and because epsilon is proper symbol in every word it's length is 0.
     * In following table is described behaviour of this method where {a,b,c} are terminals,
     * B is nonterminal an &#949; is epsilon symbol.
     * 
     * <table>
            <thead>
                    <tr>
                            <td style="width: 150px;">word</td>
                            <td style="width: 25px;">k</td>
                            <td style="width: 150px;">result</td>
                    </tr>
            </thead>
            <tbody>
                    <tr>
                            <td>abc</td>
                            <td>-1</td>
                            <td>exception</td>
                    </tr>
                    <tr>
                            <td>abc</td>
                            <td>0</td>
                            <td>&#949;</td>
                    </tr>
                    <tr>
                            <td>abc</td>
                            <td>2</td>
                            <td>ab</td>
                    </tr>
                    <tr>
                            <td>abc</td>
                            <td>4</td>
                            <td>abc</td>
                    </tr>
                    <tr>
                            <td>aBc</td>
                            <td>0</td>
                            <td>exception</td>
                    </tr>
                    <tr>
                            <td>&#949;abc</td>
                            <td>3</td>
                            <td>abc</td>
                    </tr>
                    <tr>
                            <td>&#949;a&#949;&#949;bc&#949;</td>
                            <td>3</td>
                            <td>abc</td>
                    </tr>
                    <tr>
                            <td>&#949;a&#949;&#949;bc&#949;</td>
                            <td>2</td>
                            <td>ab</td>
                    </tr>
                    <tr>
                            <td>&#949;Ac</td>
                            <td>3</td>
                            <td>exception</td>
                    </tr>
            </tbody>
    </table>
	
     * 
     * @param word input word 
     * @param k length of desired output
     * @return first k symbols of input words if input is proper
     * @throws IllegalArgumentException if k &lt; 0, word contains at least one NonTerminal
     */
    List<LLSymbol> kLengthPrefix(List<LLSymbol> word, int k) throws IllegalArgumentException;
    
    
    /**
     * Method is implementation of function called concatenation. This function is defined over words,
     * so again input words has to consist of terminals or epsilon(s). Method uses {@link #kLengthPrefix(java.util.List, int) }
     * so if {@link #kLengthPrefix(java.util.List, int) } fails this one fails aswell. The concatenation is defined following way:
     * <table>
	<thead>
		<tr>
			<td style="width: 150px;">input</td>
			<td>k</td>			
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>&#8709;.&#8709;</td>
			<td>&#8709;</td>			
		</tr>
		<tr>
			<td>&#8709;.a</td>
			<td>&#8709;</td>			
		</tr>
		<tr>
			<td>&#8709;.&#949;</td>
			<td>&#8709;</td>			
		</tr>
		<tr>
			<td>a.&#8709;</td>
			<td>k</td>			
		</tr>
		<tr>
			<td>&#949;.&#8709;</td>
			<td>&#8709;</td>			
		</tr>
		<tr>
			<td>&#949;.&#949;</td>
			<td>&#949;</td>			
		</tr>
		<tr>
			<td>&#949;.a</td>
			<td>a</td>			
		</tr>
		<tr>
			<td>a.&#949;</td>
			<td>&#949;</td>			
		</tr>
		<tr>
			<td>a.a</td>
			<td>aa</td>			
		</tr>
	</tbody>
        </table>
     * @param word1 first word consisting of terminals to be concatenated
     * @param word2 second word consisting of terminals to be concatenated
     * @param k length of output word
     * @return concatenated first word with second one with length k
     * @throws IllegalArgumentException if k &l; , or input words are not made of terminals
     */
    List<LLSymbol> concatenateWordWithPrefix(List<LLSymbol> word1, List<LLSymbol> word2, int k) throws IllegalArgumentException;
    
    
    
    /**
     * This method is direct implementation of function defined in Definition 13. It uses method {@link #concatenateWordWithPrefix(java.util.List, java.util.List, int) }
     * so if any error occurs in that method call, it's reflected here (basically wrong input)
     * @param set1
     * @param set2
     * @param k
     * @return Sets of words that are concatenated as defined by definition 17.
     * @throws IllegalArgumentException 
     */
    Set<List<LLSymbol>> concatenateSetsWithPrefix(Set<List<LLSymbol>> set1, Set<List<LLSymbol>> set2, int k) throws IllegalArgumentException;
    
    
    /**
     * Method calculates First sets for given grammar.
     * @param g grammar to be first calculated from
     * @param k size of lookahead
     * @return follow set
     * @throws IllegalArgumentException if grammar is null or k is less than 0
     */
    Map<LLSymbol,Set<List<LLSymbol>>> first(LLGrammar g, int k) throws IllegalArgumentException;
    
    /**
     * Method generates first set for given input word and grammar.
     * @param symbols input \alpha
     * @param g grammar upon which we calculate firs set
     * @param k size of lookahead
     * @return First set for given \alpha input
     * @throws IllegalArgumentException if input word is null, contains no symbols, grammar is null or k size is less than 0
     */
    Set<List<LLSymbol>> first(List<LLSymbol> symbols,LLGrammar g,int k) throws IllegalArgumentException;
    
    /**
     * Method calculates First set out of given input word from already calculated first sets.
     * @param symbols alpha string 
     * @param fiSet already calculated first set
     * @param k size of lookahead
     * @return FI_{k}(\alpha)
     * @throws IllegalArgumentException 
     */
    Set<List<LLSymbol>> firstAlpha(List<LLSymbol> symbols,Map<LLSymbol,Set<List<LLSymbol>>> fiSet,int k) throws IllegalArgumentException;
    
    /**
     * Method calculates follow sets for given grammar. Method is taken and modified from [23]
     * @param g
     * @param k
     * @return
     * @throws IllegalArgumentException if grammar is null or k is lower than 0
     */
    Map<LLSymbol,Set<List<LLSymbol>>> follow(LLGrammar g, int k) throws IllegalArgumentException;
}
