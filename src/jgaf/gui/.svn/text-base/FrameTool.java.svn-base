/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.gui;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import jgaf.editor.Editor;

/**
 *
 * @author hanis
 */
public class FrameTool {

    public static List<JInternalFrame> frameUnion(List<JInternalFrame> framesA, List<JInternalFrame> framesB) {
        List<JInternalFrame> frames = new ArrayList<JInternalFrame>();
        for (JInternalFrame jInternalFrame : framesA) {
            frames.add(jInternalFrame);
        }
        for (JInternalFrame jInternalFrame : framesB) {
            frames.add(jInternalFrame);
        }
        return frames;
    }


    public static void organizeFrames(List<JInternalFrame> frames,
                                      JDesktopPane desktop) {
        int count = frames.size();
        if (count == 0) {
            return;
        }

        int sqrt = (int) Math.sqrt(count);
        int rows = sqrt;
        int cols = sqrt;
        if (rows * cols < count) {
            cols++;
            if (rows * cols < count) {
                rows++;
            }
        }
        Dimension size = desktop.getSize();

        int w = size.width / cols;
        int h = size.height / rows;
        int x = 0;
        int y = 0;

        // Iterate over the frames, deiconifying any iconified frames and then
        // relocating & resizing each.
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
                JInternalFrame f = frames.get((i * cols) + j);

                if (!f.isClosed() && f.isIcon()) {
                    try {
                        f.setIcon(false);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(FrameTool.class.getName()).log(Level.SEVERE, null, ex);
                    }



                }

                desktop.getDesktopManager().resizeFrame(f, x, y, w, h);
                x += w;
            }
            y += h; // start the next row
            x = 0;
        }

    }



    public static void minimizeFrames(List<JInternalFrame> frames,
                                      JDesktopPane desktop) {
        for (JInternalFrame frame : frames) {
            try {
                frame.setIcon(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FrameTool.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }









}
