/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.IA006.editors;

import jgaf.IA006.tools.FirstAndFollowI;
import jgaf.IA006.tools.FirstAndFollow;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jgaf.IA006.grammar.Epsilon;
import jgaf.IA006.grammar.NonTerminal;
import jgaf.IA006.grammar.Symbol;
import jgaf.IA006.grammar.Terminal;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Empt
 */
public class FirstAndFollowImplTest 
{
    
    private FirstAndFollowI faf;
    private Symbol a = new Terminal("a");
    private Symbol b = new Terminal("b");
    private Symbol c = new Terminal("c");
    private Symbol d = new Terminal("d");
    private Symbol k = new Terminal("k");
    private Symbol A = new NonTerminal("A");
    private Symbol B = new NonTerminal("B");
    private Symbol eps = new Epsilon();
    private List<Symbol> wnok1 = Arrays.asList(A,B);
    private List<Symbol> wnok2 = Arrays.asList(eps,A,B);
    private List<Symbol> wnok3 = Arrays.asList(A,eps,eps,B);
    private List<Symbol> wnok4 = Arrays.asList(a,B);
    private List<Symbol> wnok5 = Arrays.asList(eps,a,B);
    private List<Symbol> wok1 = Arrays.asList(eps,eps,eps);
    private List<Symbol> wok2 = Arrays.asList(eps,eps,a);
    private List<Symbol> wok3 = Arrays.asList(a,eps,b);
    private List<Symbol> wok4 = Arrays.asList(a,eps,b,c);
    private List<Symbol> wok5 = Arrays.asList(a,b,c);
    private List<Symbol> wok6 = Arrays.asList(eps,eps,eps,a,eps,eps,eps,b,eps,eps,eps,c);
    private List<Symbol> wok7 = Arrays.asList(k,a,b);
    private List<Symbol> wok8 = Arrays.asList(d,k,a);
    private List<Symbol> wok9 = Arrays.asList(eps);
    private Set<List<Symbol>> nokset1 = new HashSet<>();
    private Set<List<Symbol>> okset1 = new HashSet<>();
    private Set<List<Symbol>> okseteps = new HashSet<>();
    private Set<List<Symbol>> okset2 = new HashSet<>();
    @Before
    public void setUp() 
    {
        faf = new FirstAndFollow();
        
        nokset1.add(wnok1);
        nokset1.add(wnok2);
        nokset1.add(wnok3);
        nokset1.add(wnok4);
        nokset1.add(wok5);
        
        okset1.add(wok1);
        okset1.add(wok2);
        okset1.add(wok5);
        
        okset2.add(wok7);
        okset2.add(wok8);
        okseteps.add(wok1);
    }
    
    

    /**
     * Test of kLengthPrefix method, of class FirstAndFollowImpl.
     */
    @Test
    public void testKLengthPrefix() 
    {
        try
        {
            faf.kLengthPrefix(wok1, -1);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.kLengthPrefix(wnok1, 0);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.kLengthPrefix(wnok1, 1);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.kLengthPrefix(wnok1, 2);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.kLengthPrefix(wnok1, 3);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.kLengthPrefix(wnok2, 2);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.kLengthPrefix(wnok3, 2);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.kLengthPrefix(wnok4, 2);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.kLengthPrefix(wnok5, 2);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        List<Symbol> result = null;
        
        try
        {
            result = faf.kLengthPrefix(wok1, 1);            
        }
        catch(IllegalArgumentException iae)
        {
            fail();
        }
        
        deepEquals(Arrays.asList(eps), result);
        
        try
        {
            result = faf.kLengthPrefix(wok1, 2);            
        }
        catch(IllegalArgumentException iae)
        {
            fail();
        }
        
        deepEquals(Arrays.asList(eps), result);
        
        try
        {
            result = faf.kLengthPrefix(wok1, 3);            
        }
        catch(IllegalArgumentException iae)
        {
            fail();
        }
        
        deepEquals(Arrays.asList(eps), result);
        
        try
        {
            result = faf.kLengthPrefix(wok2, 1);            
        }
        catch(IllegalArgumentException iae)
        {
            fail();
        }
        
        deepEquals(Arrays.asList(a), result);
        
        try
        {
            result = faf.kLengthPrefix(wok3, 2);            
        }
        catch(IllegalArgumentException iae)
        {
            fail();
        }
        
        deepEquals(Arrays.asList(a,b), result);
        
        try
        {
            result = faf.kLengthPrefix(wok4, 3);            
        }
        catch(IllegalArgumentException iae)
        {
            fail();
        }
        
        deepEquals(Arrays.asList(a,b,c), result);
        
        try
        {
            result = faf.kLengthPrefix(wok5, 2);            
        }
        catch(IllegalArgumentException iae)
        {
            fail();
        }
        
        deepEquals(Arrays.asList(a,b), result);
        
        try
        {
            result = faf.kLengthPrefix(wok6, 3);            
        }
        catch(IllegalArgumentException iae)
        {
            fail();
        }
        
        deepEquals(Arrays.asList(a,b,c), result);
    }
    
    private void deepEquals(List<Symbol> expected, List<Symbol> actual)
    {
        assertEquals("Size",expected.size(), actual.size());
        
        for(int i = 0 ; i < actual.size(); i++)
        {
            assertEquals("Symbols are not same",expected.get(i),actual.get(i));
        }
    }

    /**
     * Test of concatenateWordWithPrefix method, of class FirstAndFollowImpl.
     */
    @Test
    public void testConcatenateWordWithPrefix() 
    {
        try
        {
            faf.concatenateWordWithPrefix(wok1, wok1, -1);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.concatenateWordWithPrefix(wnok1, wok1, 0);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.concatenateWordWithPrefix(wnok1, wok1, 1);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.concatenateWordWithPrefix(wnok1, wok1, 10);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.concatenateWordWithPrefix(wnok1, wnok1, 10);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.concatenateWordWithPrefix(wok1, wnok1, 10);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        List<Symbol> result = null;
        
        try
        {
            result = faf.concatenateWordWithPrefix(wok1, wok1, 0);            
        }
        catch(IllegalArgumentException iae){fail();}
        
        deepEquals(Arrays.asList(eps), result);
        
        try
        {
            result = faf.concatenateWordWithPrefix(wok1, wok1, 10);            
        }
        catch(IllegalArgumentException iae){fail();}
        
        deepEquals(Arrays.asList(eps), result);
        
        try
        {
            result = faf.concatenateWordWithPrefix(wok1, wok4, 10);            
        }
        catch(IllegalArgumentException iae){fail();}
        
        deepEquals(Arrays.asList(a,b,c), result);
        
        try
        {
            result = faf.concatenateWordWithPrefix(wok5, wok5, 5);            
        }
        catch(IllegalArgumentException iae){fail();}
        
        deepEquals(Arrays.asList(a,b,c,a,b), result);
        
        try
        {
            result = faf.concatenateWordWithPrefix(wok6, wok6, 5);            
        }
        catch(IllegalArgumentException iae){fail();}
        
        deepEquals(Arrays.asList(a,b,c,a,b), result);
        
        try
        {
            result = faf.concatenateWordWithPrefix(wok6, wok6, 6);            
        }
        catch(IllegalArgumentException iae){fail();}
        
        deepEquals(Arrays.asList(a,b,c,a,b,c), result);
    }

    /**
     * Test of concatenateSetsWithPrefix method, of class FirstAndFollowImpl.
     */
    @Test
    public void testConcatenateSetsWithPrefix() {
        try
        {
            faf.concatenateSetsWithPrefix(null, null, -1);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            assertEquals("should be empty set", 0,faf.concatenateSetsWithPrefix(null, null, 0).size());            
        }
        catch(IllegalArgumentException iae){fail();}
        
        try
        {
            faf.concatenateSetsWithPrefix(nokset1, okset1, 0);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.concatenateSetsWithPrefix(nokset1, okset1, 2);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        try
        {
            faf.concatenateSetsWithPrefix(okset1, nokset1, 0);
            fail();
        }
        catch(IllegalArgumentException iae){}
        
        Set<List<Symbol>> result = null;
        try
        {
            result = faf.concatenateSetsWithPrefix(okseteps, okseteps, 0);
            assertEquals("size",1,result.size());
            
            for(List<Symbol> temp : result)
            {
                deepEquals(Arrays.asList(eps), temp);
            }
        }
        catch(IllegalArgumentException iae){fail();}
        
        try
        {
            result = faf.concatenateSetsWithPrefix(okset1, okset2, 3);
            
            
            Set<List<Symbol>> expected = new HashSet<>();
            expected.add(Arrays.asList(k,a,b));
            expected.add(Arrays.asList(d,k,a));
            expected.add(Arrays.asList(a,k,a));
            expected.add(Arrays.asList(a,d,k));
            expected.add(Arrays.asList(a,b,c));
            
            assertEquals("size",expected.size(),result.size());
            
            for(List<Symbol> ex : expected)
            {
                assertTrue("nie je tam",result.contains(ex));
            }
        }
        catch(IllegalArgumentException iae){fail();}
    }
}