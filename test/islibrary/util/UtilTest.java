/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vladi
 */
public class UtilTest {

    /**
     * Test of isInteger method, of class Util.
     */
    @Test
    public void testIsInteger() {
        System.out.println("isInteger");
        String s = "123";
        
        boolean result = Util.isInteger(s);
        assertEquals(true, result);
    }

    /**
     * Test of longToDateString method, of class Util.
     */
    @Test
    public void testLongToDateString() {
        System.out.println("longToDateString");
        long Date = 1639419496000L;
        String result = Util.longToDateString(Date);
        assertEquals("13.12.2021", result);
    }

    /**
     * Test of stringIsLetters method, of class Util.
     */
    @Test
    public void testStringIsLetters() {
        System.out.println("stringIsLetters");
        String name = "Oleg123";
        boolean result = Util.stringIsLetters(name);
        assertEquals(false, result);
    }

    /**
     * Test of stringIsNumberPhone method, of class Util.
     */
    @Test
    public void testStringIsNumberPhone() {
        System.out.println("stringIsNumberPhone");
        String s = "9203405873";
        boolean result = Util.stringIsNumberPhone(s);
        assertEquals(true, result);
    }
    
}
