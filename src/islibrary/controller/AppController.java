/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.controller;

import islibrary.data.BookModel;
import islibrary.dialog.DialogMessage;
import islibrary.screen.AddBook;
import islibrary.screen.IssuedBooks;

public class AppController {

    AddBookController addBookController;
    AuthController authController;
    MainScreenController mainScreenController;
    ReadersListController readersListController;
    RegisterReaderController registerReaderController;

    public AppController() {
        authController = AuthController.create(new AuthController.Callback() {
            @Override
            public void goNext() {
                mainScreen();
            }

            @Override
            public void showMessage(String msg) {
                showMsg(msg);
            }
        });
        
        mainScreenController = MainScreenController.create(new MainScreenController.Callback() {
            @Override
            public void showMessage(String string) {
                showMsg(string);
            }

            @Override
            public void showIssueBook(BookModel book) {
                issueBook(book);
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

    void issueBook(BookModel model) {
        IssueBookController issueBookController = IssueBookController.create(
            model,
            new IssueBookController.Callback() {
                @Override
                public void showMessage(String msg) {
                    showMsg(msg);
                }

                @Override
                public void onBookIssued() {
                    mainScreenController.resetList();
                }
        });

        issueBookController.showUi();
    }
}
