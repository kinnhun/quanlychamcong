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
        "SELECT us.*, u.full_name, u.username, s.shift_name, s.start_time, s.end_time, " +
        "l.name AS location_name, d.department_name, assign.full_name AS assigned_by, us.assigned_at " +
        "FROM user_shifts us " +
        "JOIN users u ON us.user_id = u.user_id " +
        "JOIN shifts s ON us.shift_id = s.shift_id " +
        "LEFT JOIN locations l ON us.location_id = l.location_id " +
        "LEFT JOIN departments d ON us.department_id = d.department_id " +
        "LEFT JOIN users assign ON us.assigned_by = assign.user_id " +
        "WHERE 1=1 "
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
        "SELECT COUNT(*) " +
        "FROM user_shifts us " +
        "JOIN users u ON us.user_id = u.user_id " +
        "JOIN shifts s ON us.shift_id = s.shift_id " +
        "LEFT JOIN locations l ON us.location_id = l.location_id " +
        "LEFT JOIN departments d ON us.department_id = d.department_id " +
        "WHERE 1=1 "
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


}
