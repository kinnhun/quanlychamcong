package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Notification;
import model.NotificationReceiver;

public class NotificationDAO extends DBContext {

    public int insertAndGetId(Notification noti) {
        String sql = "INSERT INTO notifications (title, content, image_url, file_url, created_by, created_at, scheduled_time, status) "
                + "OUTPUT INSERTED.notification_id VALUES (?, ?, ?, ?, ?, GETDATE(), ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, noti.getTitle());
            ps.setString(2, noti.getContent());
            ps.setString(3, noti.getImageUrl());
            ps.setString(4, noti.getFileUrl());
            ps.setInt(5, noti.getCreatedBy());
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

}
