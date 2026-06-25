package controller;

import koneksi.Koneksi;
import model.Transaksi;
import model.Product;
import model.DetailTransaksi;
import view.TransaksiView;

import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Controller untuk menghubungkan TransaksiView (GUI) dengan Transaksi (Model).
 * Dibuat sederhana dan dilengkapi komentar agar mudah dipahami untuk tugas besar PBO.
 */
public class TransaksiController {
    private final TransaksiView view;
    private Transaksi transaksi;
    private final NumberFormat rupiahFormat;

    public TransaksiController(TransaksiView view) {
        this.view = view;
        // Format mata uang Rupiah
        this.rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        
        // Inisialisasi transaksi baru
        buatTransaksiBaru();
    }

    /**
     * Membuat objek Transaksi baru dengan ID unik dan tanggal saat ini.
     */
    public final void buatTransaksiBaru() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tanggalSekarang = sdf.format(new Date());
        
        // Membuat ID transaksi unik berbasis timestamp
        String idTransaksi = "TX" + (System.currentTimeMillis() / 1000);
        
        this.transaksi = new Transaksi(idTransaksi, tanggalSekarang);
        
        // Reset tampilan GUI
        bersihTampilan();
    }

    /**
     * Memuat daftar produk/masakan dari database.
     * Jika database kosong atau terjadi error, maka akan ditambahkan produk sampel agar program tetap bisa dicoba.
     */
    public void muatProduk() {
        view.cmbProduct.removeAllItems();
        boolean adaData = false;

        try {
            Connection conn = Koneksi.getKoneksi();
            if (conn != null) {
                String sql = "SELECT * FROM masakan WHERE status = 'Tersedia'";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    String id = String.valueOf(rs.getInt("id_masakan"));
                    String nama = rs.getString("nama_masakan");
                    double harga = rs.getDouble("harga");
                    
                    // Membuat objek Product dan memasukkannya ke combobox
                    Product p = new Product(id, nama, "Makanan", harga);
                    view.cmbProduct.addItem(p);
                    adaData = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat produk dari database, menggunakan data sampel: " + e.getMessage());
        }

        // Fallback: Jika database tidak terhubung atau kosong, buat data sampel agar program tetap dapat dijalankan dan diuji
        if (!adaData) {
            view.cmbProduct.addItem(new Product("1", "Nasi Goreng Spesial", "Makanan", 25000));
            view.cmbProduct.addItem(new Product("2", "Ayam Bakar Taliwang", "Makanan", 32000));
            view.cmbProduct.addItem(new Product("3", "Es Teh Manis Jumbo", "Minuman", 5000));
            view.cmbProduct.addItem(new Product("4", "Jus Alpukat", "Minuman", 12000));
        }
    }

    /**
     * Menambahkan produk terpilih ke dalam keranjang transaksi.
     */
    public void tambahKeKeranjang() {
        Product terpilih = (Product) view.cmbProduct.getSelectedItem();
        if (terpilih == null) {
            JOptionPane.showMessageDialog(view, "Pilih produk terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(view.txtQty.getText());
            if (qty <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Jumlah beli harus berupa angka positif!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tambah produk ke model Transaksi
        transaksi.tambahProduk(terpilih, qty);
        
        // Update tabel keranjang dan info pembayaran
        updateTabelKeranjang();
        updateRincianPembayaran();
    }

    /**
     * Mengupdate data pada JTable keranjang belanja di GUI.
     */
    private void updateTabelKeranjang() {
        DefaultTableModel model = (DefaultTableModel) view.tblCart.getModel();
        model.setRowCount(0); // Kosongkan tabel terlebih dahulu

        for (DetailTransaksi detail : transaksi.getDetails()) {
            model.addRow(new Object[] {
                detail.getProduct().getId(),
                detail.getProduct().getNama(),
                detail.getProduct().getKategori(),
                rupiahFormat.format(detail.getProduct().getPrice()),
                detail.getJumlah(),
                rupiahFormat.format(detail.getSubtotal())
            });
        }
    }

    /**
     * Mengupdate label-label rincian pembayaran di GUI.
     */
    private void updateRincianPembayaran() {
        view.lblSubtotal.setText(rupiahFormat.format(transaksi.getSubtotal()));
        view.lblTax.setText(rupiahFormat.format(transaksi.getPajak()));
        view.lblTotal.setText(rupiahFormat.format(transaksi.getTotal()));
        view.lblChange.setText(rupiahFormat.format(transaksi.getUangKembali()));
    }

    /**
     * Memproses pembayaran transaksi dan mencetak struk belanja.
     */
    public void prosesPembayaran() {
        if (transaksi.getDetails().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Keranjang belanja kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double cash;
        try {
            cash = Double.parseDouble(view.txtCash.getText());
            if (cash < transaksi.getTotal()) {
                JOptionPane.showMessageDialog(view, "Uang dibayar kurang dari total belanja!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Uang dibayar harus berupa angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Set uang dibayar pada model transaksi (akan otomatis menghitung kembalian)
        transaksi.setUangDibayar(cash);
        updateRincianPembayaran();

        // Simpan transaksi ke database (opsional)
        simpanTransaksiKeDatabase();

        // Cetak struk ke JTextArea
        cetakStruk();
        
        JOptionPane.showMessageDialog(view, "Pembayaran berhasil diproses!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Mencetak struk ke area teks struk di GUI.
     */
    private void cetakStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("==============================\n");
        sb.append("      RESTO BISMILLAHTUBES    \n");
        sb.append("==============================\n");
        sb.append("ID  : ").append(transaksi.getId()).append("\n");
        sb.append("Tgl : ").append(transaksi.getTanggal()).append("\n");
        sb.append("------------------------------\n");
        sb.append(String.format("%-12s %3s %6s %7s\n", "Menu", "Qty", "Harga", "Total"));
        sb.append("------------------------------\n");
        
        for (DetailTransaksi dt : transaksi.getDetails()) {
            String nama = dt.getProduct().getNama();
            if (nama.length() > 12) {
                nama = nama.substring(0, 9) + "...";
            }
            sb.append(String.format("%-12s %3d %6.0f %7.0f\n", 
                nama, 
                dt.getJumlah(), 
                dt.getProduct().getPrice(), 
                dt.getSubtotal()
            ));
        }
        
        sb.append("------------------------------\n");
        sb.append(String.format("Subtotal    : %s\n", rupiahFormat.format(transaksi.getSubtotal())));
        sb.append(String.format("PPN (10%%)   : %s\n", rupiahFormat.format(transaksi.getPajak())));
        sb.append(String.format("Total Akhir : %s\n", rupiahFormat.format(transaksi.getTotal())));
        sb.append(String.format("Uang Bayar  : %s\n", rupiahFormat.format(transaksi.getUangDibayar())));
        sb.append(String.format("Kembalian   : %s\n", rupiahFormat.format(transaksi.getUangKembali())));
        sb.append("==============================\n");
        sb.append("      Terima Kasih Atas       \n");
        sb.append("        Kunjungan Anda        \n");
        sb.append("==============================\n");
        
        view.txtReceipt.setText(sb.toString());
    }

    /**
     * Menyimpan data transaksi ke database (jika database tersedia).
     */
    private void simpanTransaksiKeDatabase() {
        try {
            Connection conn = Koneksi.getKoneksi();
            if (conn != null) {
                // Nonaktifkan autocommit untuk melakukan transaksi database yang aman
                conn.setAutoCommit(false);
                
                // 1. Simpan ke tabel master transaksi
                String sqlTransaksi = "INSERT INTO transaksi (id_transaksi, tanggal, total) VALUES (?, ?, ?)";
                PreparedStatement psTx = conn.prepareStatement(sqlTransaksi);
                psTx.setString(1, transaksi.getId());
                psTx.setString(2, transaksi.getTanggal());
                psTx.setDouble(3, transaksi.getTotal());
                psTx.executeUpdate();

                // 2. Simpan setiap item ke tabel detail_transaksi
                String sqlDetail = "INSERT INTO detail_transaksi (id_transaksi, id_masakan, jumlah, subtotal) VALUES (?, ?, ?, ?)";
                PreparedStatement psDt = conn.prepareStatement(sqlDetail);
                for (DetailTransaksi dt : transaksi.getDetails()) {
                    psDt.setString(1, transaksi.getId());
                    psDt.setInt(2, Integer.parseInt(dt.getProduct().getId()));
                    psDt.setInt(3, dt.getJumlah());
                    psDt.setDouble(4, dt.getSubtotal());
                    psDt.executeUpdate();
                }

                conn.commit(); // Commit transaksi
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            System.out.println("Gagal menyimpan transaksi ke database: " + e.getMessage());
            // Jika terjadi kesalahan database, program tidak akan crash, melainkan hanya log error saja
        }
    }

    /**
     * Membersihkan komponen input dan label pembayaran di GUI.
     */
    private void bersihTampilan() {
        if (view.tblCart != null) {
            DefaultTableModel model = (DefaultTableModel) view.tblCart.getModel();
            model.setRowCount(0);
        }
        if (view.txtQty != null) {
            view.txtQty.setText("1");
        }
        if (view.txtCash != null) {
            view.txtCash.setText("");
        }
        if (view.txtReceipt != null) {
            view.txtReceipt.setText("");
        }
        updateRincianPembayaran();
    }
}
