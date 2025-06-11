package controller.admin;

import dal.LocationDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Departments;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "AdminDepartmentsEditController", urlPatterns = {"/admin/departments-edit"})
public class AdminDepartmentsEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            LocationDAO dao = new LocationDAO();
            Departments d = dao.getDepartmentById(id);

            if (d == null) {
                response.sendRedirect(request.getContextPath() + "/admin/departments");
                return;
            }

            request.setAttribute("department", d);
            request.getRequestDispatcher("/view/admin/departments_edit.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/departments");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name").trim();
            String code = request.getParameter("code").trim();
            String description = request.getParameter("description").trim();

            Departments d = new Departments();
            d.setDepartmentId(id);
            d.setDepartmentName(name);
            d.setDepartmentCode(code);
            d.setDescription(description);
            d.setCreatedAt(new Date()); // Giữ nguyên hoặc bạn có thể bỏ nếu không cần cập nhật

            LocationDAO dao = new LocationDAO();

            boolean codeExists = dao.getAllDepartments().stream()
                    .anyMatch(dep -> dep.getDepartmentCode().equalsIgnoreCase(code) && dep.getDepartmentId() != id);

            boolean nameExists = dao.getAllDepartments().stream()
                    .anyMatch(dep -> dep.getDepartmentName().equalsIgnoreCase(name) && dep.getDepartmentId() != id);

            if (codeExists) {
                request.setAttribute("error", "Mã phòng ban đã tồn tại.");
                request.setAttribute("department", d);
                request.getRequestDispatcher("/view/admin/departments_edit.jsp").forward(request, response);
                return;
            }

            if (nameExists) {
                request.setAttribute("error", "Tên phòng ban đã tồn tại.");
                request.setAttribute("department", d);
                request.getRequestDispatcher("/view/admin/departments_edit.jsp").forward(request, response);
                return;
            }

            boolean success = dao.updateDepartment(d);
            HttpSession session = request.getSession();
            if (success) {
                session.setAttribute("message", "Sửa thành công!");
                response.sendRedirect(request.getContextPath() + "/admin/location-departments");
            } else {
                request.setAttribute("error", "Cập nhật thất bại.");
                request.setAttribute("department", d);
                request.getRequestDispatcher("/view/admin/departments_edit.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý dữ liệu.");
            request.getRequestDispatcher("/view/admin/departments_edit.jsp").forward(request, response);
        }
    }
}
