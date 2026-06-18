package main.dao;

import database.DatabaseConnection;
import main.model.Barang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    /*
     * ===========================
     * AMBIL SEMUA PRODUK
     * ===========================
     */

    public ArrayList<Barang> getAllProducts() {
        ArrayList<Barang> list = new ArrayList<>();

        String sql =
                "SELECT * FROM products "
                + "ORDER BY nama_product ASC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Barang barang = mapResultSetToBarang(rs);
                list.add(barang);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil data produk.");
            e.printStackTrace();
        }

        return list;
    }

    /*
     * ===========================
     * METHOD CADANGAN UNTUK KODE LAMA
     * ===========================
     */

    public ArrayList<Barang> getDaftarBarang() {
        return getAllProducts();
    }

    /*
     * ===========================
     * AMBIL PRODUK BERDASARKAN ID
     * ===========================
     */

    public Barang getProductById(int idProduct) {
        String sql =
                "SELECT * FROM products "
                + "WHERE id_product = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idProduct);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBarang(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil produk berdasarkan ID.");
            e.printStackTrace();
        }

        return null;
    }

    /*
     * ===========================
     * AMBIL PRODUK BERDASARKAN KODE
     * ===========================
     */

    public Barang getProductByKode(String kodeProduct) {
        String sql =
                "SELECT * FROM products "
                + "WHERE kode_product = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, kodeProduct);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBarang(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil produk berdasarkan kode.");
            e.printStackTrace();
        }

        return null;
    }

    /*
     * METHOD CADANGAN
     */

    public Barang getProductByKodeBarang(String kodeBarang) {
        return getProductByKode(kodeBarang);
    }

    /*
     * ===========================
     * CARI PRODUK
     * ===========================
     */

    public ArrayList<Barang> searchProduct(String keyword) {
        ArrayList<Barang> list = new ArrayList<>();

        String sql =
                "SELECT * FROM products "
                + "WHERE kode_product LIKE ? "
                + "OR nama_product LIKE ? "
                + "OR kategori LIKE ? "
                + "ORDER BY nama_product ASC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            String key = "%" + keyword + "%";

            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Barang barang = mapResultSetToBarang(rs);
                    list.add(barang);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal mencari produk.");
            e.printStackTrace();
        }

        return list;
    }

    /*
     * METHOD CADANGAN
     */

    public ArrayList<Barang> cariProduk(String keyword) {
        return searchProduct(keyword);
    }

    /*
     * ===========================
     * TAMBAH PRODUK
     * ===========================
     */

    public boolean insertProduct(Barang barang) {
        String sql =
                "INSERT INTO products "
                + "(kode_product, nama_product, kategori, harga_beli, harga_jual, stok, stok_minimum) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, barang.getKodeProduct());
            ps.setString(2, barang.getNamaProduct());
            ps.setString(3, barang.getKategori());
            ps.setDouble(4, barang.getHargaBeli());
            ps.setDouble(5, barang.getHargaJual());
            ps.setInt(6, barang.getStok());
            ps.setInt(7, barang.getStokMinimum());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal menambahkan produk.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * METHOD CADANGAN
     */

    public boolean tambahProduct(Barang barang) {
        return insertProduct(barang);
    }

    public boolean tambahBarang(Barang barang) {
        return insertProduct(barang);
    }

    /*
     * ===========================
     * UPDATE PRODUK
     * ===========================
     */

    public boolean updateProduct(Barang barang) {
        String sql =
                "UPDATE products SET "
                + "kode_product = ?, "
                + "nama_product = ?, "
                + "kategori = ?, "
                + "harga_beli = ?, "
                + "harga_jual = ?, "
                + "stok = ?, "
                + "stok_minimum = ? "
                + "WHERE id_product = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, barang.getKodeProduct());
            ps.setString(2, barang.getNamaProduct());
            ps.setString(3, barang.getKategori());
            ps.setDouble(4, barang.getHargaBeli());
            ps.setDouble(5, barang.getHargaJual());
            ps.setInt(6, barang.getStok());
            ps.setInt(7, barang.getStokMinimum());
            ps.setInt(8, barang.getIdProduct());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal mengubah produk.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * METHOD CADANGAN
     */

    public boolean updateBarang(Barang barang) {
        return updateProduct(barang);
    }

    /*
     * ===========================
     * HAPUS PRODUK
     * ===========================
     */

    public boolean deleteProduct(int idProduct) {
        String sql =
                "DELETE FROM products "
                + "WHERE id_product = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idProduct);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal menghapus produk.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * METHOD CADANGAN
     */

    public boolean hapusBarang(int idProduct) {
        return deleteProduct(idProduct);
    }

    /*
     * ===========================
     * UPDATE STOK
     * ===========================
     */

    public boolean updateStock(int idProduct, int stokBaru) {
        String sql =
                "UPDATE products "
                + "SET stok = ? "
                + "WHERE id_product = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, stokBaru);
            ps.setInt(2, idProduct);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal update stok produk.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * METHOD CADANGAN
     */

    public boolean updateStok(int idProduct, int stokBaru) {
        return updateStock(idProduct, stokBaru);
    }

    /*
     * ===========================
     * KURANGI STOK
     * ===========================
     */

    public boolean reduceStock(int idProduct, int jumlah) {
        String sql =
                "UPDATE products "
                + "SET stok = stok - ? "
                + "WHERE id_product = ? AND stok >= ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, jumlah);
            ps.setInt(2, idProduct);
            ps.setInt(3, jumlah);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal mengurangi stok produk.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * METHOD CADANGAN
     */

    public boolean kurangiStok(int idProduct, int jumlah) {
        return reduceStock(idProduct, jumlah);
    }

    /*
     * ===========================
     * TAMBAH STOK
     * ===========================
     */

    public boolean addStock(int idProduct, int jumlah) {
        String sql =
                "UPDATE products "
                + "SET stok = stok + ? "
                + "WHERE id_product = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, jumlah);
            ps.setInt(2, idProduct);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal menambahkan stok produk.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * METHOD CADANGAN
     */

    public boolean tambahStok(int idProduct, int jumlah) {
        return addStock(idProduct, jumlah);
    }

    /*
     * ===========================
     * PRODUK STOK MINIMUM
     * ===========================
     */

    public ArrayList<Barang> getLowStockProducts() {
        ArrayList<Barang> list = new ArrayList<>();

        String sql =
                "SELECT * FROM products "
                + "WHERE stok <= stok_minimum "
                + "ORDER BY stok ASC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Barang barang = mapResultSetToBarang(rs);
                list.add(barang);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil produk stok minimum.");
            e.printStackTrace();
        }

        return list;
    }

    /*
     * METHOD CADANGAN
     */

    public ArrayList<Barang> getStokMinimum() {
        return getLowStockProducts();
    }

    /*
     * ===========================
     * MAPPING RESULTSET KE BARANG
     * ===========================
     */

    private Barang mapResultSetToBarang(ResultSet rs) throws Exception {
        Barang barang = new Barang();

        barang.setIdProduct(rs.getInt("id_product"));
        barang.setKodeProduct(rs.getString("kode_product"));
        barang.setNamaProduct(rs.getString("nama_product"));
        barang.setKategori(rs.getString("kategori"));
        barang.setHargaBeli(rs.getDouble("harga_beli"));
        barang.setHargaJual(rs.getDouble("harga_jual"));
        barang.setStok(rs.getInt("stok"));
        barang.setStokMinimum(rs.getInt("stok_minimum"));

        return barang;
    }
}