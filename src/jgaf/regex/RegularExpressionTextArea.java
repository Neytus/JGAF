/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.regex;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


/**
 *
 * @author hanis
 */
public class RegularExpressionTextArea extends JTextPane{

    public RegularExpressionTextArea(){
        super();
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
    }


    public void append(Color c,
                       String s) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                                            StyleConstants.Foreground, c);

        setCharacterAttributes(aset, false);
        replaceSelection(s);
    }


    public void setHighlightedText(String text, int from, int to) {
        int pos = getCaretPosition();
        if(pos > text.length()) {
            pos = text.length();
        }
        setText("");
        append(Color.BLACK, text.substring(0, from));
        append(Color.RED, text.substring(from, to+1));
        append(Color.BLACK, text.substring(to+1));
        setCaretPosition(pos);
    }

    public void setNormalText(String text) {
        int pos = getCaretPosition();
        if(pos > text.length()) {
            pos = text.length();
        }
        setText("");
        append(Color.BLACK, text);
        setCaretPosition(pos);
    }

}



