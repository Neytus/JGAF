/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.Transition;

/**
 *
 * @author hanis
 */
public class FileImporter {

    private static final String FILE_EXTENSION = "jgaf";

    public static Automaton getAutomaton(String filePath) {
        return getAutomaton(new File(filePath));
    }

    public static Automaton getAutomaton(File file) {

        Automaton automaton = new Automaton();
        BufferedReader in = null;
        try {
            StringTokenizer tokenizer = null;
            in = new BufferedReader(new FileReader(file));
            automaton.setName(in.readLine());

            tokenizer = new StringTokenizer(in.readLine(), ",");
            while (tokenizer.hasMoreTokens()) {
                automaton.addState(new State(tokenizer.nextToken()));
            }

            automaton.setInitialState(automaton.getStateByName(in.readLine()));

            tokenizer = new StringTokenizer(in.readLine(), ",");
            while (tokenizer.hasMoreTokens()) {
                automaton.addAcceptingState(automaton.getStateByName(tokenizer.nextToken()));
            }


            String line;
            while ((line = in.readLine()) != null) {
                tokenizer = new StringTokenizer(line, ",");
                State from = automaton.getStateByName(tokenizer.nextToken());
                State to = automaton.getStateByName(tokenizer.nextToken());
                automaton.addState(from);
                automaton.addState(to);
                Transition transition = new Transition(from, to);
                while (tokenizer.hasMoreTokens()) {
                    transition.addLabel(tokenizer.nextToken());
                }
                automaton.addTransition(transition);
            }
        } catch (Exception e) {
            System.out.println("something is wrong! .... " + e.toString());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
        return automaton;
    }


    public static void persistAutomaton(File file, Automaton automaton) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(automaton);
            out.close();
        } catch (IOException e) {
            System.out.println("something is wrong!(persist) .... " + e.toString());
        }
    }

    public static Automaton getSerializedAutomaton(File file) {
        ObjectInputStream in = null;
        Automaton automaton = null;
        try {
            in = new ObjectInputStream(new FileInputStream(file));
            automaton = (Automaton) in.readObject();
            in.close();
        } catch (IOException e) {
            System.out.println("something is wrong!(get persist) .... " + e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println("something is wrong!(get persist, class) .... " + e.toString());
        }
        return automaton;
    }


}
