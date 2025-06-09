package controller.manager;

import dal.LeaveRequestDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.LeaveType;

@WebServlet(name = "ManagerLeaveTypeController", urlPatterns = {"/manager/leave-types"})
public class ManagerLeaveTypeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LeaveRequestDAO dao = new LeaveRequestDAO();
        List<LeaveType> leaveTypes = dao.getAllLeaveTypes();

        request.setAttribute("leaveTypes", leaveTypes);
        request.getRequestDispatcher("/view/manager/leave-type.jsp").forward(request, response);
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");

    LeaveRequestDAO dao = new LeaveRequestDAO();

    if ("add".equals(action)) {
        // Thêm mới loại nghỉ phép
        String leaveTypeName = request.getParameter("leaveTypeName");

        if (leaveTypeName != null && !leaveTypeName.trim().isEmpty()) {
            LeaveType newType = new LeaveType();
            newType.setLeaveTypeName(leaveTypeName.trim());
            newType.setStatus("active"); // mặc định active khi thêm mới

            boolean added = dao.addLeaveType(newType);

            if (added) {
                request.getSession().setAttribute("message", "Thêm loại nghỉ phép thành công!");
            } else {
                request.getSession().setAttribute("error", "Thêm loại nghỉ phép thất bại.");
            }
        } else {
            request.getSession().setAttribute("error", "Tên loại nghỉ phép không được để trống.");
        }

        response.sendRedirect(request.getContextPath() + "/manager/leave-types");

    } else if ("delete".equals(action)) {
        // Xóa loại nghỉ phép
        String idParam = request.getParameter("id");

        try {
            int id = Integer.parseInt(idParam);
            boolean deleted = dao.deleteLeaveType(id);

            if (deleted) {
                request.getSession().setAttribute("message", "Xóa loại nghỉ phép thành công!");
            } else {
                request.getSession().setAttribute("error", "Xóa loại nghỉ phép thất bại.");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "ID không hợp lệ.");
        }

        response.sendRedirect(request.getContextPath() + "/manager/leave-types");

    } else if ("toggleStatus".equals(action)) {
        // Cập nhật trạng thái
        String idParam = request.getParameter("id");

        try {
            int id = Integer.parseInt(idParam);
            boolean toggled = dao.toggleLeaveTypeStatus(id);

            if (toggled) {
                request.getSession().setAttribute("message", "Cập nhật trạng thái thành công!");
            } else {
                request.getSession().setAttribute("error", "Cập nhật trạng thái thất bại.");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "ID không hợp lệ.");
        }

        response.sendRedirect(request.getContextPath() + "/manager/leave-types");

    } else {
        // Không rõ action → quay về danh sách
        response.sendRedirect(request.getContextPath() + "/manager/leave-types");
    }
}


    @Override
    public String getServletInfo() {
        return "ManagerLeaveTypeController - Quản lý loại nghỉ phép";
    }
}
