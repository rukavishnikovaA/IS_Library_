/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.controller.callback;

import islibrary.data.BookModel;


public interface IMainScreenController {
    public void resetList();
    
    public void onQuery(String query);

    public void addBook();

    public void issueBook(BookModel book);

    public void deleteBook(int bookNumber);

    public void editBook(BookModel book);

    public void showIssuedBooks();

    public void registerReader();

    public void showReadersList();
}
