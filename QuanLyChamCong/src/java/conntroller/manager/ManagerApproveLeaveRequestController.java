package conntroller.manager;

import dal.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Users;

import java.io.IOException;

@WebServlet(name = "ManagerApproveLeaveRequestController", urlPatterns = {"/manager/leave-requests-approve"})
public class ManagerApproveLeaveRequestController extends HttpServlet {

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
            request.getSession().setAttribute("error", "Bạn không có quyền duyệt đơn.");
            response.sendRedirect(request.getContextPath() + "/manager/leave-requests");
            return;
        }

        LeaveRequestDAO dao = new LeaveRequestDAO();
        dao.approveRequest(requestId, approver.getUserId(), note);

        request.getSession().setAttribute("message", "Đã phê duyệt đơn #" + requestId);
        response.sendRedirect(request.getContextPath() + "/manager/leave-requests");

    } catch (Exception e) {
        e.printStackTrace();
        request.getSession().setAttribute("error", "Có lỗi xảy ra trong quá trình phê duyệt.");
        response.sendRedirect(request.getContextPath() + "/manager/leave-requests");
    }
}



    @Override
    public String getServletInfo() {
        return "Servlet cho phép manager phê duyệt đơn xin nghỉ";
    }
}
