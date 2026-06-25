/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

/**
 *
 * @author Asus
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection koneksi;

    public static Connection getKoneksi() {

        try {

            String url = "jdbc:mysql://localhost/restoran_tubes";
            String user = "root";
            String pass = "";

            koneksi = DriverManager.getConnection(
                    url, user, pass);

        } catch (Exception e) {
            System.out.println(e);
        }

        return koneksi;
    }
}
