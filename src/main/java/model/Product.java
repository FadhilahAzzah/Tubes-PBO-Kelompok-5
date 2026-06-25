package model;

/**
 * Kelas model untuk merepresentasikan Produk dalam sistem.
 * Dibuat sederhana agar mudah dipahami untuk tugas besar PBO.
 */
public class Product {
    private String id;
    private String nama;
    private String kategori;
    private double harga;

    // Constructor
    public Product(String id, String nama, String kategori, double harga) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.harga = harga;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    // Alias untuk getName agar kompatibel dengan kode bahasa Inggris
    public String getName() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setName(String name) {
        this.nama = name;
    }

    public String getKategori() {
        return kategori;
    }

    // Alias untuk getCategory agar kompatibel dengan kode bahasa Inggris
    public String getCategory() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setCategory(String category) {
        this.kategori = category;
    }

    public double getHarga() {
        return harga;
    }

    // Alias untuk getPrice agar kompatibel dengan kode bahasa Inggris
    public double getPrice() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setPrice(double price) {
        this.harga = price;
    }

    // Method toString untuk menampilkan nama produk di JComboBox
    @Override
    public String toString() {
        return nama + " (Rp " + harga + ")";
    }
}
