package main.dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import main.model.Barang;
import main.model.Transaksi;

public class ReportDAO {

    // Mengambil seluruh produk
    public ArrayList<Barang> getDataProduk() {

        ArrayList<Barang> daftarBarang = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM products";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Barang barang = new Barang(
                        rs.getString("kode_product"),
                        rs.getString("nama_product"),
                        rs.getInt("stok"),
                        rs.getDouble("harga_jual")
                );

                daftarBarang.add(barang);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return daftarBarang;
    }

    // Mengambil produk dengan stok minimum
    public ArrayList<Barang> getStokMinimum() {

        ArrayList<Barang> daftarBarang = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql =
                    "SELECT * FROM products WHERE stok <= stok_minimum";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Barang barang = new Barang(
                        rs.getString("kode_product"),
                        rs.getString("nama_product"),
                        rs.getInt("stok"),
                        rs.getDouble("harga_jual")
                );

                daftarBarang.add(barang);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return daftarBarang;
    }

    // Mengambil riwayat stok
    public ArrayList<String> getRiwayatStok() {

        ArrayList<String> riwayat = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = """
                SELECT p.nama_product,
                       s.tipe,
                       s.jumlah,
                       s.keterangan
                FROM stock_history s
                JOIN products p
                ON s.id_product = p.id_product
                """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String data =
                        rs.getString("nama_product")
                        + " | "
                        + rs.getString("tipe")
                        + " | "
                        + rs.getInt("jumlah")
                        + " | "
                        + rs.getString("keterangan");

                riwayat.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return riwayat;
    }

    // Sementara dikosongkan karena model Transaksi
    // belum sesuai dengan struktur tabel transactions
    public ArrayList<Transaksi> getDataTransaksi() {

        return new ArrayList<>();
    }
}