package main.model;

public class DetailTransaksi {
    private Barang barang;
    private int jumlah;

    public DetailTransaksi(Barang barang, int jumlah) {
        this.barang = barang;
        this.jumlah = jumlah;
    }

    public Barang getBarang() {
        return barang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double hitungSubtotal() {
        return barang.getHarga() * jumlah;
    }
}