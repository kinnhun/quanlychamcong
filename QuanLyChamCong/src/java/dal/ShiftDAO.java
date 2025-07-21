/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Departments;
import model.LocationDepartments;
import model.Locations;
import model.Shift;
import java.util.*;
import model.UserShift;
import model.Users;
import java.sql.Date;
import java.sql.Types;
import model.ShiftChangeRequest;

public class ShiftDAO extends DBContext {

    public List<Shift> getAllShift() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT * FROM shifts ORDER BY start_time";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Shift s = new Shift();
                s.setShiftId(rs.getInt("shift_id"));
                s.setShiftName(rs.getString("shift_name"));
                s.setStartTime(rs.getTime("start_time"));
                s.setEndTime(rs.getTime("end_time"));
                s.setDescription(rs.getString("description"));
                s.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Shift getById(int id) {
        String sql = "SELECT * FROM shifts WHERE shift_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Shift s = new Shift();
                s.setShiftId(rs.getInt("shift_id"));
                s.setShiftName(rs.getString("shift_name"));
                s.setStartTime(rs.getTime("start_time"));
                s.setEndTime(rs.getTime("end_time"));
                s.setDescription(rs.getString("description"));
                s.setCreatedAt(rs.getTimestamp("created_at"));
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Shift s) {
        String sql = "INSERT INTO shifts (shift_name, start_time, end_time, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getShiftName());
            ps.setTime(2, s.getStartTime());
            ps.setTime(3, s.getEndTime());
            ps.setString(4, s.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Shift s) {
        String sql = "UPDATE shifts SET shift_name = ?, start_time = ?, end_time = ?, description = ? WHERE shift_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getShiftName());
            ps.setTime(2, s.getStartTime());
            ps.setTime(3, s.getEndTime());
            ps.setString(4, s.getDescription());
            ps.setInt(5, s.getShiftId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM shifts WHERE shift_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateShift(Shift shift) {
        String sql = "UPDATE shifts SET shift_name = ?, start_time = ?, end_time = ?, description = ? WHERE shift_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, shift.getShiftName());
            ps.setTime(2, shift.getStartTime());
            ps.setTime(3, shift.getEndTime());
            ps.setString(4, shift.getDescription());
            ps.setInt(5, shift.getShiftId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Shift getShiftById(int id) {
        String sql = "SELECT * FROM shifts WHERE shift_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Shift s = new Shift();
                s.setShiftId(rs.getInt("shift_id"));
                s.setShiftName(rs.getString("shift_name"));
                s.setStartTime(rs.getTime("start_time"));
                s.setEndTime(rs.getTime("end_time"));
                s.setDescription(rs.getString("description"));
                s.setCreatedAt(rs.getTimestamp("created_at"));
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserShift> getUserShiftsByManager(int managerId) {
        List<UserShift> list = new ArrayList<>();
        String sql = """
        SELECT us.*, u.full_name, u.username, s.shift_name, s.start_time, s.end_time,
               l.name AS location_name, d.department_name
        FROM user_shifts us
        JOIN users u ON us.user_id = u.user_id
        JOIN shifts s ON us.shift_id = s.shift_id
        LEFT JOIN locations l ON us.location_id = l.location_id
        LEFT JOIN departments d ON us.department_id = d.department_id
        -- Chỉ lấy những nhân viên mà manager này quản lý (qua user_locations hoặc tuỳ cách bạn quy ước)
        WHERE EXISTS (
            SELECT 1 FROM user_locations ul
            WHERE ul.user_id = us.user_id
              AND EXISTS (
                  SELECT 1 FROM user_locations mul
                  WHERE mul.user_id = ?
                  AND mul.location_id = ul.location_id
              )
        )
        ORDER BY us.date DESC, u.full_name
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserShift us = new UserShift();

                    us.setId(rs.getInt("id"));
                    us.setDate(rs.getDate("date"));
                    us.setNote(rs.getString("note"));

                    // User
                    Users user = new Users();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setUsername(rs.getString("username"));
                    us.setUser(user);

                    // Shift
                    Shift shift = new Shift();
                    shift.setShiftId(rs.getInt("shift_id"));
                    shift.setShiftName(rs.getString("shift_name"));
                    shift.setStartTime(rs.getTime("start_time"));
                    shift.setEndTime(rs.getTime("end_time"));
                    us.setShift(shift);

                    // Location
                    Locations loc = null;
                    int locId = rs.getInt("location_id");
                    if (!rs.wasNull()) {
                        loc = new Locations();
                        loc.setId(locId);
                        loc.setName(rs.getString("location_name"));
                    }
                    us.setLocation(loc);

                    // Department
                    Departments dept = null;
                    int deptId = rs.getInt("department_id");
                    if (!rs.wasNull()) {
                        dept = new Departments();
                        dept.setDepartmentId(deptId);
                        dept.setDepartmentName(rs.getString("department_name"));
                    }
                    us.setDepartment(dept);

                    list.add(us);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertUserShift(int userId, int shiftId, Date date, Integer locationId, Integer departmentId, int assignedBy, String note) {
        String sql = "INSERT INTO user_shifts (user_id, shift_id, date, location_id, department_id, assigned_by, note) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, shiftId);
            ps.setDate(3, date);
            if (locationId != null) {
                ps.setInt(4, locationId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            if (departmentId != null) {
                ps.setInt(5, departmentId);
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.setInt(6, assignedBy);
            ps.setString(7, note);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Users> getEmployeesByManager(int managerId) {
        List<Users> list = new ArrayList<>();
        // Chỉ lấy nhân viên cùng location với manager này (loại luôn chính manager khỏi kết quả)
        String sql = """
        SELECT DISTINCT u.user_id, u.full_name, u.username, u.email, u.role
        FROM users u
        JOIN user_locations ul ON u.user_id = ul.user_id
        WHERE ul.location_id IN (
            SELECT location_id FROM user_locations WHERE user_id = ?
        )
        AND u.user_id <> ?
        AND u.role = 'employee'
        ORDER BY u.full_name
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, managerId);
            ps.setInt(2, managerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Users user = new Users();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    list.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
// Kiểm tra trùng exact ca

    public boolean isUserShiftDuplicate(int userId, int shiftId, Date date, Integer locationId, Integer departmentId) {
        String sql = "SELECT COUNT(*) FROM user_shifts WHERE user_id = ? AND shift_id = ? AND date = ?"
                + (locationId != null ? " AND location_id = ?" : " AND location_id IS NULL")
                + (departmentId != null ? " AND department_id = ?" : " AND department_id IS NULL");

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setInt(idx++, userId);
            ps.setInt(idx++, shiftId);
            ps.setDate(idx++, date);
            if (locationId != null) {
                ps.setInt(idx++, locationId);
            }
            if (departmentId != null) {
                ps.setInt(idx++, departmentId);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

// Kiểm tra đã có ca nào trong ngày
    public boolean isUserAssignedOnShiftDate(int userId, Date date, Integer locationId, Integer departmentId) {
        String sql = "SELECT COUNT(*) FROM user_shifts WHERE user_id = ? AND date = ?"
                + (locationId != null ? " AND location_id = ?" : " AND location_id IS NULL")
                + (departmentId != null ? " AND department_id = ?" : " AND department_id IS NULL");
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            ps.setInt(idx++, userId);
            ps.setDate(idx++, date);
            if (locationId != null) {
                ps.setInt(idx++, locationId);
            }
            if (departmentId != null) {
                ps.setInt(idx++, departmentId);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserShift> getUserShiftsPaging(int managerId, String empId, String shiftId, String departmentId, String locationId, String date, String week, String month, int page, int pageSize) {
        List<UserShift> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT us.*, u.full_name, u.username, s.shift_name, s.start_time, s.end_time, "
                + "l.name AS location_name, d.department_name, assign.full_name AS assigned_by, us.assigned_at "
                + "FROM user_shifts us "
                + "JOIN users u ON us.user_id = u.user_id "
                + "JOIN shifts s ON us.shift_id = s.shift_id "
                + "LEFT JOIN locations l ON us.location_id = l.location_id "
                + "LEFT JOIN departments d ON us.department_id = d.department_id "
                + "LEFT JOIN users assign ON us.assigned_by = assign.user_id "
                + "WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();

        // Chỉ lấy nhân viên manager này quản lý (theo location)
        sql.append("AND EXISTS (SELECT 1 FROM user_locations ul WHERE ul.user_id = us.user_id AND EXISTS (SELECT 1 FROM user_locations mul WHERE mul.user_id = ? AND mul.location_id = ul.location_id)) ");
        params.add(managerId);

        if (empId != null && !empId.isEmpty()) {
            sql.append("AND u.user_id = ? ");
            params.add(Integer.parseInt(empId));
        }
        if (shiftId != null && !shiftId.isEmpty()) {
            sql.append("AND s.shift_id = ? ");
            params.add(Integer.parseInt(shiftId));
        }
        if (departmentId != null && !departmentId.isEmpty()) {
            sql.append("AND d.department_id = ? ");
            params.add(Integer.parseInt(departmentId));
        }
        if (locationId != null && !locationId.isEmpty()) {
            sql.append("AND l.location_id = ? ");
            params.add(Integer.parseInt(locationId));
        }
        if (date != null && !date.isEmpty()) {
            sql.append("AND us.date = ? ");
            params.add(Date.valueOf(date));
        }
        if (week != null && !week.isEmpty()) {
            sql.append("AND DATEPART(ISO_WEEK, us.date) = ? AND YEAR(us.date) = ? ");
            String[] arr = week.split("-");
            if (arr.length == 2) {
                params.add(Integer.parseInt(arr[1]));
                params.add(Integer.parseInt(arr[0]));
            }
        }
        if (month != null && !month.isEmpty()) {
            sql.append("AND FORMAT(us.date, 'yyyy-MM') = ? ");
            params.add(month);
        }

        sql.append("ORDER BY us.date DESC, u.full_name ");
        sql.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((page - 1) * pageSize);
        params.add(pageSize);

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserShift us = new UserShift();

                us.setId(rs.getInt("id"));
                us.setDate(rs.getDate("date"));
                us.setNote(rs.getString("note"));
                us.setAssignedAt(rs.getTimestamp("assigned_at"));

                // User
                Users user = new Users();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setUsername(rs.getString("username"));
                us.setUser(user);

                // Shift
                Shift shift = new Shift();
                shift.setShiftId(rs.getInt("shift_id"));
                shift.setShiftName(rs.getString("shift_name"));
                shift.setStartTime(rs.getTime("start_time"));
                shift.setEndTime(rs.getTime("end_time"));
                us.setShift(shift);

                // Location
                Locations loc = null;
                int locId = rs.getInt("location_id");
                if (!rs.wasNull()) {
                    loc = new Locations();
                    loc.setId(locId);
                    loc.setName(rs.getString("location_name"));
                }
                us.setLocation(loc);

                // Department
                Departments dept = null;
                int deptId = rs.getInt("department_id");
                if (!rs.wasNull()) {
                    dept = new Departments();
                    dept.setDepartmentId(deptId);
                    dept.setDepartmentName(rs.getString("department_name"));
                }
                us.setDepartment(dept);

                // Người phân ca
                Users assignedBy = null;
                String assignedByName = rs.getString("assigned_by");
                int assignedById = rs.getInt("assigned_by");
                if (assignedByName != null) {
                    assignedBy = new Users();
                    assignedBy.setUserId(assignedById);
                    assignedBy.setFullName(assignedByName);
                }
                us.setAssignedBy(assignedBy);

                list.add(us);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countUserShifts(int managerId, String empId, String shiftId, String departmentId, String locationId, String date, String week, String month) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) "
                + "FROM user_shifts us "
                + "JOIN users u ON us.user_id = u.user_id "
                + "JOIN shifts s ON us.shift_id = s.shift_id "
                + "LEFT JOIN locations l ON us.location_id = l.location_id "
                + "LEFT JOIN departments d ON us.department_id = d.department_id "
                + "WHERE 1=1 "
        );
        List<Object> params = new ArrayList<>();

        sql.append("AND EXISTS (SELECT 1 FROM user_locations ul WHERE ul.user_id = us.user_id AND EXISTS (SELECT 1 FROM user_locations mul WHERE mul.user_id = ? AND mul.location_id = ul.location_id)) ");
        params.add(managerId);

        if (empId != null && !empId.isEmpty()) {
            sql.append("AND u.user_id = ? ");
            params.add(Integer.parseInt(empId));
        }
        if (shiftId != null && !shiftId.isEmpty()) {
            sql.append("AND s.shift_id = ? ");
            params.add(Integer.parseInt(shiftId));
        }
        if (departmentId != null && !departmentId.isEmpty()) {
            sql.append("AND d.department_id = ? ");
            params.add(Integer.parseInt(departmentId));
        }
        if (locationId != null && !locationId.isEmpty()) {
            sql.append("AND l.location_id = ? ");
            params.add(Integer.parseInt(locationId));
        }
        if (date != null && !date.isEmpty()) {
            sql.append("AND us.date = ? ");
            params.add(Date.valueOf(date));
        }
        if (week != null && !week.isEmpty()) {
            sql.append("AND DATEPART(ISO_WEEK, us.date) = ? AND YEAR(us.date) = ? ");
            String[] arr = week.split("-");
            if (arr.length == 2) {
                params.add(Integer.parseInt(arr[1]));
                params.add(Integer.parseInt(arr[0]));
            }
        }
        if (month != null && !month.isEmpty()) {
            sql.append("AND FORMAT(us.date, 'yyyy-MM') = ? ");
            params.add(month);
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

    public UserShift getUserShiftById(int userShiftId) {
        String sql = """
        SELECT * FROM user_shifts WHERE id = ?
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userShiftId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserShift us = new UserShift();

                // Thiết lập các thuộc tính cơ bản của UserShift
                us.setId(rs.getInt("id"));
                us.setDate(rs.getDate("date"));
                us.setNote(rs.getString("note"));
                us.setAssignedAt(rs.getTimestamp("assigned_at"));

                // User
                UserDAO udao = new UserDAO();
                Users user = udao.getUserById(rs.getInt("user_id"));
                us.setUser(user);

                // Shift
                ShiftDAO sdao = new ShiftDAO();
                Shift shift = sdao.getById(rs.getInt("shift_id"));
                us.setShift(shift);

                // Location
                LocationDAO ldao = new LocationDAO();
                Locations location = ldao.getLocationById(rs.getInt("location_id"));
                us.setLocation(location);

                // Department
                Departments dept = ldao.getDepartmentById(rs.getInt("department_id"));
                us.setDepartment(dept);

                // Assigned By
                Users assignedBy = udao.getUserById(rs.getInt("assigned_by"));
                us.setAssignedBy(assignedBy);

                return us;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUserShift(int userShiftId, int userId, int shiftId, Date date, Integer locationId, Integer departmentId, int assignedBy, String note) {
        String sql = """
        UPDATE user_shifts 
        SET user_id = ?, shift_id = ?, date = ?, location_id = ?, department_id = ?, assigned_by = ?, note = ?, assigned_at = GETDATE()
        WHERE id = ?
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Gán các tham số cho câu lệnh SQL
            ps.setInt(1, userId);
            ps.setInt(2, shiftId);
            ps.setDate(3, date);

            // Xử lý location_id (có thể null)
            if (locationId != null) {
                ps.setInt(4, locationId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            // Xử lý department_id (có thể null)
            if (departmentId != null) {
                ps.setInt(5, departmentId);
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setInt(6, assignedBy);

            // Xử lý note (có thể null)
            if (note != null && !note.isEmpty()) {
                ps.setString(7, note);
            } else {
                ps.setNull(7, Types.NVARCHAR);
            }

            ps.setInt(8, userShiftId);

            // Thực thi cập nhật và trả về kết quả
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserShift> getUserShiftsByEmployee(int userId, String week, String month) {
        List<UserShift> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT us.*, u.full_name, u.username, s.shift_name, s.start_time, s.end_time, "
                + "l.name AS location_name, d.department_name, assign.full_name AS assigned_by, us.assigned_at "
                + "FROM user_shifts us "
                + "JOIN users u ON us.user_id = u.user_id "
                + "JOIN shifts s ON us.shift_id = s.shift_id "
                + "LEFT JOIN locations l ON us.location_id = l.location_id "
                + "LEFT JOIN departments d ON us.department_id = d.department_id "
                + "LEFT JOIN users assign ON us.assigned_by = assign.user_id "
                + "WHERE us.user_id = ? "
        );
        List<Object> params = new ArrayList<>();
        params.add(userId);

        sql.append("ORDER BY us.date DESC, s.start_time");

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserShift us = new UserShift();

                us.setId(rs.getInt("id"));
                us.setDate(rs.getDate("date"));
                us.setNote(rs.getString("note"));
                us.setAssignedAt(rs.getTimestamp("assigned_at"));

                // User
                Users user = new Users();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setUsername(rs.getString("username"));
                us.setUser(user);

                // Shift
                Shift shift = new Shift();
                shift.setShiftId(rs.getInt("shift_id"));
                shift.setShiftName(rs.getString("shift_name"));
                shift.setStartTime(rs.getTime("start_time"));
                shift.setEndTime(rs.getTime("end_time"));
                us.setShift(shift);

                // Location
                Locations loc = null;
                int locId = rs.getInt("location_id");
                if (!rs.wasNull()) {
                    loc = new Locations();
                    loc.setId(locId);
                    loc.setName(rs.getString("location_name"));
                }
                us.setLocation(loc);

                // Department
                Departments dept = null;
                int deptId = rs.getInt("department_id");
                if (!rs.wasNull()) {
                    dept = new Departments();
                    dept.setDepartmentId(deptId);
                    dept.setDepartmentName(rs.getString("department_name"));
                }
                us.setDepartment(dept);

                // Người phân ca
                Users assignedBy = null;
                String assignedByName = rs.getString("assigned_by"); // Cột full_name từ bảng assign
                int assignedById = rs.getInt("assigned_by");
                if (assignedByName != null) {
                    assignedBy = new Users();
                    assignedBy.setUserId(assignedById);
                    assignedBy.setFullName(assignedByName);
                }
                us.setAssignedBy(assignedBy);

                list.add(us);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalEmployees() {
        String sql = "SELECT COUNT(*) FROM users WHERE role = 'employee'";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<String, Integer> getShiftsPerMonth(String year) {
        Map<String, Integer> accountsPerMonth = new HashMap<>();
        String sql = "SELECT FORMAT(created_at, 'yyyy-MM') AS month, COUNT(*) AS account_count "
                + "FROM users "
                + "WHERE YEAR(created_at) = ? "
                + "AND role = 'employee' "
                + "AND status = 'active' "
                + "GROUP BY FORMAT(created_at, 'yyyy-MM')";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accountsPerMonth.put(rs.getString("month"), rs.getInt("account_count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Đảm bảo có dữ liệu cho 12 tháng, nếu không có thì trả về 0
        for (int i = 1; i <= 12; i++) {
            String month = String.format("%s-%02d", year, i);
            accountsPerMonth.putIfAbsent(month, 0);
        }
        return accountsPerMonth;
    }

    public int getTodayWorkingEmployees() {
        String sql = "SELECT COUNT(DISTINCT user_id) AS employee_count "
                + "FROM user_shifts "
                + "WHERE CAST(date AS DATE) = CAST(GETDATE() AS DATE)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("employee_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int[] getEmployeesPerDayInWeek(String week) {
        int[] employeesPerDay = new int[6]; // Thứ 2 đến Thứ 7
        String sql = "SELECT DATEPART(WEEKDAY, date) AS day_of_week, COUNT(DISTINCT user_id) AS employee_count "
                + "FROM user_shifts "
                + "WHERE DATEPART(ISO_WEEK, date) = ? AND YEAR(date) = ? "
                + "GROUP BY DATEPART(WEEKDAY, date)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            String[] arr = week.split("-W");
            if (arr.length == 2) {
                ps.setInt(1, Integer.parseInt(arr[1]));
                ps.setString(2, arr[0]);
            } else {
                return employeesPerDay; // Trả về mảng 0 nếu định dạng tuần không hợp lệ
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int dayOfWeek = rs.getInt("day_of_week"); // 1=CN, 2=Th2, ..., 7=Th7
                int count = rs.getInt("employee_count");
                if (dayOfWeek >= 2 && dayOfWeek <= 7) { // Thứ 2 đến Thứ 7
                    employeesPerDay[dayOfWeek - 2] = count;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeesPerDay;
    }

    // Lấy số lần đi muộn theo tháng trong năm
    public Map<String, Integer> getLateArrivalStats(String year) {
        Map<String, Integer> lateArrivalStats = new HashMap<>();
        String sql = """
            SELECT FORMAT(date, 'yyyy-MM') AS month, COUNT(*) AS late_count
            FROM user_shifts
            WHERE YEAR(date) = ?
            AND is_late = 1
            GROUP BY FORMAT(date, 'yyyy-MM')
        """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lateArrivalStats.put(rs.getString("month"), rs.getInt("late_count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Đảm bảo dữ liệu cho 12 tháng, nếu không có thì trả về 0
        for (int i = 1; i <= 12; i++) {
            String month = String.format("%s-%02d", year, i);
            lateArrivalStats.putIfAbsent(month, 0);
        }
        return lateArrivalStats;
    }

    public Map<String, Integer> getBranchAttendance(String year) {
        Map<String, Integer> branchAttendance = new HashMap<>();
        String sql = """
            SELECT l.name AS location_name, COUNT(*) AS attendance_count
            FROM user_shifts us
            JOIN locations l ON us.location_id = l.location_id
            WHERE YEAR(us.date) = ?
            GROUP BY l.name
        """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                branchAttendance.put(rs.getString("location_name"), rs.getInt("attendance_count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Thêm dữ liệu giả lập nếu không có chi nhánh (tuỳ chỉnh theo nhu cầu)
        if (branchAttendance.isEmpty()) {
            branchAttendance.put("Chi nhánh Hà Nội", 0);
            branchAttendance.put("Chi nhánh TP.HCM", 0);
            branchAttendance.put("Chi nhánh Đà Nẵng", 0);
        }
        return branchAttendance;
    }

    public Map<String, Double> getAverageLeaveDaysByDepartment(String year) {
        Map<String, Double> avgLeaveDaysByDepartment = new HashMap<>();
        String sql = """
            SELECT d.department_name, 
                   AVG(CASE WHEN us.is_permitted_leave = 1 THEN 1.0 
                            WHEN us.is_unpermitted_leave = 1 THEN 1.0 
                            ELSE 0 END) AS avg_leave_days
            FROM user_shifts us
            JOIN departments d ON us.department_id = d.department_id
            WHERE YEAR(us.date) = ?
            GROUP BY d.department_name
        """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                avgLeaveDaysByDepartment.put(rs.getString("department_name"), rs.getDouble("avg_leave_days"));
            }
            // Thêm các giá trị tổng hợp cho widget
            avgLeaveDaysByDepartment.put("totalPermitted", getTotalLeaveDays(year, true));
            avgLeaveDaysByDepartment.put("totalUnpermitted", getTotalLeaveDays(year, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Đảm bảo dữ liệu cho các phòng ban giả lập nếu cần
        if (avgLeaveDaysByDepartment.isEmpty()) {
            avgLeaveDaysByDepartment.put("Phòng Kỹ thuật", 0.0);
            avgLeaveDaysByDepartment.put("Phòng Nhân sự", 0.0);
            avgLeaveDaysByDepartment.put("Phòng Kinh doanh", 0.0);
            avgLeaveDaysByDepartment.put("totalPermitted", 0.0);
            avgLeaveDaysByDepartment.put("totalUnpermitted", 0.0);
        }
        return avgLeaveDaysByDepartment;
    }

    private double getTotalLeaveDays(String year, boolean isPermitted) {
        String sql = """
            SELECT COUNT(*) AS leave_count
            FROM user_shifts
            WHERE YEAR(date) = ?
            AND """ + (isPermitted ? "is_permitted_leave = 1" : "is_unpermitted_leave = 1");
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("leave_count") / 1.0; // Chuyển sang double
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // So sánh số ngày công giữa tháng hiện tại và tháng trước
    public Map<String, Integer> getAttendanceComparison(String currentMonth, String previousMonth) {
        Map<String, Integer> attendanceComparison = new HashMap<>();
        String sql = """
            SELECT FORMAT(date, 'yyyy-MM') AS month, COUNT(*) AS work_days
            FROM user_shifts
            WHERE FORMAT(date, 'yyyy-MM') IN (?, ?)
            AND (is_permitted_leave IS NULL OR is_permitted_leave = 0)
            AND (is_unpermitted_leave IS NULL OR is_unpermitted_leave = 0)
            GROUP BY FORMAT(date, 'yyyy-MM')
        """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, previousMonth);
            ps.setString(2, currentMonth);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                attendanceComparison.put(rs.getString("month"), rs.getInt("work_days"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Đảm bảo dữ liệu cho cả hai tháng
        attendanceComparison.putIfAbsent(currentMonth, 0);
        attendanceComparison.putIfAbsent(previousMonth, 0);
        return attendanceComparison;
    }

    public List<String> getAllDepartmentNames() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT department_name FROM Departments";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("department_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getLateAndLeaveByDepartment() {
        List<Integer> result = new ArrayList<>();
        String sql = """
        SELECT d.department_id,
               SUM(CASE WHEN a.checkin_time > '08:15:00' THEN 1 ELSE 0 END) AS late_count,
               SUM(
                   CASE WHEN lr.status = 'approved' THEN 1 ELSE 0 END
               ) AS leave_count
        FROM departments d
        JOIN user_locations ul ON ul.department_id = d.department_id
        JOIN users u ON u.user_id = ul.user_id
        LEFT JOIN attendance a ON a.user_id = u.user_id
        LEFT JOIN leave_requests lr ON lr.user_id = u.user_id
        GROUP BY d.department_id
        ORDER BY d.department_id
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int late = rs.getInt("late_count");
                int leave = rs.getInt("leave_count");
                result.add(late + leave);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getLateCount(String departmentId) {
        String sql = """
        SELECT COUNT(*) FROM attendance a
        JOIN users u ON a.user_id = u.user_id
        JOIN user_locations ul ON ul.user_id = u.user_id
        WHERE ul.department_id = ? AND a.checkin_time > '08:15:00'
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getWorkingDays(String departmentId) {
        String sql = """
        SELECT COUNT(*) FROM attendance a
        JOIN users u ON a.user_id = u.user_id
        JOIN user_locations ul ON ul.user_id = u.user_id
        WHERE ul.department_id = ?
          AND a.checkin_time IS NOT NULL
          AND a.checkout_time IS NOT NULL
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getLeaveCount(String departmentId) {
        String sql = """
        SELECT COUNT(*) FROM leave_requests lr
        JOIN users u ON lr.user_id = u.user_id
        JOIN user_locations ul ON ul.user_id = u.user_id
        WHERE ul.department_id = ? AND lr.status = 'approved'
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<String, Integer> getLateCountsByMonth() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = """
        SELECT FORMAT([date], 'yyyy-MM') AS month, COUNT(*) AS totalLate
        FROM attendance
        WHERE checkin_time > '08:15:00'
        GROUP BY FORMAT([date], 'yyyy-MM')
        ORDER BY month
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("month"), rs.getInt("totalLate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Integer> getLeaveCountsByMonth() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = """
        SELECT FORMAT(start_date, 'yyyy-MM') AS month, COUNT(*) AS totalLeave
        FROM leave_requests
        WHERE status = 'approved'
        GROUP BY FORMAT(start_date, 'yyyy-MM')
        ORDER BY month
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("month"), rs.getInt("totalLeave"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Integer> getWorkingDaysByMonth() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = """
        SELECT FORMAT([date], 'yyyy-MM') AS month, COUNT(DISTINCT [date]) AS workDays
        FROM attendance
        WHERE checkin_time IS NOT NULL AND checkout_time IS NOT NULL
        GROUP BY FORMAT([date], 'yyyy-MM')
        ORDER BY month
    """;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("month"), rs.getInt("workDays"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean insertShiftChangeRequest(ShiftChangeRequest request) {
        String sql = """
        INSERT INTO shift_change_requests 
        (user_id, from_shift_id, to_shift_id, date, reason, status)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, request.getUserId().getUserId());
            ps.setInt(2, request.getFromShiftId().getShiftId());
            ps.setInt(3, request.getToShiftId().getShiftId());
            ps.setDate(4, request.getDate());
            ps.setString(5, request.getReason());
            ps.setString(6, request.getStatus());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Shift> getAllShifts() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT shift_id, shift_name, start_time, end_time, description, created_at FROM shifts";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Shift shift = new Shift();
                shift.setShiftId(rs.getInt("shift_id"));
                shift.setShiftName(rs.getString("shift_name"));
                shift.setStartTime(rs.getTime("start_time"));
                shift.setEndTime(rs.getTime("end_time"));
                shift.setDescription(rs.getString("description"));
                shift.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(shift);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ShiftChangeRequest> getShiftChangeRequestsByUserId(int userId) {
        List<ShiftChangeRequest> list = new ArrayList<>();
        String sql = """
        SELECT scr.*, 
               fs.shift_name AS from_name, fs.start_time AS from_start, fs.end_time AS from_end,
               ts.shift_name AS to_name, ts.start_time AS to_start, ts.end_time AS to_end
        FROM shift_change_requests scr
        JOIN shifts fs ON scr.from_shift_id = fs.shift_id
        JOIN shifts ts ON scr.to_shift_id = ts.shift_id
        WHERE scr.user_id = ?
        ORDER BY scr.created_at DESC
    """;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ShiftChangeRequest req = new ShiftChangeRequest();
                req.setRequestId(rs.getInt("request_id"));
                req.setDate(rs.getDate("date"));
                req.setReason(rs.getString("reason"));
                req.setStatus(rs.getString("status"));
                req.setCreatedAt(rs.getTimestamp("created_at"));

                Shift fromShift = new Shift();
                fromShift.setShiftId(rs.getInt("from_shift_id"));
                fromShift.setShiftName(rs.getString("from_name"));
                fromShift.setStartTime(rs.getTime("from_start"));
                fromShift.setEndTime(rs.getTime("from_end"));
                req.setFromShiftId(fromShift);

                Shift toShift = new Shift();
                toShift.setShiftId(rs.getInt("to_shift_id"));
                toShift.setShiftName(rs.getString("to_name"));
                toShift.setStartTime(rs.getTime("to_start"));
                toShift.setEndTime(rs.getTime("to_end"));
                req.setToShiftId(toShift);

                list.add(req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
