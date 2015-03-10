/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.regex;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 *
 * @author hanis
 */
public class RegularExpressionFace extends JPanel {

    private RegularExpressionEditor editor;
    private JPanel centerPanel;
    private RegularExpressionTextArea regexPane;
    private RegularExpressionEditorToolbar toolbar;
    private JLabel info;
    private JPanel representer;

    public RegularExpressionFace(RegularExpressionEditor editor) {
        this.editor = editor;
        this.setLayout(new BorderLayout());

        regexPane = new RegularExpressionTextArea();
        regexPane.addKeyListener(new java.awt.event.KeyAdapter() {

            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                handleKeyTyped(evt);
            }
        });


        representer = new JPanel(new BorderLayout());
        representer.add(regexPane, BorderLayout.CENTER);
        info = new JLabel();
        info.setBackground(Color.BLACK);
        info.setOpaque(true);


        JScrollPane scroller = new JScrollPane(representer);
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scroller, BorderLayout.CENTER);
        centerPanel.add(info, BorderLayout.NORTH);


        toolbar = new RegularExpressionEditorToolbar(editor);
        add(toolbar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);


    }

    // public void writeInfo(String text) {
    //info.setText(text);
//        if(editor.hasWellFormedRegex()) {
//            info.setText(editor.getRegex().writeAlphabet());
//        } else {
//
//        }
    //  }
    public void writeInfo(String writeAlphabet) {
        info.setText(writeAlphabet);
        info.setForeground(Color.WHITE);
    }

    public void writeWarning(String writeAlphabet) {
        info.setText(writeAlphabet);
        info.setForeground(Color.RED);
    }

    public JPanel getRepresenter() {
        return representer;
    }

    private RegularExpressionEditor getEditor() {
        return editor;
    }

    public void repaintMe() {
        if (editor.getRegex() != null) {
            writeInfo(editor.getRegex().writeAlphabet());
            regexPane.setNormalText(editor.getRegex().writeAll());
        }
        repaint();
    }

    public RegularExpressionTextArea getRegexPane() {
        return regexPane;
    }

    private void handleKeyTyped(java.awt.event.KeyEvent evt) {
        String re = getExpressionText();
        getEditor().setExpression(re);
    }

    public String getExpressionText() {
        return regexPane.getText();
    }
}
