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
                <div class="container-fluid" style="background-color: #ffffff">
                    <!-- Thông báo -->
                    <c:import url="/view/compomnt/notification.jsp" />

                    <h3 class="mb-4 text-primary fw-bold fs-3">📋 Danh sách đơn xin nghỉ phép</h3>
                    <div class="table-responsive">
                        <!-- Filter -->
                        <form method="get" class="filter-container mb-3 shadow-sm p-3 bg-white rounded d-flex flex-wrap align-items-center" style="gap: 20px;">
                            <div class="me-4 mb-2">
                                <label for="filterUser" class="form-label fw-semibold text-secondary">🔍 Người nộp:</label>
                                <select id="filterUser" name="filterUser" class="form-select" onchange="this.form.submit()">
                                    <option value="">-- Tất cả --</option>
                                    <c:forEach items="${userNames}" var="name">
                                        <option value="${name}" <c:if test="${filterUser == name}">selected</c:if>>${name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="me-4 mb-2">
                                <label for="filterDepartment" class="form-label fw-semibold text-secondary">🏢 Phòng ban:</label>
                                <select id="filterDepartment" name="filterDepartment" class="form-select" onchange="this.form.submit()">
                                    <option value="">-- Tất cả --</option>
                                    <c:forEach items="${deptNames}" var="name">
                                        <option value="${name}" <c:if test="${filterDepartment == name}">selected</c:if>>${name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-2">
                                <label for="filterStatus" class="form-label fw-semibold text-secondary">📌 Trạng thái:</label>
                                <select id="filterStatus" name="filterStatus" class="form-select" onchange="this.form.submit()">
                                    <option value="">-- Tất cả --</option>
                                    <option value="Đã duyệt" <c:if test="${filterStatus == 'Đã duyệt'}">selected</c:if>>Đã duyệt</option>
                                    <option value="Đang chờ" <c:if test="${filterStatus == 'Đang chờ'}">selected</c:if>>Đang chờ</option>
                                    <option value="Từ chối" <c:if test="${filterStatus == 'Từ chối'}">selected</c:if>>Từ chối</option>
                                    <option value="Đã hủy" <c:if test="${filterStatus == 'Đã hủy'}">selected</c:if>>Đã hủy</option>
                                </select>
                            </div>
                            <div class="ms-auto">
                                <a href="${pageContext.request.contextPath}/manager/leave-requests" class="btn btn-link btn-sm">Xóa lọc</a>
                            </div>
                        </form>

                        <!-- Table -->
                        <table id="multi_col_order" class="table table-hover table-bordered text-center align-middle shadow rounded">
                            <thead class="table-light">
                                <tr class="fw-bold text-secondary">
                                    <th>#</th>
                                    <th style="width: 200px">Người nộp đơn</th>
                                    <th style="width: 200px">Địa điểm</th>
                                    <th>Phòng ban</th>
                                    <th>Loại nghỉ</th>
                                    <th>Thời gian</th>
                                    <th>Số ngày</th>
                                    <th>Trạng thái</th>
                                    <th>Người duyệt</th>
                                    <th>Ngày tạo</th>
                                    <th>Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty requests}">
                                        <tr>
                                            <td colspan="11" class="text-center text-muted">Không có dữ liệu phù hợp</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${requests}" var="r">
                                            <tr>
                                                <td>${r.requestId}</td>
                                                <td class="text-start fw-semibold">${r.user.fullName}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${r.locations != null}">
                                                            ${r.locations.name}
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i>Không có</i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${r.departments != null}">
                                                            ${r.departments.departmentName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i>Không có</i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="text-start fw-semibold"><span class="badge bg-info bg-gradient text-dark">${r.leaveTypeId.leaveTypeName}</span></td>
                                                <td>
                                                    <fmt:formatDate value="${r.startDate}" pattern="dd/MM/yyyy" /> -
                                                    <fmt:formatDate value="${r.endDate}" pattern="dd/MM/yyyy" />
                                                </td>
                                                <td><strong>${r.daysCount}</strong></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${r.status == 'approved'}">
                                                            <span class="badge bg-success bg-gradient">Đã duyệt</span>
                                                        </c:when>
                                                        <c:when test="${r.status == 'pending'}">
                                                            <span class="badge bg-warning text-dark bg-gradient">Đang chờ</span>
                                                        </c:when>
                                                        <c:when test="${r.status == 'rejected'}">
                                                            <span class="badge bg-danger bg-gradient">Từ chối</span>
                                                        </c:when>
                                                        <c:when test="${r.status == 'cancelled'}">
                                                            <span class="badge bg-secondary bg-gradient">Đã hủy</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span>${r.status}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:out value="${r.approvedBy != null ? r.approvedBy.fullName : 'Chưa duyệt'}" />
                                                </td>
                                                <td><fmt:formatDate value="${r.createdAt}" pattern="dd/MM/yyyy HH:mm" /></td>
                                                <td>
                                                    <c:if test="${r.status == 'pending'}">
                                                        <div class="d-flex justify-content-center gap-1">
                                                            <button class="btn btn-outline-success btn-sm approve-btn px-2 py-1" data-id="${r.requestId}" title="Phê duyệt">
                                                                ✅
                                                            </button>
                                                            <button class="btn btn-outline-danger btn-sm reject-btn px-2 py-1" data-id="${r.requestId}" title="Từ chối">
                                                                ❌
                                                            </button>
                                                            <button class="btn btn-outline-secondary btn-sm cancel-btn px-2 py-1" data-id="${r.requestId}" title="Hủy">
                                                                🚫
                                                            </button>
                                                        </div>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    <style>
                        /* ... giữ nguyên CSS của bạn như cũ ... */
                        /* Filter container */
                        .filter-container {
                            display: flex;
                            flex-wrap: wrap;
                            gap: 20px;
                            align-items: center;
                            margin-bottom: 20px;
                            padding: 15px 20px;
                            background-color: #f9fafb;
                            border: 1px solid #e3e6ea;
                            border-radius: 10px;
                            box-shadow: 0 1px 4px rgba(0,0,0,0.04);
                        }
                        .filter-container label {
                            font-weight: 600;
                            color: #495057;
                            margin-bottom: 6px;
                            display: block;
                        }
                        .filter-container select {
                            min-width: 200px;
                            padding: 8px 12px;
                            border-radius: 8px;
                            border: 1px solid #ced4da;
                            background-color: #ffffff;
                            font-size: 14px;
                            color: #495057;
                            box-shadow: inset 0 1px 2px rgba(0,0,0,0.03);
                            transition: border-color 0.2s, box-shadow 0.2s;
                        }
                        .filter-container select:focus {
                            border-color: #6c63ff;
                            box-shadow: 0 0 0 0.15rem rgba(108, 99, 255, 0.2);
                            outline: none;
                        }
                        table#multi_col_order {
                            border-radius: 10px;
                            overflow: hidden;
                            box-shadow: 0 2px 8px rgba(0,0,0,0.03);
                        }
                        table#multi_col_order thead {
                            background-color: #f1f3f5;
                            color: #333;
                        }
                        table#multi_col_order thead th {
                            font-size: 15px;
                            padding: 14px 12px;
                            font-weight: 700;
                        }
                        table#multi_col_order tbody td {
                            font-size: 14px;
                            padding: 12px 10px;
                            color: #333;
                            vertical-align: middle;
                        }
                        table#multi_col_order tbody tr {
                            transition: background-color 0.25s ease;
                        }
                        table#multi_col_order tbody tr:hover {
                            background-color: #f9fcff;
                        }
                        .badge {
                            border-radius: 30px !important;
                            font-size: 13px;
                            padding: 6px 12px;
                            box-shadow: 0 1px 3px rgba(0,0,0,0.06);
                        }
                        .bg-success {
                            background-color: #d1e7dd !important;
                            color: #0f5132 !important;
                        }
                        .bg-warning {
                            background-color: #fff3cd !important;
                            color: #664d03 !important;
                        }
                        .bg-danger {
                            background-color: #f8d7da !important;
                            color: #842029 !important;
                        }
                        .bg-info {
                            background-color: #cff4fc !important;
                            color: #055160 !important;
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
                        div.dataTables_filter { float: right !important; }
                        div.dataTables_paginate { float: right !important; }
                        div.dataTables_length { float: left !important; }
                        .dataTables_wrapper .dataTables_filter input {
                            margin-left: 0.5rem;
                            padding: 6px 10px;
                            border-radius: 8px;
                            border: 1px solid #ced4da;
                        }
                    </style>

                    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
                    <script>
                        document.addEventListener("DOMContentLoaded", function () {
                            // Approve/Reject/Cancel giữ nguyên
                            document.querySelectorAll(".approve-btn").forEach(btn => {
                                btn.addEventListener("click", function () {
                                    const requestId = this.getAttribute("data-id");
                                    Swal.fire({
                                        title: 'Phê duyệt đơn xin nghỉ',
                                        input: 'text',
                                        inputLabel: 'Nhập lý do phê duyệt (tuỳ chọn)',
                                        inputPlaceholder: 'Ví dụ: Đơn hợp lệ',
                                        showCancelButton: true,
                                        confirmButtonText: '✅ Phê duyệt',
                                        cancelButtonText: '❌ Hủy',
                                        confirmButtonColor: '#198754',
                                        cancelButtonColor: '#d33'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            const note = encodeURIComponent(result.value || '');
                                            window.location.href = '${pageContext.request.contextPath}/manager/leave-requests-approve?id=' + requestId + '&note=' + note;
                                        }
                                    });
                                });
                            });
                            document.querySelectorAll(".reject-btn").forEach(btn => {
                                btn.addEventListener("click", function () {
                                    const requestId = this.getAttribute("data-id");
                                    Swal.fire({
                                        title: 'Từ chối đơn xin nghỉ',
                                        input: 'text',
                                        inputLabel: 'Nhập lý do từ chối',
                                        inputPlaceholder: 'Ví dụ: Không đủ giấy tờ',
                                        showCancelButton: true,
                                        confirmButtonText: '❌ Từ chối',
                                        cancelButtonText: 'Hủy',
                                        confirmButtonColor: '#dc3545',
                                        cancelButtonColor: '#6c757d'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            const note = encodeURIComponent(result.value || '');
                                            window.location.href = '${pageContext.request.contextPath}/manager/leave-requests-reject?id=' + requestId + '&note=' + note;
                                        }
                                    });
                                });
                            });
                            document.querySelectorAll(".cancel-btn").forEach(button => {
                                button.addEventListener("click", function () {
                                    const id = this.getAttribute("data-id");
                                    Swal.fire({
                                        title: "Bạn có chắc muốn hủy đơn này?",
                                        input: "text",
                                        inputLabel: "Lý do hủy (tùy chọn)",
                                        inputPlaceholder: "Ví dụ: Hủy do lịch thay đổi",
                                        showCancelButton: true,
                                        confirmButtonText: "🚫 Hủy đơn",
                                        cancelButtonText: "Thoát"
                                    }).then(result => {
                                        if (result.isConfirmed) {
                                            const note = encodeURIComponent(result.value || '');
                                            window.location.href = '${pageContext.request.contextPath}/manager/leave-requests-cancel?id=' + id + '&note=' + note;
                                        }
                                    });
                                });
                            });
                        });
                    </script>
                </div>
            </div>
        </div>
        <!-- Import footer -->
        <c:import url="/view/compomnt/footer.jsp" />
    </body>
</html>
