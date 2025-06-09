/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;

@WebServlet(name = "AdminUserEditController", urlPatterns = {"/admin/user-edit"})
public class AdminUserEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("userId"));
        UserDAO dao = new UserDAO();
        Users user = dao.getUserById(userId);

        if (user == null) {
            request.getSession().setAttribute("error", "Không tìm thấy tài khoản.");
            response.sendRedirect(request.getContextPath() + "/admin/users");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/view/admin/edit-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        int userId = Integer.parseInt(request.getParameter("userId"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String banReason = request.getParameter("banReason");

        UserDAO dao = new UserDAO();
        Users user = dao.getUserById(userId);

        if (user == null) {
            request.getSession().setAttribute("error", "Không tìm thấy người dùng.");
            response.sendRedirect("users");
            return;
        }

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);
        user.setStatus(status);
        user.setBanReason(banReason);

        if (dao.updateUser(user)) {
            request.getSession().setAttribute("message", "Cập nhật thành công!");
            response.sendRedirect("users");
        } else {
            request.setAttribute("error", "Cập nhật thất bại!");
            request.setAttribute("user", user);
            request.getRequestDispatcher("/view/admin/edit-user.jsp").forward(request, response);
        }
    }
}
