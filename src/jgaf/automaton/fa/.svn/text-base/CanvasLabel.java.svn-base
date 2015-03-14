/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class CanvasLabel implements Cloneable {

    private final long serialVersionUID = 11000000000001001L;

    private Color foregroundColor = PropertiesHandler.getInstance().getAutomatonLabelFrontColor();
    private Color backgroundColor = PropertiesHandler.getInstance().getAutomatonLabelBackColor();

    private int xPos = 0;
    private int yPos = 0;
    private int transparency = 50;
    private String caption;
    private int rotationFactor = 0;
    private Font font = new Font("Arial", Font.PLAIN, 16);


    private transient boolean mouseOver = false;
    private transient boolean selected = false;

    private transient int dragOffsetX = 0;
    private transient int dragOffsetY = 0;

    private Shape outline;

    public CanvasLabel(String caption) {
        outline = new Rectangle();
        this.caption = caption;
    }

    public CanvasLabel() {
        this("");
    }


    public void move(int x, int y) {
        xPos+=x;
        yPos+=y;
    }

    public void setCoordinates(int x, int y) {
        setXPos(x);
        setYPos(y);
    }

    public Point getPoint() {
        return new Point(getXPos(), getYPos());
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color color) {
        this.foregroundColor = color;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

//    public int getSize() {
//        return size;
//    }
//
//    public void setSize(int size) {
//        this.size = size;
//    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }



    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getDragOffsetX() {
        return dragOffsetX;
    }

    public void setDragOffsetX(int dragOffsetX) {
        this.dragOffsetX = dragOffsetX;
    }

    public int getDragOffsetY() {
        return dragOffsetY;
    }

    public void setDragOffsetY(int dragOffsetY) {
        this.dragOffsetY = dragOffsetY;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getRotationFactor() {
        return rotationFactor;
    }

    public void setRotationFactor(int rotationFactor) {
        this.rotationFactor = rotationFactor;
    }

//    public void rotate(double angle) {
//        rotationFactor += angle;
//        if (rotationFactor >= 2*Math.PI) {
//            rotationFactor-=2*Math.PI;
//        }
//        if (rotationFactor < 0) {
//            rotationFactor+=2*Math.PI;
//        }
//    }

    /**
     * @return the bounds
     */
    public Shape getOutline() {
        return outline;
    }

    /**
     * @param bounds the bounds to set
     */
    public void setOutline(Shape bounds) {
        this.outline = bounds;
    }
    

    public int getTransparency() {
        return transparency;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
    }


    @Override
    public Object clone() {
        CanvasLabel clone = new CanvasLabel();
        clone.setCaption(caption);
        clone.setBackgroundColor(new Color(backgroundColor.getRGB()));
        clone.setForegroundColor(new Color(foregroundColor.getRGB()));
        clone.setXPos(xPos);
        clone.setYPos(yPos);
        clone.setTransparency(transparency);
        clone.setRotationFactor(rotationFactor);
        clone.setFont(new Font(font.getName(), font.getStyle(), font.getSize()));
        return clone;
    }

    public void modify(CanvasLabel label) {
        setCaption(label.getCaption());
        setBackgroundColor(label.getBackgroundColor());
        setForegroundColor(label.getForegroundColor());
        setFont(label.getFont());
       // setXPos(label.getXPos());
      //  setYPos(label.getYPos());
        setTransparency(label.getTransparency());
        setRotationFactor(label.getRotationFactor());
        setFont(label.getFont());
    }


    public boolean isEqualTo(CanvasLabel label) {
        if (label == null) {
            return false;
        }
        if (getCaption().equals(label.getCaption())
                //&& getXPos() == label.getXPos() &&
                //&& getYPos() == label.getYPos()
                && getTransparency() == label.getTransparency()
                && getRotationFactor() == label.getRotationFactor()
                && getForegroundColor().equals(label.getForegroundColor())
                && getBackgroundColor().equals(label.getBackgroundColor())
                && getFont().equals(label.getFont())) {
            return true;
        }
        return false;
    }


}
