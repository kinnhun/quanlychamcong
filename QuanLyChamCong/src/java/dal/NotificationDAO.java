package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Notification;
import model.NotificationReceiver;
import model.Users;

public class NotificationDAO extends DBContext {

    public int insertAndGetId(Notification noti) {
        String sql = "INSERT INTO notifications (title, content, image_url, file_url, created_by, created_at, scheduled_time, status) "
                + "OUTPUT INSERTED.notification_id VALUES (?, ?, ?, ?, ?, GETDATE(), ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, noti.getTitle());
            ps.setString(2, noti.getContent());
            ps.setString(3, noti.getImageUrl());
            ps.setString(4, noti.getFileUrl());
            ps.setInt(5, noti.getCreatedBy().getUserId());
            // scheduled_time
            if (noti.getScheduledTime() != null) {
                ps.setTimestamp(6, noti.getScheduledTime());
            } else {
                ps.setNull(6, java.sql.Types.TIMESTAMP);
            }
            ps.setString(7, noti.getStatus());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean insertReceiver(NotificationReceiver receiver) {
        String sql = "INSERT INTO notification_receivers (notification_id, user_id, status, read_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, receiver.getNotificationId());
            ps.setInt(2, receiver.getUserId());
            ps.setString(3, receiver.getStatus());
            if (receiver.getReadAt() != null) {
                ps.setTimestamp(4, receiver.getReadAt());
            } else {
                ps.setNull(4, java.sql.Types.TIMESTAMP);
            }
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Notification> getAllNotifications() {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT n.*, u.full_name AS sender_name FROM notifications n "
                + "LEFT JOIN users u ON n.created_by = u.user_id "
                + "ORDER BY n.created_at DESC";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            UserDAO udao = new UserDAO();
            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setImageUrl(rs.getString("image_url"));
                n.setFileUrl(rs.getString("file_url"));
                n.setCreatedBy(udao.getUserById(rs.getInt("created_by")));
                n.setCreatedAt(rs.getTimestamp("created_at"));
                n.setScheduledTime(rs.getTimestamp("scheduled_time"));
                n.setStatus(rs.getString("status"));
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Đếm tổng số thông báo theo filter
    public int countAllNotifications(String status, String senderId, String searchTitle, String searchContent) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM notifications WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (status != null && !status.isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (senderId != null && !senderId.isEmpty()) {
            sql.append(" AND created_by = ?");
            params.add(Integer.parseInt(senderId));
        }
        if (searchTitle != null && !searchTitle.isEmpty()) {
            sql.append(" AND title LIKE ?");
            params.add("%" + searchTitle + "%");
        }
        if (searchContent != null && !searchContent.isEmpty()) {
            sql.append(" AND content LIKE ?");
            params.add("%" + searchContent + "%");
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

    // Phân trang + filter + sort
    public List<Notification> getNotificationsPaging(String status, String senderId, String searchTitle, String searchContent, String sort, int page, int pageSize) {
        List<Notification> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT n.*, u.full_name AS sender_name FROM notifications n "
                + "LEFT JOIN users u ON n.created_by = u.user_id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (status != null && !status.isEmpty()) {
            sql.append(" AND n.status = ?");
            params.add(status);
        }
        if (senderId != null && !senderId.isEmpty()) {
            sql.append(" AND n.created_by = ?");
            params.add(Integer.parseInt(senderId));
        }
        if (searchTitle != null && !searchTitle.isEmpty()) {
            sql.append(" AND n.title LIKE ?");
            params.add("%" + searchTitle + "%");
        }
        if (searchContent != null && !searchContent.isEmpty()) {
            sql.append(" AND n.content LIKE ?");
            params.add("%" + searchContent + "%");
        }
        sql.append(" ORDER BY n.created_at ");
        sql.append("asc".equalsIgnoreCase(sort) ? "ASC" : "DESC");
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((page - 1) * pageSize);
        params.add(pageSize);

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            ResultSet rs = ps.executeQuery();
            UserDAO udao = new UserDAO();
            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setImageUrl(rs.getString("image_url"));
                n.setFileUrl(rs.getString("file_url"));
                n.setCreatedBy(udao.getUserById(rs.getInt("created_by")));
                n.setCreatedAt(rs.getTimestamp("created_at"));
                n.setScheduledTime(rs.getTimestamp("scheduled_time"));
                n.setStatus(rs.getString("status"));
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy danh sách tất cả người gửi
    public List<Users> getAllSenders() {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT DISTINCT u.user_id, u.full_name FROM notifications n JOIN users u ON n.created_by = u.user_id";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Users u = new Users();
                u.setUserId(rs.getInt("user_id"));
                u.setFullName(rs.getString("full_name"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStatus(int notificationId, String newStatus) {
        String sql = "UPDATE notifications SET status = ? WHERE notification_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, notificationId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
