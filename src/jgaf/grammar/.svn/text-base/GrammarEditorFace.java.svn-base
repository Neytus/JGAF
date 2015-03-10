/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author hanis
 */
public class GrammarEditorFace extends JPanel {
    
    private GrammarEditorToolbar toolbar;
    //private GrammarTableModel grammarTableModel;
    //private GrammarTableRenderer grammarTableRenderer;
    private GrammarEditor editor;
    private JPanel centerPanel;
    
    //private JTable table;
 //   private JPanel panel;

    public GrammarEditorFace(GrammarEditor editor) {
        super(new BorderLayout());
        this.editor = editor;
        initComponents();
    }

    private void initComponents() {
        toolbar = new GrammarEditorToolbar(editor);
        add(toolbar, BorderLayout.NORTH);
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        JScrollPane scroller = new JScrollPane(centerPanel);
        add(scroller, BorderLayout.CENTER);
        //add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(editor.getGrammarRepsresenter());
//        panel = new JPanel();
//        panel.setBackground(Color.GRAY);
//        centerPanel.add(panel);
//        //table = new JTable();
    }

    public GrammarEditorToolbar getToolbar() {
        return toolbar;
    }

    public void initiate() {
//        grammarTableModel = new GrammarTableModel(editor.getGrammar());
//        table.setModel(grammarTableModel);
//        grammarTableRenderer = new GrammarTableRenderer();
//        table.setDefaultRenderer(Object.class, grammarTableRenderer);
//        table.setRowHeight(25);
//   //     table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        table.setSurrendersFocusOnKeystroke(true);
//        table.getColumn(table.getColumnName(0)).setPreferredWidth(50);
//        table.getColumn(table.getColumnName(1)).setPreferredWidth(30);
//        table.getColumn(table.getColumnName(2)).setPreferredWidth(200);
//
//
//         JScrollPane scroller = new JScrollPane(table);
//         table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
//         table.setTableHeader(null);
//         panel.add(scroller, BorderLayout.CENTER);
//
//
//
//
//
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







 //       panel.add(table);
    }


    
     
    


}
