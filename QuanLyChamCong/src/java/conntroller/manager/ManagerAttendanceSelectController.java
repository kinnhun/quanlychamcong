package conntroller.manager;

import dal.AttendanceDAO;
import model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerAttendanceSelectController", urlPatterns = {"/manager/attendance-select"})
public class ManagerAttendanceSelectController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Users manager = (Users) session.getAttribute("user");
        AttendanceDAO dao = new AttendanceDAO();

        // Lấy danh sách nhân viên dưới quyền
        List<Users> employeeList = dao.getEmployeesByManager(manager.getUserId());

        request.setAttribute("employeeList", employeeList);
        request.getRequestDispatcher("/view/manager/attendance_select.jsp").forward(request, response);
    }
}