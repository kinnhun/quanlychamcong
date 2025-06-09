package dal;

import model.LeaveRequest;
import model.Users;
import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.time.LocalDate;
import model.LeaveConfig;
import model.LeaveType;
import model.Locations;

public class LeaveRequestDAO extends DBContext {

//    public List<LeaveRequest> getAllRequests() {
//        List<LeaveRequest> list = new ArrayList<>();
//        String sql = "SELECT lr.*, u.full_name as requester, a.full_name as approver FROM leave_requests lr "
//                + "LEFT JOIN users u ON lr.user_id = u.user_id "
//                + "LEFT JOIN users a ON lr.approved_by = a.user_id";
//
//        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                LeaveRequest lr = new LeaveRequest();
//                Users requester = new Users();
//                requester.setUserId(rs.getInt("user_id"));
//                requester.setFullName(rs.getString("requester"));
//
//                Users approver = null;
//                if (rs.getInt("approved_by") != 0) {
//                    approver = new Users();
//                    approver.setUserId(rs.getInt("approved_by"));
//                    approver.setFullName(rs.getString("approver"));
//                }
//
//                lr.setRequestId(rs.getInt("request_id"));
//                lr.setUser(requester);
//                lr.setStartDate(rs.getDate("start_date"));
//                lr.setEndDate(rs.getDate("end_date"));
//                lr.setLeaveType(rs.getString("leave_type"));
//                lr.setStatus(rs.getString("status"));
//                lr.setDaysCount(rs.getInt("days_count"));
//                lr.setReason(rs.getString("reason"));
//                lr.setCreatedAt(rs.getTimestamp("created_at"));
//                lr.setApprovedBy(approver);
//
//                list.add(lr);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
    public List<LeaveRequest> getAllRequests() {
        List<LeaveRequest> list = new ArrayList<>();

        String sql = "SELECT lr.*, u.full_name AS requester, a.full_name AS approver, "
                + "l.location_id, l.name AS location_name, l.address, l.is_active, l.ip_map "
                + "FROM leave_requests lr "
                + "JOIN users u ON lr.user_id = u.user_id "
                + "LEFT JOIN users a ON lr.approved_by = a.user_id "
                + "LEFT JOIN user_locations ul ON ul.user_id = u.user_id "
                + "LEFT JOIN locations l ON ul.location_id = l.location_id "
                + "ORDER BY lr.request_id";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            int currentRequestId = -1;
            LeaveRequest lr = null;

            while (rs.next()) {
                int requestId = rs.getInt("request_id");

                // Nếu gặp request_id mới → tạo LeaveRequest mới
                if (requestId != currentRequestId) {
                    lr = new LeaveRequest();

                    // Lấy người nộp đơn
                    Users requester = new Users();
                    requester.setUserId(rs.getInt("user_id"));
                    requester.setFullName(rs.getString("requester"));

                    // Lấy người duyệt (nếu có)
                    Users approver = null;
                    if (rs.getInt("approved_by") != 0) {
                        approver = new Users();
                        approver.setUserId(rs.getInt("approved_by"));
                        approver.setFullName(rs.getString("approver"));
                    }

                    // Set thông tin LeaveRequest
                    lr.setRequestId(requestId);
                    lr.setUser(requester);
                    lr.setStartDate(rs.getDate("start_date"));
                    lr.setEndDate(rs.getDate("end_date"));

                    // leave_type
                    int leaveTypeId = rs.getInt("leave_type_id");
                    LeaveRequestDAO ldao = new LeaveRequestDAO();
                    LeaveType leaveType = ldao.getLeaveTypeById(leaveTypeId);
                    lr.setLeaveTypeId(leaveType);
                    lr.setStatus(rs.getString("status"));
                    lr.setDaysCount(rs.getInt("days_count"));
                    lr.setReason(rs.getString("reason"));
                    lr.setCreatedAt(rs.getTimestamp("created_at"));
                    lr.setApprovedBy(approver);
                    lr.setApproveComment(rs.getString("approve_comment"));

                    // Tạo list locations mới
                    lr.setLocations(new ArrayList<>());

                    // Thêm vào list chính
                    list.add(lr);

                    currentRequestId = requestId;
                }

                // Với mỗi dòng → kiểm tra nếu có location thì thêm vào list locations
                int locationId = rs.getInt("location_id");
                if (locationId != 0) {
                    Locations loc = new Locations();
                    loc.setId(locationId);
                    loc.setName(rs.getString("location_name"));
                    loc.setAddress(rs.getString("address"));
                    loc.setIsActive(rs.getBoolean("is_active"));
                    loc.setIpMap(rs.getString("ip_map"));

                    lr.getLocations().add(loc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void approveRequest(int requestId, int approverId, String note) {
        String sql = "UPDATE leave_requests SET status = 'approved', approved_by = ?, approve_note = ? WHERE request_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, approverId);
            ps.setString(2, note);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rejectRequest(int requestId, int approverId, String note) {
        String sql = "UPDATE leave_requests SET status = 'rejected', approved_by = ?, approve_comment = ? WHERE request_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, approverId);
            ps.setString(2, note);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelRequest(int requestId, int userId, String note) {
        String sql = "UPDATE leave_requests SET status = 'canceled', approved_by = ?, approve_comment = ? WHERE request_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, note);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LeaveRequest getRequestById(int id) {
        String sql = "SELECT lr.*, u.full_name as requester, a.full_name as approver FROM leave_requests lr "
                + "LEFT JOIN users u ON lr.user_id = u.user_id "
                + "LEFT JOIN users a ON lr.approved_by = a.user_id "
                + "WHERE lr.request_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LeaveRequest lr = new LeaveRequest();

                // Người gửi
                Users requester = new Users();
                requester.setUserId(rs.getInt("user_id"));
                requester.setFullName(rs.getString("requester"));
                lr.setUser(requester);

                // Người duyệt (có thể null)
                int approverId = rs.getInt("approved_by");
                Users approver = null;
                if (!rs.wasNull()) {
                    approver = new Users();
                    approver.setUserId(approverId);
                    approver.setFullName(rs.getString("approver"));
                }

                // Gán các thuộc tính còn lại
                lr.setRequestId(rs.getInt("request_id"));
                lr.setStartDate(rs.getDate("start_date"));
                lr.setEndDate(rs.getDate("end_date"));

                // leave_type
                int leaveTypeId = rs.getInt("leave_type_id");
                LeaveRequestDAO ldao = new LeaveRequestDAO();
                LeaveType leaveType = ldao.getLeaveTypeById(leaveTypeId);
                lr.setLeaveTypeId(leaveType);
                lr.setStatus(rs.getString("status"));
                lr.setDaysCount(rs.getInt("days_count"));
                lr.setReason(rs.getString("reason"));
                lr.setCreatedAt(rs.getTimestamp("created_at"));
                lr.setApprovedBy(approver);
                lr.setApproveComment(rs.getString("approve_comment"));

                return lr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LeaveRequest> getRequestsByUserId(int userId) {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT lr.*, u.full_name as requester FROM leave_requests lr "
                + "JOIN users u ON lr.user_id = u.user_id "
                + "WHERE lr.user_id = ? ORDER BY lr.created_at DESC";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                Users requester = new Users();
                requester.setUserId(rs.getInt("user_id"));
                requester.setFullName(rs.getString("requester"));

                lr.setRequestId(rs.getInt("request_id"));
                lr.setUser(requester);
                lr.setStartDate(rs.getDate("start_date"));
                lr.setEndDate(rs.getDate("end_date"));

                // leave_type
                int leaveTypeId = rs.getInt("leave_type_id");
                LeaveRequestDAO ldao = new LeaveRequestDAO();
                LeaveType leaveType = ldao.getLeaveTypeById(leaveTypeId);
                lr.setLeaveTypeId(leaveType);
                lr.setStatus(rs.getString("status"));
                lr.setDaysCount(rs.getInt("days_count"));
                lr.setReason(rs.getString("reason"));
                lr.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(lr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        LeaveRequestDAO dao = new LeaveRequestDAO();
        List<LeaveConfig> list = dao.getAllConfigs();
        for (LeaveConfig leaveConfig : list) {
            System.out.println(leaveConfig.toString());
        }
    }

    // Lấy danh sách LeaveConfig
    public List<LeaveConfig> getAllConfigs() {
        List<LeaveConfig> list = new ArrayList<>();
        String sql = "SELECT lc.config_id, lc.year, lc.default_days, lc.created_by, lc.leave_type_id, "
                + "u.full_name AS creator_name, "
                + "lt.leave_type_name, lt.status "
                + "FROM leave_config lc "
                + "LEFT JOIN users u ON lc.created_by = u.user_id "
                + "LEFT JOIN leave_types lt ON lc.leave_type_id = lt.leave_type_id "
                + "ORDER BY lc.year DESC, lt.leave_type_name";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveConfig config = new LeaveConfig();

                config.setConfigId(rs.getInt("config_id"));
                config.setYear(rs.getInt("year"));
                config.setDefaultDays(rs.getInt("default_days"));

                // Set createdBy
                Users creator = new Users();
                creator.setUserId(rs.getInt("created_by"));
                creator.setFullName(rs.getString("creator_name"));
                config.setCreatedBy(creator);

                // Set LeaveType
                LeaveType leaveType = new LeaveType();
                leaveType.setLeaveTypeId(rs.getInt("leave_type_id"));
                leaveType.setLeaveTypeName(rs.getString("leave_type_name"));
                leaveType.setStatus(rs.getString("status"));
                config.setLeaveTypeId(leaveType);

                // Add to list
                list.add(config);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

// Thêm mới LeaveConfig
    public boolean addConfig(LeaveConfig config) {
        String sql = "INSERT INTO leave_config (year, leave_type_id, default_days, created_by) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, config.getYear());
            ps.setInt(2, config.getLeaveTypeId().getLeaveTypeId());
            ps.setInt(3, config.getDefaultDays());
            ps.setInt(4, config.getCreatedBy().getUserId());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

// Xóa LeaveConfig
    public boolean deleteConfig(int configId) {
        String sql = "DELETE FROM leave_config WHERE config_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, configId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addLeaveType(LeaveType newType) {
        String sql = "INSERT INTO leave_types (leave_type_name) VALUES (?)";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newType.getLeaveTypeName());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;  // Trả về true nếu thêm thành công

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteLeaveType(int id) {
        String sql = "DELETE FROM leave_types WHERE leave_type_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;  // Trả về true nếu xóa thành công

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LeaveType> getAllLeaveTypes() {
        List<LeaveType> list = new ArrayList<>();
        String sql = "SELECT leave_type_id, leave_type_name, status FROM leave_types ORDER BY leave_type_name";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LeaveType lt = new LeaveType();
                lt.setLeaveTypeId(rs.getInt("leave_type_id"));
                lt.setLeaveTypeName(rs.getString("leave_type_name"));
                lt.setStatus(rs.getString("status"));

                list.add(lt);
            }

        } catch (Exception e) {
            System.err.println("❌ Error in getAllLeaveTypes:");
            e.printStackTrace();
        }

        return list;
    }

    public boolean toggleLeaveTypeStatus(int id) {
        String sql = "UPDATE leave_types SET status = CASE WHEN status = 'active' THEN 'inactive' ELSE 'active' END WHERE leave_type_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LeaveType getLeaveTypeById(int id) {
        String sql = "SELECT leave_type_id, leave_type_name, status FROM leave_types WHERE leave_type_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LeaveType lt = new LeaveType();
                lt.setLeaveTypeId(rs.getInt("leave_type_id"));
                lt.setLeaveTypeName(rs.getString("leave_type_name"));
                lt.setStatus(rs.getString("status"));

                return lt;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Nếu không tìm thấy
    }

    public List<LeaveType> getAllLeaveTypesActive() {
        List<LeaveType> list = new ArrayList<>();
        String sql = "SELECT leave_type_id, leave_type_name, status FROM leave_types WHERE status = 'active' ORDER BY leave_type_name";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LeaveType lt = new LeaveType();
                lt.setLeaveTypeId(rs.getInt("leave_type_id"));
                lt.setLeaveTypeName(rs.getString("leave_type_name"));
                lt.setStatus(rs.getString("status"));

                list.add(lt);
            }

        } catch (Exception e) {
            System.err.println("❌ Error in getAllLeaveTypesActive:");
            e.printStackTrace();
        }

        return list;
    }

    public boolean getLeaveConfigByYearType(int year, LeaveType leaveType) {
        String sql = "SELECT 1 FROM leave_config WHERE year = ? AND leave_type_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, year);
            ps.setInt(2, leaveType.getLeaveTypeId());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public LeaveConfig getLeaveConfigById(int id) {
        String sql = "SELECT lc.config_id, lc.year, lc.default_days, lc.created_by, lc.leave_type_id "
                + "FROM leave_config lc "
                + "WHERE lc.config_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LeaveConfig config = new LeaveConfig();

                config.setConfigId(rs.getInt("config_id"));
                config.setYear(rs.getInt("year"));
                config.setDefaultDays(rs.getInt("default_days"));

                // created_by
                int creatorId = rs.getInt("created_by");
                UserDAO udao = new UserDAO();
                Users creator = udao.getUserById(creatorId);
                config.setCreatedBy(creator);

                // leave_type
                int leaveTypeId = rs.getInt("leave_type_id");
                LeaveRequestDAO ldao = new LeaveRequestDAO();
                LeaveType leaveType = ldao.getLeaveTypeById(leaveTypeId);
                config.setLeaveTypeId(leaveType);

                return config;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateLeaveConfig(LeaveConfig config) {
        String sql = "UPDATE leave_config SET year = ?, leave_type_id = ?, default_days = ? WHERE config_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, config.getYear());
            ps.setInt(2, config.getLeaveTypeId().getLeaveTypeId());
            ps.setInt(3, config.getDefaultDays());
            ps.setInt(4, config.getConfigId());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean createRequest(int userId, Date startDate, Date endDate, int leaveTypeId, String reason, int daysCount) {
        String sql = "INSERT INTO leave_requests "
                + "(user_id, start_date, end_date, leave_type_id, reason, days_count, status, created_at, approved_by, approve_comment) "
                + "VALUES (?, ?, ?, ?, ?, ?, 'pending', GETDATE(), NULL, NULL)";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            ps.setInt(4, leaveTypeId);
            ps.setString(5, reason);
            ps.setInt(6, daysCount);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
