package main.model;

public class StockHistory {
    private int idStockHistory;
    private int idProduct;
    private String tipe;
    private int jumlah;
    private String keterangan;

    public StockHistory() {
    }

    public StockHistory(int idStockHistory, int idProduct, String tipe, int jumlah, String keterangan) {
        this.idStockHistory = idStockHistory;
        this.idProduct = idProduct;
        this.tipe = tipe;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
    }

    public int getIdStockHistory() {
        return idStockHistory;
    }

    public void setIdStockHistory(int idStockHistory) {
        this.idStockHistory = idStockHistory;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}