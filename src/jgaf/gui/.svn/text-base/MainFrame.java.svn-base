/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on Oct 22, 2010, 7:56:32 PM
 */

package jgaf.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jgaf.automaton.fa.FSAutomatonEditor;
import jgaf.environment.Environment;
import jgaf.environment.PropertiesHandler;
import jgaf.l18n.Resource;
import org.dom4j.DocumentException;

/**
 *
 * @author hanis
 */
public class MainFrame extends javax.swing.JFrame {


    private Environment environment;
    private PropertiesHandler properties;
    private JDesktopPane desktop;


    /** Creates new form MainFrame */
    public MainFrame() {
        initComponents();  
        setPreferredSize(new Dimension(600,600));
        setExtendedState(MainFrame.MAXIMIZED_BOTH);

        desktop = new JDesktopPane();
        getContentPane().add(desktop, BorderLayout.CENTER);
        try {
            this.environment = Environment.createInstance(this);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(this,
                                          ex.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            dispose();
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(this,
                                          ex.getMessage(),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            dispose();
        }
        properties = PropertiesHandler.getInstance();        
        setJMenuBar(new MainFrameMenu(this));        

        setTitle(Resource.getValue("application.name"));
        pack();


        FSAutomatonEditor faEditor = (FSAutomatonEditor) Environment.getInstance().getEditorHandler().createEditor("FA");
    }

    public JDesktopPane getDesktop() {
        return desktop;
    }


//    public void addToTitle(String title) {
//        setTitle(Resource.getValue("application.name") + "[" + title + "]");
//    }

//    public void transformationPanel(Transformation transformation){
//
//        //Transformation transformation = new MyFirstTransformation(automaton, new Automaton());
//        //Transformation transformation = new NFAtoDFA((Automaton) editorHandler.getCurrentEditor().getData(), new Automaton());
//        TransformationPanel trans = new TransformationPanel(transformation, this);
//        getMainPanel().removeAll();
//        getMainPanel().add(trans);
//        addToTitle(transformation.getName());
//        getMainPanel().add(trans, BorderLayout.CENTER);
//        pack();
//    }
//
//    public void personalPanel(JPanel panel) {
//        getMainPanel().removeAll();
//        getMainPanel().add(panel, BorderLayout.CENTER);
//        pack();
//    }

//    public void createGrammar() {
//        GrammarEditor editor = new GrammarEditor(this);
//       // editor.createExample();
//        getMainPanel().removeAll();
//        getMainPanel().add(editor.getFace(), BorderLayout.CENTER);
//        pack();
//    }


    public void ImportFileTXT() {
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File(properties.getFileLastPath()));
//        int returnVal = fc.showOpenDialog(this);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            Automaton automaton = FileImporter.getAutomaton(fc.getSelectedFile());
//            AutomaticAutomatonDrawer.setAllCoordinates(automaton);
//            properties.setFileLastPath(fc.getSelectedFile().getAbsolutePath());
//            createAutomatonFrame(automaton);
//        }
    }



    public void importFileJSON() {
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File(properties.getFileLastPath()));
//        int returnVal = fc.showOpenDialog(this);
//        if(returnVal == JFileChooser.APPROVE_OPTION) {
//            try {
//                Automaton automaton = JSONImporter.ImportAutomaton(fc.getSelectedFile());
//                properties.setFileLastPath(fc.getSelectedFile().getAbsolutePath());
//                createAutomatonFrame(automaton);
//            } catch (JSONException ex) {
//                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }




 



    public void exit() {
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Grammars & Automata Framework");
        setBackground(new java.awt.Color(246, 246, 246));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pack();
    }// </editor-fold>//GEN-END:initComponents



    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                    //UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                new MainFrame().setVisible(true);
            }
        });
    }


//    private void createAutomatonFrame(Automaton automaton) {
////        if(automatonCanvas == null) {
////            automatonCanvas = new AutomatonCanvas(this);
////            mainPanel.add(automatonCanvas, BorderLayout.CENTER);
////        }
//
//        //editor = new StateDiagramEditor(automaton);
//        editor = new FSAutomatonEditor(automaton);
//        //editor = new AutomatonTableEditor(automaton);
//        AutomatonCanvas canvas = new AutomatonCanvas(this);
//        editor.setCanvas(canvas);
//        canvas.setEditor(editor);
//
//        ((FSAutomatonEditor) editor).getEditor(FSAutomatonEditor.STATE_DIAGRAM).setCanvas(canvas);
//        ((FSAutomatonEditor) editor).getEditor(FSAutomatonEditor.STATE_TRANSITION_TABLE).setCanvas(canvas);
//        setToolBar(editor.getToolBar());
////        toolbarPanel.removeAll();
////        toolbarPanel.add(editor.getToolBar(), BorderLayout.CENTER);
//        mainPanel.removeAll();
//        mainPanel.add(canvas, BorderLayout.CENTER);
//        pack();
//    }





//    public EditorsHandler getEditorHandler() {
//        return editorHandler;
//    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    // End of variables declaration//GEN-END:variables

}
