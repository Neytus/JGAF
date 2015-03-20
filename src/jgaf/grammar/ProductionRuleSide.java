/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import jgaf.Constants.MathConstants;

/**
 *
 * @author hanis
 */
public class ProductionRuleSide implements Cloneable, Comparable<ProductionRuleSide> {


    private Color fgColor = Color.BLACK;
    private Color bgColor = Color.WHITE;

    private List<Symbol> symbols;

    public ProductionRuleSide() {
        symbols = new ArrayList<Symbol>();
    }

    public ProductionRuleSide(List<Symbol> symbols) {
        this.symbols = symbols;
    }
    
    public ProductionRuleSide(ProductionRuleSide rule) {
        this.symbols = rule.getSymbols();
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }
    
    public void addEpsilon(String symbol) {
        symbols.add(new Epsilon(symbol));
    }
    
    public void addTerminal(String symbol) {
        symbols.add(new Terminal(symbol));
    }

    public void addNonterminal(String symbol) {
        symbols.add(new Nonterminal(symbol));
    }

    public int size() {
        return symbols.size();
    }

    public boolean isEmpty() {
        return symbols.isEmpty();        
    }

    public void clear() {
        symbols.removeAll(symbols);
    }

    public void addSymbolsFromString(String string) {
        string = string.trim();
        
        //added with lr extension{
        if (string.equalsIgnoreCase("eps.")){
            addSymbol(new Symbol());
            return;
        }
        //}
        
        for (int i = 0; i < string.length(); i++) {
            String letter = String.valueOf(string.charAt(i));
            if(letter.equals(letter.toUpperCase())) {
                addNonterminal(letter);
            } else {
                addTerminal(letter);
            }
        }
    }
    
    public void addSymbolsFromString(String string, boolean right) {
        string = string.trim();
        if((string.equals("eps") || (string.equals(MathConstants.EPSILON)))  && right){
            
            System.out.println("ProductionRuleSide:addSymbolsFromString - pridany epsilon: " + string);
            
            addEpsilon(string);
        }
        else{
            for (int i = 0; i < string.length(); i++) {
                String letter = String.valueOf(string.charAt(i));
                if(letter.equals(letter.toUpperCase())) {
                    addNonterminal(letter);
                } else {
                    addTerminal(letter);
                }
            }
        }
    }
    
    public void addSymbolsFromList(List<Symbol> list) {
        for(Symbol symb : list){
            symbols.add(symb);
        }
    }

    public void setSymbolsFromString(String string, boolean right) {
        clear();
        addSymbolsFromString(string, right);
    }
    
    public void setSymbolsFromString(String string) {
        clear();
        System.out.println("---" + string + "----");
        addSymbolsFromString(string);
    }
    
    public void removeSymbolOnPosition(int position){
        symbols.remove(position);
    }

    public void setSymbolsFromProductionRuleSide(ProductionRuleSide rule){
        symbols.addAll(rule.getSymbols());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Symbol symbol : symbols) {
            sb.append(symbol.toString());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof ProductionRuleSide) {
           ProductionRuleSide ruleSide = (ProductionRuleSide) obj;
           if(ruleSide.getSymbols().equals(symbols)){
                return true;
           }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.symbols != null ? this.symbols.hashCode() : 0);
        return hash;
    }


    @Override
    public Object clone() {
        ProductionRuleSide obj = new ProductionRuleSide();
        obj.setBgColor(new Color(bgColor.getRGB()));
        obj.setFgColor(new Color(fgColor.getRGB()));
        for (Symbol symbol : symbols) {
            obj.addSymbol((Symbol) symbol.clone());
        }
        return obj;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public int compareTo(ProductionRuleSide o) {
        for (int i = 0; i < getSymbols().size(); i++) {
            if(i >= o.getSymbols().size()) {
                return -1;
            }
            int comp = getSymbols().get(i).compareTo(o.getSymbols().get(i));
            if(comp != 0) {
                return comp;
            }
        }
        return 0;
    }


    public boolean isEpsilon() {
        if(symbols.size() == 1 && symbols.get(0).isEpsilon()) {
            return true;
        }
        return false;
    }

    /**
     * @return the fgColor
     */
    public Color getFgColor() {
        return fgColor;
    }

    /**
     * @param fgColor the fgColor to set
     */
    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
    }

    /**
     * @return the bgColor
     */
    public Color getBgColor() {
        return bgColor;
    }

    /**
     * @param bgColor the bgColor to set
     */
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }
    
    /**
     * added with LR extension
     *
     */
    public Symbol getFirst() {
        return symbols.get(0);
    }

    /**
     * added with LR extension
     *
     */
    
    public Symbol getSymbolAt(int pos) {
        return symbols.get(pos);
    }

    /**
     * added with LR extension
     *
     */
    public boolean containsSymbol(Symbol symbol) {
        return symbols.contains(symbol);
    }

    /**
     * added with LR extension
     *
     */
    public List<Symbol> getNonterminals() {
        ArrayList<Symbol> nonts = new ArrayList<Symbol>();
        for (Symbol s : symbols) {
            if (s.isNonterminal()) {
                nonts.add(s);
            }
        }
        return nonts;
    }
}
