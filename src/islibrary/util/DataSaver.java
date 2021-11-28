/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

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

/**
 *
 * @author Vladi
 */
public class DataSaver {

    static public void writeObject(DataReaders obj) throws IOException, ClassNotFoundException {
        ArrayList<DataReaders> list = readObject();
        list.add(obj);

        //Create FileOutputStream to write file
        File file = new File("dataReaders.txt");
        if(file.exists()) file.delete();
        
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
