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

    static public final class ReadersModelSaver extends BaseDataSaver<ReaderModel> {

        public ReadersModelSaver(String fileName) {
            super(fileName);
        }
        
        public ReaderModel findreaderByNumber(int number) {
            ArrayList<ReaderModel> list = readObject();

            for(ReaderModel reader: list) {
                if (reader.numberBilet == number) return reader;
            }
            
            return null;
        }

        public void deleteReaderByNumber(int number) {
            ArrayList<ReaderModel> list = readObject();
            
            for(ReaderModel reader: list) {
                if (reader.numberBilet == number) {
                    deleteReader(reader);
                    return;
                }
            }
        }

        public void deleteReader(ReaderModel reader) {
            ArrayList<ReaderModel> list = readObject();
            ArrayList<ReaderModel> saveList = new ArrayList<>();

            list.forEach((b) -> {
                if (b.numberBilet != reader.numberBilet) {
                    saveList.add(b);
                }
            });

            writeObjects(saveList);
        }

        @Override
        public void writeObject(ReaderModel obj) {
            ArrayList<ReaderModel> list = readObject();

            ReaderModel oldReader = ReaderModel.getFromListByNumberBilet(obj.numberBilet, list);
            if (oldReader != null) list.remove(oldReader);
            list.add(obj);
            writeObjects(list);
        }

        static ReadersModelSaver instance;

        public static ReadersModelSaver getInstance() {
            if (instance == null) {
                instance = new ReadersModelSaver("ReaderModel.txt");
            }

            return instance;
        }
    }

    public static final class BookSaver extends BaseDataSaver<BookModel> {

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

        public BookModel findBookByNumber(int number) {
            ArrayList<BookModel> list = readObject();
            
            for(BookModel book: list) {
                if (book.number == number) {
                    return book;
                }
            }
            
            return null;
        }
        
        public void deleteBookByNumber(int number) {
            ArrayList<BookModel> list = readObject();

            for(BookModel book: list) {
              if (book.number == number) {
                    deleteBook(book);
                    return;
                }  
            }
        }

        public void deleteBook(BookModel book) {
            ArrayList<BookModel> list = readObject();
            ArrayList<BookModel> saveList = new ArrayList<>();

            list.forEach((b) -> {
                if (b.number != book.number) saveList.add(b);
            });

            writeObjects(saveList);
        }

        static BookSaver instance;

        public static BookSaver getInstance() {
            if (instance == null) {
                instance = new BookSaver("BookSaver.txt");
            }
            return instance;
        }
    }

    public static final class ReaderBookPairSaver extends BaseDataSaver<ReaderBookPair> {

        public ReaderBookPairSaver(String fileName) {
            super(fileName);
        }

        @Override
        public void writeObject(ReaderBookPair obj) {
            ArrayList<ReaderBookPair> list = readObject();

            ReaderBookPair oldPair = ReaderBookPair.findTargetInList(list, obj.bookNumber, obj.readerNumber);

            if (oldPair != null) list.remove(oldPair);

            list.add(obj);
            writeObjects(list);
        }

        static ReaderBookPairSaver instance;

        public static ReaderBookPairSaver getInstance() {
            if (instance == null) {
                instance = new ReaderBookPairSaver("ReaderBookPair.txt");
            }
            return instance;
        }
    }
}
