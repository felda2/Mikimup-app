package main.model;

public class User {
    private int id;
    private String username, password, namaLengkap, role;

    // Constructor kosong
    public User() {}

    // Constructor dengan parameter
    public User(String username, String password, String namaLengkap, String role) {
        this.username = username;
        this.password = password;
        this.namaLengkap = namaLengkap;
        this.role = role;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}