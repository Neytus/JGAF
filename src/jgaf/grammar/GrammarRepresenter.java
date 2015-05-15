/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author hanis
 */
public class GrammarRepresenter extends JPanel {


    private GrammarTable table;

    public GrammarRepresenter(GrammarEditor editor) {
        this.table = new GrammarTable(editor);
        initiate();
    }

    private void initiate() {
        setLayout(new BorderLayout());

        setBackground(Color.WHITE);
        add(table, BorderLayout.CENTER);

    }


    public void repaintTable() {
        table.getGrammarTableModel().fireTableDataChanged();
    }

}
