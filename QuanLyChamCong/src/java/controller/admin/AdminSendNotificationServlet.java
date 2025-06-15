package controller.admin;

import dal.LocationDAO;
import dal.NotificationDAO;
import dal.UserDAO;
import dal.UserLocationDAO;
import model.Notification;
import model.NotificationReceiver;
import model.Departments;
import model.Locations;
import model.UserLocations;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "AdminSendNotificationServlet", urlPatterns = {"/admin/send-notification"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 20 // 20MB
)
public class AdminSendNotificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nhận tham số lọc từ form
        String locationId = request.getParameter("locationId");
        String departmentId = request.getParameter("departmentId");
        String keyword = request.getParameter("keyword");

        UserLocationDAO userLocDao = new UserLocationDAO();
        LocationDAO locationDao = new LocationDAO();

        // Lấy danh sách các địa điểm, phòng ban cho select box
        List<Locations> locationList = locationDao.getAllLocations();
        List<Departments> departmentList = locationDao.getAllDepartments();

        // Lấy danh sách user sau lọc
        List<UserLocations> userList;
        if (locationId != null || departmentId != null || (keyword != null && !keyword.trim().isEmpty())) {
            userList = userLocDao.searchUsersWithLocationAndDepartment(locationId, departmentId, keyword);
        } else {
            userList = userLocDao.getAllUsersWithLocationAndDepartment();
        }

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

        // Upload file
        Part imageFile = request.getPart("imageFile");
        Part attachFile = request.getPart("attachFile");
        String imageUrl = request.getParameter("imageUrl");
        String fileUrl = request.getParameter("fileUrl");

        String uploadedImageUrl = null;
        String uploadedFileUrl = null;

        if (imageFile != null && imageFile.getSize() > 0) {
            uploadedImageUrl = saveUploadFile(request, imageFile, "images");
        }
        if (attachFile != null && attachFile.getSize() > 0) {
            uploadedFileUrl = saveUploadFile(request, attachFile, "attachments");
        }
        // Nếu có file upload thì ưu tiên file, không thì dùng URL nhập tay
        if (uploadedImageUrl != null && !uploadedImageUrl.isEmpty()) {
            imageUrl = uploadedImageUrl;
        }
        if (uploadedFileUrl != null && !uploadedFileUrl.isEmpty()) {
            fileUrl = uploadedFileUrl;
        }

        // Các trường còn lại
        String[] userIds = request.getParameterValues("userIds");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String scheduledTimeStr = request.getParameter("scheduledTime");

        // Lấy người gửi (admin đang đăng nhập)
        model.Users user = (model.Users) request.getSession().getAttribute("user");
        int createdBy = user != null ? user.getUserId() : 0;

        Notification noti = new Notification();
        noti.setTitle(title);
        noti.setContent(content);
        noti.setImageUrl(imageUrl);
        noti.setFileUrl(fileUrl);
        UserDAO udao = new UserDAO();

        noti.setCreatedBy(udao.getUserById(createdBy));
        noti.setStatus("active");

        if (scheduledTimeStr != null && !scheduledTimeStr.isEmpty()) {
            try {
                String ts = scheduledTimeStr.replace("T", " ") + ":00";
                noti.setScheduledTime(Timestamp.valueOf(ts));
            } catch (Exception ex) {
                noti.setScheduledTime(null);
            }
        } else {
            noti.setScheduledTime(null);
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

    // Lưu file vào thư mục upload, trả về đường dẫn để truy cập (URL)
    private String saveUploadFile(HttpServletRequest request, Part filePart, String subFolder) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
        String uploadPath = request.getServletContext().getRealPath("/upload/" + subFolder);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File file = new File(uploadDir, fileName);

        try (InputStream is = filePart.getInputStream(); FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        }

        // Build absolute URL
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        String fileUrl = scheme + "://" + serverName
                + ((serverPort == 80 || serverPort == 443) ? "" : ":" + serverPort)
                + contextPath + "/upload/" + subFolder + "/" + fileName;

        return fileUrl;
    }

}
