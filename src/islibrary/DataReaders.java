/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary;

/**
 *
 * @author АРИНА
 */
public class DataReaders {
    
    int numberBilet;
    String firstName;
    String secondName;
    String lastName;
    String adress;
    String numberPhone;
    long dateOfBirth;
    int limit;
    int issuedBook;
    
    DataReaders( int numberBilet,
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
}
