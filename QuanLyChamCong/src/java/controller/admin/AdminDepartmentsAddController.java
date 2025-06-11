package controller.admin;

import dal.LocationDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Departments;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AdminDepartmentsAddController", urlPatterns = {"/admin/departments-add"})
public class AdminDepartmentsAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/admin/departments_add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name").trim();
        String code = request.getParameter("code").trim();
        String description = request.getParameter("description");

        Departments d = new Departments();
        d.setDepartmentName(name);
        d.setDepartmentCode(code);
        d.setDescription(description);
        d.setCreatedAt(new Date());

        LocationDAO dao = new LocationDAO();

        // Kiểm tra trùng tên
        boolean isNameExists = dao.getAllDepartments().stream()
                .anyMatch(dep -> dep.getDepartmentName().equalsIgnoreCase(name));

        // Kiểm tra trùng mã
        boolean isCodeExists = dao.getAllDepartments().stream()
                .anyMatch(dep -> dep.getDepartmentCode().equalsIgnoreCase(code));

        if (isNameExists) {
            request.setAttribute("error", "Tên phòng ban đã tồn tại.");
        } else if (isCodeExists) {
            request.setAttribute("error", "Mã phòng ban đã tồn tại.");
        } else {
            boolean success = dao.insertDepartments(d);
            if (success) {
                session.setAttribute("message", "Sửa thành công!");
                response.sendRedirect(request.getContextPath() + "/admin/location-departments");
                return;
            } else {
                request.setAttribute("error", "Thêm phòng ban thất bại.");
            }
        }

        // Truyền lại dữ liệu đã nhập để giữ form
        request.setAttribute("old", d);
        request.getRequestDispatcher("/view/admin/departments_add.jsp").forward(request, response);
    }
}
