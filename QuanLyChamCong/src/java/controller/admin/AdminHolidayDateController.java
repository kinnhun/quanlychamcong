package controller.admin;

import dal.HolidayDateDAO;
import model.HolidayDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminHolidayDateController", urlPatterns = {"/admin/holiday-dates"})
public class AdminHolidayDateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String keyword = request.getParameter("keyword");
        int page = 1, pageSize = 10;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignore) {
        }
        try {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        } catch (Exception ignore) {
        }

        HolidayDateDAO dao = new HolidayDateDAO();
        int[] totalRows = {0};
        List<HolidayDate> holidays = dao.getHolidayDatesPaging(year, month, keyword, page, pageSize, totalRows);

        int totalPages = (int) Math.ceil((double) totalRows[0] / pageSize);

        request.setAttribute("holidays", holidays);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);

        // Để giữ lại filter khi submit lại trang (dùng HashMap thay vì Map.of)
        Map<String, String> filter = new HashMap<>();
        if (year != null && !year.isEmpty()) {
            filter.put("year", year);
        }
        if (month != null && !month.isEmpty()) {
            filter.put("month", month);
        }
        if (keyword != null && !keyword.isEmpty()) {
            filter.put("keyword", keyword);
        }
        request.setAttribute("filter", filter);

        request.getRequestDispatcher("/view/admin/holiday-dates.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HolidayDateDAO dao = new HolidayDateDAO();

        if ("add".equals(action)) {
            // Thêm ngày nghỉ lễ
            String name = request.getParameter("holidayName");
            String dateStr = request.getParameter("holidayDate");
            String yearStr = request.getParameter("year");

            if (name == null || dateStr == null || yearStr == null || name.trim().isEmpty() || dateStr.trim().isEmpty() || yearStr.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            } else {
                try {
                    Date date = Date.valueOf(dateStr);
                    int year = Integer.parseInt(yearStr);
                    HolidayDate hd = new HolidayDate();
                    hd.setHolidayName(name.trim());
                    hd.setHolidayDate(date);
                    hd.setYear(year);

                    boolean added = dao.addHolidayDate(hd);

                    if (added) {
                        request.getSession().setAttribute("message", "Thêm ngày nghỉ lễ thành công!");
                    } else {
                        request.getSession().setAttribute("error", "Thêm ngày nghỉ lễ thất bại!");
                    }
                } catch (Exception e) {
                    request.getSession().setAttribute("error", "Dữ liệu không hợp lệ!");
                }
            }
            response.sendRedirect(request.getContextPath() + "/admin/holiday-dates");

        } else if ("delete".equals(action)) {
            // Xóa ngày nghỉ lễ
            String idParam = request.getParameter("id");
            try {
                int id = Integer.parseInt(idParam);
                boolean deleted = dao.deleteHolidayDate(id);

                if (deleted) {
                    request.getSession().setAttribute("message", "Xóa ngày nghỉ lễ thành công!");
                } else {
                    request.getSession().setAttribute("error", "Xóa ngày nghỉ lễ thất bại!");
                }
            } catch (NumberFormatException e) {
                request.getSession().setAttribute("error", "ID không hợp lệ!");
            }
            response.sendRedirect(request.getContextPath() + "/admin/holiday-dates");

        } else {
            // Không rõ action
            response.sendRedirect(request.getContextPath() + "/admin/holiday-dates");
        }
    }

    @Override
    public String getServletInfo() {
        return "AdminHolidayDateController - Quản lý ngày nghỉ lễ";
    }
}
