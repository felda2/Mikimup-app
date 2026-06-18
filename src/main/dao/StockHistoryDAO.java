package main.dao;

import database.DatabaseConnection;
import main.model.StockHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockHistoryDAO {

    /*
     * ===========================
     * INSERT RIWAYAT STOK
     * ===========================
     */

    public boolean insertStockHistory(StockHistory stockHistory) {
        String sql =
                "INSERT INTO stock_history "
                + "(id_product, tipe, jumlah, keterangan) "
                + "VALUES (?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, stockHistory.getIdProduct());
            ps.setString(2, stockHistory.getTipe());
            ps.setInt(3, stockHistory.getJumlah());
            ps.setString(4, stockHistory.getKeterangan());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal menambahkan riwayat stok.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * ===========================
     * AMBIL SEMUA RIWAYAT STOK
     * ===========================
     */

    public List<StockHistory> getAllStockHistory() {
        List<StockHistory> list = new ArrayList<>();

        String sql =
                "SELECT * FROM stock_history "
                + "ORDER BY tanggal DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                StockHistory stockHistory = mapResultSetToStockHistory(rs);
                list.add(stockHistory);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil semua riwayat stok.");
            e.printStackTrace();
        }

        return list;
    }

    /*
     * Method tambahan untuk kompatibilitas dengan kode laporan Daffa.
     */

    public ArrayList<StockHistory> getAllHistory() {
        return new ArrayList<>(getAllStockHistory());
    }

    /*
     * ===========================
     * AMBIL RIWAYAT STOK BERDASARKAN PRODUK
     * ===========================
     */

    public List<StockHistory> getHistoryByProduct(int idProduct) {
        List<StockHistory> list = new ArrayList<>();

        String sql =
                "SELECT * FROM stock_history "
                + "WHERE id_product = ? "
                + "ORDER BY tanggal DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idProduct);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockHistory stockHistory = mapResultSetToStockHistory(rs);
                    list.add(stockHistory);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil riwayat stok berdasarkan produk.");
            e.printStackTrace();
        }

        return list;
    }

    /*
     * ===========================
     * AMBIL RIWAYAT STOK BERDASARKAN TIPE
     * ===========================
     */

    public List<StockHistory> getHistoryByTipe(String tipe) {
        List<StockHistory> list = new ArrayList<>();

        String sql =
                "SELECT * FROM stock_history "
                + "WHERE tipe = ? "
                + "ORDER BY tanggal DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, tipe);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StockHistory stockHistory = mapResultSetToStockHistory(rs);
                    list.add(stockHistory);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil riwayat stok berdasarkan tipe.");
            e.printStackTrace();
        }

        return list;
    }

    /*
     * ===========================
     * RIWAYAT STOK MASUK
     * ===========================
     */

    public boolean insertStokMasuk(int idProduct, int jumlah, String keterangan) {
        StockHistory stockHistory = new StockHistory();

        stockHistory.setIdProduct(idProduct);
        stockHistory.setTipe("masuk");
        stockHistory.setJumlah(jumlah);
        stockHistory.setKeterangan(keterangan);

        return insertStockHistory(stockHistory);
    }

    /*
     * ===========================
     * RIWAYAT STOK KELUAR
     * ===========================
     */

    public boolean insertStokKeluar(int idProduct, int jumlah, String keterangan) {
        StockHistory stockHistory = new StockHistory();

        stockHistory.setIdProduct(idProduct);
        stockHistory.setTipe("keluar");
        stockHistory.setJumlah(jumlah);
        stockHistory.setKeterangan(keterangan);

        return insertStockHistory(stockHistory);
    }

    /*
     * ===========================
     * RIWAYAT STOK PENYESUAIAN
     * ===========================
     */

    public boolean insertStokPenyesuaian(int idProduct, int jumlah, String keterangan) {
        StockHistory stockHistory = new StockHistory();

        stockHistory.setIdProduct(idProduct);
        stockHistory.setTipe("penyesuaian");
        stockHistory.setJumlah(jumlah);
        stockHistory.setKeterangan(keterangan);

        return insertStockHistory(stockHistory);
    }

    /*
     * ===========================
     * HITUNG TOTAL STOK MASUK PRODUK
     * ===========================
     */

    public int getTotalStokMasukByProduct(int idProduct) {
        String sql =
                "SELECT COALESCE(SUM(jumlah), 0) AS total "
                + "FROM stock_history "
                + "WHERE id_product = ? AND tipe = 'masuk'";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idProduct);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal menghitung stok masuk.");
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * ===========================
     * HITUNG TOTAL STOK KELUAR PRODUK
     * ===========================
     */

    public int getTotalStokKeluarByProduct(int idProduct) {
        String sql =
                "SELECT COALESCE(SUM(jumlah), 0) AS total "
                + "FROM stock_history "
                + "WHERE id_product = ? AND tipe = 'keluar'";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idProduct);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal menghitung stok keluar.");
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * ===========================
     * MAPPING RESULTSET KE STOCK HISTORY
     * ===========================
     */

    private StockHistory mapResultSetToStockHistory(ResultSet rs) throws Exception {
        StockHistory stockHistory = new StockHistory();

        stockHistory.setIdStockHistory(rs.getInt("id_stock_history"));
        stockHistory.setIdProduct(rs.getInt("id_product"));
        stockHistory.setTipe(rs.getString("tipe"));
        stockHistory.setJumlah(rs.getInt("jumlah"));
        stockHistory.setKeterangan(rs.getString("keterangan"));
        stockHistory.setTanggal(rs.getString("tanggal"));

        return stockHistory;
    }
}