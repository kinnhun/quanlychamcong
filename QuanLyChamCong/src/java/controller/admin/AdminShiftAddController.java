package controller.admin;

import dal.ShiftDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Shift;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;

@WebServlet(name = "AdminShiftAddController", urlPatterns = {"/admin/shifts-add"})
public class AdminShiftAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/admin/shifts_add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            String name = request.getParameter("shift_name");
            Time start = Time.valueOf(request.getParameter("start_time") + ":00");
            Time end = Time.valueOf(request.getParameter("end_time") + ":00");
            String desc = request.getParameter("description");

            Shift s = new Shift();
            s.setShiftName(name);
            s.setStartTime(start);
            s.setEndTime(end);
            s.setDescription(desc);
            s.setCreatedAt(new java.sql.Timestamp(new Date().getTime()));

            ShiftDAO dao = new ShiftDAO();
            boolean inserted = dao.insert(s);

            if (inserted) {
                request.getSession().setAttribute("message", "Thêm ca làm thành công!");
            } else {
                request.getSession().setAttribute("error", "Thêm ca làm thất bại!");
            }
            response.sendRedirect(request.getContextPath() + "/admin/shifts");

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi hệ thống!");
            response.sendRedirect(request.getContextPath() + "/admin/shifts");
        }
    }
}
