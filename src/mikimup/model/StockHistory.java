package mikimup.model;

public class StockHistory {
    private int idStockHistory;
    private int idProduct;
    private String namaProduct;   // join dari tabel products (untuk tampilan)
    private String tipe;          // 'masuk', 'keluar', 'penyesuaian'
    private int jumlah;
    private String keterangan;
    private String tanggal;

    // Constructor kosong
    public StockHistory() {}

    // Constructor untuk INSERT (tanpa id & tanggal)
    public StockHistory(int idProduct, String tipe, int jumlah, String keterangan) {
        this.idProduct  = idProduct;
        this.tipe       = tipe;
        this.jumlah     = jumlah;
        this.keterangan = keterangan;
    }

    // Constructor lengkap (untuk SELECT)
    public StockHistory(int idStockHistory, int idProduct, String namaProduct,
                        String tipe, int jumlah, String keterangan, String tanggal) {
        this.idStockHistory = idStockHistory;
        this.idProduct      = idProduct;
        this.namaProduct    = namaProduct;
        this.tipe           = tipe;
        this.jumlah         = jumlah;
        this.keterangan     = keterangan;
        this.tanggal        = tanggal;
    }

    // ───────────── Getters & Setters ─────────────
    public int getIdStockHistory()                      { return idStockHistory; }
    public void setIdStockHistory(int id)               { this.idStockHistory = id; }

    public int getIdProduct()                           { return idProduct; }
    public void setIdProduct(int idProduct)             { this.idProduct = idProduct; }

    public String getNamaProduct()                      { return namaProduct; }
    public void setNamaProduct(String namaProduct)      { this.namaProduct = namaProduct; }

    public String getTipe()                             { return tipe; }
    public void setTipe(String tipe)                   { this.tipe = tipe; }

    public int getJumlah()                              { return jumlah; }
    public void setJumlah(int jumlah)                  { this.jumlah = jumlah; }

    public String getKeterangan()                       { return keterangan; }
    public void setKeterangan(String keterangan)        { this.keterangan = keterangan; }

    public String getTanggal()                          { return tanggal; }
    public void setTanggal(String tanggal)              { this.tanggal = tanggal; }

    @Override
    public String toString() {
        return "[" + tanggal + "] " + tipe.toUpperCase() + " " + jumlah + " | " + keterangan;
    }
}