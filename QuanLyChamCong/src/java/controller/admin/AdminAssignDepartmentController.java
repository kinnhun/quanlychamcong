package controller.admin;

import dal.LocationDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Departments;
import model.Locations;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminAssignDepartmentController", urlPatterns = {"/admin/assign-department"})
public class AdminAssignDepartmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            LocationDAO dao = new LocationDAO();

            // Lấy thông tin địa điểm
            Locations location = dao.getById(locationId);
            if (location == null) {
                request.getSession().setAttribute("error", "Không tìm thấy địa điểm.");
                response.sendRedirect(request.getContextPath() + "/admin/location");
                return;
            }

            // Lấy danh sách phòng ban và danh sách đã gán
            List<Departments> allDepartments = dao.getAllDepartments();
            List<Integer> assignedDepartmentIds = dao.getDepartmentIdsByLocation(locationId);

            // Gửi sang JSP
            request.setAttribute("location", location);
            request.setAttribute("departments", allDepartments);
            request.setAttribute("assignedIds", assignedDepartmentIds);
            request.getRequestDispatcher("/view/admin/assign_department.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi tải trang gán phòng ban.");
            response.sendRedirect(request.getContextPath() + "/admin/location");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int locationId = Integer.parseInt(request.getParameter("locationId"));
            String[] departmentIds = request.getParameterValues("departmentIds");

            LocationDAO dao = new LocationDAO();

            // Xoá hết liên kết cũ
            dao.deleteDepartmentsFromLocation(locationId);

            // Gán lại các phòng ban mới nếu có
            if (departmentIds != null) {
                for (String depIdStr : departmentIds) {
                    int depId = Integer.parseInt(depIdStr);
                    dao.assignDepartmentToLocation(locationId, depId);
                }
            }

            request.getSession().setAttribute("message", "Cập nhật phòng ban thành công!");
            response.sendRedirect(request.getContextPath() + "/admin/locations-department?locationId=" + locationId);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi cập nhật phòng ban.");
            response.sendRedirect(request.getContextPath() + "/admin/locations-department?locationId=" + request.getParameter("locationId"));
        }
    }
}
