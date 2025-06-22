package conntroller.employee;

import dal.AttendanceDAO;
import model.Attendance;
import model.Locations;
import model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EmployeeAttendanceListServlet", urlPatterns = {"/employee/attendance"})
public class EmployeeAttendanceListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");

        // Nếu chưa đăng nhập thì chuyển về trang login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = user.getUserId();

        // Nhận tham số filter và phân trang
        String status = request.getParameter("status"); // locked, unlocked, null
        String date = request.getParameter("date");
        String locationIdStr = request.getParameter("locationId");
        int locationId = 0;
        if (locationIdStr != null && !locationIdStr.isEmpty()) {
            try {
                locationId = Integer.parseInt(locationIdStr);
            } catch (Exception e) {
            }
        }
        int page = 1;
        int pageSize = 10;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        } catch (Exception e) {
            page = 1;
        }

        AttendanceDAO dao = new AttendanceDAO();

        // Lấy list địa điểm cho bộ lọc
        List<Locations> locationList = dao.getAllLocationsByUser(userId);

        // Đếm tổng số bản ghi phù hợp bộ lọc
        int totalRow = dao.countAttendanceByUserFilter(userId, status, date, locationId);
        int totalPage = (int) Math.ceil((double) totalRow / pageSize);

        // Lấy danh sách đã phân trang + filter
        List<Attendance> attendanceList = dao.getAttendanceByUserFilter(userId, status, date, locationId, page, pageSize);

        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("locationList", locationList);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("pageSize", pageSize);

        request.getRequestDispatcher("/view/employee/attendance_list.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị danh sách chấm công của nhân viên, có lọc & phân trang";
    }
}
