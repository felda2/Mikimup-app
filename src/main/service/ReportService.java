package main.service;

import java.util.ArrayList;
import main.model.Barang;
import main.model.Transaksi;

public class ReportService {

    // Menghitung total seluruh pendapatan
    public double hitungTotalPendapatan(ArrayList<Transaksi> daftarTransaksi) {

        double total = 0;

        for (Transaksi transaksi : daftarTransaksi) {
            total += transaksi.hitungTotal();
        }

        return total;
    }

    // Menghitung jumlah transaksi
    public int hitungJumlahTransaksi(ArrayList<Transaksi> daftarTransaksi) {
        return daftarTransaksi.size();
    }

    // Menghitung jumlah produk
    public int hitungJumlahProduk(ArrayList<Barang> daftarBarang) {
        return daftarBarang.size();
    }

    // Menghitung total stok seluruh produk
    public int hitungTotalStok(ArrayList<Barang> daftarBarang) {

        int totalStok = 0;

        for (Barang barang : daftarBarang) {
            totalStok += barang.getStok();
        }

        return totalStok;
    }

    // Mengambil daftar produk dengan stok minimum
    public ArrayList<Barang> getStokMinimum(ArrayList<Barang> daftarBarang, int batasMinimum) {

        ArrayList<Barang> stokMinimum = new ArrayList<>();

        for (Barang barang : daftarBarang) {
            if (barang.getStok() <= batasMinimum) {
                stokMinimum.add(barang);
            }
        }

        return stokMinimum;
    }
}