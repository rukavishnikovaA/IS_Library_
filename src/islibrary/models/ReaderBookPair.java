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

    public int readerNumber;
    public int bookNumber;
    public long returnDate;
    public long issueDate;
    public long isIssueDate;
    
    public ReaderBookPair(
        int readerNumber, 
        int bookNumber,
        long returnDate,
        long issueDate,
        long isIssueDate
    ) {
        this.readerNumber = readerNumber;
        this.bookNumber = bookNumber;
        this.returnDate = returnDate;
        this.issueDate = issueDate;
        this.isIssueDate = isIssueDate;
    }
    
    public boolean isExist() {
        ArrayList<ReaderBookPair> list = DataSaver.ReaderBookPairSaver.getInstance().readObject();
        for(ReaderBookPair pair: list) {
            if(pair.readerNumber == readerNumber && pair.bookNumber == bookNumber) {
                return !pair.wasIssued();
            }
        }
        
        return false;
    }
    
    public boolean wasIssued() {
        return isIssueDate != 0;
    }

    public static ReaderBookPair findTargetInList(
            ArrayList<ReaderBookPair> list,
            int bookNumber,
            int readerNumber
    ) {
        for(ReaderBookPair pair: list) {
            if(pair.bookNumber == bookNumber && pair.readerNumber == readerNumber) {
                return pair;
            }
        }
        
        return null;
    }
    

}