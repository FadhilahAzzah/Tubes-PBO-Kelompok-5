package model;

/**
 * Kelas model untuk merepresentasikan Detail dari sebuah Transaksi (item dan kuantitas).
 * Dibuat sederhana agar mudah dipahami untuk tugas besar PBO.
 */
public class DetailTransaksi {
    private Product product;
    private int jumlah;

    // Constructor
    public DetailTransaksi(Product product, int jumlah) {
        this.product = product;
        this.jumlah = jumlah;
    }

    // Getter dan Setter
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    // Menghitung subtotal untuk detail transaksi ini (harga * jumlah)
    public double getSubtotal() {
        if (product != null) {
            return product.getPrice() * jumlah;
        }
        return 0;
    }
}
