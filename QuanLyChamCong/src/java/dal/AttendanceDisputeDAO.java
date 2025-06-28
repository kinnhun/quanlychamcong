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

    public List<AttendanceDispute> getDisputesByManager(int userId) {
        List<AttendanceDispute> list = new ArrayList<>();
        String sql = """
            SELECT d.*, u.full_name
            FROM attendance_disputes d
            JOIN users u ON d.user_id = u.user_id
            WHERE EXISTS (
                SELECT 1 FROM user_locations ul
                WHERE ul.user_id = d.user_id
                  AND EXISTS (
                      SELECT 1 FROM user_locations m_ul
                      WHERE m_ul.user_id = ?
                        AND m_ul.location_id = ul.location_id
                  )
            )
            ORDER BY d.created_at DESC
        """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AttendanceDispute dispute = new AttendanceDispute();
                    dispute.setDisputeId(rs.getInt("dispute_id"));
                    
                    AttendanceDAO adao = new AttendanceDAO();
                    Attendance attendance = adao.getAttendanceById(rs.getInt("attendance_id"));
                    dispute.setAttendanceId(attendance);
                    
                    dispute.setUserId(rs.getInt("user_id"));
                    dispute.setReason(rs.getString("reason"));
                    dispute.setStatus(rs.getString("status"));
                    dispute.setManagerComment(rs.getString("manager_comment"));
                    dispute.setCreatedAt(rs.getTimestamp("created_at"));
                    dispute.setResolvedAt(rs.getTimestamp("resolved_at"));
                    dispute.setIssueType(rs.getString("issue_type"));
                    dispute.setAttachmentPath(rs.getString("attachment_path"));
                    dispute.setHistory(rs.getString("history"));
                    dispute.setUpdatedAt(rs.getTimestamp("updated_at"));
                    dispute.setLastUpdatedBy(rs.getInt("last_updated_by"));
                    list.add(dispute);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<Users> getEmployeesByManager(int userId) {
        List<Users> list = new ArrayList<>();
        String sql = """
            SELECT DISTINCT u.user_id, u.full_name, u.username, u.email, u.phone, u.role, u.status, u.created_at
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
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Users user = new Users();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setRole(rs.getString("role"));
                    user.setStatus(rs.getString("status"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(user);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

   
    public List<AttendanceDispute> getDisputesByManagerWithFilters(int userId, String search, String status, java.util.Date parsedDate, int page, int pageSize) {
        List<AttendanceDispute> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT d.*, u.full_name
            FROM attendance_disputes d
            JOIN users u ON d.user_id = u.user_id
            WHERE EXISTS (
                SELECT 1 FROM user_locations ul
                WHERE ul.user_id = d.user_id
                  AND EXISTS (
                      SELECT 1 FROM user_locations m_ul
                      WHERE m_ul.user_id = ?
                        AND m_ul.location_id = ul.location_id
                  )
            )
        """);

        // Thêm điều kiện tìm kiếm
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        parameters.add(userId);

        if (search != null && !search.trim().isEmpty()) {
            conditions.add("(u.full_name LIKE ? OR d.reason LIKE ?)");
            parameters.add("%" + search.trim() + "%");
            parameters.add("%" + search.trim() + "%");
        }

        if (status != null && !status.trim().isEmpty()) {
            conditions.add("d.status = ?");
            parameters.add(status.trim());
        }

        if (parsedDate != null) {
            conditions.add("CAST(d.created_at AS DATE) = ?");
            parameters.add(new java.sql.Date(parsedDate.getTime()));
        }

        // Thêm các điều kiện vào câu SQL
        if (!conditions.isEmpty()) {
            sql.append(" AND ").append(String.join(" AND ", conditions));
        }

        // Thêm sắp xếp và phân trang
        sql.append(" ORDER BY d.created_at DESC");
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            // Gán giá trị cho các tham số
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            ps.setInt(parameters.size() + 1, (page - 1) * pageSize);
            ps.setInt(parameters.size() + 2, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AttendanceDispute dispute = new AttendanceDispute();
                    dispute.setDisputeId(rs.getInt("dispute_id"));

                    AttendanceDAO adao = new AttendanceDAO();
                    Attendance attendance = adao.getAttendanceById(rs.getInt("attendance_id"));
                    dispute.setAttendanceId(attendance);

                    dispute.setUserId(rs.getInt("user_id"));
                    dispute.setReason(rs.getString("reason"));
                    dispute.setStatus(rs.getString("status"));
                    dispute.setManagerComment(rs.getString("manager_comment"));
                    dispute.setCreatedAt(rs.getTimestamp("created_at"));
                    dispute.setResolvedAt(rs.getTimestamp("resolved_at"));
                    dispute.setIssueType(rs.getString("issue_type"));
                    dispute.setAttachmentPath(rs.getString("attachment_path"));
                    dispute.setHistory(rs.getString("history"));
                    dispute.setUpdatedAt(rs.getTimestamp("updated_at"));
                    dispute.setLastUpdatedBy(rs.getInt("last_updated_by"));
                    list.add(dispute);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public int getTotalDisputesByManagerWithFilters(int userId, String search, String status, java.util.Date parsedDate) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) AS total
            FROM attendance_disputes d
            JOIN users u ON d.user_id = u.user_id
            WHERE EXISTS (
                SELECT 1 FROM user_locations ul
                WHERE ul.user_id = d.user_id
                  AND EXISTS (
                      SELECT 1 FROM user_locations m_ul
                      WHERE m_ul.user_id = ?
                        AND m_ul.location_id = ul.location_id
                  )
            )
        """);

        // Thêm điều kiện tìm kiếm
        List<String> conditions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        parameters.add(userId);

        if (search != null && !search.trim().isEmpty()) {
            conditions.add("(u.full_name LIKE ? OR d.reason LIKE ?)");
            parameters.add("%" + search.trim() + "%");
            parameters.add("%" + search.trim() + "%");
        }

        if (status != null && !status.trim().isEmpty()) {
            conditions.add("d.status = ?");
            parameters.add(status.trim());
        }

        if (parsedDate != null) {
            conditions.add("CAST(d.created_at AS DATE) = ?");
            parameters.add(new java.sql.Date(parsedDate.getTime()));
        }

        // Thêm các điều kiện vào câu SQL
        if (!conditions.isEmpty()) {
            sql.append(" AND ").append(String.join(" AND ", conditions));
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            // Gán giá trị cho các tham số
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public boolean updateDispute(AttendanceDispute dispute) {
        String sql = "UPDATE [dbo].[attendance_disputes] " +
                     "SET status = ?, manager_comment = ?, resolved_at = GETDATE(), " +
                     "updated_at = GETDATE(), last_updated_by = ? " +
                     "WHERE dispute_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, dispute.getStatus());
            stmt.setString(2, dispute.getManagerComment());
            stmt.setInt(3, dispute.getLastUpdatedBy());
            stmt.setInt(4, dispute.getDisputeId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}