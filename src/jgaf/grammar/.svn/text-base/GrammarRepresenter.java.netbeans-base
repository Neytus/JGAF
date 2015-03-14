/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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

        //JScrollPane scroller = new JScrollPane(table);
      //   table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
         
         //this.add(scroller, BorderLayout.CENTER);
        setBackground(Color.WHITE);
        add(table, BorderLayout.CENTER);




//        table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                if (e.getButton() == MouseEvent.BUTTON3) {
//                    JTable source = (JTable) e.getSource();
//                    int row = source.rowAtPoint(e.getPoint());
//                    int column = source.columnAtPoint(e.getPoint());
////                    table.setEditingRow(row);
////                    table.setSelectionBackground(Color.RED);
////                    table.setEditingColumn(column);
////                    table.setEditingRow(row);
//                    grammarTableRenderer.setCell(column, row);
//                    if (!source.isRowSelected(row)) {
//                        source.changeSelection(row, column, false, false);
//                    }
//                    JPopupMenu popup = new JPopupMenu("menu");
//                    popup.add(new JMenuItem("column: " + column));
//                    popup.add(new JMenuItem("row: " + row));
//                    popup.add(new JMenuItem("content: " + table.getValueAt(row, column)));
//
//
//                    popup.show(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//        });

    }


    public void repaintTable() {
        table.getGrammarTableModel().fireTableDataChanged();
    }

}
