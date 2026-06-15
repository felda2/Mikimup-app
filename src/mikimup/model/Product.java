package mikimup.model;

public class Product {
    private int idProduct;
    private String kodeProduct;
    private String namaProduct;
    private String kategori;
    private double hargaBeli;
    private double hargaJual;
    private int stok;
    private int stokMinimum;
    private String createdAt;
    private String updatedAt;

    // Constructor kosong
    public Product() {}

    // Constructor lengkap (tanpa id & timestamp, untuk INSERT)
    public Product(String kodeProduct, String namaProduct, String kategori,
                   double hargaBeli, double hargaJual, int stok, int stokMinimum) {
        this.kodeProduct  = kodeProduct;
        this.namaProduct  = namaProduct;
        this.kategori     = kategori;
        this.hargaBeli    = hargaBeli;
        this.hargaJual    = hargaJual;
        this.stok         = stok;
        this.stokMinimum  = stokMinimum;
    }

    // Constructor lengkap dengan id (untuk SELECT)
    public Product(int idProduct, String kodeProduct, String namaProduct, String kategori,
                   double hargaBeli, double hargaJual, int stok, int stokMinimum,
                   String createdAt, String updatedAt) {
        this.idProduct    = idProduct;
        this.kodeProduct  = kodeProduct;
        this.namaProduct  = namaProduct;
        this.kategori     = kategori;
        this.hargaBeli    = hargaBeli;
        this.hargaJual    = hargaJual;
        this.stok         = stok;
        this.stokMinimum  = stokMinimum;
        this.createdAt    = createdAt;
        this.updatedAt    = updatedAt;
    }

    // ───────────── Getters & Setters ─────────────
    public int getIdProduct()              { return idProduct; }
    public void setIdProduct(int id)       { this.idProduct = id; }

    public String getKodeProduct()                  { return kodeProduct; }
    public void setKodeProduct(String kodeProduct)  { this.kodeProduct = kodeProduct; }

    public String getNamaProduct()                  { return namaProduct; }
    public void setNamaProduct(String namaProduct)  { this.namaProduct = namaProduct; }

    public String getKategori()                     { return kategori; }
    public void setKategori(String kategori)        { this.kategori = kategori; }

    public double getHargaBeli()                    { return hargaBeli; }
    public void setHargaBeli(double hargaBeli)      { this.hargaBeli = hargaBeli; }

    public double getHargaJual()                    { return hargaJual; }
    public void setHargaJual(double hargaJual)      { this.hargaJual = hargaJual; }

    public int getStok()                            { return stok; }
    public void setStok(int stok)                   { this.stok = stok; }

    public int getStokMinimum()                     { return stokMinimum; }
    public void setStokMinimum(int stokMinimum)     { this.stokMinimum = stokMinimum; }

    public String getCreatedAt()                    { return createdAt; }
    public void setCreatedAt(String createdAt)      { this.createdAt = createdAt; }

    public String getUpdatedAt()                    { return updatedAt; }
    public void setUpdatedAt(String updatedAt)      { this.updatedAt = updatedAt; }

    // Cek apakah stok di bawah minimum (dipakai untuk peringatan)
    public boolean isStokRendah() {
        return this.stok <= this.stokMinimum;
    }

    @Override
    public String toString() {
        return "[" + kodeProduct + "] " + namaProduct + " | Stok: " + stok;
    }
}