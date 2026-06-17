package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class LaporanFrame extends JFrame {

    private JLabel lblPendapatan;
    private JLabel lblJumlahProduk;
    private JLabel lblJumlahTransaksi;

    public LaporanFrame() {

        setTitle("Laporan Penjualan Mikimup");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // ===== TITLE =====
        JLabel title = new JLabel("LAPORAN PENJUALAN MIKIMUP");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(229, 57, 90));

        mainPanel.add(title, BorderLayout.NORTH);

        // ===== FILTER =====
        JPanel filterPanel = new JPanel();

        JTextField txtDari = new JTextField(10);
        JTextField txtSampai = new JTextField(10);

        JButton btnFilter = new JButton("Filter");

        filterPanel.add(new JLabel("Dari"));
        filterPanel.add(txtDari);

        filterPanel.add(new JLabel("Sampai"));
        filterPanel.add(txtSampai);

        filterPanel.add(btnFilter);

        mainPanel.add(filterPanel, BorderLayout.WEST);

        // ===== TABS =====
        JTabbedPane tabbedPane = new JTabbedPane();

        JTable tblProduk = new JTable(
                new Object[][]{},
                new String[]{"Kode", "Nama Produk", "Stok", "Harga"}
        );
        tabbedPane.addTab("Produk", new JScrollPane(tblProduk));

        JTable tblTransaksi = new JTable(
                new Object[][]{},
                new String[]{"ID", "Tanggal", "Total"}
        );
        tabbedPane.addTab("Transaksi", new JScrollPane(tblTransaksi));

        JTable tblStok = new JTable(
                new Object[][]{},
                new String[]{"Kode", "Nama Produk", "Stok"}
        );
        tabbedPane.addTab("Stok Minimum", new JScrollPane(tblStok));

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // ===== BOTTOM PANEL =====
        JPanel bottomPanel = new JPanel();

        lblJumlahProduk = new JLabel("Jumlah Produk : 0");
        lblJumlahTransaksi = new JLabel("Jumlah Transaksi : 0");
        lblPendapatan = new JLabel("Total Pendapatan : Rp 0");

        lblJumlahProduk.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblJumlahTransaksi.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPendapatan.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnRefresh = new JButton("Refresh");
        JButton btnExport = new JButton("Export");
        JButton btnCetak = new JButton("Cetak");

        btnRefresh.setBackground(new Color(229, 57, 90));
        btnRefresh.setForeground(Color.WHITE);

        btnExport.setBackground(new Color(229, 57, 90));
        btnExport.setForeground(Color.WHITE);

        btnCetak.setBackground(new Color(229, 57, 90));
        btnCetak.setForeground(Color.WHITE);

        btnRefresh.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Data laporan berhasil diperbarui."));

        btnExport.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Fitur export belum tersedia."));

        btnCetak.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Fitur cetak belum tersedia."));

        bottomPanel.add(lblJumlahProduk);
        bottomPanel.add(lblJumlahTransaksi);
        bottomPanel.add(lblPendapatan);
        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnExport);
        bottomPanel.add(btnCetak);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}