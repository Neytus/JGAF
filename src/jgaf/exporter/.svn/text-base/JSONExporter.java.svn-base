/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.exporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;


/**
 *
 * @author hanis
 */
public class JSONExporter {

    public static void ExportAutomaton(Automaton automaton, File file) throws
            JSONException, UnsupportedEncodingException, FileNotFoundException, IOException {

        JSONObject document = new JSONObject();
        JSONObject automatonObject = new JSONObject();
        automatonObject.put("name", automaton.getName());
        automatonObject.put("description", automaton.getDescription());

        automatonObject.put("initialState", 
                automaton.hasInitialState() ? automaton.getInitialState().getName() : null);

        JSONArray statesArray = new JSONArray();
        for (State state : automaton.getStates()) {
            statesArray.put(createStateJSONObject(state));
        }
        automatonObject.put("states", statesArray);

        JSONArray transitionsArray = new JSONArray();
        for (Transition transition : automaton.getTransitions()) {
            transitionsArray.put(createTransitionJSONObject(transition));
        }
        automatonObject.put("transitions", transitionsArray);

        JSONArray finalStatesArray = new JSONArray();
        for (State state : automaton.getAcceptingStates()) {
            finalStatesArray.put(state.getName());
        }
        automatonObject.put("finalStates", finalStatesArray);
        document.put("automaton", automatonObject);


        Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        out.write(document.toString(1));
        out.close();
    }

    private static JSONObject createStateJSONObject(State state) throws JSONException {
        JSONObject stateObject = new JSONObject();
        stateObject.put("name", state.getName());
        stateObject.put("x", state.getVisualProperties().getXPos());
        stateObject.put("y", state.getVisualProperties().getYPos());
        stateObject.put("color", state.getVisualProperties().getFillColor().getRGB());
        return stateObject;
    }

    private static JSONObject createTransitionJSONObject(Transition transition) throws JSONException {
        JSONObject transitionObject = new JSONObject();
        transitionObject.put("fromState", transition.getFromState().getName());
        transitionObject.put("toState", transition.getToState().getName());
        JSONArray labelsArray = new JSONArray();
        for (String label : transition.getLabels()) {
            labelsArray.put(label);
        }
        transitionObject.put("labels", labelsArray);
        transitionObject.put("curveFactor", transition.getVisualProperties().getCurveFactor());
        return transitionObject;
    }

}
