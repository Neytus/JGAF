/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.gui.StateDiagramRepresenter;

/**
 *
 * @author hanis
 */
public class AutomatonTablePainter extends Painter {

    private static final int INITIAL_ARROW_SIZE = 20;
    private static final int ACCEPTING_ARROW_SIZE = 20;

    private AutomatonTableEditor editor;
    private Graphics2D graphics;
    private boolean antialiasing = true;
    private static final int CELL_HEIGHT = 30;
    private static final int CELL_WIDTH = 100;
    //private int offsetX = 100;
    //private int offsetY = 100;
    private int fontSize = 14;


    public AutomatonTablePainter() {
    }

    public AutomatonTableEditor getEditor() {
        return editor;
    }

    public void setEditor(AutomatonTableEditor editor) {
        this.editor = editor;
    }






    public void updateGraphics(Graphics2D g) {
        if (g != null) {
            this.graphics = g;
            if (antialiasing) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }


            double zoom = editor.getZoomfactor();
            int offsetX = editor.getOffsetX();
            int offsetY = editor.getOffsetY();
            
            g.translate(offsetX, offsetY);
            g.scale(zoom, zoom);
            g.setStroke(new BasicStroke(1));
            paintAutomatonTable();
        }
    }


    public int getWidth() {
        int columns = editor.getAutomaton().getAlphabet().size() + 1;
        return columns * CELL_WIDTH + 60;
    }

    public int getHeight() {
        int rows = editor.getAutomaton().getStates().size() + 1;
        return rows * CELL_HEIGHT + 60;
    }

    public void updateGraphicsForSVG(Graphics2D g) {
        if (g != null) {
            this.graphics = g;
            if (antialiasing) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            //int width = 1;
            int offX = getEditor().getOffsetX();
            int offY = getEditor().getOffsetY();

            getEditor().setOffsetX(30);
            getEditor().setOffsetY(30);



            double tempZoom = getEditor().getZoomfactor();
            getEditor().setZoomfactor(1);
            
            g.setStroke(new BasicStroke(1));
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
//            if (getEditor().getZoomfactor() < 1.1) {
//                width = 1;
//            } else if (getEditor().getZoomfactor() < 2) {
//                width = 2;
//            } else {
//                width = (int) getEditor().getZoomfactor();
//            }

            

            paintAutomatonTable();
            getEditor().setOffsetX(offX);
            getEditor().setOffsetY(offY);
            getEditor().setZoomfactor(tempZoom);
        }
    }



    private void paintTable(int rows, int columns, Graphics2D graphics) {
        graphics.setColor(Color.black);
        int width =  columns * CELL_WIDTH;
        int height = rows * CELL_HEIGHT;

        graphics.drawLine(CELL_WIDTH - 2, 0, CELL_WIDTH - 2, height);

        for (int i = 1; i <= columns; i++) {
            graphics.drawLine(i * CELL_WIDTH, 0, i * CELL_WIDTH, height);
        }
        graphics.drawLine(0, CELL_HEIGHT - 2, width, CELL_HEIGHT - 2);

        for (int i = 1; i <= rows; i++) {
            graphics.drawLine(0, i * CELL_HEIGHT, width, i * CELL_HEIGHT);
        }


    }

    private void paintAutomatonTable() {
        Automaton automaton = editor.getAutomaton();
        int columns = automaton.getAlphabet().size() + 1;
        int rows = automaton.getStates().size() + 1;        
        paintTable(rows, columns, graphics);


        Font nameFont = new Font("Arial", Font.PLAIN, (int) (fontSize));
        graphics.setFont(nameFont);
        int i = 1;
        Map<String, Integer> labelPos = new HashMap<String, Integer>();
        for (String label : automaton.getAlphabet()) {
            labelPos.put(label, i);
            int x = i * CELL_WIDTH + CELL_WIDTH / 2;
            int y = CELL_HEIGHT/2;
            drawCenteredText(label, x, y, nameFont, graphics);
            i++;
        }
        i = 1;
        Map<State, Integer> statePos = new HashMap<State, Integer>();
        for (State state : automaton.getStates()) {
            statePos.put(state, i);
            int textOffset = 0;
            if(state.isInitial()) {
                drawInitialArrow(5, i * CELL_HEIGHT + CELL_HEIGHT/2, Color.black, graphics);
            }
            if(state.isAccepting()) {
                drawAcceptingArrow(5, i * CELL_HEIGHT + CELL_HEIGHT/2, Color.black, graphics);
            }

            int x = CELL_WIDTH/2;
            int y = i * CELL_HEIGHT + CELL_HEIGHT/2;
            drawCenteredText(state.getName(), x, y, nameFont, graphics);
            i++;
        }


        for (State state : automaton.getStates()) {
            for (String label : automaton.getAlphabet()) {
                int x = labelPos.get(label)*CELL_WIDTH+CELL_WIDTH/2;
                int y = statePos.get(state)*CELL_HEIGHT+CELL_HEIGHT/2;
                drawCenteredText(writeStates(automaton.getStatesFromUnder(state, label)), x, y, nameFont, graphics);
            }
        }
//        for (Transition transition : automaton.getTransitions()) {
//            for (String label : transition.getLabels()) {
//                int x = (int) (offsetX + zoom*(labelPos.get(label)*CELL_WIDTH+CELL_WIDTH/2));
//                int y = (int) (offsetY + zoom*(statePos.get(transition.getFromState())*CELL_HEIGHT+CELL_HEIGHT/2));
//                drawCenteredText(transition.getToState().getName(), x, y, nameFont, graphics);
//            }
//        }

    }


    private String writeStates(List<State> states) {
        if(states.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (State state : states) {
            sb.append(state.getName()).append(",");
        }
        return sb.substring(0, sb.lastIndexOf(","));
    }



    private Rectangle2D drawCenteredText(String s, int centerX, int centerY, Font f, Graphics2D g) {
        //-- center the string --
        FontMetrics fm = g.getFontMetrics(f);
        Rectangle2D rect = fm.getStringBounds(s, g);
        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());
        int textx = centerX - textWidth / 2;
        int texty = centerY - textHeight / 2 + fm.getAscent();

        //-- render text --
        g.drawString(s, textx, texty);
        return rect;
    }


    private void drawInitialArrow(int startX, int y, Color c, Graphics2D g) {
        int TIP_LENGTH = 4;
        int endX = (int) (startX + INITIAL_ARROW_SIZE);
        g.setColor(c);
        g.drawLine(startX, y, endX, y);
        g.drawLine((int) (endX - TIP_LENGTH), (int) (y + 2), endX, y);
        g.drawLine((int) (endX - TIP_LENGTH), (int) (y - 2), endX, y);
    }

    private void drawAcceptingArrow(int startX, int y, Color c, Graphics2D g) {
        int TIP_LENGTH = 4;
        int endX = (int) (startX + ACCEPTING_ARROW_SIZE);
        g.setColor(c);
        g.drawLine(startX, y, endX, y);
        g.drawLine((int) (startX + TIP_LENGTH), (int) (y + 2), startX, y);
        g.drawLine((int) (startX + TIP_LENGTH), (int) (y - 2), startX, y);
    }




}










































//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package jgaf.automaton.fa;
//
//import java.awt.BasicStroke;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
//import java.awt.geom.Rectangle2D;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import jgaf.automaton.Automaton;
//import jgaf.automaton.State;
//import jgaf.gui.AutomatonCanvas;
//
///**
// *
// * @author hanis
// */
//public class AutomatonTablePainter extends Painter {
//
//    private static final int INITIAL_ARROW_SIZE = 20;
//    private static final int ACCEPTING_ARROW_SIZE = 20;
//
//    private AutomatonTableEditor editor;
//    private Graphics2D graphics;
//    private boolean antialiasing = true;
//    private static final int CELL_HEIGHT = 30;
//    private static final int CELL_WIDTH = 100;
//    //private int offsetX = 100;
//    //private int offsetY = 100;
//    private int fontSize = 14;
//
//
//    public AutomatonTablePainter() {
//    }
//
//    public AutomatonTableEditor getEditor() {
//        return editor;
//    }
//
//    public void setEditor(AutomatonTableEditor editor) {
//        this.editor = editor;
//    }
//
//
//
//
//
//
//    public void updateGraphics(Graphics2D g) {
//        if (g != null) {
//            this.graphics = g;
//            if (antialiasing) {
//                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            }
//            int width = 1;
//            if (getEditor().getZoomfactor() < 1.1) {
//                width = 1;
//            } else if (getEditor().getZoomfactor() < 2) {
//                width = 2;
//            } else {
//                width = (int) getEditor().getZoomfactor();
//            }
//
//            g.setStroke(new BasicStroke(width));
//
//            paintAutomatonTable();
//        }
//    }
//
//
//    public int getWidth() {
//        int columns = editor.getAutomaton().getAlphabet().size() + 1;
//        return columns * CELL_WIDTH + 60;
//    }
//
//    public int getHeight() {
//        int rows = editor.getAutomaton().getStates().size() + 1;
//        return rows * CELL_HEIGHT + 60;
//    }
//
//    public void updateGraphicsForSVG(Graphics2D g) {
//        if (g != null) {
//            this.graphics = g;
//            if (antialiasing) {
//                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            }
//
//            //int width = 1;
//            int offX = getEditor().getOffsetX();
//            int offY = getEditor().getOffsetY();
//
//            getEditor().setOffsetX(30);
//            getEditor().setOffsetY(30);
//
//
//
//            double tempZoom = getEditor().getZoomfactor();
//            getEditor().setZoomfactor(1);
//
//            g.setStroke(new BasicStroke(1));
//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, getWidth(), getHeight());
//            g.setColor(Color.BLACK);
////            if (getEditor().getZoomfactor() < 1.1) {
////                width = 1;
////            } else if (getEditor().getZoomfactor() < 2) {
////                width = 2;
////            } else {
////                width = (int) getEditor().getZoomfactor();
////            }
//
//
//
//            paintAutomatonTable();
//            getEditor().setOffsetX(offX);
//            getEditor().setOffsetY(offY);
//            getEditor().setZoomfactor(tempZoom);
//        }
//    }
//
//
//
//    private void paintTable(int rows, int columns, Graphics2D graphics) {
//        int offsetX = editor.getOffsetX();
//        int offsetY = editor.getOffsetY();
//        double zoom = editor.getZoomfactor();
//        graphics.setColor(Color.black);
//        int width =  columns * CELL_WIDTH;
//        int height = rows * CELL_HEIGHT;
//        //graphics.drawRect(offsetX, offsetY, (int) (width*zoom), (int) (height*zoom));
//            graphics.drawLine(offsetX + (int) (zoom  * (CELL_WIDTH - 2)), offsetY,
//                    offsetX + (int) (zoom * (CELL_WIDTH - 2)), offsetY + (int) (height*zoom));
//
//        for (int i = 1; i <= columns; i++) {
//            graphics.drawLine(offsetX + (int) (zoom * (i * CELL_WIDTH)), offsetY,
//                    offsetX + (int) (zoom * (i * CELL_WIDTH)), offsetY + (int) (height*zoom));
//        }
//            graphics.drawLine(offsetX, offsetY + (int) (zoom * (CELL_HEIGHT - 2)),
//                    offsetX + (int) (zoom * (width)), offsetY + (int) (zoom * (CELL_HEIGHT - 2)));
//
//        for (int i = 1; i <= rows; i++) {
//            graphics.drawLine(offsetX, offsetY + (int) (zoom * (i * CELL_HEIGHT)),
//                    offsetX + (int) (zoom * (width)), offsetY + (int) (zoom * (i * CELL_HEIGHT)));
//        }
//
//
//    }
//
//    private void paintAutomatonTable() {
//        int offsetX = editor.getOffsetX();
//        int offsetY = editor.getOffsetY();
//        double zoom = editor.getZoomfactor();
//        Automaton automaton = editor.getAutomaton();
//        int columns = automaton.getAlphabet().size() + 1;
//        int rows = automaton.getStates().size() + 1;
//        paintTable(rows, columns, graphics);
//
//
//        Font nameFont = new Font("Arial", Font.PLAIN, (int) (fontSize*zoom));
//        graphics.setFont(nameFont);
//        int i = 1;
//        Map<String, Integer> labelPos = new HashMap<String, Integer>();
//        for (String label : automaton.getAlphabet()) {
//            labelPos.put(label, i);
//            int x = (int) (offsetX + zoom * (i * CELL_WIDTH + CELL_WIDTH / 2));
//            int y = (int) (offsetY +  zoom * CELL_HEIGHT/2);
//            drawCenteredText(label, x, y, nameFont, graphics);
//            i++;
//        }
//        i = 1;
//        Map<State, Integer> statePos = new HashMap<State, Integer>();
//        for (State state : automaton.getStates()) {
//            statePos.put(state, i);
//            int textOffset = 0;
//            if(state.isInitial()) {
//                drawInitialArrow((int) (offsetX + zoom * 5), (int) (offsetY + zoom * (i * CELL_HEIGHT + CELL_HEIGHT/2))
//                        ,Color.black, graphics);
//            }
//            if(state.isAccepting()) {
//                drawAcceptingArrow((int) (offsetX + zoom * 5), (int) (offsetY + zoom * (i * CELL_HEIGHT + CELL_HEIGHT/2))
//                        ,Color.black, graphics);
//            }
//
//            int x = (int) (offsetX + zoom * CELL_WIDTH/2);
//            int y = (int) (offsetY + zoom * (i * CELL_HEIGHT + CELL_HEIGHT/2));
//            drawCenteredText(state.getName(), x, y, nameFont, graphics);
//            i++;
//        }
//
//
//        for (State state : automaton.getStates()) {
//            for (String label : automaton.getAlphabet()) {
//                int x = (int) (offsetX + zoom*(labelPos.get(label)*CELL_WIDTH+CELL_WIDTH/2));
//                int y = (int) (offsetY + zoom*(statePos.get(state)*CELL_HEIGHT+CELL_HEIGHT/2));
//                drawCenteredText(writeStates(automaton.getStatesFromUnder(state, label)), x, y, nameFont, graphics);
//            }
//        }
////        for (Transition transition : automaton.getTransitions()) {
////            for (String label : transition.getLabels()) {
////                int x = (int) (offsetX + zoom*(labelPos.get(label)*CELL_WIDTH+CELL_WIDTH/2));
////                int y = (int) (offsetY + zoom*(statePos.get(transition.getFromState())*CELL_HEIGHT+CELL_HEIGHT/2));
////                drawCenteredText(transition.getToState().getName(), x, y, nameFont, graphics);
////            }
////        }
//
//    }
//
//
//    private String writeStates(List<State> states) {
//        if(states.isEmpty()) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder();
//        for (State state : states) {
//            sb.append(state.getName()).append(",");
//        }
//        return sb.substring(0, sb.lastIndexOf(","));
//    }
//
//
//
//    private Rectangle2D drawCenteredText(String s, int centerX, int centerY, Font f, Graphics2D g) {
//        //-- center the string --
//        FontMetrics fm = g.getFontMetrics(f);
//        Rectangle2D rect = fm.getStringBounds(s, g);
//        int textHeight = (int) (rect.getHeight());
//        int textWidth = (int) (rect.getWidth());
//        int textx = centerX - textWidth / 2;
//        int texty = centerY - textHeight / 2 + fm.getAscent();
//
//        //-- render text --
//        g.drawString(s, textx, texty);
//        return rect;
//    }
//
//
//    private void drawInitialArrow(int startX, int y, Color c, Graphics2D g) {
//        double zoom = getEditor().getZoomfactor();
//        int TIP_LENGTH = 4;
//        int endX = (int) (startX + INITIAL_ARROW_SIZE * zoom);
//        g.setColor(c);
//        g.drawLine(startX, y, endX, y);
//        g.drawLine((int) (endX - TIP_LENGTH * zoom), (int) (y + 2 * zoom), endX, y);
//        g.drawLine((int) (endX - TIP_LENGTH * zoom), (int) (y - 2 * zoom), endX, y);
//    }
//
//    private void drawAcceptingArrow(int startX, int y, Color c, Graphics2D g) {
//        double zoom = getEditor().getZoomfactor();
//        int TIP_LENGTH = 4;
//        int endX = (int) (startX + ACCEPTING_ARROW_SIZE * zoom);
//        g.setColor(c);
//        g.drawLine(startX, y, endX, y);
//        g.drawLine((int) (startX + TIP_LENGTH * zoom), (int) (y + 2 * zoom), startX, y);
//        g.drawLine((int) (startX + TIP_LENGTH * zoom), (int) (y - 2 * zoom), startX, y);
//    }
//
//
//
//
//}
