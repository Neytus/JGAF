/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.environment;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaf.automaton.fa.StrokeStyle;

/**
 *
 * @author hanis
 */
public class PropertiesHandler {

    private static final String automatonStateDiameterKEY = "automaton.state.diameter";
    private static final String automatonStateStrokeColorKEY = "automaton.state.color.stroke";
    private static final String automatonStateFillColorKEY = "automaton.state.color.fill";
    private static final String automatonStateFontColorKEY = "automaton.state.color.font";
    private static final String automatonStateStrokeWidthKEY = "automaton.state.stroke.width";
    private static final String automatonStateStrokeStyleKEY = "automaton.state.stroke.style";
    private static final String automatonStateFontNameKEY = "automaton.state.font.name";
    private static final String automatonStateFontStyleKEY = "automaton.state.font.style";
    private static final String automatonStateFontSizeKEY = "automaton.state.font.size";
    private static final String automatonStateMouseOverFillColorKEY = "automaton.state.mo.color.fill";
    private static final String automatonStateMouseOverFontColorKEY = "automaton.state.mo.color.font";
    private static final String automatonStateMouseOverStrokeColorKEY = "automaton.state.mo.color.stroke";
    private static final String automatonStateSelectedFillColorKEY = "automaton.state.selected.color.fill";
    private static final String automatonStateSelectedFontColorKEY = "automaton.state.selected.color.font";
    private static final String automatonStateSelectedStrokeColorKEY = "automaton.state.selected.color.stroke";


    private static final String automatonInitArrowColorKEY = "automaton.initarrow.color";
    private static final String automatonInitArrowColorSelectedKEY = "automaton.initarrow.color.selected";
    private static final String automatonInitArrowColorMouseOverKEY = "automaton.initarrow.color.mouseover";
    private static final String automatonInitArrowStrokeWidthKEY = "automaton.initarrow.stroke.width";
    private static final String automatonInitArrowStrokeStyleKEY = "automaton.initarrow.stroke.style";
    private static final String automatonInitArrowOrientationKEY = "automaton.initarrow.orientation";
    private static final String automatonInitArrowLengthKEY = "automaton.initarrow.length";






    private static final String automatonTransitionFontColorKEY = "automaton.transition.font.color";
    private static final String automatonTransitionFontNameKEY = "automaton.transition.font.name";
    private static final String automatonTransitionFontStyleKEY = "automaton.transition.font.style";
    private static final String automatonTransitionFontSizeKEY = "automaton.transition.font.size";
    private static final String automatonTransitionBackColorKEY = "automaton.transition.back.color";
    private static final String automatonTransitionStrokeColorKEY = "automaton.transition.stroke.color";
    private static final String automatonTransitionStrokeStyleKEY = "automaton.transition.stroke.style";
    private static final String automatonTransitionStrokeWidthKEY = "automaton.transition.stroke.width";
    private static final String automatonTransitionMouseOverBackColorKEY = "automaton.transition.mo.color.back";
    private static final String automatonTransitionMouseOverFontColorKEY = "automaton.transition.mo.color.font";
    private static final String automatonTransitionMouseOverStrokeColorKEY = "automaton.transition.mo.color.stroke";
    private static final String automatonTransitionSelectedBackColorKEY = "automaton.transition.selected.color.back";
    private static final String automatonTransitionSelectedFontColorKEY = "automaton.transition.selected.color.font";
    private static final String automatonTransitionSelectedStrokeColorKEY = "automaton.transition.selected.color.stroke";



    private static final String automatonLabelFrontColorKEY = "automaton.label.font.color";
    private static final String automatonLabelFontNameKEY = "automaton.label.font.name";
    private static final String automatonLabelFontStyleKEY = "automaton.label.font.style";
    private static final String automatonLabelFontSizeKEY = "automaton.label.font.size";
    
    
    private static final String automatonLabelBackColorKEY = "automaton.label.background.color";
    private static String automatonLabelBackEnabledKEY = "automaton.label.background.enabled";
    private static String automatonLabelBackTransparencyKEY = "automaton.label.background.transparency";

    
    private static final String automatonLabelSelectedFrontColorKEY = "automaton.label.selected.color.front";
    private static final String automatonLabelSelectedBackColorKEY = "automaton.label.selected.color.back";
    private static final String automatonLabelMouseOverFrontColorKEY = "automaton.label.mouseover.color.front";
    private static final String automatonLabelMouseOverBackColorKEY = "automaton.label.mouseover.color.back";


    private static final String automatonCanvasBackgroundKEY = "automaton.canvas.background";
    private static final String automatonCanvasAntialiasingShapeKEY = "automaton.canvas.antialiasing.shape";
    private static final String automatonCanvasAntialiasingTextKEY = "automaton.canvas.antialiasing.text";

    private static final String recentFilesKEY = "file.recentfiles.files";
    private static final String recentFilesLimitKEY = "file.recentfiles.limit";
    private static final String fileLastPathKEY = "file.lastpath";

    



    
   // public static String PDA_PROPERTIES_PATH = "config/applicationDesign.xml";

    private Properties properties;


    private static PropertiesHandler INSTANCE = null;;//new PropertiesHandler();

    private static final String ARRAY_DELIMITER = "::";




    private int automatonStateDiameter; //automaton.state.diameter
    private Color automatonStateStrokeColor; //automaton.state.color.stroke
    private Color automatonStateFillColor; //automaton.state.color.fill
    private Color automatonStateFontColor; //automaton.state.color.font
    private StrokeStyle automatonStateStrokeStyle;//automaton.statetroke.style
    private double automatonStateStrokeWidth;//automaton.statetroke.style
    private Font automatonStateFont;
    private Color automatonStateMouseOverFontColor;
    private Color automatonStateMouseOverFillColor;
    private Color automatonStateMouseOverStrokeColor;
    private Color automatonStateSelectedFontColor;
    private Color automatonStateSelectedFillColor;
    private Color automatonStateSelectedStrokeColor;



    private Color automatonInitArrowColor;
    private Color automatonInitArrowColorSelected;
    private Color automatonInitArrowColorMouseOver;
    private StrokeStyle automatonInitArrowStrokeStyle;
    private double automatonInitArrowStrokeWidth;
    private double automatonInitArrowOrientation;
    private int automatonInitArrowLength;




    private Color automatonLabelFrontColor; 
    private Color automatonLabelBackColor; 
    private double automatonLabelBackTransparency;
    private boolean automatonLabelBackEnabled;
    private Color automatonLabelSelectedFrontColor;
    private Color automatonLabelSelectedBackColor; 
    private Color automatonLabelMouseOverFrontColor; 
    private Color automatonLabelMouseOverBackColor; 
    private Font automatonLabelFont;

   
    private Color automatonTransitionFontColor; 
    private Color automatonTransitionStrokeColor;
    private Color automatonTransitionBackColor;
    private double automatonTransitionStrokeWidth;
    private StrokeStyle automatonTransitionStrokeStyle;
    private Font automatonTransitionFont;
    private Color automatonTransitionMouseOverFontColor;
    private Color automatonTransitionMouseOverBackColor;
    private Color automatonTransitionMouseOverStrokeColor;
    private Color automatonTransitionSelectedFontColor;
    private Color automatonTransitionSelectedBackColor;
    private Color automatonTransitionSelectedStrokeColor;











    private Color automatonCanvasBackground;
    private boolean automatonCanvasAntialiasingShape;
    private boolean automatonCanvasAntialiasingText;


    
    private Stack<File> recentFiles; //file.recentfiles.files
    private int recentFilesLimit; //file.recentfiles.limit

    private String fileLastPath; //file.lastpath
   

    
    
   // private String registerPath  = projectPath + File.separator + "register";





    private PropertiesHandler() {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(Environment.getInstance().getPropertiesPath()));
            getAllProperties();
        } catch (IOException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
   //     } catch (URISyntaxException ex) {
   //         Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }






    public void restoreProperties() {
        getAllProperties();
    }




    public static PropertiesHandler getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PropertiesHandler();
        }
        return INSTANCE;
    }


    public void storeProperties() {
        try {
            setAllProperties();
            properties.store(new FileOutputStream(Environment.getInstance().getPropertiesPath()), "updated: " + Calendar.getInstance().getTime());
        } catch (IOException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }







    private void getAllProperties() {
        
        automatonStateDiameter = Integer.valueOf(properties.getProperty(automatonStateDiameterKEY));
        automatonStateFillColor = new Color(Integer.valueOf(properties.getProperty(automatonStateFillColorKEY)));
        automatonStateFontColor = new Color(Integer.valueOf(properties.getProperty(automatonStateFontColorKEY)));
        automatonStateStrokeColor = new Color(Integer.valueOf(properties.getProperty(automatonStateStrokeColorKEY)));
        automatonStateStrokeStyle = new StrokeStyle(properties.getProperty(automatonStateStrokeStyleKEY));
        automatonStateStrokeWidth = Double.valueOf(properties.getProperty(automatonStateStrokeWidthKEY));
        automatonStateFont = createFont(properties.getProperty(automatonStateFontNameKEY), properties.getProperty(automatonStateFontStyleKEY), properties.getProperty(automatonStateFontSizeKEY));
        automatonStateMouseOverFontColor = new Color(Integer.valueOf(properties.getProperty(automatonStateMouseOverFontColorKEY)));
        automatonStateMouseOverFillColor = new Color(Integer.valueOf(properties.getProperty(automatonStateMouseOverFillColorKEY)));
        automatonStateMouseOverStrokeColor = new Color(Integer.valueOf(properties.getProperty(automatonStateMouseOverStrokeColorKEY)));
        automatonStateSelectedFontColor = new Color(Integer.valueOf(properties.getProperty(automatonStateSelectedFontColorKEY)));
        automatonStateSelectedFillColor = new Color(Integer.valueOf(properties.getProperty(automatonStateSelectedFillColorKEY)));
        automatonStateSelectedStrokeColor = new Color(Integer.valueOf(properties.getProperty(automatonStateSelectedStrokeColorKEY)));


        automatonInitArrowColor = new Color(Integer.valueOf(properties.getProperty(automatonInitArrowColorKEY)));
        automatonInitArrowColorMouseOver = new Color(Integer.valueOf(properties.getProperty(automatonInitArrowColorMouseOverKEY)));
        automatonInitArrowColorSelected = new Color(Integer.valueOf(properties.getProperty(automatonInitArrowColorSelectedKEY)));
        automatonInitArrowStrokeStyle = new StrokeStyle(properties.getProperty(automatonInitArrowStrokeStyleKEY));
        automatonInitArrowLength = Integer.valueOf(properties.getProperty(automatonInitArrowLengthKEY));
        automatonInitArrowOrientation = Double.valueOf(properties.getProperty(automatonInitArrowOrientationKEY));
        automatonInitArrowStrokeWidth = Double.valueOf(properties.getProperty(automatonInitArrowStrokeWidthKEY));


        automatonTransitionFontColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionFontColorKEY)));
        automatonTransitionStrokeColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionStrokeColorKEY)));
        automatonTransitionStrokeStyle = new StrokeStyle(properties.getProperty(automatonTransitionStrokeStyleKEY));
        automatonTransitionStrokeWidth = Double.valueOf(properties.getProperty(automatonTransitionStrokeWidthKEY));        
        automatonTransitionFont = createFont(properties.getProperty(automatonTransitionFontNameKEY), properties.getProperty(automatonTransitionFontStyleKEY), properties.getProperty(automatonTransitionFontSizeKEY));
        automatonTransitionBackColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionBackColorKEY)));
        automatonTransitionMouseOverFontColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionMouseOverFontColorKEY)));
        automatonTransitionMouseOverBackColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionMouseOverBackColorKEY)));
        automatonTransitionMouseOverStrokeColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionMouseOverStrokeColorKEY)));
        automatonTransitionSelectedFontColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionSelectedFontColorKEY)));
        automatonTransitionSelectedBackColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionSelectedBackColorKEY)));
        automatonTransitionSelectedStrokeColor = new Color(Integer.valueOf(properties.getProperty(automatonTransitionSelectedStrokeColorKEY)));



        automatonLabelFrontColor = new Color(Integer.valueOf(properties.getProperty(automatonLabelFrontColorKEY)));
        automatonLabelBackColor = new Color(Integer.valueOf(properties.getProperty(automatonLabelBackColorKEY)));
        automatonLabelBackTransparency = Double.valueOf(properties.getProperty(automatonLabelBackTransparencyKEY));
        automatonLabelBackEnabled = Boolean.valueOf(properties.getProperty(automatonLabelBackEnabledKEY));
        automatonLabelSelectedBackColor = new Color(Integer.valueOf(properties.getProperty(automatonLabelSelectedBackColorKEY)));
        automatonLabelSelectedFrontColor = new Color(Integer.valueOf(properties.getProperty(automatonLabelSelectedFrontColorKEY)));
        automatonLabelMouseOverFrontColor = new Color(Integer.valueOf(properties.getProperty(automatonLabelMouseOverFrontColorKEY)));
        automatonLabelMouseOverBackColor = new Color(Integer.valueOf(properties.getProperty(automatonLabelMouseOverBackColorKEY)));
        automatonLabelFont = createFont(properties.getProperty(automatonLabelFontNameKEY), properties.getProperty(automatonLabelFontStyleKEY), properties.getProperty(automatonLabelFontSizeKEY));


        automatonCanvasBackground = new Color(Integer.valueOf(properties.getProperty(automatonCanvasBackgroundKEY)));
        automatonCanvasAntialiasingShape = Boolean.valueOf(properties.getProperty(automatonCanvasAntialiasingShapeKEY));
        automatonCanvasAntialiasingText = Boolean.valueOf(properties.getProperty(automatonCanvasAntialiasingTextKEY));
               
        fileLastPath = properties.getProperty(fileLastPathKEY);
        recentFiles = parseRecentFiles(properties.getProperty(recentFilesKEY));
        recentFilesLimit = Integer.valueOf(properties.getProperty(recentFilesLimitKEY));

    }


    private void setAllProperties() {
        properties.setProperty(automatonStateDiameterKEY, String.valueOf(automatonStateDiameter));
        properties.setProperty(automatonStateFillColorKEY, String.valueOf(automatonStateFillColor.getRGB()));
        properties.setProperty(automatonStateStrokeColorKEY, String.valueOf(automatonStateStrokeColor.getRGB()));
        properties.setProperty(automatonStateFontColorKEY, String.valueOf(automatonStateFontColor.getRGB()));
        properties.setProperty(automatonStateStrokeStyleKEY, automatonStateStrokeStyle.getTypeString());
        properties.setProperty(automatonStateStrokeWidthKEY, String.valueOf(automatonStateStrokeWidth));
        properties.setProperty(automatonStateFontNameKEY, getAutomatonStateFont().getName());
        properties.setProperty(automatonStateFontStyleKEY, String.valueOf(getAutomatonStateFont().getStyle()));
        properties.setProperty(automatonStateFontSizeKEY, String.valueOf(getAutomatonStateFont().getSize()));
        properties.setProperty(automatonStateMouseOverFontColorKEY, String.valueOf(getAutomatonStateMouseOverFontColor().getRGB()));
        properties.setProperty(automatonStateMouseOverFillColorKEY, String.valueOf(getAutomatonStateMouseOverFillColor().getRGB()));
        properties.setProperty(automatonStateMouseOverStrokeColorKEY, String.valueOf(getAutomatonStateMouseOverStrokeColor().getRGB()));
        properties.setProperty(automatonStateSelectedFontColorKEY, String.valueOf(getAutomatonStateSelectedFontColor().getRGB()));
        properties.setProperty(automatonStateSelectedFillColorKEY, String.valueOf(getAutomatonStateSelectedFillColor().getRGB()));
        properties.setProperty(automatonStateSelectedStrokeColorKEY, String.valueOf(getAutomatonStateSelectedStrokeColor().getRGB()));


        
        properties.setProperty(automatonInitArrowColorKEY, String.valueOf(automatonInitArrowColor.getRGB()));
        properties.setProperty(automatonInitArrowColorMouseOverKEY, String.valueOf(automatonInitArrowColorMouseOver.getRGB()));
        properties.setProperty(automatonInitArrowColorSelectedKEY, String.valueOf(automatonInitArrowColorSelected.getRGB()));
        properties.setProperty(automatonInitArrowStrokeStyleKEY, automatonInitArrowStrokeStyle.getTypeString());
        properties.setProperty(automatonInitArrowLengthKEY, String.valueOf(automatonInitArrowLength));
        properties.setProperty(automatonInitArrowOrientationKEY, String.valueOf(automatonInitArrowOrientation));
        properties.setProperty(automatonInitArrowStrokeWidthKEY, String.valueOf(automatonInitArrowStrokeWidth));


        properties.setProperty(automatonTransitionFontColorKEY, String.valueOf(automatonTransitionFontColor.getRGB()));
        properties.setProperty(automatonTransitionStrokeColorKEY, String.valueOf(automatonTransitionStrokeColor.getRGB()));
        properties.setProperty(automatonTransitionStrokeStyleKEY, automatonTransitionStrokeStyle.getTypeString());
        properties.setProperty(automatonTransitionStrokeWidthKEY, String.valueOf(automatonTransitionStrokeWidth));
        properties.setProperty(automatonTransitionBackColorKEY, String.valueOf(automatonTransitionBackColor.getRGB()));
        properties.setProperty(automatonTransitionFontNameKEY, getAutomatonTransitionFont().getName());
        properties.setProperty(automatonTransitionFontStyleKEY, String.valueOf(getAutomatonTransitionFont().getStyle()));
        properties.setProperty(automatonTransitionFontSizeKEY, String.valueOf(getAutomatonTransitionFont().getSize()));
        properties.setProperty(automatonTransitionMouseOverFontColorKEY, String.valueOf(getAutomatonTransitionMouseOverFontColor().getRGB()));
        properties.setProperty(automatonTransitionMouseOverBackColorKEY, String.valueOf(getAutomatonTransitionMouseOverBackColor().getRGB()));
        properties.setProperty(automatonTransitionMouseOverStrokeColorKEY, String.valueOf(getAutomatonTransitionMouseOverStrokeColor().getRGB()));
        properties.setProperty(automatonTransitionSelectedFontColorKEY, String.valueOf(getAutomatonTransitionSelectedFontColor().getRGB()));
        properties.setProperty(automatonTransitionSelectedBackColorKEY, String.valueOf(getAutomatonTransitionSelectedBackColor().getRGB()));
        properties.setProperty(automatonTransitionSelectedStrokeColorKEY, String.valueOf(getAutomatonTransitionSelectedStrokeColor().getRGB()));



        properties.setProperty(automatonLabelFrontColorKEY, String.valueOf(automatonLabelFrontColor.getRGB()));
        properties.setProperty(automatonLabelBackColorKEY, String.valueOf(automatonLabelBackColor.getRGB()));
        properties.setProperty(automatonLabelBackTransparencyKEY, String.valueOf(automatonLabelBackTransparency));
        properties.setProperty(automatonLabelBackEnabledKEY, String.valueOf(automatonLabelBackEnabled));
        properties.setProperty(automatonLabelSelectedFrontColorKEY, String.valueOf(automatonLabelSelectedFrontColor.getRGB()));
        properties.setProperty(automatonLabelSelectedBackColorKEY, String.valueOf(automatonLabelSelectedBackColor.getRGB()));
        properties.setProperty(automatonLabelMouseOverFrontColorKEY, String.valueOf(automatonLabelMouseOverFrontColor.getRGB()));
        properties.setProperty(automatonLabelMouseOverBackColorKEY, String.valueOf(automatonLabelMouseOverBackColor.getRGB()));
        properties.setProperty(automatonLabelFontNameKEY, getAutomatonLabelFont().getName());
        properties.setProperty(automatonLabelFontStyleKEY, String.valueOf(getAutomatonLabelFont().getStyle()));
        properties.setProperty(automatonLabelFontSizeKEY, String.valueOf(getAutomatonLabelFont().getSize()));






        properties.setProperty(fileLastPathKEY, fileLastPath);
        properties.setProperty(recentFilesLimitKEY, String.valueOf(recentFilesLimit));
        properties.setProperty(recentFilesKEY, convertStackToColonArray(recentFiles));

        
        properties.setProperty(automatonCanvasBackgroundKEY, String.valueOf(automatonCanvasBackground.getRGB()));
        properties.setProperty(automatonCanvasAntialiasingShapeKEY, String.valueOf(automatonCanvasAntialiasingShape));
        properties.setProperty(automatonCanvasAntialiasingTextKEY, String.valueOf(automatonCanvasAntialiasingText));



    }


//    private StrokeStyle2 parseStrokeStyle(String strokeStyle) {
//        if(strokeStyle.equals(STROKE_SOLID)) {
//            return StrokeStyle2.SOLID;
//        }
//        if(strokeStyle.equals(STROKE_DASHED)) {
//            return StrokeStyle2.DASHED;
//        }
//        if(strokeStyle.equals(STROKE_DOTTED)) {
//            return StrokeStyle2.DOTTED;
//        }
//        if(strokeStyle.equals(STROKE_DOT_AND_DASH)) {
//            return StrokeStyle2.DOT_AND_DASH;
//        }
//        return StrokeStyle2.SOLID;
//    }
//
//
//
//    public String getStrokeStyleString(StrokeStyle2 strokeStyle) {
//        if(automatonTransitionStrokeStyle == StrokeStyle2.DASHED) {
//            return STROKE_DASHED;
//        } else if(automatonTransitionStrokeStyle == StrokeStyle2.DOTTED) {
//            return STROKE_DOTTED;
//        } else if(automatonTransitionStrokeStyle == StrokeStyle2.DOT_AND_DASH) {
//            return STROKE_DOT_AND_DASH;
//        }
//        return STROKE_SOLID;
//    }



    private Font createFont(String name, String styleSting, String sizeSting) {
        int style = Integer.valueOf(styleSting);
        int size = Integer.valueOf(sizeSting);
        return new Font(name, style, size);
    }




    private Stack parseRecentFiles(String value) {
        Stack stack = new Stack<File>();
        if("".equals(value)) {
            return stack;
        }
        String[] array = value.split(ARRAY_DELIMITER);
        for (int i = array.length - 1; i >= 0; i--) {
            String file = array[i];
            stack.push(new File(file));
        }
        return stack;
    }

    private String convertStackToColonArray(Stack<File> stack) {
        StringBuilder sb = new StringBuilder("");
        ListIterator<File> it = stack.listIterator(stack.size());
        while(it.hasPrevious()) {
            sb.append(it.previous().getAbsolutePath());
            if(it.hasPrevious()) {
                sb.append(ARRAY_DELIMITER);
            }
        }
        return sb.toString();
    }

    public Color getAutomatonStateFillColor() {
        return automatonStateFillColor;
    }


    public void setAutomatonStateFillColor(Color automatonStateFillColor) {
        this.automatonStateFillColor = automatonStateFillColor;
    }

    public Color getAutomatonStateStrokeColor() {
        return automatonStateStrokeColor;
    }

    public void setAutomatonStateStrokeColor(Color automatonStateStrokeColor) {
        this.automatonStateStrokeColor = automatonStateStrokeColor;
    }

    public Color getAutomatonStateFontColor() {
        return automatonStateFontColor;
    }

    public void setAutomatonStateFontColor(Color automatonStateFontColor) {
        this.automatonStateFontColor = automatonStateFontColor;
    }

    public Color getAutomatonTransitionFontColor() {
        return automatonTransitionFontColor;
    }


    public void setAutomatonTransitionFontColor(Color automatonTransitionFontColor) {
        this.automatonTransitionFontColor = automatonTransitionFontColor;
    }

    public Color getAutomatonTransitionStrokeColor() {
        return automatonTransitionStrokeColor;
    }

    public void setAutomatonTransitionStrokeColor(Color automatonTransitionStrokeColor) {
        this.automatonTransitionStrokeColor = automatonTransitionStrokeColor;
    }

    public int getAutomatonStateDiameter() {
        return automatonStateDiameter;
    }

    public void setAutomatonStateDiameter(int automatonStateDiameter) {
        this.automatonStateDiameter = automatonStateDiameter;
    }


    public String getFileLastPath() {
        return fileLastPath;
    }

    public void setFileLastPath(String fileLastPath) {
        this.fileLastPath = fileLastPath;
        storeProperties();
    }

    public int getRecentFilesLimit() {
        return recentFilesLimit;
    }

    public void setRecentFilesLimit(int recentFilesLimit) {
        this.recentFilesLimit = recentFilesLimit;        
    }

    public Stack<File> getRecentFiles() {
        return recentFiles;
    }

    public File[] getRecentFilesArrayList() {
        return recentFiles.toArray(new File[recentFiles.size()]);
    }

//    public void setRecentFiles(Stack recentFiles) {
//        this.recentFiles = recentFiles;
//        properties.setProperty("file.recentfiles.files", String.valueOf(recentFiles));
//    }


    public void addRecentFile(File file) {        
        if(recentFiles.contains(file)) {
            recentFiles.remove(file);
        }
        recentFiles.push(file);
        if(recentFiles.size() > getRecentFilesLimit()) {
            recentFiles.remove(0);
        }
        storeProperties();
    }


    public Color getAutomatonLabelFrontColor() {
        return automatonLabelFrontColor;
    }


    public void setAutomatonLabelFrontColor(Color automatonLabelFrontColor) {
        this.automatonLabelFrontColor = automatonLabelFrontColor;        
    }

    public Color getAutomatonLabelBackColor() {
        return automatonLabelBackColor;
    }

    public void setAutomatonLabelBackColor(Color automatonLabelBackColor) {
        this.automatonLabelBackColor = automatonLabelBackColor;
    }

    public Color getAutomatonLabelSelectedFrontColor() {
        return automatonLabelSelectedFrontColor;
    }

    public void setAutomatonLabelSelectedFrontColor(Color automatonLabelSelectedFrontColor) {
        this.automatonLabelSelectedFrontColor = automatonLabelSelectedFrontColor;
    }

    public Color getAutomatonLabelSelectedBackColor() {
        return automatonLabelSelectedBackColor;
    }

    public void setAutomatonLabelSelectedBackColor(Color automatonLabelSelectedBackColor) {
        this.automatonLabelSelectedBackColor = automatonLabelSelectedBackColor;
    }

    public Color getAutomatonLabelMouseOverFrontColor() {
        return automatonLabelMouseOverFrontColor;
    }

    public void setAutomatonLabelMouseOverFrontColor(Color automatonLabelMouseOverFrontColor) {
        this.automatonLabelMouseOverFrontColor = automatonLabelMouseOverFrontColor;
    }

    public Color getAutomatonLabelMouseOverBackColor() {
        return automatonLabelMouseOverBackColor;
    }

    public void setAutomatonLabelMouseOverBackColor(Color automatonLabelMouseOverBackColor) {
        this.automatonLabelMouseOverBackColor = automatonLabelMouseOverBackColor;
    }

    public void setAutomatonCanvasBackground(Color automatonCanvasBackground) {
        this.automatonCanvasBackground = automatonCanvasBackground;
    }

    public Color getAutomatonCanvasBackground() {
        return automatonCanvasBackground;
    }   


    public void setAutomatonCanvasAntialiasingShape(boolean automatonEditorAntialiasingShape) {
        this.automatonCanvasAntialiasingShape = automatonEditorAntialiasingShape;
    }

    public boolean isAutomatonCanvasAntialiasingShape() {
        return automatonCanvasAntialiasingShape;
    }


    public void setAutomatonTransitionStrokeStyle(StrokeStyle automatonTransitionStrokeStyle) {
        this.automatonTransitionStrokeStyle = automatonTransitionStrokeStyle;
    }

    public StrokeStyle getAutomatonTransitionStrokeStyle() {
        return automatonTransitionStrokeStyle;
    }

    public void setAutomatonStateStrokeStyle(StrokeStyle automatonStateStrokeStyle) {
        this.automatonStateStrokeStyle = automatonStateStrokeStyle;
    }

    public StrokeStyle getAutomatonStateStrokeStyle() {
        return automatonStateStrokeStyle;
    }


    public void setAutomatonTransitionStrokeWidth(double automatonTransitionStrokeWidth) {
        this.automatonTransitionStrokeWidth = automatonTransitionStrokeWidth;
    }

    public double getAutomatonTransitionStrokeWidth() {
        return automatonTransitionStrokeWidth;
    }

    public double getAutomatonStateStrokeWidth() {
        return automatonStateStrokeWidth;
    }

    public void setAutomatonStateStrokeWidth(double automatonStateStrokeWidth) {
        this.automatonStateStrokeWidth = automatonStateStrokeWidth;
    }

    public Font getAutomatonStateFont() {
        return automatonStateFont;
    }

    public void setAutomatonStateFont(Font automatonStateFont) {
        this.automatonStateFont = automatonStateFont;
    }

    public Color getAutomatonStateMouseOverFontColor() {
        return automatonStateMouseOverFontColor;
    }

    public void setAutomatonStateMouseOverFontColor(Color automatonStateMouseOverFontColor) {
        this.automatonStateMouseOverFontColor = automatonStateMouseOverFontColor;
    }

    public Color getAutomatonStateMouseOverFillColor() {
        return automatonStateMouseOverFillColor;
    }

    public void setAutomatonStateMouseOverFillColor(Color automatonStateMouseOverFillColor) {
        this.automatonStateMouseOverFillColor = automatonStateMouseOverFillColor;
    }

    public Color getAutomatonStateMouseOverStrokeColor() {
        return automatonStateMouseOverStrokeColor;
    }

    public void setAutomatonStateMouseOverStrokeColor(Color automatonStateMouseOverStrokeColor) {
        this.automatonStateMouseOverStrokeColor = automatonStateMouseOverStrokeColor;
    }

    public Color getAutomatonStateSelectedFontColor() {
        return automatonStateSelectedFontColor;
    }

    public void setAutomatonStateSelectedFontColor(Color automatonStateSelectedFontColor) {
        this.automatonStateSelectedFontColor = automatonStateSelectedFontColor;
    }

    public Color getAutomatonStateSelectedFillColor() {
        return automatonStateSelectedFillColor;
    }

    public void setAutomatonStateSelectedFillColor(Color automatonStateSelectedFillColor) {
        this.automatonStateSelectedFillColor = automatonStateSelectedFillColor;
    }

    public Color getAutomatonStateSelectedStrokeColor() {
        return automatonStateSelectedStrokeColor;
    }

    public void setAutomatonStateSelectedStrokeColor(Color automatonStateSelectedStrokeColor) {
        this.automatonStateSelectedStrokeColor = automatonStateSelectedStrokeColor;
    }

    public Color getAutomatonInitArrowColor() {
        return automatonInitArrowColor;
    }

    public void setAutomatonInitArrowColor(Color automatonInitArrowColor) {
        this.automatonInitArrowColor = automatonInitArrowColor;
    }

    public Color getAutomatonInitArrowColorSelected() {
        return automatonInitArrowColorSelected;
    }

    public void setAutomatonInitArrowColorSelected(Color automatonInitArrowColorSelected) {
        this.automatonInitArrowColorSelected = automatonInitArrowColorSelected;
    }

    public Color getAutomatonInitArrowColorMouseOver() {
        return automatonInitArrowColorMouseOver;
    }

    public void setAutomatonInitArrowColorMouseOver(Color automatonInitArrowColorMouseOver) {
        this.automatonInitArrowColorMouseOver = automatonInitArrowColorMouseOver;
    }

    public StrokeStyle getAutomatonInitArrowStrokeStyle() {
        return automatonInitArrowStrokeStyle;
    }

    public void setAutomatonInitArrowStrokeStyle(StrokeStyle automatonInitArrowStrokeStyle) {
        this.automatonInitArrowStrokeStyle = automatonInitArrowStrokeStyle;
    }

    public double getAutomatonInitArrowStrokeWidth() {
        return automatonInitArrowStrokeWidth;
    }

    public void setAutomatonInitArrowStrokeWidth(double automatonInitArrowStrokeWidth) {
        this.automatonInitArrowStrokeWidth = automatonInitArrowStrokeWidth;
    }

    public double getAutomatonInitArrowOrientation() {
        return automatonInitArrowOrientation;
    }

    public void setAutomatonInitArrowOrientation(double automatonInitArrowOrientation) {
        this.automatonInitArrowOrientation = automatonInitArrowOrientation;
    }

    public int getAutomatonInitArrowLength() {
        return automatonInitArrowLength;
    }

    public void setAutomatonInitArrowLength(int automatonInitArrowLength) {
        this.automatonInitArrowLength = automatonInitArrowLength;
    }


    public Color getAutomatonTransitionBackColor() {
        return automatonTransitionBackColor;
    }


    public void setAutomatonTransitionBackColor(Color automatonTransitionBackColor) {
        this.automatonTransitionBackColor = automatonTransitionBackColor;
    }


    public Font getAutomatonTransitionFont() {
        return automatonTransitionFont;
    }


    public void setAutomatonTransitionFont(Font automatonTransitionFont) {
        this.automatonTransitionFont = automatonTransitionFont;
    }


    public Color getAutomatonTransitionMouseOverFontColor() {
        return automatonTransitionMouseOverFontColor;
    }


    public void setAutomatonTransitionMouseOverFontColor(Color automatonTransitionMouseOverFontColor) {
        this.automatonTransitionMouseOverFontColor = automatonTransitionMouseOverFontColor;
    }


    public Color getAutomatonTransitionMouseOverBackColor() {
        return automatonTransitionMouseOverBackColor;
    }


    public void setAutomatonTransitionMouseOverBackColor(Color automatonTransitionMouseOverFillColor) {
        this.automatonTransitionMouseOverBackColor = automatonTransitionMouseOverFillColor;
    }


    public Color getAutomatonTransitionMouseOverStrokeColor() {
        return automatonTransitionMouseOverStrokeColor;
    }


    public void setAutomatonTransitionMouseOverStrokeColor(Color automatonTransitionMouseOverStrokeColor) {
        this.automatonTransitionMouseOverStrokeColor = automatonTransitionMouseOverStrokeColor;
    }


    public Color getAutomatonTransitionSelectedFontColor() {
        return automatonTransitionSelectedFontColor;
    }


    public void setAutomatonTransitionSelectedFontColor(Color automatonTransitionSelectedFontColor) {
        this.automatonTransitionSelectedFontColor = automatonTransitionSelectedFontColor;
    }


    public Color getAutomatonTransitionSelectedBackColor() {
        return automatonTransitionSelectedBackColor;
    }


    public void setAutomatonTransitionSelectedBackColor(Color automatonTransitionSelectedFillColor) {
        this.automatonTransitionSelectedBackColor = automatonTransitionSelectedFillColor;
    }


    public Color getAutomatonTransitionSelectedStrokeColor() {
        return automatonTransitionSelectedStrokeColor;
    }


    public void setAutomatonTransitionSelectedStrokeColor(Color automatonTransitionSelectedStrokeColor) {
        this.automatonTransitionSelectedStrokeColor = automatonTransitionSelectedStrokeColor;
    }

    public Font getAutomatonLabelFont() {
        return automatonLabelFont;
    }

    public void setAutomatonLabelFont(Font automatonLabelFont) {
        this.automatonLabelFont = automatonLabelFont;
    }

    public double getAutomatonLabelBackTransparency() {
        return automatonLabelBackTransparency;
    }

    public void setAutomatonLabelBackTransparency(double automatonLabelBackTransparency) {
        this.automatonLabelBackTransparency = automatonLabelBackTransparency;
    }

    public boolean isAutomatonLabelBackEnabled() {
        return automatonLabelBackEnabled;
    }

    public void setAutomatonLabelBackEnabled(boolean automatonLabelBackEnabled) {
        this.automatonLabelBackEnabled = automatonLabelBackEnabled;
    }

    public boolean isAutomatonCanvasAntialiasingText() {
        return automatonCanvasAntialiasingText;
    }

    public void setAutomatonCanvasAntialiasingText(boolean automatonCanvasAntialiasingText) {
        this.automatonCanvasAntialiasingText = automatonCanvasAntialiasingText;
    }












}


