/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.grammar;

/**
 *
 * @author LordDrake
 */

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductionRulesSide implements Cloneable {
    
    private Color fgColor = Color.BLACK;
    private Color bgColor = Color.WHITE;

    private List<ProductionRuleSide> rules;

    public ProductionRulesSide() {
        this.rules = new ArrayList<ProductionRuleSide>();
    }

     public ProductionRulesSide(List<ProductionRuleSide> rules) {
        this.rules = rules;
    }
     public ProductionRulesSide(ProductionRuleSide rules) {
        this.rules = new ArrayList<ProductionRuleSide>();
        this.rules.add(rules);
    }

    public void addRule(ProductionRuleSide rule) {
        rules.add(rule);
    }

    public List<ProductionRuleSide> getRules() {
        return rules;
    }
    
    public int size() {
        return rules.size();
    }

    public boolean isEmpty() {   
        if(rules.isEmpty()) return rules.isEmpty();
        return rules.get(0).isEmpty();   
    }

    public void clear() {
        rules = new ArrayList<ProductionRuleSide>();
    }

    public void setRulesFromString(List<String> list, boolean right) {
        clear();
        
        //System.out.println("Tady2---" + list.get(0) + "----");
        for (String string : list){
            ProductionRuleSide ruleSide = new ProductionRuleSide();
            System.out.println("---" + string + "----");
            ruleSide.addSymbolsFromString(string, right);
            addRule(ruleSide);
        }
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        if(!rules.isEmpty()){
        for(ProductionRuleSide symbols : rules){         
            sb.append(symbols.toString());
            sb.append(" | ");
            
        }
        
            sb = sb.delete(sb.length()-3, sb.length());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof ProductionRulesSide) {
           ProductionRulesSide rulesSide = (ProductionRulesSide) obj;
           if(rulesSide.getRules().equals(rules)){
                return true;
           }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.rules != null ? this.rules.hashCode() : 0);
        return hash;
    }

    @Override
    public Object clone() {
        ProductionRulesSide obj = new ProductionRulesSide();
        
        obj.setBgColor(new Color(bgColor.getRGB()));
        obj.setFgColor(new Color(fgColor.getRGB()));
        for (ProductionRuleSide symbols : rules) {
            ProductionRuleSide rl = new ProductionRuleSide();
            for(Symbol symbol : symbols.getSymbols()){
                rl.addSymbol((Symbol) symbol.clone());
            }
            obj.addRule(rl);
        }
        return obj;
    }

    public void setRules(List<ProductionRuleSide> rules) {
        this.rules = rules;
    }

    public int compareTo(ProductionRulesSide o) {

        for (Iterator i = rules.iterator(); i.hasNext();) {
            Iterator j = o.getRules().iterator();
            if(!j.hasNext()){
                return -1;
            }
            ProductionRulesSide r1 = (ProductionRulesSide) i.next();
            ProductionRulesSide r2 = (ProductionRulesSide) j.next();
            int comp = r1.compareTo(r2);
            if(comp!=0) {
                return comp;
            }
        }
        return 0;
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
    
    public boolean hasEpsProduction(){
        for(ProductionRuleSide oneRule : rules){
            if(oneRule.isEpsilon()) return true;
        }
        return false;
    }
    
    public boolean containsRuleWithSymbol(Symbol symbol){
        for(ProductionRuleSide oneRule : rules){
            
            if(oneRule.getSymbols().contains(symbol)) return true;
        }
        return false;
    }
}
