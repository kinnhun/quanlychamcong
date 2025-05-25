package controller;

import dal.AttendanceDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Users;
import utils.Base64ImageUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import model.Attendance;

@WebServlet(name = "AttendanceController", urlPatterns = {"/attendance"})
public class AttendanceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        AttendanceDAO dao = new AttendanceDAO();
        Attendance todayAttendance = dao.getAttendanceToday(user.getUserId(), LocalDate.now());

        request.setAttribute("attendance", todayAttendance);

        // Forward đến giao diện chấm công
        request.getRequestDispatcher("/view/user/attendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action"); // "checkin" hoặc "checkout"
        String imageBase64 = request.getParameter("imageBase64");

        // Gọi hàm lưu ảnh và truyền context để lấy đường dẫn /uploads
        String fileName = Base64ImageUtil.saveImage(
                imageBase64,
                user.getUserId() + "_" + action,
                getServletContext()
        );

        if (fileName == null) {
            session.setAttribute("error", "❌ Không thể lưu ảnh chấm công.");
            response.sendRedirect(request.getContextPath() + "/view/user/attendance.jsp");
            return;
        }

        // Gọi DAO để ghi dữ liệu vào bảng attendance
        AttendanceDAO dao = new AttendanceDAO();
        boolean success = dao.saveAttendance(user.getUserId(), action, fileName);

        if (success) {
            session.setAttribute("message", "✅ Chấm công thành công.");
        } else {
            session.setAttribute("error", "❌ Chấm công thất bại. Vui lòng thử lại.");
        }

        response.sendRedirect(request.getContextPath() + "/attendance");
    }
}
