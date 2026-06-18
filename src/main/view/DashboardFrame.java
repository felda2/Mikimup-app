package main.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.model.User;

public class DashboardFrame extends JFrame {

    private User user;

    public DashboardFrame(User user) {
        this.user = user;

        setTitle("Mikimup - Dashboard");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xF8F8F8));

        JPanel contentArea = new JPanel();
        contentArea.setBackground(new Color(0xF8F8F8));
        contentArea.setLayout(new BoxLayout(contentArea, BoxLayout.Y_AXIS));

        JPanel centerCard = new JPanel();
        centerCard.setBackground(Color.WHITE);
        centerCard.setLayout(new BoxLayout(centerCard, BoxLayout.Y_AXIS));
        centerCard.setBorder(new EmptyBorder(40, 50, 40, 50));
        centerCard.setMaximumSize(new Dimension(720, 420));

        JLabel titleLabel = new JLabel(" MIKIMUP");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titleLabel.setForeground(new Color(0xE5395A));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome back, " + user.getUsername());
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        welcomeLabel.setForeground(new Color(0x4B5563));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Pilih Menu Yang Akan Dikelola");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(0x9CA3AF));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel menuPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setMaximumSize(new Dimension(600, 100));

        JButton barangButton = buatTombolMenu(" Produk");
        JButton transaksiButton = buatTombolMenu(" Transaksi");
        JButton laporanButton = buatTombolMenu(" Laporan");

        menuPanel.add(barangButton);
        menuPanel.add(transaksiButton);
        menuPanel.add(laporanButton);

        JButton logoutButton = buatLogoutButton();

        centerCard.add(titleLabel);
        centerCard.add(Box.createVerticalStrut(10));
        centerCard.add(welcomeLabel);
        centerCard.add(Box.createVerticalStrut(5));
        centerCard.add(subtitleLabel);
        centerCard.add(Box.createVerticalStrut(40));
        centerCard.add(menuPanel);
        centerCard.add(Box.createVerticalStrut(30));
        centerCard.add(logoutButton);

        contentArea.add(Box.createVerticalGlue());
        contentArea.add(centerCard);
        contentArea.add(Box.createVerticalGlue());

        mainPanel.add(contentArea, BorderLayout.CENTER);

        setContentPane(mainPanel);

        barangButton.addActionListener(e -> {
            new BarangFrame().setVisible(true);
        });

        transaksiButton.addActionListener(e -> {
            new TransaksiFrame().setVisible(true);
        });

        laporanButton.addActionListener(e -> {
            new LaporanFrame().setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Apakah Anda yakin ingin logout?",
                    "Konfirmasi Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private JButton buatTombolMenu(String teks) {

        JButton button = new JButton(teks) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(getBackground());
                g2.fillRoundRect(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        25,
                        25
                );

                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0xE5395A));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 90));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0xC92C4A));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0xE5395A));
            }
        });

        return button;
    }

    private JButton buatLogoutButton() {

        JButton button = new JButton("Logout");

        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(new Color(0xE5395A));
        button.setBackground(new Color(0xFCE4E8));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(140, 42));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(255, 220, 228));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0xFCE4E8));
            }
        });

        return button;
    }
}