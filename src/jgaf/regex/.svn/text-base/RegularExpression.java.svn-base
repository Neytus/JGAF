/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import jgaf.Constants.MathConstants;
import jgaf.Representation;

/**
 *
 * @author hanis
 */
public class RegularExpression implements Representation {


    private int operatorPosition = 0;


    private TreePosition treePosition;

    private SortedSet<String> alphabet;
    private RegularExpresionType type;
    private Operation operation;
    private RegularExpression leftChild;
    private RegularExpression rightChild;
    private String atomicName;
    private RegularExpression parent = null;
    private String name = "";
    private int encapsulationCount=0;
    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";


    public RegularExpression(String ex) throws WrongExpressionException {
        this(ex, null, TreePosition.ROOT);
    }

    private RegularExpression(String ex, RegularExpression parent, TreePosition treePosition) throws WrongExpressionException {
        this.parent = parent;
        this.treePosition = treePosition;
        create(ex);
      //  if(create(ex).getOutput() != CreationOutput.OK) {
//            throw new WrongExpressionException();
     //   }
    }

    public RegularExpression() {
        this.treePosition = TreePosition.ROOT;        
    }


    public RegularExpresionType getType() {
        return type;
    }

    public void setType(RegularExpresionType type) {
        this.type = type;
    }


    private void create(String ex) throws WrongExpressionException {
        //System.out.println(ex);
        alphabet = new TreeSet<String>();
        int encapsulation = checkEncapsulation(ex);
        if(encapsulation == -2) {
            encapsulationCount++;
            create(removeEncapsulation(ex));
            return;
        } else if(encapsulation > -1) {
            throw new WrongExpressionException(CreationOutput.PARENTHESES_INBALANCED + ", " + encapsulation + ", " + encapsulation);
        }
        List<Operation> operatorSequence = new ArrayList<Operation>();
        List<Integer> pointerSequence = new ArrayList<Integer>();

        int depth = 0;
        for (int i = 0; i < ex.length(); i++) {
            String letter = String.valueOf(ex.charAt(i));
            if(letter.equals(LEFT_PARENTHESIS)) {
                depth++;
            } else if(letter.equals(RIGHT_PARENTHESIS)) {
                depth--;
            } else if (Operation.isOperation(letter) & depth == 0) {
                Operation op = new Operation(letter);
                operatorSequence.add(op);
                pointerSequence.add(i);
            }
        }
        if (!operatorSequence.isEmpty()) {
            setType(RegularExpresionType.COMPOSED);
        } else { //TODO
            if (ex.equals("")) {
                //setType(RegularExpresionType.EPSILON);
                if (treePosition == TreePosition.ROOT) {
                    setType(RegularExpresionType.EMPTY);
                } else {
                    throw new WrongExpressionException(CreationOutput.WRONG_NUMBR_OF_OPERANDS + ", " + parent.getOperatorPosition() + ", " + parent.getOperatorPosition());
                }
            } else if (ex.equals(MathConstants.EPSILON)) {
                setType(RegularExpresionType.EPSILON);
            } else if (ex.length() == 1) {
                setType(RegularExpresionType.ATOMIC);
                atomicName = ex;
                alphabet.add(ex);
            } else {
             //   System.out.println(ex + ",," + ex.length());
                int index = getIndexIndexPosition(0, ex.length());
                throw new WrongExpressionException(CreationOutput.NONATOMIC_SYMBOL + ", " + index + ", " + (index + ex.length() - 1));
                //return new CreationOutput(CreationOutput.NONATOMIC_SYMBOL, index, index + ex.length());
            }
            return;
        }


        int index = getLeastPriorityOperatorIndex(operatorSequence);
        this.operation = operatorSequence.get(index);
        setOperatorPosition(pointerSequence.get(index), ex.length());
        String left = ex.substring(0, pointerSequence.get(index));
        String right = ex.substring(pointerSequence.get(index) + 1);

        if (operation.getArity() == 2) {
            leftChild = new RegularExpression(left, this, TreePosition.LEFT_CHILD);
            rightChild = new RegularExpression(right, this, TreePosition.RIGHT_CHILD);
            alphabet.addAll(leftChild.getAlphabet());
            alphabet.addAll(rightChild.getAlphabet());
        } else if (operation.getArity() == 1) {
            if (!right.equals("")) {
                throw new WrongExpressionException(CreationOutput.WRONG_NUMBR_OF_OPERANDS + ", " + operatorPosition + ", " + operatorPosition);
            }
            leftChild = new RegularExpression(left, this, TreePosition.LEFT_CHILD);
            alphabet.addAll(leftChild.getAlphabet());
        }
        //return new CreationOutput(CreationOutput.OK);
    }


    private  void setOperatorPosition(int index, int exLength) {
        operatorPosition = getIndexIndexPosition(index, exLength);
    }


    private int getIndexIndexPosition(int index, int exLength) {
        switch(treePosition) {
            case ROOT: {
                return index + encapsulationCount;
            }
            case LEFT_CHILD: {
                return parent.getOperatorPosition() - exLength - encapsulationCount + index;
            }
            case RIGHT_CHILD: {
                //System.out.println(parent.getOperatorPosition() +"," + encapsulationCount +","+ index);
                return parent.getOperatorPosition() + encapsulationCount + index + 1;
            }
        }
        return 0;
    }

    public int getOperatorPosition() {
        return operatorPosition;
    }


    private String removeEncapsulation(String ex) {
            return ex.substring(1, ex.length() - 1);

    }

    /**
     *
     * @param ex the regular expression string
     * @return -2 if parentheses are balanced and ex is encapsulated
     *         -1 if parentheses are balanced and ex is not encapsulated
     *          the position of violation of parentheses balancing otherwise
     */
    private int checkEncapsulation(String ex) {
        int depth = 0;
        int suspect = 0;
        boolean encapsulated = true;
        if(!ex.startsWith("(")) {
            encapsulated = false;
        }
        for (int i = 0; i < ex.length(); i++) {
            String letter = String.valueOf(ex.charAt(i));
            if (letter.equals("(")) {
                depth++;
                if(depth == 1) {
                    suspect = i;
                }
            } else if (letter.equals(")")) {
                depth--;
                if (depth < 0) {
                    return i;
                }
            }
            if (depth == 0 && i != ex.length() - 1) {
                encapsulated = false;
            }
        }
        if (depth != 0) {
            return suspect;
        }
        return encapsulated ? -2 : -1;
    }

//
//    public static CreationOutput(String ex) {
//
//    }

    private int getLeastPriorityOperatorIndex(List<Operation> operatorSequence) {
        int least = 10;
        int index = 0;
        int i=0;
        for (Operation op : operatorSequence) {
            if(least > op.getPriority()) {
                least = op.getPriority();
                index = i;
            }
            i++;
        }
        return index;
    }

    @Override
    public String toString() {
        switch(type) {
            case ATOMIC: return atomicName;
            case COMPOSED: {
                if(operation.getArity() == 1) {
                    return "(" + leftChild + operation + ")";
                } else if(operation.getArity() == 2) {
                    return "(" + leftChild + operation + rightChild + ")";
                }
            }
            case EMPTY: return MathConstants.EMPTY_SET;
            case EPSILON: return MathConstants.EPSILON;
            default: return "";
        }
    }

    public String writeAll() {
        String leftP = "";
        String rightP = "";
        for (int i = 0; i < encapsulationCount; i++) {
            leftP += "(";
            rightP += ")";
        }

        switch (type) {
            case ATOMIC:
                return leftP + atomicName + rightP;
            case COMPOSED: {
                if (operation.getArity() == 1) {
                    return leftP + leftChild.writeAll() + operation + rightP;
                } else if (operation.getArity() == 2) {
                    return leftP + leftChild.writeAll() + operation + rightChild.writeAll() + rightP;
                }
            }
            case EMPTY:
                return MathConstants.EMPTY_SET;
            case EPSILON:
                return leftP + MathConstants.EPSILON + rightP;
            default:
                return "";
        }
    }


    public String geiveMePositionos() {
        switch(type) {
            case ATOMIC: return atomicName;
            case COMPOSED: {
                if(operation.getArity() == 1) {
                    return "\n" + leftChild.geiveMePositionos() + operation +"(" + getOperatorPosition() + ")\n";
                } else if(operation.getArity() == 2) {
                    return "\n" + leftChild.geiveMePositionos() + operation +"(" + getOperatorPosition() + ")" + rightChild.geiveMePositionos() + "\n";
                }
            }
            case EMPTY: return MathConstants.EMPTY_SET;
            case EPSILON: return MathConstants.EPSILON;
            default: return "";
        }
    }


    public String writeFullyEncapsuleted() {
        return toString();
    }


    public String writeConcise() {
        switch(type) {
            case ATOMIC: return atomicName;
            case COMPOSED: {
                boolean writeP = false;
                if(parent != null && parent.getOperator().getPriority() > operation.getPriority()) {
                    writeP = true;
                }
                String string = writeP ? "(" : "";
                if(operation.getArity() == 1) {
                    string += leftChild.writeConcise() + operation.toString();
                } else if(operation.getArity() == 2) {
                    string += leftChild.writeConcise() + operation.toString() + rightChild.writeConcise();
                }
                string += writeP ? ")" : "";
                return string;
            }
            case EMPTY: return MathConstants.EMPTY_SET;
            case EPSILON: return MathConstants.EPSILON;
            default: return "";
        }
    }


    public Operation getOperator() {
        return operation;
    }

    public static void main(String[] args) {
        //String re = "a.a.(b+a)*.(a*+c).d+f";
        //String re = "(((a.a.(b+a)*.(a*+c.d+f))*))";
        String re = "(((a.a.(b+aa)*.(a*+c.d+f))*))";
        RegularExpression regex = null;
        try {
        regex = new RegularExpression(re);
        }  catch (WrongExpressionException ex) {
            System.out.println("err" + ex.getMessage());
        }
        System.out.println(re);
        //CreationOutput create = regex.create(re);
//        System.out.println(create.getOutput());
//        System.out.println(create.getFrom());
//        System.out.println(create.getTo());
//        System.out.println(re);
        System.out.println(regex);
//        System.out.println(regex.writeConcise());
//        System.out.println(regex.getAlphabet());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortedSet<String> getAlphabet() {
        return alphabet;
//        SortedSet<String> all = new TreeSet<String>();
//        all.addAll(alphabet);
//        if(leftChild != null) {
//            all.addAll(leftChild.getAlphabet());
//        }
//        if(rightChild != null) {
//            all.addAll(rightChild.getAlphabet());
//        }
//        return all;
    }


    public String writeAlphabet() {
        if(alphabet.isEmpty()) {
            return MathConstants.SIGMA + " =" + MathConstants.EMPTY_SET;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(MathConstants.SIGMA + " = {");
        for (String letter : alphabet) {
            sb.append(letter).append(", ");
        }
        return sb.substring(0, sb.length() - 2) + "}";        
    }


    @Override
    public Representation clone() {
        return new RegularExpression(writeAll());
    }

}
