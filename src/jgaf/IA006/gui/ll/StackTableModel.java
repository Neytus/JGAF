/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.Stack;
import javax.swing.table.AbstractTableModel;
import jgaf.IA006.grammar.Symbol;

/**
 *
 * @author Empt
 */
public class StackTableModel extends AbstractTableModel
{
    private Stack<Symbol> stack = new Stack<>();
    
    public void setup(Stack<Symbol> stack)
    {
        this.stack = stack;
        fireTableDataChanged();
    }
    
    public void step()
    {
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() 
    {
        return stack.size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex,
                             int columnIndex) {
        return stack.get(rowIndex);
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0)
        {
            return "Stack";
        }
        return "";//To change body of generated methods, choose Tools | Templates.
    }
    
    
}
