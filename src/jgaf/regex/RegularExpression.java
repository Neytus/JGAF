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
    private List<String> n_tree = new ArrayList<>();
    private List<Integer> leastOperationsPositions = new ArrayList<>();


    public RegularExpression(String ex) throws WrongExpressionException {
        this(ex, null, TreePosition.ROOT);
    }

    private RegularExpression(String ex, RegularExpression parent, TreePosition treePosition) throws WrongExpressionException {
        this.parent = parent;
        this.treePosition = treePosition;
        create(ex);
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


    private void create(String sample) throws WrongExpressionException {
        String ex = sample.trim();
        
        alphabet = new TreeSet<>();
        int encapsulation = checkEncapsulation(ex);
        if(encapsulation == -2) {
            
            encapsulationCount++;
            create(removeEncapsulation(ex));
            return;
        } else if(encapsulation > -1) {
            throw new WrongExpressionException(CreationOutput.PARENTHESES_INBALANCED + ", " + encapsulation + ", " + encapsulation);
        }
        List<Operation> operatorSequence = new ArrayList<>();
        List<Integer> pointerSequence = new ArrayList<>();
        
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
        } else { 
            if (ex.equals("")) {
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
                int index = getIndexIndexPosition(0, ex.length());
                throw new WrongExpressionException(CreationOutput.NONATOMIC_SYMBOL + ", " + index + ", " + (index + ex.length() - 1));
            }
            return;
        }
        
        int index = getLeastPriorityOperatorIndex(operatorSequence);
        
        List<Integer> indexList = getSameLeastPriorityOperatorIndex(operatorSequence);

        for(int i=0; i<indexList.size();i++){
            this.leastOperationsPositions.add(pointerSequence.get((int) indexList.get(i)));
        }
        this.operation = operatorSequence.get(index);
        
        setOperatorPosition(pointerSequence.get(index), ex.length());
       
        
        int lefti = 0;
        int righti = 0;
        String newEx = "";
        for(int i=0; i<=indexList.size();i++){

            if(i == indexList.size()){
                newEx = ex.substring(lefti);

                lefti= righti+1;
                n_tree.add(newEx);
            }else{
                int actual = (int) indexList.get(i);
                righti = pointerSequence.get(actual);

                newEx = ex.substring(lefti, righti);

                lefti= righti+1;
                n_tree.add(newEx);
            }
        }
      
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
                return parent.getOperatorPosition() + encapsulationCount + index + 1;
            }
            case CHILD: {
                return parent.getOperatorPosition() - exLength - encapsulationCount + index;
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
    
    private List<Integer> getSameLeastPriorityOperatorIndex(List<Operation> operatorSequence) {
        int least = 10;
        int index = 0;
        List<Integer> list = new ArrayList<>();
        for (Operation op : operatorSequence) {
            if(least > op.getPriority()) {
                least = op.getPriority();
            }
        } 
        int i=0;
        for (Operation op : operatorSequence){
            if(least == op.getPriority()){
                list.add(new Integer(i));
            }
            i++;
        }
        return list;
    }
    

    @Override
    public String toString() {
        
        switch(type) {
            case ATOMIC: return atomicName;
            case COMPOSED: {
                if(operation.getArity() == 1) {
                    return "(" + leftChild + operation +")";
                } else if(operation.getArity() == 2) {
                    return "(" + leftChild + operation +rightChild + ")";
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
        String re1 = "(a+b+c)";
        String re2 = "(a+b+c) ";
        String re3 = "c*";
        RegularExpression regex = null;
        RegularExpression regex2 = null;
        RegularExpression regex3 = null;
        try {
        regex = new RegularExpression(re1);
        regex2 = new RegularExpression(re2);
        regex3 = new RegularExpression(re3);
        }  catch (WrongExpressionException ex) {
            System.out.println("err" + ex.getMessage());
        }
       System.out.println(regex.equals(regex2));
    }

    public List<List<RegularExpression>> getAllPosibilities(){
        List<List<RegularExpression>> newList = new ArrayList<>();
        
        for(int i=0; i<n_tree.size()-1;i++){
            List<String> left = new ArrayList<>();
            List<String> right = new ArrayList<>();
            List<RegularExpression> helpList = new ArrayList<>();
            //vytvoř levou
            
            for(int j=0; j<=i; j++){
                left.add(n_tree.get(j));
            }
            helpList.addAll(concatenateRegEx(left, operation));
            for(int j=i+1; j<n_tree.size(); j++){
                right.add(n_tree.get(j));
            }
            helpList.addAll(concatenateRegEx(right, operation));

            newList.add(helpList);
        }
      
        return newList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortedSet<String> getAlphabet() {
        return alphabet;

    }

    
    public List<RegularExpression> concatenateRegEx(List<String> listRegEx, Operation op){
        StringBuilder sb = new StringBuilder();
        List<RegularExpression> result = new ArrayList<>();
        if(listRegEx.size() == 1) {
            result.add(new RegularExpression(listRegEx.get(0)));
            return result;
        }
        int i=0;
        for(String regEx : listRegEx){
            if(i<listRegEx.size()-1){
                sb.append(regEx.toString()).append(op.getName());
            }else{
                sb.append(regEx.toString());
            }
            i++;
        }
        RegularExpression exp = new RegularExpression(sb.toString());
        result.add(exp);
        return result;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegularExpression other = (RegularExpression) obj;
        if (this.alphabet != other.alphabet && (this.alphabet == null || !this.alphabet.equals(other.alphabet))) {
            System.out.println("Abeceda nesedí");
            return false;
        }
        if (this.type != other.type) {
            System.out.println("Typ");
            return false;
        }
        if (this.operation != other.operation && (this.operation == null || !this.operation.equals(other.operation))) {
           System.out.println("operace " );
            return false;
        }
        if (this.leftChild != other.leftChild && (this.leftChild == null || !this.leftChild.equals(other.leftChild))) {
            System.out.println("leve decko");
            return false;
        }
        if (this.rightChild != other.rightChild && (this.rightChild == null || !this.rightChild.equals(other.rightChild))) {
            System.out.println("pprave decko");
            return false;
        }
        if ((this.atomicName == null) ? (other.atomicName != null) : !this.atomicName.equals(other.atomicName)) {
            System.out.println("aotmicka ");
            return false;
        }
        return true;
    }

    public void setLeftChild(RegularExpression leftChild){
        this.leftChild = leftChild;
    }
    public RegularExpression getLeftChild(){
        return this.leftChild;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.operatorPosition;
        hash = 37 * hash + (this.treePosition != null ? this.treePosition.hashCode() : 0);
        hash = 37 * hash + (this.alphabet != null ? this.alphabet.hashCode() : 0);
        hash = 37 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 37 * hash + (this.operation != null ? this.operation.hashCode() : 0);
        hash = 37 * hash + (this.leftChild != null ? this.leftChild.hashCode() : 0);
        hash = 37 * hash + (this.rightChild != null ? this.rightChild.hashCode() : 0);
        hash = 37 * hash + (this.atomicName != null ? this.atomicName.hashCode() : 0);
        hash = 37 * hash + (this.parent != null ? this.parent.hashCode() : 0);
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

}
