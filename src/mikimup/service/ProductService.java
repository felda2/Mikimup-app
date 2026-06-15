package mikimup.service;

import mikimup.dao.ProductDAO;
import mikimup.dao.StockHistoryDAO;
import mikimup.model.Product;
import mikimup.model.StockHistory;

import java.util.List;

public class ProductService {

    private final ProductDAO productDAO         = new ProductDAO();
    private final StockHistoryDAO stockHistoryDAO = new StockHistoryDAO();

    // ─────────────────────────────────────────────
    // Produk — CRUD
    // ─────────────────────────────────────────────

    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    public List<Product> searchProducts(String keyword) {
        return productDAO.searchProducts(keyword);
    }

    /**
     * Tambah produk baru.
     * @return pesan error, atau null kalau berhasil
     */
    public String addProduct(Product p) {
        String err = validateProduct(p);
        if (err != null) return err;

        boolean ok = productDAO.addProduct(p);
        return ok ? null : "Gagal menyimpan produk ke database.";
    }

    /**
     * Edit produk.
     * @return pesan error, atau null kalau berhasil
     */
    public String updateProduct(Product p) {
        String err = validateProduct(p);
        if (err != null) return err;

        boolean ok = productDAO.updateProduct(p);
        return ok ? null : "Gagal mengupdate produk.";
    }

    /**
     * Hapus produk.
     * @return pesan error, atau null kalau berhasil
     */
    public String deleteProduct(int idProduct) {
        boolean ok = productDAO.deleteProduct(idProduct);
        return ok ? null : "Gagal menghapus produk.";
    }

    // ─────────────────────────────────────────────
    // Stok — tambah stok & simpan histori
    // ─────────────────────────────────────────────

    /**
     * Tambah stok produk.
     * Validasi: jumlah harus > 0.
     * Otomatis menyimpan ke stock_history.
     *
     * @return pesan error, atau null kalau berhasil
     */
    public String tambahStok(int idProduct, int jumlah, String keterangan) {
        if (jumlah <= 0) {
            return "Jumlah stok yang ditambahkan harus lebih dari 0.";
        }

        Product p = productDAO.getProductById(idProduct);
        if (p == null) return "Produk tidak ditemukan.";

        int stokBaru = p.getStok() + jumlah;

        // Update stok di tabel products
        boolean okStok = productDAO.updateStok(idProduct, stokBaru);
        if (!okStok) return "Gagal mengupdate stok.";

        // Simpan ke histori
        StockHistory sh = new StockHistory(idProduct, "masuk", jumlah,
                keterangan == null || keterangan.isBlank() ? "Tambah stok" : keterangan);
        stockHistoryDAO.addStockHistory(sh);

        return null; // berhasil
    }

    /**
     * Penyesuaian stok (set langsung ke nilai tertentu).
     * Validasi: stok tidak boleh negatif.
     *
     * @return pesan error, atau null kalau berhasil
     */
    public String penyesuaianStok(int idProduct, int stokBaru, String keterangan) {
        if (stokBaru < 0) {
            return "Stok tidak boleh bernilai negatif.";
        }

        Product p = productDAO.getProductById(idProduct);
        if (p == null) return "Produk tidak ditemukan.";

        int selisih = stokBaru - p.getStok();
        boolean okStok = productDAO.updateStok(idProduct, stokBaru);
        if (!okStok) return "Gagal menyesuaikan stok.";

        String tipe = selisih >= 0 ? "masuk" : "keluar";
        StockHistory sh = new StockHistory(idProduct, "penyesuaian", Math.abs(selisih),
                keterangan == null || keterangan.isBlank() ? "Penyesuaian stok" : keterangan);
        stockHistoryDAO.addStockHistory(sh);

        return null;
    }

    // ─────────────────────────────────────────────
    // Histori stok
    // ─────────────────────────────────────────────

    public List<StockHistory> getAllStockHistory() {
        return stockHistoryDAO.getAllStockHistory();
    }

    public List<StockHistory> getStockHistoryByProduct(int idProduct) {
        return stockHistoryDAO.getStockHistoryByProduct(idProduct);
    }

    // ─────────────────────────────────────────────
    // Helper: validasi input produk
    // ─────────────────────────────────────────────
    private String validateProduct(Product p) {
        if (p.getKodeProduct() == null || p.getKodeProduct().isBlank())
            return "Kode produk tidak boleh kosong.";
        if (p.getNamaProduct() == null || p.getNamaProduct().isBlank())
            return "Nama produk tidak boleh kosong.";
        if (p.getKategori() == null || p.getKategori().isBlank())
            return "Kategori tidak boleh kosong.";
        if (p.getHargaBeli() <= 0)
            return "Harga beli harus lebih dari 0.";
        if (p.getHargaJual() <= 0)
            return "Harga jual harus lebih dari 0.";
        if (p.getStok() < 0)
            return "Stok tidak boleh negatif.";
        if (p.getStokMinimum() < 0)
            return "Stok minimum tidak boleh negatif.";
        return null;
    }
}