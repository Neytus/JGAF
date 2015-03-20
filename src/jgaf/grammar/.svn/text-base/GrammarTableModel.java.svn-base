/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.grammar;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author hanis
 */
public class GrammarTableModel extends AbstractTableModel {

    public static final int LEFT_SIDE_INDEX = 0;
    public static final int ARROW_INDEX = 1;
    public static final int RIGHT_SIDE_INDEX = 2;
    private static final int COLUMN_COUNT = 3;
    private static final String ARROW = "->";
    private GrammarEditor editor;

    public GrammarTableModel(GrammarEditor editor) {
        this.editor = editor;
        //System.out.println(grammar.toString());
    }

    public Grammar getGrammar() {
        return editor.getGrammar();
    }

    public int getRowCount() {
        return getGrammar().getProductionCount();
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case LEFT_SIDE_INDEX:
                return "left";
            case RIGHT_SIDE_INDEX:
                return "right";
            case ARROW_INDEX:
                return "arrow";
            default:
                return "unknown";
        }
    }

    public Object getValueAt(int row, int column) {
        switch (column) {
            case LEFT_SIDE_INDEX:
                return getGrammar().getProductionRules().get(row).getLeftHandSide();
            case RIGHT_SIDE_INDEX:
                return getGrammar().getProductionRules().get(row).getRightHandSide();
            case ARROW_INDEX:
                return ARROW;
            default:
                return new Object();
        }
    }

    public ProductionRule getProductionRule(int index) {
        return getGrammar().getProductionRules().get(index);
    }

    @Override
    public void setValueAt(Object value,
                           int row,
                           int column) {
        switch (column) {
            case LEFT_SIDE_INDEX:
            case RIGHT_SIDE_INDEX: {
                String string = (String) value;
                if (string.matches("[a-zA-Z]*")) {
                    editor.changeRuleSide((ProductionRuleSide) getValueAt(row, column), string);
                }
                break;
            }
            default:
                System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == ARROW_INDEX) {
            return false;
        }
        return true;
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case LEFT_SIDE_INDEX:
            case RIGHT_SIDE_INDEX:
            //       return ProductionRuleSide.class;
            case ARROW_INDEX:
                return String.class;
            default:
                return Object.class;
        }
    }

    public void addEmptyRow() {
        getGrammar().addRule(new ProductionRule());
        fireTableRowsInserted(getRowCount(), getRowCount());
    }
}
