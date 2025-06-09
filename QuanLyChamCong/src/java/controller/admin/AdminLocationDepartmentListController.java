package controller.admin;


import dal.LocationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Departments;
import model.LocationDepartments;
import model.Locations;

@WebServlet(name = "AdminLocationDepartmentListController", urlPatterns = {"/admin/location-departments"})
public class AdminLocationDepartmentListController extends HttpServlet {


  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LocationDAO locationDao = new LocationDAO();
        
        // Lấy danh sách LocationDepartments
        List<LocationDepartments> locationDepartments = locationDao.getAllLocationDepartments();

        // Lấy danh sách Location và Departments để hiển thị
        List<Locations> locations = locationDao.getAllLocations();
        List<Departments> departments = locationDao.getAllDepartments();

        // Đẩy dữ liệu sang JSP
        request.setAttribute("locationDepartments", locationDepartments);
        request.setAttribute("locations", locations);
        request.setAttribute("departments", departments);

        // Forward sang trang JSP
        request.getRequestDispatcher("/view/admin/location_department_list.jsp").forward(request, response);
    }

}
