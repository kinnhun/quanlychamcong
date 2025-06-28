package conntroller.employee;

import dal.HolidayDateDAO;
import model.HolidayDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EmployeeHolidayViewServlet", urlPatterns = {"/employee/holidays"})
public class EmployeeHolidayViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy tham số filter & paging
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String keyword = request.getParameter("keyword");
        int page = 1;
        int pageSize = 10;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
        } catch (Exception e) {
        }

        HolidayDateDAO dao = new HolidayDateDAO();
        int[] totalRows = {0}; // Truyền mảng để nhận tổng số dòng

        // Lấy danh sách ngày nghỉ lễ đã lọc + phân trang
        List<HolidayDate> holidayList = dao.getHolidayDatesPaging(year, month, keyword, page, pageSize, totalRows);

        int totalPage = (int) Math.ceil((double) totalRows[0] / pageSize);

        // Truyền dữ liệu sang JSP
        request.setAttribute("holidayList", holidayList);
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("keyword", keyword);
        request.setAttribute("page", page);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalRows", totalRows[0]);

        request.getRequestDispatcher("/view/employee/holiday_list.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Hiển thị danh sách ngày nghỉ lễ cho nhân viên (lọc & phân trang)";
    }
}
