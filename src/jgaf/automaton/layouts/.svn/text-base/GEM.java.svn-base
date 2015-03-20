/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.layouts;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class GEM {

    private static final double TEMPERETURE_INIT = 40;
    private static final double GRAVITATIONAL_CONSTANT = 1/66;
    private static final double DESIRED_EDGE_LENGTH = 70;
    private static final double MAXIMAL_TEMPERATURE = 256;
    private static final double ALPHA_O = Math.PI/2;
    private static final double ALPHA_R = Math.PI/3;
    private static final double SIGMA_O = 1/3.0;
    private static final double SIGMA_R =  1/20.0;


    private Automaton automaton;
    private Random random;
    private List<GEMVertex> vertices;
    private int counter;
    private Point barycenter;
    private List<Integer> permutation;

    public GEM(Automaton automaton) {
        this.automaton = automaton;
        this.random = new Random();        
        initialization(automaton);
    }

    public void setLayout(Automaton automaton) {        
        GEMVertex vertex = chooseVertex(vertices);
        Point currentImpuls = impulseComputation(vertex, vertices, automaton);
        temperatureAdjustment(vertex, currentImpuls);
    }

    public void placeVertex(State state) {
        for (GEMVertex vertex : vertices) {
            if (vertex.getState().equals(state)) {
                for (int i = 0; i < 8; i++) {
                    Point currentImpuls = impulseComputation(vertex, vertices, automaton);
                    temperatureAdjustment(vertex, currentImpuls);
                }
            }
        }
    }

    public void arrange() {
        int iterCount = vertices.size() * 5;
        for (int i = 0; i < iterCount; i++) {
            setLayout(automaton);
        }
    }


    public void arrangeAutomaton() {
        int iterCount = vertices.size() * 7;
        for (int i = 0; i < iterCount; i++) {
            setLayout(automaton);
        }
        initialization(automaton);
        for (int i = 0; i < iterCount; i++) {
            setLayout(automaton);
        }
        initialization(automaton);
        for (int i = 0; i < iterCount; i++) {
            setLayout(automaton);
        }
        setTransitionCurves();
    }


    private void setTransitionCurves() {
        for(Transition transition : automaton.getTransitions()) {
            if(automaton.isBidirectionalTransition(transition)) {
                transition.getVisualProperties().setCurveFactor(1);
            } else {
                transition.getVisualProperties().setCurveFactor(0);
            }
        }
    }


    private void initialization(Automaton automaton) {
        this.vertices = new ArrayList<GEMVertex>();
        for (State state : automaton.getStates()) {
            GEMVertex vertex = new GEMVertex(state);
            vertex.setSkewGauge(0);
            vertex.setLastImpulseX(0);
            vertex.setLastImpulseY(0);
            vertex.setLocalTemperature(TEMPERETURE_INIT);
            vertex.setDegree(automaton.getStateDegree(state));
            vertices.add(vertex);
        }
        setBarycenter();
        permutation = new ArrayList<Integer>();
    }

    private GEMVertex chooseVertex(List<GEMVertex> vertices) {
//        if(counter >= vertices.size()) {
//            counter = 0;
//        }
       // return vertices.get(random.nextInt(vertices.size()));
        if(counter >= permutation.size()) {
            newPermutation();
            counter=0;
        }
        return vertices.get(permutation.get(counter++));

       // return vertices.get(2);
        //return vertices.get(counter++);
    }




    private Point impulseComputation(GEMVertex chosenVertex, List<GEMVertex> vertices, Automaton automaton) {
        

        double sub = GRAVITATIONAL_CONSTANT * functionGrowing(chosenVertex);
        double x = (barycenter.getX()/vertices.size() - chosenVertex.getCurrentPosition().getX()) * sub;
        double y = (barycenter.getY()/vertices.size() - chosenVertex.getCurrentPosition().getY()) * sub;

        Point delta = getSmallRandomVector();
        x+=delta.x;
        y+=delta.y;
        for (GEMVertex vertex : vertices) {
            if(!vertex.equals(chosenVertex)) {
                int dx = chosenVertex.getCurrentPosition().x - vertex.getCurrentPosition().x;
                int dy = chosenVertex.getCurrentPosition().y - vertex.getCurrentPosition().y;
                if(dx != 0 || dy != 0) {
                    double sq = DESIRED_EDGE_LENGTH*DESIRED_EDGE_LENGTH/(dx*dx + dy*dy);
                    x+=dx*sq;
                    y+=dy*sq;

                }
                if(automaton.getTransitionFromTo(vertex.getState(), chosenVertex.getState()) != null ||
                        automaton.getTransitionFromTo(chosenVertex.getState(), vertex.getState()) != null) {

                    x-=dx*(dx*dx + dy*dy)/(DESIRED_EDGE_LENGTH*DESIRED_EDGE_LENGTH*functionGrowing(chosenVertex));
                    y-=dy*(dx*dx + dy*dy)/(DESIRED_EDGE_LENGTH*DESIRED_EDGE_LENGTH*functionGrowing(chosenVertex));


                }
            }
        }

        return new Point((int)x,(int)y);        
    }





    private void temperatureAdjustment(GEMVertex vertex, Point currentImpuls) {
        double x = currentImpuls.x;
        double y = currentImpuls.y;
       // System.out.println("x: " + x + ", y: " + y);
        if(x != 0 || y != 0) {
            x=vertex.getLocalTemperature()*x/Math.sqrt(x*x+y*y);
            y=vertex.getLocalTemperature()*y/Math.sqrt(x*x+y*y);
            int newX = vertex.getCurrentPosition().x + (int) x;
            int newY = vertex.getCurrentPosition().y + (int) y;
            vertex.setCurrentPosition(new Point(newX, newY));
            updateBarycenter(new Point((int) x,(int) y));
        }
        double oldX = vertex.getLastImpulseX();
        double oldY = vertex.getLastImpulseY();
    //    System.out.println("x: " + x + ", y: " + y + ", oldx: " + oldX + ", oldy: " + oldY + ", t: " + vertex.getLocalTemperature() +
    //            ", skew: " + vertex.getSkewGauge());

        if(oldX != 0 || oldY != 0) {
            double beta = getAngle(x, y, oldX, oldY);
            double betaAbs = (beta < Math.PI) ? beta : beta - Math.PI;
       //     System.out.println("beta: " + Math.toDegrees(beta));
            //if(Math.sin(beta) >= Math.sin(Math.PI/2 + ALPHA_R/2)) {
//            if(betaAbs >= Math.PI/7) {
//                System.out.println("---skew b: " + vertex.getSkewGauge());
//                vertex.setSkewGauge(vertex.getSkewGauge() + SIGMA_R*Math.signum(Math.sin(beta)));
//                System.out.println("---skew af: " + vertex.getSkewGauge());
//            }
            if(betaAbs >= (Math.PI - ALPHA_R)/2.0) {
         //       System.out.println("---skew b: " + vertex.getSkewGauge());
                vertex.setSkewGauge(vertex.getSkewGauge() + SIGMA_R*Math.sin(beta));
      //          System.out.println("---skew af: " + vertex.getSkewGauge());
            }
//            if(Math.abs(Math.cos(beta)) >= Math.cos(ALPHA_O/2)) {
//                System.out.println("---temp1 b: " + vertex.getLocalTemperature());
//                System.out.println("*** sigo: " + SIGMA_O + ", cos beta: " + Math.cos(beta));
//                vertex.setLocalTemperature(vertex.getLocalTemperature()*SIGMA_O*Math.cos(beta));
//                System.out.println("---temp1 a: " + vertex.getLocalTemperature());
//
//            }
            if(betaAbs >= Math.PI - (ALPHA_O/2.0)) {
        //        System.out.println("---temp1 b: " + vertex.getLocalTemperature());
         //       System.out.println("*** sigo: " + SIGMA_O + ", cos beta: " + Math.cos(beta));
               // vertex.setLocalTemperature(vertex.getLocalTemperature()*SIGMA_O*Math.cos(beta));
       //         System.out.println("---temp1 a: " + vertex.getLocalTemperature());

            }
        //    System.out.println("---temp2 b: " + vertex.getLocalTemperature());
            vertex.setLocalTemperature(vertex.getLocalTemperature() * (1 - Math.abs(vertex.getSkewGauge())));
       //     System.out.println("---temp23 ab: " + vertex.getLocalTemperature());
            vertex.setLocalTemperature(Math.min(vertex.getLocalTemperature(), MAXIMAL_TEMPERATURE));
      //      System.out.println("---temp3 a: " + vertex.getLocalTemperature());
     //   System.out.println("x: " + x + ", y: " + y + ", oldx: " + oldX + ", oldy: " + oldY + ", t: " + vertex.getLocalTemperature() +
     //           ", skew: " + vertex.getSkewGauge());
        }
        vertex.setLastImpulseX(x);
        vertex.setLastImpulseY(y);
    }


    private Point getSmallRandomVector() {
        int x = random.nextInt(20) - 10;
        int y = random.nextInt(20) - 10;
        return new Point(x,y);
    }


    private double functionGrowing(GEMVertex vertex) {
        int degree = vertex.getDegree();
        return degree*(1+degree/2);
    }



    private double getAngle(double ux, double uy, double vx, double vy) {
//        double dotProduct = ux*vx + uy*vy;
//        double denominator = Math.sqrt(ux*ux + uy*uy)*Math.sqrt(vx*vx + vy*vy);
//        return Math.acos(dotProduct/denominator);
//


        //return Math.atan2(uy-vy, ux-vx);
       // return Math.atan(ux/uy) - Math.atan(vx/vy);

        double phaseU = getPhase(ux, uy);
        double phaseV = getPhase(vx, vy);
        double angle = phaseV - phaseU;
        if(angle < 0) {
            angle += 2*Math.PI;
        }
     //   System.out.println("PHASE: " + phaseU + "," + phaseV + "," + angle);
        return angle;

    }






    private void setBarycenter() {
        int x = 0;
        int y = 0;
        for (GEMVertex vertex : vertices) {
            x += vertex.getCurrentPosition().x;
            y += vertex.getCurrentPosition().y;
        }
        barycenter = new Point(x,y);
    }



    private void updateBarycenter(Point impuls) {
        barycenter.setLocation(barycenter.getX() + impuls.getX(), barycenter.getY() + impuls.getY());       
    }







//
//
//
//
//
//
//
//
//    private static final double TEMPERETURE_INIT = 30;
//    private static final double GRAVITATIONAL_CONSTANT = 1/16;
//    private static final double DESIRED_EDGE_LENGTH = 100;
//    private static final double MAXIMAL_TEMPERATURE = 256;
//    private static final double ALPHA_O = Math.PI/4;
//    private static final double ALPHA_R = Math.PI/10;
//    private static final double SIGMA_O = 1.5;
//    private static final double SIGMA_R =  1;
//
//
//    private Automaton automaton;
//    private Random random;
//    private List<GEMVertex> vertices;
//    private int counter;
//    private Point2D barycenter;
//
//    public GEM(Automaton automaton) {
//        this.automaton = automaton;
//        this.random = new Random();
//        this.vertices = new ArrayList<GEMVertex>();;
//        initialization(automaton);
//        this.counter = 0;
//        setBarycenter();
//    }
//
//    public void setLayout(Automaton automaton) {
//        GEMVertex vertex = chooseVertex(vertices);
//        Point currentImpuls = impulseComputation(vertex, vertices, automaton);
//   //     vertex.setCurrentPosition(new Point(vertex.getCurrentPosition().x + currentImpuls.x,
//        //        vertex.getCurrentPosition().y + currentImpuls.y));
//           temperatureAdjustment(vertex, currentImpuls);
//    }
//
//    private void initialization(Automaton automaton) {
//        for (State state : automaton.getStates()) {
//            GEMVertex vertex = new GEMVertex(state);
//            vertex.setSkewGauge(0);
//            vertex.setLastImpulseX(0);
//            vertex.setLastImpulseY(0);
//            vertex.setLocalTemperature(TEMPERETURE_INIT);
//            vertex.setDegree(automaton.getStateDegree(state));
//            vertices.add(vertex);
//        }
//    }
//
//    private GEMVertex chooseVertex(List<GEMVertex> vertices) {
//        if(counter >= vertices.size()) {
//            counter = 0;
//        }
//        //return vertices.get(random.nextInt(vertices.size()));
//        return vertices.get(counter++);
//    }
//
//
//
//
//    private Point impulseComputation(GEMVertex chosenVertex, List<GEMVertex> vertices, Automaton automaton) {
//
//
//        double sub = GRAVITATIONAL_CONSTANT * functionGrowing(chosenVertex);
//        double x = (barycenter.getX()/vertices.size() - chosenVertex.getCurrentPosition().getX()) * sub;
//        double y = (barycenter.getY()/vertices.size() - chosenVertex.getCurrentPosition().getY()) * sub;
//
//        Point delta = getSmallRandomVector();
//        x+=delta.x;
//        y+=delta.y;
//     //   System.out.println(x + ", " + y + ": " + barycenter);
//        for (GEMVertex vertex : vertices) {
//            if(!vertex.equals(chosenVertex)) {
//                int dx = chosenVertex.getCurrentPosition().x - vertex.getCurrentPosition().x;
//                int dy = chosenVertex.getCurrentPosition().y - vertex.getCurrentPosition().y;
//                if(dx != 0 || dy != 0) {
//                    double sq = DESIRED_EDGE_LENGTH*DESIRED_EDGE_LENGTH/(dx*dx + dy*dy);
//                    x+=dx*sq;
//                    y+=dy*sq;
//
//                }
//                if(automaton.getTransitionFromTo(vertex.getState(), chosenVertex.getState()) != null ||
//                        automaton.getTransitionFromTo(chosenVertex.getState(), vertex.getState()) != null) {
//
//                    x-=dx*(dx*dx + dy*dy)/(DESIRED_EDGE_LENGTH*DESIRED_EDGE_LENGTH*functionGrowing(chosenVertex));
//                    y-=dy*(dx*dx + dy*dy)/(DESIRED_EDGE_LENGTH*DESIRED_EDGE_LENGTH*functionGrowing(chosenVertex));
//
//
//                }
//            }
//        }
////        System.out.println(x + ", " + y + ": " + barycenter);
////        for (Transition edge : automaton.getIncomingTransitions(chosenVertex.getState())) {
////            int dx = chosenVertex.getCurrentPosition().x - edge.getFromState().getVisualProperties().getPoint().x;
////            int dy = chosenVertex.getCurrentPosition().y - edge.getFromState().getVisualProperties().getPoint().y;
////
////            double sq = (dx*dx + dy*dy)/(DESIRED_EDGE_LENGTH*DESIRED_EDGE_LENGTH*functionGrowing(chosenVertex));
////            System.out.println("d:" + dx + ", " + dy + ": " + sq);
////            x=x-dx*sq;
////            y=y-dy*sq;
////        }
//
//
//
//
//
//
//        System.out.println(x + ", " + y + ": " + barycenter);
//        return new Point((int)x,(int)y);
//        //chosenVertex.setCurrentImpulseX(x);
//        //chosenVertex.setCurrentImpulseY(y);
//
//
//    }
//
//
//    private Point getSmallRandomVector() {
//        int x = random.nextInt(10) - 5;
//        int y = random.nextInt(10) - 5;
//        return new Point(x,y);
//    }
//
//
//    private double functionGrowing(GEMVertex vertex) {
//        int degree = vertex.getDegree();
//        return degree*(1+degree/2);
//    }
//
//
//
//    private double getAngle(double ux, double uy, double vx, double vy) {
//      //  double uv = Math.sqrt(ux*ux + uy*uy) * Math.sqrt(vx*vx + vy*vy);
//      //  double alpha = ((ux*vx)+(uy*vy))/uv;
//      //  System.out.println("alpha: " + alpha);
//
//        return Math.atan2(ux-vx, uy-vy);
//
//        //return Math.acos(alpha);
//
//    }
//
//
//    private void temperatureAdjustment(GEMVertex vertex, Point currentImpuls) {
//        double x = currentImpuls.x;
//        double y = currentImpuls.y;
//        System.out.println("ttt: " + vertex.getLocalTemperature());
//        if(x != 0 || y != 0) {
//            x=vertex.getLocalTemperature()*x/Math.sqrt(x*x+y*y);
//            y=vertex.getLocalTemperature()*y/Math.sqrt(x*x+y*y);
//            int newX = vertex.getCurrentPosition().x + (int) x;
//            int newY = vertex.getCurrentPosition().y + (int) y;
//            vertex.setCurrentPosition(new Point(newX, newY));
//            updateBarycenter(new Point.Double(x,y));
//            //C???
//        }
////        double oldX = vertex.getLastImpulseX();
////        double oldY = vertex.getLastImpulseY();
////        if(oldX != 0 || oldY != 0) {
////            System.out.println("SSSS");
////            double beta = getAngle(x, y, oldX, oldY);
////            System.out.println("beta: " + beta);
////            if(Math.sin(beta) >= Math.sin(Math.PI/2 + ALPHA_R/2)) {
////                vertex.setSkewGauge(vertex.getSkewGauge() + SIGMA_R*sgn(Math.sin(beta)));
////            }
////            if(Math.abs(Math.cos(beta)) >= Math.cos(ALPHA_O/2)) {
////                vertex.setLocalTemperature(vertex.getLocalTemperature()*SIGMA_O*Math.cos(beta));
////            }
////
////            vertex.setLocalTemperature(vertex.getLocalTemperature() * (1 - Math.abs(vertex.getSkewGauge())));
////            vertex.setLocalTemperature(Math.min(vertex.getLocalTemperature(), MAXIMAL_TEMPERATURE));
////            vertex.setLastImpulseX(x);
////            vertex.setLastImpulseY(y);
////        }
////
////        vertex.setLastImpulseX(x);
////        vertex.setLastImpulseY(y);
////System.out.println("ttttttttt: " + vertex.getLocalTemperature());
//
//
//
//
//    }
//
//    private int sgn(double number) {
//        if(number == 0) {
//            return 0;
//        }
//        return (number > 0) ? 1 : -1;
//
//    }
//
//
//    private Point getBarycenter(List<GEMVertex> vertices) {
//        double x = 0;
//        double y = 0;
//        for (GEMVertex vertex : vertices) {
//            x += vertex.getCurrentPosition().x;
//            y += vertex.getCurrentPosition().y;
//        }
//        return new Point((int) x/vertices.size(), (int) y/vertices.size());
//    }
//
//    private void setBarycenter() {
//        double x = 0;
//        double y = 0;
//        for (GEMVertex vertex : vertices) {
//            x += vertex.getCurrentPosition().x;
//            y += vertex.getCurrentPosition().y;
//        }
//        barycenter = new Point.Double(x,y);
//    }
//
//    private void updateBarycenter(Point2D impuls) {
//        barycenter.setLocation(barycenter.getX() + impuls.getX(), barycenter.getY() + impuls.getY());
//        double x = 0;
//        double y = 0;
//        for (GEMVertex vertex : vertices) {
//            x += vertex.getCurrentPosition().x;
//            y += vertex.getCurrentPosition().y;
//        }
//        System.out.println("x: " + x + ", y: " + y);
//        System.out.println(barycenter);
//        barycenter.setLocation((int)x,(int)y);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//


    private void newPermutation() {
        int size = this.vertices.size();
        Random r = new Random();
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        permutation.clear();
        for (int i = 0; i < size; i++) {
            int rand = r.nextInt(size - i);
            permutation.add(list.remove(rand));
        }
       // System.out.println(permutation);
    }

    private double getPhase(double x, double y) {
        double angle = Math.atan(y/x);
        if(x < 0) {
            angle += Math.PI/2;
        }
        if(angle < 0) {
            angle += Math.PI;
        }
        return angle;
    }

    public static void main(String[] args) {




    }
    
    
    
    public void arrangeAutomatonForProcedures() {
        int iterCount = vertices.size() * 7;
        for (int i = 0; i < iterCount; i++) {
            setLayout(automaton);
        }
        initialization(automaton);
        for (int i = 0; i < iterCount; i++) {
            setLayout(automaton);
        }
        initialization(automaton);
        for (int i = 0; i < iterCount; i++) {
            setLayout(automaton);
        }
        setTransitionCurves();
        centerAndScaleGraphics(new Dimension(500, 500));
    }    
    
    private Automaton getAutomaton() {
        return automaton;
    }


    public void centerAndScaleGraphics(Dimension dim) {
        if (!getAutomaton().getStates().isEmpty()) {

            int minX = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;

            for (State state : getAutomaton().getStates()) {
                int x = state.getVisualProperties().getXPos();
                int y = state.getVisualProperties().getYPos();
                if (x < minX) {
                    minX = x;
                    if (state.isInitial()) {
                        minX -= 30;
                    }
                }
                if (x > maxX) {
                    maxX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }

            }

            for (Transition transition : getAutomaton().getTransitions()) {
                int x = transition.getVisualProperties().getClickPositionX();
                int y = transition.getVisualProperties().getClickPositionY();
                if (x < minX) {
                    minX = x;
                }
                if (x > maxX) {
                    maxX = x;
                }
                if (y < minY) {
                    minY = y;
                }
                if (y > maxY) {
                    maxY = y;
                }
            }

            double width = dim.getWidth();
            double height = dim.getHeight();

            double graphicsWidth = maxX - minX;
            double graphicsHeight = maxY - minY;
            double widthRatio = width / (graphicsWidth + 100);
            double heightRatio = height / (graphicsHeight + 100);

            double newZoom = 1;//zoomfactor;

            newZoom = widthRatio < heightRatio ? widthRatio : heightRatio;
            if (newZoom > 2) {
                newZoom = 2;
            } else if (newZoom < 0.2) {
                newZoom = 0.2;
            }


            int offX = (int) (((width - graphicsWidth * newZoom) / 2) / newZoom);
            int offY = (int) (((height - graphicsHeight * newZoom) / 2) / newZoom);

            int moveX = offX - minX;
            int moveY = offY - minY;


            if (moveX != 0 || moveY != 0) {
                for (State state : getAutomaton().getStates()) {
                    state.getVisualProperties().move(moveX, moveY);
                }
            }
        }
    }


}


