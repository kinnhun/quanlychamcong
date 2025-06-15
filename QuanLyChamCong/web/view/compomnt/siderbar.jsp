<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/view/lib/assets/images/favicon.png">
        <title>Admin Panel</title>
        <link href="${pageContext.request.contextPath}/view/lib/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/lib/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    </head>
    <body>


        <!-- ✅ 2. siderbar.jsp -->
        <aside class="left-sidebar" data-sidebarbg="skin6">
            <div class="scroll-sidebar" data-sidebarbg="skin6">
                <nav class="sidebar-nav">
                    <ul id="sidebarnav">

                        <p>Admin</p>
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/dashboard">
                                <i data-feather="home" class="feather-icon"></i>
                                <span class="hide-menu">Dashboard</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/users">
                                <i data-feather="users" class="feather-icon"></i>
                                <span class="hide-menu">Quản lý tài khoản</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/locations">
                                <i data-feather="map-pin" class="feather-icon"></i>
                                <span class="hide-menu">Quản lý địa điểm</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/location-departments">
                                <i data-feather="map-pin" class="feather-icon"></i>
                                <span class="hide-menu">Quản lý phòng ban</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/assign-user-location">
                                <i data-feather="user-check" class="feather-icon"></i>
                                <span class="hide-menu">Phân công nhân viên</span>
                            </a>
                        </li>

                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/shifts">
                                <i data-feather="clock" class="feather-icon"></i>
                                <span class="hide-menu">Quản lý ca làm</span>
                            </a>
                        </li>

                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/leave-types">
                                <i data-feather="tag" class="feather-icon"></i>
                                <span class="hide-menu">Loại nghỉ phép</span>
                            </a>
                        </li>

                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/leave-config">
                                <i data-feather="settings" class="feather-icon"></i>
                                <span class="hide-menu">Cấu hình ngày phép</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link" href="${pageContext.request.contextPath}/admin/holiday-dates">
                                <i data-feather="calendar" class="feather-icon"></i>
                                <span class="hide-menu"> CNgày nghỉ lễ</span>
                            </a>
                        </li>



                        <p>Manager</p>
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/manager/leave-requests">
                                <i data-feather="file-text" class="feather-icon"></i>
                                <span class="hide-menu">Quản lý đơn xin nghỉ</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link" id="attendance-link" href="#">
                                <i data-feather="clock" class="feather-icon"></i>
                                <span class="hide-menu">Quản lý chấm công</span>
                            </a>
                        </li>

                        <script>
                            document.addEventListener("DOMContentLoaded", function () {
                                var link = document.getElementById("attendance-link");
                                var today = new Date();
                                var yyyy = today.getFullYear();
                                var mm = String(today.getMonth() + 1).padStart(2, '0');
                                var dd = String(today.getDate()).padStart(2, '0');
                                var dateString = yyyy + '-' + mm + '-' + dd;

                                // Nếu cần truyền thêm userId, status rỗng thì cứ để ""
                                var url = '${pageContext.request.contextPath}/manager/attendance-list?userId=&status=&date=' + dateString;
                                link.href = url;
                            });
                        </script>

                        <li class="sidebar-item">
                            <a class="sidebar-link" href="${pageContext.request.contextPath}/manager/user-shifts">
                                <i data-feather="calendar" class="feather-icon"></i>
                                <span class="hide-menu">Quản lý phân ca</span>
                            </a>
                        </li>





                        <p>Employee</p>
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/employee/leave-request-create">
                                <i data-feather="file-plus" class="feather-icon"></i>
                                <span class="hide-menu">Tạo đơn nghỉ</span>
                            </a>
                        </li>

                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/employee/leave-requests">
                                <i data-feather="list" class="feather-icon"></i>
                                <span class="hide-menu">Danh sách đơn</span>
                            </a>
                        </li>



                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/attendance">
                                <i data-feather="calendar" class="feather-icon"></i>
                                <span class="hide-menu">Chấm công</span>
                            </a>
                        </li>
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/logout">
                                <i data-feather="log-out" class="feather-icon"></i>
                                <span class="hide-menu">Đăng xuất</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </aside>
        <!-- End Sidebar -->


        <!-- JS Scripts -->
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/jquery/dist/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/popper.js/dist/umd/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/dist/js/feather.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/dist/js/sidebarmenu.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/dist/js/custom.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/extra-libs/c3/d3.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/extra-libs/c3/c3.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/chartist/dist/chartist.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.min.js"></script>
        <script src="${pageContext.request.contextPath}/view/lib/dist/js/pages/dashboards/dashboard1.min.js"></script>
    </body>
</html>