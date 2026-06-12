package main.service;

import java.util.ArrayList;
import main.model.Transaksi;

public class TransaksiService {
    private ArrayList<Transaksi> daftarTransaksi;

    public TransaksiService() {
        daftarTransaksi = new ArrayList<>();
    }

    public void simpanTransaksi(Transaksi transaksi) {
        daftarTransaksi.add(transaksi);
    }

    public ArrayList<Transaksi> getDaftarTransaksi() {
        return daftarTransaksi;
    }
}