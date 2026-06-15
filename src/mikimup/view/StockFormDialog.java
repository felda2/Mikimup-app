package mikimup.view;

import mikimup.model.Product;
import mikimup.service.ProductService;

import javax.swing.*;
import java.awt.*;

public class StockFormDialog extends JDialog {

    private static final Color MERAH_UTAMA   = new Color(0xE5, 0x39, 0x5A);
    private static final Color MERAH_GELAP   = new Color(0xC9, 0x2C, 0x4A);
    private static final Color PINK_LEMBUT   = new Color(0xFC, 0xE4, 0xE8);
    private static final Color PUTIH         = Color.WHITE;
    private static final Color ABU_BG        = new Color(0xF8, 0xF8, 0xF8);
    private static final Color ABU_BORDER    = new Color(0xE5, 0xE7, 0xEB);
    private static final Color TEKS_GELAP    = new Color(0x1F, 0x29, 0x37);
    private static final Color TEKS_SEKUNDER = new Color(0x4B, 0x55, 0x63);
    private static final Color HIJAU_SUKSES  = new Color(0x22, 0xC5, 0x5E);

    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 13);

    private JSpinner spinnerJumlah;
    private JTextArea txtKeterangan;
    private JComboBox<String> cbTipe;

    private boolean saved = false;
    private final Product product;
    private final ProductService productService;

    public StockFormDialog(JFrame parent, Product product, ProductService service) {
        super(parent, "Kelola Stok Produk", true);
        this.product        = product;
        this.productService = service;

        setLayout(new BorderLayout());
        setSize(400, 360);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(PUTIH);

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildForm(),    BorderLayout.CENTER);
        add(buildButtons(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MERAH_UTAMA);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel lbl = new JLabel("Kelola Stok — " + product.getNamaProduct());
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(PUTIH);
        panel.add(lbl, BorderLayout.WEST);
        return panel;
    }

    private JPanel buildForm() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(PUTIH);

        // Info stok saat ini
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        infoPanel.setBackground(PINK_LEMBUT);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JLabel lblKode = new JLabel("Kode: " + product.getKodeProduct());
        lblKode.setFont(FONT_PLAIN);
        lblKode.setForeground(TEKS_SEKUNDER);

        JLabel lblStok = new JLabel("Stok Sekarang: " + product.getStok());
        lblStok.setFont(FONT_BOLD);
        lblStok.setForeground(MERAH_GELAP);

        infoPanel.add(lblKode);
        infoPanel.add(lblStok);

        // Form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PUTIH);
        formPanel.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 4, 8, 4);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        // Tipe
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0.4;
        JLabel lblTipe = new JLabel("Tipe Perubahan:");
        lblTipe.setFont(FONT_BOLD);
        lblTipe.setForeground(TEKS_GELAP);
        formPanel.add(lblTipe, gc);

        gc.gridx = 1; gc.weightx = 0.6;
        cbTipe = new JComboBox<>(new String[]{"Tambah Stok (Masuk)", "Penyesuaian Stok"});
        cbTipe.setFont(FONT_PLAIN);
        cbTipe.setBackground(PUTIH);
        cbTipe.setBorder(BorderFactory.createLineBorder(ABU_BORDER));
        formPanel.add(cbTipe, gc);

        // Jumlah
        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0.4;
        JLabel lblJumlah = new JLabel("Jumlah:");
        lblJumlah.setFont(FONT_BOLD);
        lblJumlah.setForeground(TEKS_GELAP);
        formPanel.add(lblJumlah, gc);

        gc.gridx = 1; gc.weightx = 0.6;
        spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 0, 99999, 1));
        spinnerJumlah.setFont(FONT_PLAIN);
        spinnerJumlah.setPreferredSize(new Dimension(130, 32));
        spinnerJumlah.setBorder(BorderFactory.createLineBorder(ABU_BORDER));
        formPanel.add(spinnerJumlah, gc);

        // Keterangan
        gc.gridx = 0; gc.gridy = 2; gc.weightx = 0.4;
        JLabel lblKet = new JLabel("Keterangan:");
        lblKet.setFont(FONT_BOLD);
        lblKet.setForeground(TEKS_GELAP);
        formPanel.add(lblKet, gc);

        gc.gridx = 1; gc.weightx = 0.6;
        txtKeterangan = new JTextArea(3, 15);
        txtKeterangan.setFont(FONT_PLAIN);
        txtKeterangan.setLineWrap(true);
        txtKeterangan.setWrapStyleWord(true);
        txtKeterangan.setForeground(TEKS_GELAP);
        JScrollPane scroll = new JScrollPane(txtKeterangan);
        scroll.setBorder(BorderFactory.createLineBorder(ABU_BORDER));
        formPanel.add(scroll, gc);

        outer.add(infoPanel, BorderLayout.NORTH);
        outer.add(formPanel, BorderLayout.CENTER);
        return outer;
    }

    private JPanel buildButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        panel.setBackground(ABU_BG);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ABU_BORDER));

        JButton btnBatal = new JButton("Batal");
        btnBatal.setFont(FONT_BOLD);
        btnBatal.setPreferredSize(new Dimension(90, 34));
        btnBatal.setForeground(TEKS_SEKUNDER);
        btnBatal.setBackground(PUTIH);
        btnBatal.setBorder(BorderFactory.createLineBorder(ABU_BORDER));
        btnBatal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnBatal.addActionListener(e -> dispose());

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setFont(FONT_BOLD);
        btnSimpan.setBackground(HIJAU_SUKSES);
        btnSimpan.setForeground(PUTIH);
        btnSimpan.setBorderPainted(false);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setOpaque(true);
        btnSimpan.setPreferredSize(new Dimension(90, 34));
        btnSimpan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(e -> doSimpan());

        panel.add(btnBatal);
        panel.add(btnSimpan);
        return panel;
    }

    private void doSimpan() {
        int jumlah      = (int) spinnerJumlah.getValue();
        String keterangan = txtKeterangan.getText().trim();
        boolean isMasuk = cbTipe.getSelectedIndex() == 0;

        String err = isMasuk
            ? productService.tambahStok(product.getIdProduct(), jumlah, keterangan)
            : productService.penyesuaianStok(product.getIdProduct(), jumlah, keterangan);

        if (err == null) {
            JOptionPane.showMessageDialog(this, "Stok berhasil diperbarui!");
            saved = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, err, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() { return saved; }
}