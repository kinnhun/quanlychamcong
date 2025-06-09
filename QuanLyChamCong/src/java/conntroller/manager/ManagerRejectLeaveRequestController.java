package conntroller.manager;

import dal.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;

import java.io.IOException;
@WebServlet(name = "ManagerRejectLeaveRequestController", urlPatterns = {"/manager/leave-requests-reject"})
public class ManagerRejectLeaveRequestController extends HttpServlet {

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
            Users approver = (Users) request.getSession().getAttribute("user");

            if (approver == null || !"manager".equals(approver.getRole())) {
                request.getSession().setAttribute("error", "Bạn không có quyền từ chối đơn.");
                response.sendRedirect(request.getContextPath() + "/manager/leave-requests");
                return;
            }

            LeaveRequestDAO dao = new LeaveRequestDAO();
            dao.rejectRequest(requestId, approver.getUserId(), note);

            request.getSession().setAttribute("message", "Đã từ chối đơn #" + requestId);
            response.sendRedirect(request.getContextPath() + "/manager/leave-requests");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Có lỗi xảy ra trong quá trình từ chối.");
            response.sendRedirect(request.getContextPath() + "/manager/leave-requests");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet cho phép manager từ chối đơn xin nghỉ";
    }
}
