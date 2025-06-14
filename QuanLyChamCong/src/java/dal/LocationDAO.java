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

public class LocationDAO extends DBContext {

    public List<Locations> getAll() {
        List<Locations> list = new ArrayList<>();
        String sql = "SELECT * FROM locations";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Locations l = new Locations();
                l.setId(rs.getInt("location_id"));
                l.setName(rs.getString("name"));
                l.setAddress(rs.getString("address"));
                l.setIsActive(rs.getBoolean("is_active"));
                l.setIpMap(rs.getString("ip_map"));
                list.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(Locations l) {
        String sql = "INSERT INTO locations (name, address, ip_map, is_active) VALUES (?, ?, ?, 1)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getName());
            ps.setString(2, l.getAddress());
            ps.setString(3, l.getIpMap());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM locations WHERE location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Locations getById(int id) {
        String sql = "SELECT * FROM locations WHERE location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Locations l = new Locations();
                l.setId(rs.getInt("location_id"));
                l.setName(rs.getString("name"));
                l.setAddress(rs.getString("address"));
                l.setIsActive(rs.getBoolean("is_active"));
                l.setIpMap(rs.getString("ip_map"));
                return l;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Locations l) {
        String sql = "UPDATE locations SET name = ?, address = ?, ip_map = ?, is_active = ? WHERE location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getName());
            ps.setString(2, l.getAddress());
            ps.setString(3, l.getIpMap());
            ps.setBoolean(4, l.isIsActive());
            ps.setInt(5, l.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean toggleActiveStatus(int id, boolean isActive) {
        String sql = "UPDATE locations SET is_active = ? WHERE location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isActive);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Locations getLocationById(int id) {
        String sql = "SELECT * FROM locations WHERE location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Locations l = new Locations();
                l.setId(rs.getInt("location_id"));
                l.setName(rs.getString("name"));
                l.setAddress(rs.getString("address"));
                return l;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LocationDepartments> getAllLocationDepartments() {
        List<LocationDepartments> list = new ArrayList<>();
        String sql = "SELECT location_id, department_id FROM location_departments";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocationDepartments ld = new LocationDepartments();
                LocationDAO ldao = new LocationDAO();
                Locations location = ldao.getById(rs.getInt("location_id"));
                ld.setLocationId(location);

                Departments department = ldao.getDepartmentById(rs.getInt("department_id"));
                ld.setDepartmentId(department);
                list.add(ld);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Locations> getAllLocations() {
        List<Locations> list = new ArrayList<>();
        String sql = "SELECT * FROM locations";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Locations l = new Locations();
                l.setId(rs.getInt("location_id"));
                l.setName(rs.getString("name"));
                l.setAddress(rs.getString("address"));
                l.setIsActive(rs.getBoolean("is_active"));
                l.setIpMap(rs.getString("ip_map"));
                list.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Departments> getAllDepartments() {
        List<Departments> list = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Departments d = new Departments();
                d.setDepartmentId(rs.getInt("department_id"));
                d.setDepartmentName(rs.getString("department_name"));
                d.setDepartmentCode(rs.getString("department_code"));
                d.setDescription(rs.getString("description"));
                d.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Departments getDepartmentById(int departmentId) {
        String sql = "SELECT * FROM departments WHERE department_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Departments d = new Departments();
                d.setDepartmentId(rs.getInt("department_id"));
                d.setDepartmentName(rs.getString("department_name"));
                d.setDepartmentCode(rs.getString("department_code"));
                d.setDescription(rs.getString("description"));
                d.setCreatedAt(rs.getTimestamp("created_at"));
                return d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertDepartments(Departments d) {
        String sql = "INSERT INTO departments (department_name, department_code, description, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getDepartmentName());
            ps.setString(2, d.getDepartmentCode());
            ps.setString(3, d.getDescription());
            ps.setTimestamp(4, new java.sql.Timestamp(d.getCreatedAt().getTime()));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateDepartment(Departments d) {
        String sql = "UPDATE departments SET department_name = ?, department_code = ?, description = ? WHERE department_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getDepartmentName());
            ps.setString(2, d.getDepartmentCode());
            ps.setString(3, d.getDescription());
            ps.setInt(4, d.getDepartmentId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDepartmentById(int id) {
        String sql = "DELETE FROM departments WHERE department_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Departments> getDepartmentsByLocation(int locationId) {
        List<Departments> list = new ArrayList<>();
        String sql = "SELECT d.* FROM location_departments ld "
                + "JOIN departments d ON ld.department_id = d.department_id "
                + "WHERE ld.location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Departments d = new Departments();
                d.setDepartmentId(rs.getInt("department_id"));
                d.setDepartmentName(rs.getString("department_name"));
                d.setDepartmentCode(rs.getString("department_code"));
                d.setDescription(rs.getString("description"));
                d.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getDepartmentIdsByLocation(int locationId) {
        List<Integer> departmentIds = new ArrayList<>();
        String sql = "SELECT department_id FROM location_departments WHERE location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                departmentIds.add(rs.getInt("department_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departmentIds;
    }

    public void deleteDepartmentsFromLocation(int locationId) {
        String sql = "DELETE FROM location_departments WHERE location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignDepartmentToLocation(int locationId, int depId) {
        String sql = "INSERT INTO location_departments (location_id, department_id) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            ps.setInt(2, depId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Departments> getDepartmentsByLocationId(int locId) {
        List<Departments> list = new ArrayList<>();
        String sql = "SELECT d.* FROM location_departments ld "
                + "JOIN departments d ON ld.department_id = d.department_id "
                + "WHERE ld.location_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, locId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Departments d = new Departments();
                d.setDepartmentId(rs.getInt("department_id"));
                d.setDepartmentName(rs.getString("department_name"));
                d.setDepartmentCode(rs.getString("department_code"));
                d.setDescription(rs.getString("description"));
                d.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        LocationDAO ldao = new LocationDAO();
        List<Departments> list = ldao.getDepartmentsByLocation(1);
        for (Departments departments : list) {
            System.out.println(departments.toString());
        }
    }
}
