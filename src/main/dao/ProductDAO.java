package main.dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import main.model.Barang;

public class ProductDAO {

    public ArrayList<Barang> getAllProducts() {

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
}