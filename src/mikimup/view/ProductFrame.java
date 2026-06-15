package mikimup.view;

import mikimup.model.Product;
import mikimup.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductFrame extends JPanel {

    // ── Color Palette ────────────────────────────────
    private static final Color MERAH_UTAMA   = new Color(0xE5, 0x39, 0x5A); // #E5395A
    private static final Color MERAH_GELAP   = new Color(0xC9, 0x2C, 0x4A); // #C92C4A
    private static final Color PINK_LEMBUT   = new Color(0xFC, 0xE4, 0xE8); // #FCE4E8
    private static final Color PUTIH         = new Color(0xFF, 0xFF, 0xFF); // #FFFFFF
    private static final Color ABU_BG        = new Color(0xF8, 0xF8, 0xF8); // #F8F8F8
    private static final Color ABU_BORDER    = new Color(0xE5, 0xE7, 0xEB); // #E5E7EB
    private static final Color TEKS_GELAP    = new Color(0x1F, 0x29, 0x37); // #1F2937
    private static final Color TEKS_SEKUNDER = new Color(0x4B, 0x55, 0x63); // #4B5563
    private static final Color HIJAU_SUKSES  = new Color(0x22, 0xC5, 0x5E); // #22C55E
    private static final Color KUNING_WARN   = new Color(0xF5, 0x9E, 0x0B); // #F59E0B
    private static final Color MERAH_ERROR   = new Color(0xEF, 0x44, 0x44); // #EF4444
    private static final Color BIRU_INFO     = new Color(0x3B, 0x82, 0xF6); // #3B82F6

    // ── Font (Segoe UI sebagai implementasi Poppins) ─
    private static final Font FONT_JUDUL  = new Font("Segoe UI", Font.BOLD,  18);
    private static final Font FONT_BOLD   = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_PLAIN  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 12);

    // ── Komponen ─────────────────────────────────────
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtCari;
    private JLabel lblPeringatan;

    private final ProductService productService = new ProductService();

    private static final String[] COLUMNS = {
        "ID", "Kode", "Nama Produk", "Kategori",
        "Harga Beli", "Harga Jual", "Stok", "Stok Min"
    };

    public ProductFrame() {
        setLayout(new BorderLayout(10, 10));
        setBackground(ABU_BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(buildTopPanel(),    BorderLayout.NORTH);
        add(buildTablePanel(),  BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);

        refreshTable(productService.getAllProducts());
        checkStokRendah();
    }

    // ─────────────────────────────────────────────────
    // Panel Atas: judul + search
    // ─────────────────────────────────────────────────
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(ABU_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Judul
        JLabel lblJudul = new JLabel("Manajemen Produk");
        lblJudul.setFont(FONT_JUDUL);
        lblJudul.setForeground(TEKS_GELAP);

        // Peringatan stok rendah
        lblPeringatan = new JLabel();
        lblPeringatan.setFont(FONT_BOLD);
        lblPeringatan.setForeground(MERAH_ERROR);

        JPanel judulPanel = new JPanel(new BorderLayout());
        judulPanel.setBackground(ABU_BG);
        judulPanel.add(lblJudul,      BorderLayout.WEST);
        judulPanel.add(lblPeringatan, BorderLayout.EAST);

        // Search bar
        JPanel cariPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        cariPanel.setBackground(ABU_BG);

        txtCari = new JTextField(22);
        txtCari.setFont(FONT_PLAIN);
        txtCari.setForeground(TEKS_GELAP);
        txtCari.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ABU_BORDER, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton btnCari = createButton("Cari", BIRU_INFO);
        JButton btnReset = createButton("Reset", TEKS_SEKUNDER);

        btnCari.addActionListener(e -> doSearch());
        btnReset.addActionListener(e -> {
            txtCari.setText("");
            refreshTable(productService.getAllProducts());
        });
        txtCari.addActionListener(e -> doSearch());

        cariPanel.add(new JLabel("🔍") {{ setFont(FONT_PLAIN); }});
        cariPanel.add(txtCari);
        cariPanel.add(btnCari);
        cariPanel.add(btnReset);

        panel.add(judulPanel, BorderLayout.NORTH);
        panel.add(cariPanel,  BorderLayout.SOUTH);
        return panel;
    }

    // ─────────────────────────────────────────────────
    // Tabel Produk
    // ─────────────────────────────────────────────────
    private JScrollPane buildTablePanel() {
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setFont(FONT_PLAIN);
        table.setForeground(TEKS_GELAP);
        table.setRowHeight(32);
        table.setShowVerticalLines(false);
        table.setGridColor(ABU_BORDER);
        table.setSelectionBackground(PINK_LEMBUT);
        table.setSelectionForeground(TEKS_GELAP);

        // Header
        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setBackground(MERAH_UTAMA);
        table.getTableHeader().setForeground(PUTIH);
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        // Sembunyikan kolom ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // Zebra stripe + warna peringatan stok rendah
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val,
                    boolean sel, boolean focus, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, sel, focus, row, col);
                int stok    = (int) tbl.getModel().getValueAt(row, 6);
                int stokMin = (int) tbl.getModel().getValueAt(row, 7);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                if (sel) {
                    c.setBackground(PINK_LEMBUT);
                    c.setForeground(TEKS_GELAP);
                } else if (stok <= stokMin) {
                    c.setBackground(new Color(0xFF, 0xEB, 0xEE)); // merah sangat muda
                    c.setForeground(MERAH_GELAP);
                } else if (row % 2 == 0) {
                    c.setBackground(PUTIH);
                    c.setForeground(TEKS_GELAP);
                } else {
                    c.setBackground(ABU_BG);
                    c.setForeground(TEKS_GELAP);
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(ABU_BORDER, 1));
        scroll.getViewport().setBackground(PUTIH);
        return scroll;
    }

    // ─────────────────────────────────────────────────
    // Panel Bawah: tombol aksi
    // ─────────────────────────────────────────────────
    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 12));
        panel.setBackground(ABU_BG);
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ABU_BORDER));

        JButton btnTambah    = createButton("+ Tambah Produk",  MERAH_UTAMA);
        JButton btnEdit      = createButton("✎ Edit",            BIRU_INFO);
        JButton btnHapus     = createButton("✕ Hapus",           MERAH_ERROR);
        JButton btnTambahSt  = createButton("↑ Tambah Stok",     HIJAU_SUKSES);
        JButton btnRefresh   = createButton("↻ Refresh",         TEKS_SEKUNDER);

        btnTambah.addActionListener(e -> openTambahProduk());
        btnEdit.addActionListener(e -> openEditProduk());
        btnHapus.addActionListener(e -> doHapusProduk());
        btnTambahSt.addActionListener(e -> openTambahStok());
        btnRefresh.addActionListener(e -> {
            refreshTable(productService.getAllProducts());
            checkStokRendah();
        });

        panel.add(btnTambah);
        panel.add(btnEdit);
        panel.add(btnHapus);
        panel.add(btnTambahSt);
        panel.add(btnRefresh);
        return panel;
    }

    // ─────────────────────────────────────────────────
    // Aksi
    // ─────────────────────────────────────────────────
    private void doSearch() {
        String kw = txtCari.getText().trim();
        refreshTable(kw.isEmpty()
            ? productService.getAllProducts()
            : productService.searchProducts(kw));
    }

    private void openTambahProduk() {
        ProductFormDialog dialog = new ProductFormDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), null, productService);
        dialog.setVisible(true);
        if (dialog.isSaved()) { refreshTable(productService.getAllProducts()); checkStokRendah(); }
    }

    private void openEditProduk() {
        Product p = getSelectedProduct();
        if (p == null) { showWarn("Pilih produk yang akan diedit."); return; }
        ProductFormDialog dialog = new ProductFormDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), p, productService);
        dialog.setVisible(true);
        if (dialog.isSaved()) { refreshTable(productService.getAllProducts()); checkStokRendah(); }
    }

    private void doHapusProduk() {
        Product p = getSelectedProduct();
        if (p == null) { showWarn("Pilih produk yang akan dihapus."); return; }
        int ok = JOptionPane.showConfirmDialog(this,
            "Yakin hapus produk \"" + p.getNamaProduct() + "\"?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (ok == JOptionPane.YES_OPTION) {
            String err = productService.deleteProduct(p.getIdProduct());
            if (err == null) {
                JOptionPane.showMessageDialog(this, "Produk berhasil dihapus.");
                refreshTable(productService.getAllProducts());
                checkStokRendah();
            } else {
                JOptionPane.showMessageDialog(this, err, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openTambahStok() {
        Product p = getSelectedProduct();
        if (p == null) { showWarn("Pilih produk untuk menambah stok."); return; }
        StockFormDialog dialog = new StockFormDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), p, productService);
        dialog.setVisible(true);
        if (dialog.isSaved()) { refreshTable(productService.getAllProducts()); checkStokRendah(); }
    }

    // ─────────────────────────────────────────────────
    // Helper
    // ─────────────────────────────────────────────────
    private void refreshTable(List<Product> list) {
        tableModel.setRowCount(0);
        for (Product p : list) {
            tableModel.addRow(new Object[]{
                p.getIdProduct(), p.getKodeProduct(), p.getNamaProduct(), p.getKategori(),
                "Rp " + String.format("%,.0f", p.getHargaBeli()),
                "Rp " + String.format("%,.0f", p.getHargaJual()),
                p.getStok(), p.getStokMinimum()
            });
        }
    }

    private Product getSelectedProduct() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        int id = (int) tableModel.getValueAt(row, 0);
        return productService.getAllProducts().stream()
            .filter(p -> p.getIdProduct() == id).findFirst().orElse(null);
    }

    private void checkStokRendah() {
        long n = productService.getAllProducts().stream().filter(Product::isStokRendah).count();
        lblPeringatan.setText(n > 0 ? "⚠ " + n + " produk stok rendah!" : "");
    }

    private void showWarn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Perhatian", JOptionPane.WARNING_MESSAGE);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BOLD);
        btn.setBackground(bg);
        btn.setForeground(PUTIH);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(145, 34));
        btn.setOpaque(true);
        return btn;
    }
}