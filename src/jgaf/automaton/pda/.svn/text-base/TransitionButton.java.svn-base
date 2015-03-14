/*
 * TransitionButton.java
 *
 * Created on 3. listopad 2007, 19:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author hanis
 */
public class TransitionButton extends JButton implements MouseListener {
    
    private SimpleTransition transition;
    private boolean possible;    
    private static final Font NORMAL = new Font("Dialog", Font.PLAIN, 12);
    private static final Font FOCUS = new Font("Dialog", Font.BOLD, 12);
    
    public TransitionButton(SimpleTransition transition, boolean possible, boolean whisperer) {
        addMouseListener(this);        
        this.transition = transition;
        this.possible = possible;
        setText(transition.toString());
        setFont(NORMAL);
        paintMe(whisperer);
    }
    
    public boolean isPossible() {
        return possible;
    }

    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        setFont(FOCUS);
    }

    public void mouseExited(MouseEvent e) {
       setFont(NORMAL);
    }

    public SimpleTransition getTransition() {
        return transition;
    }
    
    public void paintMe(boolean whisperer) {
        if(possible && whisperer) {
            setBackground(DesignManipulation.getInstance().getTransOkBack());
            setForeground(DesignManipulation.getInstance().getTransOkFront());
        } else {
            setBackground(DesignManipulation.getInstance().getTransNokBack());
            setForeground(DesignManipulation.getInstance().getTransNokFront());            
        }        
    }
       
}
