package controller;

import dal.NotificationDAO;
import model.Notification;
import model.Users;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "NotificationUserApiServlet", urlPatterns = {"/NotificationUserApiServlet"})
public class NotificationUserApiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users user = (Users) request.getSession().getAttribute("user");
        response.setContentType("application/json;charset=UTF-8");
        if (user == null) {
            response.getWriter().write("[]");
            return;
        }
        NotificationDAO dao = new NotificationDAO();
        List<Notification> list = dao.getNotificationsByReceiver(user.getUserId(), 10);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            Notification n = list.get(i);
            if (i > 0) sb.append(",");
            sb.append("{")
                .append("\"title\":\"").append(escapeJson(n.getTitle())).append("\",")
                .append("\"content\":\"").append(escapeJson(n.getContent())).append("\",")
                .append("\"createdAt\":\"").append(n.getCreatedAt()).append("\",")
                .append("\"receiverStatus\":\"").append(escapeJson(n.getStatus())).append("\"")
                .append("}");
        }
        sb.append("]");
        response.getWriter().write(sb.toString());
    }

    private String escapeJson(String str) {
        return str == null ? "" : str.replace("\"", "\\\"").replace("\n", " ").replace("\r", "");
    }
}
