package controller.manager;

import dal.LeaveRequestDAO;
import model.LeaveConfig;
import model.LeaveType;
import model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerLeaveConfigEditController", urlPatterns = {"/manager/leave-config-edit"})
public class ManagerLeaveConfigEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            LeaveRequestDAO dao = new LeaveRequestDAO();
            LeaveConfig config = dao.getLeaveConfigById(id);
            List<LeaveType> leaveTypes = dao.getAllLeaveTypesActive(); // Để chọn loại phép

            request.setAttribute("config", config);
            request.setAttribute("leaveTypes", leaveTypes);

            request.getRequestDispatcher("/view/manager/leave-config-edit.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/manager/leave-config");
        }
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
            int configId = Integer.parseInt(request.getParameter("configId"));
            int year = Integer.parseInt(request.getParameter("year"));
            int leaveTypeId = Integer.parseInt(request.getParameter("leaveTypeId"));
            int defaultDays = Integer.parseInt(request.getParameter("defaultDays"));

            LeaveRequestDAO dao = new LeaveRequestDAO();
            LeaveType leaveType = dao.getLeaveTypeById(leaveTypeId);

            // Tạo object LeaveConfig để update
            LeaveConfig config = new LeaveConfig();
            config.setConfigId(configId);
            config.setYear(year);
            config.setLeaveTypeId(leaveType);
            config.setDefaultDays(defaultDays);

            boolean checkExist = dao.getLeaveConfigByYearType(year, leaveType);
            if (checkExist) {
                session.setAttribute("error", "Cấu hình cho năm " + year + " và loại phép '" + leaveType.getLeaveTypeName() + "' đã tồn tại!");
            } else {

                boolean success = dao.updateLeaveConfig(config);

                if (success) {
                    session.setAttribute("message", "Cập nhật cấu hình thành công!");
                } else {
                    session.setAttribute("error", "Cập nhật cấu hình thất bại!");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra!");
        }

        response.sendRedirect(request.getContextPath() + "/manager/leave-config");
    }

}
