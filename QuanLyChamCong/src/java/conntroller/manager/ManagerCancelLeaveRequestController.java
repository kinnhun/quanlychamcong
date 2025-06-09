package conntroller.manager;

import dal.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Users;
import java.io.IOException;

@WebServlet(name = "ManagerCancelLeaveRequestController", urlPatterns = {"/manager/leave-requests-cancel"})
public class ManagerCancelLeaveRequestController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idRaw = request.getParameter("id");
            String note = request.getParameter("note");

            if (idRaw == null || idRaw.isEmpty()) {
                request.getSession().setAttribute("error", "Thiếu ID đơn nghỉ.");
                response.sendRedirect(request.getContextPath() + "/manager/leave-requests");
                return;
            }

            int requestId = Integer.parseInt(idRaw);
            Users manager = (Users) request.getSession().getAttribute("user");

            if (manager == null || !"manager".equals(manager.getRole())) {
                request.getSession().setAttribute("error", "Bạn không có quyền thực hiện thao tác này.");
                response.sendRedirect(request.getContextPath() + "/manager/leave-requests");
                return;
            }

            LeaveRequestDAO dao = new LeaveRequestDAO();
            dao.cancelRequest(requestId, manager.getUserId(), note);

            request.getSession().setAttribute("message", "Đã hủy đơn #" + requestId);
            response.sendRedirect(request.getContextPath() + "/manager/leave-requests");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Có lỗi xảy ra khi hủy đơn.");
            response.sendRedirect(request.getContextPath() + "/manager/leave-requests");
        }
    }
}
