/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import islibrary.util.DataSaver;
import java.io.Serializable;
import java.util.ArrayList;

public class ReaderBookPair implements Serializable {

    ReaderModel reader;
    BookModel book;
    long returnDate;
    long issueDate;
    long isIssueDate;
    
    public ReaderBookPair(
        ReaderModel reader, 
        BookModel book,
        long returnDate,
        long issueDate,
        long isIssueDate
    ) {
        this.book = book;
        this.reader = reader;
    }
    
    public boolean isExist() {
        ArrayList<ReaderBookPair> list = DataSaver.ReaderBookPairSaver.getInstance().readObject();
        for(ReaderBookPair pair: list) {
            if(pair.reader.numberBilet == reader.numberBilet && pair.book.number == book.number) {
                return true;
            }
        }
        
        return false;
    }
}