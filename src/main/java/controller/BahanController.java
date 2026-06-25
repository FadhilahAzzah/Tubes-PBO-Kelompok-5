/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import koneksi.Koneksi;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.BahanView;

/**
 *
 * @author Asus
 */
public class BahanController {
    BahanView view;

    public BahanController(BahanView view) {
        this.view = view;
    }
    
    public void simpan() {

        try {

            String sql =
            "INSERT INTO bahan_masakan VALUES(?,?,?,?)";

            PreparedStatement ps =
            Koneksi.getKoneksi().prepareStatement(sql);

            ps.setInt(1,
                    Integer.parseInt(
                    view.txtIdBahan.getText()));

            ps.setString(2,
                    view.txtNamaBarang.getText());

            ps.setDouble(3,
                    Double.parseDouble(
                    view.txtHargaBarang.getText()));

            ps.setInt(4,
                    Integer.parseInt(
                    view.txtStokBarang.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Disimpan");

            tampilData();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());

        }

    }

    public void tampilData() {

        DefaultTableModel model =
        new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nama Barang");
        model.addColumn("Harga");
        model.addColumn("Stok");

        try {

            Statement st =
            Koneksi.getKoneksi().createStatement();

            ResultSet rs =
            st.executeQuery(
            "SELECT * FROM bahan_masakan");

            while(rs.next()){

                model.addRow(new Object[]{

                    rs.getInt("id_bahan"),
                    rs.getString("nama_barang"),
                    rs.getDouble("harga_barang"),
                    rs.getInt("stok_barang")

                });

            }

            view.tblBahan.setModel(model);

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    public void ubah() {

        try {

            String sql =
            "UPDATE bahan_masakan SET nama_barang=?, harga_barang=?, stok_barang=? WHERE id_bahan=?";

            PreparedStatement ps =
            Koneksi.getKoneksi().prepareStatement(sql);

            ps.setString(1,
                    view.txtNamaBarang.getText());

            ps.setDouble(2,
                    Double.parseDouble(
                    view.txtHargaBarang.getText()));

            ps.setInt(3,
                    Integer.parseInt(
                    view.txtStokBarang.getText()));

            ps.setInt(4,
                    Integer.parseInt(
                    view.txtIdBahan.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Diubah");

            tampilData();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());

        }

    }

    public void hapus() {

        try {

            String sql =
            "DELETE FROM bahan_masakan WHERE id_bahan=?";

            PreparedStatement ps =
            Koneksi.getKoneksi().prepareStatement(sql);

            ps.setInt(1,
                    Integer.parseInt(
                    view.txtIdBahan.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Dihapus");

            tampilData();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());

        }

    }

    public void bersih() {

        view.txtIdBahan.setText("");
        view.txtNamaBarang.setText("");
        view.txtHargaBarang.setText("");
        view.txtStokBarang.setText("");

    }
}
