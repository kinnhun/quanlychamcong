package conntroller.manager;

import dal.LocationDAO;
import dal.ShiftDAO;
import dal.UserDAO;
import model.Departments;
import model.Locations;
import model.Shift;
import model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "ManagerUserShiftsAddController", urlPatterns = {"/manager/user-shifts-add"})
public class ManagerUserShiftsAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập, vai trò quản lý
        HttpSession session = request.getSession(false);
        Users manager = (Users) (session != null ? session.getAttribute("user") : null);
        if (manager == null || (!"manager".equals(manager.getRole()) && !"admin".equals(manager.getRole()))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy danh sách nhân viên mà manager này quản lý
        ShiftDAO shiftDAO = new ShiftDAO();
        UserDAO udao = new UserDAO();
        List<Users> employeeList = udao.getEmployeesByManager(manager.getUserId());
        List<Shift> shiftList = shiftDAO.getAllShift();

        // Lấy chi nhánh và phòng ban (nếu muốn lọc theo quyền quản lý thì tự code thêm)
        LocationDAO locationDAO = new LocationDAO();
        LocationDAO departmentDAO = new LocationDAO();

        List<Locations> locationList = locationDAO.getAllLocation();
        List<Departments> departmentList = departmentDAO.getAllDepartments();

        request.setAttribute("employeeList", employeeList);
        request.setAttribute("shiftList", shiftList);
        request.setAttribute("locationList", locationList);
        request.setAttribute("departmentList", departmentList);

        // Đặt ngày hiện tại cho form date
        request.setAttribute("now", new java.util.Date());

        request.getRequestDispatcher("/view/manager/user_shifts_add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            // Lấy dữ liệu từ request
            String userIdStr = request.getParameter("userId");
            String shiftIdStr = request.getParameter("shiftId");
            String dateStr = request.getParameter("date");
            String note = request.getParameter("note");
            String locationIdStr = request.getParameter("locationId");
            String departmentIdStr = request.getParameter("departmentId");

            // Validate dữ liệu đầu vào
            if (userIdStr == null || userIdStr.isEmpty()
                    || shiftIdStr == null || shiftIdStr.isEmpty()
                    || dateStr == null || dateStr.isEmpty()) {
                session.setAttribute("error", "Thiếu thông tin nhân viên, ca làm hoặc ngày!");
                response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
                return;
            }

            int userId = Integer.parseInt(userIdStr);
            int shiftId = Integer.parseInt(shiftIdStr);
            java.sql.Date date = java.sql.Date.valueOf(dateStr);
            Integer locationId = (locationIdStr != null && !locationIdStr.isEmpty()) ? Integer.parseInt(locationIdStr) : null;
            Integer departmentId = (departmentIdStr != null && !departmentIdStr.isEmpty()) ? Integer.parseInt(departmentIdStr) : null;

            // Lấy user đang đăng nhập (manager)
            Users manager = (Users) session.getAttribute("user");
            if (manager == null) {
                session.setAttribute("error", "Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            ShiftDAO shiftDAO = new ShiftDAO();

            // 1. Kiểm tra đã có đúng ca này cho nhân viên, ngày, location, department chưa
            if (shiftDAO.isUserShiftDuplicate(userId, shiftId, date, locationId, departmentId)) {
                session.setAttribute("error", "Nhân viên này đã được phân công ca này trong ngày này tại chi nhánh/phòng ban này!");
                response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
                return;
            }

            // 2. Kiểm tra nhân viên đã có ca nào trong ngày này tại location/department này chưa (không trùng shift_id)
            if (shiftDAO.isUserAssignedOnShiftDate(userId, date, locationId, departmentId)) {
                session.setAttribute("error", "Nhân viên đã có ca làm khác trong ngày này tại chi nhánh/phòng ban này!");
                response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
                return;
            }

            // 3. Insert nếu không trùng
            boolean success = shiftDAO.insertUserShift(userId, shiftId, date, locationId, departmentId, manager.getUserId(), note);

            if (success) {
                session.setAttribute("message", "Phân ca thành công!");
            } else {
                session.setAttribute("error", "Phân ca thất bại. Kiểm tra lại dữ liệu!");
            }
            response.sendRedirect(request.getContextPath() + "/manager/user-shifts");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Đã xảy ra lỗi hệ thống!");
            response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
        }
    }

}
