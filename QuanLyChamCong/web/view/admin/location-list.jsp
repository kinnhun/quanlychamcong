<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách địa điểm chấm công</title>
        <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
             data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

            <!-- Import header -->
            <c:import url="/view/compomnt/header.jsp" />

            <!-- Import sidebar -->
            <c:import url="/view/compomnt/siderbar.jsp" />

            <div class="page-wrapper">
                <div class="container-fluid">
                    <!-- Notification -->
                    <c:import url="/view/compomnt/notification.jsp" />

                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <h4 class="card-title mb-0">Danh sách địa điểm chấm công</h4>
                                        <a href="${pageContext.request.contextPath}/admin/location-add" class="btn btn-primary">
                                            <i class="fa fa-plus-circle"></i> Thêm địa điểm
                                        </a>
                                    </div>

                                    <div class="table-responsive">
                                        <table id="multi_col_order"
                                               class="table table-striped table-bordered display no-wrap" style="width:100%">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Tên địa điểm</th>
                                                    <th>Địa chỉ</th>
                                                    <th>IP Map</th>
                                                    <th>Trạng thái</th>
                                                    <th>Hành động</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="l" items="${locationList}">
                                                    <tr>
                                                        <td>${l.id}</td>
                                                        <td>${l.name}</td>
                                                        <td>${l.address}</td>
                                                        <td>${l.ipMap}</td>
                                                        <td>
                                                            <span class="badge badge-${l.isActive ? 'success' : 'secondary'}">
                                                                ${l.isActive ? 'Đang dùng' : 'Ngừng dùng'}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <a href="${pageContext.request.contextPath}/admin/location-edit?id=${l.id}"
                                                               class="btn btn-sm btn-warning">Sửa</a>
                                                            <c:choose>
                                                                <c:when test="${l.isActive}">
                                                                    <a href="${pageContext.request.contextPath}/admin/location-toggle?id=${l.id}&status=false"
                                                                       class="btn btn-sm btn-secondary"
                                                                       onclick="return confirm('Bạn có muốn vô hiệu hóa địa điểm này không?');">
                                                                        Vô hiệu hóa
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a href="${pageContext.request.contextPath}/admin/location-toggle?id=${l.id}&status=true"
                                                                       class="btn btn-sm btn-success"
                                                                       onclick="return confirm('Bạn có muốn kích hoạt địa điểm này không?');">
                                                                        Kích hoạt
                                                                    </a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Import footer -->
            <c:import url="/view/compomnt/footer.jsp" />
        </div>
    </body>
</html>
