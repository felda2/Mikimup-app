package main.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.model.User;
import main.util.AppTheme;

public class DashboardFrame extends JFrame {

    private User user;
    private JPanel contentArea;

    public DashboardFrame(User user) {
        this.user = user;

        setTitle("Mikimup - Dashboard");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BG_GRAY);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(AppTheme.PRIMARY_RED);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(240, 0));

        // Header sidebar
        JPanel sidebarHeader = new JPanel();
        sidebarHeader.setBackground(AppTheme.DARK_RED);
        sidebarHeader.setLayout(new BoxLayout(sidebarHeader, BoxLayout.Y_AXIS));
        sidebarHeader.setBorder(new EmptyBorder(30, 20, 30, 20));
        sidebarHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        sidebarHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appName = new JLabel("🍣  Mikimup");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 22));
        appName.setForeground(AppTheme.WHITE);
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep1 = new JSeparator();
        sep1.setForeground(new Color(255, 255, 255, 50));
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JLabel usernameLabel = new JLabel("👤  " + user.getUsername());
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        usernameLabel.setForeground(AppTheme.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel(user.getRole());
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        roleLabel.setForeground(AppTheme.SOFT_PINK);
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebarHeader.add(appName);
        sidebarHeader.add(Box.createVerticalStrut(12));
        sidebarHeader.add(sep1);
        sidebarHeader.add(Box.createVerticalStrut(12));
        sidebarHeader.add(usernameLabel);
        sidebarHeader.add(Box.createVerticalStrut(4));
        sidebarHeader.add(roleLabel);

        // Menu items
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(AppTheme.PRIMARY_RED);
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(new EmptyBorder(20, 16, 20, 16));

        JLabel menuLabel = new JLabel("MENU");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        menuLabel.setForeground(AppTheme.SOFT_PINK);
        menuLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuLabel.setBorder(new EmptyBorder(0, 8, 8, 0));

        JButton barangButton = buatTombolSidebar("🛒  Kelola Barang");
        JButton transaksiButton = buatTombolSidebar("💳  Transaksi");
        JButton laporanButton = buatTombolSidebar("📊  Laporan");

        menuPanel.add(menuLabel);
        menuPanel.add(barangButton);
        menuPanel.add(Box.createVerticalStrut(6));
        menuPanel.add(transaksiButton);
        menuPanel.add(Box.createVerticalStrut(6));
        menuPanel.add(laporanButton);

        // Logout
        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(AppTheme.PRIMARY_RED);
        logoutPanel.setLayout(new BoxLayout(logoutPanel, BoxLayout.Y_AXIS));
        logoutPanel.setBorder(new EmptyBorder(10, 16, 24, 16));

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(AppTheme.DARK_RED);
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JButton logoutButton = new JButton("🚪  Logout") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        logoutButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutButton.setBackground(new Color(0x7F, 0x1D, 0x1D));
        logoutButton.setForeground(AppTheme.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setOpaque(false);
        logoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                logoutButton.setBackground(new Color(0x99, 0x1B, 0x1B));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                logoutButton.setBackground(new Color(0x7F, 0x1D, 0x1D));
            }
        });

        logoutPanel.add(sep2);
        logoutPanel.add(Box.createVerticalStrut(12));
        logoutPanel.add(logoutButton);

        sidebar.add(sidebarHeader);
        sidebar.add(menuPanel);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutPanel);

        // ===== CONTENT AREA =====
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(AppTheme.BG_GRAY);
        contentArea.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel welcomeContent = new JLabel("👋  Selamat datang, " + user.getUsername() + "!");
        welcomeContent.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeContent.setForeground(AppTheme.TEXT_DARK);
        welcomeContent.setHorizontalAlignment(SwingConstants.CENTER);
        contentArea.add(welcomeContent, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);
        setContentPane(mainPanel);

        // Aksi tombol
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

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                dispose();
            }
        });
    }

    private JButton buatTombolSidebar(String teks) {
        JButton button = new JButton(teks);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(AppTheme.PRIMARY_RED);
        button.setForeground(AppTheme.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(8, 12, 8, 12));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(AppTheme.DARK_RED);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 0, 0, AppTheme.WHITE),
                    new EmptyBorder(8, 8, 8, 12)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(AppTheme.PRIMARY_RED);
                button.setBorder(new EmptyBorder(8, 12, 8, 12));
            }
        });

        return button;
    }
}