package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.dao.UserDAO;
import main.model.User;

public class UserFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    private UserDAO userDAO;

    private JButton tambahButton;
    private JButton editButton;
    private JButton hapusButton;
    private JButton refreshButton;
    private JButton kembaliButton;

    public UserFrame() {
        userDAO = new UserDAO();

        setTitle("Kelola Pengguna");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        getContentPane().setBackground(Color.decode("#FCE4E8"));

        initTitle();
        initTable();
        initButtonPanel();

        loadData();
    }

    private void initTitle() {
        JLabel titleLabel = new JLabel("KELOLA PENGGUNA MIKIMUP", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.decode("#E5395A"));

        add(titleLabel, BorderLayout.NORTH);
    }

    private void initTable() {
        String[] kolom = {
            "ID", "Username", "Password", "Nama Lengkap", "Role"
        };

        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(Color.decode("#E5395A"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Pengguna"));

        add(scrollPane, BorderLayout.CENTER);
    }

    private void initButtonPanel() {
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelTombol.setBackground(Color.decode("#FCE4E8"));

        tambahButton = buatButton("Tambah", "#22C55E");
        editButton = buatButton("Edit", "#3B82F6");
        hapusButton = buatButton("Hapus", "#EF4444");
        refreshButton = buatButton("Refresh", "#E5395A");
        kembaliButton = buatButton("Kembali ke Dashboard", "#E5395A");

        panelTombol.add(tambahButton);
        panelTombol.add(editButton);
        panelTombol.add(hapusButton);
        panelTombol.add(refreshButton);
        panelTombol.add(kembaliButton);

        add(panelTombol, BorderLayout.SOUTH);

        tambahButton.addActionListener(e -> tambahUser());
        editButton.addActionListener(e -> editUser());
        hapusButton.addActionListener(e -> hapusUser());
        refreshButton.addActionListener(e -> loadData());

        kembaliButton.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            dispose();
        });
    }

    private JButton buatButton(String text, String warna) {
        JButton button = new JButton(text);

        button.setBackground(Color.decode(warna));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    private void loadData() {
        tableModel.setRowCount(0);

        ArrayList<User> users = userDAO.getAllUsers();

        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.getIdUser(),
                user.getUsername(),
                user.getPassword(),
                user.getNamaLengkap(),
                user.getRole()
            });
        }
    }

    private void tambahUser() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField namaLengkapField = new JTextField();

        String[] roles = {
            "admin_kasir", "owner"
        };

        JComboBox<String> roleCombo = new JComboBox<>(roles);

        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));

        panel.add(new JLabel("Username"));
        panel.add(usernameField);

        panel.add(new JLabel("Password"));
        panel.add(passwordField);

        panel.add(new JLabel("Nama Lengkap"));
        panel.add(namaLengkapField);

        panel.add(new JLabel("Role"));
        panel.add(roleCombo);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Tambah User",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String namaLengkap = namaLengkapField.getText().trim();
            String role = roleCombo.getSelectedItem().toString();

            if (username.isEmpty() || password.isEmpty() || namaLengkap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
                return;
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setNamaLengkap(namaLengkap);
            user.setRole(role);

            boolean berhasil = userDAO.insertUser(user);

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "User berhasil ditambahkan.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan user.");
            }
        }
    }

    private void editUser() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin diedit.");
            return;
        }

        int idUser = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String usernameLama = tableModel.getValueAt(selectedRow, 1).toString();
        String passwordLama = tableModel.getValueAt(selectedRow, 2).toString();
        String namaLengkapLama = tableModel.getValueAt(selectedRow, 3).toString();
        String roleLama = tableModel.getValueAt(selectedRow, 4).toString();

        JTextField usernameField = new JTextField(usernameLama);
        JPasswordField passwordField = new JPasswordField(passwordLama);
        JTextField namaLengkapField = new JTextField(namaLengkapLama);

        String[] roles = {
            "admin_kasir", "owner"
        };

        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setSelectedItem(roleLama);

        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));

        panel.add(new JLabel("Username"));
        panel.add(usernameField);

        panel.add(new JLabel("Password"));
        panel.add(passwordField);

        panel.add(new JLabel("Nama Lengkap"));
        panel.add(namaLengkapField);

        panel.add(new JLabel("Role"));
        panel.add(roleCombo);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Edit User",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String namaLengkap = namaLengkapField.getText().trim();
            String role = roleCombo.getSelectedItem().toString();

            if (username.isEmpty() || password.isEmpty() || namaLengkap.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi.");
                return;
            }

            User user = new User();
            user.setIdUser(idUser);
            user.setUsername(username);
            user.setPassword(password);
            user.setNamaLengkap(namaLengkap);
            user.setRole(role);

            boolean berhasil = userDAO.updateUser(user);

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "User berhasil diedit.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengedit user.");
            }
        }
    }

    private void hapusUser() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang ingin dihapus.");
            return;
        }

        int idUser = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
        String username = tableModel.getValueAt(selectedRow, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus user " + username + "?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean berhasil = userDAO.deleteUser(idUser);

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "User berhasil dihapus.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus user.");
            }
        }
    }
}