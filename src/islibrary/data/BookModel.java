/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.data;

import islibrary.util.DataSaver;
import java.io.Serializable;
import java.util.ArrayList;


public class BookModel implements Serializable, Unique {

    public int number;
    public String name;
    public String author;
    public String publis;
    public String yearBook;
    public String genery;
    public String language;
    public int count;
    public int pages;

    public BookModel(int number, String name, String author, String publis, String yearBook, String genery, String language, int cout, int pages) {
        this.number = number;
        this.name = name;
        this.author = author;
        this.publis = publis;
        this.yearBook = yearBook;
        this.genery = genery;
        this.language = language;
        this.count = cout;
        this.pages = pages;
    }

    public boolean isMatchByQuery(String query) {
        return name.contains(query)
                || author.contains(query)
                || publis.contains(query)
                || yearBook.contains(query)
                || genery.contains(query)
                || language.contains(query)
                || Integer.toString(count).contains(query)
                || Integer.toString(pages).contains(query)
                || Integer.toString(number).contains(query);
    }

    public static BookModel getFromListByNumber(int number, ArrayList<BookModel> list) {
        for(BookModel item: list) {
            if(item.number == number) return item;
        }
        
        return null;
    }
    
    public static int getActualNumber() {
        ArrayList<BookModel> books = DataSaver.getInstance().bookDataSource.readObject();
        if(books.isEmpty()) return 0;
        else {
            int maxNumber = 0;
            
            for(BookModel book: books) {
                if(book.number > maxNumber) maxNumber = book.number;
            }
            
            return maxNumber + 1;
        }
    }
    
    public static int bookLimit = 10;

    @Override
    public String getUniqueNumber() {
        return Integer.toString(number);
    }    
}
