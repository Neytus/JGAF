/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.gui.ll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import jgaf.IA006.tools.Tools;
import jgaf.IA006.grammar.Symbol;

/**
 *
 * @author Empt
 */
public class LLTable 
{    
    
    List<List<Symbol>> cols = new ArrayList<>();
    List<TSymbol> rows = new ArrayList<>();
    TState[][] table;
    
    public LLTable(int rows, int cols)
    {
        table = new TState[rows][cols];
    }
    
    public void addValue(TState state,TSymbol row, List<Symbol> cols)
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
    
    public TSymbol getSymbolFor(Symbol s, Set<List<Symbol>> lfollow)
    {
        for(TSymbol ts : rows)
        {
            if(ts.getNonTerminal().equals(s) && ts.getFollow().equals(lfollow))
            {
                return ts;
            }
        }
        
        return null;
    }
    
    
    public void sortColumns()
    {
        TState[][] temp = new TState[table.length][table[0].length];
        List<List<Symbol>> sortedCols = new ArrayList<>(cols);
        Collections.sort(sortedCols,comparator);
        for(List<Symbol> column:sortedCols)
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
    
    
    public TSymbol[] rowsAsArray()
    {
        return (TSymbol[]) rows.toArray();
    }
    
    public List<TSymbol> rowsAsList()
    {
        return rows;
    }
    
    public List<List<Symbol>> colsAsList()
    {
        return cols;
    }
    
    public TState getValueAtPosition(int x, int y)
    {
        return table[y][x];
    }
    
    public void addToRows(TSymbol row)
    {
        if(rows.indexOf(row) == -1)
        {  
            this.rows.add(row);
        }
    }


    public TState getValueAtPosition(Symbol s, List<Symbol> prefix)
    {
        return getValueAtPosition(cols.indexOf(prefix),rows.indexOf(s));
    }
    
    /**
     * Method to provide formatted output for table.
     * @return 
     */
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
    
    /**
     * HTML output of given table
     * @return html representation of table
     */
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
    
    private Comparator<List<Symbol>> comparator = new Comparator<List<Symbol>>() 
    {
        @Override
        public int compare(List<Symbol> o1,List<Symbol> o2) 
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
