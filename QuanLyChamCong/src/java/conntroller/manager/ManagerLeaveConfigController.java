package conntroller.manager;

import dal.LeaveRequestDAO;
import model.LeaveConfig;
import model.Users;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LeaveConfigController", urlPatterns = {"/manager/leave-config"})
public class ManagerLeaveConfigController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LeaveRequestDAO dao = new LeaveRequestDAO();

        List<LeaveConfig> list = dao.getAllConfigs();

        request.setAttribute("configs", list);
        request.getRequestDispatcher("/view/manager/leave-config.jsp").forward(request, response);
    }

}
