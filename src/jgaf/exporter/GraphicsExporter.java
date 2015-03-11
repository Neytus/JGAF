/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.exporter;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author hanis
 */
public class GraphicsExporter {

//    public static void saveComponentAsJPEG(Component myComponent,
//                                    String fileName, String extension) throws FileNotFoundException, IOException {
//
//        Dimension size = myComponent.getSize();
//
//        System.out.println(myComponent.getWidth());
//
//        BufferedImage myImage = new BufferedImage(size.width, size.height,
//                                                  BufferedImage.TYPE_INT_RGB);
//
//        Graphics2D g2 = myImage.createGraphics();
//
//        myComponent.paint(g2);
//
//
//        OutputStream out = new FileOutputStream(fileName);
//
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//
//        encoder.encode(myImage);
//
//        out.close();
//    }

    public static void saveComponentAsGraphics(Component myComponent,
                                          File file, String format, boolean preferredSize) throws IOException {

        int width = preferredSize ? (int) myComponent.getPreferredSize().getWidth() : myComponent.getWidth();
        //int width = (int) myComponent.getPreferredSize().getWidth(); //myComponent.getWidth();
        int height = preferredSize ? (int) myComponent.getPreferredSize().getHeight() : myComponent.getHeight();
        //int height = (int) myComponent.getPreferredSize().getHeight();//myComponent.getHeight();
        BufferedImage image;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2r = image.createGraphics();
        myComponent.paint(g2r);
        g2r.dispose();


        ImageIO.write(image, format, file);

//        ImageIO.write(image, "png", new File(fileName + ".png"));
//        ImageIO.write(image, "gif", new File(fileName + ".gif"));
//            ImageIO.write(image, "jpg", new File(fileName + ".jpg"));

    }



    public static void saveComponentAsGraphics(Component myComponent,
                                          File file, String format) throws IOException {
        saveComponentAsGraphics(myComponent, file, format, false);
    }






}
