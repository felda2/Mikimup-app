package main.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class LaporanFrame extends JFrame {

    public LaporanFrame() {
        setTitle("Laporan");
        setSize(500, 300);
        setLocationRelativeTo(null);

        add(new JLabel("Halaman laporan."));
    }
}