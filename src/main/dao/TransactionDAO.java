package main.dao;

import database.DatabaseConnection;
import main.model.DetailTransaksi;
import main.model.Transaksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionDAO {

    /*
     * ===========================
     * INSERT TRANSAKSI UTAMA
     * ===========================
     */

    public int insertTransaction(Transaksi transaksi) {
        String sql =
                "INSERT INTO transactions "
                + "(kode_transaction, id_user, total_harga, bayar, kembalian, metode_pembayaran) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, transaksi.getKodeTransaction());
            ps.setInt(2, transaksi.getIdUser());
            ps.setDouble(3, transaksi.getTotalHarga());
            ps.setDouble(4, transaksi.getBayar());
            ps.setDouble(5, transaksi.getKembalian());
            ps.setString(6, transaksi.getMetodePembayaran());

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal menyimpan transaksi utama.");
            e.printStackTrace();
        }

        return -1;
    }

    /*
     * ===========================
     * INSERT DETAIL TRANSAKSI
     * ===========================
     */

    public boolean insertTransactionDetail(DetailTransaksi detail) {
        String sql =
                "INSERT INTO transaction_details "
                + "(id_transaction, id_product, jumlah, harga_satuan, subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, detail.getIdTransaction());
            ps.setInt(2, detail.getIdProduct());
            ps.setInt(3, detail.getJumlah());
            ps.setDouble(4, detail.getHargaSatuan());
            ps.setDouble(5, detail.getSubtotal());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Gagal menyimpan detail transaksi.");
            e.printStackTrace();
            return false;
        }
    }

    /*
     * ===========================
     * AMBIL SEMUA TRANSAKSI
     * ===========================
     */

    public ArrayList<Transaksi> getAllTransactions() {
        ArrayList<Transaksi> list = new ArrayList<>();

        String sql =
                "SELECT * FROM transactions "
                + "ORDER BY id_transaction DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Transaksi transaksi = new Transaksi();

                transaksi.setIdTransaction(rs.getInt("id_transaction"));
                transaksi.setKodeTransaction(rs.getString("kode_transaction"));
                transaksi.setIdUser(rs.getInt("id_user"));
                transaksi.setTanggal(rs.getString("tanggal"));
                transaksi.setTotalHarga(rs.getDouble("total_harga"));
                transaksi.setBayar(rs.getDouble("bayar"));
                transaksi.setKembalian(rs.getDouble("kembalian"));
                transaksi.setMetodePembayaran(rs.getString("metode_pembayaran"));

                list.add(transaksi);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil data transaksi.");
            e.printStackTrace();
        }

        return list;
    }

    /*
     * ===========================
     * AMBIL DETAIL TRANSAKSI
     * ===========================
     */

    public ArrayList<DetailTransaksi> getTransactionDetails(int idTransaction) {
        ArrayList<DetailTransaksi> list = new ArrayList<>();

        String sql =
                "SELECT * FROM transaction_details "
                + "WHERE id_transaction = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, idTransaction);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetailTransaksi detail = new DetailTransaksi();

                    detail.setIdDetail(rs.getInt("id_detail"));
                    detail.setIdTransaction(rs.getInt("id_transaction"));
                    detail.setIdProduct(rs.getInt("id_product"));
                    detail.setJumlah(rs.getInt("jumlah"));
                    detail.setHargaSatuan(rs.getDouble("harga_satuan"));
                    detail.setSubtotal(rs.getDouble("subtotal"));

                    list.add(detail);
                }
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil detail transaksi.");
            e.printStackTrace();
        }

        return list;
    }

    /*
     * ===========================
     * HITUNG JUMLAH TRANSAKSI
     * ===========================
     */

    public int countTransactions() {
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
            System.out.println("Gagal menghitung jumlah transaksi.");
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * ===========================
     * HITUNG TOTAL PENDAPATAN
     * ===========================
     */

    public double getTotalIncome() {
        String sql =
                "SELECT COALESCE(SUM(total_harga), 0) AS total "
                + "FROM transactions";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            System.out.println("Gagal menghitung total pendapatan.");
            e.printStackTrace();
        }

        return 0;
    }
}