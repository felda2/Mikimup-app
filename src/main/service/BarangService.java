package main.service;

import java.util.ArrayList;
import main.model.Barang;

public class BarangService {
    private ArrayList<Barang> daftarBarang;

    public BarangService() {
        daftarBarang = new ArrayList<>();
    }

    public void tambahBarang(Barang barang) {
        daftarBarang.add(barang);
    }

    public ArrayList<Barang> getDaftarBarang() {
        return daftarBarang;
    }

    public Barang cariBarang(String kodeBarang) {
        for (Barang barang : daftarBarang) {
            if (barang.getKodeBarang().equalsIgnoreCase(kodeBarang)) {
                return barang;
            }
        }

        return null;
    }
}