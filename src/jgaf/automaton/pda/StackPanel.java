/*
 * StackPanel.java
 *
 * Created on 8. únor 2008, 15:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author hanis
 */
public class StackPanel extends JPanel {
        
    public StackPanel(String bottom) {
        setLayout(new GridLayout(0,1,1,1));
        if(bottom != null) {
            setBottomSymbol(bottom);
        }
    }
    
    public StackPanel(Configuration configuration) {
        this(configuration.getStack().bottom());
        for (int i = configuration.getStack().length() - 2; i >= 0; i--) {
            addSymbol(configuration.getStack().get(i));
        }
    }
    
        
    public JLabel createLabel(String symbol) {
        JLabel label = new JLabel(symbol);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        return label;
    }
    
    public void setBottomSymbol(String symbol) {
        JLabel label = createLabel(symbol);
        label.setBackground(DesignManipulation.getInstance().getStackBottomBack());
        label.setForeground(DesignManipulation.getInstance().getStackBottomFront());
        this.add(label);
    }
    
    public void addSymbol(String symbol) {
        if(getComponentCount() == 0) {
            setBottomSymbol(symbol);
        } else {            
            JLabel label = createLabel(symbol);
            label.setBackground(DesignManipulation.getInstance().getStackSymbolBack());
            label.setForeground(DesignManipulation.getInstance().getStackSymbolFront());
            this.add(label, 0);
        }
    }
    
    public void removeSymbol() {
        this.remove(0);
    }
    
    public void doTransition(SimpleTransition transition) {
        removeSymbol();
        if(!transition.getPair().isEpsilonStack()) {
            for (int i = transition.getPair().getStackSymbols().size() - 1; i>=0; i--) {
                addSymbol(transition.getPair().getStackSymbols().get(i));
            }
        }
    }
    
    public void doUndo(SimpleTransition transition)  {
        if(!transition.getPair().isEpsilonStack()) {
            for (int i = 0; i <  transition.getPair().getStackSymbols().size(); i++) {
                removeSymbol();
            }
        }
        addSymbol(transition.getTernary().getStackSymbol());
    }
     
    
    
    public void refreshMe() {
        int compCount = getComponentCount();
        if(compCount > 0) {
            getComponent(compCount - 1).setBackground(DesignManipulation.getInstance().getStackBottomBack());
            getComponent(compCount - 1).setForeground(DesignManipulation.getInstance().getStackBottomFront());
            
        }
        for (int i = 0; i < compCount - 1; i++) {
            getComponent(i).setBackground(DesignManipulation.getInstance().getStackSymbolBack());
            getComponent(i).setForeground(DesignManipulation.getInstance().getStackSymbolFront());            
        }
    }
    
    
}
