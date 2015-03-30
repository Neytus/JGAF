/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.grammar;

/**
 * This class servers as representation of NonTerminalSymbol.
 * @author Empt
 */
public class NonTerminal extends LLSymbol
{
    public NonTerminal(String value)
    {
        super(value, SymbolType.NONTERMINAL);
    }
}
