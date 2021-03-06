/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.screen;

import islibrary.adapter.ComboBoxAdapter;
import islibrary.controller.callback.IIssuBookController;
import islibrary.dialog.DialogCalendar;
import islibrary.dialog.DialogMessage;
import islibrary.data.BookModel;
import islibrary.data.ReaderBookPair;
import islibrary.data.ReaderModel;
import islibrary.util.DataSaver;
import islibrary.util.Util;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author АРИНА
 */
public class IssueBook extends javax.swing.JFrame {

    ComboBoxAdapter<ReaderModel> adapter;
    BookModel book;
    
    IIssuBookController issuBookController;

    long returnDate;

    public IssueBook(BookModel book) {
        this.book = book;

        initComponents();

        adapter = new ComboBoxAdapter<>(comboBoxReaders);

        labelBookName.setText(book.name);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void close() {
        adapter = null;

        dispose();
    }

    void issueBook() {
        if (returnDate == 0) {
            DialogMessage.showMessage("Не выбрана дата!");
            return;
        }

        ReaderModel reader = adapter.getSelectedItem();

        if (reader.issuedBook == BookModel.bookLimit) {
            DialogMessage.showMessage("У читателя превышен лимит выднных книг!");
            return;
        }

        reader.issuedBook++;
        DataSaver.getInstance().readersDataSource.writeObject(reader);

        if (book.count <= 0) {
            DialogMessage.showMessage("Книг больше не осталось!");
            return;
        }

        book.count--;
        DataSaver.getInstance().bookDataSource.writeObject(book);

        long currentTime = Util.getCurrentDate();

        ReaderBookPair pair = new ReaderBookPair(reader.numberBilet, book.number, returnDate, currentTime, 0);
        
        issuBookController.saveNewPair(pair);
        
        close();
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
        labelBook = new javax.swing.JLabel();
        labelReader = new javax.swing.JLabel();
        labelDateOfIssue = new javax.swing.JLabel();
        buttonCancel = new javax.swing.JButton();
        buttomIssue = new javax.swing.JButton();
        labelBookName = new javax.swing.JLabel();
        comboBoxReaders = new javax.swing.JComboBox<>();
        buttonSelectDate = new javax.swing.JButton();
        labelReturnDate = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelBook.setText("Книга");

        labelReader.setText("Читатель");

        labelDateOfIssue.setText("Дата возврата");

        buttonCancel.setText("Отмена");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        buttomIssue.setText("Выдать");
        buttomIssue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttomIssueActionPerformed(evt);
            }
        });

        labelBookName.setText("jLabel1");

        comboBoxReaders.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonSelectDate.setText("Выбрать");
        buttonSelectDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSelectDateActionPerformed(evt);
            }
        });

        labelReturnDate.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelBook, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelReader, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelDateOfIssue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(35, 35, 35)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelBookName)
                            .addComponent(comboBoxReaders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonSelectDate)))
                        .addGap(183, 183, 183))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttomIssue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBook, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelBookName))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelReader, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxReaders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDateOfIssue, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSelectDate)
                    .addComponent(labelReturnDate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCancel)
                    .addComponent(buttomIssue))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        close();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttomIssueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttomIssueActionPerformed
        issueBook();
    }//GEN-LAST:event_buttomIssueActionPerformed

    private void buttonSelectDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSelectDateActionPerformed
        DialogCalendar.pickdate((long Date) -> {
            returnDate = Date;
            labelReturnDate.setText(Util.longToDateString(Date));
        });
    }//GEN-LAST:event_buttonSelectDateActionPerformed

    public void showList(ArrayList<ReaderModel> list) {
        adapter.initItems(list, (ReaderModel item) -> item.getFullname());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttomIssue;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonSelectDate;
    private javax.swing.JComboBox<String> comboBoxReaders;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelBook;
    private javax.swing.JLabel labelBookName;
    private javax.swing.JLabel labelDateOfIssue;
    private javax.swing.JLabel labelReader;
    private javax.swing.JLabel labelReturnDate;
    // End of variables declaration//GEN-END:variables
}
