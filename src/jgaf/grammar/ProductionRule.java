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
public class ProductionRule implements Comparable<ProductionRule>, Cloneable {

    public final static ProductionRule EMPTY_RULE = new ProductionRule();


    private ProductionRuleSide leftHandSide;
    private ProductionRuleSide rightHandSide;

    public ProductionRule() {
        this.leftHandSide = new ProductionRuleSide();
        this.rightHandSide = new ProductionRuleSide();
    }

    public ProductionRule(List<Symbol> leftHandSide, List<Symbol> rightHandSide) {
        this.leftHandSide = new ProductionRuleSide(leftHandSide);
        this.rightHandSide = new ProductionRuleSide(rightHandSide);
    }


    public ProductionRule(ProductionRuleSide leftHandSide, ProductionRuleSide rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    public ProductionRule(Nonterminal leftNonterminal, List<Symbol> rightHandSide) {
        List<Symbol> leftNonterminalList = new ArrayList<Symbol>();
        this.leftHandSide = new ProductionRuleSide(leftNonterminalList);
        this.rightHandSide = new ProductionRuleSide(rightHandSide);
    }

    public ProductionRule(Nonterminal leftNonterminal, Terminal rightTerminal, Nonterminal rightNonterminal) {
        List<Symbol> rightList = new ArrayList<Symbol>();
        List<Symbol> leftList = new ArrayList<Symbol>();
        leftList.add(leftNonterminal);
        rightList.add(rightTerminal);
        rightList.add(rightNonterminal);
        this.leftHandSide = new ProductionRuleSide(leftList);
        this.rightHandSide = new ProductionRuleSide(rightList);
    }


    public ProductionRule(Nonterminal leftNonterminal, Terminal rightTerminal) {
        List<Symbol> rightList = new ArrayList<Symbol>();
        List<Symbol> leftList = new ArrayList<Symbol>();
        leftList.add(leftNonterminal);
        rightList.add(rightTerminal);
        this.leftHandSide = new ProductionRuleSide(leftList);
        this.rightHandSide = new ProductionRuleSide(rightList);
    }

    public ProductionRule(Nonterminal leftNonterminal) {
        List<Symbol> rightList = new ArrayList<Symbol>();
        List<Symbol> leftList = new ArrayList<Symbol>();
        leftList.add(leftNonterminal);
        rightList.add(new Symbol());
        this.leftHandSide = new ProductionRuleSide(leftList);
        this.rightHandSide = new ProductionRuleSide(rightList);
    }


    @Override
    public String toString() {
        return writeLeftHandSide() + " -> " + writeRightHandSide();
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


    public ProductionRuleSide getRightHandSide() {
        return rightHandSide;
    }

    public void setRightHandSide(List<Symbol> rightHandSide) {
        this.rightHandSide = new ProductionRuleSide(rightHandSide);
    }

    public void setRightHandSide(ProductionRuleSide rightHandSide) {
        this.rightHandSide = rightHandSide;
    }

    public void addToLeftHandSide(Symbol symbol) {
        leftHandSide.addSymbol(symbol);
    }

    public void addToRightHandSide(Symbol symbol) {
        rightHandSide.addSymbol(symbol);
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
        if(obj instanceof ProductionRule) {
           ProductionRule rule = (ProductionRule)obj;
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



    public int compareTo(ProductionRule o) {
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
        ProductionRule obj = new ProductionRule();
        obj.setLeftHandSide((ProductionRuleSide) getLeftHandSide().clone());
        obj.setRightHandSide((ProductionRuleSide) getRightHandSide().clone());
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
