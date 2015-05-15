/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.grammar;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author hanis
 */
public class ProductionRules implements Comparable<ProductionRules>, Cloneable {

    public final static ProductionRules EMPTY_RULE = new ProductionRules();


    private ProductionRuleSide leftHandSide;
    private ProductionRulesSide rightHandSide;

    public ProductionRules() {
        this.leftHandSide = new ProductionRuleSide();
        this.rightHandSide = new ProductionRulesSide();
    }
    
    public ProductionRules(ProductionRule rule) {
        this.leftHandSide = rule.getLeftHandSide();
        this.rightHandSide = new ProductionRulesSide();
        this.rightHandSide.addRule(rule.getRightHandSide());
    }

    public ProductionRules(List<Symbol> leftHandSide, List<ProductionRuleSide> rightHandSide) {
        this.leftHandSide = new ProductionRuleSide(leftHandSide);
    }


    public ProductionRules(ProductionRuleSide leftHandSide, ProductionRulesSide rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    public ProductionRules(Nonterminal leftNonterminal, List<ProductionRuleSide> rightHandSide) {
        List<Symbol> leftNonterminalList = new ArrayList<>();
        this.leftHandSide = new ProductionRuleSide(leftNonterminalList);
        this.rightHandSide = new ProductionRulesSide(rightHandSide);
    }

    public ProductionRules(Nonterminal leftNonterminal, Terminal rightTerminal, Nonterminal rightNonterminal) {
        List<Symbol> rightList = new ArrayList<>();
        List<Symbol> leftList = new ArrayList<>();
        leftList.add(leftNonterminal);
        rightList.add(rightTerminal);
        rightList.add(rightNonterminal);
        List<ProductionRuleSide> rightRule = new ArrayList<>();
        rightRule.add(new ProductionRuleSide(rightList));
        this.leftHandSide = new ProductionRuleSide(leftList);
        this.rightHandSide = new ProductionRulesSide(rightRule);
    }


    public ProductionRules(Nonterminal leftNonterminal, Terminal rightTerminal) {
        List<Symbol> rightList = new ArrayList<>();
        List<Symbol> leftList = new ArrayList<>();
        leftList.add(leftNonterminal);
        rightList.add(rightTerminal);
        List<ProductionRuleSide> rightRule = new ArrayList<>();
        rightRule.add(new ProductionRuleSide(rightList));
        this.leftHandSide = new ProductionRuleSide(leftList);
        this.rightHandSide = new ProductionRulesSide(rightRule);
    }

    public ProductionRules(Nonterminal leftNonterminal) {
        List<Symbol> rightList = new ArrayList<>();
        List<Symbol> leftList = new ArrayList<>();
        leftList.add(leftNonterminal);
        rightList.add(new Symbol());
        List<ProductionRuleSide> rightRule = new ArrayList<>();
        rightRule.add(new ProductionRuleSide(rightList));
        this.leftHandSide = new ProductionRuleSide(leftList);
        this.rightHandSide = new ProductionRulesSide(rightRule);
    }


    @Override
    public String toString() {
        String string = "";
        int i = 0;
        for(ProductionRuleSide rule : rightHandSide.getRules()){
            string += ""+writeLeftHandSide() + " -> " + rule.toString()+"\n";
            i++;
        }
        return string.substring(0, string.length()-1);
    }


    public String writeLeftHandSide() {
        return leftHandSide.toString();
    }

    public String writeRightHandSide() {
        return rightHandSide.toString();
    }

    public ProductionRuleSide getLeftHandSide() {
        return leftHandSide;
    }

    public void setLeftHandSide(List<Symbol> leftHandSide) {
        this.leftHandSide = new ProductionRuleSide(leftHandSide);
    }

    public void setLeftHandSide(ProductionRuleSide leftHandSide) {
        this.leftHandSide = leftHandSide;
    }


    public ProductionRulesSide getRightHandSide() {
        return rightHandSide;
    }

    public void setRightHandSide(ProductionRulesSide rightHandSide) {
        this.rightHandSide = rightHandSide;
    }

    public void addToLeftHandSide(Symbol symbol) {
        leftHandSide.addSymbol(symbol);
    }

    public void addToRightHandSide(ProductionRuleSide rule) {
        rightHandSide.addRule(rule);
    }


    public boolean isEmpty() {
        return leftHandSide.isEmpty() && rightHandSide.isEmpty();
    }


    public void setBgColor(Color color) {
        leftHandSide.setBgColor(color);
        rightHandSide.setBgColor(color);
    }

    public void setFgColor(Color color) {
        leftHandSide.setFgColor(color);
        rightHandSide.setFgColor(color);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof ProductionRules) {
           ProductionRules rule = (ProductionRules)obj;
           if(rule.getLeftHandSide().equals(getLeftHandSide()) && rule.getRightHandSide().equals(getRightHandSide())){
                return true;
           }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.leftHandSide != null ? this.leftHandSide.hashCode() : 0);
        hash = 37 * hash + (this.rightHandSide != null ? this.rightHandSide.hashCode() : 0);
        return hash;
    }



    public int compareTo(ProductionRules o) {
        if(isEmpty()) {
            return 1;
        }
        if(o.isEmpty()) {
            return -1;
        }

        int comp = getLeftHandSide().compareTo(o.getLeftHandSide());
        if(comp == 0) {
            return getRightHandSide().compareTo(o.getRightHandSide());
        }
        return comp;
    }


    @Override
    public Object clone() {
        ProductionRules obj = new ProductionRules();
        obj.setLeftHandSide((ProductionRuleSide) getLeftHandSide().clone());
        obj.setRightHandSide((ProductionRulesSide) getRightHandSide().clone());
        return obj;
    }

    /**
     * 
     * added with LRextension
     */ 
    public boolean containsSymbol(Symbol symbol){
        return leftHandSide.containsSymbol(symbol) || rightHandSide.containsSymbol(symbol);
    }
    
    /**
     * 
     * added with LRextension
     */   
    public  Set<Symbol> getNonterminals(){
        Set<Symbol> nonTerminals = new HashSet<>();
        nonTerminals.addAll(leftHandSide.getNonterminals());
        nonTerminals.addAll(rightHandSide.getNonterminals());
        return nonTerminals;  
    } 

}
