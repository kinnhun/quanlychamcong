<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>📁 Danh sách phòng ban</title>
    <link href="${pageContext.request.contextPath}/view/lib/dist/css/style.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div id="main-wrapper" data-theme="light" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed" data-boxed-layout="full">

    <!-- Import header -->
    <c:import url="/view/compomnt/header.jsp" />

    <!-- Import sidebar -->
    <c:import url="/view/compomnt/siderbar.jsp" />

    <div class="page-wrapper">
        <div class="container-fluid" style="background-color: #ffffff">
            <!-- Thông báo -->
            <c:import url="/view/compomnt/notification.jsp" />

            <h3 class="mb-4 text-primary fw-bold fs-3">📁 Danh sách phòng ban</h3>

            <a href="${pageContext.request.contextPath}/admin/departments-add" class="btn btn-primary mb-4">
    ➕ Thêm phòng ban mới
</a>

            <!-- Bảng danh sách -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover text-center align-middle shadow rounded">
                    <thead class="table-light">
                    <tr class="fw-bold text-secondary">
                        <th>ID</th>
                        <th>Tên</th>
                        <th>Mã</th>
                        <th>Mô tả</th>
                        <th>Ngày tạo</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="d" items="${departments}">
                        <tr>
                            <td>${d.departmentId}</td>
                            <td class="text-start fw-semibold">${d.departmentName}</td>
                            <td><span class="badge bg-info text-dark">${d.departmentCode}</span></td>
                            <td>${d.description}</td>
                            <td><fmt:formatDate value="${d.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td>
                                <div class="d-flex justify-content-center gap-1">
                                    <a href="${pageContext.request.contextPath}/admin/departments-edit?id=${d.departmentId}"
                                       class="btn btn-warning btn-sm px-2 py-1" title="Sửa">
                                        ✏️
                                    </a>
                                    <button type="button" class="btn btn-danger btn-sm px-2 py-1 delete-btn"
                                            data-id="${d.departmentId}" title="Xoá">
                                        🗑️
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- SweetAlert2 Delete -->
            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    document.querySelectorAll(".delete-btn").forEach(btn => {
                        btn.addEventListener("click", function () {
                            const id = this.getAttribute("data-id");
                            Swal.fire({
                                title: 'Bạn có chắc muốn xóa phòng ban này?',
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonText: '🗑️ Xóa',
                                cancelButtonText: 'Hủy',
                                confirmButtonColor: '#d33',
                                cancelButtonColor: '#6c757d'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    window.location.href = '${pageContext.request.contextPath}/admin/departments-delete?id=' + id;
                                }
                            });
                        });
                    });
                });
            </script>

        </div>
    </div>

    <!-- Import footer -->
    <c:import url="/view/compomnt/footer.jsp" />
</div>
</body>
</html>
