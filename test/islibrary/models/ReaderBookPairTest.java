/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import islibrary.data.ReaderBookPair;
import datasource.ReaderBookPairSource;
import islibrary.util.DataSaver;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vladi
 */
public class ReaderBookPairTest {
    
    /**
     * Test of isExist method, of class ReaderBookPair.
     */
    @Test
    public void testIsExist() {
        System.out.println("isExist");
        ReaderBookPair instance = new ReaderBookPair(0, 0, 0, 0, 0);
        ReaderBookPairSource source = DataSaver.getInstance().readerBookPairSource;
        source.writeObject(instance);
        boolean result = instance.isExist();
        assertEquals(true, result);
    }

    /**
     * Test of wasIssued method, of class ReaderBookPair.
     */
    @Test
    public void testWasIssued() {
        System.out.println("wasIssued");
        ReaderBookPair instance = new ReaderBookPair(0, 0, 0, 0, System.currentTimeMillis());
        boolean result = instance.wasIssued();
        assertEquals(true, result);
    }
    
}
