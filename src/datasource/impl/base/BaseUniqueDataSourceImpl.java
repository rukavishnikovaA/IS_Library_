/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource.impl.base;

import datasource.base.IUniqueDataSorce;
import islibrary.data.Unique;
import java.util.ArrayList;
/**
 *
 * @author Vladi
 */
public class BaseUniqueDataSourceImpl<M extends Unique> extends IUniqueDataSorce<M> {

    private String fileName;
    private final BaseDataSourceImpl<M> delegate;

    public BaseUniqueDataSourceImpl(String fileName) {
        this.fileName = fileName;
        this.delegate = new BaseDataSourceImpl<>(fileName);
    }

    @Override
    public M findModelByNumber(String number) {

        ArrayList<M> list = readObject();

        for (M obj : list) {
            if (obj.getUniqueNumber().equals(number)) {
                return obj;
            }
        }

        return null;
    }

    @Override
    public void deleteByNumber(String number) {
        M model = findModelByNumber(number);
        if(model != null) {
            ArrayList<M> savedList = new ArrayList<M>();
            ArrayList<M> list = readObject();
            for(M m: list) {
                if(!m.getUniqueNumber().equals(model.getUniqueNumber())) {
                    savedList.add(m);
                }
            }
            
            writeObjects(savedList);
        }
    }

    @Override
    public void writeObject(M obj) {
        deleteByNumber(obj.getUniqueNumber());
        delegate.writeObject(obj);
    }

    @Override
    public void deleteAll() {
        delegate.deleteAll();
    }

    @Override
    public void writeObjects(ArrayList<M> list) {
        delegate.writeObjects(list);
    }

    @Override
    public ArrayList<M> readObject() {
        return delegate.readObject();
    }

}
