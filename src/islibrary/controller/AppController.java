/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.controller;

import islibrary.data.BookModel;
import islibrary.dialog.DialogMessage;
import islibrary.screen.AddBook;
import islibrary.screen.IssueBook;
import islibrary.screen.IssuedBooks;


public class AppController {
    
    AddBookController addBookController;
    AuthController authController;
    IssueBookController issueBookController;
    MainScreenController mainScreenController;
    ReadersListController readersListController;
    RegisterReaderController registerReaderController;
    
    AppController(
            AuthController authController,
            MainScreenController mainScreenController
    ) {
        this.authController = authController;
        this.mainScreenController = mainScreenController;
        
        //------
        authController.registerCallback(new AuthController.Callback() {
            @Override
            public void goNext() {
                mainScreen();
            }

            @Override
            public void showMessage(String msg) {
                showMsg(msg);
            }
            
        });
        
        mainScreenController.registerCallback(new MainScreenController.Callback() {
            @Override
            public void showMessage(String string) {
                showMsg(string);
            }

            @Override
            public void showIssueBook(BookModel book, IssueBook.Callback callback) {
                
            }

            @Override
            public void showEditBook(BookModel book, AddBook.ChangeBookCallback callback) {
                
            }

            @Override
            public void showIssuedBooks(IssuedBooks.OnIssueCallback callback) {
                
            }

            @Override
            public void showRegisterReder() {
                
            }

            @Override
            public void showReadersList() {
                
            }
        });
    }
    
    public void start() {
        authController.showUi();
    }
    
    void showMsg(String msg) {
        DialogMessage.showMessage(msg);
    }
    
    void mainScreen() {
        mainScreenController.showUi();
    }
    
    public static AppController create() {
        AuthController authController = AuthController.create();
        MainScreenController mainScreenController = MainScreenController.create();
        
        return new AppController(authController, mainScreenController);
    }
}
