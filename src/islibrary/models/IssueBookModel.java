/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import datasource.ReaderBookPairSource;
import datasource.ReadersDataSource;
import islibrary.data.ReaderBookPair;
import islibrary.data.ReaderModel;
import islibrary.util.DataSaver;
import java.util.ArrayList;


public class IssueBookModel {
    
    ReaderBookPairSource readerBookPairSource = DataSaver.getInstance().readerBookPairSource;
    ReadersDataSource readersDataSource = DataSaver.getInstance().readersDataSource;
    
    public void savePair(ReaderBookPair pair) {
       readerBookPairSource.writeObject(pair);
    }
    
    public ArrayList<ReaderModel> getReaders() {
        return readersDataSource.readObject();
    }
}
