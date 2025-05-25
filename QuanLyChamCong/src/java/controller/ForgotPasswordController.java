package controller;

import dal.UserDAO;
import model.Users;
import utils.EmailUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "ForgotPasswordController", urlPatterns = {"/forgot-password"})
public class ForgotPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/user/ForgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        UserDAO dao = new UserDAO();
        Users user = dao.getUserByEmail(email);

        if (user == null) {
            request.setAttribute("error", "Không tìm thấy tài khoản với email này.");
            doGet(request, response);
            return;
        }

        // Tạo token và lưu
        String token = UUID.randomUUID().toString();
        dao.createPasswordResetToken(user.getUserId(), token);

        // Tạo đường dẫn đổi mật khẩu
        String resetLink = request.getRequestURL().toString().replace("forgot-password", "reset-password") + "?token=" + token;

        // Gửi mail
        String content = "<h3>Đặt lại mật khẩu</h3>"
                + "<p>Bạn đã yêu cầu đặt lại mật khẩu. Nhấn vào liên kết bên dưới để đặt lại:</p>"
                + "<p><a href='" + resetLink + "'>Đặt lại mật khẩu</a></p>"
                + "<p>Lưu ý: liên kết chỉ có hiệu lực trong 5 phút.</p>";

        boolean sent = EmailUtils.sendEmail(email, "Khôi phục mật khẩu", content);

        if (sent) {
            request.getSession().setAttribute("message", "Email đặt lại mật khẩu đã được gửi.");
            response.sendRedirect("login");
        } else {
            request.setAttribute("error", "Gửi email thất bại. Vui lòng thử lại.");
            doGet(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Gửi email khôi phục mật khẩu có kèm token 5 phút";
    }
}
