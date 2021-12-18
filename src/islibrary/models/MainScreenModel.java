/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import datasource.BookDataSource;
import islibrary.data.BookModel;
import islibrary.util.DataSaver;
import java.util.ArrayList;


public class MainScreenModel {
    
    BookDataSource bookDataSource = DataSaver.getInstance().bookDataSource;
    
    public ArrayList<BookModel> getList() {
        return bookDataSource.readObject();
    }
    
    public ArrayList<BookModel> getListByQuery(String query) {
        ArrayList<BookModel> list = getList();
        ArrayList<BookModel> result = new ArrayList<>();

        list.forEach((book) -> {
            if (book.isMatchByQuery(query)) {
                result.add(book);
            }
        });

        return result;
    }

    public void deleteBook() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteBook(int bookNumber) {
        bookDataSource.deleteByNumber(Integer.toString(bookNumber));
    }
}
