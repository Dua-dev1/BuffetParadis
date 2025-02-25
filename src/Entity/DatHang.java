/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Omen
 */
public class DatHang {
    private String tenKhachHang;
    private String SDT;
    private String ngayDatHang;

    public DatHang() {
    }

    public DatHang(String tenKhachHang, String SDT, String ngayDatHang) {
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

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }
}
