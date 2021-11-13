import javax.swing.*;
import java.awt.event.*;

public class LoginUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonLogin;
    private JButton buttonCancel;
    private JTextField textFieldLogin;
    private JTextField textFieldPassword;
    private JLabel labelLogin;
    private JLabel labelPassword;

    public LoginUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonLogin);

        buttonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void login() {
        String longin = textFieldLogin.getText();
        String password = textFieldPassword.getText();

        if (longin.equals("Arina") && password.equals("123456")) {
            dispose();
        }else{
            DialogMessage.showMessage("Неверный логин или пароль");
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
