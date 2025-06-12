package controller.admin;

import dal.ShiftDAO;
import model.Shift;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminShiftListController", urlPatterns = {"/admin/shifts"})
public class AdminShiftListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ShiftDAO dao = new ShiftDAO();
            List<Shift> shiftList = dao.getAll();

            request.setAttribute("shiftList", shiftList);
            request.getRequestDispatcher("/view/admin/shifts.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Lỗi khi tải danh sách ca làm.");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }
}
