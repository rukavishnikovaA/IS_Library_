/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.controller;

import islibrary.controller.callback.IIssuBookController;
import islibrary.data.BookModel;
import islibrary.data.ReaderBookPair;
import islibrary.data.ReaderModel;
import islibrary.models.IssueBookModel;
import islibrary.screen.IssueBook;
import java.util.ArrayList;


public class IssueBookController implements IIssuBookController {
    
    Callback callback;
    IssueBookModel model;
    IssueBook view;
    
    IssueBookController(IssueBook view, IssueBookModel model, Callback callback) {
        this.view = view;
        this.model = model;
        this.callback = callback;
        
        ArrayList<ReaderModel> list = model.getReaders();
        view.showList(list);
    }

    @Override
    public void saveNewPair(ReaderBookPair pair) {
        if (pair.isExist()) {
            callback.showMessage("У читателя уже имеется данная книга!");
        } else {
            model.savePair(pair);
        }

        callback.onBookIssued();
    }
    
    void showUi() {
        view.setVisible(true);
        view.setSize(500, 250);
        view.setTitle("Выдача книги");
    }
    
    public static IssueBookController create(BookModel book, Callback callback) {
        IssueBook view = new IssueBook(book);
        IssueBookModel model = new IssueBookModel();
        
        return new IssueBookController(view, model, callback);
    }
    
    public static interface Callback {

        public void showMessage(String msg);

        public void onBookIssued();
        
    }
}
