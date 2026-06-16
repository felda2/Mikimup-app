package main.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.model.User;
import main.service.AuthService;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private AuthService authService;

    public LoginFrame() {
        authService = new AuthService();

        setTitle("Login Aplikasi");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Username"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        add(loginButton);

        loginButton.addActionListener(e -> prosesLogin());
    }

    private void prosesLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = authService.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");

            DashboardFrame dashboardFrame = new DashboardFrame();
            dashboardFrame.setVisible(true);

            dispose();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Username atau password salah.",
                "Login Gagal",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}