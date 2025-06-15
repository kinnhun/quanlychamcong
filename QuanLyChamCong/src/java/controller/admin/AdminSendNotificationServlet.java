package controller.admin;

import dal.LocationDAO;
import dal.NotificationDAO;
import dal.UserDAO;
import dal.UserLocationDAO;
import model.Notification;
import model.NotificationReceiver;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import model.Departments;
import model.Locations;
import model.UserLocations;

@WebServlet(name = "AdminSendNotificationServlet", urlPatterns = {"/admin/send-notification"})
public class AdminSendNotificationServlet extends HttpServlet {

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String locationId = request.getParameter("locationId");
    String departmentId = request.getParameter("departmentId");
    String keyword = request.getParameter("keyword");

    UserLocationDAO userDao = new UserLocationDAO();
    LocationDAO ldao = new LocationDAO();
    // Lấy danh sách địa điểm, phòng ban cho dropdown
    List<Locations> locationList = ldao.getAllLocations();
    List<Departments> departmentList = ldao.getAllDepartments();

    // Lấy danh sách user lọc
    List<UserLocations> userList = userDao.searchUsersWithLocationAndDepartment(locationId, departmentId, keyword);

    request.setAttribute("userList", userList);
    request.setAttribute("locationList", locationList);
    request.setAttribute("departmentList", departmentList);
    request.setAttribute("locationId", locationId);
    request.setAttribute("departmentId", departmentId);
    request.setAttribute("keyword", keyword);

    request.getRequestDispatcher("/view/admin/send-notification.jsp").forward(request, response);
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String[] userIds = request.getParameterValues("userIds");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String imageUrl = request.getParameter("imageUrl");
        String fileUrl = request.getParameter("fileUrl");
        String scheduledTimeStr = request.getParameter("scheduledTime");

        int createdBy = ((model.Users) request.getSession().getAttribute("user")).getUserId();

        Notification noti = new Notification();
        noti.setTitle(title);
        noti.setContent(content);
        noti.setImageUrl(imageUrl);
        noti.setFileUrl(fileUrl);
        noti.setCreatedBy(createdBy);
        noti.setStatus("active");
        if (scheduledTimeStr != null && !scheduledTimeStr.isEmpty()) {
            noti.setScheduledTime(Date.valueOf(scheduledTimeStr.substring(0, 10))); // yyyy-MM-dd
        }

        NotificationDAO notificationDAO = new NotificationDAO();
        int notiId = notificationDAO.insertAndGetId(noti);

        if (userIds != null && notiId > 0) {
            for (String userIdStr : userIds) {
                try {
                    int userId = Integer.parseInt(userIdStr);
                    NotificationReceiver receiver = new NotificationReceiver();
                    receiver.setNotificationId(notiId);
                    receiver.setUserId(userId);
                    receiver.setStatus("unread");
                    receiver.setReadAt(null);
                    notificationDAO.insertReceiver(receiver);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        request.getSession().setAttribute("message", "Đã gửi thông báo thành công!");
        response.sendRedirect(request.getContextPath() + "/admin/send-notification");
    }
}
