/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;




public class JTableTest extends JTable {

    public JTableTest() {
        super();
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

        JFrame frame = new JFrame("SimpleTableDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JTableTest table = new JTableTest();
        JPanel p = new JPanel();
        p.setOpaque(true); //content panes must be opaque
        p.add(table);
        frame.setContentPane(p);

p.setLayout(new BorderLayout());
p.add(table, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);


            }
        });
    }

}
