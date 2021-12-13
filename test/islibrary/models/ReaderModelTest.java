/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import islibrary.util.DataSaver;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vladi
 */
public class ReaderModelTest {
    
    /**
     * Test of isMathByQuery method, of class ReaderModel.
     */
    @Test
    public void testIsMathByQuery() {
        System.out.println("isMathByQuery");
        String query = "Hello";
        ReaderModel instance = new ReaderModel(0, query, query, query, query, query, 0, 0);
        boolean result = instance.isMathByQuery(query);
        assertEquals(true, result);
    }

    /**
     * Test of getFullname method, of class ReaderModel.
     */
    @Test
    public void testGetFullname() {
        System.out.println("getFullname");
        ReaderModel instance = new ReaderModel(0, "Иван", "Иванович", "Иванов", "", "", 0, 0);
        String expResult = "Иван Иванович Иванов";
        String result = instance.getFullname();
        assertEquals(expResult, result);
    }

    /**
     * Test of getActualBiletNumber method, of class ReaderModel.
     */
    @Test
    public void testGetActualBiletNumber() {
        System.out.println("getActualBiletNumber");
        DataSaver.ReadersModelSaver readerSaver = DataSaver.ReadersModelSaver.getInstance();
        readerSaver.deleteAll();
        
        int expResult = 0;
        int result = ReaderModel.getActualBiletNumber();
        assertEquals(expResult, result);
    }
}
