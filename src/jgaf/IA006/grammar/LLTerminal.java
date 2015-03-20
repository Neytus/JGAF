/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.grammar;

/**
 * This class servers as representation of Terminal symbol.
 * @author Empt
 */
public class LLTerminal extends LLSymbol
{
    public LLTerminal(String value)
    {
        super(value, SymbolType.TERMINAL);
    }

    @Override
    public String toString() {
        return super.getValue();
    }
}
