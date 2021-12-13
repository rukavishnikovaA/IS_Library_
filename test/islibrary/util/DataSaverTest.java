/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

import islibrary.models.BookModel;
import islibrary.models.ReaderBookPair;
import islibrary.models.ReaderModel;
import islibrary.util.DataSaver.BookSaver;
import islibrary.util.DataSaver.ReaderBookPairSaver;
import islibrary.util.DataSaver.ReadersModelSaver;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Vladi
 */
public class DataSaverTest {

    ReadersModelSaver readersSaver = ReadersModelSaver.getInstance();
    BookSaver bookSaver = BookSaver.getInstance();
    ReaderBookPairSaver readerBookPairSaver = ReaderBookPairSaver.getInstance();

    @Test
    public void testSaveReaders() {
        readersSaver.deleteAll();
        ReaderModel model = new ReaderModel(0, "", "", "", "", "", 0, 0);
        readersSaver.writeObject(model);
        ArrayList<ReaderModel> list = readersSaver.readObject();
        assertEquals(1, list.size());
    }

    @Test
    public void testSaveBook() {
        bookSaver.deleteAll();
        BookModel model = new BookModel(0, "", "", "", "", "", "", 0, 0);
        bookSaver.writeObject(model);
        ArrayList<BookModel> list = bookSaver.readObject();
        assertEquals(1, list.size());
    }

    @Test
    public void testSaveReaderBookPair() {
        readerBookPairSaver.deleteAll();
        ReaderBookPair model = new ReaderBookPair(0, 0, 0, 0, 0);
        readerBookPairSaver.writeObject(model);
        ArrayList<ReaderBookPair> list = readerBookPairSaver.readObject();
        assertEquals(1, list.size());
    }

    @Test
    public void testDeleteReaders() {
        readersSaver.deleteAll();
        ReaderModel model = new ReaderModel(0, "", "", "", "", "", 0, 0);
        readersSaver.writeObject(model);
        readersSaver.deleteAll();
        ArrayList<ReaderModel> list = readersSaver.readObject();
        int newSize = list.size();

        assertEquals(0, newSize);
    }

    @Test
    public void testDeleteBook() {
        bookSaver.deleteAll();
        BookModel model = new BookModel(0, "", "", "", "", "", "", 0, 0);
        bookSaver.writeObject(model);
        bookSaver.deleteAll();
        ArrayList<BookModel> list = bookSaver.readObject();
        int newSize = list.size();

        assertEquals(0, newSize);
    }

    @Test
    public void testDeleteReaderBookPair() {
        readerBookPairSaver.deleteAll();
        ReaderBookPair model = new ReaderBookPair(0, 0, 0, 0, 0);
        readerBookPairSaver.writeObject(model);
        readerBookPairSaver.deleteAll();
        ArrayList<ReaderBookPair> list = readerBookPairSaver.readObject();
        int newSize = list.size();

        assertEquals(0, newSize);
    }

    
    @Test
    public void testEditReaders() {
        readersSaver.deleteAll();
        ReaderModel model = new ReaderModel(0, "", "", "", "", "", 0, 0);
        readersSaver.writeObject(model);
        
        model.adress = "address";
        readersSaver.writeObject(model);
        ArrayList<ReaderModel> list = readersSaver.readObject();
        int newSize = list.size();

        assertEquals(1, newSize);
        assertEquals("address", list.get(0).adress);
    }

    @Test
    public void testEditBook() {
        bookSaver.deleteAll();
        BookModel model = new BookModel(0, "", "", "", "", "", "", 0, 0);
        bookSaver.writeObject(model);
        
        model.author = "author";
        
        bookSaver.writeObject(model);
        
        ArrayList<BookModel> list = bookSaver.readObject();
        int newSize = list.size();

        assertEquals(1, newSize);
        assertEquals("author", list.get(0).author);
    }

    @Test
    public void testEditReaderBookPair() {
        readerBookPairSaver.deleteAll();
        ReaderBookPair model = new ReaderBookPair(0, 0, 0, 0, 0);
        readerBookPairSaver.writeObject(model);
        model.isIssueDate = System.currentTimeMillis();
        readerBookPairSaver.writeObject(model);
        
        ArrayList<ReaderBookPair> list = readerBookPairSaver.readObject();
        int newSize = list.size();

        assertEquals(1, newSize);
    }


}
