package conntroller.employee;

import dal.ShiftDAO;
import model.UserShift;
import model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EmployeeShiftScheduleController", urlPatterns = {"/employee/shift-schedule"})
public class EmployeeShiftScheduleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        Users user = (Users) (session != null ? session.getAttribute("user") : null);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy danh sách ca làm của nhân viên
        ShiftDAO shiftDAO = new ShiftDAO();
        List<UserShift> userShiftList = shiftDAO.getUserShiftsByEmployee(user.getUserId(), null, null);

        request.setAttribute("userShiftList", userShiftList);
        request.getRequestDispatcher("/view/employee/shift_schedule.jsp").forward(request, response);
    }
}