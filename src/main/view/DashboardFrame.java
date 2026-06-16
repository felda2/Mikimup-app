package main.view;

import javax.swing.*;
import java.awt.*;
import main.model.User;

public class DashboardFrame extends JFrame {
    
    private User currentUser;

    public DashboardFrame(User user) {
        this.currentUser = user; // Menyimpan data user yang sedang login

        setTitle("Dashboard MIKIMUP - " + currentUser.getRole());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JLabel lblWelcome = new JLabel("Halo, " + currentUser.getUsername() + 
                                       " (Role: " + currentUser.getRole() + ")", SwingConstants.CENTER);
        add(lblWelcome, BorderLayout.NORTH);

        // Panel Menu Utama
        JPanel panelMenu = new JPanel();
        
        // Logika tampilan menu berdasarkan role
        if (currentUser.getRole().equalsIgnoreCase("owner")) {
            panelMenu.add(new JButton("Lihat Laporan Keuangan"));
            panelMenu.add(new JButton("Manajemen User"));
        } else {
            panelMenu.add(new JButton("Input Transaksi"));
            panelMenu.add(new JButton("Cetak Struk"));
        }
        
        add(panelMenu, BorderLayout.CENTER);
        
        // Tombol Logout
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
        add(btnLogout, BorderLayout.SOUTH);

        setVisible(true);
    }
}