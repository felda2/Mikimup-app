package main.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.model.User;
import main.service.AuthService;
import main.util.AppTheme;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private AuthService authService;

    public LoginFrame() {
        authService = new AuthService();

        setTitle("Mikimup - Login");
        setSize(420, 570);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(AppTheme.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 40, 40));

        // Gambar sushi proporsional
        JLabel imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon icon = new ImageIcon("D:\\Mikimup-app\\resources\\sushi.png");
        int oriWidth = icon.getIconWidth();
        int oriHeight = icon.getIconHeight();
        int targetHeight = 120;
        int targetWidth = (int) ((double) oriWidth / oriHeight * targetHeight);
        Image scaled = icon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));

        // Judul
        JLabel titleLabel = new JLabel("Welcome Back!");
        titleLabel.setFont(AppTheme.FONT_TITLE);
        titleLabel.setForeground(AppTheme.PRIMARY_RED);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subjudul
        JLabel subLabel = new JLabel("Masukkan username dan password untuk melanjutkan");
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLabel.setForeground(AppTheme.TEXT_SECONDARY);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label & field username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(AppTheme.FONT_LABEL);
        usernameLabel.setForeground(AppTheme.TEXT_DARK);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new JTextField();
        usernameField.setFont(AppTheme.FONT_INPUT);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER_GRAY, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Label & field password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(AppTheme.FONT_LABEL);
        passwordLabel.setForeground(AppTheme.TEXT_DARK);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField();
        passwordField.setFont(AppTheme.FONT_INPUT);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER_GRAY, 1, true),
                new EmptyBorder(5, 10, 5, 10)
        ));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tombol login
        loginButton = new JButton("Continue");
        loginButton.setFont(AppTheme.FONT_BUTTON);
        loginButton.setBackground(AppTheme.PRIMARY_RED);
        loginButton.setForeground(AppTheme.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                loginButton.setBackground(AppTheme.DARK_RED);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                loginButton.setBackground(AppTheme.PRIMARY_RED);
            }
        });

        loginButton.addActionListener(e -> prosesLogin());

        // Susun komponen
        mainPanel.add(imageLabel);
        mainPanel.add(Box.createVerticalStrut(16));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(subLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(usernameLabel);
        mainPanel.add(Box.createVerticalStrut(6));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createVerticalStrut(16));
        mainPanel.add(passwordLabel);
        mainPanel.add(Box.createVerticalStrut(6));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createVerticalStrut(24));
        mainPanel.add(loginButton);

        setContentPane(mainPanel);
    }

    private void prosesLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username dan Password harus diisi!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        User user = authService.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Login berhasil sebagai " + user.getRole(),
                    "Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
            );
            DashboardFrame dashboardFrame = new DashboardFrame(user);
            dashboardFrame.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Username atau Password salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}