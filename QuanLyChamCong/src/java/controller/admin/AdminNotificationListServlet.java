/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.NotificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Notification;

@WebServlet(name = "AdminNotificationListServlet", urlPatterns = {"/admin/notification-list"})
public class AdminNotificationListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Nhận các tham số filter/sort/page
        String status = request.getParameter("status");
        String senderId = request.getParameter("senderId");
        String searchTitle = request.getParameter("searchTitle");
        String searchContent = request.getParameter("searchContent");
        String sort = request.getParameter("sort"); // "asc" hoặc "desc"
        String pageStr = request.getParameter("page");
        int page = 1, pageSize = 10;
        try {
            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }
        } catch (Exception e) {
        }

        // Lấy tổng số thông báo sau filter
        NotificationDAO notificationDAO = new NotificationDAO();
        int total = notificationDAO.countAllNotifications(status, senderId, searchTitle, searchContent);

        // Lấy danh sách thông báo sau filter & phân trang
        List<Notification> notificationList = notificationDAO.getNotificationsPaging(
                status, senderId, searchTitle, searchContent, sort, page, pageSize
        );

        // Lấy danh sách người gửi cho select box lọc
        List<model.Users> senderList = notificationDAO.getAllSenders();

        request.setAttribute("notificationList", notificationList);
        request.setAttribute("senderList", senderList);

        // Để giữ filter khi chuyển trang/lọc lại
        request.setAttribute("status", status);
        request.setAttribute("senderId", senderId);
        request.setAttribute("searchTitle", searchTitle);
        request.setAttribute("searchContent", searchContent);
        request.setAttribute("sort", sort == null ? "desc" : sort);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("total", total);

        request.getRequestDispatcher("/view/admin/notification-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý cập nhật trạng thái
        String notificationIdStr = request.getParameter("notificationId");
        String newStatus = request.getParameter("newStatus");
        // Các tham số filter để redirect giữ nguyên trang hiện tại
        String status = request.getParameter("status");
        String senderId = request.getParameter("senderId");
        String searchTitle = request.getParameter("searchTitle");
        String searchContent = request.getParameter("searchContent");
        String sort = request.getParameter("sort");
        String pageStr = request.getParameter("page");
        int page = 1;
        try {
            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }
        } catch (Exception e) {
        }

        if (notificationIdStr != null && newStatus != null) {
            try {
                int notificationId = Integer.parseInt(notificationIdStr);
                NotificationDAO dao = new NotificationDAO();
                boolean check = dao.updateStatus(notificationId, newStatus);
                if (check) {
                    request.getSession().setAttribute("message", "Cập nhật trạng thái thành công!");
                } else {
                    request.getSession().setAttribute("error", "Cập nhật trạng thái thất bại!");
                }
            } catch (Exception ex) {

            }
        }
        // Redirect lại đúng trang cũ với filter/sort/page
        String redirectUrl = request.getContextPath() + "/admin/notification-list"
                + "?page=" + page
                + "&status=" + (status == null ? "" : status)
                + "&senderId=" + (senderId == null ? "" : senderId)
                + "&searchTitle=" + (searchTitle == null ? "" : searchTitle)
                + "&searchContent=" + (searchContent == null ? "" : searchContent)
                + "&sort=" + (sort == null ? "desc" : sort);
        response.sendRedirect(redirectUrl);
    }
}
