package controller;

import java.io.IOException;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Users;
import dal.UserDAO;

@WebServlet(urlPatterns = {"/update-profile"})
public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String fullName = request.getParameter("fullName") != null ? request.getParameter("fullName").trim() : "";
        String email = request.getParameter("email") != null ? request.getParameter("email").trim() : "";
        String phone = request.getParameter("phone") != null ? request.getParameter("phone").trim() : "";

        boolean isValid = true;
        StringBuilder errorMsg = new StringBuilder();

        if (fullName.isEmpty()) {
            isValid = false;
            errorMsg.append("Họ và tên không được để trống. ");
        }

        if (email.isEmpty()) {
            isValid = false;
            errorMsg.append("Email không được để trống. ");
        } else if (!isValidEmail(email)) {
            isValid = false;
            errorMsg.append("Email không hợp lệ. ");
        }

        if (phone.isEmpty()) {
            isValid = false;
            errorMsg.append("Số điện thoại không được để trống. ");
        } else if (!isValidPhone(phone)) {
            isValid = false;
            errorMsg.append("Số điện thoại không hợp lệ. ");
        }

        if (!isValid) {
            session.setAttribute("error", errorMsg.toString());
            response.sendRedirect(request.getContextPath() + "/edit-profile");  // về lại trang edit-profile để sửa
            return;
        }

        UserDAO userDAO = new UserDAO();
        boolean updated = userDAO.updateUser1(currentUser.getUserId(), fullName, email, phone);

        if (updated) {
            currentUser.setFullName(fullName);
            currentUser.setEmail(email);
            currentUser.setPhone(phone);

            session.setAttribute("user", currentUser);
            session.setAttribute("message", "Cập nhật thông tin thành công!");
        } else {
            session.setAttribute("error", "Cập nhật thông tin thất bại. Vui lòng thử lại.");
        }

        response.sendRedirect(request.getContextPath() + "/profile");
    }

    @Override
    public String getServletInfo() {
        return "UpdateProfileController - cập nhật thông tin cá nhân";
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[0-9]{9,11}$";
        return Pattern.matches(phoneRegex, phone);
    }

}
