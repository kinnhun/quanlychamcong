package controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import dal.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Users;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@WebServlet(name = "Oauth2HandlerController", urlPatterns = {"/oauth2handler"})
public class Oauth2HandlerController extends HttpServlet {

    private static final String CLIENT_ID = "20495276859-asgm8cn4636ehlrsktoc6klk7ldujrp5.apps.googleusercontent.com";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idTokenString = request.getParameter("credential");

        if (idTokenString == null || idTokenString.isEmpty()) {
            request.getSession().setAttribute("error", "Không nhận được token từ Google.");
            response.sendRedirect("login");
            return;
        }

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");

                UserDAO userDAO = new UserDAO();
                Users user = userDAO.findByEmail(email);

                HttpSession session = request.getSession();

                if (user == null) {
                    request.getSession().setAttribute("error", "Email không tồn tại");

                    response.sendRedirect("login");
                    return;
                }

                session.setAttribute("user", user);
                session.setAttribute("message", "Đăng nhập bằng Google thành công!");

                switch (user.getRole()) {
                    case "admin":
                        response.sendRedirect("admin-dashboard");
                        break;
                    case "manager":
                        response.sendRedirect("manager-dashboard");
                        break;
                    default:
                        response.sendRedirect("employee-dashboard");
                        break;
                }

            } else {
                request.getSession().setAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
                response.sendRedirect("login");
            }

        } catch (GeneralSecurityException e) {
            throw new ServletException("Lỗi xác thực với Google.", e);
        }
    }
}
