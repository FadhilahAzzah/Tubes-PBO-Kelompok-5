/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas model untuk merepresentasikan Transaksi Pembelian.
 * Dibuat sederhana dan mudah dipahami dengan komentar penjelasan dalam bahasa Indonesia.
 * 
 * @author afza
 */
public class Transaksi {
    private String id;
    private String tanggal;
    private double subtotal;
    private double pajak;
    private double total;
    private double uangDibayar;
    private double uangKembali;
    private final List<DetailTransaksi> details; // Menggunakan nama variabel 'details' agar konsisten dengan getter dan method lainnya

    // Constructor untuk menginisialisasi ID Transaksi dan Tanggal
    public Transaksi(String id, String tanggal) {
        this.id = id;
        this.tanggal = tanggal;
        this.details = new ArrayList<>();
    }

    /**
     * Menambahkan produk ke dalam keranjang transaksi.
     * Jika produk sudah ada, maka kuantitasnya akan ditambahkan.
     */
    public void tambahProduk(Product product, int quantity) {
        // Cek apakah produk sudah ada di keranjang untuk digabung kuantitasnya
        for (DetailTransaksi dt : details) {
            if (dt.getProduct().getId().equals(product.getId())) {
                dt.setJumlah(dt.getJumlah() + quantity);
                hitungTotalAkhir();
                return;
            }
        }
        
        // Jika belum ada, buat detail transaksi baru (Komposisi) dan tambahkan ke list
        DetailTransaksi detail = new DetailTransaksi(product, quantity);
        details.add(detail);
        hitungTotalAkhir();
    }

    /**
     * Menghapus produk dari keranjang transaksi berdasarkan ID Produk.
     */
    public void hapusProduk(Product product) {
        details.removeIf(detailItem -> detailItem.getProduct().getId().equals(product.getId()));
        hitungTotalAkhir();
    }

    /**
     * Menghitung subtotal, pajak (PPN 10%), total akhir, dan uang kembalian.
     */
    public void hitungTotalAkhir() {
        subtotal = 0;
        // Hitung total dari seluruh detail transaksi di keranjang
        for (DetailTransaksi detailItem : details) {
            subtotal += detailItem.getSubtotal();
        }
        pajak = subtotal * 0.1; // PPN sebesar 10%
        total = subtotal + pajak;
        
        // Hitung uang kembali jika uang yang dibayar lebih besar dari total akhir
        if (uangDibayar >= total) {
            uangKembali = uangDibayar - total;
        } else {
            uangKembali = 0;
        }
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getPajak() {
        return pajak;
    }

    public double getTotal() {
        return total;
    }

    public double getUangDibayar() {
        return uangDibayar;
    }

    public void setUangDibayar(double uangDibayar) {
        this.uangDibayar = uangDibayar;
        hitungTotalAkhir(); // Setiap kali uang dibayar diubah, hitung ulang total akhir dan kembalian
    }

    public double getUangKembali() {
        return uangKembali;
    }

    public List<DetailTransaksi> getDetails() {
        return details;
    }
}

