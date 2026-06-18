package main.dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import main.model.StockHistory;

public class StockHistoryDAO {

    public ArrayList<StockHistory> getAllHistory() {

        ArrayList<StockHistory> historyList = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "SELECT * FROM stock_history";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                StockHistory history = new StockHistory(
                        rs.getInt("id_stock_history"),
                        rs.getInt("id_product"),
                        rs.getString("tipe"),
                        rs.getInt("jumlah"),
                        rs.getString("keterangan")
                );

                historyList.add(history);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return historyList;
    }
}