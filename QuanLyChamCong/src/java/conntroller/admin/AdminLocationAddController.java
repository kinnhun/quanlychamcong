package controller.admin;

import dal.LocationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Locations;

import java.io.IOException;

@WebServlet(name = "AdminLocationAddController", urlPatterns = {"/admin/location-add"})
public class AdminLocationAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/admin/location-add.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String ipMap = request.getParameter("ipMap");

        Locations location = new Locations();
        location.setName(name);
        location.setAddress(address);
        location.setIpMap(ipMap);
        location.setIsActive(true);

        LocationDAO dao = new LocationDAO();
        boolean success = dao.insert(location);

        if (success) {
            request.getSession().setAttribute("message", "Thêm địa điểm thành công!");
            response.sendRedirect("locations");
        } else {
            request.setAttribute("error", "Thêm địa điểm thất bại!");
            request.getRequestDispatcher("/view/admin/location-add.jsp").forward(request, response);
        }
    }
}
