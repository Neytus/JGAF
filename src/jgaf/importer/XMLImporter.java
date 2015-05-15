/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.importer;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.JgafFileException;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.automaton.fa.CanvasLabel;
import jgaf.editor.EditorDescriptor;
import jgaf.grammar.Epsilon;
import jgaf.grammar.Grammar;
import jgaf.grammar.Nonterminal;
import jgaf.grammar.ProductionRuleSide;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.ProductionRulesSide;
import jgaf.grammar.Symbol;
import jgaf.grammar.Terminal;
import jgaf.procedure.ProcedureDescriptor;
import jgaf.procedure.ProcedureParameter;
import jgaf.procedure.ProcedureRepresentation;
import jgaf.regex.RegularExpression;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author hanis
 */
public class XMLImporter {

    private static Document getDocument(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }


    public static String getRepresentationId(File file) throws DocumentException, JgafFileException {
        Document document = XMLImporter.getDocument(file);
        Node idNode = document.selectSingleNode("/jgaf/@id");
        if(idNode == null) {
            throw new JgafFileException("The file is not compatible with JGAF.");
        }
        String id = idNode.getText();
        if(id == null) {
            throw new JgafFileException("Editor ID not found");
        }
        return id;
    }


    public static Automaton getAutomaton(File file) throws DocumentException {
        Automaton automaton = new Automaton();
        Document document = getDocument(file);

        automaton.setName(document.selectSingleNode("/jgaf/automaton/@name").getText());

        List list = document.selectNodes("//states/state");
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            Element element=(Element)iter.next();
            State state = new State(element.getText());
            String x = element.attributeValue("x");
            if(x != null) {
                try {
                    state.getVisualProperties().setXPos(Integer.valueOf(x));
                } catch (NumberFormatException ex) {
                }
            }
            String y = element.attributeValue("y");
            if(y != null) {
                try {
                    state.getVisualProperties().setYPos(Integer.valueOf(y));
                } catch (NumberFormatException ex) {
                }
            }

            String fillColor = element.attributeValue("fill-color");
            if(fillColor != null) {
                try {
                    state.getVisualProperties().setFillColor(new Color(Integer.valueOf(fillColor)));
                } catch (NumberFormatException ex) {
                }
            }
            String strokeColor = element.attributeValue("stroke-color");
            if(strokeColor != null) {
                try {
                    state.getVisualProperties().setStrokeColor(new Color(Integer.valueOf(strokeColor)));
                } catch (NumberFormatException ex) {
                }
            }
            String fontColor = element.attributeValue("font-color");
            if(fontColor != null) {
                try {
                    state.getVisualProperties().setFontColor(new Color(Integer.valueOf(fontColor)));
                } catch (NumberFormatException ex) {
                }
            }




            automaton.addState(state);
        }
        String initialState = document.selectSingleNode("//initialState").getText();
        if(initialState != null && !initialState.equals("")) {
            automaton.setInitialState(automaton.getStateByName(initialState));
        }
        
        list = document.selectNodes("//transitions/transition");
        iter = list.iterator();
        while (iter.hasNext()) {
            Element element=(Element)iter.next();            
            String fromStateString = element.element("fromState").getText();
            String toStateString = element.element("toState").getText();
            
            Transition transition;
            State fromState = null;
            State toState = null;
            if(fromStateString != null) {
                fromState = automaton.getStateByName(fromStateString);
            }
            if(toStateString != null) {
                toState = automaton.getStateByName(toStateString);
            }
            List labelElements = element.selectNodes("label");
            Iterator labelIter = labelElements.iterator();
            SortedSet labels = new TreeSet<String>();
            while (labelIter.hasNext()) {
                labels.add(((Element)labelIter.next()).getText());
            }
            if(fromState != null && toState != null && !labels.isEmpty()) {
                transition = new Transition(fromState, toState, labels);
                automaton.addTransition(transition);
            String curveFactor = element.attributeValue("curve");
            if(curveFactor != null) {
                try {
                    transition.getVisualProperties().setCurveFactor(Double.valueOf(curveFactor));
                } catch (NumberFormatException ex) {
                }
            }

            String fontColor = element.attributeValue("font-color");
            if(fontColor != null) {
                try {
                    transition.getVisualProperties().setFontColor(new Color(Integer.valueOf(fontColor)));
                } catch (NumberFormatException ex) {
                }
            }

            String strokeColor = element.attributeValue("stroke-color");
            if(strokeColor != null) {
                try {
                    transition.getVisualProperties().setStrokeColor(new Color(Integer.valueOf(strokeColor)));
                } catch (NumberFormatException ex) {
                }
            }


            } else {
                System.out.println("ERR1");
            }
        }
        
        list = document.selectNodes("//finalStates/state");
        iter = list.iterator();
        while (iter.hasNext()) {
            Element element=(Element)iter.next();
            State state = new State(element.getText());
            automaton.addAcceptingState(state);
        }
        return automaton;

    }








    public static List<CanvasLabel> getLabels(File file) throws DocumentException {
        Document document = getDocument(file);
        List<CanvasLabel> labels = new ArrayList<CanvasLabel>();
        List list = document.selectNodes("//labels/label");
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            Element element=(Element)iter.next();
            CanvasLabel label = new CanvasLabel(element.getText());
            String x = element.attributeValue("x");
            if(x != null) {
                try {
                    label.setXPos(Integer.valueOf(x));
                } catch (NumberFormatException ex) {
                }
            }
            String y = element.attributeValue("y");
            if(y != null) {
                try {
                    label.setYPos(Integer.valueOf(y));
                } catch (NumberFormatException ex) {
                }
            }

            String bgColor = element.attributeValue("bg-color");
            if(bgColor != null) {
                try {
                    label.setBackgroundColor(new Color(Integer.valueOf(bgColor)));
                } catch (NumberFormatException ex) {
                }
            }

            String fgColor = element.attributeValue("fg-color");
            if(fgColor != null) {
                try {
                    label.setForegroundColor(new Color(Integer.valueOf(fgColor)));
                } catch (NumberFormatException ex) {
                }
            }

            String rotation = element.attributeValue("rotation");
            if(rotation != null) {
                try {
                    label.setRotationFactor(Integer.valueOf(rotation));
                } catch (NumberFormatException ex) {
                }
            }

            String transparency = element.attributeValue("transparency");
            if(transparency != null) {
                try {
                    label.setTransparency(Integer.valueOf(transparency));
                } catch (NumberFormatException ex) {
                }
            }

            String fontName = element.attributeValue("font-name");
            String fontStyle = element.attributeValue("font-style");
            String fontSize = element.attributeValue("font-size");
            int size = 0;
            int style = 0;
                try {
                    size = Integer.valueOf(fontSize);
                    style = Integer.valueOf(fontStyle);
                } catch (NumberFormatException ex) {
                }
            Font font = new Font(fontName, style, size);
            label.setFont(font);

            labels.add(label);
        }




        return labels;

    }




    public static Grammar getGrammar(File file) throws DocumentException {
        Grammar grammar = new Grammar();
        Document document = getDocument(file);

        grammar.setName(document.selectSingleNode("/jgaf/grammar/@name").getText());

        Node grammarNode = document.selectSingleNode("/jgaf/grammar");
        String startNonterminal = grammarNode.selectSingleNode("startNonterminal").getText();
        grammar.setStartNonterminal(new Nonterminal(startNonterminal));


        Iterator iterator;

        List<Symbol> nonterminals = new ArrayList<Symbol>();
        List nonterminalList = grammarNode.selectNodes("nonterminals/nonterminal");
        iterator = nonterminalList.iterator();
        while (iterator.hasNext()) {
            Element element=(Element)iterator.next();
            nonterminals.add(new Nonterminal(element.getText()));
        }

        List<Symbol> terminals = new ArrayList<Symbol>();
        List terminalList = grammarNode.selectNodes("terminals/terminal");
        iterator = terminalList.iterator();
        while (iterator.hasNext()) {
            Element element=(Element)iterator.next();
            terminals.add(new Terminal(element.getText()));
        }

        List list = grammarNode.selectNodes("productions/rule");
        iterator = list.iterator();
        while (iterator.hasNext()) {
            Element element=(Element)iterator.next();            
            Iterator iterator2;

            ProductionRuleSide leftRuleSide = new ProductionRuleSide();
            Node leftSideNode = element.selectSingleNode("leftHandSide");
            List leftSideSymbols = leftSideNode.selectNodes("symbol");
            iterator2 = leftSideSymbols.iterator();
            while (iterator2.hasNext()) {
                Element symbolElem = (Element) iterator2.next();
                Symbol symbol = new Symbol(symbolElem.getText(), Symbol.NONTERMINAL);
                if (terminals.contains(symbol)) {
                    symbol.setType(Symbol.TERMINAL);
                }  else if (!nonterminals.contains(symbol)) {
                    System.out.println("ERR1");
                }
                leftRuleSide.addSymbol(symbol);
            }


            ProductionRulesSide rightRuleSide = new ProductionRulesSide();
            ProductionRuleSide helpRule = new ProductionRuleSide();
            Node rightSideNode = element.selectSingleNode("rightHandSide");
            List rightSideSymbols = rightSideNode.selectNodes("symbol");
            iterator2 = rightSideSymbols.iterator();
            while (iterator2.hasNext()) {
                Element symbolElem = (Element) iterator2.next();
                Symbol symbol = new Symbol(symbolElem.getText(), Symbol.NONTERMINAL);
                if (terminals.contains(symbol)) {
                    symbol.setType(Symbol.TERMINAL);
                }else if (!nonterminals.contains(symbol)) {
                    symbol.setType(Symbol.EPSILON);
                }
                helpRule.addSymbol(symbol);
            }
            rightRuleSide.addRule(helpRule);
            grammar.addRule(new ProductionRules(leftRuleSide, rightRuleSide));

        }
        return grammar;
    }

    public static RegularExpression getRegularExpression(File file) throws DocumentException {
        Document document = getDocument(file);
        Node regexNode = document.selectSingleNode("/jgaf/regex");
        String expression = regexNode.selectSingleNode("expression").getText();
        RegularExpression regex = new RegularExpression(expression);
        Node node = document.selectSingleNode("/jgaf/regex/@name");
        if (node != null) {
            regex.setName(node.getText());
        }
        return regex;
    }

    
    
    public static List<ProcedureDescriptor> getProcedureDescriptors(File file) throws DocumentException {
        Document document = getDocument(file);

        List<ProcedureDescriptor> descriptorList = new ArrayList<ProcedureDescriptor>();
        List procedureNodes = document.selectNodes("/procedures/procedure");
        Iterator iterator2 = procedureNodes.iterator();
        while (iterator2.hasNext()) {
            Element procedureElem = (Element) iterator2.next();
            String type = "A";
            Node typeNode = procedureElem.selectSingleNode("@type");
            if(typeNode != null) {
                type = typeNode.getText();
            }
            ProcedureDescriptor descriptor = new ProcedureDescriptor();
            descriptor.setType(type);
            descriptor.setName(procedureElem.selectSingleNode("name").getText());
            descriptor.setDescription(procedureElem.selectSingleNode("description").getText());
            descriptor.setClassPath(procedureElem.selectSingleNode("classPath").getText());
            
            //INPUT REPRESENTATIONS
            List inputNodes = procedureElem.selectNodes("inputs/input");
            Iterator iterator = inputNodes.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String id = element.selectSingleNode("id").getText();
                String description = element.selectSingleNode("description").getText();
                ProcedureRepresentation representation = new ProcedureRepresentation();
                representation.setId(id);
                representation.setDescription(description);
                descriptor.addInputRepresentation(representation);
            }
            //PARAMETERS
            inputNodes = procedureElem.selectNodes("parameters/parameter");
            iterator = inputNodes.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String description = element.selectSingleNode("description").getText();
                ProcedureParameter parameter = new ProcedureParameter();
                parameter.setDescription(description);
                descriptor.addParameter(parameter);
            
                //PARAMETER WITH FORCED OPTIONS, added with lr extension
                List optionNodes = element.selectNodes("poptions/poption");
                if (!optionNodes.isEmpty()){
                    ArrayList<String> forcedOptions = new ArrayList<String>();
                    Iterator oiterator = optionNodes.iterator();
                while(oiterator.hasNext()) {
                    Node name =(Node) oiterator.next();
                    forcedOptions.add(name.getText());
                }
                    parameter.setForcedOptions(forcedOptions);
                }
            }
            //OUTPUT REPRESENTATION
            Node outputNode = procedureElem.selectSingleNode("output");
            if (outputNode != null) {
                String id = outputNode.selectSingleNode("id").getText();
                String description = outputNode.selectSingleNode("description").getText();
                ProcedureRepresentation representation = new ProcedureRepresentation();
                representation.setId(id);
                representation.setDescription(description);
                descriptor.setOutputRepresentation(representation);
            }

            descriptorList.add(descriptor);
        }
        return descriptorList;
    }




    public static List<EditorDescriptor> getEditorDescriptors(File file) throws DocumentException {
        Document document = getDocument(file);

        List<EditorDescriptor> descriptorList = new ArrayList<EditorDescriptor>();
        List procedureNodes = document.selectNodes("/editors/editor");
        Iterator iterator = procedureNodes.iterator();
        while (iterator.hasNext()) {
            Element procedureElem = (Element) iterator.next();
            EditorDescriptor descriptor = new EditorDescriptor();
            descriptor.setId(procedureElem.selectSingleNode("@id").getText());
            descriptor.setName(procedureElem.selectSingleNode("name").getText());
            descriptor.setDescription(procedureElem.selectSingleNode("description").getText());
            descriptor.setClassPath(procedureElem.selectSingleNode("classPath").getText());
            descriptorList.add(descriptor);
        }
        return descriptorList;
    }





}

