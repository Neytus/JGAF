/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Dimension;
import java.awt.Rectangle;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;

/**
 *
 * @author hanis
 */
public class LayoutTools {

    public static final int LEFT_SPACE = 100;
    public static final int RIGHT_SPACE = 50;
    public static final int TOP_SPACE = 50;
    public static final int BOTTOM_SPACE = 50;


    public static Rectangle getBounds(Automaton automaton) {
        Rectangle bounds = new Rectangle();
        for (State state : automaton.getStates()) {
       //     System.out.println("state pos: " + state.getVisualProperties().getPoint());
            bounds.add(state.getVisualProperties().getPoint());
         //   System.out.println(bounds.x);
        }
       // System.out.println(bounds.getBounds().getMinY());
        return bounds.getBounds();
    }
    
    public static void moveGraphicsToTheOrigin(Automaton automaton, Dimension viewportSize) {
        Rectangle bounds = getBounds(automaton);
        double minX = bounds.getX();
        double minY = bounds.getY();
       // System.out.println("minX: " + minX);
       // System.out.println("minY: " + minY);
        for (State state : automaton.getStates()) {
         //   System.out.println("new posX: " + (state.getVisualProperties().getXPos() - minX + LEFT_SPACE));
          //  System.out.println("new posY: " + (state.getVisualProperties().getYPos() - minY + TOP_SPACE));

            state.getVisualProperties().setXPos((int) (state.getVisualProperties().getXPos() - minX + LEFT_SPACE));
            state.getVisualProperties().setYPos((int) (state.getVisualProperties().getYPos() - minY + TOP_SPACE));
        }
    }
    
//    public static void centerGraphics(Automaton automaton, Dimension viewportSize) {
//        Rectangle bounds = getBounds(automaton);
//
//
//    }

//    public static void centerAndScaleGraphics(Automaton automaton, Dimension viewportSize, boolean scale) {
//        if (!automaton.getStates().isEmpty()) {
//            Rectangle r = new Rectangle();
//
//            clearOffset();
//            clearZoom();
//            int minX = Integer.MAX_VALUE;
//            int maxX = Integer.MIN_VALUE;
//            int minY = Integer.MAX_VALUE;
//            int maxY = Integer.MIN_VALUE;
//
//            for (State state : automaton.getStates()) {
//                int x = state.getVisualProperties().getXPos();
//                int y = state.getVisualProperties().getYPos();
//                if (x < minX) {
//                    minX = x;
//                  //  mostLeftState = state;
//                }
//                if (x > maxX) {
//                    maxX = x;
//                }
//                if (y < minY) {
//                    minY = y;
//                  //  mostTopState = state;
//                }
//                if (y > maxY) {
//                    maxY = y;
//                }
//            }
//
//            for (State state : automaton.getStates()) {
//                int x = state.getVisualProperties().getXPos();
//                int y = state.getVisualProperties().getYPos();
//                state.getVisualProperties().setCoordinates(x - minX + 50, y - minY + 50);
//            }
//            if(scale) {
//                double widthRatio = viewportSize.getWidth() / (maxX - minX + 100);
//                double heightRatio = viewportSize.getHeight() / (maxY - minY + 100);
//                zoomfactor = widthRatio < heightRatio ? widthRatio : heightRatio;
//            }
//            updateGraphicsAll();
//        }
//    }
//
//
//    public void centerGraphics(Dimension viewportSize) {
//        if (!automaton.getStates().isEmpty()) {
//            clearOffset();
//       //     double oldZoom = zoomfactor;
//         //   zoomfactor = ZOOM_DEFAULT;
//            //   clearZoom();
//          //  State mostLeftState = null;
//          //  State mostTopState = null;
//            int minX = Integer.MAX_VALUE;
//            int maxX = Integer.MIN_VALUE;
//            int minY = Integer.MAX_VALUE;
//            int maxY = Integer.MIN_VALUE;
//
//            for (State state : automaton.getStates()) {
//                int x = state.getVisualProperties().getXPos();
//                int y = state.getVisualProperties().getYPos();
//                if (x < minX) {
//                    minX = x;
//                  //  mostLeftState = state;
//                }
//                if (x > maxX) {
//                    maxX = x;
//                }
//                if (y < minY) {
//                    minY = y;
//                  //  mostTopState = state;
//                }
//                if (y > maxY) {
//                    maxY = y;
//                }
//            }
//            int shiftX = (int) (viewportSize.getWidth()/zoomfactor - (maxX - minX))/2;
//            int shiftY = (int) (viewportSize.getHeight()/zoomfactor - (maxY - minY))/2;
//            for (State state : automaton.getStates()) {
//                int x = state.getVisualProperties().getXPos();
//                int y = state.getVisualProperties().getYPos();
//                state.getVisualProperties().setCoordinates(x - minX + shiftX, y - minY + shiftY);
//            }
//           // double widthRatio = viewportSize.getWidth()/(maxX - minX + 100);
//          //  double heightRatio = viewportSize.getHeight()/(maxY - minY + 100);
//          //  zoomfactor = widthRatio < heightRatio ? widthRatio : heightRatio;
//          //  zoomfactor = oldZoom;
//            updateGraphicsAll();
//        }
//    }
//
//
//
//
//    public void moveGraphicsToTheOrigin() {
//        if (!automaton.getStates().isEmpty()) {
//            clearOffset();
//            clearZoom();
//          //  State mostLeftState = null;
//          //  State mostTopState = null;
//            int minX = Integer.MAX_VALUE;
//            int maxX = Integer.MIN_VALUE;
//            int minY = Integer.MAX_VALUE;
//            int maxY = Integer.MIN_VALUE;
//
//            for (State state : automaton.getStates()) {
//                int x = state.getVisualProperties().getXPos();
//                int y = state.getVisualProperties().getYPos();
//                if (x < minX) {
//                    minX = x;
//                  //  mostLeftState = state;
//                }
//                if (x > maxX) {
//                    maxX = x;
//                }
//                if (y < minY) {
//                    minY = y;
//                  //  mostTopState = state;
//                }
//                if (y > maxY) {
//                    maxY = y;
//                }
//            }
//
//            for (State state : automaton.getStates()) {
//                int x = state.getVisualProperties().getXPos();
//                int y = state.getVisualProperties().getYPos();
//                state.getVisualProperties().setCoordinates(x - minX + 50, y - minY + 50);
//            }
//            updateGraphicsAll();
//        }
//    }

}
