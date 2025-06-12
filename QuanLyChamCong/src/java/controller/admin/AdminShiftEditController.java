package controller.admin;

import dal.ShiftDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Shift;

import java.io.IOException;
import java.sql.Time;

@WebServlet(name = "AdminShiftEditController", urlPatterns = {"/admin/shifts-edit"})
public class AdminShiftEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ShiftDAO dao = new ShiftDAO();
        Shift shift = dao.getShiftById(id);

        if (shift == null) {
            request.getSession().setAttribute("error", "Không tìm thấy ca làm.");
            response.sendRedirect(request.getContextPath() + "/admin/shifts");
            return;
        }

        request.setAttribute("shift", shift);
        request.getRequestDispatcher("/view/admin/shift_edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("shiftId"));
            String name = request.getParameter("shiftName");
            Time start = Time.valueOf(request.getParameter("startTime"));
            Time end = Time.valueOf(request.getParameter("endTime"));
            String desc = request.getParameter("description");

            Shift shift = new Shift(id, name, start, end, desc, null);
            ShiftDAO dao = new ShiftDAO();

            boolean success = dao.updateShift(shift);

            if (success) {
                request.getSession().setAttribute("message", "Cập nhật ca làm thành công!");
            } else {
                request.getSession().setAttribute("error", "Cập nhật thất bại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Có lỗi xảy ra.");
        }

        response.sendRedirect(request.getContextPath() + "/admin/shifts");
    }
}
