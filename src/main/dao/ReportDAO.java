package main.dao;

import database.DatabaseConnection;
import main.model.Barang;
import main.model.Transaksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReportDAO {

    /*
     * ===========================
     * DATA PRODUK
     * ===========================
     */

    public ArrayList<Barang> getDataProduk() {
        ArrayList<Barang> daftarBarang = new ArrayList<>();

        String sql = "SELECT * FROM products ORDER BY nama_product ASC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Barang barang = new Barang();

                barang.setIdProduct(rs.getInt("id_product"));
                barang.setKodeProduct(rs.getString("kode_product"));
                barang.setNamaProduct(rs.getString("nama_product"));
                barang.setKategori(rs.getString("kategori"));
                barang.setHargaBeli(rs.getDouble("harga_beli"));
                barang.setHargaJual(rs.getDouble("harga_jual"));
                barang.setStok(rs.getInt("stok"));
                barang.setStokMinimum(rs.getInt("stok_minimum"));

                daftarBarang.add(barang);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil data produk.");
            e.printStackTrace();
        }

        return daftarBarang;
    }

    /*
     * ===========================
     * PRODUK STOK MINIMUM
     * ===========================
     */

    public ArrayList<Barang> getStokMinimum() {
        ArrayList<Barang> daftarBarang = new ArrayList<>();

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
                Barang barang = new Barang();

                barang.setIdProduct(rs.getInt("id_product"));
                barang.setKodeProduct(rs.getString("kode_product"));
                barang.setNamaProduct(rs.getString("nama_product"));
                barang.setKategori(rs.getString("kategori"));
                barang.setHargaBeli(rs.getDouble("harga_beli"));
                barang.setHargaJual(rs.getDouble("harga_jual"));
                barang.setStok(rs.getInt("stok"));
                barang.setStokMinimum(rs.getInt("stok_minimum"));

                daftarBarang.add(barang);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil data stok minimum.");
            e.printStackTrace();
        }

        return daftarBarang;
    }

    /*
     * ===========================
     * RIWAYAT STOK
     * ===========================
     */

    public ArrayList<String> getRiwayatStok() {
        ArrayList<String> riwayat = new ArrayList<>();

        String sql =
                "SELECT p.nama_product, s.tipe, s.jumlah, s.keterangan, s.tanggal "
                + "FROM stock_history s "
                + "JOIN products p ON s.id_product = p.id_product "
                + "ORDER BY s.tanggal DESC";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                String data =
                        rs.getString("nama_product")
                        + " | "
                        + rs.getString("tipe")
                        + " | "
                        + rs.getInt("jumlah")
                        + " | "
                        + rs.getString("keterangan")
                        + " | "
                        + rs.getTimestamp("tanggal");

                riwayat.add(data);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil riwayat stok.");
            e.printStackTrace();
        }

        return riwayat;
    }

    /*
     * ===========================
     * DATA TRANSAKSI
     * ===========================
     */

    public ArrayList<Transaksi> getDataTransaksi() {
        ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();

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

                daftarTransaksi.add(transaksi);
            }

        } catch (Exception e) {
            System.out.println("Gagal mengambil data transaksi.");
            e.printStackTrace();
        }

        return daftarTransaksi;
    }

    /*
     * ===========================
     * TOTAL PENDAPATAN
     * ===========================
     */

    public double getTotalPendapatan() {
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

    /*
     * ===========================
     * JUMLAH TRANSAKSI
     * ===========================
     */

    public int getJumlahTransaksi() {
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
     * JUMLAH PRODUK
     * ===========================
     */

    public int getJumlahProduk() {
        String sql = "SELECT COUNT(*) AS jumlah FROM products";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt("jumlah");
            }

        } catch (Exception e) {
            System.out.println("Gagal menghitung jumlah produk.");
            e.printStackTrace();
        }

        return 0;
    }

    /*
     * ===========================
     * JUMLAH PRODUK STOK MINIMUM
     * ===========================
     */

    public int getJumlahStokMinimum() {
        String sql =
                "SELECT COUNT(*) AS jumlah "
                + "FROM products "
                + "WHERE stok <= stok_minimum";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt("jumlah");
            }

        } catch (Exception e) {
            System.out.println("Gagal menghitung jumlah stok minimum.");
            e.printStackTrace();
        }

        return 0;
    }
}