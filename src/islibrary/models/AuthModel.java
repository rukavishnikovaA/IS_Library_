/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.models;

public class AuthModel {
    private final String mLogin = "admin";
    private final String mPassword = "123456";
    
    public boolean checkAuth(String login, String password) {
        return login.equals(mLogin) && password.equals(mPassword);
    }
}
