package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Transaksi {

    private String idTransaksi;
    private LocalDateTime tanggal;
    private ArrayList<DetailTransaksi> daftarDetail;

    private double totalBayar;
    private String statusPembayaran;

    public Transaksi(String idTransaksi) {

        this.idTransaksi = idTransaksi;
        this.tanggal = LocalDateTime.now();
        this.daftarDetail = new ArrayList<>();

        this.totalBayar = 0;
        this.statusPembayaran = "BELUM LUNAS";
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

    public double getTotalBayar() {
        return totalBayar;
    }

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public void tambahDetail(DetailTransaksi detailTransaksi) {
        daftarDetail.add(detailTransaksi);
    }

    public void hapusDetail(int index) {

        if (index >= 0 && index < daftarDetail.size()) {
            daftarDetail.remove(index);
        }
    }

    public double hitungTotal() {

        double total = 0;

        for (DetailTransaksi detail : daftarDetail) {
            total += detail.hitungSubtotal();
        }

        return total;
    }

    public boolean verifikasiBayar(double jumlahBayar) {
        return jumlahBayar >= hitungTotal();
    }

    public double hitungKembalian() {

        if (totalBayar >= hitungTotal()) {
            return totalBayar - hitungTotal();
        }

        return 0;
    }

    public void konfirmasiTransaksi() {
        statusPembayaran = "LUNAS";
    }

    public void batalkanTransaksi() {
        statusPembayaran = "BATAL";
    }

    @Override
    public String toString() {
        return idTransaksi
                + " | Rp "
                + hitungTotal()
                + " | "
                + statusPembayaran;
    }
}