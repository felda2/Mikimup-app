package main.dao;

import java.util.ArrayList;
import main.model.Barang;
import main.model.Transaksi;

public class ReportDAO {

    public ArrayList<Barang> getDataProduk() {
        return new ArrayList<>();
    }

    public ArrayList<Barang> getStokMinimum() {
        return new ArrayList<>();
    }

    public ArrayList<String> getRiwayatStok() {
        return new ArrayList<>();
    }

    public ArrayList<Transaksi> getDataTransaksi() {
        return new ArrayList<>();
    }
}