/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.graphics.drawer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *  Class performing graph drawing algorithm described in
 *  "Algoritmy pro vykreslovani orientovanych grafu" by Ondrej Kohut
 * @author TERRMITh
 */
public class AutomaticAutomatonDrawer {

    private static Automaton a;
    private static Map<State, Set<State>> matrix;
    private static int depth;
    /**
     * standardni vzdalenost mezi vrcholy
     */
    private static final int LEFT_BOUNDARY = 50;
    private static final int RIGHT_BOUNDARY = 6090;
    private static final int TOP_BOUNDARY = 50;
    private static final int BOTTOM_BOUNDARY = 6630;
    private static final int DEFAULT_LEN = 125;
    private static final int MAX_X = (int) (RIGHT_BOUNDARY / DEFAULT_LEN);
    private static final int MAX_Y = (int) (BOTTOM_BOUNDARY / DEFAULT_LEN);

    private AutomaticAutomatonDrawer() {
    }

    /**
     * Calls all methods needed for placing states in correct coordinates
     * @param automaton
     */
    public static void setAllCoordinates(Automaton automaton) {
        a = automaton;
        if (!a.getStates().isEmpty()) {
            createMatrix();
//        print();
            putIntoLayers();  
            if(matrix.keySet().size()<MAX_X*MAX_Y){
                correctOverflow();
            }
            //      print();
          //  System.out.println("AutomaticDrawer: reduceEdgeIntersections();");
    reduceEdgeIntersections();
            createFullMatrix();
        
            reduceEdgeIntersections();

    //        reduceEdgeIntersections();

         //   System.out.println("AutomaticDrawer: assignTrueCoordinates();");
            assignTrueCoordinates();
            reduceEdgeIntersections();
                setCurveFactors();
           //  assignTrueCoordinatesOld();
     //       createCurves();
            //  print();
        }
    }

    /**
     * creates partial graph matrix
     */
    private static void createMatrix() {
        State init = a.getInitialState();
        State state;
        matrix = new HashMap<State, Set<State>>();
        matrix.clear();
        Queue<State> fifo = new LinkedList<State>();

        fifo.add(init);
        while (!fifo.isEmpty()) {
            state = fifo.poll();
            Set<State> temp = new HashSet<State>();
            temp.clear();
            for (Transition t : a.getTransitions()) {
                if ((state.equals(t.getFromState()) && !temp.contains(t.getToState()) && (!matrix.containsKey(t.getToState())))
                        && !state.equals(t.getToState())) {
                    fifo.offer(t.getToState());
                    temp.add(t.getToState());

                } else if ((state.equals(t.getToState()) && !temp.contains(t.getFromState()) && (!matrix.containsKey(t.getFromState())))
                        && !state.equals(t.getFromState())) {
                    fifo.offer(t.getFromState());
                    temp.add(t.getFromState());

                }
            }
            //states not connected to the initial state are added to the end
            if (fifo.isEmpty()) {
                for (State s : a.getStates()) {
                    if (!matrix.keySet().contains(s)) {
                        fifo.add(s);
                        temp.add(s);
                        break;
                    }
                }
            }
            matrix.put(state, temp);

        }

    }

    /**
     * Creates matrix of neighbourity out of all transitions
     */
    private static void createFullMatrix() {

        matrix = new HashMap<State, Set<State>>();
        Map<State, Set<State>> oldMatrix = new HashMap<State, Set<State>>();
        oldMatrix.putAll(matrix);
        matrix.clear();


        for (State s : a.getStates()) {
            Set<State> temp = new HashSet<State>();
            temp.clear();

            for (Transition t : a.getTransitions()) {

                if (s.equals(t.getFromState()) && !temp.contains(t.getToState()) && !s.equals(t.getToState())) {
                    temp.add(t.getToState());
                } else if (s.equals(t.getToState()) && !temp.contains(t.getFromState()) && !s.equals(t.getFromState())) {
                    temp.add(t.getFromState());
                }
            }
            
//            for(State u:oldMatrix.get(s)){
//            if(!temp.contains(u)){
//            temp.add(u);
//            }
//            }
            matrix.put(s, temp);
        }
    }

    /**
     * First step
     */
    private static void putIntoLayers() {
        // klasicke prohledavani do sirky, pri kazdem kroku se nastavuje x, pokud x
        // neni nastavene(ie <= 0), znamena to, ze vrchol neni ve fronte a ani tam nikdy nebyl
        // zaroven dojde k nastaveni souradnice y
        State init = a.getInitialState();
        State state;
        Queue<State> fifo = new LinkedList<State>();

        init.getVisualProperties().setCoordinates(0, 0);
        fifo.add(init);

        int testValue = init.getVisualProperties().getXPos() - 1;
        int y = 0;
        while (!fifo.isEmpty()) {
            state = fifo.poll();
            if (testValue != state.getVisualProperties().getXPos()) {
                testValue = state.getVisualProperties().getXPos();
                y = 0;
            }
            for (State s : matrix.get(state)) {
                if (s.getVisualProperties().getXPos() <= 0 && !s.equals(init)) {

                    int x = state.getVisualProperties().getXPos() + 1;
                    s.getVisualProperties().setCoordinates(x, y);
                    y++;
                    fifo.offer(s);
                }
            }
        }
        depth = testValue;

    }

    /**
     * Counts intersections
     * @param u
     * @param v
     * @return
     */
    private static int countIntersections(State u, State v) {
        if (u.getVisualProperties().getYPos() >= v.getVisualProperties().getYPos()) {
            throw new IllegalArgumentException("musi platit y(u)<y(v)");
        }
        int count = 0;
        for (State w : matrix.get(u)) {

            if (w.getVisualProperties().getXPos() + 1 == u.getVisualProperties().getXPos()) {
                for (State z : matrix.get(v)) {
                    if (z.getVisualProperties().getXPos() + 1 == v.getVisualProperties().getXPos() && z.getVisualProperties().getYPos() < w.getVisualProperties().getYPos()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * returns state in layer x and position y
     * @param x
     * @return
     */
    private static State getStateOn(int x, int y) {
        for (State s : a.getStates()) {
            if (s.getVisualProperties().getYPos() == y && s.getVisualProperties().getXPos() == x) {
                return s;
            }
        }
        return null;
    }

    /**
     * Returns size of layer
     * @param x
     * @return
     */
    private static int getSizeOfLayer(int x) {
        int count = 0;
        for (int y = 0;; y++) {
            State u = getStateOn(x, y);
            if (u == null) {
                if (count == 0) {
                    throw new IllegalArgumentException("BUG FOUND! It is not possible for layer to have no states!");
                }
                return count;
            } else {
                count++;
            }
        }

    }

    /**
    //     * Prints results in standard output
    //     */
//    public static void print() {
//        for (State s : a.getStates()) {
//            System.out.println("" + s.getName() + "[" + s.getVisualProperties().getXPos() + "," + s.getVisualProperties().getYPos() + "]");
//        }
//        for (Transition s : a.getTransitions()) {
//            if(s.getFromState().equals((s.getToState()))){
//            System.out.println("" + s.getFromState().getName() + "[" + s.getControlPoint().x + "," + s.getControlPoint().y + "]");
//        }}
//    }
    /**
     * Algorithm adjacent-exchange similiar to bubble sort,
     * reduces edge intersections between layers
     */
    private static void reduceEdgeIntersections() {

        for (int j = 1; j <= depth; j++) {
            int oldCount = Integer.MAX_VALUE;
            int count = Integer.MAX_VALUE - 1;
            while (count < oldCount) {
                oldCount = count;
                count = 0;
                for (int i = 0;; i++) {
                    State u = getStateOn(j, i);
                    State v = getStateOn(j, i + 1);
                    if (u == null || v == null) {
                        break;
                    }
                    int countUV = countIntersections(u, v);
                    u.getVisualProperties().setCoordinates(j, i + 1);
                    v.getVisualProperties().setCoordinates(j, i);
                    int countVU = countIntersections(v, u);
                    if (countUV < countVU) {
                        count += countUV;
                        u.getVisualProperties().setCoordinates(j, i);
                        v.getVisualProperties().setCoordinates(j, i + 1);
                    } else {
                        count += countVU;
                    }

                }
            }
        }

    }

    /**
     * When state did not fit into image, metod will move it
     */
    private static void correctOverflow() {

        for (State s : matrix.keySet()) {
            int foundx = s.getVisualProperties().getXPos();
            int foundy = s.getVisualProperties().getYPos();
            double len = Double.MAX_VALUE;

            if (s.getVisualProperties().getXPos() >= MAX_X || s.getVisualProperties().getYPos() >= MAX_Y) {
                for (int x = 0; x < MAX_X; x++) {
                    for (int y = 0; y < MAX_Y; y++) {
                        double newLen = Math.sqrt(Math.pow(x - s.getVisualProperties().getXPos(), 2) + Math.pow(y - s.getVisualProperties().getYPos(), 2));
                        if (getStateOn(x, y) == null && newLen < len) {
                            len = newLen;
                            foundx = x;
                            foundy = y;
                        }
                    }
                }
            }
            s.getVisualProperties().setCoordinates(foundx, foundy);

        }
    }

    /**
     * Algorithm uses simple uniform method
     * Unused method for possible future use
     */
//    private static void assignTrueCoordinatesOld() {
//        int xspace = RIGHT_BOUNDARY / depth;
//        for (int x = 0; x <= depth; x++) {
//            int size = getSizeOfLayer(x);
//            if(size == 0){
//                System.err.println("0 states in "+x);
//            }
//            int yspace = BOTTOM_BOUNDARY / size;
//            for (int y = 0;; y++) {
//                State u = getStateOn(x, y);
//                if (u == null) {
//                    break;
//                }
//
//                int vertical;
//                if (u.getVisualProperties().getYPos() <= TOP_BOUNDARY) {
//                    vertical = TOP_BOUNDARY + u.getDiameter();
//                } else if (u.getVisualProperties().getYPos() * yspace >= BOTTOM_BOUNDARY) {
//                    vertical = BOTTOM_BOUNDARY - u.getDiameter();
//                } else {
//                    vertical = u.getVisualProperties().getYPos() * yspace + u.getDiameter();
//                }
//
//                int horizontal;
//                if (u.getVisualProperties().getXPos() <= LEFT_BOUNDARY) {
//                    horizontal = LEFT_BOUNDARY + u.getDiameter();
//                } else if (u.getVisualProperties().getXPos() * xspace >= RIGHT_BOUNDARY) {
//                    horizontal = RIGHT_BOUNDARY - (int) (u.getDiameter() * 1.5);
//                } else {
//                    horizontal = u.getVisualProperties().getXPos() * xspace;
//                }
//                u.getVisualProperties().setCoordinates(horizontal, vertical);
//            }
//        }
//    }

    /**
     * Algorithm uses simple uniform method
     */
    private static void assignTrueCoordinates() {
        for (State u : matrix.keySet()) {

            u.getVisualProperties().setCoordinates(u.getVisualProperties().getXPos() * DEFAULT_LEN + LEFT_BOUNDARY, u.getVisualProperties().getYPos() * DEFAULT_LEN + TOP_BOUNDARY);
            // assign suitable coordinates when is no space in desired layer
                if(u.getVisualProperties().getXPos() > RIGHT_BOUNDARY ||
            u.getVisualProperties().getYPos() > BOTTOM_BOUNDARY){
            u.getVisualProperties().setCoordinates(RIGHT_BOUNDARY,BOTTOM_BOUNDARY);
            }
        }
    }

    private static void setCurveFactors() {
        for (Transition transition : a.getTransitions()) {
            if(a.getTransitionFromTo(transition.getToState(), transition.getFromState()) != null) {
                transition.getVisualProperties().setCurveFactor(1);
            } else {
                transition.getVisualProperties().setCurveFactor(0);
            }
        }
    }

//    /**
//     * Creates curvers from straight transitions if necessary
//     */
//    private static void createCurves() {
//        for (State s : matrix.keySet()) {
//            for (State u : matrix.get(s)) {
//                Transition t = new Transition("test", s, u);
//                Transition it = new Transition("test", u, s);
//
//                if (a.contains(t) && a.contains(it) && !u.equals(s)) {
//                    a.getTransition(t.getToState(), t.getFromState()).setCurveControlPoint(30);
//                    a.getTransition(t.getFromState(), t.getToState()).setCurveControlPoint(30);
//                }
//            }
//        }
//        for (Transition t : a.getTransitions()) {
//            for (State s : a.getStates()) {
//
//                if (t.intersects(s) && !s.equals(t.getFromState()) && !s.equals(t.getToState())) {
//                    a.getTransition(t.getFromState(), t.getToState()).setCurveControlPoint(60);
//                } else if (t.getFromState().equals(t.getToState())) {
//                    System.out.println("AutomaticDrawer.createCurves: setting reflexive transition");
//                    t.setDefaults();
//                }
//            }
//        }
//
//    }
}
