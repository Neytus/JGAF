/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.sll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import jgaf.IA006.tools.Tools;
import jgaf.IA006.grammar.LLSymbol;

/**
 *
 * @author Empt
 */
public class SLLTable 
{    
    
    List<List<LLSymbol>> cols = new ArrayList<>();
    List<LLSymbol> rows = new ArrayList<>();
    SLLState[][] table;
    
    public SLLTable(int rows, int cols)
    {
        table = new SLLState[rows][cols];
    }
    
    public void addValue(SLLState state,LLSymbol row, List<LLSymbol> cols)
    {
        if(this.cols.indexOf(cols) == -1)
        {   //prvok tu este nie je
            this.cols.add(cols);
        }
        
        if(rows.indexOf(row) == -1)
        {
            rows.add(row);
        }
        
        table[rows.indexOf(row)][this.cols.indexOf(cols)] = state;
    }
    
    
    
    
    public void sortColumns()
    {
        SLLState[][] temp = new SLLState[table.length][table[0].length];
        List<List<LLSymbol>> sortedCols = new ArrayList<>(cols);
        Collections.sort(sortedCols,comparator);
        for(List<LLSymbol> column:sortedCols)
        {
            int oldPosition = cols.indexOf(column);
            int newPosition = sortedCols.indexOf(column);
            for(int i = 0; i < table.length;i++)
            {
                temp[i][newPosition] = table[i][oldPosition];
            }     
        }
        table = temp;
        cols.clear();
        cols.addAll(sortedCols);
    }
    
    
    public LLSymbol[] rowsAsArray()
    {
        return (LLSymbol[]) rows.toArray();
    }
    
    public List<LLSymbol> rowsAsList()
    {
        return rows;
    }
    
    public List<List<LLSymbol>> colsAsList()
    {
        return cols;
    }
    
    public SLLState getValueAtPosition(int x, int y)
    {
        return table[y][x];
    }
    
    public void addToRows(LLSymbol row)
    {
        if(rows.indexOf(row) == -1)
        {  
            this.rows.add(row);
        }
    }


    public SLLState getValueAtPosition(LLSymbol s, List<LLSymbol> prefix)
    {
        return getValueAtPosition(cols.indexOf(prefix),rows.indexOf(s));
    }
    
    public String nicePrint()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%9s | ","x"));
        System.out.format("%9s | ","x");
        for(int i = 0 ; i < cols.size(); i++)
        {
            sb.append(String.format("%9s | ",Tools.buildWord(cols.get(i))));
            System.out.format("%9s | ",Tools.buildWord(cols.get(i)));
        }
        sb.append("\n");
        System.out.println();
        for(int i = 0 ; i < table.length; i++)
        { 
            sb.append(String.format("%9s | ", rows.get(i).toString()));
            System.out.format("%9s | ", rows.get(i).toString());
            for(int j = 0 ; j < table[i].length; j++)
            {
                sb.append(String.format("%9s | ", Tools.buildWord(table[i][j] == null ? null : table[i][j].getRule())));
                System.out.format("%9s | ", Tools.buildWord(table[i][j] == null ? null : table[i][j].getRule()));
            }
            sb.append("\n");
            System.out.println();
        }
        
        return sb.toString();
    }
    
    public String htmlPrint()
    {
        StringBuilder sb = new StringBuilder("<table style=\"border-collapse: collapse;\" cellspacing=\"0\">");
        sb.append("<tr>\n\t<td></td>");
        for(int i = 0; i < cols.size(); i++)
        {
            sb.append("\n\t<td style=\"border-style: solid; border-width: 1px; padding-left: 5px;padding-right: 5px;\">")
                    .append(Tools.buildWord(cols.get(i))).append("</td>");
        }
        sb.append("\n</tr>");
        
        for(int i = 0 ; i < rows.size(); i++)
        {
            sb.append("<tr>");
            sb.append("\n\t<td style=\"border-style: solid; border-width: 1px; padding-left: 5px;padding-right: 5px;\">").append(rows.get(i)).append("</td>");
            for(int j = 0; j < cols.size();j++)
            {
                sb.append("\n\t<td style=\"border-style: solid; border-width: 1px; padding-left: 5px;padding-right: 5px;\">").append(Tools.buildHTMLWordT(table[i][j] == null ? null : table[i][j].getRule())).append("</td>");
            }
            sb.append("\n</tr>");
        }
        
        
        sb.append("</table>");
        
        
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(rows).append("\n");
        sb.append(cols).append("\n");
        for(int i = 0 ; i < table.length; i++)
        { 
            for(int j = 0 ; j < table[i].length; j++)
            {
                sb.append("[").append(table[i][j]).append("] - ");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    //help stuff
    private String printColumn(int i )
    {
        StringBuilder sb = new StringBuilder("[");
        for(int j=0; j <table.length;j++)
        {
            sb.append(Tools.buildWord(table[j][i] == null ? null : table[j][i].getRule())).append(",");
        }
        
        sb.append("]");
        
        return sb.toString();
    }
    
    private Comparator<List<LLSymbol>> comparator = new Comparator<List<LLSymbol>>() {

        @Override
        public int compare(List<LLSymbol> o1,List<LLSymbol> o2) 
        {
            int result = o2.size() - o1.size();
            
            if(result == 0)
            {
                return Tools.buildWord(o1).compareTo(Tools.buildWord(o2));
            }
            
            return result;
        }
    };
}
