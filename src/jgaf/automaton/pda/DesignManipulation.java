/*
 * DesignManipulation.java
 *
 * Created on 2. září 2007, 3:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author hanis
 */
public class DesignManipulation {
    
    private static DesignManipulation instance;
    
    //******* get default color for common components******************************88
    private static final Color panelBackDef = (new JPanel()).getBackground();    
    private static final Color labelFrontDef = (new JLabel()).getForeground();    
    private static final Color inputFrontDef = (new JTextField()).getForeground();
    private static final Color inputBackDef = (new JTextField()).getBackground();    
    private static final Color buttonFrontDef = (new JButton()).getForeground();
    private static final Color buttonBackDef = (new JButton()).getBackground();
    private static final Color radioFrontDef = (new JRadioButton()).getForeground();
    private static final Color checkFrontDef = (new JCheckBox()).getForeground();
    private static final Color panelTitleDef = (new JLabel()).getForeground();
    //***********************************************************************************8
    


//
//  <panelTitle>-103</panelTitle>
//  <wordPre>-10092544</wordPre>
//  <wordCurrent>-65536</wordCurrent>
//  <wordPost>-6750208</wordPost>
//  <wordPanel>-16777216</wordPanel>
//  <panelBack>-13421773</panelBack>
//  <matrixStackOkBack>-3407872</matrixStackOkBack>
//  <matrixStackOkFront>-256</matrixStackOkFront>
//  <matrixStackNokBack>-10092544</matrixStackNokBack>
//  <matrixStackNokFront>-1</matrixStackNokFront>
//  <matrixCornerBack>-13826554</matrixCornerBack>
//  <matrixTransFromOkBack>-3407872</matrixTransFromOkBack>
//  <matrixTransFromOkFront>-256</matrixTransFromOkFront>
//  <matrixTransFromNokBack>-10092544</matrixTransFromNokBack>
//  <matrixTransFromNokFront>-1</matrixTransFromNokFront>
//  <matrixTransToOkBack>-3407872</matrixTransToOkBack>
//  <matrixTransToOkFront>-256</matrixTransToOkFront>
//  <matrixTransToNokBack>-15329258</matrixTransToNokBack>
//  <matrixTransToNokFront>-1</matrixTransToNokFront>
//  <matrixPanel>-16777216</matrixPanel>
//  <listBackOk>-52429</listBackOk>
//  <listFrontOk>-16777216</listFrontOk>
//  <listBackNok>-16777216</listBackNok>
//  <listFrontNok>-52</listFrontNok>



    private Color listFront = new Color(-1);
    private Color listBack = new Color(-13434880);



    private Color panelBack = new Color(-2694442);
    
    private Color labelFront = new Color(-1);
    
    private Color inputFront = new Color(-1);
    private Color inputBack = new Color(-16777216);


    private Color buttonFront = new Color(-1);
    private Color buttonBack = new Color(-13434880);

    private Color radioFront = new Color(-1);
    private Color checkFront = new Color(-1);
    
    private Color panelTitle = null;



    private Color stackSymbolFront = new Color(-1);
    private Color stackSymbolBack = new Color(-10092544);
    private Color stackBottomFront = new Color(-256);
    private Color stackBottomBack = new Color(-65536);
    private Color stackPanelBack = new Color(-15593199);
    private Color stackStateFront = new Color(-1);
    


    private Color transOkBack = Color.ORANGE;
    private Color transOkFront = Color.BLUE;
    private Color transNokBack = Color.GRAY;
    private Color transNokFront = Color.BLACK;
    private Color transPanelBack = Color.BLACK;
    
    private Color wordPre = Color.BLACK;
    private Color wordCurrent = Color.RED;
    private Color wordPost = Color.WHITE;
    private Color wordPanel = Color.BLACK;
    
    
    
    private Color matrixStackOkBack = null;
    private Color matrixStackOkFront = null;    
    private Color matrixStackNokBack = null;
    private Color matrixStackNokFront = null;
    private Color matrixCornerBack = null;    
    private Color matrixTransFromOkBack = null;
    private Color matrixTransFromOkFront = null;    
    private Color matrixTransFromNokBack = null;
    private Color matrixTransFromNokFront = null;
    private Color matrixTransToOkBack = null;
    private Color matrixTransToOkFront = null;
    private Color matrixTransToNokBack = null;
    private Color matrixTransToNokFront = null;    
    private Color matrixPanel = null;


    
    private Color listFrontOk = null;
    private Color listBackOk = null;
    private Color listFrontNok = null;
    private Color listBackNok = null;
    
    
    
    private boolean listCustom = true;
    private boolean labelCustom = true;
    private boolean buttonCustom = false;
    private boolean panelCustom = true;
    private boolean inputCustom = true;
    private boolean radioCustom = true;
    private boolean checkCustom = true;
    private boolean titleCustom = true;
    


    
    private DesignManipulation() {
    }
    
    public static DesignManipulation getInstance() {
        if(instance == null) {           
            instance = new DesignManipulation();
        }
        return instance;
    }
    
    public void setDesign(Component component) {
        setDesign(component, true);
    }
    
    public void setDesign(Component component, boolean first) {

        if(first) {
            if(isPanelCustom()) {
                component.setBackground(getPanelBack());
            } else {
                component.setBackground(getPanelBackDef());
            }
        }

        if(component instanceof JLabel) {
            if(isPanelCustom()) {
                component.setBackground(getPanelBack());
            } else {
                component.setBackground(getPanelBackDef());
            }
            if(isLabelCustom()) {
                component.setForeground(getLabelFront());
            } else {
                component.setForeground(getLabelFrontDef());
            }

        } else if(component instanceof JRadioButton) {
            if(isPanelCustom()) {
                component.setBackground(getPanelBack());
            } else {
                component.setBackground(getPanelBackDef());
            }
            if(isRadioCustom()) {
                component.setForeground(getRadioFront());
            } else {
                component.setForeground(getRadioFrontDef());
            }

        } else if(component instanceof JCheckBox) {
            if(isPanelCustom()) {
                component.setBackground(getPanelBack());
            } else {
                component.setBackground(getPanelBackDef());
            }
            if(isCheckCustom()) {
                component.setForeground(getCheckFront());
            } else {
                component.setForeground(getCheckFrontDef());
            }

        } else if(component instanceof JPanel) {
            Border border = ((JPanel) component).getBorder();
            if(border instanceof TitledBorder) {
                if(isTitleCustom()) {
                    ((TitledBorder) border).setTitleColor(getPanelTitle());
                } else {
                    ((TitledBorder) border).setTitleColor(getPanelTitleDef());
                }
            }
            if(isPanelCustom()) {
                component.setBackground(getPanelBack());
            } else {
                component.setBackground(getPanelBackDef());
            }
            if(isLabelCustom()) {
                component.setForeground(getLabelFront());
            } else {
                component.setForeground(getLabelFrontDef());
            }
            for (Component comp : ((JPanel)component).getComponents()) {
                setDesign(comp, false);
            }

        } else if(component instanceof JButton) {
            if(isButtonCustom()) {
                component.setForeground(getButtonFront());
                component.setBackground(getButtonBack());
            } else {
                component.setForeground(getButtonFrontDef());
                component.setBackground(getButtonBackDef());
            }

        } else if(component instanceof JTextField) {
            if(isInputCustom()) {
                component.setForeground(getInputFront());
                component.setBackground(getInputBack());
            } else {
                component.setForeground(getInputFrontDef());
                component.setBackground(getInputBackDef());
            }

        } else if(component instanceof JScrollPane) {
            JScrollPane scrool = (JScrollPane) component;
            for (Component comp : scrool.getViewport().getComponents()) {
                if(comp instanceof JTextArea) {
                    comp.setBackground(getListBack());
                    comp.setForeground(getListFront());
                }
            }

        } else if(component instanceof JTabbedPane) {
            for (Component comp : ((JTabbedPane)component).getComponents()) {
                setDesign(comp, false);
            }
        }
//        else if(component instanceof JTextArea && isPanelCustom()) {
//            component.setBackground(Color.BLACK);
//            component.setForeground(Color.GREEN);
//        } else if(component instanceof JList && isPanelCustom()) {
//            component.setBackground(getListBack());
//            component.setForeground(getListFront());
//        }

    }



    public Color getListFront() {
        return listFront;
    }

    public void setListFront(Color listFront) {
        this.listFront = listFront;
    }

    public Color getListBack() {
        return listBack;
    }

    public void setListBack(Color listBack) {
        this.listBack = listBack;
    }

    public Color getPanelBack() {
        return panelBack;
    }

    public void setPanelBack(Color panelBack) {
        this.panelBack = panelBack;
    }

    public Color getLabelFront() {
        return labelFront;
    }

    public void setLabelFront(Color labelFront) {
        this.labelFront = labelFront;
    }

    public Color getInputFront() {
        return inputFront;
    }

    public void setInputFront(Color inputFront) {
        this.inputFront = inputFront;
    }

    public Color getInputBack() {
        return inputBack;
    }

    public void setInputBack(Color inputBack) {
        this.inputBack = inputBack;
    }

    public Color getButtonFront() {
        return buttonFront;
    }

    public void setButtonFront(Color buttonFront) {
        this.buttonFront = buttonFront;
    }

    public Color getButtonBack() {
        return buttonBack;
    }

    public void setButtonBack(Color buttonBack) {
        this.buttonBack = buttonBack;
    }

    public Color getRadioFront() {
        return radioFront;
    }

    public void setRadioFront(Color radioFront) {
        this.radioFront = radioFront;
    }

    public Color getCheckFront() {
        return checkFront;
    }

    public void setCheckFront(Color checkFront) {
        this.checkFront = checkFront;
    }

    public boolean isListCustom() {
        return listCustom;
    }

    public void setListCustom(boolean listCustom) {
        this.listCustom = listCustom;
    }


    public boolean isLabelCustom() {
        return labelCustom;
    }

    public void setLabelCustom(boolean labelCustom) {
        this.labelCustom = labelCustom;
    }

    public boolean isButtonCustom() {
        return buttonCustom;
    }

    public void setButtonCustom(boolean buttonCustom) {
        this.buttonCustom = buttonCustom;
    }

    public boolean isPanelCustom() {
        return panelCustom;
    }

    public void setPanelCustom(boolean panelCustom) {
        this.panelCustom = panelCustom;
    }

    public boolean isInputCustom() {
        return inputCustom;
    }

    public void setInputCustom(boolean inputCustom) {
        this.inputCustom = inputCustom;
    }

    public boolean isRadioCustom() {
        return radioCustom;
    }

    public void setRadioCustom(boolean radioCustom) {
        this.radioCustom = radioCustom;
    }

    public boolean isCheckCustom() {
        return checkCustom;
    }

    public void setCheckCustom(boolean checkCustom) {
        this.checkCustom = checkCustom;
    }

    public static Color getPanelBackDef() {
        return panelBackDef;
    }

    public static Color getLabelFrontDef() {
        return labelFrontDef;
    }

    public static Color getInputFrontDef() {
        return inputFrontDef;
    }

    public static Color getInputBackDef() {
        return inputBackDef;
    }

    public static Color getButtonFrontDef() {
        return buttonFrontDef;
    }

    public static Color getButtonBackDef() {
        return buttonBackDef;
    }

    public static Color getRadioFrontDef() {
        return radioFrontDef;
    }

    public static Color getCheckFrontDef() {
        return checkFrontDef;
    }

    public Color getStackSymbolFront() {
        return stackSymbolFront;
    }

    public void setStackSymbolFront(Color stackSymbolFront) {
        this.stackSymbolFront = stackSymbolFront;
    }

    public Color getStackSymbolBack() {
        return stackSymbolBack;
    }

    public void setStackSymbolBack(Color stackSymbolBack) {
        this.stackSymbolBack = stackSymbolBack;
    }

    public Color getStackBottomFront() {
        return stackBottomFront;
    }

    public void setStackBottomFront(Color stackBottomFront) {
        this.stackBottomFront = stackBottomFront;
    }

    public Color getStackBottomBack() {
        return stackBottomBack;
    }

    public void setStackBottomBack(Color stackBottomBack) {
        this.stackBottomBack = stackBottomBack;
    }

    public Color getStackPanelBack() {
        return stackPanelBack;
    }

    public void setStackPanelBack(Color stackPanelBack) {
        this.stackPanelBack = stackPanelBack;
    }

    public Color getStackStateFront() {
        return stackStateFront;
    }

    public void setStackStateFront(Color stackStateFront) {
        this.stackStateFront = stackStateFront;
    }

    public Color getTransOkBack() {
        return transOkBack;
    }

    public void setTransOkBack(Color transOkBack) {
        this.transOkBack = transOkBack;
    }

    public Color getTransOkFront() {
        return transOkFront;
    }

    public void setTransOkFront(Color transOkFront) {
        this.transOkFront = transOkFront;
    }

    public Color getTransNokBack() {
        return transNokBack;
    }

    public void setTransNokBack(Color transNokBack) {
        this.transNokBack = transNokBack;
    }

    public Color getTransNokFront() {
        return transNokFront;
    }

    public void setTransNokFront(Color transNokFront) {
        this.transNokFront = transNokFront;
    }

    public Color getTransPanelBack() {
        return transPanelBack;
    }

    public void setTransPanelBack(Color transPanelBack) {
        this.transPanelBack = transPanelBack;
    }

    public Color getWordPre() {
        return wordPre;
    }

    public void setWordPre(Color wordPre) {
        this.wordPre = wordPre;
    }

    public Color getWordCurrent() {
        return wordCurrent;
    }

    public void setWordCurrent(Color wordCurrent) {
        this.wordCurrent = wordCurrent;
    }

    public Color getWordPost() {
        return wordPost;
    }

    public void setWordPost(Color wordPost) {
        this.wordPost = wordPost;
    }

    public Color getWordPanel() {
        return wordPanel;
    }

    public void setWordPanel(Color wordPanel) {
        this.wordPanel = wordPanel;
    }

    public boolean isTitleCustom() {
        return titleCustom;
    }

    public void setTitleCustom(boolean titleCustom) {
        this.titleCustom = titleCustom;
    }

    public Color getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(Color panelTitle) {
        this.panelTitle = panelTitle;
    }

    public static Color getPanelTitleDef() {
        return panelTitleDef;
    }

    public Color getMatrixStackOkBack() {
        return matrixStackOkBack;
    }

    public void setMatrixStackOkBack(Color matrixStackOkBack) {
        this.matrixStackOkBack = matrixStackOkBack;
    }

    public Color getMatrixStackOkFront() {
        return matrixStackOkFront;
    }

    public void setMatrixStackOkFront(Color matrixStackOkFront) {
        this.matrixStackOkFront = matrixStackOkFront;
    }

    public Color getMatrixStackNokBack() {
        return matrixStackNokBack;
    }

    public void setMatrixStackNokBack(Color matrixStackNokBack) {
        this.matrixStackNokBack = matrixStackNokBack;
    }

    public Color getMatrixStackNokFront() {
        return matrixStackNokFront;
    }

    public void setMatrixStackNokFront(Color matrixStackNokFront) {
        this.matrixStackNokFront = matrixStackNokFront;
    }

    public Color getMatrixCornerBack() {
        return matrixCornerBack;
    }

    public void setMatrixCornerBack(Color matrixCornerBack) {
        this.matrixCornerBack = matrixCornerBack;
    }

    public Color getMatrixTransFromOkBack() {
        return matrixTransFromOkBack;
    }

    public void setMatrixTransFromOkBack(Color matrixTransFromOkBack) {
        this.matrixTransFromOkBack = matrixTransFromOkBack;
    }

    public Color getMatrixTransFromOkFront() {
        return matrixTransFromOkFront;
    }

    public void setMatrixTransFromOkFront(Color matrixTransFromOkFront) {
        this.matrixTransFromOkFront = matrixTransFromOkFront;
    }

    public Color getMatrixTransFromNokBack() {
        return matrixTransFromNokBack;
    }

    public void setMatrixTransFromNokBack(Color matrixTransFromNokBack) {
        this.matrixTransFromNokBack = matrixTransFromNokBack;
    }

    public Color getMatrixTransFromNokFront() {
        return matrixTransFromNokFront;
    }

    public void setMatrixTransFromNokFront(Color matrixTransFromNokFront) {
        this.matrixTransFromNokFront = matrixTransFromNokFront;
    }

    public Color getMatrixTransToOkBack() {
        return matrixTransToOkBack;
    }

    public void setMatrixTransToOkBack(Color matrixTransToOkBack) {
        this.matrixTransToOkBack = matrixTransToOkBack;
    }

    public Color getMatrixTransToOkFront() {
        return matrixTransToOkFront;
    }

    public void setMatrixTransToOkFront(Color matrixTransToOkFront) {
        this.matrixTransToOkFront = matrixTransToOkFront;
    }

    public Color getMatrixTransToNokBack() {
        return matrixTransToNokBack;
    }

    public void setMatrixTransToNokBack(Color matrixTransToNokBack) {
        this.matrixTransToNokBack = matrixTransToNokBack;
    }

    public Color getMatrixTransToNokFront() {
        return matrixTransToNokFront;
    }

    public void setMatrixTransToNokFront(Color matrixTransToNokFront) {
        this.matrixTransToNokFront = matrixTransToNokFront;
    }

    public Color getMatrixPanel() {
        return matrixPanel;
    }

    public void setMatrixPanel(Color matrixPanel) {
        this.matrixPanel = matrixPanel;
    }

    public Color getListFrontOk() {
        return listFrontOk;
    }

    public void setListFrontOk(Color listFrontOk) {
        this.listFrontOk = listFrontOk;
    }

    public Color getListBackOk() {
        return listBackOk;
    }

    public void setListBackOk(Color listBackOk) {
        this.listBackOk = listBackOk;
    }

    public Color getListFrontNok() {
        return listFrontNok;
    }

    public void setListFrontNok(Color listFrontNok) {
        this.listFrontNok = listFrontNok;
    }

    public Color getListBackNok() {
        return listBackNok;
    }

    public void setListBackNok(Color listBackNok) {
        this.listBackNok = listBackNok;
    }
    
}
