package controller.employee;

import dal.ShiftDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.ShiftChangeRequest;
import model.Shift;
import model.Users;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "EmployeeShiftChangeRequestController", urlPatterns = {"/employee/shift-change-request"})
public class EmployeeShiftChangeRequestController extends HttpServlet {

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       
            ShiftDAO shiftDAO = new ShiftDAO();
            List<Shift> allShifts = shiftDAO.getAllShift();

            req.setAttribute("shifts", allShifts);
            req.getRequestDispatcher("/view/employee/shift-change-form.jsp").forward(req, resp);
      
    }

   @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    try {
        int userId = Integer.parseInt(req.getParameter("userId"));
        int fromShiftId = Integer.parseInt(req.getParameter("fromShiftId"));
        int toShiftId = Integer.parseInt(req.getParameter("toShiftId"));
        String dateStr = req.getParameter("date");
        String reason = req.getParameter("reason");

        // Validate đầu vào
        if (dateStr == null || dateStr.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/employee/shift-change-form.jsp?error=missing_date");
            return;
        }

        ShiftChangeRequest request = new ShiftChangeRequest();
        request.setUserId(new Users(userId));
        request.setFromShiftId(new Shift(fromShiftId));
        request.setToShiftId(new Shift(toShiftId));
        request.setDate(Date.valueOf(dateStr));
        request.setReason(reason);
        request.setStatus("pending");

        ShiftDAO shiftDAO = new ShiftDAO();
        boolean inserted = shiftDAO.insertShiftChangeRequest(request);

        if (inserted) {
            resp.sendRedirect(req.getContextPath() + "/employee/shift-change-history?success=true");
        } else {
            resp.sendRedirect(req.getContextPath() + "/employee/shift-change-form?error=insert_failed");
        }

    } catch (Exception e) {
        e.printStackTrace();
        resp.sendRedirect(req.getContextPath() + "/employee/shift-change-form?error=true");
    }
}

}
