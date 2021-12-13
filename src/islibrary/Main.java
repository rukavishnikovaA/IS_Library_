package islibrary;

import islibrary.screen.LoginUI;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            LoginUI dialog = new LoginUI();
            dialog.pack();
            dialog.setVisible(true);
            dialog.setSize(400, 300);
        });
    }
}
