/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author hanis
 */
public class PushdownAutomatonRepresenter extends JPanel {
    
    //private MatrixTableRender matrixRender;

    private JTable matrixTable;

    public PushdownAutomatonRepresenter() {
        matrixTable = new JTable();
        matrixTable.setBackground(Color.WHITE);
        matrixTable.setShowGrid(true);
        matrixTable.setGridColor(Color.BLACK);
        matrixTable.getTableHeader().setBackground(Color.WHITE);
        matrixTable.setRowSelectionAllowed(false);
        initiate();
    }

    private void initiate() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JScrollPane scroller = new JScrollPane(matrixTable);
        scroller.getViewport().setBackground(Color.WHITE);
        add(scroller, BorderLayout.CENTER);
    }


    public void setAutomaton(PushdownAutomaton automaton) {
        if(automaton == null) {
            throw new NullPointerException();
        }
        MatrixData matrixData = new MatrixData(automaton);
        matrixTable.setModel(new MatrixTableModel(matrixData));
        PushdownAutomatonEditorRenderer matrixRender = new PushdownAutomatonEditorRenderer(matrixData);
        matrixTable.setDefaultRenderer(Object.class, matrixRender);
        matrixTable.getTableHeader().setDefaultRenderer(matrixRender);
        matrixTable.setRowHeight(25);
        matrixTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        matrixTable.getColumn(matrixTable.getColumnName(0)).setPreferredWidth(100);
        for (int i = 1; i < matrixTable.getColumnCount(); i++) {
            matrixTable.getColumn(matrixTable.getColumnName(i)).setPreferredWidth(150);
        
        }
  //      matrixTable.validate();
    
//        matrixTable.sizeColumnsToFit(1);

        repaint();

    }




}
