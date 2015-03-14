/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.exporter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.automaton.fa.CanvasLabel;
import jgaf.grammar.Grammar;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.Symbol;
import jgaf.regex.RegularExpression;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author hanis
 */
public class XMLExporter {

    public static void saveAutomaton(Automaton automaton, List<CanvasLabel> labels, File file) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element jgafElem = document.addElement("jgaf");
        jgafElem.addAttribute("id", "FA");
        Element root = jgafElem.addElement("automaton");        
        root.addAttribute("name", automaton.getName());

        Element elemStates = root.addElement("states");
        for (State state : automaton.getStates()) {
            Element elemState = elemStates.addElement("state");
            elemState.addText(state.getName());
            elemState.addAttribute("x", String.valueOf(state.getVisualProperties().getXPos()));
            elemState.addAttribute("y", String.valueOf(state.getVisualProperties().getYPos()));
            elemState.addAttribute("fill-color", String.valueOf(state.getVisualProperties().getFillColor().getRGB()));
            elemState.addAttribute("stroke-color", String.valueOf(state.getVisualProperties().getStrokeColor().getRGB()));
            elemState.addAttribute("font-color", String.valueOf(state.getVisualProperties().getFontColor().getRGB()));
        }

        Element elemInit = root.addElement("initialState");
        if(automaton.getInitialState() != null) {
            elemInit.addText(automaton.getInitialState().getName());
        }

        Element elemTransitions = root.addElement("transitions");
        for (Transition transition : automaton.getTransitions()) {
            Element elemTransition = elemTransitions.addElement("transition");
            elemTransition.addAttribute("font-color", String.valueOf(transition.getVisualProperties().getFontColor().getRGB()));
            elemTransition.addAttribute("stroke-color", String.valueOf(transition.getVisualProperties().getStrokeColor().getRGB()));
            elemTransition.addAttribute("curve", String.valueOf(transition.getVisualProperties().getCurveFactor()));

            elemTransition.addElement("fromState").addText(transition.getFromState().getName());
            elemTransition.addElement("toState").addText(transition.getToState().getName());
            for (String label : transition.getLabels()) {
                elemTransition.addElement("label").addText(label);
            }
        }

        Element elemFinalStates = root.addElement("finalStates");
        for (State state : automaton.getAcceptingStates()) {
            Element elemState = elemFinalStates.addElement("state");
            elemState.addText(state.getName());
        }


        Element elemLabels = root.addElement("labels");
        for (CanvasLabel label : labels) {
            Element elemLabel = elemLabels.addElement("label");
            elemLabel.addText(label.getCaption());
            elemLabel.addAttribute("x", String.valueOf(label.getXPos()));
            elemLabel.addAttribute("y", String.valueOf(label.getYPos()));
            elemLabel.addAttribute("bg-color", String.valueOf(label.getBackgroundColor().getRGB()));
            elemLabel.addAttribute("fg-color", String.valueOf(label.getForegroundColor().getRGB()));
            elemLabel.addAttribute("rotation", String.valueOf(label.getRotationFactor()));
            elemLabel.addAttribute("transparency", String.valueOf(label.getTransparency()));
            elemLabel.addAttribute("font-name", String.valueOf(label.getFont().getFontName()));
            elemLabel.addAttribute("font-style", String.valueOf(label.getFont().getStyle()));
            elemLabel.addAttribute("font-size", String.valueOf(label.getFont().getSize()));
        }



        writeDocument(document, file);

    }


    public static void saveGrammar(Grammar grammar, File file) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("jgaf");
        root.addAttribute("id", "G");
        Element grammarElem = root.addElement("grammar");
        grammarElem.addAttribute("name", grammar.getName());

        Element elemNonterminals = grammarElem.addElement("nonterminals");
        for (Symbol symbol : grammar.getNonterminals()) {
            Element elemState = elemNonterminals.addElement("nonterminal");
            elemState.addText(symbol.getName());
        }

        Element elemTerminals = grammarElem.addElement("terminals");
        for (Symbol symbol : grammar.getTerminals()) {
            if(symbol.isEpsilon()){
                Element elemState = elemTerminals.addElement("epsilon");
                elemState.addText(symbol.getName());
            }else{
                Element elemState = elemTerminals.addElement("terminal");
                elemState.addText(symbol.getName());
            }            
        }


        Element elemStart = grammarElem.addElement("startNonterminal");
        if(grammar.getStartNonterminal() != null) {
            elemStart.addText(grammar.getStartNonterminal().getName());
        }

        Element elemProductions = grammarElem.addElement("productions");
        for (ProductionRules rule : grammar.getProductionRulesType2()) {
            List<Symbol> leftHandSide = rule.getLeftHandSide().getSymbols();
            List<ProductionRuleSide> rightHandSide = rule.getRightHandSide().getRules();
            for(ProductionRuleSide oneRule : rightHandSide){
                Element elemRule = elemProductions.addElement("rule");
                Element elemLeft = elemRule.addElement("leftHandSide");
                for (Symbol symbol : leftHandSide) {
                    Element elemSymbol = elemLeft.addElement("symbol").addText(symbol.getName());
                }
                Element elemRight = elemRule.addElement("rightHandSide");
                for (Symbol symbol : oneRule.getSymbols()) {
                    Element elemSymbol = elemRight.addElement("symbol").addText(symbol.getName());
                }
            }
        }

        writeDocument(document, file);

    }






    public static void saveRegularExpression(RegularExpression regex, File file) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("jgaf");
        root.addAttribute("id", "RE");
        Element regexElem = root.addElement("regex");
        regexElem.addAttribute("name", regex.getName());
        regexElem.addElement("expression").addText(regex.writeAll());
        writeDocument(document, file);
    }





    
    private static void writeDocument(Document document, File file) throws IOException {
//        if(!file.getName().toLowerCase().endsWith(".xml")) {
//            outputFile = new File(file.getPath() + ".xml");
//        }    
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");          
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(
                new FileOutputStream(file), "UTF-8"), format);
        writer.write(document);
        writer.close();
    }

}
