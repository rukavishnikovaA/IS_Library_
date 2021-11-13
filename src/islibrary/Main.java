package islibrary;

public class Main {
    public static void main(String[] args) {
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginUI dialog = new LoginUI();
        dialog.pack();
        dialog.setVisible(true);
                
            }
        });
        
    }
}
