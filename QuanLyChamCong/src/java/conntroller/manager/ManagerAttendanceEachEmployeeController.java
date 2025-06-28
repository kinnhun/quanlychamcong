package conntroller.manager;

import dal.AttendanceDAO;
import model.Attendance;
import model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerAttendanceEachEmployeeController", urlPatterns = {"/manager/attendance-each-employee"})
public class ManagerAttendanceEachEmployeeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin manager từ session
        Users manager = (Users) session.getAttribute("user");

        // Lấy và xử lý tham số từ request
        String userIdParam = request.getParameter("userId");
        String pageParam = request.getParameter("page");

        int currentPage = 1;
        int pageSize = 10;
        try {
            if (pageParam != null) {
                currentPage = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException ignored) {}

        Integer selectedUserId = null;
        try {
            if (userIdParam != null && !userIdParam.isEmpty()) {
                selectedUserId = Integer.parseInt(userIdParam);
            }
        } catch (NumberFormatException ignored) {}

        // Khởi tạo DAO
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        // Lấy danh sách nhân viên dưới quyền
        List<Users> employees = attendanceDAO.getEmployeesByManager(manager.getUserId());

        // Chuẩn bị dữ liệu chấm công
        List<Attendance> attendanceList = null;
        int totalRecords = 0;
        int totalPages = 0;

        // Báo cáo tổng số ngày
        int totalWorkingDays = 0;
        int totalAbsentDays = 0;
        int totalLateDays = 0;

        // Lấy chấm công nếu có userId được chọn
        if (selectedUserId != null) {
            attendanceList = attendanceDAO.getAttendanceByUserId(selectedUserId, currentPage, pageSize);
            totalRecords = attendanceDAO.countAttendanceByUserId(selectedUserId);
            totalPages = (int) Math.ceil((double) totalRecords / pageSize);

            // Tính báo cáo cho nhân viên được chọn
            totalWorkingDays = attendanceDAO.countWorkingDaysByUserId(selectedUserId);
            totalAbsentDays = attendanceDAO.countAbsentDaysByUserId(selectedUserId);
            totalLateDays = attendanceDAO.countLateDaysByUserId(selectedUserId);
        }

       

        // Truyền dữ liệu sang JSP
        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("employeeList", employees);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("selectedUserId", selectedUserId != null ? selectedUserId : "");
        request.setAttribute("viewMode", "each-employee");
        request.setAttribute("totalWorkingDays", totalWorkingDays);
        request.setAttribute("totalAbsentDays", totalAbsentDays);
        request.setAttribute("totalLateDays", totalLateDays);

        // Forward đến JSP
        request.getRequestDispatcher("/view/manager/attendance_list_for_each.jsp").forward(request, response);
    }
}