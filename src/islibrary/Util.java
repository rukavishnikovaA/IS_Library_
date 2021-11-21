/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author АРИНА
 */
public class Util {
    public static boolean isInteger(String s) {
    return isInteger(s,10);
}

public static boolean isInteger(String s, int radix) {
    if(s.isEmpty()) return false;
    for(int i = 0; i < s.length(); i++) {
        if(i == 0 && s.charAt(i) == '-') {
            if(s.length() == 1) return false;
            else continue;
        }
        if(Character.digit(s.charAt(i),radix) < 0) return false;
    }
    return true;
}
public static void serializeDataOut(DataReaders ish)throws IOException{
    String fileName= "dataReaders.txt";
    FileOutputStream fos = new FileOutputStream(fileName);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(ish);
    oos.close();
}

public static DataReaders serializeDataIn() throws IOException, ClassNotFoundException{
   String fileName= "dataReaders.txt";
   FileInputStream fin = new FileInputStream(fileName);
   ObjectInputStream ois = new ObjectInputStream(fin);
   DataReaders iHandler= (DataReaders) ois.readObject();
   ois.close();
   return iHandler;
}

public static String longToDateString (long Date){
    Date date=new Date(Date);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);
        return dateText;
}
}
