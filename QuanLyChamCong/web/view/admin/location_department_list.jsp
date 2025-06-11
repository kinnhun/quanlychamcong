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

                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h3 class="text-primary fw-bold fs-3 mb-0">📁 Danh sách phòng ban</h3>
                        <a href="${pageContext.request.contextPath}/admin/departments-add" class="btn btn-primary">
                            ➕ Thêm phòng ban mới
                        </a>
                    </div>


                    <div class="table-responsive">
                        <table class="table table-hover table-bordered text-center align-middle shadow rounded" id="department_table">
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
                                                   class="btn btn-outline-warning btn-sm px-2 py-1" title="Sửa">✏️</a>
                                                <button type="button" class="btn btn-outline-danger btn-sm px-2 py-1 delete-btn"
                                                        data-id="${d.departmentId}" title="Xoá">🗑️</button>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Style nâng cao -->
                    <style>
                        table#department_table {
                            border-radius: 10px;
                            overflow: hidden;
                            box-shadow: 0 2px 8px rgba(0,0,0,0.03);
                        }

                        table#department_table thead th {
                            font-size: 15px;
                            padding: 14px 12px;
                            font-weight: 700;
                        }

                        table#department_table tbody td {
                            font-size: 14px;
                            padding: 12px 10px;
                            color: #333;
                            vertical-align: middle;
                        }

                        table#department_table tbody tr:hover {
                            background-color: #f9fcff;
                        }

                        .badge {
                            border-radius: 30px !important;
                            font-size: 13px;
                            padding: 6px 12px;
                            box-shadow: 0 1px 3px rgba(0,0,0,0.06);
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

                        .d-flex.gap-1 > .btn {
                            margin: 0 2px;
                        }
                    </style>

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
