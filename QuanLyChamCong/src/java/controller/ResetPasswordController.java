package controller.user;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ResetPasswordController", urlPatterns = {"/reset-password"})
public class ResetPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        if (token == null || token.isEmpty()) {
            request.setAttribute("error", "Token không hợp lệ.");
            request.getRequestDispatcher("view/user/reset-password.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        int userId = dao.getUserIdByToken(token);
        if (userId == -1) {
            request.setAttribute("error", "Token đã hết hạn hoặc không hợp lệ.");
            request.getRequestDispatcher("view/user/reset-password.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("view/user/reset-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");

        if (!newPass.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu không khớp.");
            request.setAttribute("token", token);
            request.getRequestDispatcher("view/user/reset-password.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        int userId = dao.getUserIdByToken(token);
        if (userId == -1) {
            request.setAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
            request.getRequestDispatcher("view/user/reset-password.jsp").forward(request, response);
            return;
        }

        // Cập nhật mật khẩu
        dao.updatePasswordByUserId(userId, newPass);
        dao.deleteToken(token);

        request.getSession().setAttribute("message", "Cập nhật mật khẩu thành công.");
        response.sendRedirect("login");
    }
}
