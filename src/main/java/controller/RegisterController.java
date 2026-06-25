/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import koneksi.Koneksi;
import java.sql.*;
import javax.swing.JOptionPane;
import view.LoginView;
import view.RegisterView;

/**
 *
 * @author Asus
 */
public class RegisterController {
    RegisterView view;

    public RegisterController(RegisterView view) {
        this.view = view;
    }
    
    public void register() {

        String username =
                view.txtUsername.getText();

        String password =
                String.valueOf(
                view.txtPassword.getText());

        String role =
                view.cmbRole.getSelectedItem().toString();

        try {

            if(username.isEmpty()
                    || password.isEmpty()
                    || role.equals("Pilih Role")) {

                JOptionPane.showMessageDialog(
                        null,
                        "Lengkapi Semua Data");

                return;
            }

            String cekSql =
            "SELECT * FROM users WHERE username=?";

            PreparedStatement cek =
            Koneksi.getKoneksi()
            .prepareStatement(cekSql);

            cek.setString(1, username);

            ResultSet rs =
            cek.executeQuery();

            if(rs.next()){

                JOptionPane.showMessageDialog(
                        null,
                        "Username Sudah Digunakan");

                return;
            }

            String sql =
            "INSERT INTO users(username,password,role) VALUES(?,?,?)";

            PreparedStatement ps =
            Koneksi.getKoneksi()
            .prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(
                    null,
                    "Registrasi Berhasil");

            LoginView login =
            new LoginView();

            login.setVisible(true);

            view.dispose();

        } catch(Exception e){

            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage());

        }
    }

    public void kembali() {

        LoginView login =
        new LoginView();

        login.setVisible(true);

        view.dispose();
    }
}
