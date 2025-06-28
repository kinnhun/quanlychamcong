package conntroller.manager;

import dal.LocationDAO;
import dal.ShiftDAO;
import dal.UserDAO;
import model.Departments;
import model.Locations;
import model.Shift;
import model.UserShift;
import model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "ManagerUserShiftsEditController", urlPatterns = {"/manager/user-shifts-edit"})
public class ManagerUserShiftsEditController extends HttpServlet {

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

        // Lấy userShiftId từ tham số
        String userShiftIdStr = request.getParameter("userShiftId");
        if (userShiftIdStr == null || userShiftIdStr.isEmpty()) {
            session.setAttribute("error", "Không tìm thấy thông tin phân ca!");
            response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
            return;
        }

        int userShiftId;
        try {
            userShiftId = Integer.parseInt(userShiftIdStr);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID phân ca không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
            return;
        }

        // Lấy thông tin phân ca
        ShiftDAO shiftDAO = new ShiftDAO();
        UserShift userShift = shiftDAO.getUserShiftById(userShiftId);
        if (userShift == null) {
            session.setAttribute("error", "Không tìm thấy phân ca!");
            response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
            return;
        }

        // Lấy danh sách nhân viên, ca, chi nhánh, phòng ban
        UserDAO udao = new UserDAO();
        LocationDAO locationDAO = new LocationDAO();
        List<Users> employeeList = udao.getEmployeesByManager(manager.getUserId());
        List<Shift> shiftList = shiftDAO.getAllShift();
        List<Locations> locationList = locationDAO.getAllLocation();
        List<Departments> departmentList = locationDAO.getAllDepartments();

        request.setAttribute("userShift", userShift);
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("shiftList", shiftList);
        request.setAttribute("locationList", locationList);
        request.setAttribute("departmentList", departmentList);

        request.getRequestDispatcher("/view/manager/user_shifts_edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            // Lấy dữ liệu từ request
            String userShiftIdStr = request.getParameter("userShiftId");
            String userIdStr = request.getParameter("userId");
            String shiftIdStr = request.getParameter("shiftId");
            String dateStr = request.getParameter("date");
            String note = request.getParameter("note");
            String locationIdStr = request.getParameter("locationId");
            String departmentIdStr = request.getParameter("departmentId");

            // Validate dữ liệu đầu vào
            if (userShiftIdStr == null || userShiftIdStr.isEmpty() ||
                userIdStr == null || userIdStr.isEmpty() ||
                shiftIdStr == null || shiftIdStr.isEmpty() ||
                dateStr == null || dateStr.isEmpty()) {
                session.setAttribute("error", "Thiếu thông tin cần thiết!");
                response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
                return;
            }

            int userShiftId = Integer.parseInt(userShiftIdStr);
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

            // Kiểm tra trùng lặp (trừ chính bản ghi hiện tại)
            if (shiftDAO.isUserShiftDuplicate(userId, shiftId, date, locationId, departmentId)) {
                session.setAttribute("error", "Nhân viên này đã được phân công ca này trong ngày này tại chi nhánh/phòng ban này!");
                response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
                return;
            }

            // Kiểm tra nhân viên đã có ca nào khác trong ngày này tại location/department (trừ bản ghi hiện tại)
            if (shiftDAO.isUserAssignedOnShiftDate(userId, date, locationId, departmentId)) {
                session.setAttribute("error", "Nhân viên đã có ca làm khác trong ngày này tại chi nhánh/phòng ban này!");
                response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
                return;
            }

            // Cập nhật phân ca
            boolean success = shiftDAO.updateUserShift(userShiftId, userId, shiftId, date, locationId, departmentId, manager.getUserId(), note);

            if (success) {
                session.setAttribute("message", "Cập nhật phân ca thành công!");
            } else {
                session.setAttribute("error", "Cập nhật phân ca thất bại. Kiểm tra lại dữ liệu!");
            }
            response.sendRedirect(request.getContextPath() + "/manager/user-shifts");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Đã xảy ra lỗi hệ thống!");
            response.sendRedirect(request.getContextPath() + "/manager/user-shifts");
        }
    }
}