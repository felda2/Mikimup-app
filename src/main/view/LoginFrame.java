package main.view;

import javax.swing.*;
import java.awt.*;
import main.model.User;
import main.service.AuthService;

public class LoginFrame extends JFrame {
    private JTextField txtUsername = new JTextField(15);
    private JPasswordField txtPassword = new JPasswordField(15);
    private JButton btnLogin = new JButton("Login");
    private JButton btnDaftar = new JButton("Belum punya akun? Daftar");
    
    private AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Login System - MIKIMUP");
        setSize(350, 250); // Tinggi ditambah sedikit untuk tombol daftar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- UI Layout ---
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        // Tombol Login
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnLogin, gbc);

        // Tombol Daftar
        gbc.gridy = 3;
        add(btnDaftar, gbc);

        // --- Action Listeners ---
        btnLogin.addActionListener(e -> prosesLogin());
        
        btnDaftar.addActionListener(e -> {
            new RegisterFrame(); // Membuka frame registrasi
            this.dispose();      // Menutup login
        });

        setVisible(true);
    }

    private void prosesLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!");
            return;
        }

        User loggedInUser = authService.loginUser(username, password);

        if (loggedInUser != null) {
            JOptionPane.showMessageDialog(this, "Selamat Datang, " + loggedInUser.getUsername());
            
            // Mengirim data user ke Dashboard
            new DashboardFrame(loggedInUser).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login Gagal! Username atau Password salah.", 
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}