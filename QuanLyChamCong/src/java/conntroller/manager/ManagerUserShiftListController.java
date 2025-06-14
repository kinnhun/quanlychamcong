package conntroller.manager;

import dal.ShiftDAO;
import dal.UserDAO;
import dal.LocationDAO;
import model.UserShift;
import model.Users;
import model.Departments;
import model.Shift;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ManagerUserShiftListController", urlPatterns = {"/manager/user-shifts"})
public class ManagerUserShiftListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Users manager = (Users) session.getAttribute("user");

        // Lấy tham số filter và phân trang
        String empId = request.getParameter("employeeId");
        String shiftId = request.getParameter("shiftId");
        String departmentId = request.getParameter("departmentId");
        String date = request.getParameter("date");
        String week = request.getParameter("week");
        String month = request.getParameter("month");
        int page = 1;
        int pageSize = 10;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            if (page <= 0) page = 1;
        } catch (Exception ignored) {}
        try {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
            if (pageSize <= 0) pageSize = 10;
        } catch (Exception ignored) {}

        ShiftDAO shiftDAO = new ShiftDAO();
        UserDAO userDAO = new UserDAO();
        LocationDAO departmentDAO = new LocationDAO();

        List<Users> employeeList = userDAO.getEmployeesByManager(manager.getUserId());
        List<Shift> shiftList = shiftDAO.getAllShift();
        List<Departments> departmentList = departmentDAO.getDepartmentsByManager(manager.getUserId());

        List<UserShift> userShiftList = shiftDAO.getUserShiftsPaging(
                manager.getUserId(), empId, shiftId, departmentId, null, date, week, month, page, pageSize
        );
        int totalRows = shiftDAO.countUserShifts(
                manager.getUserId(), empId, shiftId, departmentId, null, date, week, month
        );
        int totalPages = (int) Math.ceil((double) totalRows / pageSize);

        Map<String, String> filter = new HashMap<>();
        filter.put("employeeId", empId);
        filter.put("shiftId", shiftId);
        filter.put("departmentId", departmentId);
        filter.put("date", date);
        filter.put("week", week);
        filter.put("month", month);

        request.setAttribute("employeeList", employeeList);
        request.setAttribute("shiftList", shiftList);
        request.setAttribute("departmentList", departmentList);

        request.setAttribute("userShiftList", userShiftList);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("filter", filter);

        request.getRequestDispatcher("/view/manager/user_shifts_list.jsp").forward(request, response);
    }
}
