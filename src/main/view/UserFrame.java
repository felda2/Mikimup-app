package main.view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import main.dao.UserDAO;
import main.model.User;

public class UserFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private UserDAO userDAO = new UserDAO();

    public UserFrame() {
        setTitle("Kelola Pengguna");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabel
        String[] kolom = {"Username", "Password", "Role"};
        tableModel = new DefaultTableModel(kolom, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Tombol
        JPanel panelTombol = new JPanel();
        JButton tambahButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton hapusButton = new JButton("Hapus");

        panelTombol.add(tambahButton);
        panelTombol.add(editButton);
        panelTombol.add(hapusButton);
        add(panelTombol, BorderLayout.SOUTH);

        // Load data
        loadData();

        // Aksi tombol
        tambahButton.addActionListener(e -> tambahUser());
        editButton.addActionListener(e -> editUser());
        hapusButton.addActionListener(e -> hapusUser());
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
            tableModel.addRow(new Object[]{u.getUsername(), u.getPassword(), u.getRole()});
        }
    }

    private void tambahUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        String[] roles = {"admin_kasir", "owner"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);

        Object[] fields = {
            "Username:", usernameField,
            "Password:", passwordField,
            "Role:", roleCombo
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Tambah User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password harus diisi!");
                return;
            }

            User user = new User(username, password, role);
            if (userDAO.tambahUser(user)) {
                JOptionPane.showMessageDialog(this, "User berhasil ditambahkan!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan user!");
            }
        }
    }

    private void editUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin diedit!");
            return;
        }

        String usernameLama = (String) tableModel.getValueAt(selectedRow, 0);
        JTextField usernameField = new JTextField(usernameLama);
        JPasswordField passwordField = new JPasswordField();
        String[] roles = {"admin_kasir", "owner"};
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setSelectedItem(tableModel.getValueAt(selectedRow, 2));

        Object[] fields = {
            "Username:", usernameField,
            "Password baru:", passwordField,
            "Role:", roleCombo
        };

        int result = JOptionPane.showConfirmDialog(this, fields, "Edit User", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username dan password harus diisi!");
                return;
            }

            User user = new User(username, password, role);
            if (userDAO.editUser(usernameLama, user)) {
                JOptionPane.showMessageDialog(this, "User berhasil diedit!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengedit user!");
            }
        }
    }

    private void hapusUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin dihapus!");
            return;
        }

        String username = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus user " + username + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (userDAO.hapusUser(username)) {
                JOptionPane.showMessageDialog(this, "User berhasil dihapus!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus user!");
            }
        }
    }
}