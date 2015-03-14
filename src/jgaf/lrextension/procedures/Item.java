/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jgaf.grammar.ProductionRules;
import jgaf.grammar.Symbol;
import jgaf.lrextension.WString;
/**
 *
 * @author g
 */
public class Item {
    private  ProductionRules rule;
    private  Set<WString> locFo;
    private  int position ;
    private  int toItemSet;
    private  Color[] coloring;
    //equals hash a bla
    
    public Item(){
    }
    
    public Item(ProductionRules rule,int position,Set<WString> locFo){
    this.rule=rule;  
    this.position=position;
    
    this.locFo=locFo;
    this.toItemSet=-1;
    coloring=new Color[4];
    decolor();
    }

    public Item(ProductionRules rule,int position){
    this.rule=rule;  
    this.position=position;
    this.toItemSet=-1;
    coloring=new Color[4];
    decolor();
    }
    
    public int getToItemSet() {
        return toItemSet;
    }

    public void setToItemSet(int toItemSet) {
        this.toItemSet = toItemSet;
    }
    
    public boolean dotInRange(){
        //return rule.getRightHandSide().size()>position;
        return rule.getRightHandSide().getRules().get(0).size()>position;
    }
    
    public Symbol atDot(){
    if (dotInRange() )
        return rule.getRightHandSide().getSymbolAt(position);
    else return null;
    }
    
    public boolean isShiftable(){
          return (dotInRange()&& !atDot().isEpsilon());
    }
    
    public WString toRead(){
    
        if (isShiftable()){
            //List<Symbol> subList= rule.getRightHandSide().getSymbols().subList(position+1, rule.getRightHandSide().getSymbols().size());
            List<Symbol> subList= rule.getRightHandSide().getRules().get(0).getSymbols().subList(position+1, rule.getRightHandSide().getSymbols().size());
            WString r = new WString(subList);
            return r;}
        else return null;
    }
    
    public WString allToRead(){
    
        if (isShiftable()){
            //List<Symbol> subList= rule.getRightHandSide().getSymbols().subList(position, rule.getRightHandSide().getSymbols().size());
           List<Symbol> subList= rule.getRightHandSide().getRules().get(0).getSymbols().subList(position, rule.getRightHandSide().getSymbols().size());
        WString r = new WString(subList);
        return r ;}
        else return null;
    }
    
    public boolean needsClosure(){
        return dotInRange() && atDot().isNonterminal();
    }
 
    public Set<WString> getLocFo() {
        return locFo;
    }

    public void setLocFo(Set<WString> locFo) {
        this.locFo = locFo;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ProductionRules getRule() {
        return rule;
    }

    public void setRule(ProductionRules rule) {
        this.rule = rule;
    }

    
    public boolean toSameState(Item item) {
       
        final Item other = item;
        if (this.rule != other.rule && (this.rule == null || !this.rule.equals(other.rule))) {
            return false;
        }
        if (this.locFo != other.locFo && (this.locFo == null || !this.locFo.equals(other.locFo))) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.rule != other.rule && (this.rule == null || !this.rule.equals(other.rule))) {
            return false;
        }
        if (this.locFo != other.locFo && (this.locFo == null || !this.locFo.equals(other.locFo))) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        if (this.toItemSet != other.toItemSet) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.rule != null ? this.rule.hashCode() : 0);
        hash = 67 * hash + (this.locFo != null ? this.locFo.hashCode() : 0);
        hash = 67 * hash + this.position;
        hash = 67 * hash + this.toItemSet;
        return hash;
    }

    

    
    public String to2String() {
        return (rule.toString()+position+locFo+toItemSet);
    }
    
    public String getRuleStr(){
        
        String s=
        rule.getRightHandSide().toString().substring(0,position)
        + "."+rule.getRightHandSide().toString().substring(position);
        return (rule.getLeftHandSide()+"->"+s);
        
        /*
        String s=
        rule.getRightHandSide().getRules().get(0).toString().substring(0,position)
        + "."+rule.getRightHandSide().getRules().get(0).toString().substring(position);
        return (rule.getLeftHandSide()+"->"+s);
        */
    }
    
    public String getLocFoStr(){
         if (locFo==null) return ("");
        StringBuilder sb=new StringBuilder();
        sb.append("{");
        String prefix = "";        
        for (List list:locFo){
             sb.append(prefix);
            prefix = ",";          
            for (Object symbol : list) {
                        sb.append(symbol);
            }
            
                }
       sb.append("}");
            return sb.toString();
    }
    
    public String getNextSymbolStr(){
        if (!isShiftable()) return "";
        return atDot().toString();
    }
    
    public String getToItemSetStr(){
        if (isShiftable()) {
             if (getToItemSet()==-1) {
            return "";
        }
            return (getToItemSet()+"");
        }
    else {
            return ("r( " +rule+")");
        }
    }
    
    @Override
    public String toString() {
        return (getRuleStr() +" "+getNextSymbolStr()+" "+ getToItemSetStr()+" "+getLocFoStr());
    }
    @Override
    public Item clone(){              
    Set<WString> newLocFo= new HashSet<WString>(locFo);
        Item i=new Item(rule, position, newLocFo);
    i.setToItemSet(getToItemSet());
    i.setColoring(coloring);
    return i;
    }
    
    public void decolor(){
    color(Color.BLACK);
    }
    
    public void color(int pos,Color col){ 
    coloring[pos]=col;
    }

    public void color(Color col){
    coloring[0]=col;
    coloring[1]=col;
    coloring[2]=col;
    coloring[3]=col;}
    
    public Color getColor(int pos){
    return coloring[pos];
    }

    public void setColoring(Color[] coloring) {
        int i=0;
        for (Color color : coloring) {
          this.coloring[i]=color;  
          i++;
        }
    }
    public Symbol getLeftSymbol(){ 
            return rule.getLeftHandSide().getFirst();
                    }
    
}
