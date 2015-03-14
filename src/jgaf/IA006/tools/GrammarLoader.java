/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaf.IA006.grammar.Grammar;

/**
 *
 * @author Empt
 */
public class GrammarLoader 
{
    private File f;
    private Grammar g = new Grammar();
    
    public GrammarLoader(File f)
    {
        this.f = f;
    }
    
    
    public Grammar processFile()
    {
        Path p = Paths.get(f.getAbsolutePath());
        List<String> lines = new ArrayList<>();
        try 
        {
            lines = Files.readAllLines(p, Charset.forName("UTF-8"));
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(GrammarLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringBuilder sb = new StringBuilder();
        for(String s : lines)
        {
            if(!s.startsWith("//"))
            {
                sb.append(s); // nevkladat novy riadok lebo nepojdu RE                
            }
            
        }
        
        g = GrammarFactory.generateFromString(sb.toString());
        
        return g;
    }
}
