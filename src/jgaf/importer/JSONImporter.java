/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.importer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author hanis
 */
public class JSONImporter {

    public static Automaton ImportAutomaton(File file) throws JSONException, IOException {
        Automaton automaton = new Automaton();        


        JSONObject automatonObject = new JSONObject(getJSONStringFromFile(file)).getJSONObject("automaton");
                
        automaton.setName(automatonObject.getString("name"));
        automaton.setDescription(automatonObject.getString("description"));
        
        JSONArray states = (JSONArray) automatonObject.getJSONArray("states");
        for (int i = 0; i < states.length(); i++) {
            JSONObject stateObject = states.getJSONObject(i);
            State state = new State(stateObject.getString("name"));
            state.getVisualProperties().setCoordinates(stateObject.getInt("x"), stateObject.getInt("y"));
            state.getVisualProperties().setFillColor(new Color(stateObject.getInt("color")));            
            automaton.addState(state);
        }

        JSONArray transitions = (JSONArray) automatonObject.getJSONArray("transitions");
        for (int i = 0; i < transitions.length(); i++) {
            JSONObject transitionObject = transitions.getJSONObject(i);
            State fromState = automaton.getStateByName(transitionObject.getString("fromState"));
            State toState = automaton.getStateByName(transitionObject.getString("toState"));
            Transition transition = new Transition(fromState, toState);
            transition.getVisualProperties().setCurveFactor(transitionObject.getDouble("curveFactor"));
            transition.getVisualProperties().setStrokeColor(new Color(transitionObject.getInt("color")));

            JSONArray labels = (JSONArray) transitionObject.getJSONArray("labels");
            for (int j = 0; j < labels.length(); j++) {
                transition.addLabel(labels.getString(j));
            }

            automaton.addTransition(transition);
        }

        JSONArray finalStates = (JSONArray) automatonObject.getJSONArray("finalStates");
        for (int i = 0; i < finalStates.length(); i++) {
            automaton.addAcceptingState(new State(finalStates.getString(i)));
        }
        String initialState = automatonObject.getString("initialState");
        if(initialState != null) {
            automaton.setInitialState(new State(initialState));
        }
        return automaton;
    }



    private static String getJSONStringFromFile(File file) throws IOException {
        BufferedReader in = in = new BufferedReader(new FileReader(file));
        String inputLine;
        StringBuilder sb = new StringBuilder("");
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine);
        }
        in.close();
        return sb.toString();
    }

    
 }
