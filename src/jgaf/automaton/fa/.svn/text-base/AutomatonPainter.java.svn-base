/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.automaton.fa;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class AutomatonPainter extends Painter {




    private final Color SELECTIN_RECTANGLE_COLOR = new Color(30,50,222);
    private final double ARCDISTANCE = 25;
    //private Simulator dfaSim = null;
    private StateDiagramEditor editor = null;
    private Graphics2D graphics = null;
    

    public AutomatonPainter() {
    }

    public int getStateRadius() {
        return getStateDiameter()/2;
    }

    public int getStateDiameter() {
        return PropertiesHandler.getInstance().getAutomatonStateDiameter();
    }

    public StateDiagramEditor getEditor() {
        return editor;
    }

    public void setEditor(StateDiagramEditor dfaEditor) {
        this.editor = dfaEditor;
    }



//    public void optimizeCropPan(int optoffset) {
//        //-- get dimensions of image --
//        int minX = 9999999;
//        int minY = 9999999;
//        int maxX = -9999999;
//        int maxY = -9999999;
//
//        Automaton automaton = getEditor().getAutomaton();
//        for (State state : automaton.getStates()) {
//
//
//            if (state.getVisualProperties().getXPos() < minX) {
//                minX = state.getVisualProperties().getXPos();
//                if (state.isInitial()) {
//                    minX = minX - 30;
//                }
//            }
//
//            if (state.getVisualProperties().getYPos() < minY) {
//                minY = state.getVisualProperties().getYPos();
//            }
//            if (state.getVisualProperties().getXPos() > maxX) {
//                maxX = state.getVisualProperties().getXPos();
//            }
//            if (state.getVisualProperties().getYPos() > maxY) {
//                maxY = state.getVisualProperties().getYPos();
//            }
//        }
//        for (Transition t : automaton.getTransitions()) {
//
//
//
//            if (t.getVisualProperties().getClickPositionX() < minX) {
//                minX = (int) t.getVisualProperties().getClickPositionX();
//            }
//            if (t.getVisualProperties().getClickPositionY() < minY) {
//                minY = (int) t.getVisualProperties().getClickPositionY();
//            }
//            if (t.getVisualProperties().getClickPositionX() > maxX) {
//                maxX = (int) t.getVisualProperties().getClickPositionX();
//            }
//            if (t.getVisualProperties().getClickPositionY() > maxY) {
//                maxY = (int) t.getVisualProperties().getClickPositionY();
//            }
//        }
//
//
//        if (automaton.getStates().size() > 0) {
//            int safetyDistance = 35;
//            int imWidth = Math.max(0, maxX - minX) + 3 * safetyDistance;
//            int imHeight = Math.max(0, maxY - minY) + 2 * safetyDistance;
//
//            editor.setOffsetX(-minX + (int) (1.5 * safetyDistance) + optoffset);
//            editor.setOffsetY(-minY + safetyDistance + optoffset);
//        }
//    }

    /**
     * Export the current DFA as a cropped picture
     * @param f Destination file
     * @return true if all OK
     */
    public boolean exportPNGFile(File f) {
        //System.out.println("QQEQWEQW");
        //-- get dimensions of image --
        int minX = 9999999;
        int minY = 9999999;
        int maxX = -9999999;
        int maxY = -9999999;

        Automaton automaton = getEditor().getAutomaton();
        for (State state : automaton.getStates()) {
            if (state.getVisualProperties().getXPos() < minX) {
                minX = state.getVisualProperties().getXPos();
                if (state.isInitial()) {
                    minX = minX - 30;
                }
            }

            if (state.getVisualProperties().getYPos() < minY) {
                minY = state.getVisualProperties().getYPos();
            }
            if (state.getVisualProperties().getXPos() > maxX) {
                maxX = state.getVisualProperties().getXPos();
            }
            if (state.getVisualProperties().getYPos() > maxY) {
                maxY = state.getVisualProperties().getYPos();
            }

            for (Transition t : automaton.getTransitions()) {


                if (t.getVisualProperties().getClickPositionX() < minX) {
                    minX = (int) t.getVisualProperties().getClickPositionX();
                }
                if (t.getVisualProperties().getClickPositionY() < minY) {
                    minY = (int) t.getVisualProperties().getClickPositionY();
                }
                if (t.getVisualProperties().getClickPositionX() > maxX) {
                    maxX = (int) t.getVisualProperties().getClickPositionX();
                }
                if (t.getVisualProperties().getClickPositionY() > maxY) {
                    maxY = (int) t.getVisualProperties().getClickPositionY();
                }
            }
        }
        if (automaton.getStates().size() > 0) {
            int safetyDistance = 35;
            int imWidth = (int) (Math.max(0, maxX - minX) * getEditor().getZoomfactor() + 3 * safetyDistance * getEditor().getZoomfactor());
            int imHeight = (int) (Math.max(0, maxY - minY) * getEditor().getZoomfactor() + 2 * safetyDistance * getEditor().getZoomfactor());

            editor.setOffsetX((int) ((-minX + 1.5 * safetyDistance) * getEditor().getZoomfactor()));
            editor.setOffsetY((int) ((-minY + safetyDistance) * getEditor().getZoomfactor()));


            BufferedImage bi = new BufferedImage(imWidth, imHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D gimg = (Graphics2D) bi.getGraphics();
            gimg.setColor(Color.WHITE);
            gimg.fillRect(0, 0, imWidth, imHeight);
            updateGraphics(gimg);

            try {
                ImageIO.write(bi, "png", f);
                JOptionPane.showMessageDialog(null, "File saved successfully!", "Image export", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error while saving file!", "Image export", JOptionPane.ERROR_MESSAGE);
            }

            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No elements to export!", "Image export", JOptionPane.ERROR_MESSAGE);

            return false;
        }



    }


    private BasicStroke getTransitionStroke() {
        return getStroke(PropertiesHandler.getInstance().getAutomatonTransitionStrokeWidth(),
                PropertiesHandler.getInstance().getAutomatonTransitionStrokeStyle());
    }


    private BasicStroke getStateStroke() {
        return getStroke(PropertiesHandler.getInstance().getAutomatonStateStrokeWidth(),
                PropertiesHandler.getInstance().getAutomatonStateStrokeStyle());
    }

    private BasicStroke getInitArrowStroke() {
        return getStroke(editor.getInitArrow().getStrokeWidth(), editor.getInitArrow().getStrokeStyle());
    }


    private BasicStroke getStroke(double stokeWidth, StrokeStyle strokeStyle) {
        float width = (float) (stokeWidth * getZoom());
        float[] dash = strokeStyle.getStrokeDash(2*width, getZoom());
        if(dash.length == 0) {
            return new BasicStroke(width);
        }
        return new BasicStroke(width,
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f,
                                dash,
                                0f);
    }


    private double getZoom() {
        return getEditor().getZoomfactor();
    }


    public void updateGraphics(Graphics2D g) {
        if (g != null) {            
            this.graphics = g;
            if (PropertiesHandler.getInstance().isAutomatonCanvasAntialiasingShape()) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);            
            }
            if (PropertiesHandler.getInstance().isAutomatonCanvasAntialiasingText()) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            } else {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            }
            g.setStroke(getStateStroke());
            paintStates();
            g.setStroke(getTransitionStroke());
            paintTransitions();
            paintUserActions();
            if(editor.areLabelsVisible()) {
                paintLabels();
            }
            g.setStroke(new BasicStroke(1));
            if (editor.isSelectionDragging()) {
                paintSelectionRectangle();
            }
        }
    }



    public void updateGraphicsForSVG(Graphics2D g) {
        if (g != null) {            
            this.graphics = g;
            if (PropertiesHandler.getInstance().isAutomatonCanvasAntialiasingShape()) {
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);            
            }
            if (PropertiesHandler.getInstance().isAutomatonCanvasAntialiasingText()) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            } else {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            }
                        
            int offX = getEditor().getOffsetX();
            int offY = getEditor().getOffsetY();
            double tempZoom = getEditor().getZoomfactor();
            getEditor().setZoomfactor(1);

            Rectangle r = getEditor().getBounds();
            getEditor().setOffsetX((int) -r.getX() + 50);
            getEditor().setOffsetY((int) -r.getY() + 50);

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, r.width - r.x + 100, r.height - r.y + 100);

            g.setStroke(getStateStroke());
            paintStates();
            g.setStroke(getTransitionStroke());
            paintTransitions();
            paintUserActions();
            if(editor.areLabelsVisible()) {
                paintLabels();
            }
            g.setStroke(new BasicStroke(1));
            if (editor.isSelectionDragging()) {
                paintSelectionRectangle();
            }            
            getEditor().setOffsetX(offX);
            getEditor().setOffsetY(offY);
            getEditor().setZoomfactor(tempZoom);            
        }
    }


//    public void updateGraphicsForSVG(Graphics2D g) {
//        if (g != null) {
//            this.graphics = g;
//            if (PropertiesHandler.getInstance().isAutomatonCanvasAntialiasingShape()) {
//                //-- nice rendering --
//                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            }
//            int width = 1;
//            int offX = getEditor().getOffsetX();
//            int offY = getEditor().getOffsetY();
//            double tempZoom = getEditor().getZoomfactor();
//            getEditor().setZoomfactor(1);
//
//            Rectangle r = getEditor().getBounds();
//            getEditor().setOffsetX((int) -r.getX() + 50);
//            getEditor().setOffsetY((int) -r.getY() + 50);
//
//            g.setStroke(new BasicStroke(width));
//
//            g.setColor(Color.WHITE);
//            g.fillRect(0, 0, r.width - r.x + 100, r.height - r.y + 100);
//            g.setColor(Color.BLACK);
//            paintStates();
//            paintTransitions();
//            paintUserActions();
//            if(editor.areLabelsVisible()) {
//                paintLabels();
//            }
//            getEditor().setOffsetX(offX);
//            getEditor().setOffsetY(offY);
//            getEditor().setZoomfactor(tempZoom);
//        }
//    }



    private void paintSelectionRectangle() {
        Graphics2D g = this.graphics;
        g.setColor(SELECTIN_RECTANGLE_COLOR);

        Rectangle2D r = editor.getSelectionRectangle();        
        int x = (int) (r.getX()*editor.getZoomfactor() + editor.getOffsetX());
        int y = (int) (r.getY()*editor.getZoomfactor() + editor.getOffsetY());
        int w = (int) (r.getWidth()*editor.getZoomfactor());
        int h = (int) (r.getHeight()*editor.getZoomfactor());
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
        g.fillRect(x, y, w, h);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));


        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
    }


    private void paintLabels() {
        Graphics2D g = this.graphics;
        List<CanvasLabel> labels = editor.getLabels();
        for (CanvasLabel label : labels) {
            int x = label.getXPos();
            int y = label.getYPos();

            int textX = (int) (editor.getOffsetX() + editor.getZoomfactor() * x);
            int textY = (int) (editor.getOffsetY() + editor.getZoomfactor() * y);
            double rotationFactor = Math.toRadians(label.getRotationFactor());
            g.rotate(rotationFactor, textX, textY);
            int newSize = (int) (label.getFont().getSize() * editor.getZoomfactor());
            Font font = new Font(label.getFont().getName(),label.getFont().getStyle(), newSize);
            FontMetrics fm = g.getFontMetrics(font);
            Rectangle2D rect = fm.getStringBounds(label.getCaption(), g);
            Rectangle2D bounds = new Rectangle();
            bounds.setRect(rect.getX() + textX, rect.getY() + textY,
                    rect.getWidth(), rect.getHeight());

            g.setColor(label.getBackgroundColor());

            PropertiesHandler properties = PropertiesHandler.getInstance();
            boolean paintBackground = properties.isAutomatonLabelBackEnabled();
            if (label.isMouseOver()) {
                g.setColor(properties.getAutomatonLabelMouseOverBackColor());
                paintBackground = true;
            }
            if(label.isSelected()) {
                g.setColor(properties.getAutomatonLabelSelectedBackColor());
                paintBackground = true;
            }

            AffineTransform rotation = AffineTransform.getRotateInstance(rotationFactor, textX, textY);
            Shape shape = rotation.createTransformedShape(bounds);
            label.setOutline(shape);

            if(paintBackground) {
                double alpha = properties.getAutomatonLabelBackTransparency();
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) alpha));
                g.fill(bounds);
                g.setComposite(getDefaultComposite());
            }


            g.setFont(font);
            g.setColor(label.getForegroundColor());
            if (label.isMouseOver()) {
                g.setColor(properties.getAutomatonLabelMouseOverFrontColor());
            }
            if(label.isSelected()) {
                g.setColor(properties.getAutomatonLabelSelectedFrontColor());
            }

            g.drawString(label.getCaption(), textX, textY);
            g.rotate(-rotationFactor, textX, textY);
        }
        g.setColor(Color.black);

    }


    private Composite getDefaultComposite() {
        return AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f);
    }

    /**
     * paint the states of the DFA
     */
    private void paintStates() {
        Graphics2D g = this.graphics;
        Automaton automaton = editor.getAutomaton();

        PropertiesHandler properties = PropertiesHandler.getInstance();
        Font font = properties.getAutomatonStateFont();
        Font stateFont = font.deriveFont((float) (font.getSize()*editor.getZoomfactor()));

        g.setFont(stateFont);
        for (State state : automaton.getStates()) {


            int x = state.getVisualProperties().getXPos();
            int y = state.getVisualProperties().getYPos();



            Color backgroundColor = state.getVisualProperties().getFillColor();
            Color strokeColor = state.getVisualProperties().getStrokeColor();
            Color foregroundColor = state.getVisualProperties().getFontColor();            


            if (state.getVisualProperties().isSelected()) {
                backgroundColor = properties.getAutomatonStateSelectedFillColor();
                strokeColor = properties.getAutomatonStateSelectedStrokeColor();
                foregroundColor = properties.getAutomatonStateSelectedFontColor();
            } else if (state.getVisualProperties().isMouseOver()) {
                backgroundColor = properties.getAutomatonStateMouseOverFillColor();
                strokeColor = properties.getAutomatonStateMouseOverStrokeColor();
                foregroundColor = properties.getAutomatonStateMouseOverFontColor();
            }



//            if(dfaSim.isSimulationModeActive() && dfaSim.getCurrentHighlightedState() == s) {
//                lineColor = COLORSTATELINESCURRENT;
//                backgroundColor = COLORSTATECURRENT;
//                if (s.isInitial() && dfa.getCurrentPosition()== 0)
//                    startLineColor = COLORSTATELINESCURRENT;
//                if (dfaSim.isHasFinallyAccepted())
//                {
//                    backgroundColor = COLORSTATEACCEPTED;
//                    lineColor = COLORSTATEACCEPTED2;
//                }
//
//            }
 //           int centerX = (int) ((automatonEditor.getOffsetX() + x) * automatonEditor.getZoomfactor());
 //           int centerY = (int) ((automatonEditor.getOffsetY() + y) * automatonEditor.getZoomfactor());

            int centerX = (int) (editor.getOffsetX() + editor.getZoomfactor() * x);
            int centerY = (int) (editor.getOffsetY() + editor.getZoomfactor() * y);
            int diameter = (int) (editor.getZoomfactor() * getStateDiameter());
            int radius = (int) (editor.getZoomfactor() * getStateRadius());

            if (state.isAccepting()) {
                double z = editor.getZoomfactor();
                int additionalradius = (int) (editor.getZoomfactor() * 4);
                g.setColor(backgroundColor);
                g.fillOval((int) (centerX - radius), (int) (centerY - radius), diameter, diameter);
                g.setColor(strokeColor);
                g.drawOval((int) (centerX - radius), (int) (centerY - radius), diameter, diameter);
                if (state.getVisualProperties().isSelected()) {
                    g.setColor(foregroundColor);
                }
                g.drawOval((int) (centerX - (radius - additionalradius)), (int) (centerY - (radius - additionalradius)), diameter - 2 * additionalradius, diameter - 2 * additionalradius);

            } else {
                g.setColor(backgroundColor);
                g.fillOval((int) (centerX - radius), (int) (centerY - radius), diameter, diameter);
                g.setColor(strokeColor);
                g.drawOval((int) (centerX - radius), (int) (centerY - radius), diameter, diameter);
            }

            if (state.isInitial()) {
                //drawStartArrow((int) (centerX - radius - 10 * getEditor().getZoomfactor()), centerY, startLineColor, g);
                g.setStroke(getInitArrowStroke());
                drawInitialArrow(centerX, centerY, g);
                g.setStroke(getStateStroke());
            }

            g.setColor(foregroundColor);
            //-- center the string --
            drawStateNameText(state.getName(), centerX, centerY, stateFont, g);
        }
        g.setColor(Color.black);


    }

    /**
     * paint transition edges of the DFA
     */
    private void paintTransitions() {
        Graphics2D g = this.graphics;
        Automaton automaton = editor.getAutomaton();
        
        Font font = PropertiesHandler.getInstance().getAutomatonTransitionFont();
        Font transitionFont = font.deriveFont((float) (font.getSize() * editor.getZoomfactor()));
        g.setFont(transitionFont);


        for (Transition transition : automaton.getTransitions()) {
            State fromState = transition.getFromState();
                State toState = transition.getToState();
                if (fromState != null && toState != null) {
                    String caption = transition.writeLabels();
                    if (caption.length() > 16) {
                        caption = caption.substring(0, 15) + "...";
                    }


                    paintTransition(transition, caption, false);
            }
        }
    }

    /**
     * get the comma seperated string from Arraylist like 'a, b, c'
     * @param t Transition object
     * @return Stirng
     */
//    private String getStringFromInputArray(Transition t)
//    {
//        if (t != null)
//        {
//            String c = "";
//            for (int i=0;i<t.getInput().size();i++)
//            {
//                if (i == t.getInput().size()-1)
//                {
//                    c = c + t.getInput().get(i);
//                } else
//                {
//                    c = c + t.getInput().get(i) + ",";
//                }
//            }
//            return c;
//        } else
//        return "-";
//    }
    /**
     * paints a transition
     * @param fromState From state
     * @param toState To state
     * @param transition Transition object
     * @param caption Label of the edge
     * @param color color (if special)
     * @param fakeTrans Boolean flag to distinguish between real and the helping transition in add transition mode
     */
    public void paintTransition(Transition transition, String caption, boolean fakeTrans) {
            State fromState = transition.getFromState();
            State toState = transition.getToState();
            int captionPositionX = 0;
            int captionPositionY = 0;

            PropertiesHandler properties = PropertiesHandler.getInstance();
            Color backgroundColor = properties.getAutomatonTransitionBackColor();
            Color strokeColor = transition.getVisualProperties().getStrokeColor();
            Color foregroundColor = transition.getVisualProperties().getFontColor();

            boolean paintLabelBackground = false;
            if (transition.getVisualProperties().isMouseOver()) {
                backgroundColor = properties.getAutomatonTransitionMouseOverBackColor();
                strokeColor = properties.getAutomatonTransitionMouseOverStrokeColor();
                foregroundColor = properties.getAutomatonTransitionMouseOverFontColor();
                paintLabelBackground = true;
            }

            if (transition.getVisualProperties().isSelected()) {
                backgroundColor = properties.getAutomatonTransitionSelectedBackColor();
                strokeColor = properties.getAutomatonTransitionSelectedStrokeColor();
                foregroundColor = properties.getAutomatonTransitionSelectedFontColor();
                paintLabelBackground = true;
            }

            if (fakeTrans) {
                backgroundColor = Color.ORANGE;
                strokeColor = Color.RED;
                foregroundColor = Color.BLUE;
            }

            //-- simulation purposes --
//            if (dfaSim.isSimulationModeActive())
//            {
//                if (dfaSim.getLastTransitionTaken() != null && t == dfaSim.getLastTransitionTaken())
//                {
//                    colorCaptionColor = COLORTRANSITIONLINETAKEN;
//                    colorLineColor = COLORTRANSITIONLINETAKEN;
//                    colorFont = Color.white;
//                }
//            }

           // boolean showTouchButton = (getEditor().getToolState() == EditorState.hand) && t.getVisualProperties().isSelected();

            double zoom = editor.getZoomfactor();
            
            Graphics2D g = this.graphics;
            int s1x = (int) (fromState.getVisualProperties().getXPos() * zoom);
            int s1y = (int) (fromState.getVisualProperties().getYPos() * zoom);

            int s2x = (int) (toState.getVisualProperties().getXPos() * zoom);
            int s2y = (int) (toState.getVisualProperties().getYPos() * zoom);

       //     g.setColor(color);

            //-- arc case or linear --
        //    boolean isBidirectional = false;

//            if (!fakeTrans) {
//                //            try {
//                // isBidirectional = dfaEditor.getDfa().isBidirectionalTransition(s1, s2);
//                //            } catch (NoSuchTransitionException ex) {
//                //                System.err.println(ex.getMessage());
//                //            }
//                isBidirectional = true;//dfaEditor.getDfa().containsTransition(new Transition(s2,s1));
//            } else {
//                isBidirectional = true;
//            }

            if (fromState != toState) {
                //-- get control point --
                int dx = s2x - s1x;
                int dy = s2y - s1y;
                double vlength = calcVectorLength(dx, dy);

                if (vlength > 0) {
                    QuadCurve2D c = new QuadCurve2D.Double();

                    double centerx = (s2x + s1x) / 2;
                    double centery = (s2y + s1y) / 2;

                    double normx = dx / vlength;
                    double normy = dy / vlength;

                    double additionalArcDistance = vlength / 100;
                    //-- turn vector 90 degrees --
                    double turnedx = ARCDISTANCE * normy * additionalArcDistance * transition.getVisualProperties().getCurveFactor();
                    double turnedy = -ARCDISTANCE * normx * additionalArcDistance * transition.getVisualProperties().getCurveFactor();

                    int cpointx = (int) (centerx + turnedx);
                    int cpointy = (int) (centery + turnedy);

                    double textAdaption = 35;
                    double absCurveFactor = Math.abs(transition.getVisualProperties().getCurveFactor());
                    double direction = 1;
                    if (transition.getVisualProperties().getCurveFactor() < 0) {
                        direction = -1;
                        textAdaption = 35;
                    }
                    if (absCurveFactor < 2) {
                        textAdaption = Math.max(20, textAdaption * curveAdaptionFactor(absCurveFactor));
                    }
                    textAdaption = textAdaption * direction;
                    //System.out.println(t.getCurveFactor());

                    int textpointx = (int) (centerx + turnedx / (2) + normy * textAdaption * zoom);
                    int textpointy = (int) (centery + turnedy / (2) - normx * textAdaption * zoom);

                    //-- tangential crossing with the circles (start and end of curve) --
                    int radius = (int) (editor.getZoomfactor() * getStateRadius());

                    Double p1[] = getIntersectionPoint(s1x, s1y, cpointx, cpointy, 1.2 * radius);//1.2
                    Double p2[] = getIntersectionPoint(s2x, s2y, cpointx, cpointy, 1.4 * radius);//1.4



                    int h1x = (int) (Math.round(p1[0]) + editor.getOffsetX());
                    int h1y = (int) (Math.round(p1[1]) + editor.getOffsetY());

                    int h2x = (int) (Math.round(p2[0]) + editor.getOffsetX());
                    int h2y = (int) (Math.round(p2[1]) + editor.getOffsetY());

                    g.setColor(strokeColor);
                    //-- quadratic arc --
                    if (vlength / zoom > getStateDiameter() + 15) {
                        c.setCurve(h1x, h1y,
                                cpointx + editor.getOffsetX(), cpointy + editor.getOffsetY(),
                                h2x, h2y);
                        g.draw(c);
                    }
                    //-- draw text --
                    if (paintLabelBackground) {
                        Rectangle2D fbounds = getFontBounds(caption,
                                textpointx + (int) editor.getOffsetX(),
                                textpointy + (int) editor.getOffsetY(),
                                null, g);
                        paintTransitionHighlightRectangle(fbounds, backgroundColor, (int) (4 * getEditor().getZoomfactor()), g);
                    }

                    //-- arrow --
                    double ax = h2x - cpointx - editor.getOffsetX();
                    double ay = h2y - cpointy - editor.getOffsetY();

                    double arrowAngle = Math.atan2(ay, ax);
                    if (vlength <= (getStateDiameter() + 25) * zoom) {
                        arrowAngle = Math.atan2(dy, dx);
                    }
                    g.setColor(strokeColor);
                    drawArrow(h2x, h2y, 4, arrowAngle, g);

                    g.setColor(foregroundColor);
                    drawCenteredText(caption, textpointx + (int) editor.getOffsetX(),
                            textpointy + (int) editor.getOffsetY(), null, g);
                    captionPositionX = textpointx;
                    captionPositionY = textpointy;

                    //-- touchup button --

              //      if (showTouchButton) {
          //              drawTouchTransitionButton(t, normx, normy, additionalArcDistance, h1x, h1y, h2x, h2y);
             //       }
                }

//            } else if (s1 != s2) {
//                //-- linear-case --
//                Vector<Double> p1 = getIntersectionPoint(s1x, s1y, s2x, s2y, z * 1.2 * STATEDRAWSIZE / 2);
//                Vector<Double> p2 = getIntersectionPoint(s2x, s2y, s1x, s1y, z * 1.4 * STATEDRAWSIZE / 2);
//                int h1x = (int) (Math.round(p1.get(0)) + automatonEditor.getOffsetX());
//                int h1y = (int) (Math.round(p1.get(1)) + automatonEditor.getOffsetY());
//
//                int h2x = (int) (Math.round(p2.get(0)) + automatonEditor.getOffsetX());
//                int h2y = (int) (Math.round(p2.get(1)) + automatonEditor.getOffsetY());
//                g.setColor(colorLineColor);
//                g.drawLine(h1x, h1y, h2x, h2y);
//
//                double ax = s2x - s1x;
//                double ay = s2y - s1y;
//
//
//                double vlength = calcVectorLength(ax, ay);
//
//                if (vlength > 0) {
//
//                    double centerx = (s2x + s1x) / 2;
//                    double centery = (s2y + s1y) / 2;
//
//                    double normx = ax / vlength;
//                    double normy = ay / vlength;
//
//                    double arrowAngle = Math.atan2(ay, ax);
//                    g.setColor(colorLineColor);
//                    drawArrow(h2x, h2y, 4, arrowAngle, g);
//                    // -- text --
//                    int textX = (int) (centerx + 12 * normy * automatonEditor.getZoomfactor());
//                    int textY = (int) (centery - 12 * normx * automatonEditor.getZoomfactor());
//
//
//                    if (paintLabelBackground) {
//                        Rectangle2D fbounds = getFontBounds(caption,
//                                textX + (int) automatonEditor.getOffsetX(),
//                                textY + (int) automatonEditor.getOffsetY(),
//                                transitionFont, g);
//                        paintTransitionHighlightRectangle(fbounds, colorCaptionColor, (int) (4 * getEditor().getZoomfactor()), g);
//                    }
//                    g.setColor(colorFont);
//                    drawCenteredText(caption,
//                            textX + (int) automatonEditor.getOffsetX(),
//                            textY + (int) automatonEditor.getOffsetY(),
//                            transitionFont, g);
//                    captionPositionX = textX;
//                    captionPositionY = textY;
//                }
            } else {
                //-- cirlce to state itself --

//
//                double boxX = s1x - STATEDRAWSIZE * 0.3 * z;
//                double boxY = s1y - STATEDRAWSIZE * 0.95 * z;
//                double w = STATEDRAWSIZE * 0.6 * z;
//                double h = STATEDRAWSIZE * 0.6 * z;
//
//                g.setColor(colorLineColor);
//                Arc2D arc = new Arc2D.Double(boxX + automatonEditor.getOffsetX(), boxY + automatonEditor.getOffsetY(), w, h, -20, 220, Arc2D.OPEN);
//                g.draw(arc);
//


                double diameter = editor.getZoomfactor() * getStateDiameter();
                double boxX = s1x - diameter * 0.3;
                double boxY = s1y - diameter * 0.95;
                double w = diameter * 0.6;
                double h = diameter * 0.6;

                double angle = transition.getVisualProperties().getCurveFactor();
              //  angle = Math.PI/3;
                double rotAngle = angle + Math.PI/2;


                g.setColor(strokeColor);
                 g.rotate(rotAngle, s1x + editor.getOffsetX(), s1y + editor.getOffsetY());
                Arc2D arc = new Arc2D.Double(boxX + editor.getOffsetX(), boxY + editor.getOffsetY(), w, h, -20, 220, Arc2D.OPEN);
                g.draw(arc);



                //-- arrow --
                double ax = s1x + 0.3 * diameter;
                double ay = s1y - 0.6 * diameter;
                double arrowAngle = 1.9D;
                g.setColor(strokeColor);
                drawArrow((int) ax + (int) editor.getOffsetX(), (int) ay + (int) editor.getOffsetY(), 4, arrowAngle, g);


//                double r = STATEDRAWSIZE/2 + 0.5;
//
//                double angle = Math.PI;
//                double extent = Math.PI/2;
//                double length = r + 50;
//
//                double x1 = r*Math.cos(angle + extent/2) + s1x;
//                double y1 = r*Math.sin(angle + extent/2) + s1y;
//
//                double x2 = r*Math.cos(angle - extent/2) + s1x;
//                double y2 = r*Math.sin(angle - extent/2) + s1y;
//
//                double cx = (length)*Math.cos(angle) + s1x;
//                double cy = (length)*Math.sin(angle) + s1y;


             //   g.draw(new Arc2D.Double(s1x - r - 10, s1y - r - 10, 2*r + 20, 2*r + 20, -20, 220, Arc2D.PIE));

                //g.draw(new QuadCurve2D.Double(x1, y1,cx, cy, x2,y2));
            //g.draw(new Arc2D.Double
                 g.rotate(-rotAngle, s1x + editor.getOffsetX(), s1y + editor.getOffsetY());



                // -- text --

                int textX = (int) ((diameter * 1.2) * Math.cos(angle)) + s1x;
                int textY = (int) ((diameter * 1.2) * Math.sin(angle)) + s1y;


//                int textX = (int) s1x;
//                int textY = (int) (s1y - STATEDRAWSIZE * 1.2 * z);
                if (paintLabelBackground) {
                    Rectangle2D fbounds = getFontBounds(caption,
                            textX + (int) editor.getOffsetX(),
                            textY + (int) editor.getOffsetY(), null, g);
                    paintTransitionHighlightRectangle(fbounds, backgroundColor, (int) (4 * getEditor().getZoomfactor()), g);
                }
                g.setColor(foregroundColor);
                drawCenteredText(caption,
                        textX + (int) editor.getOffsetX(),
                        textY + (int) editor.getOffsetY(),
                        null, g);
                captionPositionX = textX;
                captionPositionY = textY;


            }
            transition.getVisualProperties().setClickPositionX((int) (captionPositionX / zoom));
            transition.getVisualProperties().setClickPositionY((int) (captionPositionY / zoom));
            g.setColor(Color.black);
        

    }

    /**
     * get the curve adaption factor for the quadratic curves
     * @param input double value
     * @return
     */
    private double curveAdaptionFactor(double input) {
        double f = (input - 1);
        f = 1 / (f * f * f * f + 1);
        return 0.7 * (input / 2) * (input / 2) + 0.3 * f;
    }
    

//    /**
//     * another adaption factor for the best postions of captions
//     * @param input
//     * @param min
//     * @return
//     */
//    private double curvePointAdaptionFactor(double input, double min) {
//        double x = input - min;
//        return 1 - 0.25 / (x * x + 1);
//    }

    /**
     * paint the rounded background highlight box of transitions
     * @param r coordinates
     * @param c color to fill
     * @param additionalBorder determines the additional distance to the border
     * @param g canvas to draw on
     */
    private void paintTransitionHighlightRectangle(Rectangle2D r, Color c, int additionalBorder, Graphics2D g) {
        g.setColor(c);
        g.fillRoundRect((int) (r.getX() - additionalBorder), (int) (r.getY() - additionalBorder), (int) (r.getWidth() + 2 * additionalBorder), (int) (r.getHeight() + 2 * additionalBorder), 10, 10);
    }



    private void drawInitialArrow(int px, int py, Graphics2D g) {
        int xCenter = px;
        InitialArrow initialArrow = editor.getInitArrow();
        double angle = initialArrow.getOrientation();


        g.rotate(angle, xCenter, py);
        px-= (int) (10 + getStateRadius())*getEditor().getZoomfactor();
        double lx = initialArrow.getLength() * getZoom();
        double s1x = px - lx;
        double s1y = py;

        Rectangle bounds = new Rectangle((int) s1x, (int) (s1y - 6 * getZoom()), (int) (lx + 8 * getZoom()), (int) (12 * getZoom()));


        AffineTransform rotation = AffineTransform.getRotateInstance(angle, xCenter, py);
        Shape shape = rotation.createTransformedShape(bounds);
        initialArrow.setShape(shape);
        initialArrow.setStateCenter(new Point(xCenter, py));


        Color color = initialArrow.getColor();

        if(initialArrow.isSelected()) {
            color = PropertiesHandler.getInstance().getAutomatonInitArrowColorSelected();
        }
        if(initialArrow.isMouseOver()) {
            color = PropertiesHandler.getInstance().getAutomatonInitArrowColorMouseOver();
        }
        g.setColor(color);
        g.drawLine(px, py, (int) s1x, (int) s1y);
        drawArrow(px, py, 4, 0, g);
        g.rotate(-angle, xCenter, py);
    }

    /**
     * draw the transition arrows
     * @param px position x
     * @param py position y
     * @param size size (not used in the moment)
     * @param angle Angle the arrow should be painted
     * @param g canvas to paint on
     */
    private void drawArrow(int px, int py, double size, double angle, Graphics2D g) {
        double p1x = -0.5 * size * editor.getZoomfactor();
        double p1y = -1.1 * size * editor.getZoomfactor();
        double p2x = -0.5 * size * editor.getZoomfactor();
        double p2y = 1.1 * size * editor.getZoomfactor();
        double p3x = 2 * size * editor.getZoomfactor();
        double p3y = 0;

        double t1x = px + turnXbyAngle(p1x, p1y, angle);
        double t1y = py + turnYbyAngle(p1x, p1y, angle);
        double t2x = px + turnXbyAngle(p2x, p2y, angle);
        double t2y = py + turnYbyAngle(p2x, p2y, angle);
        double t3x = px + turnXbyAngle(p3x, p3y, angle);
        double t3y = py + turnYbyAngle(p3x, p3y, angle);
        Polygon s = new Polygon();
        s.addPoint((int) t1x, (int) t1y);
        s.addPoint((int) t2x, (int) t2y);
        s.addPoint((int) t3x, (int) t3y);
        g.fillPolygon(s);
    }

    /**
     * turn a x vector component
     * @param x vector x
     * @param y vector y
     * @param a angle
     * @return new x value
     */
    private double turnXbyAngle(double x, double y, double a) {
        return Math.cos(a) * x - Math.sin(a) * y;
    }

    /**
     *  turn a y vector component
     * @param x vector x
     * @param y vector y
     * @param a angle
     * @return new y value
     */
    private double turnYbyAngle(double x, double y, double a) {
        return Math.sin(a) * x + Math.cos(a) * y;
    }

    /**
     * get the boundaries of a string with a special font context
     * @param s Text
     * @param centerX position x
     * @param centerY position y
     * @param f Font
     * @param g canvas
     * @return
     */
    private Rectangle2D getFontBounds(String s, int centerX, int centerY, Font f, Graphics2D g) {
        //-- center the string --
        if(f==null) {
            f=g.getFont();
        }
        FontMetrics fm = g.getFontMetrics(f);
        Rectangle2D rect = fm.getStringBounds(s, g);

        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());
        int textx = centerX - textWidth / 2;
        int texty = centerY - textHeight / 2;

        rect.setRect(textx, texty, textWidth, textHeight);

        return rect;
    }

    /**
     * draw a centered text at a certain position
     * @param s Text to draw
     * @param centerX position x
     * @param centerY position y
     * @param f Font
     * @param g canvas
     * @return rectangle of text bounds
     */
    private Rectangle2D drawCenteredText(String s, int centerX, int centerY, Font f, Graphics2D g) {
        //-- center the string --
        if(f==null) {
            f = g.getFont();
        }
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


    private void drawStateNameText(String text, int centerX, int centerY, Font font, Graphics2D g) {
        FontMetrics fm = g.getFontMetrics(font);
        Rectangle2D rect = fm.getStringBounds(text, g);
        double diameter = PropertiesHandler.getInstance().getAutomatonStateDiameter() * getZoom();
        if(rect.getWidth() >  diameter) {
            int validLetters = (int) (diameter * text.length() / rect.getWidth() - 1);
            if(validLetters < text.length()) {
                text = text.substring(0, validLetters) + "..";
                rect = fm.getStringBounds(text, g);
            }
        }
        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());
        int textx = centerX - textWidth / 2;
        int texty = centerY - textHeight / 2 + fm.getAscent();

        //-- render text --
        g.drawString(text, textx, texty);
    }



    /**
     * calculate the intersection point of a line with a circle
     * @param fromX vector x
     * @param fromY vector y
     * @param toX vector x
     * @param toY vector y
     * @param distance radius/distance of circle
     * @return vector objects
     */
    public Double[] getIntersectionPoint(double fromX, double fromY, double toX, double toY, double distance) {
        Double v[] = new Double[2];
        double dx = toX - fromX;
        double dy = toY - fromY;
        double l = calcVectorLength(dx, dy);

        if (l > 0) {
            double dnx = dx / l;
            double dny = dy / l;

            v[0] = fromX + dnx * distance;
            v[1] = fromY + dny * distance;
        } else {

            v[0] = 0D;
            v[1] = 0D;
        }
        return v;
    }

    /**
     * get vector length
     * @param dx
     * @param dy
     * @return length of vector
     */
    private double calcVectorLength(double dx, double dy) {
        if (dx == 0 && dy == 0) {
            return 0;
        } else {
            return Math.sqrt(dx * dx + dy * dy);
        }
    }



    /**
     * paint user help actions like show possible transitions/states
     */
    private void paintUserActions() {
//        if (automatonEditor.getDummyState().getVisualProperties().isVisible()) {
//            paintDummyState(automatonEditor.getDummyState());
//        }
        paintDummyTransition(editor.getDummyTransition());
    }

    /**
     * paint the user helping state
     * @param s State to paint
     */
//    public void paintDummyState(State s) {
//        Graphics2D g = this.graphics;
//
//        int radius = (int) (automatonEditor.getZoomfactor() * STATEDRAWSIZE / 2);
//
//        int px = s.getVisualProperties().getXPos() - radius + (int) automatonEditor.getOffsetX();
//        int py = s.getVisualProperties().getYPos() - radius + (int) automatonEditor.getOffsetY();
//
//        g.setColor(s.getVisualProperties().getFillColor());
//        g.fillOval(px, py, (int) (STATEDRAWSIZE * automatonEditor.getZoomfactor()), (int) (STATEDRAWSIZE * automatonEditor.getZoomfactor()));
//        g.setColor(COLORADDNEWELEMENT2);
//        g.drawOval(px, py, (int) (STATEDRAWSIZE * automatonEditor.getZoomfactor()), (int) (STATEDRAWSIZE * automatonEditor.getZoomfactor()));
//
//        Font nameFont = new Font("Arial", Font.PLAIN, (int) (TEXTSIZE * automatonEditor.getZoomfactor()));
//        g.setColor(Color.white);
//        drawCenteredText(s.getName(), px + (int) (radius / 1.2), py + (int) (radius / 1.5), nameFont, g);
//        g.setColor(Color.black);
//    }

    /**
     * paint the dummy transition in add transition mode
     * @param t paint transition
     */
    public void paintDummyTransition(Transition t) {
        if (t.getVisualProperties().isVisible()) {
//            paintTransition(t, "Add transition " + t.getFromState().getName()
//                    + " > " + t.getToState().getName(), COLORADDNEWELEMENT, true);
            paintTransition(t, " " , true);            
        }

    }


}
