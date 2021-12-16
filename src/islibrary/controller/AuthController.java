/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.controller;

import islibrary.controller.callback.AuthCallback;
import islibrary.models.AuthModel;
import islibrary.screen.LoginUI;

public class AuthController implements AuthCallback {
    AuthModel model;
    LoginUI view;
    Callback callback;
    
    public AuthController(AuthModel model, LoginUI view) {
        this.view = view;
        this.model = model;
        
        view.subscribe(this);
    }

    void registerCallback(Callback callback) {
        this.callback = callback;
    }
    
    public void showUi() {
        view.pack();
        view.setVisible(true);
        view.setSize(400, 300);
    }

    @Override
    public void onAuth(String login, String password) {
        if(model.checkAuth(login, password)) {
            callback.goNext();
            view.dispose();
        } else {
            callback.showMessage("Неверный логин или пароль");
        }
    }
    
    public static AuthController create()  {
        LoginUI view = new LoginUI();
        AuthModel model = new AuthModel();
        
        return new AuthController(model, view);
    }
   
    static interface Callback {
        void goNext();
        void showMessage(String msg);
    }
}
