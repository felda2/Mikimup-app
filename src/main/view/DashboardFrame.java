package main.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("Dashboard");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        add(new JLabel("Selamat datang di aplikasi."));

        JButton barangButton = new JButton("Kelola Barang");
        JButton transaksiButton = new JButton("Transaksi");
        JButton laporanButton = new JButton("Laporan");

        add(barangButton);
        add(transaksiButton);
        add(laporanButton);

        barangButton.addActionListener(e -> {
            BarangFrame barangFrame = new BarangFrame();
            barangFrame.setVisible(true);
        });

        transaksiButton.addActionListener(e -> {
            TransaksiFrame transaksiFrame = new TransaksiFrame();
            transaksiFrame.setVisible(true);
        });

        laporanButton.addActionListener(e -> {
            LaporanFrame laporanFrame = new LaporanFrame();
            laporanFrame.setVisible(true);
        });
    }
}