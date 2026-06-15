package mikimup.view;

import mikimup.model.Product;
import mikimup.service.ProductService;

import javax.swing.*;
import java.awt.*;

public class ProductFormDialog extends JDialog {

    private static final Color MERAH_UTAMA   = new Color(0xE5, 0x39, 0x5A);
    private static final Color MERAH_GELAP   = new Color(0xC9, 0x2C, 0x4A);
    private static final Color PINK_LEMBUT   = new Color(0xFC, 0xE4, 0xE8);
    private static final Color PUTIH         = Color.WHITE;
    private static final Color ABU_BG        = new Color(0xF8, 0xF8, 0xF8);
    private static final Color ABU_BORDER    = new Color(0xE5, 0xE7, 0xEB);
    private static final Color TEKS_GELAP    = new Color(0x1F, 0x29, 0x37);
    private static final Color TEKS_SEKUNDER = new Color(0x4B, 0x55, 0x63);
    private static final Color PLACEHOLDER   = new Color(0x9C, 0xA3, 0xAF);

    private static final Font FONT_BOLD  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_PLAIN = new Font("Segoe UI", Font.PLAIN, 13);

    private JTextField txtKode, txtNama, txtKategori;
    private JTextField txtHargaBeli, txtHargaJual;
    private JTextField txtStok, txtStokMin;

    private boolean saved = false;
    private final Product existingProduct;
    private final ProductService productService;

    public ProductFormDialog(JFrame parent, Product product, ProductService service) {
        super(parent, product == null ? "Tambah Produk Baru" : "Edit Produk", true);
        this.existingProduct = product;
        this.productService  = service;

        setLayout(new BorderLayout());
        setSize(440, 460);
        setLocationRelativeTo(parent);
        setResizable(false);
        getContentPane().setBackground(PUTIH);

        add(buildHeader(),  BorderLayout.NORTH);
        add(buildForm(),    BorderLayout.CENTER);
        add(buildButtons(), BorderLayout.SOUTH);

        if (existingProduct != null) populateFields();
    }

    // Header merah
    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MERAH_UTAMA);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel lbl = new JLabel(existingProduct == null ? "Tambah Produk Baru" : "Edit Produk");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(PUTIH);
        panel.add(lbl, BorderLayout.WEST);
        return panel;
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(18, 24, 10, 24));
        panel.setBackground(PUTIH);

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 4, 6, 4);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        txtKode      = styledField();
        txtNama      = styledField();
        txtKategori  = styledField();
        txtHargaBeli = styledField();
        txtHargaJual = styledField();
        txtStok      = styledField();
        txtStokMin   = styledField();

        if (existingProduct != null) {
            txtKode.setEditable(false);
            txtKode.setBackground(ABU_BG);
            txtStok.setEditable(false);
            txtStok.setBackground(ABU_BG);
            txtStok.setToolTipText("Ubah stok lewat tombol Tambah Stok");
        }

        String[] labels = {
            "Kode Produk *", "Nama Produk *", "Kategori *",
            "Harga Beli (Rp) *", "Harga Jual (Rp) *",
            "Stok Awal *", "Stok Minimum *"
        };
        JTextField[] fields = {
            txtKode, txtNama, txtKategori,
            txtHargaBeli, txtHargaJual, txtStok, txtStokMin
        };

        for (int i = 0; i < labels.length; i++) {
            gc.gridx = 0; gc.gridy = i; gc.weightx = 0.35;
            JLabel lbl = new JLabel(labels[i]);
            lbl.setFont(FONT_BOLD);
            lbl.setForeground(TEKS_GELAP);
            panel.add(lbl, gc);

            gc.gridx = 1; gc.weightx = 0.65;
            panel.add(fields[i], gc);
        }

        return panel;
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
        btnSimpan.setBackground(MERAH_UTAMA);
        btnSimpan.setForeground(PUTIH);
        btnSimpan.setBorderPainted(false);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setOpaque(true);
        btnSimpan.setPreferredSize(new Dimension(90, 34));
        btnSimpan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(e -> doSimpan());

        // Hover effect tombol simpan
        btnSimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btnSimpan.setBackground(MERAH_GELAP); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btnSimpan.setBackground(MERAH_UTAMA); }
        });

        panel.add(btnBatal);
        panel.add(btnSimpan);
        return panel;
    }

    private void doSimpan() {
        String kode     = txtKode.getText().trim();
        String nama     = txtNama.getText().trim();
        String kategori = txtKategori.getText().trim();
        double hargaBeli, hargaJual;
        int stok, stokMin;

        try {
            hargaBeli = Double.parseDouble(txtHargaBeli.getText().trim().replace(",", "."));
            hargaJual = Double.parseDouble(txtHargaJual.getText().trim().replace(",", "."));
            stok      = Integer.parseInt(txtStok.getText().trim());
            stokMin   = Integer.parseInt(txtStokMin.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka.",
                "Input Salah", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Product p = new Product(kode, nama, kategori, hargaBeli, hargaJual, stok, stokMin);
        String err;
        if (existingProduct == null) {
            err = productService.addProduct(p);
        } else {
            p.setIdProduct(existingProduct.getIdProduct());
            p.setStok(existingProduct.getStok());
            err = productService.updateProduct(p);
        }

        if (err == null) {
            JOptionPane.showMessageDialog(this, "Produk berhasil disimpan!");
            saved = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, err, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateFields() {
        txtKode.setText(existingProduct.getKodeProduct());
        txtNama.setText(existingProduct.getNamaProduct());
        txtKategori.setText(existingProduct.getKategori());
        txtHargaBeli.setText(String.valueOf((int) existingProduct.getHargaBeli()));
        txtHargaJual.setText(String.valueOf((int) existingProduct.getHargaJual()));
        txtStok.setText(String.valueOf(existingProduct.getStok()));
        txtStokMin.setText(String.valueOf(existingProduct.getStokMinimum()));
    }

    private JTextField styledField() {
        JTextField f = new JTextField();
        f.setFont(FONT_PLAIN);
        f.setForeground(TEKS_GELAP);
        f.setPreferredSize(new Dimension(200, 32));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ABU_BORDER, 1, true),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        return f;
    }

    public boolean isSaved() { return saved; }
}