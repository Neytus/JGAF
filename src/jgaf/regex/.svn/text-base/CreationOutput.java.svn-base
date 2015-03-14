/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.regex;

/**
 *
 * @author hanis
 */
public class CreationOutput {

    public static final int OK = 0;
//    public static final int PARENTHESES_INBALANCED = 1;
//    public static final int NONATOMIC_SYMBOL = 2;
//    public static final int WRONG_NUMBR_OF_OPERANDS = 3;

    public static final String PARENTHESES_INBALANCED = "Parentheses inbalanced";
    public static final String NONATOMIC_SYMBOL = "Nonatomic symbol";
    public static final String WRONG_NUMBR_OF_OPERANDS = "Wrong number of operands";


    private int output;
    private int from;
    private int to;


    public CreationOutput(int output, int from, int to) {
        this.output = output;
        this.from = from;
        this.to = to;
    }

    public CreationOutput(int output, int pointer) {
        this(output, pointer, pointer);
    }
    public CreationOutput(int output) {
        this(output, 0, 0);
    }

    public int getOutput() {
        return output;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }


}
