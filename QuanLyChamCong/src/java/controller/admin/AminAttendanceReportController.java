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

        AttendanceDAO dao = new AttendanceDAO();
        List<Attendance> attendanceList = dao.filterByDate(fromDate, toDate, employeeId, status);

        UserDAO userDao = new UserDAO();
        List<Users> usersList = userDao.getAllUsers();

        // Dữ liệu cho biểu đồ
        int[] attendanceStatus = {0, 0, 0}; // 0 - Có mặt, 1 - Vắng, 2 - Tăng ca
        for (Attendance att : attendanceList) {
            if (att.getCheckinTime() != null && att.getCheckoutTime() != null) {
                attendanceStatus[0]++; // Có mặt
            } else if (att.getCheckinTime() == null && att.getCheckoutTime() == null) {
                attendanceStatus[1]++; // Vắng
            } else {
                attendanceStatus[2]++; // Tăng ca
            }
        }

        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("usersList", usersList);
        request.setAttribute("attendanceStatus", attendanceStatus);

        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("employeeId", employeeId);
        request.setAttribute("status", status);

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

        AttendanceDAO dao = new AttendanceDAO();
        List<Attendance> attendanceList = dao.filterByDate(fromDate, toDate, employeeId, status);

        // Tạo workbook và sheet Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance Report");

        // Tiêu đề cho các cột
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nhân Viên");
        headerRow.createCell(1).setCellValue("Ngày");
        headerRow.createCell(2).setCellValue("Giờ vào");
        headerRow.createCell(3).setCellValue("Giờ ra");
        headerRow.createCell(4).setCellValue("Trạng thái");

        // Dữ liệu chấm công
        int rowNum = 1;
        for (Attendance attendance : attendanceList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(attendance.getUser().getFullName());
            row.createCell(1).setCellValue(attendance.getDate().toString());
            row.createCell(2).setCellValue(attendance.getCheckinTime() != null ? attendance.getCheckinTime().toString() : "Chưa chấm công");
            row.createCell(3).setCellValue(attendance.getCheckoutTime() != null ? attendance.getCheckoutTime().toString() : "Chưa chấm công");
            
            String statusText = (attendance.getCheckinTime() != null && attendance.getCheckoutTime() != null) ? "Có mặt" :
                                (attendance.getCheckinTime() == null && attendance.getCheckoutTime() == null) ? "Vắng" :
                                "Tăng ca";
            row.createCell(4).setCellValue(statusText);
        }

        // Thiết lập header của file Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=attendance_report.xlsx");

        // Ghi nội dung workbook vào response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
