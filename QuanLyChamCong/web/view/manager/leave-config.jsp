<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Cấu hình ngày phép</title>
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


                    <h3 class="fw-bold text-primary mt-4 mb-4 d-flex justify-content-between align-items-center">
                        Cấu hình ngày phép
                        <a href="${pageContext.request.contextPath}/manager/leave-config-add" class="btn btn-success">
                            + Thêm mới
                        </a>
                    </h3>


                    <!-- Bảng Danh Sách -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover bg-white">
                            <thead class="table-primary">
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Năm</th>
                                    <th scope="col">Loại nghỉ phép</th>
                                    <th scope="col">Số ngày mặc định</th>
                                    <th scope="col">Người tạo</th>
                                    <th scope="col">Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="config" items="${configs}" varStatus="loop">
                                    <tr>
                                        <td>${loop.index + 1}</td>
                                        <td>${config.year}</td>
                                        <td>${config.leaveTypeId.leaveTypeName}</td>
                                        <td>${config.defaultDays}</td>
                                        <td>${config.createdBy.fullName}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/manager/leave-config-edit?id=${config.configId}" 
                                               class="btn btn-sm btn-primary">
                                                Sửa
                                            </a>
                                        </td>

                                    </tr>
                                </c:forEach>

                                <c:if test="${empty leaveConfigs}">
                                    <tr>
                                        <td colspan="6" class="text-center text-muted py-4">Chưa có cấu hình nào.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>

                </div> <!-- container-fluid -->
            </div> <!-- page-wrapper -->

        </div> <!-- main-wrapper -->

        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />

    </body>
</html>
