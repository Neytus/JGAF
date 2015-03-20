/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

/**
 *
 * @author hanis
 */
public class ProcedureParameter {

    private String description;
    private String text;

    //added with lr extension
    private List<String> forcedOptions;
    private boolean hasForcedOptions;
    
    public ProcedureParameter() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
    //added with lr extension
    public boolean hasForcedOptions(){
        return hasForcedOptions;
    }
    
    //added with lr extension
    public void setForcedOptions( List<String> forcedOptions){
        hasForcedOptions=true;
        this.forcedOptions=forcedOptions;
    }
    
    //added with lr extension
    public List<String> getForcedOptions(){
    return forcedOptions;
    }
}
