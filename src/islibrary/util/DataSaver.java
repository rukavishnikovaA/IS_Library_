/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

import datasource.BookDataSource;
import datasource.ReaderBookPairSource;
import datasource.ReadersDataSource;

/**
 *
 * @author Vladi
 */
public class DataSaver {

    public BookDataSource bookDataSource = new BookDataSource();
    public ReadersDataSource readersDataSource = new ReadersDataSource();
    public ReaderBookPairSource readerBookPairSource = new ReaderBookPairSource();

    private static DataSaver instance;
    public static DataSaver getInstance() {
        if(instance == null) {
            instance = new DataSaver();
        }
        return instance;
    }
}
