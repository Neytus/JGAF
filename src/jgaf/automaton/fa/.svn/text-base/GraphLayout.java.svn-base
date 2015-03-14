/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class GraphLayout {
//public class ForceDirectedLayout<N, E> extends GraphLayout<N, E> {

//    private Random random = new Random();
//    private boolean firstTime = true;
//
//    public void performGraphLayout(Automaton automaton) {
//        //ObjectScene scene = graph.getScene();
//        //Collection<N> allNodes = graph.getNodes();
//        Collection<State> allNodes = automaton.getStates();
//        Map<State, Point> forceForEachNode = new HashMap<State, Point>();
//
//
//
//        if (firstTime) {
//            List<Point> list = new ArrayList<Point>();
//            for (State state : allNodes) {
//                Point point = new Point(random.nextInt(600), random.nextInt(600));
//                list.add(point);
//            }
//
//
//            for (int i = 0; i < (list.size() / 2); i++) {
//                Point p = list.get(i);
//
//                for (int j = i + 1; j < list.size(); j++) {
//                    Point p2 = list.get(j);
//                    double distance = Math.sqrt(Math.pow(p2.x - p.x, 2) + Math.pow(p2.y - p.y, 2));
//                    if (distance < 40) {
//                        p2.translate(30, 30);
//                    }
//                }
//
//            }
//
//
//            for (State state : allNodes) {
//                Point p = list.remove(0);
//                //setResolvedNodeLocation(graph, State, p);
//                state.getVisualProperties().setCoordinates(p.x, p.y);
//                state.getVisualProperties().setCoordinates(p.x, p.y);
//            }
//            firstTime = false;
//
//            return;
//        }
//
//        //initialize forceForEachNode map
//        for (State state : allNodes) {
//            forceForEachNode.put(state, new Point(0, 0));
//        }
//
//
//        //Calculate repulsion forces
//for (State state : allNodes) {
//            HashSet<State> allOtherNodes = new HashSet<State>(allNodes);
//            allOtherNodes.remove(state);
//            for (Iterator<State> it1 = allOtherNodes.iterator(); it1.hasNext();) {
//                State otherNode = it1.next();
//
//
//                Point thisWidget = new Point (state.getVisualProperties().getXPos(),state.getVisualProperties().getYPos());
//                Point otherWidget = new Point (otherNode.getVisualProperties().getXPos(),otherNode.getVisualProperties().getYPos());
//                if ((null != thisWidget) && (null != otherWidget)) {
//                    int x = thisWidget.x - otherWidget.x;
//                    int y = thisWidget.y - otherWidget.y;
//                    double sum = Math.pow(x, 2) + Math.pow(y, 2);
//                    double distance = Math.sqrt(sum);
//                    double minDist = 50.0;
//                    x = ((int) (x * (minDist / distance))); //inversely proportional to the distance
//                    y = ((int) (y * (minDist / distance)));
//                    //Point force = new Point(x,y);
//               //     Point currentForce = forceForEachNode.get(thisNode);
//               //     currentForce.translate(x, y);
//                }
//            }
//        }
//
//        // get got all the repulsion forces.
//
//        //now the attraction forces.
//for (State thisNode : allNodes) {
//            //for (E edge : graph.findNodeEdges(thisNode, true, false)) {
//                Set<Transition> set = new HashSet<Transition>();
//                for (Transition transition : thisNode.getOutgoingTransitions()) {
//                    set.add(transition);
//
//                }
//                for (Transition transition : thisNode.getIncomingTransitions()) {
//                    set.add(transition);
//                }
//                for (Transition edge : set) {
//
//                State otherNode = edge.getToState();
//                if (otherNode != null) {
//                    Point thisWidget = new Point (thisNode.getVisualProperties().getXPos(),thisNode.getVisualProperties().getYPos());
//                    Point otherWidget = new Point (otherNode.getVisualProperties().getXPos(),otherNode.getVisualProperties().getYPos());
//                    if ((null != thisWidget) && (null != otherWidget)) {
//
//                        int x = thisWidget.x - otherWidget.x;
//                        int y = thisWidget.y - otherWidget.y;
//                        double sum = Math.pow(x, 2) + Math.pow(y, 2);
//                        double distance = Math.sqrt(sum);
//                        double minDist = 50.0;
//                        x = -((int) (x * (distance / 30))); //directly proportional to the distance
//                        y = -((int) (y * (distance / 30)));
//
//                        //Point force = new Point(x,y);
//                        Point currentForce = forceForEachNode.get(thisNode);
//                        currentForce.translate(x, y);
//                        currentForce = forceForEachNode.get(otherNode);
//                        currentForce.translate(-x, -y);
//                    }
//                }
//            }
//        }
//
//        //Use the force  (silly joke)
//for (State object : allNodes) {
//            Point force = forceForEachNode.get(object);
//            int x = force.x / 50;
//            int y = force.y / 50;
//            if (x > 0) {
//                x = Math.min(x, 15);
//            } else {
//                x = Math.max(x, -15);
//            }
//            if (y > 0) {
//                y = Math.min(y, 15);
//            } else {
//                y = Math.max(y, -15);
//            }
//            //x = x+((int) (x * (((float)random.nextInt(5)) / 100.0)));
//            //y = y+((int) (y * (((float)random.nextInt(5)) / 100.0)));
//            x = x + (-3+random.nextInt(6));
//            y = y + (-3+random.nextInt(6));
//
//
//            Point newPosition = new Point (object.getVisualProperties().getXPos(),object.getVisualProperties().getYPos());
//            if (null != newPosition) {
//                newPosition.translate(x, y);
//             //   setResolvedNodeLocation(graph, object, newPosition);
//                object.getVisualProperties().setCoordinates(newPosition.x, newPosition.y);
//            }
//
//        }
//    }
//
    
}
