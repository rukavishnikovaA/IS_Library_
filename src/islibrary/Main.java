package islibrary;

import islibrary.controller.AppController;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            AppController controll = new AppController();
            controll.start();
        });
    }
}
