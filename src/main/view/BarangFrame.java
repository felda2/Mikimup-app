package main.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class BarangFrame extends JFrame {

    public BarangFrame() {
        setTitle("Kelola Barang");
        setSize(500, 300);
        setLocationRelativeTo(null);

        add(new JLabel("Halaman pengelolaan barang."));
    }
}