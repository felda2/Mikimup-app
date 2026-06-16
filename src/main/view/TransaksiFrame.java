package main.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import main.model.Barang;
import main.model.DetailTransaksi;
import main.model.Transaksi;
import main.service.BarangService;
import main.service.TransaksiService;

public class TransaksiFrame extends JFrame {

    private BarangService barangService;
    private TransaksiService transaksiService;

    private JComboBox<String> produkCombo;
    private JTextField jumlahField;

    private JButton tambahButton;
    private JButton editButton;
    private JButton hapusButton;

    private JTable tabel;
    private DefaultTableModel model;

    private JLabel totalLabel;
    private JTextField bayarField;
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

        setTitle("Transaksi");
        setSize(1000, 650);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        initPanelInput();
        initTable();
        initPanelRiwayat();
        initPanelBawah();
    }

    private void loadDataBarang() {

        barangService.tambahBarang(
                new Barang(
                        "KB001",
                        "Kimbab Original",
                        50,
                        15000));

        barangService.tambahBarang(
                new Barang(
                        "KB002",
                        "Kimbab Spicy",
                        30,
                        18000));

        barangService.tambahBarang(
                new Barang(
                        "KB003",
                        "Kimbab Cheese",
                        20,
                        20000));
    }

    private String generateId() {
        return String.format("TRX%03d", nomorTransaksi++);
    }

    private void initPanelInput() {

        JPanel panel =
                new JPanel(
                        new GridLayout(3, 2, 10, 10));

        produkCombo =
                new JComboBox<>();

        for (Barang barang :
                barangService.getDaftarBarang()) {

            produkCombo.addItem(
                    barang.getKodeBarang()
                    + " - "
                    + barang.getNamaBarang());
        }

        jumlahField = new JTextField();

        tambahButton =
                new JButton("Tambah Item");

        panel.add(new JLabel("Produk"));
        panel.add(produkCombo);

        panel.add(new JLabel("Jumlah"));
        panel.add(jumlahField);

        panel.add(new JLabel(""));
        panel.add(tambahButton);

        add(panel, BorderLayout.NORTH);

        tambahButton.addActionListener(
                e -> tambahItem());
    }

    private void initTable() {

        model =
                new DefaultTableModel(
                        new String[]{
                                "Kode",
                                "Nama",
                                "Jumlah",
                                "Harga",
                                "Subtotal"
                        },
                        0);

        tabel = new JTable(model);

        JPanel panel =
                new JPanel(
                        new BorderLayout());

        panel.add(
                new JScrollPane(tabel),
                BorderLayout.CENTER);

        JPanel tombolPanel =
                new JPanel();

        editButton =
                new JButton("Edit Item");

        hapusButton =
                new JButton("Hapus Item");

        tombolPanel.add(editButton);
        tombolPanel.add(hapusButton);

        panel.add(
                tombolPanel,
                BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);

        editButton.addActionListener(
                e -> editItem());

        hapusButton.addActionListener(
                e -> hapusItem());
    }

    private void initPanelRiwayat() {

        riwayatArea =
                new JTextArea();

        riwayatArea.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(
                        riwayatArea);

        scroll.setBorder(
                BorderFactory.createTitledBorder(
                        "Riwayat Transaksi"));

        scroll.setPreferredSize(
                new Dimension(
                        250,
                        0));

        add(scroll, BorderLayout.EAST);
    }

    private void initPanelBawah() {

        JPanel bawah =
                new JPanel(
                        new GridLayout(
                                6,
                                2,
                                10,
                                10));

        totalLabel =
                new JLabel(
                        "Total : Rp 0");

        bayarField =
                new JTextField();

        kembalianLabel =
                new JLabel(
                        "Rp 0");

        jumlahTransaksiLabel =
                new JLabel(
                        "Jumlah Transaksi : 0");

        pendapatanLabel =
                new JLabel(
                        "Pendapatan : Rp 0");

        simpanButton =
                new JButton(
                        "Simpan Transaksi");

        cetakButton =
                new JButton(
                        "Cetak Struk");

        bawah.add(totalLabel);
        bawah.add(new JLabel(""));

        bawah.add(new JLabel("Bayar"));
        bawah.add(bayarField);

        bawah.add(new JLabel("Kembalian"));
        bawah.add(kembalianLabel);

        bawah.add(jumlahTransaksiLabel);
        bawah.add(pendapatanLabel);

        bawah.add(cetakButton);
        bawah.add(simpanButton);

        add(bawah, BorderLayout.SOUTH);

        bayarField.addKeyListener(
                new java.awt.event.KeyAdapter() {

                    @Override
                    public void keyReleased(
                            java.awt.event.KeyEvent evt) {

                        hitungKembalian();
                    }
                });

        simpanButton.addActionListener(
                e -> simpanTransaksi());

        cetakButton.addActionListener(
                e -> cetakStruk());
    }

    private void tambahItem() {

        try {

            int index =
                    produkCombo.getSelectedIndex();

            Barang barang =
                    barangService
                            .getDaftarBarang()
                            .get(index);

            int jumlah =
                    Integer.parseInt(
                            jumlahField.getText());

            DetailTransaksi detail =
                    new DetailTransaksi(
                            barang,
                            jumlah);

            transaksiAktif
                    .tambahDetail(detail);

            model.addRow(new Object[]{
                    barang.getKodeBarang(),
                    barang.getNamaBarang(),
                    jumlah,
                    barang.getHarga(),
                    detail.hitungSubtotal()
            });

            updateTotal();

            jumlahField.setText("");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input tidak valid");
        }
    }

    private void editItem() {

        int row =
                tabel.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pilih item terlebih dahulu");

            return;
        }

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Jumlah baru");

        if (input == null) {
            return;
        }

        try {

            int jumlahBaru =
                    Integer.parseInt(input);

            DetailTransaksi detail =
                    transaksiAktif
                            .getDaftarDetail()
                            .get(row);

            detail.setJumlah(jumlahBaru);

            model.setValueAt(
                    jumlahBaru,
                    row,
                    2);

            model.setValueAt(
                    detail.hitungSubtotal(),
                    row,
                    4);

            updateTotal();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input tidak valid");
        }
    }

    private void hapusItem() {

        int row =
                tabel.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pilih item dahulu");

            return;
        }

        transaksiAktif.hapusDetail(row);

        model.removeRow(row);

        updateTotal();
    }

    private void updateTotal() {

        totalLabel.setText(
                "Total : Rp "
                + transaksiAktif.hitungTotal());

        hitungKembalian();
    }

    private void hitungKembalian() {

        try {

            double bayar =
                    Double.parseDouble(
                            bayarField.getText());

            double total =
                    transaksiAktif.hitungTotal();

            kembalianLabel.setText(
                    "Rp "
                    + (bayar - total));

        } catch (Exception e) {

            kembalianLabel.setText(
                    "Rp 0");
        }
    }

    private void simpanTransaksi() {

        try {

            double bayar =
                    Double.parseDouble(
                            bayarField.getText());

            if (!transaksiAktif
                    .verifikasiBayar(bayar)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Uang kurang");

                return;
            }

            transaksiAktif
                    .setTotalBayar(bayar);

            transaksiAktif
                    .konfirmasiTransaksi();

            transaksiService
                    .simpanTransaksi(
                            transaksiAktif);

            transaksiTerakhir =
                    transaksiAktif;

            riwayatArea.setText(
                    transaksiService
                            .tampilkanRiwayat());

            jumlahTransaksiLabel.setText(
                    "Jumlah Transaksi : "
                    + transaksiService
                    .jumlahTransaksi());

            pendapatanLabel.setText(
                    "Pendapatan : Rp "
                    + transaksiService
                    .hitungPendapatan());

            JOptionPane.showMessageDialog(
                    this,
                    "Transaksi berhasil disimpan");

            resetForm();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Input pembayaran tidak valid");
        }
    }

    private void cetakStruk() {

        if (transaksiTerakhir == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Belum ada transaksi");

            return;
        }

        JTextArea area =
                new JTextArea(
                        transaksiService
                                .generateStruk(
                                        transaksiTerakhir));

        area.setEditable(false);

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(area),
                "Struk",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {

        model.setRowCount(0);

        jumlahField.setText("");

        bayarField.setText("");

        totalLabel.setText(
                "Total : Rp 0");

        kembalianLabel.setText(
                "Rp 0");

        transaksiAktif =
                new Transaksi(
                        generateId());
    }
}