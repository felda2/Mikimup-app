package main.model;

public class DetailTransaksi {

    private int idDetail;
    private int idTransaction;
    private int idProduct;

    private Barang barang;
    private int jumlah;
    private double hargaSatuan;
    private double subtotal;

    public DetailTransaksi() {
    }

    public DetailTransaksi(Barang barang, int jumlah) {
        this.barang = barang;
        this.jumlah = jumlah;

        if (barang != null) {
            this.idProduct = barang.getIdProduct();
            this.hargaSatuan = barang.getHargaJual();
            this.subtotal = barang.getHargaJual() * jumlah;
        }
    }

    public DetailTransaksi(int idProduct, int jumlah, double hargaSatuan) {
        this.idProduct = idProduct;
        this.jumlah = jumlah;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = hargaSatuan * jumlah;
    }

    public int getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(int idDetail) {
        this.idDetail = idDetail;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;

        if (barang != null) {
            this.idProduct = barang.getIdProduct();
            this.hargaSatuan = barang.getHargaJual();
            this.subtotal = this.hargaSatuan * this.jumlah;
        }
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
        this.subtotal = this.hargaSatuan * jumlah;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
        this.subtotal = hargaSatuan * this.jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double hitungSubtotal() {
        this.subtotal = hargaSatuan * jumlah;
        return this.subtotal;
    }

    /*
     * ===========================
     * METHOD CADANGAN
     * Supaya kode lama yang masih pakai nama lama
     * tidak langsung error.
     * ===========================
     */

    public String getKodeBarang() {
        if (barang != null) {
            return barang.getKodeProduct();
        }

        return "";
    }

    public String getNamaBarang() {
        if (barang != null) {
            return barang.getNamaProduct();
        }

        return "";
    }

    public double getHarga() {
        return hargaSatuan;
    }

    @Override
    public String toString() {
        String namaProduct = "";

        if (barang != null) {
            namaProduct = barang.getNamaProduct();
        }

        return namaProduct
                + " x "
                + jumlah
                + " = Rp "
                + getSubtotal();
    }
}