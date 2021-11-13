import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogMessage extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel success;

    public DialogMessage() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }
    static public void showMessage (String message) {
        DialogMessage dialog = new DialogMessage();
        dialog.setMessage(message);
        dialog.setVisible(true);
    }

    private void setMessage(String message) {
        success.setText(message);
    }

    private void onOK() {
        // add your code here
        dispose();
    }
}
