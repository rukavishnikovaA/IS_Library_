/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adapter;

import java.util.ArrayList;
import javax.swing.JComboBox;

public class ComboBoxAdapter<I> {
    JComboBox<String> comboBox;
    ArrayList<I> list;
    
    public ComboBoxAdapter(JComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }
    
    public void initItems(ArrayList<I> list, InitListLambda<I> lambda) {
        this.list = list;
        comboBox.removeAllItems();
        
        for(I item: list) {
            String string = lambda.getString(item);
            comboBox.addItem(string);
        }
    }
    
    public I getSelectedItem() {
        int index = comboBox.getSelectedIndex();
        return list.get(index);
    }
    
    public interface InitListLambda<I> {
        String getString(I item);
    }
    
}
