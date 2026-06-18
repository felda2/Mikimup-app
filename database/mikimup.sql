DROP DATABASE IF EXISTS mikimup_db;
CREATE DATABASE mikimup_db;
USE mikimup_db;

CREATE TABLE users (
    id_user INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama_lengkap VARCHAR(100) NOT NULL,
    role ENUM('admin_kasir', 'owner') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id_product INT AUTO_INCREMENT PRIMARY KEY,
    kode_product VARCHAR(30) NOT NULL UNIQUE,
    nama_product VARCHAR(100) NOT NULL,
    kategori VARCHAR(50) NOT NULL,
    harga_beli DECIMAL(12,2) NOT NULL,
    harga_jual DECIMAL(12,2) NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    stok_minimum INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE stock_history (
    id_stock_history INT AUTO_INCREMENT PRIMARY KEY,
    id_product INT NOT NULL,
    tipe ENUM('masuk', 'keluar', 'penyesuaian') NOT NULL,
    jumlah INT NOT NULL,
    keterangan VARCHAR(255),
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_product) REFERENCES products(id_product)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE transactions (
    id_transaction INT AUTO_INCREMENT PRIMARY KEY,
    kode_transaction VARCHAR(30) NOT NULL UNIQUE,
    id_user INT NOT NULL,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_harga DECIMAL(12,2) NOT NULL,
    bayar DECIMAL(12,2) NOT NULL,
    kembalian DECIMAL(12,2) NOT NULL,
    metode_pembayaran ENUM('cash', 'qris', 'transfer') NOT NULL DEFAULT 'cash',
    FOREIGN KEY (id_user) REFERENCES users(id_user)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE transaction_details (
    id_detail INT AUTO_INCREMENT PRIMARY KEY,
    id_transaction INT NOT NULL,
    id_product INT NOT NULL,
    jumlah INT NOT NULL,
    harga_satuan DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (id_transaction) REFERENCES transactions(id_transaction)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (id_product) REFERENCES products(id_product)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

INSERT INTO users (username, password, nama_lengkap, role) VALUES
('admin', '12345', 'Admin Kasir', 'admin_kasir'),
('owner', '12345', 'Owner Mikimup', 'owner');

INSERT INTO products (
    kode_product,
    nama_product,
    kategori,
    harga_beli,
    harga_jual,
    stok,
    stok_minimum
) VALUES
('KB001', 'Kimbab Original', 'Kimbab', 8000, 12000, 20, 5),
('KB002', 'Kimbab Spicy Chicken', 'Kimbab', 10000, 15000, 20, 5),
('KB003', 'Kimbab Beef Bulgogi', 'Kimbab', 12000, 18000, 15, 5),
('KB004', 'Kimbab Tuna Mayo', 'Kimbab', 10000, 15000, 15, 5),
('KB005', 'Kimbab Cheese', 'Kimbab', 10000, 16000, 15, 5),
('KB006', 'Mini Kimbab Mix', 'Kimbab', 7000, 10000, 25, 5),
('MN001', 'Es Teh', 'Minuman', 2000, 5000, 30, 10),
('MN002', 'Air Mineral', 'Minuman', 2000, 4000, 30, 10);