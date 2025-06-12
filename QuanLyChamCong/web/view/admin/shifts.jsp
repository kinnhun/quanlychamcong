<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>⏰ Danh sách ca làm</title>
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
        <div class="container-fluid" style="background-color: #ffffff">
            <!-- Thông báo -->
            <c:import url="/view/compomnt/notification.jsp" />

            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="text-primary fw-bold fs-3 mb-0">⏰ Danh sách ca làm</h3>
                <a href="${pageContext.request.contextPath}/admin/shifts-add" class="btn btn-primary">
                    ➕ Thêm ca làm
                </a>
            </div>

            <div class="table-responsive">
                <table class="table table-hover table-bordered text-center align-middle shadow rounded" id="shift_table">
                    <thead class="table-light">
                        <tr class="fw-bold text-secondary">
                            <th>ID</th>
                            <th>Tên ca</th>
                            <th>Giờ bắt đầu</th>
                            <th>Giờ kết thúc</th>
                            <th>Mô tả</th>
                            <th>Ngày tạo</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${shiftList}">
                            <tr>
                                <td>${s.shiftId}</td>
                                <td class="text-start fw-semibold">${s.shiftName}</td>
                                <td>${s.startTime}</td>
                                <td>${s.endTime}</td>
                                <td>${s.description}</td>
                                <td><fmt:formatDate value="${s.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td>
                                    <div class="d-flex justify-content-center gap-1">
                                        <a href="${pageContext.request.contextPath}/admin/shifts-edit?id=${s.shiftId}"
                                           class="btn btn-outline-warning btn-sm px-2 py-1" title="Sửa">✏️</a>
                                        <button type="button" class="btn btn-outline-danger btn-sm px-2 py-1 delete-btn"
                                                data-id="${s.shiftId}" title="Xoá">🗑️</button>
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
                                title: 'Bạn có chắc muốn xóa ca làm này?',
                                icon: 'warning',
                                showCancelButton: true,
                                confirmButtonText: '🗑️ Xóa',
                                cancelButtonText: 'Hủy',
                                confirmButtonColor: '#d33',
                                cancelButtonColor: '#6c757d'
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    window.location.href = '${pageContext.request.contextPath}/admin/shifts-delete?id=' + id;
                                }
                            });
                        });
                    });
                });
            </script>

        </div>
    </div>

    <!-- Footer -->
    <c:import url="/view/compomnt/footer.jsp" />
</div>
</body>
</html>
