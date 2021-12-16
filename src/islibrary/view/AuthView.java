package islibrary.view;

import javax.swing.JButton;
import javax.swing.JTextField;

public interface AuthView {
    JTextField getLoginTextField();
    JTextField getPasswordTextField();
    JButton getCancelButton();
    JButton getAuthButton();
}
