/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Vladi
 */
public class UtilTest {
    
    public UtilTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of isInteger method, of class Util.
     */
    @Test
    public void testIsInteger() {
        System.out.println("isInteger");
        String s = "";
        boolean expResult = false;
        boolean result = Util.isInteger(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of longToDateString method, of class Util.
     */
    @Test
    public void testLongToDateString() {
        System.out.println("longToDateString");
        long Date = 0L;
        String expResult = "";
        String result = Util.longToDateString(Date);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stringIsLetters method, of class Util.
     */
    @Test
    public void testStringIsLetters() {
        System.out.println("stringIsLetters");
        String name = "";
        boolean expResult = false;
        boolean result = Util.stringIsLetters(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stringIsNumberPhone method, of class Util.
     */
    @Test
    public void testStringIsNumberPhone() {
        System.out.println("stringIsNumberPhone");
        String s = "";
        boolean expResult = false;
        boolean result = Util.stringIsNumberPhone(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
