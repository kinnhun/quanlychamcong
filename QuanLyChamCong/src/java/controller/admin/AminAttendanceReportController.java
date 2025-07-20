package controller.admin;

import dal.AttendanceDAO;
import dal.UserDAO;
import model.Attendance;
import model.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

@WebServlet(name="AminAttendanceReportController", urlPatterns={"/admin/attendance-report"})
public class AminAttendanceReportController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String employeeId = request.getParameter("employeeId");
        String status = request.getParameter("status");

        // Bổ sung các tham số lọc mới
        String departmentId = request.getParameter("departmentId");
        String locationId = request.getParameter("locationId");
        String leaveType = request.getParameter("leaveType");
        String[] selectedColumns = request.getParameterValues("columns");

        AttendanceDAO dao = new AttendanceDAO();
        List<Attendance> attendanceList = dao.filterByDate(fromDate, toDate, employeeId, status);
        // TODO: Bạn có thể mở rộng filterByDate để nhận thêm departmentId, locationId nếu cần

        UserDAO userDao = new UserDAO();
        List<Users> usersList = userDao.getAllUsers();

        // Dữ liệu cho biểu đồ
        int[] attendanceStatus = {0, 0, 0}; // 0 - Có mặt, 1 - Vắng, 2 - Tăng ca
        for (Attendance att : attendanceList) {
            if (att.getCheckinTime() != null && att.getCheckoutTime() != null) {
                attendanceStatus[0]++;
            } else if (att.getCheckinTime() == null && att.getCheckoutTime() == null) {
                attendanceStatus[1]++;
            } else {
                attendanceStatus[2]++;
            }
        }

        // Gửi dữ liệu về cho JSP
        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("usersList", usersList);
        request.setAttribute("attendanceStatus", attendanceStatus);

        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("employeeId", employeeId);
        request.setAttribute("status", status);

        // Bổ sung dữ liệu lọc
        request.setAttribute("departmentId", departmentId);
        request.setAttribute("locationId", locationId);
        request.setAttribute("leaveType", leaveType);
        request.setAttribute("selectedColumns", selectedColumns);

        request.getRequestDispatcher("/view/admin/attendance-report.jsp").forward(request, response);
    }

    // Hàm xử lý xuất file Excel
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String employeeId = request.getParameter("employeeId");
        String status = request.getParameter("status");

        // Lọc mở rộng (giống doGet)
        String departmentId = request.getParameter("departmentId");
        String locationId = request.getParameter("locationId");
        String leaveType = request.getParameter("leaveType");
        String[] selectedColumns = request.getParameterValues("columns");

        AttendanceDAO dao = new AttendanceDAO();
        List<Attendance> attendanceList = dao.filterByDate(fromDate, toDate, employeeId, status);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance Report");

        // Tiêu đề cột động theo selectedColumns
        Row headerRow = sheet.createRow(0);
        int colIndex = 0;
        if (selectedColumns == null || selectedColumns.length == 0) {
            selectedColumns = new String[] {"employee", "date", "checkin", "checkout", "status"};
        }
        for (String col : selectedColumns) {
            switch (col) {
                case "employee":
                    headerRow.createCell(colIndex++).setCellValue("Nhân Viên");
                    break;
                case "date":
                    headerRow.createCell(colIndex++).setCellValue("Ngày");
                    break;
                case "checkin":
                    headerRow.createCell(colIndex++).setCellValue("Giờ vào");
                    break;
                case "checkout":
                    headerRow.createCell(colIndex++).setCellValue("Giờ ra");
                    break;
                case "status":
                    headerRow.createCell(colIndex++).setCellValue("Trạng thái");
                    break;
            }
        }

        // Ghi dữ liệu
        int rowNum = 1;
        for (Attendance attendance : attendanceList) {
            Row row = sheet.createRow(rowNum++);
            int dataCol = 0;
            for (String col : selectedColumns) {
                switch (col) {
                    case "employee":
                        row.createCell(dataCol++).setCellValue(attendance.getUser().getFullName());
                        break;
                    case "date":
                        row.createCell(dataCol++).setCellValue(attendance.getDate().toString());
                        break;
                    case "checkin":
                        row.createCell(dataCol++).setCellValue(attendance.getCheckinTime() != null ? attendance.getCheckinTime().toString() : "Chưa chấm");
                        break;
                    case "checkout":
                        row.createCell(dataCol++).setCellValue(attendance.getCheckoutTime() != null ? attendance.getCheckoutTime().toString() : "Chưa chấm");
                        break;
                    case "status":
                        String statusText = (attendance.getCheckinTime() != null && attendance.getCheckoutTime() != null) ? "Có mặt" :
                                            (attendance.getCheckinTime() == null && attendance.getCheckoutTime() == null) ? "Vắng" :
                                            "Tăng ca";
                        row.createCell(dataCol++).setCellValue(statusText);
                        break;
                }
            }
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=attendance_report.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
