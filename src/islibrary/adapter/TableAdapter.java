/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.adapter;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Vladi
 */
public class TableAdapter<M> {
    JTable table;
    ArrayList<M> list;
    
    public TableAdapter(JTable table) {
        this.table = table;
    }
    
    public void initItems(ArrayList<M> list, DefaultTableModel model, InitItemLambda<M> lamda) {
        this.list = list;
        
        list.forEach((item) -> {
            model.addRow(lamda.addRow(item));
        });
        
        table.setModel(model);
    }
    
    public M getSelectedModel() {
        int selectedRow = table.getSelectedRow();
        if(selectedRow == -1) selectedRow = 0;
        return list.get(selectedRow);
    }
    
    public interface InitItemLambda<I> {
        Object[] addRow(I model);
    }
}
