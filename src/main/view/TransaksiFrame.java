package main.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TransaksiFrame extends JFrame {

    public TransaksiFrame() {
        setTitle("Transaksi");
        setSize(500, 300);
        setLocationRelativeTo(null);

        add(new JLabel("Halaman transaksi."));
    }
}