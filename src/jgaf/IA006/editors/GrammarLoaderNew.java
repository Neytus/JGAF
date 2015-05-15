/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.editors;

import javax.swing.JPanel;
import jgaf.IA006.gui.GrammarLoaderFrame;
import jgaf.Representation;
import jgaf.grammar.Grammar;

/**
 *
 * @author Empt
 */
public class GrammarLoaderNew extends jgaf.procedure.Procedure
{
    private GrammarLoaderFrame glf;
    private Grammar g;
    private String kString; 
    private int k;
    
    
    public GrammarLoaderNew() {
    }

    @Override
    public void create() {
        glf = new GrammarLoaderFrame(g, k);
    }

    @Override
    public JPanel getFace() {
        return glf;
    }

    @Override
    public String checkInputRepresentation() {
        if (!g.hasStartNonterminal()) {
            return "Grammar has not selected starting nonterminal";
        }
        if (!g.isContextFreeE()) {
            return "Grammar is not context-free";
        }
        return CHECK_OK;
    }

    @Override
    public String checkInputParameters() {
        try {
            k = Integer.parseInt(kString);
        } catch (NumberFormatException e) {
            return "k must be whole non-negative number";
        }
        if (k < 0) {
            return "k must be whole non-negative number";
        }
        k = Integer.parseInt(kString);
        
        return CHECK_OK;
    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentation) {
        g = (Grammar) inputRepresentation[0].clone();
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        kString = inputParameters[0];
    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}
