/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.fa;

/**
 *
 * @author hanis
 */
public class StrokeStyle {

    public static final String STROKE_DOTTED = "dotted";
    public static final String STROKE_SOLID = "solid";
    public static final String STROKE_DASHED = "dashed";
    public static final String STROKE_DOT_AND_DASH = "dot-and-dash";


    public static final int DOTTED = 0;
    public static final int SOLID = 1;
    public static final int DASHED = 2;
    public static final int DOT_AND_DASH = 3;


    public static final String[] STYLES = {"solid", "dashed", "dotted", "dot-and-dash"};

    private int type;




    public StrokeStyle() {
        this.type = SOLID;
    }


    public StrokeStyle(String style) {
        this.type = parseStringStroke(style);
    }


    private int parseStringStroke(String strokeStyle) {
        if(strokeStyle.equals(STROKE_SOLID)) {
            return SOLID;
        }
        if(strokeStyle.equals(STROKE_DASHED)) {
            return DASHED;
        }
        if(strokeStyle.equals(STROKE_DOTTED)) {
            return DOTTED;
        }
        if(strokeStyle.equals(STROKE_DOT_AND_DASH)) {
            return DOT_AND_DASH;
        }
        return SOLID;
    }


    public int getType() {
        return type;
    }
    
    public void setType() {
        
    }


    public String getTypeString() {
        return getStrokeStyleString();
    }


    private String getStrokeStyleString() {
        if(type == DASHED) {
            return STROKE_DASHED;
        } else if(type == DOTTED) {
            return STROKE_DOTTED;
        } else if(type == DOT_AND_DASH) {
            return STROKE_DOT_AND_DASH;
        }
        return STROKE_SOLID;
    }



    public float[] getStrokeDash(float width, double zoomfactor) {
        float zoom = (float) zoomfactor;
        switch (type) {
            case SOLID: return new float[]{};
            case DASHED: return new float[]{10*zoom, 10*zoom};
            case DOTTED: return new float[]{width, width};
            case DOT_AND_DASH: return new float[]{10*zoom, 5*zoom, width, 5*zoom};
            default: return new float[]{};
        }
    }


}
