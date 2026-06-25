/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import koneksi.Koneksi;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import view.MasakanView;

public class MasakanController {

    MasakanView form;

    public MasakanController(MasakanView form) {
        this.form = form;
    }

    public void simpan() {

        try {

            String sql =
            "INSERT INTO masakan VALUES(?,?,?,?)";

            PreparedStatement ps =
            Koneksi.getKoneksi().prepareStatement(sql);

            ps.setInt(1,
                    Integer.parseInt(form.txtId.getText()));

            ps.setString(2,
                    form.txtNama.getText());

            ps.setDouble(3,
                    Double.parseDouble(
                    form.txtHarga.getText()));

            ps.setString(4,
                    form.cmbStatus.getSelectedItem().toString());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Data Berhasil Disimpan");

            tampilData();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void tampilData() {

        DefaultTableModel model =
        new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Harga");
        model.addColumn("Status");

        try {

            Statement st =
            Koneksi.getKoneksi().createStatement();

            ResultSet rs =
            st.executeQuery("SELECT * FROM masakan");

            while(rs.next()){

                model.addRow(new Object[]{
                    rs.getInt("id_masakan"),
                    rs.getString("nama_masakan"),
                    rs.getDouble("harga"),
                    rs.getString("status")
                });
            }

            form.tblMasakan.setModel(model);

        } catch(Exception e){
            System.out.println(e);
        }
    }

    public void hapus() {

        try {

            String sql =
            "DELETE FROM masakan WHERE id_masakan=?";

            PreparedStatement ps =
            Koneksi.getKoneksi().prepareStatement(sql);

            ps.setInt(1,
            Integer.parseInt(form.txtId.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Data Berhasil Dihapus");

            tampilData();

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void ubah() {

        try {

            String sql =
            "UPDATE masakan SET nama_masakan=?, harga=?, status=? WHERE id_masakan=?";

            PreparedStatement ps =
            Koneksi.getKoneksi().prepareStatement(sql);

            ps.setString(1,
                    form.txtNama.getText());

            ps.setDouble(2,
                    Double.parseDouble(
                    form.txtHarga.getText()));

            ps.setString(3,
                    form.cmbStatus.getSelectedItem().toString());

            ps.setInt(4,
                    Integer.parseInt(form.txtId.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Data Berhasil Diubah");

            tampilData();

        } catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void bersih() {

        form.txtId.setText("");
        form.txtNama.setText("");
        form.txtHarga.setText("");

        form.cmbStatus.setSelectedIndex(0);
    }

//    public void simpan() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}