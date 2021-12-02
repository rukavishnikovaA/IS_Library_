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

/**
 *
 * @author Vladi
 */
abstract class IDataSaver<M> {

    protected String fileName;

    public IDataSaver(String fileName) {
        this.fileName = fileName;
    }

    public void writeObject(M obj) {
        ArrayList<M> list = readObject();

        list.add(obj);
        writeObjects(list);
    }

    public void writeObjects(ArrayList<M> list) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }

        try {
            //Create FileOutputStream to write file
            FileOutputStream fos = new FileOutputStream(fileName);
            //Create ObjectOutputStream to write object
            ObjectOutputStream objOutputStream = new ObjectOutputStream(fos);
            //Write object to file
            for (M data : list) {
                objOutputStream.writeObject(data);
                objOutputStream.reset();
            }
            objOutputStream.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public ArrayList<M> readObject() {
        ArrayList<M> listAccount = new ArrayList();

        try {
            FileInputStream fis = new FileInputStream(fileName);
            //Create new ObjectInputStream object to read object from file
            ObjectInputStream obj = new ObjectInputStream(fis);
            try {
                while (fis.available() != -1) {
                    //Read object from file
                    M acc = (M) obj.readObject();
                    listAccount.add(acc);
                }
            } catch (EOFException ex) {
                //Logger.getLogger(IDataSaver.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(IDataSaver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(IDataSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
        //ex.printStackTrace();

        return listAccount;
    }
}
