package main.view;

import javax.swing.*;
import java.awt.*;
import main.model.User;
import main.service.AuthService;

public class RegisterFrame extends JFrame {
    private JTextField txtUsername = new JTextField(15);
    private JPasswordField txtPassword = new JPasswordField(15);
    private JTextField txtNama = new JTextField(15);
    private JComboBox<String> cbRole = new JComboBox<>(new String[]{"admin_kasir", "owner"});
    private JButton btnRegister = new JButton("Daftar");
    private AuthService authService = new AuthService();

    public RegisterFrame() {
        setTitle("Registrasi Pengguna Baru");
        setSize(300, 250);
        setLayout(new GridLayout(5, 2, 5, 5));
        setLocationRelativeTo(null);

        add(new JLabel("Username:")); add(txtUsername);
        add(new JLabel("Password:")); add(txtPassword);
        add(new JLabel("Nama Lengkap:")); add(txtNama);
        add(new JLabel("Role:")); add(cbRole);
        add(btnRegister);

        btnRegister.addActionListener(e -> {
            User newUser = new User();
            newUser.setUsername(txtUsername.getText());
            newUser.setPassword(new String(txtPassword.getPassword()));
            newUser.setNamaLengkap(txtNama.getText());
            newUser.setRole(cbRole.getSelectedItem().toString());

            if (authService.registerUser(newUser)) {
                JOptionPane.showMessageDialog(this, "Registrasi Berhasil!");
                dispose();
                new LoginFrame(); // Kembali ke halaman login
            } else {
                JOptionPane.showMessageDialog(this, "Registrasi Gagal! Username mungkin sudah terpakai.");
            }
        });

        setVisible(true);
    }
}