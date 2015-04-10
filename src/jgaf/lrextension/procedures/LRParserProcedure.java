/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import jgaf.lrextension.procedurefaces.LRParserFace;
import jgaf.lrextension.procedurefaces.TablePanel;
import jgaf.lrextension.procedurefaces.ParserPanel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import jgaf.lrextension.CFGUtils;
import jgaf.Representation;
import jgaf.grammar.Grammar;
import jgaf.procedure.Procedure;

/**
 *
 * @author g
 */
public class LRParserProcedure extends Procedure {

    private Grammar g;
    private JPanel face;
    private int type;
    private int k;
    private String kString;
    private String typeString;
    private String namePrefix;
    public static final int LR = 0;
    public static final int LALR = 1;
    public static final int SLR = 2;

    public LRParserProcedure() {
        
        
        
    }

    @Override
    public String checkInputRepresentation() {
        
        /**
         * JB 
         */
        g.normalize();       
        
        if (!g.hasStartNonterminal()) {
            return "Grammar has not selected starting nonterminal";
        }
        if (!g.isContextFreeE()) {
            return "Grammar is not context-free";
        }

        if (CFGUtils.hasUnreachablesOrUnusables(g)) {
            CFGUtils.deleteUnusable(g);

            JOptionPane.showMessageDialog(
                    getProcedureFrame(),
                    "Grammar has ureachable or unusable nonterminals, all rules containing theese will be deleted.", "Warning", JOptionPane.WARNING_MESSAGE);
            if (!g.hasStartNonterminal()) {
                return "Resulting grammar is empty";
            }
            CFGUtils.deleteUnreachable(g);
        }

        if (!CFGUtils.isAugmented(g)) {
            JOptionPane.showMessageDialog(
                    getProcedureFrame(),
                    "Grammar has more then one rule with starting nonterminal, it will be augmented.", "Warning", JOptionPane.WARNING_MESSAGE);
            CFGUtils.augment(g);
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
        
        if (typeString.equalsIgnoreCase("lr") || typeString.equalsIgnoreCase("lr(k)")) {
            type = LR;
            namePrefix=("LR("+k+")");
            return CHECK_OK;
        }

        if (typeString.equalsIgnoreCase("lalr") || typeString.equalsIgnoreCase("lalr(k)")) {
            namePrefix=("LALR("+k+")");
            type = LALR;
            return CHECK_OK;
        }

        if (typeString.equalsIgnoreCase("slr") || typeString.equalsIgnoreCase("slr(k)")) {
            namePrefix=("SLR("+k+")");
            type = SLR;
            return CHECK_OK;
        }

        return "Wrong parameter: Type. \n Only LR, LALR and SLR are allowed.";

    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentation) {
        g = (Grammar) inputRepresentation[0].clone();
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        kString = inputParameters[1];
        typeString = inputParameters[0];
        
    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
    }

    @Override
    public JPanel getFace() {
        return face;
    }

    public void create() {

        //  if (moreGrammarChecks()) face.getParent().setVisible(false);
//getProcedureFrame().dispose();

         ItemAuto itemAuto =new ItemAuto(g, k, type);
       itemAuto.calcAuto();
       ParserTablesCalc ptc =new ParserTablesCalc(itemAuto);
       ptc.calcActionTable();
       ptc.calcGotoTable();
       ParserSimCalc psc= new ParserSimCalc(ptc,g);        
       
        JPanel autoPanel = new ItemAutoPanel(itemAuto);
        TablePanel tablePanel = new TablePanel(ptc);
        ParserPanel parserPanel = new ParserPanel(ptc,psc);
        
        




        face = new LRParserFace(autoPanel,tablePanel,parserPanel);
    }

    public Grammar getGram() {
        return g;
    }

    public int getType() {
        return type;
    }

    public int getK() {
        return k;
    }
    
//    public void setNameID(String s) {
//        super.nameID = namePrefix + " - "+s;
//    }
    
     public String getTitle() {
        return  namePrefix + " - Parsing";
    }
}
