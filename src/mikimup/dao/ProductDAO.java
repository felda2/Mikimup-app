package mikimup.dao;

import mikimup.config.DatabaseConnection;
import mikimup.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // ─────────────────────────────────────────────
    // 1. Ambil semua produk
    // ─────────────────────────────────────────────
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY nama_product ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getAllProducts: " + e.getMessage());
        }
        return list;
    }

    // ─────────────────────────────────────────────
    // 2. Cari produk berdasarkan keyword (kode / nama / kategori)
    // ─────────────────────────────────────────────
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products " +
                     "WHERE kode_product LIKE ? OR nama_product LIKE ? OR kategori LIKE ? " +
                     "ORDER BY nama_product ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error searchProducts: " + e.getMessage());
        }
        return list;
    }

    // ─────────────────────────────────────────────
    // 3. Tambah produk baru
    // ─────────────────────────────────────────────
    public boolean addProduct(Product p) {
        String sql = "INSERT INTO products (kode_product, nama_product, kategori, " +
                     "harga_beli, harga_jual, stok, stok_minimum) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getKodeProduct());
            ps.setString(2, p.getNamaProduct());
            ps.setString(3, p.getKategori());
            ps.setDouble(4, p.getHargaBeli());
            ps.setDouble(5, p.getHargaJual());
            ps.setInt(6, p.getStok());
            ps.setInt(7, p.getStokMinimum());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error addProduct: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // 4. Edit produk
    // ─────────────────────────────────────────────
    public boolean updateProduct(Product p) {
        String sql = "UPDATE products SET kode_product=?, nama_product=?, kategori=?, " +
                     "harga_beli=?, harga_jual=?, stok_minimum=? WHERE id_product=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getKodeProduct());
            ps.setString(2, p.getNamaProduct());
            ps.setString(3, p.getKategori());
            ps.setDouble(4, p.getHargaBeli());
            ps.setDouble(5, p.getHargaJual());
            ps.setInt(6, p.getStokMinimum());
            ps.setInt(7, p.getIdProduct());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updateProduct: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // 5. Hapus produk
    // ─────────────────────────────────────────────
    public boolean deleteProduct(int idProduct) {
        String sql = "DELETE FROM products WHERE id_product=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProduct);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleteProduct: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // 6. Update stok saja (dipakai saat tambah stok)
    // ─────────────────────────────────────────────
    public boolean updateStok(int idProduct, int stokBaru) {
        String sql = "UPDATE products SET stok=? WHERE id_product=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, stokBaru);
            ps.setInt(2, idProduct);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updateStok: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // 7. Cari produk berdasarkan ID
    // ─────────────────────────────────────────────
    public Product getProductById(int idProduct) {
        String sql = "SELECT * FROM products WHERE id_product=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProduct);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);

        } catch (SQLException e) {
            System.out.println("Error getProductById: " + e.getMessage());
        }
        return null;
    }

    // ─────────────────────────────────────────────
    // Helper: mapping ResultSet → Product
    // ─────────────────────────────────────────────
    private Product mapRow(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("id_product"),
            rs.getString("kode_product"),
            rs.getString("nama_product"),
            rs.getString("kategori"),
            rs.getDouble("harga_beli"),
            rs.getDouble("harga_jual"),
            rs.getInt("stok"),
            rs.getInt("stok_minimum"),
            rs.getString("created_at"),
            rs.getString("updated_at")
        );
    }
}