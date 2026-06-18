package main.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DatabaseConnection;
import main.model.DetailTransaksi;
import main.model.Transaksi;

public class TransaksiService {

    /*
     * ===========================
     * SIMPAN TRANSAKSI
     * ===========================
     */

    public boolean simpanTransaksi(Transaksi transaksi) {
        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();

            if (conn == null) {
                System.out.println("Koneksi database null.");
                return false;
            }

            conn.setAutoCommit(false);

            /*
             * INSERT KE TABEL transactions
             */

            String sqlTransaksi =
                    "INSERT INTO transactions "
                    + "(kode_transaction, id_user, total_harga, bayar, kembalian, metode_pembayaran) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            int idTransactionBaru = -1;

            try (
                PreparedStatement psTransaksi = conn.prepareStatement(
                        sqlTransaksi,
                        Statement.RETURN_GENERATED_KEYS
                )
            ) {
                psTransaksi.setString(1, transaksi.getKodeTransaction());
                psTransaksi.setInt(2, transaksi.getIdUser());
                psTransaksi.setDouble(3, transaksi.getTotalHarga());
                psTransaksi.setDouble(4, transaksi.getBayar());
                psTransaksi.setDouble(5, transaksi.getKembalian());
                psTransaksi.setString(6, transaksi.getMetodePembayaran());

                int rowsInserted = psTransaksi.executeUpdate();

                if (rowsInserted == 0) {
                    conn.rollback();
                    return false;
                }

                try (ResultSet generatedKeys = psTransaksi.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idTransactionBaru = generatedKeys.getInt(1);
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            }

            /*
             * QUERY UNTUK INSERT DETAIL TRANSAKSI
             */

            String sqlDetail =
                    "INSERT INTO transaction_details "
                    + "(id_transaction, id_product, jumlah, harga_satuan, subtotal) "
                    + "VALUES (?, ?, ?, ?, ?)";

            /*
             * QUERY UNTUK UPDATE STOK PRODUK
             * stok >= ? digunakan supaya stok tidak menjadi minus.
             */

            String sqlUpdateStok =
                    "UPDATE products "
                    + "SET stok = stok - ? "
                    + "WHERE id_product = ? AND stok >= ?";

            /*
             * QUERY UNTUK INSERT RIWAYAT STOK
             */

            String sqlStockHistory =
                    "INSERT INTO stock_history "
                    + "(id_product, tipe, jumlah, keterangan) "
                    + "VALUES (?, ?, ?, ?)";

            try (
                PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                PreparedStatement psUpdateStok = conn.prepareStatement(sqlUpdateStok);
                PreparedStatement psStockHistory = conn.prepareStatement(sqlStockHistory)
            ) {
                for (DetailTransaksi detail : transaksi.getDaftarDetail()) {

                    /*
                     * INSERT DETAIL TRANSAKSI
                     */

                    psDetail.setInt(1, idTransactionBaru);
                    psDetail.setInt(2, detail.getIdProduct());
                    psDetail.setInt(3, detail.getJumlah());
                    psDetail.setDouble(4, detail.getHargaSatuan());
                    psDetail.setDouble(5, detail.getSubtotal());

                    int detailInserted = psDetail.executeUpdate();

                    if (detailInserted == 0) {
                        conn.rollback();
                        return false;
                    }

                    /*
                     * UPDATE STOK PRODUK
                     */

                    psUpdateStok.setInt(1, detail.getJumlah());
                    psUpdateStok.setInt(2, detail.getIdProduct());
                    psUpdateStok.setInt(3, detail.getJumlah());

                    int stokUpdated = psUpdateStok.executeUpdate();

                    if (stokUpdated == 0) {
                        conn.rollback();
                        System.out.println("Stok tidak mencukupi untuk produk ID: " + detail.getIdProduct());
                        return false;
                    }

                    /*
                     * INSERT STOCK HISTORY
                     */

                    psStockHistory.setInt(1, detail.getIdProduct());
                    psStockHistory.setString(2, "keluar");
                    psStockHistory.setInt(3, detail.getJumlah());
                    psStockHistory.setString(
                            4,
                            "Terjual pada transaksi " + transaksi.getKodeTransaction()
                    );

                    int historyInserted = psStockHistory.executeUpdate();

                    if (historyInserted == 0) {
                        conn.rollback();
                        return false;
                    }
                }
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackError) {
                rollbackError.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException closeError) {
                closeError.printStackTrace();
            }
        }
    }

    /*
     * Method ini dibuat supaya kalau TransaksiFrame lama masih memanggil
     * simpanTransaksi(transaksi, bayar), kodenya tetap tidak error.
     */

    public boolean simpanTransaksi(Transaksi transaksi, double jumlahBayar) {
        double total = transaksi.hitungTotal();
        double kembalian = jumlahBayar - total;

        transaksi.setTotalHarga(total);
        transaksi.setBayar(jumlahBayar);
        transaksi.setKembalian(kembalian);

        if (transaksi.getMetodePembayaran() == null || transaksi.getMetodePembayaran().isEmpty()) {
            transaksi.setMetodePembayaran("cash");
        }

        return simpanTransaksi(transaksi);
    }

    /*
     * ===========================
     * RIWAYAT TRANSAKSI
     * ===========================
     */

    public ArrayList<String> getRiwayatTransaksi() {
        ArrayList<String> daftar = new ArrayList<>();

        String sql =
                "SELECT kode_transaction, total_harga, metode_pembayaran, tanggal "
                + "FROM transactions "
                + "ORDER BY id_transaction DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                String data =
                        rs.getString("kode_transaction")
                        + " | Rp "
                        + rs.getDouble("total_harga")
                        + " | "
                        + rs.getString("metode_pembayaran")
                        + " | "
                        + rs.getTimestamp("tanggal");

                daftar.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return daftar;
    }

    /*
     * ===========================
     * JUMLAH TRANSAKSI
     * ===========================
     */

    public int jumlahTransaksi() {
        String sql = "SELECT COUNT(*) AS jumlah FROM transactions";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt("jumlah");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * ===========================
     * TOTAL PENDAPATAN
     * ===========================
     */

    public double hitungPendapatan() {
        String sql = "SELECT COALESCE(SUM(total_harga), 0) AS total FROM transactions";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}