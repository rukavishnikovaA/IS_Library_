/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

import islibrary.models.BookModel;
import islibrary.models.DataReaders;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 *
 * @author Vladi
     */
public class DataSaver {

    public static class DataReadersSaver {

        static public void writeObject(DataReaders obj) throws IOException, ClassNotFoundException {
            ArrayList<DataReaders> list = readObject();
            
            DataReaders oldReader = DataReaders.getFromListByNumberBilet(obj.numberBilet, list);
            
            if(oldReader != null) list.remove(oldReader);
            
            list.add(obj);
            writeObjects(list);
        }

        static public void writeObjects(ArrayList<DataReaders> list) throws IOException, ClassNotFoundException {
            //Create FileOutputStream to write file
            File file = new File("dataReaders.txt");
            if (file.exists()) {
                file.delete();
            }

            FileOutputStream fos = new FileOutputStream("dataReaders.txt");
            //Create ObjectOutputStream to write object
            ObjectOutputStream objOutputStream = new ObjectOutputStream(fos);
            //Write object to file
            for (DataReaders data : list) {
                objOutputStream.writeObject(data);
                objOutputStream.reset();
            }
            objOutputStream.close();
        }

        static public ArrayList<DataReaders> readObject() throws ClassNotFoundException, IOException {
            ArrayList<DataReaders> listAccount = new ArrayList();
            //Create new FileInputStream object to read file

            try {
                FileInputStream fis = new FileInputStream("dataReaders.txt");
                //Create new ObjectInputStream object to read object from file
                ObjectInputStream obj = new ObjectInputStream(fis);
                try {
                    while (fis.available() != -1) {
                        //Read object from file
                        DataReaders acc = (DataReaders) obj.readObject();
                        listAccount.add(acc);
                    }
                } catch (EOFException ex) {
                    //ex.printStackTrace();
                }
            } catch (FileNotFoundException ex) {
                //ex.printStackTrace();
            }

            return listAccount;
        }
    }

    public static class BookSaver {
        
        static public boolean bookNumberIsUnique(int number) {
            ArrayList<BookModel> list = readObject();
            
            for(BookModel book: list) {
                if (book.number == number) {
                    return false;
                } 
            }

            return true;
        }

        static public void writeObject(BookModel obj) {
            ArrayList<BookModel> list = readObject();
            list.add(obj);
            
            writeObject(list);
        }

        static public void writeObject(ArrayList<BookModel> list) {
            //Create FileOutputStream to write file
            File file = new File("BookModels.txt");
            if (file.exists()) {
                file.delete();
            }

            try {
                FileOutputStream fos = new FileOutputStream("BookModels.txt");
                //Create ObjectOutputStream to write object
                ObjectOutputStream objOutputStream = new ObjectOutputStream(fos);
                //Write object to file
                for (BookModel data : list) {
                    objOutputStream.writeObject(data);
                    objOutputStream.reset();
                }
                objOutputStream.close();
            } catch (Exception e) {
                //
            }
        }

        static public void deleteBookByNumber(Integer number) {
            ArrayList<BookModel> list = readObject();

            list.forEach((BookModel book) -> {
                if (book.number == number) {
                    deleteBook(book);
                    return;
                }
            });
        }

        static public void deleteBook(BookModel book) {
            ArrayList<BookModel> list = readObject();
            ArrayList<BookModel> saveList = new ArrayList<>();
            
            list.forEach((b) -> {
                if(b.number != book.number) {
                    saveList.add(b);
                }
            });
            
            writeObject(saveList);
        }

        static public ArrayList<BookModel> readObject() {
            ArrayList<BookModel> listAccount = new ArrayList();
            //Create new FileInputStream object to read file

            try {
                FileInputStream fis = new FileInputStream("BookModels.txt");
                //Create new ObjectInputStream object to read object from file
                ObjectInputStream obj = new ObjectInputStream(fis);
                try {
                    while (fis.available() != -1) {
                        //Read object from file
                        BookModel acc = (BookModel) obj.readObject();
                        listAccount.add(acc);
                    }
                } catch (EOFException ex) {
                    //ex.printStackTrace();
                }
            } catch (FileNotFoundException ex) {
                //ex.printStackTrace();
            } catch (IOException | ClassNotFoundException ex) {
                //ex.printStackTrace();
            }

            return listAccount;
        }
    }

}
