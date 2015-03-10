/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jgaf.automaton.Automaton;
import jgaf.automaton.State;
import jgaf.automaton.fa.CanvasLabel;
import jgaf.automaton.fa.StateDiagramEditor;
import jgaf.environment.PropertiesHandler;

/**
 *
 * @author hanis
 */
public class AutomatonPreviewCanvas extends JPanel implements MouseListener, MouseMotionListener {

    public final StateDiagramEditor editor;
    
    public AutomatonPreviewCanvas(StateDiagramEditor editor) {
        super();
        this.editor = editor;
        addMouseListener(this);
        addMouseMotionListener(this);

    }


    @Override
    public void paintComponent(Graphics g) {
        setBackground(PropertiesHandler.getInstance().getAutomatonCanvasBackground());
        super.paintComponent(g);
        if (editor != null) {
                editor.paint((Graphics2D) g);
        }

//    Graphics2D g2 = (Graphics2D)g;
//    double x = 15, y = 50, w = 70, h = 70;
//    Ellipse2D e = new Ellipse2D.Double(x, y, w, h);
//    GradientPaint gp = new GradientPaint(75, 75, Color.white,
//        95, 95, Color.gray, true);
//    // Fill with a gradient.
//    g2.setPaint(gp);
//    g2.fill(e);
//    // Stroke with a solid color.
//    e.setFrame(x + 100, y, w, h);
//    g2.setPaint(Color.black);
//    g2.setStroke(new BasicStroke(8));
//    g2.draw(e);
//    // Stroke with a gradient.
//    e.setFrame(x + 200, y, w, h);
//    g2.setPaint(gp);
//    g2.draw(e);

    }


    public void paintMe() {
        editor.paint((Graphics2D) getGraphics());
        revalidate();
    }

//    public static void main(String[] args) {
//
//
// JFrame f = new JFrame();
//    f.addWindowListener(new WindowAdapter() {
//      public void windowClosing(WindowEvent e) {
//        System.exit(0);
//      }
//    });
//
//
//
//        Automaton a = new Automaton();
//        State s1 = new State("p", 50, 50);
//        State s2 = new State("q", 150, 50);
//        a.addState(s2);
//        a.addState(s1);
//        a.addTransition(s1, s2, "a");
//        a.addTransition(s1, s1, "b");
//        a.setInitialState(s1);
//        a.addAcceptingState(s2);
//
//        CanvasLabel label1 = new CanvasLabel("caption");
//        label1.move(8, 100);
//        StateDiagramEditor editor = new StateDiagramEditor(a, label1);
//
//
//
//
//
//    f.setContentPane(new AutomatonPreviewCanvas(editor));
//    f.setSize(800,375);
//    f.setVisible(true);
//  }

    public void mouseClicked(MouseEvent e) {
        editor.handleMouseLeftPressed(e);
        repaint();
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {        
        editor.handleMouseMoved(e);
        repaint();
    }

}
