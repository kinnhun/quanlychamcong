/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package conntroller.manager;

import dal.LeaveRequestDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.LeaveConfig;
import model.LeaveType;
import model.Users;

@WebServlet(name = "ManagerLeaveConfigAddController", urlPatterns = {"/manager/leave-config-add"})
public class ManagerLeaveConfigAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Load danh sách leaveTypes để đổ vào select box
        LeaveRequestDAO dao = new LeaveRequestDAO();
        List<LeaveType> leaveTypes = dao.getAllLeaveTypesActive();

        request.setAttribute("leaveTypes", leaveTypes);
        request.getRequestDispatcher("/view/manager/leave-config-add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Users currentUser = (Users) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int year = Integer.parseInt(request.getParameter("year"));
            int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
            int defaultDays = Integer.parseInt(request.getParameter("defaultDays"));

            LeaveConfig config = new LeaveConfig();
            config.setYear(year);

            LeaveRequestDAO dao = new LeaveRequestDAO();

            // Lấy LeaveType object
            LeaveType leaveType = dao.getLeaveTypeById(leaveTypeId);
            config.setLeaveTypeId(leaveType);

            config.setDefaultDays(defaultDays);
            config.setCreatedBy(currentUser);

            // Kiểm tra đã tồn tại cấu hình cho năm + loại phép chưa
            boolean checkExist = dao.getLeaveConfigByYearType(year, leaveType);

            if (checkExist) {
                // Nếu đã tồn tại → không cho thêm, báo lỗi
                session.setAttribute("error", "Cấu hình cho năm " + year + " và loại phép '" + leaveType.getLeaveTypeName() + "' đã tồn tại!");
            } else {
                // Nếu chưa tồn tại → thêm mới
                boolean success = dao.addConfig(config);

                if (success) {
                    session.setAttribute("message", "Thêm cấu hình thành công!");
                } else {
                    session.setAttribute("error", "Thêm cấu hình thất bại!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra!");
        }

        response.sendRedirect(request.getContextPath() + "/manager/leave-config");
    }

}
