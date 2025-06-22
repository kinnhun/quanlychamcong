/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.AttendanceDispute;
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

public class AttendanceDisputeDAO extends DBContext {

    public boolean createDispute(AttendanceDispute dispute) {
        String sql = """
        INSERT INTO attendance_disputes
        (attendance_id, user_id, reason, status, created_at, issue_type, attachment_path)
        VALUES (?, ?, ?, ?, GETDATE(), ?, ?)
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dispute.getAttendanceId().getAttendanceId());
            ps.setInt(2, dispute.getUserId());
            ps.setString(3, dispute.getReason());
            ps.setString(4, dispute.getStatus() == null ? "pending" : dispute.getStatus());
            ps.setString(5, dispute.getIssueType());
            ps.setString(6, dispute.getAttachmentPath());
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
