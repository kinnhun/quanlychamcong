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

@WebServlet(name = "ManagerAttendanceListController", urlPatterns = {"/manager/attendance-list"})
public class ManagerAttendanceListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Users manager = (Users) session.getAttribute("user");

        // Lấy tham số filter
        String userIdStr = request.getParameter("userId");
        String status = request.getParameter("status");
        String date = request.getParameter("date");
        String pageStr = request.getParameter("page");

        int page = 1, pageSize = 10;
        if (pageStr != null) try { page = Integer.parseInt(pageStr); } catch (Exception ignored) {}

        Integer userId = null;
        if (userIdStr != null && !userIdStr.isEmpty()) {
            try { userId = Integer.parseInt(userIdStr); } catch (Exception ignored) {}
        }

        AttendanceDAO dao = new AttendanceDAO();

        // Đếm tổng số bản ghi lọc được
        int totalRecords = dao.countAttendanceByManagerFilter(manager.getUserId(), userId, status, date);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Lấy danh sách theo trang
        List<Attendance> attendanceList = dao.getAttendanceByManagerFilter(
                manager.getUserId(), userId, status, date, page, pageSize);

        // Lấy danh sách nhân viên (cho filter dropdown)
        List<Users> employeeList = dao.getEmployeesByManager(manager.getUserId());

        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("employeeList", employeeList);

        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("selectedUserId", userId != null ? userId : "");
        request.setAttribute("selectedStatus", status != null ? status : "");
        request.setAttribute("selectedDate", date != null ? date : "");

        request.getRequestDispatcher("/view/manager/attendance_list.jsp").forward(request, response);
    }
}
