package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.HolidayDate;

public class HolidayDateDAO extends DBContext {

    // Lấy danh sách tất cả ngày nghỉ lễ
    public List<HolidayDate> getAllHolidayDates() {
        List<HolidayDate> list = new ArrayList<>();
        String sql = "SELECT holiday_id, holiday_name, holiday_date, year, created_at FROM holiday_dates ORDER BY holiday_date ASC";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HolidayDate hd = new HolidayDate(
                        rs.getInt("holiday_id"),
                        rs.getString("holiday_name"),
                        rs.getDate("holiday_date"),
                        rs.getInt("year"),
                        rs.getDate("created_at")
                );
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm một ngày nghỉ lễ mới
    public boolean addHolidayDate(HolidayDate hd) {
        String sql = "INSERT INTO holiday_dates (holiday_name, holiday_date, year) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hd.getHolidayName());
            ps.setDate(2, hd.getHolidayDate());
            ps.setInt(3, hd.getYear());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một ngày nghỉ lễ theo id
    public boolean deleteHolidayDate(int id) {
        String sql = "DELETE FROM holiday_dates WHERE holiday_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HolidayDate> getHolidayDatesPaging(String year, String month, String keyword, int page, int pageSize, int[] totalRows) {
        List<HolidayDate> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT holiday_id, holiday_name, holiday_date, year, created_at FROM holiday_dates WHERE 1=1");

        // Filter by year
        if (year != null && !year.isEmpty()) {
            sql.append(" AND year = ?");
            params.add(Integer.parseInt(year));
        }
        // Filter by month
        if (month != null && !month.isEmpty()) {
            sql.append(" AND MONTH(holiday_date) = ?");
            params.add(Integer.parseInt(month));
        }
        // Search by keyword (name)
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND holiday_name LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }

        // Tổng số dòng
        String countSql = "SELECT COUNT(*) FROM holiday_dates WHERE 1=1"
                + (year != null && !year.isEmpty() ? " AND year = ?" : "")
                + (month != null && !month.isEmpty() ? " AND MONTH(holiday_date) = ?" : "")
                + (keyword != null && !keyword.trim().isEmpty() ? " AND holiday_name LIKE ?" : "");

        // Phân trang: OFFSET/FETCH (SQL Server 2012+)
        sql.append(" ORDER BY holiday_date ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection conn = getConnection()) {
            // Đếm tổng số dòng
            try (PreparedStatement psCount = conn.prepareStatement(countSql)) {
                int idx = 1;
                if (year != null && !year.isEmpty()) {
                    psCount.setInt(idx++, Integer.parseInt(year));
                }
                if (month != null && !month.isEmpty()) {
                    psCount.setInt(idx++, Integer.parseInt(month));
                }
                if (keyword != null && !keyword.trim().isEmpty()) {
                    psCount.setString(idx++, "%" + keyword.trim() + "%");
                }
                try (ResultSet rs = psCount.executeQuery()) {
                    if (rs.next()) {
                        totalRows[0] = rs.getInt(1);
                    }
                }
            }

            // Lấy dữ liệu phân trang
            try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
                int idx = 1;
                for (Object param : params) {
                    if (param instanceof Integer) {
                        ps.setInt(idx++, (Integer) param);
                    } else {
                        ps.setString(idx++, (String) param);
                    }
                }
                ps.setInt(idx++, (page - 1) * pageSize);
                ps.setInt(idx++, pageSize);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        HolidayDate hd = new HolidayDate(
                                rs.getInt("holiday_id"),
                                rs.getString("holiday_name"),
                                rs.getDate("holiday_date"),
                                rs.getInt("year"),
                                rs.getDate("created_at")
                        );
                        list.add(hd);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
