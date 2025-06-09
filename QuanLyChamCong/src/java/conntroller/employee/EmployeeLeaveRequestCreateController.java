/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package conntroller.employee;

import dal.LeaveRequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import model.LeaveType;
import model.Users;

@WebServlet(name = "EmployeeLeaveRequestCreateController", urlPatterns = {"/employee/leave-request-create"})
public class EmployeeLeaveRequestCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            LeaveRequestDAO dao = new LeaveRequestDAO();
            List<LeaveType> leaveTypes = dao.getAllLeaveTypesActive();
            request.setAttribute("leaveTypes", leaveTypes);
            request.getRequestDispatcher("/view/employee/leave-request-create.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/employee/leave-requests");
        }
    }

  @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        Users user = (Users) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Đọc và parse dữ liệu từ form
        String startStr = request.getParameter("start_date");
        String endStr = request.getParameter("end_date");
        int leaveTypeId = Integer.parseInt(request.getParameter("leave_type")); 
        String reason = request.getParameter("reason");

        // Chuyển đổi sang LocalDate để tính toán
        LocalDate start = LocalDate.parse(startStr);
        LocalDate end = LocalDate.parse(endStr);

        // Tính số ngày nghỉ (bao gồm cả ngày đầu và ngày cuối)
        int daysCount = (int) (end.toEpochDay() - start.toEpochDay()) + 1;

        // Tạo DAO và gọi hàm tạo đơn
        LeaveRequestDAO dao = new LeaveRequestDAO();

        boolean success = dao.createRequest(
                user.getUserId(),
                Date.valueOf(start),
                Date.valueOf(end),
                leaveTypeId,
                reason,
                daysCount
        );

        // Gửi thông báo và điều hướng
        if (success) {
            request.getSession().setAttribute("message", " Đã gửi đơn xin nghỉ thành công.");
        } else {
            request.getSession().setAttribute("error", " Không thể gửi đơn xin nghỉ.");
        }

        response.sendRedirect(request.getContextPath() + "/employee/leave-request-create");

    } catch (Exception e) {
        e.printStackTrace();
        request.getSession().setAttribute("error", " Lỗi hệ thống khi gửi đơn xin nghỉ.");
        response.sendRedirect(request.getContextPath() + "/employee/leave-request-create");
    }
}


    @Override
    public String getServletInfo() {
        return "Tạo đơn xin nghỉ cho nhân viên";
    }
}
