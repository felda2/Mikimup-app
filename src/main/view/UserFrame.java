package main.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserFrame extends JFrame {

    public UserFrame() {
        setTitle("Kelola Pengguna");
        setSize(500, 300);
        setLocationRelativeTo(null);

        add(new JLabel("Halaman pengelolaan pengguna."));
    }
}