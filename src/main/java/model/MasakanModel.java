/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public class MasakanModel {
    private int idMasakan;
    private String namaMasakan;
    private double harga;
    private String status;

    public int getIdMasakan() {
        return idMasakan;
    }

    public void setIdMasakan(int idMasakan) {
        this.idMasakan = idMasakan;
    }

    public String getNamaMasakan() {
        return namaMasakan;
    }

    public void setNamaMasakan(String namaMasakan) {
        this.namaMasakan = namaMasakan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
