package controller;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Users;

import java.io.IOException;

@WebServlet(name = "ChangePasswordController", urlPatterns = {"/change-password"})
public class ChangePasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        Users user = (session != null) ? (Users) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/view/user/change-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        Users user = (session != null) ? (Users) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy dữ liệu từ form
        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirmPass = request.getParameter("confirmPassword");

        // Kiểm tra độ dài mật khẩu
        if (newPass == null || newPass.length() < 6) {
            request.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự.");
            request.getRequestDispatcher("/view/user/change-password.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu cũ
        UserDAO dao = new UserDAO();
        if (!dao.checkPassword(user.getUserId(), oldPass)) {
            request.setAttribute("error", "Mật khẩu cũ không đúng.");
            request.getRequestDispatcher("/view/user/change-password.jsp").forward(request, response);
            return;
        }

        // Kiểm tra mật khẩu mới và xác nhận
        if (!newPass.equals(confirmPass)) {
            request.setAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
            request.getRequestDispatcher("/view/user/change-password.jsp").forward(request, response);
            return;
        }

        // Cập nhật mật khẩu (gợi ý mã hóa)
        // String hashedPassword = BCrypt.hashpw(newPass, BCrypt.gensalt());
        // dao.updatePasswordByUserId(user.getUserId(), hashedPassword);
        dao.updatePasswordByUserId(user.getUserId(), newPass); // Hiện tại lưu plaintext

        // Cập nhật session (nếu cần)
        request.getSession().setAttribute("message", "Đổi mật khẩu thành công.");
        response.sendRedirect(request.getContextPath() + "/profile"); // Chuyển hướng về trang chính
    }
}