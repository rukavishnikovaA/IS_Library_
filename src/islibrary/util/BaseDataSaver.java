/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


abstract class BaseDataSaver<M> {

    private String TAG = BaseDataSaver.class.getName();
    protected String fileName;

    public BaseDataSaver(String fileName) {
        this.fileName = fileName;
    }

    public void writeObject(M obj) {
        ArrayList<M> list = readObject();

        list.add(obj);
        writeObjects(list);
    }
    
    public void deleteAll() {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    public void writeObjects(ArrayList<M> list) {
        File file = new File(fileName);
        if (file.exists()) file.delete();

        try {
            //Write object to file
            //Create ObjectOutputStream to write object
            try ( //Create FileOutputStream to write file
                    FileOutputStream fos = new FileOutputStream(fileName)) {
                //Write object to file
                //Create ObjectOutputStream to write object
                try (ObjectOutputStream objOutputStream = new ObjectOutputStream(fos)) {
                    //Write object to file
                    for (M data : list) {
                        objOutputStream.writeObject(data);
                        objOutputStream.reset();
                    }
                }
            }
        } catch (IOException e) {
            // Do nothing
            
            System.err.println("e = " + e.getMessage());
        }
    }

    public ArrayList<M> readObject() {
        ArrayList<M> listAccount = new ArrayList();

        try {
            //Create new ObjectInputStream object to read object from file
            try (FileInputStream fis = new FileInputStream(fileName)) {
                //Create new ObjectInputStream object to read object from file
                ObjectInputStream obj = new ObjectInputStream(fis);
                try {
                    while (fis.available() != -1) {
                        //Read object from file
                        M acc = (M) obj.readObject();
                        listAccount.add(acc);
                    }
                } catch (EOFException ex) {
                    // Do nothing
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TAG).log(Level.FINE, "Файл не найден.");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
        }

        return listAccount;
    }
}
