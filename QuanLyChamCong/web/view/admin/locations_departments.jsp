<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <title>📌 Phòng ban theo địa điểm</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    </head>
    <body>
        <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6"
             data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed"
             data-boxed-layout="full">

            <!-- Header & Sidebar -->
            <c:import url="/view/compomnt/header.jsp" />
            <c:import url="/view/compomnt/siderbar.jsp" />

            <div class="page-wrapper">
                <div class="container-fluid">

                    <!-- Notification -->
                    <c:import url="/view/compomnt/notification.jsp" />

                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h3 class="text-primary fw-bold fs-3 mb-0">📌 Phòng ban tại địa điểm: ${location.name}</h3>
                        <a href="${pageContext.request.contextPath}/admin/locations" class="btn btn-outline-secondary">
                            ⬅️ Quay lại danh sách địa điểm
                        </a>
                    </div>

                   

                    <!-- Danh sách phòng ban đã gán -->
                    <div class="card mb-4">
                        <div class="card-body">
 <div class="d-flex justify-content-between align-items-center mb-3">
                        <h5 class="card-title fw-bold mb-0">📂 Danh sách phòng ban đã gán</h5>
                        <a href="${pageContext.request.contextPath}/admin/assign-department?locationId=${location.id}" 
                           class="btn btn-success">
                            ➕ Gán phòng ban mới
                        </a>
                    </div>

                        </div>
                        <table class="table table-hover table-bordered text-center align-middle" id="location_dep_table">
                            <thead class="table-light">
                                <tr class="fw-bold text-secondary">
                                    <th>ID</th>
                                    <th>Tên phòng ban</th>
                                    <th>Mã</th>
                                    <th>Mô tả</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="d" items="${assignedDepartments}">
                                    <tr>
                                        <td>${d.departmentId}</td>
                                        <td class="text-start fw-semibold">${d.departmentName}</td>
                                        <td><span class="badge bg-info text-dark">${d.departmentCode}</span></td>
                                        <td>${d.description}</td>
                                        
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>





                <style>
                    #location_dep_table th, #location_dep_table td {
                        font-size: 14px;
                        padding: 10px;
                    }

                    .badge {
                        border-radius: 20px !important;
                        padding: 6px 12px;
                        font-size: 13px;
                    }

                    .btn-sm {
                        font-size: 13px !important;
                        padding: 6px 10px !important;
                        border-radius: 8px !important;
                        transition: all 0.2s ease;
                    }

                    .btn-sm:hover {
                        transform: translateY(-1px);
                        box-shadow: 0 3px 6px rgba(0,0,0,0.08);
                    }
                </style>
            </div>
        </div>

        <!-- Footer -->
        <c:import url="/view/compomnt/footer.jsp" />
    </div>
</body>
</html>
