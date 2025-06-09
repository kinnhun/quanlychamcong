package controller.admin;

import dal.LocationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Locations;

import java.io.IOException;

@WebServlet(name = "AdminLocationEditController", urlPatterns = {"/admin/location-edit"})
public class AdminLocationEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        LocationDAO dao = new LocationDAO();
        Locations location = dao.getById(id);

        if (location != null) {
            request.setAttribute("location", location);
            request.getRequestDispatcher("/view/admin/location-edit.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("error", "Không tìm thấy địa điểm");
            response.sendRedirect(request.getContextPath() + "/admin/locations");
            return; // ✅ Dừng xử lý response tại đây
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String ipMap = request.getParameter("ipMap");
        boolean isActive = Boolean.parseBoolean(request.getParameter("isActive"));

        Locations l = new Locations();
        l.setId(id);
        l.setName(name);
        l.setAddress(address);
        l.setIpMap(ipMap);
        l.setIsActive(isActive);

        LocationDAO dao = new LocationDAO();
        boolean success = dao.update(l);

        if (success) {
            request.getSession().setAttribute("message", "Cập nhật địa điểm thành công!");
        } else {
            request.getSession().setAttribute("error", "Cập nhật thất bại!");
        }

        response.sendRedirect(request.getContextPath() + "/admin/locations");
    }
}
