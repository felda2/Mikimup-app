package main.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public boolean simpanTransaksi(
            Transaksi transaksi,
            double jumlahBayar) {

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();

            conn.setAutoCommit(false);

            double total = transaksi.hitungTotal();
            double kembalian = jumlahBayar - total;

            /*
             * INSERT TRANSACTIONS
             */

            String sqlTransaksi =
                    "INSERT INTO transactions "
                    + "(id_transaksi,tanggal,total_bayar,jumlah_bayar,kembalian,status_pembayaran) "
                    + "VALUES (?,?,?,?,?,?)";

            PreparedStatement psTransaksi =
                    conn.prepareStatement(sqlTransaksi);

            psTransaksi.setString(
                    1,
                    transaksi.getIdTransaksi());

            psTransaksi.setTimestamp(
                    2,
                    java.sql.Timestamp.valueOf(
                            transaksi.getTanggal()));

            psTransaksi.setDouble(
                    3,
                    total);

            psTransaksi.setDouble(
                    4,
                    jumlahBayar);

            psTransaksi.setDouble(
                    5,
                    kembalian);

            psTransaksi.setString(
                    6,
                    "LUNAS");

            psTransaksi.executeUpdate();

            /*
             * INSERT DETAIL
             */

            String sqlDetail =
                    "INSERT INTO transaction_details "
                    + "(id_transaksi,kode_barang,jumlah,harga,subtotal) "
                    + "VALUES (?,?,?,?,?)";

            PreparedStatement psDetail =
                    conn.prepareStatement(sqlDetail);

            /*
             * UPDATE STOK
             */

            String sqlUpdate =
                    "UPDATE products "
                    + "SET stok = stok - ? "
                    + "WHERE kode_barang=?";

            PreparedStatement psUpdate =
                    conn.prepareStatement(sqlUpdate);

            /*
             * STOCK HISTORY
             */

            String sqlHistory =
                    "INSERT INTO stock_history "
                    + "(kode_barang,jumlah,tanggal,keterangan) "
                    + "VALUES (?,?,NOW(),?)";

            PreparedStatement psHistory =
                    conn.prepareStatement(sqlHistory);

            for (DetailTransaksi detail
                    : transaksi.getDaftarDetail()) {

                /*
                 * DETAIL
                 */

                psDetail.setString(
                        1,
                        transaksi.getIdTransaksi());

                psDetail.setString(
                        2,
                        detail.getBarang().getKodeBarang());

                psDetail.setInt(
                        3,
                        detail.getJumlah());

                psDetail.setDouble(
                        4,
                        detail.getBarang().getHarga());

                psDetail.setDouble(
                        5,
                        detail.hitungSubtotal());

                psDetail.executeUpdate();

                /*
                 * UPDATE STOK
                 */

                psUpdate.setInt(
                        1,
                        detail.getJumlah());

                psUpdate.setString(
                        2,
                        detail.getBarang().getKodeBarang());

                psUpdate.executeUpdate();

                /*
                 * STOCK HISTORY
                 */

                psHistory.setString(
                        1,
                        detail.getBarang().getKodeBarang());

                psHistory.setInt(
                        2,
                        detail.getJumlah());

                psHistory.setString(
                        3,
                        "Transaksi "
                                + transaksi.getIdTransaksi());

                psHistory.executeUpdate();

            }

            conn.commit();

            return true;

        } catch (Exception e) {

            try {

                if (conn != null) {
                    conn.rollback();
                }

            } catch (SQLException ex) {

                ex.printStackTrace();

            }

            e.printStackTrace();

            return false;

        } finally {

            try {

                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

    }

    /*
     * ===========================
     * RIWAYAT TRANSAKSI
     * ===========================
     */

    public ArrayList<String> getRiwayatTransaksi() {

        ArrayList<String> daftar =
                new ArrayList<>();

        try {

            Connection conn =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT * "
                    + "FROM transactions "
                    + "ORDER BY tanggal DESC";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                daftar.add(

                        rs.getString("id_transaksi")

                        + " | Rp "

                        + rs.getDouble("total_bayar")

                        + " | "

                        + rs.getString("status_pembayaran")

                );

            }

            rs.close();
            ps.close();
            conn.close();

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

        try {

            Connection conn =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT COUNT(*) AS jumlah "
                    + "FROM transactions";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

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

        try {

            Connection conn =
                    DatabaseConnection.getConnection();

            String sql =
                    "SELECT SUM(total_bayar) AS total "
                    + "FROM transactions";

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getDouble("total");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return 0;

    }

}