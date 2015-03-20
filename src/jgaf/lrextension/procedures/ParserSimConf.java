/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.lrextension.procedures;

/**
 *
 * @author g
 */
public class ParserSimConf {
    private ParseStep step;
    private int gotoState;
    private int actionState;
    private String log;

    public ParserSimConf(ParseStep step,
                         int gotoState,
                         int actionState,
                         String log) {
        this.step = step;
        this.gotoState = gotoState;
        this.actionState = actionState;
        this.log = log;
    }
    
    
}
