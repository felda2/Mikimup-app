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

    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double hitungSubtotal() {
        return barang.getHarga() * jumlah;
    }

    @Override
    public String toString() {
        return barang.getNamaBarang()
                + " x "
                + jumlah
                + " = Rp "
                + hitungSubtotal();
    }

}