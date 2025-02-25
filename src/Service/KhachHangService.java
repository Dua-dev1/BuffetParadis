/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;
import Entity.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class KhachHangService {
    private final String connectionUrl = 
        "jdbc:sqlserver://localhost:1433;databaseName=BuffetParadis;user=sa;password=Traumerei362;trustServerCertificate=true;encrypt=true";

    // Get all customers
    public List<KhachHang> getAllKhachHang() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT tenKhachHang, SDT, ngayDatHang FROM KhachHang";
        
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(new KhachHang(
                    rs.getString("tenKhachHang"),
                    rs.getString("SDT"),
                    rs.getString("ngayDatHang")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi truy vấn dữ liệu!");
        }
        return list;
    }

    // Add new customer
    public boolean addKhachHang(KhachHang kh) {
        if (!validateKhachHang(kh)) return false;

        if (findKhachHangBySDT(kh.getSDT()) != null) {
            JOptionPane.showMessageDialog(null, "Số điện thoại đã tồn tại!");
            return false;
        }

        String sql = "INSERT INTO KhachHang (tenKhachHang, SDT, ngayDatHang) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, kh.getTenKhachHang());
            ps.setString(2, kh.getSDT());
            ps.setString(3, kh.getNgayDatHang());
            
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thêm khách hàng thành công!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi thêm khách hàng!");
        }
        return false;
    }

    // Update customer by SDT
   public boolean updateKhachHang(KhachHang kh) {
    if (!validateKhachHang(kh)) return false;

    String sql = "UPDATE KhachHang SET tenKhachHang = ? WHERE SDT = ?";
    try (Connection conn = DriverManager.getConnection(connectionUrl);
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, kh.getTenKhachHang());
        ps.setString(2, kh.getSDT());

        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy số điện thoại trong hệ thống!");
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
   public boolean updateKhachHangAndDatHang(KhachHang kh) {
    if (!validateKhachHang(kh)) return false;

    String sqlKhachHang = "UPDATE KhachHang SET tenKhachHang = ? WHERE SDT = ?";
    String sqlDatHang = "UPDATE DatHang SET tenKhachHang = ?, ngayDatHang = ? WHERE SDT = ?";

    try (Connection conn = DriverManager.getConnection(connectionUrl)) {
        conn.setAutoCommit(false); // Start transaction

        try (PreparedStatement psKhachHang = conn.prepareStatement(sqlKhachHang);
             PreparedStatement psDatHang = conn.prepareStatement(sqlDatHang)) {

            // Update KhachHang
            psKhachHang.setString(1, kh.getTenKhachHang());
            psKhachHang.setString(2, kh.getSDT());
            int rowsKhachHang = psKhachHang.executeUpdate();

            // Update DatHang
            psDatHang.setString(1, kh.getTenKhachHang());
            psDatHang.setString(2, kh.getNgayDatHang());
            psDatHang.setString(3, kh.getSDT());
            int rowsDatHang = psDatHang.executeUpdate();

            if (rowsKhachHang > 0 && rowsDatHang > 0) {
                conn.commit(); // Commit transaction if both updates succeed
                return true;
            } else {
                conn.rollback(); // Rollback if either update fails
                JOptionPane.showMessageDialog(null, "Không tìm thấy số điện thoại trong hệ thống!");
                return false;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



    // Delete customer by SDT
    public boolean deleteKhachHang(String SDT) {
        if (findKhachHangBySDT(SDT) == null) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không tồn tại!");
            return false;
        }

        String sql = "DELETE FROM KhachHang WHERE SDT = ?";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, SDT);
            
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi xóa khách hàng!");
        }
        return false;
    }

    // Find customer by SDT
    public KhachHang findKhachHangBySDT(String SDT) {
        String sql = "SELECT tenKhachHang, SDT, ngayDatHang FROM KhachHang WHERE SDT = ?";
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, SDT);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new KhachHang(rs.getString("tenKhachHang"), rs.getString("SDT"), rs.getString("ngayDatHang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi tìm khách hàng!");
        }
        return null;
    }

    // Validate customer data
    private boolean validateKhachHang(KhachHang kh) {
        if (kh.getTenKhachHang().isEmpty() || kh.getSDT().isEmpty() || kh.getNgayDatHang().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không được để trống bất kỳ trường nào!");
            return false;
        }
        if (!kh.getSDT().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Số điện thoại phải có 10 chữ số!");
            return false;
        }
        if (!kh.getNgayDatHang().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(null, "Ngày đặt hàng phải có định dạng yyyy-MM-dd!");
            return false;
        }
        return true;
    }
    // Check if an SDT exists in the database
public boolean isSDTExists(String SDT) {
    String sql = "SELECT COUNT(*) FROM KhachHang WHERE SDT = ?";
    try (Connection conn = DriverManager.getConnection(connectionUrl);
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, SDT);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // If count > 0, SDT exists
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
public boolean deleteDatHang(String SDT) {
    String sql = "DELETE FROM DatHang WHERE SDT = ?";
    try (Connection conn = DriverManager.getConnection(connectionUrl);
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, SDT);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



}





