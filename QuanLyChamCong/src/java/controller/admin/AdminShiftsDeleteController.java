package controller.admin;

import dal.ShiftDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "AdminShiftsDeleteController", urlPatterns = {"/admin/shifts-delete"})
public class AdminShiftsDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int shiftId = Integer.parseInt(request.getParameter("id"));
            ShiftDAO dao = new ShiftDAO();

            boolean success = dao.delete(shiftId);
            if (success) {
                request.getSession().setAttribute("message", "Xóa ca làm thành công!");
            } else {
                request.getSession().setAttribute("error", "Xóa ca làm thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi xóa ca làm!");
        }

        response.sendRedirect(request.getContextPath() + "/admin/shifts");
    }
}
