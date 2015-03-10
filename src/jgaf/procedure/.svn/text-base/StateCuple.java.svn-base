/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class StateCuple {

    private State s1;
    private State s2;


    public StateCuple(State s1, State s2) {
        this.s1 = s1;
        this.s2 = s2;
    }


    public State getS1() {
        return s1;
    }

    public void setS1(State s1) {
        this.s1 = s1;
    }

    public State getS2() {
        return s2;
    }

    public void setS2(State s2) {
        this.s2 = s2;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof StateCuple) {
           StateCuple cuple = (StateCuple) obj;
           if(cuple.getS1().equals(s1) && cuple.getS2().equals(s2)) {
                return true;
           }
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + s1.getName() + ", " + s2.getName() + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.s1 != null ? this.s1.hashCode() : 0);
        hash = 29 * hash + (this.s2 != null ? this.s2.hashCode() : 0);
        return hash;
    }




}

