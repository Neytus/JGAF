/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import jgaf.automaton.Automaton;
import jgaf.gui.AutomatonTableRepresenter;

/**
 *
 * @author hanis
 */
public class AutomatonTableEditor implements FSAutomatonSubEditor {


    private static double ZOOM_DEFAULT = 1;
    private static double ZOOM_MIN = 0.2;
    private static double ZOOM_MAX = 4;
    private static double ZOOM_JUMP = 0.2;

    private double zoomfactor = 1;

    private AutomatonTablePainter painter;
    private AutomatonTableRepresenter representer;



    private int offsetX = 100;
    private int offsetY = 100;
    private int panStartX = 0;
    private int panStartY = 0;
    private int oldOffsetX = 0;
    private int oldOffsetY = 0;

    private boolean editable = true;

    private FSAutomatonEditor editor;




    public AutomatonTableEditor(FSAutomatonEditor editor) {
        this.editor = editor;
        representer = new AutomatonTableRepresenter(this);
        painter = new AutomatonTablePainter();
        painter.setEditor(this);
    }

    public Painter getPainter() {
        return painter;
    }


    public boolean isEditable() {
        return true;
    }

    public void handleMouseLeftPressed(MouseEvent evt) {
        panStartX = evt.getX();
        panStartY = evt.getY();
        oldOffsetX = getOffsetX();
        oldOffsetY = getOffsetY();
    }

    public void handleMouseLeftReleased(MouseEvent evt) {

    }

    public void handleMouseDragged(MouseEvent evt) {
            setOffsetX(oldOffsetX + evt.getX() - panStartX);
            setOffsetY(oldOffsetY + evt.getY() - panStartY);
            repaint();
    }

    public void handleMouseMoved(MouseEvent evt) {
    }

    public void handleKeyPressed(KeyEvent evt) {
    }

    public void handleRightMouseClick(MouseEvent evt) {
    }


    public void paint(Graphics2D g2d) {
        painter.updateGraphics(g2d);
    }


    public void repaint() {
        if(representer != null)  {
            representer.repaint();
        }

    }

    public void redo() {
    }

    
    public void undo() {
    }

    public void zoomIn() {
        if (zoomfactor < ZOOM_MAX) {
            zoomfactor += ZOOM_JUMP;
            if (zoomfactor > ZOOM_MAX) {
                zoomfactor = ZOOM_MAX;
            }
            repaint();
        }
    }

    public void zoomOut() {
        if (zoomfactor > ZOOM_MIN) {
            zoomfactor -= ZOOM_JUMP;
            if (zoomfactor < ZOOM_MIN) {
                zoomfactor = ZOOM_MIN;
            }
            repaint();
        }
    }

    public void clearZoom() {
        if (zoomfactor != ZOOM_DEFAULT) {
            zoomfactor = ZOOM_DEFAULT;
            repaint();
        }
    }


    public Automaton getAutomaton() {
        return editor.getAutomaton();
    }

    public double getZoomfactor() {
        return zoomfactor;
    }

    public void setZoomfactor(double zoomfactor) {
        this.zoomfactor = zoomfactor;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }



    public BufferedImage getImage() {
        BufferedImage bi = new BufferedImage(painter.getWidth(), painter.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gimg = (Graphics2D) bi.getGraphics();
        painter.updateGraphicsForSVG(gimg);
        return bi;
    }

    public Dimension getPreferredSize() {
        int width = (int) (offsetX + (painter.getWidth()-60) * zoomfactor);
        int height = (int) (offsetY + (painter.getHeight()-60) * zoomfactor);
        return new Dimension(width, height);
    }

    public void center() {
        double width = editor.getViewportSize().getWidth();
        double height = editor.getViewportSize().getHeight();

        int tableWidth = painter.getWidth();
        int tableHeight = painter.getHeight();

        double widthRatio = width / (tableWidth);
        double heightRatio = height / (tableHeight);

        double newZoom = 1;//zoomfactor;

        if (widthRatio < 1 || heightRatio < 1) { // scale if needed
            newZoom = widthRatio < heightRatio ? widthRatio : heightRatio;
            if (newZoom > ZOOM_MAX) {
                newZoom = ZOOM_MAX;
            } else if (newZoom < ZOOM_MIN) {
                newZoom = ZOOM_MIN;
            }            
        }
        setZoomfactor(newZoom);
        int offX = (int) ((width - (tableWidth - 60) * zoomfactor) / 2);
        int offY = (int) ((height - (tableHeight - 60) * zoomfactor) / 2);

        setOffsetX(offX);
        setOffsetY(offY);

        repaint();


        //offsetX + painter.getWidth() * zoomfactor);
    }



    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public JPanel getRepresenter() {
        return representer;
    }
//
//    @Override
//    public boolean exportPNG(File file) {
//        BufferedImage bi = new BufferedImage(painter.getWidth(), painter.getHeight(), BufferedImage.TYPE_INT_RGB);
//        Graphics2D gimg = (Graphics2D) bi.getGraphics();
//        painter.updateGraphicsForSVG(gimg);
//        try {
//            ImageIO.write(bi, "png", file);
//         //   JOptionPane.showMessageDialog(null, "File saved successfully!", "Image export", JOptionPane.INFORMATION_MESSAGE);
//            return true;
//        } catch (IOException ex) {
//           // JOptionPane.showMessageDialog(null, "Error while saving file!", "Image export", JOptionPane.ERROR_MESSAGE);
//        }
//        return false;
//    }

}
