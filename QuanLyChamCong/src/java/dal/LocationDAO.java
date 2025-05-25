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

}
