/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.tools;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaf.IA006.grammar.LLGrammar;
import jgaf.grammar.Grammar;
import static jgaf.importer.XMLImporter.getGrammar;
import org.dom4j.DocumentException;

/**
 *
 * @author Empt
 */
public class GrammarLoader 
{
    private File f;
    private Grammar gram;
    private LLGrammar g = new LLGrammar();
    
    
    public GrammarLoader(File f)
    {
        this.f = f;
    }
    
    public GrammarLoader(Grammar gram) {
        gram.toString();             
        this.gram = gram;
    }
    
    public LLGrammar processFile() 
    {
        Grammar grammar = new Grammar();
        try {
            grammar = getGrammar(f);
        } catch (DocumentException ex) {
            Logger.getLogger(GrammarLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        g = GrammarFactory.convertGrammar(grammar);
        
        return g;
    }
}
