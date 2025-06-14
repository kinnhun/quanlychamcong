/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import model.Attendance;
import model.Locations;
import java.sql.Timestamp;
import model.Users;

public class AttendanceDAO extends DBContext {

    public Attendance getAttendanceToday(int userId, LocalDate date) {
        String sql = "SELECT * FROM attendance WHERE user_id = ? AND date = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Attendance a = new Attendance();
                a.setCheckinTime(rs.getTimestamp("checkin_time"));
                a.setCheckoutTime(rs.getTimestamp("checkout_time"));
                a.setCheckinImageUrl(rs.getString("checkin_image_url"));
                a.setCheckoutImageUrl(rs.getString("checkout_image_url"));
                return a;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean saveAttendance(int userId, String action, String fileName) {
        LocalDate today = LocalDate.now();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        String selectSql = "SELECT * FROM attendance WHERE user_id = ? AND date = ?";
        String insertSql = "INSERT INTO attendance (user_id, date, location_id, checkin_time, checkin_image_url, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        String updateCheckin = "UPDATE attendance SET checkin_time = ?, checkin_image_url = ? WHERE user_id = ? AND date = ?";
        String updateCheckout = "UPDATE attendance SET checkout_time = ?, checkout_image_url = ? WHERE user_id = ? AND date = ?";

        try (Connection conn = getConnection(); PreparedStatement checkStmt = conn.prepareStatement(selectSql)) {

            checkStmt.setInt(1, userId);
            checkStmt.setDate(2, Date.valueOf(today));
            ResultSet rs = checkStmt.executeQuery();

            boolean exists = rs.next();

            if (!exists && action.equals("checkin")) {
                try (PreparedStatement insert = conn.prepareStatement(insertSql)) {
                    insert.setInt(1, userId);
                    insert.setDate(2, Date.valueOf(today));
                    insert.setInt(3, 1); // ✅ mặc định location_id = 1 (có thể sửa)
                    insert.setTimestamp(4, now);
                    insert.setString(5, fileName);
                    insert.setTimestamp(6, now);
                    return insert.executeUpdate() > 0;
                }
            }

            if (exists && action.equals("checkin")) {
                try (PreparedStatement update = conn.prepareStatement(updateCheckin)) {
                    update.setTimestamp(1, now);
                    update.setString(2, fileName);
                    update.setInt(3, userId);
                    update.setDate(4, Date.valueOf(today));
                    return update.executeUpdate() > 0;
                }
            }

            if (exists && action.equals("checkout")) {
                try (PreparedStatement update = conn.prepareStatement(updateCheckout)) {
                    update.setTimestamp(1, now);
                    update.setString(2, fileName);
                    update.setInt(3, userId);
                    update.setDate(4, Date.valueOf(today));
                    return update.executeUpdate() > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Attendance> getAttendanceByManager(int managerId) {
        List<Attendance> list = new ArrayList<>();
        String sql = """
        SELECT a.*, 
               u.user_id, u.full_name, u.username,
               l.location_id, l.name AS location_name, l.address AS location_address
        FROM attendance a
        JOIN users u ON a.user_id = u.user_id
        LEFT JOIN locations l ON a.location_id = l.location_id
        -- Chỉ lấy các dòng mà manager quản lý user đó qua user_locations
        WHERE EXISTS (
            SELECT 1 FROM user_locations ul
            WHERE ul.user_id = a.user_id
              AND EXISTS (
                  SELECT 1 FROM user_locations m_ul
                  WHERE m_ul.user_id = ?
                    AND m_ul.location_id = ul.location_id
              )
        )
        ORDER BY a.date DESC, u.full_name ASC
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance att = new Attendance();

                    att.setAttendanceId(rs.getInt("attendance_id"));

                    // User
                    Users user = new Users();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setUsername(rs.getString("username"));
                    att.setUser(user);

                    // Date, Time
                    att.setDate(rs.getDate("date"));
                    att.setCheckinTime(rs.getTimestamp("checkin_time"));
                    att.setCheckoutTime(rs.getTimestamp("checkout_time"));

                    // Location
                    Locations loc = new Locations();
                    loc.setId(rs.getInt("location_id"));
                    loc.setName(rs.getString("location_name"));
                    loc.setAddress(rs.getString("location_address"));
                    att.setLocation(loc);

                    att.setCheckinImageUrl(rs.getString("checkin_image_url"));
                    att.setCheckoutImageUrl(rs.getString("checkout_image_url"));
                    att.setIsLocked(rs.getBoolean("is_locked"));
                    att.setCreatedAt(rs.getTimestamp("created_at"));

                    list.add(att);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int countAttendanceByManagerFilter(int managerId, Integer employeeId, String status, String date) {
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*) FROM attendance a
        JOIN users u ON a.user_id = u.user_id
        WHERE EXISTS (
            SELECT 1 FROM user_locations ul
            WHERE ul.user_id = a.user_id
              AND EXISTS (
                  SELECT 1 FROM user_locations m_ul
                  WHERE m_ul.user_id = ?
                    AND m_ul.location_id = ul.location_id
              )
        )
    """);
        List<Object> params = new ArrayList<>();
        params.add(managerId);

        if (employeeId != null) {
            sql.append(" AND a.user_id = ? ");
            params.add(employeeId);
        }
        if (status != null && !status.isEmpty()) {
            if (status.equals("present")) {
                sql.append(" AND a.checkin_time IS NOT NULL ");
            } else if (status.equals("absent")) {
                sql.append(" AND a.checkin_time IS NULL ");
            }
        }
        if (date != null && !date.isEmpty()) {
            sql.append(" AND a.date = ? ");
            params.add(Date.valueOf(date));
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Attendance> getAttendanceByManagerFilter(int managerId, Integer employeeId, String status, String date, int page, int pageSize) {
        List<Attendance> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT a.*, 
               u.user_id, u.full_name, u.username,
               l.location_id, l.name AS location_name, l.address AS location_address
        FROM attendance a
        JOIN users u ON a.user_id = u.user_id
        LEFT JOIN locations l ON a.location_id = l.location_id
        WHERE EXISTS (
            SELECT 1 FROM user_locations ul
            WHERE ul.user_id = a.user_id
              AND EXISTS (
                  SELECT 1 FROM user_locations m_ul
                  WHERE m_ul.user_id = ?
                    AND m_ul.location_id = ul.location_id
              )
        )
    """);
        List<Object> params = new ArrayList<>();
        params.add(managerId);

        if (employeeId != null) {
            sql.append(" AND a.user_id = ? ");
            params.add(employeeId);
        }
        if (status != null && !status.isEmpty()) {
            if (status.equals("present")) {
                sql.append(" AND a.checkin_time IS NOT NULL ");
            } else if (status.equals("absent")) {
                sql.append(" AND a.checkin_time IS NULL ");
            }
        }
        if (date != null && !date.isEmpty()) {
            sql.append(" AND a.date = ? ");
            params.add(Date.valueOf(date));
        }
        sql.append(" ORDER BY a.date DESC, u.full_name ASC ");
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");

        // Phân trang tính offset
        int offset = (page - 1) * pageSize;
        params.add(offset);
        params.add(pageSize);

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Attendance att = new Attendance();

                att.setAttendanceId(rs.getInt("attendance_id"));

                // User
                Users user = new Users();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setUsername(rs.getString("username"));
                att.setUser(user);

                // Date, Time
                att.setDate(rs.getDate("date"));
                att.setCheckinTime(rs.getTimestamp("checkin_time"));
                att.setCheckoutTime(rs.getTimestamp("checkout_time"));

                // Location
                Locations loc = new Locations();
                loc.setId(rs.getInt("location_id"));
                loc.setName(rs.getString("location_name"));
                loc.setAddress(rs.getString("location_address"));
                att.setLocation(loc);

                att.setCheckinImageUrl(rs.getString("checkin_image_url"));
                att.setCheckoutImageUrl(rs.getString("checkout_image_url"));
                att.setIsLocked(rs.getBoolean("is_locked"));
                att.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(att);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Users> getEmployeesByManager(int managerId) {
        List<Users> list = new ArrayList<>();
        String sql = """
        SELECT DISTINCT u.user_id, u.full_name, u.username
        FROM users u
        JOIN user_locations ul ON ul.user_id = u.user_id
        WHERE EXISTS (
            SELECT 1 FROM user_locations m_ul
            WHERE m_ul.user_id = ?
              AND m_ul.location_id = ul.location_id
        )
       
        ORDER BY u.full_name ASC
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setUsername(rs.getString("username"));
                list.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

}
