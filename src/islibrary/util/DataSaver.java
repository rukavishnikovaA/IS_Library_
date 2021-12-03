/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

import islibrary.models.BookModel;
import islibrary.models.ReaderModel;
import islibrary.models.ReaderBookPair; 
import java.util.ArrayList; 

/**
 *
 * @author Vladi
 */
public class DataSaver {

    static public class ReadersModelSaver extends IDataSaver<ReaderModel> {

        public ReadersModelSaver(String fileName) {
            super(fileName);
        }

       @Override
       public void writeObject(ReaderModel obj) {
            ArrayList<ReaderModel> list = readObject();

            ReaderModel oldReader = ReaderModel.getFromListByNumberBilet(obj.numberBilet, list);

            if (oldReader != null) {
                list.remove(oldReader);
            }

            list.add(obj);
            writeObjects(list);
       }
       
       static ReadersModelSaver instance;
       public static ReadersModelSaver getInstance() {
           if(instance == null) {
               instance = new ReadersModelSaver("ReaderModel.txt");
           }
           
           return instance;
       }
    }
    
    
    public static class BookSaver extends IDataSaver<BookModel> {

        public BookSaver(String fileName) {
            super(fileName);
        }
        
        @Override
       public void writeObject(BookModel obj) {
            ArrayList<BookModel> list = readObject();

            BookModel oldBook = BookModel.getFromListByNumber(obj.number, list);

            if (oldBook != null) {
                list.remove(oldBook);
            }

            list.add(obj);
            writeObjects(list);
       }
        
        public void deleteBookByNumber(Integer number) {
            ArrayList<BookModel> list = readObject();

            list.forEach((BookModel book) -> {
                if (book.number == number) {
                    deleteBook(book);
                    return;
                }
            });
        }

        public void deleteBook(BookModel book) {
            ArrayList<BookModel> list = readObject();
            ArrayList<BookModel> saveList = new ArrayList<>();

            list.forEach((b) -> {
                if (b.number != book.number) {
                    saveList.add(b);
                }
            });

            writeObjects(saveList);
        }
        
        static BookSaver instance;
        public static BookSaver getInstance() {
           if(instance == null) instance = new BookSaver("BookSaver.txt");
           return instance;
        }
    }

    public static class ReaderBookPairSaver extends IDataSaver<ReaderBookPair>  {
        public ReaderBookPairSaver(String fileName) {
            super(fileName);
        }
              
        static ReaderBookPairSaver instance;
        public static ReaderBookPairSaver getInstance() {
           if(instance == null) instance = new ReaderBookPairSaver("ReaderBookPair.txt");
           return instance;
        }
    }
}
