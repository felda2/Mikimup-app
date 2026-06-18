package main.model;

public class Barang {

    private int idProduct;
    private String kodeProduct;
    private String namaProduct;
    private String kategori;
    private double hargaBeli;
    private double hargaJual;
    private int stok;
    private int stokMinimum;

    public Barang() {
    }

    public Barang(String kodeProduct, String namaProduct, int stok, double hargaJual) {
        this.kodeProduct = kodeProduct;
        this.namaProduct = namaProduct;
        this.stok = stok;
        this.hargaJual = hargaJual;
    }

    public Barang(
            int idProduct,
            String kodeProduct,
            String namaProduct,
            String kategori,
            double hargaBeli,
            double hargaJual,
            int stok,
            int stokMinimum) {

        this.idProduct = idProduct;
        this.kodeProduct = kodeProduct;
        this.namaProduct = namaProduct;
        this.kategori = kategori;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.stok = stok;
        this.stokMinimum = stokMinimum;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getKodeProduct() {
        return kodeProduct;
    }

    public void setKodeProduct(String kodeProduct) {
        this.kodeProduct = kodeProduct;
    }

    public String getNamaProduct() {
        return namaProduct;
    }

    public void setNamaProduct(String namaProduct) {
        this.namaProduct = namaProduct;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getStokMinimum() {
        return stokMinimum;
    }

    public void setStokMinimum(int stokMinimum) {
        this.stokMinimum = stokMinimum;
    }

    /*
     * ===========================
     * METHOD CADANGAN
     * Supaya kode lama yang masih memakai nama Barang
     * tidak langsung error.
     * ===========================
     */

    public String getKodeBarang() {
        return kodeProduct;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeProduct = kodeBarang;
    }

    public String getNamaBarang() {
        return namaProduct;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaProduct = namaBarang;
    }

    public double getHarga() {
        return hargaJual;
    }

    public void setHarga(double harga) {
        this.hargaJual = harga;
    }

    @Override
    public String toString() {
        return kodeProduct
                + " - "
                + namaProduct
                + " | Stok: "
                + stok
                + " | Rp "
                + hargaJual;
    }
}