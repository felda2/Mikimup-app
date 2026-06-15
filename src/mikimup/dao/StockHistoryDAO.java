package mikimup.dao;

import mikimup.config.DatabaseConnection;
import mikimup.model.StockHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockHistoryDAO {

    // ─────────────────────────────────────────────
    // 1. Simpan histori stok baru
    // ─────────────────────────────────────────────
    public boolean addStockHistory(StockHistory sh) {
        String sql = "INSERT INTO stock_history (id_product, tipe, jumlah, keterangan) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sh.getIdProduct());
            ps.setString(2, sh.getTipe());
            ps.setInt(3, sh.getJumlah());
            ps.setString(4, sh.getKeterangan());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error addStockHistory: " + e.getMessage());
            return false;
        }
    }

    // ─────────────────────────────────────────────
    // 2. Ambil semua histori stok (join nama produk)
    // ─────────────────────────────────────────────
    public List<StockHistory> getAllStockHistory() {
        List<StockHistory> list = new ArrayList<>();
        String sql = "SELECT sh.*, p.nama_product " +
                     "FROM stock_history sh " +
                     "JOIN products p ON sh.id_product = p.id_product " +
                     "ORDER BY sh.tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getAllStockHistory: " + e.getMessage());
        }
        return list;
    }

    // ─────────────────────────────────────────────
    // 3. Ambil histori stok berdasarkan id produk
    // ─────────────────────────────────────────────
    public List<StockHistory> getStockHistoryByProduct(int idProduct) {
        List<StockHistory> list = new ArrayList<>();
        String sql = "SELECT sh.*, p.nama_product " +
                     "FROM stock_history sh " +
                     "JOIN products p ON sh.id_product = p.id_product " +
                     "WHERE sh.id_product = ? " +
                     "ORDER BY sh.tanggal DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProduct);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getStockHistoryByProduct: " + e.getMessage());
        }
        return list;
    }

    // ─────────────────────────────────────────────
    // Helper: mapping ResultSet → StockHistory
    // ─────────────────────────────────────────────
    private StockHistory mapRow(ResultSet rs) throws SQLException {
        return new StockHistory(
            rs.getInt("id_stock_history"),
            rs.getInt("id_product"),
            rs.getString("nama_product"),
            rs.getString("tipe"),
            rs.getInt("jumlah"),
            rs.getString("keterangan"),
            rs.getString("tanggal")
        );
    }
}