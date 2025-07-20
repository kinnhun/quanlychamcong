/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb
fs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import com.google.gson.Gson;
import dal.ShiftDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import model.Shift;

@WebServlet(name = "AdminDashboardController", urlPatterns = {"/admin/dashboard"})
public class AdminDashboardController extends HttpServlet {

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
            out.println("<title>Servlet AdminDashboardController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminDashboardController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Lấy year và week động dựa trên ngày hiện tại
     *
     * @return Map chứa year và week (định dạng yyyy-Www)
     */
    private Map<String, String> getCurrentYearAndWeek() {
        LocalDate today = LocalDate.now(); // Lấy ngày hiện tại
        int year = today.getYear(); // Lấy năm
        int week = today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR); // Lấy tuần theo ISO
        Map<String, String> result = new HashMap<>();
        result.put("year", String.valueOf(year));
        result.put("week", String.format("%d-W%02d", year, week));
        return result;
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
        // lịch ca làm chấm công
        ShiftDAO sdao = new ShiftDAO();
        List<Shift> listShift = sdao.getAllShift();
        request.setAttribute("listShift", listShift);
        // tình trạng nhân viên 
        // tổng số nhân viên 
        int totalEmployees = sdao.getTotalEmployees();
        request.setAttribute("totalEmployees", totalEmployees);

        // Lấy year và week động
        Map<String, String> yearAndWeek = getCurrentYearAndWeek();
        String year = yearAndWeek.get("year"); // Ví dụ: "2025"
        String week = yearAndWeek.get("week"); // Ví dụ: "2025-W26"

        Map<String, Integer> shiftsPerMonth = sdao.getShiftsPerMonth(year);
        request.setAttribute("shiftsPerMonth", shiftsPerMonth);

        // Số nhân viên làm việc hôm nay
        int todayWorkingEmployees = sdao.getTodayWorkingEmployees();
        request.setAttribute("todayWorkingEmployees", todayWorkingEmployees);

        // Số nhân viên làm việc mỗi ngày trong tuần hiện tại
        int[] employeesPerDay = sdao.getEmployeesPerDayInWeek(week);
        request.setAttribute("employeesPerDay", employeesPerDay);

        List<String> departmentNames = sdao.getAllDepartmentNames();
        List<Integer> lateAndLeaveCounts = sdao.getLateAndLeaveByDepartment(); // Map hoặc 2 mảng song song

        request.setAttribute("departmentNames", new Gson().toJson(departmentNames));
        request.setAttribute("lateAndLeaveCounts", new Gson().toJson(lateAndLeaveCounts));

        request.setAttribute("lastMonthLate", sdao.getLateCount("2025-06"));
        request.setAttribute("lastMonthLeave", sdao.getLeaveCount("2025-06"));
        request.setAttribute("lastMonthWorkdays", sdao.getWorkingDays("2025-06"));

        request.setAttribute("thisMonthLate", sdao.getLateCount("2025-07"));
        request.setAttribute("thisMonthLeave", sdao.getLeaveCount("2025-07"));
        request.setAttribute("thisMonthWorkdays", sdao.getWorkingDays("2025-07"));

        Map<String, Integer> lateByMonth = sdao.getLateCountsByMonth();
        Map<String, Integer> leaveByMonth = sdao.getLeaveCountsByMonth();
        Map<String, Integer> workdaysByMonth = sdao.getWorkingDaysByMonth();

        Gson gson = new Gson();
        request.setAttribute("months", gson.toJson(lateByMonth.keySet()));
        request.setAttribute("lateChartData", gson.toJson(lateByMonth.values()));
        request.setAttribute("leaveChartData", gson.toJson(leaveByMonth.values()));
        request.setAttribute("workChartData", gson.toJson(workdaysByMonth.values()));

        request.getRequestDispatcher("/view/admin/dashboard.jsp").forward(request, response);
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
