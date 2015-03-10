/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

/**
 *
 * @author hanis
 */
public class WrongProcedureInputException extends Exception {

    /**
     * Creates a new instance of <code>WrongProcedureInputException</code> without detail message.
     */
    public WrongProcedureInputException() {
    }


    /**
     * Constructs an instance of <code>WrongProcedureInputException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public WrongProcedureInputException(String msg) {
        super(msg);
    }
}
