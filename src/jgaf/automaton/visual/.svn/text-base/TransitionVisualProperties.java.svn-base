/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.visual;

import java.awt.Color;
import java.io.Serializable;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class TransitionVisualProperties implements Serializable, Cloneable {

private final long serialVersionUID = 10000000000001002L;
    private transient boolean visible = true;
    private transient boolean selected = false;
    private transient boolean mouseOver = false;

    private int clickPositionX = 0;
    private int clickPositionY = 0;

    private double curveFactor = 0;

    private Color strokeColor = PropertiesHandler.getInstance().getAutomatonTransitionStrokeColor();
    private Color fontColor = PropertiesHandler.getInstance().getAutomatonTransitionFontColor();



    public TransitionVisualProperties() {        
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

    public int getClickPositionX() {
        return clickPositionX;
    }

    public void setClickPositionX(int clickPositionX) {
        this.clickPositionX = clickPositionX;
    }

    public int getClickPositionY() {
        return clickPositionY;
    }

    public void setClickPositionY(int clickPositionY) {
        this.clickPositionY = clickPositionY;
    }

    public double getCurveFactor() {
        return curveFactor;
    }

    public void setCurveFactor(double curveFactor) {
        this.curveFactor = curveFactor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void modify(TransitionVisualProperties properties) {
        curveFactor = properties.getCurveFactor();
        fontColor = properties.getFontColor();
        strokeColor = properties.getStrokeColor();
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof TransitionVisualProperties) {
           TransitionVisualProperties prop = (TransitionVisualProperties) obj;
           if(prop.getCurveFactor() == getCurveFactor()
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
        hash = 61 * hash + (int) (Double.doubleToLongBits(this.curveFactor) ^ (Double.doubleToLongBits(this.curveFactor) >>> 32));
        hash = 61 * hash + (this.strokeColor != null ? this.strokeColor.hashCode() : 0);
        hash = 61 * hash + (this.fontColor != null ? this.fontColor.hashCode() : 0);
        return hash;
    }



    @Override
    public Object clone() {
        TransitionVisualProperties obj = new TransitionVisualProperties();
        obj.setClickPositionX(getClickPositionX());
        obj.setClickPositionY(getClickPositionY());
        obj.setCurveFactor(getCurveFactor());
        obj.setFontColor(new Color(fontColor.getRGB()));
        obj.setStrokeColor(new Color(strokeColor.getRGB()));
        return obj;
    }



}
