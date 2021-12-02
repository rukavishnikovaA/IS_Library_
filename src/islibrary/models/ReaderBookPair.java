/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import java.io.Serializable;

public class ReaderBookPair implements Serializable {

    ReaderModel reader;
    BookModel book;
    long returnDate;
    
    public ReaderBookPair(ReaderModel reader, BookModel book, long returnDate) {
        this.book = book;
        this.reader = reader;
    }
}