package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import main.dao.ReportDAO;
import main.model.Barang;
import main.model.Transaksi;

public class LaporanFrame extends JFrame {

    private ReportDAO reportDAO;

    private JLabel lblPendapatan;
    private JLabel lblJumlahProduk;
    private JLabel lblJumlahTransaksi;

    private JTable tblProduk;
    private JTable tblTransaksi;
    private JTable tblStok;

    private DefaultTableModel modelProduk;
    private DefaultTableModel modelTransaksi;
    private DefaultTableModel modelStok;

    private JTextField txtDari;
    private JTextField txtSampai;

    public LaporanFrame() {
        reportDAO = new ReportDAO();

        setTitle("Laporan Penjualan Mikimup");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.decode("#F8F8F8"));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("LAPORAN PENJUALAN MIKIMUP");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.decode("#E5395A"));

        mainPanel.add(title, BorderLayout.NORTH);

        JPanel filterPanel = buatFilterPanel();
        mainPanel.add(filterPanel, BorderLayout.WEST);

        JTabbedPane tabbedPane = buatTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = buatBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        loadDataLaporan();
    }

    private JPanel buatFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Tanggal"));
        filterPanel.setPreferredSize(new Dimension(170, 0));

        txtDari = new JTextField(10);
        txtSampai = new JTextField(10);

        JButton btnFilter = new JButton("Filter");
        btnFilter.setBackground(Color.decode("#E5395A"));
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false);

        btnFilter.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Filter tanggal belum diterapkan.\nData yang ditampilkan masih seluruh data."
            );
            loadDataLaporan();
        });

        filterPanel.add(new JLabel("Dari"));
        filterPanel.add(txtDari);

        filterPanel.add(new JLabel("Sampai"));
        filterPanel.add(txtSampai);

        filterPanel.add(btnFilter);

        return filterPanel;
    }

    private JTabbedPane buatTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        modelProduk = new DefaultTableModel(
                new String[]{"Kode", "Nama Produk", "Kategori", "Stok", "Harga Jual"},
                0
        );

        tblProduk = new JTable(modelProduk);
        tblProduk.getTableHeader().setBackground(Color.decode("#E5395A"));
        tblProduk.getTableHeader().setForeground(Color.WHITE);
        tblProduk.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblProduk.setRowHeight(24);

        tabbedPane.addTab("Produk", new JScrollPane(tblProduk));

        modelTransaksi = new DefaultTableModel(
                new String[]{"Kode Transaksi", "Tanggal", "Total", "Bayar", "Kembalian", "Metode"},
                0
        );

        tblTransaksi = new JTable(modelTransaksi);
        tblTransaksi.getTableHeader().setBackground(Color.decode("#E5395A"));
        tblTransaksi.getTableHeader().setForeground(Color.WHITE);
        tblTransaksi.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblTransaksi.setRowHeight(24);

        tabbedPane.addTab("Transaksi", new JScrollPane(tblTransaksi));

        modelStok = new DefaultTableModel(
                new String[]{"Kode", "Nama Produk", "Stok", "Stok Minimum"},
                0
        );

        tblStok = new JTable(modelStok);
        tblStok.getTableHeader().setBackground(Color.decode("#E5395A"));
        tblStok.getTableHeader().setForeground(Color.WHITE);
        tblStok.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblStok.setRowHeight(24);

        tabbedPane.addTab("Stok Minimum", new JScrollPane(tblStok));

        return tabbedPane;
    }

    private JPanel buatBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        lblJumlahProduk = new JLabel("Jumlah Produk : 0");
        lblJumlahTransaksi = new JLabel("Jumlah Transaksi : 0");
        lblPendapatan = new JLabel("Total Pendapatan : Rp 0");

        lblJumlahProduk.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblJumlahTransaksi.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPendapatan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPendapatan.setForeground(Color.decode("#E5395A"));

        JButton btnRefresh = buatButton("Refresh");
        JButton btnExport = buatButton("Export");
        JButton btnCetak = buatButton("Cetak");
        JButton btnKembali = buatButton("Kembali ke Dashboard");

        btnRefresh.addActionListener(e -> {
            loadDataLaporan();
            JOptionPane.showMessageDialog(this, "Data laporan berhasil diperbarui.");
        });

        btnExport.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Fitur export belum tersedia.")
        );

        btnCetak.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Fitur cetak belum tersedia.")
        );

        btnKembali.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            dispose();
        });

        bottomPanel.add(lblJumlahProduk);
        bottomPanel.add(lblJumlahTransaksi);
        bottomPanel.add(lblPendapatan);
        bottomPanel.add(btnRefresh);
        bottomPanel.add(btnExport);
        bottomPanel.add(btnCetak);
        bottomPanel.add(btnKembali);

        return bottomPanel;
    }

    private JButton buatButton(String text) {
        JButton button = new JButton(text);

        button.setBackground(Color.decode("#E5395A"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));

        return button;
    }

    private void loadDataLaporan() {
        loadDataProduk();
        loadDataTransaksi();
        loadDataStokMinimum();
        loadRingkasan();
    }

    private void loadDataProduk() {
        modelProduk.setRowCount(0);

        ArrayList<Barang> daftarBarang = reportDAO.getDataProduk();

        for (Barang barang : daftarBarang) {
            modelProduk.addRow(new Object[]{
                    barang.getKodeProduct(),
                    barang.getNamaProduct(),
                    barang.getKategori(),
                    barang.getStok(),
                    barang.getHargaJual()
            });
        }
    }

    private void loadDataTransaksi() {
        modelTransaksi.setRowCount(0);

        ArrayList<Transaksi> daftarTransaksi = reportDAO.getDataTransaksi();

        for (Transaksi transaksi : daftarTransaksi) {
            modelTransaksi.addRow(new Object[]{
                    transaksi.getKodeTransaction(),
                    transaksi.getTanggal(),
                    transaksi.getTotalHarga(),
                    transaksi.getBayar(),
                    transaksi.getKembalian(),
                    transaksi.getMetodePembayaran()
            });
        }
    }

    private void loadDataStokMinimum() {
        modelStok.setRowCount(0);

        ArrayList<Barang> daftarStokMinimum = reportDAO.getStokMinimum();

        for (Barang barang : daftarStokMinimum) {
            modelStok.addRow(new Object[]{
                    barang.getKodeProduct(),
                    barang.getNamaProduct(),
                    barang.getStok(),
                    barang.getStokMinimum()
            });
        }
    }

    private void loadRingkasan() {
        lblJumlahProduk.setText("Jumlah Produk : " + reportDAO.getJumlahProduk());
        lblJumlahTransaksi.setText("Jumlah Transaksi : " + reportDAO.getJumlahTransaksi());
        lblPendapatan.setText("Total Pendapatan : Rp " + reportDAO.getTotalPendapatan());
    }
}