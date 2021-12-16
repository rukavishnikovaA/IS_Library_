/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource.base;

import islibrary.data.Unique;

/**
 *
 * @author Vladi
 */
public abstract class IUniqueDataSorce<M extends Unique> implements IDataSource<M> {
    abstract public M findModelByNumber(String number);
    abstract public void deleteByNumber(String number);
    public void deleteModel(M model) {
        deleteByNumber(model.getUniqueNumber());
    }
}
