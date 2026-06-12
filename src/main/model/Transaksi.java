package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Transaksi {
    private String idTransaksi;
    private LocalDateTime tanggal;
    private ArrayList<DetailTransaksi> daftarDetail;

    public Transaksi(String idTransaksi) {
        this.idTransaksi = idTransaksi;
        this.tanggal = LocalDateTime.now();
        this.daftarDetail = new ArrayList<>();
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public ArrayList<DetailTransaksi> getDaftarDetail() {
        return daftarDetail;
    }

    public void tambahDetail(DetailTransaksi detailTransaksi) {
        daftarDetail.add(detailTransaksi);
    }

    public double hitungTotal() {
        double total = 0;

        for (DetailTransaksi detail : daftarDetail) {
            total += detail.hitungSubtotal();
        }

        return total;
    }
}