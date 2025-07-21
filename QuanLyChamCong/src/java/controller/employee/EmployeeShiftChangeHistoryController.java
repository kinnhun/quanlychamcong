/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.employee;

import dal.ShiftDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.ShiftChangeRequest;
import model.Users;


@WebServlet(name = "EmployeeShiftChangeHistoryController", urlPatterns = {"/employee/shift-change-history"})
public class EmployeeShiftChangeHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Users account = (Users) req.getSession().getAttribute("user");
        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        ShiftDAO dao = new ShiftDAO();
        List<ShiftChangeRequest> requests = dao.getShiftChangeRequestsByUserId(account.getUserId());
        req.setAttribute("requests", requests);
        req.getRequestDispatcher("/view/employee/shift-change-history.jsp").forward(req, resp);
    }
}

