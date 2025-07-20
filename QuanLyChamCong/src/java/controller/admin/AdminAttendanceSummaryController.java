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

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet(name = "AdminAttendanceSummaryController", urlPatterns = {"/admin/attendance-report1"})
public class AdminAttendanceSummaryController extends HttpServlet {

    private Map<Integer, double[]> buildSummaryMap(List<Users> usersList, AttendanceDAO dao, String fromDate, String toDate) {
        Map<Integer, double[]> summaryMap = new HashMap<>();

        for (Users u : usersList) {
            List<Attendance> userRecords = dao.filterByDate(fromDate, toDate, String.valueOf(u.getUserId()), null);
            double totalHours = 0;
            int late = 0, early = 0, workingDays = 0;

            for (Attendance record : userRecords) {
                Timestamp checkin = record.getCheckinTime();
                Timestamp checkout = record.getCheckoutTime();

                if (checkin != null && checkout != null) {
                    Duration duration = Duration.between(checkin.toLocalDateTime(), checkout.toLocalDateTime());
                    totalHours += duration.toMinutes() / 60.0;
                    workingDays++;

                    LocalTime checkinTime = checkin.toLocalDateTime().toLocalTime();
                    LocalTime checkoutTime = checkout.toLocalDateTime().toLocalTime();

                    if (checkinTime.isAfter(LocalTime.of(8, 15))) {
                        late++;
                    }
                    if (checkoutTime.isBefore(LocalTime.of(17, 0))) {
                        early++;
                    }
                }
            }

            int leaveWith = dao.countLeave(u.getUserId(), fromDate, toDate, true);
            int leaveWithout = dao.countLeave(u.getUserId(), fromDate, toDate, false);

            summaryMap.put(u.getUserId(), new double[]{
                totalHours, late, early, leaveWith, leaveWithout, workingDays
            });
        }

        return summaryMap;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String employeeId = request.getParameter("employeeId");

            AttendanceDAO dao = new AttendanceDAO();
            UserDAO userDao = new UserDAO();

            List<Attendance> attendanceList = dao.filterByDate(fromDate, toDate, employeeId, null);
            List<Users> usersList = userDao.getAllUsers();

            int[] attendanceStatus = {0, 0, 0};
            for (Attendance att : attendanceList) {
                if (att.getCheckinTime() != null && att.getCheckoutTime() != null) {
                    attendanceStatus[0]++;
                } else if (att.getCheckinTime() == null && att.getCheckoutTime() == null) {
                    attendanceStatus[1]++;
                } else {
                    attendanceStatus[2]++;
                }
            }

            Map<Integer, double[]> summaryMap = buildSummaryMap(usersList, dao, fromDate, toDate);

            request.setAttribute("attendanceList", attendanceList);
            request.setAttribute("usersList", usersList);
            request.setAttribute("attendanceStatus", attendanceStatus);
            request.setAttribute("summaryMap", summaryMap);
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
            request.setAttribute("employeeId", employeeId);

            request.getRequestDispatcher("/view/admin/attendance-report1.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra khi xử lý dữ liệu.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String employeeId = request.getParameter("employeeId");

            AttendanceDAO dao = new AttendanceDAO();
            UserDAO userDao = new UserDAO();

            List<Users> usersList;
            if (employeeId != null && !employeeId.isEmpty()) {
                Users selectedUser = userDao.getUserById(Integer.parseInt(employeeId));
                usersList = selectedUser != null ? Collections.singletonList(selectedUser) : new ArrayList<>();
            } else {
                usersList = userDao.getAllUsers();
            }

            Map<Integer, double[]> summaryMap = buildSummaryMap(usersList, dao, fromDate, toDate);

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=attendance_summary.xlsx");

            try (XSSFWorkbook workbook = new XSSFWorkbook(); java.io.OutputStream out = response.getOutputStream()) {

                Sheet sheet = workbook.createSheet("Báo cáo tổng hợp");
                String[] headers = {"STT", "Tên nhân viên", "Tổng giờ làm", "Đi muộn", "Về sớm", "Nghỉ có phép", "Nghỉ không phép", "Ngày công"};

                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                int rowNum = 1;
                int stt = 1;
                for (Users u : usersList) {
                    double[] summary = summaryMap.get(u.getUserId());
                    if (summary == null) {
                        continue;
                    }

                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(stt++);
                    row.createCell(1).setCellValue(u.getFullName());
                    row.createCell(2).setCellValue(Math.round(summary[0] * 100.0) / 100.0);
                    row.createCell(3).setCellValue((int) summary[1]);
                    row.createCell(4).setCellValue((int) summary[2]);
                    row.createCell(5).setCellValue((int) summary[3]);
                    row.createCell(6).setCellValue((int) summary[4]);
                    row.createCell(7).setCellValue((int) summary[5]);
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                workbook.write(out);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xuất Excel.");
        }
    }
}
