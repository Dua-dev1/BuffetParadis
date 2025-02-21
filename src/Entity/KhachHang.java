/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ASUS
 */
public class KhachHang {
    private String tenKhachHang;
    private int SDT;
    private String ngayDatHang;

    public KhachHang() {
    }

    public KhachHang(String tenKhachHang, int SDT, String ngayDatHang) {
        this.tenKhachHang = tenKhachHang;
        this.SDT = SDT;
        this.ngayDatHang = ngayDatHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }
    
    
}
