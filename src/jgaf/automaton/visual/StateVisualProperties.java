/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.visual;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class StateVisualProperties implements Serializable, Cloneable {

    private final long serialVersionUID = 10000000000001001L;

    private int xPos = 0;
    private int yPos = 0;

    private transient int dragOffsetX = 0;
    private transient int dragOffsetY = 0;

    private transient boolean visible = true;
    private transient boolean selected = false;
    private transient boolean mouseOver = false;

    private Color strokeColor = PropertiesHandler.getInstance().getAutomatonStateStrokeColor();
    private Color fillColor = PropertiesHandler.getInstance().getAutomatonStateFillColor();
    private Color fontColor = PropertiesHandler.getInstance().getAutomatonStateFontColor();
   

    public StateVisualProperties() {
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setCoordinates(int x, int y) {
        setXPos(x);
        setYPos(y);
    }

    public void setCoordinates(Point point) {
        setXPos(point.x);
        setYPos(point.y);
    }


    public Point getPoint() {
        return new Point(xPos, yPos);
    }

    public void move(int x, int y) {
        xPos+=x;
        yPos+=y;
    }


    /**
     * @return the dragOffsetX
     */
    public int getDragOffsetX() {
        return dragOffsetX;
    }

    /**
     * @param dragOffsetX the dragOffsetX to set
     */
    public void setDragOffsetX(int dragOffsetX) {
        this.dragOffsetX = dragOffsetX;
    }

    /**
     * @return the dragOffsetY
     */
    public int getDragOffsetY() {
        return dragOffsetY;
    }

    /**
     * @param dragOffsetY the dragOffsetY to set
     */
    public void setDragOffsetY(int dragOffsetY) {
        this.dragOffsetY = dragOffsetY;
    }


    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color color) {
        this.strokeColor = color;
    }
    
    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color color) {
        this.fontColor = color;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }



    public void modify(StateVisualProperties properties) {
            xPos = properties.getXPos();
            yPos = properties.getYPos();
            setFillColor(properties.getFillColor());
            fontColor = properties.getFontColor();
            strokeColor = properties.getStrokeColor();
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof StateVisualProperties) {
           StateVisualProperties prop = (StateVisualProperties) obj;
           if(prop.getXPos() == getXPos() && prop.getYPos() == getYPos()
                   && prop.getFillColor().equals(getFillColor())
                   && prop.getStrokeColor().equals(getStrokeColor())
                   && prop.getFontColor().equals(getFontColor())) {
                return true;
           }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.xPos;
        hash = 53 * hash + this.yPos;
        hash = 53 * hash + (this.strokeColor != null ? this.strokeColor.hashCode() : 0);
        hash = 53 * hash + (this.getFillColor() != null ? this.getFillColor().hashCode() : 0);
        hash = 53 * hash + (this.fontColor != null ? this.fontColor.hashCode() : 0);
        return hash;
    }



    @Override
    public Object clone() {
        StateVisualProperties obj = new StateVisualProperties();
        obj.setXPos(xPos);
        obj.setYPos(yPos);
        obj.setFillColor(new Color(getFillColor().getRGB()));
        obj.setFontColor(new Color(fontColor.getRGB()));
        obj.setStrokeColor(new Color(strokeColor.getRGB()));
        return obj;
    }

}


