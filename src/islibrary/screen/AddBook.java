/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.screen;

import islibrary.adapter.ComboBoxAdapter;
import islibrary.dialog.DialogMessage;
import islibrary.data.BookModel;
import islibrary.util.DataSaver;
import islibrary.util.Util;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author АРИНА
 */
public final class AddBook extends javax.swing.JFrame {

    AddBookCallback addBookCallback;
    ChangeBookCallback changeBookCallback;

    ComboBoxAdapter<String> genryAdapter;
    ComboBoxAdapter<String> languageAdapter;

    /**
     * Creates new form AddBook
     *
     * @param callback
     */
    public AddBook(AddBookCallback callback) {
        this.addBookCallback = callback;
        initComponents();
        init();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public AddBook(ChangeBookCallback callback, BookModel model) {
        this.changeBookCallback = callback;
        initComponents();
        init();
        initComponents(model);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    void init() {
        genryAdapter = new ComboBoxAdapter<String>(comboBoxGenre);
        languageAdapter = new ComboBoxAdapter<String>(comboBoxLanguage);

        genryAdapter.initItems(getGenryList(), (item) -> {
            return item;
        });
        languageAdapter.initItems(getLanguagesList(), (item) -> {
            return item;
        });

        labelBookNumberValue.setText(Integer.toString(BookModel.getActualNumber()));
    }

    void initComponents(BookModel model) {
        textFieldNameOfBook.setText(model.name);
        textFieldAutor.setText(model.author);
        textFieldPublish.setText(model.publis);
        textFieldYearBook.setText(model.yearBook);

        genryAdapter.setSelectedItem(model.genery);
        languageAdapter.setSelectedItem(model.language);

        spinnerNumberBook.setValue(model.count);
        textFieldNumberPages.setText(Integer.toString(model.pages));
        labelBookNumberValue.setText(Integer.toString(model.number));
    }

    public ArrayList<String> getGenryList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Роман");
        list.add("Повесть");
        list.add("Рассказ");
        list.add("Комедия");
        list.add("Трагедия");
        list.add("Драма");
        list.add("Поэма");
        list.add("Пьеса");
        
        return list;
    }

    public ArrayList<String> getLanguagesList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Русский");
        list.add("Английский");
        list.add("Французский");
        list.add("Немецкий");
        return list;
    }

    private void saveBook() {
        if (textFieldAutor.getText().isEmpty()) {
            DialogMessage.showMessage("Поле Автор не может быть пустым!");
            return;
        }
        if (!Util.isInteger(textFieldNumberPages.getText())) {
            DialogMessage.showMessage("Введите корректно количество страниц!");
            return;
        }
        int pages = Integer.parseInt(textFieldNumberPages.getText());
        if (pages <= 0) {
            DialogMessage.showMessage("Введите корректно количество страниц!");
            return;
        }
        int spinValue = (int) spinnerNumberBook.getValue();

        if (isEditMode()) {
            if (spinValue < 0) {
                DialogMessage.showMessage("Количество книг должно быть больше или равно 0!");
                return;
            }
        } else {
            if (spinValue <= 0) {
                DialogMessage.showMessage("Количество книг должно быть больше нуля!");
                return;
            }
        }
        
        int number;
        if(isEditMode()) {
            number = Integer.parseInt(labelBookNumberValue.getText());
        } else {
            number = BookModel.getActualNumber();
        }

        BookModel book = new BookModel(
                number,
                textFieldNameOfBook.getText(),
                textFieldAutor.getText(),
                textFieldPublish.getText(),
                textFieldYearBook.getText(),
                comboBoxGenre.getSelectedItem().toString(),
                comboBoxLanguage.getSelectedItem().toString(),
                spinValue,
                pages
        );

        DataSaver.getInstance().bookDataSource.writeObject(book);
        dispose();

        if (isEditMode()) {
            changeBookCallback.onBookChanged();
            changeBookCallback = null;
        } else {
            addBookCallback.onAddNewBook();
            addBookCallback = null;
        }
    }

    boolean isEditMode() {
        return addBookCallback == null;
    }

    public static void showDialog(AddBookCallback callback) {
        AddBook dialog = new AddBook(callback);
        dialog.setVisible(true);
        dialog.setSize(550, 360);
        dialog.setTitle("Добавление новой книги");
    }

    public static void showDialog(BookModel book, ChangeBookCallback callback) {
        AddBook dialog = new AddBook(callback, book);
        dialog.setVisible(true);
        dialog.setSize(550, 360);
        dialog.setTitle("Изменение книги");
    }

    public interface AddBookCallback {

        void onAddNewBook();
    }

    public interface ChangeBookCallback {

        void onBookChanged();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField5 = new javax.swing.JTextField();
        labelNameOfBook = new javax.swing.JLabel();
        textFieldNameOfBook = new javax.swing.JTextField();
        labelAutor = new javax.swing.JLabel();
        textFieldAutor = new javax.swing.JTextField();
        labelPublish = new javax.swing.JLabel();
        textFieldPublish = new javax.swing.JTextField();
        labelYearBook = new javax.swing.JLabel();
        textFieldYearBook = new javax.swing.JTextField();
        labelGenre = new javax.swing.JLabel();
        comboBoxGenre = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        comboBoxLanguage = new javax.swing.JComboBox<>();
        labelNumberBook = new javax.swing.JLabel();
        spinnerNumberBook = new javax.swing.JSpinner();
        labelNumberPages = new javax.swing.JLabel();
        textFieldNumberPages = new javax.swing.JTextField();
        buttonSave = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        labeBookNumber = new javax.swing.JLabel();
        labelBookNumberValue = new javax.swing.JLabel();

        jTextField5.setText("jTextField5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelNameOfBook.setText("Наименование");

        labelAutor.setText("Автор");

        labelPublish.setText("Издательство");

        labelYearBook.setText("Год выпуска");

        labelGenre.setText("Жанр");

        comboBoxGenre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Язык");

        comboBoxLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        labelNumberBook.setText("Количество");

        labelNumberPages.setText("Количество страниц");

        textFieldNumberPages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNumberPagesActionPerformed(evt);
            }
        });

        buttonSave.setText("Сохранить");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        buttonCancel.setText("Отмена");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        labeBookNumber.setText("Номер");

        labelBookNumberValue.setText("1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelYearBook, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelPublish, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelAutor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelNameOfBook, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                            .addComponent(labeBookNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textFieldNameOfBook)
                            .addComponent(textFieldAutor)
                            .addComponent(textFieldPublish, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(textFieldYearBook, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelBookNumberValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelNumberBook)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinnerNumberBook, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelNumberPages)
                            .addGap(18, 18, 18)
                            .addComponent(textFieldNumberPages, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(labelGenre, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(6, 6, 6)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(comboBoxLanguage, 0, 139, Short.MAX_VALUE)
                                .addComponent(comboBoxGenre, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labeBookNumber)
                    .addComponent(labelBookNumberValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNameOfBook)
                    .addComponent(textFieldNameOfBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAutor)
                    .addComponent(textFieldAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPublish)
                    .addComponent(textFieldPublish, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelYearBook)
                    .addComponent(textFieldYearBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelGenre)
                    .addComponent(comboBoxGenre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(comboBoxLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumberBook)
                    .addComponent(spinnerNumberBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNumberPages)
                    .addComponent(textFieldNumberPages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonSave)
                    .addComponent(buttonCancel))
                .addContainerGap(184, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldNumberPagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldNumberPagesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldNumberPagesActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        saveBook();
    }//GEN-LAST:event_buttonSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonSave;
    private javax.swing.JComboBox<String> comboBoxGenre;
    private javax.swing.JComboBox<String> comboBoxLanguage;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel labeBookNumber;
    private javax.swing.JLabel labelAutor;
    private javax.swing.JLabel labelBookNumberValue;
    private javax.swing.JLabel labelGenre;
    private javax.swing.JLabel labelNameOfBook;
    private javax.swing.JLabel labelNumberBook;
    private javax.swing.JLabel labelNumberPages;
    private javax.swing.JLabel labelPublish;
    private javax.swing.JLabel labelYearBook;
    private javax.swing.JSpinner spinnerNumberBook;
    private javax.swing.JTextField textFieldAutor;
    private javax.swing.JTextField textFieldNameOfBook;
    private javax.swing.JTextField textFieldNumberPages;
    private javax.swing.JTextField textFieldPublish;
    private javax.swing.JTextField textFieldYearBook;
    // End of variables declaration//GEN-END:variables
}
