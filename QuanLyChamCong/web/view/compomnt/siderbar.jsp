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
                        <li class="sidebar-item">
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/dashboard.jsp">
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
                            <a class="sidebar-link sidebar-link" href="${pageContext.request.contextPath}/admin/attendance">
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