package controller.employee;

import dal.LeaveRequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.LeaveRequest;

@WebServlet(name = "EmployeeLeaveRequestDetailController", urlPatterns = {"/employee/leave-request-detail"})
public class EmployeeLeaveRequestDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int requestId = Integer.parseInt(request.getParameter("id"));
            LeaveRequestDAO dao = new LeaveRequestDAO();
            LeaveRequest lr = dao.getRequestById(requestId);

            if (lr == null) {
                request.getSession().setAttribute("error", "Không tìm thấy đơn xin nghỉ.");
                response.sendRedirect(request.getContextPath() + "/employee/leave-requests");
                return;
            }

            request.setAttribute("requestDetail", lr);
            request.getRequestDispatcher("/view/employee/leave-request-detail.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/employee/leave-requests");
        }
    }
}
