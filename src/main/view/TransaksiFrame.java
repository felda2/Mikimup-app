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
import main.service.BarangService;
import main.service.TransaksiService;

public class TransaksiFrame extends JFrame {

        private BarangService barangService;
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

        private JTextArea riwayatArea;

        private JLabel jumlahTransaksiLabel;
        private JLabel pendapatanLabel;

        private Transaksi transaksiAktif;
        private Transaksi transaksiTerakhir;

        private int nomorTransaksi = 1;

        public TransaksiFrame() {

                barangService = new BarangService();
                transaksiService = new TransaksiService();

                loadDataBarang();

                transaksiAktif = new Transaksi(generateId());

                setTitle("Transaksi Mikimup");

                setSize(1100, 700);

                setLocationRelativeTo(null);

                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                setLayout(new BorderLayout(12, 12));

                ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
                getContentPane().setBackground(Color.decode("#FCE4E8"));
                BorderFactory.createLineBorder(Color.decode("#E5E7EB"));

                initPanelInput();

                initTable();

                initPanelRiwayat();

                initPanelBawah();

        }

        private String generateId() {

                return String.format("TRX%03d", nomorTransaksi++);

        }

        // LOAD DATA PRODUK DATABASE
        private void loadDataBarang() {

                barangService.getDaftarBarang().clear();
                try {
                        Connection conn = DatabaseConnection.getConnection();

                        String sql = "SELECT * FROM products ORDER BY nama_barang";

                        PreparedStatement ps = conn.prepareStatement(sql);

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                                Barang barang = new Barang(
                                                rs.getString("kode_barang"),
                                                rs.getString("nama_barang"),
                                                rs.getInt("stok"),
                                                rs.getDouble("harga"));
                                barangService.tambahBarang(barang);
                        }
                        rs.close();
                        ps.close();
                        conn.close();
                } catch (Exception e) {
                        e.printStackTrace();
                }

        }

        // PanelInputan
        private void initPanelInput() {
                JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

                panel.setBorder(BorderFactory.createTitledBorder("Input Transaksi"));
                panel.setBackground(Color.WHITE);

                cariField = new JTextField();
                cariField.setBackground(Color.WHITE);
                cariField.setForeground(Color.decode("#1F2937"));

                produkCombo = new JComboBox<>();
                produkCombo.setBackground(Color.WHITE);

                for (Barang barang : barangService.getDaftarBarang()) {
                        produkCombo.addItem(barang.getKodeBarang()+ " - "+ barang.getNamaBarang());
                }

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
                add(panel,BorderLayout.NORTH);

        }
        //TabelTransaksi
        private void initTable() {
                model = new DefaultTableModel(new String[] {
                        "Kode","Nama","Jumlah","Harga","Subtotal"
                },0);

                //tabelTransaksi
                tabel = new JTable(model);
                tabel.getTableHeader().setBackground(Color.decode("#E5395A"));
                tabel.getTableHeader().setForeground(Color.WHITE);
                tabel.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

                tabel.setRowHeight(25);

                JPanel panel = new JPanel(new BorderLayout(10,10));
                panel.setBorder(BorderFactory.createTitledBorder("Keranjang Transaksi"));
                panel.setBackground(Color.WHITE);
                panel.add(new JScrollPane(tabel),BorderLayout.CENTER);
                JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                //EditButton
                editButton = new JButton("Edit Item");
                editButton.setBackground(Color.decode("#3B82F6"));
                editButton.setForeground(Color.WHITE);
                editButton.setFocusPainted(false);

                //HapusButton
                hapusButton = new JButton("Hapus Item");
                tombolPanel.add(editButton);
                tombolPanel.add(hapusButton);
                hapusButton.setBackground(Color.decode("#EF4444"));
                hapusButton.setForeground(Color.WHITE);
                hapusButton.setFocusPainted(false);

                panel.add(tombolPanel,BorderLayout.SOUTH);
                add(panel,BorderLayout.CENTER);

        }
        //PanelRiwayatKu
        private void initPanelRiwayat() {

                riwayatArea = new JTextArea();
                riwayatArea.setEditable(false);
                riwayatArea.setLineWrap(true);
                riwayatArea.setWrapStyleWord(true);
                JScrollPane scroll = new JScrollPane(riwayatArea);

                scroll.setPreferredSize(new Dimension(260,0));
                scroll.setBorder(BorderFactory.createTitledBorder("Riwayat Transaksi"));

                add(scroll,BorderLayout.EAST);

        }
        //PanelBawahTransaksi
        private void initPanelBawah() {
                JPanel bawah = new JPanel(new GridLayout(6,2,10,10));

                bawah.setBorder(BorderFactory.createEmptyBorder(12,0,0,0));

                totalLabel = new JLabel("Total : Rp 0");
                totalLabel.setForeground(Color.decode("#1F2937"));
                totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                bayarField = new JTextField();
                bayarField.setBackground(Color.WHITE);bayarField.setForeground(Color.decode("#1F2937"));

                metodeCombo = new JComboBox<>();
                metodeCombo.setBackground(Color.WHITE);

                metodeCombo.addItem("Cash");
                metodeCombo.addItem("QRIS");
                metodeCombo.addItem("Transfer");

                kembalianLabel = new JLabel("Rp 0");
                kembalianLabel.setForeground(Color.decode("#1F2937"));
                kembalianLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                jumlahTransaksiLabel = new JLabel("Jumlah Transaksi : 0");
                jumlahTransaksiLabel.setForeground(Color.decode("#1F2937"));
                jumlahTransaksiLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                pendapatanLabel = new JLabel("Pendapatan : Rp 0");
                pendapatanLabel.setForeground(Color.decode("#1F2937"));
                pendapatanLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                //simpanButton
                simpanButton = new JButton("Simpan Transaksi");
                simpanButton.setBackground(Color.decode("#22C55E"));
                simpanButton.setForeground(Color.WHITE);
                simpanButton.setFocusPainted(false);

                //cetakButton
                cetakButton = new JButton("Cetak Struk");
                cetakButton.setBackground(Color.decode("#E5395A"));
                cetakButton.setForeground(Color.WHITE);           
                cetakButton.setFocusPainted(false);

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

                add(bawah,BorderLayout.SOUTH);

                tambahButton.addActionListener(e -> tambahItem());
                editButton.addActionListener(e -> editItem());
                hapusButton.addActionListener(e -> hapusItem());
                simpanButton.addActionListener(e -> simpanTransaksi());
                cetakButton.addActionListener(e -> cetakStruk());
                bayarField.addKeyListener(new java.awt.event.KeyAdapter() {
                        @Override
                        public void keyReleased(
                                        java.awt.event.KeyEvent evt) {
                                hitungKembalian();
                        }
                });

                cariField.addKeyListener(new java.awt.event.KeyAdapter() {
                        @Override
                        public void keyReleased(
                                        java.awt.event.KeyEvent evt) {
                                filterProduk();
                        }
                });
                refreshInfo();

        }
        //FilterProduk
        private void filterProduk() {
                String keyword = cariField.getText().toLowerCase();
                produkCombo.removeAllItems();
                for (Barang barang : barangService.getDaftarBarang()) {
                        if (barang.getKodeBarang().toLowerCase().contains(keyword) || barang.getNamaBarang().toLowerCase().contains(keyword)) {
                                produkCombo.addItem(barang.getKodeBarang()+ " - "+ barang.getNamaBarang());
                        }
                }
        }
        //RefreshInfo
        private void refreshInfo() {
                jumlahTransaksiLabel.setText("Jumlah Transaksi : "+ transaksiService.jumlahTransaksi());
                pendapatanLabel.setText("Pendapatan : Rp "+ transaksiService.hitungPendapatan());
                riwayatArea.setText("");

                for (String data : transaksiService.getRiwayatTransaksi()) {
                        riwayatArea.append(data+ "\n");
                }
        }
        //TambahItemTransaksi
        private void tambahItem() {
                try {
                        if (produkCombo.getSelectedIndex() == -1) {
                                JOptionPane.showMessageDialog(this,"Pilih produk terlebih dahulu");
                                return;
                        }

                        int index = produkCombo.getSelectedIndex();
                        Barang barang = barangService.getDaftarBarang().get(index);
                        int jumlah = Integer.parseInt(jumlahField.getText());

                        if (jumlah <= 0) {
                                JOptionPane.showMessageDialog(this,"Jumlah harus lebih dari 0");
                                return;
                        }

                        if (jumlah > barang.getStok()) {

                                JOptionPane.showMessageDialog(this,"Stok tidak mencukupi");
                                return;

                        }

                        DetailTransaksi detail = new DetailTransaksi(barang,jumlah);

                        transaksiAktif.tambahDetail(detail);

                        model.addRow(new Object[] {
                                barang.getKodeBarang(),
                                barang.getNamaBarang(),
                                jumlah,
                                barang.getHarga(),
                                detail.hitungSubtotal()
                        });
                        updateTotal();
                        jumlahField.setText("");
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,"Input jumlah tidak valid");
                }

        }
        //EditItemTransaksi
        private void editItem() {
                int row = tabel.getSelectedRow();
                if (row == -1) {
                        JOptionPane.showMessageDialog(this,"Pilih item terlebih dahulu");
                        return;
                }

                String input = JOptionPane.showInputDialog(this,"Jumlah baru");

                if (input == null) {
                        return;
                }

                try {
                        int jumlahBaru = Integer.parseInt(input);
                        DetailTransaksi detail = transaksiAktif.getDaftarDetail().get(row);
                        if (jumlahBaru <= 0) {
                                JOptionPane.showMessageDialog(this,"Jumlah tidak boleh 0");
                                return;
                        }
                        detail.setJumlah(jumlahBaru);
                        model.setValueAt(jumlahBaru,row,2);
                        model.setValueAt(detail.hitungSubtotal(),row,4);
                        updateTotal();
                } catch (Exception e) {

                        JOptionPane.showMessageDialog(this,"Input tidak valid");

                }

        }

        //HapusItemTransaksi
        private void hapusItem() {
                int row = tabel.getSelectedRow();
                if (row == -1) {
                        JOptionPane.showMessageDialog(this,"Pilih item terlebih dahulu");
                        return;

                }

                transaksiAktif.hapusDetail(row);
                model.removeRow(row);
                updateTotal();
        }
        //UpdateTotalTransaksi
        private void updateTotal() {
                totalLabel.setText("Total : Rp " + transaksiAktif.hitungTotal());
                hitungKembalian();
        }
        //HitungKembalianTransaksi
        private void hitungKembalian() {
                try {
                        if (bayarField.getText().isEmpty()) {
                                kembalianLabel.setText("Rp 0");
                                return;
                        }

                        double bayar = Double.parseDouble(bayarField.getText());
                        double total = transaksiAktif.hitungTotal();
                        double kembali = bayar - total;

                        if (kembali < 0) {
                                kembali = 0;
                        }

                        kembalianLabel.setText("Rp "+ kembali);
                } catch (Exception e) {
                        kembalianLabel.setText("Rp 0");
                }

        }
        //SimpanTransaksi
        private void simpanTransaksi() {
                try {
                        if (transaksiAktif.getDaftarDetail().isEmpty()) {
                                JOptionPane.showMessageDialog(this,"Belum ada item transaksi");
                                return;

                        }

                        double bayar = Double.parseDouble(bayarField.getText());

                        if (!transaksiAktif.verifikasiBayar(bayar)) {
                                JOptionPane.showMessageDialog(this,"Jumlah pembayaran kurang");
                                return;
                        }

                        transaksiAktif.setTotalBayar(bayar);
                        transaksiAktif.konfirmasiTransaksi();
                        boolean berhasil = transaksiService.simpanTransaksi(transaksiAktif,bayar);

                        if (berhasil) {
                                transaksiTerakhir = transaksiAktif;
                                JOptionPane.showMessageDialog(this,"Transaksi berhasil disimpan");
                                refreshInfo();
                                resetForm();

                        } else {

                                JOptionPane.showMessageDialog(this,"Transaksi gagal disimpan");

                        }

                } catch (Exception e) {

                        JOptionPane.showMessageDialog(this,"Input pembayaran tidak valid");

                }

        }
        //CetakStrukTransaksi
        private void cetakStruk() {
                if (transaksiTerakhir == null) {
                        JOptionPane.showMessageDialog(this,"Belum ada transaksi");
                        return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("========================\n");
                sb.append("       MIKIMUP\n");
                sb.append("========================\n\n");
                sb.append("ID : ").append(transaksiTerakhir.getIdTransaksi()).append("\n");
                sb.append("Tanggal : ").append(transaksiTerakhir.getTanggal()).append("\n\n");

                for (DetailTransaksi detail : transaksiTerakhir.getDaftarDetail()) {
                        sb.append(detail.getBarang().getNamaBarang()).append("\n");
                        sb.append(detail.getJumlah()).append(" x Rp ").append(detail.getBarang().getHarga())
                        .append(" = Rp ").append(detail.hitungSubtotal()).append("\n\n");
                }

                sb.append("------------------------\n");
                sb.append("TOTAL : Rp ").append(transaksiTerakhir.hitungTotal()).append("\n");
                sb.append("BAYAR : Rp ").append(transaksiTerakhir.getTotalBayar()).append("\n");
                sb.append("KEMBALIAN : Rp ").append(transaksiTerakhir.hitungKembalian()).append("\n");
                sb.append("========================\n");
                sb.append("Terima Kasih Sudah Membeli Kimbab kami\n");

                JTextArea area = new JTextArea(sb.toString());

                area.setEditable(false);

                JOptionPane.showMessageDialog(this,new JScrollPane(area),"Struk",JOptionPane.INFORMATION_MESSAGE);

        }
        //ResetFormTransaksi
        private void resetForm() {
                model.setRowCount(0);
                jumlahField.setText("");
                bayarField.setText("");
                cariField.setText("");
                totalLabel.setText("Total : Rp 0");
                kembalianLabel.setText("Rp 0");
                produkCombo.removeAllItems();

                loadDataBarang();

                for (Barang barang : barangService.getDaftarBarang()) {
                        produkCombo.addItem(barang.getKodeBarang()+ " - "+ barang.getNamaBarang());
                }

                transaksiAktif = new Transaksi(
                                generateId());

        }
}
