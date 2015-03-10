/*
 * XMLDataManipulation.java
 *
 * Created on 27. srpen 2007, 15:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author hanis
 */
public class XMLDataManipulation {
    
    
    public static XMLDataManipulation instance = null;

    public static final String ROOT_PATH// = new File("").getAbsolutePath() + File.separator;
      
       = (new File(System.getProperties().getProperty("java.class.path").toString()).getAbsoluteFile()).getParent() + File.separator; 

  
    
    public static final String EXPORT_PATH = ROOT_PATH + "exports" + File.separator;        
    public static final String AUTOMATA_PATH = ROOT_PATH + "Automata" + File.separator;
    
    public static final String DATA_PATH = ROOT_PATH + "data" + File.separator;    
    public static final String TRANS_PATH = DATA_PATH + "transformations" + File.separator;
    public static final String CONFIGURE_PATH = DATA_PATH + "configure" + File.separator;
    public static final String TEMP_PATH = DATA_PATH + "temp" + File.separator;    
    public static final String TEMP_MODIFY_PATH = TEMP_PATH + "automataTemp.xml";    
    public static final String DESIGN_PATH = DATA_PATH + "design" + File.separator;
    public static final String DEFAULT_PATH = CONFIGURE_PATH + "defaultValues.xml";
    public static final String DESIGN = CONFIGURE_PATH + "applicationDesign.xml";

    
    private XMLDataManipulation() {
    }
        
    public static XMLDataManipulation getInstance() {
        if(instance == null) {
            return new XMLDataManipulation();
        } else {
            return instance;
        }
    }
    
    private Document getDocument(File file) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        return document;
    }
    
    private Document getDocument(String path) throws DocumentException {
        return getDocument(new File(path));
    }
    
    private void writeDocument(Document document, String path) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(path), format);
        writer.write(document);
        writer.close();    
    }    

    private void writeDocument(Document document, File file) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(file), format);
        writer.write(document);
        writer.close();
    }

    
    public PushdownAutomaton getAutomata(File file) throws DocumentException {
        PushdownAutomaton automaton = new PushdownAutomaton();

        Document document = getDocument(file);

        //automaton.setName(document.selectSingleNode("/jgaf/automaton/@name").getText());

        Element root = (Element) document.getRootElement().selectSingleNode("/jgaf/automaton");
        automaton.setName(document.selectSingleNode("//name").getText());
        automaton.setDescription(document.selectSingleNode("//description").getText());
        
        for (Iterator i = root.element("states").elementIterator("state"); i.hasNext(); ) {
            automaton.addStates(((Element) i.next()).getText());
        }        
        for (Iterator i = root.element("inputAlphabet").elementIterator("symbol"); i.hasNext(); ) {
            automaton.addInputAlphabet(((Element) i.next()).getText().charAt(0));
        }       
        for (Iterator i = root.element("stackAlphabet").elementIterator("stackSymbol"); i.hasNext(); ) {
            automaton.addStackAlphabet(((Element) i.next()).getText());
        }       

        TransitionFunction function = new TransitionFunction();
        for (Iterator i = root.element("transitionFunction").elementIterator("transition"); i.hasNext(); ) {
            Element transitionElement = (Element) i.next();
            Transition transition = new Transition();
            String state = transitionElement.selectSingleNode("state").getText();
            String stackSymbol = transitionElement.selectSingleNode("stackSymbol").getText();
            
            if(transitionElement.selectSingleNode("symbol").hasContent()) {
                char symbol = transitionElement.selectSingleNode("symbol").getText().charAt(0);
                transition.setTernary(new Ternary(state, symbol, stackSymbol));
            } else {
                transition.setTernary(new Ternary(state, stackSymbol));
            }                                    
            for (Iterator j = transitionElement.elementIterator("toPair"); j.hasNext(); ) {
                Element pairElement = (Element) j.next();
                String newState = pairElement.selectSingleNode("newState").getText();
                if(pairElement.selectSingleNode("newStackSymbols").hasContent()) {
                    List<String> stackSymbols = new ArrayList<String>();
                    for (Iterator k = pairElement.element("newStackSymbols").elementIterator("stackSymbol"); k.hasNext(); ) {
                        stackSymbols.add(((Element) k.next()).getText());
                    }
                    transition.addPair(new Pair(newState, stackSymbols));
                } else {
                    transition.addPair(new Pair(newState));
                }                
            }
            function.addTransition(transition);                        
        }       
        automaton.setTransitionFunction(function);
        automaton.setStartState(document.selectSingleNode("//startState").getText());
        automaton.setInitialStackSymbol(document.selectSingleNode("//initialStackSymbol").getText());
        
        for (Iterator i = root.element("finalStates").elementIterator("finalState"); i.hasNext(); ) {
            automaton.addFinalStates(((Element) i.next()).getText());
        }               
        //automaton.setPath(path);
        return automaton;
    }

    
    
    public void saveAutomaton(PushdownAutomaton automaton, File file) throws IOException {




        Document document = DocumentHelper.createDocument();
        //Element root = document.addElement("automata");
        
        Element jgafElem = document.addElement("jgaf");
        jgafElem.addAttribute("id", "PDA");
        Element root = jgafElem.addElement("automaton");        
        
        root.addElement("name").addText(automaton.getName());
        root.addElement("description").addText(automaton.getDescription());
        
        Element elemStates = root.addElement("states");
        for (String state : automaton.getStates()) {
            elemStates.addElement("state").addText(state);
        }
        Element elemAlphabet = root.addElement("inputAlphabet");
        for (Character input : automaton.getInputAlphabet()) {
            elemAlphabet.addElement("symbol").addText(String.valueOf(input));
        }
        Element elemStack = root.addElement("stackAlphabet");
        for (String stack : automaton.getStackAlphabet()) {
            elemStack.addElement("stackSymbol").addText(stack);
        }        
        
        Element elemFunction = root.addElement("transitionFunction");
        for (Transition transition : automaton.getTransitionFunction().getTransitions()) {
            Element elemTransition = elemFunction.addElement("transition");
            Ternary ternary = transition.getTernary();
            elemTransition.addElement("state").setText(ternary.getState());
            //elemTransition.addElement("symbol").setText(String.valueOf(ternary.getSymbol()));
            Element symbol = elemTransition.addElement("symbol");
            if(!ternary.isEpsilon()) {
                symbol.addText(String.valueOf(ternary.getSymbol()));
            }
            elemTransition.addElement("stackSymbol").setText(String.valueOf(ternary.getStackSymbol()));
            for (Pair pair : transition.getPairs()) {
                Element elemPair = elemTransition.addElement("toPair");
                elemPair.addElement("newState").addText(pair.getState());
                Element stackSymbols = elemPair.addElement("newStackSymbols");
                if(!pair.isEpsilonStack()) {
                    for (String stackSymbol : pair.getStackSymbols()) {
                        stackSymbols.addElement("stackSymbol").addText(stackSymbol);
                    }
                }
            }
        }
        root.addElement("startState").setText(automaton.getStartState());
        root.addElement("initialStackSymbol").setText(String.valueOf(automaton.getInitialStackSymbol()));
        Element elemFinal = root.addElement("finalStates");
        for (String finalState : automaton.getFinalStates()) {
            elemFinal.addElement("finalState").addText(finalState);
        }
  
        writeDocument(document, file);
    }

   
    
    
    
    
//
//    public DefaultValues getDefaultValues() throws DocumentException {
//        DefaultValues defaultValues = DefaultValues.getInstance();
//        Document document = getDocument(this.DEFAULT_PATH);
//        defaultValues.setEpsilon(document.selectSingleNode("//epsilon").getText());
//        defaultValues.setStatesSet(document.selectSingleNode("//statesSet").getText());
//        defaultValues.setStackAlphabet(document.selectSingleNode("//stackAlphabet").getText());
//        defaultValues.setInputAlphabet(document.selectSingleNode("//inputAlphabet").getText());
//        defaultValues.setFinalStates(document.selectSingleNode("//finalStates").getText());
//        defaultValues.setTransition(document.selectSingleNode("//transition").getText());
//
//        defaultValues.setName(document.selectSingleNode("//name").getText());
//        defaultValues.setDescription(document.selectSingleNode("//description").getText());
//        defaultValues.setDelay(Integer.parseInt(document.selectSingleNode("//delay").getText()));
//        defaultValues.setAccepting(Integer.parseInt(document.selectSingleNode("//accepting").getText()));
//        return defaultValues;
//    }
    
    
//    public void saveDefaultValues(DefaultValues defaultValues) throws IOException {
//        Document document = DocumentHelper.createDocument();
//        Element root = document.addElement("defaultValues");
//        root.addElement("epsilon").addText(defaultValues.getEpsilon());
//        root.addElement("statesSet").addText(defaultValues.getStatesSet());
//        root.addElement("stackAlphabet").addText(defaultValues.getStackAlphabet());
//        root.addElement("inputAlphabet").addText(defaultValues.getInputAlphabet());
//        root.addElement("finalStates").addText(defaultValues.getFinalStates());
//        root.addElement("transition").addText(defaultValues.getTransition());
//
//        root.addElement("name").addText(defaultValues.getName());
//        root.addElement("description").addText(defaultValues.getDescription());
//        root.addElement("delay").addText(String.valueOf(defaultValues.getDelay()));
//        root.addElement("accepting").addText(String.valueOf(defaultValues.getAccepting()));
//        writeDocument(document, this.DEFAULT_PATH);
//    }
 
    
    public void saveDesign() throws IOException {
        saveDesign(this.DESIGN);
    }
    
    public void saveDesign(String path) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("design");
        DesignManipulation designManipulation = DesignManipulation.getInstance();
        
        root.addElement("buttonUse").addText(String.valueOf(designManipulation.isButtonCustom()));
        root.addElement("inputUse").addText(String.valueOf(designManipulation.isInputCustom()));
        root.addElement("radioUse").addText(String.valueOf(designManipulation.isRadioCustom()));
        root.addElement("checkUse").addText(String.valueOf(designManipulation.isCheckCustom()));
        root.addElement("labelUse").addText(String.valueOf(designManipulation.isLabelCustom()));
        root.addElement("listUse").addText(String.valueOf(designManipulation.isListCustom()));
        root.addElement("panelUse").addText(String.valueOf(designManipulation.isPanelCustom()));
        root.addElement("titleUse").addText(String.valueOf(designManipulation.isTitleCustom()));
        
        root.addElement("buttonBack").addText(String.valueOf(designManipulation.getButtonBack().getRGB()));
        root.addElement("buttonFront").addText(String.valueOf(designManipulation.getButtonFront().getRGB()));
        root.addElement("inputBack").addText(String.valueOf(designManipulation.getInputBack().getRGB()));
        root.addElement("inputFront").addText(String.valueOf(designManipulation.getInputFront().getRGB()));
        root.addElement("radioFront").addText(String.valueOf(designManipulation.getRadioFront().getRGB()));
        root.addElement("checkFront").addText(String.valueOf(designManipulation.getCheckFront().getRGB()));        
        root.addElement("labelFront").addText(String.valueOf(designManipulation.getLabelFront().getRGB()));
        root.addElement("panelTitle").addText(String.valueOf(designManipulation.getPanelTitle().getRGB()));
        root.addElement("listBack").addText(String.valueOf(designManipulation.getListBack().getRGB()));
        root.addElement("listFront").addText(String.valueOf(designManipulation.getListFront().getRGB()));
        
        root.addElement("stackSymbolFront").addText(String.valueOf(designManipulation.getStackSymbolFront().getRGB()));
        root.addElement("stackSymbolBack").addText(String.valueOf(designManipulation.getStackSymbolBack().getRGB()));
        root.addElement("stackBottomFront").addText(String.valueOf(designManipulation.getStackBottomFront().getRGB()));
        root.addElement("stackBottomBack").addText(String.valueOf(designManipulation.getStackBottomBack().getRGB()));
        root.addElement("stackPanelBack").addText(String.valueOf(designManipulation.getStackPanelBack().getRGB()));
        root.addElement("stackStateFront").addText(String.valueOf(designManipulation.getStackStateFront().getRGB()));
        
        root.addElement("transOkBack").addText(String.valueOf(designManipulation.getTransOkBack().getRGB()));
        root.addElement("transOkFront").addText(String.valueOf(designManipulation.getTransOkFront().getRGB()));
        root.addElement("transNokBack").addText(String.valueOf(designManipulation.getTransNokBack().getRGB()));
        root.addElement("transNokFront").addText(String.valueOf(designManipulation.getTransNokFront().getRGB()));
        root.addElement("transPanelBack").addText(String.valueOf(designManipulation.getTransPanelBack().getRGB()));
                        
        root.addElement("wordPre").addText(String.valueOf(designManipulation.getWordPre().getRGB()));
        root.addElement("wordCurrent").addText(String.valueOf(designManipulation.getWordCurrent().getRGB()));
        root.addElement("wordPost").addText(String.valueOf(designManipulation.getWordPost().getRGB()));        
        root.addElement("wordPanel").addText(String.valueOf(designManipulation.getWordPanel().getRGB()));        
        
        root.addElement("panelBack").addText(String.valueOf(designManipulation.getPanelBack().getRGB()));
                                                
        root.addElement("matrixStackOkBack").addText(String.valueOf(designManipulation.getMatrixStackOkBack().getRGB()));
        root.addElement("matrixStackOkFront").addText(String.valueOf(designManipulation.getMatrixStackOkFront().getRGB()));
        root.addElement("matrixStackNokBack").addText(String.valueOf(designManipulation.getMatrixStackNokBack().getRGB()));
        root.addElement("matrixStackNokFront").addText(String.valueOf(designManipulation.getMatrixStackNokFront().getRGB()));
        root.addElement("matrixCornerBack").addText(String.valueOf(designManipulation.getMatrixCornerBack().getRGB()));
        root.addElement("matrixTransFromOkBack").addText(String.valueOf(designManipulation.getMatrixTransFromOkBack().getRGB()));
        root.addElement("matrixTransFromOkFront").addText(String.valueOf(designManipulation.getMatrixTransFromOkFront().getRGB()));
        root.addElement("matrixTransFromNokBack").addText(String.valueOf(designManipulation.getMatrixTransFromNokBack().getRGB()));
        root.addElement("matrixTransFromNokFront").addText(String.valueOf(designManipulation.getMatrixTransFromNokFront().getRGB()));
        root.addElement("matrixTransToOkBack").addText(String.valueOf(designManipulation.getMatrixTransToOkBack().getRGB()));
        root.addElement("matrixTransToOkFront").addText(String.valueOf(designManipulation.getMatrixTransToOkFront().getRGB()));
        root.addElement("matrixTransToNokBack").addText(String.valueOf(designManipulation.getMatrixTransToNokBack().getRGB()));
        root.addElement("matrixTransToNokFront").addText(String.valueOf(designManipulation.getMatrixTransToNokFront().getRGB()));
        root.addElement("matrixPanel").addText(String.valueOf(designManipulation.getMatrixPanel().getRGB()));                
       
        root.addElement("listBackOk").addText(String.valueOf(designManipulation.getListBackOk().getRGB()));        
        root.addElement("listFrontOk").addText(String.valueOf(designManipulation.getListFrontOk().getRGB()));        
        root.addElement("listBackNok").addText(String.valueOf(designManipulation.getListBackNok().getRGB()));        
        root.addElement("listFrontNok").addText(String.valueOf(designManipulation.getListFrontNok().getRGB()));                      
        
        
        writeDocument(document, path);
    }
    

    public void getDesign() throws DocumentException {
        getDesign(this.DESIGN);
    }
    
    public void getDesign(String path) throws DocumentException {
        Document document = null;
        document = getDocument(path);
        DesignManipulation designManipulation = DesignManipulation.getInstance();

        designManipulation.setButtonCustom(Boolean.valueOf(document.selectSingleNode("//buttonUse").getText()));
        designManipulation.setInputCustom(Boolean.valueOf(document.selectSingleNode("//inputUse").getText()));
        designManipulation.setRadioCustom(Boolean.valueOf(document.selectSingleNode("//radioUse").getText()));
        designManipulation.setCheckCustom(Boolean.valueOf(document.selectSingleNode("//checkUse").getText()));
        designManipulation.setLabelCustom(Boolean.valueOf(document.selectSingleNode("//labelUse").getText()));
        designManipulation.setListCustom(Boolean.valueOf(document.selectSingleNode("//listUse").getText()));
        designManipulation.setPanelCustom(Boolean.valueOf(document.selectSingleNode("//panelUse").getText()));
        designManipulation.setTitleCustom(Boolean.valueOf(document.selectSingleNode("//titleUse").getText()));

        designManipulation.setButtonBack(new Color(Integer.parseInt(document.selectSingleNode("//buttonBack").getText())));
        designManipulation.setButtonFront(new Color(Integer.parseInt(document.selectSingleNode("//buttonFront").getText())));
        designManipulation.setInputBack(new Color(Integer.parseInt(document.selectSingleNode("//inputBack").getText())));
        designManipulation.setInputFront(new Color(Integer.parseInt(document.selectSingleNode("//inputFront").getText())));
        designManipulation.setRadioFront(new Color(Integer.parseInt(document.selectSingleNode("//radioFront").getText())));
        designManipulation.setCheckFront(new Color(Integer.parseInt(document.selectSingleNode("//checkFront").getText())));
        designManipulation.setLabelFront(new Color(Integer.parseInt(document.selectSingleNode("//labelFront").getText())));
        designManipulation.setPanelTitle(new Color(Integer.parseInt(document.selectSingleNode("//panelTitle").getText())));
        designManipulation.setListBack(new Color(Integer.parseInt(document.selectSingleNode("//listBack").getText())));
        designManipulation.setListFront(new Color(Integer.parseInt(document.selectSingleNode("//listFront").getText())));

        designManipulation.setStackSymbolFront(new Color(Integer.parseInt(document.selectSingleNode("//stackSymbolFront").getText())));
        designManipulation.setStackSymbolBack(new Color(Integer.parseInt(document.selectSingleNode("//stackSymbolBack").getText())));
        designManipulation.setStackBottomFront(new Color(Integer.parseInt(document.selectSingleNode("//stackBottomFront").getText())));
        designManipulation.setStackBottomBack(new Color(Integer.parseInt(document.selectSingleNode("//stackBottomBack").getText())));
        designManipulation.setStackPanelBack(new Color(Integer.parseInt(document.selectSingleNode("//stackPanelBack").getText())));
        designManipulation.setStackStateFront(new Color(Integer.parseInt(document.selectSingleNode("//stackStateFront").getText())));

        designManipulation.setTransOkBack(new Color(Integer.parseInt(document.selectSingleNode("//transOkBack").getText())));
        designManipulation.setTransOkFront(new Color(Integer.parseInt(document.selectSingleNode("//transOkFront").getText())));
        designManipulation.setTransNokBack(new Color(Integer.parseInt(document.selectSingleNode("//transNokBack").getText())));
        designManipulation.setTransNokFront(new Color(Integer.parseInt(document.selectSingleNode("//transNokFront").getText())));
        designManipulation.setTransPanelBack(new Color(Integer.parseInt(document.selectSingleNode("//transPanelBack").getText())));

        designManipulation.setWordPre(new Color(Integer.parseInt(document.selectSingleNode("//wordPre").getText())));
        designManipulation.setWordCurrent(new Color(Integer.parseInt(document.selectSingleNode("//wordCurrent").getText())));
        designManipulation.setWordPost(new Color(Integer.parseInt(document.selectSingleNode("//wordPost").getText())));
        designManipulation.setWordPanel(new Color(Integer.parseInt(document.selectSingleNode("//wordPanel").getText())));

        designManipulation.setPanelBack(new Color(Integer.parseInt(document.selectSingleNode("//panelBack").getText())));

        designManipulation.setMatrixStackOkBack(new Color(Integer.parseInt(document.selectSingleNode("//matrixStackOkBack").getText())));
        designManipulation.setMatrixStackOkFront(new Color(Integer.parseInt(document.selectSingleNode("//matrixStackOkFront").getText())));
        designManipulation.setMatrixStackNokBack(new Color(Integer.parseInt(document.selectSingleNode("//matrixStackNokBack").getText())));
        designManipulation.setMatrixStackNokFront(new Color(Integer.parseInt(document.selectSingleNode("//matrixStackNokFront").getText())));
        designManipulation.setMatrixCornerBack(new Color(Integer.parseInt(document.selectSingleNode("//matrixCornerBack").getText())));
        designManipulation.setMatrixTransFromOkBack(new Color(Integer.parseInt(document.selectSingleNode("//matrixTransFromOkBack").getText())));
        designManipulation.setMatrixTransFromOkFront(new Color(Integer.parseInt(document.selectSingleNode("//matrixTransFromOkFront").getText())));
        designManipulation.setMatrixTransFromNokBack(new Color(Integer.parseInt(document.selectSingleNode("//matrixTransFromNokBack").getText())));
        designManipulation.setMatrixTransFromNokFront(new Color(Integer.parseInt(document.selectSingleNode("//matrixTransFromNokFront").getText())));
        designManipulation.setMatrixTransToOkBack(new Color(Integer.parseInt(document.selectSingleNode("//matrixTransToOkBack").getText())));
        designManipulation.setMatrixTransToOkFront(new Color(Integer.parseInt(document.selectSingleNode("//matrixTransToOkFront").getText())));
        designManipulation.setMatrixTransToNokBack(new Color(Integer.parseInt(document.selectSingleNode("//matrixTransToNokBack").getText())));
        designManipulation.setMatrixTransToNokFront(new Color(Integer.parseInt(document.selectSingleNode("//matrixTransToNokFront").getText())));
        designManipulation.setMatrixPanel(new Color(Integer.parseInt(document.selectSingleNode("//matrixPanel").getText())));

        designManipulation.setListBackOk(new Color(Integer.parseInt(document.selectSingleNode("//listBackOk").getText())));
        designManipulation.setListFrontOk(new Color(Integer.parseInt(document.selectSingleNode("//listFrontOk").getText())));
        designManipulation.setListBackNok(new Color(Integer.parseInt(document.selectSingleNode("//listBackNok").getText())));
        designManipulation.setListFrontNok(new Color(Integer.parseInt(document.selectSingleNode("//listFrontNok").getText())));
    }
    
    
    
    
    private  Document createSimpleAutomata(PushdownAutomaton automata) throws IOException {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("automata");
        root.addElement("name").addText(automata.getName());
        root.addElement("description").addText(automata.getDescription());
        root.addElement("seven").addText(automata.definitionSeven());
        root.addElement("stateSymbol").addText(DefaultValues.getInstance().getStatesSet());
        root.addElement("states").addText(automata.writeStates());

        root.addElement("alphabetSymbol").addText(DefaultValues.getInstance().getInputAlphabet());
        root.addElement("alphabet").addText(automata.writeInputAlphabet());
        
        root.addElement("stackSymbol").addText(DefaultValues.getInstance().getStackAlphabet());
        root.addElement("ctack").addText(automata.writeStackAlphabet());

        root.addElement("finalSymbol").addText(DefaultValues.getInstance().getFinalStates());
        root.addElement("final").addText(automata.writeFinalStates());
        
                
        root.addElement("transSymbol").addText(DefaultValues.getInstance().getTransition());        
        
        Element elemTrans = root.addElement("transitions");
        for (Transition trans : automata.getTransitionFunction().getTransitions()) {
            elemTrans.addElement("trans").addText(trans.toString());
        }        
        return document;        
    }
        
    
    public void createHTML(PushdownAutomaton automata, String path) throws AutomataException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = factory.newTransformer(new StreamSource(new File(TRANS_PATH + "automataToHTML.xsl")));
            transformer.transform(new DocumentSource(createSimpleAutomata(automata)), new StreamResult(new FileWriter(path + ".html")));
        } catch (TransformerException ex) {
            throw new AutomataException(L12N.getValue("warn.transform.error"));
        } catch (IOException ex) {
            throw new AutomataException(L12N.getValue("warn.io.error"));
        }
        
    }


}
