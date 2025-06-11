/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.admin;

import dal.LocationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Departments;
import model.Locations;


@WebServlet(name = "AdminLocationsDepartmentController", urlPatterns = {"/admin/locations-department"})
public class AdminLocationsDepartmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        try {
            int locationId = Integer.parseInt(request.getParameter("locationId"));

            LocationDAO dao = new LocationDAO();
            Locations location = dao.getLocationById(locationId);
            List<Departments> allDepartments = dao.getAllDepartments();
            List<Departments> assignedDepartments = dao.getDepartmentsByLocation(locationId);

            request.setAttribute("location", location);
            request.setAttribute("allDepartments", allDepartments);
            request.setAttribute("assignedDepartments", assignedDepartments);

            request.getRequestDispatcher("/view/admin/locations_departments.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/location");
        }
    }
}
