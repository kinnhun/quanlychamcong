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

}
