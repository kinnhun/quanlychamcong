package conntroller.employee;

import dal.AttendanceDAO;
import dal.AttendanceDisputeDAO;
import model.Attendance;
import model.AttendanceDispute;
import model.Users;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "EmployeeDisputeSubmitServlet", urlPatterns = {"/employee/disputes"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 10 * 1024 * 1024, // 10MB
        maxRequestSize = 15 * 1024 * 1024 // 15MB
)
public class EmployeeDisputeSubmitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/employee/dispute_create.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String attendanceDateStr = request.getParameter("attendance_id"); // yyyy-MM-dd
        String reason = request.getParameter("reason");
        String issueType = request.getParameter("issue_type");

        // Xử lý lấy attendanceId từ ngày và user
        int attendanceId = -1;
        AttendanceDAO attDao = new AttendanceDAO();
        try {
            LocalDate date = LocalDate.parse(attendanceDateStr);
            Attendance att = attDao.getAttendanceToday(user.getUserId(), date);
            if (att == null) {
                // Nếu chưa có chấm công, có thể cho phép khiếu nại hoặc báo lỗi
                session.setAttribute("error", "Không tìm thấy bản ghi chấm công ngày này.");
                response.sendRedirect(request.getContextPath() + "/employee/disputes");
                return;
            }
            attendanceId = att.getAttendanceId();
        } catch (Exception ex) {
            session.setAttribute("error", "Ngày chấm công không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/employee/disputes");
            return;
        }

        // Xử lý file upload
        Part filePart = request.getPart("attachment");
        String attachmentPath = null;
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadFolder = getServletContext().getRealPath("/uploads/dispute");
            Files.createDirectories(Paths.get(uploadFolder));
            String storedFileName = System.currentTimeMillis() + "_" + fileName;
            String filePath = uploadFolder + File.separator + storedFileName;
            filePart.write(filePath);
            attachmentPath = "/uploads/dispute/" + storedFileName;
        }

        // Tạo đối tượng khiếu nại
        AttendanceDispute dispute = new AttendanceDispute();
        // Nếu dùng attendanceId là int
        AttendanceDAO adao = new AttendanceDAO();
        Attendance aa= adao.getAttendanceById(attendanceId);
        dispute.setAttendanceId(aa);

        dispute.setUserId(user.getUserId());
        dispute.setReason(reason);
        dispute.setIssueType(issueType);
        dispute.setAttachmentPath(attachmentPath);

        // Gọi DAO để lưu vào DB
        AttendanceDisputeDAO dao = new AttendanceDisputeDAO();
        boolean success = dao.createDispute(dispute);

        if (success) {
            session.setAttribute("message", "Gửi khiếu nại thành công!");
        } else {
            session.setAttribute("error", "Gửi khiếu nại thất bại!");
        }
        response.sendRedirect(request.getContextPath() + "/employee/disputes");
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý gửi khiếu nại chấm công cho nhân viên";
    }
}
