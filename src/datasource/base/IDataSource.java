/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource.base;

import java.util.ArrayList;


public interface IDataSource<M> {
    public void writeObject(M obj);
    public void deleteAll();
    public void writeObjects(ArrayList<M> list);
    public ArrayList<M> readObject();
}
