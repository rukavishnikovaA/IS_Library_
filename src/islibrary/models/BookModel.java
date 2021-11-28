/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import java.io.Serializable;

/**
 *
 * @author Vladi
 */
public class BookModel implements Serializable {

    public int number;
    public String name;
    public String author;
    public String publis;
    public String yearBook;
    public String genery;
    public String language;
    public int cout;
    public int pages;

    public BookModel(int number, String name, String author, String publis, String yearBook, String genery, String language, int cout, int pages) {
        this.number = number;
        this.name = name;
        this.author = author;
        this.publis = publis;
        this.yearBook = yearBook;
        this.genery = genery;
        this.language = language;
        this.cout = cout;
        this.pages = pages;
    }
    
    
    public boolean isMatchByQuery(String query) {
        return name.contains(query)
                || author.contains(query)
                || publis.contains(query)
                || yearBook.contains(query)
                || genery.contains(query)
                || language.contains(query)
                || Integer.toString(cout).contains(query)
                || Integer.toString(pages).contains(query)
                || Integer.toString(number).contains(query) ;
    }
}
