package controller.admin;

import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Users;

import java.io.IOException;

@WebServlet(name = "AdminUserAddController", urlPatterns = {"/admin/user-add"})
public class AdminUserAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/admin/add-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        UserDAO dao = new UserDAO();

        // Kiểm tra trùng lặp
        if (dao.isUsernameExists(username)) {
            request.setAttribute("error", "Tài khoản đã tồn tại.");
            request.getRequestDispatcher("/view/admin/add-user.jsp").forward(request, response);
            return;
        }

        if (dao.isEmailExists(email)) {
            request.setAttribute("error", "Email đã tồn tại.");
            request.getRequestDispatcher("/view/admin/add-user.jsp").forward(request, response);
            return;
        }

        if (dao.isPhoneExists(phone)) {
            request.setAttribute("error", "Số điện thoại đã tồn tại.");
            request.getRequestDispatcher("/view/admin/add-user.jsp").forward(request, response);
            return;
        }

        // Tạo user mới
        Users user = new Users();
        user.setUsername(username);
        user.setPasswordHash(password); // có thể mã hóa sau
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);
        user.setStatus("active");

        boolean success = dao.insertUser(user);

        if (success) {
            // ✅ Soạn nội dung email
            String emailContent = "<h3>Tài khoản của bạn đã được tạo thành công!</h3>"
                    + "<p><b>Tài khoản:</b> " + username + "</p>"
                    + "<p><b>Mật khẩu:</b> " + password + "</p>"
                    + "<p>Vui lòng đăng nhập vào hệ thống và đổi mật khẩu ngay khi có thể.</p>";

            // ✅ Gửi email
            boolean mailSent = utils.EmailUtils.sendEmail(
                    email,
                    "Thông tin tài khoản - Hệ thống chấm công",
                    emailContent
            );

            // ✅ Thông báo nếu gửi thành công hoặc không
            if (mailSent) {
                request.getSession().setAttribute("message", "Tạo tài khoản thành công. Đã gửi email thông báo cho người dùng.");
            } else {
                request.getSession().setAttribute("message", "Tạo tài khoản thành công. ⚠ Không gửi được email.");
            }

            response.sendRedirect("users");
        }

    }
}
