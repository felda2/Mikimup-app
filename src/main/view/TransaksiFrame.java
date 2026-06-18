package main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.DatabaseConnection;
import main.model.Barang;
import main.model.DetailTransaksi;
import main.model.Transaksi;
import main.service.TransaksiService;

public class TransaksiFrame extends JFrame {

    private TransaksiService transaksiService;

    private JComboBox<String> produkCombo;
    private JTextField cariField;
    private JTextField jumlahField;

    private JButton tambahButton;
    private JButton editButton;
    private JButton hapusButton;

    private JTable tabel;
    private DefaultTableModel model;

    private JLabel totalLabel;
    private JTextField bayarField;
    private JComboBox<String> metodeCombo;
    private JLabel kembalianLabel;

    private JButton simpanButton;
    private JButton cetakButton;
    private JButton kembaliButton;

    private JTextArea riwayatArea;
    private JLabel jumlahTransaksiLabel;
    private JLabel pendapatanLabel;

    private Transaksi transaksiAktif;
    private Transaksi transaksiTerakhir;

    private List<Barang> daftarBarang;
    private List<Barang> daftarBarangTampil;

    public TransaksiFrame() {
        transaksiService = new TransaksiService();

        daftarBarang = new ArrayList<>();
        daftarBarangTampil = new ArrayList<>();

        loadDataBarang();
        transaksiAktif = buatTransaksiBaru();

        setTitle("Transaksi Mikimup");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        getContentPane().setBackground(Color.decode("#FCE4E8"));

        initPanelInput();
        initTable();
        initPanelRiwayat();
        initPanelBawah();

        refreshProdukCombo();
        refreshInfo();
    }

    private Transaksi buatTransaksiBaru() {
        Transaksi transaksi = new Transaksi();

        transaksi.setKodeTransaction(generateKodeTransaction());
        transaksi.setIdUser(1);
        transaksi.setTotalHarga(0);
        transaksi.setBayar(0);
        transaksi.setKembalian(0);
        transaksi.setMetodePembayaran("cash");

        return transaksi;
    }

    private String generateKodeTransaction() {
        String kodeDefault = "TRX001";

        String sql = "SELECT kode_transaction FROM transactions "
                + "ORDER BY id_transaction DESC LIMIT 1";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastKode = rs.getString("kode_transaction");

                if (lastKode != null && lastKode.length() >= 6) {
                    int nomor = Integer.parseInt(lastKode.substring(3));
                    return String.format("TRX%03d", nomor + 1);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal generate kode transaksi: " + e.getMessage());
        }

        return kodeDefault;
    }

    private void loadDataBarang() {
        daftarBarang.clear();

        String sql = "SELECT * FROM products ORDER BY nama_product ASC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Barang barang = new Barang();

                barang.setIdProduct(rs.getInt("id_product"));
                barang.setKodeProduct(rs.getString("kode_product"));
                barang.setNamaProduct(rs.getString("nama_product"));
                barang.setKategori(rs.getString("kategori"));
                barang.setHargaBeli(rs.getDouble("harga_beli"));
                barang.setHargaJual(rs.getDouble("harga_jual"));
                barang.setStok(rs.getInt("stok"));
                barang.setStokMinimum(rs.getInt("stok_minimum"));

                daftarBarang.add(barang);
            }

            daftarBarangTampil = new ArrayList<>(daftarBarang);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data produk dari database.");
            e.printStackTrace();
        }
    }

    private void initPanelInput() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Input Transaksi"));
        panel.setBackground(Color.WHITE);

        cariField = new JTextField();
        cariField.setBackground(Color.WHITE);
        cariField.setForeground(Color.decode("#1F2937"));

        produkCombo = new JComboBox<>();
        produkCombo.setBackground(Color.WHITE);

        jumlahField = new JTextField();
        jumlahField.setBackground(Color.WHITE);
        jumlahField.setForeground(Color.decode("#1F2937"));

        tambahButton = new JButton("Tambah Item");
        tambahButton.setBackground(Color.decode("#E5395A"));
        tambahButton.setForeground(Color.WHITE);
        tambahButton.setFocusPainted(false);

        panel.add(new JLabel("Cari Produk"));
        panel.add(cariField);

        panel.add(new JLabel("Pilih Produk"));
        panel.add(produkCombo);

        panel.add(new JLabel("Jumlah"));
        panel.add(jumlahField);

        panel.add(new JLabel(""));
        panel.add(tambahButton);

        add(panel, BorderLayout.NORTH);
    }

    private void initTable() {
        model = new DefaultTableModel(new String[]{
            "Kode", "Nama", "Jumlah", "Harga", "Subtotal"
        }, 0);

        tabel = new JTable(model);
        tabel.getTableHeader().setBackground(Color.decode("#E5395A"));
        tabel.getTableHeader().setForeground(Color.WHITE);
        tabel.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabel.setRowHeight(25);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Keranjang Transaksi"));
        panel.setBackground(Color.WHITE);
        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);

        JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tombolPanel.setBackground(Color.WHITE);

        editButton = new JButton("Edit Item");
        editButton.setBackground(Color.decode("#3B82F6"));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);

        hapusButton = new JButton("Hapus Item");
        hapusButton.setBackground(Color.decode("#EF4444"));
        hapusButton.setForeground(Color.WHITE);
        hapusButton.setFocusPainted(false);

        tombolPanel.add(editButton);
        tombolPanel.add(hapusButton);

        panel.add(tombolPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
    }

    private void initPanelRiwayat() {
        riwayatArea = new JTextArea();
        riwayatArea.setEditable(false);
        riwayatArea.setLineWrap(true);
        riwayatArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(riwayatArea);
        scroll.setPreferredSize(new Dimension(260, 0));
        scroll.setBorder(BorderFactory.createTitledBorder("Riwayat Transaksi"));

        add(scroll, BorderLayout.EAST);
    }

    private void initPanelBawah() {
        JPanel bawah = new JPanel(new GridLayout(7, 2, 10, 10));
        bawah.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        bawah.setBackground(Color.decode("#FCE4E8"));

        totalLabel = new JLabel("Total : Rp 0");
        totalLabel.setForeground(Color.decode("#1F2937"));
        totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        bayarField = new JTextField();
        bayarField.setBackground(Color.WHITE);
        bayarField.setForeground(Color.decode("#1F2937"));

        metodeCombo = new JComboBox<>();
        metodeCombo.setBackground(Color.WHITE);
        metodeCombo.addItem("cash");
        metodeCombo.addItem("qris");
        metodeCombo.addItem("transfer");

        kembalianLabel = new JLabel("Rp 0");
        kembalianLabel.setForeground(Color.decode("#1F2937"));
        kembalianLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        jumlahTransaksiLabel = new JLabel("Jumlah Transaksi : 0");
        jumlahTransaksiLabel.setForeground(Color.decode("#1F2937"));
        jumlahTransaksiLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pendapatanLabel = new JLabel("Pendapatan : Rp 0");
        pendapatanLabel.setForeground(Color.decode("#1F2937"));
        pendapatanLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        simpanButton = new JButton("Simpan Transaksi");
        simpanButton.setBackground(Color.decode("#22C55E"));
        simpanButton.setForeground(Color.WHITE);
        simpanButton.setFocusPainted(false);

        cetakButton = new JButton("Cetak Struk");
        cetakButton.setBackground(Color.decode("#E5395A"));
        cetakButton.setForeground(Color.WHITE);
        cetakButton.setFocusPainted(false);

        kembaliButton = new JButton("Kembali ke Dashboard");
        kembaliButton.setBackground(Color.decode("#E5395A"));
        kembaliButton.setForeground(Color.WHITE);
        kembaliButton.setFocusPainted(false);

        bawah.add(totalLabel);
        bawah.add(new JLabel(""));

        bawah.add(new JLabel("Jumlah Bayar"));
        bawah.add(bayarField);

        bawah.add(new JLabel("Metode Pembayaran"));
        bawah.add(metodeCombo);

        bawah.add(new JLabel("Kembalian"));
        bawah.add(kembalianLabel);

        bawah.add(jumlahTransaksiLabel);
        bawah.add(pendapatanLabel);

        bawah.add(cetakButton);
        bawah.add(simpanButton);

        bawah.add(new JLabel(""));
        bawah.add(kembaliButton);

        add(bawah, BorderLayout.SOUTH);

        tambahButton.addActionListener(e -> tambahItem());
        editButton.addActionListener(e -> editItem());
        hapusButton.addActionListener(e -> hapusItem());
        simpanButton.addActionListener(e -> simpanTransaksi());
        cetakButton.addActionListener(e -> cetakStruk());

        kembaliButton.addActionListener(e -> {
            new DashboardFrame().setVisible(true);
            dispose();
        });

        bayarField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hitungKembalian();
            }
        });

        cariField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterProduk();
            }
        });
    }

    private void refreshProdukCombo() {
        produkCombo.removeAllItems();

        for (Barang barang : daftarBarangTampil) {
            produkCombo.addItem(barang.getKodeProduct() + " - " + barang.getNamaProduct());
        }
    }

    private void filterProduk() {
        String keyword = cariField.getText().toLowerCase();

        daftarBarangTampil.clear();

        for (Barang barang : daftarBarang) {
            boolean cocokKode = barang.getKodeProduct().toLowerCase().contains(keyword);
            boolean cocokNama = barang.getNamaProduct().toLowerCase().contains(keyword);
            boolean cocokKategori = barang.getKategori().toLowerCase().contains(keyword);

            if (cocokKode || cocokNama || cocokKategori) {
                daftarBarangTampil.add(barang);
            }
        }

        refreshProdukCombo();
    }

    private void tambahItem() {
        try {
            if (produkCombo.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Pilih produk terlebih dahulu.");
                return;
            }

            if (jumlahField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jumlah belum diisi.");
                return;
            }

            int index = produkCombo.getSelectedIndex();
            Barang barang = daftarBarangTampil.get(index);

            int jumlah = Integer.parseInt(jumlahField.getText());

            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0.");
                return;
            }

            if (jumlah > barang.getStok()) {
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi.");
                return;
            }

            DetailTransaksi detail = new DetailTransaksi();
            detail.setIdProduct(barang.getIdProduct());
            detail.setBarang(barang);
            detail.setJumlah(jumlah);
            detail.setHargaSatuan(barang.getHargaJual());
            detail.setSubtotal(barang.getHargaJual() * jumlah);

            transaksiAktif.tambahDetail(detail);

            model.addRow(new Object[]{
                barang.getKodeProduct(),
                barang.getNamaProduct(),
                jumlah,
                barang.getHargaJual(),
                detail.getSubtotal()
            });

            updateTotal();
            jumlahField.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input jumlah harus berupa angka.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan item.");
            e.printStackTrace();
        }
    }

    private void editItem() {
        int row = tabel.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih item terlebih dahulu.");
            return;
        }

        String input = JOptionPane.showInputDialog(this, "Masukkan jumlah baru:");

        if (input == null) {
            return;
        }

        try {
            int jumlahBaru = Integer.parseInt(input);

            if (jumlahBaru <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0.");
                return;
            }

            DetailTransaksi detail = transaksiAktif.getDaftarDetail().get(row);
            Barang barang = detail.getBarang();

            if (jumlahBaru > barang.getStok()) {
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi.");
                return;
            }

            detail.setJumlah(jumlahBaru);
            detail.setSubtotal(detail.getHargaSatuan() * jumlahBaru);

            model.setValueAt(jumlahBaru, row, 2);
            model.setValueAt(detail.getSubtotal(), row, 4);

            updateTotal();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input jumlah harus berupa angka.");
        }
    }

    private void hapusItem() {
        int row = tabel.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih item terlebih dahulu.");
            return;
        }

        transaksiAktif.getDaftarDetail().remove(row);
        model.removeRow(row);

        updateTotal();
    }

    private void updateTotal() {
        double total = transaksiAktif.hitungTotal();
        transaksiAktif.setTotalHarga(total);

        totalLabel.setText("Total : Rp " + total);
        hitungKembalian();
    }

    private void hitungKembalian() {
        try {
            if (bayarField.getText().trim().isEmpty()) {
                kembalianLabel.setText("Rp 0");
                return;
            }

            double bayar = Double.parseDouble(bayarField.getText());
            double total = transaksiAktif.hitungTotal();
            double kembali = bayar - total;

            if (kembali < 0) {
                kembalianLabel.setText("Rp 0");
            } else {
                kembalianLabel.setText("Rp " + kembali);
            }

        } catch (NumberFormatException e) {
            kembalianLabel.setText("Rp 0");
        }
    }

    private void simpanTransaksi() {
        try {
            if (transaksiAktif.getDaftarDetail().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Belum ada item transaksi.");
                return;
            }

            if (bayarField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jumlah bayar belum diisi.");
                return;
            }

            double bayar = Double.parseDouble(bayarField.getText());
            double total = transaksiAktif.hitungTotal();

            if (bayar < total) {
                JOptionPane.showMessageDialog(this, "Jumlah pembayaran kurang.");
                return;
            }

            transaksiAktif.setTotalHarga(total);
            transaksiAktif.setBayar(bayar);
            transaksiAktif.setKembalian(bayar - total);
            transaksiAktif.setMetodePembayaran(metodeCombo.getSelectedItem().toString());

            boolean berhasil = transaksiService.simpanTransaksi(transaksiAktif);

            if (berhasil) {
                transaksiTerakhir = transaksiAktif;

                JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan.");

                refreshInfo();
                resetForm();

            } else {
                JOptionPane.showMessageDialog(this, "Transaksi gagal disimpan.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input pembayaran harus berupa angka.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan transaksi.");
            e.printStackTrace();
        }
    }

    private void cetakStruk() {
        if (transaksiTerakhir == null) {
            JOptionPane.showMessageDialog(this, "Belum ada transaksi yang bisa dicetak.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("========================\n");
        sb.append("        MIKIMUP\n");
        sb.append("========================\n\n");
        sb.append("Kode Transaksi : ").append(transaksiTerakhir.getKodeTransaction()).append("\n");
        sb.append("Metode Bayar   : ").append(transaksiTerakhir.getMetodePembayaran()).append("\n\n");

        for (DetailTransaksi detail : transaksiTerakhir.getDaftarDetail()) {
            Barang barang = detail.getBarang();

            sb.append(barang.getNamaProduct()).append("\n");
            sb.append(detail.getJumlah())
                    .append(" x Rp ")
                    .append(detail.getHargaSatuan())
                    .append(" = Rp ")
                    .append(detail.getSubtotal())
                    .append("\n\n");
        }

        sb.append("------------------------\n");
        sb.append("TOTAL     : Rp ").append(transaksiTerakhir.getTotalHarga()).append("\n");
        sb.append("BAYAR     : Rp ").append(transaksiTerakhir.getBayar()).append("\n");
        sb.append("KEMBALIAN : Rp ").append(transaksiTerakhir.getKembalian()).append("\n");
        sb.append("========================\n");
        sb.append("Terima kasih sudah membeli Kimbab kami.\n");

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Struk", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        model.setRowCount(0);

        jumlahField.setText("");
        bayarField.setText("");
        cariField.setText("");

        totalLabel.setText("Total : Rp 0");
        kembalianLabel.setText("Rp 0");

        loadDataBarang();
        refreshProdukCombo();

        transaksiAktif = buatTransaksiBaru();
    }

    private void refreshInfo() {
        jumlahTransaksiLabel.setText("Jumlah Transaksi : " + getJumlahTransaksi());
        pendapatanLabel.setText("Pendapatan : Rp " + getTotalPendapatan());

        riwayatArea.setText("");

        String sql = "SELECT kode_transaction, total_harga, metode_pembayaran "
                + "FROM transactions ORDER BY id_transaction DESC LIMIT 10";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                riwayatArea.append(
                        rs.getString("kode_transaction")
                        + " | Rp "
                        + rs.getDouble("total_harga")
                        + " | "
                        + rs.getString("metode_pembayaran")
                        + "\n"
                );
            }

        } catch (Exception e) {
            riwayatArea.append("Gagal memuat riwayat transaksi.\n");
        }
    }

    private int getJumlahTransaksi() {
        String sql = "SELECT COUNT(*) AS jumlah FROM transactions";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("jumlah");
            }

        } catch (Exception e) {
            System.out.println("Gagal menghitung jumlah transaksi: " + e.getMessage());
        }

        return 0;
    }

    private double getTotalPendapatan() {
        String sql = "SELECT COALESCE(SUM(total_harga), 0) AS pendapatan FROM transactions";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("pendapatan");
            }

        } catch (Exception e) {
            System.out.println("Gagal menghitung pendapatan: " + e.getMessage());
        }

        return 0;
    }
}