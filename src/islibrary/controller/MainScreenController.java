/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.controller;

import islibrary.data.BookModel;
import islibrary.models.MainScreenModel;
import islibrary.screen.MainScreen;
import java.util.ArrayList;
import islibrary.controller.callback.IMainScreenController;
import islibrary.screen.AddBook;
import islibrary.screen.IssuedBooks;


public class MainScreenController implements IMainScreenController {

    MainScreen view;
    MainScreenModel model;
    Callback callback;

    public MainScreenController(
        MainScreen view, 
        MainScreenModel model,
        Callback callback
    ) {
        this.view = view;
        this.model = model;
        this.callback = callback;
        
        view.subscibe(this);
        resetList();
    }
    
    @Override
    public void onQuery(String query) {
        ArrayList<BookModel> list = model.getListByQuery(query);
        view.showList(list);
    }
    
    @Override
    public void resetList() {
        ArrayList<BookModel> list = model.getList();
        view.showList(list);
    }

    @Override
    public void addBook() {
        AddBook.showDialog(() -> { resetList(); });
    }

    @Override
    public void issueBook(BookModel book) {
        if(book.count > 0) {
            callback.showIssueBook(book);
        } else {
            callback.showMessage("Книг больше не осталось!");
        }
    }

    @Override
    public void deleteBook(int bookNumber) {
        model.deleteBook(bookNumber);
        resetList();
    }

    @Override
    public void editBook(BookModel book) {
        callback.showEditBook(book, () -> { resetList(); });
    }

    @Override
    public void showIssuedBooks() {
        callback.showIssuedBooks(() -> { resetList() ;});
    }

    @Override
    public void registerReader() {
        callback.showRegisterReder();
    }

    @Override
    public void showReadersList() {
        callback.showReadersList();
    }

    void registerCallback(Callback callback) {
        this.callback = callback;
    }
    
    static MainScreenController create(Callback callback) {
        MainScreen mainScreen = new MainScreen();
        MainScreenModel mainScreenModel = new MainScreenModel();
        
        return new MainScreenController(mainScreen, mainScreenModel, callback);
    }

    void showUi() {
        view.setVisible(true);
        view.setSize(680, 420);
        view.setTitle("Библиотека");
    }
    
    static public interface Callback {
        void showMessage(String string);
        
        void showIssueBook(BookModel book);
        
        void showEditBook(BookModel book, AddBook.ChangeBookCallback callback);
        
        void showIssuedBooks(IssuedBooks.OnIssueCallback callback);
        
        void showRegisterReder();
        
        void showReadersList();
    }
}
