/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import koneksi.Koneksi;
import java.sql.*;
import javax.swing.JOptionPane;
import view.BahanView;
import view.MasakanView;
import view.TransaksiView;
import view.LoginView;
/**
 *
 * @author Asus
 */

public class LoginController {
        LoginView form;

    public LoginController(LoginView form) {
        this.form = form;
    }
    
     public void login() {

        String username =
                form.txtUsername.getText();

        String password =
                String.valueOf(
                form.txtPassword.getText());

        String role =
                form.cmbRole.getSelectedItem()
                .toString();

        try {

            String sql =
            "SELECT * FROM users "
            + "WHERE username=? "
            + "AND password=? "
            + "AND role=?";

            PreparedStatement ps =
            Koneksi.getKoneksi()
            .prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs =
            ps.executeQuery();

            if(rs.next()){

                JOptionPane.showMessageDialog(
                null,
                "Login Berhasil");

                if(role.equals("Manajemen Dapur")){

                    MasakanView Dapur =
                    new MasakanView();

                    Dapur.setVisible(true);

                } else if(role.equals("Manajemen Logistik/gudang")){
                    
                    BahanView Bahan = new BahanView();
                    
                    Bahan.setVisible(true);
                } else if(role.equals("Kasir")){
                    
                    TransaksiView Transaksi = new TransaksiView();
                    
                    Transaksi.setVisible(true);
                }

                form.dispose();

            }else{

                JOptionPane.showMessageDialog(
                null,
                "Username, Password atau Role Salah");

            }

        } catch(Exception e){

            JOptionPane.showMessageDialog(
            null,
            e.getMessage());

        }
     }
}
