package controller.manager;

import dal.AttendanceDisputeDAO;
import model.AttendanceDispute;
import model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ManagerDisputeListController", urlPatterns = {"/manager/dispute-list"})
public class ManagerDisputeListController extends HttpServlet {

    private static final int PAGE_SIZE = 10; // Số bản ghi mỗi trang

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Users manager = (Users) session.getAttribute("user");
        AttendanceDisputeDAO dao = new AttendanceDisputeDAO();

        // Lấy tham số tìm kiếm và lọc
        String search = request.getParameter("search") != null ? request.getParameter("search").trim() : "";
        String status = request.getParameter("status") != null ? request.getParameter("status").trim() : "";
        String createdDate = request.getParameter("createdDate") != null ? request.getParameter("createdDate").trim() : "";
        
        // Xử lý ngày tạo
        Date parsedDate = null;
        if (!createdDate.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                parsedDate = sdf.parse(createdDate);
            } catch (Exception e) {
                parsedDate = null;
            }
        }

        // Lấy tham số phân trang
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            if (page < 1) page = 1;
        } catch (NumberFormatException e) {
            page = 1;
        }

        // Lấy danh sách khiếu nại với tìm kiếm, lọc và phân trang
        List<AttendanceDispute> disputeList = dao.getDisputesByManagerWithFilters(
                manager.getUserId(), search, status, parsedDate, page, PAGE_SIZE);
        int totalRecords = dao.getTotalDisputesByManagerWithFilters(
                manager.getUserId(), search, status, parsedDate);
        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);

        // Lấy danh sách nhân viên
        List<Users> employeeList = dao.getEmployeesByManager(manager.getUserId());

        // Đặt các thuộc tính cho JSP
        request.setAttribute("disputeList", disputeList);
        request.setAttribute("employeeList", employeeList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalRecords", totalRecords);
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("createdDate", createdDate);

        request.getRequestDispatcher("/view/manager/dispute_list.jsp").forward(request, response);
    }
}