<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Danh sách tài khoản</title>
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
                    <!-- Thông báo -->
                    <c:import url="/view/compomnt/notification.jsp" />


                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="d-flex justify-content-between align-items-center mb-3">
                                        <h4 class="card-title mb-0">Danh sách tài khoản</h4>
                                        <a href="${pageContext.request.contextPath}/admin/user-add" class="btn btn-primary">
                                            <i class="fa fa-plus-circle"></i> Thêm tài khoản
                                        </a>
                                    </div>


                                    <div class="table-responsive">
                                        <table id="multi_col_order"
                                               class="table table-striped table-bordered display no-wrap" style="width:100%">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Tài khoản</th>
                                                    <th>Họ tên</th>
                                                    <th>Email</th>
                                                    <th>SĐT</th>
                                                    <th>Vai trò</th>
                                                    <th>Trạng thái</th>
                                                    <th>Ngày tạo</th>
                                                    <th>Hành động</th>
                                                </tr>

                                            </thead>
                                            <tbody>
                                                <c:forEach var="u" items="${userList}">
                                                    <tr>
                                                        <td>${u.userId}</td>
                                                        <td>${u.username}</td>
                                                        <td>${u.fullName}</td>
                                                        <td>${u.email}</td>
                                                        <td>${u.phone}</td>
                                                        <td>
                                                            <span class="badge badge-${u.role == 'admin' ? 'danger' : (u.role == 'manager' ? 'warning' : 'info')}">
                                                                ${u.role}
                                                            </span>
                                                        </td>
                                                        <td>
                                                            <span class="badge badge-${u.status == 'active' ? 'success' : 'secondary'}">
                                                                ${u.status}
                                                            </span>
                                                        </td>
                                                        <td><fmt:formatDate value="${u.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${u.status == 'banned'}">
                                                                    <a href="${pageContext.request.contextPath}/admin/user-unban?userId=${u.userId}"
                                                                       class="btn btn-sm btn-success"
                                                                       onclick="return confirm('Bạn có muốn mở khóa tài khoản này không?');">
                                                                        Unban
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a href="${pageContext.request.contextPath}/admin/user-ban?userId=${u.userId}"
                                                                       class="btn btn-sm btn-danger"
                                                                       onclick="return confirm('Bạn có chắc chắn muốn ban tài khoản này không?');">
                                                                        Ban
                                                                    </a>
                                                                </c:otherwise>
                                                            </c:choose>


                                                            <a href="${pageContext.request.contextPath}/admin/user-edit?userId=${u.userId}" 
                                                               class="btn btn-sm btn-warning">Sửa</a>
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

        </div>

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />


    </div>
</body>
</html>
