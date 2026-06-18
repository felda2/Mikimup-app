package main.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Transaksi {

    private int idTransaction;
    private String kodeTransaction;
    private int idUser;
    private LocalDateTime tanggal;

    private double totalHarga;
    private double bayar;
    private double kembalian;
    private String metodePembayaran;

    private ArrayList<DetailTransaksi> daftarDetail;

    public Transaksi() {
        this.tanggal = LocalDateTime.now();
        this.daftarDetail = new ArrayList<>();
        this.totalHarga = 0;
        this.bayar = 0;
        this.kembalian = 0;
        this.metodePembayaran = "cash";
    }

    public Transaksi(String kodeTransaction) {
        this.kodeTransaction = kodeTransaction;
        this.tanggal = LocalDateTime.now();
        this.daftarDetail = new ArrayList<>();
        this.totalHarga = 0;
        this.bayar = 0;
        this.kembalian = 0;
        this.metodePembayaran = "cash";
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getKodeTransaction() {
        return kodeTransaction;
    }

    public void setKodeTransaction(String kodeTransaction) {
        this.kodeTransaction = kodeTransaction;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDateTime tanggal) {
        this.tanggal = tanggal;
    }

    public void setTanggal(String tanggal) {
        if (tanggal != null) {
            this.tanggal = Timestamp.valueOf(tanggal).toLocalDateTime();
        }
    }

    public ArrayList<DetailTransaksi> getDaftarDetail() {
        return daftarDetail;
    }

    public void setDaftarDetail(ArrayList<DetailTransaksi> daftarDetail) {
        this.daftarDetail = daftarDetail;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }

    public double getBayar() {
        return bayar;
    }

    public void setBayar(double bayar) {
        this.bayar = bayar;
    }

    public double getKembalian() {
        return kembalian;
    }

    public void setKembalian(double kembalian) {
        this.kembalian = kembalian;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public void tambahDetail(DetailTransaksi detail) {
        daftarDetail.add(detail);
        this.totalHarga = hitungTotal();
    }

    public void hapusDetail(int index) {
        if (index >= 0 && index < daftarDetail.size()) {
            daftarDetail.remove(index);
            this.totalHarga = hitungTotal();
        }
    }

    public double hitungTotal() {
        double total = 0;

        for (DetailTransaksi detail : daftarDetail) {
            total += detail.getSubtotal();
        }

        return total;
    }

    public boolean verifikasiBayar(double jumlahBayar) {
        return jumlahBayar >= hitungTotal();
    }

    public double hitungKembalian() {
        if (bayar >= hitungTotal()) {
            return bayar - hitungTotal();
        }

        return 0;
    }

    /*
     * ===========================
     * METHOD CADANGAN
     * Supaya kode lama yang masih memanggil nama lama
     * tidak langsung error.
     * ===========================
     */

    public String getIdTransaksi() {
        return kodeTransaction;
    }

    public void setIdTransaksi(String idTransaksi) {
        this.kodeTransaction = idTransaksi;
    }

    public double getTotalBayar() {
        return bayar;
    }

    public void setTotalBayar(double totalBayar) {
        this.bayar = totalBayar;
    }

    public String getStatusPembayaran() {
        if (bayar >= hitungTotal()) {
            return "LUNAS";
        }

        return "BELUM LUNAS";
    }

    public void setStatusPembayaran(String statusPembayaran) {
        // Tidak dipakai lagi karena database baru tidak punya kolom status_pembayaran.
    }

    public void konfirmasiTransaksi() {
        this.totalHarga = hitungTotal();
        this.kembalian = hitungKembalian();
    }

    public void batalkanTransaksi() {
        this.daftarDetail.clear();
        this.totalHarga = 0;
        this.bayar = 0;
        this.kembalian = 0;
    }

    @Override
    public String toString() {
        return kodeTransaction
                + " | "
                + tanggal
                + " | Rp "
                + hitungTotal()
                + " | "
                + metodePembayaran;
    }
}