/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.automaton.pda;

import java.awt.Dimension;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class SimulatorController {
    private static final int WORD = 0;
    private static final int CONF = 1;
    
    private static final int MANUAL = 0;
    private static final int RANDOM = 1;
    private static final int DETERMINISTIC = 2;
    private static final int ACCEPTING = 3;
    
    private PushdownAutomaton automata;
    private MatrixData matrixData;
    private MatrixTableRender matrixRender;
    
 //   private StackListModel stackListModel; 
    private StackPanel stackPanel = null;
    
    private JLabel labCompltyWord;
    private JLabel labRestWord;
    private JLabel labCurrentChar;
    
    private Random random = null;
    
    private boolean AutomataChosen;
    
    private javax.swing.Timer timer;
    
    
    
    private DefaultValues defaultValues;
    
    private boolean simulationInProgress = false;
    
    private DialogTerm progressDialog;

  //  private Dimension windowDim;
    
    private int simulationType;
    
    public static String ABSOLUT_PATH;

    
    public SimulatorController(JPanel panel) {       
    
    }






    private int getRandom(int number) {
        if(random == null) {
            random = new Random();
        }
        return random.nextInt(number);
    }




}
