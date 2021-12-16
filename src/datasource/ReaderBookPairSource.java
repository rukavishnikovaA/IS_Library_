/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datasource;

import datasource.impl.base.BaseUniqueDataSourceImpl;
import islibrary.data.ReaderBookPair;


public class ReaderBookPairSource extends BaseUniqueDataSourceImpl<ReaderBookPair> {
    public ReaderBookPairSource() {
        super("ReaderBookPairSource");
    }
}
