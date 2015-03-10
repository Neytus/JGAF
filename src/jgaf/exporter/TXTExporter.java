/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jgaf.exporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author hanis
 */
public class TXTExporter {

    public static void writeStringToFile(File file,
                                         String string) throws TXTWriterException {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(string);
        } catch (FileNotFoundException e) {
            throw new TXTWriterException();
        } catch (IOException e) {
            throw new TXTWriterException();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                throw new TXTWriterException();
            }
        }
    }
}
