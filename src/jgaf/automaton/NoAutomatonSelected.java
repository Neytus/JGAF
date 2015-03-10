/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton;

/**
 *
 * @author hanis
 */
public class NoAutomatonSelected extends Exception {

    /**
     * Creates a new instance of <code>NoAutomatonSelected</code> without detail message.
     */
    public NoAutomatonSelected() {
    }


    /**
     * Constructs an instance of <code>NoAutomatonSelected</code> with the specified detail message.
     * @param msg the detail message.
     */
    public NoAutomatonSelected(String msg) {
        super(msg);
    }
}
