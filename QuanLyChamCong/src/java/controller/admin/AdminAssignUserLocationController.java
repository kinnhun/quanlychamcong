/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.LocationDAO;
import dal.UserDAO;
import dal.UserLocationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Locations;
import model.Users;

import java.io.IOException;
import java.util.List;
import utils.EmailUtils;

@WebServlet(name = "AdminAssignUserLocationController", urlPatterns = {"/admin/assign-user-location"})
public class AdminAssignUserLocationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LocationDAO locationDAO = new LocationDAO();
        UserDAO userDAO = new UserDAO();
        UserLocationDAO userLocationDAO = new UserLocationDAO();

        // Lấy các giá trị từ form lọc
        String filterLocationId = request.getParameter("locationId");
        String filterRole = request.getParameter("role");
        String filterStatus = request.getParameter("status");
        String keyword = request.getParameter("keyword");

        List<Locations> locationList = locationDAO.getAll();              // danh sách chi nhánh
        List<Users> userList = userDAO.searchEmployees( // danh sách nhân viên hiển thị trong select box
                filterLocationId, filterRole, filterStatus, keyword);
        List<Object[]> assignmentList = userLocationDAO.searchAssignments(
                filterLocationId, filterRole, filterStatus, keyword);     // kết quả bảng phân công

        // Gửi về JSP
        request.setAttribute("locationList", locationList);
        request.setAttribute("userList", userList);
        request.setAttribute("assignmentList", assignmentList);
        request.setAttribute("selectedLocation", filterLocationId);
        request.setAttribute("selectedRole", filterRole);
        request.setAttribute("selectedStatus", filterStatus);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/view/admin/assign-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));

        UserLocationDAO dao = new UserLocationDAO();

        // Kiểm tra nếu đã tồn tại phân công
        boolean alreadyAssigned = dao.isAssigned(userId, locationId);

        if (alreadyAssigned) {
            request.getSession().setAttribute("error", "Nhân viên này đã được phân công vào chi nhánh này.");
        } else {
            boolean success = dao.assignUserToLocation(userId, locationId);
            if (success) {
                UserDAO userDao = new UserDAO();
                Users user = userDao.getUserById(userId);
                LocationDAO ld = new LocationDAO();
                Locations location = ld.getLocationById(locationId);

                String subject = "Bạn đã được phân công mới";
                String content = String.format(
                        "<p>Xin chào %s,</p><p>Bạn vừa được phân công làm việc tại chi nhánh <strong>%s</strong> - %s.</p>",
                        user.getFullName(), location.getName(), location.getAddress());

                EmailUtils.sendEmail(user.getEmail(), subject, content);
                request.getSession().setAttribute("message", "Phân công thành công và đã gửi email");
            } else {
                request.getSession().setAttribute("error", "Phân công thất bại. Vui lòng thử lại.");
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/assign-user-location");
    }

    @Override
    public String getServletInfo() {
        return "Phân công nhân viên cho địa điểm";
    }
}
