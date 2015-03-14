/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class InitialArrow implements Cloneable {



    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";

    public static final String[] BASIC_ORIENTATIONS = {LEFT, RIGHT, TOP, BOTTOM};

    private Color color;
    private boolean selected;
    private boolean mouseOver;
    private Shape shape;
    private double orientation;
    private int length;
    private StrokeStyle strokeStyle;
    private double strokeWidth;
    private Point StateCenter;




    public InitialArrow() {
        PropertiesHandler properties = PropertiesHandler.getInstance();
        this.color = properties.getAutomatonInitArrowColor();
        this.orientation = properties.getAutomatonInitArrowOrientation();
        this.length = properties.getAutomatonInitArrowLength();
        this.strokeWidth = properties.getAutomatonInitArrowStrokeWidth();
        this.strokeStyle = properties.getAutomatonInitArrowStrokeStyle();
        this.mouseOver = false;
        this.selected = false;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean containsPoint(Point point) {
        if(shape == null) {
            return false;
        }
        return shape.contains(point);
    }

    public Rectangle getBounds() {
        return this.shape.getBounds();
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public StrokeStyle getStrokeStyle() {
        return strokeStyle;
    }

    public void setStrokeStyle(StrokeStyle strokeStyle) {
        this.strokeStyle = strokeStyle;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Point getStateCenter() {
        return StateCenter;
    }

    public void setStateCenter(Point StateCenter) {
        this.StateCenter = StateCenter;
    }


    public boolean hasSameBasicProperties(InitialArrow initArrow) {
        return initArrow.getColor().equals(getColor()) &&
                initArrow.getStrokeStyle().getType() == getStrokeStyle().getType() &&
                initArrow.getLength() == getLength() &&
                Math.abs(initArrow.getStrokeWidth() - getStrokeWidth()) < 0.0001;
    }


    public void modify(InitialArrow initArrow) {
        setStrokeStyle(initArrow.getStrokeStyle());
        setLength(initArrow.getLength());
        setColor(initArrow.getColor());
        setStrokeWidth(initArrow.getStrokeWidth());
    }


    @Override
    public Object clone() {
        InitialArrow obj = new InitialArrow();
        obj.setColor(new Color(getColor().getRGB()));
        obj.setLength(length);
        obj.setStrokeWidth(strokeWidth);
        obj.setStrokeStyle(new StrokeStyle(strokeStyle.getTypeString()));        
        return obj;
    }

    public int getX() {
        return (int) shape.getBounds().getX();
    }

    public int getY() {
        return (int) shape.getBounds().getY();
    }


}
