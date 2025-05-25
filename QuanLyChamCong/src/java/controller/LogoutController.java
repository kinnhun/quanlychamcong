package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "LogoutController", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Huỷ session
        HttpSession session = request.getSession(false); // false: không tạo session nếu chưa có
        if (session != null) {
            session.invalidate();
        }

        // Chuyển về trang đăng nhập
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // POST cũng xử lý như GET
    }

    @Override
    public String getServletInfo() {
        return "LogoutController - xử lý đăng xuất người dùng";
    }
}
