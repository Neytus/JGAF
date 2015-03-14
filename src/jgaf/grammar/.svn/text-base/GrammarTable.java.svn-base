/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;

/**
 *
 * @author hanis
 */
public class GrammarTable extends JTable implements MouseListener, KeyListener {

    private GrammarTableModel grammarTableModel;
    private GrammarTableRenderer grammarTableRenderer;
    private GrammarEditor editor;
    
    private RuleSidePopupMenu ruleSidePopupMenu;
    private RulePopupMenu rulePopupMenu;


    public GrammarTable(GrammarEditor editor) {
        this.editor = editor;
        setPopupMenus();
        init();
        addMouseListener(this);

    }

//    @Override
//    public boolean isEnabled() {
//        if(editor == null) {
//            return false;
//        }
//        return editor.isEditable();
//    }

    private void init() {
        grammarTableModel = new GrammarTableModel(getEditor());
        setModel(getGrammarTableModel());
        grammarTableRenderer = new GrammarTableRenderer();
        setDefaultRenderer(Object.class, getGrammarTableRenderer());

        setRowHeight(25);
        setSurrendersFocusOnKeystroke(true);


        //System.out.println(cell.getText() + ": " + cell.getFontMetrics(cell.getFont()).stringWidth(cell.getText()));

        getColumn(getColumnName(0)).setPreferredWidth(40);
        getColumn(getColumnName(1)).setPreferredWidth(30);
        getColumn(getColumnName(2)).setPreferredWidth(150);

//        getColumn(getColumnName(2)).sizeWidthToFit();
  //      System.out.println("maxwidth: " + getColumn(getColumnName(2)).getMaxWidth());
        
    //    getGrammarTableRenderer().getTableCellRendererComponent(this, ui, rowSelectionAllowed, showVerticalLines, WIDTH, WIDTH)
        //getColumn(getColumnName(2)).getCellEditor().
        setTableHeader(null);        
    }


    private void setPopupMenus() {
        ruleSidePopupMenu = new RuleSidePopupMenu(editor);
        rulePopupMenu = new RulePopupMenu(editor);
    }



    public GrammarTableModel getGrammarTableModel() {
        return grammarTableModel;
    }

    public GrammarTableRenderer getGrammarTableRenderer() {
        return grammarTableRenderer;
    }

    public GrammarEditor getEditor() {
        return editor;
    }




    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked");
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("mouse pressed");
        showPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("mouse released");
        showPopup(e);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }



    private void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            requestFocus();
            JTable source = (JTable)e.getSource();
            int row = source.rowAtPoint(e.getPoint());
            int column = source.columnAtPoint(e.getPoint());
            source.changeSelection(row, column, false, false);
            if(column == GrammarTableModel.LEFT_SIDE_INDEX) {
                ProductionRuleSide ruleSide = (ProductionRuleSide) getValueAt(row, column);
                ruleSidePopupMenu.show(ruleSide, e.getComponent(), e.getPoint(), false);
            } else if(column == GrammarTableModel.RIGHT_SIDE_INDEX) {
                ProductionRuleSide ruleSide = (ProductionRuleSide) getValueAt(row, column);
                ruleSidePopupMenu.show(ruleSide, e.getComponent(), e.getPoint(), true);
            } else if(column == GrammarTableModel.ARROW_INDEX) {
                ProductionRule rule = grammarTableModel.getProductionRule(row);
                rulePopupMenu.show(rule, e.getComponent(), e.getPoint());
            }      
        }
    }

    private void showPopup(KeyEvent e) {
        if (e.isControlDown()) {
        //    ruleSidePopupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
    }


   // @Override
 //   public void tableChanged(TableModelEvent e) {
 //       System.out.println("table changed");
        //        int row = e.getFirstRow();
//        int column = e.getColumn();
//        //TableModel model = (TableModel)e.getSource();
//       // String columnName = getColumnName(column);
//        if(column == 0) {
//            System.out.println("left choosen");
//            System.out.println((ProductionRuleSide) getValueAt(row, column));
//        }
//                if(column == 1) {
//            System.out.println("arrow choosen");
//            //System.out.println(getValueAt(row, column));
//        }
//        if(column == 2) {
//            System.out.println("right choosen");
//            System.out.println((ProductionRuleSide) getValueAt(row, column));
//        }
//    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(editor.isEditable()) {
            editor.handleKeyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
    }







}
