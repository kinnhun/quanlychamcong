package controller.admin;

import dal.HolidayDateDAO;
import model.HolidayDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "AdminHolidayDateController", urlPatterns = {"/admin/holiday-dates"})
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB
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

            if (name == null || dateStr == null || yearStr == null
                    || name.trim().isEmpty() || dateStr.trim().isEmpty() || yearStr.trim().isEmpty()) {
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

        } else if ("import".equals(action)) {
            System.out.println("XmlBeans loaded from: "
                    + org.apache.xmlbeans.XmlBeans.class.getProtectionDomain().getCodeSource().getLocation());

            try {
                Part filePart = request.getPart("excelFile");
                InputStream inputStream = filePart.getInputStream();
                Workbook workbook = new XSSFWorkbook(inputStream);
                Sheet sheet = workbook.getSheetAt(0);

                int imported = 0, failed = 0;
                List<String> errorRows = new ArrayList<>();
                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ dòng đầu
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }

                    try {
                        String name = getCellString(row, 0);
                        String dateStr = getCellString(row, 1); // yyyy-MM-dd
                        String yearStr = getCellString(row, 2);

                        if (name == null || dateStr == null || yearStr == null) {
                            failed++;
                            errorRows.add("Dòng " + (i + 1) + ": Thiếu dữ liệu");
                            continue;
                        }
                        Date date = Date.valueOf(dateStr.trim());
                        int year = Integer.parseInt(yearStr.trim());

                        // Check trùng
                        if (dao.isDuplicateHoliday(name.trim(), date, year)) {
                            failed++;
                            errorRows.add("Dòng " + (i + 1) + ": Trùng tên/ngày/năm");
                            continue;
                        }

                        HolidayDate hd = new HolidayDate();
                        hd.setHolidayName(name.trim());
                        hd.setHolidayDate(date);
                        hd.setYear(year);

                        boolean added = dao.addHolidayDate(hd);
                        if (added) {
                            imported++;
                        } else {
                            failed++;
                            errorRows.add("Dòng " + (i + 1) + ": Lỗi khi thêm vào DB");
                        }
                    } catch (Exception ex) {
                        failed++;
                        errorRows.add("Dòng " + (i + 1) + ": Dữ liệu không hợp lệ");
                    }
                }
                workbook.close();
                inputStream.close();
                StringBuilder msg = new StringBuilder("Import thành công " + imported + " dòng"
                        + (failed > 0 ? (", thất bại " + failed + " dòng.") : "."));
                if (!errorRows.isEmpty()) {
                    msg.append("<br>Chi tiết lỗi:<br>");
                    for (String err : errorRows) {
                        msg.append(err).append("<br>");
                    }
                }
                request.getSession().setAttribute("message", msg.toString());
            } catch (Exception ex) {
                request.getSession().setAttribute("error", "Import file không thành công!");
            }
            response.sendRedirect(request.getContextPath() + "/admin/holiday-dates");
        } else {
            // Không rõ action
            response.sendRedirect(request.getContextPath() + "/admin/holiday-dates");
        }
    }

    private String getCellString(Row row, int idx) {
        if (row == null) {
            return null;
        }
        Cell cell = row.getCell(idx);
        if (cell == null) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    @Override
    public String getServletInfo() {
        return "AdminHolidayDateController - Quản lý ngày nghỉ lễ";
    }
}
