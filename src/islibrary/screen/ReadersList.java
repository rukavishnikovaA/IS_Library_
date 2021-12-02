/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.screen;

import islibrary.models.DataReaders;
import islibrary.util.DataSaver;
import islibrary.util.Util;
import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author АРИНА
 */
public class ReadersList extends javax.swing.JFrame {

    /**
     * Creates new form ReadersList
     */
    public ReadersList() {
        initComponents();
        showList("");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    
    private void showList(String query) {
        ArrayList<DataReaders> list = getListByQuery(query);
        
        DefaultTableModel modelTable = new DefaultTableModel(new Object[]{"Имя", "Отчество", "Фамилия", "Номер билета", "Телефон", "Дата рождения", "Адрес"}, 0);
        jTable1.setModel(modelTable);

        list.forEach(data -> {
            modelTable.addRow(new Object[]{
                data.firstName,
                data.secondName,
                data.lastName,
                data.numberBilet,
                data.numberPhone, 
                Util.longToDateString(data.dateOfBirth) ,
                data.adress
            });
        });
    }
    
    private ArrayList<DataReaders> getListByQuery(String query) {
        ArrayList<DataReaders> list = GetReaders();
        ArrayList<DataReaders> result = new ArrayList<>();
        
        list.forEach((data) -> {
            if(data.isMathByQuery(query)) result.add(data);
        });
        
        return result; 
    }
    
    private DataReaders getSelectedReader() {
        int selectedRow = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Vector data = model.getDataVector();
        Vector r = (Vector) data.elementAt(selectedRow);
        int number_bilet = (int) r.get(3);
        ArrayList<DataReaders> list = getListByQuery("");
        return DataReaders.getFromListByNumberBilet(number_bilet, list);
    }
    
    private void editSeletedReader() {
        DataReaders reader = getSelectedReader();  
        RegisterReader.showDialog(reader, () -> {
            showList("");
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        textFieldSearchReaders = new javax.swing.JTextField();
        labelSearch = new javax.swing.JLabel();
        buttonSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonCoice = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelSearch.setText("Поиск");

        buttonSearch.setText("Найти");
        buttonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        buttonCoice.setText("Изменить");
        buttonCoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCoiceActionPerformed(evt);
            }
        });

        buttonCancel.setText("Отмена");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldSearchReaders, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonCoice)
                        .addGap(15, 15, 15)
                        .addComponent(buttonCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldSearchReaders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCoice)
                    .addComponent(buttonCancel))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchActionPerformed
        String query = textFieldSearchReaders.getText();
        showList(query);
    }//GEN-LAST:event_buttonSearchActionPerformed

    private void buttonCoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCoiceActionPerformed
        editSeletedReader();
    }//GEN-LAST:event_buttonCoiceActionPerformed
    public static void showDialog() {
        ReadersList readerList = new ReadersList();
        readerList.setVisible(true);
        readerList.setSize(700, 400);
        readerList.setTitle("Читатели");
    }

    ArrayList<DataReaders> GetReaders() {
        try {
            return DataSaver.DataReadersSaver.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadersList.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadersList.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ArrayList<>();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonCoice;
    private javax.swing.JButton buttonSearch;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JTextField textFieldSearchReaders;
    // End of variables declaration//GEN-END:variables
}
