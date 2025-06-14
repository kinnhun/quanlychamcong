/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Users;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserLocationDAO extends DBContext {

   public boolean assignUserToLocation(int userId, int locationId, int departmentId) {
    String sql = "INSERT INTO user_locations (user_id, location_id, department_id, assigned_at) VALUES (?, ?, ?, GETDATE())";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ps.setInt(2, locationId);
        ps.setInt(3, departmentId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


    public List<Users> getUsersByLocation(int locationId) {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT u.* FROM users u JOIN user_locations ul ON u.user_id = ul.user_id WHERE ul.location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users u = new Users();
                // set fields...
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> getAllAssignments() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT u.full_name, u.email, u.role, l.name AS location_name "
                + "FROM user_locations ul "
                + "JOIN users u ON ul.user_id = u.user_id "
                + "JOIN locations l ON ul.location_id = l.location_id";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getString("full_name");
                row[1] = rs.getString("email");
                row[2] = rs.getString("role");
                row[3] = rs.getString("location_name");
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

public List<Object[]> searchAssignments(String locationId, String role, String status, String keyword) {
    List<Object[]> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder(
            "SELECT u.full_name, u.email, l.name AS location_name, l.address, d.department_name, ul.assigned_at, u.user_id, l.location_id, d.department_id "
            + "FROM user_locations ul "
            + "JOIN users u ON ul.user_id = u.user_id "
            + "JOIN locations l ON ul.location_id = l.location_id "
            + "JOIN departments d ON ul.department_id = d.department_id "
            + "WHERE 1=1");

    List<Object> params = new ArrayList<>();

    if (locationId != null && !locationId.isEmpty()) {
        sql.append(" AND l.location_id = ?");
        params.add(Integer.parseInt(locationId));
    }
    if (role != null && !role.isEmpty()) {
        sql.append(" AND u.role = ?");
        params.add(role);
    }
    if (status != null && !status.isEmpty()) {
        sql.append(" AND u.status = ?");
        params.add(status);
    }
    if (keyword != null && !keyword.trim().isEmpty()) {
        sql.append(" AND (u.full_name LIKE ? OR u.email LIKE ? OR u.username LIKE ?)");
        String like = "%" + keyword + "%";
        params.add(like);
        params.add(like);
        params.add(like);
    }

    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Object[] row = new Object[9];
            row[0] = rs.getString("full_name");
            row[1] = rs.getString("email");
            row[2] = rs.getString("location_name");
            row[3] = rs.getString("address");
            row[4] = rs.getString("department_name"); // ✅ tên phòng ban
            row[5] = rs.getTimestamp("assigned_at");
            row[6] = rs.getInt("user_id");
            row[7] = rs.getInt("location_id");
            row[8] = rs.getInt("department_id");
            list.add(row);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

   
public boolean deleteAssignment(int userId, int locationId) {
    String sql = "DELETE FROM user_locations WHERE user_id = ? AND location_id = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ps.setInt(2, locationId);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

public boolean isAssigned(int userId, int locationId, int departmentId) {
    String sql = "SELECT 1 FROM user_locations WHERE user_id = ? AND location_id = ? AND department_id = ?";
    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ps.setInt(2, locationId);
        ps.setInt(3, departmentId);
        ResultSet rs = ps.executeQuery();
        return rs.next(); // true nếu tồn tại
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
