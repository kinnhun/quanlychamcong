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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import model.LeaveRequest;
import model.Users;

@WebServlet(name = "ManagerLeaveRequestListController", urlPatterns = {"/manager/leave-requests"})
public class ManagerLeaveRequestListController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ManagerLeaveRequestListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManagerLeaveRequestListController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Users user = (Users) session.getAttribute("user");

        LeaveRequestDAO dao = new LeaveRequestDAO();
        List<LeaveRequest> list = dao.getAllRequestsByManager(user.getUserId());

        // Xử lý bộ lọc
        Set<String> userNames = new LinkedHashSet<>();
        Set<String> deptNames = new LinkedHashSet<>();
        for (LeaveRequest r : list) {
            userNames.add(r.getUser().getFullName());
            if (r.getDepartments() != null && r.getDepartments().getDepartmentName() != null) {
                deptNames.add(r.getDepartments().getDepartmentName());
            }
        }

        // Xử lý lọc theo query param
        String filterUser = request.getParameter("filterUser");
        String filterDept = request.getParameter("filterDepartment");
        String filterStatus = request.getParameter("filterStatus");

        List<LeaveRequest> filteredList = new ArrayList<>();
        for (LeaveRequest r : list) {
            boolean match = true;
            if (filterUser != null && !filterUser.isEmpty() && !r.getUser().getFullName().equals(filterUser)) {
                match = false;
            }
            if (filterDept != null && !filterDept.isEmpty()) {
                String deptName = (r.getDepartments() != null) ? r.getDepartments().getDepartmentName() : "";
                if (!deptName.equals(filterDept)) {
                    match = false;
                }
            }
            if (filterStatus != null && !filterStatus.isEmpty()) {
                // Chuyển đổi status trong DB về label filter
                String statusLabel = "";
                switch (r.getStatus()) {
                    case "approved":
                        statusLabel = "Đã duyệt";
                        break;
                    case "pending":
                        statusLabel = "Đang chờ";
                        break;
                    case "rejected":
                        statusLabel = "Từ chối";
                        break;
                    case "cancelled":
                        statusLabel = "Đã hủy";
                        break;
                    default:
                        statusLabel = r.getStatus();
                }
                if (!statusLabel.equals(filterStatus)) {
                    match = false;
                }
            }
            if (match) {
                filteredList.add(r);
            }
        }

        request.setAttribute("userNames", userNames);
        request.setAttribute("deptNames", deptNames);
        request.setAttribute("requests", filteredList);
        request.setAttribute("filterUser", filterUser);
        request.setAttribute("filterDepartment", filterDept);
        request.setAttribute("filterStatus", filterStatus);

        request.getRequestDispatcher("/view/manager/leave_requests.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
