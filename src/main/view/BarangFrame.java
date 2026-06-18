package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BarangFrame extends JFrame {

    public BarangFrame() {
        setTitle("Kelola Barang");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Halaman pengelolaan barang.", JLabel.CENTER);

        JButton kembaliButton = new JButton("Kembali ke Dashboard");
        kembaliButton.setBackground(Color.decode("#E5395A"));
        kembaliButton.setForeground(Color.WHITE);
        kembaliButton.setFocusPainted(false);

        kembaliButton.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            dispose();
        });

        JPanel panelBawah = new JPanel();
        panelBawah.add(kembaliButton);

        add(label, BorderLayout.CENTER);
        add(panelBawah, BorderLayout.SOUTH);
    }
}