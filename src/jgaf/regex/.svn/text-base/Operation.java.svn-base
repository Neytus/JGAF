/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.regex;

/**
 *
 * @author hanis
 */
public final class Operation {

    public static final int CONCATENATION = 0;
    public static final int UNION = 1;
    public static final int ITERATION = 2;

    private int type;
    private int priority;
    private int arity;
    private String name;



    public Operation(int type) {
        if(type < 0 || type > 2) {
            throw new WrongExpressionException();
        }
        setType(type);
    }

    public Operation(String op) {
        if(op.equals(".")) {
            setType(CONCATENATION);
        } else if(op.equals("+")) {
            setType(UNION);
        } else if(op.equals("*")) {
            setType(ITERATION);
        } else {
            throw new WrongExpressionException();
        }
    }

    public int getType() {
        return type;
    }

    public int getPriority() {
        return priority;
    }


    public int getArity() {
        return arity;
    }

   
    public String getName() {
        return name;
    }


    private void setType(int type) {
        this.type = type;
        switch(type) {
            case CONCATENATION: {
                this.priority = 2;
                this.arity = 2;
                this.name = ".";
                break;
            }
            case ITERATION: {
                this.priority = 3;
                this.arity = 1;
                this.name = "*";
                break;
            }
            case UNION: {
                this.priority = 1;
                this.arity = 2;
                this.name = "+";
                break;
            }
        }
    }

    @Override
    public String toString() {
        return getName();
    }


    public static boolean isOperation(String op) {
        return op.equals("*") || op.equals(".") || op.equals("+");
    }

}
