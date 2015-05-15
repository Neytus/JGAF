/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.procedure;

import java.util.ArrayList;
import java.util.List;
import jgaf.Representation;

/**
 *
 * @author hanis
 */
public class ProcedureDescriptor {
    public static final String ANALYSIS = "A";
    public static final String TRANSFORMATION = "T";
    public static final String SIMULATION = "S";


    private String name;
    private String description;
    private String classPath;
    private List<ProcedureParameter> parameters;
    private List<ProcedureRepresentation> inputRepresentations;
    private ProcedureRepresentation outputRepresentation;
    private String type;

    public ProcedureDescriptor() {
        parameters = new ArrayList<ProcedureParameter>();
        inputRepresentations = new ArrayList<ProcedureRepresentation>();
    }


    public int getInputRepresentationsCardinality() {
        return inputRepresentations.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProcedureParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<ProcedureParameter> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(ProcedureParameter parameter) {
        parameters.add(parameter);
    }


    public List<ProcedureRepresentation> getInputRepresentations() {
        return inputRepresentations;
    }

    public void setInputRepresentations(List<ProcedureRepresentation> inputRepresentations) {
        this.inputRepresentations = inputRepresentations;
    }

    public void addInputRepresentation(ProcedureRepresentation representation) {
        inputRepresentations.add(representation);
    }

    public ProcedureRepresentation getOutputRepresentation() {
        return outputRepresentation;
    }

    public void setOutputRepresentation(ProcedureRepresentation outputRepresentation) {
        this.outputRepresentation = outputRepresentation;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public Representation[] getInputRepresentationArray() {
        Representation[] inputRepresentationArray = new Representation[getInputRepresentations().size()];
        int i = 0;
        for (ProcedureRepresentation representation : getInputRepresentations()) {
            inputRepresentationArray[i++] = representation.getRepresentation();
        }
        return inputRepresentationArray;
    }

    public String[] getParametersArray() {
        String[] parametersArray = new String[getParameters().size()];
        int i = 0;
        for (ProcedureParameter parameter : getParameters()) {
            parametersArray[i++] = parameter.getText();
        }
        return parametersArray;
    }

    public boolean hasOutput() {
        return outputRepresentation != null;
    }


    @Override
    public String toString() {
        return name == null ? "" : name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
