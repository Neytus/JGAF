/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import jgaf.lrextension.procedurefaces.FiFoProcedureFace;
import com.rits.cloning.Cloner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import jgaf.Constants.MathConstants;
import jgaf.lrextension.CFGUtils;
import jgaf.lrextension.FiFoUtils;
import jgaf.lrextension.WString;
import jgaf.Representation;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.Symbol;
import jgaf.lrextension.StringOutputUtils;
import jgaf.procedure.Procedure;

/**
 *
 * @author g
 */
public class FiFoProcedure extends Procedure {

    private FiFoProcedureFace face;
    private Grammar g;
    private List<Map<Symbol, Set<WString>>> FiSteps;
    private List<List<Map<Symbol, Set<WString>>>> fiSequence;
    private List<Map<Symbol, Set<WString>>> FoSteps;
    private List<List<Map<Symbol, Set<WString>>>> foSequence;
    private int k;
    private String kString;
    private List<String> msgSeq;
    private Map<Symbol, Set<WString>> fiMap;
    private String fiEqString;
    private String foEqString;

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
            return "k must be whole positive number";
        }
        if (k < 1) {
            return "k must be whole positive number";
        }
        k = Integer.parseInt(kString);
        return CHECK_OK;
    }

    @Override
    public JPanel getFace() {
        return face;
    }

    @Override
    public void create() {
        FiSteps = new ArrayList<Map<Symbol, Set<WString>>>();
        fiSequence = new ArrayList<List<Map<Symbol, Set<WString>>>>();
        FoSteps = new ArrayList<Map<Symbol, Set<WString>>>();
        foSequence = new ArrayList<List<Map<Symbol, Set<WString>>>>();
        msgSeq = new ArrayList<String>();

//        try {
//            
//        
//   
        fiEqString = StringOutputUtils.fiEquationsString(g, k);
        foEqString = StringOutputUtils.foEquationsString(g, k);


        calcFi();

        calcFo();


//      // FiFoUtils.calcFiMap(g, k);
//      // fiMap=FiFoUtils.calcFo(g, k);
//       } catch (OutOfMemoryError e) { System.out.print("dosla pamet"); return false;
//        }
        face = new FiFoProcedureFace(this);
//        final WorkerTest worker=new WorkerTest();
//        
//        JDialog dialog = new JDialog();
//        worker.addPropertyChangeListener(
//        new WorkerTestDialog(dialog));
//        
//        dialog.setVisible(true);
//        worker.execute();
        //the dialog will be visible until the SwingWorker is done


//        int delay = 2000; //milliseconds
//  ActionListener taskPerformer = new ActionListener() {
//      public void actionPerformed(ActionEvent evt) {
//          if (!worker.isDone()) {
//              JDialog dialog = new JDialog();
//        worker.addPropertyChangeListener(
//        new WorkerTestDialog(dialog));
//        
//        dialog.setVisible(true);
//      }
//          }
//          
//  };
//   Timer timer =new Timer(delay, taskPerformer);
//        timer.setRepeats(false);
//        timer.start();











        //JOptionPane.showInputDialog(getProcedureFrame(), face);

    }

    @Override
    public void assignInputRepresentation(Representation... inputRepresentation) {
        g = (Grammar) inputRepresentation[0];
    }

    @Override
    public void assignInputParameters(String... inputParameters) {
        kString = inputParameters[0];
    }

    @Override
    public void assignOutputRepresentation(Representation outputRepresentation) {
    }

    private void calcFi() {



        fiMap = new HashMap<Symbol, Set<WString>>();
        Map<Symbol, Set<WString>> previouseFiMap = new HashMap<Symbol, Set<WString>>();
        Map<Symbol, Set<WString>> gramMap = CFGUtils.getGrammarMap(g);

        for (Symbol nonT : gramMap.keySet()) {
            previouseFiMap.put(nonT, new HashSet<WString>());
        }
        int iteration = 0;
        fixConf("Calculating FIRST by finding minimal fixed point in recursive equations:" + fiEqString);
        SortedSet<Symbol> sortedNonts = new TreeSet<Symbol>(gramMap.keySet());



        while (!fiMap.equals(previouseFiMap)) {
            fixConf("iteration " + iteration);
            Map<Symbol, Set<WString>> tmpMap = new HashMap<Symbol, Set<WString>>();
            FiSteps.add(tmpMap);

            previouseFiMap.putAll(fiMap);
            iteration++;
            for (Symbol nonT : sortedNonts) {
                StringBuilder sb = new StringBuilder();
                sb.append("FI" + StringOutputUtils.kSub(k) + "(" + nonT + ")=\n");
                fiMap.put(nonT, new HashSet<WString>());
                tmpMap.put(nonT, new HashSet<WString>());
                String prefix = "\t";
                for (WString rule : gramMap.get(nonT)) {
                    sb.append(prefix);
                    Set<WString> ruleFi = new HashSet<WString>();
                    boolean firstRun2 = true;
                    String prefix2 = "";
                    for (Symbol symbol : rule) {
                        sb.append(prefix2);
                        Set<WString> symbolFi = new HashSet<WString>();
                        if (symbol.isNonterminal()) {
                            symbolFi = previouseFiMap.get(symbol);
                            sb.append(StringOutputUtils.setToString(symbolFi));
                        } else {
                            WString thisSymbol = new WString();
                            thisSymbol.add(symbol);
                            symbolFi.add(thisSymbol);
                            sb.append("{" + symbol + "}");
                        }
                        if (firstRun2) {
                            ruleFi = symbolFi;
                        } else {
                            ruleFi = FiFoUtils.concatSet(ruleFi, symbolFi);
                            ruleFi = FiFoUtils.ShorternSet(k, ruleFi);
                        }
                        firstRun2 = false;
                        prefix2 = " " + StringOutputUtils.oPlusK(k) + " ";
                    }

                    fiMap.get(nonT).addAll(ruleFi);
                    tmpMap.get(nonT).addAll(ruleFi);
                    prefix = "\n\t" + MathConstants.UNION + " ";
                }
                fixConf(sb.toString());
            }



        }
        fixConf("Nothing has changed in last iteration - minimal fixed point found.");
    }

    private void calcFo() {
        fixConf("Calculating FOLLOW by finding minimal fixed point in theese recursive equations:" + foEqString);
        Map<Symbol, Set<WString>> foMap = new HashMap<Symbol, Set<WString>>();

        //Map<Symbol, Set<WString>> fiMap = FiFoUtils.calcFiMap(g, k);

        Map<Symbol, Set<WString>> previouseFoMap = new HashMap<Symbol, Set<WString>>();
        Map<Symbol, Set<WString>> gramMap = CFGUtils.getGrammarMap(g);
        Map<Symbol, Set<WString>> startMap = new HashMap<Symbol, Set<WString>>();
        FoSteps.add(startMap);
        for (Symbol nonT : gramMap.keySet()) {
            if (nonT.equals(g.getStartNonterminal())) {
                previouseFoMap.put(nonT, FiFoUtils.createEpsilonSet());
                startMap.put(nonT, FiFoUtils.createEpsilonSet());

            } else {
                previouseFoMap.put(nonT, new HashSet<WString>());
                startMap.put(nonT, new HashSet<WString>());
            }
        }
        fixConf("FOLLOW set of starting nonterminal is initialized to " + MathConstants.EPSILON + ", the rest is " + MathConstants.EMPTY_SET);
        int iteration=1;
        while (!foMap.equals(previouseFoMap)) {
            
            fixConf("iteration: " + iteration++);
            Map<Symbol, Set<WString>> tmpMap = new HashMap<Symbol, Set<WString>>();
            FoSteps.add(tmpMap);
            previouseFoMap.putAll(foMap);

            for (Iterator<Symbol> it = gramMap.keySet().iterator(); it.hasNext();) {
                StringBuilder sb = new StringBuilder();
                
                Symbol nonCalc = it.next();
                sb.append("Fo" + StringOutputUtils.kSub(k) + "(" + nonCalc + ")=");
                foMap.put(nonCalc, new HashSet<WString>());
                tmpMap.put(nonCalc, new HashSet<WString>());
                String prefix2 = "\n\t";
                if (nonCalc.equals(g.getStartNonterminal())) {
                    foMap.get(nonCalc).addAll(FiFoUtils.createEpsilonSet());
                    tmpMap.get(nonCalc).addAll(FiFoUtils.createEpsilonSet());
                    sb.append("\n\t{"+MathConstants.EPSILON+"}");
                    prefix2 = "\n\t" + MathConstants.UNION + " ";
                
                }
                
                for (Iterator<Symbol> it2 = gramMap.keySet().iterator(); it2.hasNext();) {
                    Symbol nonOther = it2.next();
                    for (WString rule : gramMap.get(nonOther)) {
                        int fromIndex = 0;

                        WString subRule = rule;
                        
                        while (subRule.contains(nonCalc)) {
                            sb.append(prefix2);
                            Set<WString> subRuleFi;
                            subRule = new WString(subRule.subList(subRule.indexOf(nonCalc) + 1, subRule.size()));
                            subRuleFi = FiFoUtils.fiFast(subRule, k, fiMap);
                            
                            String subrulePlusStr="";
                            if (subRuleFi.isEmpty()) {
                                subRuleFi.addAll(FiFoUtils.createEpsilonSet());
                                
                            }else {subrulePlusStr=StringOutputUtils.setToString(subRuleFi) +
                                    StringOutputUtils.oPlusK(k);}
                            Set<WString> nonOtherOldFo = new HashSet<WString>();
                            nonOtherOldFo.addAll(previouseFoMap.get(nonOther));
                            
                            sb.append( subrulePlusStr+ 
                                    StringOutputUtils.setToString(nonOtherOldFo));
                            
                            
                            
                            subRuleFi = FiFoUtils.concatSet(subRuleFi, nonOtherOldFo);
                            subRuleFi = FiFoUtils.ShorternSet(k, subRuleFi);
                            foMap.get(nonCalc).addAll(subRuleFi);
                            tmpMap.get(nonCalc).addAll(subRuleFi);

                            

                            prefix2 = "\n\t" + MathConstants.UNION + " ";
                        }

                    }
                }fixConf(sb.toString());
            } 
        }
        fixConf("Nothing has changed in last iteration - minimal fixed point found.");
        fixConf("Valid results for functions FIRST & FOLLOW are shown in last rows of their respective tables.");
    }

    private void fixConf(String s) {
        Cloner cl = new Cloner();

        fiSequence.add(cl.deepClone(FiSteps));
        foSequence.add(cl.deepClone(FoSteps));
        msgSeq.add(s);
//        if (msgSeq.isEmpty()){
//        msgSeq.add(s);
//        }else{
//        String msgs =msgSeq.get(msgSeq.size()-1);
//        msgSeq.add(msgs + "\n" + s);
//        }
    }

    public Grammar getG() {
        return g;
    }

    public List<List<Map<Symbol, Set<WString>>>> getFiSeq() {


        return fiSequence;

    }

    public List<List<Map<Symbol, Set<WString>>>> getFoSeq() {


        return foSequence;

    }

    public List<String> getMsgSeq() {
        return msgSeq;
    }

    public WString getNonts() {
        return new WString(g.getNonterminals());
    }

    public int getNumberOfSteps() {
        return fiSequence.size();
    }

    public Set<WString> calcUserFi(String string) {
        ProductionRuleSide tmp = new ProductionRuleSide();
        tmp.setSymbolsFromString(string);
        WString symbols = new WString(tmp.getSymbols());
        Set<Symbol> gramSym = g.getSymbols();
        for (Symbol symbol : symbols) {
            if (!gramSym.contains(symbol)) {
                throw new IllegalArgumentException();
            }
        }
        return FiFoUtils.fiFast(symbols, k, fiMap);

    }

    public int getK() {
        return k;
    }

    public String getFiEqString() {
        return fiEqString;
    }

    public String getFoEqString() {
        return foEqString;
    }
    
    public String getTitle() {
        return  "First("+k+") & Follow("+k+") ";
    }
}
