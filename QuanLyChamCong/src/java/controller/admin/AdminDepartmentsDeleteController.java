package controller.admin;

import dal.LocationDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "AdminDepartmentsDeleteController", urlPatterns = {"/admin/departments-delete"})
public class AdminDepartmentsDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            LocationDAO dao = new LocationDAO();
            boolean success = dao.deleteDepartmentById(id);

            if (success) {
                session.setAttribute("message", "Xóa phòng ban thành công!");
            } else {
                session.setAttribute("error", "Không thể xóa phòng ban. Có thể phòng ban đang được sử dụng.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Đã xảy ra lỗi khi xóa phòng ban.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/location-departments");
    }
}
