/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import islibrary.data.BookModel;
import datasource.BookDataSource;
import islibrary.util.DataSaver;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vladi
 */
public class BookModelTest {
    
    /**
     * Test of isMatchByQuery method, of class BookModel.
     */
    @Test
    public void testIsMatchByQuery() {
        System.out.println("isMatchByQuery");
        String query = "Hello";
        BookModel instance = new BookModel(0, query, query, query, query, query, query, 0, 0);
        boolean result = instance.isMatchByQuery(query);
        assertEquals(true, result);
    }

    /**
     * Test of getActualNumber method, of class BookModel.
     */
    @Test
    public void testGetActualNumber() {
        System.out.println("getActualNumber");
        
        BookDataSource bookSaver = DataSaver.getInstance().bookDataSource;
        bookSaver.deleteAll();
        
        int result = BookModel.getActualNumber();
        assertEquals(0, result);
    }
    
}
