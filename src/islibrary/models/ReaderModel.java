/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

import islibrary.util.DataSaver;
import islibrary.util.Util;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author АРИНА
 */
public class ReaderModel implements Serializable {

    public int numberBilet;
    public String firstName;
    public String secondName;
    public String lastName;
    public String adress;
    public String numberPhone;
    public long dateOfBirth;
    public int limit;
    public int issuedBook;

    public ReaderModel(int numberBilet,
            String firstName,
            String secondName,
            String lastName,
            String adress,
            String numberPhone,
            long dateOfBirth,
            int limit,
            int issuedBook) {
        this.adress = adress;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.issuedBook = issuedBook;
        this.lastName = lastName;
        this.limit = limit;
        this.numberBilet = numberBilet;
        this.numberPhone = numberPhone;
        this.secondName = secondName;
    }

    public boolean isMathByQuery(String query) {
        return firstName.contains(query)
                || secondName.contains(query)
                || lastName.contains(query)
                || adress.contains(query)
                || numberPhone.contains(query)
                || Integer.toString(numberBilet).contains(query)
                || Integer.toString(limit).contains(query)
                || Integer.toString(issuedBook).contains(query)
                || Util.longToDateString(dateOfBirth).contains(query);
    }

    public String getFullname() {
        return firstName + " " + secondName + " " + lastName;
    }

    public static ReaderModel getFromListByNumberBilet(int number, ArrayList<ReaderModel> list) {
        for (ReaderModel r : list) {
            if (r.numberBilet == number) {
                return r;
            }
        }

        return null;
    }

    public static int getActualBiletNumber() {
        ArrayList<ReaderModel> list = DataSaver.ReadersModelSaver.getInstance().readObject();
        if (list.isEmpty()) {
            return 0;
        } else {
            int maxNumber = 0;
            for (ReaderModel reader : list) {
                if (reader.numberBilet > maxNumber) {
                    maxNumber = reader.numberBilet;
                }
            }

            return maxNumber + 1;
        }

    }

}
