/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.guitools;

import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import jgaf.Constants.MathConstants;
import jgaf.grammar.Symbol;
import jgaf.lrextension.procedures.ParseStep;

/**
 *
 * @author g
 */
public class ParseTableModel extends AbstractTableModel {

    private List<ParseStep> parseSteps;

    public ParseTableModel(List<ParseStep> ParseSteps) {
        this.parseSteps = ParseSteps;

    }

    public int getRowCount() {
        return parseSteps.size();
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int rowIndex,
                             int columnIndex) {
        switch (columnIndex) {
            case 0:
                return rowIndex;
            case 2:
                return createStackHTMLString(rowIndex);
//            case 2:
//                return actionToString(parseSteps.get(rowIndex).getNextAction());
            case 1:
                if (parseSteps.get(rowIndex).getInput().isEmpty()) {
                    return MathConstants.EPSILON;
                } else {
                    return parseSteps.get(rowIndex).getInput();
                }
                
            case 3:
                return parseSteps.get(rowIndex).getRules();
            default:
                return new Object();
        }


    }
    public String[] colNames = {"Step",
        
//        "Next Action",
        "Input",
        "Stack",
        "Output"};
    // Types of the columns. 

    @Override
    public String getColumnName(int col) {
        return colNames[col];

    }

    private String actionToString(ParseStep.PAction action) {
        switch (action) {
            case ACCEPT:
                return "Accept";
            case SHIFT:
                return "Shift";
            case CONFLICT:
                return "Conflict";
            case ERRORR:
                return "Error";
            case REDUCE:
                return "Reduce";
            default:
                return "internalerror";
        }

    }

    private String createStackHTMLString(int rowIndex) {
        StringBuilder sb = new StringBuilder();
        List<Integer> auto = parseSteps.get(rowIndex).getAuto();
        List<Symbol> stack = parseSteps.get(rowIndex).getStack();
        int autoIndex = 0;
        if (auto.size() < 1) {
            throw new IllegalArgumentException("empty stack");
        }
        sb.append("<html>");
        sb.append("<sub><span style=\"color: blue;\">");
        sb.append(auto.get(autoIndex++));
        sb.append("</span></sub>");
        for (Symbol symbol : stack) {
            sb.append(symbol);
            if (auto.contains(auto.get(autoIndex))){
            sb.append("<sub><span style=\"color: blue;\">");
            
            sb.append(auto.get(autoIndex++));
            
            sb.append("</span></sub>");}
        }
        sb.append("</html>");
        return sb.toString();
    }
}
